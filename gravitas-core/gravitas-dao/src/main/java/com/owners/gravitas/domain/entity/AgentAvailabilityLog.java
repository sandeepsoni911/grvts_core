package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;

/**
 * The Class AgentAvailabilityLog.
 * 
 * @author pabhishek
 */
@Entity( name = "GR_AGENT_AVAILABILITY_LOG" )
public class AgentAvailabilityLog extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -9058066210929345272L;

    /** The off duty reason. */
    @Column( name = "OFF_DUTY_REASON", nullable = true )
    private String offDutyReason;

    /** The off duty start time. */
    @CreatedDate
    @Column( name = "OFF_DUTY_START_TIME", nullable = true )
    @Type( type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime" )
    private DateTime offDutyStartTime;

    /** The off duty end time. */
    @CreatedDate
    @Column( name = "OFF_DUTY_END_TIME", nullable = true )
    @Type( type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime" )
    private DateTime offDutyEndTime;

    @Column( name = "IN_PROCESS", nullable = true )
    private boolean inProcess;

    /** The agent details. */
    @ManyToOne( )
    @JoinColumn( name = "AGENT_DETAIL_ID", unique = false )
    private AgentDetails agentDetails;

    /**
     * Gets the off duty reason.
     *
     * @return the off duty reason
     */
    public String getOffDutyReason() {
        return offDutyReason;
    }

    /**
     * Sets the off duty reason.
     *
     * @param offDutyReason
     *            the new off duty reason
     */
    public void setOffDutyReason( final String offDutyReason ) {
        this.offDutyReason = offDutyReason;
    }

    /**
     * Gets the off duty start time.
     *
     * @return the off duty start time
     */
    public DateTime getOffDutyStartTime() {
        return offDutyStartTime;
    }

    /**
     * Sets the off duty start time.
     *
     * @param offDutyStartTime
     *            the new off duty start time
     */
    public void setOffDutyStartTime( final DateTime offDutyStartTime ) {
        this.offDutyStartTime = offDutyStartTime;
    }

    /**
     * Gets the off duty end time.
     *
     * @return the off duty end time
     */
    public DateTime getOffDutyEndTime() {
        return offDutyEndTime;
    }

    /**
     * Sets the off duty end time.
     *
     * @param offDutyEndTime
     *            the new off duty end time
     */
    public void setOffDutyEndTime( final DateTime offDutyEndTime ) {
        this.offDutyEndTime = offDutyEndTime;
    }

    /**
     * Checks if is in process.
     *
     * @return true, if is in process
     */
    public boolean isInProcess() {
        return inProcess;
    }

    /**
     * Sets the in process.
     *
     * @param inProcess
     *            the new in process
     */
    public void setInProcess( final boolean inProcess ) {
        this.inProcess = inProcess;
    }

    /**
     * Gets the agent details.
     *
     * @return the agent details
     */
    public AgentDetails getAgentDetails() {
        return agentDetails;
    }

    /**
     * Sets the agent details.
     *
     * @param agentDetails
     *            the new agent details
     */
    public void setAgentDetails( final AgentDetails agentDetails ) {
        this.agentDetails = agentDetails;
    }
}
