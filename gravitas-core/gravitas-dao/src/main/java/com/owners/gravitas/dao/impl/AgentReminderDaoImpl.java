package com.owners.gravitas.dao.impl;

import static com.owners.gravitas.util.RestUtil.buildRequest;
import static com.owners.gravitas.util.RestUtil.createHttpHeader;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.owners.gravitas.dao.AgentReminderDao;
import com.owners.gravitas.domain.Reminder;
import com.owners.gravitas.dto.response.PostResponse;

/**
 * The Class AgentReminderDaoImpl.
 *
 * @author harshads
 */
@Repository
public class AgentReminderDaoImpl extends BaseDaoImpl implements AgentReminderDao {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentReminderDaoImpl.class );

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
    public PostResponse setReminder( final String agentId, final String taskId, final Reminder reminder ) {
        LOGGER.debug( "Set agent reminder for agent id " + agentId );
        final String reqUrl = buildFirebaseURL( "agents/" + agentId + "/tasks/" + taskId + "/reminders/" );
        return getRestTemplate().exchange( reqUrl, HttpMethod.POST,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), reminder ),
                PostResponse.class ).getBody();
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
    public PostResponse setOpportunityReminder( final String agentId, final String opportunityId, final Reminder reminder ) {
        LOGGER.debug( "Set agent reminder for agent id " + agentId );
        final String reqUrl = buildFirebaseURL( "agents/" + agentId + "/opportunities/" + opportunityId + "/reminders/" );
        return getRestTemplate().exchange( reqUrl, HttpMethod.POST,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), reminder ),
                PostResponse.class ).getBody();
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
     * @return the agent info
     */
    @Override
    public Reminder getReminder( final String agentId, final String taskId, final String reminderId ) {
        final String reqUrl = buildFirebaseURL( "agents/" + agentId + "/tasks/" + taskId + "/reminders/" + reminderId );
        return getRestTemplate().exchange( reqUrl, HttpMethod.GET,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), null ),
                Reminder.class ).getBody();
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
     * @return the post response
     */
    @Override
    public PostResponse patchReminder( final String agentId, final String taskId, final String reminderId,
            final Map< String, Object > reminder ) {
        LOGGER.debug( "patch agent reminder for reminder id " + agentId );
        final String reqUrl = buildFirebaseURL( "agents/" + agentId + "/tasks/" + taskId + "/reminders/" + reminderId );
        return getRestTemplate().exchange( reqUrl, HttpMethod.PATCH,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), reminder ),
                PostResponse.class ).getBody();
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
    public Reminder deleteReminder( final String agentId, final String taskId, final String reminderId ) {
        final String reqUrl = buildFirebaseURL( "agents/" + agentId + "/tasks/" + taskId + "/reminders/" + reminderId );
        return getRestTemplate().exchange( reqUrl, HttpMethod.DELETE,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), null ),
                Reminder.class ).getBody();
    }

    /**
     * Gets the all reminders.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @return the all reminders
     */
    @Override
    public Map< String, Reminder > getTaskReminders( final String agentId, final String taskId ) {
        final String reqUrl = buildFirebaseURL( "agents/" + agentId + "/tasks/" + taskId + "/reminders" );
        final Map< String, Map< String, String > > reminders = getRestTemplate().exchange( reqUrl, HttpMethod.GET,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), null ),
                Map.class ).getBody();
        final Map< String, Reminder > remindersMap = new HashMap< String, Reminder >();
        if (MapUtils.isNotEmpty( reminders )) {
            for ( final Entry< String, Map< String, String > > entry : reminders.entrySet() ) {
                final Reminder reminder = new ObjectMapper().convertValue( entry.getValue(), Reminder.class );
                remindersMap.put( entry.getKey(), reminder );
            }
        }
        return remindersMap;
    }
}
