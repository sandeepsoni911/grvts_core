package com.owners.gravitas.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owners.gravitas.domain.entity.AgentTask;
import com.owners.gravitas.domain.entity.AgentTaskStatusLog;

// TODO: Auto-generated Javadoc
/**
 * The Interface AgentTaskStatusLogRepository.
 *
 * @author raviz
 */
public interface AgentTaskStatusLogRepository extends JpaRepository< AgentTaskStatusLog, String > {

    /**
     * Find top1 by agent task and status in order by created date desc.
     *
     * @param agentTask
     *            the agent task
     * @param statuses
     *            the statuses
     * @return the agent task status log
     */
    AgentTaskStatusLog findTop1ByAgentTaskAndStatusInOrderByCreatedDateDesc( AgentTask agentTask,
            Collection< String > statuses );

    /**
     * Find by agent task.
     *
     * @param agentTask
     *            the agent task
     * @return the agent task status log
     */
    AgentTaskStatusLog findByAgentTask( AgentTask agentTask );

    /**
     * Find top2 by agent task order by created date desc.
     *
     * @param agentTask
     *            the agent task
     * @return the list
     */
    List< AgentTaskStatusLog > findTop2ByAgentTaskOrderByCreatedDateDesc( AgentTask agentTask );

    /**
     * Find top1 by agent task and status log not equal order by created date
     * desc.
     *
     * @param agentTask
     *            the agent task
     * @param status
     *            the status
     * @return the agent task status log
     */
    AgentTaskStatusLog findTop1ByAgentTaskAndStatusNotOrderByCreatedDateDesc( AgentTask agentTask, String status );
}
