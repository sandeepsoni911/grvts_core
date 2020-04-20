package com.owners.gravitas.service.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dao.AgentRequestDao;
import com.owners.gravitas.domain.Request;
import com.owners.gravitas.dto.response.PostResponse;

/**
 * The Class RequestServiceImplTest.
 *
 * @author vishwanathm
 */
public class RequestServiceImplTest extends AbstractBaseMockitoTest {

    /** The request service impl. */
    @InjectMocks
    private AgentRequestServiceImpl requestServiceImpl;

    /** The request dao. */
    @Mock
    private AgentRequestDao requestDao;

    /**
     * Test save request.
     */
    @Test
    public void testSaveRequest() {
        Mockito.when( requestDao.saveAgentRequest( Mockito.any( Request.class ), Mockito.anyString() )).thenReturn( new PostResponse() );
        requestServiceImpl.saveRequest( "agentId", new Request() );
        Mockito.verify( requestDao ).saveAgentRequest( Mockito.any( Request.class ), Mockito.anyString() );
    }
}
