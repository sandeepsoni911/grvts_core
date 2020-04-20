package com.owners.gravitas.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class AgentSuggestedPropertyRequest.
 *
 * @author javeedsy
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class AgentSuggestedPropertyRequest {

    /** The opportunity Id. */
    private String opportunityId;

    /** The dateTime. */
    private Long dateTime;

    /** The listing id. */
    private String listingId;

    /** The Property Address. */
    private String propertyAddress;

    /** The Notes. */
    private String notes;

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
     * Gets the date time.
     *
     * @return the date time
     */
    public Long getDateTime() {
        return dateTime;
    }

    /**
     * Sets the date time.
     *
     * @param dateTime
     *            the new date time
     */
    public void setDateTime( final Long dateTime ) {
        this.dateTime = dateTime;
    }

    /**
     * Gets the listing id.
     *
     * @return the listing id
     */
    public String getListingId() {
        return listingId;
    }

    /**
     * Sets the listing id.
     *
     * @param listingId
     *            the new listing id
     */
    public void setListingId( final String listingId ) {
        this.listingId = listingId;
    }

    /**
     * Gets the property address.
     *
     * @return the property address
     */
    public String getPropertyAddress() {
        return propertyAddress;
    }

    /**
     * Sets the property address.
     *
     * @param propertyAddress
     *            the new property address
     */
    public void setPropertyAddress( final String propertyAddress ) {
        this.propertyAddress = propertyAddress;
    }

    /**
     * Gets the notes.
     *
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the notes.
     *
     * @param notes
     *            the new notes
     */
    public void setNotes( final String notes ) {
        this.notes = notes;
    }

}
