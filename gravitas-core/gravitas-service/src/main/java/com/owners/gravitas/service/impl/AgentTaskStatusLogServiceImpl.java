package com.owners.gravitas.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.domain.entity.AgentTask;
import com.owners.gravitas.domain.entity.AgentTaskStatusLog;
import com.owners.gravitas.repository.AgentTaskStatusLogRepository;
import com.owners.gravitas.service.AgentTaskStatusLogService;

/**
 * The Class AgentTaskStatusLogServiceImpl.
 *
 * @author raviz
 */
@Service
public class AgentTaskStatusLogServiceImpl implements AgentTaskStatusLogService {

    /** The agent task status log repository. */
    @Autowired
    private AgentTaskStatusLogRepository agentTaskStatusLogRepository;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentTaskStatusLogService#save(com.owners.
     * gravitas.domain.entity.AgentTaskStatusLog)
     */
    @Override
    @Transactional
    public AgentTaskStatusLog save( final AgentTaskStatusLog agentTaskStatusLog ) {
        return agentTaskStatusLogRepository.save( agentTaskStatusLog );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.AgentTaskStatusLogService#
     * findByAgentTaskAndStatus(com.owners.gravitas.domain.entity.AgentTask,
     * java.util.Set)
     */
    @Override
    @Transactional( readOnly = true )
    public AgentTaskStatusLog findByAgentTaskAndStatus( final AgentTask agentTask, final Set< String > statuses ) {
        return agentTaskStatusLogRepository.findTop1ByAgentTaskAndStatusInOrderByCreatedDateDesc( agentTask, statuses );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentTaskStatusLogService#findTop2ByAgentTask
     * (com.owners.gravitas.domain.entity.AgentTask)
     */
    @Override
    @Transactional( readOnly = true )
    public AgentTaskStatusLog findTopByAgentTaskAndStatus( final AgentTask agentTask, String status ) {
        return agentTaskStatusLogRepository.findTop1ByAgentTaskAndStatusNotOrderByCreatedDateDesc( agentTask, status );
    }
}
