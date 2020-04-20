package com.owners.gravitas.delegate;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.flowable.engine.delegate.DelegateExecution;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.business.AgentBusinessService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;

/**
 * The Class AgentAvailabilityServiceTaskTest.
 * 
 * @author pabhishek
 */
public class AgentAvailabilityServiceTaskTest extends AbstractBaseMockitoTest {

    /** The agent availability service task. */
    @InjectMocks
    private AgentAvailabilityServiceTask agentAvailabilityServiceTask;

    /** The agent business service. */
    @Mock
    private AgentBusinessService agentBusinessService;

    /** The execution. */
    @Mock
    private DelegateExecution execution;

    /**
     * Test handle start off duty.
     */
    @Test
    public void testHandleStartOffDuty() {
        final String executionId = "testexecutionid";

        when( execution.getId() ).thenReturn( executionId );
        doNothing().when( agentBusinessService ).processAgentOffDuty( executionId );

        agentAvailabilityServiceTask.handleStartOffDuty( execution );

        verify( agentBusinessService ).processAgentOffDuty( executionId );
    }

    /**
     * Test handle end off duty.
     */
    @Test
    public void testHandleEndOffDuty() {
        final String executionId = "testexecutionid";

        when( execution.getId() ).thenReturn( executionId );
        doNothing().when( agentBusinessService ).processAgentOnDuty( executionId );

        agentAvailabilityServiceTask.handleEndOffDuty( execution );

        verify( agentBusinessService ).processAgentOnDuty( executionId );
    }
}
