package com.owners.gravitas.domain.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 * The Class PotentialOpportunityAssignmentLog.
 */
@Entity( name = "GR_POTENTIAL_OPPORTUNITY_ASSIGNMENT_LOG" )
public class PotentialOpportunityAssignmentLog extends AbstractPersistable {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 3134752266045846647L;

    /** The action type. */
    @Column( name = "CRM_ID", nullable = true )
    private String crmId;

    /** The action on. */
    @Column( name = "OPP_LABEL", nullable = true )
    private String opportunityLabel;

    /** The action object id. */
    @Column( name = "OPP_SCORE", nullable = true )
    private String opportunityScore;

    /** The modified by. */
    @Column( name = "ZIP", nullable = true )
    private String zip;

    /** The previous value. */
    @Column( name = "STATE", nullable = true )
    private String state;

    /** The current value. */
    @Column( name = "OPP_THRESHOLD", nullable = true )
    private int opportunityThreshold;

    /** The description. */
    @Column( name = "DAY_THRESHOLD", nullable = true )
    private int dayThreshold;

    /** The platform. */
    @Column( name = "RR_THRESHOLD", nullable = true )
    private int rrThreshold;

    /** The created by. */
    @Column( name = "CREATED_BY", updatable = false )
    private String createdBy;

    /** The created date. */
    @Column( name = "CREATED_ON", updatable = false )
    @Type( type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime" )
    private DateTime createdDate;

    /** The agents. */
    @OneToMany( mappedBy = "opportunity", cascade = CascadeType.ALL )
    private List< PotentialOpportunityAgentAssignmentLog > agents;

    /**
     * Instantiates a new potential opportunity assignment log.
     */
    public PotentialOpportunityAssignmentLog() {
        super();
    }

    /**
     * Gets the crm id.
     *
     * @return the crmId
     */
    public String getCrmId() {
        return crmId;
    }

    /**
     * Sets the crm id.
     *
     * @param crmId
     *            the crmId to set
     */
    public void setCrmId( String crmId ) {
        this.crmId = crmId;
    }

    /**
     * Gets the opportunity label.
     *
     * @return the opportunityLabel
     */
    public String getOpportunityLabel() {
        return opportunityLabel;
    }

    /**
     * Sets the opportunity label.
     *
     * @param opportunityLabel
     *            the opportunityLabel to set
     */
    public void setOpportunityLabel( String opportunityLabel ) {
        this.opportunityLabel = opportunityLabel;
    }

    /**
     * Gets the opportunity score.
     *
     * @return the opportunityScore
     */
    public String getOpportunityScore() {
        return opportunityScore;
    }

    /**
     * Sets the opportunity score.
     *
     * @param opportunityScore
     *            the opportunityScore to set
     */
    public void setOpportunityScore( String opportunityScore ) {
        this.opportunityScore = opportunityScore;
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
     *            the zip to set
     */
    public void setZip( String zip ) {
        this.zip = zip;
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state.
     *
     * @param state
     *            the state to set
     */
    public void setState( String state ) {
        this.state = state;
    }

    /**
     * Gets the opportunity threshold.
     *
     * @return the opportunityThreshold
     */
    public int getOpportunityThreshold() {
        return opportunityThreshold;
    }

    /**
     * Sets the opportunity threshold.
     *
     * @param opportunityThreshold
     *            the opportunityThreshold to set
     */
    public void setOpportunityThreshold( int opportunityThreshold ) {
        this.opportunityThreshold = opportunityThreshold;
    }

    /**
     * Gets the day threshold.
     *
     * @return the dayThreshold
     */
    public int getDayThreshold() {
        return dayThreshold;
    }

    /**
     * Sets the day threshold.
     *
     * @param dayThreshold
     *            the dayThreshold to set
     */
    public void setDayThreshold( int dayThreshold ) {
        this.dayThreshold = dayThreshold;
    }

    /**
     * Gets the rr threshold.
     *
     * @return the rrThreshold
     */
    public int getRrThreshold() {
        return rrThreshold;
    }

    /**
     * Sets the rr threshold.
     *
     * @param rrThreshold
     *            the rrThreshold to set
     */
    public void setRrThreshold( int rrThreshold ) {
        this.rrThreshold = rrThreshold;
    }

    /**
     * Gets the created by.
     *
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the created by.
     *
     * @param createdBy
     *            the createdBy to set
     */
    public void setCreatedBy( String createdBy ) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the created date.
     *
     * @return the createdDate
     */
    public DateTime getCreatedDate() {
        return createdDate == null ? null : createdDate;
    }

    /**
     * Sets the created date.
     *
     * @param createdDate
     *            the createdDate to set
     */
    public void setCreatedDate( DateTime createdDate ) {
        this.createdDate = createdDate == null ? null : createdDate;
    }

    /**
     * Gets the agents.
     *
     * @return the agents
     */
    public List< PotentialOpportunityAgentAssignmentLog > getAgents() {
        return agents;
    }

    /**
     * Sets the agents.
     *
     * @param agents
     *            the agents to set
     */
    public void setAgents( List< PotentialOpportunityAgentAssignmentLog > agents ) {
        this.agents = agents;
    }
}
