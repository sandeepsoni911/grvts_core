package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * The Class ActivityProperty.
 * 
 * @author pabhishek
 */
@Entity( name = "GR_ACTIVITY_PROPERTY" )
public class ActivityProperty extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -4696800053447532619L;

    /** The property id. */
    @Column( name = "property_id", nullable = true )
    private String propertyId;

    /** The listing id. */
    @Column( name = "listing_id", nullable = true )
    private String listingId;

    /** The property address. */
    @Column( name = "property_attributes", nullable = true )
    private String propertyAttributes;

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
     * Gets the property attributes.
     *
     * @return the property attributes
     */
    public String getPropertyAttributes() {
        return propertyAttributes;
    }

    /**
     * Sets the property attributes.
     *
     * @param propertyAttributes
     *            the new property attributes
     */
    public void setPropertyAttributes( final String propertyAttributes ) {
        this.propertyAttributes = propertyAttributes;
    }

}
