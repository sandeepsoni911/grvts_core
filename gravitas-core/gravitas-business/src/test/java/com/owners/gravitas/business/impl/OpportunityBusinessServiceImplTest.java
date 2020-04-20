/*
 *
 */
package com.owners.gravitas.business.impl;

import static com.owners.gravitas.constants.Constants.QUERY_PARAM_ID;
import static com.owners.gravitas.enums.CRMObject.OPPORTUNITY;
import static com.owners.gravitas.enums.LeadRequestType.REQUEST_INFORMATION;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.amqp.OpportunityContact;
import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.ActionFlowBusinessService;
import com.owners.gravitas.business.ActionLogBusinessService;
import com.owners.gravitas.business.AgentNotificationBusinessService;
import com.owners.gravitas.business.AgentOpportunityBusinessService;
import com.owners.gravitas.business.AgentTaskBusinessService;
import com.owners.gravitas.business.builder.OpportunityContactBuilder;
import com.owners.gravitas.business.builder.OpportunitySourceBuilder;
import com.owners.gravitas.business.builder.domain.BuyerRequestBuilder;
import com.owners.gravitas.business.builder.request.BuyerOpportunityRequestBuilder;
import com.owners.gravitas.business.builder.request.CRMAccountRequestBuilder;
import com.owners.gravitas.business.builder.request.CRMContactRequestBuilder;
import com.owners.gravitas.business.builder.request.ReferralExchangeOpportunityRequestBuilder;
import com.owners.gravitas.business.builder.request.ReferralExchangeRequestEmailBuilder;
import com.owners.gravitas.business.builder.request.SellerOpportunityRequestBuilder;
import com.owners.gravitas.business.builder.request.SlackRequestBuilder;
import com.owners.gravitas.business.task.AgentLookupTask;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.ActionFlowConfig;
import com.owners.gravitas.config.AgentOpportunityBusinessConfig;
import com.owners.gravitas.config.CoShoppingConfig;
import com.owners.gravitas.config.HappyAgentsConfig;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.domain.Request;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.domain.entity.ActionLog;
import com.owners.gravitas.dto.ActionLogDto;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.OpportunityDetails;
import com.owners.gravitas.dto.Property;
import com.owners.gravitas.dto.PropertyOrder;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.dto.Seller;
import com.owners.gravitas.dto.crm.request.CRMAccountRequest;
import com.owners.gravitas.dto.crm.request.CRMContactRequest;
import com.owners.gravitas.dto.crm.request.CRMOpportunityContactRoleRequest;
import com.owners.gravitas.dto.crm.request.CRMOpportunityRequest;
import com.owners.gravitas.dto.crm.response.AccountResponse;
import com.owners.gravitas.dto.crm.response.CRMOpportunityResponse;
import com.owners.gravitas.dto.crm.response.CRMResponse;
import com.owners.gravitas.dto.crm.response.ContactResponse;
import com.owners.gravitas.dto.crm.response.OpportunityContactRoleResponse;
import com.owners.gravitas.dto.crm.response.OpportunityResponse;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.dto.request.NotificationRequest;
import com.owners.gravitas.dto.request.OpportunityRequest;
import com.owners.gravitas.dto.request.ReferralExchangeRequest;
import com.owners.gravitas.dto.request.ScheduleTourLeadRequest;
import com.owners.gravitas.dto.request.ScheduleTourMeetingRequest;
import com.owners.gravitas.dto.request.SlackRequest;
import com.owners.gravitas.dto.response.AgentResponse;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.NotificationResponse;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.enums.RecordType;
import com.owners.gravitas.exception.ResultNotFoundException;
import com.owners.gravitas.service.AccountService;
import com.owners.gravitas.service.ActionLogService;
import com.owners.gravitas.service.AgentAssignmentLogService;
import com.owners.gravitas.service.AgentOpportunityService;
import com.owners.gravitas.service.AgentRequestService;
import com.owners.gravitas.service.CRMQueryService;
import com.owners.gravitas.service.ContactService;
import com.owners.gravitas.service.MailService;
import com.owners.gravitas.service.OpportunityContactRoleService;
import com.owners.gravitas.service.OpportunityService;
import com.owners.gravitas.service.RecordTypeService;
import com.owners.gravitas.service.ReferralExchangeService;
import com.owners.gravitas.service.SearchService;
import com.owners.gravitas.service.SlackService;
import com.owners.gravitas.util.GravitasWebUtil;
import com.zuner.coshopping.model.enums.LeadRequestType;

/**
 * The Class SellerBusinessServiceImplTest.
 *
 * @author harshads
 */
public class OpportunityBusinessServiceImplTest extends AbstractBaseMockitoTest {

    /** The seller business service impl test. */
    @InjectMocks
    private OpportunityBusinessServiceImpl opportunityBusinessServiceImpl;

    /** The referral exchange opportunity request builder. */
    @Mock
    private ReferralExchangeOpportunityRequestBuilder referralExchangeOpportunityRequestBuilder;

    /** The referral exchange service. */
    @Mock
    private ReferralExchangeService referralExchangeService;

    /** The referral exchange request email builder. */
    @Mock
    private ReferralExchangeRequestEmailBuilder referralExchangeRequestEmailBuilder;

    /** The mail service. */
    @Mock
    private MailService mailService;

    /** The agent opportunity business config. */
    @Mock
    private AgentOpportunityBusinessConfig agentOpportunityBusinessConfig;

    /** The record type service. */
    @Mock
    private RecordTypeService recordTypeService;

    /** The crm service. */
    @Mock
    private CRMQueryService crmQueryService;

    /** The opportunity service. */
    @Mock
    private OpportunityService opportunityService;

    /** The opportunity contact role service. */
    @Mock
    private OpportunityContactRoleService opportunityContactRoleService;

    /** The account service. */
    @Mock
    private AccountService accountService;

    /** The contact service. */
    @Mock
    private ContactService contactService;

    /** The contact request builder. */
    @Mock
    private CRMContactRequestBuilder crmContactRequestBuilder;

    /** The crm account request builder. */
    @Mock
    private CRMAccountRequestBuilder crmAccountRequestBuilder;

    /** The crm opportunity request builder. */
    @Mock
    private SellerOpportunityRequestBuilder crmOpportunityRequestBuilder;

    /** The buyer opportunity request builder. */
    @Mock
    private BuyerOpportunityRequestBuilder buyerOpportunityRequestBuilder;

    /** The slack publish request builder. */
    @Mock
    private SlackRequestBuilder slackRequestBuilder;

    /** The slack service. */
    @Mock
    private SlackService slackService;

    /** The opportunity source builder. */
    @Mock
    private OpportunitySourceBuilder opportunitySourceBuilder;

    /** The opportunity contact builder. */
    @Mock
    private OpportunityContactBuilder opportunityContactBuilder;

    /** The buyer request builder. */
    @Mock
    private BuyerRequestBuilder buyerRequestBuilder;

    /** The request service. */
    @Mock
    private AgentRequestService requestService;

    /** The agent notification business service. */
    @Mock
    private AgentNotificationBusinessService agentNotificationBusinessService;

    /** The action log business service. */
    @Mock
    private ActionLogBusinessService actionLogBusinessService;

    /** The agent opportunity service. */
    @Mock
    private AgentOpportunityService agentOpportunityService;

    /** The agent lookup task. */
    @Mock
    private AgentLookupTask agentLookupTask;

    /** The gravitas web util. */
    @Mock
    private GravitasWebUtil gravitasWebUtil;

    /** The agent opportunity business service. */
    @Mock
    private AgentOpportunityBusinessService agentOpportunityBusinessService;

    /** The agent assignment log service. */
    @Mock
    private AgentAssignmentLogService agentAssignmentLogService;

    /** The happy agents config. */
    @Mock
    private HappyAgentsConfig happyAgentsConfig;

    /** The agent task business service. */
    @Mock
    private AgentTaskBusinessService agentTaskBusinessService;
    
    /** The CoShoppingConfig service. */
    @Mock
    private CoShoppingConfig coShoppingConfig;
    
    @Mock
    private SearchService searchService;
    
    @Mock
    private ActionFlowBusinessService actionFlowBusinessService ;
    
    @Mock
    private ActionLogService actionLogService;
    
    @InjectMocks
    private ActionFlowConfig actionFlowConfig;

    /**
     * Creates the opportunity with account no exists test.
     */
    @Test
    public void createOpportunityWithAccountNoExistsTest() {

        Mockito.when( slackRequestBuilder.convertTo( Mockito.any( OpportunityRequest.class ) ) )
                .thenReturn( new SlackRequest() );

        Mockito.doNothing().when( slackService ).publishToSlack( Mockito.any( SlackRequest.class ),
                Mockito.anyString() );

        Mockito.doNothing().when( opportunityService ).isOpportunityExists( Mockito.anyString() );

        Mockito.when( crmAccountRequestBuilder.convertTo( Mockito.any( OpportunityRequest.class ) ) )
                .thenReturn( new CRMAccountRequest() );

        Mockito.when( crmQueryService.findOne( Mockito.anyString(), Mockito.any( QueryParams.class ) ) )
                .thenThrow( new ResultNotFoundException( "Test" ) ).thenReturn( new HashMap< String, Object >() )
                .thenReturn( new HashMap< String, Object >() );
        Mockito.when( accountService.createAccount( Mockito.any( CRMAccountRequest.class ) ) )
                .thenReturn( new AccountResponse() );

        Mockito.when( crmContactRequestBuilder.convertTo( Mockito.any( OpportunityRequest.class ) ) )
                .thenReturn( new CRMContactRequest() );
        Mockito.when( contactService.createContact( Mockito.any( CRMContactRequest.class ) ) )
                .thenReturn( new ContactResponse() );

        Mockito.when( crmOpportunityRequestBuilder.convertTo( Mockito.any( OpportunityRequest.class ) ) )
                .thenReturn( new CRMOpportunityRequest() );
        Mockito.when( opportunityService.createOpportunity( Mockito.any( CRMOpportunityRequest.class ) ) )
                .thenReturn( new OpportunityResponse() );

        Mockito.when( opportunityContactRoleService
                .createOpportunityContactRole( ( Mockito.any( CRMOpportunityContactRoleRequest.class ) ) ) )
                .thenReturn( new OpportunityContactRoleResponse() );

        final OpportunityRequest request = new OpportunityRequest();
        final PropertyOrder propertyOrder = new PropertyOrder();

        final Property property = new Property();
        property.setListingId( "listingId" );

        propertyOrder.setProperty( property );

        request.setPropertyOrder( propertyOrder );

        request.setSeller( new Seller() );

        opportunityBusinessServiceImpl.createSellerOpportunity( request, RecordType.MLS );

        Mockito.verify( accountService ).createAccount( Mockito.any( CRMAccountRequest.class ) );
        Mockito.verify( crmQueryService, VerificationModeFactory.times( 2 ) ).findOne( Mockito.anyString(),
                Mockito.any( QueryParams.class ) );

    }

    /**
     * Creates the opportunity with account no exists test.
     */
    @Test
    public void createOpportunityWithAccountNoExistsTestWithFirstName() {

        Mockito.when( slackRequestBuilder.convertTo( Mockito.any( OpportunityRequest.class ) ) )
                .thenReturn( new SlackRequest() );

        Mockito.doNothing().when( slackService ).publishToSlack( Mockito.any( SlackRequest.class ),
                Mockito.anyString() );

        Mockito.doNothing().when( opportunityService ).isOpportunityExists( Mockito.anyString() );

        Mockito.when( crmAccountRequestBuilder.convertTo( Mockito.any( OpportunityRequest.class ) ) )
                .thenReturn( new CRMAccountRequest() );

        Mockito.when( crmQueryService.findOne( Mockito.anyString(), Mockito.any( QueryParams.class ) ) )
                .thenThrow( new ResultNotFoundException( "Test" ) ).thenReturn( new HashMap< String, Object >() )
                .thenReturn( new HashMap< String, Object >() );
        Mockito.when( accountService.createAccount( Mockito.any( CRMAccountRequest.class ) ) )
                .thenReturn( new AccountResponse() );

        Mockito.when( crmContactRequestBuilder.convertTo( Mockito.any( OpportunityRequest.class ) ) )
                .thenReturn( new CRMContactRequest() );
        Mockito.when( contactService.createContact( Mockito.any( CRMContactRequest.class ) ) )
                .thenReturn( new ContactResponse() );

        Mockito.when( crmOpportunityRequestBuilder.convertTo( Mockito.any( OpportunityRequest.class ) ) )
                .thenReturn( new CRMOpportunityRequest() );
        Mockito.when( opportunityService.createOpportunity( Mockito.any( CRMOpportunityRequest.class ) ) )
                .thenReturn( new OpportunityResponse() );

        Mockito.when( opportunityContactRoleService
                .createOpportunityContactRole( ( Mockito.any( CRMOpportunityContactRoleRequest.class ) ) ) )
                .thenReturn( new OpportunityContactRoleResponse() );

        final OpportunityRequest request = new OpportunityRequest();
        final PropertyOrder propertyOrder = new PropertyOrder();

        final Property property = new Property();
        property.setListingId( "listingId" );

        propertyOrder.setProperty( property );

        request.setPropertyOrder( propertyOrder );
        final Seller seller = new Seller();
        seller.setFirstName( "test" );
        request.setSeller( seller );

        opportunityBusinessServiceImpl.createSellerOpportunity( request, RecordType.MLS );

        Mockito.verify( accountService ).createAccount( Mockito.any( CRMAccountRequest.class ) );
        Mockito.verify( crmQueryService, VerificationModeFactory.times( 2 ) ).findOne( Mockito.anyString(),
                Mockito.any( QueryParams.class ) );

    }

    /**
     * Creates the opportunity with account exists test.
     */
    @Test
    public void createOpportunityWithAccountExistsTest() {

        Mockito.when( slackRequestBuilder.convertTo( Mockito.any( OpportunityRequest.class ) ) )
                .thenReturn( new SlackRequest() );

        Mockito.doNothing().when( slackService ).publishToSlack( Mockito.any( SlackRequest.class ),
                Mockito.anyString() );

        Mockito.doNothing().when( opportunityService ).isOpportunityExists( Mockito.anyString() );

        final Map< String, Object > requestMap = new HashMap< String, Object >();
        requestMap.put( "Id", "testid" );

        Mockito.when( crmQueryService.findOne( Mockito.anyString(), Mockito.any( QueryParams.class ) ) )
                .thenReturn( requestMap );

        Mockito.when( crmAccountRequestBuilder.convertTo( Mockito.any( OpportunityRequest.class ) ) )
                .thenReturn( new CRMAccountRequest() );
        Mockito.when( accountService.createAccount( Mockito.any( CRMAccountRequest.class ) ) )
                .thenReturn( new AccountResponse() );

        Mockito.when( crmContactRequestBuilder.convertTo( Mockito.any( OpportunityRequest.class ) ) )
                .thenReturn( new CRMContactRequest() );
        Mockito.when( contactService.createContact( Mockito.any( CRMContactRequest.class ) ) )
                .thenReturn( new ContactResponse() );

        Mockito.when( crmOpportunityRequestBuilder.convertTo( Mockito.any( OpportunityRequest.class ) ) )
                .thenReturn( new CRMOpportunityRequest() );
        Mockito.when( opportunityService.createOpportunity( Mockito.any( CRMOpportunityRequest.class ) ) )
                .thenReturn( new OpportunityResponse() );

        Mockito.when( opportunityContactRoleService
                .createOpportunityContactRole( ( Mockito.any( CRMOpportunityContactRoleRequest.class ) ) ) )
                .thenReturn( new OpportunityContactRoleResponse() );

        final OpportunityRequest request = new OpportunityRequest();
        final PropertyOrder propertyOrder = new PropertyOrder();

        final Property property = new Property();
        property.setListingId( "listingId" );
        propertyOrder.setProperty( property );
        request.setPropertyOrder( propertyOrder );
        final Seller seller = new Seller();
        request.setSeller( seller );

        opportunityBusinessServiceImpl.createSellerOpportunity( request, RecordType.MLS );

        Mockito.verifyZeroInteractions( accountService );
        Mockito.verify( crmQueryService, VerificationModeFactory.times( 2 ) ).findOne( Mockito.anyString(),
                Mockito.any( QueryParams.class ) );
    }

    /**
     * Test update opportuity.
     */
    @Test
    public void testUpdateOpportuity() {
        Mockito.when( opportunityService.getOpportunity( Mockito.anyString() ) )
                .thenReturn( new CRMOpportunityResponse() );
        Mockito.when( buyerOpportunityRequestBuilder.convertTo( new LeadRequest() ) )
                .thenReturn( new CRMOpportunityRequest() );
        final OpportunityResponse response = opportunityBusinessServiceImpl.updateOpportunity( new LeadRequest(),
                "opportunityId " );

        Assert.assertNotNull( response );
        Mockito.verify( opportunityService ).updateOpportunity( Mockito.any( CRMOpportunityRequest.class ),
                Mockito.anyString(), anyBoolean() );
    }

    /**
     * Test get opportunity.
     */
    @Test
    public void testGetOpportunity() {
        final String opportunityId = "test";
        Mockito.when( opportunitySourceBuilder.convertTo( Mockito.any() ) ).thenReturn( null );
        Mockito.when( opportunityContactBuilder.convertTo( Mockito.any() ) ).thenReturn( new OpportunityContact() );
        final OpportunitySource opportunitySource = opportunityBusinessServiceImpl.getOpportunity( opportunityId );
        Assert.assertNotNull( opportunitySource );
    }

    /**
     * Test get opportunities.
     */
    @Test
    public void testGetOpportunities() {
        final String opportunityId = "test";
        final CRMResponse crmResponse = opportunityBusinessServiceImpl.getAgentOpportunities( opportunityId );
        Mockito.verify( crmQueryService ).findAll( Mockito.any(), Mockito.any() );
    }

    /**
     * Test create opportunity buyer request.
     */
    @Test
    public void testCreateOpportunityBuyerRequest() {
        final LeadRequest request = new LeadRequest();
        request.setRequestType( REQUEST_INFORMATION.name() );
        final PostResponse response = new PostResponse();
        response.setName( "test" );
        final Opportunity opportunity = new Opportunity();
        opportunity.setDeleted( false );
        
        when( coShoppingConfig.isEnableBuyerOpportunityScheduleTour()).thenReturn(false);
        when( agentOpportunityService.getOpportunityById( anyString(), anyString() ) ).thenReturn( opportunity );
        Mockito.when( buyerRequestBuilder.convertTo( Mockito.any() ) ).thenReturn( new Request() );
        Mockito.when( agentTaskBusinessService.createBuyerTask( Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any() ) ).thenReturn( response );
        Mockito.when( requestService.saveRequest( Mockito.any(), Mockito.any() ) ).thenReturn( response );
        Mockito.doNothing().when( actionLogBusinessService ).auditForAction( Mockito.any( ActionLogDto.class ) );
        opportunityBusinessServiceImpl.processBuyerRequest( request, "", "" );
        verify( agentNotificationBusinessService ).sendPushNotification( anyString(),
                any( NotificationRequest.class ) );
    }
    
    /**
     * Test create opportunity buyer request.
     */
    @Test
    public void testCreateOpportunityBuyerRequestWithRequestTypeAsScheduleTour() {
        final LeadRequest request = new LeadRequest();
        request.setRequestType( LeadRequestType.SCHEDULE_TOUR.toString() );
        final PostResponse response = new PostResponse();
        response.setName( "test" );
        final Opportunity opportunity = new Opportunity();
        opportunity.setDeleted( false );
        
        when( coShoppingConfig.isEnableBuyerOpportunityScheduleTour()).thenReturn(true);
        when( agentOpportunityService.getOpportunityById( anyString(), anyString() ) ).thenReturn( opportunity );
        Mockito.when( buyerRequestBuilder.convertTo( Mockito.any() ) ).thenReturn( new Request() );
        Mockito.when( agentTaskBusinessService.createBuyerTask( Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any() ) ).thenReturn( response );
        Mockito.when( requestService.saveRequest( Mockito.any(), Mockito.any() ) ).thenReturn( response );
        Mockito.doNothing().when( actionLogBusinessService ).auditForAction( Mockito.any( ActionLogDto.class ) );
        opportunityBusinessServiceImpl.processBuyerRequest( request, "", "" );
        verify( agentNotificationBusinessService ).sendPushNotification( anyString(),
                any( NotificationRequest.class ) );
    }

    /**
     * Should not process buyer request if opportunity is deleted.
     */
    @Test
    public void shouldNotProcessBuyerRequestIfOpportunityIsDeleted() {
        final Opportunity opportunity = new Opportunity();
        opportunity.setDeleted( true );
        when( agentOpportunityService.getOpportunityById( anyString(), anyString() ) ).thenReturn( opportunity );
        opportunityBusinessServiceImpl.processBuyerRequest( null, "", "" );
        verifyZeroInteractions( buyerRequestBuilder );
        verifyZeroInteractions( requestService );
        verifyZeroInteractions( agentTaskBusinessService );
        verifyZeroInteractions( requestService );
        verifyZeroInteractions( agentNotificationBusinessService );
        verify( agentOpportunityService ).getOpportunityById( anyString(), anyString() );
    }

    /**
     * Test update ocl opportunity.
     */
    @Test
    public void testUpdateOclOpportunity() {
        final String crmOpportunityId = "dummy crmOpportunityId";
        final String loanPhase = "dummy loanPhase";
        final int loanNumber = 12345;

        opportunityBusinessServiceImpl.updateOclOpportunity( crmOpportunityId, loanPhase, loanNumber, "" );

        verify( opportunityService ).patchOpportunity( any(), any() );
    }

    /**
     * Test get opportunity id by record type and email.
     */
    @Test
    public void testGetOpportunityIdByRecordTypeAndEmail() {
        final String email = "email@email.com";
        final String recordTypeId = "recordTypeId";
        final String expected = "opportunityId";
        when( opportunityService.getOpportunityIdByRecordTypeIdAndEmail( recordTypeId, email ) ).thenReturn( expected );
        final String actual = opportunityBusinessServiceImpl.getOpportunityIdByRecordTypeAndEmail( recordTypeId,
                email );
        assertEquals( expected, actual );
    }

    /**
     * Test get opportunity id by record type and emails should return null if
     * not exists.
     */
    @Test
    public void testGetOpportunityIdByRecordTypeAndEmailsShouldReturnNullIfNotExists() {
        final String recordType = "recordType";
        final String email = "email@email.com";
        final String recordTypeId = "recordTypeId";
        when( recordTypeService.getRecordTypeIdByName( recordType, OPPORTUNITY.getName() ) ).thenReturn( recordTypeId );
        final String expected = "opportunityId";
        when( opportunityService.getOpportunityIdByRecordTypeIdAndEmail( recordTypeId, email ) )
                .thenThrow( ResultNotFoundException.class );
        final String actual = opportunityBusinessServiceImpl.getOpportunityIdByRecordTypeAndEmail( recordType, email );
        assertNull( actual );
    }

    /**
     * Test get opportunity create details.
     */
    @Test
    public void testGetOpportunityCreateDetails() {
        final Map< String, Object > requestMap = new HashMap< String, Object >();
        final Map< String, String > innerMap = new HashMap< String, String >();
        innerMap.put( "Name", "test" );
        innerMap.put( "Email", "test" );
        requestMap.put( "RecordType", innerMap );
        requestMap.put( "Owner", innerMap );
        requestMap.put( "Owner", innerMap );
        when( crmQueryService.findOne( Mockito.anyString(), Mockito.any() ) ).thenReturn( requestMap );
        final OpportunityContact contact = new OpportunityContact();
        contact.setPrimaryContact( new Contact() );
        when( opportunityContactBuilder.convertTo( contactService.findContactById( Mockito.any(), Mockito.any() ) ) )
                .thenReturn( contact );
        final OpportunitySource source = opportunityBusinessServiceImpl.getOpportunityCreateDetails( "test" );
        assertEquals( source.getOpportunityOwnerName(), "test" );
    }

    /**
     * Test get opportunity by crm id.
     */
    @Test
    public void testGetOpportunityByCRMId() {
        final CRMOpportunityResponse response = new CRMOpportunityResponse();
        response.setName( "test" );
        response.setRecordTypeName( "test" );
        response.setPropertyZip( "test" );
        when( opportunityService.getOpportunity( Mockito.any() ) ).thenReturn( response );
        final OpportunityDetails details = opportunityBusinessServiceImpl.getOpportunityByCRMId( "test" );
        assertEquals( details.getName(), response.getName() );
    }

    /**
     * Test update opportunity.
     */
    @Test
    public void testUpdateOpportunity() {
        final Map< String, Object > request = new HashMap<>();
        request.put( "agentEmail", "test@t.com" );
        request.put( "zipcode", "23215" );
        final Map< String, Object > response = new HashMap<>();
        response.put( "id", "testId" );
        final String opportunityId = "test";
        final OpportunitySource opportunitySource = new OpportunitySource();
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( response );
        when( opportunitySourceBuilder.convertTo( Mockito.any() ) ).thenReturn( opportunitySource );
        when( opportunityContactBuilder.convertTo( Mockito.any() ) ).thenReturn( new OpportunityContact() );

        opportunityBusinessServiceImpl.updateOpportunity( "test", request );

        verify( agentOpportunityBusinessService ).handleOpportunityChange( Mockito.any( OpportunitySource.class ) );
    }

    /**
     * Test get opportunity id by record type and loan number.
     */
    @Test
    public void testGetOpportunityIdByRecordTypeAndLoanNumber() {
        final String returnVal = "testResult";
        when( opportunityService.getOpportunityIdByRecordTypeIdAndLoanNumber( Mockito.any(), Mockito.anyInt() ) )
                .thenReturn( returnVal );
        final String result = opportunityBusinessServiceImpl.getOpportunityIdByRecordTypeAndLoanNumber( "test", 0 );
        assertEquals( result, returnVal );
    }

    /**
     * Test assign opportunity to agent.
     */
    @Test
    public void testAssignOpportunityToAgent() {
        ReflectionTestUtils.invokeMethod( opportunityBusinessServiceImpl, "initializeOpportunityFieldsMap" );
        final OpportunitySource opportunitySource = new OpportunitySource();
        final String agentEmail = "a@a.com";
        final Map< String, Object > map = new HashMap<>();
        final String agentId = "agentId";
        map.put( QUERY_PARAM_ID, agentId );
        final Map< String, Object > contactMap = new HashMap<>();

        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        when( contactService.findContactById( anyString(), anyString() ) ).thenReturn( contactMap );
        when( opportunityContactBuilder.convertTo( contactMap ) ).thenReturn( new OpportunityContact() );

        opportunityBusinessServiceImpl.assignOpportunityToAgent( opportunitySource, agentEmail );

        verify( opportunityService ).patchOpportunity( anyMap(), anyString() );
        verify( agentOpportunityBusinessService ).handleOpportunityChange( any( OpportunitySource.class ) );
    }

    /**
     * Test forward to referral exchange should refer and send notification.
     */
    @Test
    public void testForwardToReferralExchangeShouldReferAndSendNotification() {
        final OpportunitySource opportunitySource = new OpportunitySource();
        opportunitySource.setPrimaryContact( new Contact() );
        final ReferralExchangeRequest newRequest = new ReferralExchangeRequest();
        final EmailNotification notification = new EmailNotification();
        final Map< String, Object > map = new HashMap<>();
        final String agentId = "agentId";
        map.put( QUERY_PARAM_ID, agentId );

        when( referralExchangeOpportunityRequestBuilder.convertTo( opportunitySource ) ).thenReturn( newRequest );
        when( agentOpportunityBusinessConfig.isReferralEmailNotificationEnabled() ).thenReturn( true );
        when( referralExchangeRequestEmailBuilder.convertTo( anyString(), anyList() ) ).thenReturn( notification );
        when( mailService.send( notification ) ).thenReturn( new NotificationResponse() );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );

        opportunityBusinessServiceImpl.forwardToReferralExchange( opportunitySource );

        verify( referralExchangeOpportunityRequestBuilder ).convertTo( opportunitySource );
        verify( opportunityService ).patchOpportunity( anyMap(), anyString() );
        verify( mailService ).send( notification );
    }

    /**
     * Test forward to referral exchange should update error details in case of
     * exception.
     */
    @Test
    public void testForwardToReferralExchangeShouldUpdateErrorDetailsInCaseOfException() {
        final OpportunitySource opportunitySource = new OpportunitySource();
        final Contact contact = new Contact();
        contact.setFirstName( "firstName" );
        opportunitySource.setPrimaryContact( contact );
        final ReferralExchangeRequest newRequest = new ReferralExchangeRequest();
        final EmailNotification notification = new EmailNotification();
        final Map< String, Object > map = new HashMap<>();
        final String agentId = "agentId";
        map.put( QUERY_PARAM_ID, agentId );

        when( referralExchangeOpportunityRequestBuilder.convertTo( opportunitySource ) ).thenReturn( newRequest );
        when( agentOpportunityBusinessConfig.isReferralEmailNotificationEnabled() ).thenReturn( true );
        when( referralExchangeRequestEmailBuilder.convertTo( anyString(), anyList() ) ).thenReturn( notification );
        when( mailService.send( notification ) ).thenThrow( new HttpClientErrorException( HttpStatus.BAD_GATEWAY ) );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );

        opportunityBusinessServiceImpl.forwardToReferralExchange( opportunitySource );

        verify( referralExchangeOpportunityRequestBuilder ).convertTo( opportunitySource );
        verify( opportunityService ).patchOpportunity( anyMap(), anyString() );
    }

    /**
     * Test get opportunity id by record type and email should return empty in
     * case of exception.
     */
    @Test
    public void testGetOpportunityIdByRecordTypeAndEmailShouldReturnEmptyInCaseOfException() {
        final String recordTypeId = "recId";
        final String email = "a@a.com";
        when( opportunityService.getOpportunityIdByRecordTypeIdAndEmail( recordTypeId, email ) )
                .thenThrow( new ResultNotFoundException( null ) );
        final String actual = opportunityBusinessServiceImpl.getOpportunityIdByRecordTypeAndEmail( recordTypeId,
                email );
        assertEquals( EMPTY, actual );
    }

    /**
     * Testget opportunity id by record type and loan number.
     */
    @Test
    public void testgetOpportunityIdByRecordTypeAndLoanNumber() {
        final String recordTypeId = "recId";
        final int loanNumber = 1;
        when( opportunityService.getOpportunityIdByRecordTypeIdAndLoanNumber( recordTypeId, loanNumber ) )
                .thenThrow( new ResultNotFoundException( null ) );
        final String actual = opportunityBusinessServiceImpl.getOpportunityIdByRecordTypeAndLoanNumber( recordTypeId,
                loanNumber );
        assertEquals( EMPTY, actual );
    }

    /**
     * Test update opportunity should update agent assignment log.
     */
    // @Test
    // public void testUpdateOpportunityShouldUpdateAgentAssignmentLog() {
    // final String crmId = "crmId";
    // final Map< String, Object > request = new HashMap<>();
    // request.put( "key", "value" );
    // final Map< String, Object > map = new HashMap<>();
    // final String agentId = "agentId";
    // map.put( QUERY_PARAM_ID, agentId );
    // final Map< String, Object > contactMap = new HashMap<>();
    // final AgentAssignmentLog log = new AgentAssignmentLog();
    //
    // when( crmQueryService.findOne( anyString(), any( QueryParams.class ) )
    // ).thenReturn( map );
    // when( contactService.findContactById( anyString(), anyString() )
    // ).thenReturn( contactMap );
    // when( opportunityContactBuilder.convertTo( contactMap ) ).thenReturn( new
    // OpportunityContact() );
    // when( agentAssignmentLogService.findByOpportunityIdAndStatus(
    // anyString(), anyString(), any( Pageable.class ) ) )
    // .thenReturn( log );
    //
    // opportunityBusinessServiceImpl.updateOpportunity( crmId, request );
    //
    // verify( opportunityService ).patchOpportunity( anyMap(), anyString() );
    // verify( agentLookupTask ).updateAgentAssignmentAudit( anyString(),
    // anyString(), anyString() );
    // verify( agentOpportunityBusinessService ).handleOpportunityChange( any(
    // OpportunitySource.class ) );
    // verify( agentAssignmentLogService ).save( log );
    // }

    /**
     * Test Overloaded forward to referral exchange should refer and send
     * notification.
     * with reasonLost as noAgentAvailable
     */
    @Test
    public void testOverloadedForwardToReferralExchangeWithNoAgentAvailableShouldReferAndSendNotification() {

        final String crmId = "crmId";
        final String reasonLost = "noAgentAvailable";
        final Contact contact = new Contact();
        contact.setFirstName( "First Name" );
        contact.setLastName( "Last Name" );
        final OpportunitySource opportunitySource = new OpportunitySource();
        opportunitySource.setPrimaryContact( contact );

        final ReferralExchangeRequest newRequest = new ReferralExchangeRequest();
        final EmailNotification notification = new EmailNotification();
        final Map< String, Object > map = new HashMap<>();
        final String agentId = "agentId";
        map.put( QUERY_PARAM_ID, agentId );

        final Map< String, Object > response = new HashMap<>();
        response.put( "id", "testId" );
        final OpportunityContact opportunityContact = new OpportunityContact();
        opportunityContact.setPrimaryContact( contact );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( response );
        when( opportunitySourceBuilder.convertTo( Mockito.any() ) ).thenReturn( opportunitySource );
        when( opportunityContactBuilder.convertTo( Mockito.any() ) ).thenReturn( opportunityContact );
        when( referralExchangeOpportunityRequestBuilder.convertTo( opportunitySource ) ).thenReturn( newRequest );
        when( agentOpportunityBusinessConfig.isReferralEmailNotificationEnabled() ).thenReturn( true );
        when( referralExchangeRequestEmailBuilder.convertTo( anyString(), anyList() ) ).thenReturn( notification );
        when( mailService.send( notification ) ).thenReturn( new NotificationResponse() );

        opportunityBusinessServiceImpl.forwardToReferralExchange( crmId, reasonLost );

        verify( opportunityService ).patchOpportunity( anyMap(), anyString() );
        verify( mailService ).send( notification );
    }

    /**
     * Test Overloaded forward to referral exchange should update error details
     * in case of
     * exception.
     * with reasonLost as noAgentAvailable
     */
    @Test
    public void testOverloadedForwardToReferralExchangeWithNoAgentAvailableShouldUpdateErrorDetailsInCaseOfException() {
        final OpportunitySource opportunitySource = new OpportunitySource();
        final String crmId = "crmId";
        final String reasonLost = "noAgentAvailable";
        final Contact contact = new Contact();
        contact.setFirstName( "firstName" );
        opportunitySource.setPrimaryContact( contact );
        opportunitySource.setCrmId( crmId );
        final ReferralExchangeRequest newRequest = new ReferralExchangeRequest();
        final EmailNotification notification = new EmailNotification();
        final Map< String, Object > map = new HashMap<>();
        final String agentId = "agentId";
        map.put( QUERY_PARAM_ID, agentId );
        final OpportunityContact opportunityContact = new OpportunityContact();
        opportunityContact.setPrimaryContact( contact );

        when( opportunitySourceBuilder.convertTo( Mockito.any() ) ).thenReturn( opportunitySource );
        when( opportunityContactBuilder.convertTo( Mockito.any() ) ).thenReturn( opportunityContact );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        when( referralExchangeOpportunityRequestBuilder.convertTo( opportunitySource ) ).thenReturn( newRequest );
        when( agentOpportunityBusinessConfig.isReferralEmailNotificationEnabled() ).thenReturn( true );
        when( referralExchangeRequestEmailBuilder.convertTo( anyString(), anyList() ) ).thenReturn( notification );
        when( mailService.send( notification ) ).thenThrow( new HttpClientErrorException( HttpStatus.BAD_GATEWAY ) );

        opportunityBusinessServiceImpl.forwardToReferralExchange( crmId, reasonLost );

        verify( opportunityService ).patchOpportunity( anyMap(), anyString() );
    }

    /**
     * Test Overloaded forward to referral exchange should refer and send
     * notification.
     * with reasonLost as No COVERAGE
     */
    @Test
    public void testOverloadedForwardToReferralExchangeWithOutOfCoverageShouldReferAndSendNotification() {

        final String crmId = "crmId";
        final String reasonLost = "No COVERAGE";
        final Contact contact = new Contact();
        contact.setFirstName( "First Name" );
        contact.setLastName( "Last Name" );
        final OpportunitySource opportunitySource = new OpportunitySource();
        opportunitySource.setPrimaryContact( contact );

        final ReferralExchangeRequest newRequest = new ReferralExchangeRequest();
        final EmailNotification notification = new EmailNotification();
        final Map< String, Object > map = new HashMap<>();
        final String agentId = "agentId";
        map.put( QUERY_PARAM_ID, agentId );

        final Map< String, Object > response = new HashMap<>();
        response.put( "id", "testId" );
        final OpportunityContact opportunityContact = new OpportunityContact();
        opportunityContact.setPrimaryContact( contact );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( response );
        when( opportunitySourceBuilder.convertTo( Mockito.any() ) ).thenReturn( opportunitySource );
        when( opportunityContactBuilder.convertTo( Mockito.any() ) ).thenReturn( opportunityContact );
        when( referralExchangeOpportunityRequestBuilder.convertTo( opportunitySource ) ).thenReturn( newRequest );
        when( agentOpportunityBusinessConfig.isReferralEmailNotificationEnabled() ).thenReturn( true );
        when( referralExchangeRequestEmailBuilder.convertTo( anyString(), anyList() ) ).thenReturn( notification );
        when( mailService.send( notification ) ).thenReturn( new NotificationResponse() );

        opportunityBusinessServiceImpl.forwardToReferralExchange( crmId, reasonLost );

        verify( opportunityService ).patchOpportunity( anyMap(), anyString() );
        verify( mailService ).send( notification );
    }

    /**
     * Test Overloaded forward to referral exchange should update error details
     * in case of
     * exception.
     * with reasonLost as No COVERAGE
     */
    @Test
    public void testOverloadedForwardToReferralExchangeWithOutOfCoverageShouldUpdateErrorDetailsInCaseOfException() {
        final OpportunitySource opportunitySource = new OpportunitySource();
        final String crmId = "crmId";
        final String reasonLost = "No COVERAGE";
        final Contact contact = new Contact();
        contact.setFirstName( "firstName" );
        opportunitySource.setPrimaryContact( contact );
        opportunitySource.setCrmId( crmId );
        final ReferralExchangeRequest newRequest = new ReferralExchangeRequest();
        final EmailNotification notification = new EmailNotification();
        final Map< String, Object > map = new HashMap<>();
        final String agentId = "agentId";
        map.put( QUERY_PARAM_ID, agentId );
        final OpportunityContact opportunityContact = new OpportunityContact();
        opportunityContact.setPrimaryContact( contact );

        when( opportunitySourceBuilder.convertTo( Mockito.any() ) ).thenReturn( opportunitySource );
        when( opportunityContactBuilder.convertTo( Mockito.any() ) ).thenReturn( opportunityContact );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        when( referralExchangeOpportunityRequestBuilder.convertTo( opportunitySource ) ).thenReturn( newRequest );
        when( agentOpportunityBusinessConfig.isReferralEmailNotificationEnabled() ).thenReturn( true );
        when( referralExchangeRequestEmailBuilder.convertTo( anyString(), anyList() ) ).thenReturn( notification );
        when( mailService.send( notification ) ).thenThrow( new HttpClientErrorException( HttpStatus.BAD_GATEWAY ) );

        opportunityBusinessServiceImpl.forwardToReferralExchange( crmId, reasonLost );

        verify( opportunityService ).patchOpportunity( anyMap(), anyString() );
    }
    
    @Test
    public void TestCreateScheduledTourMeetings_whenJmxFalse() {
        final String crmId = "testCrmId";
        final ScheduleTourLeadRequest meetingRequest = new ScheduleTourLeadRequest();
        final List<ScheduleTourLeadRequest> meetingRequestList = new ArrayList<>(); 
        meetingRequestList.add( meetingRequest );
        when( coShoppingConfig.isEnableScheduleTourMeetings()).thenReturn(false);
        final ScheduleTourMeetingRequest mtr = new ScheduleTourMeetingRequest();
        mtr.setMeetingRequest( meetingRequestList );
       final AgentResponse respose =  opportunityBusinessServiceImpl.createScheduledTourMeetings( crmId, mtr );
       assertEquals( Status.FAILURE , respose.getStatus() );  
        
    }
    
    @InjectMocks
    protected ActionFlowBusinessService actionFlowSpy = spy(new ActionFlowBusinessServiceImpl());
    
    @InjectMocks
    protected ActionFlowConfig actionFlowconfig = spy(new ActionFlowConfig());
    @Test
    public void testCreateSkipMeetingActionFlow() {
        final String crmId = "testCrmId";
        final String insideSalesEmail = "email@email.com";
        final Search search = new Search();
        search.setAgentEmail( "agent@ownerstest.com" );
        search.setAgentId( "1r7yMnjl9DSXNBQmJeis8jS4Wbp1" );
        search.setOpportunityId( "-L5I_G5T3jWTK2sODQOL" );
        final Opportunity opportunity = new Opportunity();
        opportunity.setDeleted( false );
        opportunity.setOpportunityType( "BUYER" );
        final ActionLog actionLog = new ActionLog();
        actionLog.setActionBy( insideSalesEmail );
        actionLog.setActionEntity( OPPORTUNITY.name() );
        actionLog.setActionEntityId( search.getOpportunityId() );
        actionLog.setActionType( Constants.SKIP_SCHEDULE_MEETING );
        actionLog.setFieldName( search.getAgentId() );
        actionLog.setDescription( Constants.SKIP_SCHEDULE_MEETING_DESC );
        when( searchService.searchByCrmOpportunityId( crmId ) ).thenReturn( search );
        when( agentOpportunityService.getOpportunityById( anyString(), anyString() ) ).thenReturn( opportunity );
        when(actionFlowBusinessService.isEligibleForScriptedCall(search.getAgentEmail())).thenReturn( true );
        Mockito.doNothing().when( actionFlowBusinessService ).createActionGroup( search.getOpportunityId(),
                search.getAgentId(), opportunity, search );
        when(actionLogService.save( actionLog )).thenReturn( actionLog );
        final BaseResponse response =  opportunityBusinessServiceImpl.createSkipMeetingActionFlow( insideSalesEmail, crmId ) ;
        assertEquals( Status.SUCCESS, response.getStatus() );
    }
}
