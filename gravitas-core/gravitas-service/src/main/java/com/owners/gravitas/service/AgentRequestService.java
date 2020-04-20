package com.owners.gravitas.service;

import java.util.Map;

import com.owners.gravitas.domain.Request;
import com.owners.gravitas.dto.response.PostResponse;

/**
 * The Interface RequestService.
 */
public interface AgentRequestService {

    /**
     * Save request.
     *
     * @param agentId
     *            the agent id
     * @param buyerRequest
     *            the buyer request
     * @return the post response
     */
    public PostResponse saveRequest( String agentId, Request buyerRequest );

    /**
     * Gets the buyer request.
     *
     * @param agentId
     *            the agent id
     * @param requestId
     *            the request id
     * @return the buyer request
     */
    public Request getBuyerRequest( String agentId, String requestId );

    /**
     * Gets the agent opportunity all requests.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @return the agent opportunity all requests
     */
    public Map< String, Request > getRequestsByOpportunityId( String agentId, String opportunityId );

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
    public Request patchAgentRequest( String requestId, String agentId, Map< String, Object > request );

    /**
     * Save agent requests.
     *
     * @param newRequests
     *            the new requests
     * @param newAgentId
     *            the new agent id
     */
    public void saveAgentRequests( Map< String, Request > newRequests, String newAgentId );
}
