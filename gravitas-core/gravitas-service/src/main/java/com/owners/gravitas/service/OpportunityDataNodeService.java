package com.owners.gravitas.service;

import com.owners.gravitas.domain.OpportunityDataNode;
import com.owners.gravitas.dto.response.PostResponse;
/**
 * The OpportunityDataNodeService
 * @author sandeepsoni
 *
 */
public interface OpportunityDataNodeService {


    /**
     * add Opps data node.
     *
     * @param oppsDataNode
     *            the agent note
     * @param agentId
     *            the agent id
     * @return the post response
     */
    PostResponse addDataNode( final OpportunityDataNode oppsDataNode, final String agentId
    		, final String opportunityId , final String dataNodeKey);
    
    /**
     * Get OpportunityDataNode
     *
     * @param cclRejectionReasonCode
     *            the agent note
     * @param agentId
     *            the agent id
     * @return
     */
    public OpportunityDataNode getDataNode( String agentId, String opportunityId, String dataNodeKey);

}
