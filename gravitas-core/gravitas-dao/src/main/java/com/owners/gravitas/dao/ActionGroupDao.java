package com.owners.gravitas.dao;

import java.util.List;
import java.util.Map;

import com.owners.gravitas.domain.Action;
import com.owners.gravitas.domain.entity.ActionGroup;
import com.owners.gravitas.dto.response.PostResponse;

// TODO: Auto-generated Javadoc
/**
 * The Interface AgentActionGroupDao.
 *
 * @author shivamm
 */
public interface ActionGroupDao {

    /**
     * Gets the action flow.
     *
     * @return the action group
     */
    public ActionGroup getActionGroup();

    /**
     * Creates the action group.
     *
     * @param agentId
     *            the agent id
     * @param actionGroup
     *            the action group
     * @return the post response
     */
    PostResponse createActionGroup( final String agentId, final com.owners.gravitas.domain.ActionGroup actionGroup );

    /**
     * Gets the action info.
     *
     * @param agentId
     *            the agent id
     * @param actionGroupId
     *            the action group id
     * @param actionId
     *            the action id
     * @return the action info
     */
    public Action getAction( final String agentId, final String actionGroupId, final String actionId );

    /**
     * Patch agent action info.
     *
     * @param agentId
     *            the agent id
     * @param actionGroupId
     *            the action group id
     * @param actionId
     *            the action id
     * @param action
     *            the action
     */
    public void patchAction( final String agentId, final String actionGroupId, final String actionId,
            final Map< String, Object > action );

    /**
     * Find all.
     *
     * @return the list
     */
    List< ActionGroup > findAll();

    /**
     * Gets the action group.
     *
     * @param agentId
     *            the agent id
     * @param actionGroupId
     *            the action group id
     * @return the action group
     */
    com.owners.gravitas.domain.ActionGroup getActionGroup( String agentId, String actionGroupId );

    /**
     * Gets the start time.
     *
     * @param agentId
     *            the agent id
     * @param actionGroupId
     *            the action group id
     * @return the start time
     */
    String getStartTime( String agentId, String actionGroupId );

    /**
     * Patch action flow.
     *
     * @param agentId
     *            the agent id
     * @param actionGroupId
     *            the action group id
     * @param data
     *            the data
     */
    void patchActionGroup( String agentId, String actionGroupId, Map< String, Object > data );
}
