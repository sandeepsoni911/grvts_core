package com.owners.gravitas.controller;

import static com.owners.gravitas.constants.UserPermission.ADD_AGENT_ACTION;
import static com.owners.gravitas.constants.UserPermission.ADD_AGENT_NOTE;
import static com.owners.gravitas.constants.UserPermission.ADD_AGENT_OPPORTUNITY;
import static com.owners.gravitas.constants.UserPermission.ADD_AGENT_SUGGESTED_PROPERTY;
import static com.owners.gravitas.constants.UserPermission.ADD_AGENT_TASK;
import static com.owners.gravitas.constants.UserPermission.ADD_DEVICE;
import static com.owners.gravitas.constants.UserPermission.ADD_GOOGLE_CALENDAR_EVENT;
import static com.owners.gravitas.constants.UserPermission.ADD_REMINDER;
import static com.owners.gravitas.constants.UserPermission.ADD_USER;
import static com.owners.gravitas.constants.UserPermission.COMPLETE_AGENT_TASK;
import static com.owners.gravitas.constants.UserPermission.DELETE_AGENT_NOTE;
import static com.owners.gravitas.constants.UserPermission.DELETE_AGENT_TASK;
import static com.owners.gravitas.constants.UserPermission.DELETE_DEVICE;
import static com.owners.gravitas.constants.UserPermission.DELETE_REMINDER;
import static com.owners.gravitas.constants.UserPermission.SEND_BUYER_REQUEST_EMAIL;
import static com.owners.gravitas.constants.UserPermission.UPDATE_AGENT;
import static com.owners.gravitas.constants.UserPermission.UPDATE_AGENT_CONTACT;
import static com.owners.gravitas.constants.UserPermission.UPDATE_AGENT_NOTE;
import static com.owners.gravitas.constants.UserPermission.UPDATE_AGENT_OPPORTUNITY;
import static com.owners.gravitas.constants.UserPermission.UPDATE_AGENT_SIGNATURE;
import static com.owners.gravitas.constants.UserPermission.UPDATE_AGENT_TASK;
import static com.owners.gravitas.constants.UserPermission.UPDATE_LAST_VIEWED;
import static com.owners.gravitas.constants.UserPermission.UPDATE_REMINDER;
import static com.owners.gravitas.constants.UserPermission.UPDATE_USER;
import static com.owners.gravitas.constants.UserPermission.VIEW_AGENT_ACTION;
import static com.owners.gravitas.constants.UserPermission.VIEW_AGENT_COMMISSION;
import static com.owners.gravitas.constants.UserPermission.VIEW_AGENT_METRICS;
import static com.owners.gravitas.constants.UserPermission.VIEW_BEST_FIT_AGENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.owners.gravitas.annotation.PerformanceLog;
import com.owners.gravitas.annotation.ReadArgs;
import com.owners.gravitas.business.AgentBusinessService;
import com.owners.gravitas.business.AgentContactBusinessService;
import com.owners.gravitas.business.AgentLookupBusinessService;
import com.owners.gravitas.business.AgentOpportunityBusinessService;
import com.owners.gravitas.business.AgentReminderBusinessService;
import com.owners.gravitas.business.AgentReportsBusinessService;
import com.owners.gravitas.business.AgentTaskBusinessService;
import com.owners.gravitas.business.BeanValidationService;
import com.owners.gravitas.domain.OpportunityDataNode;
import com.owners.gravitas.dto.ApiUser;
import com.owners.gravitas.dto.UserAddress;
import com.owners.gravitas.dto.request.ActionRequest;
import com.owners.gravitas.dto.request.AgentDeviceRequest;
import com.owners.gravitas.dto.request.AgentLeadRequest;
import com.owners.gravitas.dto.request.AgentNoteRequest;
import com.owners.gravitas.dto.request.AgentOnboardRequest;
import com.owners.gravitas.dto.request.AgentSuggestedPropertyRequest;
import com.owners.gravitas.dto.request.AgentTaskRequest;
import com.owners.gravitas.dto.request.EmailRequest;
import com.owners.gravitas.dto.request.LastViewedRequest;
import com.owners.gravitas.dto.request.OpportunityDataRequest;
import com.owners.gravitas.dto.request.ReminderRequest;
import com.owners.gravitas.dto.request.TaskUpdateRequest;
import com.owners.gravitas.dto.response.ActionLogResponse;
import com.owners.gravitas.dto.response.AgentEmailResponse;
import com.owners.gravitas.dto.response.AgentResponse;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.util.GravitasWebUtil;
import com.owners.gravitas.validator.ContactUpdateRequestValidator;
import com.owners.gravitas.validator.OpportunityUpdateRequestValidator;
import com.owners.gravitas.validator.UserRoleValidator;

/**
 * Test AgentController for setting up the AWS API gateway.
 *
 * @author manishd
 */
@RestController
public class AgentController extends BaseController {

    /** The Constant LOGGER. */
    private static final Logger LOG = LoggerFactory.getLogger( AgentController.class );

    /** The gravitas web util. */
    @Autowired
    private GravitasWebUtil gravitasWebUtil;

    /** The role validator. */
    @Autowired
    private UserRoleValidator roleValidator;

    /** The agent opportunity business service. */
    @Autowired
    private AgentBusinessService agentBusinessService;

    /** The agent contact business service. */
    @Autowired
    private AgentContactBusinessService agentContactBusinessService;

    /** The agent opportunity business service. */
    @Autowired
    private AgentOpportunityBusinessService agentOpportunityBusinessService;

    /** The agent lookup business service. */
    @Autowired
    private AgentLookupBusinessService agentLookupBusinessService;

    /** The contact update request validator. */
    @Autowired
    private ContactUpdateRequestValidator contactUpdateRequestValidator;

    /** The opportunity update request validator. */
    @Autowired
    private OpportunityUpdateRequestValidator opportunityUpdateRequestValidator;

    /** The agent remider business service. */
    @Autowired
    private AgentReminderBusinessService agentReminderBusinessService;

    /** The agent task business service. */
    @Autowired
    private AgentTaskBusinessService agentTaskBusinessService;

    /** The agent reports business service. */
    @Autowired
    private AgentReportsBusinessService agentReportsBusinessService;

    /** The bean validation service. */
    @Autowired
    private BeanValidationService beanValidationService;

    /**
     * An API to Register agent if logging in to first time.
     *
     * @return the agent response
     */
    @CrossOrigin
    @RequestMapping( value = "/agents", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    public AgentResponse registerAgent() {
        final ApiUser user = gravitasWebUtil.getAppUser();
        return agentBusinessService.register( user.getUid(), user.getEmail() );
    }

    /**
     * Add agent notes.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param request
     *            the request
     * @return the opportunity response
     */
    @CrossOrigin
    @RequestMapping( value = "/agents/{agentId}/opportunities/{opportunityId}/notes", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { ADD_AGENT_NOTE } )
    public AgentResponse addAgentNote( @PathVariable final String agentId, @PathVariable final String opportunityId,
            @Valid @RequestBody final AgentNoteRequest request ) {
        roleValidator.validateByAgentId( agentId );
        return agentBusinessService.addAgentNote( agentId, opportunityId, request );
    }

    /**
     * Update agent note.
     *
     * @param agentId
     *            the agent id
     * @param noteId
     *            the notes id
     * @param request
     *            the request
     * @return the agent response
     */
    @CrossOrigin
    @RequestMapping( value = "/agents/{agentId}/notes/{noteId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { UPDATE_AGENT_NOTE } )
    public AgentResponse updateAgentNote( @PathVariable final String agentId, @PathVariable final String noteId,
            @Valid @RequestBody final AgentNoteRequest request ) {
        roleValidator.validateByAgentId( agentId );
        return agentBusinessService.updateAgentNote( agentId, noteId, request );
    }

    /**
     * Delete agent note.
     *
     * @param agentId
     *            the agent id
     * @param noteId
     *            the note id
     * @return the agent response
     */
    @CrossOrigin
    @RequestMapping( value = "/agents/{agentId}/notes/{noteId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { DELETE_AGENT_NOTE } )
    public AgentResponse deleteAgentNote( @PathVariable final String agentId, @PathVariable final String noteId ) {
        roleValidator.validateByAgentId( agentId );
        return agentBusinessService.deleteAgentNote( agentId, noteId );
    }

    /**
     * Update agent stage.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param request
     *            the request
     * @return the opportunity response
     */
    @CrossOrigin
    @RequestMapping( value = "/agents/{agentId}/opportunities/{opportunityId}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { UPDATE_AGENT_OPPORTUNITY } )
    public AgentResponse updateAgentOpportunity( @PathVariable final String agentId,
            @PathVariable final String opportunityId, @RequestBody final Map< String, Object > request,
            final HttpServletRequest httpRequest ) {
        roleValidator.validateByAgentId( agentId );
        LOG.info( "updateAgentOpportunity called for Agent : " + agentId + " Request : " + request );
        opportunityUpdateRequestValidator.validateOpportunityRequest( request );
        request.put( "httpRequest", httpRequest );
        return agentOpportunityBusinessService.patchOpportunity( agentId, opportunityId, request );
    }

    /**
     * Adds the device.
     *
     * @param agentId
     *            the agent id
     * @param request
     *            the request
     * @return the agent response
     */
    @CrossOrigin
    @RequestMapping( value = "/agents/{agentId}/devices", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { ADD_DEVICE } )
    public AgentResponse addDevice( @PathVariable final String agentId,
            @Valid @RequestBody final AgentDeviceRequest request ) {
        roleValidator.validateByAgentId( agentId );
        return agentBusinessService.addDevice( agentId, request );
    }

    /**
     * Removes the device.
     *
     * @param agentId
     *            the agent id
     * @param deviceId
     *            the device id
     * @return the agent response
     */
    @CrossOrigin
    @RequestMapping( value = "/agents/{agentId}/devices/{deviceId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { DELETE_DEVICE } )
    public AgentResponse removeDevice( @PathVariable final String agentId, @PathVariable final String deviceId ) {
        roleValidator.validateByAgentId( agentId );
        return agentBusinessService.removeDevice( agentId, deviceId );
    }

    /**
     * Synchronize agent with the all opportunities details.
     *
     * @param agentId
     *            the agent id
     * @param contactId
     *            the contact id
     * @param updateRequest
     *            the update request
     * @return the agent response
     */
    // @CrossOrigin
    // @RequestMapping( value = "/agents/{agentId}", method = RequestMethod.PUT,
    // produces = MediaType.APPLICATION_JSON_VALUE )
    // @ReadArgs
    // @PerformanceLog
    // @Secured( { AGENT } )
    // public AgentResponse synchronizeAgent( @PathVariable final String agentId
    // ) {
    // roleValidator.getValidAgent( agentId );
    // return agentOpportunityBusinessService.synchronizeAgentOpportunities(
    // agentId );
    // }

    /**
     * Update contact.
     *
     * @param agentId
     *            the agent id
     * @param contactId
     *            the contact id
     * @param updateRequest
     *            the update request
     * @return the agent response
     */
    @CrossOrigin
    @RequestMapping( value = "/agents/{agentId}/contacts/{contactId}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { UPDATE_AGENT_CONTACT } )
    public AgentResponse updateContact( @PathVariable final String agentId, @PathVariable final String contactId,
            @Valid @RequestBody final Map< String, String > updateRequest ) {
        roleValidator.validateByAgentId( agentId );
        contactUpdateRequestValidator.validate( updateRequest );
        return agentContactBusinessService.updateContact( agentId, contactId, updateRequest );
    }

    /**
     * Creates the agent task.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param taskRequest
     *            the task request
     * @return the agent response
     */
    @CrossOrigin
    @RequestMapping( value = "/agents/{agentId}/opportunities/{opportunityId}/tasks", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { ADD_AGENT_TASK } )
    public AgentResponse createAgentTask( @PathVariable final String agentId, @PathVariable final String opportunityId,
            @Valid @RequestBody final AgentTaskRequest taskRequest ) {
        roleValidator.validateByAgentId( agentId );
        return agentTaskBusinessService.createAgentTask( agentId, opportunityId, taskRequest );
    }

    /**
     * Complete task.
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
    @RequestMapping( value = "/agents/{agentId}/tasks/{taskId}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { UPDATE_AGENT_TASK } )
    public AgentResponse updateAgentTask( @PathVariable final String agentId, @PathVariable final String taskId,
            @RequestBody( required = false ) final TaskUpdateRequest request ) {
        roleValidator.validateByAgentId( agentId );
        return agentTaskBusinessService.handleAgentTask( agentId, taskId, request );
    }

    /**
     * Complete task.
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
    @RequestMapping( value = "/agents/{agentId}/tasks/{taskId}/complete", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { COMPLETE_AGENT_TASK } )
    public AgentResponse completeAgentTask( @PathVariable final String agentId, @PathVariable final String taskId,
            @RequestBody( required = false ) final TaskUpdateRequest request ) {
        roleValidator.validateByAgentId( agentId );
        return agentTaskBusinessService.completeAgentTask( agentId, taskId, request );
    }

    /**
     * Sets the reminder.
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
    @RequestMapping( value = "/agents/{agentId}/tasks/{taskId}/reminder", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { ADD_REMINDER } )
    public AgentResponse setReminder( @PathVariable final String agentId, @PathVariable final String taskId,
            @Valid @RequestBody final ReminderRequest request ) {
        roleValidator.validateByAgentId( agentId );
        return agentReminderBusinessService.setReminder( agentId, taskId, request );
    }
    
    /**
     * Sets the reminder.
     *
     * @param agentId
     *            the agent id
     * @param request
     *            the request
     * @return the agent response
     */
    @CrossOrigin
    @RequestMapping( value = "/agents/{agentId}/reminder", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { ADD_REMINDER } )
    public AgentResponse setReminder( @PathVariable final String agentId,@Valid @RequestBody final ReminderRequest request ) {
        roleValidator.validateByAgentId( agentId );
        return agentReminderBusinessService.setReminder( agentId, request );
    }
    

    /**
     * Delete reminder.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param reminderId
     *            the reminder id
     * @return the agent response
     */
    @CrossOrigin
    @RequestMapping( value = "/agents/{agentId}/tasks/{taskId}/reminders/{reminderId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { DELETE_REMINDER } )
    public AgentResponse deleteReminder( @PathVariable final String agentId, @PathVariable final String taskId,
            @PathVariable final String reminderId ) {
        roleValidator.validateByAgentId( agentId );
        return agentReminderBusinessService.deleteReminder( agentId, taskId, reminderId );
    }

    /**
     * Update agent task.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param reminderId
     *            the reminder id
     * @param updateReminder
     *            the request
     * @return the agent response
     */
    @CrossOrigin
    @RequestMapping( value = "/agents/{agentId}/tasks/{taskId}/reminders/{reminderId}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { UPDATE_REMINDER } )
    public AgentResponse updateReminder( @PathVariable final String agentId, @PathVariable final String taskId,
            @PathVariable final String reminderId, @Valid @RequestBody final ReminderRequest updateReminder ) {
        roleValidator.validateByAgentId( agentId );
        return agentReminderBusinessService.updateReminder( agentId, taskId, reminderId, updateReminder );
    }

    /**
     * Update last viewed date time for given id.
     *
     * @param agentId
     *            the agent id
     * @param id
     *            the id of node object to be updated.
     * @param viewedRequest
     *            the viewed request
     * @return the agent response
     */
    @CrossOrigin
    @RequestMapping( value = "/agents/{agentId}/{id}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { UPDATE_LAST_VIEWED } )
    public AgentResponse updateLastViewed( @PathVariable final String agentId, @PathVariable final String id,
            @Valid @RequestBody final LastViewedRequest viewedRequest ) {
        roleValidator.validateByAgentId( agentId );
        return agentBusinessService.updateLastViewed( agentId, id, viewedRequest );
    }

    /**
     * Creates the agent action.
     *
     * @param agentId
     *            the agent id
     * @param actionRequest
     *            the action request
     * @return the base response
     */
    @CrossOrigin
    @RequestMapping( value = "/agents/{agentId}/actions", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { ADD_AGENT_ACTION } )
    public BaseResponse createAgentAction( @PathVariable final String agentId,
            @Valid @RequestBody final ActionRequest actionRequest ) {
        final ApiUser user = roleValidator.validateByAgentId( agentId );
        agentBusinessService.updateFirstContactTime( agentId, actionRequest );
        return agentBusinessService.createAgentAction( user.getEmail(), actionRequest );
    }

    /**
     * Gets the CTA audit logs.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param ctaType
     *            the cta type
     * @return the CTA audit logs
     */
    @CrossOrigin
    @RequestMapping( value = "/agents/{agentId}/opportunities/{opportunityId}/actions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @Secured( { VIEW_AGENT_ACTION } )
    @PerformanceLog
    public ActionLogResponse getCTAAuditLogs( @PathVariable final String agentId,
            @PathVariable final String opportunityId, @RequestParam( required = false ) final String ctaType ) {
        roleValidator.validateByAgentId( agentId );
        return agentBusinessService.getCTAAuditLogs( agentId, opportunityId, ctaType );
    }

    /**
     * Gets the agent metrics.
     *
     * @param agentId
     *            the agent id
     * @param timeFrame
     *            the time frame
     * @return the agent metrics
     */
    @CrossOrigin
    @RequestMapping( value = "/agents/{agentId}/metrics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @Secured( { VIEW_AGENT_METRICS } )
    @PerformanceLog
    public Map< String, Object > getAgentMetrics( @PathVariable final String agentId,
            @RequestParam( required = false ) final Integer timeFrame ) {
        roleValidator.validateByAgentId( agentId );
        return agentReportsBusinessService.getPerformanceLog( agentId, timeFrame );
    }

    /**
     * Creates a self generated opportunity.
     *
     * @param agentId
     *            the agent id
     * @param agentLeadRequest
     *            the generic lead request
     * @return the base response
     */
    @CrossOrigin
    @ReadArgs
    @PerformanceLog
    @Secured( { ADD_AGENT_OPPORTUNITY } )
    @RequestMapping( value = "/agents/{agentId}/opportunities", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public BaseResponse createAgentOpportunity( @PathVariable final String agentId,
            @RequestBody @Valid final AgentLeadRequest agentLeadRequest ) {
        LOG.info( "Request received for creating a oppportunity thorugh agent app" );
        final ApiUser user = roleValidator.validateByAgentId( agentId );
        return agentOpportunityBusinessService.createAgentOpportunity( user.getEmail(), agentLeadRequest );
    }

    /**
     * Onboard.
     *
     * @param request
     *            the request
     * @return the base response
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @CrossOrigin
    @RequestMapping( value = "/agents/onboard", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    @Secured( { ADD_USER } )
    @PerformanceLog
    public BaseResponse onboard( final MultipartHttpServletRequest request ) throws IOException {
        final AgentOnboardRequest onboardRequest = buildOnboardRequest( request );
        beanValidationService.validateData( onboardRequest );
        return agentBusinessService.onboard( onboardRequest );
    }

    /**
     * Update agent.
     *
     * @param emailId
     *            the email id
     * @param request
     *            the request
     * @return the base response
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @CrossOrigin
    @RequestMapping( value = "/agents/update/{emailId}", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    @Secured( { UPDATE_USER } )
    @PerformanceLog
    public BaseResponse updateAgent( @PathVariable final String emailId, final MultipartHttpServletRequest request )
            throws IOException {
        final AgentOnboardRequest onboardRequest = buildOnboardRequest( request );
        beanValidationService.validateData( onboardRequest );
        return agentBusinessService.updateAgent( emailId, onboardRequest );
    }

    /**
     * Gets the best performing agents by zipcode V 1.
     *
     * @param zipcode
     *            the zipcode
     * @param crmOpportunityId
     *            the crm opportunity id
     * @param state
     *            the state
     * @return the best performing agents by zipcode V 1
     */
    @CrossOrigin
    @RequestMapping( value = "/agents/v1/bestFit", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE )
    @Secured( { VIEW_BEST_FIT_AGENT } )
    @PerformanceLog
    @ReadArgs
    public Map< String, Object > getBestPerformingAgentsByZipcodeV1( @RequestParam final String zipcode,
            @RequestParam final String crmOpportunityId, @RequestParam final String state ) {
        LOG.info( "finding bestFit agent for opp: " + crmOpportunityId + " zip: " + zipcode + " state: " + state );
        return agentLookupBusinessService.getBestPerformingAgentsByZipcodeV1( zipcode, crmOpportunityId, state );
    }

    /**
     * Send buyer request email.
     *
     * @param agentId
     *            the agent id
     * @param emailRequest
     *            the email request
     * @return the agent response
     */
    @CrossOrigin
    @RequestMapping( value = { "/agents/{agentId}/sendEmail",
            "/agents/{agentId}/email/send" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { SEND_BUYER_REQUEST_EMAIL } )
    public AgentResponse sendBuyerRequestEmail( @PathVariable final String agentId,
            @Valid @RequestBody final EmailRequest emailRequest ) {
        final ApiUser user = roleValidator.validateByAgentId( agentId );
        return agentBusinessService.sendAgentEmail( user.getEmail(), emailRequest );
    }

    /**
     * Send multiple buyer request emails.
     *
     * @param agentId
     *            the agent id
     * @param emailRequests
     *            the email requests
     * @return the agent email response
     */
    @CrossOrigin
    @RequestMapping( value = { "/agents/{agentId}/sendMultipleEmail",
            "/agents/{agentId}/send-email" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { SEND_BUYER_REQUEST_EMAIL } )
    public AgentEmailResponse sendMultipleBuyerRequestEmails( @PathVariable final String agentId,
            @RequestBody final List< EmailRequest > emailRequests ) {
        final ApiUser user = roleValidator.validateByAgentId( agentId );
        return agentBusinessService.sendAgentEmails( user.getEmail(), emailRequests );
    }

    /**
     * Sync task with google calendar.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @return the base response
     */
    @CrossOrigin
    @RequestMapping( value = { "/agents/{agentId}/tasks/{taskId}/addToGoogleCalendar",
            "/agents/{agentId}/tasks/{taskId}/calender" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @Secured( { ADD_GOOGLE_CALENDAR_EVENT } )
    @PerformanceLog
    public AgentResponse addToGoogleCalendar( @PathVariable final String agentId, @PathVariable final String taskId ) {
        final ApiUser user = roleValidator.validateByAgentId( agentId );
        return agentTaskBusinessService.addToGoogleCalendar( agentId, taskId, user.getEmail() );
    }

    /**
     * Update agent.
     *
     * @param agentId
     *            the agent id
     * @param request
     *            the request
     * @return the base response
     */
    @CrossOrigin
    @RequestMapping( value = "/agents/{agentId}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { UPDATE_AGENT } )
    public BaseResponse updateAgent( @PathVariable final String agentId,
            @RequestBody final Map< String, Object > request ) {
        roleValidator.validateByAgentId( agentId );
        return agentBusinessService.updateAgent( agentId, request );
    }

    /**
     * Update actionflow.
     *
     * @param agentId
     *            the agent id
     * @param actionFlowId
     *            the action flow id
     * @param actionId
     *            the action id
     * @param actionMap
     *            the action map
     * @return the base response
     */
    @CrossOrigin
    @RequestMapping( value = "/agents/{agentId}/actionFlow/{actionFlowId}/action/{actionId}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { UPDATE_AGENT } )
    public BaseResponse updateActionflow( @PathVariable final String agentId, @PathVariable final String actionFlowId,
            @PathVariable final String actionId,
            @RequestBody( required = false ) final Map< String, Object > actionMap ) {
        LOG.info( "Update action flow for action id : " + actionId );
        roleValidator.validateByAgentId( agentId );
        return agentBusinessService.updateAction( agentId, actionFlowId, actionId, actionMap );
    }

    /**
     * Delete agent task.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @return the agent response
     */
    @CrossOrigin
    @RequestMapping( value = "/agents/{agentId}/tasks/{taskId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { DELETE_AGENT_TASK } )
    public AgentResponse deleteAgentTask( @PathVariable final String agentId, @PathVariable final String taskId ) {
        final ApiUser apiUser = roleValidator.validateByAgentId( agentId );
        return agentTaskBusinessService.deleteAgentTask( agentId, taskId, apiUser );
    }

    /**
     * Gets the agent commission.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @return the agent commission
     */
    @CrossOrigin
    @RequestMapping( value = "/agents/{agentId}/opportunities/{opportunityId}/commission", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE )
    @Secured( { VIEW_AGENT_COMMISSION } )
    @PerformanceLog
    public BaseResponse getAgentCommission( @PathVariable final String agentId,
            @PathVariable final String opportunityId ) {
        roleValidator.validateByAgentId( agentId );
        return agentOpportunityBusinessService.getOpportunityCommission( agentId, opportunityId );
    }

    /**
     * Creates the agent suggested property.
     *
     * @param agentId
     *            the agent id
     * @param suggestPropertyRequest
     *            the task request
     * @return the agent response
     */
    @CrossOrigin
    @RequestMapping( value = "/agents/{agentId}/suggestedProperty", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { ADD_AGENT_SUGGESTED_PROPERTY } )
    public AgentResponse createAgentSuggestedProperty( @PathVariable final String agentId,
            @Valid @RequestBody final AgentSuggestedPropertyRequest agentSuggestedPropertyRequest ) {
        roleValidator.validateByAgentId( agentId );
        return agentBusinessService.createAgentSuggestedProperty( agentId, agentSuggestedPropertyRequest );
    }

    /**
     * 
     * @param agentId
     *            the agent id
     * @param signature
     *            the signature text to update
     * @return the base response
     */
    @CrossOrigin
    @RequestMapping( value = "/agents/{agentId}/preferences/signature", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { UPDATE_AGENT_SIGNATURE } )
    public BaseResponse saveAgentSignature( @PathVariable final String agentId,
            @Valid @RequestBody( required = true ) final Object signature ) {
        roleValidator.validateByAgentId( agentId );
        return agentBusinessService.saveAgentSignature( agentId, signature );
    }

    /**
     * Save agent preferences data.
     *
     * @param agentId
     *            the agent id
     * @param path
     *            the path
     * @param agentSpecific
     *            the agent specific
     * @param preferenceData
     *            the preference data
     * @return the base response
     */
    @CrossOrigin
    @RequestMapping( value = "/agents/{agentId}/add/preferences", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { UPDATE_AGENT_SIGNATURE } )
    public BaseResponse saveAgentPreferencesData( @PathVariable final String agentId,
            @RequestParam( required = true ) final String path,
            @RequestParam( required = true ) final Boolean agentSpecific,
            @Valid @RequestBody( required = true ) final Map< String, Object > preferenceData ) {
        roleValidator.validateByAgentId( agentId );
        return agentBusinessService.saveAgentPreferencesData( agentId, path, agentSpecific, preferenceData );
    }

    /**
     * Builds the onboard request.
     *
     * @param request
     *            the request
     * @return the onboard request
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private AgentOnboardRequest buildOnboardRequest( final MultipartHttpServletRequest request ) throws IOException {
        final AgentOnboardRequest onboardRequest = new AgentOnboardRequest();
        onboardRequest.setFirstName( request.getParameter( "firstName" ) );
        onboardRequest.setLastName( request.getParameter( "lastName" ) );
        onboardRequest.setEmail( request.getParameter( "email" ) );
        onboardRequest.setPhone( request.getParameter( "phone" ) );
        onboardRequest.setBiodata( request.getParameter( "biodata" ) );
        onboardRequest.setPhotoData( getPhotoData( request.getFileMap() ) );
        onboardRequest.setPersonalEmail( request.getParameter( "personalEmail" ) );
        onboardRequest.setLanguage( request.getParameter( "language" ) );
        onboardRequest.setRoleId( "1099_AGENT" );
        onboardRequest.setType( "Field Agent" );
        onboardRequest.setManagingBrokerId( request.getParameter( "managingBrokerId" ) );
        onboardRequest.setMobileCarrier( request.getParameter( "mobileCarrier" ) );
        onboardRequest.setAgentStartingDate( request.getParameter( "agentStartingDate" ) );
        onboardRequest.setNotes( request.getParameter( "notes" ) );
        onboardRequest.setDrivingRadius( request.getParameter( "drivingRadius" ) );
        onboardRequest.setStatus( request.getParameter( "status" ) );
        onboardRequest.setDeleteFile( Boolean.valueOf( request.getParameter( "deleteFile" ) ) );
        onboardRequest.setLicense( request.getParameter( "license" ) );
        onboardRequest.setAvailable( true );

        final UserAddress address = new UserAddress();
        address.setAddress1( request.getParameter( "address1" ) );
        address.setAddress2( request.getParameter( "address2" ) );
        address.setCity( request.getParameter( "city" ) );
        address.setState( request.getParameter( "state" ) );
        address.setZip( request.getParameter( "zip" ) );
        onboardRequest.setAddress( address );

        return onboardRequest;
    }

    /**
     * Gets the photo data.
     *
     * @param files
     *            the files
     * @return the photo data
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private byte[] getPhotoData( final Map< String, MultipartFile > files ) throws IOException {
        byte[] data = null;
        for ( final MultipartFile file : files.values() ) {
            data = file.getBytes();
            break;
        }
        return data;
    }

    /**
     * Add agent notes.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param request
     *            the request
     * @return the opportunity response
     */
    @CrossOrigin
    @RequestMapping( value = "/agents/{agentId}/opportunities/{opportunityId}/data", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    public AgentResponse addOppsDataNode( @PathVariable final String agentId, @PathVariable final String opportunityId,
            @RequestBody final OpportunityDataRequest opportunityDataRequest ) {
        roleValidator.validateByAgentId( agentId );
        return agentBusinessService.addDataNode( agentId, opportunityId, opportunityDataRequest );
    }

    /**
     * Add agent notes.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param request
     *            the request
     * @return the opportunity response
     */
    @CrossOrigin
    @RequestMapping( value = "/agents/{agentId}/opportunities/{opportunityId}/data/{dataNodeKey}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    public OpportunityDataNode getOppsDataNode( @PathVariable final String agentId,
            @PathVariable final String opportunityId, @PathVariable final String dataNodeKey ) {
        roleValidator.validateByAgentId( agentId );
        return agentBusinessService.getDataNode( agentId, opportunityId, dataNodeKey );
    }

    /**
     * Checks if is crm id permitted.
     *
     * @param agentId
     *            the agent id
     * @param crmId
     *            the crm id
     * @return the base response
     */
    @CrossOrigin
    @RequestMapping( value = "/agents/{agentId}/permissions/crmid/{crmId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    public BaseResponse isCrmIdPermitted( @PathVariable final String agentId, @PathVariable final String crmId ) {

        roleValidator.validateByAgentId( agentId );
        return agentOpportunityBusinessService.isCrmIdPermitted( agentId, crmId );
    }
}
