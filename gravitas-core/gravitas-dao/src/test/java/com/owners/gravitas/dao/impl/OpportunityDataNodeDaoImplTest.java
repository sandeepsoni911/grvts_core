package com.owners.gravitas.dao.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dao.helper.FirebaseRestAuthenticator;
import com.owners.gravitas.domain.OpportunityDataNode;
import com.owners.gravitas.dto.FirebaseAccess;
import com.owners.gravitas.dto.response.PostResponse;

/**
 * Test class for OpportunityDataNodeDaoImpl
 * @author sandeepsoni
 *
 */
public class OpportunityDataNodeDaoImplTest extends AbstractBaseMockitoTest {
	
	@InjectMocks
	OpportunityDataNodeDaoImpl opportunityDataNodeDaoImpl;
	
	 /** The rest template. */
    @Mock
    private RestTemplate restTemplate;

    /** The authenticator. */
    @Mock
    private FirebaseRestAuthenticator authenticator;
	
	
	/**
	 * To test AddDataNode
	 */
	@Test
	public void testAddDataNode() {
		FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( OpportunityDataNode.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new PostResponse(), HttpStatus.OK ) );
		OpportunityDataNode opportunityDataNode = new OpportunityDataNode();
		PostResponse postResponse = opportunityDataNodeDaoImpl.addDataNode(opportunityDataNode,
				"agentId", "oppsId", "noteKey");
		Assert.assertNotNull(postResponse);
	}
	
	/**
	 * To test GetDataNode
	 */
	@Test
	public void testGetDataNode() {
		FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( OpportunityDataNode.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new OpportunityDataNode(), HttpStatus.OK ) );
		OpportunityDataNode opportunityDataNode = new OpportunityDataNode();
		OpportunityDataNode response = opportunityDataNodeDaoImpl.getDataNode("agentId", "oppsId", "noteKey");
		Assert.assertNotNull(response);
	}

	
	
}
