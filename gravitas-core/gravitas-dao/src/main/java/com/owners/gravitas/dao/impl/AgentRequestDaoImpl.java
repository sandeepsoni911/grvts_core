package com.owners.gravitas.dao.impl;

import static com.owners.gravitas.constants.Constants.AGENT_ID;
import static com.owners.gravitas.constants.Constants.OPPORTUNITY_ID;
import static com.owners.gravitas.constants.FirebaseQuery.GET_REQUESTS_BY_OPPORTUNITY_ID;
import static com.owners.gravitas.util.RestUtil.buildRequest;
import static com.owners.gravitas.util.RestUtil.createHttpHeader;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.owners.gravitas.dao.AgentRequestDao;
import com.owners.gravitas.domain.Request;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.dto.response.PostResponse;

/**
 * The Class RequestDaoImpl.
 */
@Repository
public class AgentRequestDaoImpl extends BaseDaoImpl implements AgentRequestDao {

    /**
     * Save request.
     *
     * @param buyerRequest
     *            the buyer request
     * @param agentId
     *            the agent id
     */
    @Override
    public PostResponse saveAgentRequest( final Request buyerRequest, final String agentId ) {
        String reqUrl = buildFirebaseURL( "agents/" + agentId + "/requests" );
        return getRestTemplate().exchange( reqUrl, HttpMethod.POST,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), buyerRequest ),
                PostResponse.class ).getBody();
    }

    /**
     * Gets the buyer request.
     *
     * @param agentId
     *            the agent id
     * @param requestId
     *            the request id
     * @return the buyer request
     */
    @Override
    public Request getBuyerRequest( final String agentId, final String requestId ) {
        String reqUrl = buildFirebaseURL( "agents/" + agentId + "/requests/" + requestId );
        return getRestTemplate().exchange( reqUrl, HttpMethod.GET,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), null ),
                Request.class ).getBody();
    }

    /**
     * Gets the agent opportunity all requests.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @return the agent opportunity all requests
     */
    @Override
    public Map< String, Request > getRequestsByOpportunityId( final String agentId, final String opportunityId ) {
        final String reqUrl = buildFirebaseQueryURL( GET_REQUESTS_BY_OPPORTUNITY_ID );
        final QueryParams params = new QueryParams();
        params.add( AGENT_ID, agentId );
        params.add( OPPORTUNITY_ID, opportunityId );
        final Map< String, Map< String, String > > requests = getRestTemplate().exchange( reqUrl, HttpMethod.GET,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), null ), Map.class,
                params.getParams() ).getBody();
        final Map< String, Request > newRequest = new HashMap< String, Request >();
        for ( Entry< String, Map< String, String > > entry : requests.entrySet() ) {
            final ObjectMapper mapper = new ObjectMapper();
            final Request request = mapper.convertValue( entry.getValue(), Request.class );
            newRequest.put( entry.getKey(), request );
        }
        return newRequest;
    }

    /**
     * Patch agent request.
     *
     * @param requestId
     *            the request id
     * @param agentId
     *            the agent id
     * @param request
     *            the request
     * @return the request
     */
    @Override
    public Request patchAgentRequest( final String requestId, final String agentId,
            final Map< String, Object > request ) {

        final String reqUrl = buildFirebaseURL( "agents/" + agentId + "/requests/" + requestId );
        return getRestTemplate().exchange( reqUrl, HttpMethod.PATCH,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), request ),
                Request.class ).getBody();
    }

    /**
     * Save agent requests.
     *
     * @param requestMap
     *            the request map
     * @param agentId
     *            the agent id
     * @return the post response
     */
    @Override
    public PostResponse saveAgentRequests( final Map< String, Request > requestMap, final String agentId ) {
        final String reqUrl = buildFirebaseURL( "agents/" + agentId + "/requests" );
        return getRestTemplate().exchange( reqUrl, HttpMethod.PATCH,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), requestMap ),
                PostResponse.class ).getBody();
    }
}
