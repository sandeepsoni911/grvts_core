package com.owners.gravitas.service.impl;

import static com.owners.gravitas.constants.Constants.ACTION_OBJ;
import static com.owners.gravitas.constants.Constants.AGENT_ID;
import static com.owners.gravitas.constants.Constants.ENTITY_ID;
import static com.owners.gravitas.enums.ActionEntity.TASK;
import static com.owners.gravitas.enums.ActionType.CREATE;
import static com.owners.gravitas.enums.ActionType.UPDATE;
import static com.owners.gravitas.enums.ActionType.UPSERT;
import static com.owners.gravitas.enums.AgentTaskStatus.CONFIRMED;
import static com.owners.gravitas.enums.ErrorCode.AGENT_TASK_NOT_FOUND;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.annotation.Audit;
import com.owners.gravitas.constants.NotificationParameters;
import com.owners.gravitas.dao.AgentTaskDao;
import com.owners.gravitas.domain.AgentInfo;
import com.owners.gravitas.domain.Task;
import com.owners.gravitas.domain.entity.AgentTask;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.enums.TaskType;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.repository.AgentTaskRepository;
import com.owners.gravitas.service.AgentService;
import com.owners.gravitas.service.AgentTaskService;
import com.owners.gravitas.service.CoShoppingService;
import com.zuner.coshopping.model.common.Resource;
import com.zuner.coshopping.model.lead.LeadModel;
import com.zuner.coshopping.model.lead.LeadUpdate;
import com.zuner.coshopping.model.lead.LeadUpdateRequest;

/**
 * The Class AgentTaskServiceImpl.
 *
 * @author vishwanathm
 */
@Service
public class AgentTaskServiceImpl implements AgentTaskService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentTaskServiceImpl.class );

    /** The agent task dao. */
    @Autowired
    private AgentTaskDao agentTaskDao;

    /** The agent task repository. */
    @Autowired
    private AgentTaskRepository agentTaskRepository;
    
    @Autowired
    private AgentService agentService;
    
    /** The co shopping service. */
    @Autowired
    private CoShoppingService coShoppingService;
    
    /**
     * Save agent task.
     *
     * @param task
     *            the task
     * @param agentId
     *            the agent id
     * @return the post response
     */
    @Override
    @Audit( type = CREATE, entity = TASK, args = { ACTION_OBJ, AGENT_ID } )
    public PostResponse saveAgentTask( final Task task, final String agentId ) {
        return agentTaskDao.saveAgentTask( task, agentId );
    }

    /**
     * Save agent tasks.
     *
     * @param taskMap
     *            the task map
     * @param agentId
     *            the agent id
     * @return the post response
     */
    @Override
    @Audit( type = UPSERT, entity = TASK, args = { ACTION_OBJ, AGENT_ID } )
    public PostResponse saveAgentTasks( final Map< String, Task > taskMap, final String agentId ) {
        return agentTaskDao.saveAgentTasks( taskMap, agentId );
    }

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
    @Override
    @Audit( type = UPDATE, entity = TASK, args = { ENTITY_ID, AGENT_ID, ACTION_OBJ } )
    public Task patchAgentTask( final String taskId, final String agentId, final Map< String, Object > task ) {
        LOGGER.info( "Sending patch request for task: " + taskId + " and agebtId: " + agentId );
        return agentTaskDao.patchAgentTask( taskId, agentId, task );
    }

    /**
     * Gets the all agent opportunity tasks.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @return the all agent opportunity tasks
     */
    @Override
    public Map< String, Task > getTasksByOpportunityId( final String agentId, final String opportunityId ) {
        return agentTaskDao.getTasksByOpportunityId( agentId, opportunityId );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.AgentTaskService#getTaskById(java.lang.
     * String, java.lang.String)
     */
    @Override
    public Task getTaskById( final String agentId, final String taskId ) {
        final Task task = agentTaskDao.getTaskById( agentId, taskId );
        if (task == null) {
            throw new ApplicationException( "Agent task not found for agentId: " + agentId + " taskId :" + taskId,
                    AGENT_TASK_NOT_FOUND );
        }
        return task;
    }

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
    @Override
    public Map< String, Task > getTasksByType( final String agentId, final String opportunityId, final String type ) {
        return agentTaskDao.getTaskByType( agentId, opportunityId, type );
    }

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

    @Override
    public Map< String, Task > getOpenTasksByType( final String agentId, final String opportunityId,
            final String type ) {
        final Map< String, Task > openTasks = new HashMap<>();
        final Map< String, Task > allTasks = agentTaskDao.getTaskByType( agentId, opportunityId, type );
        for ( final Entry< String, Task > entry : allTasks.entrySet() ) {
            final Task task = entry.getValue();
            if (null != task && task.getCompletedDtm() == null) {
                openTasks.put( entry.getKey(), task );
            }
        }
        return openTasks;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.AgentTaskService#getByTaskId(java.lang.
     * String)
     */
    @Override
    public AgentTask getByTaskId( final String taskId ) {
        return agentTaskRepository.findByTaskId( taskId );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.AgentTaskService#findAll(java.util.Set)
     */
    @Override
    public List< AgentTask > findAll( final Set< String > taskIds ) {
        return agentTaskRepository.findAllTask( taskIds );
    }

    /**
     * Save agent task.
     *
     * @param agentTask
     *            the agent task
     * @return the agent task
     */
    @Override
    public AgentTask saveAgentTask( final AgentTask agentTask ) {
        return agentTaskRepository.save( agentTask );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentTaskService#getTasksByAgentEmail(java.
     * lang.String)
     */
    @Override
    public List< AgentTask > getTasksByAgentEmail( final String email, final Date frmDtm, final Date toDtm,
            final Set< String > status, final Set< String > types ) {
        return ( List< AgentTask > ) ( agentTaskRepository.findByAgentEmail( email, frmDtm, toDtm, status, types ) );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentTaskService#getAgentTasksByOpportunityId
     * (java.lang.String)
     */
    @Override
    public List< AgentTask > getAgentTasksByOpportunityId( final String crmId, final Date frmDtm, final Date toDtm,
            final Set< String > status, final Set< String > types ) {
        return ( List< AgentTask > ) agentTaskRepository.findAgentTasksByCrmId( crmId, frmDtm, toDtm, status, types );
    }

    /**
     * Gets the agent tasks by opportunity id no date.
     *
     * @param opportunityId
     *            the opportunity id
     * @param status
     *            the status
     * @param types
     *            the types
     * @return the agent tasks by opportunity id no date
     */
    @Override
    public List< AgentTask > getAgentTasksByFbId( final String opportunityId, final Set< String > status,
            final Set< String > types ) {
        return ( List< AgentTask > ) agentTaskRepository.findAgentTasksByFbId( opportunityId, status, types );
    }

    /**
     * Gets the agent tasks by dates.
     *
     * @param frmDtm
     *            the frm dtm
     * @param toDtm
     *            the to dtm
     * @param status
     *            the status
     * @param types
     *            the types
     * @return the agent tasks by dates
     */
    @Override
    public List< AgentTask > getAgentTasksByDates( final Date frmDtm, final Date toDtm, final Set< String > status,
            final Set< String > types ) {
        return ( List< AgentTask > ) ( agentTaskRepository.findByCreatedDate( frmDtm, toDtm, status, types ) );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.AgentTaskService#
     * findByAgentEmailAndStatusNotAndDueDate(java.lang.String,
     * java.lang.String, java.util.Date, java.util.Date)
     */
    @Override
    public List< AgentTask > findByAgentEmailAndStatusNotAndDueDate( final String agentEmail,
            final String excludedStstus, final Date startDate, final Date endDate ) {
        return agentTaskRepository.findByAgentEmailAndStatusNotAndDueDate( agentEmail, excludedStstus, startDate,
                endDate );
    }

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
    @Override
    public List< AgentTask > getAgentTasksByDates( final Date frmDtm, final Date toDtm, final Set< String > status ) {
        return ( List< AgentTask > ) ( agentTaskRepository.findByCreatedDate( frmDtm, toDtm, status ) );
    }

    /**
     * Gets the agent tasks by taskIds.
     * 
     * @param taskIds
     *            the taskIds
     * @return the agent tasks by taskIds
     */
    @Override
    public List< AgentTask > getAgentTaskByTaskIds( final Set< String > taskIds ) {
        return agentTaskRepository.findAllTask( taskIds );
    }

    @Override
    public boolean isEligibleForScheduleTourConfirmationEmail( final Task task ) {
        LOGGER.info( "Task received: {}", task );
        boolean isEligible = false;
        if (TaskType.SCHEDULE_TOUR.getType().equalsIgnoreCase( task.getTaskType() )
                && StringUtils.isNotBlank( task.getListingId() ) && StringUtils.isNotBlank( task.getCoShoppingId() )
                && !CONFIRMED.name().equalsIgnoreCase( task.getStatus() )) {
            isEligible = true;
        }
        LOGGER.info( "Is eligible for schedule tour confirmation email: {}", isEligible );
        return isEligible;
    }

    @Override
    public Map< String, String > prepareAgentAndBuyerInfoMap( final AgentInfo agentInfo, final Contact contact ) {
        Map< String, String > map = agentService.getAgentFirstAndLastName( agentInfo.getEmail() );
        map.put( NotificationParameters.BUYER_FIRST_NAME, StringUtils.capitalize( contact.getFirstName() ) );
        map.put( NotificationParameters.BUYER_EMAIL, contact.getEmail() );
        map.put( NotificationParameters.USER_ID, contact.getOwnersComId() );
        map.put( NotificationParameters.AGENT_EMAIL, agentInfo.getEmail() );
        map.put( NotificationParameters.AGENT_PHONE,
                com.owners.gravitas.util.StringUtils.formatPhoneNumberWithPeriods( agentInfo.getPhone() ) );
        return map;
    }

    @Override
    public LeadUpdateRequest prepareCoshoppingTaskWithStatus( Map< String, Task > taskMap, String status ) {
        final LeadUpdateRequest coShoppingLeadUpdateRequest = new LeadUpdateRequest();
        final List< LeadUpdate > coShoppingLeadUpdateList = new ArrayList< LeadUpdate >();

        if (!org.springframework.util.CollectionUtils.isEmpty( taskMap )) {
            for ( final Entry< String, Task > entry : taskMap.entrySet() ) {
                final Task task = entry.getValue();
                if (StringUtils.isNotBlank( task.getCoShoppingId() )) {
                    final LeadUpdate coShoppingLeadUpdate = new LeadUpdate();
                    coShoppingLeadUpdate.setId( task.getCoShoppingId() );
                    coShoppingLeadUpdate.setStatus( CONFIRMED.toString() );
                    coShoppingLeadUpdateList.add( coShoppingLeadUpdate );
                }
            }
        }
        coShoppingLeadUpdateRequest.setLeadUpdateRequest( coShoppingLeadUpdateList );
        return coShoppingLeadUpdateRequest;
    }
    
    @Override
    public Resource pushStatusToCoshopping( Task task, String status ) {
        Resource coshoppingTourResponse = null;
        if (StringUtils.isNotBlank( task.getCoShoppingId() )) {
            // Update status to co-shopping service
            LeadModel editLeadRequest = new LeadModel();
            editLeadRequest.setStatus( status );
            try {
                LOGGER.info( "Updating to co-shopping with the task status {}", status ); 
                coshoppingTourResponse = coShoppingService.editLeadDetails( editLeadRequest, task.getCoShoppingId() );
                if (null != coshoppingTourResponse) {
                    LOGGER.info( "Coshopping push done for the opportunity id {} with coshopping id {}", task.getOpportunityId(),
                            coshoppingTourResponse.getId() );
                } else {
                    LOGGER.info( "Coshopping push failed for the opportunity id {}", task.getOpportunityId() );
                }
            } catch ( Exception e ) {
                LOGGER.error( "Error while pushing status to Coshopping for the co-shopping id: {}", task.getCoShoppingId(), e );
            }
        }
        return coshoppingTourResponse;
    }
}
