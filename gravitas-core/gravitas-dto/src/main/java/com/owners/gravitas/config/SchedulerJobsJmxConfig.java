package com.owners.gravitas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.owners.gravitas.util.PropertyWriter;

// TODO: Auto-generated Javadoc
/**
 * The Class SchedulerJobsJmxConfig.
 */
@ManagedResource( objectName = "com.owners.gravitas:name=SchedulerJobsJmxConfig" )
@Component
public class SchedulerJobsJmxConfig {

    /** The property writer. */
    @Autowired
    private PropertyWriter propertyWriter;

    /** The enable all agent reminders. */
    @Value( "${scheduler.all.enabled: true}" )
    private boolean enableAllJobs;

    /** The enable new opp agent reminder. */
    @Value( "${scheduler.agent.newOpportunity.reminder.enabled: true}" )
    private boolean enableNewOppAgentReminder;

    /** The enable claimed opp agent reminder. */
    @Value( "${scheduler.agent.claimedOpportunity.reminder.enabled: true}" )
    private boolean enableClaimedOppAgentReminder;

    /** The enable all agent reminders. */
    @Value( "${system.health.checkup.enabled: true}" )
    private boolean enableSystemHealthCheckup;

    /** The enable agent uid analytics. */
    @Value( "${scheduler.agent.analytics.enabled: true}" )
    private boolean enableAgentAnalytics;

    /** The enable opportunity mapping analytics. */
    @Value( "${scheduler.opportunityMapping.analytics.enabled: true}" )
    private boolean enableOpportunityMappingAnalytics;

    /** The enable opportunity mapping analytics. */
    @Value( "${scheduler.error.excel.analytics.log: true}" )
    private boolean errorLogAnalytics;

    /** The enable agent action incomplete reminder. */
    @Value( "${scheduler.agent.action.flow.incomplete.reminder.enabled: true}" )
    private boolean enableAgentActionIncompleteReminder;

    /** The enable outbound attempt check schedular. */
    @Value( "${scheduler.outbound.attempt.check.enabled: true}" )
    private boolean enableOutboundAttemptCheckSchedular;

    /**
     * Checks if is enable system health checkup.
     *
     * @return true, if is enable system health checkup
     */
    @ManagedAttribute
    public boolean isEnableSystemHealthCheckup() {
        return enableSystemHealthCheckup;
    }

    /**
     * Sets the enable system health checkup.
     *
     * @param enableSystemHealthCheckup
     *            the new enable system health checkup
     */
    @ManagedAttribute
    public void setEnableSystemHealthCheckup( final boolean enableSystemHealthCheckup ) {
        this.enableSystemHealthCheckup = enableSystemHealthCheckup;
        propertyWriter.saveJmxProperty( "system.health.checkup.enabled", enableSystemHealthCheckup );
    }

    /**
     * Checks if is enable all agent reminder.
     *
     * @return true, if is enable all agent reminder
     */
    @ManagedAttribute
    public boolean isEnableAllJobs() {
        return enableAllJobs;
    }

    /**
     * Sets the enable all agent reminder.
     *
     * @param enableAllJobs
     *            the new enable all agent reminder
     */
    @ManagedAttribute
    public void setEnableAllJobs( final boolean enableAllAgentReminders ) {
        this.enableAllJobs = enableAllAgentReminders;
        propertyWriter.saveJmxProperty( "scheduler.all.enabled", enableAllAgentReminders );
    }

    /**
     * Checks if is enable new opp agent reminder.
     *
     * @return the enableNewOppAgentReminder
     */
    @ManagedAttribute
    public boolean isEnableNewOppAgentReminder() {
        return enableNewOppAgentReminder;
    }

    /**
     * Sets the enable new opp agent reminder.
     *
     * @param enableNewOppAgentReminder
     *            the enableNewOppAgentReminder to set
     */
    @ManagedAttribute
    public void setEnableNewOppAgentReminder( final boolean enableNewOppAgentReminder ) {
        this.enableNewOppAgentReminder = enableNewOppAgentReminder;
        propertyWriter.saveJmxProperty( "scheduler.agent.newOpportunity.reminder.enabled", enableNewOppAgentReminder );
    }

    /**
     * Checks if is enable claimed opp agent reminder.
     *
     * @return the enableClaimedOppAgentReminder
     */
    @ManagedAttribute
    public boolean isEnableClaimedOppAgentReminder() {
        return enableClaimedOppAgentReminder;
    }

    /**
     * Sets the enable claimed opp agent reminder.
     *
     * @param enableClaimedOppAgentReminder
     *            the enableClaimedOppAgentReminder to set
     */
    @ManagedAttribute
    public void setEnableClaimedOppAgentReminder( final boolean enableClaimedOppAgentReminder ) {
        this.enableClaimedOppAgentReminder = enableClaimedOppAgentReminder;
        propertyWriter.saveJmxProperty( "scheduler.agent.claimedOpportunity.reminder.enabled",
                enableClaimedOppAgentReminder );
    }

    /**
     * Checks if is enable agent uid analytics.
     *
     * @return true, if is enable agent uid analytics
     */
    @ManagedAttribute
    public boolean isEnableAgentAnalytics() {
        return enableAgentAnalytics;
    }

    /**
     * Sets the enable agent analytics.
     *
     * @param enableAgentAnalytics
     *            the new enable agent uid analytics
     */
    @ManagedAttribute
    public void setEnableAgentAnalytics( final boolean enableAgentAnalytics ) {
        this.enableAgentAnalytics = enableAgentAnalytics;
        propertyWriter.saveJmxProperty( "scheduler.agent.analytics.enabled", enableAgentAnalytics );
    }

    /**
     * Checks if is enable opportunity mapping analytics.
     *
     * @return true, if is enable opportunity mapping analytics
     */
    @ManagedAttribute
    public boolean isEnableOpportunityMappingAnalytics() {
        return enableOpportunityMappingAnalytics;
    }

    /**
     * Sets the enable opportunity mapping analytics.
     *
     * @param enableOpportunityMappingAnalytics
     *            the new enable opportunity mapping analytics
     */
    @ManagedAttribute
    public void setEnableOpportunityMappingAnalytics( final boolean enableOpportunityMappingAnalytics ) {
        this.enableOpportunityMappingAnalytics = enableOpportunityMappingAnalytics;
        propertyWriter.saveJmxProperty( "scheduler.opportunityMapping.analytics.enabled",
                enableOpportunityMappingAnalytics );
    }

    /**
     * Checks if is error log analytics.
     *
     * @return true, if is error log analytics
     */
    @ManagedAttribute
    public boolean isErrorLogAnalytics() {
        return errorLogAnalytics;
    }

    /**
     * Sets the error log analytics.
     *
     * @param errorLogAnalytics
     *            the new error log analytics
     */
    @ManagedAttribute
    public void setErrorLogAnalytics( final boolean errorLogAnalytics ) {
        this.errorLogAnalytics = errorLogAnalytics;
        propertyWriter.saveJmxProperty( "scheduler.error.excel.analytics.log", errorLogAnalytics );
        this.errorLogAnalytics = errorLogAnalytics;
    }

    /**
     * Checks if is enable agent action incomplete reminder.
     *
     * @return true, if is enable agent action incomplete reminder
     */
    @ManagedAttribute
    public boolean isEnableAgentActionIncompleteReminder() {
        return enableAgentActionIncompleteReminder;
    }

    /**
     * Sets the enable agent action incomplete reminder.
     *
     * @param enableAgentActionIncompleteReminder
     *            the new enable agent action incomplete reminder
     */
    @ManagedAttribute
    public void setEnableAgentActionIncompleteReminder( final boolean enableAgentActionIncompleteReminder ) {
        this.enableAgentActionIncompleteReminder = enableAgentActionIncompleteReminder;
        propertyWriter.saveJmxProperty( "scheduler.agent.action.flow.incomplete.reminder.enabled",
                enableAgentActionIncompleteReminder );
    }

    /**
     * Checks if is enable outbound attempt check schedular.
     *
     * @return true, if is enable outbound attempt check schedular
     */
    @ManagedAttribute
    public boolean isEnableOutboundAttemptCheckSchedular() {
        return enableOutboundAttemptCheckSchedular;
    }

    /**
     * Sets the enable outbound attempt check schedular.
     *
     * @param enableOutboundAttemptCheckSchedular
     *            the new enable outbound attempt check schedular
     */
    @ManagedAttribute
    public void setEnableOutboundAttemptCheckSchedular( final boolean enableOutboundAttemptCheckSchedular ) {
        this.enableOutboundAttemptCheckSchedular = enableOutboundAttemptCheckSchedular;
        propertyWriter.saveJmxProperty( "scheduler.outbound.attempt.check.enabled",
                enableOutboundAttemptCheckSchedular );
    }

}
