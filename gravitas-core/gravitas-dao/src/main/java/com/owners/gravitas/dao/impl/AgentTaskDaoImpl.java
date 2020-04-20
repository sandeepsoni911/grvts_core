package com.owners.gravitas.dao.impl;

import static com.owners.gravitas.constants.Constants.AGENT_ID;
import static com.owners.gravitas.constants.Constants.OPPORTUNITY_ID;
import static com.owners.gravitas.constants.FirebaseQuery.GET_TASKS_BY_OPPORTUNITY_ID;
import static com.owners.gravitas.enums.TaskType.CONTACT_OPPORTUNITY;
import static com.owners.gravitas.util.RestUtil.buildRequest;
import static com.owners.gravitas.util.RestUtil.createHttpHeader;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

import com.owners.gravitas.dao.AgentTaskDao;
import com.owners.gravitas.domain.Task;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.util.JsonUtil;

/**
 * The Class AgentTaskDaoImpl.
 *
 * @author vishwanathm
 */
@Repository
public class AgentTaskDaoImpl extends BaseDaoImpl implements AgentTaskDao {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentTaskDaoImpl.class );

    /**
     * Save agent tasks for provided agent id.
     *
     * @param task
     *            the task
     * @param agentId
     *            the agent id
     * @return the post response
     */
    @Override
    public PostResponse saveAgentTask( final Task task, final String agentId ) {
        LOGGER.debug( "Adding task on firebase for agent " + agentId );
        final String reqUrl = buildFirebaseURL( "agents/" + agentId + "/tasks" );
        return getRestTemplate().exchange( reqUrl, HttpMethod.POST,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), task ),
                PostResponse.class ).getBody();
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
    public PostResponse saveAgentTasks( final Map< String, Task > taskMap, final String agentId ) {
        LOGGER.debug( "Adding all tasks on firebase for agent " + agentId );
        final String reqUrl = buildFirebaseURL( "agents/" + agentId + "/tasks" );
        return getRestTemplate().exchange( reqUrl, HttpMethod.PATCH,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), taskMap ),
                PostResponse.class ).getBody();
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
    public Task patchAgentTask( final String taskId, final String agentId, final Map< String, Object > task ) {
        LOGGER.debug( "Patching agent task on firebase and agent is " + agentId );
        final String reqUrl = buildFirebaseURL( "agents/" + agentId + "/tasks/" + taskId );
        return getRestTemplate().exchange( reqUrl, HttpMethod.PATCH,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), task ),
                Task.class ).getBody();
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
        final String reqUrl = buildFirebaseQueryURL( GET_TASKS_BY_OPPORTUNITY_ID );
        final QueryParams params = new QueryParams();
        params.add( AGENT_ID, agentId );
        params.add( OPPORTUNITY_ID, opportunityId );
        final Map< String, Map< String, String > > tasks = getRestTemplate().exchange( reqUrl, HttpMethod.GET,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), null ), Map.class,
                params.getParams() ).getBody();
        final Map< String, Task > newTasks = new HashMap< String, Task >();
        for ( final Entry< String, Map< String, String > > entry : tasks.entrySet() ) {
            final String jsonTask = JsonUtil.toJson( entry.getValue() );
            final Task task = JsonUtil.toType( jsonTask, Task.class );
            newTasks.put( entry.getKey(), task );
        }
        return newTasks;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dao.AgentTaskDao#getTaskById(java.lang.String,
     * java.lang.String)
     */
    @Override
    public Task getTaskById( final String agentId, final String taskId ) {
        final String reqUrl = buildFirebaseURL( "/agents/" + agentId + "/tasks/" + taskId );
        return getRestTemplate().exchange( reqUrl, HttpMethod.GET,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), null ),
                Task.class ).getBody();
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
    public Map< String, Task > getTaskByType( final String agentId, final String opportunityId, final String type ) {
        final Map< String, Task > result = new HashMap<>();
        final Map< String, Task > tasks = getTasksByOpportunityId( agentId, opportunityId );
        for ( final Entry< String, Task > entry : tasks.entrySet() ) {
            final Task task = entry.getValue();
            if (StringUtils.isNotBlank( task.getTaskType() ) && task.getTaskType().equalsIgnoreCase( type )) {
                result.put( entry.getKey(), task );
            }
        }
        return result;
    }

    /**
     * Gets the tasks by opportunity id.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @return the tasks by opportunity id
     */
    @Override
    public int getOpenContactOpportunityTypeTasks( final String agentId, final String opportunityId ) {
        final String reqUrl = buildFirebaseQueryURL( GET_TASKS_BY_OPPORTUNITY_ID );
        final QueryParams params = new QueryParams();
        int count = 0;
        params.add( AGENT_ID, agentId );
        params.add( OPPORTUNITY_ID, opportunityId );
        final Map< String, Map< String, String > > tasks = getRestTemplate().exchange( reqUrl, HttpMethod.GET,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), null ), Map.class,
                params.getParams() ).getBody();
        for ( final Map< String, String > entry : tasks.values() ) {
            if (CONTACT_OPPORTUNITY.getType().equals( entry.get( "taskType" ) )
                    && !entry.containsKey( "completedBy" )) {
                count++;
            }
        }
        return count;
    }
	
}
