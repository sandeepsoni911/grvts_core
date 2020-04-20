package com.owners.gravitas.business;

import java.util.Map;

import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.dto.response.AgentResponse;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.enums.PushNotificationType;

/**
 * The Interface AgentOpportunityBusinessService.
 *
 * @author vishwanathm
 */
public interface AgentOpportunityBusinessService {

    /**
     * Handle opportunity change.
     *
     * @param opportunitySource
     *            the opportunity source
     */
    void handleOpportunityChange( OpportunitySource opportunitySource );

    /**
     * Update opportunity.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param request
     *            the request
     * @return the agent response
     */
    AgentResponse patchOpportunity( String agentId, String opportunityId, Map< String, Object > request );

    /**
     * Creates the agent opportunity.
     *
     * @param agentEmail
     *            the email
     * @param agentLeadRequest
     *            the agent lead request
     * @return the base response
     */
    BaseResponse createAgentOpportunity( final String agentEmail, final LeadRequest agentLeadRequest );

    /**
     * Gets the agent commission.
     *
     * @param agentId
     *            the id
     * @param opportunityId
     *            the opportunity id
     * @return the base response
     */
    BaseResponse getOpportunityCommission( final String agentId, final String opportunityId );
    
        
    /**
     * Checks if Email is permitted for the given email id
     * @param agentId
     * @param emailId
     * @return the base response
     */
    BaseResponse isCrmIdPermitted( final String agentId, final String crmId );
    
    
    /**
     * Send opportunity push notifications.
     *
     * @param opportunityId
     *            the opportunityId id
     * @param agentSearch
     *            the agent search
     */
    void sendOpportunityPushNotifications( String opportunityId, Search agentSearch );

    /**
     * Send confirmed task push notifications.
     *
     * @param taskId
     *            the task id
     * @param agentId
     *            the agent id
     * @param PushNotificationType
     *            the push notification type
     */
    void sendPendingOpportunityPushNotifications( String taskId, String agentId, PushNotificationType eventType );

}
