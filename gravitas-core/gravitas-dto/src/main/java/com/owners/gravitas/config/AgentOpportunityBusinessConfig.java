package com.owners.gravitas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class AgentOpportunityBusinessConfig.
 *
 * @author shivamm
 */
@ManagedResource( objectName = "com.owners.gravitas:name=AgentOpportunityBusinessConfig" )
@Component
public class AgentOpportunityBusinessConfig {

    /** The opportunity referral enabled. */
    @Value( "${opportunity.referral.enabled:false}" )
    private boolean opportunityReferralEnabled;

    /** The referral email notification enabled. */
    @Value( "${referral.email.notification:true}" )
    private boolean referralEmailNotificationEnabled;

    /** The opportunity date range. */
    @Value( "${faceToFace.opportunity.range}" )
    private int faceToFaceopportunityRange;

    /** The opportunity date range. */
    @Value( "${scheduled.meetings.date.range}" )
    private int scheduledMeetingsDateRange;

    /** The ocl email allowed states. */
    @Value( "${ocl.email.allowed.states}" )
    private String oclEmailAllowedStates;

    /** The ocl lead source not allowed. */
    @Value( "${ocl.lead.source.not.allowed}" )
    private String oclLeadSourceNotAllowed;
    
    /** The agent schedule meeting start time. */
    @Value( "${agent.schedule.meeting.start.time}" )
    private String agentScheduleMetingStartTime;
    
    /** The agent schedule meeting end time. */
    @Value( "${agent.schedule.meeting.end.time}" )
    private String agentScheduleMetingEndTime;

    /** The property writer. */
    @Autowired
    private PropertyWriter propertyWriter;

    /**
     * Checks if is opportunity referral enabled.
     *
     * @return true, if is opportunity referral enabled
     */
    @ManagedAttribute
    public boolean isOpportunityReferralEnabled() {
        return opportunityReferralEnabled;
    }

    /**
     * Sets the opportunity referral enabled.
     *
     * @param opportunityReferralEnabled
     *            the new opportunity referral enabled
     */
    @ManagedAttribute( description = "Enable/Disable opportunity referral functionality" )
    public void setOpportunityReferralEnabled( final boolean opportunityReferralEnabled ) {
        this.opportunityReferralEnabled = opportunityReferralEnabled;
        propertyWriter.saveJmxProperty( "opportunity.referral.enabled", opportunityReferralEnabled );
    }

    /**
     * Checks if is referral email notification enabled.
     *
     * @return true, if is referral email notification enabled
     */
    @ManagedAttribute
    public boolean isReferralEmailNotificationEnabled() {
        return referralEmailNotificationEnabled;
    }

    /**
     * Sets the referral email notification enabled.
     *
     * @param referralEmailNotificationEnabled
     *            the new referral email notification enabled
     */
    @ManagedAttribute
    public void setReferralEmailNotificationEnabled( final boolean referralEmailNotificationEnabled ) {
        this.referralEmailNotificationEnabled = referralEmailNotificationEnabled;
        propertyWriter.saveJmxProperty( "referral.email.notification", referralEmailNotificationEnabled );
    }

    @ManagedAttribute
    public int getFaceToFaceopportunityRange() {
        return faceToFaceopportunityRange;
    }

    @ManagedAttribute
    public void setFaceToFaceopportunityRange( final int faceToFaceopportunityRange ) {
        this.faceToFaceopportunityRange = faceToFaceopportunityRange;
        propertyWriter.saveJmxProperty( "faceToFace.opportunity.range", faceToFaceopportunityRange );
    }

    @ManagedAttribute
    public int getScheduledMeetingsDateRange() {
        return scheduledMeetingsDateRange;
    }

    @ManagedAttribute
    public void setScheduledMeetingsDateRange( final int scheduledMeetingsDateRange ) {
        this.scheduledMeetingsDateRange = scheduledMeetingsDateRange;
        propertyWriter.saveJmxProperty( "scheduled.meetings.date.range", scheduledMeetingsDateRange );
    }

    @ManagedAttribute
    public String getOclEmailAllowedStates() {
        return oclEmailAllowedStates;
    }

    @ManagedAttribute
    public void setOclEmailAllowedStates( final String oclEmailAllowedStates ) {
        this.oclEmailAllowedStates = oclEmailAllowedStates;
        propertyWriter.saveJmxProperty( "ocl.email.allowed.states", oclEmailAllowedStates );
    }

    @ManagedAttribute
    public String getOclLeadSourceNotAllowed() {
        return oclLeadSourceNotAllowed;
    }

    @ManagedAttribute
    public void setOclLeadSourceNotAllowed( final String oclLeadSourceNotAllowed ) {
        this.oclLeadSourceNotAllowed = oclLeadSourceNotAllowed;
        propertyWriter.saveJmxProperty( "ocl.lead.source.not.allowed", oclLeadSourceNotAllowed );
    }

    @ManagedAttribute
	public String getAgentScheduleMetingStartTime() {
		return agentScheduleMetingStartTime;
	}

    @ManagedAttribute
	public void setAgentScheduleMetingStartTime(String agentScheduleMetingStartTime) {
		this.agentScheduleMetingStartTime = agentScheduleMetingStartTime;
		propertyWriter.saveJmxProperty( "agent.schedule.meeting.start.time", agentScheduleMetingStartTime );
	}	

    @ManagedAttribute
	public String getAgentScheduleMetingEndTime() {
		return agentScheduleMetingEndTime;
	}

    @ManagedAttribute
	public void setAgentScheduleMetingEndTime(String agentScheduleMetingEndTime) {
		this.agentScheduleMetingEndTime = agentScheduleMetingEndTime;
		propertyWriter.saveJmxProperty( "agent.schedule.meeting.end.time", agentScheduleMetingEndTime );
	}
    
}
