package com.owners.gravitas.controller;

import static com.owners.gravitas.constants.UserPermission.VIEW_CLAIM_PUBLIC_LEADS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.owners.gravitas.annotation.PerformanceLog;
import com.owners.gravitas.business.LeadBusinessService;
import com.owners.gravitas.dto.LeadDetailsList;
import com.owners.gravitas.dto.request.LeadDetailsRequest;
import com.owners.gravitas.dto.response.ClaimLeadResponse;
import com.owners.gravitas.dto.response.LeadDetailsResponse;
import com.owners.gravitas.dto.response.LeadLayoutResponse;
import com.owners.gravitas.dto.response.MenuConfigResponse;
import com.owners.gravitas.validator.LeadDetailsRequestValidator;

/**
 * The class LeadWebController
 * 
 * @author imranmoh
 *
 */
@RestController
public class LeadWebController extends BaseWebController{

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( LeadWebController.class );
    
    @Autowired
    private LeadBusinessService leadBusinessService;
    
    @Autowired
    private LeadDetailsRequestValidator leadDetailsRequestValidator;
    
    
    /**
     * Controller method to get all public available leads
     * @return
     */
    @CrossOrigin
    @RequestMapping( value = {
            "/leads/public" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @Secured( { VIEW_CLAIM_PUBLIC_LEADS } )
//    @PerformanceLog
    public LeadDetailsList getPublicAvailableLeads( final LeadDetailsRequest leadDetailsRequest ) {
        leadDetailsRequestValidator.validateLeadDetailsRequest( leadDetailsRequest );
        return leadBusinessService.getAvailableLeads( leadDetailsRequest );
    }
    
    /**
     * 
     * @return
     */
    @CrossOrigin
    @RequestMapping( value = {"/leads/myleads" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @Secured({VIEW_CLAIM_PUBLIC_LEADS})
//    @PerformanceLog
    public LeadDetailsList getMyClaimLeads( final LeadDetailsRequest leadDetailsRequest ) {
        LOGGER.info( "Displaying all public leads available" );
        return leadBusinessService.getAllMyLeads( leadDetailsRequest );
    }
    
    /**
     * @param crmId
     * @return
     */
    @CrossOrigin
    @RequestMapping( value = { "/claim-lead/{crmId}" }, method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE )
    @Secured( { VIEW_CLAIM_PUBLIC_LEADS } )
    @PerformanceLog
    public ClaimLeadResponse claimLead( @PathVariable final String crmId ) {
        LOGGER.info( "claiming public lead by inside sales for crmId : {}" , crmId );
        return leadBusinessService.claimLead( crmId );
    }
    
    /**
     * @param crmId
     * @return
     */
    @CrossOrigin
    @RequestMapping( value = { "/get-lead/{crmId}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @Secured( { VIEW_CLAIM_PUBLIC_LEADS } )
    @PerformanceLog
    public LeadDetailsResponse getLeadDetails( @PathVariable final String crmId ) {
        LOGGER.info( "Getting all information of a lead for crm id : {}" , crmId );
        return leadBusinessService.getLeadDetails( crmId );
    }
    
    /**
     * @param crmId
     * @return
     */
    @CrossOrigin
    @RequestMapping( value = { "/get-opportunity/{crmId}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @Secured( { VIEW_CLAIM_PUBLIC_LEADS } )
    @PerformanceLog
    public LeadDetailsResponse getOpportunityDetails( @PathVariable final String crmId ) {
        LOGGER.info( "Getting all information of a lead for crm id : {}" , crmId );
        return leadBusinessService.getOpportunityDetails( crmId );
    }
    
    /**
     * @param source
     * @return
     */
    @CrossOrigin
    @RequestMapping( value = { "/leadlayout/{source}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @Secured( { VIEW_CLAIM_PUBLIC_LEADS } )
    @PerformanceLog
    public LeadLayoutResponse getLeadLayout( @PathVariable final String source ) {
        LOGGER.info( "Getting lead layout information for source : {}" , source );
        return leadBusinessService.getLeadLayout(source);
    }
    
    /**
     * @param source
     * @return
     */
    @CrossOrigin
    @RequestMapping( value = { "/leadlayout/{source}" }, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
    @Secured( { VIEW_CLAIM_PUBLIC_LEADS } )
    @PerformanceLog
    public LeadLayoutResponse saveLeadLayout( @RequestBody final String layout, @PathVariable final String source ) {
        LOGGER.info( "Storing lead layout information for source : {}" , source );
        return leadBusinessService.saveLeadLayout(source, layout);
    }
    
    /**
     * @param source
     * @return
     */
    @CrossOrigin
    @RequestMapping( value = { "/menus/{role}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @PerformanceLog
    public MenuConfigResponse getMenuConfigurationOfRole( @PathVariable final String role ) {
        LOGGER.info( "Get menu configuration Of role : {}" , role );
        return leadBusinessService.getMenuConfigurationOfRole( role );
    }
    
    /**
     * @param source
     * @return
     */
    @CrossOrigin
    @RequestMapping( value = { "/menus" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @PerformanceLog
    public MenuConfigResponse getListOfMenusForAllRole() {
        LOGGER.info( "Get list of active menus for all role" );
        return leadBusinessService.getListOfMenusForAllRole();
    }
    
    /**
     * @param source
     * @return
     */
    @CrossOrigin
    @RequestMapping( value = { "/menus/routePath" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @PerformanceLog
    public MenuConfigResponse getListOfRolesForAllRoutePath() {
        LOGGER.info( "Get list of roles for all routePath" );
        return leadBusinessService.getListOfRolesForAllRoutePath();
    }
    
}
