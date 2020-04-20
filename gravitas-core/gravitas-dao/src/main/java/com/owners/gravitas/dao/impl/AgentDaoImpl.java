package com.owners.gravitas.dao.impl;

import static com.owners.gravitas.constants.Constants.AGENT_ID;
import static com.owners.gravitas.constants.FirebaseQuery.AGENT_ID_EXIST;
import static com.owners.gravitas.constants.FirebaseQuery.GET_AGENT_BY_ID;
import static com.owners.gravitas.constants.FirebaseQuery.GET_AGENT_EMAIL;
import static com.owners.gravitas.constants.FirebaseQuery.GET_ALL_AGENT_ID;
import static com.owners.gravitas.util.RestUtil.buildRequest;
import static com.owners.gravitas.util.RestUtil.createHttpHeader;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

import com.owners.gravitas.dao.AgentDao;
import com.owners.gravitas.domain.Agent;
import com.owners.gravitas.domain.AgentHolder;
import com.owners.gravitas.domain.LastViewed;
import com.owners.gravitas.dto.QueryParams;

/**
 * The Class AgentDaoImpl.
 *
 * @author vishwanathm
 */
@Repository
public class AgentDaoImpl extends BaseDaoImpl implements AgentDao {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentDaoImpl.class );

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dao.AgentDao#saveAgent(com.owners.gravitas.domain.
     * AgentHolder)
     */
    @Override
    public Agent saveAgent( final AgentHolder agentHolder ) {
        final String reqUrl = buildFirebaseURL( "/agents/" + agentHolder.getAgentId() );
        return getRestTemplate().exchange( reqUrl, HttpMethod.PUT,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ),
                        agentHolder.getAgent() ),
                Agent.class ).getBody();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dao.AgentDao#deleteAgent(java.lang.String)
     */
    @Override
    public Agent deleteAgent( final String agentId ) {
        final String reqUrl = buildFirebaseURL( "/agents/" + agentId );
        return getRestTemplate().exchange( reqUrl, HttpMethod.DELETE,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), null ),
                Agent.class ).getBody();
    }

    /**
     * Update last viewed.
     *
     * @param agentId
     *            the agent id
     * @param id
     *            the id
     * @param node
     *            the node
     */
    @Override
    public LastViewed updateLastViewed( final String agentId, final String id, final String node ) {
        final LastViewed lastViewed = new LastViewed();
        lastViewed.setLastViewedDtm( new Date().getTime() );
        final String reqUrl = buildFirebaseURL( "/agents/" + agentId + "/" + node + "/" + id );
        return getRestTemplate().exchange( reqUrl, HttpMethod.PATCH,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), lastViewed ),
                LastViewed.class ).getBody();
    }

    /**
     * Gets the all agents id.
     *
     * @return the all agents id
     */
    @Override
    public Set< String > getAllAgentIds() {
        final String reqUrl = getFirebaseHost() + GET_ALL_AGENT_ID;
        final Map< String, Boolean > reposne = getRestTemplate().exchange( reqUrl, HttpMethod.GET,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), null ),
                Map.class ).getBody();
        return reposne.keySet();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dao.AgentDao#isAgentExist(java.lang.String)
     */
    @Override
    public boolean isAgentExist( final String agentId ) {
        final String reqUrl = getFirebaseHost() + AGENT_ID_EXIST;
        final QueryParams params = new QueryParams();
        params.add( AGENT_ID, agentId );
        final Map< String, Boolean > response = getRestTemplate().exchange( reqUrl, HttpMethod.GET,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), null ), Map.class,
                params.getParams() ).getBody();
        return response != null;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dao.AgentDao#getAgentById(java.lang.String)
     */
    @Override
    public Agent getAgentById( final String agentId ) {
        final String reqUrl = getFirebaseHost() + GET_AGENT_BY_ID;
        final QueryParams params = new QueryParams();
        params.add( AGENT_ID, agentId );
        final Agent agent = getRestTemplate().exchange( reqUrl, HttpMethod.GET,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), null ),
                Agent.class, params.getParams() ).getBody();
        return agent;
    }

    /**
     * Gets the agent email.
     *
     * @param agentId
     *            the agent id
     * @return the agent email
     */
    @Override
    public String getAgentEmailById( final String agentId ) {
        final String reqUrl = getFirebaseHost() + GET_AGENT_EMAIL;
        final QueryParams params = new QueryParams();
        params.add( AGENT_ID, agentId );
        return getRestTemplate().exchange( reqUrl, HttpMethod.GET,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), null ),
                String.class, params.getParams() ).getBody();
    }

}
