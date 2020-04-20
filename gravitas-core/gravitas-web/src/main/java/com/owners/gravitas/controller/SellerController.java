package com.owners.gravitas.controller;

import static com.owners.gravitas.enums.RecordType.MLS;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.owners.gravitas.annotation.PerformanceLog;
import com.owners.gravitas.business.OpportunityBusinessService;
import com.owners.gravitas.dto.crm.response.OpportunityResponse;
import com.owners.gravitas.dto.request.OpportunityRequest;

/**
 * The Class SellerController.
 *
 * @author vishwanathm
 */
@RestController
public class SellerController extends BaseController {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( SellerController.class );

    /** The seller business service. */
    @Autowired
    private OpportunityBusinessService opportunityBusinessService;

    /**
     * Creates the opportunity.
     *
     * @param request
     *            the request
     * @return the opportunity response
     */
    @PerformanceLog
    @RequestMapping( value = { "/seller/listing",
            "/opportunity" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public OpportunityResponse createSellerListing( @RequestBody @Valid final OpportunityRequest request ) {
        LOGGER.debug( "new request for opportunity!!" );
        return opportunityBusinessService.createSellerOpportunity( request, MLS );
    }
}
