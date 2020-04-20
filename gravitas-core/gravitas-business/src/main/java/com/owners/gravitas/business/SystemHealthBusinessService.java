package com.owners.gravitas.business;

import java.util.List;

import com.owners.gravitas.amqp.AgentSource;
import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.amqp.OpportunityContact;
import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.domain.entity.SchedulerLog;
import com.owners.gravitas.dto.GravitasHealthStatus;

/**
 * The Interface SystemHealthBusinessService.
 *
 * @author ankusht
 */
public interface SystemHealthBusinessService {

    /**
     * Check system health.
     *
     * @param schedulerLog
     *            the scheduler log
     * @return the scheduler log
     */
    void checkSystemHealth( final SchedulerLog schedulerLog );

    /**
     * Gets the gravitas health status.
     *
     * @return the gravitas health status
     */
    List< GravitasHealthStatus > getGravitasHealthStatus();

    /**
     * Listen to lead change topic.
     *
     * @param {@link
     *            LeadSource}
     */
    void listenToLeadChangeTopic( LeadSource leadSource );

    /**
     * Listen to opportunity change topic.
     *
     * @param {@link
     *            OpportunitySource}
     */
    void listenToOpportunityChangeTopic( OpportunitySource opportunitySource );

    /**
     * Listen to opportunity create topic.
     *
     * @param {@link
     *            OpportunitySource}
     */
    void listenToOpportunityCreateTopic( OpportunitySource opportunitySource );

    /**
     * Listen to contact change topic.
     *
     * @param {@link
     *            OpportunityContact}
     */
    void listenToContactChangeTopic( OpportunityContact opportunityContact );

    /**
     * Listen to agent create topic.
     *
     * @param {@link
     *            AgentSource}
     */
    void listenToAgentCreateTopic();

    /**
     * Check system errors.
     */
    void checkSystemErrors();

    /**
     * Notify gravitas db down.
     *
     * @param errorMsg
     *            the error msg
     */
    void notifyGravitasDbDown( String errorMsg );

    /**
     * Listen to lead create topic.
     *
     * @param leadSource the lead source
     */
    void listenToLeadCreateTopic( LeadSource leadSource );
}
