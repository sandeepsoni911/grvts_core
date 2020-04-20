package com.owners.gravitas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.dao.OpportunityDataNodeDao;
import com.owners.gravitas.domain.OpportunityDataNode;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.service.OpportunityDataNodeService;

/**
 * Implementation class for OpportunityDataNodeService
 * 
 * @author sandeepsoni
 *
 */
@Service
public class OpportunityDataNodeServiceImpl implements OpportunityDataNodeService {

	@Autowired
	OpportunityDataNodeDao opportunityDataNodeDao;

	@Override
	public PostResponse addDataNode(OpportunityDataNode oppsDataNode, String agentId, String opportunityId,
			String dataNodeKey) {
		return opportunityDataNodeDao.addDataNode(oppsDataNode, agentId, opportunityId, dataNodeKey);
	}

	@Override
	public OpportunityDataNode getDataNode(String agentId, String opportunityId, String dataNodeKey) {
		return opportunityDataNodeDao.getDataNode(agentId, opportunityId, dataNodeKey);
	}

}
