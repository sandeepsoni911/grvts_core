package com.owners.gravitas.business.impl;

import static com.owners.gravitas.dto.response.BaseResponse.Status.SUCCESS;
import static com.owners.gravitas.enums.AgentTaskStatus.CANCELLED;
import static com.owners.gravitas.enums.AgentTaskStatus.COMPLETE;
import static com.owners.gravitas.enums.AgentTaskStatus.COMPLETED;
import static com.owners.gravitas.enums.AgentTaskStatus.INCOMPLETE;
import static com.owners.gravitas.enums.AgentTaskStatus.UNASSIGNED;
import static com.owners.gravitas.enums.LeadRequestType.REQUEST_INFORMATION;
import static com.owners.gravitas.enums.RefType.VALID_CHECKIN_QUESTIONNAIRE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.Test;

import com.google.api.services.calendar.model.Event;
import com.owners.gravitas.business.ActionLogBusinessService;
import com.owners.gravitas.business.AgentNotificationBusinessService;
import com.owners.gravitas.business.AgentReminderBusinessService;
import com.owners.gravitas.business.PropertyBusinessService;
import com.owners.gravitas.business.builder.CoShoppingLeadBuilder;
import com.owners.gravitas.business.builder.ContactBuilder;
import com.owners.gravitas.business.builder.GoogleCalendarEventBuilder;
import com.owners.gravitas.business.builder.response.UserDetailsResponseBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.AgentCheckinTaskConfig;
import com.owners.gravitas.config.CoShoppingConfig;
import com.owners.gravitas.config.LeadOpportunityBusinessConfig;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.domain.Reminder;
import com.owners.gravitas.domain.Request;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.domain.Task;
import com.owners.gravitas.domain.entity.AgentTask;
import com.owners.gravitas.domain.entity.AgentTaskStatusLog;
import com.owners.gravitas.domain.entity.RefCode;
import com.owners.gravitas.domain.entity.RefType;
import com.owners.gravitas.dto.ActionLogDto;
import com.owners.gravitas.dto.ApiUser;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.PropertyData;
import com.owners.gravitas.dto.TourDetails;
import com.owners.gravitas.dto.User;
import com.owners.gravitas.dto.request.AgentTaskRequest;
import com.owners.gravitas.dto.request.CoShoppingLeadRequest;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.dto.request.NotificationRequest;
import com.owners.gravitas.dto.request.TaskUpdateRequest;
import com.owners.gravitas.dto.response.AgentResponse;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.CheckScheduleMeetingValidationResponse;
import com.owners.gravitas.dto.response.CheckinDetailResponse;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.dto.response.PropertyDetailsResponse;
import com.owners.gravitas.dto.response.UserDetailsResponse;
import com.owners.gravitas.enums.AgentTaskStatus;
import com.owners.gravitas.enums.TaskType;
import com.owners.gravitas.service.AgentContactService;
import com.owners.gravitas.service.AgentDetailsService;
import com.owners.gravitas.service.AgentOpportunityService;
import com.owners.gravitas.service.AgentRequestService;
import com.owners.gravitas.service.AgentService;
import com.owners.gravitas.service.AgentTaskService;
import com.owners.gravitas.service.AgentTaskStatusLogService;
import com.owners.gravitas.service.CalendarService;
import com.owners.gravitas.service.CheckinFeedbackService;
import com.owners.gravitas.service.CoShoppingService;
import com.owners.gravitas.service.ContactEntityService;
import com.owners.gravitas.service.OpportunityService;
import com.owners.gravitas.service.PropertyService;
import com.owners.gravitas.service.RefCodeService;
import com.owners.gravitas.service.RefTypeService;
import com.owners.gravitas.service.SearchService;
import com.owners.gravitas.service.UserService;
import com.owners.gravitas.service.impl.ContactServiceImpl;
import com.owners.gravitas.util.GravitasWebUtil;
import com.owners.gravitas.util.TimeZoneUtil;
import com.owners.gravitas.validator.AgentTaskValidator;
import com.zuner.coshopping.model.common.Resource;
import com.zuner.coshopping.model.lead.LeadModel;

/**
 * The Class AgentBusinessServiceImplTest.
 *
 * @author vishwanathm
 */
public class AgentTaskBusinessServiceImplTest extends AbstractBaseMockitoTest {
    /** The agent business service impl. */
    @InjectMocks
    private AgentTaskBusinessServiceImpl agentTaskBusinessServiceImpl;

    /** The contact entity service. */
    @Mock
    private ContactEntityService contactEntityService;

    /** The property service. */
    @Mock
    private PropertyService propertyService;

    /** The agent service. */
    @Mock
    private AgentService agentService;

    /** The user details response builder. */
    @Mock
    private UserDetailsResponseBuilder userDetailsResponseBuilder;

    /** The action log business service. */
    @Mock
    private ActionLogBusinessService actionLogBusinessService;

    /** The user service. */
    @Mock
    private UserService userService;

    /** The gravitas web util. */
    @Mock
    private GravitasWebUtil gravitasWebUtil;

    /** The agent notification business service. */
    @Mock
    private AgentNotificationBusinessService agentNotificationBusinessService;

    /** The agent opportunity business service impl. */
    @Mock
    private AgentOpportunityBusinessServiceImpl agentOpportunityBusinessServiceImpl;

    /** The contact service impl. */
    @Mock
    private ContactServiceImpl contactServiceImpl;

    /** The agent opportunity service. */
    @Mock
    private AgentOpportunityService agentOpportunityService;

    /** The agent task service. */
    @Mock
    private AgentTaskService agentTaskService;

    /** The co shopping config. */
    @Mock
    private CoShoppingConfig coShoppingConfig;

    /** The agent reminder business service. */
    @Mock
    private AgentReminderBusinessService agentReminderBusinessService;

    /** The agent request service. */
    @Mock
    private AgentRequestService agentRequestService;

    /** The property business service. */
    @Mock
    private PropertyBusinessService propertyBusinessService;

    /** The google calendar event builder. */
    @Mock
    private GoogleCalendarEventBuilder googleCalendarEventBuilder;

    /** The calendar service. */
    @Mock
    private CalendarService calendarService;

    /** The contact builder. */
    @Mock
    private ContactBuilder contactBuilder;

    /** The contact service v1. */
    @Mock
    private OpportunityService opportunityService;

    /** The agent task status log service. */
    @Mock
    private AgentTaskStatusLogService agentTaskStatusLogService;

    /** The agent task validator. */
    @Mock
    private AgentTaskValidator agentTaskValidator;

    /** The ref code service. */
    @Mock
    private RefCodeService refCodeService;

    /** The ref type code service. */
    @Mock
    private RefTypeService refTypeService;

    /** The agent checkin task config. */
    @Mock
    private AgentCheckinTaskConfig agentCheckinTaskConfig;
    /** The co shopping lead request builder. */
    @Mock
    private CoShoppingLeadBuilder coShoppingLeadBuilder;

    /** The co shopping service. */
    @Mock
    private CoShoppingService coShoppingService;

    /** The checkin feedback service. */
    @Mock
    private CheckinFeedbackService checkinFeedbackService;

    /** The agent contact service. */
    @Mock
    private AgentContactService agentContactService;

    /** The search service. */
    @Mock
    private SearchService searchService;

    /** The contact service v1. */
    @Mock
    private ContactEntityService contactServiceV1;

    @Mock
    private LeadOpportunityBusinessConfig leadOpportunityBusinessConfig;

    @Mock
    private AgentDetailsService agenDetailsService;

    @InjectMocks
    private TimeZoneUtil timeZoneUtil;

    /**
     * Test create agent task.
     */
    @Test
    public void testCreateAgentTask() {
        when( agentOpportunityService.getOpportunityById( any(), any() ) ).thenReturn( new Opportunity() );
        when( agentTaskService.saveAgentTask( any(), any() ) ).thenReturn( new PostResponse() );
        final AgentResponse response = agentTaskBusinessServiceImpl.createAgentTask( "test", "test",
                new AgentTaskRequest() );
        assertEquals( response.getStatus(), SUCCESS );
    }

    /**
     * Test update agent task.
     */
    @Test
    public void testUpdateAgentTask() {
        Mockito.doNothing().when( actionLogBusinessService ).auditForAction( Mockito.any( ActionLogDto.class ) );
        when( gravitasWebUtil.getAppUserEmail() ).thenReturn( null );
        final TaskUpdateRequest request = new TaskUpdateRequest();
        request.setTitleSelectionReason( "test" );
        final Task task = new Task();
        task.setOpportunityId( "test" );
        when( agentOpportunityBusinessServiceImpl.patchOpportunity( "test", "test", new HashMap<>() ) )
                .thenReturn( new AgentResponse( "test", Status.FAILURE, "test" ) );
        when( agentTaskService.getTaskById( "test", "test" ) ).thenReturn( task );
        final AgentResponse response = agentTaskBusinessServiceImpl.handleAgentTask( "test", "test", request );
        assertNotNull( response );
    }

    /**
     * Should create agent task if dueDtm is not null.
     */
    @Test
    public void shouldCreateAgentTask_IfDueDtmIsNotNull() {
        final PostResponse postResponse = new PostResponse();
        postResponse.setName( "dummy name" );
        Mockito.doNothing().when( actionLogBusinessService ).auditForAction( Mockito.any( ActionLogDto.class ) );
        when( gravitasWebUtil.getAppUserEmail() ).thenReturn( null );
        when( agentTaskService.getTaskById( anyString(), anyString() ) ).thenReturn( new Task() );
        when( agentOpportunityService.getOpportunityById( anyString(), anyString() ) ).thenReturn( new Opportunity() );
        when( agentTaskService.saveAgentTask( any( Task.class ), anyString() ) ).thenReturn( postResponse );
        when( searchService.searchByOpportunityId( anyString() ) ).thenReturn( new Search() );
        when( agentContactService.getContactById( anyString(), anyString() ) )
                .thenReturn( new com.owners.gravitas.domain.Contact() );
        when( agentNotificationBusinessService.sendPushNotification( anyString(), any( NotificationRequest.class ) ) )
                .thenReturn( null );

        final TaskUpdateRequest taskUpdateRequest = new TaskUpdateRequest();
        final AgentResponse response = agentTaskBusinessServiceImpl.handleAgentTask( "test", "test",
                taskUpdateRequest );

        assertNotNull( response );
    }

    /**
     * Should save task if not exist.
     */
    @Test
    public void shouldSaveTaskifNotExists() {
        final String agentId = "dummy agent id";
        final String opportunityId = "dummy opp id";
        final TaskType type = TaskType.USER_DEFINED;
        final String title = "dummy title";
        final String name = "dummy name";
        final PostResponse response = new PostResponse();

        when( agentTaskService.getTasksByType( anyString(), anyString(), anyString() ) ).thenReturn( null );
        when( agentTaskService.saveAgentTask( any( Task.class ), anyString() ) ).thenReturn( response );
        agentTaskBusinessServiceImpl.saveTaskifNotExists( agentId, opportunityId, type, title, name );
        verify( agentTaskService ).saveAgentTask( any( Task.class ), anyString() );
    }

    /**
     * Close task by type test.
     */
    @Test
    public void closeTaskByTypeTest() {
        final Map< String, Task > result = new HashMap<>();
        final Task task = new Task();
        task.setDueDtm( 12313123L );
        task.setDueDtm( 1L );
        task.setLocation( "location" );
        task.setTitle( "title" );
        result.put( "test", task );
        final Map< String, Task > openTasks = new HashMap<>();
        openTasks.put( "key1", task );
        when( agentTaskService.getOpenTasksByType( anyString(), anyString(), anyString() ) ).thenReturn( openTasks );
        when( agentTaskService.getTasksByType( Mockito.anyString(), Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( result );
        agentTaskBusinessServiceImpl.closeTaskByType( "agentId", "opportunityId", TaskType.CONTACT_OPPORTUNITY );
        verify( agentTaskService ).patchAgentTask( Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf( String.class, Object.class ) );
    }

    /**
     * Close task by type test not matching task.
     */
    @Test
    public void closeTaskByTypeTestNotMatchingTask() {
        final Map< String, Task > result = new HashMap<>();
        when( agentTaskService.getTasksByType( Mockito.anyString(), Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( result );
        agentTaskBusinessServiceImpl.closeTaskByType( "agentId", "opportunityId", TaskType.CONTACT_OPPORTUNITY );
        verify( agentTaskService, VerificationModeFactory.times( 0 ) ).patchAgentTask( Mockito.anyString(),
                Mockito.anyString(), Mockito.anyMapOf( String.class, Object.class ) );
    }

    /**
     * Test add to google calendar.
     */
    @Test
    public void testAddToGoogleCalendar() {
        final String agentId = "agentId";
        final String taskId = "taskId";
        final String agentEmail = "email";
        final Task task = new Task();
        task.setOpportunityId( "test" );
        task.setTitle( "test" );
        final Event event = new Event();
        event.setSummary( "test" );
        event.setHtmlLink( "test" );
        final com.owners.gravitas.domain.Contact contact = new com.owners.gravitas.domain.Contact();
        contact.setFirstName( "test" );
        contact.setLastName( "test" );
        final Contact contactDomain = new Contact();
        contactDomain.setFirstName( "testFirstName" );
        contactDomain.setLastName( "testLastName" );

        when( contactServiceImpl.getContactByOpportunityId( anyString() ) ).thenReturn( contact );
        when( agentTaskService.getTaskById( agentId, taskId ) ).thenReturn( task );
        when( googleCalendarEventBuilder.convertTo( task ) ).thenReturn( event );
        when( calendarService.createEvent( agentEmail, event ) ).thenReturn( event );
        when( contactBuilder.convertTo( contact ) ).thenReturn( contactDomain );
        final AgentResponse agentResponse = agentTaskBusinessServiceImpl.addToGoogleCalendar( agentId, taskId,
                agentEmail );
        assertEquals( agentResponse.getStatus(), SUCCESS );
        assertEquals( agentResponse.getId(), taskId );
        verify( agentTaskService ).getTaskById( anyString(), anyString() );
        verify( googleCalendarEventBuilder ).convertTo( any( Task.class ) );
        verify( calendarService ).createEvent( anyString(), any( Event.class ) );
        verify( contactBuilder ).convertTo( contact );
    }

    /**
     * Test create agent task when due date provided.
     */
    @Test
    public void testCreateAgentTaskWhenDueDateProvided() {
        final AgentTaskRequest agentTaskRequest = new AgentTaskRequest();
        agentTaskRequest.setDueDtm( new Date( System.currentTimeMillis() + 5 * 60000 ) );
        when( agentOpportunityService.getOpportunityById( any(), any() ) ).thenReturn( new Opportunity() );
        when( agentTaskService.saveAgentTask( any(), any() ) ).thenReturn( new PostResponse() );
        final AgentResponse response = agentTaskBusinessServiceImpl.createAgentTask( "test", "test", agentTaskRequest );
        assertEquals( response.getStatus(), SUCCESS );
        verify( agentOpportunityService ).getOpportunityById( any(), any() );
        verify( agentTaskService ).saveAgentTask( any(), any() );
        verify( agentReminderBusinessService ).setTaskReminder( any(), any(), any(), any(), any() );
    }

    /**
     * Test handle agent task should update agent task.
     */
    @Test
    public void testHandleAgentTaskShouldUpdateAgentTask() {
        final String agentId = "agentId";
        final String taskId = "taskId";
        final TaskUpdateRequest taskUpdateRequest = new TaskUpdateRequest();
        taskUpdateRequest.setDueDtm( new Date() );
        taskUpdateRequest.setLocation( "location" );
        taskUpdateRequest.setTitle( "title" );
        taskUpdateRequest.setStatus( AgentTaskStatus.CONFIRMED.toString() );
        final Task task = new Task();
        task.setIsPrimary( true );

        when( agentTaskService.getTaskById( agentId, taskId ) ).thenReturn( task );

        final AgentResponse agentResponse = agentTaskBusinessServiceImpl.handleAgentTask( agentId, taskId,
                taskUpdateRequest );

        assertEquals( agentResponse.getStatus(), SUCCESS );
        assertEquals( agentResponse.getId(), taskId );
        //verify( agentTaskService ).getTaskById( agentId, taskId );
        verify( agentReminderBusinessService ).updateAndCancelReminder( agentId, taskId,
                taskUpdateRequest.getDueDtm().getTime() );
        verify( agentTaskService ).patchAgentTask( any(), any(), any() );
    }

    /**
     * Test handle agent task should update agent task and create task for
     * schedule tour.
     */
    @Test
    public void testHandleAgentTaskShouldUpdateAgentTaskAndCreateTaskForScheduleTour() {
        final String agentId = "agentId";
        final String taskId = "taskId";
        final TaskUpdateRequest taskUpdateRequest = new TaskUpdateRequest();
        taskUpdateRequest.setScheduleDtm( new Date() );
        taskUpdateRequest.setDescription( "notes" );
        final Task task = new Task();
        final Map< String, Request > requests = new HashMap<>();
        final Request request = new Request();
        request.setTaskId( taskId );
        requests.put( "key", request );

        when( agentTaskService.getTaskById( agentId, taskId ) ).thenReturn( task );
        when( agentRequestService.getRequestsByOpportunityId( agentId, task.getOpportunityId() ) )
                .thenReturn( requests );
        when( agentOpportunityService.getOpportunityById( agentId, task.getOpportunityId() ) )
                .thenReturn( new Opportunity() );
        when( agentTaskService.saveAgentTask( any(), any() ) ).thenReturn( new PostResponse() );
        when( propertyBusinessService.getPropertyLocation( any() ) ).thenReturn( "location" );

        final AgentResponse agentResponse = agentTaskBusinessServiceImpl.handleAgentTask( agentId, taskId,
                taskUpdateRequest );

        assertEquals( agentResponse.getStatus(), SUCCESS );
        assertEquals( agentResponse.getId(), taskId );
        verify( agentTaskService ).getTaskById( agentId, taskId );
        verify( agentRequestService ).getRequestsByOpportunityId( agentId, task.getOpportunityId() );
        verify( propertyBusinessService ).getPropertyLocation( any() );
        verify( agentOpportunityService ).getOpportunityById( agentId, task.getOpportunityId() );
        verify( agentTaskService ).saveAgentTask( any(), any() );
        verify( agentTaskService ).patchAgentTask( any(), any(), any() );
        verify( agentReminderBusinessService ).cancelAgentTaskReminders( agentId, taskId );
    }

    /**
     * Test complete agent task when request is null.
     */
    @Test
    public void testCompleteAgentTaskWhenRequestIsNull() {
        final String agentId = "agentId";
        final String taskId = "taskId";

        final AgentResponse agentResponse = agentTaskBusinessServiceImpl.completeAgentTask( agentId, taskId, null );

        assertEquals( agentResponse.getStatus(), SUCCESS );
        assertEquals( agentResponse.getId(), taskId );
        verify( agentTaskService ).patchAgentTask( anyString(), anyString(), any() );
        verify( agentReminderBusinessService ).cancelAgentTaskReminders( agentId, taskId );
    }

    /**
     * Test complete agent task should create task for schedule tour.
     */
    @Test
    public void testCompleteAgentTaskShouldCreateTaskForScheduleTour() {
        final String agentId = "agentId";
        final String taskId = "taskId";
        final TaskUpdateRequest taskUpdateRequest = new TaskUpdateRequest();
        taskUpdateRequest.setScheduleDtm( new Date( System.currentTimeMillis() + 5 * 60000 ) );
        final Task task = new Task();

        when( agentTaskService.getTaskById( agentId, taskId ) ).thenReturn( task );
        when( agentOpportunityService.getOpportunityById( agentId, task.getOpportunityId() ) )
                .thenReturn( new Opportunity() );
        when( agentTaskService.saveAgentTask( any(), any() ) ).thenReturn( new PostResponse() );

        final AgentResponse agentResponse = agentTaskBusinessServiceImpl.completeAgentTask( agentId, taskId,
                taskUpdateRequest );

        assertEquals( agentResponse.getStatus(), SUCCESS );
        assertEquals( agentResponse.getId(), taskId );
        verify( propertyBusinessService ).getPropertyLocation( any() );
        verify( agentTaskService, times( 2 ) ).getTaskById( agentId, taskId );
        verify( agentOpportunityService ).getOpportunityById( agentId, task.getOpportunityId() );
        verify( agentTaskService ).saveAgentTask( any(), any() );
        verify( agentReminderBusinessService ).setTaskReminder( anyString(), anyString(), anyString(), any(), any() );
        verify( agentTaskService ).patchAgentTask( anyString(), anyString(), any() );
        verify( agentReminderBusinessService ).cancelAgentTaskReminders( agentId, taskId );
        verifyZeroInteractions( agentOpportunityBusinessServiceImpl );
    }

    /**
     * Test complete agent task should update title selection reason.
     */
    @Test
    public void testCompleteAgentTaskShouldUpdateTitleSelectionReason() {
        final String agentId = "agentId";
        final String taskId = "taskId";
        final TaskUpdateRequest taskUpdateRequest = new TaskUpdateRequest();
        taskUpdateRequest.setTitleSelectionReason( "test" );
        final Task task = new Task();

        when( agentTaskService.getTaskById( agentId, taskId ) ).thenReturn( task );
        when( agentOpportunityService.getOpportunityById( agentId, task.getOpportunityId() ) )
                .thenReturn( new Opportunity() );
        when( agentTaskService.saveAgentTask( any(), any() ) ).thenReturn( new PostResponse() );

        final AgentResponse agentResponse = agentTaskBusinessServiceImpl.completeAgentTask( agentId, taskId,
                taskUpdateRequest );

        assertEquals( agentResponse.getStatus(), SUCCESS );
        assertEquals( agentResponse.getId(), taskId );

        verify( agentOpportunityBusinessServiceImpl ).patchOpportunity( anyString(), anyString(), any() );
        verify( agentTaskService ).patchAgentTask( anyString(), anyString(), any() );
        verify( agentReminderBusinessService ).cancelAgentTaskReminders( agentId, taskId );
    }

    @Test
    public void testCreateAgentTask_IfTypeHasValueAsMakeOffer() {
        final AgentTaskRequest agentTaskRequest = new AgentTaskRequest();
        agentTaskRequest.setDueDtm( new Date( System.currentTimeMillis() + 5 * 60000 ) );
        agentTaskRequest.setType( TaskType.MAKE_OFFER.getType() );
        when( agentOpportunityService.getOpportunityById( any(), any() ) ).thenReturn( new Opportunity() );
        when( agentTaskService.saveAgentTask( any(), any() ) ).thenReturn( new PostResponse() );
        final AgentResponse response = agentTaskBusinessServiceImpl.createAgentTask( "test", "test", agentTaskRequest );
        assertEquals( response.getStatus(), SUCCESS );
        verify( agentOpportunityService ).getOpportunityById( any(), any() );
        verify( agentTaskService ).saveAgentTask( any(), any() );
        verify( agentReminderBusinessService ).setTaskReminder( any(), any(), any(), any(), any() );
    }

    /**
     * Test create agent task_ if type has value as schedule tour.
     */
    @Test
    public void testCreateAgentTask_IfTypeHasValueAsScheduleTour() {
        final AgentTaskRequest agentTaskRequest = new AgentTaskRequest();
        final Date dueDate = new Date( System.currentTimeMillis() + 5 * 60000 );
        agentTaskRequest.setDueDtm( dueDate );
        agentTaskRequest.setType( TaskType.SCHEDULE_TOUR.getType() );
        agentTaskRequest.setListingId( "listingId" );
        final CoShoppingLeadRequest request = new CoShoppingLeadRequest();
        final LeadModel model = new LeadModel();
        request.setLeadModel( model );
        final String listingId = "listingId";
        final String oppId = "opportunityId";
        final Search search = new Search();
        search.setAgentEmail( "testagent1@ownerstest.com" );
        final PropertyDetailsResponse propertyDetailsResponse = new PropertyDetailsResponse();
        final PropertyData propertyData = new PropertyData();
        propertyData.setBathRooms( new BigDecimal( 0 ) );
        propertyDetailsResponse.setData( propertyData );
        final com.owners.gravitas.domain.entity.Contact contact = new com.owners.gravitas.domain.entity.Contact();
        Mockito.when( contactEntityService.getContactByFbOpportunityId( oppId ) ).thenReturn( contact );
        Mockito.when( propertyService.getPropertyDetails( listingId ) ).thenReturn( propertyDetailsResponse );
        when( coShoppingService.postLeadDetails( request ) ).thenReturn( new Resource() );
        when( coShoppingConfig.isScheduledTourByFieldAgentEnabled() ).thenReturn( true );
        when( coShoppingLeadBuilder.build( dueDate, contact, propertyData, "EST" ) ).thenReturn( request );
        when( agentOpportunityService.getOpportunityById( any(), any() ) ).thenReturn( new Opportunity() );
        when( agentTaskService.saveAgentTask( any(), any() ) ).thenReturn( new PostResponse() );
        when( searchService.searchByCrmOpportunityId( any() ) ).thenReturn( search );
        when( agenDetailsService.getAgentsTimeZone( search.getAgentEmail() ) ).thenReturn( "EST" );
        final AgentResponse response = agentTaskBusinessServiceImpl.createAgentTask( "agentId", "opportunityId",
                agentTaskRequest );
        assertEquals( response.getStatus(), SUCCESS );
        verify( agentOpportunityService ).getOpportunityById( any(), any() );
        verify( agentTaskService ).saveAgentTask( any(), any() );
        verify( agentReminderBusinessService ).setTaskReminder( any(), any(), any(), any(), any() );
    }

    @Test
    public void testCreateAgentTask_TypeWithDefaultValue() {
        final AgentTaskRequest agentTaskRequest = new AgentTaskRequest();
        agentTaskRequest.setDueDtm( new Date( System.currentTimeMillis() + 5 * 60000 ) );
        when( agentOpportunityService.getOpportunityById( any(), any() ) ).thenReturn( new Opportunity() );
        when( agentTaskService.saveAgentTask( any(), any() ) ).thenReturn( new PostResponse() );
        final AgentResponse response = agentTaskBusinessServiceImpl.createAgentTask( "test", "test", agentTaskRequest );
        assertEquals( response.getStatus(), SUCCESS );
        verify( agentOpportunityService ).getOpportunityById( any(), any() );
        verify( agentTaskService ).saveAgentTask( any(), any() );
        verify( agentReminderBusinessService ).setTaskReminder( any(), any(), any(), any(), any() );
    }

    /**
     * Test delete agent task.
     */
    @Test( enabled = false )
    public void testDeleteAgentTask() {
        final AgentTask agentTask = new AgentTask();
        final Task task = new Task();
        final ApiUser apiUser = new ApiUser();
        apiUser.setUid( "some_agent_uuid" );
        apiUser.setEmail( "agent@owners.com" );
        final AgentTaskStatusLog statusLog = new AgentTaskStatusLog();
        statusLog.setCreatedDate( new DateTime() );
        when( agentTaskService.getTaskById( anyString(), anyString() ) ).thenReturn( task );
        when( agentTaskService.getByTaskId( anyString() ) ).thenReturn( agentTask );
        when( agentTaskService.saveAgentTask( any( AgentTask.class ) ) ).thenReturn( agentTask );
        when( agentTaskService.patchAgentTask( anyString(), anyString(), Mockito.any( ( Map.class ) ) ) )
                .thenReturn( task );
        when( agentTaskStatusLogService.save( any() ) ).thenReturn( statusLog );
        agentTaskBusinessServiceImpl.deleteAgentTask( "testAgendId", "testTaskId", apiUser );

        verify( agentTaskService ).saveAgentTask( any( AgentTask.class ) );
        verify( agentTaskService ).patchAgentTask( anyString(), anyString(), Mockito.any( ( Map.class ) ) );
    }

    /**
     * Test unassign tasks should set status unassigned when completed dtm is
     * null.
     */
    @Test
    public void testUnassignTasksShouldSetStatusUnassignedWhenCompletedDtmIsNull() {
        final String opportunityId = "test";
        final String agentId = "test";
        final String key = "test";
        final Map< String, Task > tasks = new HashMap<>();
        final Task task = new Task();
        task.setStatus( INCOMPLETE.name() );
        tasks.put( key, task );
        final PostResponse response = new PostResponse();

        when( agentTaskService.getTasksByOpportunityId( agentId, opportunityId ) ).thenReturn( tasks );
        when( agentTaskService.saveAgentTasks( tasks, agentId ) ).thenReturn( response );

        agentTaskBusinessServiceImpl.unassignTasks( opportunityId, agentId );
        assertEquals( tasks.get( key ).getStatus(), UNASSIGNED.name() );
        verify( agentTaskService ).getTasksByOpportunityId( agentId, opportunityId );
        verify( agentTaskService ).saveAgentTasks( tasks, agentId );
    }

    /**
     * Test unassign tasks should set status unassigned when task status is
     * cancelled and completed dtm is not null.
     */
    @Test
    public void testUnassignTasksShouldSetStatusUnassignedWhenTaskStatusIsCancelledAndCompletedDtmIsNotNull() {
        final String opportunityId = "test";
        final String agentId = "test";
        final String key = "test";
        final Map< String, Task > tasks = new HashMap<>();
        final Task task = new Task();
        task.setStatus( CANCELLED.name() );
        task.setCompletedDtm( 1L );
        tasks.put( key, task );
        final PostResponse response = new PostResponse();

        when( agentTaskService.getTasksByOpportunityId( agentId, opportunityId ) ).thenReturn( tasks );
        when( agentTaskService.saveAgentTasks( tasks, agentId ) ).thenReturn( response );

        agentTaskBusinessServiceImpl.unassignTasks( opportunityId, agentId );
        assertEquals( tasks.get( key ).getStatus(), UNASSIGNED.name() );
        verify( agentTaskService ).getTasksByOpportunityId( agentId, opportunityId );
        verify( agentTaskService ).saveAgentTasks( tasks, agentId );
    }

    /**
     * Test unassign tasks should not set status unassigned when completed dtm
     * is not null.
     */
    @Test
    public void testUnassignTasksShouldNotSetStatusUnassignedWhenCompletedDtmIsNotNull() {
        final String opportunityId = "test";
        final String agentId = "test";
        final String key = "test";
        final Map< String, Task > tasks = new HashMap<>();
        final Task task = new Task();
        task.setStatus( COMPLETED.name() );
        task.setCompletedDtm( 1L );
        tasks.put( key, task );
        final PostResponse response = new PostResponse();

        when( agentTaskService.getTasksByOpportunityId( agentId, opportunityId ) ).thenReturn( tasks );
        when( agentTaskService.saveAgentTasks( tasks, agentId ) ).thenReturn( response );

        agentTaskBusinessServiceImpl.unassignTasks( opportunityId, agentId );
        assertEquals( tasks.get( key ).getStatus(), COMPLETED.name() );
        assertNotEquals( tasks.get( key ).getStatus(), UNASSIGNED.name() );
        verify( agentTaskService ).getTasksByOpportunityId( agentId, opportunityId );
        verify( agentTaskService ).saveAgentTasks( tasks, agentId );
    }

    /**
     * Test copy tasks and requests should save request having associated task.
     */
    @Test
    public void testCopyTasksAndRequestsShouldSaveRequestHavingAssociatedTask() {
        final String oldAgentId = "testOldAgentId";
        final String newAgentId = "testNewAgentId";
        final String oldOpportunityId = "testOldOpportunityId";
        final String newOpportunityId = "testNewOpportunityId";
        final String contactId = "testContactId";
        final String value = "testValue";
        final String requestId = "testRequestId";

        final Map< String, String > newContacts = new HashMap<>();
        newContacts.put( contactId, value );
        final Map< String, Task > oldTasks = new HashMap<>();
        final Task task = new Task();
        task.setStatus( INCOMPLETE.name() );
        task.setRequestId( requestId );
        final Map< String, Reminder > reminders = new HashMap<>();
        final Reminder reminder = new Reminder();
        reminders.put( "reminderKey", reminder );
        task.setReminders( reminders );
        oldTasks.put( "test", task );
        final Map< String, Request > oldRequests = new HashMap<>();
        final Request request = new Request();
        oldRequests.put( requestId, request );
        final com.owners.gravitas.domain.Contact contact = new com.owners.gravitas.domain.Contact();
        final List< String > notificationIds = new ArrayList<>();
        final Contact newContact = new Contact();
        final PostResponse response = new PostResponse();

        when( agentTaskService.getTasksByOpportunityId( oldAgentId, oldOpportunityId ) ).thenReturn( oldTasks );
        when( agentRequestService.getRequestsByOpportunityId( oldAgentId, oldOpportunityId ) )
                .thenReturn( oldRequests );
        when( agentContactService.getContactById( newAgentId, contactId ) ).thenReturn( contact );
        when( contactBuilder.convertTo( contact ) ).thenReturn( newContact );
        when( agentReminderBusinessService.pushReminderNotification( anyString(), anyString(), any( Contact.class ),
                anyLong(), any( Task.class ), any() ) ).thenReturn( notificationIds );
        when( agentTaskService.saveAgentTasks( anyMap(), anyString() ) ).thenReturn( response );
        doNothing().when( agentRequestService ).saveAgentRequests( anyMap(), anyString() );

        agentTaskBusinessServiceImpl.copyTasksAndRequests( oldAgentId, newAgentId, oldOpportunityId, newOpportunityId,
                newContacts );

        verify( agentTaskService ).getTasksByOpportunityId( oldAgentId, oldOpportunityId );
        verify( agentRequestService ).getRequestsByOpportunityId( oldAgentId, oldOpportunityId );
        verify( agentTaskService ).saveAgentTask( any( AgentTask.class ) );
        verify( agentTaskStatusLogService ).save( any( AgentTaskStatusLog.class ) );
        verify( agentContactService ).getContactById( newAgentId, contactId );
        verify( contactBuilder ).convertTo( contact );
        verify( agentReminderBusinessService ).pushReminderNotification( anyString(), anyString(), any( Contact.class ),
                anyLong(), any( Task.class ), any() );
        verify( agentTaskService ).saveAgentTasks( anyMap(), anyString() );
        verify( agentRequestService ).saveAgentRequests( anyMap(), anyString() );
    }

    /**
     * Test copy tasks and requests should save request not having associated
     * task.
     */
    @Test
    public void testCopyTasksAndRequestsShouldSaveRequestNotHavingAssociatedTask() {
        final String oldAgentId = "testOldAgentId";
        final String newAgentId = "testNewAgentId";
        final String oldOpportunityId = "testOldOpportunityId";
        final String newOpportunityId = "testNewOpportunityId";
        final String contactId = "testContactId";
        final String value = "testValue";
        final String requestId = "testRequestId";

        final Map< String, String > newContacts = new HashMap<>();
        newContacts.put( contactId, value );
        final Map< String, Task > oldTasks = new HashMap<>();
        final Task task = new Task();
        task.setStatus( INCOMPLETE.name() );
        task.setRequestId( requestId );
        final Map< String, Reminder > reminders = new HashMap<>();
        final Reminder reminder = new Reminder();
        reminders.put( "reminderKey", reminder );
        task.setReminders( reminders );
        oldTasks.put( "test", task );
        final Map< String, Request > oldRequests = new HashMap<>();
        final Request request = new Request();
        oldRequests.put( "test", request );
        final com.owners.gravitas.domain.Contact contact = new com.owners.gravitas.domain.Contact();
        final List< String > notificationIds = new ArrayList<>();
        final Contact newContact = new Contact();
        final PostResponse response = new PostResponse();

        when( agentTaskService.getTasksByOpportunityId( oldAgentId, oldOpportunityId ) ).thenReturn( oldTasks );
        when( agentRequestService.getRequestsByOpportunityId( oldAgentId, oldOpportunityId ) )
                .thenReturn( oldRequests );
        when( agentContactService.getContactById( newAgentId, contactId ) ).thenReturn( contact );
        when( contactBuilder.convertTo( contact ) ).thenReturn( newContact );
        when( agentReminderBusinessService.pushReminderNotification( anyString(), anyString(), any( Contact.class ),
                anyLong(), any( Task.class ), any() ) ).thenReturn( notificationIds );
        when( agentTaskService.saveAgentTasks( anyMap(), anyString() ) ).thenReturn( response );
        doNothing().when( agentRequestService ).saveAgentRequests( anyMap(), anyString() );

        agentTaskBusinessServiceImpl.copyTasksAndRequests( oldAgentId, newAgentId, oldOpportunityId, newOpportunityId,
                newContacts );

        verify( agentTaskService ).getTasksByOpportunityId( oldAgentId, oldOpportunityId );
        verify( agentRequestService ).getRequestsByOpportunityId( oldAgentId, oldOpportunityId );
        verify( agentTaskService ).saveAgentTask( any( AgentTask.class ) );
        verify( agentTaskStatusLogService ).save( any( AgentTaskStatusLog.class ) );
        verify( agentContactService ).getContactById( newAgentId, contactId );
        verify( contactBuilder ).convertTo( contact );
        verify( agentReminderBusinessService ).pushReminderNotification( anyString(), anyString(), any( Contact.class ),
                anyLong(), any( Task.class ), any() );
        verify( agentTaskService ).saveAgentTasks( anyMap(), anyString() );
        verify( agentRequestService ).saveAgentRequests( anyMap(), anyString() );
    }

    /**
     * Test copy tasks and requests should save request not having associated
     * task.
     */
    @Test
    public void testCopyTasksAndRequestsWhenRemindersAreEmpty() {
        final String oldAgentId = "testOldAgentId";
        final String newAgentId = "testNewAgentId";
        final String oldOpportunityId = "testOldOpportunityId";
        final String newOpportunityId = "testNewOpportunityId";
        final String contactId = "testContactId";
        final String value = "testValue";
        final String requestId = "testRequestId";

        final Map< String, String > newContacts = new HashMap<>();
        newContacts.put( contactId, value );
        final Map< String, Task > oldTasks = new HashMap<>();
        final Task task = new Task();
        task.setStatus( INCOMPLETE.name() );
        task.setRequestId( requestId );
        oldTasks.put( "test", task );
        final Map< String, Request > oldRequests = new HashMap<>();
        final Request request = new Request();
        oldRequests.put( "test", request );
        final com.owners.gravitas.domain.Contact contact = new com.owners.gravitas.domain.Contact();
        final List< String > notificationIds = new ArrayList<>();
        final Contact newContact = new Contact();
        final PostResponse response = new PostResponse();

        when( agentTaskService.getTasksByOpportunityId( oldAgentId, oldOpportunityId ) ).thenReturn( oldTasks );
        when( agentRequestService.getRequestsByOpportunityId( oldAgentId, oldOpportunityId ) )
                .thenReturn( oldRequests );
        when( agentContactService.getContactById( newAgentId, contactId ) ).thenReturn( contact );
        when( contactBuilder.convertTo( contact ) ).thenReturn( newContact );
        when( agentReminderBusinessService.pushReminderNotification( anyString(), anyString(), any( Contact.class ),
                anyLong(), any( Task.class ), any() ) ).thenReturn( notificationIds );
        when( agentTaskService.saveAgentTasks( anyMap(), anyString() ) ).thenReturn( response );
        doNothing().when( agentRequestService ).saveAgentRequests( anyMap(), anyString() );

        agentTaskBusinessServiceImpl.copyTasksAndRequests( oldAgentId, newAgentId, oldOpportunityId, newOpportunityId,
                newContacts );

        verify( agentTaskService ).getTasksByOpportunityId( oldAgentId, oldOpportunityId );
        verify( agentRequestService ).getRequestsByOpportunityId( oldAgentId, oldOpportunityId );
        verify( agentTaskService ).saveAgentTask( any( AgentTask.class ) );
        verify( agentTaskStatusLogService ).save( any( AgentTaskStatusLog.class ) );
        verifyZeroInteractions( agentContactService );
        verifyZeroInteractions( contactBuilder );
        verifyZeroInteractions( agentReminderBusinessService );
        verify( agentTaskService ).saveAgentTasks( anyMap(), anyString() );
        verify( agentRequestService ).saveAgentRequests( anyMap(), anyString() );
    }

    /**
     * Test reassign tasks to same agent when status is not blank.
     */
    @Test
    public void testReassignTasksToSameAgentWhenStatusIsNotBlank() {
        final String agentId = "testAgentId";
        final String opportunityId = "testOppId";
        final String key = "teskKey";

        final Map< String, Task > tasks = new HashMap<>();
        final Task task = new Task();
        task.setStatus( INCOMPLETE.name() );
        tasks.put( key, task );
        final AgentTask agentTask = new AgentTask();
        final AgentTaskStatusLog statusLog = new AgentTaskStatusLog();
        statusLog.setStatus( INCOMPLETE.name() );

        when( agentTaskService.getTasksByOpportunityId( agentId, opportunityId ) ).thenReturn( tasks );
        when( agentTaskService.getByTaskId( key ) ).thenReturn( agentTask );
        when( agentTaskStatusLogService.findTopByAgentTaskAndStatus( agentTask, UNASSIGNED.name() ) )
                .thenReturn( statusLog );
        when( agentTaskStatusLogService.save( any( AgentTaskStatusLog.class ) ) ).thenReturn( statusLog );

        agentTaskBusinessServiceImpl.reassignTasksToSameAgent( agentId, opportunityId );

        verify( agentTaskService ).getTasksByOpportunityId( agentId, opportunityId );
        verify( agentTaskService, times( 2 ) ).getByTaskId( key );
        verify( agentTaskStatusLogService ).findTopByAgentTaskAndStatus( agentTask, UNASSIGNED.name() );
        verify( agentTaskStatusLogService ).save( any( AgentTaskStatusLog.class ) );
        verify( agentTaskService ).saveAgentTask( agentTask );
        verify( agentTaskService ).saveAgentTasks( tasks, agentId );
    }

    /**
     * Test reassign tasks to same agent when status is blank.
     */
    @Test
    public void testReassignTasksToSameAgentWhenStatusIsBlank() {
        final String agentId = "testAgentId";
        final String opportunityId = "testOppId";
        final String key = "teskKey";

        final Map< String, Task > tasks = new HashMap<>();
        final Task task = new Task();
        tasks.put( key, task );
        final AgentTask agentTask = new AgentTask();
        final AgentTaskStatusLog statusLog = new AgentTaskStatusLog();

        when( agentTaskService.getTasksByOpportunityId( agentId, opportunityId ) ).thenReturn( tasks );
        when( agentTaskService.getByTaskId( key ) ).thenReturn( agentTask );
        when( agentTaskStatusLogService.findTopByAgentTaskAndStatus( agentTask, UNASSIGNED.name() ) )
                .thenReturn( statusLog );
        when( agentTaskStatusLogService.save( any( AgentTaskStatusLog.class ) ) ).thenReturn( statusLog );

        agentTaskBusinessServiceImpl.reassignTasksToSameAgent( agentId, opportunityId );

        verify( agentTaskService ).getTasksByOpportunityId( agentId, opportunityId );
        verify( agentTaskService ).getByTaskId( key );
        verify( agentTaskStatusLogService ).findTopByAgentTaskAndStatus( agentTask, UNASSIGNED.name() );
        verifyZeroInteractions( agentTaskStatusLogService );
        verify( agentTaskService, times( 0 ) ).saveAgentTask( agentTask );
        verify( agentTaskService ).saveAgentTasks( tasks, agentId );
    }

    /**
     * Test reassign tasks to same agent when tasks are not available on
     * firebase.
     */
    @Test
    public void testReassignTasksToSameAgentWhenTasksAreNotAvailableOnFirebase() {
        final String agentId = "testAgentId";
        final String opportunityId = "testOppId";
        final String key = "teskKey";
        final Map< String, Task > tasks = new HashMap<>();

        when( agentTaskService.getTasksByOpportunityId( agentId, opportunityId ) ).thenReturn( tasks );

        agentTaskBusinessServiceImpl.reassignTasksToSameAgent( agentId, opportunityId );

        verify( agentTaskService ).getTasksByOpportunityId( agentId, opportunityId );
        verify( agentTaskService, times( 0 ) ).getByTaskId( key );
        verifyZeroInteractions( agentTaskStatusLogService );
        verify( agentTaskService, times( 0 ) ).saveAgentTask( any( AgentTask.class ) );
        verify( agentTaskService ).saveAgentTasks( any(), anyString() );
    }

    /**
     * Test reassign tasks to same agent when status is not blank and reminders
     * are set.
     */
    @Test
    public void testReassignTasksToSameAgentWhenStatusIsNotBlankAndRemindersAreSet() {
        final String agentId = "testAgentId";
        final String opportunityId = "testOppId";
        final String key = "teskKey";

        final Map< String, Task > tasks = new HashMap<>();
        final Task task = new Task();
        final Map< String, Reminder > reminders = new HashMap<>();
        final Reminder reminder = new Reminder();
        reminders.put( "reminder", reminder );
        task.setReminders( reminders );
        task.setStatus( INCOMPLETE.name() );
        tasks.put( key, task );
        final AgentTask agentTask = new AgentTask();
        final AgentTaskStatusLog statusLog = new AgentTaskStatusLog();
        statusLog.setStatus( INCOMPLETE.name() );

        when( agentTaskService.getTasksByOpportunityId( agentId, opportunityId ) ).thenReturn( tasks );
        when( agentTaskService.getByTaskId( key ) ).thenReturn( agentTask );
        when( agentTaskStatusLogService.findTopByAgentTaskAndStatus( agentTask, UNASSIGNED.name() ) )
                .thenReturn( statusLog );
        when( agentTaskStatusLogService.save( any( AgentTaskStatusLog.class ) ) ).thenReturn( statusLog );

        agentTaskBusinessServiceImpl.reassignTasksToSameAgent( agentId, opportunityId );

        verify( agentTaskService ).getTasksByOpportunityId( agentId, opportunityId );
        verify( agentTaskService, times( 2 ) ).getByTaskId( key );
        verify( agentTaskStatusLogService ).findTopByAgentTaskAndStatus( agentTask, UNASSIGNED.name() );
        verify( agentTaskStatusLogService ).save( any( AgentTaskStatusLog.class ) );
        verify( agentTaskService ).saveAgentTask( agentTask );
        verify( agentTaskService ).saveAgentTasks( tasks, agentId );
    }

    /**
     * Test create buyer task should create buyer task.
     */
    @Test
    public void testCreateBuyerTaskShouldCreateBuyerTask() {
        final LeadRequest request = new LeadRequest();
        request.setRequestType( REQUEST_INFORMATION.name() );
        final String agentId = "testAgentId";
        final String opportunityId = "testOppId";
        final String requestId = "testRequestId";
        final String fbTaskId = "fbTaskId";
        final PostResponse expectedResponse = new PostResponse();
        expectedResponse.setName( fbTaskId );
        final Request requestBuyer = new Request();
        final List< List< TourDetails > > dates = new ArrayList<>();
        final List< TourDetails > list = new ArrayList<>();
        final TourDetails td = new TourDetails<>();
        td.setValue( "[2018-06-25T10:12:12.111+05:30]" );
        list.add( td );
        dates.add( list );
        requestBuyer.setDates( dates );
        final com.owners.gravitas.domain.entity.Opportunity opportunityV1 = new com.owners.gravitas.domain.entity.Opportunity();
        final AgentTask agentTask = new AgentTask();
        final AgentTaskStatusLog statusLog = new AgentTaskStatusLog();

        when( agentTaskService.saveAgentTask( any( Task.class ), anyString() ) ).thenReturn( expectedResponse );
        when( opportunityService.getOpportunityByFbId( opportunityId, Boolean.FALSE ) ).thenReturn( opportunityV1 );
        when( agentTaskService.saveAgentTask( any( AgentTask.class ) ) ).thenReturn( agentTask );
        when( agentTaskStatusLogService.save( any( AgentTaskStatusLog.class ) ) ).thenReturn( statusLog );

        final PostResponse actualResponse = agentTaskBusinessServiceImpl.createBuyerTask( request, agentId,
                opportunityId, requestId, requestBuyer );

        assertEquals( actualResponse, expectedResponse );
        verify( agentTaskService ).saveAgentTask( any( Task.class ), anyString() );
        verify( opportunityService ).getOpportunityByFbId( opportunityId, Boolean.FALSE );
        verify( agentTaskService ).saveAgentTask( any( AgentTask.class ) );
        verify( agentTaskStatusLogService ).save( any( AgentTaskStatusLog.class ) );
    }

    /**
     * Test delete tasks should delete tasks when task having completed dtm.
     */
    @Test
    public void testDeleteTasksShouldDeleteTasksWhenTaskHavingCompletedDtm() {
        final String opportunityId = "testOppId";
        final String agentId = "testAgentId";
        final String key = "testKey";
        final Map< String, Task > tasks = new HashMap<>();
        final Task task = new Task();
        task.setCompletedDtm( 1L );
        tasks.put( key, task );
        final AgentTask agentTask = new AgentTask();
        final AgentTaskStatusLog statusLog = new AgentTaskStatusLog();
        final PostResponse expectedResponse = new PostResponse();
        expectedResponse.setName( key );

        when( agentTaskService.getTasksByOpportunityId( agentId, opportunityId ) ).thenReturn( tasks );
        when( agentTaskService.getByTaskId( key ) ).thenReturn( agentTask );
        when( agentTaskStatusLogService.save( any( AgentTaskStatusLog.class ) ) ).thenReturn( statusLog );
        when( agentTaskService.saveAgentTask( agentTask ) ).thenReturn( agentTask );
        doNothing().when( agentReminderBusinessService ).cancelReminders( agentId, key, task.getReminders() );
        when( agentTaskService.saveAgentTasks( tasks, agentId ) ).thenReturn( expectedResponse );

        agentTaskBusinessServiceImpl.deleteTasks( opportunityId, agentId );

        verify( agentTaskService ).getTasksByOpportunityId( agentId, opportunityId );
        verify( agentTaskService, times( 2 ) ).getByTaskId( key );
        verify( agentTaskStatusLogService ).save( any( AgentTaskStatusLog.class ) );
        verify( agentTaskService, times( 2 ) ).saveAgentTask( agentTask );
        verify( agentReminderBusinessService ).cancelReminders( agentId, key, task.getReminders() );
        verify( agentTaskService ).saveAgentTasks( tasks, agentId );
    }

    /**
     * Test delete tasks should delete tasks when task not having completed dtm.
     */
    @Test
    public void testDeleteTasksShouldDeleteTasksWhenTaskNotHavingCompletedDtm() {
        final String opportunityId = "testOppId";
        final String agentId = "testAgentId";
        final String key = "testKey";
        final Map< String, Task > tasks = new HashMap<>();
        final Task task = new Task();
        tasks.put( key, task );
        final AgentTask agentTask = new AgentTask();
        final AgentTaskStatusLog statusLog = new AgentTaskStatusLog();
        final PostResponse expectedResponse = new PostResponse();
        expectedResponse.setName( key );

        when( agentTaskService.getTasksByOpportunityId( agentId, opportunityId ) ).thenReturn( tasks );
        when( agentTaskService.getByTaskId( key ) ).thenReturn( agentTask );
        when( agentTaskStatusLogService.save( any( AgentTaskStatusLog.class ) ) ).thenReturn( statusLog );
        when( agentTaskService.saveAgentTask( agentTask ) ).thenReturn( agentTask );
        doNothing().when( agentReminderBusinessService ).cancelReminders( agentId, key, task.getReminders() );
        when( agentTaskService.saveAgentTasks( tasks, agentId ) ).thenReturn( expectedResponse );

        agentTaskBusinessServiceImpl.deleteTasks( opportunityId, agentId );

        verify( agentTaskService ).getTasksByOpportunityId( agentId, opportunityId );
        verify( agentTaskService, times( 1 ) ).getByTaskId( key );
        verify( agentTaskStatusLogService, times( 0 ) ).save( any( AgentTaskStatusLog.class ) );
        verify( agentTaskService, times( 1 ) ).saveAgentTask( agentTask );
        verify( agentReminderBusinessService ).cancelReminders( agentId, key, task.getReminders() );
        verify( agentTaskService ).saveAgentTasks( tasks, agentId );
    }

    /**
     * Test complete agent task should close agent task.
     */
    @Test
    public void testCompleteAgentTaskShouldCloseAgentTask() {
        final String agentId = "testAgentId";
        final String taskId = "testTaskId";
        final TaskUpdateRequest request = new TaskUpdateRequest();
        request.setStatus( CANCELLED.name() );
        request.setCancellationReason( "reason" );
        final Task task = new Task();

        when( agentTaskService.patchAgentTask( anyString(), anyString(), anyMap() ) ).thenReturn( task );
        doNothing().when( agentReminderBusinessService ).cancelAgentTaskReminders( agentId, taskId );

        final AgentResponse actualResponse = agentTaskBusinessServiceImpl.completeAgentTask( agentId, taskId, request );

        assertEquals( actualResponse.getId(), taskId );
        verify( agentTaskService ).patchAgentTask( anyString(), anyString(), anyMap() );
        verify( agentReminderBusinessService ).cancelAgentTaskReminders( agentId, taskId );

    }

    /**
     * Test complete agent task should close agent task when status is complete.
     */
    @Test
    public void testCompleteAgentTaskShouldCloseAgentTaskWhenStatusIsComplete() {
        final String agentId = "testAgentId";
        final String taskId = "testTaskId";
        final TaskUpdateRequest request = new TaskUpdateRequest();
        request.setStatus( COMPLETE.name() );
        request.setCancellationReason( "reason" );
        final Task task = new Task();

        when( agentTaskService.patchAgentTask( anyString(), anyString(), anyMap() ) ).thenReturn( task );
        doNothing().when( agentReminderBusinessService ).cancelAgentTaskReminders( agentId, taskId );

        final AgentResponse actualResponse = agentTaskBusinessServiceImpl.completeAgentTask( agentId, taskId, request );

        assertEquals( actualResponse.getId(), taskId );
        verify( agentTaskService ).patchAgentTask( anyString(), anyString(), anyMap() );
        verify( agentReminderBusinessService ).cancelAgentTaskReminders( agentId, taskId );

    }

    /**
     * Testget checkin details_search by opportunity id.
     */
    @Test
    public void testgetCheckinDetails_searchByOpportunityId() {
        final String search = "search";
        final String fromDate = "fromDate";
        final String toDate = "toDate";
        final User user = new User();
        final List< User > userList = new ArrayList<>();
        userList.add( user );
        final UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
        userDetailsResponse.setUsers( userList );
        final com.owners.gravitas.domain.entity.Contact contact = new com.owners.gravitas.domain.entity.Contact();
        final com.owners.gravitas.domain.entity.Opportunity opp = new com.owners.gravitas.domain.entity.Opportunity();
        opp.setContact( contact );
        opp.setAssignedAgentId( "agentId" );
        final AgentTask task = new AgentTask();
        task.setOpportunity( opp );
        task.setCreatedDate( new DateTime() );
        final List< AgentTask > list = new ArrayList();
        list.add( task );
        ReflectionTestUtils.setField( agentTaskBusinessServiceImpl, "taskCheckinStatuses", "VALID" );
        ReflectionTestUtils.setField( agentTaskBusinessServiceImpl, "taskCheckinTypes", "SCHEDULE_MEETING" );
        when( userDetailsResponseBuilder.convertTo( Mockito.anyList() ) ).thenReturn( userDetailsResponse );
        when( contactServiceV1.getContactByCrmId( search ) ).thenReturn( contact );
        when( agentTaskService.getAgentTasksByOpportunityId( anyString(), Mockito.any( Date.class ),
                Mockito.any( Date.class ), Mockito.anySet(), Mockito.anySet() ) ).thenReturn( list );
        final CheckinDetailResponse actualResponse = agentTaskBusinessServiceImpl.getCheckinDetails( search, fromDate,
                toDate );
        assertNotNull( actualResponse );
    }

    /**
     * Testget checkin details_search by agent email.
     */
    @Test
    public void testgetCheckinDetails_searchByAgentEmail() {
        final String search = "abc@test.com";
        final String fromDate = "fromDate";
        final String toDate = "toDate";
        final User user = new User();
        final List< User > userList = new ArrayList<>();
        userList.add( user );
        final UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
        userDetailsResponse.setUsers( userList );
        final com.owners.gravitas.domain.entity.Contact contact = new com.owners.gravitas.domain.entity.Contact();
        final com.owners.gravitas.domain.entity.Opportunity opp = new com.owners.gravitas.domain.entity.Opportunity();
        opp.setContact( contact );
        opp.setAssignedAgentId( "agentId" );
        final AgentTask task = new AgentTask();
        task.setOpportunity( opp );
        task.setCreatedDate( new DateTime() );
        final List< AgentTask > list = new ArrayList();
        list.add( task );
        ReflectionTestUtils.setField( agentTaskBusinessServiceImpl, "taskCheckinStatuses", "VALID" );
        ReflectionTestUtils.setField( agentTaskBusinessServiceImpl, "taskCheckinTypes", "SCHEDULE_MEETING" );
        when( userDetailsResponseBuilder.convertTo( Mockito.anyList() ) ).thenReturn( userDetailsResponse );
        when( contactServiceV1.getContactByCrmId( search ) ).thenReturn( contact );
        when( agentTaskService.getTasksByAgentEmail( anyString(), Mockito.any( Date.class ), Mockito.any( Date.class ),
                Mockito.anySet(), Mockito.anySet() ) ).thenReturn( list );
        final CheckinDetailResponse actualResponse = agentTaskBusinessServiceImpl.getCheckinDetails( search, fromDate,
                toDate );
        assertNotNull( actualResponse );
    }

    /**
     * Testget checkin details_search by buyer email.
     */
    @Test
    public void testgetCheckinDetails_searchByBuyerEmail() {
        final String search = "abc@test.com";
        final String fromDate = "fromDate";
        final String toDate = "toDate";
        final User user = new User();
        final List< User > userList = new ArrayList<>();
        userList.add( user );
        final UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
        userDetailsResponse.setUsers( userList );
        final com.owners.gravitas.domain.entity.Contact contact = new com.owners.gravitas.domain.entity.Contact();
        final com.owners.gravitas.domain.entity.Opportunity opp = new com.owners.gravitas.domain.entity.Opportunity();
        opp.setContact( contact );
        contact.addOpportunity( opp );
        opp.setAssignedAgentId( "agentId" );
        final AgentTask task = new AgentTask();
        task.setOpportunity( opp );
        task.setCreatedDate( new DateTime() );
        final List< AgentTask > list = new ArrayList();
        list.add( task );
        ReflectionTestUtils.setField( agentTaskBusinessServiceImpl, "taskCheckinStatuses", "VALID" );
        ReflectionTestUtils.setField( agentTaskBusinessServiceImpl, "taskCheckinTypes", "SCHEDULE_MEETING" );
        when( userDetailsResponseBuilder.convertTo( Mockito.anyList() ) ).thenReturn( userDetailsResponse );
        when( contactServiceV1.getContactByEmailAndType( anyString(), anyString() ) ).thenReturn( contact );
        when( contactServiceV1.getContactByCrmId( search ) ).thenReturn( contact );
        when( agentTaskService.getAgentTasksByOpportunityId( anyString(), Mockito.any( Date.class ),
                Mockito.any( Date.class ), Mockito.anySet(), Mockito.anySet() ) ).thenReturn( list );
        final CheckinDetailResponse actualResponse = agentTaskBusinessServiceImpl.getCheckinDetails( search, fromDate,
                toDate );
        assertNotNull( actualResponse );

    }

    /**
     * Testget checkin details_search empty.
     */
    @Test
    public void testgetCheckinDetails_searchEmpty() {
        final String search = "";
        final String fromDate = "fromDate";
        final String toDate = "toDate";
        final User user = new User();
        final List< User > userList = new ArrayList<>();
        userList.add( user );
        final UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
        userDetailsResponse.setUsers( userList );
        final com.owners.gravitas.domain.entity.Contact contact = new com.owners.gravitas.domain.entity.Contact();
        final com.owners.gravitas.domain.entity.Opportunity opp = new com.owners.gravitas.domain.entity.Opportunity();
        opp.setContact( contact );
        contact.addOpportunity( opp );
        opp.setAssignedAgentId( "agentId" );
        final AgentTask task = new AgentTask();
        task.setOpportunity( opp );
        task.setCreatedDate( new DateTime() );
        final List< AgentTask > list = new ArrayList();
        list.add( task );
        ReflectionTestUtils.setField( agentTaskBusinessServiceImpl, "taskCheckinStatuses", "VALID" );
        ReflectionTestUtils.setField( agentTaskBusinessServiceImpl, "taskCheckinTypes", "SCHEDULE_MEETING" );
        when( userDetailsResponseBuilder.convertTo( Mockito.anyList() ) ).thenReturn( userDetailsResponse );
        when( contactServiceV1.getContactByEmailAndType( anyString(), anyString() ) ).thenReturn( contact );
        when( contactServiceV1.getContactByCrmId( search ) ).thenReturn( contact );
        when( agentTaskService.getAgentTasksByOpportunityId( anyString(), Mockito.any( Date.class ),
                Mockito.any( Date.class ), Mockito.anySet(), Mockito.anySet() ) ).thenReturn( list );
        final CheckinDetailResponse actualResponse = agentTaskBusinessServiceImpl.getCheckinDetails( search, fromDate,
                toDate );
        assertNotNull( actualResponse );
    }

    /**
     * Testget questionnaire.
     */
    @Test
    public void testgetQuestionnaire() {
        final String type = VALID_CHECKIN_QUESTIONNAIRE.name();
        final RefType refType = new RefType();
        final Set< RefCode > codeSet = new HashSet();
        final RefCode code = new RefCode();
        codeSet.add( code );
        when( refTypeService.getRefTypeByType( anyString() ) ).thenReturn( refType );
        when( refCodeService.findRefCodeByRefType( refType ) ).thenReturn( codeSet );
        final Set< String > response = agentTaskBusinessServiceImpl.getQuestionnaire( type );
        assertNotNull( response );
    }

    /**
     * Testanswer questionnaire.
     */
    @Test
    public void testanswerQuestionnaire() {
        final Map< String, Object > request = new HashMap< String, Object >();
        request.put( "objectType", "task" );
        request.put( "type", VALID_CHECKIN_QUESTIONNAIRE.name() );
        final com.owners.gravitas.domain.entity.Opportunity opp = new com.owners.gravitas.domain.entity.Opportunity();
        opp.setAssignedAgentId( "agentId" );
        final AgentTask task = new AgentTask();
        task.setOpportunity( opp );
        when( agentTaskService.getByTaskId( anyString() ) ).thenReturn( task );
        final BaseResponse response = agentTaskBusinessServiceImpl.answerQuestionnaire( request );
        assertNotNull( response );
    }

    @Test
    public void testupdateScheduledTasksByOpportunityId() {
        final AgentTaskStatusLog agentTaskStatusLog = new AgentTaskStatusLog();
        final Task task = new Task();
        final AgentTask agentTask = new AgentTask();
        agentTask.setType( "SCHEDULE_MEETING" );
        agentTask.setCreatedBy( "Inside Sales" );
        agentTask.setStatus( "PENDING" );
        task.setCoShoppingId( "dummyCoShoppingId" );
        agentTaskStatusLog.setStatus( "confirmed" );
        agentTaskStatusLog.setCreatedDate( new DateTime() );
        when( agentTaskService.getAgentTasksByFbId( anyString(), any(), any() ) )
                .thenReturn( Arrays.asList( agentTask ) );
        when( agentTaskService.patchAgentTask( anyString(), anyString(), anyMapOf( String.class, Object.class ) ) )
                .thenReturn( task );
        when( agentTaskStatusLogService.save( any( AgentTaskStatusLog.class ) ) ).thenReturn( agentTaskStatusLog );
        agentTaskBusinessServiceImpl.updateScheduledTasksByOpportunityId( "test", "test" );
        verify( agentTaskStatusLogService, atLeastOnce() ).save( any() );
        verify( agentTaskService, atLeastOnce() ).saveAgentTask( any( AgentTask.class ) );
        verify( agentTaskService, atLeastOnce() ).patchAgentTask( anyString(), anyString(), any() );
    }

    /**
     * Test to check if any meeting is schedule for given agent and opportunity
     * by inside sales
     */
    @Test
    public void testCheckIfAgentEventExists_CreatedByInsideSales() {
        final String key = "test";
        final Map< String, Task > tasks = new HashMap<>();
        final Task task = new Task();
        task.setStatus( INCOMPLETE.name() );
        task.setCreatedBy( "Inside Sales" );
        tasks.put( key, task );
        final Search search = new Search();
        search.setAgentId( "123asda" );
        search.setOpportunityId( "-Lasdasds" );
        when( searchService.searchByCrmOpportunityId( anyString() ) ).thenReturn( search );
        when( agentTaskService.getTasksByOpportunityId( search.getAgentId(), search.getOpportunityId() ) )
                .thenReturn( tasks );
        final CheckScheduleMeetingValidationResponse response = agentTaskBusinessServiceImpl
                .checkIfAgentMeetingEventExists( "test", "test" );
        assertEquals( response.getStatus(), SUCCESS );
        assertFalse( response.isValidationsPassed() );
    }

    /**
     * Test to check if any meeting is schedule for given agent and opportunity
     * created by Agent
     */
    @Test
    public void testCheckIfAgentEventExists_CreatedByAgent() {
        final String key = "test";
        final Map< String, Task > tasks = new HashMap<>();
        final Task task = new Task();
        task.setStatus( INCOMPLETE.name() );
        task.setCreatedBy( "testagent1@ownerstest.com" );
        tasks.put( key, task );
        final Search search = new Search();
        search.setAgentId( "123asda" );
        search.setOpportunityId( "-Lasdasds" );
        when( searchService.searchByCrmOpportunityId( anyString() ) ).thenReturn( search );
        when( agentTaskService.getTasksByOpportunityId( search.getAgentId(), search.getOpportunityId() ) )
                .thenReturn( tasks );
        final CheckScheduleMeetingValidationResponse response = agentTaskBusinessServiceImpl
                .checkIfAgentMeetingEventExists( "test", "test" );
        assertEquals( response.getStatus(), SUCCESS );
        assertTrue( response.isValidationsPassed() );
    }

    /**
     * Test to check if any meeting is schedule for given agent and opportunity
     * created by inside sales but not active
     */
    @Test
    public void testCheckIfAgentEventExists_CreatedByInsideSalesNotActive() {
        final String key = "test";
        final Map< String, Task > tasks = new HashMap<>();
        final Task task = new Task();
        task.setStatus( INCOMPLETE.name() );
        task.setCreatedBy( "Inside Sales" );
        task.setDeleted( true );
        tasks.put( key, task );
        final Search search = new Search();
        search.setAgentId( "123asda" );
        search.setOpportunityId( "-Lasdasds" );
        when( searchService.searchByCrmOpportunityId( anyString() ) ).thenReturn( search );
        when( agentTaskService.getTasksByOpportunityId( search.getAgentId(), search.getOpportunityId() ) )
                .thenReturn( tasks );
        final CheckScheduleMeetingValidationResponse response = agentTaskBusinessServiceImpl
                .checkIfAgentMeetingEventExists( "test", "test" );
        assertEquals( response.getStatus(), SUCCESS );
        assertTrue( response.isValidationsPassed() );
    }

    /**
     * Test to check if any meeting is schedule for given agent and opportunity
     * with multiple tasks by inside sales
     */
    @Test
    public void testCheckIfAgentEventExists_MultipleCreatedByInsideSales() {
        final Map< String, Task > tasks = new HashMap<>();
        final Task task1 = new Task();
        task1.setStatus( CANCELLED.name() );
        task1.setCreatedBy( "Inside Sales" );
        task1.setDeleted( true );

        final Task task2 = new Task();
        task2.setStatus( INCOMPLETE.name() );
        task2.setCreatedBy( "Inside Sales" );
        task2.setDeleted( false );

        final Task task3 = new Task();
        task3.setStatus( COMPLETED.name() );
        task3.setCreatedBy( "Inside Sales" );
        task3.setDeleted( true );

        final Task task4 = new Task();
        task4.setStatus( COMPLETED.name() );
        task4.setCreatedBy( "Inside Sales" );
        task4.setDeleted( true );

        tasks.put( "task1", task1 );
        tasks.put( "task2", task2 );
        tasks.put( "task3", task3 );
        tasks.put( "task4", task4 );
        final Search search = new Search();
        search.setAgentId( "123asda" );
        search.setOpportunityId( "-Lasdasds" );
        when( searchService.searchByCrmOpportunityId( anyString() ) ).thenReturn( search );
        when( agentTaskService.getTasksByOpportunityId( search.getAgentId(), search.getOpportunityId() ) )
                .thenReturn( tasks );
        final CheckScheduleMeetingValidationResponse response = agentTaskBusinessServiceImpl
                .checkIfAgentMeetingEventExists( "test", "test" );
        assertEquals( response.getStatus(), SUCCESS );
        assertFalse( response.isValidationsPassed() );
    }

}
