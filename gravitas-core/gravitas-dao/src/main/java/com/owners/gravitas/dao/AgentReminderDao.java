package com.owners.gravitas.dao;

import java.util.Map;

import com.owners.gravitas.domain.Reminder;
import com.owners.gravitas.dto.response.PostResponse;

/**
 * The Interface AgentReminderDao.
 *
 * @author harshads
 */
public interface AgentReminderDao {

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
     *            the opportunity Id
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
     * @return the agent info
     */
    Reminder getReminder( String agentId, String taskId, String reminderId );

    /**
     * Gets the all reminders.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @return the all reminders
     */
    Map< String, Reminder > getTaskReminders( String agentId, String taskId );

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
     * @return the post response
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

}
