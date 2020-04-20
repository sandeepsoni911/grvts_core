package com.owners.gravitas.business;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.owners.gravitas.amqp.AgentSource;
import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.domain.OpportunityDataNode;
import com.owners.gravitas.dto.SlackError;
import com.owners.gravitas.dto.crm.response.CRMAgentResponse;
import com.owners.gravitas.dto.request.ActionRequest;
import com.owners.gravitas.dto.request.AgentDeviceRequest;
import com.owners.gravitas.dto.request.AgentNoteRequest;
import com.owners.gravitas.dto.request.AgentOnboardRequest;
import com.owners.gravitas.dto.request.AgentSuggestedPropertyRequest;
import com.owners.gravitas.dto.request.EmailRequest;
import com.owners.gravitas.dto.request.LastViewedRequest;
import com.owners.gravitas.dto.request.OpportunityDataRequest;
import com.owners.gravitas.dto.response.ActionLogResponse;
import com.owners.gravitas.dto.response.AgentEmailResponse;
import com.owners.gravitas.dto.response.AgentResponse;
import com.owners.gravitas.dto.response.BaseResponse;

/**
 * The Interface AgentBusinessService.
 *
 * @author amits
 */
public interface AgentBusinessService {

    /**
     * Register agent .
     *
     * @param email
     *            the email
     * @param string
     *            the string
     * @return the agent response
     */
    AgentResponse register( String email, String string );

    /**
     * Handle agent notes insert.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param request
     *            the request
     * @return the opportunity response
     */
    AgentResponse addAgentNote( String agentId, String opportunityId, AgentNoteRequest request );

    /**
     * Adds the device.
     *
     * @param agentId
     *            the agent id
     * @param request
     *            the request
     * @return the agent response
     */
    AgentResponse addDevice( String agentId, AgentDeviceRequest request );

    /**
     * Removes the device.
     *
     * @param agentId
     *            the agent id
     * @param deviceId
     *            the device id
     * @return the agent response
     */
    AgentResponse removeDevice( String agentId, String deviceId );

    /**
     * Update agent note.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param request
     *            the request
     * @return the agent response
     */
    AgentResponse updateAgentNote( String agentId, String opportunityId, AgentNoteRequest request );

    /**
     * Delete agent note.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @return the agent response
     */
    AgentResponse deleteAgentNote( String agentId, String opportunityId );

    /**
     * Update last viewed.
     *
     * @param agentId
     *            the agent id
     * @param id
     *            the id
     * @param viewedRequest
     *            the viewed request
     * @return the agent response
     */
    AgentResponse updateLastViewed( String agentId, String id, LastViewedRequest viewedRequest );

    /**
     * Creates the agent action.
     *
     * @param email
     *            the email
     * @param actionRequest
     *            the action request
     * @return the agent response
     */
    AgentResponse createAgentAction( String email, ActionRequest actionRequest );

    /**
     * Gets the CTA audit logs.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param ctaType
     *            the cta type
     * @return the CTA audit logs
     */
    ActionLogResponse getCTAAuditLogs( String agentId, String opportunityId, String ctaType );

    /**
     * Post error to slack.
     *
     * @param error
     *            the error
     */
    void postErrorToSlack( SlackError error );

    /**
     * Send pts email notification.
     *
     * @param agentSource
     *            the agent source
     */
    void sendPTSEmailNotifications( AgentSource agentSource );

    /**
     * Onboards agent.
     *
     * @param request
     *            the request
     * @return the base response
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    BaseResponse onboard( AgentOnboardRequest request ) throws IOException;

    /**
     * Update agent.
     *
     * @param agentSource
     *            the agent source
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    void updateAgent( final AgentSource agentSource ) throws IOException;

    /**
     * Update agent.
     *
     * @param email
     *            the email
     * @param request
     *            the request
     * @return the base response
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    BaseResponse updateAgent( String email, AgentOnboardRequest request ) throws IOException;

    /**
     * Update agent.
     *
     * @param agentId
     *            the agent id
     * @param request
     *            the request
     * @return the base response
     */
    BaseResponse updateAgent( String agentId, Map< String, Object > request );

    /**
     * Send agent emails.
     *
     * @param agentEmail
     *            the agent email
     * @param emailRequests
     *            the email requests
     * @return the agent email response
     */
    AgentEmailResponse sendAgentEmails( String agentEmail, List< EmailRequest > emailRequests );

    /**
     * Send agent email.
     *
     * @param email
     *            the email
     * @param emailRequest
     *            the email requests
     * @return the agent response
     */
    AgentResponse sendAgentEmail( String email, EmailRequest emailRequest );

    /**
     * Update agent score.
     */
    void syncAgentScore();

    /**
     * Send pending sale PTS email notifications.
     *
     * @param pts
     *            the pts
     * @param agentSource
     *            the agent source
     * @param opportunitySource
     *            the opportunity source
     */
    void sendPendingSalePTSEmailNotification( boolean pts, AgentSource agentSource,
            OpportunitySource opportunitySource );

    /**
     * Send sold stage PTS email notifications.
     *
     * @param pts
     *            the pts
     * @param agentSource
     *            the agent source
     * @param opportunitySource
     *            the opportunity source
     */
    void sendSoldStagePTSEmailNotifications( boolean pts, final AgentSource agentSource,
            final OpportunitySource opportunitySource );

    /**
     * Sync agent details.
     *
     * @param agentSource
     *            the agent source
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    void syncAgentDetails( AgentSource agentSource ) throws IOException;

    /**
     * Gets the CRM agent.
     *
     * @param agentId
     *            the agent id
     * @return the CRM agent
     */
    CRMAgentResponse getCRMAgentById( String agentId );

    /**
     * Gets the CRM agent by email.
     *
     * @param agentEmail
     *            the agent email
     * @return the CRM agent by email
     */
    AgentSource getCRMAgentByEmail( String agentEmail );

    /**
     * Update action.
     *
     * @param agentId
     *            the agent id
     * @param actionGroupId
     *            the action group id
     * @param actionId
     *            the action id
     * @param action
     *            the action
     * @return the base response
     */
    BaseResponse updateAction( String agentId, String actionGroupId, String actionId, Map< String, Object > action );

    /**
     * Update first contact time.
     *
     * @param agentId
     *            the agent id
     * @param actionRequest
     *            the action request
     */
    void updateFirstContactTime( String agentId, ActionRequest actionRequest );

    /**
     * Process agent off duty.
     *
     * @param executionId
     *            the execution id
     */
    void processAgentOffDuty( String executionId );

    /**
     * Process agent on duty.
     *
     * @param executionId
     *            the execution id
     */
    void processAgentOnDuty( String executionId );

    /**
     * Creates the agent suggested property.
     *
     * @param agentId
     *            the agent id
     * @param agentSuggestedPropertyRequest
     *            the Agent Suggested Property request
     * @return the agent response
     */
    AgentResponse createAgentSuggestedProperty( String agentId,
            AgentSuggestedPropertyRequest agentSuggestedPropertyRequest );

    /**
     * Save agent sign generically
     * 
     * @param agentId
     * @param signature
     * @return
     */
    BaseResponse saveAgentSignature( String agentId, Object signature );

    /**
     * Save agent preferences data.
     *
     * @param agentId
     *            the agent id
     * @param path
     *            the path
     * @param agentSpecific
     *            the agent specific
     * @param preferenceData
     *            the preference data
     * @return the base response
     */
    BaseResponse saveAgentPreferencesData( String agentId, String path, Boolean agentSpecific,
            Map< String, Object > preferenceData );

    /**
     * Add reason Code to Opps
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param request
     *            the request
     * @return the opportunity response
     */
    AgentResponse addDataNode( String agentId, String opportunityId, OpportunityDataRequest opportunityDataRequest );

    /**
     * Get OpportunityDataNode
     *
     * @param cclRejectionReasonCode
     *            the agent note
     * @param agentId
     *            the agent id
     * @return
     */
    public OpportunityDataNode getDataNode( String agentId, String opportunityId, String dataNodeKey );

}
