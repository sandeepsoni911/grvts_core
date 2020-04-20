package com.owners.gravitas.business;

import java.util.Map;
import java.util.Set;

import com.owners.gravitas.domain.Request;
import com.owners.gravitas.dto.ApiUser;
import com.owners.gravitas.dto.request.AgentTaskRequest;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.dto.request.TaskUpdateRequest;
import com.owners.gravitas.dto.response.AgentResponse;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.CheckScheduleMeetingValidationResponse;
import com.owners.gravitas.dto.response.CheckinDetailResponse;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.enums.TaskType;

// TODO: Auto-generated Javadoc
/**
 * The Interface AgentTaskBusinessService.
 */
public interface AgentTaskBusinessService {
    /**
     * Creates the agent task.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param taskRequest
     *            the task request
     * @return the agent response
     */
    AgentResponse createAgentTask( String agentId, String opportunityId, AgentTaskRequest taskRequest );

    /**
     * Respond to buyer request.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param acknowledgementRequest
     *            the acknowledgement request
     * @return the agent response
     */
    AgentResponse handleAgentTask( String agentId, String taskId, TaskUpdateRequest acknowledgementRequest );

    /**
     * Complete agent's task.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param acknowledgementRequest
     *            the acknowledgement request
     * @return the agent response
     */
    AgentResponse completeAgentTask( String agentId, String taskId, TaskUpdateRequest acknowledgementRequest );

    /**
     * Save taskif not exists.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param type
     *            the type
     * @param title
     *            the title
     * @param name
     *            the name
     * @return the post response
     */
    PostResponse saveTaskifNotExists( String agentId, String opportunityId, TaskType type, String title, String name );

    /**
     * Close task by type.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param type
     *            the type
     */
    void closeTaskByType( String agentId, String opportunityId, TaskType type );

    /**
     * Adds the to google calendar.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param agentEmail
     *            the agent email
     * @return the base response
     */
    AgentResponse addToGoogleCalendar( String agentId, String taskId, String agentEmail );

    /**
     * Delete agent task.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param apiUser
     *            the api user
     * @return the agent response
     */
    AgentResponse deleteAgentTask( String agentId, String taskId, ApiUser apiUser );

    /**
     * Gets the checkin details.
     *
     * @param search
     *            the search
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @return the checkin details
     */
    CheckinDetailResponse getCheckinDetails( String search, String fromDate, String toDate );

    /**
     * Gets the questionnaire.
     *
     * @param type
     *            the type
     * @return the questionnaire
     */
    Set< String > getQuestionnaire( String type );

    /**
     * Unassign tasks.
     *
     * @param opportunityId
     *            the opportunity id
     * @param agentId
     *            the agent id
     */
    void unassignTasks( String opportunityId, String agentId );

    /**
     * Copy tasks and requests.
     *
     * @param oldAgentId
     *            the old agent id
     * @param newAgentId
     *            the new agent id
     * @param oldOpportunityId
     *            the old opportunity id
     * @param newOpportunityId
     *            the new opportunity id
     * @param newContacts
     *            the new contacts
     */
    void copyTasksAndRequests( String oldAgentId, String newAgentId, String oldOpportunityId, String newOpportunityId,
            Map< String, String > newContacts );

    /**
     * Reassign tasks to same agent.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     */
    void reassignTasksToSameAgent( String agentId, String opportunityId );

    /**
     * Creates the buyer task.
     *
     * @param request
     *            the request
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param requestId
     *            the request id
     * @return the post response
     */
    PostResponse createBuyerTask( LeadRequest request, String agentId, String opportunityId, String requestId,
            Request buyerRequest );

    /**
     * Delete tasks.
     *
     * @param opportunityId
     *            the opportunity id
     * @param agentId
     *            the agent id
     */
    void deleteTasks( String opportunityId, String agentId );

    /**
     * Answer questionnaire.
     *
     * @param request
     *            the request
     * @return the base response
     */
    BaseResponse answerQuestionnaire( Map< String, Object > request );

    /**
     * Update scheduled tasks by opportunity id.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     */
    void updateScheduledTasksByOpportunityId( String agentId, String opportunityId );

    /**
     * Check if the agent meeting exist for the given crm Id
     *
     * @param agentEmail
     *            the agent email
     * @param crmId
     *            the crm Id
     * @return schedule meeting validation response
     */
    CheckScheduleMeetingValidationResponse checkIfAgentMeetingEventExists( final String agentEmail,
            final String crmId );

}
