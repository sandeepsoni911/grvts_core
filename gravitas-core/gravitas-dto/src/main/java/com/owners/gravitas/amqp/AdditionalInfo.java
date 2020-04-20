package com.owners.gravitas.amqp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.dto.BaseDTO;

/**
 * The Class AdditionalInfo.
 *
 * @author pabhishek
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class AdditionalInfo extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5393468493973450243L;

    /** The listing id info. */
    private ListingIdInfo listingIdInfo;

    /**
     * Instantiates a new additional info.
     */
    public AdditionalInfo() {

    }

    /**
     * Instantiates a new additional info.
     *
     * @param listingIdInfo
     *            the listing id info
     */
    public AdditionalInfo( final ListingIdInfo listingIdInfo ) {
        super();
        this.listingIdInfo = listingIdInfo;
    }

    /**
     * Gets the listing id info.
     *
     * @return the listing id info
     */
    public ListingIdInfo getListingIdInfo() {
        return listingIdInfo;
    }

    /**
     * Sets the listing id info.
     *
     * @param listingIdInfo
     *            the new listing id info
     */
    public void setListingIdInfo( final ListingIdInfo listingIdInfo ) {
        this.listingIdInfo = listingIdInfo;
    }

}
