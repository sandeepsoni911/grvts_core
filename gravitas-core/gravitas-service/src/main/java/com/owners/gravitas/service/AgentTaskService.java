package com.owners.gravitas.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.owners.gravitas.domain.AgentInfo;
import com.owners.gravitas.domain.Task;
import com.owners.gravitas.domain.entity.AgentTask;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.dto.response.PostResponse;
import com.zuner.coshopping.model.common.Resource;
import com.zuner.coshopping.model.lead.LeadUpdateRequest;

// TODO: Auto-generated Javadoc
/**
 * The Interface AgentTaskService.
 *
 * @author vishwanathm
 */
public interface AgentTaskService {

    /**
     * Save agent task.
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
     * Gets the opportunity id by task id.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @return the opportunity id by task id
     */
    Task getTaskById( String agentId, String taskId );

    /**
     * Gets the tasks by type.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param type
     *            the type
     * @return the task by type
     */
    Map< String, Task > getTasksByType( String agentId, String opportunityId, String type );

    /**
     * Gets the open tasks by type.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param type
     *            the type
     * @return the open task by type
     */
    Map< String, Task > getOpenTasksByType( String agentId, String opportunityId, String type );

    /**
     * Gets the by task id.
     *
     * @param taskId
     *            the task id
     * @return the by task id
     */
    AgentTask getByTaskId( String taskId );

    /**
     * Find by task id.
     *
     * @param taskId
     *            the task id
     * @return the agent task
     */
    List< AgentTask > findAll( Set< String > taskIds );

    /**
     * Save agent task.
     *
     * @param agentTask
     *            the agent task
     * @return the agent task
     */
    AgentTask saveAgentTask( AgentTask agentTask );

    /**
     * Gets the agent tasks by dates.
     *
     * @param frmDtm
     *            the frm dtm
     * @param toDtm
     *            the to dtm
     * @param status
     *            the status
     * @return the agent tasks by dates
     */
    List< AgentTask > getAgentTasksByDates( Date frmDtm, Date toDtm, Set< String > status, final Set< String > types );

    /**
     * Gets the agent tasks by dates.
     *
     * @param frmDtm
     *            the frm dtm
     * @param toDtm
     *            the to dtm
     * @param status
     *            the status
     * @return the agent tasks by dates
     */
    List< AgentTask > getAgentTasksByDates( Date frmDtm, Date toDtm, Set< String > status );

    /**
     * Gets the agent tasks by opportunity id.
     *
     * @param crmId
     *            the crm id
     * @param frmDtm
     *            the frm dtm
     * @param toDtm
     *            the to dtm
     * @param status
     *            the status
     * @return the agent tasks by opportunity id
     */
    List< AgentTask > getAgentTasksByOpportunityId( String crmId, Date frmDtm, Date toDtm, Set< String > status,
            final Set< String > types );

    /**
     * Gets the tasks by agent email.
     *
     * @param email
     *            the email
     * @param frmDtm
     *            the frm dtm
     * @param toDtm
     *            the to dtm
     * @param status
     *            the status
     * @return the tasks by agent email
     */
    List< AgentTask > getTasksByAgentEmail( String email, Date frmDtm, Date toDtm, Set< String > status,
            final Set< String > types );

    /**
     * Find by agent email and status not and due date.
     *
     * @param email
     *            the email
     * @param excludedStstus
     *            the excluded ststus
     * @param startDate
     *            the start date
     * @param endDate
     *            the end date
     * @return the list
     */
    List< AgentTask > findByAgentEmailAndStatusNotAndDueDate( final String email, String excludedStstus, Date startDate,
            Date endDate );

    /**
     * Gets the agent tasks by opportunity id without date.
     *
     * @param crmId
     *            the crm id
     * @param status
     *            the status
     * @param types
     *            the types
     * @return the agent tasks by opportunity id without date
     */
    List< AgentTask > getAgentTasksByFbId( String opportunityId, Set< String > status, Set< String > types );

    /**
     * Gets the agent task by task ids.
     *
     * @param taskIds
     *            the task ids
     * @return the agent task by task ids
     */
    List< AgentTask > getAgentTaskByTaskIds( final Set< String > taskIds );
    
    /**
     * Checks eligibility for sending tour confirmation email
     * 
     * @param task
     * @return
     */
    boolean isEligibleForScheduleTourConfirmationEmail( Task task );
    
    /**
     * preapre agent and buyer info map
     * 
     * @param agentInfo
     * @param contact
     * @return
     */
    Map<String, String> prepareAgentAndBuyerInfoMap(AgentInfo agentInfo, Contact contact);
    
    /**
     * prepare co-shopping task with status
     * 
     * @param nonPrimaryTaskMap
     * @param status
     * @return
     */
    LeadUpdateRequest prepareCoshoppingTaskWithStatus( final Map< String, Task > taskMap, String status );
    
    /**
     * push task status to coshopping
     * 
     * @param task
     * @param status
     * @return
     */
    Resource pushStatusToCoshopping( Task task, String status );
}
