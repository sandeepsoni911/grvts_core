package com.owners.gravitas.service.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.AgentTask;
import com.owners.gravitas.domain.entity.AgentTaskStatusLog;
import com.owners.gravitas.repository.AgentTaskStatusLogRepository;

/**
 * The Class AgentTaskStatusLogServiceImplTest.
 *
 * @author raviz
 */
public class AgentTaskStatusLogServiceImplTest extends AbstractBaseMockitoTest {

    /** The agent task status log service impl. */
    @InjectMocks
    private AgentTaskStatusLogServiceImpl agentTaskStatusLogServiceImpl;

    /** The agent task status log repository. */
    @Mock
    private AgentTaskStatusLogRepository agentTaskStatusLogRepository;

    /**
     * Test save.
     */
    @Test
    public void testSave() {
        final AgentTaskStatusLog expectedLog = new AgentTaskStatusLog();
        when( agentTaskStatusLogRepository.save( expectedLog ) ).thenReturn( expectedLog );
        final AgentTaskStatusLog actualLog = agentTaskStatusLogServiceImpl.save( expectedLog );
        assertEquals( actualLog, expectedLog );
        verify( agentTaskStatusLogRepository ).save( expectedLog );
    }

    /**
     * Test find top by agent task and status.
     */
    @Test
    public void testFindTopByAgentTaskAndStatus() {
        final AgentTask agentTask = new AgentTask();
        final String status = "test";
        final AgentTaskStatusLog expectedLog = new AgentTaskStatusLog();
        when( agentTaskStatusLogRepository.findTop1ByAgentTaskAndStatusNotOrderByCreatedDateDesc( agentTask, status ) )
                .thenReturn( expectedLog );
        final AgentTaskStatusLog actualLog = agentTaskStatusLogServiceImpl.findTopByAgentTaskAndStatus( agentTask,
                status );
        assertEquals( actualLog, expectedLog );
        verify( agentTaskStatusLogRepository ).findTop1ByAgentTaskAndStatusNotOrderByCreatedDateDesc( agentTask,
                status );
    }

}
