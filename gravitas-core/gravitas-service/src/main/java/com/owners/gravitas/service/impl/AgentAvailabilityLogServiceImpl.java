package com.owners.gravitas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.domain.entity.AgentAvailabilityLog;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.repository.AgentAvailabilityLogRepository;
import com.owners.gravitas.service.AgentAvailabilityLogService;

/**
 * The Class AgentAvailabilityLogServiceImpl.
 * 
 * @author pabhishek
 */
@Service
public class AgentAvailabilityLogServiceImpl implements AgentAvailabilityLogService {

    /** The agent availability log repository. */
    @Autowired
    private AgentAvailabilityLogRepository agentAvailabilityLogRepository;

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.AgentAvailabilityLogService#saveLog(com.
     * owners.gravitas.domain.entity.AgentAvailabilityLog)
     */
    @Override
    public AgentAvailabilityLog saveLog( final AgentAvailabilityLog agentAvailabilityLog ) {
        return agentAvailabilityLogRepository.save( agentAvailabilityLog );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentAvailabilityLogService#getLog(com.owners
     * .gravitas.domain.entity.AgentDetails, boolean)
     */
    @Override
    public AgentAvailabilityLog getLog( final AgentDetails agentDetails, final boolean inProcess ) {
        return agentAvailabilityLogRepository.findByAgentDetailsAndInProcess( agentDetails, inProcess );
    }
}
