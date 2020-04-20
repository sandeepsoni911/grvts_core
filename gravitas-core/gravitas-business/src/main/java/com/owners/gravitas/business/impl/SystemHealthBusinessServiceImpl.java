/*
 *
 */
package com.owners.gravitas.business.impl;

import static com.owners.gravitas.constants.Constants.CAUSE_COLUMN_LENGTH;
import static com.owners.gravitas.enums.CRMObject.OPPORTUNITY;
import static com.owners.gravitas.enums.ErrorCode.INTERNAL_SERVER_ERROR;
import static com.owners.gravitas.enums.GravitasSystemType.FIREBASE;
import static com.owners.gravitas.enums.GravitasSystemType.GRAVITAS_DB;
import static com.owners.gravitas.enums.GravitasSystemType.RABBIT_MQ;
import static com.owners.gravitas.enums.GravitasSystemType.SALESFORCE;
import static com.owners.gravitas.enums.RecordType.BUYER;
import static com.owners.gravitas.enums.RecordType.OWNERS;
import static com.owners.gravitas.enums.TopicName.AGENT;
import static com.owners.gravitas.enums.TopicName.CONTACT;
import static com.owners.gravitas.enums.TopicName.LEAD;
import static com.owners.gravitas.enums.TopicName.LEAD_CREATE;
import static com.owners.gravitas.enums.TopicName.OPPORTUNITY_CHANGE;
import static com.owners.gravitas.enums.TopicName.OPPORTUNITY_CREATE;
import static com.owners.gravitas.enums.TopicName.values;
import static com.owners.gravitas.enums.TopicStatusType.active;
import static com.owners.gravitas.enums.TopicStatusType.inprogress;
import static java.lang.System.currentTimeMillis;
import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.amqp.OpportunityContact;
import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.GravitasNotificationBusinessService;
import com.owners.gravitas.business.LeadBusinessService;
import com.owners.gravitas.business.SystemHealthBusinessService;
import com.owners.gravitas.config.SystemHealthJmxConfig;
import com.owners.gravitas.domain.entity.SchedulerLog;
import com.owners.gravitas.domain.entity.SystemErrorLog;
import com.owners.gravitas.domain.entity.TopicStatus;
import com.owners.gravitas.dto.GravitasHealthStatus;
import com.owners.gravitas.enums.CRMObject;
import com.owners.gravitas.enums.GravitasSystemType;
import com.owners.gravitas.enums.TopicName;
import com.owners.gravitas.event.FailedTopicEvent;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.exception.ResultNotFoundException;
import com.owners.gravitas.listener.AmqpCompositeConnectionListener;
import com.owners.gravitas.service.CRMAuthenticatorService;
import com.owners.gravitas.service.ContactService;
import com.owners.gravitas.service.OpportunityService;
import com.owners.gravitas.service.RecordTypeService;
import com.owners.gravitas.service.SystemErrorLogService;
import com.owners.gravitas.service.SystemHealthService;
import com.owners.gravitas.service.TopicStatusService;

/**
 * The Class SystemHealthBusinessServiceImpl.
 *
 * @author ankusht
 */
@Service( "systemHealthBusinessService" )
public class SystemHealthBusinessServiceImpl implements SystemHealthBusinessService {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( SystemHealthBusinessServiceImpl.class );

    /** The Constant TOPIC. */
    private static final String TOPIC = " Topic";

    /** The Constant SALESFORCE_TOPICS. */
    private static final String SALESFORCE_TOPICS = "Salesforce Topic Services";

    /** The crm authenticator. */
    @Autowired
    @Qualifier( "crmRestAuthenticator" )
    private CRMAuthenticatorService crmAuthenticatorService;

    /** The system error log service. */
    @Autowired
    private SystemErrorLogService systemErrorLogService;

    /** The system health service. */
    @Autowired
    private SystemHealthService systemHealthService;

    /** The opportunity service. */
    @Autowired
    private OpportunityService opportunityService;

    /** The amqp composite connection listener. */
    @Autowired
    private AmqpCompositeConnectionListener amqpCompositeConnectionListener;

    /** The business service. */
    @Autowired
    private LeadBusinessService leadBusinessService;

    /** The record type service. */
    @Autowired
    private RecordTypeService recordTypeService;

    /** The topic status service. */
    @Autowired
    private TopicStatusService topicStatusService;

    /** The contact service. */
    @Autowired
    private ContactService contactService;

    /** The gravitas notification business service. */
    @Autowired
    private GravitasNotificationBusinessService gravitasNotificationBusinessService;

    /** The system health jmx config. */
    @Autowired
    private SystemHealthJmxConfig systemHealthJmxConfig;

    /** The application event publisher. */
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.SystemHealthBusinessService#
     * checkSystemHealth(com.owners.gravitas.domain.entity.SchedulerLog)
     */
    @Override
    public void checkSystemHealth( final SchedulerLog schedulerLog ) {
        try {
            final Set< SystemErrorLog > systemErrorLogs = new HashSet<>();
            final DateTime now = schedulerLog.getCreatedDate();
            checkFirebaseConnection( schedulerLog, systemErrorLogs, now );
            checkSalesforceConnection( schedulerLog, systemErrorLogs, now );
            checkRabbitMQConnection( schedulerLog, systemErrorLogs, now );
            checkGravitasDBConnection( schedulerLog, systemErrorLogs, now );
            if (!systemErrorLogs.isEmpty()) {
                systemErrorLogService.saveSystemErrorLogs( systemErrorLogs );
            }
            checkTopicListeners( schedulerLog );
        } catch ( final Exception jce ) {
            if (jce instanceof DataAccessException || jce instanceof TransactionException) {
                notifyGravitasDbDown( getStackTrace( jce ) );
            } else {
                throw jce;
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.SystemHealthBusinessService#
     * checkSystemErrors()
     */
    @Override
    @Transactional( readOnly = true, propagation = REQUIRES_NEW )
    public void checkSystemErrors() {
        try {
            Thread.sleep( systemHealthJmxConfig.getContactTopicUpdateWaitDuration() );
            LOGGER.debug( "Getting all systems health statuses" );
            final List< GravitasHealthStatus > gravitasHealthStatuses = getGravitasHealthStatus();
            gravitasHealthStatuses.forEach( status -> {
                if (CollectionUtils.isEmpty( status.getSubSystems() )) {
                    notifySystemError( status );
                } else {
                    status.getSubSystems().forEach( subSyst -> {
                        notifySystemError( subSyst );
                    } );
                }
            } );
        } catch ( final Exception e ) {
            LOGGER.error( "Unable to notify slack channel about system errors", e );
        }
    }

    /**
     * Notify system error.
     *
     * @param status
     *            the status
     */
    private void notifySystemError( final GravitasHealthStatus status ) {
        if (!status.isWorking()) {
            if (status.getSystemName().contains( TOPIC )) {
                final TopicName topicName = TopicName.getTopicName( status.getSystemName() );
                if (topicName != null) {
                    applicationEventPublisher.publishEvent( new FailedTopicEvent( this, topicName ) );
                }
            }
            gravitasNotificationBusinessService.notifySystemError( status );
        }
    }

    /**
     * Check rabbit MQ connection.
     *
     * @param schedulerLog
     *            the scheduler log
     * @param systemErrorLogs
     *            the scheduler result logs
     * @param now
     *            the now
     */
    private void checkRabbitMQConnection( final SchedulerLog schedulerLog, final Set< SystemErrorLog > systemErrorLogs,
            final DateTime now ) {
        if (!amqpCompositeConnectionListener.isBrokerReachable()) {
            systemErrorLogs.add( buildErrorLog( schedulerLog, RABBIT_MQ.getType(),
                    systemHealthJmxConfig.getRabbitmqDownMsg(), now ) );
        }
    }

    /**
     * Check salesforce connection.
     *
     * @param schedulerLog
     *            the scheduler log
     * @param systemErrorLogs
     *            the scheduler result logs
     * @param now
     *            the now
     */
    private void checkSalesforceConnection( final SchedulerLog schedulerLog,
            final Set< SystemErrorLog > systemErrorLogs, final DateTime now ) {
        try {
            crmAuthenticatorService.authenticate();
        } catch ( final Exception e ) {
            systemErrorLogs.add( buildErrorLog( schedulerLog, SALESFORCE.getType(), getStackTrace( e ), now ) );
        }
    }

    /**
     * Check gravitas DB connection.
     *
     * @param schedulerLog
     *            the scheduler log
     * @param systemErrorLogs
     *            the scheduler result logs
     * @param now
     *            the now
     */
    private void checkGravitasDBConnection( final SchedulerLog schedulerLog,
            final Set< SystemErrorLog > systemErrorLogs, final DateTime now ) {
        try {
            systemHealthService.executeQueryOnGravitasDB();
        } catch ( final Exception e ) {
            systemErrorLogs.add( buildErrorLog( schedulerLog, GRAVITAS_DB.getType(), getStackTrace( e ), now ) );
        }
    }

    /**
     * Check firebase connection.
     *
     * @param schedulerLog
     *            the scheduler log
     * @param systemErrorLogs
     *            the scheduler result logs
     * @param now
     *            the now
     */
    private void checkFirebaseConnection( final SchedulerLog schedulerLog, final Set< SystemErrorLog > systemErrorLogs,
            final DateTime now ) {
        try {
            systemHealthService.connectToRefDataNode();
        } catch ( final Exception e ) {
            systemErrorLogs.add( buildErrorLog( schedulerLog, FIREBASE.getType(), getStackTrace( e ), now ) );
        }
    }

    /**
     * Builds the error log.
     *
     * @param schedulerLog
     *            the scheduler log
     * @param systemName
     *            the system name
     * @param cause
     *            the cause
     * @param now
     *            the now
     * @return the scheduler result log
     */
    private SystemErrorLog buildErrorLog( final SchedulerLog schedulerLog, final String systemName, final String cause,
            final DateTime now ) {
        final SystemErrorLog errorLog = new SystemErrorLog();
        errorLog.setSchedulerLog( schedulerLog );
        errorLog.setSystemName( systemName );
        if (cause.length() > CAUSE_COLUMN_LENGTH) {
            errorLog.setCause( cause.substring( 0, CAUSE_COLUMN_LENGTH ) );
        } else {
            errorLog.setCause( cause );
        }
        errorLog.setCreatedDate( now );
        return errorLog;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.SystemHealthBusinessService#
     * getGravitasHealthStatus()
     */
    @Override
    @Transactional( readOnly = true, propagation = REQUIRES_NEW )
    public List< GravitasHealthStatus > getGravitasHealthStatus() {
        final Set< String > allSystems = getAllSystemNames();
        final List< SystemErrorLog > failedSystemsLogs = systemErrorLogService.findLatestSystemErrors();
        final List< GravitasHealthStatus > allStatuses = new ArrayList<>();
        final List< GravitasHealthStatus > topicStatuses = new ArrayList<>();
        final List< TopicStatus > topicStatusList = topicStatusService.findAll();
        for ( final String system : allSystems ) {
            if (system.contains( TOPIC )) {
                topicStatuses.add( checkForTopicFailure( system, topicStatusList ) );
            } else {
                allStatuses.add( checkForFailure( system, failedSystemsLogs ) );
            }
        }
        allStatuses.add( buildTopicHealthObject( topicStatuses ) );
        return allStatuses;
    }

    /**
     * Gets the all system names.
     *
     * @return the all system names
     */
    private Set< String > getAllSystemNames() {
        final Set< String > allSystems = new LinkedHashSet<>();
        for ( final GravitasSystemType type : GravitasSystemType.values() ) {
            allSystems.add( type.getType() );
        }
        for ( final TopicName topicName : TopicName.values() ) {
            allSystems.add( topicName.getName() );
        }
        return allSystems;
    }

    /**
     * Check for topic failure.
     *
     * @param name
     *            the name
     * @param topicStatusList
     *            the topic status list
     * @return the gravitas health status
     */
    private GravitasHealthStatus checkForTopicFailure( final String name, final List< TopicStatus > topicStatusList ) {
        GravitasHealthStatus healthStatus = new GravitasHealthStatus( name, systemHealthJmxConfig.getServiceUpMsg(),
                true, EMPTY );
        final List< TopicStatus > filteredList = topicStatusList.stream()
                .filter( ts -> ts.getTopicName().equals( name ) ).collect( Collectors.toList() );
        if (!filteredList.isEmpty()) {
            final TopicStatus topicStatus = filteredList.get( 0 );
            if (!topicStatus.getStatus().equals( active.name() )) {
                healthStatus = new GravitasHealthStatus( name, systemHealthJmxConfig.getServiceDownMsg(), false,
                        String.format( systemHealthJmxConfig.getTopicDownMsg(), name ) );
            }
        }
        return healthStatus;
    }

    /**
     * Check for failure.
     *
     * @param name
     *            the name
     * @param failedSystemsLogs
     *            the failed systems logs
     * @return the gravitas health status
     */
    private GravitasHealthStatus checkForFailure( final String name, final List< SystemErrorLog > failedSystemsLogs ) {
        GravitasHealthStatus healthStatus = new GravitasHealthStatus( name, systemHealthJmxConfig.getServiceUpMsg(),
                true, EMPTY );
        for ( final SystemErrorLog errorLog : failedSystemsLogs ) {
            if (name.equals( errorLog.getSystemName() )) {
                healthStatus = new GravitasHealthStatus( name, systemHealthJmxConfig.getServiceDownMsg(), false,
                        errorLog.getCause() );
            }
        }
        return healthStatus;
    }

    /**
     * Builds the topic health object.
     *
     * @param topicsHealthStstusList
     *            the topics health ststus list
     * @return the gravitas health status
     */
    private GravitasHealthStatus buildTopicHealthObject( final List< GravitasHealthStatus > topicsHealthStstusList ) {
        final GravitasHealthStatus topicHealthStstus = new GravitasHealthStatus( SALESFORCE_TOPICS,
                systemHealthJmxConfig.getServiceDownMsg(), false, EMPTY );
        topicHealthStstus.setSubSystems( topicsHealthStstusList );
        if (topicsHealthStstusList.stream().allMatch( status -> status.isWorking() )) {
            topicHealthStstus.setWorking( true );
            topicHealthStstus.setMessage( systemHealthJmxConfig.getServiceUpMsg() );
        }
        return topicHealthStstus;
    }

    /**
     * Check topic listeners status.
     *
     * @param schedulerLog
     *            the scheduler log
     */
    private void checkTopicListeners( final SchedulerLog schedulerLog ) {
        resetTopicStatuses();
        systemHealthService.createCrmLead( systemHealthJmxConfig.getSystemHealthLeadEmail() );
        systemHealthService.createCrmAgent();
        updateCrmOpportunity();
    }

    /**
     * Update crm opportunity.
     */
    private void updateCrmOpportunity() {
        final String opportunityId = createDefaultOpportunity(
                systemHealthJmxConfig.getSystemHealthOpportunityEmail() );
        systemHealthService.updateCrmOpportunity( opportunityId );
        updateContact( opportunityId );
    }

    /**
     * Update contact.
     *
     * @param crmOpportunityId
     *            the crm opportunity id
     */
    private void updateContact( final String crmOpportunityId ) {
        final String contactId = contactService.findContactIdByOpportunityId( crmOpportunityId );
        final Map< String, Object > patchRequest = new HashMap<>();
        final String ownersRecordTypeId = recordTypeService.getRecordTypeIdByName( OWNERS.getType(),
                CRMObject.CONTACT.getName() );
        patchRequest.put( "LastName", currentTimeMillis() );
        patchRequest.put( "LeadSource", currentTimeMillis() );
        patchRequest.put( "RecordTypeId", ownersRecordTypeId ); // must always
                                                                // be updated
        contactService.patchContact( patchRequest, contactId );
    }

    /**
     * Insert topic data.
     */
    private void resetTopicStatuses() {
        final Set< String > topics = topicStatusService.findTopicNames();
        for ( final TopicName topicName : values() ) {
            final String name = topicName.getName();
            final String status = inprogress.name();
            if (isEmpty( topics ) || !topics.contains( name )) {
                topicStatusService.save( new TopicStatus( name, status ) );
            } else {
                topicStatusService.updateTopicStatus( name, status );
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.SystemHealthBusinessService#
     * listenToAgentCreateTopic(com.owners.gravitas.amqp.AgentSource)
     */
    @Override
    public void listenToAgentCreateTopic() {
        LOGGER.debug( "Agent topic is working fine" );
        topicStatusService.updateTopicStatus( AGENT.getName(), active.name() );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.SystemHealthBusinessService#
     * listenToLeadChangeTopic(com.owners.gravitas.amqp.LeadSource)
     */
    @Override
    public void listenToLeadChangeTopic( final LeadSource leadSource ) {
        LOGGER.debug( "Lead topic listner is working fine" );
        topicStatusService.updateTopicStatus( LEAD.getName(), active.name() );
        final String opportunityId = leadBusinessService.convertLeadToOpportunity( leadSource.getId() );
        LOGGER.debug( "Lead " + leadSource.getEmail() + " converted into opportunity: " + opportunityId );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.SystemHealthBusinessService#
     * listenToLeadCreateTopic(com.owners.gravitas.amqp.LeadSource)
     */
    @Override
    public void listenToLeadCreateTopic( final LeadSource leadSource ) {
        LOGGER.debug( "Lead topic create listner is working fine" );
        topicStatusService.updateTopicStatus( LEAD_CREATE.getName(), active.name() );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.SystemHealthBusinessService#
     * listenToOpportunityCreateTopic(com.owners.gravitas.amqp.
     * OpportunitySource)
     */
    @Override
    public void listenToOpportunityCreateTopic( final OpportunitySource opportunitySource ) {
        LOGGER.debug( "Opportunity create topic listener is working fine" );
        topicStatusService.updateTopicStatus( OPPORTUNITY_CREATE.getName(), active.name() );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.SystemHealthBusinessService#
     * listenToOpportunityChangeTopic(com.owners.gravitas.amqp.
     * OpportunitySource)
     */
    @Override
    public void listenToOpportunityChangeTopic( final OpportunitySource opportunitySource ) {
        LOGGER.debug( "Opportunity change topic listener is working fine" );
        topicStatusService.updateTopicStatus( OPPORTUNITY_CHANGE.getName(), active.name() );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.SystemHealthBusinessService#
     * listenToContactChangeTopic(com.owners.gravitas.amqp.OpportunityContact)
     */
    @Override
    public void listenToContactChangeTopic( final OpportunityContact opportunityContact ) {
        LOGGER.debug( "Contact change topic listener is working fine" );
        topicStatusService.updateTopicStatus( CONTACT.getName(), active.name() );
    }

    /**
     * Creates the default opportunity.
     *
     * @param email
     *            the email
     * @return the string
     */
    private String createDefaultOpportunity( final String email ) {
        String opportunityId = EMPTY;
        try {
            final String oppRecordTypeId = recordTypeService.getRecordTypeIdByName( BUYER.name(), OPPORTUNITY.name() );
            opportunityId = opportunityService.getOpportunityIdByRecordTypeIdAndEmail( oppRecordTypeId, email );
        } catch ( final ResultNotFoundException e ) {
            // if opportunity doesn't exist already, create it
            final String leadId = systemHealthService.createDefaultLeadIfNotExist( email );
            if (isNotEmpty( leadId )) {
                opportunityId = leadBusinessService.convertLeadToOpportunity( leadId );
                if (StringUtils.isEmpty( opportunityId )) {
                    throw new ApplicationException( "Default buyer opportunity creation failed for email:" + email,
                            INTERNAL_SERVER_ERROR );
                }
            } else {
                throw new ApplicationException( "Default buyer lead creation failed for email: " + email,
                        INTERNAL_SERVER_ERROR );
            }
            LOGGER.debug( "IGNORE: Problem in creating default opportunity", e );
        }
        return opportunityId;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.SystemHealthBusinessService#
     * notifyGravitasDbDown(java.lang.String)
     */
    @Override
    public void notifyGravitasDbDown( final String errorMsg ) {
        final GravitasHealthStatus gravitasHealthStatus = new GravitasHealthStatus( GRAVITAS_DB.getType(), errorMsg,
                false, EMPTY );
        gravitasNotificationBusinessService.notifySystemError( gravitasHealthStatus );
    }
}
