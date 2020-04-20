package com.owners.gravitas.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.owners.gravitas.annotation.PerformanceLog;
import com.owners.gravitas.annotation.ReadArgs;
import com.owners.gravitas.business.AgentRatingBusinessService;
import com.owners.gravitas.business.PropertyBusinessService;
import com.owners.gravitas.business.UserBusinessService;
import com.owners.gravitas.dto.crm.request.UserLoginLogRequest;
import com.owners.gravitas.dto.request.FeedbackRequest;
import com.owners.gravitas.dto.request.SavedSearchRequest;
import com.owners.gravitas.dto.response.AgentRatingResponse;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.validator.UserRoleValidator;

/**
 * The Class PrivateUserController.
 *
 * @author pabhishek
 */
@RestController
@Validated
public class PublicUserController extends BaseController {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( PublicUserController.class );

    /** The agent onboarding business service. */
    @Autowired
    private UserBusinessService userBusinessService;

    /** The agent rating business service. */
    @Autowired
    private AgentRatingBusinessService agentRatingBusinessService;

    /** The user role validator. */
    @Autowired
    private UserRoleValidator userRoleValidator;

    /** The property business service. */
    @Autowired
    private PropertyBusinessService propertyBusinessService;

    /**
     * Gets the user details.
     *
     * @param email
     *            the email
     * @return the user details
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @CrossOrigin
    @RequestMapping( value = "/users/{email}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE )
    @PerformanceLog
    public BaseResponse getUserDetails(
            @NotBlank( message = "  error.email.required" ) @PathVariable final String email ) throws IOException {
        LOGGER.info( "getting user details for " + email );
        return userBusinessService.getUserDetails( email );
    }

    /**
     * Gets the agent details.
     *
     * @param listingId
     *            the listing id
     * @return the agent details
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @CrossOrigin
    @RequestMapping( value = "/users/listings/{listingId}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE )
    @PerformanceLog
    public BaseResponse getAgentDetails(
            @Valid @Size( min = 0, max = 60, message = "error.lead.listingId.size" ) @NotBlank( message = "error.listingId.required" ) @PathVariable final String listingId )
            throws IOException {
        return userBusinessService.getAgentDetails( listingId );
    }

    /**
     * Log user login.
     *
     * @param userLoginLogRequest
     *            the user login log request
     * @return the base response
     */
    @CrossOrigin
    @RequestMapping( value = "/users/login/log", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
    @PerformanceLog
    public BaseResponse logUserLogin( @RequestBody final UserLoginLogRequest userLoginLogRequest ) {
        LOGGER.info( "logging the user details for ipaddress " + userLoginLogRequest.getIpAddress() );
        return userBusinessService.saveUserLoginLog( userLoginLogRequest );
    }

    /**
     * Save agent rating.
     *
     * @param a
     *            the a
     * @param b
     *            the b
     * @return
     * @throws UnsupportedEncodingException
     *             the unsupported encoding exception
     */
    @CrossOrigin
    @ReadArgs
    @PerformanceLog
    @RequestMapping( value = "/ratings", method = POST )
    public AgentRatingResponse saveAgentRating( @RequestParam( required = true ) final String a,
            @RequestParam( required = true ) final String b ) {
        return agentRatingBusinessService.saveAgentRating( a, b );
    }

    /**
     * Save agents feedback.
     *
     * @param feedbackRequest
     *            the feedback request
     */
    @CrossOrigin
    @ReadArgs
    @PerformanceLog
    @RequestMapping( value = "/feedback", method = POST )
    public void saveAgentsFeedback( @RequestBody @Valid final FeedbackRequest feedbackRequest ) {
        agentRatingBusinessService.saveFeedback( feedbackRequest );
    }

    /**
     * Gets the agents details.
     *
     * @param userId
     *            the owners Id of the client
     * @param agentOpportunityType
     *            optional
     *            the opportunity type
     * @return the base response
     */

    @CrossOrigin
    @RequestMapping( value = {
            "/users/{userId}/agents" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @PerformanceLog
    public BaseResponse getAgentsDetails(
            @NotBlank( message = "error.userId.required" ) @PathVariable final String userId,
            @RequestParam( required = false ) final String agentOpportunityType ) {
        userRoleValidator.validateUserId( userId );
        return userBusinessService.getAgentsDetails( userId, agentOpportunityType );
    }
}
