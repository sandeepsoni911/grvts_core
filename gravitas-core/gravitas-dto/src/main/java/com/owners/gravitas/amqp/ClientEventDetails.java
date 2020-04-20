package com.owners.gravitas.amqp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.dto.BaseDTO;

/**
 * The Class ClientEventDetails.
 *
 * @author pabhishek
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class ClientEventDetails extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8379391148185658460L;

    /** The property id. */
    private String propertyId;

    /** The listing id. */
    private String listingId;

    /** The mls id. */
    private String mlsId;

    /** The user id. */
    private String userId;

    /** The visitor id. */
    private String visitorId;

    /** The generated on. */
    private long generatedOn;

    /** The processed. */
    private boolean processed;

    /**
     * Instantiates a new client event details.
     */
    public ClientEventDetails() {

    }

    /**
     * Instantiates a new client event details.
     *
     * @param propertyId
     *            the property id
     * @param listingId
     *            the listing id
     * @param mlsId
     *            the mls id
     * @param userId
     *            the user id
     * @param visitorId
     *            the visitor id
     * @param generatedOn
     *            the generated on
     * @param processed
     *            the processed
     */
    public ClientEventDetails( final String propertyId, final String listingId, final String mlsId, final String userId,
            final String visitorId, final long generatedOn, final boolean processed ) {
        super();
        this.propertyId = propertyId;
        this.listingId = listingId;
        this.mlsId = mlsId;
        this.userId = userId;
        this.visitorId = visitorId;
        this.generatedOn = generatedOn;
        this.processed = processed;
    }

    /**
     * Gets the property id.
     *
     * @return the property id
     */
    public String getPropertyId() {
        return propertyId;
    }

    /**
     * Sets the property id.
     *
     * @param propertyId
     *            the new property id
     */
    public void setPropertyId( final String propertyId ) {
        this.propertyId = propertyId;
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
     * Gets the mls id.
     *
     * @return the mls id
     */
    public String getMlsId() {
        return mlsId;
    }

    /**
     * Sets the mls id.
     *
     * @param mlsId
     *            the new mls id
     */
    public void setMlsId( final String mlsId ) {
        this.mlsId = mlsId;
    }

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     *
     * @param userId
     *            the new user id
     */
    public void setUserId( final String userId ) {
        this.userId = userId;
    }

    /**
     * Gets the visitor id.
     *
     * @return the visitor id
     */
    public String getVisitorId() {
        return visitorId;
    }

    /**
     * Sets the visitor id.
     *
     * @param visitorId
     *            the new visitor id
     */
    public void setVisitorId( final String visitorId ) {
        this.visitorId = visitorId;
    }

    /**
     * Gets the generated on.
     *
     * @return the generated on
     */
    public long getGeneratedOn() {
        return generatedOn;
    }

    /**
     * Sets the generated on.
     *
     * @param generatedOn
     *            the new generated on
     */
    public void setGeneratedOn( final long generatedOn ) {
        this.generatedOn = generatedOn;
    }

    /**
     * Checks if is processed.
     *
     * @return true, if is processed
     */
    public boolean isProcessed() {
        return processed;
    }

    /**
     * Sets the processed.
     *
     * @param processed
     *            the new processed
     */
    public void setProcessed( final boolean processed ) {
        this.processed = processed;
    }

}
