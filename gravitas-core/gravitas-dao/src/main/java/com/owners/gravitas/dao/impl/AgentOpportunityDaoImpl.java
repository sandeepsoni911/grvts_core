package com.owners.gravitas.dao.impl;

import static com.owners.gravitas.constants.Constants.AGENT_ID;
import static com.owners.gravitas.constants.Constants.FROM_DTM;
import static com.owners.gravitas.constants.FirebaseQuery.GET_AGENTS_CLAIMED_OPPORTUNITY;
import static com.owners.gravitas.constants.FirebaseQuery.GET_AGENTS_NEW_OPPORTUNITY;
import static com.owners.gravitas.constants.FirebaseQuery.GET_OPPORTUNITIES_BY_AGENT_ID;
import static com.owners.gravitas.util.RestUtil.buildRequest;
import static com.owners.gravitas.util.RestUtil.createHttpHeader;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

import com.owners.gravitas.dao.AgentOpportunityDao;
import com.owners.gravitas.dao.AgentTaskDao;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.util.StringUtils;

/**
 * The Class OpportunityDaoImpl.
 *
 * @author harshads
 */
@Repository
public class AgentOpportunityDaoImpl extends BaseDaoImpl implements AgentOpportunityDao {

    /** The agent task dao. */
    @Autowired
    private AgentTaskDao agentTaskDao;

    /**
     * Gets the opportunity by id.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @return the opportunity by id
     */
    @Override
    public Opportunity getOpportunityById( final String agentId, final String opportunityId ) {
        String reqUrl = buildFirebaseURL( "agents/" + agentId + "/opportunities/" + opportunityId );
        return getRestTemplate().exchange( reqUrl, HttpMethod.GET,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), null ),
                Opportunity.class ).getBody();
    }

    /**
     * Save opportunity.
     *
     * @param agentId
     *            the agent id
     * @param opportunity
     *            the opportunity
     */
    @Override
    public PostResponse saveOpportunity( final String agentId, final Opportunity opportunity ) {
        final String reqUrl = buildFirebaseURL( "agents/" + agentId + "/opportunities" );
        return getRestTemplate().exchange( reqUrl, HttpMethod.POST,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), opportunity ),
                PostResponse.class ).getBody();
    }

    /**
     * Update opportunity.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param opportunity
     *            the opportunity
     */
    @Override
    public void updateOpportunity( final String agentId, final String opportunityId, final Opportunity opportunity ) {
        final String reqUrl = buildFirebaseURL( "agents/" + agentId + "/opportunities/" + opportunityId );
        getRestTemplate().exchange( reqUrl, HttpMethod.PATCH,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), opportunity ),
                Opportunity.class ).getBody();
    }

    /**
     * Save opportunity stage.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param opportunity
     *            the opportunity
     * @return the Opportunity
     */
    @Override
    public Opportunity patchOpportunity( final String agentId, final String opportunityId,
            final Map< String, Object > opportunity ) {
        String reqUrl = buildFirebaseURL( "agents/" + agentId + "/opportunities/" + opportunityId + "/" );
        return getRestTemplate().exchange( reqUrl, HttpMethod.PATCH,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), opportunity ),
                Opportunity.class ).getBody();
    }

    /**
     * Gets the agent opportunites.
     *
     * @param agentId
     *            the agent id
     * @return the agent opportunites
     */
    @Override
    public Map< String, Object > getAgentOpportunites( final String agentId ) {
        final String reqUrl = buildFirebaseURL( "agents/" + agentId + "/opportunities" );
        return getRestTemplate().exchange( reqUrl, HttpMethod.GET,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), null ),
                Map.class ).getBody();
    }

    /**
     * Gets the agent new opportunities count.
     *
     * @param agentId
     *            the agent id
     * @return the agent new opportunities count
     */
    @Override
    public int getAgentNewOpportunitiesCount( final String agentId ) {
        final String reqUrl = getFirebaseHost() + String.format( GET_AGENTS_NEW_OPPORTUNITY, agentId );
        final Map< String, Map > response = getRestTemplate().exchange( reqUrl, HttpMethod.GET,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), null ),
                Map.class ).getBody();
        int count = 0;
        final Set< Map.Entry< String, Map > > entries = response.entrySet();
        for ( Map.Entry< String, Map > entry : entries ) {
            if (entry.getValue().containsKey( "deleted" ) && ( Boolean ) entry.getValue().get( "deleted" )) {
                continue;
            }
            count++;
        }
        return count;
    }

    @Override
    public int getOpportunityCountByDays( final String agentId, final long fromDtm ) {
        final String reqUrl = getFirebaseHost() + GET_OPPORTUNITIES_BY_AGENT_ID;
        final QueryParams params = new QueryParams();
        params.add( AGENT_ID, agentId );
        params.add( FROM_DTM, StringUtils.convertObjectToString( fromDtm ) );
        final Map< String, Map > response = getRestTemplate().exchange( reqUrl, HttpMethod.GET,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), null ), Map.class,
                params.getParams() ).getBody();
        final Set< Map.Entry< String, Map > > entries = response.entrySet();
        int count = 0;
        for ( Map.Entry< String, Map > entry : entries ) {
            if (entry.getValue().containsKey( "deleted" ) && ( Boolean ) entry.getValue().get( "deleted" )) {
                continue;
            }
            count++;
        }
        return count;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dao.AgentOpportunityDao#
     * hasAgentClaimedOpportunityWithOpenTasks(java.lang.String)
     */
    @Override
    public boolean hasAgentClaimedOpportunityWithOpenTasks( final String agentId ) {
        final String reqUrl = getFirebaseHost() + String.format( GET_AGENTS_CLAIMED_OPPORTUNITY, agentId );
        final Map< String, Map > response = getRestTemplate().exchange( reqUrl, HttpMethod.GET,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), null ),
                Map.class ).getBody();
        final Set< Map.Entry< String, Map > > entries = response.entrySet();
        for ( Map.Entry< String, Map > entry : entries ) {
            if (entry.getValue().containsKey( "deleted" ) && ( Boolean ) entry.getValue().get( "deleted" )) {
                continue;
            }
            if (agentTaskDao.getOpenContactOpportunityTypeTasks( agentId, entry.getKey() ) > 0) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dao.AgentOpportunityDao#getOpportunitiesFromTime(java
     * .lang.String, long)
     */
    @Override
    public Map< String, Map > getOpportunitiesFromTime( final String agentId, final long fromDtm ) {
        final String reqUrl = getFirebaseHost() + GET_OPPORTUNITIES_BY_AGENT_ID;
        final QueryParams params = new QueryParams();
        params.add( AGENT_ID, agentId );
        params.add( FROM_DTM, StringUtils.convertObjectToString( fromDtm ) );
        final Map< String, Map > response = getRestTemplate().exchange( reqUrl, HttpMethod.GET,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), null ), Map.class,
                params.getParams() ).getBody();
        return response;
    }
}
