package com.owners.gravitas.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.owners.gravitas.domain.entity.AgentTask;

/**
 * The Interface AgentTaskRepository.
 *
 * @author raviz
 */
public interface AgentTaskRepository extends JpaRepository< AgentTask, String > {

    /** The get agent task by email. */
    String GET_AGENT_TASK_BY_EMAIL_AND_STATUS = "select t from GR_AGENT_TASK t inner join t.opportunity o where o.assignedAgentId=:agentEmail and o.deleted=false and t.type in :types and t.status in :status and cast(t.createdDate as date) BETWEEN :startDate and :endDate order by t.createdDate";

    /** The get agent task by crm opportunity id. */
    String GET_AGENT_TASK_BY_CRM_OPPORTUNITY_ID_AND_STATUS = "select t from GR_AGENT_TASK t inner join t.opportunity o inner join o.contact c where o.deleted=false and t.type in :types and t.status in :status and c.crmId=:crmId and cast(t.createdDate as date) BETWEEN :startDate and :endDate order by t.createdDate";

    /** The get agent task by crm opportunity id and status without dates. */
    String GET_AGENT_TASK_BY_FB_OPPORTUNITY_ID_AND_STATUS = "select t from GR_AGENT_TASK t inner join t.opportunity o where o.deleted=false and t.type in :types and t.status in :status and o.opportunityId=:opportunityId";

    /** The get agent task by dates. */
    String GET_AGENT_TASK_BY_DATES_AND_STATUS = "select t from GR_AGENT_TASK t inner join t.opportunity o where o.deleted=false and t.type in :types and t.status in :status and cast(t.createdDate as date) BETWEEN :startDate and :endDate order by t.createdDate";

    final static String FIND_ALL_TASK_BY_IDS = "select * from gr_agent_task where TASK_ID in :taskIds";

    String GET_AGENT_TASK_BY_EMAIL_AND_DUE_DATE = "select t from GR_AGENT_TASK t inner join t.opportunity o where o.assignedAgentId=:agentEmail and o.deleted=false and t.status NOT LIKE %:excludedStstus% and convert(t.scheduledDtm, datetime) BETWEEN :startDate and :endDate order by t.scheduledDtm";

    /** The get agent task by dates where location and scheduled dtm. */
    String GET_AGENT_TASK_BY_DATES_WHERE_LOCATION_NOT_NULL_AND_SCHEDULED_DTM_NOT_NULL = "select t from GR_AGENT_TASK t inner join t.opportunity o where o.deleted=false and t.status in :status and t.location is not null and t.scheduledDtm is not null and cast(t.createdDate as date) BETWEEN :startDate and :endDate order by t.createdDate";

    /**
     * Find by task id.
     *
     * @param taskId
     *            the task id
     * @return the agent task
     */
    AgentTask findByTaskId( String taskId );

    /**
     * Find by task id.
     *
     * @param taskId
     *            the task id
     * @return the agent task
     */
    @Query( value = FIND_ALL_TASK_BY_IDS, nativeQuery = true )
    List< AgentTask > findAllTask( @Param( value = "taskIds" ) Set< String > taskIds );

    /**
     * Find by created by.
     *
     * @param agentEmail
     *            the agent email
     * @param startDate
     *            the start date
     * @param endDate
     *            the end date
     * @return the sets the
     */
    @Query( value = GET_AGENT_TASK_BY_EMAIL_AND_STATUS )
    Collection< AgentTask > findByAgentEmail( @Param( value = "agentEmail" ) String agentEmail,
            @Param( value = "startDate" ) Date startDate, @Param( value = "endDate" ) Date endDate,
            @Param( value = "status" ) Set< String > status, @Param( value = "types" ) Set< String > types );

    /**
     * Find by agent email and due date.
     *
     * @param agentEmail
     *            the agent email
     * @param excludedStstus
     *            the excluded ststus
     * @param startDate
     *            the start date
     * @param endDate
     *            the end date
     * @return the collection
     */
    @Query( value = GET_AGENT_TASK_BY_EMAIL_AND_DUE_DATE )
    List< AgentTask > findByAgentEmailAndStatusNotAndDueDate( @Param( value = "agentEmail" ) String agentEmail,
            @Param( "excludedStstus" ) String excludedStstus, @Param( value = "startDate" ) Date startDate,
            @Param( value = "endDate" ) Date endDate );

    /**
     * Find agent tasks by crm id.
     *
     * @param crmId
     *            the crm id
     * @param startDate
     *            the start date
     * @param endDate
     *            the end date
     * @return the collection
     */
    @Query( value = GET_AGENT_TASK_BY_CRM_OPPORTUNITY_ID_AND_STATUS )
    Collection< AgentTask > findAgentTasksByCrmId( @Param( value = "crmId" ) String crmId,
            @Param( value = "startDate" ) Date startDate, @Param( value = "endDate" ) Date endDate,
            @Param( value = "status" ) Set< String > status, @Param( value = "types" ) Set< String > types );

    /**
     * Find agent tasks by crm id no date.
     *
     * @param crmId
     *            the crm id
     * @param status
     *            the status
     * @param types
     *            the types
     * @return the collection
     */
    @Query( value = GET_AGENT_TASK_BY_FB_OPPORTUNITY_ID_AND_STATUS )
    Collection< AgentTask > findAgentTasksByFbId( @Param( value = "opportunityId" ) String opportunityId,
            @Param( value = "status" ) Set< String > status, @Param( value = "types" ) Set< String > types );

    /**
     * Find by created date.
     *
     * @param startDate
     *            the start date
     * @param endDate
     *            the end date
     * @return the collection
     */
    @Query( value = GET_AGENT_TASK_BY_DATES_AND_STATUS )
    Collection< AgentTask > findByCreatedDate( @Param( value = "startDate" ) Date startDate,
            @Param( value = "endDate" ) Date endDate, @Param( value = "status" ) Set< String > status,
            @Param( value = "types" ) Set< String > types );

    /**
     * Find by scheduled dtm and location not null.
     *
     * @param startDate
     *            the start date
     * @param endDate
     *            the end date
     * @return the collection
     */
    @Query( value = GET_AGENT_TASK_BY_DATES_WHERE_LOCATION_NOT_NULL_AND_SCHEDULED_DTM_NOT_NULL )
    Collection< AgentTask > findByCreatedDate( @Param( value = "startDate" ) Date startDate,
            @Param( value = "endDate" ) Date endDate, @Param( value = "status" ) Set< String > status );
    
    
    @Query(value = "SELECT opportunity_id FROM gr_agent_task WHERE id = :taskId", nativeQuery = true)
    String getOpportunityIdByTaskId(@Param(value = "taskId") final String taskId);

}
