package com.owners.gravitas.business.impl;

import static com.owners.gravitas.constants.Constants.ADDRESS;
import static com.owners.gravitas.constants.Constants.LISTING;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.business.PropertyBusinessService;
import com.owners.gravitas.dto.response.Options;
import com.owners.gravitas.dto.response.PropertyDetailsResponse;
import com.owners.gravitas.dto.response.SuggestionOutputResponse;
import com.owners.gravitas.dto.response.Suggestions;
import com.owners.gravitas.dto.response.SuggestionsResponse;
import com.owners.gravitas.service.PropertyService;
import com.owners.gravitas.util.AddressFormatter;

/**
 * The Class PropertyBusinessServiceImpl.
 */
@Service
public class PropertyBusinessServiceImpl implements PropertyBusinessService {

    /** The Property Details Services . */
    @Autowired
    private PropertyService propertyService;

    /** The address formatter. */
    @Autowired
    private AddressFormatter addressFormatter;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.PropertyBusinessService#getPropertyDetails(
     * java.lang.String)
     */
    @Override
    public PropertyDetailsResponse getPropertyDetails( final String listingId ) {
        return propertyService.getPropertyDetails( listingId );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.PropertyBusinessService#getPropertyLocation(
     * java.lang.String)
     */
    @Override
    public String getPropertyLocation( final String listingId ) {
        final PropertyDetailsResponse propertyDetails = getPropertyDetails( listingId );
        String propertyLocation = StringUtils.EMPTY;
        if (null != propertyDetails) {
            propertyLocation = addressFormatter.formatAddress( propertyDetails.getData().getPropertyAddress() );
        }
        return propertyLocation;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.PropertyBusinessService#
     * getAllOptionsInLocation(java.lang.String)
     */
    @Override
    public SuggestionsResponse getAllOptionsInLocation( final String locationId ) {
        return buildSuggetion( propertyService.getAllOptionsInLocation( locationId ) );
    }

    /**
     * Builds the location.
     *
     * @param propertyDetails
     *            the property details
     * @param propertyLocation
     *            the property location
     * @return the string
     */
    private SuggestionsResponse buildSuggetion( final SuggestionOutputResponse suggestionOutputResponse ) {
        final SuggestionsResponse suggestionsResponse = new SuggestionsResponse();
        final List< Options > suggestionsList = new ArrayList<>();
        final Map< String, Map< String, List< Suggestions > > > map = suggestionOutputResponse.getSuggestions();
        for ( final String key : map.keySet() ) {
            if (key.equals( ADDRESS ) || key.equals( LISTING )) {
                continue;
            }
            final Map< String, List< Suggestions > > ret = map.get( key );
            for ( final String key1 : ret.keySet() ) {
                final List< Suggestions > suggestions = ret.get( key1 );
                for ( final Suggestions suggestion : suggestions ) {
                    final Options sugget = new Options();
                    sugget.setPolygonLabel( suggestion.getLabel() );
                    sugget.setPolygonUiId( suggestion.getId() );
                    sugget.setKey( key );
                    suggestionsList.add( sugget );
                }

            }
        }
        suggestionsResponse.setSuggestions( suggestionsList );
        return suggestionsResponse;
    }
}
