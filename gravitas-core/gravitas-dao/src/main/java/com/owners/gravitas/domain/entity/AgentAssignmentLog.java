package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * The Class AgentAssignmentLog.
 * 
 * @author ankusht, abhishek
 */
@Entity( name = "GR_AGENT_ASSIGNMENT_LOG" )
public class AgentAssignmentLog extends AbstractAuditable {

    /** The Constant REASON_CULUMN_LENGTH. */
    private static final int REASON_CULUMN_LENGTH = 499;

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -7468849791512781371L;

    /** The crm opportunity id. */
    @Column( name = "CRM_OPPORTUNITY_ID", nullable = false )
    private String crmOpportunityId;

    /** The zip. */
    @Column( name = "ZIP", nullable = false )
    private String zip;

    /** The cbsa code. */
    @Column( name = "CBSA_CODE", nullable = false )
    private String cbsaCode;

    /** The owners market label. */
    @Column( name = "OWNERS_MARKET_LABEL", nullable = true )
    private String ownersMarketLabel;

    /** The agent email. */
    @Column( name = "AGENT_EMAIL", nullable = false )
    private String agentEmail;

    /** The priority. */
    @Column( name = "PRIORITY", nullable = true )
    private Integer priority;

    /** The agent score. */
    @Column( name = "AGENT_SCORE", nullable = false )
    private double agentScore;

    /** The agent type. */
    @Column( name = "AGENT_TYPE", nullable = false )
    private String agentType;

    /** The number of opps in threshold period. */
    @Column( name = "OPP_ASSIGNED_IN_THRESHOLD", nullable = false )
    private int numberOfOppsInThresholdPeriod;

    /** The assignment status. */
    @Column( name = "ASSIGNMENT_STATUS", nullable = false )
    private String assignmentStatus;

    /** The reason. */
    @Column( name = "REASON", nullable = true )
    private String reason;

    /**
     * Gets the crm opportunity id.
     *
     * @return the crm opportunity id
     */
    public String getCrmOpportunityId() {
        return crmOpportunityId;
    }

    /**
     * Sets the crm opportunity id.
     *
     * @param crmOpportunityId
     *            the new crm opportunity id
     */
    public void setCrmOpportunityId( final String crmOpportunityId ) {
        this.crmOpportunityId = crmOpportunityId;
    }

    /**
     * Gets the zip.
     *
     * @return the zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * Sets the zip.
     *
     * @param zip
     *            the new zip
     */
    public void setZip( final String zip ) {
        this.zip = zip;
    }

    /**
     * Gets the cbsa code.
     *
     * @return the cbsa code
     */
    public String getCbsaCode() {
        return cbsaCode;
    }

    /**
     * Sets the cbsa code.
     *
     * @param cbsaCode
     *            the new cbsa code
     */
    public void setCbsaCode( final String cbsaCode ) {
        this.cbsaCode = cbsaCode;
    }

    /**
     * Gets the owners market label.
     *
     * @return the owners market label
     */
    public String getOwnersMarketLabel() {
        return ownersMarketLabel;
    }

    /**
     * Sets the owners market label.
     *
     * @param ownersMarketLabel
     *            the new owners market label
     */
    public void setOwnersMarketLabel( final String ownersMarketLabel ) {
        this.ownersMarketLabel = ownersMarketLabel;
    }

    /**
     * Gets the agent email.
     *
     * @return the agent email
     */
    public String getAgentEmail() {
        return agentEmail;
    }

    /**
     * Sets the agent email.
     *
     * @param agentEmail
     *            the new agent email
     */
    public void setAgentEmail( final String agentEmail ) {
        this.agentEmail = agentEmail;
    }

    /**
     * Gets the agent score.
     *
     * @return the agent score
     */
    public double getAgentScore() {
        return agentScore;
    }

    /**
     * Sets the agent score.
     *
     * @param agentScore
     *            the new agent score
     */
    public void setAgentScore( final double agentScore ) {
        this.agentScore = agentScore;
    }

    /**
     * Gets the agent type.
     *
     * @return the agent type
     */
    public String getAgentType() {
        return agentType;
    }

    /**
     * Sets the agent type.
     *
     * @param agentType
     *            the new agent type
     */
    public void setAgentType( final String agentType ) {
        this.agentType = agentType;
    }

    /**
     * Gets the number of opps in threshold period.
     *
     * @return the number of opps in threshold period
     */
    public int getNumberOfOppsInThresholdPeriod() {
        return numberOfOppsInThresholdPeriod;
    }

    /**
     * Sets the number of opps in threshold period.
     *
     * @param numberOfOppsInThresholdPeriod
     *            the new number of opps in threshold period
     */
    public void setNumberOfOppsInThresholdPeriod( final int numberOfOppsInThresholdPeriod ) {
        this.numberOfOppsInThresholdPeriod = numberOfOppsInThresholdPeriod;
    }

    /**
     * Gets the assignment status.
     *
     * @return the assignment status
     */
    public String getAssignmentStatus() {
        return assignmentStatus;
    }

    /**
     * Sets the assignment status.
     *
     * @param assignmentStatus
     *            the new assignment status
     */
    public void setAssignmentStatus( final String assignmentStatus ) {
        this.assignmentStatus = assignmentStatus.toLowerCase();
    }

    /**
     * Gets the reason.
     *
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the reason.
     *
     * @param reason
     *            the new reason
     */
    public void setReason( final String reason ) {
        if (reason != null) {
            final String text = reason.trim();
            if (text.length() > REASON_CULUMN_LENGTH) {
                this.reason = text.substring( 0, REASON_CULUMN_LENGTH );
            } else {
                this.reason = text;
            }
        }
    }

    /**
     * Gets the priority.
     *
     * @return the priority
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * Sets the priority.
     *
     * @param priority
     *            the new priority
     */
    public void setPriority( final Integer priority ) {
        this.priority = priority;
    }
}
