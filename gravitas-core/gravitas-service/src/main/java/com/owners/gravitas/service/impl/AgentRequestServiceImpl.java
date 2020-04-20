package com.owners.gravitas.service.impl;

import static com.owners.gravitas.constants.Constants.ACTION_OBJ;
import static com.owners.gravitas.constants.Constants.AGENT_ID;
import static com.owners.gravitas.constants.Constants.ENTITY_ID;
import static com.owners.gravitas.enums.ActionEntity.REQUEST;
import static com.owners.gravitas.enums.ActionType.CREATE;
import static com.owners.gravitas.enums.ActionType.UPDATE;
import static com.owners.gravitas.enums.ActionType.UPSERT;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.annotation.Audit;
import com.owners.gravitas.dao.AgentRequestDao;
import com.owners.gravitas.domain.Request;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.service.AgentRequestService;

/**
 * The Class RequestServiceImpl.
 *
 * @author harshads
 */
@Service
public class AgentRequestServiceImpl implements AgentRequestService {

    /** The request dao. */
    @Autowired
    private AgentRequestDao agentRequestDao;

    /**
     * Save request.
     *
     * @param agentId
     *            the agent id
     * @param buyerRequest
     *            the buyer request
     * @return the post response
     */
    @Override
    @Audit( type = CREATE, entity = REQUEST, args = { AGENT_ID, ACTION_OBJ } )
    public PostResponse saveRequest( final String agentId, final Request request ) {
        return agentRequestDao.saveAgentRequest( request, agentId );
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
        return agentRequestDao.getBuyerRequest( agentId, requestId );
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
        return agentRequestDao.getRequestsByOpportunityId( agentId, opportunityId );
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
    @Audit( type = UPDATE, entity = REQUEST, args = { ENTITY_ID, AGENT_ID, ACTION_OBJ } )
    public Request patchAgentRequest( final String requestId, final String agentId,
            final Map< String, Object > request ) {
        return agentRequestDao.patchAgentRequest( requestId, agentId, request );
    }

    /**
     * Save agent requests.
     *
     * @param newRequests
     *            the new requests
     * @param newAgentId
     *            the new agent id
     */
    @Override
    @Audit( type = UPSERT, entity = REQUEST, args = { ACTION_OBJ, AGENT_ID } )
    public void saveAgentRequests( final Map< String, Request > requestMap, final String agentId ) {
        agentRequestDao.saveAgentRequests( requestMap, agentId );
    }

}
