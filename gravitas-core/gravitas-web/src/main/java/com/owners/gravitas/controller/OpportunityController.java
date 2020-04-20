package com.owners.gravitas.controller;

import static com.owners.gravitas.constants.UserPermission.ADD_SCHEDULED_MEETING;
import static com.owners.gravitas.constants.UserPermission.ASSIGN_AGENT;
import static com.owners.gravitas.constants.UserPermission.VIEW_DISCOUNT;
import static com.owners.gravitas.constants.UserPermission.VIEW_BEST_FIT_AGENT;
import static com.owners.gravitas.constants.UserPermission.VIEW_CLAIM_PUBLIC_LEADS;
import static com.owners.gravitas.constants.UserPermission.VIEW_CRM_OPPORTUNITY;
import static com.owners.gravitas.dto.response.BaseResponse.Status.SUCCESS;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Map;

import javax.validation.Valid;

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
import com.owners.gravitas.business.OpportunityBusinessService;
import com.owners.gravitas.dto.OpportunityDetails;
import com.owners.gravitas.dto.request.AgentMeetingRequest;
import com.owners.gravitas.dto.request.ScheduleTourMeetingRequest;
import com.owners.gravitas.dto.response.AgentResponse;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.OpportunityDiscountResponse;
import com.owners.gravitas.service.CoShoppingService;
import com.owners.gravitas.validator.ScheduleTourLeadValidator;
import com.zuner.coshopping.model.api.Response;
import com.zuner.coshopping.model.lead.LeadResponse;

/**
 * The Class OpportunityController.
 *
 * @author amits
 */
@RestController
public class OpportunityController extends BaseWebController {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( OpportunityController.class );

    /** The seller business service. */
    @Autowired
    private OpportunityBusinessService opportunityBusinessService;
    
    /** The CoShopping service. */
    @Autowired
    private CoShoppingService coShoppingService;
    
    /** The Schedule Tour validator. */
    @Autowired
    private ScheduleTourLeadValidator scheduleTourLeadValidator;

    /**
     * Gets the opportunity by crm id.
     *
     * @param crmId
     *            the crm id
     * @return the opportunity by crm id
     */
    @CrossOrigin
    @RequestMapping( value = "/opportunities/{crmId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @Secured( { VIEW_CRM_OPPORTUNITY } )
    @PerformanceLog
    public OpportunityDetails getOpportunityByCRMId( @PathVariable final String crmId ) {
        LOGGER.info( "opportunity details requested for opportunity " + crmId );
        return opportunityBusinessService.getOpportunityByCRMId( crmId );
    }

    /**
     * Assign agent to opportunity.
     *
     * @param crmId
     *            the crm id
     * @param request
     *            the request
     * @return the agent response
     */
    @CrossOrigin
    @RequestMapping( value = { "/opportunities/{crmId}/assignAgent",
            "/opportunities/{crmId}/assign" }, method = RequestMethod.PATCH, produces = APPLICATION_JSON_VALUE )
    @Secured( { ASSIGN_AGENT } )
    @PerformanceLog
    public AgentResponse assignAgentToOpportunity( @PathVariable final String crmId,
            @RequestBody final Map< String, Object > request ) {
        LOGGER.info( "AssignAgent API called for Opportunity: " + crmId );
        opportunityBusinessService.updateOpportunity( crmId, request );
        return new AgentResponse( crmId, SUCCESS, "Agent assigned successfully" );
    }

    /**
     * Reject agent for opportunity.
     *
     * @param crmId
     *            the crm id
     * @param request
     *            the request
     * @return the agent response
     */
    @CrossOrigin
    @RequestMapping( value = {
            "/opportunities/{crmId}/rejectAgent" }, method = RequestMethod.PATCH, produces = APPLICATION_JSON_VALUE )
    @Secured( { ASSIGN_AGENT } )
    @PerformanceLog
    public AgentResponse rejectAgentForOpportunity( @PathVariable final String crmId,
            @RequestBody final Map< String, Object > request ) {
        LOGGER.info( "Reject Agent API called for Opportunity: " + crmId );
        opportunityBusinessService.rejectOpportunity( crmId, request );
        return new AgentResponse( crmId, SUCCESS, "Agent Rejected successfully" );
    }

    /**
     * Assign opportunity to Ref Exchange.
     *
     * @param crmId
     *            the crm id
     * @param request
     *            the request
     * @return the agent response
     */
    @CrossOrigin
    @RequestMapping( value = "/opportunities/{crmId}/refer", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE )
    @Secured( { VIEW_BEST_FIT_AGENT } )
    @PerformanceLog
    public AgentResponse forwardToReferralExchange( @PathVariable final String crmId,
            @RequestBody final Map< String, String > request ) {
        LOGGER.info( "AssignAgent API called for Opportunity: " + crmId );
        opportunityBusinessService.forwardToReferralExchange( crmId, request.get( "message" ) );
        return new AgentResponse( crmId, SUCCESS, "Opportunity successfully forwarded to Referral Exchange." );
    }

    /**
     * 
     * @param crmId
     * @param taskRequest
     * @return
     */
    @CrossOrigin
    @RequestMapping( value = {
            "/opportunities/{crmId}/schedule-meeting" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @Secured( { ADD_SCHEDULED_MEETING } )
    @PerformanceLog
    public AgentResponse createScheduledMeeting( @PathVariable final String crmId,
            @Valid @RequestBody final AgentMeetingRequest meetingRequest ) {
        LOGGER.info( "creating schedule meeting/tour for opportunity: " + crmId );
        return opportunityBusinessService.createScheduledMeeting( crmId, meetingRequest );
    }
    
    
    /**
     * creates/updates Schedule tour meetings selected by the inside sales
     * 
     * @param crmId
     * @param meetingRequest
     * @return
     */
    @CrossOrigin
    @RequestMapping( value = {
            "/opportunities/{crmId}/schedule-tour" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @Secured( { ADD_SCHEDULED_MEETING } )
    @PerformanceLog
    public AgentResponse createScheduleTourMeetings( @PathVariable final String crmId,
            @RequestBody final ScheduleTourMeetingRequest meetingRequest ) {
        LOGGER.info( "creating schedule tours for opportunity: {} " , crmId );
        /*
         * Commenting this as part of Story GRACRM-802
         */
        //scheduleTourLeadValidator.validateScheduleTourLeadRequest( meetingRequest );
        return opportunityBusinessService.createScheduledTourMeetings( crmId, meetingRequest );
    }
    
    @CrossOrigin
    @RequestMapping( value = { "/coshopping/opportunities/{emailId}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @PerformanceLog
    @Secured( { VIEW_CLAIM_PUBLIC_LEADS } )
	public Response<LeadResponse> getLeadDetailsCoShopping( @PathVariable final String emailId ) {
    	return coShoppingService.getLeadDetails(emailId);
	}
    
    /**
     * create scripted action flow and entry in action log in skip of schedule meeting/tour.
     * 
     * @param insideSalesEmail
     * @param crmId
     */
    @CrossOrigin
    @RequestMapping( value = "/opportunities/{crmId}/skipmeeting/{insideSalesEmail}", method = RequestMethod.POST )
    @Secured( { ADD_SCHEDULED_MEETING } )
    @PerformanceLog
    public BaseResponse createSkipMeetingActionFlow( @PathVariable final String insideSalesEmail,
            @PathVariable final String crmId ) {
        LOGGER.info( "Skipping opportunity: {} by Inside Sales: {}", crmId, insideSalesEmail );
        return opportunityBusinessService.createSkipMeetingActionFlow( insideSalesEmail, crmId );
    }
    
    /**
     * Get Discount for the opportunity.
     * 
     * @param agentFbId
     * @param fbId
     */
    @CrossOrigin
    @RequestMapping( value = "/agents/{agentFbId}/opportunities/{oppFbId}/discount", method = RequestMethod.GET )
    @Secured( { VIEW_DISCOUNT } )
    @PerformanceLog
    public OpportunityDiscountResponse getDiscountForOpportunity( @PathVariable final String agentFbId,
            @PathVariable final String oppFbId ) {
        LOGGER.info( "START: API to get discount for the opp firebase id:{}, Assigned Agent Id:{}", oppFbId, agentFbId );
        return opportunityBusinessService.getDiscountForOpportunity( agentFbId, oppFbId );
    }
}
