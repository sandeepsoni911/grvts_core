package com.owners.gravitas.service.impl;

import static java.lang.Boolean.TRUE;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.AgentAvailabilityLog;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.repository.AgentAvailabilityLogRepository;

/**
 * The Class AgentAvailabilityLogServiceImplTest.
 * 
 * @author pabhishek
 */
public class AgentAvailabilityLogServiceImplTest extends AbstractBaseMockitoTest {

    /** The agent availability log service impl. */
    @InjectMocks
    private AgentAvailabilityLogServiceImpl agentAvailabilityLogServiceImpl;

    /** The agent availability log repository. */
    @Mock
    private AgentAvailabilityLogRepository agentAvailabilityLogRepository;

    /**
     * Test save log.
     */
    @Test
    public void testSaveLog() {
        final AgentAvailabilityLog agentAvailabilityLog = new AgentAvailabilityLog();
        final AgentAvailabilityLog expectedAgentAvailabilityLog = new AgentAvailabilityLog();

        when( agentAvailabilityLogRepository.save( agentAvailabilityLog ) ).thenReturn( expectedAgentAvailabilityLog );

        final AgentAvailabilityLog actualAgentAvailabilityLog = agentAvailabilityLogServiceImpl
                .saveLog( agentAvailabilityLog );

        assertEquals( actualAgentAvailabilityLog, expectedAgentAvailabilityLog );
        verify( agentAvailabilityLogRepository ).save( agentAvailabilityLog );
    }

    /**
     * Test get log.
     */
    @Test
    public void testGetLog() {
        final AgentDetails agentDetails = new AgentDetails();
        final boolean inProcess = TRUE;
        final AgentAvailabilityLog expectedAgentAvailabilityLog = new AgentAvailabilityLog();

        when( agentAvailabilityLogRepository.findByAgentDetailsAndInProcess( agentDetails, inProcess ) )
                .thenReturn( expectedAgentAvailabilityLog );

        final AgentAvailabilityLog actualAgentAvailabilityLog = agentAvailabilityLogServiceImpl.getLog( agentDetails,
                inProcess );

        assertEquals( actualAgentAvailabilityLog, expectedAgentAvailabilityLog );
        verify( agentAvailabilityLogRepository ).findByAgentDetailsAndInProcess( agentDetails, inProcess );
    }
}
