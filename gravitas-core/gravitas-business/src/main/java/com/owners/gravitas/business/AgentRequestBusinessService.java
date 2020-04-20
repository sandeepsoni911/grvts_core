package com.owners.gravitas.business;

import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.dto.response.PostResponse;

/**
 * The Class AgentRequestBusinessServiceImpl.
 *
 * @author vishwanathm
 **/
public interface AgentRequestBusinessService {

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
    PostResponse createAgentRequest( OpportunitySource opportunitySource, String opportunityId, String agentId );

}
