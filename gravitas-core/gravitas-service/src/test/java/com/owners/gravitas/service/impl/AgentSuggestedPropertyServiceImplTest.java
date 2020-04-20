package com.owners.gravitas.service.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dao.AgentSuggestedPropertyDao;
import com.owners.gravitas.dto.request.AgentSuggestedPropertyRequest;
import com.owners.gravitas.dto.response.PostResponse;

// TODO: Auto-generated Javadoc
/**
 * The Class AgentSuggestedPropertyServiceImplTest.
 *
 * @author javeedsy
 */
public class AgentSuggestedPropertyServiceImplTest extends AbstractBaseMockitoTest {

    /** The agent task service impl. */
    @InjectMocks
    private AgentSuggestedPropertyServiceImpl agentSuggestedPropertyServiceImpl;

    /** The agent Suggested Property dao. */
    @Mock
    private AgentSuggestedPropertyDao agentSuggestedPropertyDao;

    /**
     * Test save agent suggested property.
     */
    @Test
    public void testSaveAgentSuggestedProperty() {
        final PostResponse ps = new PostResponse();
        ps.setName( "name" );
        Mockito.when( agentSuggestedPropertyDao
                .saveAgentSuggestedProperty( Mockito.any( AgentSuggestedPropertyRequest.class ), Mockito.anyString() ) )
                .thenReturn( ps );
        final PostResponse resp = agentSuggestedPropertyServiceImpl
                .saveAgentSuggestedProperty( new AgentSuggestedPropertyRequest(), "agentId" );
        Mockito.verify( agentSuggestedPropertyDao )
                .saveAgentSuggestedProperty( Mockito.any( AgentSuggestedPropertyRequest.class ), Mockito.anyString() );
        Assert.assertNotNull( resp );
        Assert.assertNotNull( resp.getName() );
    }

}
