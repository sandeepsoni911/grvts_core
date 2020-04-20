package com.owners.gravitas.business.scheduler;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.Test;

import com.owners.gravitas.business.AgentReportsBusinessService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.AgentReportingJobsJmxConfig;
import com.owners.gravitas.lock.AgentReportingSchedularLockHandler;

/**
 * The Class AgentReportingJobExecutorTest.
 *
 * @author ankusht
 */
public class AgentReportingJobExecutorTest extends AbstractBaseMockitoTest {

    /** The job executor. */
    @InjectMocks
    private AgentReportingJobExecutor agentReportingJobExecutor;

    /** The agent reporting jobs jmx config. */
    @Mock
    private AgentReportingJobsJmxConfig agentReportingJobsJmxConfig;

    /** The lock handler. */
    @Mock
    private AgentReportingSchedularLockHandler lockHandler;

    /** The agent reports business service. */
    @Mock
    private AgentReportsBusinessService agentReportsBusinessService;

    /**
     * Test execute agent performance analytics job.
     */
    @Test
    public void testExecuteAgentPerformanceAnalyticsJob() {
        when( agentReportingJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( agentReportingJobsJmxConfig.isEnableAgentsScoreAnalyticsJob() ).thenReturn( true );
        when( lockHandler.acquireLock( ReflectionTestUtils
                .getField( agentReportingJobExecutor, "AGENT_PERFORMANCE_JOB_LOCK_KEY" ).toString() ) )
                        .thenReturn( true );
        doNothing().when( agentReportsBusinessService ).performScoreAnalytics();

        agentReportingJobExecutor.executeAgentPerformanceAnalyticsJob();

        verify( agentReportingJobsJmxConfig ).isEnableAllJobs();
        verify( agentReportingJobsJmxConfig ).isEnableAgentsScoreAnalyticsJob();
        verify( lockHandler ).acquireLock( ReflectionTestUtils
                .getField( agentReportingJobExecutor, "AGENT_PERFORMANCE_JOB_LOCK_KEY" ).toString() );
        verify( lockHandler ).releaseLock( ReflectionTestUtils
                .getField( agentReportingJobExecutor, "AGENT_PERFORMANCE_JOB_LOCK_KEY" ).toString() );
    }

    /**
     * Test execute agent performance analytics job when exception occurs.
     */
    @Test
    public void testExecuteAgentPerformanceAnalyticsJobWhenExceptionOccurs() {
        when( agentReportingJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( agentReportingJobsJmxConfig.isEnableAgentsScoreAnalyticsJob() ).thenReturn( true );
        when( lockHandler.acquireLock( ReflectionTestUtils
                .getField( agentReportingJobExecutor, "AGENT_PERFORMANCE_JOB_LOCK_KEY" ).toString() ) )
                        .thenReturn( true );
        doThrow( new RuntimeException() ).when( agentReportsBusinessService ).performScoreAnalytics();

        agentReportingJobExecutor.executeAgentPerformanceAnalyticsJob();

        verify( agentReportingJobsJmxConfig ).isEnableAllJobs();
        verify( agentReportingJobsJmxConfig ).isEnableAgentsScoreAnalyticsJob();
        verify( lockHandler ).acquireLock( ReflectionTestUtils
                .getField( agentReportingJobExecutor, "AGENT_PERFORMANCE_JOB_LOCK_KEY" ).toString() );
        verify( lockHandler ).releaseLock( ReflectionTestUtils
                .getField( agentReportingJobExecutor, "AGENT_PERFORMANCE_JOB_LOCK_KEY" ).toString() );
    }

    /**
     * Test execute agent performance analytics job when all jobs disabled.
     */
    @Test
    public void testExecuteAgentPerformanceAnalyticsJobWhenAllJobsDisabled() {
        when( agentReportingJobsJmxConfig.isEnableAllJobs() ).thenReturn( false );
        agentReportingJobExecutor.executeAgentPerformanceAnalyticsJob();
        verify( agentReportingJobsJmxConfig ).isEnableAllJobs();
    }

    /**
     * Test execute agent performance analytics job when agents score analytics
     * job disabled.
     */
    @Test
    public void testExecuteAgentPerformanceAnalyticsJobWhenAgentsScoreAnalyticsJobDisabled() {
        when( agentReportingJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( agentReportingJobsJmxConfig.isEnableAgentsScoreAnalyticsJob() ).thenReturn( false );
        agentReportingJobExecutor.executeAgentPerformanceAnalyticsJob();
        verify( agentReportingJobsJmxConfig ).isEnableAllJobs();
    }

    /**
     * Test execute agent performance analytics job when lock is not acquired.
     */
    @Test
    public void testExecuteAgentPerformanceAnalyticsJobWhenLockIsNotAcquired() {
        when( agentReportingJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( agentReportingJobsJmxConfig.isEnableAgentsScoreAnalyticsJob() ).thenReturn( true );
        when( lockHandler.acquireLock( ReflectionTestUtils
                .getField( agentReportingJobExecutor, "AGENT_PERFORMANCE_JOB_LOCK_KEY" ).toString() ) )
                        .thenReturn( false );
        agentReportingJobExecutor.executeAgentPerformanceAnalyticsJob();
        verify( agentReportingJobsJmxConfig ).isEnableAllJobs();
    }

}
