package com.owners.gravitas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owners.gravitas.domain.entity.AgentAvailabilityLog;
import com.owners.gravitas.domain.entity.AgentDetails;

/**
 * The Interface AgentAvailabilityLogRepository.
 * 
 * @author pabhishek
 */
public interface AgentAvailabilityLogRepository extends JpaRepository< AgentAvailabilityLog, String > {
    /**
     * Find by agent details and in process.
     *
     * @param agentDetails
     *            the agent details
     * @param inProcess
     *            the in process
     * @return the agent availability log
     */
    AgentAvailabilityLog findByAgentDetailsAndInProcess( AgentDetails agentDetails, boolean inProcess );

}
