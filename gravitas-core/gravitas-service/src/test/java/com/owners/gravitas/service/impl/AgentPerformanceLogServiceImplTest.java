package com.owners.gravitas.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.AgentPerformanceLog;
import com.owners.gravitas.repository.AgentPerformanceLogRepository;

/**
 * The Class AgentPerformanceLogServiceImplTest.
 *
 * @author ankusht
 */
public class AgentPerformanceLogServiceImplTest extends AbstractBaseMockitoTest {

    /** The agent performance metrics repository. */
    @Mock
    private AgentPerformanceLogRepository agentPerformanceLogRepository;

    /** The agent performance metrics service impl. */
    @InjectMocks
    private AgentPerformanceLogServiceImpl agentPerformanceMetricsServiceImpl;

    /**
     * Test save performance log for valid input.
     */
    @Test
    public void testSavePerformanceLogForValidInput() {
        AgentPerformanceLog agentPerformanceLog = new AgentPerformanceLog();
        AgentPerformanceLog newAgentPerformanceLog = new AgentPerformanceLog();
        when( agentPerformanceLogRepository.save( agentPerformanceLog ) ).thenReturn( newAgentPerformanceLog );
        AgentPerformanceLog retVal = agentPerformanceMetricsServiceImpl.savePerformanceLog( agentPerformanceLog );
        assertEquals( newAgentPerformanceLog, retVal );
        verify( agentPerformanceLogRepository ).save( agentPerformanceLog );
    }

    @Test
    public void testFindLatestMetricByAgentFbIdForValidInput() {
        String agentId = "id";
        Pageable pageable = new PageRequest( 1, 1 );
        Page< AgentPerformanceLog > page = new PageImpl< AgentPerformanceLog >( new ArrayList<>() );
        List< AgentPerformanceLog > expected = page.getContent();
        when( agentPerformanceLogRepository.findLatestMetricByAgentFbId( agentId, pageable ) ).thenReturn( page );
        List< AgentPerformanceLog > actual = agentPerformanceMetricsServiceImpl.findLatestMetricByAgentFbId( agentId,
                pageable );
        assertEquals( expected, actual );
        verify( agentPerformanceLogRepository ).findLatestMetricByAgentFbId( agentId, pageable );
    }

}
