package com.owners.gravitas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class EventDetail.
 * 
 * @author bhardrah
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class EventDetail extends BaseDTO {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -4714274661898656859L;
    /** The property Id. */
    private String propertyId;
    /** The listing id. */
    private String listingId;
    /** The property attributes. */
    private String propertyAttributes;
    
    /**
     * Gets the propertyId.
     *
     * @return the propertyId
     */
    public String getPropertyId() {
        return propertyId;
    }
    /**
     * Sets the property Id.
     *
     * @param propertyId
     *            the property Id
     */
    public void setPropertyId( String propertyId ) {
        this.propertyId = propertyId;
    }
    /**
     * Gets the listing id.
     *
     * @return the listingId
     */
    public String getListingId() {
        return listingId;
    }
    /**
     * Sets the listing Id.
     *
     * @param listingId
     *            the listing Id
     */
    public void setListingId( String listingId ) {
        this.listingId = listingId;
    }
    /**
     * Gets the property attributes.
     *
     * @return the propertyAttributes
     */
    public String getPropertyAttributes() {
        return propertyAttributes;
    }
    /**
     * Sets the property attributes.
     *
     * @param propertyAttributes
     *            the property attributes
     */
    public void setPropertyAttributes( String propertyAttributes ) {
        this.propertyAttributes = propertyAttributes;
    }
}
