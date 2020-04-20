package com.owners.gravitas.business.impl;

import static com.owners.gravitas.constants.CRMConstants.CREATE_SUCCESSFUL;
import static com.owners.gravitas.constants.CRMConstants.LEAD_FARMING_SYSTEM_ACTIONS;
import static com.owners.gravitas.constants.CRMConstants.MESSAGE;
import static com.owners.gravitas.constants.CRMConstants.OPERATION_FAILED;
import static com.owners.gravitas.constants.CRMConstants.SAVE_SEARCH_DUPLICATE;
import static com.owners.gravitas.constants.CRMConstants.SAVE_SEARCH_FAILED;
import static com.owners.gravitas.constants.CRMConstants.STATUS;
import static com.owners.gravitas.constants.CRMConstants.UPDATE_SUCCESSFUL;
import static com.owners.gravitas.constants.Constants.COMMA;
import static com.owners.gravitas.constants.Constants.CREATED;
import static com.owners.gravitas.constants.Constants.EXISTS;
import static com.owners.gravitas.constants.Constants.FAILED;
import static com.owners.gravitas.constants.Constants.LEAD;
import static com.owners.gravitas.constants.Constants.SAVE_SEARCH;
import static com.owners.gravitas.constants.Constants.SAVE_SEARCH_CREATE_MSG;
import static com.owners.gravitas.constants.Constants.SAVE_SEARCH_EXISTS_MSG;
import static com.owners.gravitas.constants.Constants.SAVE_SEARCH_FAILED_MSG;
import static com.owners.gravitas.dto.response.BaseResponse.Status.DUPLICATE;
import static com.owners.gravitas.dto.response.BaseResponse.Status.SUCCESS;
import static com.owners.gravitas.enums.ProspectAttributeType.FARMING_SYSTEM_ACTIONS;
import static com.owners.gravitas.enums.ProspectAttributeType.NOTES;
import static com.owners.gravitas.enums.StatusType.deleted;
import static com.owners.gravitas.enums.UserStatusType.ACTIVE;
import static com.owners.gravitas.enums.UserStatusType.HOLD;
import static com.owners.gravitas.enums.UserStatusType.INACTIVE;
import static com.owners.gravitas.enums.UserStatusType.ONBOARDING;
import static com.owners.gravitas.util.DateUtil.DEFAULT_CRM_DATE_PATTERN;
import static com.owners.gravitas.util.DateUtil.toSqlDate;
import static java.lang.Boolean.FALSE;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.api.client.util.ArrayMap;
import com.google.api.client.util.Base64;
import com.google.api.services.admin.directory.model.User;
import com.google.api.services.calendar.model.Event;
import com.hubzu.notification.dto.client.email.data.EmailDataFilter;
import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.BuyerFarmingBusinessService;
import com.owners.gravitas.business.UserBusinessService;
import com.owners.gravitas.business.builder.GoogleCalendarEventListBuilder;
import com.owners.gravitas.business.builder.response.AgentDetailsResponseBuilder;
import com.owners.gravitas.business.builder.response.AgentsResponseBuilder;
import com.owners.gravitas.business.builder.response.RoleDetailsResponseBuilder;
import com.owners.gravitas.business.builder.response.UserDetailsResponseBuilder;
import com.owners.gravitas.config.AgentOpportunityBusinessConfig;
import com.owners.gravitas.constants.BuyerFarmingConstants;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.domain.entity.AgentTask;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.ContactActivity;
import com.owners.gravitas.domain.entity.ObjectAttributeConfig;
import com.owners.gravitas.domain.entity.ObjectType;
import com.owners.gravitas.domain.entity.Role;
import com.owners.gravitas.domain.entity.UserLoginLog;
import com.owners.gravitas.dto.Agent;
import com.owners.gravitas.dto.AlertDetail;
import com.owners.gravitas.dto.CalendarEvent;
import com.owners.gravitas.dto.CrmNote;
import com.owners.gravitas.dto.EventDetail;
import com.owners.gravitas.dto.NotificationDetail;
import com.owners.gravitas.dto.NotificationResponseObject;
import com.owners.gravitas.dto.UserDetails;
import com.owners.gravitas.dto.crm.request.UserLoginLogRequest;
import com.owners.gravitas.dto.request.SavedSearchChimeraRequest;
import com.owners.gravitas.dto.request.UserNotificationConfigDetails;
import com.owners.gravitas.dto.response.AgentDetailsResponse;
import com.owners.gravitas.dto.response.AgentsDetailsResponse;
import com.owners.gravitas.dto.response.AgentsResponse;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.CalendarEventListResponse;
import com.owners.gravitas.dto.response.CheckScheduleMeetingValidationResponse;
import com.owners.gravitas.dto.response.SaveSearchResponse;
import com.owners.gravitas.dto.response.ScheduleMeetingDetails;
import com.owners.gravitas.dto.response.ScheduleMeetingResponse;
import com.owners.gravitas.dto.response.UserActivityResponse;
import com.owners.gravitas.dto.response.UserDetailsResponse;
import com.owners.gravitas.dto.response.UserNotificationConfigResponse;
import com.owners.gravitas.enums.UserStatusType;
import com.owners.gravitas.service.AgentDetailsService;
import com.owners.gravitas.service.AgentTaskService;
import com.owners.gravitas.service.BuyerService;
import com.owners.gravitas.service.CalendarService;
import com.owners.gravitas.service.ContactActivityService;
import com.owners.gravitas.service.ContactEntityService;
import com.owners.gravitas.service.LeadService;
import com.owners.gravitas.service.MailDetailStatusService;
import com.owners.gravitas.service.NoteService;
import com.owners.gravitas.service.ObjectAttributeConfigService;
import com.owners.gravitas.service.ObjectTypeService;
import com.owners.gravitas.service.RoleMemberService;
import com.owners.gravitas.service.UserService;
import com.owners.gravitas.service.builder.UserActivityResponseBuilder;
import com.owners.gravitas.util.DateUtil;
import com.owners.gravitas.util.JsonUtil;
import com.owners.gravitas.util.TimeZoneUtil;

/**
 * The Class UserBusinessServiceImpl.
 *
 * @author harshads
 */
@Service
@Transactional( readOnly = true )
@ManagedResource( objectName = "com.owners.gravitas:name=BestAgentOpportunityAssign" )
public class UserBusinessServiceImpl implements UserBusinessService {

    /** The Constant DEFAULT_FILTER. */
    private static final String DEFAULT_FILTER = "default";

    /** The Constant FIELD_AGENT. */
    private static final String FIELD_AGENT = "1099_AGENT";

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( UserBusinessServiceImpl.class );

    @Value( "${agent.calendar.events.limit:30}" )
    private int agentCalendarEventsLimit;

    /** The agent onboarding service. */
    @Autowired
    private UserService userService;

    /** The role member service. */
    @Autowired
    private RoleMemberService roleMemberService;

    /** The agent details builder. */
    @Autowired
    private AgentDetailsResponseBuilder agentDetailsResponseBuilder;

    /** The role details response builder. */
    @Autowired
    private RoleDetailsResponseBuilder roleDetailsResponseBuilder;

    /** The user details response builder. */
    @Autowired
    private UserDetailsResponseBuilder userDetailsResponseBuilder;

    /** The agent details service. */
    @Autowired
    private AgentDetailsService agentDetailsService;

    /** The agents response builder. */
    @Autowired
    private AgentsResponseBuilder agentsResponseBuilder;

    /** The contact service V 1. */
    @Autowired
    private ContactEntityService contactServiceV1;

    /** The contact activity service. */
    @Autowired
    private ContactActivityService contactActivityService;

    /** The mail service. */
    @Autowired
    private MailDetailStatusService mailDetailStatusService;

    @Autowired
    private BuyerService buyerService;

    @Autowired
    ObjectAttributeConfigService objectAttributeConfigService;

    /** The calendar service. */
    @Autowired
    private CalendarService calendarService;

    @Autowired
    private GoogleCalendarEventListBuilder googleCalendarEventListBuilder;

    /** The object type service. */
    @Autowired
    private ObjectTypeService objectTypeService;

    /** The buyer registration business service. */
    @Autowired
    private BuyerFarmingBusinessService buyerFarmingBusinessService;

    /** The lead service. */
    @Autowired
    private LeadService leadService;

    /** The note service. */
    @Autowired
    private NoteService noteService;

    @Autowired
    private AgentTaskService agentTaskService;

    /** The AgentOpportunityBusinessConfig. */
    @Autowired
    private AgentOpportunityBusinessConfig agentOpportunityBusinessConfig;
    
    @Autowired
    private TimeZoneUtil timeZoneUtil;
    
    @Value( "${agent.calendar.default.timezone:EST}" )
    private String defaultTimeZoneForAgents;
    
    /**
     * Gets the user details.
     *
     * @param email
     *            the email
     * @return the user details
     */
    @Override
    public BaseResponse getUserDetails( final String email ) {
        final UserDetails userDetails = userService.getUserDetails( email );
        LOGGER.info( "agent is valid " + email );
        return agentDetailsResponseBuilder.convertTo( userDetails );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.UserBusinessService#getUserPhones(java.lang.
     * String)
     */
    @Override
    public List< ArrayMap< String, Object > > getUserPhones( final String email ) {
        final User user = userService.getUser( email );
        LOGGER.info( "agent is valid " + email );
        return ( List< ArrayMap< String, Object > > ) user.getPhones();
    }

    /**
     * Checks if is google user exists.
     *
     * @param email
     *            the email
     * @return the boolean
     */
    @Override
    public Boolean isGoogleUserExists( final String email ) {
        return userService.isGoogleUserExists( email );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.UserBusinessService#getUserRolesByEmail(com.
     * owners.gravitas.dto.ApiUser)
     */
    @Override
    public BaseResponse getUserRolesByEmail( final String email ) {
        final Set< String > firebaseRoles = userService.getRoles( null, email );
        final List< Role > dbRoles = roleMemberService.getRolesByUserEmailId( email );
        return roleDetailsResponseBuilder.convertTo( getUserRoles( firebaseRoles, dbRoles ) );
    }

    /**
     * Reset password.
     *
     * @param email
     *            the email
     * @return the base response
     */
    @Override
    public BaseResponse resetPassword( final String email ) {
        userService.resetPassword( email );
        return new BaseResponse( Status.SUCCESS, "Password reset successful" );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.UserBusinessService#getAgentDetails(java.
     * lang.String)
     */
    @Override
    public BaseResponse getAgentDetails( final String listingId ) {
        LOGGER.info( "getting agent details for listingId " + listingId );
        final String agentEmail = agentDetailsService.findAgentEmailByListingId( listingId );
        return getUserDetails( agentEmail );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.UserBusinessService#saveUserLoginLog(com.
     * owners.gravitas.dto.crm.request.UserLoginLogRequest)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public BaseResponse saveUserLoginLog( final UserLoginLogRequest userLoginLogRequest ) {
        final UserLoginLog userLoginLog = new UserLoginLog();
        BeanUtils.copyProperties( userLoginLogRequest, userLoginLog );
        userLoginLog.setCreatedDate( new DateTime() );
        userService.saveUserLoginLog( userLoginLog );
        LOGGER.info( "User details for ipaddress:" + userLoginLog.getIpAddress() + " is logged successfully" );
        return new BaseResponse( Status.SUCCESS, "User login details saved successfully" );
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
    @Override
    public BaseResponse getAgentsByManager( final String email, final String filter ) {
        final List< com.owners.gravitas.domain.entity.User > users = new ArrayList<>();
        List< AgentDetails > agents = userService.getUsersByManagingBroker( email );
        if (StringUtils.equalsIgnoreCase( filter, DEFAULT_FILTER )) {
            agents = filterAgentsOnStatuses( agents, null );
        }
        for ( final AgentDetails agentDetails : agents ) {
            users.add( agentDetails.getUser() );
        }
        final List< User > googleUsers = userService.getUsersByEmails( getUserEmails( users ) );
        return buildAgentsResponse( agentsResponseBuilder.convertTo( googleUsers ), agents );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.UserBusinessService#getUsersByFilters(java.
     * lang.String, java.lang.String)
     */
    @Override
    public BaseResponse getUsersByFilters( final String role, final String filter ) {
        BaseResponse baseResponse = new BaseResponse();
        List< com.owners.gravitas.domain.entity.User > users = new ArrayList<>();
        List< User > googleUsers = new ArrayList< User >();
        if (StringUtils.isNotBlank( role )) {
            baseResponse = getUsersForRole( role, filter );
        } else {
            LOGGER.info( "Fetching all user details from database " );
            users = userService.findAll();
            googleUsers = userService.getUsersByEmails( getUserEmails( users ) );
            baseResponse = buildUserDetailsResponse( userDetailsResponseBuilder.convertTo( googleUsers ), users );
        }
        return baseResponse;
    }

    /**
     * Gets the users for role.
     *
     * @param role
     *            the role
     * @param filter
     *            the filter
     * @return the users for role
     */
    private BaseResponse getUsersForRole( final String role, final String filter ) {
        BaseResponse baseResponse = new BaseResponse();
        final List< String > statusFilter = getFilteredStatuses( role, filter );
        final List< com.owners.gravitas.domain.entity.User > users = userService.getUsersByRole( role, statusFilter );
        LOGGER.info( "Total users found # " + users.size() );
        final List< User > googleUsers = userService.getUsersByEmails( getUserEmails( users ) );
        LOGGER.info( "Total google users # " + googleUsers.size() );
        // if role is 1099_AGENT
        if (role.equalsIgnoreCase( FIELD_AGENT ) && !users.isEmpty()) {
            LOGGER.info( "Fetching agent details and agent score of 1099_AGENTS " );
            final List< AgentDetails > agentDetails = agentDetailsService.getAgents( users );
            LOGGER.info( "Total field agent from db # " + agentDetails.size() );
            baseResponse = buildAgentsResponse( agentsResponseBuilder.convertTo( googleUsers ), agentDetails );
        }
        // if role is other than 1099_AGENT
        else {
            LOGGER.info( "Fetching user details from database " );
            baseResponse = buildUserDetailsResponse( userDetailsResponseBuilder.convertTo( googleUsers ), users );
        }
        return baseResponse;
    }

    /**
     * Gets the filtered statuses.
     *
     * @param role
     *            the role
     * @param filter
     *            the filter
     * @return the filtered statuses
     */
    private List< String > getFilteredStatuses( final String role, final String filter ) {
        final List< String > statuses = new ArrayList<>();
        if (StringUtils.equalsIgnoreCase( filter, DEFAULT_FILTER )) {
            statuses.add( ACTIVE.toString() );
            statuses.add( HOLD.toString() );
        } else if (filter != null) {
            addToStatusListIfNotNull( statuses, filter );
        }
        return statuses;
    }

    /**
     * Adds the to status list if not null.
     *
     * @param statuses
     *            the statuses
     * @param filter
     *            the filter
     */
    private void addToStatusListIfNotNull( final List< String > statuses, final String filter ) {
        for ( final String status : filter.split( COMMA ) ) {
            statuses.add( status );
        }
    }

    /**
     * Filter agents on statuses.
     *
     * @param agents
     *            the agents
     * @param statuses
     *            the statuses
     * @return the list
     */

    private List< AgentDetails > filterAgentsOnStatuses( final List< AgentDetails > agents,
            final List< UserStatusType > statuses ) {
        List< UserStatusType > filters = new ArrayList<>();
        filters.add( INACTIVE );
        filters.add( ONBOARDING );
        if (CollectionUtils.isNotEmpty( statuses )) {
            filters = statuses;
        }
        final Iterator< AgentDetails > agentIterator = agents.iterator();
        while ( agentIterator.hasNext() ) {
            if (filters.contains( UserStatusType.getStatusType( agentIterator.next().getUser().getStatus() ) )) {
                agentIterator.remove();
            }
        }
        return agents;
    }

    /**
     * Builds the agent details response.
     *
     * @param agentsResponse
     *            the agents response
     * @param dbAgents
     *            the db agents
     * @return the user details response
     */
    private AgentsResponse buildAgentsResponse( final AgentsResponse agentsResponse, final List< AgentDetails > dbAgents ) {
        for ( final AgentDetails dbAgentDetails : dbAgents ) {
            for ( final Agent agent : agentsResponse.getAgents() ) {
                if (agent.getEmail().equalsIgnoreCase( dbAgentDetails.getUser().getEmail() )) {
                    agent.setStatus( dbAgentDetails.getUser().getStatus() );
                    agent.setScore( dbAgentDetails.getScore() );
                    break;
                }
            }
        }
        Collections.sort( agentsResponse.getAgents() );
        return agentsResponse;
    }

    /**
     * Gets the user emails.
     *
     * @param users
     *            the users
     * @return the user emails
     */
    private List< String > getUserEmails( final List< com.owners.gravitas.domain.entity.User > users ) {
        final List< String > emails = new ArrayList<>();
        for ( final com.owners.gravitas.domain.entity.User user : users ) {
            emails.add( user.getEmail() );
        }
        return emails;
    }

    /**
     * Gets the user roles.
     *
     * @param firebaseRoles
     *            the firebase roles
     * @param dbRoles
     *            the db roles
     * @return the user roles
     */
    private List< Role > getUserRoles( final Set< String > firebaseRoles, final List< Role > dbRoles ) {
        Role role = null;
        labelOuter: for ( final String roles : firebaseRoles ) {
            for ( final Role dbRole : dbRoles ) {
                if (dbRole.getName().equals( roles )) {
                    continue labelOuter;
                }
            }
            role = new Role();
            role.setName( roles );
            dbRoles.add( role );
        }
        return dbRoles;
    }

    /**
     * Sets the user details response.
     *
     * @param userDetailsResponse
     *            the user details response
     * @param dbUsers
     *            the db users
     * @return the user details response
     */
    private UserDetailsResponse buildUserDetailsResponse( final UserDetailsResponse userDetailsResponse,
            final List< com.owners.gravitas.domain.entity.User > dbUsers ) {
        for ( final com.owners.gravitas.domain.entity.User dbUser : dbUsers ) {
            for ( final com.owners.gravitas.dto.User googleUser : userDetailsResponse.getUsers() ) {
                if (googleUser.getEmail().equalsIgnoreCase( dbUser.getEmail() )) {
                    googleUser.setStatus( dbUser.getStatus() );
                    break;
                }
            }
        }
        Collections.sort( userDetailsResponse.getUsers() );
        return userDetailsResponse;
    }

    /**
     * Gets the agent details.
     *
     * @param userId
     *            the owners Id of the client
     * @param agentOpportunityType
     *            the opportunity Type
     * @return the agents details
     */
    @Override
    public BaseResponse getAgentsDetails( final String userId, final String agentOpportunityType ) {
        LOGGER.info( "getAgentsDetails userId : {}, agentOpportunityType : {}", userId, agentOpportunityType );
        final List< UserDetails > users = userService.getAgentsDetails( userId, agentOpportunityType );
        final List< Agent > agentsDetails = new ArrayList< Agent >();
        users.stream().filter( user -> user != null )
                .forEach( user -> agentsDetails.add( agentDetailsResponseBuilder.convertTo( user ).getDetails() ) );
        final AgentsDetailsResponse agentsDetailsResponse = new AgentsDetailsResponse();
        agentsDetailsResponse.setAgentsDetails( agentsDetails );
        if (agentsDetails.isEmpty()) {
            agentsDetailsResponse.setMessage( "No Agent available for this client" );
        }
        LOGGER.info( "getAgentsDetails userId : {}, agentOpportunityType : {}, response : {}", userId,
                agentOpportunityType, JsonUtil.toJson( agentsDetailsResponse ) );
        return agentsDetailsResponse;
    }

    /**
     * Gets the user activity details.
     *
     * @param input
     *            the input
     * @return the user activity details
     */
    @Override
    public BaseResponse getUserActivityDetails( final String input ) {
        LOGGER.info( "getUserActivityDetails input : {}", input );
        final UserActivityResponseBuilder userActivityResponseBuilder = new UserActivityResponseBuilder();
        List< ContactActivity > contactActivity = null;
        NotificationResponseObject notificationResponseObject = null;
        ObjectAttributeConfig config = null;
        Contact contact = null;
        boolean isInputEmail = false;
        if (input.contains( "@" )) {
            isInputEmail = true;
        }
        if (isInputEmail) {
            contact = contactServiceV1.getContactByEmail( input );
            if (contact != null) {
                contactActivity = contactActivityService.getContactActivityByUserId( contact.getOwnersComId() );
            }
        } else {
            contact = contactServiceV1.getContactByOwnersComId( input );
            contactActivity = contactActivityService.getContactActivityByUserId( input );
        }

        if (!CollectionUtils.isEmpty( contactActivity )) {
            notificationResponseObject = mailDetailStatusService
                    .getNotificationFeedback( getEmailDateFilterObject( contactActivity ) );
        }
        if (contact != null) {
            config = objectAttributeConfigService.getObjectAttributeConfig( NOTES.getKey(), contact.getObjectType() );
        }

        UserActivityResponse userActivityResponse = userActivityResponseBuilder
                .from( contact, contactActivity, notificationResponseObject, config ).build();
        if (null != userActivityResponse) {
            upadateSaveSearchStatus( userActivityResponse, contact.getId() );
            userActivityResponseBuilder.setAutoRegistrationDetails( userActivityResponse, contact, config );
            if (null == userActivityResponse.getUserId()) {
                userActivityResponse.setUserId( contact != null ? contact.getOwnersComId() : null );
            }
        } else if (null != contact) {
            LOGGER.info( "getUserActivityDetails response::" + userActivityResponse );
            userActivityResponse = new UserActivityResponse();
            userActivityResponse.setUserId( contact.getOwnersComId() );
            userActivityResponse.setFirstName( contact.getFirstName() );
            userActivityResponse.setLastName( contact.getLastName() );
            userActivityResponse.setEmail( contact.getEmail() );
            userActivityResponse.setPhone( contact.getPhone() );
            if(contact.getAssignInsideSales()!=null) {
                userActivityResponse.setLeadClaimed( true );
            }
            upadateSaveSearchStatus( userActivityResponse, contact.getId() );
        }
        LOGGER.info( "getUserActivityDetails input : {}, response : {}", input,
                JsonUtil.toJson( userActivityResponse ) );
        return userActivityResponse;
    }

    EmailDataFilter getEmailDateFilterObject( final List< ContactActivity > contactActivityList ) {

        final EmailDataFilter emailDataFilter = new EmailDataFilter();
        emailDataFilter.setPageNumber( 0 );
        emailDataFilter.setPageSize( 50 );
        final List< String > requestIdList = new ArrayList< String >( 0 );
        for ( final ContactActivity contactActivity : contactActivityList ) {
            requestIdList.add( contactActivity.getNotificationId() );
        }
        emailDataFilter.setRequestIdList( requestIdList );

        return emailDataFilter;
    }

    private void upadateSaveSearchStatus( final UserActivityResponse userActivityResponse, final String contactId ) {
        final List< Object[] > contactJsonAttributes = contactServiceV1.findSaveSearchStatus( contactId );
        AlertDetail alertDetail = null;

        for ( final Object[] contactJsonAttribute : contactJsonAttributes ) {
            LOGGER.info( "saveSearchStatus-> {} :: {} " , contactJsonAttribute[0] , contactJsonAttribute[1] );
            alertDetail = new AlertDetail();

            final String eventText = ( String ) contactJsonAttribute[0];
            if (eventText.contains( SAVE_SEARCH_CREATE_MSG ) || eventText.contains( SAVE_SEARCH_FAILED_MSG )
                    || eventText.contains( SAVE_SEARCH_EXISTS_MSG )) {//Extra check added for bug OWNCORE-9115
                alertDetail.setEventType( SAVE_SEARCH );
                if (eventText.contains( SAVE_SEARCH_CREATE_MSG )) {
                    alertDetail.setEventText( CREATED );
                } else if (eventText.contains( SAVE_SEARCH_FAILED_MSG )) {
                    alertDetail.setEventText( FAILED );
                } else if (eventText.contains( SAVE_SEARCH_EXISTS_MSG )) {
                    alertDetail.setEventText( EXISTS );
                }
                alertDetail.setCreatedOn( new DateTime( contactJsonAttribute[1] ).getMillis() );
                alertDetail.setEventDetails( new ArrayList< EventDetail >() );
                alertDetail.setNotificationDetail( new NotificationDetail() );

                userActivityResponse.getAlertDetails().add( alertDetail );
            }
        }
    }

    @Override
    public SaveSearchResponse viewExistSaveSearch( final String uuid ) {
        return buyerService.viewExistSaveSearch( uuid );
    }

    /**
     * 
     * @param emailId
     * @param timeMin
     * @param timeMax
     * @return
     */
    @Override
    public BaseResponse getGoogleCalendarEvents( final String emailId, Long timeMin, Long timeMax ) {
        LOGGER.info( "getGoogleCalendarEvents for emailId : {} and timeMin : {} and timeMax : {} ", emailId, timeMin,
                timeMax );
        timeMin = timeMin != null ? timeMin : DateUtil.getStartOfDayLongTime();
        timeMax = timeMax != null ? timeMax : DateUtil.getLongTimeAfterInputDays( agentCalendarEventsLimit );
        final List< Event > events = calendarService.getEvents( emailId, timeMin, timeMax );
        final List< CalendarEvent > googleCalendarEventList = googleCalendarEventListBuilder.convertTo( events );
        final String timeZone = agentDetailsService.getAgentsTimeZone(emailId);
        final CalendarEventListResponse calendarEventListResponse = new CalendarEventListResponse();
        calendarEventListResponse.setCalendarEvents( googleCalendarEventList );
        calendarEventListResponse.setTimezone(timeZone);
        LOGGER.info( "getGoogleCalendarEvents for emailId : {} and googleCalendarEventsResponse : {}", emailId,
                JsonUtil.toJson( calendarEventListResponse ) );
        return calendarEventListResponse;
    }
    

    /**
     * To get Agents time zone
     * based on email id
     * @param emailId
     * @return
     */
	public TimeZone getAgentsTimeZone(final String emailId) {
		TimeZone timezone = TimeZone.getTimeZone("EST");
		final AgentDetails agentDetails = agentDetailsService.findAgentByEmail(emailId);
		if(agentDetails != null && agentDetails.getState() != null) {
			 timezone = TimeZone.getTimeZone("America/"+agentDetails.getState());
		}
		return timezone;
	}

	@Override
    public Object updateSaveSearch( final SavedSearchChimeraRequest savedSearchRequest ) {
        LOGGER.info( "Start : update  Save Search for userId : {} ", savedSearchRequest.getUserId() );
        final String uuid = savedSearchRequest.getUuid().trim();
        savedSearchRequest.setUuid( null );
        Map< String, Object > response = new HashMap<>();
        final Contact contact = contactServiceV1.getContactByOwnersComId( savedSearchRequest.getUserId().trim() );
        final boolean isSaveSearchExists = buyerService.checkSaveSearchExists( contact.getOwnersComId() );
        LOGGER.info( "is Save Search Exists for userId : {} ", isSaveSearchExists );
        if (isSaveSearchExists) {
            final Object saveSearchResponse = createSaveSearch( savedSearchRequest );
            LOGGER.info( "End : update  Save Search for userId " );
            response = ( Map< String, Object > ) saveSearchResponse;

            LOGGER.info( "Buyer save search response:{} " + response.get( STATUS ) );
            if (SUCCESS.name().equals( response.get( STATUS.toLowerCase() ) ) && ( response.get( MESSAGE ) == null
                    || !response.get( MESSAGE ).toString().contains( OPERATION_FAILED ) )) {
                LOGGER.info( "Start : deleting  Save Search for search ID : {} ", uuid );
                final Object object = buyerService.deleteSaveSearch( uuid.trim() );
                final Map< String, Object > delResponse = ( Map< String, Object > ) object;
                LOGGER.info( "End : delete  Save Search for userId status :{} ", delResponse.get( MESSAGE ) );
                response.put( "message", UPDATE_SUCCESSFUL );
            }
        }
        return response;
    }

    @Override
    @Transactional( readOnly = false, propagation = REQUIRED )
    public Object createSaveSearch( final SavedSearchChimeraRequest savedSearchRequest ) {
        LOGGER.info( "Start : create Save Search for userId : {} ", savedSearchRequest.getUserId() );
        final Object saveSearchResponse = buyerService.createsaveSearch( savedSearchRequest );
        final Map< String, Object > response = ( Map< String, Object > ) saveSearchResponse;
        LOGGER.info( "Buyer save search response:{} " + response.get( STATUS ) );

        if (SUCCESS.name().equals( response.get( STATUS.toLowerCase() ) ) && ( response.get( MESSAGE ) == null
                || !response.get( MESSAGE ).toString().contains( OPERATION_FAILED ) )) {
            final Contact contact = contactServiceV1.getContactByOwnersComId( savedSearchRequest.getUserId().trim() );
            final Map< String, Object > crmLeadRequest = new HashMap<>();
            final String notesStr = BuyerFarmingConstants.SAVE_SEARCH_SUCCESS_COMMENTS_TXT + new Date();
            final ObjectType objectType = objectTypeService.findByName( LEAD.toLowerCase() );
            crmLeadRequest.put( LEAD_FARMING_SYSTEM_ACTIONS, BuyerFarmingConstants.SAVE_SEARCH_SUCCESS );
            contactServiceV1.addOrUpdateAttribute( contact, objectType, NOTES.getKey(), notesStr );
            contactServiceV1.addOrUpdateAttribute( contact, objectType, FARMING_SYSTEM_ACTIONS.getKey(),
                    BuyerFarmingConstants.SAVE_SEARCH_SUCCESS );
            noteService.saveNote( new CrmNote( contact.getCrmId(), notesStr, null ) );
            contactServiceV1.save( contact );
            leadService.updateLead( crmLeadRequest, contact.getCrmId(), FALSE );
            response.put( MESSAGE, CREATE_SUCCESSFUL );
        } else if (DUPLICATE.name().equals( response.get( STATUS ) )) {
            response.put( MESSAGE, SAVE_SEARCH_DUPLICATE );
        } else {
            response.put( MESSAGE, SAVE_SEARCH_FAILED );
        }
        return saveSearchResponse;
    }

    @Override
    public ScheduleMeetingResponse getScheduleMeetingDetails( final String emailId, final String fromDate,
            final String toDate, final Boolean isAgent ) {
        LOGGER.info(
                "Start: Schedule Meeting Details for emailId : {} and fromDate : {} and toDate : {} and fromDate : {} and isAgent : {} ",
                emailId, fromDate, toDate,fromDate, isAgent );
        final Date[] dates = getFromAndToDates( fromDate, toDate );
        List< Object[] > objList = null;
        try{
        	if (isAgent != null) {
                // considering entered email id is for Agent
                objList = userService.getScheduleMeetingDetails( emailId, dates, true );

                // if objList is null, i.e. entered email id is for Buyer
                if (CollectionUtils.isEmpty( objList )) {
                    objList = userService.getScheduleMeetingDetails( emailId, dates, false );
                }
            } else {
                objList = userService.getScheduleMeetingDetails( emailId, dates, isAgent );
            }
        }catch(Exception ex){
        	LOGGER.info("Exception in getScheduleMeetingDetails : {}", ex);
        }
        
        final Map< String, com.owners.gravitas.dto.User > userMap = getUserDetails( objList, 0 );
        final ScheduleMeetingResponse scheduleMeetingResponse = buildScheduleMeetingResponse( objList, userMap);
        LOGGER.info( "End: Schedule Meeting Details for emailId : {} and isAgent : {} ", emailId, isAgent );
        return scheduleMeetingResponse;
    }

    /**
     * Builds the Schedule Meeting Response details response.
     *
     * @param Schedule
     *            Meeting Response details
     *            the Schedule Meeting Response details response
     * @param dbAgents
     *            the db agents
     * @return the user details response
     */
    private ScheduleMeetingResponse buildScheduleMeetingResponse( final List< Object[] > objList,
            final Map< String, com.owners.gravitas.dto.User > userMap) {
        final ScheduleMeetingResponse scheduleMeetingResponse = new ScheduleMeetingResponse();
        final List< ScheduleMeetingDetails > scheduleMeetingList = new ArrayList<>();
        scheduleMeetingResponse.setScheduleMeetingDetails( scheduleMeetingList );
        LocalDate now = LocalDate.now();
        for ( final Object[] obj : objList ) {
        	
        	String status = null;
        	if(obj[9] != null && (boolean)obj[9]){
        		status = Constants.DELETED_CC;
            }else {
            	status = getStringValue( obj[6] ).toLowerCase();
            	if(status.equalsIgnoreCase(Constants.PENDING_LC)){
            		Date date = (null!=obj[4]) ? (Date)obj[4] : null;
            		if(compareMeetingDateWithCurrentDate(date,getStringValue( obj[7] ))){
            			status = Constants.PAST_DUE_LC;
            		}
            		status = StringUtils.capitalize(status);
            	}else if (status.equalsIgnoreCase(Constants.COMPLETED_LC) || status.equalsIgnoreCase(Constants.CONFIRMED_LC)){
            		status = StringUtils.capitalize(status);
            	}else {
            		status = null;
            	}
            }
        	
        	if(status != null){
        		final ScheduleMeetingDetails scheduleMeetingDetails = new ScheduleMeetingDetails();
                scheduleMeetingDetails.setMeetingstatus(status);
                final com.owners.gravitas.dto.User user = userMap.get( obj[0] );
                if (user != null) {
                    scheduleMeetingDetails.setAgentName(getStringValue(user.getFirstName()) + " " + getStringValue(user.getLastName()));
                    scheduleMeetingDetails.setAgentPhoneNo( getStringValue( user.getPhone() ) );
                }
                scheduleMeetingDetails.setAgentEmailId( getStringValue( obj[0] ) );
                scheduleMeetingDetails.setBuyerName( getStringValue( obj[1] ) );
                scheduleMeetingDetails.setBuyerEmailId( getStringValue( obj[2] ) );
                scheduleMeetingDetails.setBuyerPhoneNo( getStringValue( obj[3] ) );
                final String agentState = getStringValue( obj[7] );
                final String meetingDate =  convertDateToAgentTimeZone( null!=obj[4] ? (Date)obj[4]:null , agentState );
                scheduleMeetingDetails.setMeetingDate( meetingDate );
                scheduleMeetingDetails.setMeetingLocation( getStringValue( obj[5] ) );
                scheduleMeetingDetails.setAgentState( getStringValue( obj[8] ) );
                scheduleMeetingDetails.setStage( getStringValue( obj[10] ) );
                scheduleMeetingDetails.setOpportunityId( getStringValue( obj[11] ) );
                scheduleMeetingDetails.setStatus( StringUtils.capitalize(getStringValue( obj[12] )) );
                scheduleMeetingDetails.setAvailability( (boolean)obj[13] ? Constants.ON_DUTY : Constants.OFF_DUTY  );
                scheduleMeetingList.add( scheduleMeetingDetails );
        	}
        }
        return scheduleMeetingResponse;
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
    private Date[] getFromAndToDates( final String fromDate, String toDate ) {
        LOGGER.info( "Start: selecting date range" );
        final Date[] dates = new Date[2];
        final Calendar cal = Calendar.getInstance();
        final SimpleDateFormat format1 = new SimpleDateFormat( DEFAULT_CRM_DATE_PATTERN );
        if (StringUtils.isEmpty( fromDate ) || StringUtils.isEmpty( toDate )) {
            cal.add( Calendar.DATE, 0 );
            final String formatted1 = format1.format( cal.getTime() );

            cal.add( Calendar.DATE, agentOpportunityBusinessConfig.getScheduledMeetingsDateRange() + 1 );
            final String formatted2 = format1.format( cal.getTime() );

            dates[0] = toSqlDate( formatted1, DEFAULT_CRM_DATE_PATTERN );
            dates[1] = toSqlDate( formatted2, DEFAULT_CRM_DATE_PATTERN );
        } else {
            try {
                cal.setTime( format1.parse( toDate ) );
                cal.add( Calendar.DAY_OF_MONTH, 1 );
                toDate = format1.format( cal.getTime() );
                dates[0] = toSqlDate( fromDate.trim(), DEFAULT_CRM_DATE_PATTERN );
                dates[1] = toSqlDate( toDate.trim(), DEFAULT_CRM_DATE_PATTERN );
            } catch ( final ParseException e ) {
                LOGGER.info( "Passed toDate is not formatable  in formaat {}", DEFAULT_CRM_DATE_PATTERN );
                e.printStackTrace();
            }
        }
        LOGGER.info( "End: Selected Date ranges are :  fromDate : {} and toDate : {}", dates[0], dates[1] );
        return dates;
    }

    /**
     * Build the Google UserMap.
     *
     * @param details
     *            the details data
     * @return the Google UserMap
     */
    private Map< String, com.owners.gravitas.dto.User > getUserDetails( final List< Object[] > details,
            final int emaiIdIndex ) {
        LOGGER.info( "Start: getUserMap for the list of email id's" );
        final Set< String > emailSet = new LinkedHashSet<>();

        details.forEach( entry -> {
            emailSet.add( getStringValue( entry[emaiIdIndex] ) );
        } );
        final List< String > emailList = new ArrayList<>( emailSet );
        LOGGER.debug( "Fetching userMap from google for emails => {}", JsonUtil.toJson( emailList ) );
        final Map< String, com.owners.gravitas.dto.User > userMap = getUserNameMap( emailList );
        LOGGER.debug( "Fetched userMap from google. userMap => {}", JsonUtil.toJson( userMap ) );
        LOGGER.info( "End: getUserMap for the list of email id's" );
        return userMap;
    }

    /**
     * Gets the string value.
     *
     * @param object
     *            the object
     * @return the string value
     */
    private String getStringValue( final Object object ) {
        return ( null == object ) ? "" : object.toString();
    }

    /**
     * Build the map of Google users information.
     *
     * @param emailList
     *            the email list
     * @return the map of Google users information
     */
    private Map< String, com.owners.gravitas.dto.User > getUserNameMap( final List< String > emailList ) {
        LOGGER.info( "Start: Get Google Users information" );
        final Map< String, com.owners.gravitas.dto.User > userMap = new HashMap<>();
        final List< User > googleUsers = userService.getUsersByEmails( emailList );
        final UserDetailsResponse userDetailsResponse = userDetailsResponseBuilder.convertTo( googleUsers );
        for ( final com.owners.gravitas.dto.User usr : userDetailsResponse.getUsers() ) {
            userMap.put( usr.getEmail(), usr );
        }
        LOGGER.info( "Ends: Get Google Users information" );
        return userMap;
    }

    private LeadSource createLeadSource( final Contact contact ) {
        final LeadSource leadSource = new LeadSource();
        leadSource.setId( contact.getCrmId() );
        leadSource.setOwnerId( contact.getOwnersComId() );
        leadSource.setDoNotEmail( false );
        leadSource.setEmail( contact.getEmail() );
        return leadSource;
    }

    @Override
    public CheckScheduleMeetingValidationResponse isAgentEventExists( final String agentEmail, final Long dueDtm ) {
        LOGGER.info( "checking if events exists for agentEmail : {} dueDate : {} ", agentEmail, dueDtm );
        final CheckScheduleMeetingValidationResponse checkScheduleMeetingValidationResponse = new CheckScheduleMeetingValidationResponse();
        final Long startTime = new DateTime( dueDtm ).minusMinutes( 59 ).getMillis();
        final Long endTime = new DateTime( dueDtm ).plusMinutes( 59 ).getMillis();
        final Long startGoogleEventTime = new DateTime( dueDtm ).getMillis();
        final List< AgentTask > agentTasks = agentTaskService.findByAgentEmailAndStatusNotAndDueDate( agentEmail,
                deleted.name(), new java.util.Date( startTime ), new java.util.Date( endTime ) );
        if (!CollectionUtils.isEmpty( agentTasks )) {
            checkScheduleMeetingValidationResponse.setValidationsPassed( false );
            return checkScheduleMeetingValidationResponse;
        }
        final List< Event > events = calendarService.getEvents( agentEmail, startGoogleEventTime, endTime );
        if (!CollectionUtils.isEmpty( events )) {
            checkScheduleMeetingValidationResponse.setValidationsPassed( false );
            return checkScheduleMeetingValidationResponse;
        }
        checkScheduleMeetingValidationResponse.setValidationsPassed( true );
        return checkScheduleMeetingValidationResponse;
    }
    
    /**
     * Method to convert the schedule meeting date to agent's timeZone
     * 
     * @param date
     * @param agentState
     * @return String date converted to agent's timeZone
     */
    private String convertDateToAgentTimeZone( final Date date, final String agentState ) {
        String timezone = defaultTimeZoneForAgents;
        LOGGER.info( "converting date: {} for agent having state: {} ", date, agentState );
        if(null==date) {
            return "No schedule Meeting date!";
        }
        if (StringUtils.isNotBlank( agentState )) {
            timezone = timeZoneUtil.getTimeZoneByState( agentState );
            LOGGER.info( "time zone: {} obtained from state: {} ", timezone, agentState );
        }
        final DateTimeZone dateTimeZone = DateTimeZone.forID( timezone );
        return DateUtil.toString( new LocalDateTime( date, dateTimeZone ), DateUtil.AGENT_DATE_TIME_FORMAT ) + " "
                + timezone.substring( 0, 3 );
    }
    
    private boolean compareMeetingDateWithCurrentDate( final Date date, final String agentState ) {
        String timezone = defaultTimeZoneForAgents;
        if(null==date) {
            return false;
        }
        if (StringUtils.isNotBlank( agentState )) {
            timezone = timeZoneUtil.getTimeZoneByState( agentState );
        }
        final DateTimeZone dateTimeZone = DateTimeZone.forID( timezone );
        LocalDateTime meetingDate = new LocalDateTime( date, dateTimeZone );
        LocalDateTime now = new LocalDateTime( new Date(), dateTimeZone );
        return now.isAfter(meetingDate);
    }

    @Override
    public BaseResponse updateNotificationConfig( final UserNotificationConfigDetails userNotificationConfigDetails ) {
        BaseResponse baseResponse = null;
        try {
            LOGGER.info( "User notification config updation for the user {}", userNotificationConfigDetails.getUserId() );
            final Contact contact = contactServiceV1.getContactByOwnersComId( userNotificationConfigDetails.getUserId() );
            if (null != contact) {
                userNotificationConfigDetails.setEmailId( contact.getEmail() );
            }
            final String mappedSubscriptionType = userService.getSubscriptionType( userNotificationConfigDetails.getSubscribeType() );
            userNotificationConfigDetails.setSubscribeType( mappedSubscriptionType );
            final UserNotificationConfigResponse response = buyerService.updateNotificationConfig( userNotificationConfigDetails );
            if (null != response && null != response.getResult()
                    && Status.SUCCESS.name().equalsIgnoreCase( response.getResult().getStatus() )) {
                baseResponse = new BaseResponse( Status.SUCCESS, "User notification config updated successfully" );
            } else {
                baseResponse = new BaseResponse( Status.FAILURE, "User notification config updation failed" );
            }
        } catch ( final Exception e ) {
            baseResponse = new BaseResponse( Status.FAILURE, "User notification config updation failed" );
            LOGGER.error( "Exception while updateNotificationConfig for userId {} with exception detail {}",
                    userNotificationConfigDetails.getUserId(), e );
        }
        return baseResponse;
    }
}
