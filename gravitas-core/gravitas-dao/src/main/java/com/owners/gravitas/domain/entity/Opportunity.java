package com.owners.gravitas.domain.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 * The Class OpportunityV1.
 *
 * @author shivamm
 *         The Class Opportunity.
 */
@Entity( name = "GR_OPPORTUNITY" )
public class Opportunity extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1633548100973832562L;

    /** The opportunity id. */
    @Column( name = "FB_OPPORTUNITY_ID" )
    private String opportunityId;

    /** The assigned agent id. */
    @Column( name = "ASSIGNED_AGENT_EMAIL" )
    private String assignedAgentId;

    /** The opportunity. */
    @ManyToOne
    @JoinColumn( name = "CONTACT_ID", insertable = false, updatable = false )
    private Contact contact;

    /** The assigned date. */
    @Column( name = "ASSIGNED_DATE" )
    @Type( type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime" )
    private DateTime assignedDate;

    /** The deleted. */
    @Column( name = "DELETED" )
    private boolean deleted;

    /** The listing id details. */
    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    @JoinColumn( nullable = false, name = "OPPORTUNITY_ID", referencedColumnName = "ID" )
    private Set< ListingIdDetails > listingIdDetails;

    /** The response time. */
    @Column( name = "RESPONSE_TIME" )
    private Long responseTime;

    /** The first contact dtm. */
    @Column( name = "FIRST_CONTACTED_ON" )
    @Type( type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime" )
    private DateTime firstContactDtm;

    /** The agent ocl notes. */
    @Column( name = "AGENT_OCL_NOTES" )
    private String agentOclNotes;

    /** The ocl referral status. */
    @Column( name = "OCL_REFERRAL_STATUS" )
    private String oclReferralStatus;

    /**
     * Gets the opportunity id.
     *
     * @return the opportunity id
     */
    public String getOpportunityId() {
        return opportunityId;
    }

    /**
     * Sets the opportunity id.
     *
     * @param opportunityId
     *            the new opportunity id
     */
    public void setOpportunityId( final String opportunityId ) {
        this.opportunityId = opportunityId;
    }

    /**
     * Gets the contact.
     *
     * @return the contact
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * Sets the contact.
     *
     * @param contact
     *            the new contact
     */
    public void setContact( final Contact contact ) {
        this.contact = contact;
    }

    /**
     * Gets the assigned agent id.
     *
     * @return the assigned agent id
     */
    public String getAssignedAgentId() {
        return assignedAgentId;
    }

    /**
     * Sets the assigned agent id.
     *
     * @param assignedAgentId
     *            the new assigned agent id
     */
    public void setAssignedAgentId( final String assignedAgentId ) {
        this.assignedAgentId = assignedAgentId;
    }

    /**
     * Gets the assigned date.
     *
     * @return the assigned date
     */
    public DateTime getAssignedDate() {
        return assignedDate;
    }

    /**
     * Sets the assigned date.
     *
     * @param assignedDate
     *            the new assigned date
     */
    public void setAssignedDate( final DateTime assignedDate ) {
        this.assignedDate = assignedDate;
    }

    /**
     * Checks if is deleted.
     *
     * @return true, if is deleted
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * Sets the deleted.
     *
     * @param deleted
     *            the new deleted
     */
    public void setDeleted( final boolean deleted ) {
        this.deleted = deleted;
    }

    /**
     * Gets the response time.
     *
     * @return the response time
     */
    public Long getResponseTime() {
        return responseTime;
    }

    /**
     * Sets the response time.
     *
     * @param responseTime
     *            the new response time
     */
    public void setResponseTime( final Long responseTime ) {
        this.responseTime = responseTime;
    }

    /**
     * Gets the first contact dtm.
     *
     * @return the first contact dtm
     */
    public DateTime getFirstContactDtm() {
        return firstContactDtm;
    }

    /**
     * Sets the first contact dtm.
     *
     * @param firstContactDtm
     *            the new first contact dtm
     */
    public void setFirstContactDtm( final DateTime firstContactDtm ) {
        this.firstContactDtm = firstContactDtm;
    }

    /**
     * Gets the listing id details.
     *
     * @return the listing id details
     */
    public Set< ListingIdDetails > getListingIdDetails() {
        return listingIdDetails;
    }

    /**
     * Sets the listing id details.
     *
     * @param listingIdDetails
     *            the new listing id details
     */
    public void setListingIdDetails( final Set< ListingIdDetails > listingIdDetails ) {
        this.listingIdDetails = listingIdDetails;
    }

    /**
     * Gets the agent ocl notes.
     *
     * @return the agent ocl notes
     */
    public String getAgentOclNotes() {
        return agentOclNotes;
    }

    /**
     * Sets the agent ocl notes.
     *
     * @param agentOclNotes
     *            the new agent ocl notes
     */
    public void setAgentOclNotes( final String agentOclNotes ) {
        this.agentOclNotes = agentOclNotes;
    }

    /**
     * Gets the ocl referral status.
     *
     * @return the ocl referral status
     */
    public String getOclReferralStatus() {
        return oclReferralStatus;
    }

    /**
     * Sets the ocl referral status.
     *
     * @param oclReferralStatus
     *            the new ocl referral status
     */
    public void setOclReferralStatus( final String oclReferralStatus ) {
        this.oclReferralStatus = oclReferralStatus;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append( "Opportunity [opportunityId=" );
        builder.append( opportunityId );
        builder.append( ", assignedAgentId=" );
        builder.append( assignedAgentId );
        builder.append( ", assignedDate=" );
        builder.append( assignedDate );
        builder.append( ", deleted=" );
        builder.append( deleted );
        builder.append( ", listingIdDetails=" );
        builder.append( listingIdDetails );
        builder.append( ", responseTime=" );
        builder.append( responseTime );
        builder.append( ", firstContactDtm=" );
        builder.append( firstContactDtm );
        builder.append( ", agentOclNotes=" );
        builder.append( agentOclNotes );
        builder.append( ", oclReferralStatus=" );
        builder.append( oclReferralStatus );
        builder.append( "]" );
        return builder.toString();
    }
}
