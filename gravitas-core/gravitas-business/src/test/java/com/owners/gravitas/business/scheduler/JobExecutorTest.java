package com.owners.gravitas.business.scheduler;

import static com.owners.gravitas.enums.JobType.GRAVITAS_HEALTH_CHECK_JOB;
import static com.owners.gravitas.enums.JobType.OPPORTUNITY_MAPPING_JOB;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.getField;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.Test;

import com.owners.gravitas.business.AgentAnalyicsBusinessService;
import com.owners.gravitas.business.AgentBusinessService;
import com.owners.gravitas.business.AgentReminderBusinessService;
import com.owners.gravitas.business.AgentReportsBusinessService;
import com.owners.gravitas.business.SystemHealthBusinessService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.AgentMetricsJmxConfig;
import com.owners.gravitas.config.SchedulerJobsJmxConfig;
import com.owners.gravitas.domain.entity.SchedulerLog;
import com.owners.gravitas.lock.AgentNewOppSchedulerLockHandler;
import com.owners.gravitas.service.ErrorLogService;
import com.owners.gravitas.service.SchedulerLogService;

/**
 * The Class JobExecutorTest.
 *
 * @author ankusht
 */
public class JobExecutorTest extends AbstractBaseMockitoTest {

    /** The lock handler. */
    @Mock
    private AgentNewOppSchedulerLockHandler lockHandler;

    /** The gravitas systems health business service. */
    @Mock
    private SystemHealthBusinessService gravitasSystemsHealthBusinessService;

    /** The agent business service. */
    @Mock
    private AgentReminderBusinessService agentReminderBusinessService;

    /** The agent performance metrics service. */
    @Mock
    private AgentReportsBusinessService agentReportsBusinessService;

    /** The agent performance metrics properties holder. */
    @Mock
    private AgentMetricsJmxConfig agentMetricsJmxConfig;

    /** The scheduler jobs jmx config. */
    @Mock
    private SchedulerJobsJmxConfig schedulerJobsJmxConfig;

    /** The scheduler log service. */
    @Mock
    private SchedulerLogService schedulerLogService;

    /** The job executor. */
    @InjectMocks
    private JobExecutor jobExecutor;

    /** The agent analyics business service. */
    @Mock
    private AgentAnalyicsBusinessService agentAnalyicsBusinessService;

    /** The error log service. */
    @Mock
    private ErrorLogService errorLogService;

    /** The agent business service. */
    @Mock
    private AgentBusinessService agentBusinessService;

    /**
     * Test notify agents new oppo should send reminder if enabled.
     */
    @Test
    public void testNotifyAgentsNewOppoShouldSendReminderIfEnabled() {
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( schedulerJobsJmxConfig.isEnableNewOppAgentReminder() ).thenReturn( true );
        when( lockHandler.acquireLock( "AGENT_NEW_OPP_SCHEDULAR_RUN" ) ).thenReturn( true );
        jobExecutor.notifyAgentsNewOppo();
        verify( agentReminderBusinessService ).sendNewOppReminder();
        verify( lockHandler ).acquireLock( "AGENT_NEW_OPP_SCHEDULAR_RUN" );
        verify( lockHandler ).releaseLock( "AGENT_NEW_OPP_SCHEDULAR_RUN" );
    }

    /**
     * Test notify agents new oppo should not send reminder if all jobs
     * disabled.
     */
    @Test
    public void testNotifyAgentsNewOppoShouldNotSendReminderIfAllJobsDisabled() {
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( false );
        jobExecutor.notifyAgentsNewOppo();
        verifyZeroInteractions( agentReminderBusinessService );
        verifyZeroInteractions( lockHandler );
    }

    /**
     * Test notify agents new oppo should not send reminder if new opp agent
     * reminder disabled.
     */
    @Test
    public void testNotifyAgentsNewOppoShouldNotSendReminderIfNewOppAgentReminderDisabled() {
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( schedulerJobsJmxConfig.isEnableNewOppAgentReminder() ).thenReturn( false );
        jobExecutor.notifyAgentsNewOppo();
        verifyZeroInteractions( agentReminderBusinessService );
        verifyZeroInteractions( lockHandler );
    }

    /**
     * Test notify agents new oppo should not send reminder if lock is not
     * acquired.
     */
    @Test
    public void testNotifyAgentsNewOppoShouldNotSendReminderIfLockIsNotAcquired() {
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( schedulerJobsJmxConfig.isEnableNewOppAgentReminder() ).thenReturn( true );
        when( lockHandler.acquireLock( "AGENT_NEW_OPP_SCHEDULAR_RUN" ) ).thenReturn( false );
        jobExecutor.notifyAgentsNewOppo();
        verifyZeroInteractions( agentReminderBusinessService );
    }

    /**
     * Test notify agents new oppo when interrupted exception thrown.
     */
    @Test
    public void testNotifyAgentsNewOppoWhenInterruptedExceptionThrown() {
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( schedulerJobsJmxConfig.isEnableNewOppAgentReminder() ).thenReturn( true );
        when( lockHandler.acquireLock( "AGENT_NEW_OPP_SCHEDULAR_RUN" ) ).thenReturn( true );
        doThrow( InterruptedException.class ).when( agentReminderBusinessService ).sendNewOppReminder();
        jobExecutor.notifyAgentsNewOppo();
        verify( agentReminderBusinessService ).sendNewOppReminder();
        verify( lockHandler ).acquireLock( "AGENT_NEW_OPP_SCHEDULAR_RUN" );
        verify( lockHandler ).releaseLock( "AGENT_NEW_OPP_SCHEDULAR_RUN" );
    }

    /**
     * Test notify agents claimed oppo should send reminder if enabled.
     */
    @Test
    public void testNotifyAgentsClaimedOppoShouldSendReminderIfEnabled() {
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( schedulerJobsJmxConfig.isEnableClaimedOppAgentReminder() ).thenReturn( true );
        when( lockHandler.acquireLock( "AGENT_CLAIMED_OPP_SCHEDULAR_RUN" ) ).thenReturn( true );
        jobExecutor.notifyAgentsClaimedOppo();
        verify( agentReminderBusinessService ).sendClaimedOppReminder();
        verify( lockHandler ).acquireLock( "AGENT_CLAIMED_OPP_SCHEDULAR_RUN" );
        verify( lockHandler ).releaseLock( "AGENT_CLAIMED_OPP_SCHEDULAR_RUN" );
    }

    /**
     * Test notify agents claimed oppo should not send reminder if all jobs
     * disabled.
     */
    @Test
    public void testNotifyAgentsClaimedOppoShouldNotSendReminderIfAllJobsDisabled() {
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( false );
        when( schedulerJobsJmxConfig.isEnableClaimedOppAgentReminder() ).thenReturn( true );
        when( lockHandler.acquireLock( "AGENT_CLAIMED_OPP_SCHEDULAR_RUN" ) ).thenReturn( true );
        jobExecutor.notifyAgentsClaimedOppo();
        verifyZeroInteractions( agentReminderBusinessService );
        verifyZeroInteractions( lockHandler );
    }

    /**
     * Test notify agents claimed oppo should not send reminder if claimed opp
     * agent reminder disabled.
     */
    @Test
    public void testNotifyAgentsClaimedOppoShouldNotSendReminderIfClaimedOppAgentReminderDisabled() {
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( schedulerJobsJmxConfig.isEnableClaimedOppAgentReminder() ).thenReturn( false );
        when( lockHandler.acquireLock( "AGENT_CLAIMED_OPP_SCHEDULAR_RUN" ) ).thenReturn( true );
        jobExecutor.notifyAgentsClaimedOppo();
        verifyZeroInteractions( agentReminderBusinessService );
        verifyZeroInteractions( lockHandler );
    }

    /**
     * Test notify agents claimed oppo should not send reminder if lock is not
     * acquired.
     */
    @Test
    public void testNotifyAgentsClaimedOppoShouldNotSendReminderIfLockIsNotAcquired() {
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( schedulerJobsJmxConfig.isEnableClaimedOppAgentReminder() ).thenReturn( true );
        when( lockHandler.acquireLock( "AGENT_CLAIMED_OPP_SCHEDULAR_RUN" ) ).thenReturn( false );
        jobExecutor.notifyAgentsClaimedOppo();
        verifyZeroInteractions( agentReminderBusinessService );
    }

    /**
     * Test notify agents claimed when interrupted exception thrown.
     */
    @Test
    public void testNotifyAgentsClaimedWhenInterruptedExceptionThrown() {
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( schedulerJobsJmxConfig.isEnableClaimedOppAgentReminder() ).thenReturn( true );
        when( lockHandler.acquireLock( "AGENT_CLAIMED_OPP_SCHEDULAR_RUN" ) ).thenReturn( true );
        doThrow( InterruptedException.class ).when( agentReminderBusinessService ).sendClaimedOppReminder();
        jobExecutor.notifyAgentsClaimedOppo();
        verify( agentReminderBusinessService ).sendClaimedOppReminder();
        verify( lockHandler ).acquireLock( "AGENT_CLAIMED_OPP_SCHEDULAR_RUN" );
        verify( lockHandler ).releaseLock( "AGENT_CLAIMED_OPP_SCHEDULAR_RUN" );
    }

    /**
     * Test evaluate agent performance metrics should run job if all enabled.
     */
    @Test
    public void testEvaluateAgentPerformanceMetricsShouldRunJobIfAllEnabled() {
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( agentMetricsJmxConfig.isAgentPerformanceMetricsJobEnabled() ).thenReturn( true );
        when( lockHandler.acquireLock( "AGENT_PERFORMANCE_METRICS_JOB_LOCK_KEY" ) ).thenReturn( true );
        jobExecutor.evaluateAgentPerformanceMetrics();
        verify( agentReportsBusinessService ).evaluateAgentPerformanceMetrics();
        verify( lockHandler ).acquireLock( "AGENT_PERFORMANCE_METRICS_JOB_LOCK_KEY" );
        verify( lockHandler ).releaseLock( "AGENT_PERFORMANCE_METRICS_JOB_LOCK_KEY" );
    }

    /**
     * Test evaluate agent performance metrics should not run job if all jobs
     * disabled.
     */
    @Test
    public void testEvaluateAgentPerformanceMetricsShouldNotRunJobIfAllJobsDisabled() {
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( false );
        jobExecutor.evaluateAgentPerformanceMetrics();
        verifyZeroInteractions( agentReportsBusinessService );
        verifyZeroInteractions( lockHandler );
    }

    /**
     * Test evaluate agent performance metrics should not run job if all jobs
     * agent performance metrics job disabled.
     */
    @Test
    public void testEvaluateAgentPerformanceMetricsShouldNotRunJobIfAllJobsAgentPerformanceMetricsJobDisabled() {
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( agentMetricsJmxConfig.isAgentPerformanceMetricsJobEnabled() ).thenReturn( false );
        jobExecutor.evaluateAgentPerformanceMetrics();
        verifyZeroInteractions( agentReportsBusinessService );
        verifyZeroInteractions( lockHandler );
    }

    /**
     * Test evaluate agent performance metrics should not run job if lock is not
     * acquired.
     */
    @Test
    public void testEvaluateAgentPerformanceMetricsShouldNotRunJobIfLockIsNotAcquired() {
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( agentMetricsJmxConfig.isAgentPerformanceMetricsJobEnabled() ).thenReturn( true );
        when( lockHandler.acquireLock( "AGENT_PERFORMANCE_METRICS_JOB_LOCK_KEY" ) ).thenReturn( false );
        jobExecutor.evaluateAgentPerformanceMetrics();
        verifyZeroInteractions( agentReportsBusinessService );
    }

    /**
     * Test evaluate agent performance metrics when interrupted exception
     * thrown.
     */
    @Test
    public void testEvaluateAgentPerformanceMetricsWhenInterruptedExceptionThrown() {
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( agentMetricsJmxConfig.isAgentPerformanceMetricsJobEnabled() ).thenReturn( true );
        when( lockHandler.acquireLock( "AGENT_PERFORMANCE_METRICS_JOB_LOCK_KEY" ) ).thenReturn( true );
        doThrow( InterruptedException.class ).when( agentReportsBusinessService ).evaluateAgentPerformanceMetrics();
        jobExecutor.evaluateAgentPerformanceMetrics();
        verify( agentReportsBusinessService ).evaluateAgentPerformanceMetrics();
        verify( lockHandler ).acquireLock( "AGENT_PERFORMANCE_METRICS_JOB_LOCK_KEY" );
        verify( lockHandler ).releaseLock( "AGENT_PERFORMANCE_METRICS_JOB_LOCK_KEY" );
    }

    /**
     * Test execute gravitas system health job should run job if all enabled.
     */
    @Test
    public void testExecuteGravitasSystemHealthJobShouldRunJobIfAllEnabled() {
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( schedulerJobsJmxConfig.isEnableSystemHealthCheckup() ).thenReturn( true );
        when( lockHandler.acquireLock( "GRAVITAS_SYSTEM_HEALTH_JOB_LOCK_KEY" ) ).thenReturn( true );
        final SchedulerLog schedulerLog = new SchedulerLog();
        when( schedulerLogService.startJob( GRAVITAS_HEALTH_CHECK_JOB ) ).thenReturn( schedulerLog );

        jobExecutor.executeGravitasSystemHealthJob();
        verify( schedulerLogService ).startJob( GRAVITAS_HEALTH_CHECK_JOB );
        verify( schedulerLogService ).endJob( schedulerLog );
        verify( lockHandler ).acquireLock( "GRAVITAS_SYSTEM_HEALTH_JOB_LOCK_KEY" );
        verify( lockHandler ).releaseLock( "GRAVITAS_SYSTEM_HEALTH_JOB_LOCK_KEY" );
    }

    /**
     * Test execute gravitas system health job should not run job if all jobs
     * disabled.
     */
    @Test
    public void testExecuteGravitasSystemHealthJobShouldNotRunJobIfAllJobsDisabled() {
        final String lockName = ( String ) ReflectionTestUtils.getField( jobExecutor,
                "GRAVITAS_SYSTEM_HEALTH_JOB_LOCK_KEY" );
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( false );
        when( lockHandler.acquireLock( lockName ) ).thenReturn( true );
        jobExecutor.executeGravitasSystemHealthJob();
        verifyZeroInteractions( schedulerLogService );
        verifyZeroInteractions( gravitasSystemsHealthBusinessService );
        verify( lockHandler ).acquireLock( lockName );
    }

    /**
     * Test execute gravitas system health job should not run job if system
     * health checkup disabled.
     */
    @Test
    public void testExecuteGravitasSystemHealthJobShouldNotRunJobIfSystemHealthCheckupDisabled() {
        final String lockName = ( String ) ReflectionTestUtils.getField( jobExecutor,
                "GRAVITAS_SYSTEM_HEALTH_JOB_LOCK_KEY" );
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( lockHandler.acquireLock( lockName ) ).thenReturn( true );
        when( schedulerJobsJmxConfig.isEnableSystemHealthCheckup() ).thenReturn( false );
        jobExecutor.executeGravitasSystemHealthJob();
        verifyZeroInteractions( schedulerLogService );
        verifyZeroInteractions( gravitasSystemsHealthBusinessService );
        verify( lockHandler ).acquireLock( lockName );
    }

    /**
     * Test execute gravitas system health job should not run job if lock is not
     * acquired.
     */
    @Test
    public void testExecuteGravitasSystemHealthJobShouldNotRunJobIfLockIsNotAcquired() {
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( schedulerJobsJmxConfig.isEnableSystemHealthCheckup() ).thenReturn( true );
        when( lockHandler.acquireLock( "GRAVITAS_SYSTEM_HEALTH_JOB_LOCK_KEY" ) ).thenReturn( false );
        jobExecutor.executeGravitasSystemHealthJob();
        verifyZeroInteractions( schedulerLogService );
        verifyZeroInteractions( gravitasSystemsHealthBusinessService );
    }

    /**
     * Test execute gravitas system health job when interrupted exception
     * thrown.
     */
    @Test
    public void testExecuteGravitasSystemHealthJobWhenInterruptedExceptionThrown() {
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( schedulerJobsJmxConfig.isEnableSystemHealthCheckup() ).thenReturn( true );
        when( lockHandler.acquireLock( "GRAVITAS_SYSTEM_HEALTH_JOB_LOCK_KEY" ) ).thenReturn( true );
        final SchedulerLog schedulerLog = new SchedulerLog();
        when( schedulerLogService.startJob( GRAVITAS_HEALTH_CHECK_JOB ) ).thenReturn( schedulerLog );
        doThrow( InterruptedException.class ).when( gravitasSystemsHealthBusinessService )
                .checkSystemHealth( schedulerLog );

        jobExecutor.executeGravitasSystemHealthJob();
        verify( schedulerLogService ).startJob( GRAVITAS_HEALTH_CHECK_JOB );
        verify( schedulerLogService ).endJob( schedulerLog );
        verify( gravitasSystemsHealthBusinessService ).checkSystemHealth( schedulerLog );
        verify( lockHandler ).acquireLock( "GRAVITAS_SYSTEM_HEALTH_JOB_LOCK_KEY" );
        verify( lockHandler ).releaseLock( "GRAVITAS_SYSTEM_HEALTH_JOB_LOCK_KEY" );
    }

    /**
     * Test execute gravitas system health job should run job if all enabled.
     */
    @Test
    public void testExecuteAgentAnalyticsJob() {
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( schedulerJobsJmxConfig.isEnableAgentAnalytics() ).thenReturn( true );
        when( lockHandler.acquireLock( "AGENT_ANALYTICS_JOB_LOCK_KEY" ) ).thenReturn( true );
        Mockito.doNothing().when( agentAnalyicsBusinessService ).startAgentAnalytics();
        Mockito.doNothing().when( agentAnalyicsBusinessService ).opportunityMappingAnalytics();

        jobExecutor.executeAgentAnalyticsJob();
        verify( agentAnalyicsBusinessService ).startAgentAnalytics();
        verify( lockHandler ).acquireLock( "AGENT_ANALYTICS_JOB_LOCK_KEY" );
        verify( lockHandler ).releaseLock( "AGENT_ANALYTICS_JOB_LOCK_KEY" );
    }

    /**
     * Test execute gravitas system health job should not run job if all jobs
     * disabled.
     */
    @Test
    public void testExecuteAgentAnalyticsShouldNotRunJobIfAllJobsDisabled() {
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( false );
        jobExecutor.executeAgentAnalyticsJob();
        verifyZeroInteractions( agentAnalyicsBusinessService );
    }

    /**
     * Test execute gravitas system health job should not run job if system
     * health checkup disabled.
     */
    @Test
    public void testExecuteAgentAnalyticsShouldNotRunJobIfAnalyticsDisabled() {
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( schedulerJobsJmxConfig.isEnableAgentAnalytics() ).thenReturn( false );
        jobExecutor.executeAgentAnalyticsJob();
        verifyZeroInteractions( agentAnalyicsBusinessService );
    }

    /**
     * Test execute gravitas system health job should not run job if lock is not
     * acquired.
     */
    @Test
    public void testExecuteAgentAnalyticsShouldNotRunJobIfLockIsNotAcquired() {
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( schedulerJobsJmxConfig.isEnableAgentAnalytics() ).thenReturn( true );
        when( lockHandler.acquireLock( "AGENT_ANALYTICS_JOB_LOCK_KEY" ) ).thenReturn( false );
        jobExecutor.executeAgentAnalyticsJob();
        verifyZeroInteractions( agentAnalyicsBusinessService );
    }

    /**
     * Test execute agent analytics job when exception thrown.
     */
    @Test
    public void testExecuteAgentAnalyticsJobWhenExceptionThrown() {
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( schedulerJobsJmxConfig.isEnableAgentAnalytics() ).thenReturn( true );
        when( lockHandler.acquireLock( "AGENT_ANALYTICS_JOB_LOCK_KEY" ) ).thenReturn( true );
        doNothing().when( agentAnalyicsBusinessService ).startAgentAnalytics();
        doNothing().when( agentAnalyicsBusinessService ).opportunityMappingAnalytics();
        doThrow( InterruptedException.class ).when( agentAnalyicsBusinessService ).startAgentAnalytics();

        jobExecutor.executeAgentAnalyticsJob();
        verify( agentAnalyicsBusinessService ).startAgentAnalytics();
        verify( lockHandler ).acquireLock( "AGENT_ANALYTICS_JOB_LOCK_KEY" );
        verify( lockHandler ).releaseLock( "AGENT_ANALYTICS_JOB_LOCK_KEY" );
    }

    /**
     * Test execute opportunity mapping job.
     */
    @Test
    public void testExecuteOpportunityMappingJob() {
        final String lockKey = ( String ) getField( jobExecutor, "OPPORTUNITY_MAPPING_JOB_LOCK_KEY" );
        final SchedulerLog schedulerLog = new SchedulerLog();

        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( schedulerJobsJmxConfig.isEnableOpportunityMappingAnalytics() ).thenReturn( true );
        when( lockHandler.acquireLock( lockKey ) ).thenReturn( true );
        when( schedulerLogService.startJob( OPPORTUNITY_MAPPING_JOB ) ).thenReturn( schedulerLog );
        doNothing().when( agentAnalyicsBusinessService ).opportunityMappingAnalytics();
        doNothing().when( schedulerLogService ).endJob( schedulerLog );
        doNothing().when( lockHandler ).releaseLock( lockKey );

        jobExecutor.executeOpportunityMappingJob();

        verify( schedulerJobsJmxConfig ).isEnableAllJobs();
        verify( schedulerJobsJmxConfig ).isEnableOpportunityMappingAnalytics();
        verify( lockHandler ).acquireLock( lockKey );
        verify( schedulerLogService ).startJob( OPPORTUNITY_MAPPING_JOB );
        verify( agentAnalyicsBusinessService ).opportunityMappingAnalytics();
        verify( schedulerLogService ).endJob( schedulerLog );
        verify( lockHandler ).releaseLock( lockKey );
    }

    /**
     * Test execute opportunity mapping job when all job are disabled.
     */
    @Test
    public void testExecuteOpportunityMappingJobWhenAllJobAreDisabled() {
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( false );
        jobExecutor.executeOpportunityMappingJob();
        verify( schedulerJobsJmxConfig ).isEnableAllJobs();
        verifyZeroInteractions( schedulerJobsJmxConfig );
        verifyZeroInteractions( lockHandler );
        verifyZeroInteractions( schedulerLogService );
        verifyZeroInteractions( agentAnalyicsBusinessService );
    }

    /**
     * Test execute opportunity mapping job when opportunity mapping analytics
     * disabled.
     */
    @Test
    public void testExecuteOpportunityMappingJobWhenOpportunityMappingAnalyticsDisabled() {
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( schedulerJobsJmxConfig.isEnableOpportunityMappingAnalytics() ).thenReturn( false );

        jobExecutor.executeOpportunityMappingJob();

        verify( schedulerJobsJmxConfig ).isEnableAllJobs();
        verify( schedulerJobsJmxConfig ).isEnableOpportunityMappingAnalytics();
        verifyZeroInteractions( lockHandler );
        verifyZeroInteractions( schedulerLogService );
        verifyZeroInteractions( agentAnalyicsBusinessService );
    }

    /**
     * Test execute opportunity mapping job when lock not acquired.
     */
    @Test
    public void testExecuteOpportunityMappingJobWhenLockNotAcquired() {
        final String lockKey = ( String ) getField( jobExecutor, "OPPORTUNITY_MAPPING_JOB_LOCK_KEY" );

        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( schedulerJobsJmxConfig.isEnableOpportunityMappingAnalytics() ).thenReturn( true );
        when( lockHandler.acquireLock( lockKey ) ).thenReturn( false );

        jobExecutor.executeOpportunityMappingJob();

        verify( schedulerJobsJmxConfig ).isEnableAllJobs();
        verify( schedulerJobsJmxConfig ).isEnableOpportunityMappingAnalytics();
        verify( lockHandler ).acquireLock( lockKey );
        verifyZeroInteractions( schedulerLogService );
        verifyZeroInteractions( agentAnalyicsBusinessService );
    }

    /**
     * Test execute opportunity mapping job when exception is thrown.
     */
    @Test
    public void testExecuteOpportunityMappingJobWhenExceptionIsThrown() {
        final String lockKey = ( String ) getField( jobExecutor, "OPPORTUNITY_MAPPING_JOB_LOCK_KEY" );
        final SchedulerLog schedulerLog = null;

        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( schedulerJobsJmxConfig.isEnableOpportunityMappingAnalytics() ).thenReturn( true );
        when( lockHandler.acquireLock( lockKey ) ).thenReturn( true );
        when( schedulerLogService.startJob( OPPORTUNITY_MAPPING_JOB ) ).thenThrow( Exception.class );
        doNothing().when( schedulerLogService ).endJob( schedulerLog );
        doNothing().when( lockHandler ).releaseLock( lockKey );

        jobExecutor.executeOpportunityMappingJob();

        verify( schedulerJobsJmxConfig ).isEnableAllJobs();
        verify( schedulerJobsJmxConfig ).isEnableOpportunityMappingAnalytics();
        verify( lockHandler ).acquireLock( lockKey );
        verify( schedulerLogService ).startJob( OPPORTUNITY_MAPPING_JOB );
        verifyZeroInteractions( agentAnalyicsBusinessService );
        verify( schedulerLogService ).endJob( schedulerLog );
        verify( lockHandler ).releaseLock( lockKey );
    }

    /**
     * Test execute analytics.
     */
    @Test
    public void testExecuteAnalytics() {
        final String agentAnalyticslockKey = ( String ) getField( jobExecutor, "AGENT_ANALYTICS_JOB_LOCK_KEY" );
        final String OpportunityMappinglockKey = ( String ) getField( jobExecutor, "OPPORTUNITY_MAPPING_JOB_LOCK_KEY" );
        final SchedulerLog schedulerLog = new SchedulerLog();

        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( schedulerJobsJmxConfig.isEnableAgentAnalytics() ).thenReturn( true );
        when( lockHandler.acquireLock( agentAnalyticslockKey ) ).thenReturn( true );
        doNothing().when( agentAnalyicsBusinessService ).startAgentAnalytics();
        doNothing().when( lockHandler ).releaseLock( agentAnalyticslockKey );
        when( schedulerJobsJmxConfig.isEnableOpportunityMappingAnalytics() ).thenReturn( true );
        when( lockHandler.acquireLock( OpportunityMappinglockKey ) ).thenReturn( true );
        when( schedulerLogService.startJob( OPPORTUNITY_MAPPING_JOB ) ).thenReturn( schedulerLog );
        doNothing().when( agentAnalyicsBusinessService ).opportunityMappingAnalytics();
        doNothing().when( schedulerLogService ).endJob( schedulerLog );
        doNothing().when( lockHandler ).releaseLock( OpportunityMappinglockKey );

        jobExecutor.executeAnalytics();

        verify( schedulerJobsJmxConfig, times( 3 ) ).isEnableAllJobs();
        verify( schedulerJobsJmxConfig ).isEnableAgentAnalytics();
        verify( lockHandler ).acquireLock( agentAnalyticslockKey );
        verify( agentAnalyicsBusinessService ).startAgentAnalytics();
        verify( lockHandler ).releaseLock( agentAnalyticslockKey );
        verify( schedulerJobsJmxConfig ).isEnableOpportunityMappingAnalytics();
        verify( lockHandler ).acquireLock( OpportunityMappinglockKey );
        verify( schedulerLogService ).startJob( OPPORTUNITY_MAPPING_JOB );
        verify( agentAnalyicsBusinessService ).opportunityMappingAnalytics();
        verify( schedulerLogService ).endJob( schedulerLog );
        verify( lockHandler ).releaseLock( OpportunityMappinglockKey );
    }

    /**
     * Test execute slack error log analytics job.
     */
    @Test
    public void testExecuteSlackErrorLogAnalyticsJob() {
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( schedulerJobsJmxConfig.isErrorLogAnalytics() ).thenReturn( true );
        doNothing().when( errorLogService ).publishErrorReportOnSlack();

        jobExecutor.executeSlackErrorLogAnalyticsJob();

        verify( schedulerJobsJmxConfig ).isEnableAllJobs();
        verify( schedulerJobsJmxConfig ).isErrorLogAnalytics();
        verify( errorLogService ).publishErrorReportOnSlack();
    }

    /**
     * Test sync agent scores.
     */
    @Test
    public void testSyncAgentScores() {
        ReflectionTestUtils.setField( jobExecutor, "scoreJobEnabled", true );
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( lockHandler.acquireLock( anyString() ) ).thenReturn( true );
        doNothing().when( agentBusinessService ).syncAgentScore();

        jobExecutor.syncAgentScores();

        verify( schedulerJobsJmxConfig ).isEnableAllJobs();
        verify( lockHandler ).acquireLock( anyString() );
        verify( agentBusinessService ).syncAgentScore();

    }

    /**
     * Test notify agents action flow incomplete morning.
     */
    @Test
    public void testNotifyAgentsActionFlowIncompleteMorning() {
        final String lockKey = ( String ) getField( jobExecutor, "AGENT_ACTION_FLOW_INCOMPLETE_REMINDER_LOCK_KEY" );
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( schedulerJobsJmxConfig.isEnableAgentActionIncompleteReminder() ).thenReturn( true );
        when( lockHandler.acquireLock( lockKey ) ).thenReturn( true );
        doNothing().when( agentReminderBusinessService ).sendActionFlowIncompleteReminder();
        doNothing().when( lockHandler ).releaseLock( lockKey );

        jobExecutor.notifyAgentsActionFlowIncompleteMorning();

        verify( schedulerJobsJmxConfig ).isEnableAllJobs();
        verify( schedulerJobsJmxConfig ).isEnableAgentActionIncompleteReminder();
        verify( lockHandler ).acquireLock( lockKey );
        verify( agentReminderBusinessService ).sendActionFlowIncompleteReminder();
        verify( lockHandler ).releaseLock( lockKey );
    }

    /**
     * Test notify agents action flow incomplete morning when disable all jobs.
     */
    @Test
    public void testNotifyAgentsActionFlowIncompleteMorningWhenDisableAllJobs() {
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( false );

        jobExecutor.notifyAgentsActionFlowIncompleteMorning();

        verify( schedulerJobsJmxConfig ).isEnableAllJobs();
        verifyZeroInteractions( schedulerJobsJmxConfig );
        verifyZeroInteractions( lockHandler );
        verifyZeroInteractions( agentReminderBusinessService );
        verifyZeroInteractions( lockHandler );
    }

    /**
     * Test notify agents action flow incomplete morning when disabled action
     * incomplete reminder job.
     */
    @Test
    public void testNotifyAgentsActionFlowIncompleteMorningWhenDisabledActionIncompleteReminderJob() {
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( schedulerJobsJmxConfig.isEnableAgentActionIncompleteReminder() ).thenReturn( false );

        jobExecutor.notifyAgentsActionFlowIncompleteMorning();

        verify( schedulerJobsJmxConfig ).isEnableAllJobs();
        verify( schedulerJobsJmxConfig ).isEnableAgentActionIncompleteReminder();
        verifyZeroInteractions( lockHandler );
        verifyZeroInteractions( agentReminderBusinessService );
        verifyZeroInteractions( lockHandler );
    }

    /**
     * Test notify agents action flow incomplete morning when lock is not
     * acquired.
     */
    @Test
    public void testNotifyAgentsActionFlowIncompleteMorningWhenLockIsNotAcquired() {
        final String lockKey = ( String ) getField( jobExecutor, "AGENT_ACTION_FLOW_INCOMPLETE_REMINDER_LOCK_KEY" );
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( schedulerJobsJmxConfig.isEnableAgentActionIncompleteReminder() ).thenReturn( true );
        when( lockHandler.acquireLock( lockKey ) ).thenReturn( false );

        jobExecutor.notifyAgentsActionFlowIncompleteMorning();

        verify( schedulerJobsJmxConfig ).isEnableAllJobs();
        verify( schedulerJobsJmxConfig ).isEnableAgentActionIncompleteReminder();
        verify( lockHandler ).acquireLock( lockKey );
        verifyZeroInteractions( agentReminderBusinessService );
        verifyZeroInteractions( lockHandler );
    }

    /**
     * Test notify agents action flow incomplete afternoon.
     */
    @Test
    public void testNotifyAgentsActionFlowIncompleteAfternoon() {
        final String lockKey = ( String ) getField( jobExecutor, "AGENT_ACTION_FLOW_INCOMPLETE_REMINDER_LOCK_KEY" );
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( schedulerJobsJmxConfig.isEnableAgentActionIncompleteReminder() ).thenReturn( true );
        when( lockHandler.acquireLock( lockKey ) ).thenReturn( true );
        doNothing().when( agentReminderBusinessService ).sendActionFlowIncompleteReminder();
        doNothing().when( lockHandler ).releaseLock( lockKey );

        jobExecutor.notifyAgentsActionFlowIncompleteAfternoon();

        verify( schedulerJobsJmxConfig ).isEnableAllJobs();
        verify( schedulerJobsJmxConfig ).isEnableAgentActionIncompleteReminder();
        verify( lockHandler ).acquireLock( lockKey );
        verify( agentReminderBusinessService ).sendActionFlowIncompleteReminder();
        verify( lockHandler ).releaseLock( lockKey );
    }

    /**
     * Test notify agents action flow incomplete afternoon when disable all
     * jobs.
     */
    @Test
    public void testNotifyAgentsActionFlowIncompleteAfternoonWhenDisableAllJobs() {
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( false );

        jobExecutor.notifyAgentsActionFlowIncompleteAfternoon();

        verify( schedulerJobsJmxConfig ).isEnableAllJobs();
        verifyZeroInteractions( schedulerJobsJmxConfig );
        verifyZeroInteractions( lockHandler );
        verifyZeroInteractions( agentReminderBusinessService );
        verifyZeroInteractions( lockHandler );
    }

    /**
     * Test notify agents action flow incomplete afternoon when disabled action
     * incomplete reminder job.
     */
    @Test
    public void testNotifyAgentsActionFlowIncompleteAfternoonWhenDisabledActionIncompleteReminderJob() {
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( schedulerJobsJmxConfig.isEnableAgentActionIncompleteReminder() ).thenReturn( false );

        jobExecutor.notifyAgentsActionFlowIncompleteAfternoon();

        verify( schedulerJobsJmxConfig ).isEnableAllJobs();
        verify( schedulerJobsJmxConfig ).isEnableAgentActionIncompleteReminder();
        verifyZeroInteractions( lockHandler );
        verifyZeroInteractions( agentReminderBusinessService );
        verifyZeroInteractions( lockHandler );
    }

    /**
     * Test notify agents action flow incomplete afternoon when lock is not
     * acquired.
     */
    @Test
    public void testNotifyAgentsActionFlowIncompleteAfternoonWhenLockIsNotAcquired() {
        final String lockKey = ( String ) getField( jobExecutor, "AGENT_ACTION_FLOW_INCOMPLETE_REMINDER_LOCK_KEY" );
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( schedulerJobsJmxConfig.isEnableAgentActionIncompleteReminder() ).thenReturn( true );
        when( lockHandler.acquireLock( lockKey ) ).thenReturn( false );

        jobExecutor.notifyAgentsActionFlowIncompleteAfternoon();

        verify( schedulerJobsJmxConfig ).isEnableAllJobs();
        verify( schedulerJobsJmxConfig ).isEnableAgentActionIncompleteReminder();
        verify( lockHandler ).acquireLock( lockKey );
        verifyZeroInteractions( agentReminderBusinessService );
        verifyZeroInteractions( lockHandler );
    }

    /**
     * Test execute sync agent score job.
     */
    @Test
    public void testExecuteSyncAgentScoreJob() {
        ReflectionTestUtils.setField( jobExecutor, "scoreJobEnabled", true );
        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( lockHandler.acquireLock( anyString() ) ).thenReturn( true );
        doNothing().when( agentBusinessService ).syncAgentScore();

        jobExecutor.executeSyncAgentScoreJob();

        verify( schedulerJobsJmxConfig ).isEnableAllJobs();
        verify( lockHandler ).acquireLock( anyString() );
        verify( agentBusinessService ).syncAgentScore();
    }
}
