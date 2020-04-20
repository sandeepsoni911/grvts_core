/**
 *
 */
package com.owners.gravitas.service;

import java.util.Map;

import com.owners.gravitas.domain.Reminder;
import com.owners.gravitas.dto.response.PostResponse;

// TODO: Auto-generated Javadoc
/**
 * The Interface AgentReminderService.
 *
 * @author harshads
 */
public interface AgentReminderService {

    /**
     * Sets the reminder.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param reminder
     *            the reminder
     * @return the post response
     */
    PostResponse setReminder( String agentId, String taskId, Reminder reminder );
    
    /**
     * Sets the reminder.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the opportunity id
     * @param reminder
     *            the reminder
     * @return the post response
     */
    PostResponse setOpportunityReminder( String agentId, String opportunityId, Reminder reminder );

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
     * Patch reminder.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param reminderId
     *            the reminder id
     * @param reminder
     *            the reminder
     * @return the reminder
     */
    PostResponse patchReminder( String agentId, String taskId, String reminderId, Map< String, Object > reminder );

    /**
     * Delete reminder.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param reminderId
     *            the reminder id
     * @return the reminder
     */
    Reminder deleteReminder( final String agentId, final String taskId, final String reminderId );

    /**
     * Gets the task reminders.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @return the all reminders
     */
    Map< String, Reminder > getTaskReminders( String agentId, String taskId );
}
