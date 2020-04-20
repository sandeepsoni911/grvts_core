package com.owners.gravitas.service;

import java.util.Set;

import com.owners.gravitas.domain.entity.AgentTask;
import com.owners.gravitas.domain.entity.AgentTaskStatusLog;

/**
 * The Interface AgentTaskStatusLogService.
 *
 * @author raviz
 */
public interface AgentTaskStatusLogService {

    /**
     * Save.
     *
     * @param agentTaskStatusLog
     *            the agent task status log
     * @return the agent task status log
     */
    public AgentTaskStatusLog save( AgentTaskStatusLog agentTaskStatusLog );

    /**
     * Find by agent task and status.
     *
     * @param agentTask
     *            the agent task
     * @param statuses
     *            the statuses
     * @return the agent task status log
     */

    public AgentTaskStatusLog findByAgentTaskAndStatus( final AgentTask agentTask, final Set< String > statuses );

    /**
     * Find top by agent task and status.
     *
     * @param agentTask
     *            the agent task
     * @param status
     *            the status
     * @return the agent task status log
     */
    AgentTaskStatusLog findTopByAgentTaskAndStatus( AgentTask agentTask, String status );

}
