package com.owners.gravitas.business.impl;

import static com.owners.gravitas.enums.PushNotificationType.SCHEDULED_TASK_APPOINTMENT_REMINDER;
import static com.owners.gravitas.enums.PushNotificationType.NOTIFICATION_OCL_REMINDER;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.business.AgentNotificationBusinessService;
import com.owners.gravitas.business.AgentReminderBusinessService;
import com.owners.gravitas.dao.AgentOpportunityDao;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.domain.Reminder;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.domain.Task;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.request.NotificationRequest;
import com.owners.gravitas.dto.request.ReminderRequest;
import com.owners.gravitas.dto.response.AgentResponse;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.enums.ErrorCode;
import com.owners.gravitas.enums.PushNotificationType;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.service.AgentContactService;
import com.owners.gravitas.service.AgentReminderService;
import com.owners.gravitas.service.AgentService;
import com.owners.gravitas.service.AgentTaskService;
import com.owners.gravitas.service.SearchService;

/**
 * The Class AgentReminderBusinessServiceImpl.
 *
 * @author harshads
 */
@Service
public class AgentReminderBusinessServiceImpl implements AgentReminderBusinessService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentReminderBusinessServiceImpl.class );

    /** The agent reminder service. */
    @Autowired
    private AgentReminderService agentReminderService;

    /** The agent notification business service. */
    @Autowired
    private AgentNotificationBusinessService agentNotificationBusinessService;

    /** The agent task service. */
    @Autowired
    private AgentTaskService agentTaskService;
    
    /** The opportunity dao. */
    @Autowired
    private AgentOpportunityDao opportunityDao;

    /** The search service. */
    @Autowired
    private SearchService searchService;

    /** The agent contact service. */
    @Autowired
    private AgentContactService agentContactService;

    /** The agent service. */
    @Autowired
    private AgentService agentService;

    /** The agent reminder task. */
    @Autowired
    private AgentReminderTask agentReminderTask;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.AgentBusinessService#sendNewOppReminder()
     */
    @Override
    public void sendNewOppReminder() {
        agentService.getAllAgentIds().forEach( agentId -> agentReminderTask.sendNewOppNotification( agentId ) );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.AgentBusinessService#sendClaimedOppReminder(
     * )
     */
    @Override
    public void sendClaimedOppReminder() {
        agentService.getAllAgentIds().forEach( agentId -> agentReminderTask.sendClaimedOppNotification( agentId ) );
    }

    /**
     * Sets the reminder.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param request
     *            the request
     * @return the agent response
     */
    @Override
    public AgentResponse setReminder( final String agentId, final String taskId, final ReminderRequest request ) {
        final Task task = agentTaskService.getTaskById( agentId, taskId );
        return createReminder( agentId, taskId, request, task );
    }
    

    /**
     * Creates the reminder.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param request
     *            the request
     * @param task
     *            the task
     * @return the agent response
     */
    @Override
    public AgentResponse createReminder( final String agentId, final String taskId, final ReminderRequest request,
            final Task task ) {
        final List< String > notificationIds = pushReminderNotification( agentId, taskId, task.getOpportunityId(),
                request.getTriggerDtm().getTime(), task, SCHEDULED_TASK_APPOINTMENT_REMINDER );
        return saveReminder( agentId, taskId, request.getTriggerDtm().getTime(), notificationIds );
    }
    
    
    /**
     * Sets the reminder.
     *
     * @param agentId
     *            the agent id
     * @param request
     *            the request
     * @return the agent response
     */
    @Override
    public AgentResponse setReminder( final String agentId, final ReminderRequest request ) {
    	final Opportunity opportunity = opportunityDao.getOpportunityById( agentId, request.getOpportunityId() );
    	return createReminder( agentId, request.getOpportunityId(), request, opportunity );
    }
    
   
    
    /**
     * Creates the reminder.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the opportunityId id
     * @param request
     *            the request
     * @param opportunity
     *            the opportunity
     * @return the agent response
     */
    private AgentResponse createReminder( final String agentId, final String opportunityId, final ReminderRequest request,
    		final Opportunity opportunity ) {

    	final List< String > notificationIds = pushReminderNotification( agentId, null, opportunityId,
    			request.getTriggerDtm().getTime(), null, NOTIFICATION_OCL_REMINDER );
    	if(CollectionUtils.isEmpty(notificationIds)) {
    		return new AgentResponse("", Status.FAILURE, "Did not get nofitication ID's from notification engine");
    	}
    	return setOpportunityReminder( agentId, opportunityId, request.getTriggerDtm().getTime(), notificationIds );
    }

    /**
     * Send scheduled task notification.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param taskId
     *            the task id
     * @param task
     *            the task
     * @param type
     *            the type
     * @return
     */
    @Override
    public AgentResponse setTaskReminder( final String agentId, final String opportunityId, final String taskId,
            final Task task, final PushNotificationType type ) {
        final List< String > notificationIds = pushReminderNotification( agentId, taskId, opportunityId,
                task.getDueDtm(), task, type );
        return saveReminder( agentId, taskId, task.getDueDtm(), notificationIds );
    }

    /**
     * Update reminder.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param reminderId
     *            the reminder id
     * @param updateReminder
     *            the update reminder
     * @return the agent response
     */
    @Override
    public AgentResponse updateReminder( final String agentId, final String taskId, final String reminderId,
            final ReminderRequest updateReminder ) {
        final Reminder reminder = agentReminderService.getReminder( agentId, taskId, reminderId );
        return updateReminder( agentId, taskId, reminderId, updateReminder.getTriggerDtm().getTime(), reminder );
    }

    /**
     * Gets the reminder.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param reminderId
     *            the reminder id
     * @return the reminder
     */
    @Override
    public Reminder getReminder( final String agentId, final String taskId, final String reminderId ) {
        return agentReminderService.getReminder( agentId, taskId, reminderId );
    }

    /**
     * Delete agent task reminder.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param reminderId
     *            the reminder id
     * @return the agent response
     */
    @Override
    public AgentResponse deleteReminder( final String agentId, final String taskId, final String reminderId ) {
        LOGGER.info( "Deleting reminderId {}, of taskId : {},  for agent: {}", reminderId, taskId, agentId );

        final Reminder reminder = agentReminderService.getReminder( agentId, taskId, reminderId );
        if (reminder != null) {
            // check for past reminders
            if (reminder.getTriggerDtm() < new DateTime().getMillis()) {
                return new AgentResponse( reminderId, Status.FAILURE,
                        "Operation was Unsuccessful. The reminder dateTime should be future dateTime." );
            }
            // Cancel reminders
            cancelReminder( agentId, taskId, reminderId, reminder );
            // hard delete reminder node from firebase
            agentReminderService.deleteReminder( agentId, taskId, reminderId );
        } else {
            return new AgentResponse( reminderId, Status.FAILURE,
                    "Operation was Unsuccessful. The reminder does not exist" );
        }
        return new AgentResponse( reminderId );
    }

    /**
     * Update reminder.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param reminderId
     *            the reminder id
     * @param updateReminder
     *            the update reminder
     * @return the agent response
     */
    @Override
    public void updateAndCancelReminder( final String agentId, final String taskId, final Long triggerDtm ) {
        final Task task = agentTaskService.getTaskById( agentId, taskId );
        final Map< String, Reminder > reminders = agentReminderService.getTaskReminders( agentId, taskId );
        for ( final Entry< String, Reminder > entries : reminders.entrySet() ) {
            final Reminder reminder = entries.getValue();
            if (reminder.getTriggerDtm().equals( task.getDueDtm() )) {
                updateReminder( agentId, taskId, entries.getKey(), triggerDtm, reminder );
            } else {
                cancelReminder( agentId, taskId, entries.getKey(), reminder );
            }
        }
    }

    /**
     * Update reminder.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param reminderId
     *            the reminder id
     * @param triggerDtm
     *            the trigger dtm
     * @param reminder
     *            the reminder
     */
    private AgentResponse updateReminder( final String agentId, final String taskId, final String reminderId,
            final Long triggerDtm, final Reminder reminder ) {
        if (null != reminder) {
            if (CollectionUtils.isNotEmpty( reminder.getNotificationIds() )) {
                reminder.getNotificationIds().forEach( notificationId -> {
                    agentNotificationBusinessService.updatePushNotification( notificationId, triggerDtm );
                } );
            }
            final Map< String, Object > reminderMap = new HashMap<>();
            reminderMap.put( "triggerDtm", triggerDtm );
            reminderMap.put( "lastModifiedDtm", new Date().getTime() );
            agentReminderService.patchReminder( agentId, taskId, reminderId, reminderMap );
        } else {
            throw new ApplicationException( ErrorCode.INVALID_TASK_REMINDER_ID.getErrorDetail(),
                    ErrorCode.INVALID_TASK_REMINDER_ID );
        }
        return new AgentResponse( reminderId );
    }

    /**
     * Cancel reminder.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param reminderId
     *            the reminder id
     * @return the agent response
     */
    @Override
    public void cancelAgentTaskReminders( final String agentId, final String taskId ) {
        LOGGER.info( "cancelling task reminders for task {} and agent {}", taskId, agentId );
        final Map< String, Reminder > reminders = agentReminderService.getTaskReminders( agentId, taskId );
        cancelReminders( agentId, taskId, reminders );
    }

    /**
     * Cancel reminders.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param reminders
     *            the reminders
     */
    @Override
    public void cancelReminders( final String agentId, final String taskId, final Map< String, Reminder > reminders ) {
        if (MapUtils.isNotEmpty( reminders )) {
            for ( final Entry< String, Reminder > entries : reminders.entrySet() ) {
                cancelReminder( agentId, taskId, entries.getKey(), entries.getValue() );
            }
        }
    }

    /**
     * Cancel reminder.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param reminderId
     *            the reminder id
     * @param reminder
     *            the reminder
     */
    @Override
    public void cancelReminder( final String agentId, final String taskId, final String reminderId,
            final Reminder reminder ) {
        if (null != reminder) {
            if (CollectionUtils.isNotEmpty( reminder.getNotificationIds() )) {
                reminder.getNotificationIds().forEach( notificationId -> {
                    agentNotificationBusinessService.cancelPushNotification( notificationId );
                } );
            }
            reminder.setNotificationIds( null );
            final Map< String, Object > reminderMap = new HashMap<>();
            reminderMap.put( "lastModifiedDtm", new Date().getTime() );
            reminderMap.put( "notificationIds", null );
            agentReminderService.patchReminder( agentId, taskId, reminderId, reminderMap );
        }
    }

    /**
     * Save reminder.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param dueDtm
     *            the due dtm
     * @param notificationIds
     *            the notification ids
     * @return the agent response
     */
    private AgentResponse saveReminder( final String agentId, final String taskId, final Long dueDtm,
            final List< String > notificationIds ) {
        final Reminder reminder = new Reminder( dueDtm, notificationIds );
        reminder.setCreatedDtm( new Date().getTime() );
        final PostResponse reminderResponse = agentReminderService.setReminder( agentId, taskId, reminder );
        LOGGER.info( "reminder set up successful, agentId " + agentId + ", taskId " + taskId + " & reminder id "
                + reminderResponse.getName() );
        return new AgentResponse( reminderResponse.getName() );
    }
    
    /**
     * Save reminder.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param dueDtm
     *            the due dtm
     * @param notificationIds
     *            the notification ids
     * @return the agent response
     */
    private AgentResponse setOpportunityReminder( final String agentId, final String opportunityId, final Long dueDtm,
            final List< String > notificationIds ) {
        final Reminder reminder = new Reminder( dueDtm, notificationIds );
        reminder.setCreatedDtm( new Date().getTime() );
        final PostResponse reminderResponse = agentReminderService.setOpportunityReminder( agentId, opportunityId, reminder );
        LOGGER.info( "reminder set up successful, agentId " + agentId + ", opportunityId " + opportunityId + " & reminder id "
                + reminderResponse.getName() );
        return new AgentResponse( reminderResponse.getName() );
    }
    

    /**
     * Push reminder notification.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param opportunityId
     *            the opportunity id
     * @param dueDtm
     *            the due dtm
     * @param task
     *            the task
     * @param type
     *            the type
     * @return the list
     */
    @Override
    public List< String > pushReminderNotification( final String agentId, final String taskId,
            final String opportunityId, final Long dueDtm, final Task task, final PushNotificationType type ) {
        final Search contactSearch = searchService.searchByOpportunityId( opportunityId );
        final NotificationRequest pushNotification = new NotificationRequest();
        if (null != contactSearch) {
            final com.owners.gravitas.domain.Contact contact = agentContactService
                    .getContactById( contactSearch.getAgentId(), contactSearch.getContactId() );
            pushNotification.setFirstName( contact.getFirstName() );
            pushNotification.setLastName( contact.getLastName() );
        }
        if(task != null) {
        	pushNotification.setTitle( task.getTitle() );
        }
        if(!StringUtils.isEmpty(taskId)) {
        	pushNotification.setTaskId( taskId );
        }
        pushNotification.setOpportunityId(opportunityId);
        pushNotification.setTriggerDtm( dueDtm );
        pushNotification.setEventType( type );
        return agentNotificationBusinessService.sendPushNotification( agentId, pushNotification );
    }

    /**
     * Push reminder notification.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param contact
     *            the contact
     * @param dueDtm
     *            the due dtm
     * @param task
     *            the task
     * @param type
     *            the type
     * @return the list
     */
    @Override
    public List< String > pushReminderNotification( final String agentId, final String taskId, final Contact contact,
            final Long dueDtm, final Task task, final PushNotificationType type ) {
        final NotificationRequest pushNotification = new NotificationRequest();
        if (null != contact) {
            pushNotification.setFirstName( contact.getFirstName() );
            pushNotification.setLastName( contact.getLastName() );
        }
        pushNotification.setTitle( task.getTitle() );
        pushNotification.setTaskId( taskId );
        pushNotification.setTriggerDtm( dueDtm );
        pushNotification.setEventType( type );
        return agentNotificationBusinessService.sendPushNotification( agentId, pushNotification );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentReminderBusinessService#
     * sendActionFlowIncompleteReminder()
     */
    @Override
    public void sendActionFlowIncompleteReminder() {
        agentService.getAllAgentIds()
                .forEach( agentId -> agentReminderTask.sendActionFlowIncompleteReminder( agentId ) );
    }

}
