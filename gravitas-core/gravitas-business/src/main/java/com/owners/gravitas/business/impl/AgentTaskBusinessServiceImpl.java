package com.owners.gravitas.business.impl;

import static com.owners.gravitas.constants.Constants.BLANK_SPACE;
import static com.owners.gravitas.constants.Constants.CLOSING_BRACKET;
import static com.owners.gravitas.constants.Constants.GRAVITAS;
import static com.owners.gravitas.constants.Constants.HYPHEN;
import static com.owners.gravitas.constants.Constants.OPENING_BRACKET;
import static com.owners.gravitas.constants.Constants.SYSTEM_GENERATED;
import static com.owners.gravitas.constants.Constants.TASK;
import static com.owners.gravitas.enums.AgentTaskStatus.CANCELLED;
import static com.owners.gravitas.enums.AgentTaskStatus.COMPLETE;
import static com.owners.gravitas.enums.AgentTaskStatus.COMPLETED;
import static com.owners.gravitas.enums.AgentTaskStatus.CONFIRMED;
import static com.owners.gravitas.enums.AgentTaskStatus.DELETED;
import static com.owners.gravitas.enums.AgentTaskStatus.INCOMPLETE;
import static com.owners.gravitas.enums.AgentTaskStatus.PENDING;
import static com.owners.gravitas.enums.AgentTaskStatus.UNASSIGNED;
import static com.owners.gravitas.enums.ErrorCode.AGENT_TASK_NOT_FOUND;
import static com.owners.gravitas.enums.ErrorCode.TASK_DELETE_NOT_ALLOWED_FOR_AGENT_WHEN_FLAG_IS_ON;
import static com.owners.gravitas.enums.PushNotificationType.SCHEDULED_TASK_APPOINTMENT_REMINDER;
import static com.owners.gravitas.enums.RecordType.BUYER;
import static com.owners.gravitas.enums.TaskType.CLAIM_OPPORTUNITY;
import static com.owners.gravitas.enums.TaskType.SCHEDULE_MEETING;
import static com.owners.gravitas.enums.TaskType.SCHEDULE_TOUR;
import static com.owners.gravitas.enums.TaskType.USER_DEFINED;
import static com.owners.gravitas.enums.TaskType.valueOf;
import static com.owners.gravitas.util.DateUtil.DEFAULT_CRM_DATE_PATTERN;
import static com.owners.gravitas.util.DateUtil.GRAVITAS_AUDIT_DATE_PATTERN;
import static com.owners.gravitas.util.DateUtil.ISO_DATE_FORMAT;
import static com.owners.gravitas.util.DateUtil.toSqlDate;
import static com.owners.gravitas.util.StringUtils.convertObjectToString;
import static com.owners.gravitas.util.StringUtils.isEmailIdValid;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.collections4.MapUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.api.services.calendar.model.Event;
import com.owners.gravitas.business.AgentNotificationBusinessService;
import com.owners.gravitas.business.AgentOpportunityBusinessService;
import com.owners.gravitas.business.AgentReminderBusinessService;
import com.owners.gravitas.business.AgentTaskBusinessService;
import com.owners.gravitas.business.PropertyBusinessService;
import com.owners.gravitas.business.builder.AgentTaskBuilder;
import com.owners.gravitas.business.builder.CoShoppingLeadBuilder;
import com.owners.gravitas.business.builder.ContactBuilder;
import com.owners.gravitas.business.builder.GoogleCalendarEventBuilder;
import com.owners.gravitas.business.builder.response.UserDetailsResponseBuilder;
import com.owners.gravitas.business.tour.TourConfirmationBusiness;
import com.owners.gravitas.config.AgentCheckinTaskConfig;
import com.owners.gravitas.config.CoShoppingConfig;
import com.owners.gravitas.config.LeadOpportunityBusinessConfig;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.domain.Reminder;
import com.owners.gravitas.domain.Request;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.domain.Task;
import com.owners.gravitas.domain.entity.AgentTask;
import com.owners.gravitas.domain.entity.AgentTaskStatusLog;
import com.owners.gravitas.domain.entity.CheckinFeedback;
import com.owners.gravitas.domain.entity.RefCode;
import com.owners.gravitas.dto.ApiUser;
import com.owners.gravitas.dto.CheckinDetailsDTO;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.OpportunityDetailsDTO;
import com.owners.gravitas.dto.TaskCheckinDetailsDTO;
import com.owners.gravitas.dto.TourDetails;
import com.owners.gravitas.dto.User;
import com.owners.gravitas.dto.request.AgentTaskRequest;
import com.owners.gravitas.dto.request.CoShoppingLeadRequest;
import com.owners.gravitas.dto.request.CoShoppingLeadUpdateModel;
import com.owners.gravitas.dto.request.CoShoppingLeadUpdateRequest;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.dto.request.TaskUpdateRequest;
import com.owners.gravitas.dto.response.AgentResponse;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.CheckScheduleMeetingValidationResponse;
import com.owners.gravitas.dto.response.CheckinDetailResponse;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.dto.response.PropertyDetailsResponse;
import com.owners.gravitas.dto.response.UserDetailsResponse;
import com.owners.gravitas.enums.LeadRequestType;
import com.owners.gravitas.enums.PushNotificationType;
import com.owners.gravitas.enums.RefType;
import com.owners.gravitas.enums.TaskType;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.service.AgentContactService;
import com.owners.gravitas.service.AgentDetailsService;
import com.owners.gravitas.service.AgentInfoService;
import com.owners.gravitas.service.AgentOpportunityService;
import com.owners.gravitas.service.AgentRequestService;
import com.owners.gravitas.service.AgentService;
import com.owners.gravitas.service.AgentTaskService;
import com.owners.gravitas.service.AgentTaskStatusLogService;
import com.owners.gravitas.service.CalendarService;
import com.owners.gravitas.service.CheckinFeedbackService;
import com.owners.gravitas.service.CoShoppingService;
import com.owners.gravitas.service.ContactEntityService;
import com.owners.gravitas.service.ContactService;
import com.owners.gravitas.service.MailService;
import com.owners.gravitas.service.OpportunityService;
import com.owners.gravitas.service.PropertyService;
import com.owners.gravitas.service.RefCodeService;
import com.owners.gravitas.service.RefTypeService;
import com.owners.gravitas.service.SearchService;
import com.owners.gravitas.service.UserService;
import com.owners.gravitas.util.DateUtil;
import com.owners.gravitas.util.JsonUtil;
import com.owners.gravitas.validator.AgentTaskValidator;
import com.zuner.coshopping.model.common.Resource;
import com.zuner.coshopping.model.lead.LeadUpdateRequest;

/**
 * The Class AgentTaskServiceBuilderImpl.
 *
 * @author amits
 */
@Service( "agentTaskBusinessService" )
public class AgentTaskBusinessServiceImpl implements AgentTaskBusinessService {

    /** The Constant SCHEDULED_TOUR. */
    private static final String SCHEDULED_TOUR = "Scheduled Tour";

    /** The Constant INSIDE_SALES. */
    private static final String INSIDE_SALES = "Inside Sales";

    private static final String TAG_CREATED_BY = "Created By ";
    private static final String TAG_SEPARATOR = ";";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentTaskBusinessServiceImpl.class );

    /** The task checkin statuses. */
    @Value( "${agent.task.checkin.statuses}" )
    private String taskCheckinStatuses;

    /** The task checkin types. */
    @Value( "${agent.task.checkin.task.types}" )
    private String taskCheckinTypes;

    /** The agent opportunity service. */
    @Autowired
    private AgentOpportunityService agentOpportunityService;

    /** The agent task service. */
    @Autowired
    private AgentTaskService agentTaskService;

    /** The agent reminder business service. */
    @Autowired
    private AgentReminderBusinessService agentReminderBusinessService;

    /** The agent request service. */
    @Autowired
    private AgentRequestService agentRequestService;

    @Autowired
    private AgentDetailsService agentDetailsService;

    @Autowired
    private AgentInfoService agentInfoService;
    /** The property business service. */
    @Autowired
    private PropertyBusinessService propertyBusinessService;

    /** The agent opportunity business service. */
    @Autowired
    private AgentOpportunityBusinessService agentOpportunityBusinessService;

    /** The google calendar event builder. */
    @Autowired
    private GoogleCalendarEventBuilder googleCalendarEventBuilder;

    /** The calendar service. */
    @Autowired
    private CalendarService calendarService;

    /** The contact service. */
    @Autowired
    private ContactService contactService;

    /** The contact builder. */
    @Autowired
    private ContactBuilder contactBuilder;

    /** The contact service v1. */
    @Autowired
    private OpportunityService opportunityService;

    /** The agent task status log service. */
    @Autowired
    private AgentTaskStatusLogService agentTaskStatusLogService;

    /** The agent task builder. */
    @Autowired
    private AgentTaskBuilder agentTaskBuilder;

    /** The agent task validator. */
    @Autowired
    private AgentTaskValidator agentTaskValidator;

    /** The contact service v1. */
    @Autowired
    private ContactEntityService contactServiceV1;

    /** The user service. */
    @Autowired
    private UserService userService;

    /** The user details response builder. */
    @Autowired
    private UserDetailsResponseBuilder userDetailsResponseBuilder;

    /** The ref type code service. */
    @Autowired
    private RefTypeService refTypeService;

    /** The ref code service. */
    @Autowired
    private RefCodeService refCodeService;

    /** The agent checkin task config. */
    @Autowired
    private AgentCheckinTaskConfig agentCheckinTaskConfig;

    /** The checkin feedback service. */
    @Autowired
    private CheckinFeedbackService checkinFeedbackService;

    /** The agent contact service. */
    @Autowired
    private AgentContactService agentContactService;

    /** The search service. */
    @Autowired
    private SearchService searchService;

    /** The co shopping lead request builder. */
    @Autowired
    private CoShoppingLeadBuilder coShoppingLeadBuilder;

    /** The co shopping service. */
    @Autowired
    private CoShoppingService coShoppingService;

    /** The co shopping config. */
    @Autowired
    private CoShoppingConfig coShoppingConfig;

    /** The contact entity service. */
    @Autowired
    private ContactEntityService contactEntityService;

    /** The property service. */
    @Autowired
    private PropertyService propertyService;

    @Autowired
    private AgentService agentService;

    @Autowired
    private MailService mailService;

    @Autowired
    private LeadOpportunityBusinessConfig leadOpportunityBusinessConfig;

    /** The agent push notification business service. */
    @Autowired
    private AgentNotificationBusinessService agentNotificationBusinessService;

    @Autowired
    private TourConfirmationBusiness tourConfirmationBusiness;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.AgentTaskBusinessService#createAgentTask(
     * java.lang.String, java.lang.String,
     * com.owners.gravitas.dto.request.AgentTaskRequest)
     */
    @Override
    @Transactional( propagation = REQUIRES_NEW )
    public AgentResponse createAgentTask( final String agentId, final String opportunityId,
            final AgentTaskRequest taskRequest ) {
        LOGGER.info( "Create task request received for agent {} , opportunity {} , is {} ", agentId, opportunityId,
                JsonUtil.toJson( taskRequest ) );
        AgentResponse response = null;
        String coShoppingId = null;
        final com.owners.gravitas.domain.entity.Contact contact = contactEntityService
                .getContactByFbOpportunityId( opportunityId );
        
        final Opportunity opportunity = agentOpportunityService.getOpportunityById( agentId, opportunityId );
        // get agent email from search node
        final Search existingOpportunity = searchService.searchByCrmOpportunityId( opportunity.getCrmId() );
        if (opportunity != null) {
            if (coShoppingService.isEligibleForCoshoppingTourCreation( taskRequest )) {
                LOGGER.info( "all conditions met, bulding coshopping request" );
                final PropertyDetailsResponse propertyDetails = propertyService
                        .getPropertyDetails( taskRequest.getListingId() );
                // use agent email to get timeZone
                final String agentTimeZone = agentDetailsService
                        .getAgentsTimeZone( existingOpportunity.getAgentEmail() );
                final CoShoppingLeadRequest request = coShoppingLeadBuilder.build( taskRequest.getDueDtm(), contact,
                        propertyDetails.getData(), agentTimeZone );
                request.getLeadModel().setType( coShoppingService.getRequestType( taskRequest.getType() ) );
                request.getLeadModel().setListingId( taskRequest.getListingId() );
                request.getLeadModel().setStatus( taskRequest.getStatus() );
                Resource coshoppingTourResponse = null;
                try {
                    coshoppingTourResponse = coShoppingService.postLeadDetails( request );
                } catch ( final Exception ex ) {
                    LOGGER.error(
                            "Exception occurred in create agent task while pushing to coshopping api for the opportunity id {} with exception ",
                            opportunityId, ex );
                }
                if (null != coshoppingTourResponse) {
                    coShoppingId = coshoppingTourResponse.getId();
                }
                LOGGER.info( "coshopping tour id is : {} ", coShoppingId );
            }
            final Task task = buildTask( agentId, opportunityId, taskRequest, coShoppingId );
            final PostResponse postResponse = agentTaskService.saveAgentTask( task, agentId );
            final String taskId = postResponse.getName();
            saveDbTask( task, taskId );

            // task is scheduled then set scheduled push notification as well.
            LOGGER.info( "Verifying adhoc request createdDtm  = {} ", task.getCreatedDtm() );
            if (task.getDueDtm() != null && task.getDueDtm() > task.getCreatedDtm()) {
                agentReminderBusinessService.setTaskReminder( agentId, opportunityId, postResponse.getName(), task,
                        PushNotificationType.SCHEDULED_TASK_APPOINTMENT_REMINDER );
            }

            // send push notification based on status
            if (( SCHEDULE_MEETING.getType().equalsIgnoreCase( taskRequest.getType() )
                    || SCHEDULE_TOUR.getType().equalsIgnoreCase( taskRequest.getType() ) )
                    && coShoppingConfig.isEnableScheduleTourMeetings()) {
                if (CONFIRMED.name().equalsIgnoreCase( taskRequest.getStatus() )) {
                    // in-case of confirmed sending NEW_OPPORTUNITY
                    LOGGER.debug(
                            "Sending NEW_OPPORTUNITY push notification when opp is assigned with confirmed status for oppId: {}",
                            opportunityId );
                    agentOpportunityBusinessService.sendOpportunityPushNotifications( opportunityId,
                            existingOpportunity );
                } else if (PENDING.name().equalsIgnoreCase( taskRequest.getStatus() )) {
                    // in-case of pending sending
                    // PUSH_NTFC_NEW_PENDING_OPPORTUNITY
                    LOGGER.debug( "sending NEW_PENDING_OPPORTUNITY notification for task: {} ", taskId );
                    agentOpportunityBusinessService.sendPendingOpportunityPushNotifications( taskId, agentId,
                            PushNotificationType.NEW_PENDING_OPPORTUNITY );
                }
            }

            // send confirmation email for SCHEDULE_TOUR
            //Here checking IsWarmTransferCall should be false, because warm transfer flow also calling this method, which 
            //is causing duplicate tour email send to the buyer. Reference GRACRM-1139
            if (coShoppingConfig.isScheduledTourByFieldAgentEnabled()
                    && CONFIRMED.name().equalsIgnoreCase( taskRequest.getStatus() ) && !taskRequest.getIsWarmTransferCall()) {
                final Task updatedTask = agentTaskService.getTaskById( agentId, taskId );
                updatedTask.setId( taskId );
                LOGGER.info( "Updated task received after agent created task: {}", updatedTask );
                if (TaskType.SCHEDULE_TOUR.getType().equalsIgnoreCase( updatedTask.getTaskType() )
                        && StringUtils.isNotBlank( updatedTask.getListingId() )
                        && StringUtils.isNotBlank( updatedTask.getCoShoppingId() )) {
                    LOGGER.info(
                            "Sending schedule tour confirmation email for the agent created task with agent id: {}",
                            agentId );
                    tourConfirmationBusiness.sendConfirmationEmailForScheduleTour( agentId, updatedTask );
                }
            }
            response = new AgentResponse( postResponse.getName() );
        }
        return response;
    }

    /**
     * Builds the task.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param taskRequest
     *            the task request
     * @return the task
     */
    private Task buildTask( final String agentId, final String opportunityId, final AgentTaskRequest taskRequest,
            final String coShoppingId ) {
        final Long createdDtm = new DateTime().getMillis();
        final String type = StringUtils.isBlank( taskRequest.getType() ) ? USER_DEFINED.getType()
                : taskRequest.getType();
        final Long dueDtm = ( null != taskRequest.getDueDtm() ) ? taskRequest.getDueDtm().getTime() : null;
        final String status = StringUtils.isBlank( taskRequest.getStatus() ) ? INCOMPLETE.name()
                : taskRequest.getStatus();
        final String createdBy = ( StringUtils.isNotBlank( taskRequest.getCreatedBy() ) ) ? taskRequest.getCreatedBy()
                : agentId;
        final String coShoppingFbId = StringUtils.isBlank( coShoppingId ) ? taskRequest.getCoShoppingId()
                : coShoppingId;
        final Task task = new Task( taskRequest.getTitle(), opportunityId, type, createdBy, createdDtm );
        task.setDescription( taskRequest.getDescription() );
        task.setLocation( taskRequest.getLocation() );
        task.setDueDtm( dueDtm );
        task.setStatus( status );
        task.setListingId( taskRequest.getListingId() );
        if (StringUtils.isNotBlank( createdBy )) {
            final String currentTags = task.getTag();
            String newTags = currentTags;
            if (StringUtils.isNotBlank( currentTags )) {
                newTags = currentTags.trim() + TAG_SEPARATOR + TAG_CREATED_BY + createdBy.trim();
            } else {
                newTags = TAG_CREATED_BY + createdBy.trim();
            }
            task.setTag( newTags );
        }
        task.setCoShoppingId( coShoppingFbId );
        task.setIsPrimary( taskRequest.isPrimary() );
        if (coShoppingConfig.isEnableScheduleTourMeetings()) {
            task.setIsWarmTransferCall( taskRequest.getIsWarmTransferCall() );
        }
        return task;
    }

    /**
     * Save db task.
     *
     * @param task
     *            the task
     * @param taskId
     *            the task id
     */
    private void saveDbTask( final Task task, final String taskId ) {
        LOGGER.info( "saving agent task into database for taskId: {}, task: {}", taskId, JsonUtil.toJson( task ) );
        if (isNotBlank( task.getStatus() )) {
            AgentTask agentTask = new AgentTask();
            agentTask.setTaskId( taskId );
            final com.owners.gravitas.domain.entity.Opportunity opportunity = opportunityService
                    .getOpportunityByFbId( task.getOpportunityId(), Boolean.FALSE );
            agentTask.setOpportunity( opportunity );
            agentTask.setType( task.getTaskType() );
            agentTask.setLocation( task.getLocation() );
            agentTask.setScheduledDtm( task.getDueDtm() != null ? new DateTime( task.getDueDtm() ) : null );
            agentTask.setTitle( task.getTitle() );
            agentTask.setTag( task.getTag() );
            agentTask.setStatus( task.getStatus() );
            agentTask.setDescription( task.getDescription() );
            agentTask.setDeleted( task.getDeleted() );
            agentTask.setDeletedBy( task.getDeletedBy() );
            agentTask.setWarmCallTransferFlag(
                    task.getIsWarmTransferCall() == null ? false : task.getIsWarmTransferCall() );
            agentTask = agentTaskService.saveAgentTask( agentTask );

            final AgentTaskStatusLog agentTaskStatusLog = new AgentTaskStatusLog();
            agentTaskStatusLog.setAgentTask( agentTask );
            agentTaskStatusLog.setStatus( task.getStatus().toLowerCase() );
            agentTaskStatusLogService.save( agentTaskStatusLog );
        }
    }

    /**
     * Update agent task.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the request id
     * @param request
     *            the acknowledgement request
     * @return the agent response
     */
    @Override
    public AgentResponse handleAgentTask( final String agentId, final String taskId, final TaskUpdateRequest request ) {
        LOGGER.info( "Update request received for agent " + agentId + " task " + taskId + " is"
                + JsonUtil.toJson( request ) );
        if (request != null) {
            checkCompleteStatus( request );
            if (null != request.getDueDtm() || isNotBlank( request.getLocation() ) || isNotBlank( request.getTitle() )
                    || isNotCompletedOrCancelled( request.getStatus() )) {
                // Check if the request is for update task
                return updateAgentTask( agentId, taskId, request );
            }
            if (null != request.getScheduleDtm()) {
                // If not for update then look if scheduled task to be created
                createTaskForScheduleTour( agentId, taskId, request );
            }
            if (isNotBlank( request.getTitleSelectionReason() )) {
                updateTitleSelectionReason( agentId, taskId, request );
            }
        }

        // Close task
        LOGGER.info( "Closing task for agent " + agentId + " task " + taskId );
        return closeAgentTask( agentId, taskId, request );
    }

    /**
     * Complete agent's task.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the request id
     * @param request
     *            the acknowledgement request
     * @return the agent response
     */
    @Override
    public AgentResponse completeAgentTask( final String agentId, final String taskId,
            final TaskUpdateRequest request ) {
        // required to validate task existence
        agentTaskService.getTaskById( agentId, taskId );
        if (request != null) {
            if (null != request.getScheduleDtm()) {
                // If not for update then look if scheduled task to be created
                createTaskForScheduleTour( agentId, taskId, request );
            }
            if (isNotBlank( request.getTitleSelectionReason() )) {
                updateTitleSelectionReason( agentId, taskId, request );
            }
        }
        // Close task
        return closeAgentTask( agentId, taskId, request );
    }

    /**
     * Save distinct task.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param type
     *            the type
     * @param title
     *            the title
     * @param name
     *            the name
     * @return the post response
     */
    @Override
    public PostResponse saveTaskifNotExists( final String agentId, final String opportunityId, final TaskType type,
            final String title, final String name ) {
        PostResponse response = null;
        final Map< String, Task > openTasks = agentTaskService.getOpenTasksByType( agentId, opportunityId,
                type.getType() );
        if (openTasks.isEmpty()) {
            response = saveTask( agentId, opportunityId, type, title, name );
        }
        return response;
    }

    /**
     * Close task by type.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param type
     *            the type
     */
    @Override
    public void closeTaskByType( final String agentId, final String opportunityId, final TaskType type ) {
        final Map< String, Task > openTasks = agentTaskService.getOpenTasksByType( agentId, opportunityId,
                type.getType() );
        for ( final Entry< String, Task > entry : openTasks.entrySet() ) {
            final Task task = entry.getValue();
            if (null != task && task.getCompletedDtm() == null) {
                handleAgentTask( agentId, entry.getKey(), null );
            }
        }
    }

    /**
     * Adds the to google calendar.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param agentEmail
     *            the agent email
     * @return the base response
     */
    @Override
    public AgentResponse addToGoogleCalendar( final String agentId, final String taskId, final String agentEmail ) {
        final Task task = agentTaskService.getTaskById( agentId, taskId );
        LOGGER.info( "Agent " + agentEmail + " task will be added to google calendar" );
        final Event event = googleCalendarEventBuilder.convertTo( task );
        final com.owners.gravitas.domain.Contact contact = contactService
                .getContactByOpportunityId( task.getOpportunityId() );
        final Contact contactDto = contactBuilder.convertTo( contact );
        event.setSummary( getSummary( contactDto, task.getTitle() ) );
        final Event savedEvent = calendarService.createEvent( agentEmail, event );
        LOGGER.info( "Agent event is created in google calendar for agent: " + agentEmail + " with link "
                + savedEvent.getHtmlLink() );
        return new AgentResponse( taskId );
    }

    /**
     * Delete agent task.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param apiUser
     *            the api user
     * @return the agent response
     */
    @Override
    public AgentResponse deleteAgentTask( final String agentId, final String taskId, final ApiUser apiUser ) {
        LOGGER.info( "Deleting task " + taskId + " for agent " + agentId );
        final AgentTask agentTask = isDeleteAllowed( taskId );
        if (agentTask != null) {
            final AgentTaskStatusLog taskStatusLog = updateDbTaskLog( DELETED.name(), null, apiUser.getEmail(),
                    agentTask );
            agentReminderBusinessService.cancelAgentTaskReminders( agentId, taskId );
            final Map< String, Object > patch = createPatchForDelete( apiUser.getUid(), taskStatusLog );
            agentTaskService.patchAgentTask( taskId, agentId, patch );
            final Task task = agentTaskService.getTaskById( agentId, taskId );
            agentTaskService.pushStatusToCoshopping( task, CANCELLED.name() );
            LOGGER.info( "Task " + taskId + " deleted for agent " + agentId );
        } else {
            throw new ApplicationException( "Agent task not found for agentId: " + agentId + " taskId:" + taskId,
                    AGENT_TASK_NOT_FOUND );
        }
        return new AgentResponse( taskId );
    }

    /**
     * Checks if is delete allowed.
     *
     * @param taskId
     *            the task id
     * @param loggedInUserRoles
     * @param agentTask
     *            the agent task
     * @return the agent task
     */
    private AgentTask isDeleteAllowed( final String taskId ) {
        if (agentCheckinTaskConfig.isDisallow1099AgentsToDeleteTasks()) {
            throw new ApplicationException( TASK_DELETE_NOT_ALLOWED_FOR_AGENT_WHEN_FLAG_IS_ON.getErrorDetail(),
                    TASK_DELETE_NOT_ALLOWED_FOR_AGENT_WHEN_FLAG_IS_ON );
        }
        return agentTaskService.getByTaskId( taskId );
    }

    /**
     * Created patch for delete.
     *
     * @param agentFbId
     *            the agent fb id
     * @param taskStatusLog
     *            the task status log
     * @return the map
     */
    private Map< String, Object > createPatchForDelete( final String agentFbId,
            final AgentTaskStatusLog taskStatusLog ) {
        final long lastModifiedDtm = taskStatusLog == null ? new DateTime().getMillis()
                : taskStatusLog.getCreatedDate().getMillis();
        final Map< String, Object > patch = new HashMap<>();
        patch.put( "lastModifiedDtm", lastModifiedDtm );
        patch.put( "deleted", TRUE );
        patch.put( "deletedBy", agentFbId );
        if (taskStatusLog.getReason() != null) {
            patch.put( "deletedReason", taskStatusLog.getReason() );
        }
        patch.put( "status", DELETED.name() );
        return patch;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.AgentTaskBusinessService#getCheckinDetails(
     * java.lang.String)
     */
    @Override
    public CheckinDetailResponse getCheckinDetails( final String search, final String fromDateStr,
            final String toDateStr ) {
        LOGGER.info( "Search checkin details for " + search + " from " + fromDateStr + " to " + toDateStr );
        CheckinDetailResponse response = new CheckinDetailResponse();
        final Date[] dates = getFromAndToDates( fromDateStr, toDateStr );
        List< AgentTask > checkinTasks = null;
        if (isNotBlank( search )) {
            if (isEmailIdValid( search )) {
                checkinTasks = getTasksByEmail( search, dates[0], dates[1] );
            } else {
                checkinTasks = getTasksByOpportunityId( search, dates[0], dates[1] );
            }
        } else {
            checkinTasks = getAllTasks( dates[0], dates[1] );
        }
        if (isNotEmpty( checkinTasks )) {
            response = processCheckinTask( checkinTasks, response );
        }
        return response;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.AgentTaskBusinessService#unassignTasks(java.
     * lang.String, java.lang.String)
     */
    @Override
    public void unassignTasks( final String opportunityId, final String agentId ) {
        LOGGER.info( "Unassigning tasks for " + agentId + "(" + opportunityId + ")" );
        final Map< String, Task > tasks = agentTaskService.getTasksByOpportunityId( agentId, opportunityId );
        for ( final Map.Entry< String, Task > taskEntry : tasks.entrySet() ) {
            if (( null == taskEntry.getValue().getCompletedDtm()
                    || CANCELLED.name().equals( taskEntry.getValue().getStatus() ) )
                    && !taskEntry.getValue().getDeleted()) {
                taskEntry.getValue().setStatus( UNASSIGNED.name() );
                taskEntry.getValue().setLastModifiedDtm( new Date().getTime() );
            }
        }
        agentTaskService.saveAgentTasks( tasks, agentId );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentTaskBusinessService#
     * copyTasksAndRequests(java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, java.util.Map)
     */
    @Override
    public void copyTasksAndRequests( final String oldAgentId, final String newAgentId, final String oldOpportunityId,
            final String newOpportunityId, final Map< String, String > newContacts ) {
        LOGGER.info( "Copying tasks and requests from " + oldAgentId + "(" + oldOpportunityId + ") to " + newAgentId
                + "(" + newOpportunityId + ")" );

        // get all task by opportunity
        final Map< String, Task > oldTasks = agentTaskService.getTasksByOpportunityId( oldAgentId, oldOpportunityId );

        // get all buyer request by opportunity.
        final Map< String, Request > oldRequests = agentRequestService.getRequestsByOpportunityId( oldAgentId,
                oldOpportunityId );

        // save tasks
        final Map< String, Task > newTasks = new HashMap<>();
        final Map< String, Request > newRequests = new HashMap<>();

        for ( final Entry< String, Task > taskEntry : oldTasks.entrySet() ) {
            final String newTaskId = UUID.randomUUID().toString();

            final Task existingTask = taskEntry.getValue();
            existingTask.setOpportunityId( newOpportunityId );
            if (!DELETED.name().equalsIgnoreCase( existingTask.getStatus() )
                    || !CANCELLED.name().equalsIgnoreCase( existingTask.getStatus() )) {
                if (leadOpportunityBusinessConfig.isReassignedOpportunityTaskConfig()
                        && ( SCHEDULE_MEETING.getType().equals( existingTask.getTaskType() )
                                || SCHEDULE_TOUR.getType().equals( existingTask.getTaskType() ) )) {
                    existingTask.setDeleted( TRUE );
                    existingTask.setDeletedBy( GRAVITAS );
                } else {
                    existingTask.setDeleted( FALSE );
                }
            }

            final String createdBy = existingTask.getCreatedBy();
            LOGGER.info( "Task  id : {} Created  By:{}", existingTask, createdBy );
            if (!StringUtils.isEmpty( createdBy ) && !INSIDE_SALES.equalsIgnoreCase( createdBy )) {
                existingTask.setCreatedBy( newAgentId );
                LOGGER.info( "After setting created by Task  id :{} Created By changed to :{}", existingTask,
                        newAgentId );
            }

            saveDbTask( existingTask, newTaskId );

            final Map< String, Reminder > newReminders = copyReminders( newAgentId, newTaskId, existingTask,
                    existingTask.getReminders(), newContacts );
            // special business case to remove old reminder from firebase itself
            if (null != existingTask.getReminders()) {
                existingTask.getReminders().clear();
            }
            existingTask.setReminders( newReminders );

            newTasks.put( newTaskId, existingTask );

            final Request taskAssociatedRequest = oldRequests.get( existingTask.getRequestId() );
            if (null != taskAssociatedRequest) {
                taskAssociatedRequest.setTaskId( newTaskId );
                taskAssociatedRequest.setDeleted( FALSE );
                taskAssociatedRequest.setOpportunityId( newOpportunityId );
                final String newRequestId = UUID.randomUUID().toString();
                newRequests.put( newRequestId, taskAssociatedRequest );
                oldRequests.remove( existingTask.getRequestId() );
                existingTask.setRequestId( newRequestId );
            }
        }
        agentTaskService.saveAgentTasks( newTasks, newAgentId );

        // save requests which are not having associated task.
        for ( final Entry< String, Request > requestEntry : oldRequests.entrySet() ) {
            final Request existingRequest = requestEntry.getValue();
            // existingRequest.setDeleted( FALSE );
            existingRequest.setOpportunityId( newOpportunityId );
            newRequests.put( UUID.randomUUID().toString(), existingRequest );
        }

        agentRequestService.saveAgentRequests( newRequests, newAgentId );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentTaskBusinessService#
     * reassignTasksToSameAgent(java.lang.String, java.lang.String)
     */
    @Override
    public void reassignTasksToSameAgent( final String agentId, final String opportunityId ) {
        LOGGER.info( "Reassigning tasks for opp: " + opportunityId + " to " + agentId );
        final Map< String, Task > tasks = agentTaskService.getTasksByOpportunityId( agentId, opportunityId );
        for ( final Map.Entry< String, Task > taskEntry : tasks.entrySet() ) {
            final Task existingTask = taskEntry.getValue();
            final String status = getPreviousTaskStatus( taskEntry.getKey() );
            if (isNotBlank( status )) {
                existingTask.setStatus( status.toUpperCase() );
                updateDbTask( taskEntry.getKey(), status, null );
            }
            if (!CANCELLED.name().equals( existingTask.getStatus() )) {
                final Map< String, Reminder > reminders = getReminders( agentId, opportunityId, taskEntry,
                        existingTask );
                existingTask.setReminders( reminders );
            }
            if (!DELETED.name().equalsIgnoreCase( existingTask.getStatus() )) {
                existingTask.setDeleted( FALSE );
            }
            existingTask.setLastModifiedDtm( new Date().getTime() );
        }
        agentTaskService.saveAgentTasks( tasks, agentId );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.AgentTaskBusinessService#createBuyerTask(com
     * .owners.gravitas.dto.request.LeadRequest, java.lang.String,
     * java.lang.String, java.lang.String)
     */
    @Override
    public PostResponse createBuyerTask( final LeadRequest request, final String agentId, final String opportunityId,
            final String requestId, final Request buyerRequest ) {
        final String titleTxt = LeadRequestType.valueOf( request.getRequestType() ).getType();
        final Long now = new DateTime().getMillis();
        final Task task = new Task( titleTxt, opportunityId, valueOf( request.getRequestType() ).getType(), agentId,
                now );
        final List< List< TourDetails > > tourDetailsList = buyerRequest.getDates();

        if (requestId == null && CollectionUtils.isNotEmpty( tourDetailsList )) {
            task.setDates( tourDetailsList );
        }

        if (requestId != null) {
            task.setRequestId( requestId );
        }

        if (requestId == null && request.getCoShoppingId() != null) {
            task.setCoShoppingId( request.getCoShoppingId() );
        }

        if (requestId == null && buyerRequest.getStatus() != null) {
            task.setStatus( buyerRequest.getStatus() );
        } else {
            task.setStatus( INCOMPLETE.name() );
        }

        if (requestId == null) {
            task.setListingId( request.getListingId() );
            task.setLocation( request.getPropertyAddress() );
            final DateTime requestDateTime = populateRequestDateTime( tourDetailsList );
            if (requestDateTime != null) {
                task.setDueDtm( requestDateTime.getMillis() );
            }
        }
        final PostResponse taskResponse = agentTaskService.saveAgentTask( task, agentId );
        saveDbTask( task, taskResponse.getName() );
        return taskResponse;
    }

    /**
     * Populate request date time.
     *
     * @param tourDetailsList
     *            the tour details list
     * @return the date time
     */
    private DateTime populateRequestDateTime( final List< List< TourDetails > > tourDetailsList ) {
        DateTime requestDateTime = null;
        if (CollectionUtils.isNotEmpty( tourDetailsList )) {
            final List< TourDetails > list = tourDetailsList.get( 0 );
            if (CollectionUtils.isNotEmpty( list )) {
                final String requestDateTimeStr = list.get( 0 ).getValue().toString();
                String formattedStr = requestDateTimeStr;
                if (formattedStr.contains( "[" )) {
                    formattedStr = requestDateTimeStr.substring( requestDateTimeStr.indexOf( "[" ) + 1,
                            requestDateTimeStr.length() - 1 );
                }
                requestDateTime = DateUtil.toDateTime( formattedStr, ISO_DATE_FORMAT );
            }
        }
        return requestDateTime;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.AgentTaskBusinessService#answerQuestionnaire
     * (java.util.Map)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public BaseResponse answerQuestionnaire( final Map< String, Object > request ) {
        final BaseResponse response = new BaseResponse();
        final String objectType = convertObjectToString( request.get( "objectType" ) );
        final String objectId = convertObjectToString( request.get( "objectId" ) );
        final String status = convertObjectToString( request.get( "status" ) );
        final String type = convertObjectToString( request.get( "type" ) );
        final String reason = convertObjectToString( request.get( "reason" ) );
        final Map< String, String > questionnaire = ( Map< String, String > ) request.get( "questionnaire" );
        LOGGER.info( "Submitted questionnaire for " + objectType + " " + objectId + " Status : " + status );
        if (isNotBlank( objectType ) && objectType.equalsIgnoreCase( "task" ) && isNotEmpty( questionnaire )) {
            final AgentTask task = agentTaskService.getByTaskId( objectId );
            if (task != null) {
                final Set< RefCode > refCodes = getQuestionRefCodes( type );
                final List< CheckinFeedback > feedbackSet = new ArrayList<>();
                checkinFeedbackService.deleteCheckins( checkinFeedbackService.getCheckinFeedback( task ) );
                questionnaire.entrySet().forEach( entry -> {
                    final CheckinFeedback feedback = new CheckinFeedback();
                    feedback.setQuestionRef( getRefCode( refCodes, entry.getKey() ) );
                    feedback.setAnswer( entry.getValue() );
                    feedback.setTask( task );
                    feedbackSet.add( feedback );
                } );
                checkinFeedbackService.saveAll( feedbackSet );
                final TaskUpdateRequest taskUpdateRequest = new TaskUpdateRequest();
                taskUpdateRequest.setStatus( status );
                taskUpdateRequest.setCancellationReason( reason );
                updateAgentTask(
                        searchService.searchByAgentEmail( task.getOpportunity().getAssignedAgentId() ).getAgentId(),
                        objectId, taskUpdateRequest );
            } else {
                response.setMessage( "Task does not exist" );
            }
        }
        return response;
    }

    /**
     * Gets the questionnaire.
     *
     * @param type
     *            the type
     * @return the questionnaire
     */
    @Override
    public Set< String > getQuestionnaire( final String type ) {
        LOGGER.debug( "Get questionnaire for RefType : " + type );
        final Set< RefCode > refCodes = getQuestionRefCodes( type );
        final Set< String > response = refCodes.stream()
                .collect( Collectors.mapping( RefCode::getCode, Collectors.toSet() ) );
        LOGGER.debug( "Questionnaire response : " + response );
        return response;
    }

    /**
     * Update scheduled tasks by opportunity id.
     *
     * @param opportunity
     *            the opportunity id
     */
    @Override
    public void updateScheduledTasksByOpportunityId( final String agentId, final String opportunityId ) {
        LOGGER.info( "Update Scheduled tasks of opportunity " + opportunityId );
        final List< AgentTask > tasks = agentTaskService.getAgentTasksByFbId( opportunityId,
                new HashSet< String >( Arrays.asList( PENDING.name() ) ),
                new HashSet< String >( Arrays.asList( SCHEDULE_MEETING.name(), SCHEDULE_TOUR.name() ) ) );
        tasks.forEach( task -> updateAgentTask( agentId, task, CONFIRMED.name() ) );

        // Auto complete system generated tasks CLAIM_OPPORTUNITY
        final List< AgentTask > autoCompleteTaskList = agentTaskService.getAgentTasksByFbId( opportunityId,
                new HashSet< String >( Arrays.asList( INCOMPLETE.name() ) ),
                new HashSet< String >( Arrays.asList( CLAIM_OPPORTUNITY.name() ) ) );
        if (!org.springframework.util.CollectionUtils.isEmpty( autoCompleteTaskList )) {
            final Map< String, Task > fbAgentOpportunityTasks = agentTaskService.getTasksByOpportunityId( agentId,
                    opportunityId );
            LOGGER.info( "FB task list to verify CREATED_BY fbAgentOpportunityTasks : {}", fbAgentOpportunityTasks );
            for ( final AgentTask agentTask : autoCompleteTaskList ) {
                final Task task = fbAgentOpportunityTasks.get( agentTask.getTaskId() );
                LOGGER.info( "FB task : {} and DB AgentTask : {}", task, agentTask );
                if (agentTask.getType().equalsIgnoreCase( SCHEDULE_MEETING.name() ) && task != null
                        && task.getCreatedBy().equalsIgnoreCase( INSIDE_SALES )) {
                    updateAgentTask( agentId, agentTask, CONFIRMED.name() );
                } else {
                    updateAgentTask( agentId, agentTask, COMPLETED.name() );
                }
            }
        }
        // update status to confirm in coShopping FB
        updateAgentTaskInCoshoppingFb( agentId, opportunityId, CONFIRMED.name() );
    }

    /**
     * Update agent task in coshopping fb.
     *
     * @param tasks
     *            the tasks
     * @param status
     *            the status
     */
    private void updateAgentTaskInCoshoppingFb( final String agentId, final String opportunityId,
            final String status ) {

        final Map< String, Task > nonPrimaryTaskMap = agentTaskService.getTasksByOpportunityId( agentId,
                opportunityId );
        LOGGER.info(
                "updateAgentTaskInCoshoppingFb()  nonPrimaryTaskMap : {} to status {} for agentId {}, opportunityId {} ",
                nonPrimaryTaskMap, status, agentId, opportunityId );
        // Removing primary Task from nonPrimaryTasks
        final Set< String > removeTaskIdList = new LinkedHashSet< String >();
        for ( final Entry< String, Task > entry : nonPrimaryTaskMap.entrySet() ) {
            final Task tmpTask = entry.getValue();
            if (!( tmpTask.getCreatedBy().equalsIgnoreCase( INSIDE_SALES ) && !tmpTask.getIsPrimary() )) {
                removeTaskIdList.add( entry.getKey() );
            }
        }
        for ( final String taskIdToRemove : removeTaskIdList ) {
            nonPrimaryTaskMap.remove( taskIdToRemove );
        }

        LOGGER.info( "updateAgentTaskInCoshoppingFb() after removing primaryTask nonPrimaryTaskMap : {} to status {} ",
                nonPrimaryTaskMap, status );

        if (coShoppingConfig.isCoshoppingLeadUpdateEnabled()) {
            if (!org.springframework.util.CollectionUtils.isEmpty( nonPrimaryTaskMap )) {
                final CoShoppingLeadUpdateRequest coShoppingLeadUpdateRequest = new CoShoppingLeadUpdateRequest();
                final List< CoShoppingLeadUpdateModel > coShoppingLeadUpdateList = new ArrayList< CoShoppingLeadUpdateModel >();
                for ( final Entry< String, Task > entry : nonPrimaryTaskMap.entrySet() ) {
                    final Task nonPrimaryTask = entry.getValue();
                    if (nonPrimaryTask.getCoShoppingId() != null) {
                        final CoShoppingLeadUpdateModel nonPrimaryCoShoppingLeadUpdate = new CoShoppingLeadUpdateModel();
                        nonPrimaryCoShoppingLeadUpdate.setId( nonPrimaryTask.getCoShoppingId() );
                        nonPrimaryCoShoppingLeadUpdate.setStatus( status );
                        coShoppingLeadUpdateList.add( nonPrimaryCoShoppingLeadUpdate );
                    }
                }
                coShoppingLeadUpdateRequest.setLeadUpdateRequest( coShoppingLeadUpdateList );
                try {
                    coShoppingService.updateLeadDetails( coShoppingLeadUpdateRequest );
                } catch ( final Exception e ) {
                    LOGGER.error( "Error while updating to Coshopping api for the opportunityId: {}", opportunityId,
                            e );
                }
            }
        }
    }

    /**
     * Gets the from and to dates.
     *
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @return the from and to dates
     */
    private Date[] getFromAndToDates( final String fromDate, final String toDate ) {
        final Date[] dates = new Date[2];
        if (StringUtils.isEmpty( fromDate ) || StringUtils.isEmpty( toDate )) {
            dates[0] = toSqlDate( agentCheckinTaskConfig.getFromDate(), DEFAULT_CRM_DATE_PATTERN );
            dates[1] = toSqlDate( agentCheckinTaskConfig.getToDate(), DEFAULT_CRM_DATE_PATTERN );
        } else {
            dates[0] = toSqlDate( fromDate, DEFAULT_CRM_DATE_PATTERN );
            dates[1] = toSqlDate( toDate, DEFAULT_CRM_DATE_PATTERN );
        }
        return dates;
    }

    /**
     * Gets the question ref codes.
     *
     * @param type
     *            the type
     * @return the question ref codes
     */
    private Set< RefCode > getQuestionRefCodes( final String type ) {
        final com.owners.gravitas.domain.entity.RefType refType = refTypeService
                .getRefTypeByType( RefType.valueOf( type ).name() );
        final Set< RefCode > refCodes = refCodeService.findRefCodeByRefType( refType );
        return refCodes;
    }

    /**
     * Gets the ref code.
     *
     * @param refCodes
     *            the ref codes
     * @param qestionCode
     *            the qestion code
     * @return the ref code
     */
    private RefCode getRefCode( final Set< RefCode > refCodes, final String qestionCode ) {
        return refCodes.stream().filter( refCode -> refCode.getCode().equals( qestionCode ) ).findFirst()
                .orElse( null );
    }

    /**
     * Process checkin task.
     *
     * @param checkinTasks
     *            the checkin tasks
     * @param response
     *            the response
     * @return the checkin detail response
     */
    private CheckinDetailResponse processCheckinTask( final List< AgentTask > checkinTasks,
            final CheckinDetailResponse response ) {
        final Set< String > agentEmails = new HashSet<>();
        for ( final AgentTask task : checkinTasks ) {

            final com.owners.gravitas.domain.entity.Opportunity opportunity = task.getOpportunity();
            agentEmails.add( opportunity.getAssignedAgentId() );
            final com.owners.gravitas.domain.entity.Contact contact = opportunity.getContact();
            final CheckinDetailsDTO checkin = new CheckinDetailsDTO();
            final OpportunityDetailsDTO opportunityDetail = buildOpportunityDetailsDTO( task, contact );
            final TaskCheckinDetailsDTO taskDetail = buildTaskCheckinDetailsDto( task );
            checkin.setId( opportunity.getAssignedAgentId() );
            checkin.setOpportunityDetailsDTO( opportunityDetail );
            checkin.setTaskCheckinDetailsDTO( taskDetail );
            response.getCheckinDetailsDTO().add( checkin );

        }
        // find all agents' google account details.
        final Map< String, User > users = getUsers( agentEmails );
        // add google details into all AgentCheckinDetailsDtos
        response.getCheckinDetailsDTO().forEach( agentDto -> agentDto.setUser( users.get( agentDto.getId() ) ) );
        return response;
    }

    /**
     * Builds the checkin details dto.
     *
     * @param task
     *            the task
     * @return the checkin details dto
     */
    private TaskCheckinDetailsDTO buildTaskCheckinDetailsDto( final AgentTask task ) {
        final TaskCheckinDetailsDTO checkinDetailsDTO = new TaskCheckinDetailsDTO();
        checkinDetailsDTO.setCheckinDate( DateUtil.toString( task.getCreatedDate(), GRAVITAS_AUDIT_DATE_PATTERN ) );
        checkinDetailsDTO.setLocation( task.getLocation() );
        checkinDetailsDTO.setStatus( task.getStatus() );
        checkinDetailsDTO.setId( task.getTaskId() );
        return checkinDetailsDTO;
    }

    /**
     * Builds the opportunity details dto.
     *
     * @param agentTask
     *            the agent task
     * @param contact
     *            the contact
     * @return the opportunity details dto
     */
    private OpportunityDetailsDTO buildOpportunityDetailsDTO( final AgentTask agentTask,
            final com.owners.gravitas.domain.entity.Contact contact ) {
        final OpportunityDetailsDTO opportunityDetailsDTO = new OpportunityDetailsDTO();
        opportunityDetailsDTO.setOpportunityId( contact.getCrmId() );
        opportunityDetailsDTO.setBuyerName( contact.getFirstName() + Constants.SPACE + contact.getLastName() );
        opportunityDetailsDTO.setBuyerEmail( contact.getEmail() );
        opportunityDetailsDTO.setBuyerPhone( contact.getPhone() );
        return opportunityDetailsDTO;
    }

    /**
     * Gets the users.
     *
     * @param emails
     *            the emails
     * @return the users
     */
    private Map< String, User > getUsers( final Set< String > emails ) {
        final Map< String, User > usersMap = new HashMap<>();
        final UserDetailsResponse userDetailsResponse = userDetailsResponseBuilder
                .convertTo( userService.getUsersByEmails( new ArrayList<>( emails ) ) );
        userDetailsResponse.getUsers().forEach( user -> usersMap.put( user.getEmail(), user ) );
        return usersMap;
    }

    /**
     * Gets the tasks by email.
     *
     * @param searchStr
     *            the search str
     * @param fromDtm
     *            the from dtm
     * @param toDtm
     *            the to dtm
     * @return the tasks by email
     */
    private List< AgentTask > getTasksByEmail( final String searchStr, final Date fromDtm, final Date toDtm ) {
        // if email is agent's email.
        List< AgentTask > checkinTasks = agentTaskService.getTasksByAgentEmail( searchStr, fromDtm, toDtm,
                new HashSet< String >( Arrays.asList( taskCheckinStatuses.split( "," ) ) ),
                new HashSet< String >( Arrays.asList( taskCheckinTypes.split( "," ) ) ) );
        // if set is emty then email is buyer's email.
        if (isEmpty( checkinTasks )) {
            final com.owners.gravitas.domain.entity.Contact contact = contactServiceV1
                    .getContactByEmailAndType( searchStr, BUYER.getType() );
            if (contact != null) {

                for ( final com.owners.gravitas.domain.entity.Opportunity opportunity : contact.getOpportunities() ) {
                    if (!opportunity.isDeleted()) {
                        checkinTasks = agentTaskService.getAgentTasksByOpportunityId( contact.getCrmId(), fromDtm,
                                toDtm, new HashSet< String >( Arrays.asList( taskCheckinStatuses.split( "," ) ) ),
                                new HashSet< String >( Arrays.asList( taskCheckinTypes.split( "," ) ) ) );
                    }
                }
            }
        }
        return checkinTasks;
    }

    /**
     * Gets the all tasks.
     *
     * @param fromDtm
     *            the from dtm
     * @param toDtm
     *            the to dtm
     * @return the all tasks
     */
    private List< AgentTask > getAllTasks( final Date fromDtm, final Date toDtm ) {
        return agentTaskService.getAgentTasksByDates( fromDtm, toDtm,
                new HashSet< String >( Arrays.asList( taskCheckinStatuses.split( "," ) ) ) );
    }

    /**
     * Gets the tasks by id.
     *
     * @param searchStr
     *            the search str
     * @param startDate
     *            the start date
     * @param endDate
     *            the end date
     * @return the tasks by id
     */
    private List< AgentTask > getTasksByOpportunityId( final String searchStr, final Date startDate,
            final Date endDate ) {
        List< AgentTask > checkinTasks = null;
        final com.owners.gravitas.domain.entity.Contact contact = contactServiceV1.getContactByCrmId( searchStr );
        if (contact != null) {
            checkinTasks = agentTaskService.getAgentTasksByOpportunityId( contact.getCrmId(), startDate, endDate,
                    new HashSet< String >( Arrays.asList( taskCheckinStatuses.split( "," ) ) ),
                    new HashSet< String >( Arrays.asList( taskCheckinTypes.split( "," ) ) ) );
        }
        return checkinTasks;
    }

    /**
     * Gets the summary.
     *
     * @param contact
     *            the contact
     * @param title
     *            the title
     * @return the summary
     */
    private String getSummary( final Contact contact, final String title ) {
        final String opportunityName = getName( contact.getFirstName(), contact.getLastName() );
        return TASK + BLANK_SPACE + HYPHEN + BLANK_SPACE + opportunityName + BLANK_SPACE + OPENING_BRACKET + title
                + CLOSING_BRACKET;

    }

    /**
     * Gets the name.
     *
     * @param firstName
     *            the first name
     * @param lastName
     *            the last name
     * @return the name
     */
    private String getName( final String firstName, final String lastName ) {
        String name = lastName;
        if (StringUtils.isNotBlank( firstName )) {
            name = firstName + BLANK_SPACE + lastName;
        }
        return name;
    }

    /**
     * Update title selection reason.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param request
     *            the request
     */
    private void updateTitleSelectionReason( final String agentId, final String taskId,
            final TaskUpdateRequest request ) {
        final Task task = agentTaskService.getTaskById( agentId, taskId );
        final Map< String, Object > requestMap = new HashMap<>();
        requestMap.put( "titleSelectionReason", request.getTitleSelectionReason() );
        agentOpportunityBusinessService.patchOpportunity( agentId, task.getOpportunityId(), requestMap );
    }

    /**
     * Update agent task.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param request
     *            the request
     * @return the agent response
     */
    private AgentResponse updateAgentTask( final String agentId, final String taskId,
            final TaskUpdateRequest request ) {
        agentTaskValidator.validateStatus( request.getStatus() );
        LOGGER.info( "Patch task for agent : {}, task : {}, request :{}", agentId, taskId, JsonUtil.toJson( request ) );
        final Task task = agentTaskService.getTaskById( agentId, taskId );
        if (task != null) {
            final Map< String, Object > taskParams = new HashMap<>();
            if (request.getDueDtm() != null) {
                taskParams.put( "dueDtm", request.getDueDtm().getTime() );
                agentReminderBusinessService.updateAndCancelReminder( agentId, taskId, request.getDueDtm().getTime() );
            }
            addParamIfNotBlank( taskParams, "description", request.getDescription() );
            addParamIfNotBlank( taskParams, "location", request.getLocation() );
            addParamIfNotBlank( taskParams, "listingId", request.getListingId() );
            addParamIfNotBlank( taskParams, "title", request.getTitle() );
            addParamIfNotBlank( taskParams, "status", request.getStatus() );

            updateAgentTaskDetails( taskId, request );
            final AgentTaskStatusLog taskStatusLog = updateDbTask( taskId, request.getStatus(),
                    request.getCancellationReason() );
            final long lastModifiedDtm = taskStatusLog == null ? new DateTime().getMillis()
                    : taskStatusLog.getCreatedDate().getMillis();
            taskParams.put( "lastModifiedDtm", lastModifiedDtm );

            // Updating lead in coShopping
            if (coShoppingConfig.isCoshoppingLeadUpdateEnabled()) {
                try {
                    updateCoShoppingFirebaseLead( agentId, request, task );
                    if (StringUtils.isNotBlank( task.getCoShoppingId() )) {
                        taskParams.put( "coShoppingId", task.getCoShoppingId() );
                    }
                } catch ( final Exception ex ) {
                    LOGGER.error(
                            "Exception occurred while updating to coshopping api for the task id {} with exception ",
                            taskId, ex );
                }
            }

            agentTaskService.patchAgentTask( taskId, agentId, taskParams );

            // confirm all non primary tasks when primary task is confirmed.
            Map< String, Task > nonPrimaryTaskMap = null;
            if (!coShoppingConfig.isScheduledTourByFieldAgentEnabled()
                    && request.getStatus().equalsIgnoreCase( CONFIRMED.toString() ) && task.getIsPrimary()) {

                nonPrimaryTaskMap = agentTaskService.getTasksByOpportunityId( agentId, task.getOpportunityId() );
                LOGGER.debug( "Fetched nonPrimaryTaskMap  :  {} ", JsonUtil.toJson( nonPrimaryTaskMap ) );

                if (!org.springframework.util.CollectionUtils.isEmpty( nonPrimaryTaskMap )) {
                    // Keep only non primary tasks created by InsideSales
                    final List< AgentTask > tasks = getNonPrimaryTaskOnly( nonPrimaryTaskMap );
                    LOGGER.debug( "nonPrimaryTaskMap after removing other Tasks:  {} ",
                            JsonUtil.toJson( nonPrimaryTaskMap ) );

                    // Update non primary Task in FB & DB
                    if (tasks != null) {
                        tasks.forEach( agentTask -> updateAgentTask( agentId, agentTask, CONFIRMED.name() ) );
                    }

                    final LeadUpdateRequest coShoppingLeadUpdateRequest = agentTaskService
                            .prepareCoshoppingTaskWithStatus( nonPrimaryTaskMap, request.getStatus() );
                    try {
                        coShoppingService.updateLeadDetails( coShoppingLeadUpdateRequest );
                    } catch ( final Exception e ) {
                        LOGGER.error( "Error while updating data to Coshopping for the task id : {}", taskId, e );
                    }
                }
            }

            // send confirmation email for SCHEDULE_TOUR
            if (CONFIRMED.name().equalsIgnoreCase( request.getStatus() )
                    && !CONFIRMED.name().equalsIgnoreCase( task.getStatus() )) {
                final Task updatedTask = agentTaskService.getTaskById( agentId, taskId );
                updatedTask.setId( taskId );
                LOGGER.info( "Updated task received: {}", updatedTask );
                if (TaskType.SCHEDULE_TOUR.getType().equalsIgnoreCase( updatedTask.getTaskType() )
                        && StringUtils.isNotBlank( updatedTask.getListingId() )
                        && StringUtils.isNotBlank( updatedTask.getCoShoppingId() )) {
                    LOGGER.info( "Sending schedule tour confirmation email for the agent id: {}", agentId );
                    tourConfirmationBusiness.sendConfirmationEmailForScheduleTour( agentId, updatedTask );
                }
            }
        }
        return new AgentResponse( taskId );
    }

    private void updateCoShoppingFirebaseLead( final String agentId, final TaskUpdateRequest request,
            final Task task ) {
        final Search existingOpportunity = searchService.searchByOpportunityId( task.getOpportunityId() );
        LOGGER.info( "crm opportunity for the agent is {}", existingOpportunity );
        // If listing is there without co-shopping id then create a entry in
        // co-shopping service
        if (StringUtils.isBlank( task.getCoShoppingId() ) && ( StringUtils.isNotBlank( task.getListingId() )
                || StringUtils.isNotBlank( request.getListingId() ) )) {
            if (StringUtils.isBlank( request.getListingId() )) {
                request.setListingId( task.getListingId() );
            }
            if (StringUtils.isBlank( request.getType() )) {
                request.setType( task.getTaskType() );
            }
            if (null == request.getDueDtm()) {
                request.setDueDtm( new Date( task.getDueDtm() ) );
            }
            if (StringUtils.isBlank( request.getCoShoppingId() )) {
                request.setCoShoppingId( task.getCoShoppingId() );
            }
            final String coShoppingId = tourConfirmationBusiness.createCoshoppingTour( request,
                    existingOpportunity.getCrmOpportunityId(), existingOpportunity.getAgentEmail() );
            task.setCoShoppingId( coShoppingId );
        } else if (StringUtils.isNotBlank( task.getCoShoppingId() )) {
            // If co-shopping id is there then update the changes in co-shopping
            // service
            tourConfirmationBusiness.editCoshoppingTour( request, task, existingOpportunity.getAgentEmail() );
        }
    }

    /**
     * Removes the non primary task.
     *
     * @param nonPrimaryTaskMap
     *            the non primary task map
     */
    private List< AgentTask > getNonPrimaryTaskOnly( final Map< String, Task > nonPrimaryTaskMap ) {
        if (!org.springframework.util.CollectionUtils.isEmpty( nonPrimaryTaskMap )) {
            final List< String > removeTaskIdList = new ArrayList< String >();
            final Set< String > taskIdList = new LinkedHashSet< String >();
            for ( final Entry< String, Task > entry : nonPrimaryTaskMap.entrySet() ) {
                final Task tmpTask = entry.getValue();
                if (!( tmpTask.getCreatedBy().equalsIgnoreCase( INSIDE_SALES ) && !tmpTask.getIsPrimary() )) {
                    removeTaskIdList.add( entry.getKey() );
                } else {
                    taskIdList.add( entry.getKey() );
                }
            }
            for ( final String taskIdToRemove : removeTaskIdList ) {
                nonPrimaryTaskMap.remove( taskIdToRemove );
            }
            return ( !org.springframework.util.CollectionUtils.isEmpty( taskIdList ) )
                    ? agentTaskService.findAll( taskIdList )
                    : null;
        }
        return null;
    }

    /**
     * Gets the property tour information.
     *
     * @param agentEmailId
     *            the agent email id
     * @param dueDtm
     *            the due dtm
     * @return the property tour information
     */
    /*
     * private String getPropertyTourInformation( final String agentEmailId,
     * final Date dueDtm ) {
     * final String agentTimeZone = agentDetailsService.getAgentsTimeZone(
     * agentEmailId );
     * final DateTimeZone dateTimeZone = DateTimeZone.forID( agentTimeZone );
     * LOGGER.info( "Getting tour information for : {}, {}", dueDtm,
     * agentTimeZone );
     * return DateUtil.toString( new LocalDateTime( dueDtm, dateTimeZone ),
     * DateUtil.CO_SHOPPING_PROPERTY_TOUR_INFO_DATE_PATTERN );
     * }
     */

    /**
     * Update agent task details.
     *
     * @param taskId
     *            the task id
     * @param request
     *            the request
     */
    private void updateAgentTaskDetails( final String taskId, final TaskUpdateRequest request ) {
        final AgentTask agentTask = agentTaskService.getByTaskId( taskId );
        if (agentTask != null) {
            LOGGER.info( "updating check in task details" + taskId );
            agentTaskService.saveAgentTask( agentTaskBuilder.convertTo( request, agentTask ) );
        }
    }

    /**
     * Update db task.
     *
     * @param taskId
     *            the task id
     * @param status
     *            the status
     * @param cancellationReason
     *            the cancellation reason
     * @return the agent task status log
     */
    private AgentTaskStatusLog updateDbTask( final String taskId, final String status,
            final String cancellationReason ) {
        final AgentTask agentTask = agentTaskService.getByTaskId( taskId );
        return updateDbTaskLog( status, cancellationReason, null, agentTask );
    }

    /**
     * mark deleted flag to true
     * 
     * @param taskId
     */
    private void deleteDbTask( final String taskId ) {
        final AgentTask agentTask = agentTaskService.getByTaskId( taskId );
        if (null != agentTask) {
            agentTask.setDeleted( TRUE );
            agentTask.setDeletedBy( GRAVITAS );// GRACOR-1300 : Bug fix
            agentTaskService.saveAgentTask( agentTask );
        }
    }

    /**
     * Update db task log.
     *
     * @param status
     *            the status
     * @param cancellationReason
     *            the cancellation reason
     * @param agentEmail
     *            the agent email
     * @param agentTask
     *            the agent task
     * @return the agent task status log
     */
    private AgentTaskStatusLog updateDbTaskLog( final String status, final String cancellationReason,
            final String agentEmail, final AgentTask agentTask ) {
        AgentTaskStatusLog agentTaskStatusLog = null;
        if (agentTask != null) {
            LOGGER.info( "adding check in task status " + status + " for task " + agentTask.getTaskId() );
            if (isNotBlank( status )) {
                agentTaskStatusLog = new AgentTaskStatusLog();
                agentTaskStatusLog.setAgentTask( agentTask );
                agentTaskStatusLog.setStatus( status.toLowerCase() );
                agentTaskStatusLog.setReason( cancellationReason );
                agentTaskStatusLog = agentTaskStatusLogService.save( agentTaskStatusLog );
                agentTask.setStatus( agentTaskStatusLog.getStatus() );
                if (DELETED.name().equalsIgnoreCase( status )) {
                    if (agentTask.getDeletedBy() == null && agentEmail != null) {
                        agentTask.setDeletedBy( agentEmail );
                    }
                    agentTask.setDeleted( true );
                }
                agentTask.setLastModifiedDate( agentTaskStatusLog.getCreatedDate() );
                agentTaskService.saveAgentTask( agentTask );
            }
        }
        return agentTaskStatusLog;
    }

    /**
     * Update db task.
     *
     * @param agentTask
     *            the agent task
     * @param status
     *            the status
     * @param cancellationReason
     *            the cancellation reason
     */
    private AgentTaskStatusLog updateDbTask( final AgentTask agentTask, final String status,
            final String cancellationReason ) {
        AgentTaskStatusLog agentTaskStatusLog = null;
        if (agentTask != null) {
            LOGGER.info( "adding check in task status " + status + " for task " + agentTask.getId() );
            if (isNotBlank( status )) {
                agentTaskStatusLog = new AgentTaskStatusLog();
                agentTaskStatusLog.setAgentTask( agentTask );
                agentTaskStatusLog.setStatus( status.toLowerCase() );
                agentTaskStatusLog.setReason( cancellationReason );
                agentTaskStatusLog = agentTaskStatusLogService.save( agentTaskStatusLog );
                agentTask.setStatus( agentTaskStatusLog.getStatus() );
                agentTask.setLastModifiedDate( agentTaskStatusLog.getCreatedDate() );
                agentTaskService.saveAgentTask( agentTask );
            }
        }
        return agentTaskStatusLog;
    }

    /**
     * Adds the param if not blank.
     *
     * @param taskParams
     *            the task params
     * @param paramName
     *            the param name
     * @param paramValue
     *            the param value
     */
    private void addParamIfNotBlank( final Map< String, Object > taskParams, final String paramName,
            final Object paramValue ) {
        if (paramValue != null) {
            taskParams.put( paramName, paramValue );
        }
    }

    /**
     * Close agent task.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param request
     *            the request
     * @return the agent response
     */
    private AgentResponse closeAgentTask( final String agentId, final String taskId, final TaskUpdateRequest request ) {
        LOGGER.info( "Close task for agent " + agentId + " and task " + taskId );
        final Map< String, Object > taskParams = new HashMap<>();
        String status = COMPLETED.name();
        String cancellationReason = null;
        if (request != null && StringUtils.isNotBlank( request.getStatus() )) {
            status = COMPLETE.name().equals( request.getStatus() ) ? COMPLETED.name() : request.getStatus();
        }
        if (request != null && StringUtils.isNotBlank( request.getCancellationReason() )) {
            cancellationReason = request.getCancellationReason();
        }
        final AgentTaskStatusLog taskStatusLog = updateDbTask( taskId, status, cancellationReason );
        final Long now = taskStatusLog == null ? new DateTime().getMillis()
                : taskStatusLog.getCreatedDate().getMillis();
        taskParams.put( "completedBy", agentId );
        taskParams.put( "completedDtm", now );
        taskParams.put( "lastModifiedDtm", now );
        taskParams.put( "status", status );
        addParamIfNotBlank( taskParams, "cancellationReason", cancellationReason );
        if (request != null && StringUtils.isNotEmpty( request.getDescription() )) {
            addParamIfNotBlank( taskParams, "description", request.getDescription() );
        }

        agentTaskService.patchAgentTask( taskId, agentId, taskParams );
        agentReminderBusinessService.cancelAgentTaskReminders( agentId, taskId );
        return new AgentResponse( taskId );
    }

    /**
     * Confirm pending agent task.
     *
     * @param agentId
     *            the agent id
     * @param task
     *            the task id
     * @param request
     *            the request
     * @return the agent response
     */
    private Task updateAgentTask( final String agentId, final AgentTask task, final String status ) {
        LOGGER.info( "Confirmed task for agent " + agentId + " and task " + task.getTaskId() );
        final String oldTaskStatus = task.getStatus();
        final Map< String, Object > taskParams = new HashMap<>();
        final AgentTaskStatusLog taskStatusLog = updateDbTask( task, status, null );
        final Long now = taskStatusLog == null ? new DateTime().getMillis()
                : taskStatusLog.getCreatedDate().getMillis();
        taskParams.put( "lastModifiedDtm", now );
        taskParams.put( "status", status );
        // As per business requirement setting completedDtm & completedBy on
        // Task Completed.
        LOGGER.info( "oldTaskStatus : {}, status after DB update : {}", oldTaskStatus, task.getStatus() );
        if (!oldTaskStatus.equalsIgnoreCase( COMPLETED.name() ) && status != null
                && status.equalsIgnoreCase( COMPLETED.name() )) {
            taskParams.put( "completedDtm", now );
            taskParams.put( "completedBy", GRAVITAS );
        }
        return agentTaskService.patchAgentTask( task.getTaskId(), agentId, taskParams );
    }

    /**
     * Save task.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param type
     *            the type
     * @param title
     *            the title
     * @param name
     *            the name
     * @return the post response
     */
    private PostResponse saveTask( final String agentId, final String opportunityId, final TaskType type,
            final String title, final String name ) {
        final String titleTxt = String.format( title, name );
        final Task task = new Task( titleTxt, opportunityId, type.getType(), agentId, new DateTime().getMillis() );
        task.setStatus( INCOMPLETE.name() );
        task.setTag( SYSTEM_GENERATED );
        final PostResponse postResponse = agentTaskService.saveAgentTask( task, agentId );
        saveDbTask( task, postResponse.getName() );
        return postResponse;
    }

    /**
     * Creates the task for schedule tour.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param request
     *            the request
     */
    private void createTaskForScheduleTour( final String agentId, final String taskId,
            final TaskUpdateRequest request ) {
        if (request != null && request.getScheduleDtm() != null) {
            LOGGER.info( "Create Scheduled Task for agent " + agentId + " and task " + taskId );
            final AgentTaskRequest taskRequest = new AgentTaskRequest();
            final Task task = agentTaskService.getTaskById( agentId, taskId );
            taskRequest.setTitle( SCHEDULED_TOUR );
            taskRequest.setDueDtm( request.getScheduleDtm() );
            String propertyLocation = StringUtils.EMPTY;
            try {
                propertyLocation = getPropertyLocation( agentId, taskId, task );
            } catch ( final ApplicationException ae ) {
                LOGGER.info( "problem getting property details", ae );
            }
            if (StringUtils.isNotBlank( propertyLocation )) {
                taskRequest.setLocation( propertyLocation );
            }
            createAgentTask( agentId, task.getOpportunityId(), taskRequest );
        }
    }

    /**
     * Gets the property location.
     *
     * @param agentId
     *            the agent id
     * @param taskId
     *            the task id
     * @param task
     *            the task
     * @return the property location
     */
    private String getPropertyLocation( final String agentId, final String taskId, final Task task ) {
        String listingId = StringUtils.EMPTY;
        final Map< String, Request > requests = agentRequestService.getRequestsByOpportunityId( agentId,
                task.getOpportunityId() );
        if (MapUtils.isNotEmpty( requests )) {
            for ( final Request buyerRequest : requests.values() ) {
                if (buyerRequest.getTaskId().equalsIgnoreCase( taskId )) {
                    listingId = buyerRequest.getListingId();
                    break;
                }
            }
        }
        return propertyBusinessService.getPropertyLocation( listingId );
    }

    /**
     * Copy reminders.
     *
     * @param newAgentId
     *            the new agent id
     * @param newTaskId
     *            the new task id
     * @param newTask
     *            the new task
     * @param oldReminders
     *            the old reminders
     * @param newContacts
     *            the new contacts
     * @return the map
     */
    private final Map< String, Reminder > copyReminders( final String newAgentId, final String newTaskId,
            final Task newTask, final Map< String, Reminder > oldReminders, final Map< String, String > newContacts ) {
        final Map< String, Reminder > newReminders = new HashMap<>();
        if (MapUtils.isNotEmpty( oldReminders )) {
            Contact newContact = null;
            for ( final String contactId : newContacts.keySet() ) {
                final com.owners.gravitas.domain.Contact contact = agentContactService.getContactById( newAgentId,
                        contactId );
                newContact = contactBuilder.convertTo( contact );
            }
            for ( final Reminder reminder : oldReminders.values() ) {
                final String reminderId = UUID.randomUUID().toString();
                final List< String > notificationIds = agentReminderBusinessService.pushReminderNotification(
                        newAgentId, newTaskId, newContact, reminder.getTriggerDtm(), newTask,
                        PushNotificationType.SCHEDULED_TASK_APPOINTMENT_REMINDER );
                reminder.setNotificationIds( notificationIds );
                newReminders.put( reminderId, reminder );
                LOGGER.info( "New reminder created and copied to task " + newTaskId );
            }
            LOGGER.info( "New reminders created for task " + newTask.getTaskType() );
        }
        return newReminders;
    }

    /**
     * Gets the reminders.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param taskEntry
     *            the task entry
     * @param existingTask
     *            the existing task
     * @return the reminders
     */
    private Map< String, Reminder > getReminders( final String agentId, final String opportunityId,
            final Map.Entry< String, Task > taskEntry, final Task existingTask ) {
        final Map< String, Reminder > reminders = new HashMap<>();
        if (MapUtils.isNotEmpty( existingTask.getReminders() )) {
            for ( final Map.Entry< String, Reminder > reminderEntry : existingTask.getReminders().entrySet() ) {
                final Reminder reminder = reminderEntry.getValue();
                if (reminder != null) {
                    final List< String > notificationIds = agentReminderBusinessService.pushReminderNotification(
                            agentId, taskEntry.getKey(), opportunityId, reminder.getTriggerDtm(), existingTask,
                            SCHEDULED_TASK_APPOINTMENT_REMINDER );
                    reminder.setNotificationIds( notificationIds );
                    reminders.put( reminderEntry.getKey(), reminder );
                }
            }
        }
        return reminders;
    }

    /**
     * Gets the previous task status.
     *
     * @param taskId
     *            the task id
     * @return the previous status
     */
    private String getPreviousTaskStatus( final String taskId ) {
        String status = INCOMPLETE.name();
        final AgentTask agentTask = agentTaskService.getByTaskId( taskId );
        if (agentTask != null) {
            final AgentTaskStatusLog statusLogs = agentTaskStatusLogService.findTopByAgentTaskAndStatus( agentTask,
                    UNASSIGNED.name() );

            status = statusLogs.getStatus();
        }
        return status;
    }

    /**
     * Checks if is not completed or cancelled.
     *
     * @param status
     *            the status
     * @return true, if is not completed or cancelled
     */
    private boolean isNotCompletedOrCancelled( final String status ) {
        return ( isBlank( status ) ? false
                : !( COMPLETED.name().equalsIgnoreCase( status ) || CANCELLED.name().equalsIgnoreCase( status ) ) );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.AgentTaskBusinessService#deleteTasks(java.
     * lang.String, java.lang.String)
     */
    @Override
    public void deleteTasks( final String opportunityId, final String agentId ) {
        LOGGER.info( "Deleting tasks for " + opportunityId + " and " + agentId );
        final Map< String, Task > tasks = agentTaskService.getTasksByOpportunityId( agentId, opportunityId );
        for ( final Map.Entry< String, Task > taskEntry : tasks.entrySet() ) {
            taskEntry.getValue().setDeleted( TRUE );
            taskEntry.getValue().setLastModifiedDtm( new Date().getTime() );
            deleteDbTask( taskEntry.getKey() );
            if (null != taskEntry.getValue().getCompletedDtm()) {
                updateDbTask( taskEntry.getKey(), UNASSIGNED.name(), null );
            }
            final Map< String, Reminder > oldReminders = taskEntry.getValue().getReminders();
            agentReminderBusinessService.cancelReminders( agentId, taskEntry.getKey(), oldReminders );
            //GRACOR-1416 : In case of completed task, we should not send status as cancelled to co-shopping
            LOGGER.info( "Updating deleted task status to cancelled in co-shopping for the task status {}",
                    taskEntry.getValue().getStatus() );
            if (!COMPLETED.name().equalsIgnoreCase( taskEntry.getValue().getStatus() )) {
                agentTaskService.pushStatusToCoshopping( taskEntry.getValue(), CANCELLED.name() );
            }
        }
        agentTaskService.saveAgentTasks( tasks, agentId );
    }
    
    /**
     * Check complete status.
     *
     * @param request
     *            the request
     */
    private void checkCompleteStatus( final TaskUpdateRequest request ) {
        if (COMPLETE.name().equalsIgnoreCase( request.getStatus() )) {
            request.setStatus( COMPLETED.name() );
        }
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentTaskBusinessService#
     * checkIfAgentMeetingEventExists(java.lang.String, java.lang.String)
     */
    @Override
    public CheckScheduleMeetingValidationResponse checkIfAgentMeetingEventExists( final String agentEmail,
            final String crmId ) {
        LOGGER.info( "checking if events exists for agentEmail : {} crmId : {} ", agentEmail, crmId );
        final CheckScheduleMeetingValidationResponse checkScheduleMeetingValidationResponse = new CheckScheduleMeetingValidationResponse();
        if (coShoppingConfig.isEnableScheduleTourMeetings()) {
            checkScheduleMeetingValidationResponse.setScheduleTourMeetingEnabled( true );
        }
        final Search existingOpportunity = searchService.searchByCrmOpportunityId( crmId );
        if (null != existingOpportunity) {
            LOGGER.info( "checking if events exists in firebase for agent : {} & opportunity : {} ",
                    existingOpportunity.getAgentId(), existingOpportunity.getOpportunityId() );
            final Map< String, Task > tasksMap = agentTaskService.getTasksByOpportunityId(
                    existingOpportunity.getAgentId(), existingOpportunity.getOpportunityId() );
            if (checkIfTaskExists( tasksMap )) {
                checkScheduleMeetingValidationResponse.setValidationsPassed( false );
                return checkScheduleMeetingValidationResponse;
            }
        }
        checkScheduleMeetingValidationResponse.setValidationsPassed( true );
        return checkScheduleMeetingValidationResponse;
    }

    /**
     * Method to check if there are any tasks created by Inside Sales.
     *
     * @param tasksMap
     *            The map containing the agent Tasks
     *
     * @return true if tasks created by Inside Sales else false
     */
    private boolean checkIfTaskExists( final Map< String, Task > tasksMap ) {
        boolean isTaskExists = false;
        if (null != tasksMap && !tasksMap.isEmpty()) {
            for ( final Map.Entry< String, Task > entry : tasksMap.entrySet() ) {
                if (null != entry) {
                    final Task task = entry.getValue();
                    if (task != null && INSIDE_SALES.equalsIgnoreCase( task.getCreatedBy() ) && !task.getDeleted()) {
                        isTaskExists = true;
                    }
                }
            }
        }
        return isTaskExists;
    }
}
