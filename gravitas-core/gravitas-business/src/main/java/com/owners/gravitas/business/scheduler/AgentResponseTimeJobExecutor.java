package com.owners.gravitas.business.scheduler;

import static com.owners.gravitas.enums.ActionEntity.OPPORTUNITY;
import static com.owners.gravitas.enums.BuyerStage.CLAIMED;
import static com.owners.gravitas.enums.BuyerStage.NEW;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.owners.gravitas.annotation.PerformanceLog;
import com.owners.gravitas.domain.Agent;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.domain.Stage;
import com.owners.gravitas.domain.entity.ActionLog;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.OpportunityResponseLog;
import com.owners.gravitas.lock.AgentNewOppSchedulerLockHandler;
import com.owners.gravitas.service.ActionLogService;
import com.owners.gravitas.service.ContactEntityService;
import com.owners.gravitas.service.OpportunityResponseLogService;
import com.owners.gravitas.service.OpportunityService;
import com.owners.gravitas.service.builder.OpportunityEntityBuilder;

/**
 * The Class AgentResponseTimeJobExecutor.
 *
 * @author raviz
 */
@Component
@ManagedResource( objectName = "com.owners.gravitas:name=AgentResponseTimeJobExecutor" )
public class AgentResponseTimeJobExecutor {

    /** The file path. */
    private String filePath;

    /** The Constant POOL_SIZE. */
    private static final int POOL_SIZE = 100;

    /** The Constant CONSIDERED. */
    private static final String CONSIDERED = "considered";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentResponseTimeJobExecutor.class );

    /** The Constant AGENT_RESPONSE_TIME_JOB_LOCK_KEY. */
    private static final String AGENT_RESPONSE_TIME_JOB_LOCK_KEY = "AGENT_RESPONSE_TIME_JOB_LOCK_KEY";

    /** The lock handler. */
    @Autowired
    private AgentNewOppSchedulerLockHandler lockHandler;

    /** The opportunity service. */
    @Autowired
    private OpportunityService opportunityService;

    /** The mapper. */
    @Autowired
    private ObjectMapper mapper;

    /** The action log service. */
    @Autowired
    private ActionLogService actionLogService;

    /** The entity opportunity builder v1. */
    @Autowired
    private OpportunityEntityBuilder entityOpportunityBuilderV1;

    /** The contact service v1. */
    @Autowired
    private ContactEntityService contactServiceV1;

    /** The opportunity response log service. */
    @Autowired
    private OpportunityResponseLogService opportunityResponseLogService;

    /** The executor. */
    private ThreadPoolExecutor executor = null;

    /** The action logs. */
    private Map< String, ActionLog > actionLogMap = null;

    /** The default response time. */
    @Value( "${opportunity.default.response.time}" )
    private int defaultResponseTime;

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        executor = ( ThreadPoolExecutor ) Executors.newFixedThreadPool( POOL_SIZE );
    }

    /**
     * Gets the action logs.
     *
     * @return the action logs
     */
    private void getActionLogs() {
        actionLogMap = new TreeMap<>();
        final List< ActionLog > actionLogs = actionLogService.getActionLogs( OPPORTUNITY.name(), getCtaValues() );
        actionLogs.forEach( actionLog -> {
            final String oppId = actionLog.getActionEntityId();
            if (!actionLogMap.containsKey( oppId )) {
                actionLogMap.put( oppId, actionLog );
            }
        } );
    }

    /**
     * Shutdown.
     */
    @PreDestroy
    public void shutdown() {
        executor.shutdown();
    }

    /**
     * Sets the file path.
     *
     * @param filePath
     *            the new file path
     */
    @ManagedOperation
    public void setFilePath( final String filePath ) {
        this.filePath = filePath;
    }

    /**
     * Retry sync agents response time.
     *
     * @param status
     *            the status
     */
    @ManagedOperation
    @PerformanceLog
    public void syncAgentsResponseTime( final String status ) {
        LOGGER.info( "Executing agent response time job at " + LocalDateTime.now() );
        if (lockHandler.acquireLock( AGENT_RESPONSE_TIME_JOB_LOCK_KEY )) {
            try {
                List< String > failedOppIds = null;
                if (isNotBlank( status )) {
                    failedOppIds = opportunityResponseLogService.getFailedOpportunities( status );
                }
                syncAgentsResponseTime( failedOppIds );
            } catch ( final Exception e ) {
                LOGGER.error( e.toString() );
            } finally {
                lockHandler.releaseLock( AGENT_RESPONSE_TIME_JOB_LOCK_KEY );
            }
        }
    }

    /**
     * Sync agents response time.
     *
     * @param failedOppIds
     *            the failed ids
     */
    private void syncAgentsResponseTime( final List< String > failedOppIds ) {
        LOGGER.info( "Executing agent response time job at " + LocalDateTime.now() );
        getActionLogs();
        try {
            final Map< String, LinkedHashMap > agentMap = readJsonData();
            if (MapUtils.isNotEmpty( agentMap )) {
                int opCount = 0;
                int agentCount = 0;
                for ( final Entry< String, LinkedHashMap > agentEntry : agentMap.entrySet() ) {
                    try {
                        agentCount++;
                        final Agent agent = mapper.convertValue( agentEntry.getValue(), Agent.class );
                        final String agentEmail = agent.getInfo().getEmail();
                        LOGGER.info( "Synching data for agent: " + agentEmail );
                        final Map< String, Opportunity > opportunities = agent.getOpportunities();
                        if (MapUtils.isNotEmpty( opportunities )) {
                            opCount += opportunities.size();
                            LOGGER.info( "agent=" + agentEmail + " opportunities=" + opportunities.size() );
                            for ( final Entry< String, Opportunity > entry : opportunities.entrySet() ) {
                                if (CollectionUtils.isEmpty( failedOppIds )) {
                                    createTask( agentEmail, entry );
                                } else {
                                    if (failedOppIds.contains( entry.getKey() )) {
                                        createTask( agentEmail, entry );
                                    }
                                }
                            }
                        }
                    } catch ( final Exception e ) {
                        LOGGER.error( e.toString() );
                    }
                }
                LOGGER.info( "Total no of opportunities=" + opCount );
                LOGGER.info( "Total no of agents=" + agentCount );
            }
        } catch ( final Exception e ) {
            LOGGER.error( e.toString() );
        }
    }

    /**
     * Creates the task.
     *
     * @param agentEmail
     *            the agent email
     * @param entry
     *            the entry
     */
    private void createTask( final String agentEmail, final Entry< String, Opportunity > entry ) {
        final ResponseTimeTask task = new ResponseTimeTask( entry, agentEmail );
        try {
            executor.submit( task );
        } catch ( final Exception e ) {
            LOGGER.error( "", e );
        }
    }

    /**
     * Read json data.
     *
     * @return the map
     */
    private Map< String, LinkedHashMap > readJsonData() {
        Map< String, LinkedHashMap > agentMap = new HashMap<>();
        try {
            LOGGER.info( "reading file from " + filePath );
            agentMap = mapper.readValue( new FileInputStream( new File( filePath ) ), Map.class );
        } catch ( final Exception e ) {
            LOGGER.error( "some exception --->", e );
        }
        return agentMap;
    }

    /**
     * Gets the cta values.
     *
     * @return the cta values
     */
    private List< String > getCtaValues() {
        final List< String > ctaValues = new ArrayList<>();
        ctaValues.add( "sms" );
        ctaValues.add( "email" );
        ctaValues.add( "call" );
        return ctaValues;
    }

    /**
     * The Class ResponseTimeTask.
     */
    private class ResponseTimeTask implements Runnable {

        /** The Constant UPDATED_BY. */
        private static final String UPDATED_BY = "updated by ";

        /** The Constant CREATED_BY. */
        private static final String CREATED_BY = "created by ";

        /** The agent id. */
        private Entry< String, Opportunity > entry;

        /** The agent email. */
        private String agentEmail;

        /**
         * Instantiates a new response time task.
         *
         * @param oppEntry
         *            the opp entry
         * @param agentEmail
         *            the agent email
         */
        public ResponseTimeTask( final Entry< String, Opportunity > oppEntry, final String agentEmail ) {
            super();
            this.entry = oppEntry;
            this.agentEmail = agentEmail;
        }

        /**
         * Run.
         */
        /*
         * (non-Javadoc)
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run() {
            final String threadName = Thread.currentThread().getName() + "---" + Thread.currentThread().getId();
            final String fbOppId = entry.getKey();
            final OpportunityResponseLog log = opportunityResponseLogService
                    .save( new OpportunityResponseLog( threadName, fbOppId, agentEmail, CONSIDERED ) );
            try {
                final Opportunity opportunity = entry.getValue();
                LOGGER.info( threadName + ":-> FB Opportunity: " + fbOppId + " CRMID: " + opportunity.getCrmId() );
                com.owners.gravitas.domain.entity.Opportunity opportunityV1 = opportunityService
                        .getOpportunityByFbId( fbOppId );
                if (opportunityV1 == null) {
                    final Contact contact = saveContact( opportunity, fbOppId );
                    opportunityV1 = contact.getOpportunities().iterator().next();
                    updateOpportunityV1( threadName, fbOppId, opportunity, log, opportunityV1 );
                    log.setStatus( CREATED_BY + threadName );
                } else {
                    updateOpportunityV1( threadName, fbOppId, opportunity, log, opportunityV1 );
                }
                log.setThreadId( threadName );
            } catch ( final Exception e ) {
                LOGGER.error( "Exception occurred in thread: " + threadName + " for opp: " + fbOppId, e );
                log.setStatus( "Exception:" + e.getMessage() );
            }
            opportunityResponseLogService.save( log );
        }

        /**
         * Update opportunity v1.
         *
         * @param threadName
         *            the thread name
         * @param fbOppId
         *            the fb opp id
         * @param opportunity
         *            the opportunity
         * @param log
         *            the log
         * @param opportunityV1
         *            the opportunity v1
         */
        private void updateOpportunityV1( final String threadName, final String fbOppId, final Opportunity opportunity,
                final OpportunityResponseLog log, final com.owners.gravitas.domain.entity.Opportunity opportunityV1 ) {
            LOGGER.info( threadName + ":-> found OpportunityV1: " + opportunityV1.getId() );
            final DateTime firstContactDtm = computeFirstContactDtm( fbOppId, opportunity, opportunityV1, threadName );
            if (firstContactDtm != null) {
                LOGGER.info( threadName + ":-> firstContactDtm calculated as: " + firstContactDtm + "="
                        + firstContactDtm.getMillis() );
                updateTimes( opportunityV1, firstContactDtm );
                log.setStatus( UPDATED_BY + threadName );
            }
        }

        /**
         * Save contact.
         *
         * @param opportunity
         *            the opportunity
         * @param fbOppId
         *            the fb opp id
         * @return the contact
         */
        private Contact saveContact( final Opportunity opportunity, final String fbOppId ) {
            Contact contact = null;
            contact = entityOpportunityBuilderV1.convertFrom( contact, opportunity, fbOppId, agentEmail );
            return contactServiceV1.save( contact );
        }

        /**
         * Compute first contact dtm.
         *
         * @param fbOppId
         *            the fb opp id
         * @param opportunity
         *            the opportunity
         * @param opportunityV1
         *            the opportunity v1
         * @param threadName
         *            the thread name
         * @return the date time
         */
        private DateTime computeFirstContactDtm( final String fbOppId, final Opportunity opportunity,
                final com.owners.gravitas.domain.entity.Opportunity opportunityV1, final String threadName ) {
            final ActionLog actionLog = actionLogMap.get( fbOppId );
            DateTime firstContactDtm = null;
            if (actionLog != null) {
                firstContactDtm = actionLog.getCreatedDate();
            }

            final List< Stage > stageHistory = opportunity.getStageHistory();
            if (CollectionUtils.isNotEmpty( stageHistory )) {
                final Stage firstStage = stageHistory.get( 0 );
                LOGGER.info( "FIRST STAGE IS " + firstStage );
                if (!isNewOrClaimed( firstStage )) {
                    firstContactDtm = opportunityV1.getAssignedDate().plus( defaultResponseTime );
                    LOGGER.info(
                            threadName + ":-> first stage for :" + fbOppId + " was " + stageHistory.get( 0 ).getStage()
                                    + " hence setting default response time as " + defaultResponseTime );
                } else {
                    for ( final Stage stage : stageHistory ) {
                        if (!isNewOrClaimed( stage )) {
                            LOGGER.info( "STAGE IS " + stage );
                            final Long stageTimeStamp = stage.getTimestamp();
                            if (firstContactDtm == null) {
                                firstContactDtm = new DateTime( stageTimeStamp );
                            } else if (firstContactDtm.isAfter( stageTimeStamp )) {
                                firstContactDtm = new DateTime( stageTimeStamp );
                            }
                            break;
                        }
                    }
                }
            } else {
                LOGGER.info(
                        threadName + ":-> only 1 stage for :" + fbOppId + " = " + stageHistory.get( 0 ).getStage() );
            }
            return firstContactDtm;
        }

        /**
         * Checks if is new or claimed.
         *
         * @param stage
         *            the stage
         * @return true, if is new or claimed
         */
        private boolean isNewOrClaimed( final Stage stage ) {
            return NEW.getStage().equalsIgnoreCase( stage.getStage() )
                    || CLAIMED.getStage().equalsIgnoreCase( stage.getStage() );
        }

        /**
         * Update times.
         *
         * @param opportunityV1
         *            the opportunity v1
         * @param firstContactDtm
         *            the first contact dtm
         */
        private void updateTimes( final com.owners.gravitas.domain.entity.Opportunity opportunityV1,
                final DateTime firstContactDtm ) {
            opportunityV1.setFirstContactDtm( firstContactDtm );
            final long responseTime = firstContactDtm.minus( opportunityV1.getAssignedDate().getMillis() ).getMillis();
            if (responseTime > 0) {
                opportunityV1.setResponseTime( responseTime );
            }
            opportunityService.save( opportunityV1 );
        }

    }
}
