package com.owners.gravitas.controller;

import static com.owners.gravitas.constants.UserPermission.ADD_SCHEDULED_MEETING;
import static com.owners.gravitas.constants.UserPermission.RESET_PASSWORD;
import static com.owners.gravitas.constants.UserPermission.SAVE_SEARCH;
import static com.owners.gravitas.constants.UserPermission.UPDATE_GROUP;
import static com.owners.gravitas.constants.UserPermission.VALIDATE_AGENT_TASK;
import static com.owners.gravitas.constants.UserPermission.VIEW_AGENT_GROUP;
import static com.owners.gravitas.constants.UserPermission.VIEW_CHECK_IN;
import static com.owners.gravitas.constants.UserPermission.VIEW_EMAIL;
import static com.owners.gravitas.constants.UserPermission.VIEW_GOOGLE_CALENDAR_EVENTS;
import static com.owners.gravitas.constants.UserPermission.VIEW_GROUP;
import static com.owners.gravitas.constants.UserPermission.VIEW_MANAGING_BROKER_REPORT;
import static com.owners.gravitas.constants.UserPermission.VIEW_ROLE;
import static com.owners.gravitas.constants.UserPermission.VIEW_USER;
import static com.owners.gravitas.constants.UserPermission.VIEW_USER_ACTIVITY;
import static com.owners.gravitas.constants.UserRole.MANAGING_BROKER;
import static com.owners.gravitas.util.DateUtil.DEFAULT_CRM_DATE_PATTERN;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
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
import com.owners.gravitas.business.AgentTaskBusinessService;
import com.owners.gravitas.business.GroupManagementBusinessService;
import com.owners.gravitas.business.PropertyBusinessService;
import com.owners.gravitas.business.UserBusinessService;
import com.owners.gravitas.dto.request.SavedSearchChimeraRequest;
import com.owners.gravitas.dto.request.TaskUpdateRequest;
import com.owners.gravitas.dto.request.UserNotificationConfigDetails;
import com.owners.gravitas.dto.response.AgentResponse;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.CheckinDetailResponse;
import com.owners.gravitas.dto.response.GroupDetailResponse;
import com.owners.gravitas.dto.response.SaveSearchResponse;
import com.owners.gravitas.dto.response.ScheduleMeetingResponse;
import com.owners.gravitas.dto.response.SuggestionsResponse;
import com.owners.gravitas.enums.NotificationType;
import com.owners.gravitas.util.JsonUtil;
import com.owners.gravitas.validator.UserRoleValidator;
import com.owners.gravitas.validators.Date;

/**
 * The Class UserController.
 *
 * @author harshads
 */
@RestController
@Validated
public class UserController extends BaseWebController {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( UserController.class );

    /** The agent onboarding business service. */
    @Autowired
    private UserBusinessService userBusinessService;

    /** The user role validator. */
    @Autowired
    private UserRoleValidator userRoleValidator;

    /** The group management business service. */
    @Autowired
    private GroupManagementBusinessService groupManagementBusinessService;

    /** The agent task business service. */
    @Autowired
    private AgentTaskBusinessService agentTaskBusinessService;

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
    @RequestMapping( value = "/users/{email}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @Secured( VIEW_USER )
    @PerformanceLog
    public BaseResponse getUserDetails(
            @NotBlank( message = "error.agent.email.required" ) @PathVariable final String email ) throws IOException {
        LOGGER.info( "getting user details for " + email );
        return userBusinessService.getUserDetails( email );
    }

    /**
     * Checks if is email exists.
     *
     * @param email
     *            the email
     * @return the boolean
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @CrossOrigin
    @RequestMapping( value = "/users/{email}/exists", method = RequestMethod.GET )
    @Secured( VIEW_EMAIL )
    @PerformanceLog
    public Boolean isGoogleUserExists(
            @NotBlank( message = "error.agent.email.required" ) @PathVariable final String email ) throws IOException {
        LOGGER.info( "getting user details for " + email );
        return userBusinessService.isGoogleUserExists( email );
    }

    /**
     * Gets the users by role.
     *
     * @param role
     *            the role
     * @return the users by role
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @CrossOrigin
    @RequestMapping( value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @Secured( VIEW_USER )
    @PerformanceLog
    public BaseResponse getUsersByFilters( @RequestParam( required = false ) final String role,
            @RequestParam( required = false ) final String filter ) throws IOException {
        LOGGER.info( "getting user details for " + role + " and filter " + filter );
        return userBusinessService.getUsersByFilters( role, filter );
    }

    /**
     * Gets the user roles by email.
     *
     * @param email
     *            the email
     * @return the user roles by email
     */
    @CrossOrigin
    @RequestMapping( value = "/users/{email}/roles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @Secured( VIEW_ROLE )
    @PerformanceLog
    public BaseResponse getUserRolesByEmail(
            @NotBlank( message = "error.email.required" ) @PathVariable final String email ) {
        userRoleValidator.validateByUserEmail( email );
        return userBusinessService.getUserRolesByEmail( email );
    }

    /**
     * Reset password.
     *
     * @param email
     *            the email
     * @return the base response
     */
    @CrossOrigin
    @RequestMapping( value = { "/users/{email}/password",
            "/users/{email}/reset-password" }, method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE )
    @Secured( { RESET_PASSWORD } )
    @PerformanceLog
    public BaseResponse resetPassword(
            @NotBlank( message = "error.email.required" ) @PathVariable final String email ) {
        LOGGER.info( "resetting password for " + email );
        return userBusinessService.resetPassword( email );
    }

    /**
     * Gets the agents by manager.
     *
     * @param email
     *            the email
     * @param filter
     *            the filter
     * @return the agents by manager
     */
    @CrossOrigin
    @RequestMapping( value = { "/managers/{email}/users",
            "/users/{email}/reportees" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @Secured( { VIEW_MANAGING_BROKER_REPORT } )
    @PerformanceLog
    public BaseResponse getAgentsByManager(
            @NotBlank( message = "error.email.required" ) @PathVariable final String email,
            @RequestParam( required = false ) final String filter ) {
        userRoleValidator.validateByUserEmail( email, MANAGING_BROKER );
        LOGGER.info( "getting agents for agent  " + email );
        return userBusinessService.getAgentsByManager( email, filter );
    }

    /**
     * Gets the groups by agent.
     *
     * @param email
     *            the email
     * @return the groups by agent
     */
    @CrossOrigin
    @RequestMapping( value = "/users/{email}/groups", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { VIEW_GROUP, VIEW_AGENT_GROUP } )
    public GroupDetailResponse getGroupsByAgent( @PathVariable final String email ) {
        LOGGER.info( "Fetching groups for agent : " + email );
        return groupManagementBusinessService.getGroupsByAgent( email );
    }

    /**
     * Update agents groups.
     *
     * @param email
     *            the email
     * @param groupNames
     *            the group names
     * @return the base response
     */
    @CrossOrigin
    @RequestMapping( value = "/users/{email}/groups", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( UPDATE_GROUP )
    public BaseResponse updateAgentsGroups( @PathVariable final String email,
            @RequestBody final List< String > groupNames ) {
        return groupManagementBusinessService.updateAgentsGroups( email, groupNames );
    }

    /**
     * Gets the activity details by user.
     *
     * @param input
     *            the input
     * @return the activity by user
     */
    @CrossOrigin
    @RequestMapping( value = "/users/activity", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { VIEW_USER_ACTIVITY } )
    public BaseResponse getUserActivity( @RequestParam( required = true ) final String input ) {
        LOGGER.info( "Fetching activity for user : " + input );
        userRoleValidator.validateUserActivityInput( input );
        final BaseResponse buyerActivityResponse = userBusinessService.getUserActivityDetails( input );
        LOGGER.info( "Fetching activity for user response : {}", JsonUtil.toJson( buyerActivityResponse ) );
        return buyerActivityResponse;
    }

    /**
     * Validate check in.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param request
     *            the request
     * @return the agent response
     */
    @CrossOrigin
    @RequestMapping( value = "/users/agents/{agentId}/tasks/{taskId}/validate-checkin", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { VALIDATE_AGENT_TASK } )
    public AgentResponse validateCheckIn( @PathVariable final String agentId, @PathVariable final String taskId,
            @RequestBody( required = false ) final TaskUpdateRequest request ) {
        return agentTaskBusinessService.handleAgentTask( agentId, taskId, request );
    }

    /**
     * Gets the checkin details.
     *
     * @param search
     *            the search
     * @return the checkin details
     */
    @CrossOrigin
    @RequestMapping( value = "/users/checkin-details", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { VIEW_CHECK_IN } )
    public CheckinDetailResponse getCheckinDetails( @RequestParam( required = true ) final String search,
            @RequestParam( required = false ) @Date( format = DEFAULT_CRM_DATE_PATTERN ) final String fromDate,
            @RequestParam( required = false ) @Date( format = DEFAULT_CRM_DATE_PATTERN ) final String toDate ) {
        return agentTaskBusinessService.getCheckinDetails( search, fromDate, toDate );
    }

    /**
     * Gets the questionnaire.
     *
     * @param type
     *            the type
     * @return the questionnaire
     */
    @CrossOrigin
    @RequestMapping( value = "/users/questionnaire", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { VIEW_CHECK_IN } )
    public Set< String > getQuestionnaire( @RequestParam final String type ) {
        return agentTaskBusinessService.getQuestionnaire( type );
    }

    /**
     * Answer questionnaire.
     *
     * @param type
     *            the type
     * @param taskId
     *            the task id
     * @param request
     *            the request
     * @return the base response
     */
    @CrossOrigin
    @RequestMapping( value = "/users/answer-questionnaire", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { VIEW_CHECK_IN } )
    public BaseResponse answerQuestionnaire( @RequestBody( required = true ) final Map< String, Object > request ) {
        return agentTaskBusinessService.answerQuestionnaire( request );
    }

    /**
     * Gets the google calendar events by agent email.
     *
     * @param agentEmail
     *            the agent Email
     * @param timeMin
     *            the timeMin
     * @param timeMax
     *            the timeMax
     * @return the google calendar events by agent email.
     */
    @CrossOrigin
    @RequestMapping( value = {
            "/users/agents/{agentEmail}/calendar/events" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @Secured( { VIEW_GOOGLE_CALENDAR_EVENTS } )
    @PerformanceLog
    public BaseResponse getGoogleCalendarEvents( @PathVariable final String agentEmail,
            @RequestParam( required = false ) final Long timeMin,
            @RequestParam( required = false ) final Long timeMax ) {
        userRoleValidator.validateByAgentEmail( agentEmail );
        return userBusinessService.getGoogleCalendarEvents( agentEmail, timeMin, timeMax );
    }

    @CrossOrigin
    @RequestMapping( value = {
            "/users/{userId}/saveSearchDetails" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @Secured( { SAVE_SEARCH } )
    @PerformanceLog
    public SaveSearchResponse viewExistSaveSearch(
            @NotBlank( message = "error.userId.required" ) @PathVariable final String userId ) {
        userRoleValidator.validateUserId( userId );
        return userBusinessService.viewExistSaveSearch( userId );
    }

    /**
     * Gets the locations details in the given location.
     *
     * @param location
     *            the location
     * @return the locations details
     */
    @CrossOrigin
    @RequestMapping( value = "/suggetions/{suggetionId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @PerformanceLog
    @Secured( { SAVE_SEARCH } )
    public SuggestionsResponse getSuggetions( @PathVariable final String suggetionId ) {
        LOGGER.info( "getting location details for location id" + suggetionId );
        return propertyBusinessService.getAllOptionsInLocation( suggetionId );
    }

    /**
     * 
     * @param userId
     * @return
     */
    @CrossOrigin
    @RequestMapping( value = "/users/SaveSearch", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE )
    @PerformanceLog
    @Secured( { SAVE_SEARCH } )
    public Object updateSaveSearch( @RequestBody( required = true ) final SavedSearchChimeraRequest savedSearchRequest ) {
        final Object object = userBusinessService.updateSaveSearch( savedSearchRequest );
        return object;
    }

    /**
     * 
     * @param userId
     * @return
     */
    @CrossOrigin
    @RequestMapping( value = "/users/SaveSearch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )
    @PerformanceLog
    @Secured( { SAVE_SEARCH } )
    public Object createSaveSearch( @RequestBody( required = true ) final SavedSearchChimeraRequest savedSearchRequest ) {
        final Object object = userBusinessService.createSaveSearch( savedSearchRequest );
        return object;
    }

    /**
     * 
     * @param userId
     * @return
     */
    @CrossOrigin
    @RequestMapping( value = "/users/{emailId}/meetings/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @PerformanceLog
    @Secured( { SAVE_SEARCH } )
    public ScheduleMeetingResponse getAgentScheduleMeetingDetails( @PathVariable final String emailId,
            @RequestParam( required = false ) final String fromDate,
            @RequestParam( required = false ) final String toDate ) {
    	LOGGER.info( "getting schedule meeting of agent : {} ", emailId );
        return userBusinessService.getScheduleMeetingDetails( emailId, fromDate, toDate, true );
    }

    /**
     * 
     * @param userId
     * @return
     */
    @CrossOrigin
    @RequestMapping( value = "/users/meetings/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @PerformanceLog
    @Secured( { SAVE_SEARCH } )
    public ScheduleMeetingResponse getScheduleMeetingDetails( @RequestParam( required = false ) final String fromDate,
            @RequestParam( required = false ) final String toDate ) {
    	LOGGER.info( "getting schedule meeting of all the agents ");
        return userBusinessService.getScheduleMeetingDetails( null, fromDate, toDate, null );
    }

    /**
     * 
     * @param agentEmail
     * @param dueDtm
     * @return
     */
    @CrossOrigin
    @RequestMapping( value = {
            "/users/agents/{agentEmail}/calendar/events/exists" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @Secured( { ADD_SCHEDULED_MEETING } )
    @PerformanceLog
    public BaseResponse isAgentEventExists( @PathVariable final String agentEmail,
            @RequestParam( required = true ) final Long dueDtm ) {
        LOGGER.info( "checking if the events exists for agent : {} for dueDtm : {}" + agentEmail, dueDtm );
        return userBusinessService.isAgentEventExists( agentEmail, dueDtm );
    }
    
    /**
     * Checks if meeting is scheduled for a given agent and opportunity
     * combination
     * 
     * @param agentEmail
     *            the agent email Id
     * @param crmId
     *            the crm id
     * @return CheckScheduleMeetingValidationResponse
     */
    @CrossOrigin
    @RequestMapping( value = {
            "/users/agents/{agentEmail}/opportunity/{crmId}/calendar/events/exists" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @Secured( { ADD_SCHEDULED_MEETING } )
    @PerformanceLog
    public BaseResponse isAgentScheduledMeetingExists( @PathVariable final String agentEmail,
            @PathVariable final String crmId ) {
        LOGGER.info( "checking if the events exists for agent : {} for crmId : {}", agentEmail, crmId );
        return agentTaskBusinessService.checkIfAgentMeetingEventExists( agentEmail, crmId );
    }

    /**
     * 
     * @param userId
     * @return
     */
    @CrossOrigin
    @RequestMapping( value = "/users/update/notification/config", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )
    @PerformanceLog
    @Secured( { SAVE_SEARCH } )
    public BaseResponse updateNotificationConfig(
            @RequestBody( required = true ) final UserNotificationConfigDetails userNotificationConfigDetails ) {
        LOGGER.info("updateNotificationConfig request-> {}", userNotificationConfigDetails);
        userNotificationConfigDetails.setNotificationType( NotificationType.SAVE_SEARCH_SEARCHID );
        return userBusinessService.updateNotificationConfig( userNotificationConfigDetails );
    }
}
