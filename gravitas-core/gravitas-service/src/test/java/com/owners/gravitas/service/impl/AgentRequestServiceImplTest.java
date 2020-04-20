package com.owners.gravitas.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dao.AgentRequestDao;
import com.owners.gravitas.domain.Request;
import com.owners.gravitas.dto.response.PostResponse;

/**
 * The Class AgentRequestServiceImplTest.
 *
 * @author vishwanathm
 */
public class AgentRequestServiceImplTest extends AbstractBaseMockitoTest {

    /** The agent request service impl. */
    @InjectMocks
    private AgentRequestServiceImpl agentRequestServiceImpl;

    /** The request dao. */
    @Mock
    private AgentRequestDao agentRequestDao;

    /**
     * Test save agent requests.
     */
    @Test
    public void testSaveAgentRequests() {
        Mockito.when( agentRequestDao.saveAgentRequests( Mockito.anyMap(), Mockito.anyString() ) )
                .thenReturn( new PostResponse() );
        agentRequestServiceImpl.saveAgentRequests( new HashMap<>(), "agentId" );
        Mockito.verify( agentRequestDao ).saveAgentRequests( Mockito.anyMap(), Mockito.anyString() );

    }

    /**
     * Test save request.
     */
    @Test
    public void testSaveRequest() {
        Mockito.when( agentRequestDao.saveAgentRequest( Mockito.any( Request.class ), Mockito.anyString() ) )
                .thenReturn( new PostResponse() );
        agentRequestServiceImpl.saveRequest( "agentId", new Request() );
        Mockito.verify( agentRequestDao ).saveAgentRequest( Mockito.any( Request.class ), Mockito.anyString() );
    }

    /**
     * Test get buyer request.
     */
    @Test
    public void testGetBuyerRequest() {
        Mockito.when( agentRequestDao.getBuyerRequest( Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( new Request() );
        final Request request = agentRequestServiceImpl.getBuyerRequest( "agentId", "requestId" );
        Assert.assertNotNull( request );
        Mockito.verify( agentRequestDao ).getBuyerRequest( Mockito.anyString(), Mockito.anyString() );
    }

    /**
     * Test get requests by opportunity id.
     */
    @Test
    public void testGetRequestsByOpportunityId() {
        Mockito.when( agentRequestDao.getRequestsByOpportunityId( Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( new HashMap<>() );
        final Map< String, Request > map = agentRequestServiceImpl.getRequestsByOpportunityId( "testID", "testID" );
        Assert.assertNotNull( map );
        Mockito.verify( agentRequestDao ).getRequestsByOpportunityId( Mockito.anyString(), Mockito.anyString() );
    }

    /**
     * Test patch agent request.
     */
    @Test
    public void testPatchAgentRequest() {
        Mockito.when( agentRequestDao.patchAgentRequest( Mockito.anyString(), Mockito.anyString(), Mockito.anyMap() ) )
                .thenReturn( new Request() );
        final Request request = agentRequestServiceImpl.patchAgentRequest( "requestId", "agentId", new HashMap<>() );
        Assert.assertNotNull( request );
        Mockito.verify( agentRequestDao ).patchAgentRequest( Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMap() );
    }

}
