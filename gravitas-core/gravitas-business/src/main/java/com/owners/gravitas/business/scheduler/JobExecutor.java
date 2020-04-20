package com.owners.gravitas.business.scheduler;

import static com.owners.gravitas.enums.JobType.AGENT_ACTION_FLOW_INCOMPLETE_REMINDER_JOB;
import static com.owners.gravitas.enums.JobType.AGENT_ANALYTICS_JOB;
import static com.owners.gravitas.enums.JobType.AGENT_CLAIMED_OPP_REMINDER_JOB;
import static com.owners.gravitas.enums.JobType.AGENT_NEW_OPP_REMINDER_JOB;
import static com.owners.gravitas.enums.JobType.AGENT_PERFORMANCE_METRICS_JOB;
import static com.owners.gravitas.enums.JobType.GRAVITAS_HEALTH_CHECK_JOB;
import static com.owners.gravitas.enums.JobType.OPPORTUNITY_MAPPING_JOB;
import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionException;

import com.owners.gravitas.annotation.ScheduledJobLog;
import com.owners.gravitas.business.AgentAnalyicsBusinessService;
import com.owners.gravitas.business.AgentBusinessService;
import com.owners.gravitas.business.AgentReminderBusinessService;
import com.owners.gravitas.business.AgentReportsBusinessService;
import com.owners.gravitas.business.SystemHealthBusinessService;
import com.owners.gravitas.config.AgentMetricsJmxConfig;
import com.owners.gravitas.config.SchedulerJobsJmxConfig;
import com.owners.gravitas.domain.entity.SchedulerLog;
import com.owners.gravitas.lock.AgentNewOppSchedulerLockHandler;
import com.owners.gravitas.service.ErrorLogService;
import com.owners.gravitas.service.SchedulerLogService;

/**
 * The Class JobExecutor is a central location to invoke all scheduled jobs.
 *
 * @author ankusht
 */
@Component
@ManagedResource( objectName = "com.owners.gravitas:name=JobExecutor" )
public class JobExecutor {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( JobExecutor.class );

    /** The Constant GRAVITAS_SYSTEM_HEALTH_JOB_LOCK_KEY. */
    private static final String GRAVITAS_SYSTEM_HEALTH_JOB_LOCK_KEY = "GRAVITAS_SYSTEM_HEALTH_JOB_LOCK_KEY";

    /** The Constant AGENT_NEW_OPP_REMINDER_LOCK_KEY. */
    private static final String AGENT_NEW_OPP_REMINDER_LOCK_KEY = "AGENT_NEW_OPP_SCHEDULAR_RUN";

    /** The Constant AGENT_CLAIMED_OPP_REMINDER_LOCK_KEY. */
    private static final String AGENT_CLAIMED_OPP_REMINDER_LOCK_KEY = "AGENT_CLAIMED_OPP_SCHEDULAR_RUN";

    /** The Constant AGENT_PERFORMANCE_METRICS_JOB_LOCK_KEY. */
    private static final String AGENT_PERFORMANCE_METRICS_JOB_LOCK_KEY = "AGENT_PERFORMANCE_METRICS_JOB_LOCK_KEY";

    /** The Constant AGENT_ANALYTICS_JOB_LOCK_KEY. */
    private static final String AGENT_ANALYTICS_JOB_LOCK_KEY = "AGENT_ANALYTICS_JOB_LOCK_KEY";

    /** The Constant OPPORTUNITY_MAPPING_JOB_LOCK_KEY. */
    private static final String OPPORTUNITY_MAPPING_JOB_LOCK_KEY = "OPPORTUNITY_MAPPING_JOB_LOCK_KEY";

    /** The Constant AGENT_SCORE_SYNC_JOB_LOCK_KEY. */
    private static final String AGENT_SCORE_SYNC_JOB_LOCK_KEY = "AGENT_SCORE_SYNC_JOB_LOCK_KEY";

    /** The Constant AGENT_ACTION_FLOW_INCOMPLETE_REMINDER_LOCK_KEY. */
    private static final String AGENT_ACTION_FLOW_INCOMPLETE_REMINDER_LOCK_KEY = "AGENT_ACTION_FLOW_INCOMPLETE_REMINDER_LOCK_KEY";

    /** The lock handler. */
    @Autowired
    private AgentNewOppSchedulerLockHandler lockHandler;

    /** The gravitas systems health business service. */
    @Autowired
    private SystemHealthBusinessService systemHealthBusinessService;

    /** The agent business service. */
    @Autowired
    private AgentReminderBusinessService agentReminderBusinessService;

    /** The agent reports business service. */
    @Autowired
    private AgentReportsBusinessService agentReportsBusinessService;

    /** The agent performance metrics properties holder. */
    @Autowired
    private AgentMetricsJmxConfig agentMetricsJmxConfig;

    /** The scheduler jobs jmx config. */
    @Autowired
    private SchedulerJobsJmxConfig schedulerJobsJmxConfig;

    /** The scheduler log service. */
    @Autowired
    private SchedulerLogService schedulerLogService;

    /** The agent analyics business service. */
    @Autowired
    private AgentAnalyicsBusinessService agentAnalyicsBusinessService;

    /** The error log service. */
    @Autowired
    private ErrorLogService errorLogService;

    /** The agent business service. */
    @Autowired
    private AgentBusinessService agentBusinessService;

    /** The score job enabled. */
    @Value( value = "${agent.score.sync.enabled:true}" )
    private boolean scoreJobEnabled;


    /**
     * Notify agents new oppo.
     */
    @Scheduled( cron = "${cron.job.agent.opportunity.new}", zone = "${cron.job.timezone}" )
    @ScheduledJobLog( jobType = AGENT_NEW_OPP_REMINDER_JOB )
    public void notifyAgentsNewOppo() {
        if (schedulerJobsJmxConfig.isEnableAllJobs() && schedulerJobsJmxConfig.isEnableNewOppAgentReminder()
                && lockHandler.acquireLock( AGENT_NEW_OPP_REMINDER_LOCK_KEY )) {
            try {
                agentReminderBusinessService.sendNewOppReminder();
                Thread.sleep( 2000 );
            } catch ( final InterruptedException e ) {
                LOGGER.error( e.getLocalizedMessage(), e );
            } finally {
                lockHandler.releaseLock( AGENT_NEW_OPP_REMINDER_LOCK_KEY );
            }
        }
    }

    /**
     * Notify agents claimed oppo.
     */
    @Scheduled( cron = "${cron.job.agent.opportunity.claimed}", zone = "${cron.job.timezone}" )
    @ScheduledJobLog( jobType = AGENT_CLAIMED_OPP_REMINDER_JOB )
    public void notifyAgentsClaimedOppo() {
        if (schedulerJobsJmxConfig.isEnableAllJobs() && schedulerJobsJmxConfig.isEnableClaimedOppAgentReminder()
                && lockHandler.acquireLock( AGENT_CLAIMED_OPP_REMINDER_LOCK_KEY )) {
            try {
                agentReminderBusinessService.sendClaimedOppReminder();
                Thread.sleep( 2000 );
            } catch ( final InterruptedException e ) {
                LOGGER.error( e.getLocalizedMessage(), e );
            } finally {
                lockHandler.releaseLock( AGENT_CLAIMED_OPP_REMINDER_LOCK_KEY );
            }
        }
    }

    /**
     * Evaluate agent performance metrics.
     */
    @ManagedOperation
    @Scheduled( cron = "${cron.job.agent.performance.metrics}", zone = "${cron.job.agent.performance.metrics.timezone}" )
    @ScheduledJobLog( jobType = AGENT_PERFORMANCE_METRICS_JOB )
    public void evaluateAgentPerformanceMetrics() {
        if (schedulerJobsJmxConfig.isEnableAllJobs() && agentMetricsJmxConfig.isAgentPerformanceMetricsJobEnabled()
                && lockHandler.acquireLock( AGENT_PERFORMANCE_METRICS_JOB_LOCK_KEY )) {
            try {
                agentReportsBusinessService.evaluateAgentPerformanceMetrics();
                Thread.sleep( 2000 );
            } catch ( final InterruptedException e ) {
                LOGGER.error( e.getLocalizedMessage(), e );
            } finally {
                lockHandler.releaseLock( AGENT_PERFORMANCE_METRICS_JOB_LOCK_KEY );
            }
        }
    }

    /**
     * Execute gravitas system health job.
     */
    @Scheduled( cron = "${cron.job.gravitas.systems.health}", zone = "${cron.job.timezone}" )
    @ManagedOperation
    public void executeGravitasSystemHealthJob() {
        final boolean lockAcquired = acquireHealthSystemJobLock();
        if (lockAcquired && schedulerJobsJmxConfig.isEnableAllJobs()
                && schedulerJobsJmxConfig.isEnableSystemHealthCheckup()) {
            SchedulerLog schedulerLog = null;
            try {
                LOGGER.debug( "Executing system health checkup job " + " Time is : " + LocalDateTime.now() );
                schedulerLog = schedulerLogService.startJob( GRAVITAS_HEALTH_CHECK_JOB );
                systemHealthBusinessService.checkSystemHealth( schedulerLog );
                Thread.sleep( 2000 );
            } catch ( final InterruptedException e ) {
                LOGGER.error( e.getLocalizedMessage(), e );
            } finally {
                lockHandler.releaseLock( GRAVITAS_SYSTEM_HEALTH_JOB_LOCK_KEY );
                schedulerLogService.endJob( schedulerLog );
                systemHealthBusinessService.checkSystemErrors();
            }
        }
    }

    /**
     * Acquire health system job lock.
     *
     * @return true, if successful
     */
    private boolean acquireHealthSystemJobLock() {
        boolean lockAcquired = false;
        try {
            lockAcquired = lockHandler.acquireLock( GRAVITAS_SYSTEM_HEALTH_JOB_LOCK_KEY );
        } catch ( final Exception jce ) {
            if (jce instanceof DataAccessException || jce instanceof TransactionException) {
                systemHealthBusinessService.notifyGravitasDbDown( getStackTrace( jce ) );
            }
        }
        return lockAcquired;
    }

    /**
     * Execute agent analytics job.
     */
    @Scheduled( cron = "${cron.job.agent.analytics}", zone = "${cron.job.timezone}" )
    @ScheduledJobLog( jobType = AGENT_ANALYTICS_JOB )
    public void executeAgentAnalyticsJob() {
        LOGGER.info( "Executing agent analytics job at " + LocalDateTime.now() );
        if (schedulerJobsJmxConfig.isEnableAllJobs() && schedulerJobsJmxConfig.isEnableAgentAnalytics()
                && lockHandler.acquireLock( AGENT_ANALYTICS_JOB_LOCK_KEY )) {
            try {
                agentAnalyicsBusinessService.startAgentAnalytics();
                Thread.sleep( 2000 );
            } catch ( final Exception e ) {
                LOGGER.error( e.getLocalizedMessage(), e );
            } finally {
                lockHandler.releaseLock( AGENT_ANALYTICS_JOB_LOCK_KEY );
            }
        }
    }

    /**
     * Execute opportunity mapping job.
     */
    @Scheduled( cron = "${cron.job.agent.analytics}", zone = "${cron.job.timezone}" )
    public void executeOpportunityMappingJob() {
        LOGGER.info( "Executing Opportunity Mapping job at " + LocalDateTime.now() );
        if (schedulerJobsJmxConfig.isEnableAllJobs() && schedulerJobsJmxConfig.isEnableOpportunityMappingAnalytics()
                && lockHandler.acquireLock( OPPORTUNITY_MAPPING_JOB_LOCK_KEY )) {
            SchedulerLog schedulerLog = null;
            try {
                schedulerLog = schedulerLogService.startJob( OPPORTUNITY_MAPPING_JOB );
                agentAnalyicsBusinessService.opportunityMappingAnalytics();
                Thread.sleep( 2000 );
            } catch ( final Exception e ) {
                LOGGER.error( e.getLocalizedMessage(), e );
            } finally {
                schedulerLogService.endJob( schedulerLog );
                lockHandler.releaseLock( OPPORTUNITY_MAPPING_JOB_LOCK_KEY );
            }
        }
    }

    /**
     * Execute slack error log analytics job.
     */
    @Scheduled( cron = "${cron.job.slack.error.log.analytics}" )
    @ManagedOperation( description = "Log error on slack" )
    public void executeSlackErrorLogAnalyticsJob() {
        LOGGER.info( "Executing Error Logging on slack job " + LocalDateTime.now() );
        if (schedulerJobsJmxConfig.isEnableAllJobs() && schedulerJobsJmxConfig.isErrorLogAnalytics()) {
            errorLogService.publishErrorReportOnSlack();
        }
    }

    /**
     * Sync agent scores.
     */
    @Scheduled( cron = "${cron.job.agent.score.sync}", zone = "${cron.job.timezone}" )
    public void syncAgentScores() {
        LOGGER.info( "Executing Opportunity Mapping job at " + LocalDateTime.now() );
        if (schedulerJobsJmxConfig.isEnableAllJobs() && scoreJobEnabled
                && lockHandler.acquireLock( AGENT_SCORE_SYNC_JOB_LOCK_KEY )) {
            try {
                agentBusinessService.syncAgentScore();
            } catch ( final Exception e ) {
                LOGGER.error( e.getLocalizedMessage(), e );
            } finally {
                lockHandler.releaseLock( AGENT_SCORE_SYNC_JOB_LOCK_KEY );
            }
        }
    }

    /**
     * Execute analytics managed operation.
     */
    @ManagedOperation( description = "Executes Agent and opportunity mapping analytics Job" )
    public void executeAnalytics() {
        executeAgentAnalyticsJob();
        executeOpportunityMappingJob();
        syncAgentScores();
    }

    /**
     * Execute sync agent score job.
     */
    @ManagedOperation( description = "Executes Agent score sync Job" )
    public void executeSyncAgentScoreJob() {
        syncAgentScores();
    }

    /**
     * Notify agents action flow incomplete morning.
     */
    @Scheduled( cron = "${cron.job.agent.action.incomplete.morning}", zone = "${cron.job.timezone}" )
    @ScheduledJobLog( jobType = AGENT_ACTION_FLOW_INCOMPLETE_REMINDER_JOB )
    public void notifyAgentsActionFlowIncompleteMorning() {
        notifyAgentsActionFlowIncomplete();
    }

    /**
     * Notify agents action flow incomplete afternoon.
     */
    @Scheduled( cron = "${cron.job.agent.action.incomplete.afternoon}", zone = "${cron.job.timezone}" )
    @ScheduledJobLog( jobType = AGENT_ACTION_FLOW_INCOMPLETE_REMINDER_JOB )
    public void notifyAgentsActionFlowIncompleteAfternoon() {
        notifyAgentsActionFlowIncomplete();
    }

    /**
     * Notify agents action flow incomplete.
     */
    private void notifyAgentsActionFlowIncomplete() {
        LOGGER.info( "Executing agents action flow incomplete job " + LocalDateTime.now() );
        if (schedulerJobsJmxConfig.isEnableAllJobs() && schedulerJobsJmxConfig.isEnableAgentActionIncompleteReminder()
                && lockHandler.acquireLock( AGENT_ACTION_FLOW_INCOMPLETE_REMINDER_LOCK_KEY )) {
            try {
                agentReminderBusinessService.sendActionFlowIncompleteReminder();
                Thread.sleep( 2000 );
            } catch ( final InterruptedException e ) {
                LOGGER.error( e.getLocalizedMessage(), e );
            } finally {
                lockHandler.releaseLock( AGENT_ACTION_FLOW_INCOMPLETE_REMINDER_LOCK_KEY );
            }
        }
    }
}
