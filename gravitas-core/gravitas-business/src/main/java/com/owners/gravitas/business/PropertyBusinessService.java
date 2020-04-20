/**
 *
 */
package com.owners.gravitas.business;

import com.owners.gravitas.dto.response.PropertyDetailsResponse;
import com.owners.gravitas.dto.response.SuggestionOutputResponse;
import com.owners.gravitas.dto.response.SuggestionsResponse;

/**
 * The Interface PropertyBusinessService.
 *
 * @author harshads
 */
public interface PropertyBusinessService {

    /**
     * Gets the property details.
     *
     * @param listingId
     *            the listing id
     * @return the property details
     */
    PropertyDetailsResponse getPropertyDetails( String listingId );

    /**
     * Gets the property location.
     *
     * @param listingId
     *            the listing id
     * @return the property location
     */
    String getPropertyLocation( String listingId );

    /**
     * Gets the locations details in the given location.
     *
     * @param location
     *            the location
     * @return the locations details
     */
    SuggestionsResponse getAllOptionsInLocation(String locationId);
}
