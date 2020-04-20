/*
 *
 */
package com.owners.gravitas.dao;

import java.util.List;
import java.util.Map;

import com.owners.gravitas.domain.Task;
import com.owners.gravitas.dto.response.PostResponse;

/**
 * The Interface AgentTaskDao.
 *
 * @author vishwanathm
 */
public interface AgentTaskDao {

    /**
     * Save agent tasks for provided agent id.
     *
     * @param task
     *            the task
     * @param agentId
     *            the agent id
     * @return the post response
     */
    PostResponse saveAgentTask( Task task, String agentId );

    /**
     * Save agent tasks.
     *
     * @param taskMap
     *            the task map
     * @param agentId
     *            the agent id
     * @return the post response
     */
    PostResponse saveAgentTasks( final Map< String, Task > taskMap, final String agentId );

    /**
     * Patch agent task.
     *
     * @param taskId
     *            the task id
     * @param agentId
     *            the agent id
     * @param task
     *            the task
     * @return the task
     */
    Task patchAgentTask( String taskId, String agentId, Map< String, Object > task );

    /**
     * Gets the all agent opportunity tasks.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @return the all agent opportunity tasks
     */
    Map< String, Task > getTasksByOpportunityId( String agentId, String opportunityId );

    /**
     * Gets the task by id.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @return the task by id
     */
    Task getTaskById( String agentId, String taskId );

    /**
     * Gets the task by type.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param type
     *            the type
     * @return the task by type
     */
    Map< String, Task > getTaskByType( String agentId, String opportunityId, String type );

    /**
     * Gets the tasks by opportunity id.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @return the tasks by opportunity id
     */
    int getOpenContactOpportunityTypeTasks( final String agentId, final String opportunityId );
    
}
