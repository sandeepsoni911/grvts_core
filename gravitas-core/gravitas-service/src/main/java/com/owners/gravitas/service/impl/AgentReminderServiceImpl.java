package com.owners.gravitas.service.impl;

import static com.owners.gravitas.constants.Constants.ACTION_OBJ;
import static com.owners.gravitas.constants.Constants.AGENT_ID;
import static com.owners.gravitas.constants.Constants.ENTITY_ID;
import static com.owners.gravitas.enums.ActionEntity.REMINDER;
import static com.owners.gravitas.enums.ActionType.CREATE;
import static com.owners.gravitas.enums.ActionType.DELETE;
import static com.owners.gravitas.enums.ActionType.UPDATE;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.annotation.Audit;
import com.owners.gravitas.dao.AgentReminderDao;
import com.owners.gravitas.domain.Reminder;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.service.AgentReminderService;

/**
 * The Class AgentReminderServiceImpl.
 *
 * @author harshads
 */
@Service
public class AgentReminderServiceImpl implements AgentReminderService {

    /** The agent reminder dao. */
    @Autowired
    private AgentReminderDao agentReminderDao;

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
    @Override
    @Audit( type = CREATE, entity = REMINDER, args = { AGENT_ID, "taskId", ACTION_OBJ } )
    public PostResponse setReminder( final String agentId, final String taskId, final Reminder reminder ) {
        return agentReminderDao.setReminder( agentId, taskId, reminder );
    }

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
    @Override
    @Audit( type = CREATE, entity = REMINDER, args = { AGENT_ID, "opportunityId", ACTION_OBJ } )
    public PostResponse setOpportunityReminder( final String agentId, final String opportunityId, final Reminder reminder ) {
        return agentReminderDao.setOpportunityReminder( agentId, opportunityId, reminder );
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
        return agentReminderDao.getReminder( agentId, taskId, reminderId );
    }

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
    @Override
    @Audit( type = UPDATE, entity = REMINDER, args = { AGENT_ID, "taskId", ENTITY_ID, ACTION_OBJ } )
    public PostResponse patchReminder( final String agentId, final String taskId, final String reminderId,
            final Map< String, Object > reminder ) {
        return agentReminderDao.patchReminder( agentId, taskId, reminderId, reminder );
    }

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
    @Override
    @Audit( type = DELETE, entity = REMINDER, args = { AGENT_ID, "taskId", ACTION_OBJ } )
    public Reminder deleteReminder( final String agentId, final String taskId, final String reminderId ) {
        return agentReminderDao.deleteReminder( agentId, taskId, reminderId );
    }

    /**
     * Gets the task reminders.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @return the all reminders
     */
    @Override
    public Map< String, Reminder > getTaskReminders( final String agentId, final String taskId ) {
        return agentReminderDao.getTaskReminders( agentId, taskId );
    }
}
