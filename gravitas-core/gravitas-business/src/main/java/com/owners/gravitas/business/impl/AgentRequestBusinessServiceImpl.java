package com.owners.gravitas.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.AgentRequestBusinessService;
import com.owners.gravitas.business.builder.domain.RequestBuilder;
import com.owners.gravitas.domain.Request;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.service.AgentRequestService;

/**
 * The Class AgentRequestBusinessServiceImpl.
 *
 * @author vishwanathm
 */
@Service( "agentRequestBusinessService" )
public class AgentRequestBusinessServiceImpl implements AgentRequestBusinessService {

    /** The request service. */
    @Autowired
    private AgentRequestService requestService;

    /** The request builder. */
    @Autowired
    private RequestBuilder requestBuilder;

    /** The enable opportunity buyer request. */
    @Value( "${opportunity.buyerRequest.enable: true}" )
    private boolean enableOpportunityBuyerRequest;

    /**
     * Create agent request.
     *
     * @param opportunitySource
     *            the opportunity source
     * @param opportunityId
     *            the opportunity id
     * @param agentId
     *            the agent id
     * @return the post response
     */
    @Override
    public PostResponse createAgentRequest( final OpportunitySource opportunitySource, final String opportunityId,
            final String agentId ) {
        PostResponse requestResponse = null;
        if (enableOpportunityBuyerRequest) {
            final Request request = requestBuilder.convertTo( opportunitySource );
            if (request != null) {
                request.setOpportunityId( opportunityId );
                requestResponse = requestService.saveRequest( agentId, request );
            }
        }
        return requestResponse;
    }
}
