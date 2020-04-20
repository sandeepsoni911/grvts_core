/*
 *
 */
package com.owners.gravitas.service;

import java.util.List;
import java.util.Map;

import com.owners.gravitas.domain.Action;
import com.owners.gravitas.domain.ActionGroup;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.domain.entity.OpportunityAction;
import com.owners.gravitas.dto.response.PostResponse;

// TODO: Auto-generated Javadoc
/**
 * The Class ActionGroupService.
 *
 * @author shivamm
 */
public interface ActionGroupService {

    /**
     * Creates the action group.
     *
     * @param agentId
     *            the agent id
     * @param actionGroup
     *            the action group
     * @param opportunityId
     *            the opportunity id
     * @param opportunity
     *            the opportunity
     * @return the post response
     */
    PostResponse createActionGroup( final String agentId, final ActionGroup actionGroup, final String opportunityId,
            final Opportunity opportunity );

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
    Action getActionInfo( final String agentId, final String actionGroupId, final String actionId );

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
    void patchAgentActionInfo( final String agentId, final String actionGroupId, final String actionId,
            final Map< String, Object > action );

    /**
     * Gets the action group.
     *
     * @param agentId
     *            the agent id
     * @param actionGroupId
     *            the action group id
     * @return the action group
     */
    ActionGroup getActionGroup( String agentId, String actionGroupId );

    /**
     * Patch action group.
     *
     * @param agentId
     *            the agent id
     * @param actionGroupId
     *            the action group id
     * @param agentEmail
     *            the agent email
     * @param actionFlowData
     *            the action flow data
     */
    void patchActionGroup( final String agentId, final String actionGroupId, final String agentEmail,
            final Map< String, Object > actionFlowData );

    /**
     * Gets the opportunity action.
     *
     * @param actionGroupId
     *            the action group id
     * @param actionId
     *            the action id
     * @return the opportunity action
     */
    OpportunityAction getOpportunityAction( final String actionGroupId, final String actionId );

    /**
     * Gets the start time.
     *
     * @param actionGroupId
     *            the action group id
     * @return the start time
     */
    List< OpportunityAction > getStartTime( String actionGroupId );

    /**
     * Save action group.
     *
     * @param actionFlow
     *            the action flow
     * @return the list
     */
    List< OpportunityAction > saveActionGroup( List< OpportunityAction > actionFlow );

    /**
     * Save action.
     *
     * @param action
     *            the action
     * @return the opportunity action
     */
    OpportunityAction saveAction( OpportunityAction action );

    /**
     * Save action group.
     *
     * @param actionFlowId
     *            the action id
     * @param actionGroup
     *            the action group
     */
    List< OpportunityAction > saveActionGroup( String actionFlowId, ActionGroup actionGroup );
}
