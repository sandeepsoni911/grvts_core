package com.owners.gravitas.dao;

import com.owners.gravitas.domain.OpportunityDataNode;
import com.owners.gravitas.dto.response.PostResponse;

/**
 * The OpportunityDataNodeDao
 * @author sandeepsoni
 *
 */
public interface OpportunityDataNodeDao {
	
	/**
     * Save OpportunityDataNode
     *
     * @param cclRejectionReasonCode
     *            the agent note
     * @param agentId
     *            the agent id
     * @return
     */
    public PostResponse addDataNode( OpportunityDataNode oppsDataNode, String agentId, String opportunityId
    		, String dataNodeKey);
    
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
