package com.owners.gravitas.business.scheduler;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.owners.gravitas.business.AgentReportsBusinessService;
import com.owners.gravitas.config.AgentReportingJobsJmxConfig;
import com.owners.gravitas.lock.AgentReportingSchedularLockHandler;

/**
 * The Class AgentReportingJobExecutor is a central location to invoke all agent
 * reporting(analytics) jobs.
 *
 * @author raviz
 */
@Component
@ManagedResource(objectName = "com.owners.gravitas:name=AgentReportingJobExecutor")
public class AgentReportingJobExecutor {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AgentReportingJobExecutor.class);

	/** The Constant MEDIAN_STAGE_DURATION_JOB_LOCK_KEY. */
	private static final String AGENT_PERFORMANCE_JOB_LOCK_KEY = "AGENT_PERFORMANCE_JOB_LOCK_KEY";

	/** The agent reporting jobs jmx config. */
	@Autowired
	private AgentReportingJobsJmxConfig agentReportingJobsJmxConfig;

	/** The lock handler. */
	@Autowired
	private AgentReportingSchedularLockHandler lockHandler;

	/** The agent reports business service. */
	@Autowired
	private AgentReportsBusinessService agentReportsBusinessService;

	/**
	 * Execute agent performance analytics job.
	 */
	@Scheduled(cron = "${cron.job.agent.score.analytics}", zone = "${cron.job.timezone}")
	@ManagedOperation
	public void executeAgentPerformanceAnalyticsJob() {
		if (agentReportingJobsJmxConfig.isEnableAllJobs()
				&& agentReportingJobsJmxConfig.isEnableAgentsScoreAnalyticsJob()
				&& lockHandler.acquireLock(AGENT_PERFORMANCE_JOB_LOCK_KEY)) {
			try {
				LOGGER.info("Executing Agent Performance Analysis job at " + LocalDateTime.now());
				agentReportsBusinessService.performScoreAnalytics();
			} catch (Exception e) {
				LOGGER.debug("Error Occured while running Schedular job " + e.getLocalizedMessage());
			} finally {
				lockHandler.releaseLock(AGENT_PERFORMANCE_JOB_LOCK_KEY);
				LOGGER.info("Ending Agent Performance Analysis job at " + LocalDateTime.now());
			}
		}
	}
}
