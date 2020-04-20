package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class OpportunityAgentAssignment.
 */
@Entity( name = "GR_POTENTIAL_AGENT_ASSIGNMENT_LOG" )
@Table( name = "GR_POTENTIAL_AGENT_ASSIGNMENT_LOG" )
public class PotentialOpportunityAgentAssignmentLog extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 3134752266045846647L;

    /** The opportunity. */
    @ManyToOne
    @JoinColumn( name = "OPPORTUNITY_ASSIGNMENT_ID" )
    private PotentialOpportunityAssignmentLog opportunity;

    /** The priority. */
    @Column( name = "PRIORITY", nullable = true )
    private int priority;

    /** The platform version. */
    @Column( name = "AGENT_EMAIL", nullable = true )
    private String agentEmail;

    /** The platform version. */
    @Column( name = "AGENT_SCORE", nullable = true )
    private String agentScore;

    /** The platform version. */
    @Column( name = "AGENT_STATUS", nullable = true )
    private String agentStatus;

    /** The platform version. */
    @Column( name = "STATUS", nullable = true )
    private String status;

    /**
     * Instantiates a new potential opportunity agent assignment log.
     */
    public PotentialOpportunityAgentAssignmentLog() {
        super();
    }

    /**
     * Gets the agent email.
     *
     * @return the agentEmail
     */
    public String getAgentEmail() {
        return agentEmail;
    }

    /**
     * Sets the agent email.
     *
     * @param agentEmail
     *            the agentEmail to set
     */
    public void setAgentEmail( String agentEmail ) {
        this.agentEmail = agentEmail;
    }

    /**
     * Gets the agent score.
     *
     * @return the agentScore
     */
    public String getAgentScore() {
        return agentScore;
    }

    /**
     * Sets the agent score.
     *
     * @param agentScore
     *            the agentScore to set
     */
    public void setAgentScore( String agentScore ) {
        this.agentScore = agentScore;
    }

    /**
     * Gets the agent status.
     *
     * @return the agentStatus
     */
    public String getAgentStatus() {
        return agentStatus;
    }

    /**
     * Sets the agent status.
     *
     * @param agentStatus
     *            the agentStatus to set
     */
    public void setAgentStatus( String agentStatus ) {
        this.agentStatus = agentStatus;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param displayed
     *            the new status
     */
    public void setStatus( String displayed ) {
        this.status = displayed;
    }

    /**
     * Gets the opportunity.
     *
     * @return the opportunity
     */
    public PotentialOpportunityAssignmentLog getOpportunity() {
        return opportunity;
    }

    /**
     * Sets the opportunity.
     *
     * @param opportunity
     *            the opportunity to set
     */
    public void setOpportunity( PotentialOpportunityAssignmentLog opportunity ) {
        this.opportunity = opportunity;
    }

    /**
     * Gets the priority.
     *
     * @return the priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Sets the priority.
     *
     * @param priority
     *            the priority to set
     */
    public void setPriority( int priority ) {
        this.priority = priority;
    }

}
