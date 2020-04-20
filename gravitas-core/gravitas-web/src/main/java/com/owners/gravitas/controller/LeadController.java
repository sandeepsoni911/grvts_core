package com.owners.gravitas.controller;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.owners.gravitas.annotation.PerformanceLog;
import com.owners.gravitas.business.LeadBusinessService;
import com.owners.gravitas.dto.request.BuyerLeadRequest;
import com.owners.gravitas.dto.request.GenericLeadRequest;
import com.owners.gravitas.dto.request.MakeOfferRequest;
import com.owners.gravitas.dto.request.OclLeadRequest;
import com.owners.gravitas.dto.request.RequestInformationRequest;
import com.owners.gravitas.dto.request.ScheduleTourRequest;
import com.owners.gravitas.dto.response.LeadResponse;
import com.owners.gravitas.business.handler.LeadProcessingRetryHandler;

/**
 * The Class LeadController.
 */
@RestController
public class LeadController extends BaseController {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( LeadController.class );

    /** instance of {@link LeadBusinessService}. */
    @Autowired
    private LeadBusinessService buyerBusinessService;

    @Autowired
    private LeadProcessingRetryHandler leadProcessingRetryHandler;
    
    /**
     * Creates the lead.
     *
     * @param request
     *            the request
     * @return the new buyer lead response
     */
    @PerformanceLog
    @RequestMapping( value = "/prospect", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public LeadResponse createGenericLead( @RequestBody @Valid final GenericLeadRequest request ) {
        LOGGER.debug( "new request for lead creation!!" );
        return buyerBusinessService.createLead( request, Boolean.TRUE, null );
    }

    /**
     * Creates the lead.
     *
     * @param request
     *            the request
     * @return the new buyer lead response
     */
    @PerformanceLog
    @RequestMapping( value = { "/buyer/lead",
            "/lead" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public LeadResponse createBuyerLead( @RequestBody @Valid final BuyerLeadRequest request ) {
        LOGGER.debug( "new request for buyer lead creation!!" );
        return buyerBusinessService.createLead( request, Boolean.TRUE, null );
    }

    /**
     * Creates the lead for make an offer lead request type.
     *
     * @param request
     *            the request
     * @return the new buyer lead response
     */
    @PerformanceLog
    @RequestMapping( value = "/make-offer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public LeadResponse createOfferLead( @RequestBody @Valid final MakeOfferRequest request ) {
        LOGGER.debug( "new request for make an offer request type lead creation!!" );
        return buyerBusinessService.createLead( request, null );
    }

    /**
     * Creates the lead for schedule tour lead request type.
     *
     * @param request
     *            the request
     * @return the new buyer lead response
     */
    @PerformanceLog
    @RequestMapping( value = "/schedule-tour", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public LeadResponse createTourLead( @RequestBody @Valid final ScheduleTourRequest request ) {
        LOGGER.debug( "new request for schedule tour request type lead creation!!" );
        return buyerBusinessService.createLead( request, null );
    }

    /**
     * Creates the lead for ask a question lead request type.
     *
     * @param request
     *            the request
     * @return the new buyer lead response
     */
    @PerformanceLog
    @RequestMapping( value = "/ask-question", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public LeadResponse createQuestionLead( @RequestBody @Valid final RequestInformationRequest request ) {
        LOGGER.debug( "new request for ask question request type lead creation!!" );
        return buyerBusinessService.createLead( request, null );
    }

    /**
     * Creates the un-bounce lead.
     *
     * @param jsonData
     *            the json data
     * @return the lead response
     */
    @PerformanceLog
    @RequestMapping( value = "/unbounce/prospect", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )
    public LeadResponse createUnbounceLead(
            @Valid @NotBlank( message = "error.lead.unbounce.jsonData.required" ) @RequestParam( "data.json" ) final String jsonData ) {
        LOGGER.debug( "new request for unbounce lead creation!!" );
        return buyerBusinessService.createUnbounceLead( jsonData, null );
    }
    
    /**
     * Creates OCL lead.
     *
     * @param request
     *            the request
     * @return the new buyer lead response
     */
    @PerformanceLog
    @RequestMapping( value = { "/buyer/ocl_lead"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public LeadResponse createOclLead( @RequestBody final OclLeadRequest request ) {
        LOGGER.debug( "New Ocl Lead request for buye for crmId :{}!!",request.getCrmId() );
        return buyerBusinessService.createLead( request, Boolean.FALSE );
	}

}
