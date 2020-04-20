package com.owners.gravitas.business.impl;

import static com.owners.gravitas.dto.response.BaseResponse.Status.SUCCESS;
import static com.owners.gravitas.enums.CRMObject.AGENT;
import static com.owners.gravitas.enums.PushNotificationType.ON_DUTY_AGENT;
import static com.owners.gravitas.enums.RecordType.OWNERS_COM;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.joda.time.DateTime;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.hubzu.notification.dto.client.email.EmailNotification;
import com.hubzu.notification.dto.client.push.AppType;
import com.owners.gravitas.amqp.AgentSource;
import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.ActionLogBusinessService;
import com.owners.gravitas.business.AgentEmailBusinessService;
import com.owners.gravitas.business.AgentNotificationBusinessService;
import com.owners.gravitas.business.AgentTaskBusinessService;
import com.owners.gravitas.business.AuditTrailBusinessService;
import com.owners.gravitas.business.OpportunityBusinessService;
import com.owners.gravitas.business.ProcessBusinessService;
import com.owners.gravitas.business.UserBusinessService;
import com.owners.gravitas.business.builder.ActionLogBuilder;
import com.owners.gravitas.business.builder.AgentDetailsBuilder;
import com.owners.gravitas.business.builder.AgentOnboardEmailNotificationBuilder;
import com.owners.gravitas.business.builder.AgentOnboardRequestBuilder;
import com.owners.gravitas.business.builder.AgentSourceResponseBuilder;
import com.owners.gravitas.business.builder.CRMAgentBuilder;
import com.owners.gravitas.business.builder.CRMContactBuilder;
import com.owners.gravitas.business.builder.EscrowEmailNotificationBuilder;
import com.owners.gravitas.business.builder.GmailMessageBuilder;
import com.owners.gravitas.business.builder.OpportunityContactBuilder;
import com.owners.gravitas.business.builder.OpportunitySourceBuilder;
import com.owners.gravitas.business.builder.PendingSalePTSEmailNotificationBuilder;
import com.owners.gravitas.business.builder.PreliminaryTitleReportsEmailNotificationBuilder;
import com.owners.gravitas.business.builder.PushNotificationBuilder;
import com.owners.gravitas.business.builder.SoldStagePTSEmailNotificationBuilder;
import com.owners.gravitas.business.builder.UserBuilder;
import com.owners.gravitas.business.builder.domain.AgentBuilder;
import com.owners.gravitas.business.builder.domain.GoogleUserBuilder;
import com.owners.gravitas.business.builder.domain.RequestBuilder;
import com.owners.gravitas.business.builder.domain.SearchBuilder;
import com.owners.gravitas.business.builder.request.CRMAgentRequestBuilder;
import com.owners.gravitas.business.builder.request.ErrorSlackRequestBuilder;
import com.owners.gravitas.business.builder.response.ActionLogResponseBuilder;
import com.owners.gravitas.business.task.AuditLogTask;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.AgentEmailJmxConfig;
import com.owners.gravitas.config.AgentTopicJmxConfig;
import com.owners.gravitas.config.BadgeCounterJmxConfig;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.domain.Agent;
import com.owners.gravitas.domain.AgentHolder;
import com.owners.gravitas.domain.AgentInfo;
import com.owners.gravitas.domain.Device;
import com.owners.gravitas.domain.LastViewed;
import com.owners.gravitas.domain.Note;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.domain.OpportunityDataNode;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.domain.entity.Action;
import com.owners.gravitas.domain.entity.ActionLog;
import com.owners.gravitas.domain.entity.AgentAvailabilityLog;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.domain.entity.OpportunityAction;
import com.owners.gravitas.domain.entity.Process;
import com.owners.gravitas.domain.entity.Role;
import com.owners.gravitas.domain.entity.RoleMember;
import com.owners.gravitas.domain.entity.User;
import com.owners.gravitas.dto.ActionLogDto;
import com.owners.gravitas.dto.SlackError;
import com.owners.gravitas.dto.crm.response.CRMAgentResponse;
import com.owners.gravitas.dto.crm.response.CRMResponse;
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
import com.owners.gravitas.dto.response.AgentDetailsResponse;
import com.owners.gravitas.dto.response.AgentEmailResponse;
import com.owners.gravitas.dto.response.AgentEmailResult;
import com.owners.gravitas.dto.response.AgentResponse;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.enums.ActionEntity;
import com.owners.gravitas.enums.GravitasProcess;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.exception.ResultNotFoundException;
import com.owners.gravitas.handler.OpportunityChangeHandlerFactory;
import com.owners.gravitas.service.ActionGroupService;
import com.owners.gravitas.service.ActivitiService;
import com.owners.gravitas.service.AgentAvailabilityLogService;
import com.owners.gravitas.service.AgentContactService;
import com.owners.gravitas.service.AgentDetailsService;
import com.owners.gravitas.service.AgentInfoService;
import com.owners.gravitas.service.AgentNoteService;
import com.owners.gravitas.service.AgentOpportunityService;
import com.owners.gravitas.service.AgentPreferenceService;
import com.owners.gravitas.service.AgentRequestService;
import com.owners.gravitas.service.AgentService;
import com.owners.gravitas.service.AgentSuggestedPropertyService;
import com.owners.gravitas.service.AgentTaskService;
import com.owners.gravitas.service.CRMQueryService;
import com.owners.gravitas.service.ContactService;
import com.owners.gravitas.service.GmailService;
import com.owners.gravitas.service.MailService;
import com.owners.gravitas.service.OpportunityDataNodeService;
import com.owners.gravitas.service.OpportunityService;
import com.owners.gravitas.service.PushNotificationService;
import com.owners.gravitas.service.RecordTypeService;
import com.owners.gravitas.service.RoleMemberService;
import com.owners.gravitas.service.SearchService;
import com.owners.gravitas.service.SlackService;
import com.owners.gravitas.service.UserService;
import com.owners.gravitas.util.GravitasWebUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class AgentBusinessServiceImplTest.
 *
 * @author vishwanathm
 */
public class AgentBusinessServiceImplTest extends AbstractBaseMockitoTest {

    /** The agent business service impl. */
    @InjectMocks
    private AgentBusinessServiceImpl agentBusinessServiceImpl;

    /** The agent suggested property service. */
    @Mock
    private AgentSuggestedPropertyService agentSuggestedPropertyService;

    /** The agent service. */
    @Mock
    private AgentService agentService;

    /** The agent note service. */
    @Mock
    private AgentNoteService agentNoteService;

    /** The opportunity service. */
    @Mock
    private OpportunityService opportunityService;

    /** The opportunity business service. */
    @Mock
    private OpportunityBusinessService opportunityBusinessService;

    /** The agent info service. */
    @Mock
    private AgentInfoService agentInfoService;

    /** The agent builder. */
    @Mock
    private AgentBuilder agentBuilder;

    /** The search holder builder. */
    @Mock
    private SearchBuilder contactSearchBuilder;

    /** The search service. */
    @Mock
    private SearchService searchService;

    /** The agent opportunity service. */
    @Mock
    private AgentOpportunityService agentOpportunityService;

    /** The request builder. */
    @Mock
    private RequestBuilder requestBuilder;

    /** The agent contact service. */
    @Mock
    private AgentContactService agentContactService;

    /** The request service. */
    @Mock
    private AgentRequestService requestService;

    /** The push notification builder. */
    @Mock
    private PushNotificationBuilder pushNotificationBuilder;

    /** The push notification service. */
    @Mock
    private PushNotificationService pushNotificationService;
    /** The opportunity source builder. */
    @Mock
    private OpportunitySourceBuilder opportunitySourceBuilder;

    /** The opportunity contact builder. */
    @Mock
    private OpportunityContactBuilder opportunityContactBuilder;

    /** The opportunity change handler factory. */
    @Mock
    private OpportunityChangeHandlerFactory opportunityChangeHandlerFactory;

    /** The contact service. */
    @Mock
    private ContactService contactService;

    /** The crm contact builder. */
    @Mock
    private CRMContactBuilder crmContactBuilder;

    /** The agent push notification business service. */
    @Mock
    private AgentNotificationBusinessService agentPushNotificationBusinessService;

    /** The agent task service. */
    @Mock
    private AgentTaskService agentTaskService;

    /** The agent opportunity business service impl. */
    @Mock
    private AgentOpportunityBusinessServiceImpl agentOpportunityBusinessServiceImpl;

    /** The action log builder. */
    @Mock
    private ActionLogBuilder actionLogBuilder;

    /** The action log business service. */
    @Mock
    private ActionLogBusinessService actionLogBusinessService;

    /** The gravitas web util. */
    @Mock
    private GravitasWebUtil gravitasWebUtil;

    /** The action log response builder. */
    @Mock
    private ActionLogResponseBuilder actionLogResponseBuilder;

    /** The error slack request builder. */
    @Mock
    private ErrorSlackRequestBuilder errorSlackRequestBuilder;

    /** the slack service. */
    @Mock
    private SlackService slackService;

    /** The agent onboard email notification builder. */
    @Mock
    private AgentOnboardEmailNotificationBuilder agentOnboardEmailNotificationBuilder;

    /** The mail service. */
    @Mock
    private MailService mailService;

    /** The user service. */
    @Mock
    private UserService userService;

    /** The agent details service. */
    @Mock
    private AgentDetailsService agentDetailsService;

    /** The user builder. */
    @Mock
    private GoogleUserBuilder googleUserBuilder;

    /** The crm agent request builder. */
    @Mock
    private CRMAgentRequestBuilder crmAgentRequestBuilder;

    /** The agent user builder. */
    @Mock
    private UserBuilder userBuilder;

    /** The agent details builder. */
    @Mock
    private AgentDetailsBuilder agentDetailsBuilder;

    /** The user business service. */
    @Mock
    private UserBusinessService userBusinessService;

    /** The gmail message builder. */
    @Mock
    private GmailMessageBuilder gmailMessageBuilder;

    /** The gmail service. */
    @Mock
    private GmailService gmailService;

    /** The role member service. */
    @Mock
    private RoleMemberService roleMemberService;

    /** The crm service. */
    @Mock
    private CRMQueryService crmQueryService;

    /** The escrow email notification builder. */
    @Mock
    private EscrowEmailNotificationBuilder escrowEmailNotificationBuilder;

    /** The preliminary title reports email notification builder. */
    @Mock
    private PreliminaryTitleReportsEmailNotificationBuilder preliminaryTitleReportsEmailNotificationBuilder;

    /** The audit trail business service. */
    @Mock
    private AuditTrailBusinessService auditTrailBusinessService;

    /** The pending sale PTS email notification builder. */
    @Mock
    private PendingSalePTSEmailNotificationBuilder pendingSalePTSEmailNotificationBuilder;

    /** The sold stage PTS email notification builder. */
    @Mock
    private SoldStagePTSEmailNotificationBuilder soldStagePTSEmailNotificationBuilder;

    /** The gmail. */
    @Mock
    private Gmail gmail;

    /** The agent email business service. */
    @Mock
    private AgentEmailBusinessService agentEmailBusinessService;

    @Mock
    private AgentTaskBusinessService agentTaskBusinessService;

    /** The agent email jmx config. */
    @Mock
    private AgentEmailJmxConfig agentEmailJmxConfig;

    /** The record type service. */
    @Mock
    private RecordTypeService recordTypeService;

    /** The agent onboard request builder. */
    @Mock
    private AgentOnboardRequestBuilder agentOnboardRequestBuilder;

    /** The agent topic jmx config. */
    @Mock
    private AgentTopicJmxConfig agentTopicJmxConfig;

    /** The crm agent builder. */
    @Mock
    private CRMAgentBuilder crmAgentBuilder;

    /** The agent source response builder. */
    @Mock
    private AgentSourceResponseBuilder agentSourceResponseBuilder;

    /** The action group service. */
    @Mock
    private ActionGroupService actionGroupService;

    /** The action group service. */
    @Mock
    private AuditLogTask auditLogTask;

    /** The agent availability log service. */
    @Mock
    private AgentAvailabilityLogService agentAvailabilityLogService;

    /** The runtime service. */
    @Mock
    private RuntimeService runtimeService;

    @Mock
    private ActivitiService activitiService;

    /** The process instance. */
    @Mock
    private ProcessInstance processInstance;

    /** The process business service. */
    @Mock
    private ProcessBusinessService processBusinessService;

    /** The agent notification business service. */
    @Mock
    private AgentNotificationBusinessService agentNotificationBusinessService;

    @Mock
    private AgentPreferenceService agentPreferenceService;

    /** The badge counter jmx config. */
    @Mock
    private BadgeCounterJmxConfig badgeCounterJmxConfig;

    @Mock
    private OpportunityDataNodeService opportunityDataNodeService;

    /**
     * Test register.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testRegister() throws IOException {
        final String agentId = "test";
        final String agentEmail = "test@test.com";
        final Search search = new Search();
        when( searchService.searchByAgentEmail( Mockito.anyString() ) ).thenReturn( search );
        when( contactSearchBuilder.convertTo( Mockito.any( AgentHolder.class ) ) )
                .thenReturn( new HashMap< String, Search >() );
        final AgentDetailsResponse userDetailsResponse = new AgentDetailsResponse();
        when( userBusinessService.getUserDetails( agentEmail ) ).thenReturn( userDetailsResponse );
        final AgentResponse response = agentBusinessServiceImpl.register( agentId, agentEmail );
        Assert.assertEquals( response.getId(), agentId );
        Assert.assertEquals( response.getStatus(), Status.SUCCESS );
    }

    /**
     * Test register.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testRegister2() throws IOException {
        final String agentId = "test";
        final String agentEmail = "test@test.com";
        final Search search = new Search();
        when( searchService.searchByAgentEmail( Mockito.anyString() ) ).thenReturn( search );
        when( contactSearchBuilder.convertTo( Mockito.any( AgentHolder.class ) ) )
                .thenReturn( new HashMap< String, Search >() );
        final AgentDetailsResponse userDetailsResponse = new AgentDetailsResponse();
        final com.owners.gravitas.dto.Agent details = new com.owners.gravitas.dto.Agent();
        userDetailsResponse.setDetails( details );
        when( userBusinessService.getUserDetails( agentEmail ) ).thenReturn( userDetailsResponse );
        final AgentResponse response = agentBusinessServiceImpl.register( agentId, agentEmail );
        Assert.assertEquals( response.getId(), agentId );
        Assert.assertEquals( response.getStatus(), Status.SUCCESS );
    }

    /**
     * Test register.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testRegister3() throws IOException {
        final String agentId = "test";
        final String agentEmail = "test@test.com";
        final Search search = new Search();
        when( searchService.searchByAgentEmail( Mockito.anyString() ) ).thenReturn( search );
        when( contactSearchBuilder.convertTo( Mockito.any( AgentHolder.class ) ) )
                .thenReturn( new HashMap< String, Search >() );
        when( userBusinessService.getUserDetails( agentEmail ) ).thenThrow( IOException.class );
        // when( agentService.getCrmAgentDetails( agentEmail ) ).thenReturn(
        // "11111" );
        final AgentResponse response = agentBusinessServiceImpl.register( agentId, agentEmail );
        Assert.assertEquals( response.getId(), agentId );
        Assert.assertEquals( response.getStatus(), Status.SUCCESS );
    }

    /**
     * Test register should rollback registration.
     *
     * @throws Exception
     *             the exception
     */
    @Test( expectedExceptions = Exception.class )
    public void testRegisterShouldRollbackRegistration() throws Exception {
        final String agentId = "test";
        final String agentEmail = "test@test.com";
        final Search search = new Search();
        final Map< String, Search > searchMap = new HashMap< String, Search >();
        searchMap.put( "key", new Search() );
        when( searchService.searchByAgentEmail( Mockito.anyString() ) ).thenReturn( search );
        when( contactSearchBuilder.convertTo( Mockito.any( AgentHolder.class ) ) ).thenReturn( searchMap );
        Mockito.doThrow( Exception.class ).when( searchService ).saveAll( Mockito.anyMap() );
        final AgentDetailsResponse userDetailsResponse = new AgentDetailsResponse();
        when( userBusinessService.getUserDetails( agentEmail ) ).thenReturn( userDetailsResponse );
        final AgentResponse response = agentBusinessServiceImpl.register( agentId, agentEmail );
        Assert.assertEquals( response.getId(), agentId );
        Assert.assertEquals( response.getStatus(), Status.SUCCESS );
    }

    /**
     * should not save agent if it's found in search.
     */
    @Test
    public void shouldNotSaveAgent_IfItsAlreadyPresent() {
        final String agentId = "test";
        final String agentEmail = "test@test.com";
        final CRMResponse crmResponse = new CRMResponse();
        final AgentHolder agentHolder = new AgentHolder();
        final Agent agent = new Agent();
        final AgentInfo agentInfo = new AgentInfo();
        agentHolder.setAgent( agent );
        agentHolder.getAgent().setInfo( agentInfo );
        when( searchService.searchByAgentId( Mockito.anyString() ) ).thenReturn( new Search() );
        when( opportunityBusinessService.getAgentOpportunities( Mockito.anyString() ) ).thenReturn( crmResponse );
        when( contactSearchBuilder.convertTo( Mockito.any( AgentHolder.class ) ) )
                .thenReturn( new HashMap< String, Search >() );
        final AgentResponse response = agentBusinessServiceImpl.register( agentId, agentEmail );
        Assert.assertEquals( response.getId(), agentId );
        Assert.assertEquals( response.getStatus(), Status.SUCCESS );
        verifyZeroInteractions( agentBuilder );
        verifyZeroInteractions( agentService );
        verifyZeroInteractions( gravitasWebUtil );
        verifyZeroInteractions( actionLogBusinessService );
        verify( searchService ).searchByAgentId( anyString() );
    }

    /**
     * should rollback the operation if an exception occurs while saving a new
     * agent.
     */
    @Test( expectedExceptions = Exception.class )
    public void shouldRollBackIf_ExceptionIsEncountered_WhileSavingAnAgent() {
        final String agentId = "test";
        final String agentEmail = "test@test.com";
        final CRMResponse crmResponse = new CRMResponse();
        final AgentHolder agentHolder = new AgentHolder();
        final Agent agent = new Agent();
        final AgentInfo agentInfo = new AgentInfo();
        agentHolder.setAgent( agent );
        agentHolder.getAgent().setInfo( agentInfo );
        when( searchService.searchByAgentId( Mockito.anyString() ) ).thenReturn( null );
        when( opportunityBusinessService.getAgentOpportunities( Mockito.anyString() ) ).thenReturn( crmResponse );
        when( agentBuilder.convertTo( Mockito.any( List.class ) ) ).thenReturn( agentHolder );
        when( contactSearchBuilder.convertTo( Mockito.any( AgentHolder.class ) ) )
                .thenReturn( new HashMap< String, Search >() );
        when( agentBuilder.convertTo( Mockito.any(), Mockito.any() ) ).thenReturn( agentHolder );
        doThrow( Exception.class ).when( agentService ).saveAgent( agentHolder );
        doNothing().when( agentService ).deleteAgent( anyString() );
        Mockito.doNothing().when( actionLogBusinessService ).auditForAction( Mockito.any( ActionLogDto.class ) );
        doNothing().when( searchService ).saveAll( anyMap() );
        agentBusinessServiceImpl.register( agentId, agentEmail );
    }

    /**
     * should do nothing if rollback operation fails.
     */
    @Test( expectedExceptions = Exception.class )
    public void should_DoNothing_IfExceptionIsEncountered_WhileRollingBack_AnAgent() {
        final String agentId = "test";
        final String agentEmail = "test@test.com";
        when( searchService.searchByAgentId( Mockito.anyString() ) ).thenReturn( null );
        when( contactSearchBuilder.convertTo( Mockito.any( AgentHolder.class ) ) )
                .thenReturn( new HashMap< String, Search >() );
        doThrow( Exception.class ).when( agentService ).saveAgent( Mockito.any( AgentHolder.class ) );
        doThrow( Exception.class ).when( agentService ).deleteAgent( anyString() );
        Mockito.doNothing().when( actionLogBusinessService ).auditForAction( Mockito.any( ActionLogDto.class ) );
        doNothing().when( searchService ).saveAll( anyMap() );
        agentBusinessServiceImpl.register( agentId, agentEmail );
    }

    /**
     * Should do nothing if exception is encountered in rolling back an agent.
     */
    @Test( expectedExceptions = Exception.class )
    public void should_DoNothing_IfExceptionIsEncountered_InRollingBack_AnAgent() {
        final String agentId = "test";
        final String agentEmail = "test@test.com";
        final CRMResponse crmResponse = new CRMResponse();
        final AgentHolder agentHolder = new AgentHolder();
        final Agent agent = new Agent();
        final AgentInfo agentInfo = new AgentInfo();
        agentHolder.setAgent( agent );
        agentHolder.getAgent().setInfo( agentInfo );
        when( searchService.searchByAgentId( Mockito.anyString() ) ).thenReturn( null );
        when( opportunityBusinessService.getAgentOpportunities( Mockito.anyString() ) ).thenReturn( crmResponse );
        when( agentBuilder.convertTo( Mockito.any( List.class ) ) ).thenReturn( agentHolder );
        when( contactSearchBuilder.convertTo( Mockito.any( AgentHolder.class ) ) )
                .thenReturn( new HashMap< String, Search >() );
        when( agentBuilder.convertTo( Mockito.any(), Mockito.any() ) ).thenReturn( agentHolder );
        doThrow( Exception.class ).when( agentService ).saveAgent( agentHolder );
        doNothing().when( agentService ).deleteAgent( anyString() );
        Mockito.doNothing().when( actionLogBusinessService ).auditForAction( Mockito.any( ActionLogDto.class ) );
        doThrow( Exception.class ).when( searchService ).saveAll( anyMap() );
        agentBusinessServiceImpl.register( agentId, agentEmail );
    }

    /**
     * Test add agent opportunity note.
     */
    @Test
    public void testAddAgentNote() {
        AgentResponse response = null;
        final String agentId = "test";
        final String opportunityId = "test";
        final AgentNoteRequest request = new AgentNoteRequest();
        request.setDetails( "testData" );
        when( agentNoteService.saveAgentNote( Mockito.any( Note.class ), Mockito.anyString() ) )
                .thenReturn( new PostResponse() );
        when( agentOpportunityService.getOpportunityById( Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( new Opportunity() );
        when( gravitasWebUtil.getAppUserEmail() ).thenReturn( "" );
        Mockito.doNothing().when( actionLogBusinessService ).auditForAction( Mockito.any( ActionLogDto.class ) );
        response = agentBusinessServiceImpl.addAgentNote( agentId, opportunityId, request );
        Assert.assertEquals( response.getStatus(), Status.SUCCESS );
    }

    /**
     * Should not save agent note if opportunity is not found.
     */
    @Test
    public void shouldNotSaveAgentNoteIfOpportunityNotFound() {
        final String agentId = "test";
        final String opportunityId = "test";
        final AgentNoteRequest request = new AgentNoteRequest();
        request.setDetails( "testData" );
        when( agentOpportunityService.getOpportunityById( Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( null );
        final AgentResponse response = agentBusinessServiceImpl.addAgentNote( agentId, opportunityId, request );
        Assert.assertNull( response );
        verifyZeroInteractions( agentNoteService );
        verifyZeroInteractions( actionLogBusinessService );
        verifyZeroInteractions( gravitasWebUtil );
    }

    /**
     * Test update agent note.
     */
    @Test
    public void testUpdateAgentNote() {
        final String agentId = "agentId";
        final String noteId = "noteId";
        final AgentNoteRequest request = new AgentNoteRequest();
        request.setDetails( "testData" );
        final Note note = new Note();
        note.setDeleted( false );
        when( agentNoteService.getAgentNote( agentId, noteId ) ).thenReturn( note );
        when( agentNoteService.updateAgentNote( Mockito.any( Note.class ), Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( new PostResponse() );
        final AgentResponse response = agentBusinessServiceImpl.updateAgentNote( agentId, noteId, request );
        assertEquals( response.getStatus(), Status.SUCCESS );
        assertEquals( response.getId(), noteId );
        verify( agentNoteService ).getAgentNote( agentId, noteId );
        verify( agentNoteService ).updateAgentNote( Mockito.any( Note.class ), Mockito.anyString(),
                Mockito.anyString() );
    }

    /**
     * Test update agent note_deleted note.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testUpdateAgentNote_deletedNote() {
        final AgentNoteRequest request = new AgentNoteRequest();
        request.setDetails( "testData" );
        final Note note = new Note();
        note.setDeleted( true );
        when( agentNoteService.getAgentNote( Mockito.anyString(), Mockito.anyString() ) ).thenReturn( note );
        when( agentNoteService.updateAgentNote( Mockito.any( Note.class ), Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( new PostResponse() );
        agentBusinessServiceImpl.updateAgentNote( "test", "noteId", request );
    }

    /**
     * Test delete agent note.
     */
    @Test
    public void testDeleteAgentNote() {
        final AgentNoteRequest request = new AgentNoteRequest();
        request.setDetails( "testData" );
        when( agentNoteService.getAgentNote( Mockito.anyString(), Mockito.anyString() ) ).thenReturn( new Note() );
        when( agentNoteService.updateAgentNote( Mockito.any( Note.class ), Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( new PostResponse() );
        final AgentResponse response = agentBusinessServiceImpl.deleteAgentNote( "test", "noteId" );
        Assert.assertEquals( response.getStatus(), Status.SUCCESS );
    }

    /**
     * Test add device.
     */
    @Test
    public void testAddDevice() {
        AgentResponse response = null;
        final String agentId = "test";
        final AgentDeviceRequest request = new AgentDeviceRequest();
        request.setDeviceId( "testId" );
        request.setDeviceType( AppType.IOS );
        when( agentInfoService.getAgentInfo( Mockito.anyString() ) ).thenReturn( new AgentInfo() );
        when( badgeCounterJmxConfig.isBadgeCountEnabled() ).thenReturn( true );
        response = agentBusinessServiceImpl.addDevice( agentId, request );
        when( agentOpportunityService.getAgentNewOpportunitiesCount( agentId ) ).thenReturn( 2 );
        verify( agentOpportunityService ).getAgentNewOpportunitiesCount( anyString() );
        verify( agentNotificationBusinessService ).sendPushNotification( anyString(),
                any( NotificationRequest.class ) );
        Assert.assertEquals( response.getStatus(), Status.SUCCESS );

    }

    /**
     * Test remove un registered device.
     */
    @Test
    public void testRemoveUnRegisteredDevice() {
        AgentResponse response = null;
        final String agentId = "test";
        final String deviceId = "test";
        when( agentInfoService.getAgentInfo( Mockito.anyString() ) ).thenReturn( new AgentInfo() );
        response = agentBusinessServiceImpl.removeDevice( agentId, deviceId );
        Assert.assertEquals( response.getStatus(), Status.SUCCESS );
    }

    /**
     * Test remove registered device.
     */
    @Test
    public void testRemoveRegisteredDevice() {
        AgentResponse response = null;
        final String agentId = "test";
        final String deviceId = "test";
        final AgentInfo info = new AgentInfo();
        final Device device = new Device();
        device.setId( deviceId );
        info.addDevice( device );
        when( agentInfoService.getAgentInfo( Mockito.anyString() ) ).thenReturn( info );
        response = agentBusinessServiceImpl.removeDevice( agentId, deviceId );
        Assert.assertEquals( response.getStatus(), Status.SUCCESS );
    }

    /**
     * Test update last viewed.
     */
    @Test
    public void testUpdateLastViewed() {
        final LastViewedRequest request = new LastViewedRequest();
        final LastViewed lastViewed = new LastViewed();
        request.setObjectType( "phone" );
        final String agentId = "agentId";
        final String id = "testId";
        final ActionLogDto actionLogDto = new ActionLogDto();
        when( agentService.updateLastViewed( agentId, id, null ) ).thenReturn( lastViewed );
        when( agentInfoService.getAgentInfo( agentId ) ).thenReturn( new AgentInfo() );
        when( auditTrailBusinessService.getActionLogDto( any(), anyString(), anyMap(), any( ActionEntity.class ),
                anyString() ) ).thenReturn( actionLogDto );
        doNothing().when( auditTrailBusinessService ).saveAuditForAction( actionLogDto );
        final AgentResponse agentResponse = agentBusinessServiceImpl.updateLastViewed( agentId, id, request );
        Mockito.verify( agentService ).updateLastViewed( Mockito.any(), Mockito.any(), Mockito.any() );
        assertEquals( id, agentResponse.getId() );
        verify( agentService ).updateLastViewed( agentId, id, null );
        verify( auditLogTask ).auditLastViewed( agentId, id, request, lastViewed );
    }

    /**
     * Test update last viewed.
     */
    @Test
    public void testUpdateLastViewedForOpportunity() {
        final LastViewedRequest request = new LastViewedRequest();
        final LastViewed lastViewed = new LastViewed();
        request.setObjectType( "opportunity" );
        final String agentId = "agentId";
        final String id = "testId";
        final ActionLogDto actionLogDto = new ActionLogDto();
        when( agentService.updateLastViewed( agentId, id, "opportunities" ) ).thenReturn( lastViewed );
        when( agentInfoService.getAgentInfo( agentId ) ).thenReturn( new AgentInfo() );
        when( auditTrailBusinessService.getActionLogDto( any(), anyString(), anyMap(), any( ActionEntity.class ),
                anyString() ) ).thenReturn( actionLogDto );
        doNothing().when( auditTrailBusinessService ).saveAuditForAction( actionLogDto );
        final AgentResponse agentResponse = agentBusinessServiceImpl.updateLastViewed( agentId, id, request );
        Mockito.verify( agentService ).updateLastViewed( Mockito.any(), Mockito.any(), Mockito.any() );
        assertEquals( id, agentResponse.getId() );
        verify( agentService ).updateLastViewed( agentId, id, "opportunities" );
        verify( auditLogTask ).auditLastViewed( agentId, id, request, lastViewed );
    }

    /**
     * Test update last viewed.
     */
    @Test
    public void testUpdateLastViewedForTask() {
        final LastViewedRequest request = new LastViewedRequest();
        final LastViewed lastViewed = new LastViewed();
        request.setObjectType( "task" );
        final String agentId = "agentId";
        final String id = "testId";
        final ActionLogDto actionLogDto = new ActionLogDto();
        when( agentService.updateLastViewed( agentId, id, "tasks" ) ).thenReturn( lastViewed );
        when( agentInfoService.getAgentInfo( agentId ) ).thenReturn( new AgentInfo() );
        when( auditTrailBusinessService.getActionLogDto( any(), anyString(), anyMap(), any( ActionEntity.class ),
                anyString() ) ).thenReturn( actionLogDto );
        doNothing().when( auditTrailBusinessService ).saveAuditForAction( actionLogDto );
        final AgentResponse agentResponse = agentBusinessServiceImpl.updateLastViewed( agentId, id, request );
        Mockito.verify( agentService ).updateLastViewed( Mockito.any(), Mockito.any(), Mockito.any() );
        assertEquals( id, agentResponse.getId() );
        verify( agentService ).updateLastViewed( agentId, id, "tasks" );
        verify( auditLogTask ).auditLastViewed( agentId, id, request, lastViewed );
    }

    /**
     * Test update last viewed.
     */
    @Test
    public void testUpdateLastViewedForRequest() {
        final LastViewedRequest request = new LastViewedRequest();
        final LastViewed lastViewed = new LastViewed();
        request.setObjectType( "request" );
        final String agentId = "agentId";
        final String id = "testId";
        final ActionLogDto actionLogDto = new ActionLogDto();
        when( agentService.updateLastViewed( agentId, id, "requests" ) ).thenReturn( lastViewed );
        when( agentInfoService.getAgentInfo( agentId ) ).thenReturn( new AgentInfo() );
        when( auditTrailBusinessService.getActionLogDto( any(), anyString(), anyMap(), any( ActionEntity.class ),
                anyString() ) ).thenReturn( actionLogDto );
        doNothing().when( auditTrailBusinessService ).saveAuditForAction( actionLogDto );
        final AgentResponse agentResponse = agentBusinessServiceImpl.updateLastViewed( agentId, id, request );
        Mockito.verify( agentService ).updateLastViewed( Mockito.any(), Mockito.any(), Mockito.any() );
        assertEquals( id, agentResponse.getId() );
        verify( agentService ).updateLastViewed( agentId, id, "requests" );
        verify( auditLogTask ).auditLastViewed( agentId, id, request, lastViewed );
    }

    /**
     * Test creact agent action.
     */
    @Test
    public void testCreactAgentAction() {
        final ActionRequest actionRequest = new ActionRequest();
        final ActionLog actionLog = new ActionLog();
        when( actionLogBuilder.convertTo( Mockito.any( ActionRequest.class ) ) ).thenReturn( actionLog );
        final ActionLog actionLog2 = new ActionLog();
        final String id = "id";
        actionLog2.setId( id );
        when( actionLogBusinessService.saveActionLog( actionLog ) ).thenReturn( actionLog2 );
        final AgentResponse resp = agentBusinessServiceImpl.createAgentAction( "agentId", actionRequest );
        Assert.assertNotNull( resp );
        assertEquals( id, resp.getId() );
    }

    /**
     * Should get CTA audit logs for valid inputs.
     */
    @Test
    public void shouldGetCtaAuditLogsForValidInputs() {
        final String agentId = "dummy agent id";
        final String opportunityId = "testOppId";
        final String ctaType = "cta1,cta2";

        final List< ActionLog > actionLogs = new ArrayList<>();
        final ActionLog actionLog = new ActionLog();
        final String actionType = "dummy type";
        actionLog.setActionType( actionType );
        actionLogs.add( actionLog );

        final ActionLogResponse response = new ActionLogResponse();
        final List< ActionLogDto > actionLogDtos = new ArrayList<>();
        final ActionLogDto dto = new ActionLogDto();
        dto.setActionType( actionType );
        actionLogDtos.add( dto );
        response.setActionLogs( actionLogDtos );

        when( agentInfoService.getAgentInfo( agentId ) ).thenReturn( new AgentInfo() );
        when( actionLogResponseBuilder.convertTo( actionLogs ) ).thenReturn( response );
        when( actionLogBusinessService.getCTAAuditLogs( anyString(), anyString(), anyList() ) )
                .thenReturn( actionLogs );

        final ActionLogResponse actionLogResponse = agentBusinessServiceImpl.getCTAAuditLogs( agentId, opportunityId,
                ctaType );
        assertEquals( actionType, actionLogResponse.getActionLogs().iterator().next().getActionType() );
        verify( actionLogBusinessService ).getCTAAuditLogs( anyString(), anyString(), anyList() );
        verify( actionLogResponseBuilder ).convertTo( anyList() );
    }

    /**
     * Should get CTA audit logs for blank CTA type.
     */
    @Test
    public void shouldGetCtaAuditLogsForBlankCtaType() {
        final String agentId = "dummy agent id";
        final String opportunityId = "testOppId";
        final String ctaType = "";

        final List< ActionLog > actionLogs = new ArrayList<>();
        final ActionLog actionLog = new ActionLog();
        final String actionType = "dummy type";
        actionLog.setActionType( actionType );
        actionLogs.add( actionLog );

        final ActionLogResponse response = new ActionLogResponse();
        final List< ActionLogDto > actionLogDtos = new ArrayList<>();
        final ActionLogDto dto = new ActionLogDto();
        dto.setActionType( actionType );
        actionLogDtos.add( dto );
        response.setActionLogs( actionLogDtos );

        when( agentInfoService.getAgentInfo( agentId ) ).thenReturn( new AgentInfo() );
        when( actionLogResponseBuilder.convertTo( actionLogs ) ).thenReturn( response );
        when( actionLogBusinessService.getCTAAuditLogs( anyString(), anyString(), anyList() ) )
                .thenReturn( actionLogs );

        final ActionLogResponse actionLogResponse = agentBusinessServiceImpl.getCTAAuditLogs( agentId, opportunityId,
                ctaType );
        assertEquals( actionType, actionLogResponse.getActionLogs().iterator().next().getActionType() );
        verify( actionLogBusinessService ).getCTAAuditLogs( anyString(), anyString(), anyList() );
        verify( actionLogResponseBuilder ).convertTo( anyList() );
    }

    /**
     * Should post error to slack for valid input.
     */
    @Test
    public void shouldPostErrorToSlackForValidInput() {
        when( errorSlackRequestBuilder.convertTo( any( SlackError.class ) ) ).thenReturn( new SlackRequest() );
        doNothing().when( slackService ).publishToSlack( any( SlackRequest.class ), anyString() );
        agentBusinessServiceImpl.postErrorToSlack( any( SlackError.class ) );
    }

    /**
     * Test send pts email notification.
     */
    @Test
    public void testSendPTSEmailNotificationShouldSendThreeNotifications() {
        final AgentSource agentSource = new AgentSource();
        final EmailNotification notification = new EmailNotification();
        when( agentOnboardEmailNotificationBuilder.convertTo( agentSource, null ) ).thenReturn( notification );
        when( escrowEmailNotificationBuilder.convertTo( agentSource, null ) ).thenReturn( notification );
        when( preliminaryTitleReportsEmailNotificationBuilder.convertTo( agentSource, null ) )
                .thenReturn( notification );
        agentBusinessServiceImpl.sendPTSEmailNotifications( agentSource );
        verify( mailService, times( 3 ) ).send( notification );
    }

    /**
     * Test send PTS email notification should send no notification.
     */
    @Test
    public void testSendPTSEmailNotificationShouldSendNoNotification() {
        final AgentSource agentSource = new AgentSource();
        final EmailNotification notification = null;
        when( agentOnboardEmailNotificationBuilder.convertTo( agentSource, notification ) ).thenReturn( notification );
        when( escrowEmailNotificationBuilder.convertTo( agentSource, notification ) ).thenReturn( notification );
        when( preliminaryTitleReportsEmailNotificationBuilder.convertTo( agentSource, notification ) )
                .thenReturn( notification );
        agentBusinessServiceImpl.sendPTSEmailNotifications( agentSource );
        verifyZeroInteractions( mailService );
    }

    /**
     * Test onboard agent.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    private void testOnboardAgent() throws Exception {
        final AgentOnboardRequest request = new AgentOnboardRequest();
        request.setStatus( "active" );
        request.setPhotoData( "test".getBytes() );
        when( agentDetailsBuilder.convertFrom( request ) ).thenReturn( new AgentDetails() );
        final User user = new User();
        final List< RoleMember > roleMemberList = new ArrayList<>();
        final RoleMember rm = new RoleMember();
        roleMemberList.add( rm );
        user.setRoleMember( roleMemberList );
        when( userBuilder.convertFrom( any( AgentOnboardRequest.class ), any( User.class ) ) ).thenReturn( user );
        final AgentDetails ad = new AgentDetails();
        when( agentDetailsBuilder.convertFrom( any( AgentOnboardRequest.class ), any( AgentDetails.class ) ) )
                .thenReturn( ad );
        final BaseResponse response = agentBusinessServiceImpl.onboard( request );
        Assert.assertNotNull( response );
    }

    /**
     * Test update agent should update agent on firebase and database when agent
     * is found on firebase and database.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testUpdateAgentShouldUpdateAgentOnFbAndDbWhenAgentFoundOnFbAndDb() throws IOException {
        final AgentSource agentSource = new AgentSource();
        agentSource.setEmail( "email@email.com" );
        agentSource.setStatus( "dummyStatus" );
        final User user = new User();
        final Search search = new Search();
        final AgentInfo agentInfo = new AgentInfo();
        final AgentDetails agentDetails = new AgentDetails();
        agentDetails.setUser( user );

        when( userService.save( user ) ).thenReturn( new User() );
        when( searchService.searchByAgentEmail( agentSource.getEmail() ) ).thenReturn( search );
        when( agentInfoService.getAgentInfo( search.getAgentId() ) ).thenReturn( agentInfo );
        when( agentInfoService.patchAgentInfo( search.getAgentId(), agentInfo ) ).thenReturn( new AgentInfo() );
        when( agentDetailsService.findAgentByEmail( agentSource.getEmail() ) ).thenReturn( agentDetails );
        when( agentDetailsService.save( agentDetails ) ).thenReturn( new AgentDetails() );
        when( crmAgentBuilder.convertTo( agentSource, agentDetails ) ).thenReturn( agentDetails );
        agentBusinessServiceImpl.updateAgent( agentSource );

        verify( userService ).save( user );
        verify( searchService ).searchByAgentEmail( agentSource.getEmail() );
        verify( agentInfoService ).getAgentInfo( search.getAgentId() );
        verify( agentInfoService ).patchAgentInfo( search.getAgentId(), agentInfo );
        verify( agentDetailsService ).findAgentByEmail( agentSource.getEmail() );
        verify( agentDetailsService ).save( agentDetails );
    }

    /**
     * Test update agent should update agent on firebase when agent found on
     * firebase and not found in database.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testUpdateAgentShouldUpdateAgentOnFbWhenAgentFoundOnFbAndNotFoundInDb() throws IOException {
        final AgentSource agentSource = new AgentSource();
        agentSource.setId( "crmId" );
        agentSource.setEmail( "email@email.com" );
        agentSource.setStatus( "dummyStatus" );
        final Search search = new Search();
        final AgentInfo agentInfo = new AgentInfo();

        when( searchService.searchByAgentEmail( agentSource.getEmail() ) ).thenReturn( search );
        when( agentInfoService.getAgentInfo( search.getAgentId() ) ).thenReturn( agentInfo );
        when( agentInfoService.patchAgentInfo( search.getAgentId(), agentInfo ) ).thenReturn( new AgentInfo() );
        when( agentDetailsService.findAgentByEmail( agentSource.getEmail() ) ).thenReturn( null );

        agentBusinessServiceImpl.updateAgent( agentSource );

        verify( searchService ).searchByAgentEmail( agentSource.getEmail() );
        verify( agentInfoService ).getAgentInfo( search.getAgentId() );
        verify( agentInfoService ).patchAgentInfo( search.getAgentId(), agentInfo );
        verify( agentDetailsService ).findAgentByEmail( agentSource.getEmail() );
        verifyNoMoreInteractions( agentDetailsService );
    }

    /**
     * Test update agent should not update agent on firebase and database when
     * agent not found on firebase and database.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testUpdateAgentShouldNotUpdateAgentOnFbAndDbWhenAgentNotFoundOnFbAndDb() throws IOException {
        final AgentSource agentSource = new AgentSource();
        agentSource.setEmail( "email@email.com" );
        agentSource.setStatus( "dummyStatus" );

        when( searchService.searchByAgentEmail( agentSource.getEmail() ) ).thenReturn( null );
        when( agentDetailsService.findAgentByEmail( agentSource.getEmail() ) ).thenReturn( null );

        agentBusinessServiceImpl.updateAgent( agentSource );

        verify( searchService ).searchByAgentEmail( agentSource.getEmail() );
        verifyZeroInteractions( agentInfoService );
        verify( agentDetailsService ).findAgentByEmail( agentSource.getEmail() );
        verifyNoMoreInteractions( agentDetailsService );
    }

    /**
     * Test update agent success.
     */
    @Test
    public void testUpdateAgentSuccess() {
        final String agentId = "agentId";
        final String agentEmail = "agentEmail@test.com";
        final Map< String, Object > request = new HashMap<>();
        request.put( "onDuty", TRUE );
        final Search search = new Search();
        search.setAgentEmail( agentEmail );
        search.setAgentId( agentId );
        final AgentInfo agentInfo = new AgentInfo();
        final Map< String, Object > crmQueryResponse = new HashMap<>();
        crmQueryResponse.put( Constants.QUERY_PARAM_ID, "crmAgentId" );
        final User user = new User();
        user.setEmail( "agentEmail@test.com" );
        final AgentDetails agentDetails = new AgentDetails();
        agentDetails.setUser( user );
        final Map< String, Object > crmAgentDetails = new HashMap<>();
        crmAgentDetails.put( "Id", "crmAgentId" );
        final Map< String, String > crmAgentFields = ( Map< String, String > ) getField( agentBusinessServiceImpl,
                "crmAgentFields" );
        crmAgentFields.put( "onDuty", "Active__c" );

        final AgentAvailabilityLog agentAvailabilityLog = new AgentAvailabilityLog();

        when( agentService.getAgentEmailById( agentId ) ).thenReturn( agentEmail );
        when( agentDetailsService.findAgentByEmail( agentEmail ) ).thenReturn( agentDetails );
        when( agentAvailabilityLogService.getLog( agentDetails, TRUE ) ).thenReturn( agentAvailabilityLog );
        when( agentAvailabilityLogService.saveLog( agentAvailabilityLog ) ).thenReturn( agentAvailabilityLog );
        when( agentDetailsService.save( agentDetails ) ).thenReturn( new AgentDetails() );
        when( agentInfoService.getAgentInfo( agentId ) ).thenReturn( agentInfo );
        when( agentInfoService.patchAgentInfo( agentId, agentInfo ) ).thenReturn( new AgentInfo() );
        when( agentService.getAgentDetails( agentEmail ) ).thenReturn( crmAgentDetails );
        doNothing().when( agentService ).patchCRMAgent( any(), anyString() );

        final BaseResponse response = agentBusinessServiceImpl.updateAgent( agentId, request );
        assertNotNull( response );
        assertEquals( response.getStatus(), SUCCESS );
        verify( agentService ).getAgentEmailById( agentId );
        verify( agentDetailsService ).findAgentByEmail( agentEmail );
        verify( agentAvailabilityLogService ).getLog( agentDetails, TRUE );
        verify( agentAvailabilityLogService ).saveLog( agentAvailabilityLog );
        verify( agentDetailsService ).save( agentDetails );
        verify( agentInfoService ).getAgentInfo( agentId );
        verify( agentInfoService ).patchAgentInfo( agentId, agentInfo );
        verify( agentService ).getAgentDetails( agentEmail );
        verify( agentService ).patchCRMAgent( any(), anyString() );
    }

    /**
     * Test update agent success for off duty request.
     */
    @Test
    public void testUpdateAgentSuccessForOffDutyRequest() {
        final String agentId = "agentId";
        final String agentEmail = "agentEmail@test.com";
        final Map< String, Object > request = new HashMap<>();
        request.put( "onDuty", FALSE );
        request.put( "offDutyEndTime", 123456789l );
        final Map< String, Object > variableMap = new HashMap<>();
        variableMap.put( "offDutyStartTime", DateTime.now().plusSeconds( 2 ).toString() );
        variableMap.put( "offDutyEndTime", DateTime.now().plusMinutes( 1 ) );
        variableMap.put( "agentEmail", agentEmail );
        variableMap.put( "agentId", agentId );
        variableMap.put( "request", request );

        final Search search = new Search();
        search.setAgentEmail( agentEmail );
        search.setAgentId( agentId );
        final AgentInfo agentInfo = new AgentInfo();
        final Map< String, Object > crmQueryResponse = new HashMap<>();
        crmQueryResponse.put( Constants.QUERY_PARAM_ID, "crmAgentId" );
        final User user = new User();
        user.setEmail( "agentEmail@test.com" );
        final AgentDetails agentDetails = new AgentDetails();
        agentDetails.setUser( user );
        final Map< String, Object > crmAgentDetails = new HashMap<>();
        crmAgentDetails.put( "Id", "crmAgentId" );
        final Map< String, String > crmAgentFields = ( Map< String, String > ) getField( agentBusinessServiceImpl,
                "crmAgentFields" );
        crmAgentFields.put( "onDuty", "Active__c" );

        final AgentAvailabilityLog agentAvailabilityLog = new AgentAvailabilityLog();

        when( agentService.getAgentEmailById( agentId ) ).thenReturn( agentEmail );
        when( agentDetailsService.findAgentByEmail( agentEmail ) ).thenReturn( agentDetails );
        when( agentAvailabilityLogService.getLog( agentDetails, TRUE ) ).thenReturn( agentAvailabilityLog );
        when( agentAvailabilityLogService.saveLog( agentAvailabilityLog ) ).thenReturn( agentAvailabilityLog );
        when( agentDetailsService.save( agentDetails ) ).thenReturn( new AgentDetails() );
        when( agentInfoService.getAgentInfo( agentId ) ).thenReturn( agentInfo );
        when( agentInfoService.patchAgentInfo( agentId, agentInfo ) ).thenReturn( new AgentInfo() );
        when( agentService.getAgentDetails( agentEmail ) ).thenReturn( crmAgentDetails );
        doNothing().when( agentService ).patchCRMAgent( any(), anyString() );

        when( runtimeService.startProcessInstanceByKey( "agentAvailabilityProcess", variableMap ) )
                .thenReturn( processInstance );

        final BaseResponse response = agentBusinessServiceImpl.updateAgent( agentId, request );
        assertNotNull( response );
        assertEquals( response.getStatus(), SUCCESS );
        verify( agentService ).getAgentEmailById( agentId );
        verify( agentDetailsService ).findAgentByEmail( agentEmail );
        verify( agentAvailabilityLogService ).getLog( agentDetails, TRUE );
        verify( agentAvailabilityLogService ).saveLog( agentAvailabilityLog );
        verify( agentDetailsService ).save( agentDetails );
        verify( agentInfoService ).getAgentInfo( agentId );
        verify( agentInfoService ).patchAgentInfo( agentId, agentInfo );
        verify( agentService ).getAgentDetails( agentEmail );
        verify( agentService ).patchCRMAgent( any(), anyString() );
        // verify( runtimeService, times( 1 ) );
    }

    /**
     * Test update agent if request body is empty.
     */
    @Test
    public void testUpdateAgentIfRequestBodyIsEmpty() {
        final BaseResponse response = agentBusinessServiceImpl.updateAgent( "agentId", new HashMap<>() );
        assertNotNull( response );
        assertEquals( response.getStatus(), SUCCESS );
        verifyZeroInteractions( agentService );
        verifyZeroInteractions( agentInfoService );
        verifyZeroInteractions( agentDetailsService );
    }

    /**
     * Test process agent off duty.
     */
    @Test
    public void testProcessAgentOffDuty() {
        final String executionId = "testexecutionid";
        final String agentId = "agentId";
        final String agentEmail = "agentEmail@test.com";
        final Map< String, Object > request = new HashMap<>();
        request.put( "onDuty", FALSE );
        final DateTime offDutyStartTime = new DateTime();
        final DateTime offDutyEndTime = new DateTime();
        final Search search = new Search();
        search.setAgentEmail( agentEmail );
        search.setAgentId( agentId );
        final AgentInfo agentInfo = new AgentInfo();
        final Map< String, Object > crmQueryResponse = new HashMap<>();
        crmQueryResponse.put( Constants.QUERY_PARAM_ID, "crmAgentId" );
        final User user = new User();
        user.setEmail( "agentEmail@test.com" );
        final AgentDetails agentDetails = new AgentDetails();
        agentDetails.setUser( user );
        final Map< String, Object > crmAgentDetails = new HashMap<>();
        crmAgentDetails.put( "Id", "crmAgentId" );
        final Map< String, String > crmAgentFields = ( Map< String, String > ) getField( agentBusinessServiceImpl,
                "crmAgentFields" );
        crmAgentFields.put( "onDuty", "Active__c" );
        final AgentAvailabilityLog agentAvailabilityLog = new AgentAvailabilityLog();
        final AgentAvailabilityLog newAgentAvailabilityLog = new AgentAvailabilityLog();

        when( runtimeService.getVariable( executionId, "agentEmail" ) ).thenReturn( agentEmail );
        when( runtimeService.getVariable( executionId, "offDutyStartTime" ) ).thenReturn( offDutyStartTime );
        when( runtimeService.getVariable( executionId, "offDutyEndTime" ) ).thenReturn( offDutyEndTime );
        doNothing().when( processBusinessService ).createProcess( agentEmail, executionId,
                GravitasProcess.AGENT_AVAILABILITY_PROCESS );
        when( agentDetailsService.findAgentByEmail( agentEmail ) ).thenReturn( agentDetails );
        when( agentAvailabilityLogService.saveLog( newAgentAvailabilityLog ) ).thenReturn( agentAvailabilityLog );

        agentBusinessServiceImpl.processAgentOffDuty( executionId );

        verify( runtimeService ).getVariable( executionId, "agentEmail" );
        verify( runtimeService ).getVariable( executionId, "offDutyStartTime" );
        verify( runtimeService ).getVariable( executionId, "offDutyEndTime" );
        verify( processBusinessService ).createProcess( agentEmail, executionId,
                GravitasProcess.AGENT_AVAILABILITY_PROCESS );
        verify( agentDetailsService, times( 1 ) ).findAgentByEmail( agentEmail );
    }

    /**
     * Test process agent on duty.
     */
    @Test
    public void testProcessAgentOnDuty() {
        final String executionId = "testexecutionid";
        final String agentId = "agentId";
        final String agentEmail = "agentEmail@test.com";
        final Map< String, Object > request = new HashMap<>();
        request.put( "onDuty", FALSE );
        final Search search = new Search();
        search.setAgentEmail( agentEmail );
        search.setAgentId( agentId );
        final AgentInfo agentInfo = new AgentInfo();
        final Map< String, Object > crmQueryResponse = new HashMap<>();
        crmQueryResponse.put( Constants.QUERY_PARAM_ID, "crmAgentId" );
        final User user = new User();
        user.setEmail( "agentEmail@test.com" );
        final AgentDetails agentDetails = new AgentDetails();
        agentDetails.setUser( user );
        final Map< String, Object > crmAgentDetails = new HashMap<>();
        crmAgentDetails.put( "Id", "crmAgentId" );
        final Map< String, String > crmAgentFields = ( Map< String, String > ) getField( agentBusinessServiceImpl,
                "crmAgentFields" );
        crmAgentFields.put( "onDuty", "Active__c" );
        final AgentAvailabilityLog agentAvailabilityLog = new AgentAvailabilityLog();
        final NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setEventType( ON_DUTY_AGENT );

        when( runtimeService.getVariable( executionId, "agentEmail" ) ).thenReturn( agentEmail );
        when( runtimeService.getVariable( executionId, "agentId" ) ).thenReturn( agentId );
        when( runtimeService.getVariable( executionId, "request" ) ).thenReturn( request );
        when( agentDetailsService.findAgentByEmail( agentEmail ) ).thenReturn( agentDetails );
        when( agentAvailabilityLogService.getLog( agentDetails, TRUE ) ).thenReturn( agentAvailabilityLog );
        when( agentAvailabilityLogService.saveLog( agentAvailabilityLog ) ).thenReturn( agentAvailabilityLog );
        when( agentDetailsService.save( agentDetails ) ).thenReturn( new AgentDetails() );
        when( agentInfoService.getAgentInfo( agentId ) ).thenReturn( agentInfo );
        when( agentInfoService.patchAgentInfo( agentId, agentInfo ) ).thenReturn( new AgentInfo() );
        when( agentService.getAgentDetails( agentEmail ) ).thenReturn( crmAgentDetails );
        doNothing().when( agentService ).patchCRMAgent( any(), anyString() );
        when( agentNotificationBusinessService.sendPushNotification( agentId, notificationRequest ) )
                .thenReturn( new ArrayList<>() );
        when( processBusinessService.deActivateProcess( agentEmail, GravitasProcess.AGENT_AVAILABILITY_PROCESS,
                executionId ) ).thenReturn( new Process() );

        agentBusinessServiceImpl.processAgentOnDuty( executionId );

        verify( runtimeService ).getVariable( executionId, "agentEmail" );
        verify( runtimeService ).getVariable( executionId, "agentId" );
        verify( runtimeService ).getVariable( executionId, "request" );
        verify( agentDetailsService, times( 2 ) ).findAgentByEmail( agentEmail );
        verify( agentAvailabilityLogService, times( 2 ) ).getLog( agentDetails, TRUE );
        verify( agentAvailabilityLogService ).saveLog( agentAvailabilityLog );
        verify( agentDetailsService ).save( agentDetails );
        verify( agentInfoService ).getAgentInfo( agentId );
        verify( agentInfoService ).patchAgentInfo( agentId, agentInfo );
        verify( agentService ).getAgentDetails( agentEmail );
        verify( agentService ).patchCRMAgent( any(), anyString() );
        // verify( agentNotificationBusinessService, times( 1 ) );
        verify( processBusinessService ).deActivateProcess( agentEmail, GravitasProcess.AGENT_AVAILABILITY_PROCESS,
                executionId );

    }

    /**
     * Test update agent if request body contains incorrect value.
     */
    @Test
    public void testUpdateAgentIfRequestBodyContainsIncorrectValue() {
        final Map< String, Object > request = new HashMap<>();
        request.put( "wrongkey", "testData" );
        final BaseResponse response = agentBusinessServiceImpl.updateAgent( "agentId", request );
        assertNotNull( response );
        assertEquals( response.getStatus(), SUCCESS );
        verifyZeroInteractions( agentService );
        verifyZeroInteractions( agentInfoService );
        verifyZeroInteractions( agentDetailsService );
    }

    /**
     * Test update agent should not update details on database and crm if agent
     * not found.
     */
    @Test
    private void testUpdateAgentShouldNotUpdateDetailsOnDatabaseAndCRMIfAgentNotFound() {
        final String agentId = "agentId";
        final String agentEmail = "agentEmail@test.com";
        final Map< String, Object > request = new HashMap<>();
        request.put( "onDuty", TRUE );
        final AgentInfo agentInfo = new AgentInfo();

        when( agentService.getAgentEmailById( agentId ) ).thenReturn( agentEmail );
        when( agentDetailsService.findAgentByEmail( agentEmail ) ).thenReturn( null );
        when( agentInfoService.getAgentInfo( agentId ) ).thenReturn( agentInfo );
        when( agentInfoService.patchAgentInfo( agentId, agentInfo ) ).thenReturn( new AgentInfo() );
        when( agentService.getAgentDetails( agentEmail ) ).thenThrow( ResultNotFoundException.class );

        final BaseResponse response = agentBusinessServiceImpl.updateAgent( "agentId", request );

        assertNotNull( response );
        assertEquals( response.getStatus(), SUCCESS );
        verify( agentService ).getAgentEmailById( agentId );
        verify( agentDetailsService ).findAgentByEmail( agentEmail );
        verify( agentDetailsService, times( 0 ) ).save( any() );
        verify( agentInfoService ).getAgentInfo( agentId );
        verify( agentInfoService ).patchAgentInfo( agentId, agentInfo );
        verify( agentService ).getAgentDetails( agentEmail );
        verify( agentService, times( 0 ) ).patchCRMAgent( anyMap(), anyString() );
    }

    /**
     * Test send pending sale PTS email notifications should send email
     * notification.
     */
    @Test
    public void testSendPendingSalePTSEmailNotificationsShouldSendEmailNotification() {
        final AgentSource agentSource = new AgentSource();
        final EmailNotification notification = new EmailNotification();
        final OpportunitySource opportunitySource = new OpportunitySource();
        when( pendingSalePTSEmailNotificationBuilder.convertTo( true, agentSource, opportunitySource ) )
                .thenReturn( notification );
        agentBusinessServiceImpl.sendPendingSalePTSEmailNotification( true, agentSource, opportunitySource );
        verify( mailService ).send( notification );
    }

    /**
     * Test send pending sale PTS email notifications should not send email
     * notification.
     */
    @Test
    public void testSendPendingSalePTSEmailNotificationsShouldNotSendEmailNotification() {
        final AgentSource agentSource = new AgentSource();
        final EmailNotification notification = null;
        final OpportunitySource opportunitySource = new OpportunitySource();
        when( pendingSalePTSEmailNotificationBuilder.convertTo( false, agentSource, opportunitySource ) )
                .thenReturn( notification );
        agentBusinessServiceImpl.sendPendingSalePTSEmailNotification( false, agentSource, opportunitySource );
        verifyZeroInteractions( mailService );
    }

    /**
     * Test send sold stage PTS email notifications should send email
     * notification.
     */
    @Test
    public void testSendSoldStagePTSEmailNotificationsShouldSendEmailNotification() {
        final AgentSource agentSource = new AgentSource();
        final OpportunitySource opportunitySource = new OpportunitySource();
        final EmailNotification notification = new EmailNotification();
        when( soldStagePTSEmailNotificationBuilder.convertTo( true, agentSource, opportunitySource ) )
                .thenReturn( notification );
        agentBusinessServiceImpl.sendSoldStagePTSEmailNotifications( true, agentSource, opportunitySource );
        verify( mailService ).send( notification );
    }

    /**
     * Test send sold stage PTS email notifications should not send email
     * notification.
     */
    @Test
    public void testSendSoldStagePTSEmailNotificationsShouldNotSendEmailNotification() {
        final AgentSource agentSource = new AgentSource();
        final OpportunitySource opportunitySource = new OpportunitySource();
        final EmailNotification notification = null;
        when( soldStagePTSEmailNotificationBuilder.convertTo( true, agentSource, opportunitySource ) )
                .thenReturn( notification );
        agentBusinessServiceImpl.sendSoldStagePTSEmailNotifications( true, agentSource, opportunitySource );
        verifyZeroInteractions( mailService );
    }

    /**
     * Test send agent email.
     */
    @Test
    public void testSendAgentEmail() {
        final String agentEmail = "a@a.com";
        final EmailRequest emailRequest = new EmailRequest();
        final List< String > toList = new ArrayList<>();
        toList.add( "a@a.com" );
        emailRequest.setTo( toList );
        final String expected = "id";
        final Message message = new Message();
        when( gmailMessageBuilder.convertTo( emailRequest ) ).thenReturn( message );
        when( gmailService.getGmailService( agentEmail ) ).thenReturn( gmail );
        when( gmailService.sendEmail( gmail, message ) ).thenReturn( expected );
        final AgentResponse actual = agentBusinessServiceImpl.sendAgentEmail( agentEmail, emailRequest );
        assertEquals( actual.getId(), expected );
        verify( gmailMessageBuilder ).convertTo( emailRequest );
        verify( gmailService ).getGmailService( agentEmail );
        verify( gmailService ).sendEmail( gmail, message );
    }

    /**
     * Test send agent emails.
     */
    @Test
    public void testSendAgentEmails() {
        final String agentEmail = "a@a.com";
        final List< EmailRequest > emailRequests = new ArrayList<>();
        final EmailRequest emailRequest = new EmailRequest();
        final AgentEmailResult expected = new AgentEmailResult( null, null );
        final Future< AgentEmailResult > future = new Future< AgentEmailResult >() {

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public AgentEmailResult get( final long timeout, final TimeUnit unit )
                    throws InterruptedException, ExecutionException, TimeoutException {
                return expected;
            }

            @Override
            public AgentEmailResult get() throws InterruptedException, ExecutionException {
                return null;
            }

            @Override
            public boolean cancel( final boolean mayInterruptIfRunning ) {
                return false;
            }
        };
        emailRequests.add( emailRequest );
        when( gmailService.getGmailService( agentEmail ) ).thenReturn( gmail );
        when( agentEmailBusinessService.sendAgentEmail( emailRequest, gmail, agentEmail ) ).thenReturn( future );
        final AgentEmailResponse actual = agentBusinessServiceImpl.sendAgentEmails( agentEmail, emailRequests );
        assertEquals( actual.getResults().iterator().next(), expected );
        verify( gmailService ).getGmailService( agentEmail );
        verify( agentEmailBusinessService ).sendAgentEmail( emailRequest, gmail, agentEmail );
    }

    /**
     * Test send agent emails should throw exception for empty request.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testSendAgentEmailsShouldThrowExceptionForEmptyRequest() {
        final String agentEmail = "a@a.com";
        final List< EmailRequest > emailRequests = null;
        final AgentEmailResponse actual = agentBusinessServiceImpl.sendAgentEmails( agentEmail, emailRequests );
    }

    /**
     * Test send agent emails should send empty response for timeout.
     */
    @Test
    public void testSendAgentEmailsShouldSendEmptyResponseForTimeout() {
        final String agentEmail = "a@a.com";
        final List< EmailRequest > emailRequests = new ArrayList<>();
        final EmailRequest emailRequest = new EmailRequest();
        final AgentEmailResult expected = new AgentEmailResult( null, null );
        final Future< AgentEmailResult > future = new Future< AgentEmailResult >() {

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public AgentEmailResult get( final long timeout, final TimeUnit unit )
                    throws InterruptedException, ExecutionException, TimeoutException {
                throw new TimeoutException();
            }

            @Override
            public AgentEmailResult get() throws InterruptedException, ExecutionException {
                return null;
            }

            @Override
            public boolean cancel( final boolean mayInterruptIfRunning ) {
                return false;
            }
        };
        emailRequests.add( emailRequest );
        when( gmailService.getGmailService( agentEmail ) ).thenReturn( gmail );
        when( agentEmailBusinessService.sendAgentEmail( emailRequest, gmail, agentEmail ) ).thenReturn( future );
        final AgentEmailResponse actual = agentBusinessServiceImpl.sendAgentEmails( agentEmail, emailRequests );
        assertEquals( actual.getResults().size(), 0 );
        verify( gmailService ).getGmailService( agentEmail );
        verify( agentEmailBusinessService ).sendAgentEmail( emailRequest, gmail, agentEmail );
    }

    /**
     * Test send agent emails should throw exception if interrupted exception
     * occurs.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testSendAgentEmailsShouldThrowExceptionIfInterruptedExceptionOccurs() {
        final String agentEmail = "a@a.com";
        final List< EmailRequest > emailRequests = new ArrayList<>();
        final EmailRequest emailRequest = new EmailRequest();
        final AgentEmailResult expected = new AgentEmailResult( null, null );
        final Future< AgentEmailResult > future = new Future< AgentEmailResult >() {

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public AgentEmailResult get( final long timeout, final TimeUnit unit )
                    throws InterruptedException, ExecutionException, TimeoutException {
                throw new InterruptedException();
            }

            @Override
            public AgentEmailResult get() throws InterruptedException, ExecutionException {
                return null;
            }

            @Override
            public boolean cancel( final boolean mayInterruptIfRunning ) {
                return false;
            }
        };
        emailRequests.add( emailRequest );
        when( gmailService.getGmailService( agentEmail ) ).thenReturn( gmail );
        when( agentEmailBusinessService.sendAgentEmail( emailRequest, gmail, agentEmail ) ).thenReturn( future );
        agentBusinessServiceImpl.sendAgentEmails( agentEmail, emailRequests );
    }

    /**
     * Test send agent emails should throw exception if execution exception
     * occurs.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testSendAgentEmailsShouldThrowExceptionIfExecutionExceptionOccurs() {
        final String agentEmail = "a@a.com";
        final List< EmailRequest > emailRequests = new ArrayList<>();
        final EmailRequest emailRequest = new EmailRequest();
        final AgentEmailResult expected = new AgentEmailResult( null, null );
        final Future< AgentEmailResult > future = new Future< AgentEmailResult >() {

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public AgentEmailResult get( final long timeout, final TimeUnit unit )
                    throws InterruptedException, ExecutionException, TimeoutException {
                throw new ExecutionException( null );
            }

            @Override
            public AgentEmailResult get() throws InterruptedException, ExecutionException {
                return null;
            }

            @Override
            public boolean cancel( final boolean mayInterruptIfRunning ) {
                return false;
            }
        };
        emailRequests.add( emailRequest );
        when( gmailService.getGmailService( agentEmail ) ).thenReturn( gmail );
        when( agentEmailBusinessService.sendAgentEmail( emailRequest, gmail, agentEmail ) ).thenReturn( future );
        agentBusinessServiceImpl.sendAgentEmails( agentEmail, emailRequests );
    }

    /**
     * Test update agent.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testUpdateAgent() throws IOException {
        final AgentOnboardRequest request = new AgentOnboardRequest();
        request.setStatus( "active" );
        request.setPhotoData( "test".getBytes() );
        final User user = new User();
        final List< RoleMember > roleMemberList = new ArrayList<>();
        final RoleMember rm = new RoleMember();
        roleMemberList.add( rm );
        user.setRoleMember( roleMemberList );
        final AgentDetails ad = new AgentDetails();
        final Search search = new Search();
        search.setAgentId( "testAgentId" );
        final String agentEmail = "test";

        when( agentDetailsBuilder.convertFrom( request ) ).thenReturn( new AgentDetails() );
        when( userBuilder.convertFrom( any( AgentOnboardRequest.class ), any( User.class ) ) ).thenReturn( user );
        when( agentDetailsBuilder.convertFrom( any( AgentOnboardRequest.class ), any( AgentDetails.class ) ) )
                .thenReturn( ad );
        when( searchService.searchByAgentEmail( anyString() ) ).thenReturn( search );
        when( agentInfoService.getAgentInfo( anyString() ) ).thenReturn( new AgentInfo() );

        final BaseResponse actual = agentBusinessServiceImpl.updateAgent( agentEmail, request );

        verify( userService ).getUser( "test" );
        assertEquals( actual.getStatus(), SUCCESS );
    }

    /**
     * Test sync agent details should save agent details when eligible to save
     * in db and agent status is active.
     *
     * @param agentSource
     *            the agent source
     * @throws Exception
     *             the exception
     */
    @Test( dataProvider = "agentSourceDataProvider" )
    public void testSyncAgentDetailsShouldSaveAgentDetailsWhenEligibleToSaveInDbAndAgentStatusIsActive(
            final AgentSource agentSource ) throws Exception {
        final AgentOnboardRequest onboardRequest = new AgentOnboardRequest();
        onboardRequest.setStatus( "active" );
        final User user = new User();
        final List< RoleMember > roleMemberList = new ArrayList<>();
        final RoleMember roleMember = new RoleMember();
        final Role role = new Role();
        roleMember.setRole( role );
        roleMemberList.add( roleMember );
        user.setRoleMember( roleMemberList );
        final AgentDetails agentDetails = new AgentDetails();

        when( agentTopicJmxConfig.isEnableAgentDbSync() ).thenReturn( true );
        when( userService.findByEmail( agentSource.getEmail() ) ).thenReturn( null );
        when( recordTypeService.getRecordTypeIdByName( OWNERS_COM.getType(), AGENT.getName() ) )
                .thenReturn( agentSource.getRecordTypeId() );
        when( agentOnboardRequestBuilder.convertTo( agentSource ) ).thenReturn( onboardRequest );
        when( userService.findByEmail( onboardRequest.getEmail() ) ).thenReturn( null );
        when( userBuilder.convertFrom( onboardRequest, null ) ).thenReturn( user );
        when( roleMemberService.save( role ) ).thenReturn( role );
        when( userService.save( user ) ).thenReturn( user );
        when( agentDetailsService.findAgentByEmail( onboardRequest.getEmail() ) ).thenReturn( agentDetails );
        when( agentDetailsBuilder.convertFrom( onboardRequest, agentDetails ) ).thenReturn( agentDetails );
        when( agentDetailsService.save( agentDetails ) ).thenReturn( agentDetails );

        agentBusinessServiceImpl.syncAgentDetails( agentSource );

        verify( agentTopicJmxConfig ).isEnableAgentDbSync();
        verify( userService ).findByEmail( agentSource.getEmail() );
        verify( recordTypeService ).getRecordTypeIdByName( OWNERS_COM.getType(), AGENT.getName() );
        verify( agentOnboardRequestBuilder ).convertTo( agentSource );
        verify( userService ).findByEmail( onboardRequest.getEmail() );
        verify( userBuilder ).convertFrom( onboardRequest, null );
        verify( roleMemberService ).save( role );
        verify( userService ).save( user );
        verify( agentDetailsService ).findAgentByEmail( onboardRequest.getEmail() );
        verify( agentDetailsBuilder ).convertFrom( onboardRequest, agentDetails );
        verify( agentDetailsService ).save( agentDetails );
    }

    /**
     * Test sync agent details should save agent details when eligible to save
     * in db and agent status is inactive.
     *
     * @param agentSource
     *            the agent source
     * @throws Exception
     *             the exception
     */
    @Test( dataProvider = "agentSourceDataProvider" )
    public void testSyncAgentDetailsShouldSaveAgentDetailsWhenEligibleToSaveInDbAndAgentStatusIsInactive(
            final AgentSource agentSource ) throws Exception {
        final AgentOnboardRequest onboardRequest = new AgentOnboardRequest();
        onboardRequest.setStatus( "INACTIVE" );
        final User user = new User();
        final List< RoleMember > roleMemberList = new ArrayList<>();
        final RoleMember roleMember = new RoleMember();
        final Role role = new Role();
        roleMember.setRole( role );
        roleMemberList.add( roleMember );
        user.setRoleMember( roleMemberList );
        final AgentDetails agentDetails = new AgentDetails();

        when( agentTopicJmxConfig.isEnableAgentDbSync() ).thenReturn( true );
        when( userService.findByEmail( agentSource.getEmail() ) ).thenReturn( null );
        when( recordTypeService.getRecordTypeIdByName( OWNERS_COM.getType(), AGENT.getName() ) )
                .thenReturn( agentSource.getRecordTypeId() );
        when( agentOnboardRequestBuilder.convertTo( agentSource ) ).thenReturn( onboardRequest );
        when( userService.findByEmail( onboardRequest.getEmail() ) ).thenReturn( null );
        when( userBuilder.convertFrom( onboardRequest, null ) ).thenReturn( user );
        when( roleMemberService.save( role ) ).thenReturn( role );
        when( userService.save( user ) ).thenReturn( user );
        when( agentDetailsService.findAgentByEmail( onboardRequest.getEmail() ) ).thenReturn( agentDetails );
        when( agentDetailsBuilder.convertFrom( onboardRequest, agentDetails ) ).thenReturn( agentDetails );
        when( agentDetailsService.save( agentDetails ) ).thenReturn( agentDetails );

        agentBusinessServiceImpl.syncAgentDetails( agentSource );

        verify( agentTopicJmxConfig ).isEnableAgentDbSync();
        verify( userService ).findByEmail( agentSource.getEmail() );
        verify( recordTypeService ).getRecordTypeIdByName( OWNERS_COM.getType(), AGENT.getName() );
        verify( agentOnboardRequestBuilder ).convertTo( agentSource );
        verify( userService ).findByEmail( onboardRequest.getEmail() );
        verify( userBuilder ).convertFrom( onboardRequest, null );
        verify( roleMemberService ).save( role );
        verify( userService, Mockito.times( 2 ) ).save( user );
        verify( agentDetailsService ).findAgentByEmail( onboardRequest.getEmail() );
        verify( agentDetailsBuilder ).convertFrom( onboardRequest, agentDetails );
        verify( agentDetailsService ).save( agentDetails );
    }

    /**
     * Test sync agent details should save agent details when field agent flag
     * is true and type is not field agent.
     *
     * @param agentSource
     *            the agent source
     * @throws Exception
     *             the exception
     */
    @Test( dataProvider = "agentSourceDataProvider" )
    public void testSyncAgentDetailsShouldSaveAgentDetailsWhenFieldAgentFlagIsTrueAndTypeIsNotFieldAgent(
            final AgentSource agentSource ) throws Exception {
        agentSource.setAgentType( "Managing Broker" );
        final AgentOnboardRequest onboardRequest = new AgentOnboardRequest();
        onboardRequest.setStatus( "active" );
        final User user = new User();
        final List< RoleMember > roleMemberList = new ArrayList<>();
        final RoleMember roleMember = new RoleMember();
        final Role role = new Role();
        roleMember.setRole( role );
        roleMemberList.add( roleMember );
        user.setRoleMember( roleMemberList );
        final AgentDetails agentDetails = new AgentDetails();

        when( agentTopicJmxConfig.isEnableAgentDbSync() ).thenReturn( true );
        when( userService.findByEmail( agentSource.getEmail() ) ).thenReturn( null );
        when( recordTypeService.getRecordTypeIdByName( OWNERS_COM.getType(), AGENT.getName() ) )
                .thenReturn( agentSource.getRecordTypeId() );
        when( agentOnboardRequestBuilder.convertTo( agentSource ) ).thenReturn( onboardRequest );
        when( userService.findByEmail( onboardRequest.getEmail() ) ).thenReturn( null );
        when( userBuilder.convertFrom( onboardRequest, null ) ).thenReturn( user );
        when( roleMemberService.save( role ) ).thenReturn( role );
        when( userService.save( user ) ).thenReturn( user );
        when( agentDetailsService.findAgentByEmail( onboardRequest.getEmail() ) ).thenReturn( agentDetails );
        when( agentDetailsBuilder.convertFrom( onboardRequest, agentDetails ) ).thenReturn( agentDetails );
        when( agentDetailsService.save( agentDetails ) ).thenReturn( agentDetails );

        agentBusinessServiceImpl.syncAgentDetails( agentSource );

        verify( agentTopicJmxConfig ).isEnableAgentDbSync();
        verify( userService ).findByEmail( agentSource.getEmail() );
        verify( recordTypeService ).getRecordTypeIdByName( OWNERS_COM.getType(), AGENT.getName() );
        verify( agentOnboardRequestBuilder ).convertTo( agentSource );
        verify( userService ).findByEmail( onboardRequest.getEmail() );
        verify( userBuilder ).convertFrom( onboardRequest, null );
        verify( roleMemberService ).save( role );
        verify( userService ).save( user );
        verify( agentDetailsService ).findAgentByEmail( onboardRequest.getEmail() );
        verify( agentDetailsBuilder ).convertFrom( onboardRequest, agentDetails );
        verify( agentDetailsService ).save( agentDetails );
    }

    /**
     * Test sync agent details should not save agent details when agent exists
     * in db.
     *
     * @param agentSource
     *            the agent source
     * @throws Exception
     *             the exception
     */
    @Test( dataProvider = "agentSourceDataProvider" )
    public void testSyncAgentDetailsShouldNotSaveAgentDetailsWhenAgentExistsInDb( final AgentSource agentSource )
            throws Exception {
        when( agentTopicJmxConfig.isEnableAgentDbSync() ).thenReturn( true );
        when( userService.findByEmail( agentSource.getEmail() ) ).thenReturn( new User() );
        when( recordTypeService.getRecordTypeIdByName( OWNERS_COM.getType(), AGENT.getName() ) )
                .thenReturn( agentSource.getRecordTypeId() );

        agentBusinessServiceImpl.syncAgentDetails( agentSource );

        verify( agentTopicJmxConfig ).isEnableAgentDbSync();
        verify( userService ).findByEmail( agentSource.getEmail() );
        verify( recordTypeService ).getRecordTypeIdByName( OWNERS_COM.getType(), AGENT.getName() );
        verifyZeroInteractions( agentOnboardRequestBuilder );
    }

    /**
     * Test sync agent details should not save agent details when agent record
     * type is not owners.
     *
     * @param agentSource
     *            the agent source
     * @throws Exception
     *             the exception
     */
    @Test( dataProvider = "agentSourceDataProvider" )
    public void testSyncAgentDetailsShouldNotSaveAgentDetailsWhenAgentRecordTypeIsNotOwners(
            final AgentSource agentSource ) throws Exception {
        when( agentTopicJmxConfig.isEnableAgentDbSync() ).thenReturn( true );
        when( recordTypeService.getRecordTypeIdByName( OWNERS_COM.getType(), AGENT.getName() ) )
                .thenReturn( "testDifferentRecordTypeId" );

        agentBusinessServiceImpl.syncAgentDetails( agentSource );

        verify( agentTopicJmxConfig ).isEnableAgentDbSync();
        verifyZeroInteractions( userService );
        verify( recordTypeService ).getRecordTypeIdByName( OWNERS_COM.getType(), AGENT.getName() );
        verifyZeroInteractions( agentOnboardRequestBuilder );
    }

    /**
     * Test sync agent details should not save agent details when field agent
     * flag is false and type is not field agent.
     *
     * @param agentSource
     *            the agent source
     * @throws Exception
     *             the exception
     */
    @Test( dataProvider = "agentSourceDataProvider" )
    public void testSyncAgentDetailsShouldNotSaveAgentDetailsWhenFieldAgentFlagIsFalseAndTypeIsNotFieldAgent(
            final AgentSource agentSource ) throws Exception {
        agentSource.setFieldAgent( false );
        agentSource.setAgentType( "managing broker" );

        when( agentTopicJmxConfig.isEnableAgentDbSync() ).thenReturn( true );

        agentBusinessServiceImpl.syncAgentDetails( agentSource );

        verify( agentTopicJmxConfig ).isEnableAgentDbSync();
        verifyZeroInteractions( userService );
        verifyZeroInteractions( recordTypeService );
        verifyZeroInteractions( agentOnboardRequestBuilder );
    }

    /**
     * Test sync agent details should save agent details when field agent flag
     * is true and jmx is disbaled.
     *
     * @param agentSource
     *            the agent source
     * @throws Exception
     *             the exception
     */
    @Test( dataProvider = "agentSourceDataProvider" )
    public void testSyncAgentDetailsShouldSaveAgentDetailsWhenFieldAgentFlagIsTrueAndJmxIsDisbaled(
            final AgentSource agentSource ) throws Exception {
        when( agentTopicJmxConfig.isEnableAgentDbSync() ).thenReturn( false );
        agentBusinessServiceImpl.syncAgentDetails( agentSource );
        verify( agentTopicJmxConfig ).isEnableAgentDbSync();
        verifyZeroInteractions( userService );
        verifyZeroInteractions( recordTypeService );
        verifyZeroInteractions( agentOnboardRequestBuilder );
    }

    /**
     * Test get crm agent should return agent when agent available.
     */
    @Test
    public void testGetCRMAgentShouldReturnAgentWhenAgentAvailable() {
        final String agentId = "testId";
        when( agentService.getCRMAgentById( agentId ) ).thenReturn( new CRMAgentResponse() );
        final CRMAgentResponse crmAgent = agentBusinessServiceImpl.getCRMAgentById( agentId );
        assertNotNull( crmAgent );
        verify( agentService ).getCRMAgentById( agentId );
    }

    /**
     * Test get crm agent should return null when agent not available.
     */
    @Test
    public void testGetCRMAgentShouldReturnNullWhenAgentNotAvailable() {
        final String agentId = "testId";
        when( agentService.getCRMAgentById( agentId ) ).thenReturn( null );
        final CRMAgentResponse crmAgent = agentBusinessServiceImpl.getCRMAgentById( agentId );
        assertNull( crmAgent );
        verify( agentService ).getCRMAgentById( agentId );
    }

    /**
     * Gets the agent source.
     *
     * @return the agent source
     */
    @DataProvider( name = "agentSourceDataProvider" )
    private Object[][] getAgentSource() {
        final AgentSource agentSource = new AgentSource();
        agentSource.setEmail( "test@email.com" );
        agentSource.setRecordTypeId( "testRecordTypeId" );
        agentSource.setFieldAgent( true );
        agentSource.setAgentType( "Field Agent" );
        return new Object[][] { { agentSource } };
    }

    /**
     * Test get CRM agent by email.
     */
    @Test
    public void testGetCRMAgentByEmail() {
        final String email = "test@test.com";
        final AgentSource agentSource = new AgentSource();
        final Map< String, Object > crmAgents = new HashMap<>();
        crmAgents.put( "test", "test" );
        when( agentService.getAgentDetails( email ) ).thenReturn( crmAgents );
        when( agentSourceResponseBuilder.convertTo( crmAgents ) ).thenReturn( agentSource );
        final AgentSource source = agentBusinessServiceImpl.getCRMAgentByEmail( email );
        assertNotNull( source );
        assertEquals( source, agentSource );
        verify( agentService ).getAgentDetails( email );
        verify( agentSourceResponseBuilder ).convertTo( crmAgents );
    }

    /**
     * Test update action with map not null.
     */
    @Test
    public void testUpdateAction_WithMapNotNull() {
        ReflectionTestUtils.setField( agentBusinessServiceImpl, "F2F_MEETING_ACTION_ID", "3" );
        final Map< String, Object > actionMap = new HashMap<>();
        final String agentId = "test";
        final String actionGroupId = "test";
        final String actionId = "0";
        final OpportunityAction action = new OpportunityAction();
        final Action act = new Action();
        act.setOrder( "1" );
        action.setAction( act );
        action.setActionId( "0" );
        final List< OpportunityAction > list = new ArrayList<>();
        list.add( action );
        when( actionGroupService.getStartTime( actionGroupId ) ).thenReturn( list );

        agentBusinessServiceImpl.updateAction( agentId, actionGroupId, actionId, actionMap );
        verify( actionGroupService ).patchAgentActionInfo( agentId, actionGroupId, actionId, actionMap );
    }

    /**
     * Test update action with map null.
     */
    @Test
    public void testUpdateAction_WithMapNull() {
        ReflectionTestUtils.setField( agentBusinessServiceImpl, "F2F_MEETING_ACTION_ID", "3" );
        final Map< String, Object > actionMap = null;
        final String agentId = "test";
        final String actionGroupId = "test";
        final String actionId = "0";
        final OpportunityAction action = new OpportunityAction();
        final Action act = new Action();
        act.setOrder( "1" );
        action.setAction( act );
        action.setActionId( "0" );
        final List< OpportunityAction > list = new ArrayList<>();
        list.add( action );
        when( actionGroupService.getStartTime( actionGroupId ) ).thenReturn( list );
        agentBusinessServiceImpl.updateAction( agentId, actionGroupId, actionId, actionMap );
    }

    /**
     * Test update action with map.
     */
    @Test
    public void testUpdateAction_WithMap() {
        ReflectionTestUtils.setField( agentBusinessServiceImpl, "F2F_MEETING_ACTION_ID", "3" );
        final Map< String, Object > actionMap = new HashMap<>();
        actionMap.put( "complete", true );
        final String agentId = "test";
        final String actionGroupId = "test";
        final String actionId = "0";
        final OpportunityAction action = new OpportunityAction();
        final Action act = new Action();
        act.setOrder( "1" );
        action.setAction( act );
        action.setActionId( "0" );
        final List< OpportunityAction > list = new ArrayList<>();
        list.add( action );

        when( actionGroupService.getStartTime( actionGroupId ) ).thenReturn( list );
        agentBusinessServiceImpl.updateAction( agentId, actionGroupId, actionId, actionMap );
        verify( actionGroupService ).patchAgentActionInfo( agentId, actionGroupId, actionId, actionMap );
    }

    /**
     * Test update action_ with map not null and action flow complete.
     */
    @Test
    public void testUpdateAction_WithMapNotNullAndActionFlowComplete() {
        ReflectionTestUtils.setField( agentBusinessServiceImpl, "F2F_MEETING_ACTION_ID", "3" );
        final Map< String, Object > actionMap = new HashMap<>();
        final String agentId = "test";
        final String actionGroupId = "test";
        final String actionId = "0";
        final OpportunityAction action = new OpportunityAction();
        final Action act = new Action();
        act.setOrder( "3" );
        action.setAction( act );
        action.setActionId( "0" );
        action.setCompleted( true );
        final List< OpportunityAction > list = new ArrayList<>();
        list.add( action );
        when( actionGroupService.getStartTime( actionGroupId ) ).thenReturn( list );

        agentBusinessServiceImpl.updateAction( agentId, actionGroupId, actionId, actionMap );
        verify( actionGroupService ).patchAgentActionInfo( agentId, actionGroupId, actionId, actionMap );
    }

    /**
     * Test update action_ with map null and action flow complete.
     */
    @Test
    public void testUpdateAction_WithMapNullAndActionFlowComplete() {
        ReflectionTestUtils.setField( agentBusinessServiceImpl, "F2F_MEETING_ACTION_ID", "3" );
        final Map< String, Object > actionMap = null;
        final String agentId = "test";
        final String actionGroupId = "test";
        final String actionId = "0";
        final OpportunityAction action = new OpportunityAction();
        final Action act = new Action();
        act.setOrder( "3" );
        action.setAction( act );
        action.setActionId( "0" );
        action.setCompleted( true );
        final List< OpportunityAction > list = new ArrayList<>();
        list.add( action );
        when( actionGroupService.getStartTime( actionGroupId ) ).thenReturn( list );
        agentBusinessServiceImpl.updateAction( agentId, actionGroupId, actionId, actionMap );
    }

    /**
     * Test update action_ with map and action flow complete.
     */
    @Test
    public void testUpdateAction_WithMapAndActionFlowComplete() {
        ReflectionTestUtils.setField( agentBusinessServiceImpl, "F2F_MEETING_ACTION_ID", "3" );
        final Map< String, Object > actionMap = new HashMap<>();
        actionMap.put( "complete", true );
        final String agentId = "test";
        final String actionGroupId = "test";
        final String actionId = "0";
        final OpportunityAction action = new OpportunityAction();
        final Action act = new Action();
        act.setOrder( "3" );
        action.setAction( act );
        action.setActionId( "0" );
        action.setCompleted( true );
        final List< OpportunityAction > list = new ArrayList<>();
        list.add( action );

        when( actionGroupService.getStartTime( actionGroupId ) ).thenReturn( list );
        agentBusinessServiceImpl.updateAction( agentId, actionGroupId, actionId, actionMap );
        verify( actionGroupService ).patchAgentActionInfo( agentId, actionGroupId, actionId, actionMap );
    }

    /**
     * Test sync agent score.
     */
    @Test
    public void testSyncAgentScore() {
        doNothing().when( agentService ).syncAgentScore();
        agentBusinessServiceImpl.syncAgentScore();
        verify( agentService ).syncAgentScore();
    }

    /**
     * Test create agent suggested property.
     */
    @Test
    public void testCreateAgentSuggestedProperty() {

        when( agentSuggestedPropertyService.saveAgentSuggestedProperty( any(), any() ) )
                .thenReturn( new PostResponse() );
        final AgentResponse response = agentBusinessServiceImpl.createAgentSuggestedProperty( "test",
                new AgentSuggestedPropertyRequest() );
        assertEquals( response.getStatus(), SUCCESS );
    }

    /**
     * Test save agent signature with string input.
     */
    @Test
    public void testSaveAgentSignatureWithString() {
        BaseResponse response = null;
        final String request = "foo bar";
        when( agentInfoService.getAgentInfo( Mockito.anyString() ) ).thenReturn( new AgentInfo() );
        response = agentBusinessServiceImpl.saveAgentSignature( "Dmla42", request );
        Assert.assertNotNull( response );
        Assert.assertEquals( response.getStatus(), Status.SUCCESS );
    }

    /**
     * Test save agent signature with any object input.
     */
    @Test
    public void testSaveAgentSignatureWithAnyObject() {
        BaseResponse response = null;
        final Map< String, String > request = new HashMap<>();
        request.put( "html", "<p>foo</p><p>bar</p>" );
        request.put( "text", "nbar" );
        when( agentInfoService.getAgentInfo( Mockito.anyString() ) ).thenReturn( new AgentInfo() );
        response = agentBusinessServiceImpl.saveAgentSignature( "Dmla42", request );
        Assert.assertNotNull( response );
        Assert.assertEquals( response.getStatus(), Status.SUCCESS );
    }

    /**
     * Test save agent signature with boolean input.
     */
    @Test
    public void testSaveAgentSignatureWithBoolean() {
        BaseResponse response = null;
        final boolean request = true;
        when( agentInfoService.getAgentInfo( Mockito.anyString() ) ).thenReturn( new AgentInfo() );
        response = agentBusinessServiceImpl.saveAgentSignature( "Dmla42", request );
        Assert.assertNotNull( response );
        Assert.assertEquals( response.getStatus(), Status.SUCCESS );
    }

    /**
     * Test save agent signature with number input.
     */
    @Test
    public void testSaveAgentSignatureWithNumber() {
        BaseResponse response = null;
        final int request = 1000;
        when( agentInfoService.getAgentInfo( Mockito.anyString() ) ).thenReturn( new AgentInfo() );
        response = agentBusinessServiceImpl.saveAgentSignature( "Dmla42", request );
        Assert.assertNotNull( response );
        Assert.assertEquals( response.getStatus(), Status.SUCCESS );
    }

    /**
     * Test create update agent signature when agent does not exists.
     */
    @Test
    public void testSaveAgentSignatureWithNoAgentExist() {
        BaseResponse response = null;
        final Object request = null;
        when( agentInfoService.getAgentInfo( Mockito.anyString() ) ).thenReturn( null );
        response = agentBusinessServiceImpl.saveAgentSignature( "notExists", request );
        Assert.assertNotNull( response );
        Assert.assertEquals( response.getStatus(), Status.FAILURE );
    }

    /**
     * to test addDataNode with valid key
     */
    @Test
    public void testAddDataNode_withValidKey() {
        ReflectionTestUtils.setField( agentBusinessServiceImpl, "validKeysForOppsDataNode", "key1" );
        when( opportunityDataNodeService.getDataNode( "agentId", "OppsId", "key1" ) ).thenReturn( null );
        final PostResponse postResponse = new PostResponse();
        postResponse.setStatus( Status.SUCCESS );
        when( opportunityDataNodeService.addDataNode( Mockito.any( OpportunityDataNode.class ), anyString(),
                anyString(), anyString() ) ).thenReturn( postResponse );
        final OpportunityDataRequest opportunityDataRequest = new OpportunityDataRequest();
        opportunityDataRequest.setKey( "key1" );
        final AgentResponse response = agentBusinessServiceImpl.addDataNode( "agentId", "OppsId",
                opportunityDataRequest );
        Assert.assertEquals( response.getStatus(), Status.SUCCESS );

    }

    /**
     * To test AddDataNode withoutKey
     * 
     * @throws Exception
     */
    @Test( expectedExceptions = Exception.class )
    public void testAddDataNode_withoutKey() throws Exception {
        ReflectionTestUtils.setField( agentBusinessServiceImpl, "validKeysForOppsDataNode", "key1" );
        when( opportunityDataNodeService.getDataNode( "agentId", "OppsId", "key1" ) ).thenReturn( null );
        final OpportunityDataRequest opportunityDataRequest = new OpportunityDataRequest();
        final AgentResponse response = agentBusinessServiceImpl.addDataNode( "agentId", "OppsId",
                opportunityDataRequest );
    }

    /**
     * To test AddDataNode with wrong key
     * 
     * @throws Exception
     */
    @Test( expectedExceptions = Exception.class )
    public void testAddDataNode_withWrongKey() throws Exception {
        ReflectionTestUtils.setField( agentBusinessServiceImpl, "validKeysForOppsDataNode", "key1" );
        when( opportunityDataNodeService.getDataNode( "agentId", "OppsId", "key1" ) ).thenReturn( null );
        final OpportunityDataRequest opportunityDataRequest = new OpportunityDataRequest();
        opportunityDataRequest.setKey( "key222" );
        final AgentResponse response = agentBusinessServiceImpl.addDataNode( "agentId", "OppsId",
                opportunityDataRequest );
    }

    /**
     * to test addDataNode with valid key
     */
    @Test
    public void testGetDataNode() {
        ReflectionTestUtils.setField( agentBusinessServiceImpl, "validKeysForOppsDataNode", "key1" );
        final OpportunityDataNode dummyResponse = new OpportunityDataNode();
        when( opportunityDataNodeService.getDataNode( "agentId", "OppsId", "key1" ) ).thenReturn( dummyResponse );
        final OpportunityDataRequest opportunityDataRequest = new OpportunityDataRequest();
        opportunityDataRequest.setKey( "key1" );
        final OpportunityDataNode response = agentBusinessServiceImpl.getDataNode( "agentId", "OppsId", "key1" );
        Assert.assertNotNull( response );

    }

    @Test
    public void testSaveAgentPreferencesData_invalidAgent() {
        BaseResponse response = null;
        final Map< String, Object > request = new HashMap<>();
        when( agentInfoService.getAgentInfo( Mockito.anyString() ) ).thenReturn( null );
        response = agentBusinessServiceImpl.saveAgentPreferencesData( "agent123", "data.config.sms", true, request );
        Assert.assertNotNull( response );
        Assert.assertEquals( response.getStatus(), Status.FAILURE );
    }

    @Test
    public void testSaveAgentPreferencesData_agentSpecific() {
        BaseResponse response = null;
        final Map< String, Object > request = new HashMap<>();
        request.put( "title", "Hi" );
        request.put( "msg", "test msg" );
        when( agentInfoService.getAgentInfo( Mockito.anyString() ) ).thenReturn( new AgentInfo() );
        response = agentBusinessServiceImpl.saveAgentPreferencesData( "agent123", "data.config.sms", true, request );
        Assert.assertNotNull( response );
        Assert.assertEquals( response.getStatus(), Status.SUCCESS );
    }

    @Test
    public void testSaveAgentPreferencesData_notAgentSpecific() {
        BaseResponse response = null;
        final Map< String, Object > request = new HashMap<>();
        request.put( "subject", "hello" );
        request.put( "body", "text email" );
        when( agentInfoService.getAgentInfo( Mockito.anyString() ) ).thenReturn( new AgentInfo() );
        response = agentBusinessServiceImpl.saveAgentPreferencesData( "agent123", "data.config.email", false, request );
        Assert.assertNotNull( response );
        Assert.assertEquals( response.getStatus(), Status.SUCCESS );
    }

}
