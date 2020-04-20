package com.owners.gravitas.business;

import com.owners.gravitas.domain.Action;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.domain.entity.OpportunityAction;
import com.owners.gravitas.enums.PushNotificationType;

/**
 * The Interface ActionFlowBusinessService.
 *
 * @author raviz
 */
public interface ActionFlowBusinessService {

    /**
     * Creates the action group.
     *
     * @param opportunityId
     *            the opportunity id
     * @param agentId
     *            the agent id
     * @param opportunity
     *            the opportunity
     * @param agentSearch
     *            the agent search
     */
    void createActionGroup( String opportunityId, String agentId, Opportunity opportunity, Search agentSearch );

    /**
     * Checks if is eligible for scripted call.
     *
     * @param opportunityType
     *            the opportunity type
     * @param agentEmail
     *            the agent email
     * @return true, if is eligible for scripted call
     */
    boolean isEligibleForScriptedCall( String opportunityType, String agentEmail );

    /**
     * Checks if is eligible for scripted call.
     *
     * @param agentEmail
     *            the agent email
     * @return true, if is eligible for scripted call
     */
    boolean isEligibleForScriptedCall( String agentEmail );

    /**
     * Send opportunity push notifications.
     *
     * @param agentId
     *            the agent id
     * @param type
     *            the type
     */
    void sendOpportunityPushNotifications( String agentId, PushNotificationType type );

    /**
     * Gets the action info.
     *
     * @param agentId
     *            the agent id
     * @param actionFlowId
     *            the action flow id
     * @param action
     *            the action
     * @return the action info
     */
    Action getActionInfo( String agentId, String actionFlowId, String action );

    /**
     * Gets the opportunity action.
     *
     * @param actionGroupId
     *            the action group id
     * @param actionId
     *            the action id
     * @return the opportunity action
     */
    OpportunityAction getOpportunityAction( String actionGroupId, String actionId );
}
