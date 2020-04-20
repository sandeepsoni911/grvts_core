/*
 * 
 */
package com.owners.gravitas.business.task;

import static com.owners.gravitas.constants.Constants.BEST_FIT_AGENT_DAY_THRESHOLD;
import static com.owners.gravitas.constants.Constants.BEST_FIT_AGENT_OPPORTUNITY_THRESHOLD;
import static com.owners.gravitas.constants.Constants.OPPORTUNITY_RR_THRESHOLD;
import static com.owners.gravitas.enums.AssignmentStatus.considered;
import static com.owners.gravitas.enums.HungryAgentsStatus.HUNGRY_CONSIDERED;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.business.builder.AgentAssignmentLogBuilder;
import com.owners.gravitas.config.HungryAgentsConfig;
import com.owners.gravitas.domain.entity.AgentAssignmentLog;
import com.owners.gravitas.domain.entity.AgentDetailsV1;
import com.owners.gravitas.domain.entity.PotentialOpportunityAgentAssignmentLog;
import com.owners.gravitas.domain.entity.PotentialOpportunityAssignmentLog;
import com.owners.gravitas.dto.Agent;
import com.owners.gravitas.dto.AgentAssignmentLogDto;
import com.owners.gravitas.dto.crm.response.CRMOpportunityResponse;
import com.owners.gravitas.service.CbsaMarketLevelService;
import com.owners.gravitas.service.OpportunityAssignmentLogService;
import com.owners.gravitas.service.OpportunityService;

/**
 * The Class AgentLookupTask.
 *
 * @author amits
 */
@Service
public class AgentLookupTask {

    /** The Constant DISPLAYED. */
    private static final String DISPLAYED = "displayed";

    /** The Constant IGNORED. */
    private static final String IGNORED = "ignored";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentLookupTask.class );

    /** The log service. */
    @Autowired
    private OpportunityAssignmentLogService logService;

    /** The agent assignment log builder. */
    @Autowired
    private AgentAssignmentLogBuilder agentAssignmentLogBuilder;

    /** The cbsa market level service. */
    @Autowired
    private CbsaMarketLevelService cbsaMarketLevelService;

    /** The opportunity service. */
    @Autowired
    private OpportunityService opportunityService;

    /** The hungry agents config. */
    @Autowired
    private HungryAgentsConfig hungryAgentsConfig;

    /**
     * Opportunity assignment audit.
     *
     * @param zipcode
     *            the zipcode
     * @param state
     *            the state
     * @param crmOpportunity
     *            the crm opportunity
     * @param thresholds
     *            the thresholds
     * @param agents
     *            the agents
     * @param bestFitAgents
     *            the best fit agents
     * @param currentUser
     *            the current user
     */
    @Async( value = "apiExecutor" )
    public void auditOpportunityAssignment( final String zipcode, final String state,
            final CRMOpportunityResponse crmOpportunity, final Map< String, Integer > thresholds,
            final List< AgentDetailsV1 > agents, final List< Agent > bestFitAgents, final String currentUser ) {
        LOGGER.debug( "Audit log for opportunity agent assignment " + crmOpportunity );
        final PotentialOpportunityAssignmentLog opportunityLog = buildPotentialOpportunityAssignmentLog( zipcode, state,
                crmOpportunity, thresholds, currentUser );
        final List< PotentialOpportunityAgentAssignmentLog > logs = new ArrayList<>();
        int priority = 1;
        // iterate through selected agents by sequence and remove them from
        // master list.
        for ( final Agent agent : bestFitAgents ) {
            final AgentDetailsV1 agentDetails = getAgentDetailByEmail( agents, agent );
            final PotentialOpportunityAgentAssignmentLog agentLog = buildPotentialOpportunityAgentAssignmentLog(
                    currentUser, opportunityLog, agentDetails, DISPLAYED, priority++ );
            logs.add( agentLog );
            agents.remove( agentDetails );
        }
        // iterate remaining filtered out agents.
        for ( final AgentDetailsV1 agent : agents ) {
            final PotentialOpportunityAgentAssignmentLog agentLog = buildPotentialOpportunityAgentAssignmentLog(
                    currentUser, opportunityLog, agent, IGNORED, -1 );
            logs.add( agentLog );
        }
        opportunityLog.setAgents( logs );
        logService.save( opportunityLog );
    }

    /**
     * Agent assign audit.
     *
     * @param crmId
     *            the crm id
     * @param agentEmail
     *            the agent email
     * @param currentUser
     *            the current user
     */
    @Async( value = "apiExecutor" )
    public void updateAgentAssignmentAudit( final String crmId, final String agentEmail, final String currentUser ) {
        LOGGER.debug( "Audit log for opportunity agent assignment " + crmId );
        logService.updateAgentAssignmentAudit( crmId.substring( 0, 15 ), agentEmail, currentUser );
    }

    /**
     * Builds the potential opportunity agent assignment log.
     *
     * @param currentUser
     *            the current user
     * @param opportunityLog
     *            the opportunity log
     * @param agent
     *            the agent
     * @param status
     *            the status
     * @param priority
     *            the priority
     * @return the potential opportunity agent assignment log
     */
    private PotentialOpportunityAgentAssignmentLog buildPotentialOpportunityAgentAssignmentLog(
            final String currentUser, final PotentialOpportunityAssignmentLog opportunityLog,
            final AgentDetailsV1 agent, final String status, final int priority ) {
        final PotentialOpportunityAgentAssignmentLog log = new PotentialOpportunityAgentAssignmentLog();
        log.setOpportunity( opportunityLog );
        log.setPriority( priority );
        log.setAgentEmail( agent.getEmail() );
        log.setAgentScore( agent.getScore() );
        log.setAgentStatus( agent.getStatus() );
        log.setStatus( status );
        log.setCreatedBy( currentUser );
        final DateTime currentTime = new DateTime( System.currentTimeMillis() );
        log.setCreatedDate( currentTime );
        log.setLastModifiedBy( currentUser );
        log.setLastModifiedDate( currentTime );
        return log;
    }

    /**
     * Builds the potential opportunity log.
     *
     * @param zipcode
     *            the zipcode
     * @param state
     *            the state
     * @param crmOpportunity
     *            the crm opportunity
     * @param thresholds
     *            the thresholds
     * @param currentUser
     *            the current user
     * @return the potential opportunity assignment log
     */
    private PotentialOpportunityAssignmentLog buildPotentialOpportunityAssignmentLog( final String zipcode,
            final String state, final CRMOpportunityResponse crmOpportunity, final Map< String, Integer > thresholds,
            final String currentUser ) {
        final PotentialOpportunityAssignmentLog log = new PotentialOpportunityAssignmentLog();
        log.setCrmId( crmOpportunity.getId().substring( 0, 15 ) );
        log.setOpportunityLabel( crmOpportunity.getLabel() );
        log.setOpportunityScore( crmOpportunity.getScore() );
        log.setZip( zipcode );
        log.setState( state );
        log.setOpportunityThreshold( thresholds.get( BEST_FIT_AGENT_OPPORTUNITY_THRESHOLD ) );
        log.setDayThreshold( thresholds.get( BEST_FIT_AGENT_DAY_THRESHOLD ) );
        log.setRrThreshold( thresholds.get( OPPORTUNITY_RR_THRESHOLD ) );
        log.setCreatedBy( currentUser );
        log.setCreatedDate( new DateTime( System.currentTimeMillis() ) );
        return log;
    }

    /**
     * Gets the agent detail by email.
     *
     * @param agents
     *            the agents
     * @param agent
     *            the agent
     * @return the agent detail by email
     */
    private AgentDetailsV1 getAgentDetailByEmail( final List< AgentDetailsV1 > agents, final Agent agent ) {
        return agents.stream().filter( streamAgent -> streamAgent.getEmail().equals( agent.getEmail() ) ).findAny()
                .get();
    }

    /**
     * Builds the assignment log.
     *
     * @param dto
     *            the dto
     * @param crmOpportunityId
     *            the crm opportunity id
     * @param isHungryAgentLookup
     *            the is hungry agent lookup
     * @return the future
     */
    @Async( value = "apiExecutor" )
    @Transactional( readOnly = true )
    public Future< AgentAssignmentLog > buildAssignmentLog( final AgentAssignmentLogDto dto,
            final String crmOpportunityId, final boolean isHungryAgentLookup ) {
        final AgentAssignmentLog agentAssignmentLog = agentAssignmentLogBuilder.convertTo( dto );
        if (isHungryAgentLookup) {
            agentAssignmentLog.setAssignmentStatus( HUNGRY_CONSIDERED.name().toLowerCase() );
        } else {
            agentAssignmentLog.setAssignmentStatus( considered.name() );
        }
        agentAssignmentLog.setCrmOpportunityId( crmOpportunityId );
        final int count = getAssignedOppsCountForThreshold( agentAssignmentLog.getCbsaCode(),
                agentAssignmentLog.getAgentEmail(), isHungryAgentLookup );
        agentAssignmentLog.setNumberOfOppsInThresholdPeriod( count );
        return new AsyncResult<>( agentAssignmentLog );
    }

    /**
     * Gets the assigned opps count for threshold.
     *
     * @param cbsaCode
     *            the cbsa code
     * @param agentEmail
     *            the agent email
     * @param isHungryAgentLookup
     *            the is hungry agent lookup
     * @return the assigned opps count for threshold
     */
    private int getAssignedOppsCountForThreshold( final String cbsaCode, final String agentEmail,
            final boolean isHungryAgentLookup ) {
        int thresholdPeriodDays = 0;
        if (isHungryAgentLookup) {
            thresholdPeriodDays = hungryAgentsConfig.getHungryAgentsOppAssignmentPeriod();
        } else {
            thresholdPeriodDays = cbsaMarketLevelService.findThresholdByCbsaCode( cbsaCode );
        }
        final DateTime assignedDate = DateTime.now().minusDays( thresholdPeriodDays );
        return opportunityService.getAgentOppCountByAssignedDate( agentEmail, assignedDate );
    }
}
