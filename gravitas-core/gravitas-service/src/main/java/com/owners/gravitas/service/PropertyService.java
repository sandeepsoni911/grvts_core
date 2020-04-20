package com.owners.gravitas.service;

import com.owners.gravitas.dto.PropertyData;
import com.owners.gravitas.dto.response.PropertyDetailsResponse;
import com.owners.gravitas.dto.response.SimilarProperty;
import com.owners.gravitas.dto.response.SimilarPropertyResponse;
import com.owners.gravitas.dto.response.SuggestionOutputResponse;

/**
 * The Interface PropertyService.
 *
 * @author vishwanathm
 */
public interface PropertyService {

    /**
     * Gets the property details.
     *
     * @param listingId
     *            the listing id
     * @return the property details
     */
    PropertyDetailsResponse getPropertyDetails( String listingId );

    /**
     * Gets the locations details in the given location.
     *
     * @param location
     *            the location
     * @return the locations details
     */
    SuggestionOutputResponse getAllOptionsInLocation( String locationId );

    /**
     * Gets the property details if available.
     *
     * @param listingId
     *            the listing id
     * @return the property details if available
     */
    PropertyDetailsResponse getPropertyDetailsIfAvailable( String listingId );

    /**
     * 
     * @param propertyDetailsResponse
     * @param listingId
     * @return
     */
    String getPdpUrl(PropertyDetailsResponse propertyDetailsResponse, String listingId);

    /**
     * 
     * @param propertyData
     */
    SimilarPropertyResponse getBuyerSimilarListingsSearchDetails(PropertyData propertyData); 
    
    /**
     * format street name and zip like 825-Gaston-St-SW-30310
     * 
     * @param streetName
     * @param zip
     * @return
     */
    String formatStreetNameAndZip( String streetName, String zip );
    
    /**
     * remove any slashes from property id
     * 
     * @param propertyId
     * @return
     */
    String getValidPropertyId( String propertyId );
    
    /**
     * Get pdp url
     * 
     * @param similarProperty
     * @return
     */
    String getPdpUrl( SimilarProperty similarProperty );
}
