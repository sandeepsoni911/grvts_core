/**
 *
 */
package com.owners.gravitas.controller;

import static com.owners.gravitas.constants.UserPermission.VIEW_PROPERTY_DETAILS;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.owners.gravitas.annotation.PerformanceLog;
import com.owners.gravitas.business.PropertyBusinessService;
import com.owners.gravitas.dto.response.PropertyDetailsResponse;
import com.owners.gravitas.dto.response.SuggestionOutputResponse;
import com.owners.gravitas.dto.response.SuggestionsResponse;

/**
 * The Class PropertyController.
 *
 * @author harshads
 */
@RestController
public class PropertyController extends BaseWebController {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentController.class );

    /** The property business service. */
    @Autowired
    private PropertyBusinessService propertyBusinessService;

    /**
     * Gets the property details.
     *
     * @param listingId
     *            the listing id
     * @return the property details
     */
    @CrossOrigin
    @RequestMapping( value = "/property/{listingId}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE )
    @Secured( { VIEW_PROPERTY_DETAILS } )
    @PerformanceLog
    public PropertyDetailsResponse getPropertyDetails( @PathVariable final String listingId ) {
        LOGGER.info( "getting property details for listing id" + listingId );
        return propertyBusinessService.getPropertyDetails( listingId );
    }
}
