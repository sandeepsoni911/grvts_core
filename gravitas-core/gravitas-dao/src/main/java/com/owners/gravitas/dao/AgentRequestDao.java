package com.owners.gravitas.dao;

import java.util.Map;

import com.owners.gravitas.domain.Request;
import com.owners.gravitas.dto.response.PostResponse;

/**
 * The Interface RequestDao.
 */
public interface AgentRequestDao {

    /**
     * Save request.
     *
     * @param buyerRequest
     *            the buyer request
     * @param agentId
     *            the agent id
     * @return the post response
     */
    PostResponse saveAgentRequest( Request buyerRequest, String agentId );

    /**
     * Gets the buyer request.
     *
     * @param agentId
     *            the agent id
     * @param requestId
     *            the request id
     * @return the buyer request
     */
    Request getBuyerRequest( String agentId, String requestId );

    /**
     * Gets the agent opportunity all requests.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @return the agent opportunity all requests
     */
    Map< String, Request > getRequestsByOpportunityId( String agentId, String opportunityId );

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
    Request patchAgentRequest( String requestId, String agentId, Map< String, Object > request );

    /**
     * Save agent requests.
     *
     * @param requestMap
     *            the request map
     * @param agentId
     *            the agent id
     * @return the post response
     */
    PostResponse saveAgentRequests( Map< String, Request > requestMap, String agentId );

}
