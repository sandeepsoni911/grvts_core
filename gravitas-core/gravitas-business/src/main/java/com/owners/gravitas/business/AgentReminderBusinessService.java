package com.owners.gravitas.business;

import java.util.List;
import java.util.Map;

import com.owners.gravitas.domain.Reminder;
import com.owners.gravitas.domain.Task;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.request.ReminderRequest;
import com.owners.gravitas.dto.response.AgentResponse;
import com.owners.gravitas.enums.PushNotificationType;

/**
 * The Interface AgentReminderBusinessService.
 *
 * @author harshads
 */
public interface AgentReminderBusinessService {

    /**
     * Notify agent about new opportunities in the queue.
     */
    void sendNewOppReminder();

    /**
     * Notify agent about claimed opportunities which needed to be contacted are
     * in the queue.
     */
    void sendClaimedOppReminder();

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
    AgentResponse setReminder( String agentId, String taskId, ReminderRequest request );
    
    /**
     * Sets the reminder.
     *
     * @param agentId
     *            the agent id
     * @param request
     *            the request
     * @return the agent response
     */
    AgentResponse setReminder( String agentId, ReminderRequest request );

    /**
     * Sets the task reminder.
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
     */
    AgentResponse setTaskReminder( String agentId, String opportunityId, String taskId, Task task,
            PushNotificationType type );

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
    AgentResponse updateReminder( String agentId, String taskId, String reminderId, ReminderRequest updateReminder );

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
    Reminder getReminder( String agentId, String taskId, String reminderId );

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
    AgentResponse deleteReminder( String agentId, String taskId, String reminderId );

    /**
     * Cancel reminder.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     */
    void cancelAgentTaskReminders( String agentId, String taskId );

    /**
     * Update reminders.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param triggerDtm
     *            the trigger dtm
     */
    void updateAndCancelReminder( String agentId, String taskId, Long triggerDtm );

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
    AgentResponse createReminder( String agentId, String taskId, ReminderRequest request, Task task );

    /**
     * Cancel and patch reminders.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param reminders
     *            the reminders
     */
    void cancelReminders( String agentId, String taskId, Map< String, Reminder > reminders );

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
    void cancelReminder( String agentId, String taskId, String reminderId, Reminder reminder );

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
    List< String > pushReminderNotification( String agentId, String taskId, String opportunityId, Long dueDtm,
            Task task, PushNotificationType type );

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
    List< String > pushReminderNotification( String agentId, String taskId, Contact contact, Long dueDtm, Task task,
            PushNotificationType type );

    /**
     * Notify agents to complete any incomplete action flow
     */
    void sendActionFlowIncompleteReminder();

}
