package com.owners.gravitas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class AgentReportingJobsJmxConfig which has configurations that
 * enable/disable agent reporting related jobs.
 *
 * @author raviz
 */
@ManagedResource(objectName = "com.owners.gravitas:name=AgentReportingJobsJmxConfig")
@Component
public class AgentReportingJobsJmxConfig {

	/** The property writer. */
	@Autowired
	private PropertyWriter propertyWriter;

	/** The enable all jobs. */
	@Value("${agent.reporting.all.enabled: true}")
	private boolean enableAllJobs;

	/** The enable agents median stage duration job. */
	@Value("${agent.reporting.median.stage.duration.enabled: true}")
	private boolean enableAgentsMedianStageDurationJob;

	/** The enable agents score analytics job. */
	@Value("${agent.reporting.score.analytics.enabled: true}")
	private boolean enableAgentsScoreAnalyticsJob;

	/**
	 * Checks if is enable all jobs.
	 *
	 * @return true, if is enable all jobs
	 */
	@ManagedAttribute
	public boolean isEnableAllJobs() {
		return enableAllJobs;
	}

	/**
	 * Sets the enable all jobs.
	 *
	 * @param enableAllJobs
	 *            the new enable all jobs
	 */
	@ManagedAttribute
	public void setEnableAllJobs(final boolean enableAllJobs) {
		this.enableAllJobs = enableAllJobs;
		propertyWriter.saveJmxProperty("agent.reporting.all.enabled", enableAllJobs);
	}

	/**
	 * Checks if is enable agents median stage duration job.
	 *
	 * @return true, if is enable agents median stage duration job
	 */
	@ManagedAttribute
	public boolean isEnableAgentsMedianStageDurationJob() {
		return enableAgentsMedianStageDurationJob;
	}

	/**
	 * Sets the enable agents median stage duration job.
	 *
	 * @param enableAgentsMedianStageDurationJob
	 *            the new enable agents median stage duration job
	 */
	@ManagedAttribute
	public void setEnableAgentsMedianStageDurationJob(final boolean enableAgentsMedianStageDurationJob) {
		this.enableAgentsMedianStageDurationJob = enableAgentsMedianStageDurationJob;
		propertyWriter.saveJmxProperty("agent.reporting.median.stage.duration.enabled",
				enableAgentsMedianStageDurationJob);
	}

	/**
	 * Checks if is enable agents score analytics job.
	 *
	 * @return the enableAgentsScoreAnalyticsJob
	 */
	@ManagedAttribute
	public boolean isEnableAgentsScoreAnalyticsJob() {
		return enableAgentsScoreAnalyticsJob;
	}

	/**
	 * Sets the enable agents score analytics job.
	 *
	 * @param enableAgentsScoreAnalyticsJob
	 *            the enableAgentsScoreAnalyticsJob to set
	 */
	@ManagedAttribute
	public void setEnableAgentsScoreAnalyticsJob(boolean enableAgentsScoreAnalyticsJob) {
		this.enableAgentsScoreAnalyticsJob = enableAgentsScoreAnalyticsJob;
		propertyWriter.saveJmxProperty("agent.reporting.score.analytics.enabled", enableAgentsScoreAnalyticsJob);
	}
}
