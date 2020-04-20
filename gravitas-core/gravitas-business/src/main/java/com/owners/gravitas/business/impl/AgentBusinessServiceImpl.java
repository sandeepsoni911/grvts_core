package com.owners.gravitas.business.impl;

import static com.owners.gravitas.constants.Constants.COMMA;
import static com.owners.gravitas.constants.Constants.CRM_AGENT_PHONE;
import static com.owners.gravitas.dto.response.BaseResponse.Status.FAILURE;
import static com.owners.gravitas.dto.response.BaseResponse.Status.SUCCESS;
import static com.owners.gravitas.enums.CRMObject.AGENT;
import static com.owners.gravitas.enums.ErrorCode.EMAIL_REQUEST_REQUIRED;
import static com.owners.gravitas.enums.GravitasProcess.AGENT_AVAILABILITY_PROCESS;
import static com.owners.gravitas.enums.PushNotificationType.ON_DUTY_AGENT;
import static com.owners.gravitas.enums.PushNotificationType.UPDATE_BADGE_COUNTER;
import static com.owners.gravitas.enums.RecordType.OWNERS_COM;
import static com.owners.gravitas.enums.UserStatusType.ACTIVE;
import static com.owners.gravitas.util.StringUtils.convertObjectToString;
import static com.owners.gravitas.util.StringUtils.removeDoubleQuotes;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.apache.commons.collections4.MapUtils.isEmpty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.RuntimeService;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.api.client.util.ArrayMap;
import com.google.api.services.admin.directory.model.User;
import com.google.api.services.admin.directory.model.UserPhoto;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.amqp.AgentSource;
import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.ActionLogBusinessService;
import com.owners.gravitas.business.AgentBusinessService;
import com.owners.gravitas.business.AgentEmailBusinessService;
import com.owners.gravitas.business.AgentNotificationBusinessService;
import com.owners.gravitas.business.AgentTaskBusinessService;
import com.owners.gravitas.business.ProcessBusinessService;
import com.owners.gravitas.business.UserBusinessService;
import com.owners.gravitas.business.builder.ActionLogBuilder;
import com.owners.gravitas.business.builder.AgentDetailsBuilder;
import com.owners.gravitas.business.builder.AgentOnboardEmailNotificationBuilder;
import com.owners.gravitas.business.builder.AgentOnboardRequestBuilder;
import com.owners.gravitas.business.builder.AgentSourceResponseBuilder;
import com.owners.gravitas.business.builder.CRMAgentBuilder;
import com.owners.gravitas.business.builder.EscrowEmailNotificationBuilder;
import com.owners.gravitas.business.builder.GmailMessageBuilder;
import com.owners.gravitas.business.builder.PendingSalePTSEmailNotificationBuilder;
import com.owners.gravitas.business.builder.PreliminaryTitleReportsEmailNotificationBuilder;
import com.owners.gravitas.business.builder.SoldStagePTSEmailNotificationBuilder;
import com.owners.gravitas.business.builder.UserBuilder;
import com.owners.gravitas.business.builder.domain.GoogleUserBuilder;
import com.owners.gravitas.business.builder.domain.SearchBuilder;
import com.owners.gravitas.business.builder.request.CRMAgentRequestBuilder;
import com.owners.gravitas.business.builder.request.ErrorSlackRequestBuilder;
import com.owners.gravitas.business.builder.response.ActionLogResponseBuilder;
import com.owners.gravitas.business.task.AuditLogTask;
import com.owners.gravitas.business.tour.TourConfirmationBusiness;
import com.owners.gravitas.config.AgentEmailJmxConfig;
import com.owners.gravitas.config.AgentTopicJmxConfig;
import com.owners.gravitas.config.BadgeCounterJmxConfig;
import com.owners.gravitas.domain.ActionGroup;
import com.owners.gravitas.domain.Agent;
import com.owners.gravitas.domain.AgentHolder;
import com.owners.gravitas.domain.AgentInfo;
import com.owners.gravitas.domain.AgentPreference;
import com.owners.gravitas.domain.Device;
import com.owners.gravitas.domain.LastViewed;
import com.owners.gravitas.domain.Note;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.domain.OpportunityDataNode;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.domain.Task;
import com.owners.gravitas.domain.entity.ActionLog;
import com.owners.gravitas.domain.entity.AgentAvailabilityLog;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.domain.entity.OpportunityAction;
import com.owners.gravitas.domain.entity.Process;
import com.owners.gravitas.dto.SlackError;
import com.owners.gravitas.dto.crm.response.CRMAgentResponse;
import com.owners.gravitas.dto.request.ActionRequest;
import com.owners.gravitas.dto.request.AgentDeviceRequest;
import com.owners.gravitas.dto.request.AgentNoteRequest;
import com.owners.gravitas.dto.request.AgentOnboardRequest;
import com.owners.gravitas.dto.request.AgentSuggestedPropertyRequest;
import com.owners.gravitas.dto.request.EmailRequest;
import com.owners.gravitas.dto.request.LastViewedRequest;
import com.owners.gravitas.dto.request.NotificationRequest;
import com.owners.gravitas.dto.request.OpportunityDataRequest;
import com.owners.gravitas.dto.request.SlackRequest;
import com.owners.gravitas.dto.response.ActionLogResponse;
import com.owners.gravitas.dto.response.AgentEmailResponse;
import com.owners.gravitas.dto.response.AgentEmailResult;
import com.owners.gravitas.dto.response.AgentResponse;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.enums.BuyerStage;
import com.owners.gravitas.enums.ErrorCode;
import com.owners.gravitas.enums.TaskType;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.exception.ResultNotFoundException;
import com.owners.gravitas.service.ActionGroupService;
import com.owners.gravitas.service.ActivitiService;
import com.owners.gravitas.service.AgentAvailabilityLogService;
import com.owners.gravitas.service.AgentContactService;
import com.owners.gravitas.service.AgentDetailsService;
import com.owners.gravitas.service.AgentInfoService;
import com.owners.gravitas.service.AgentNoteService;
import com.owners.gravitas.service.AgentOpportunityService;
import com.owners.gravitas.service.AgentPreferenceService;
import com.owners.gravitas.service.AgentService;
import com.owners.gravitas.service.AgentSuggestedPropertyService;
import com.owners.gravitas.service.AgentTaskService;
import com.owners.gravitas.service.GmailService;
import com.owners.gravitas.service.MailService;
import com.owners.gravitas.service.OpportunityDataNodeService;
import com.owners.gravitas.service.RecordTypeService;
import com.owners.gravitas.service.RoleMemberService;
import com.owners.gravitas.service.SearchService;
import com.owners.gravitas.service.SlackService;
import com.owners.gravitas.service.UserService;
import com.owners.gravitas.util.GravitasWebUtil;
import com.owners.gravitas.util.JsonUtil;

/**
 * The Class AgentBusinessServiceImpl performs below operations. 1) Process the
 * opportunity changes from CRM. 2) Process opportunity contact changes from
 * CRM. 3) Register new agent if logging in first time. 4) Synchronize all
 * opportunities from CRM. 5) Add agent notes. 6) Update opportunity stage. 7)
 * Add and remove device.
 *
 * @author amits
 */
@Service( "agentBusinessService" )
@Transactional( readOnly = true )
public class AgentBusinessServiceImpl implements AgentBusinessService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentBusinessServiceImpl.class );

    /** The agent service. */
    @Autowired
    private AgentService agentService;

    /** The agent note service. */
    @Autowired
    private AgentNoteService agentNoteService;

    /** The agent info service. */
    @Autowired
    private AgentInfoService agentInfoService;

    @Autowired
    private AgentPreferenceService agentPreferenceService;

    /** The search holder builder. */
    @Autowired
    private SearchBuilder searchBuilder;

    /** The agent opportunity service. */
    @Autowired
    private AgentOpportunityService agentOpportunityService;

    /** The search service. */
    @Autowired
    private SearchService searchService;

    /** The action log business service. */
    @Autowired
    private ActionLogBusinessService actionLogBusinessService;

    /** The action log builder. */
    @Autowired
    private ActionLogBuilder actionLogBuilder;

    /** The action log response builder. */
    @Autowired
    private ActionLogResponseBuilder actionLogResponseBuilder;

    /** The agent suggested property service. */
    @Autowired
    private AgentSuggestedPropertyService agentSuggestedPropertyService;

    @Autowired
    private AgentTaskBusinessService agentTaskBusinessService;

    /** The cta values. */
    private List< String > ctaValues = new ArrayList<>();

    /** The enable opportunity buyer request. */
    @Value( "${agent.app.cta.actions}" )
    private String ctaValuesString;

    /** The slack service impl. */
    @Autowired
    private SlackService slackService;

    /** The error slack request builder. */
    @Autowired
    private ErrorSlackRequestBuilder errorSlackRequestBuilder;

    /** The premium title service notification builder. */
    @Autowired
    private AgentOnboardEmailNotificationBuilder agentOnboardEmailNotificationBuilder;

    /** The pending sale PTS email notification builder. */
    @Autowired
    private PendingSalePTSEmailNotificationBuilder pendingSalePTSEmailNotificationBuilder;

    /** The sold stage PTS email notification builder. */
    @Autowired
    private SoldStagePTSEmailNotificationBuilder soldStagePTSEmailNotificationBuilder;

    /** The escrow email notification builder. */
    @Autowired
    private EscrowEmailNotificationBuilder escrowEmailNotificationBuilder;

    /** The preliminary title reports email notification builder. */
    @Autowired
    private PreliminaryTitleReportsEmailNotificationBuilder preliminaryTitleReportsEmailNotificationBuilder;

    /** The mail service. */
    @Autowired
    private MailService mailService;

    /** The agent error slack channel. */
    @Value( "${slack.channel.url.agents.errors}" )
    private String agentErrorSlackChannel;

    /** The crm agent request builder. */
    @Autowired
    private CRMAgentRequestBuilder crmAgentRequestBuilder;

    /** The agent details service. */
    @Autowired
    private AgentDetailsService agentDetailsService;

    /** The agent details builder. */
    @Autowired
    private AgentDetailsBuilder agentDetailsBuilder;

    /** The user builder. */
    @Autowired
    private GoogleUserBuilder googleUserBuilder;

    /** The agent user builder. */
    @Autowired
    private UserBuilder userBuilder;

    /** The agent onboarding service. */
    @Autowired
    private UserService userService;

    /** The user business service. */
    @Autowired
    private UserBusinessService userBusinessService;

    /** The agent email business service. */
    @Autowired
    private AgentEmailBusinessService agentEmailBusinessService;

    /** The gmail message builder. */
    @Autowired
    private GmailMessageBuilder gmailMessageBuilder;

    /** The gmail service. */
    @Autowired
    private GmailService gmailService;

    /** The role service. */
    @Autowired
    private RoleMemberService roleMemberService;

    /** The gravitas web util. */
    @Autowired
    private GravitasWebUtil gravitasWebUtil;

    /** The agent email jmx config. */
    @Autowired
    private AgentEmailJmxConfig agentEmailJmxConfig;

    /** The agent source to agent details builder. */
    @Autowired
    private CRMAgentBuilder crmAgentBuilder;

    /** The agent url. */
    @Value( value = "${salesforce.agent.url}" )
    private String agentUrl;

    /** The record type service. */
    @Autowired
    private RecordTypeService recordTypeService;

    /** The agent onboard request builder. */
    @Autowired
    private AgentOnboardRequestBuilder agentOnboardRequestBuilder;

    /** The agent topic jmx config. */
    @Autowired
    private AgentTopicJmxConfig agentTopicJmxConfig;

    /** The Constant DISABLE_EMAIL_PREFIX. */
    private final static String DISABLE_EMAIL_PREFIX = "disabled_";

    /** The Constant FIELD_AGENT. */
    private final static String FIELD_AGENT = "Field Agent";

    /** The Constant INACTIVE_STATUS. */
    private final static String INACTIVE_STATUS = "inactive";

    /** The Constant AGENT_EMAIL. */
    private static final String AGENT_EMAIL = "agentEmail";

    /** The Constant OFF_DUTY_START_TIME. */
    private static final String OFF_DUTY_START_TIME = "offDutyStartTime";

    /** The Constant OFF_DUTY_END_TIME. */
    private static final String OFF_DUTY_END_TIME = "offDutyEndTime";

    /** The Constant REQUEST. */
    private static final String REQUEST = "request";

    /** The Constant AGENT_ID. */
    private static final String AGENT_ID = "agentId";

    /** The Constant ON_DUTY. */
    private static final String ON_DUTY = "onDuty";

    /** The Constant obj. */
    private final static Map< String, String > crmAgentFields = new HashMap< String, String >();

    @Value( "${action.flow.facetoface.order.id}" )
    private String F2F_MEETING_ACTION_ID;

    /** The agent source response builder. */
    @Autowired
    private AgentSourceResponseBuilder agentSourceResponseBuilder;

    /** The action group service. */
    @Autowired
    private ActionGroupService actionGroupService;

    /** The audit log task. */
    @Autowired
    private AuditLogTask auditLogTask;

    /** The agent availability log service. */
    @Autowired
    private AgentAvailabilityLogService agentAvailabilityLogService;

    /** The process business service. */
    @Autowired
    private ProcessBusinessService processBusinessService;

    /** The runtime service. */
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ActivitiService activitiService;

    /** The agent notification business service. */
    @Autowired
    private AgentNotificationBusinessService agentNotificationBusinessService;

    /** The badge counter jmx config. */
    @Autowired
    private BadgeCounterJmxConfig badgeCounterJmxConfig;

    /** The agent contact service. */
    @Autowired
    private AgentContactService agentContactService;

    @Autowired
    private OpportunityDataNodeService opportunityDataNodeService;
    
    @Autowired
    private TourConfirmationBusiness tourConfirmationBusiness;
    
    /** The agent task service. */
    @Autowired
    private AgentTaskService agentTaskService;

    @Value( "${opportunity.data.node.valid.keys}" )
    private String validKeysForOppsDataNode;

    /**
     * Initialize cta values.
     */
    @PostConstruct
    private void initializeValues() {
        ctaValues.addAll( Arrays.asList( ctaValuesString.split( COMMA ) ) );
        crmAgentFields.put( "onDuty", "Active__c" );
    }

    /**
     * Register agent if the agent record not in firebase account with fetching
     * all agent's opportunities, contacts and requests from CRM.
     *
     * @param agentId
     *            the agent id
     * @param agentEmail
     *            the agent email
     * @return the agent response
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public AgentResponse register( final String agentId, final String agentEmail ) {
        final Search agentSearch = searchService.searchByAgentId( agentId );
        AgentResponse response = new AgentResponse( agentId, Status.SUCCESS, "Agent already registered" );

        LOGGER.info( "Agent registration started for " + agentEmail );
        if (agentSearch == null) {
            LOGGER.info( "Agent is not registered. Registration process started for " + agentEmail );
            final AgentHolder agentHolder = new AgentHolder( agentId );
            final Agent agent = new Agent();
            // create agent info and sent to agent
            agent.setInfo( createAgentInfo( agentEmail ) );
            agentHolder.setAgent( agent );
            final Map< String, Search > searchMap = searchBuilder.convertTo( agentHolder );
            try {
                agentService.saveAgent( agentHolder );
                searchService.saveAll( searchMap );
            } catch ( final Exception ex ) {
                rollbackRegistration( agentHolder, searchMap );
                throw ex;
            }
            response = new AgentResponse( agentId );
            LOGGER.info( "Agent registration completed for " + agentEmail );
        }
        return response;
    }

    /**
     * Handle agent notes insert.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param request
     *            the request
     * @return the opportunity response
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public AgentResponse addAgentNote( final String agentId, final String opportunityId,
            final AgentNoteRequest request ) {
        LOGGER.info( "Creating note for agent " + agentId );
        AgentResponse response = null;
        final Opportunity opportunity = agentOpportunityService.getOpportunityById( agentId, opportunityId );
        if (opportunity != null) {
            final Note agentNote = new Note();
            agentNote.setOpportunityId( opportunityId );
            agentNote.setDetails( request.getDetails() );
            agentNote.setCreatedDtm( new Date().getTime() );
            agentNote.setLastModifiedDtm( new Date().getTime() );
            agentNote.setDeleted( Boolean.FALSE );
            final PostResponse postResponse = agentNoteService.saveAgentNote( agentNote, agentId );
            response = new AgentResponse( postResponse.getName() );
        }
        return response;
    }

    /**
     * Update agent note.
     *
     * @param agentId
     *            the agent id
     * @param noteId
     *            the note id
     * @param request
     *            the request
     * @return the agent response
     */
    @Override
    public AgentResponse updateAgentNote( final String agentId, final String noteId, final AgentNoteRequest request ) {
        LOGGER.info( "Updating note: " + noteId + " for agent: " + agentId );
        final Note existingAgentNote = agentNoteService.getAgentNote( agentId, noteId );
        if (existingAgentNote.isDeleted()) {
            throw new ApplicationException( "Agent note: " + noteId + " deleted from system.",
                    ErrorCode.NOTE_DELETED_ERROR );
        }
        final Note updatedNote = new Note();
        updatedNote.setDetails( request.getDetails() );
        updatedNote.setLastModifiedDtm( new Date().getTime() );
        agentNoteService.updateAgentNote( updatedNote, agentId, noteId );
        return new AgentResponse( noteId );
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
    @Override
    public AgentResponse deleteAgentNote( final String agentId, final String noteId ) {
        LOGGER.debug( "Deleting note: " + noteId + " for agent: " + agentId );
        agentNoteService.getAgentNote( agentId, noteId );
        final Note updatedNote = new Note();
        updatedNote.setDeleted( Boolean.TRUE );
        updatedNote.setLastModifiedDtm( new Date().getTime() );
        agentNoteService.updateAgentNote( updatedNote, agentId, noteId );
        return new AgentResponse( noteId );
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
    @Override
    public AgentResponse addDevice( final String agentId, final AgentDeviceRequest request ) {
        final AgentInfo existingInfo = agentInfoService.getAgentInfo( agentId );
        final AgentInfo agentInfo = new AgentInfo();
        agentInfo.setDevices( existingInfo.getDevices() );
        final Device device = new Device();
        device.setId( request.getDeviceId() );
        device.setType( request.getDeviceType().toString() );
        agentInfo.removeDevice( device );
        agentInfo.addDevice( device );
        agentInfo.setLastModifiedDtm( new Date().getTime() );
        agentInfoService.patchAgentInfo( agentId, agentInfo );
        if (badgeCounterJmxConfig.isBadgeCountEnabled()) {
            LOGGER.info( "Agent logging in sending count of new opportunity for agent : {}", agentId );
            final NotificationRequest notificationRequest = new NotificationRequest(
                    agentOpportunityService.getAgentNewOpportunitiesCount( agentId ) );
            notificationRequest.setEventType( UPDATE_BADGE_COUNTER );
            agentNotificationBusinessService.sendPushNotification( agentId, notificationRequest );
        }
        return new AgentResponse( request.getDeviceId(), Status.SUCCESS, "Agent device registered successfully" );
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
    @Override
    public AgentResponse removeDevice( final String agentId, final String deviceId ) {
        String reponseMsg = "Agent device un-registered successfully";
        final AgentInfo agentInfo = new AgentInfo();
        final Device device = new Device();
        device.setId( deviceId );
        final AgentInfo existingInfo = agentInfoService.getAgentInfo( agentId );
        agentInfo.setDevices( existingInfo.getDevices() );
        if (!agentInfo.removeDevice( device )) {
            reponseMsg = "Agent device is not registered with system";
        } else {
            agentInfo.setLastModifiedDtm( new Date().getTime() );
        }
        agentInfoService.patchAgentInfo( agentId, agentInfo );
        return new AgentResponse( deviceId, Status.SUCCESS, reponseMsg );
    }

    /**
     * Update last viewed.
     *
     * @param agentId
     *            the agent id
     * @param id
     *            the id
     * @param viewedRequest
     *            the viewed request
     * @return the agent response
     */
    @Override
    public AgentResponse updateLastViewed( final String agentId, final String id,
            final LastViewedRequest viewedRequest ) {
        String node = null;
        switch ( viewedRequest.getObjectType().toLowerCase() ) {
            case "opportunity":
                node = "opportunities";
                break;
            case "task":
                node = "tasks";
                break;
            case "request":
                node = "requests";
                break;
            default:
                break;
        }
        final LastViewed lastViewed = agentService.updateLastViewed( agentId, id, node );
        auditLogTask.auditLastViewed( agentId, id, viewedRequest, lastViewed );
        LOGGER.debug( "Firebase updated successfully for " + node + " with id" + id );
        return new AgentResponse( id );
    }

    /**
     * Creates the agent action.
     *
     * @param email
     *            the email
     * @param actionRequest
     *            the action request
     * @return the base response
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public AgentResponse createAgentAction( final String email, final ActionRequest actionRequest ) {
        ActionLog actionLog = actionLogBuilder.convertTo( actionRequest );
        actionLog.setActionBy( email );
        actionLog = actionLogBusinessService.saveActionLog( actionLog );
        return new AgentResponse( actionLog.getId() );
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
    @Override
    public ActionLogResponse getCTAAuditLogs( final String agentId, final String opportunityId, final String ctaType ) {
        final List< String > ctaList = new ArrayList<>();
        if (StringUtils.isNotBlank( ctaType )) {
            final StringTokenizer ctaTokenizer = new StringTokenizer( ctaType, COMMA );
            while ( ctaTokenizer.hasMoreElements() ) {
                ctaList.add( ctaTokenizer.nextToken().trim().toUpperCase() );
            }
        } else {
            ctaList.addAll( ctaValues );
        }
        final AgentInfo info = agentInfoService.getAgentInfo( agentId );
        final List< ActionLog > actionLogs = actionLogBusinessService.getCTAAuditLogs( info.getEmail(), opportunityId,
                ctaList );
        return actionLogResponseBuilder.convertTo( actionLogs );
    }

    /**
     * Post error details to slack channel.
     *
     * @param error
     *            the error
     */
    @Override
    public void postErrorToSlack( final SlackError error ) {
        final SlackRequest errorRequest = errorSlackRequestBuilder.convertTo( error );
        slackService.publishToSlack( errorRequest, agentErrorSlackChannel );
    }

    /**
     * Send pts email notification.
     *
     * @param agentSource
     *            the agent source
     */
    @Override
    public void sendPTSEmailNotifications( final AgentSource agentSource ) {
        LOGGER.debug( "sending PTS notifocations to " + agentSource.getEmail() );
        sendOnBoardsEmailNotification( agentSource );
        sendEscrowEmailNotification( agentSource );
        sendPreliminaryTitleReportsEmailNotification( agentSource );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentBusinessService#
     * sendPendingSalePTSEmailNotifications(boolean,
     * com.owners.gravitas.amqp.AgentSource)
     */
    @Override
    public void sendPendingSalePTSEmailNotification( final boolean pts, final AgentSource agentSource,
            final OpportunitySource opportunitySource ) {
        final EmailNotification notification = pendingSalePTSEmailNotificationBuilder.convertTo( pts, agentSource,
                opportunitySource );
        if (null != notification) {
            LOGGER.debug( "sending pending sale pts email notification to " + agentSource.getEmail() );
            mailService.send( notification );
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.AgentBusinessService#updateAction(java.lang.
     * String, java.lang.String, java.lang.String,
     * com.owners.gravitas.domain.Action)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public BaseResponse updateAction( final String agentId, final String actionFlowId, final String actionId,
            final Map< String, Object > actions ) {
        LOGGER.info( "Update action flow " + actionFlowId + " action " + actionId );
        Map< String, Object > actionMap = actions;
        List< OpportunityAction > actionList = actionGroupService.getStartTime( actionFlowId );
        final long currentMillis = new Date().getTime();
        final String apiUserEmail = gravitasWebUtil.getAppUserEmail();
        OpportunityAction oppAction = null;
        if (CollectionUtils.isEmpty( actionList )) {
            final ActionGroup actionGroup = actionGroupService.getActionGroup( agentId, actionFlowId );
            actionList = actionGroupService.saveActionGroup( actionFlowId, actionGroup );
        }

        try {
            oppAction = actionList.stream().filter( action -> action.getActionId().equalsIgnoreCase( actionId ) )
                    .findAny().get();
        } catch ( final NoSuchElementException e ) {
            LOGGER.info( "Invalid actionId " + e.getMessage() );
        }
        if (oppAction != null) {

            if (isEmpty( actionMap )) {
                actionMap = setActionLastVisitedDtm( agentId, actionFlowId, actionMap, actionList, currentMillis,
                        apiUserEmail );
            } else {
                updateLastUpdateDtm( actionMap, currentMillis, oppAction );
            }
            actionGroupService.patchAgentActionInfo( agentId, actionFlowId, actionId, actionMap );
            oppAction.setLastActionVisitedOn( new DateTime( currentMillis ) );
            if (actionMap.containsKey( "complete" )) {
                oppAction.setCompleted( ( boolean ) actionMap.get( "complete" ) );
            }
        }
        actionGroupService.saveAction( oppAction );
        // if third action which is "Set up F2F" of ActionFlow is complete then
        // mark schedule task of
        // that opportunity as CONFIRMED.
        LOGGER.info( "Action order " + oppAction.getAction().getOrder() + " F2F_MEETING_ACTION_ID "
                + F2F_MEETING_ACTION_ID );
        if (Integer.valueOf( oppAction.getAction().getOrder() ) == Integer.valueOf( F2F_MEETING_ACTION_ID )
                && oppAction.isCompleted()) {
            // send confirmation email for SCHEDULE_TOUR
            Map< String, Task > openScheduleTourTasks = agentTaskService.getOpenTasksByType( agentId,
                    oppAction.getOpportunityId(), TaskType.SCHEDULE_TOUR.getType() );
            if (null != openScheduleTourTasks) {
                for ( Entry<String, Task> taskEntry : openScheduleTourTasks.entrySet() ) {
                    Task task = taskEntry.getValue();
                    task.setId(taskEntry.getKey());
                    if (!task.getDeleted() && task.getIsPrimary()
                            && agentTaskService.isEligibleForScheduleTourConfirmationEmail( task )) {
                        tourConfirmationBusiness.sendConfirmationEmailForScheduleTour( agentId, task );
                    }
                }
            }
            agentTaskBusinessService.updateScheduledTasksByOpportunityId( agentId, oppAction.getOpportunityId() );
        }
        auditLogTask.auditActionFlow( agentId, apiUserEmail, actionFlowId, actionId, actionMap );
        return new AgentResponse( "Action id " + actionId, Status.SUCCESS, "Agent action updated successfully" );
    }

    /**
     * Update last update dtm.
     *
     * @param actionMap
     *            the action map
     * @param currentMillis
     *            the current millis
     * @param oppAction
     *            the opp action
     */
    private void updateLastUpdateDtm( final Map< String, Object > actionMap, final long currentMillis,
            final OpportunityAction oppAction ) {
        actionMap.put( "lastVisitedDtm", currentMillis );
        actionMap.put( "lastModifiedDtm", currentMillis );
        oppAction.setLastActionUpdatedOn( new DateTime( currentMillis ) );
    }

    /**
     * Sets the action last visited dtm.
     *
     * @param agentId
     *            the agent id
     * @param actionFlowId
     *            the action flow id
     * @param actionMap
     *            the action map
     * @param actionList
     *            the action list
     * @param currentMillis
     *            the current millis
     * @param apiUserEmail
     *            the api user email
     * @return the map
     */
    private Map< String, Object > setActionLastVisitedDtm( final String agentId, final String actionFlowId,
            Map< String, Object > actionMap, final List< OpportunityAction > actionList, final long currentMillis,
            final String apiUserEmail ) {
        if (actionMap == null) {
            actionMap = new HashMap<>();
        }
        actionMap.put( "lastVisitedDtm", currentMillis );

        if (!actionList.get( 0 ).isDeleted() && actionList.get( 0 ).getStartedOn() == null) {
            final Map< String, Object > actionFlowData = new HashMap<>();

            actionFlowData.put( "startDtm", currentMillis );
            actionGroupService.patchActionGroup( agentId, actionFlowId, apiUserEmail, actionFlowData );

            actionList.forEach( action -> action.setStartedOn( new DateTime( currentMillis ) ) );
            actionGroupService.saveActionGroup( actionList );
        }
        return actionMap;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentBusinessService#
     * sendSoldStagePTSEmailNotifications(boolean,
     * com.owners.gravitas.amqp.AgentSource)
     */
    @Override
    public void sendSoldStagePTSEmailNotifications( final boolean pts, final AgentSource agentSource,
            final OpportunitySource opportunitySource ) {
        final EmailNotification notification = soldStagePTSEmailNotificationBuilder.convertTo( pts, agentSource,
                opportunitySource );
        if (null != notification) {
            LOGGER.debug( "sending sold stage pts email notification to " + agentSource.getEmail() );
            mailService.send( notification );
        }
    }

    /**
     * Onboards agent.
     *
     * @param request
     *            the request
     * @return the base response
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public BaseResponse onboard( final AgentOnboardRequest request ) throws IOException {
        setDBAgentDetails( request, request.getEmail() );
        final User newUser = googleUserBuilder.convertFrom( request );
        userService.createGoogleUser( newUser );
        LOGGER.info( "Agent profile created sucessfully on GOOGLE, email was " + request.getEmail() );
        saveUpdateUserPhoto( request, request.getEmail() );
        agentService.createCRMAgent( crmAgentRequestBuilder.convertTo( request ) );
        LOGGER.info( "Agent profile created sucessfully on salesforce, email was " + request.getEmail() );
        return new BaseResponse( SUCCESS, "Agent (" + request.getEmail() + ") onboarded successfully" );
    }

    /**
     * Send agent email.
     *
     * @param agentEmail
     *            the agent email
     * @param emailRequest
     *            the email request
     * @return the base response
     */
    @Override
    public AgentResponse sendAgentEmail( final String agentEmail, final EmailRequest emailRequest ) {
        LOGGER.debug(
                "Agent " + agentEmail + " is replying buyer request for email: " + emailRequest.getTo().get( 0 ) );
        final Message message = gmailMessageBuilder.convertTo( emailRequest );
        final Gmail gmail = gmailService.getGmailService( agentEmail );
        final String sentId = gmailService.sendEmail( gmail, message );
        LOGGER.info( "Agent has sent email to: " + emailRequest.getTo().get( 0 ) + ", sentId: " + sentId );
        return new AgentResponse( sentId );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.AgentBusinessService#sendAgentEmail(java.
     * lang.String, java.util.List)
     */
    @Override
    public AgentEmailResponse sendAgentEmails( final String agentEmail, final List< EmailRequest > emailRequests ) {
        if (CollectionUtils.isEmpty( emailRequests )) {
            throw new ApplicationException( "At least one email request is required", EMAIL_REQUEST_REQUIRED );
        }
        final Gmail gmail = gmailService.getGmailService( agentEmail );
        final List< Future< AgentEmailResult > > futureList = new ArrayList< Future< AgentEmailResult > >(
                emailRequests.size() );
        for ( final EmailRequest emailRequest : emailRequests ) {
            final Future< AgentEmailResult > future = agentEmailBusinessService.sendAgentEmail( emailRequest, gmail,
                    agentEmail );
            futureList.add( future );
        }
        return processEmailFutures( futureList );
    }

    /**
     * Send on boards email notification.
     *
     * @param agentSource
     *            the agent source
     */
    private void sendOnBoardsEmailNotification( final AgentSource agentSource ) {
        final EmailNotification notification = agentOnboardEmailNotificationBuilder.convertTo( agentSource, null );
        if (null != notification) {
            LOGGER.debug( "sending onboard email notification to " + agentSource.getEmail() );
            mailService.send( notification );
        }
    }

    /**
     * Send escrow email notification.
     *
     * @param agentSource
     *            the agent source
     */
    private void sendEscrowEmailNotification( final AgentSource agentSource ) {
        final EmailNotification notification = escrowEmailNotificationBuilder.convertTo( agentSource, null );
        if (null != notification) {
            LOGGER.debug( "sending escrow email notification to " + agentSource.getEmail() );
            mailService.send( notification );
        }
    }

    /**
     * Send preliminary title reports email notification.
     *
     * @param agentSource
     *            the agent source
     */
    private void sendPreliminaryTitleReportsEmailNotification( final AgentSource agentSource ) {
        final EmailNotification notification = preliminaryTitleReportsEmailNotificationBuilder.convertTo( agentSource,
                null );
        if (null != notification) {
            LOGGER.debug( "sending preliminary title reports email notification to " + agentSource.getEmail() );
            mailService.send( notification );
        }
    }

    /**
     * Process email futures.
     *
     * @param futureList
     *            the future list
     * @return the agent email response
     */
    private AgentEmailResponse processEmailFutures( final List< Future< AgentEmailResult > > futureList ) {
        final AgentEmailResponse agentEmailResponse = new AgentEmailResponse();
        for ( final Future< AgentEmailResult > future : futureList ) {
            try {
                final AgentEmailResult result = future.get( agentEmailJmxConfig.getAgentEmailTimeout(),
                        TimeUnit.SECONDS );
                agentEmailResponse.addResult( result );
            } catch ( final TimeoutException e ) {
                agentEmailResponse.clearResults();
                LOGGER.error( "TimeoutException occured while sending email...", e );
                break;
            } catch ( InterruptedException | ExecutionException e ) {
                LOGGER.error( "Exception occured while sending email...", e );
                throw new ApplicationException( e.getMessage(), e );
            }
        }
        return agentEmailResponse;
    }

    /**
     * Rollback registration.
     *
     * @param agentHolder
     *            the agent holder
     * @param searchMap
     *            the search map
     */
    private void rollbackRegistration( final AgentHolder agentHolder, final Map< String, Search > searchMap ) {
        try {
            agentService.deleteAgent( agentHolder.getAgentId() );
        } catch ( final Exception ex ) {
            LOGGER.error( ex.getLocalizedMessage(), ex );
        }
        for ( final String key : searchMap.keySet() ) {
            searchMap.put( key, null );
        }
        try {
            searchService.saveAll( searchMap );
        } catch ( final Exception ex ) {
            LOGGER.error( ex.getLocalizedMessage(), ex );
        }
    }

    /**
     * Save user.
     *
     * @param request
     *            the request
     * @param fetchedUser
     *            the fetched user
     * @return the com.owners.gravitas.domain.entity. user
     */
    private com.owners.gravitas.domain.entity.User saveUser( final AgentOnboardRequest request,
            final com.owners.gravitas.domain.entity.User fetchedUser ) {
        com.owners.gravitas.domain.entity.User user = userBuilder.convertFrom( request, fetchedUser );
        // saving 1099_AGENT role only
        roleMemberService.save( user.getRoleMember().get( 0 ).getRole() );
        user = userService.save( user );
        LOGGER.debug( "Agent profile saved sucessfully, email was " + request.getEmail() );
        return user;
    }

    /**
     * Save agent details.
     *
     * @param request
     *            the request
     * @param user
     *            the user
     * @param fetchedAgentDetails
     *            the fetched agent details
     */
    private void saveAgentDetails( final AgentOnboardRequest request, final com.owners.gravitas.domain.entity.User user,
            final AgentDetails fetchedAgentDetails ) {
        final AgentDetails agentDetails = agentDetailsBuilder.convertFrom( request, fetchedAgentDetails );
        agentDetails.setUser( user );
        agentDetailsService.save( agentDetails );
        LOGGER.debug( "Agent details saved sucessfully, email was " + request.getEmail() );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.AgentBusinessService#updateAgentsAttributes(
     * java.lang.String, java.lang.String, boolean)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public void updateAgent( final AgentSource agentSource ) throws IOException {
        final String agentEmail = agentSource.getEmail();
        LOGGER.info( "update request received for agent " + agentEmail + ", time is " + LocalDateTime.now() );
        // updating agent to db
        final AgentDetails agentDetails = agentDetailsService.findAgentByEmail( agentEmail );
        if (null != agentDetails) {
            agentDetails.getUser().setStatus( agentSource.getStatus().toLowerCase() );
            userService.save( agentDetails.getUser() );
            updateAgentDetails( agentSource, agentDetails );
        }
        // updating on google
        final AgentOnboardRequest agentOnboardRequest = agentOnboardRequestBuilder.convertTo( agentSource );
        final User fetchedUser = userService.getUser( agentSource.getEmail() );
        final User newUser = googleUserBuilder.convertFrom( agentOnboardRequest, fetchedUser );
        userService.updateGoogleUser( agentSource.getEmail(), newUser );

        // updating firebase
        final Search search = searchService.searchByAgentEmail( agentEmail );
        final Map< String, Object > request = new HashMap<>();
        request.put( "onDuty", agentSource.isAvailable() );
        if (search != null) {
            updateAgentInfo( search.getAgentId(), request, null );
            LOGGER.info( "Agent with email " + agentEmail + " is available on firebase & updated" );
        }
        LOGGER.info( "updated agent " + agentEmail + " successfully" );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.AgentBusinessService#updateAgent(java.lang.
     * String, com.owners.gravitas.dto.request.AgentOnboardRequest)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public BaseResponse updateAgent( final String email, final AgentOnboardRequest request ) throws IOException {
        setDBAgentDetails( request, email );
        final User fetchedUser = userService.getUser( email );
        final User newUser = googleUserBuilder.convertFrom( request, fetchedUser );
        userService.updateGoogleUser( email, newUser );
        LOGGER.info( "Agent profile updated sucessfully, email was " + email );
        saveUpdateUserPhoto( request, email );
        agentService.updateCRMAgent( crmAgentRequestBuilder.convertTo( request ) );
        LOGGER.info( "Agent profile updated sucessfully on salesforce, email was " + email );
        setFirebaseAgentPhone( email, request.getPhone() );
        return new BaseResponse( SUCCESS, "Agent (" + email + ") updated successfully" );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentBusinessService#syncAgentScore()
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public void syncAgentScore() {
        LOGGER.debug( "agent score updated started" );
        agentService.syncAgentScore();
        LOGGER.debug( "agent score updated successfully via backend job" );
    }

    /**
     * Save update user photo.
     *
     * @param request
     *            the request
     * @param email
     *            the email
     */
    private void saveUpdateUserPhoto( final AgentOnboardRequest request, final String email ) {
        if (null != request.getPhotoData()) {
            final UserPhoto newPhoto = new UserPhoto();
            newPhoto.encodePhotoData( request.getPhotoData() );
            userService.updateGooglePhoto( email, newPhoto );
            LOGGER.debug( "Agent photo uploaded sucessfully, email was " + email );
        } else if (request.isDeleteFile()) {
            userService.deleteGooglePhoto( email );
        }
    }

    /**
     * Sets the DB agent details.
     *
     * @param request
     *            the request
     * @param email
     *            the email
     */
    private void setDBAgentDetails( final AgentOnboardRequest request, final String email ) {
        com.owners.gravitas.domain.entity.User user = userService.findByEmail( email );
        user = saveUser( request, user );
        final AgentDetails agentDetails = agentDetailsService.findAgentByEmail( email );
        saveAgentDetails( request, user, agentDetails );
        if (INACTIVE_STATUS.equalsIgnoreCase( request.getStatus() )) {
            user.setEmail( DISABLE_EMAIL_PREFIX + email );
            userService.save( user );
        }
    }

    /**
     * Sets the firebase agent phone.
     *
     * @param email
     *            the email
     * @param phone
     *            the phone
     */
    private void setFirebaseAgentPhone( final String email, final String phone ) {
        final Search search = searchService.searchByAgentEmail( email );
        String agentId = null;
        if (search != null) {
            agentId = search.getAgentId();
            if (agentId != null) {
                final AgentInfo agentInfo = agentInfoService.getAgentInfo( agentId );
                if (agentInfo != null) {
                    agentInfo.setPhone( phone );
                    agentInfoService.patchAgentInfo( agentId, agentInfo );
                    LOGGER.info( "Agent phone info updated sucessfully on firebase, agentId was " + agentId );
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.AgentBusinessService#updateAgent
     * (java.lang.String, java.util.Map)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public BaseResponse updateAgent( final String agentId, final Map< String, Object > request ) {
        LOGGER.debug( "Update details for agent " + agentId );
        if (MapUtils.isNotEmpty( request ) && request.containsKey( ON_DUTY )) {
            final String agentEmail = removeDoubleQuotes( agentService.getAgentEmailById( agentId ) );
            final boolean onDuty = ( Boolean ) request.get( ON_DUTY );
            if (!request.containsKey( OFF_DUTY_END_TIME )) {
                updateAgent( agentId, request, agentEmail, onDuty, null );
            } else {
                final DateTime offDutyStartTime = DateTime.now().plusSeconds( 2 );
                updateAgent( agentId, request, agentEmail, onDuty, offDutyStartTime.getMillis() );
                startAgentOffDutyProcess( request, agentEmail, agentId, offDutyStartTime );
            }
        }
        return new BaseResponse( Status.SUCCESS, "Agent update successful" );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.AgentBusinessService#processAgentOffDuty(
     * java.lang.String)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public void processAgentOffDuty( final String executionId ) {
        final String agentEmail = ( String ) runtimeService.getVariable( executionId, AGENT_EMAIL );
        final DateTime offDutyStartTime = new DateTime(
                runtimeService.getVariable( executionId, OFF_DUTY_START_TIME ) );
        final DateTime offDutyEndTime = new DateTime( runtimeService.getVariable( executionId, OFF_DUTY_END_TIME ) );

        LOGGER.info( "Agent is going on off duty from " + offDutyStartTime + " to " + offDutyEndTime );
        LOGGER.info( "Creating process with execution id " + executionId );
        processBusinessService.createProcess( agentEmail, executionId, AGENT_AVAILABILITY_PROCESS );

        final AgentAvailabilityLog agentAvailabilityLog = buildAgentAvailabilityLog( agentEmail, offDutyStartTime,
                offDutyEndTime );
        LOGGER.info( "Saving new off duty request log for agent " + agentEmail );
        agentAvailabilityLogService.saveLog( agentAvailabilityLog );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.AgentBusinessService#processAgentOnDuty(java
     * .lang.String)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public void processAgentOnDuty( final String executionId ) {
        final String agentEmail = ( String ) runtimeService.getVariable( executionId, AGENT_EMAIL );
        final String agentId = ( String ) runtimeService.getVariable( executionId, AGENT_ID );
        final Map< String, Object > request = ( Map ) runtimeService.getVariable( executionId, REQUEST );
        final DateTime offDutyStartTime = new DateTime(
                runtimeService.getVariable( executionId, OFF_DUTY_START_TIME ) );

        LOGGER.info( "Updating agent " + agentEmail + " to be available from off duty" );
        final AgentDetails agentDetails = agentDetailsService.findAgentByEmail( agentEmail );
        final AgentAvailabilityLog availabilityLog = agentAvailabilityLogService.getLog( agentDetails, TRUE );
        if (availabilityLog != null) {
            request.put( ON_DUTY, TRUE );
            updateAgent( agentId, request, agentEmail, TRUE, offDutyStartTime.getMillis() );
            final NotificationRequest notificationRequest = new NotificationRequest();
            notificationRequest.setEventType( ON_DUTY_AGENT );
            LOGGER.info( "Sending push notification for agent " + agentEmail );
            agentNotificationBusinessService.sendPushNotification( agentId, notificationRequest );
        }
        processBusinessService.deActivateProcess( agentEmail, AGENT_AVAILABILITY_PROCESS, executionId );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.AgentBusinessService#getCRMAgentByEmail(java
     * .lang.String)
     */
    @Override
    public AgentSource getCRMAgentByEmail( final String agentEmail ) {
        return agentSourceResponseBuilder.convertTo( agentService.getAgentDetails( agentEmail ) );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.AgentBusinessService#getCRMAgentById(java.
     * lang.String)
     */
    @Override
    public CRMAgentResponse getCRMAgentById( final String crmAgentId ) {
        return agentService.getCRMAgentById( crmAgentId );
    }

    /**
     * Sync agent details.
     *
     * @param agentSource
     *            the agent source
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public void syncAgentDetails( final AgentSource agentSource ) throws IOException {
        if (agentTopicJmxConfig.isEnableAgentDbSync() && isAgentEligibleToSync( agentSource )) {
            LOGGER.info( "Started sync of agent in database for " + agentSource.getEmail() );
            final AgentOnboardRequest agentOnboardRequest = agentOnboardRequestBuilder.convertTo( agentSource );
            setDBAgentDetails( agentOnboardRequest, agentOnboardRequest.getEmail() );

            final User fetchedUser = userService.getUser( agentSource.getEmail() );
            final User newUser = googleUserBuilder.convertFrom( agentOnboardRequest, fetchedUser );
            userService.updateGoogleUser( agentSource.getEmail(), newUser );
            LOGGER.info( "Agent profile created sucessfully, email was " + agentOnboardRequest.getEmail() );
        }
    }

    /**
     * Creates the agent.
     *
     * @param agentEmail
     *            the agetn email
     * @return the agent info
     */
    private AgentInfo createAgentInfo( final String agentEmail ) {
        final AgentInfo info = new AgentInfo();
        info.setEmail( agentEmail );
        info.setPhone( getAgentPhoneNumber( agentEmail ) );
        info.setLastLoginDtm( new Date().getTime() );
        info.setLastModifiedDtm( new Date().getTime() );
        info.setOnDuty( Boolean.TRUE );
        return info;
    }

    /**
     * Gets the agent's phone number.
     *
     * @param agentEmail
     *            the agent email
     * @return the agents phone number
     */
    private String getAgentPhoneNumber( final String agentEmail ) {
        String phone = StringUtils.EMPTY;
        final List< ArrayMap< String, Object > > phones = userBusinessService.getUserPhones( agentEmail );
        phone = getAgentPhones( phones );
        if (StringUtils.isBlank( phone )) {
            try {
                final Map< String, Object > response = agentService.getCrmAgentDetails( agentEmail );
                phone = ( String ) response.get( CRM_AGENT_PHONE );
            } catch ( final Exception e ) {
                LOGGER.debug( "Unable to retrieve agent's phone number from Salesforce account" + e.getMessage() );
            }
        }
        return phone;
    }

    /**
     * Update agent info.
     *
     * @param agentId
     *            the agent id
     * @param request
     *            the request
     * @param offDutyStartDtm
     *            the off duty start dtm
     */
    private void updateAgentInfo( final String agentId, final Map< String, Object > request,
            final Long offDutyStartDtm ) {
        final AgentInfo agentInfo = agentInfoService.getAgentInfo( agentId );
        agentInfo.setOnDuty( ( Boolean ) request.get( "onDuty" ) );
        if (offDutyStartDtm != null) {
            agentInfo.setOffDutyStartDtm( offDutyStartDtm );
        }
        if (request.get( OFF_DUTY_END_TIME ) != null) {
            agentInfo.setOffDutyEndDtm( ( long ) request.get( OFF_DUTY_END_TIME ) );
        }
        agentInfo.setLastModifiedDtm( new Date().getTime() );
        agentInfoService.patchAgentInfo( agentId, agentInfo );
        LOGGER.debug( "Agent is updated successfully in firebase" );
    }

    /**
     * Update crm agent.
     *
     * @param agentEmail
     *            the agent email
     * @param request
     *            the request
     */
    private void updateCRMAgent( final String agentEmail, final Map< String, Object > request ) {
        try {
            final Map< String, Object > crmRequest = new HashMap<>();
            final Map< String, Object > crmAgentDetails = agentService.getAgentDetails( agentEmail );
            final String crmAgentId = convertObjectToString( crmAgentDetails.get( "Id" ) );
            for ( final Entry< String, Object > entry : request.entrySet() ) {
                if (crmAgentFields.get( entry.getKey() ) != null) {
                    crmRequest.put( crmAgentFields.get( entry.getKey() ), entry.getValue() );
                }
            }
            agentService.patchCRMAgent( crmRequest, crmAgentId );
            LOGGER.debug( "Agent is updated successfully on CRM" );
        } catch ( final ResultNotFoundException e ) {
            LOGGER.debug( "Agent details not found on CRM. Hence we are not updating on CRM " + e.getMessage() );
        }
    }

    /**
     * Update agent details .
     *
     * @param source
     *            the source
     * @param agentDetails
     *            the agent details
     */
    private void updateAgentDetails( final AgentSource source, final AgentDetails agentDetails ) {
        crmAgentBuilder.convertTo( source, agentDetails );
        agentDetailsService.save( agentDetails );
        LOGGER.debug( "Agent is updated successfully in database. " + source.getEmail() );
    }

    /**
     * Gets the agent phones.
     *
     * @param phones
     *            the phones
     * @return the agent phones
     */
    private String getAgentPhones( final List< ArrayMap< String, Object > > phones ) {
        String phone = "";
        if (phones != null) {
            for ( final ArrayMap< String, Object > phoneMap : phones ) {
                if ("work".equals( phoneMap.get( "type" ) )) {
                    final Object fieldValue = phoneMap.get( "value" );
                    if (fieldValue != null && !"null".equals( fieldValue )) {
                        phone = String.valueOf( fieldValue );
                        break;
                    }
                }
            }
        }
        return phone;
    }

    /**
     * Checks if is agent eligible to sync.
     *
     * @param agentSource
     *            the agent source
     * @return true, if is agent eligible to sync
     */
    private boolean isAgentEligibleToSync( final AgentSource agentSource ) {
        boolean isEligible = false;
        if (agentSource.isFieldAgent() || FIELD_AGENT.equalsIgnoreCase( agentSource.getAgentType() )) {
            final String recordTypeId = recordTypeService.getRecordTypeIdByName( OWNERS_COM.getType(),
                    AGENT.getName() );
            if (agentSource.getRecordTypeId().equals( recordTypeId )) {
                final com.owners.gravitas.domain.entity.User user = userService.findByEmail( agentSource.getEmail() );
                isEligible = ( user == null );
            }
        }
        return isEligible;
    }

    /**
     * Start agent off duty process.
     *
     * @param request
     *            the request
     * @param agentEmail
     *            the agent email
     * @param agentId
     *            the agent id
     * @param offDutyStartTime
     *            the off duty start time
     */
    private void startAgentOffDutyProcess( final Map< String, Object > request, final String agentEmail,
            final String agentId, final DateTime offDutyStartTime ) {
        final List< Process > processes = processBusinessService.getAllProcess( agentEmail, AGENT_AVAILABILITY_PROCESS,
                ACTIVE.getStatus() );
        if (CollectionUtils.isNotEmpty( processes )) {
            for ( final Process process : processes ) {
                final String processInstanceId = activitiService.findProcessInstanceId( process.getExecutionId() );
                runtimeService.suspendProcessInstanceById( processInstanceId );
                processBusinessService.deActivateProcess( agentEmail, AGENT_AVAILABILITY_PROCESS,
                        process.getExecutionId() );
            }
        }

        final Map< String, Object > variableMap = new HashMap<>();
        variableMap.put( OFF_DUTY_START_TIME, offDutyStartTime.toString() );
        variableMap.put( OFF_DUTY_END_TIME, new DateTime( ( long ) request.get( OFF_DUTY_END_TIME ) ).toString() );
        variableMap.put( AGENT_EMAIL, agentEmail );
        variableMap.put( AGENT_ID, agentId );
        variableMap.put( REQUEST, request );
        LOGGER.debug( "Starting agent off duty process for agent " + agentEmail );
        runtimeService.startProcessInstanceByKey( "agentAvailabilityProcess", variableMap );
    }

    /**
     * Update agent on FB, SF and DB.
     *
     * @param agentId
     *            the agent id
     * @param request
     *            the request
     * @param agentEmail
     *            the agent email
     * @param onDuty
     *            the on duty
     * @param offDutyStartDtm
     *            the off duty start dtm
     */
    private void updateAgent( final String agentId, final Map< String, Object > request, final String agentEmail,
            final boolean onDuty, final Long offDutyStartDtm ) {
        final AgentDetails agentDetails = agentDetailsService.findAgentByEmail( agentEmail );
        if (agentDetails != null) {
            agentDetails.setAvailability( onDuty );
            agentDetailsService.save( agentDetails );
            LOGGER.debug( "Agent is updated successfully in database." + agentEmail );
            closeInProcessOffDutyRequests( agentDetails );
        }
        updateAgentInfo( agentId, request, offDutyStartDtm );
        updateCRMAgent( agentEmail, request );
    }

    /**
     * Close in process off duty requests.
     *
     * @param agentDetails
     *            the agent details
     */
    private void closeInProcessOffDutyRequests( final AgentDetails agentDetails ) {
        final AgentAvailabilityLog availabilityLog = agentAvailabilityLogService.getLog( agentDetails, TRUE );
        if (availabilityLog != null) {
            availabilityLog.setInProcess( FALSE );
            LOGGER.debug( "Closing in process off duty requests for agent " + agentDetails.getUser().getEmail() );
            agentAvailabilityLogService.saveLog( availabilityLog );
        }
    }

    /**
     * Builds the agent availability log.
     *
     * @param agentEmail
     *            the agent email
     * @param inProcess
     *            the in process
     * @param offDutyStartTime
     *            the off duty start time
     * @param offDutyEndTime
     *            the off duty end time
     * @return the agent availability log
     */
    private AgentAvailabilityLog buildAgentAvailabilityLog( final String agentEmail, final DateTime offDutyStartTime,
            final DateTime offDutyEndTime ) {
        final AgentDetails agentDetails = agentDetailsService.findAgentByEmail( agentEmail );
        final AgentAvailabilityLog agentAvailabilityLog = new AgentAvailabilityLog();
        agentAvailabilityLog.setAgentDetails( agentDetails );
        agentAvailabilityLog.setInProcess( TRUE );
        agentAvailabilityLog.setOffDutyStartTime( offDutyStartTime );
        agentAvailabilityLog.setOffDutyEndTime( offDutyEndTime );
        return agentAvailabilityLog;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.AgentBusinessService#updateFirstContactTime(
     * com.owners.gravitas.dto.request.ActionRequest)
     */
    @Override
    public void updateFirstContactTime( final String agentId, final ActionRequest actionRequest ) {
        final Opportunity fbOpportunity = agentOpportunityService.getOpportunityById( agentId,
                actionRequest.getActionEntityId() );
        if (isUpdateRequired( fbOpportunity )) {
            LOGGER.info( "we need to update the first contact time for " + actionRequest.getActionEntityId()
                    + " stage is " + fbOpportunity.getStage() );
            final com.owners.gravitas.domain.Contact contact = agentContactService.getContactById( agentId,
                    String.valueOf( fbOpportunity.getContacts().toArray()[0] ) );
            agentOpportunityService.patchOpportunityForCTA( agentId, actionRequest.getActionEntityId(), fbOpportunity,
                    contact.getEmails().get( 0 ) );

            LOGGER.info( "contact updated successfully!!" );
        }
    }

    /**
     * Checks if is update required.
     *
     * @param fbOpportunity
     *            the fbOpportunity
     * @return true, if is update required
     */
    private boolean isUpdateRequired( final Opportunity fbOpportunity ) {
        // not checked for new and claimed stages of seller as enum values are
        // matching for seller and buyer, do check other stages if values are
        // different
        return null != fbOpportunity
                && ( fbOpportunity.getStage().equalsIgnoreCase( BuyerStage.NEW.getStage() )
                        || fbOpportunity.getStage().equalsIgnoreCase( BuyerStage.CLAIMED.getStage() ) )
                && null == fbOpportunity.getFirstContactDtm();
    }

    /**
     * Adds suggested property.
     *
     * @param agentId
     *            the agent id
     * @param agentSuggestedPropertyRequest
     *            the suggested property details
     * @return the AgentResponse response
     */
    @Override
    public AgentResponse createAgentSuggestedProperty( final String agentId,
            final AgentSuggestedPropertyRequest agentSuggestedPropertyRequest ) {

        LOGGER.info( "Create suggested property request received from agent " + agentId + " is {}",
                JsonUtil.toJson( agentSuggestedPropertyRequest ) );

        if (null == agentSuggestedPropertyRequest.getDateTime()) {
            agentSuggestedPropertyRequest.setDateTime( new DateTime().getMillis() );
        }

        final PostResponse postResponse = agentSuggestedPropertyService
                .saveAgentSuggestedProperty( agentSuggestedPropertyRequest, agentId );
        return new AgentResponse( postResponse.getName() );
    }

    @Override
    public BaseResponse saveAgentSignature( final String agentId, final Object signature ) {
        final AgentInfo agentInfo = agentInfoService.getAgentInfo( agentId );
        if (null != agentInfo && null != signature) {
            final AgentPreference agentPreference = new AgentPreference();
            agentPreference.setSignature( signature );
            agentPreference.setLastModifiedDtm( new Date().getTime() );
            agentPreferenceService.saveAgentPreferences( agentId, agentPreference );
            return new BaseResponse( SUCCESS, "Agent signature updated successfully" );
        }
        return new BaseResponse( FAILURE, "No signature found with agentId: " + agentId );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentBusinessService#
     * saveAgentPreferencesData(java.lang.String, java.lang.String,
     * java.lang.String, java.lang.Object)
     */
    @Override
    public BaseResponse saveAgentPreferencesData( final String agentId, final String path, final Boolean agentSpecific,
            final Map< String, Object > preferenceData ) {

        if (StringUtils.isEmpty( path ) || agentSpecific == null) {
            return new BaseResponse( FAILURE, "Path/agentSpecific parameters cannot be null or Empty" );
        }

        final AgentInfo agentInfo = agentInfoService.getAgentInfo( agentId );
        final String fbPreferencePath = agentPreferenceService.getFbPreferencePath( agentId, path, agentSpecific );
        if (null != agentInfo) {
            preferenceData.put( "lastModifiedDtm", new Date().getTime() );
            agentPreferenceService.saveAgentPreferencesData( fbPreferencePath, preferenceData );
            return new BaseResponse( SUCCESS, "Agent Preferences created/updated successfully" );
        }
        return new BaseResponse( FAILURE, "Invalid AgentId, Agent Not Found with agentId: " + agentId );
    }

    @Override
    public AgentResponse addDataNode( final String agentId, final String opportunityId,
            final OpportunityDataRequest opportunityDataRequest ) {

        if (StringUtils.isEmpty( opportunityDataRequest.getKey() )) {
            throw new ApplicationException( "node key is empty or null", ErrorCode.NULL_OR_EMPTY_DATA_NODE_KEY );
        }
        LOGGER.info( "Creating/Updating data node for agent " + agentId + " with dataKey: "
                + opportunityDataRequest.getKey() );
        if (!validKeysForOppsDataNode.contains( opportunityDataRequest.getKey() )) {
            LOGGER.error( "Invalid key for opportunity data node : " + opportunityDataRequest.getKey() );
            throw new ApplicationException( "Invalid key for opportunity data node", ErrorCode.INVALID_DATA_NODE_KEY );
        }

        final OpportunityDataNode opportunityDataNode = opportunityDataNodeService.getDataNode( agentId, opportunityId,
                opportunityDataRequest.getKey() );
        AgentResponse response = null;
        final OpportunityDataNode oppsDataNode = new OpportunityDataNode();
        if (opportunityDataNode != null) {
            oppsDataNode.setCreatedDtm( opportunityDataNode.getCreatedDtm() );
            LOGGER.info( "Updating value for nodekey : " + opportunityDataRequest.getKey() + "from oldValue :"
                    + opportunityDataNode.getValue() + " to new Value: " + opportunityDataRequest.getValue() );
        } else {
            oppsDataNode.setCreatedDtm( new Date().getTime() );
        }
        oppsDataNode.setValue( opportunityDataRequest.getValue() );
        oppsDataNode.setLastModifiedDtm( new Date().getTime() );
        final PostResponse postResponse = opportunityDataNodeService.addDataNode( oppsDataNode, agentId, opportunityId,
                opportunityDataRequest.getKey() );
        response = new AgentResponse( postResponse.getName() );
        return response;
    }

    @Override
    public OpportunityDataNode getDataNode( final String agentId, final String opportunityId,
            final String dataNodeKey ) {
        if (StringUtils.isEmpty( agentId ) && StringUtils.isEmpty( opportunityId )
                && StringUtils.isEmpty( dataNodeKey )) {
            throw new ApplicationException( "Invalid key for opportunity data node", ErrorCode.INVALID_REQUEST );
        }
        return opportunityDataNodeService.getDataNode( agentId, opportunityId, dataNodeKey );
    }
}
