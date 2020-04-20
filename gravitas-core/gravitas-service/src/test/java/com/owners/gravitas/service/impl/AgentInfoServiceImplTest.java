package com.owners.gravitas.service.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dao.AgentInfoDao;
import com.owners.gravitas.domain.AgentInfo;
import com.owners.gravitas.exception.AgentNotFoundException;

/**
 * The Class AgentInfoServiceImplTest.
 *
 * @author vishwanathm
 */
public class AgentInfoServiceImplTest extends AbstractBaseMockitoTest {

    /** The agent contact service impl. */
    @InjectMocks
    private AgentInfoServiceImpl agentInfoServiceImpl;

    /** The agent info dao. */
    @Mock
    private AgentInfoDao agentInfoDao;

    /**
     * Test save contact.
     */
    @Test
    public void testPatchAgentInfo() {
        Mockito.when( agentInfoDao.patchAgentInfo( Mockito.anyString(), Mockito.any( AgentInfo.class ) ) )
                .thenReturn( new AgentInfo() );
        final AgentInfo res = agentInfoServiceImpl.patchAgentInfo( "123123", new AgentInfo() );
        Assert.assertNotNull( res );
    }

    /**
     * Test update contact.
     */
    @Test
    public void testGetAgentInfo() {
        Mockito.when( agentInfoDao.getAgentInfo( Mockito.anyString() ) ).thenReturn( new AgentInfo() );
        AgentInfo info = agentInfoServiceImpl.getAgentInfo( "123123" );
        Assert.assertNotNull( info );
    }

    /**
     * Test get agent info exception.
     */
    @Test( expectedExceptions = AgentNotFoundException.class )
    public void testGetAgentInfoException() {
        Mockito.when( agentInfoDao.getAgentInfo( Mockito.anyString() ) ).thenReturn( null );
        agentInfoServiceImpl.getAgentInfo( "123123" );
    }
}
