package com.owners.gravitas.service;

import com.owners.gravitas.domain.entity.AgentAvailabilityLog;
import com.owners.gravitas.domain.entity.AgentDetails;

/**
 * The Interface AgentAvailabilityLogService.
 * 
 * @author pabhishek
 */
public interface AgentAvailabilityLogService {

    /**
     * Save log.
     *
     * @param agentAvailabilityLog
     *            the agent availability log
     * @return the agent availability log
     */
    AgentAvailabilityLog saveLog( AgentAvailabilityLog agentAvailabilityLog );

    /**
     * Gets the log.
     *
     * @param agentDetails
     *            the agent details
     * @param inProcess
     *            the in process
     * @return the log
     */
    AgentAvailabilityLog getLog( AgentDetails agentDetails, boolean inProcess );
}
