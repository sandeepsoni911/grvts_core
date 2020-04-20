package com.owners.gravitas.service.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.repository.AgentCoverageRepository;

/**
 * The Class AgentCoverageServiceImplTest.
 *
 * @author amits
 */
public class AgentCoverageServiceImplTest extends AbstractBaseMockitoTest {

    /** The agent coverage service impl. */
    @InjectMocks
    private AgentCoverageServiceImpl agentCoverageServiceImpl;

    /** The agent coverage repository. */
    @Mock
    AgentCoverageRepository agentCoverageRepository;

    /**
     * Test update servable zip code.
     */
    @Test
    public void testUpdateServableZipCode() {
        final AgentDetails agent = new AgentDetails();
        agentCoverageServiceImpl.updateServableZipCode( agent, "test", true );
        Mockito.verify( agentCoverageRepository ).updateServableZip( agent, "test", true );
    }

    /**
     * Test update servable zip code for all agents.
     */
    @Test
    public void testUpdateServableZipCodeForAllAgents() {
        agentCoverageServiceImpl.updateServableZipCodeForAllAgents( "test", true );
        Mockito.verify( agentCoverageRepository ).updateServableZipForAllAgents( "test", true );
    }
}
