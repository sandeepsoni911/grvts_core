package com.owners.gravitas.service.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dao.OpportunityDataNodeDao;
import com.owners.gravitas.domain.OpportunityDataNode;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.PostResponse;

/**
 * Test class for OpportunityDataNodeService
 * @author sandeepsoni
 *
 */
public class OpportunityDataNodeServiceImplTest extends AbstractBaseMockitoTest {

	
	@InjectMocks
	OpportunityDataNodeServiceImpl opportunityDataNodeServiceImpl;
	
	@Mock
	OpportunityDataNodeDao opportunityDataNodeDao;
	
	/**
	 * To test addDataNode
	 */
	@Test
	public void testAddDataNode() {
		OpportunityDataNode opportunityDataNode = new OpportunityDataNode();
		PostResponse response =  new PostResponse();
		response.setStatus(Status.SUCCESS);
		Mockito.when(opportunityDataNodeDao.addDataNode(Mockito.anyObject(), Mockito.anyString()
				, Mockito.anyString(), Mockito.anyString())).thenReturn(response);
		PostResponse postResponse = opportunityDataNodeServiceImpl.addDataNode(opportunityDataNode,
				"agentId", "opportunityId", "dataNodeKey");
		Assert.assertNotNull(postResponse);
		Assert.assertEquals(postResponse.getStatus(), Status.SUCCESS);
		
	}
	
	/**
	 * To test GetDataNode
	 */
	@Test
	public void testGetDataNode() {
		OpportunityDataNode opportunityDataNode = new OpportunityDataNode();
		Mockito.when(opportunityDataNodeDao.getDataNode( Mockito.anyString()
				, Mockito.anyString(), Mockito.anyString())).thenReturn(opportunityDataNode);
		OpportunityDataNode response = opportunityDataNodeServiceImpl.getDataNode("agentId"
				, "opportunityId", "dataNodeKey");
		Assert.assertNotNull(response);
		
	}
	
}
