package com.owners.gravitas.business.impl;

import static com.owners.gravitas.constants.CRMConstants.LEAD_DEDUP_COUNT;
import static com.owners.gravitas.enums.CRMObject.LEAD;
import static com.owners.gravitas.enums.LeadStatus.FORWARDED_TO_REF_EX;
import static com.owners.gravitas.enums.LeadStatus.QUALIFIED;
import static com.owners.gravitas.enums.RecordType.BUYER;
import static com.owners.gravitas.enums.RecordType.SELLER;
import static java.lang.Boolean.TRUE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.RuntimeService;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.hubzu.dsync.service.DistributedSynchronizationService;
import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.BuyerFarmingBusinessService;
import com.owners.gravitas.business.LeadFollowupBusinessService;
import com.owners.gravitas.business.OpportunityBusinessService;
import com.owners.gravitas.business.ProcessBusinessService;
import com.owners.gravitas.business.builder.CoShoppingLeadBuilder;
import com.owners.gravitas.business.builder.ContactEntityBuilder;
import com.owners.gravitas.business.builder.ContactLogBuilder;
import com.owners.gravitas.business.builder.EmailNotificationBuilder;
import com.owners.gravitas.business.builder.MortgageLeadNotificationBuilder;
import com.owners.gravitas.business.builder.request.CRMLeadRequestBuilder;
import com.owners.gravitas.business.builder.request.ContactStatusBuilder;
import com.owners.gravitas.business.builder.request.GenericLeadRequestBuilder;
import com.owners.gravitas.business.builder.request.OCLGenericLeadRequestBuilder;
import com.owners.gravitas.business.builder.request.ReferralExchangeRequestBuilder;
import com.owners.gravitas.business.builder.request.ReferralExchangeRequestEmailBuilder;
import com.owners.gravitas.business.task.LeadTask;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.BuyerFarmingConfig;
import com.owners.gravitas.config.CoShoppingConfig;
import com.owners.gravitas.config.LeadBusinessConfig;
import com.owners.gravitas.config.LeadOpportunityBusinessConfig;
import com.owners.gravitas.config.LiveVoxJmxConfig;
import com.owners.gravitas.config.ZillowHotlineJmxConfig;
import com.owners.gravitas.constants.CRMConstants;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.ContactStatus;
import com.owners.gravitas.domain.entity.Process;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.dto.crm.request.CRMLeadRequest;
import com.owners.gravitas.dto.crm.response.CRMLeadResponse;
import com.owners.gravitas.dto.crm.response.CRMResponse;
import com.owners.gravitas.dto.crm.response.OpportunityResponse;
import com.owners.gravitas.dto.request.GenericLeadRequest;
import com.owners.gravitas.dto.request.LeadDetailsRequest;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.dto.request.ReferralExchangeRequest;
import com.owners.gravitas.dto.request.RequestTypeLeadRequest;
import com.owners.gravitas.dto.response.LeadResponse;
import com.owners.gravitas.dto.response.NotificationResponse;
import com.owners.gravitas.enums.BuyerFarmType;
import com.owners.gravitas.enums.GravitasProcess;
import com.owners.gravitas.enums.LeadRequestType;
import com.owners.gravitas.enums.LeadStatus;
import com.owners.gravitas.enums.RecordType;
import com.owners.gravitas.exception.InvalidArgumentException;
import com.owners.gravitas.exception.ResultNotFoundException;
import com.owners.gravitas.lock.SyncCacheLockHandler;
import com.owners.gravitas.service.BuyerService;
import com.owners.gravitas.service.CRMQueryService;
import com.owners.gravitas.service.CoShoppingService;
import com.owners.gravitas.service.ContactEntityService;
import com.owners.gravitas.service.ContactLogService;
import com.owners.gravitas.service.ContactStatusService;
import com.owners.gravitas.service.DeDuplicationService;
import com.owners.gravitas.service.LeadService;
import com.owners.gravitas.service.LiveVoxService;
import com.owners.gravitas.service.MailService;
import com.owners.gravitas.service.ObjectTypeService;
import com.owners.gravitas.service.OpportunityService;
import com.owners.gravitas.service.RecordTypeService;
import com.owners.gravitas.service.ReferralExchangeService;
import com.owners.gravitas.service.builder.LiveVoxCampaignRecordRequestBuilder;
import com.owners.gravitas.service.cache.LiveVoxLookupService;
import com.owners.gravitas.service.util.LiveVoxUtil;
import com.owners.gravitas.validator.LeadValidator;

/**
 * Test class for {@link BuyerBusinessServiceImpl}.
 *
 * @author harshads
 */
public class LeadBusinessServiceImplTest extends AbstractBaseMockitoTest {

    /** The business service impl. */
    @InjectMocks
    private LeadBusinessServiceImpl businessServiceImpl;

    /** The buyer farming config. */
    @Mock
    private BuyerFarmingConfig buyerFarmingConfig;
    
    @Mock
    private ZillowHotlineJmxConfig zillowHotlineJmxConfig;

    /** The oclead request builder. */
    @Mock
    private OCLGenericLeadRequestBuilder ocleadRequestBuilder;

    /** The record type service. */
    @Mock
    private RecordTypeService recordTypeService;

    /** The lead service. */
    @Mock
    private LeadService leadService;

    /** The mail service. */
    @Mock
    private MailService mailService;

    /** The email notification builder. */
    @Mock
    private EmailNotificationBuilder emailNotificationBuilder;

    /** The crm query service. */
    @Mock
    private CRMQueryService crmQueryService;

    /** The crm lead request builder. */
    @Mock
    private CRMLeadRequestBuilder crmLeadRequestBuilder;

    /** The marketing email business service. */
    @Mock
    private LeadFollowupBusinessService marketingEmailBusinessService;

    /** The request. */
    @Mock
    private LeadRequest request;

    /** The de duplication service. */
    @Mock
    private DeDuplicationService deDuplicationService;

    /** The opportunity business service. */
    @Mock
    private OpportunityBusinessService opportunityBusinessService;

    /** The referral exchange request builder. */
    @Mock
    private ReferralExchangeRequestBuilder referralExchangeRequestBuilder;

    /** The lead exchange service. */
    @Mock
    private ReferralExchangeService referralExchangeService;

    /** The Lead validation service. */
    @Mock
    private LeadValidator leadValidator;

    /** The generic lead request builder. */
    @Mock
    private GenericLeadRequestBuilder genericLeadRequestBuilder;

    /** The Opportunity Service service. */
    @Mock
    private OpportunityService opportunityServiceImpl;

    /** The generic lead request builder from lead source. */
    @Mock
    private OCLGenericLeadRequestBuilder ownerGenericLeadRequestBuilder;

    /** The config. */
    @Mock
    private LeadBusinessConfig config;

    /** The referral exchange request email builder. */
    @Mock
    private ReferralExchangeRequestEmailBuilder referralExchangeRequestEmailBuilder;

    /** The date time zone. */
    @Mock
    private DateTimeZone dateTimeZone;

    /** The lead opportunity business config. */
    @Mock
    private LeadOpportunityBusinessConfig leadOpportunityBusinessConfig;

    /** The contact service V 1. */
    @Mock
    private ContactEntityService contactServiceV1;

    /** The contact builder V 1. */
    @Mock
    private ContactEntityBuilder contactBuilderV1;

    /** The lead task. */
    @Mock
    private LeadTask leadTask;

    /** The buyer registration business service. */
    @Mock
    private BuyerFarmingBusinessService buyerFarmingBusinessService;

    /** The runtime service. */
    @Mock
    protected RuntimeService runtimeService;

    /** The process management business service. */
    @Mock
    protected ProcessBusinessService processBusinessService;

    /** The lead followup business service. */
    @Mock
    private LeadFollowupBusinessService leadFollowupBusinessService;

    @Mock
    private MortgageLeadNotificationBuilder mortgageLeadNotificationBuilder;

    @Mock
    private ContactLogBuilder contactLogBuilder;

    @Mock
    private ContactLogService contactLogService;

    @Mock
    @Qualifier( value = "liveVoxService" )
    private LiveVoxService liveVoxService;

    @Mock
    private LiveVoxCampaignRecordRequestBuilder liveVoxCampaignRecordRequestBuilder;

    @Mock
    private LiveVoxLookupService liveVoxLookupService;

    @Mock
    private LiveVoxJmxConfig liveVoxJmxConfig;

    @Mock
    private LiveVoxUtil liveVoxUtil;

    /** The object type service. */
    @Mock
    private ObjectTypeService objectTypeService;
    
    /** The buyer service. */
    @Mock
    private BuyerService buyerService;
    @Mock
    private CoShoppingConfig coShoppingConfig;
    @Mock
    private CoShoppingService coShoppingService;
    @Mock
    private CoShoppingLeadBuilder coShoppingLeadBuilder;
    @Mock
    private SyncCacheLockHandler syncCacheLockHandler;
    
    @Mock
    ContactStatusBuilder contactStatusBuilder;
    
    @Mock
    ContactStatusService contactStatusService;

    /**
     * Before method.
     */
    @BeforeMethod( )
    private void beforeMethod() {
        ReflectionTestUtils.setField( businessServiceImpl, "referralExchangeExcludedLeadSourcesStr",
                "app-android-owners.com registration,app-ios-owners.com registration" );
        ReflectionTestUtils.setField( businessServiceImpl, "oclLeadBuyerStates", "fl,ga,ma,pa,il,tx,ca,oh" );
        ReflectionTestUtils.setField( businessServiceImpl, "oclLeadBuyerStatus", "qualified" );
        ReflectionTestUtils.setField( businessServiceImpl, "excludedLostReasonsStr",
                "Duplicate Lead,Invalid Email,Is an Agent,Out Of Coverage Area" );
        ReflectionTestUtils.setField( businessServiceImpl, "notifyLeadsForFinanceInState", "CA" );
        ReflectionTestUtils.invokeMethod( businessServiceImpl, "initDataBuilder" );
        Mockito.reset( config );
        Mockito.when(syncCacheLockHandler.acquireLockBlocking(Mockito.anyString()))
        .thenReturn(true);
    }

    /** Test create lead with happy flow. */
    @Test
    public void createBuyerLeadWithHappyFlowTest() {
        final CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setId( "123123" );

        final CRMResponse crmResponse = new CRMResponse();
        crmResponse.setRecords( new ArrayList< Map< String, Object > >() );

        when( deDuplicationService.deDuplicateLead( anyString() ) ).thenReturn( crmResponse );
        when( deDuplicationService.getDeDuplicatedLead( anyString(), anyObject() ) ).thenReturn( crmResponse );
        when( leadService.createLead( any( CRMLeadRequest.class ), anyBoolean() ) ).thenReturn( leadResponse );
        when( emailNotificationBuilder.convertTo( any( GenericLeadRequest.class ) ) )
                .thenReturn( new EmailNotification() );
        final Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Id", "123456" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        when( crmLeadRequestBuilder.convertTo( any( LeadRequest.class ) ) ).thenReturn( new CRMLeadRequest() );
        when( config.getAfterHoursStates() ).thenReturn( "AB" );

        final GenericLeadRequest lead = new GenericLeadRequest();
        lead.setLeadType( RecordType.BUYER.toString() );
        lead.setState( "test" );
        Mockito.when( contactServiceV1.getContact( anyString(), anyString() ) ).thenReturn( null );
        Mockito.when( contactBuilderV1.convertTo( lead, null ) ).thenReturn( new Contact() );

        businessServiceImpl.createLead( lead, true, null );
        verify( leadService ).createLead( any( CRMLeadRequest.class ), anyBoolean() );
    }

    
    /** Test create lead with happy flow. */
    @Test(expectedExceptions =Exception.class)
    public void createLead_withException() {
        final CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setId( "123123" );

        final CRMResponse crmResponse = new CRMResponse();
        crmResponse.setRecords( new ArrayList< Map< String, Object > >() );

        when( deDuplicationService.deDuplicateLead( anyString() ) ).thenReturn( crmResponse );
        when( deDuplicationService.getDeDuplicatedLead( anyString(), anyObject() ) ).thenReturn( crmResponse );
        when( leadService.createLead( any( CRMLeadRequest.class ), anyBoolean() ) ).thenThrow(new Exception("400 Bad Request"));
        when( emailNotificationBuilder.convertTo( any( GenericLeadRequest.class ) ) )
                .thenReturn( new EmailNotification() );
        final Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Id", "123456" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        when( crmLeadRequestBuilder.convertTo( any( LeadRequest.class ) ) ).thenReturn( new CRMLeadRequest() );
        when( config.getAfterHoursStates() ).thenReturn( "AB" );

        final GenericLeadRequest lead = new GenericLeadRequest();
        lead.setLeadType( RecordType.BUYER.toString() );
        lead.setState( "test" );
        Mockito.when( contactServiceV1.getContact( anyString(), anyString() ) ).thenReturn( null );
        Mockito.when( contactBuilderV1.convertTo( lead, null ) ).thenReturn( new Contact() );
        
        ContactStatus contactStatusRequest = new ContactStatus();
		contactStatusRequest.setEmail("test@mail.com");
		contactStatusRequest.setRequestJson("some json object");
		contactStatusRequest.setRequestType("New");
        when(contactStatusBuilder.convertTo(request)).thenReturn(contactStatusRequest);
        when(contactStatusService.save(contactStatusRequest)).thenReturn(contactStatusRequest);
        businessServiceImpl.createLead( lead, true, null );
        
        verify(contactStatusService).save(any(ContactStatus.class));
    }
    /** Test create lead with interested in financing true. */
    @Test
    public void createLeadWithIsInterestedInFinancingCheckedTest() {
        final CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setId( "123123" );

        final CRMResponse crmResponse = new CRMResponse();
        crmResponse.setRecords( new ArrayList< Map< String, Object > >() );

        when( deDuplicationService.getDeDuplicatedLead( anyString(), anyObject() ) ).thenReturn( crmResponse );
        when( leadService.createLead( any( CRMLeadRequest.class ), anyBoolean() ) ).thenReturn( leadResponse );
        when( emailNotificationBuilder.convertTo( any( GenericLeadRequest.class ) ) )
                .thenReturn( new EmailNotification() );

        final Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Id", "123456" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        when( crmLeadRequestBuilder.convertTo( any( LeadRequest.class ) ) ).thenReturn( new CRMLeadRequest() );
        when( config.getAfterHoursStates() ).thenReturn( "AB" );

        final GenericLeadRequest lead = new GenericLeadRequest();
        lead.setLeadType( RecordType.BUYER.toString() );
        lead.setState( "test" );
        lead.setInterestedInFinancing( true );
        Mockito.when( contactServiceV1.getContact( anyString(), anyString() ) ).thenReturn( null );
        Mockito.when( contactBuilderV1.convertTo( lead, null ) ).thenReturn( new Contact() );
        businessServiceImpl.createLead( lead, true, null );
    }

    /** Test create lead with interested in financing false. */
    @Test
    public void createLeadWithIsInterestedInFinancingUncheckedTest() {
        final CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setId( "123123" );

        final CRMResponse crmResponse = new CRMResponse();
        crmResponse.setRecords( new ArrayList< Map< String, Object > >() );

        when( deDuplicationService.getDeDuplicatedLead( anyString(), anyObject() ) ).thenReturn( crmResponse );
        when( leadService.createLead( any( CRMLeadRequest.class ), anyBoolean() ) ).thenReturn( leadResponse );
        when( emailNotificationBuilder.convertTo( any( GenericLeadRequest.class ) ) )
                .thenReturn( new EmailNotification() );

        final Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Id", "123456" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        when( crmLeadRequestBuilder.convertTo( any( LeadRequest.class ) ) ).thenReturn( new CRMLeadRequest() );
        when( config.getAfterHoursStates() ).thenReturn( "AB" );

        final GenericLeadRequest lead = new GenericLeadRequest();
        lead.setLeadType( RecordType.BUYER.toString() );
        lead.setState( "test" );
        lead.setInterestedInFinancing( false );

        Mockito.when( contactServiceV1.getContact( anyString(), anyString() ) ).thenReturn( null );
        Mockito.when( contactBuilderV1.convertTo( lead, null ) ).thenReturn( new Contact() );

        businessServiceImpl.createLead( lead, true, null );

        verify( leadService ).createLead( any( CRMLeadRequest.class ), anyBoolean() );

    }

    /**
     * Test create lead with interested in financing true and lead type
     * Owners.com Loans
     */
    @Test
    public void createLeadWithIsInterestedInFinancingChecked_leadTypeOwnersLoanTest() {
        final CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setId( "123123" );

        final CRMResponse crmResponse = new CRMResponse();
        crmResponse.setRecords( new ArrayList< Map< String, Object > >() );

        when( deDuplicationService.getDeDuplicatedLead( anyString(), anyObject() ) ).thenReturn( crmResponse );
        when( leadService.createLead( any( CRMLeadRequest.class ), anyBoolean() ) ).thenReturn( leadResponse );
        when( emailNotificationBuilder.convertTo( any( GenericLeadRequest.class ) ) )
                .thenReturn( new EmailNotification() );
        when( mailService.send( any( EmailNotification.class ) ) ).thenReturn( new NotificationResponse() );
        final Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Id", "123456" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        when( crmLeadRequestBuilder.convertTo( any( LeadRequest.class ) ) ).thenReturn( new CRMLeadRequest() );

        final GenericLeadRequest lead = new GenericLeadRequest();
        lead.setLeadType( RecordType.OWNERS_COM_LOANS.toString() );
        lead.setState( "test" );
        lead.setInterestedInFinancing( true );

        Mockito.when( contactServiceV1.getContact( anyString(), anyString() ) ).thenReturn( null );
        Mockito.when( contactBuilderV1.convertTo( lead, null ) ).thenReturn( new Contact() );

        businessServiceImpl.createLead( lead, true, null );
        verify( leadService ).createLead( any( CRMLeadRequest.class ), anyBoolean() );
    }

    /**
     * Test create lead with interested in financing false and lead type other
     * than Buyer and Owners.com Loans.
     */
    @Test
    public void createLeadWithIsInterestedInFinancingUnchecked_OtherLeadTypeTest() {
        final CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setId( "123123" );

        final CRMResponse crmResponse = new CRMResponse();
        crmResponse.setRecords( new ArrayList< Map< String, Object > >() );

        when( deDuplicationService.deDuplicateLead( anyString() ) ).thenReturn( crmResponse );
        when( deDuplicationService.getDeDuplicatedLead( anyString(), anyObject() ) ).thenReturn( crmResponse );
        when( leadService.createLead( any( CRMLeadRequest.class ), anyBoolean() ) ).thenReturn( leadResponse );
        when( emailNotificationBuilder.convertTo( any( GenericLeadRequest.class ) ) )
                .thenReturn( new EmailNotification() );
        when( mailService.send( any( EmailNotification.class ) ) ).thenReturn( new NotificationResponse() );
        final Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Id", "123456" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        when( crmLeadRequestBuilder.convertTo( any( LeadRequest.class ) ) ).thenReturn( new CRMLeadRequest() );

        final GenericLeadRequest lead = new GenericLeadRequest();
        lead.setLeadType( RecordType.MLS.toString() );
        lead.setState( "test" );
        lead.setInterestedInFinancing( false );

        Mockito.when( contactServiceV1.getContact( anyString(), anyString() ) ).thenReturn( null );
        Mockito.when( contactBuilderV1.convertTo( lead, null ) ).thenReturn( new Contact() );

        businessServiceImpl.createLead( lead, true, null );
        verify( leadService ).createLead( any( CRMLeadRequest.class ), anyBoolean() );
    }

    /**
     * Test create lead with interested in financing false and lead type
     * Owners.com Loans.
     */
    @Test
    public void createLeadWithIsInterestedInFinancingUnchecked_leadTypeOwnersLoanTest() {
        final CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setId( "123123" );

        final CRMResponse crmResponse = new CRMResponse();
        crmResponse.setRecords( new ArrayList< Map< String, Object > >() );

        when( deDuplicationService.getDeDuplicatedLead( anyString(), anyObject() ) ).thenReturn( crmResponse );
        when( leadService.createLead( any( CRMLeadRequest.class ), anyBoolean() ) ).thenReturn( leadResponse );
        when( emailNotificationBuilder.convertTo( any( GenericLeadRequest.class ) ) )
                .thenReturn( new EmailNotification() );
        when( mailService.send( any( EmailNotification.class ) ) ).thenReturn( new NotificationResponse() );
        final Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Id", "123456" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        when( crmLeadRequestBuilder.convertTo( any( LeadRequest.class ) ) ).thenReturn( new CRMLeadRequest() );

        final GenericLeadRequest lead = new GenericLeadRequest();
        lead.setLeadType( RecordType.OWNERS_COM_LOANS.toString() );
        lead.setState( "test" );
        lead.setInterestedInFinancing( false );
        Mockito.when( contactServiceV1.getContact( anyString(), anyString() ) ).thenReturn( null );
        Mockito.when( contactBuilderV1.convertTo( lead, null ) ).thenReturn( new Contact() );
        businessServiceImpl.createLead( lead, true, null );
        verify( leadService ).createLead( any( CRMLeadRequest.class ), anyBoolean() );
    }

    /**
     * Test create lead with interested in financing false and lead type Seller.
     */
    @Test
    public void createLeadWithIsInterestedInFinancingUnchecked_leadTypeSellerTest() {
        final CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setId( "123123" );

        final CRMResponse crmResponse = new CRMResponse();
        crmResponse.setRecords( new ArrayList< Map< String, Object > >() );

        when( deDuplicationService.deDuplicateLead( anyString() ) ).thenReturn( crmResponse );
        when( deDuplicationService.getDeDuplicatedLead( anyString(), anyObject() ) ).thenReturn( crmResponse );
        when( leadService.createLead( any( CRMLeadRequest.class ), anyBoolean() ) ).thenReturn( leadResponse );
        when( emailNotificationBuilder.convertTo( any( GenericLeadRequest.class ) ) )
                .thenReturn( new EmailNotification() );
        when( mailService.send( any( EmailNotification.class ) ) ).thenReturn( new NotificationResponse() );
        final Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Id", "123456" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        when( crmLeadRequestBuilder.convertTo( any( LeadRequest.class ) ) ).thenReturn( new CRMLeadRequest() );

        final GenericLeadRequest lead = new GenericLeadRequest();
        lead.setLeadType( RecordType.SELLER.toString() );
        lead.setState( "test" );
        lead.setInterestedInFinancing( false );

        Mockito.when( contactServiceV1.getContact( anyString(), anyString() ) ).thenReturn( null );
        Mockito.when( contactBuilderV1.convertTo( lead, null ) ).thenReturn( new Contact() );

        businessServiceImpl.createLead( lead, true, null );
        verify( leadService ).createLead( any( CRMLeadRequest.class ), anyBoolean() );
    }

    /**
     * Should create lead if buyer is found in search by contact email.
     */
    @Test
    public void shouldCreateLead_IfBuyerNotFound_InSearchByContactEmail() {
        final CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setId( "123123" );

        final CRMResponse crmResponse = new CRMResponse();
        crmResponse.setRecords( new ArrayList< Map< String, Object > >() );
        doNothing().when( opportunityBusinessService ).processBuyerRequest( any( LeadRequest.class ), anyString(),
                anyString() );
        when( deDuplicationService.getSearchByContactEmail( anyString() ) ).thenReturn( new Search() );

        when( deDuplicationService.getDeDuplicatedLead( anyString(), anyObject() ) ).thenReturn( crmResponse );
        when( leadService.createLead( any( CRMLeadRequest.class ), anyBoolean() ) ).thenReturn( leadResponse );
        when( emailNotificationBuilder.convertTo( any( GenericLeadRequest.class ) ) )
                .thenReturn( new EmailNotification() );

        final Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Id", "123456" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        when( crmLeadRequestBuilder.convertTo( any( LeadRequest.class ) ) ).thenReturn( new CRMLeadRequest() );
        when( config.getAfterHoursStates() ).thenReturn( "AB" );

        final GenericLeadRequest lead = new GenericLeadRequest();
        lead.setLeadType( RecordType.BUYER.toString() );
        lead.setState( "test" );

        Mockito.when( contactServiceV1.getContact( anyString(), anyString() ) ).thenReturn( null );
        Mockito.when( contactBuilderV1.convertTo( lead, null ) ).thenReturn( new Contact() );

        businessServiceImpl.createLead( lead, true, null );

        verify( leadService ).createLead( any( CRMLeadRequest.class ), anyBoolean() );
    }

    /**
     * Lead status.
     *
     * @return the object[][]
     */
    @DataProvider( name = "leadStatus" )
    public Object[][] leadStatus() {
        return new Object[][] { { "open" }, { "lost" }, { "qualified" }, { "open" } };
    }

    /**
     * Creates the buyer lead for dedupe.
     *
     * @param status
     *            the status
     */
    @Test( dataProvider = "leadStatus" )
    public void createBuyerLeadForDedupe( final String status ) {
        final GenericLeadRequest lead = testCreateLead( status );
        businessServiceImpl.createLead( lead, true, null );
    }

    /**
     * Test create lead.
     *
     * @param status
     *            the status
     * @return the generic lead request
     */
    protected GenericLeadRequest testCreateLead( final String status ) {
        final CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setId( "123123" );

        final CRMResponse crmResponse = new CRMResponse();
        final Map< String, Object > dedupMap = new HashMap<>();
        final List< Map< String, Object > > list = new ArrayList();
        dedupMap.put( CRMConstants.ID, "Test" );
        dedupMap.put( LEAD_DEDUP_COUNT, "1" );
        list.add( dedupMap );
        crmResponse.setRecords( list );
        when( config.getAfterHoursStates() ).thenReturn( "AB" );
        when( deDuplicationService.deDuplicateLead( anyString() ) ).thenReturn( crmResponse );
        when( deDuplicationService.getDeDuplicatedLead( anyString(), anyObject() ) ).thenReturn( crmResponse );
        final CRMLeadResponse response = new CRMLeadResponse();
        response.setLeadStatus( status );
        when( leadService.getLead( anyString() ) ).thenReturn( response );
        when( leadService.createLead( any( CRMLeadRequest.class ) ) ).thenReturn( leadResponse );
        when( emailNotificationBuilder.convertTo( any( GenericLeadRequest.class ) ) )
                .thenReturn( new EmailNotification() );
        when( mailService.send( any( EmailNotification.class ) ) ).thenReturn( new NotificationResponse() );
        final Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Id", "123456" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        when( crmLeadRequestBuilder.convertTo( any( LeadRequest.class ), any( CRMLeadRequest.class ) ) )
                .thenReturn( new CRMLeadRequest() );
        when( crmLeadRequestBuilder.convertTo( any( LeadRequest.class ) ) ).thenReturn( new CRMLeadRequest() );

        final GenericLeadRequest lead = new GenericLeadRequest();
        lead.setLeadType( RecordType.BUYER.toString() );
        lead.setState( "AD" );
        Mockito.when( contactServiceV1.getContact( anyString(), anyString() ) ).thenReturn( null );
        Mockito.when( contactBuilderV1.convertTo( lead, null ) ).thenReturn( new Contact() );
        when( opportunityBusinessService.updateOpportunity( any( LeadRequest.class ), anyString() ) )
                .thenReturn( new OpportunityResponse() );
        when( leadService.createLead( any( CRMLeadRequest.class ), anyBoolean() ) ).thenReturn( leadResponse );
        when( leadService.updateLead( any( CRMLeadRequest.class ), anyString(), anyBoolean() ) )
                .thenReturn( new CRMLeadResponse() );
        return lead;
    }

    /**
     * Creates the buyer lead with after now.
     */
    @Test
    public void createBuyerLeadWithAfterNow() {
        final CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setId( "123123" );

        final CRMResponse crmResponse = new CRMResponse();
        crmResponse.setRecords( new ArrayList< Map< String, Object > >() );

        when( deDuplicationService.deDuplicateLead( anyString() ) ).thenReturn( crmResponse );
        when( deDuplicationService.getDeDuplicatedLead( anyString(), anyObject() ) ).thenReturn( crmResponse );
        when( leadService.createLead( any( CRMLeadRequest.class ), anyBoolean() ) ).thenReturn( leadResponse );
        when( emailNotificationBuilder.convertTo( any( GenericLeadRequest.class ) ) )
                .thenReturn( new EmailNotification() );
        when( mailService.send( any( EmailNotification.class ) ) ).thenReturn( new NotificationResponse() );
        final Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Id", "123456" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        when( crmLeadRequestBuilder.convertTo( any( LeadRequest.class ) ) ).thenReturn( new CRMLeadRequest() );

        final GenericLeadRequest lead = new GenericLeadRequest();
        lead.setLeadType( RecordType.BUYER.toString() );
        Mockito.when( contactServiceV1.getContact( anyString(), anyString() ) ).thenReturn( null );
        Mockito.when( contactBuilderV1.convertTo( lead, null ) ).thenReturn( new Contact() );

        businessServiceImpl.createLead( lead, false, null );

        verify( leadService ).createLead( any( CRMLeadRequest.class ), anyBoolean() );
    }

    /**
     * Creates the buyer lead disable notification.
     */
    @Test
    public void createBuyerLead_ScoreAndLableShouldUpdate() {

        final CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setId( "123123" );

        final CRMResponse crmResponse = new CRMResponse();
        crmResponse.setRecords( new ArrayList< Map< String, Object > >() );
        final Contact con = new Contact();
        when( config.getAfterHoursStates() ).thenReturn( "AB" );
        when( deDuplicationService.deDuplicateLead( anyString() ) ).thenReturn( crmResponse );
        when( deDuplicationService.getDeDuplicatedLead( anyString(), anyObject() ) ).thenReturn( crmResponse );
        when( leadService.createLead( any( CRMLeadRequest.class ), anyBoolean() ) ).thenReturn( leadResponse );
        when( emailNotificationBuilder.convertTo( any( GenericLeadRequest.class ) ) )
                .thenReturn( new EmailNotification() );
        when( mailService.send( any( EmailNotification.class ) ) ).thenReturn( new NotificationResponse() );
        final Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Id", "123456" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        when( crmLeadRequestBuilder.convertTo( any( LeadRequest.class ) ) ).thenReturn( new CRMLeadRequest() );
        when( leadOpportunityBusinessConfig.isContactDatabaseConfig() ).thenReturn( true );
        when( contactServiceV1.getContact( anyString(), anyString() ) ).thenReturn( con );
        when( contactBuilderV1.convertTo( any( LeadRequest.class ), any( Contact.class ) ) ).thenReturn( con );
        Mockito.doNothing().when( leadTask ).updateLeadScore( any( LeadRequest.class ), any(Contact.class), Mockito.anyInt(),
                Mockito.anyBoolean() );

        when( config.getShiftHoursStates1() ).thenReturn( "AB" );
        final GenericLeadRequest lead = new GenericLeadRequest();
        lead.setLeadType( RecordType.BUYER.toString() );
        lead.setState( "AB" );

        when( config.getTimeZoneId() ).thenReturn( "Asia/Kolkata" );
        when( config.getOfficeAfterHourStart() ).thenReturn( 1 );
        when( config.getOfficeAfterMinsStart() ).thenReturn( 1 );
        when( config.isOfficeAfterHourEnabled() ).thenReturn( true );
        when( config.getShiftHoursStates1() ).thenReturn( "AC" );
        when( config.getShiftHoursStates2() ).thenReturn( "AB" );

        businessServiceImpl.createLead( lead, true, null );

        verify( leadService ).createLead( any( CRMLeadRequest.class ), anyBoolean() );
        verify( leadTask ).updateLeadScore( any( LeadRequest.class ), any(Contact.class), Mockito.anyInt(),
                Mockito.anyBoolean() );
        verify( config, Mockito.times( 2 ) ).getTimeZoneId();
        verify( config, Mockito.times( 1 ) ).getOfficeAfterHourStart();
        verify( config, Mockito.times( 1 ) ).getOfficeAfterMinsStart();
        verify( config, Mockito.times( 1 ) ).getShiftHourStartState2();
        verify( config, Mockito.times( 1 ) ).getShiftHourEndState2();
    }

    /**
     * Test create buyer lead handle create lead_from crm.
     */
    @Test
    public void testCreateBuyerLeadHandleCreateLead_fromCRM() {
        final LeadSource source = new LeadSource();
        source.setRecordTypeName( "Buyer" );
        source.setState( "AB" );
        source.setId( "test" );
        source.setCreatedById( "re" );
        when( config.getTimeZoneId() ).thenReturn( "Asia/Kolkata" );
        when( config.getShiftHourStartState2() ).thenReturn( 1 );
        when( config.getShiftHourEndState2() ).thenReturn( 1 );
        when( config.isOfficeAfterHourEnabled() ).thenReturn( true );
        when( config.getShiftHoursStates2() ).thenReturn( "AB" );
        when( config.getShiftHoursStates1() ).thenReturn( "AV" );
        when( config.getAfterHoursStates() ).thenReturn( "AB" );
        when( config.getSfApiUserId() ).thenReturn( "test" );
        businessServiceImpl.handleLeadCreate( source );

        verify( leadTask ).updateLeadScore( source, "test", false );
        verify( config, Mockito.times( 1 ) ).getTimeZoneId();
    }

    /**
     * Creates the seller lead score and lable should not update.
     */
    @Test
    public void createSellerLead_ScoreAndLableShouldNotUpdate() {

        final CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setId( "123123" );

        final CRMResponse crmResponse = new CRMResponse();
        crmResponse.setRecords( new ArrayList< Map< String, Object > >() );
        final Contact con = new Contact();

        when( deDuplicationService.deDuplicateLead( anyString() ) ).thenReturn( crmResponse );
        when( deDuplicationService.getDeDuplicatedLead( anyString(), anyObject() ) ).thenReturn( crmResponse );
        when( leadService.createLead( any( CRMLeadRequest.class ), anyBoolean() ) ).thenReturn( leadResponse );
        when( emailNotificationBuilder.convertTo( any( GenericLeadRequest.class ) ) )
                .thenReturn( new EmailNotification() );
        when( mailService.send( any( EmailNotification.class ) ) ).thenReturn( new NotificationResponse() );
        final Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Id", "123456" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        when( crmLeadRequestBuilder.convertTo( any( LeadRequest.class ) ) ).thenReturn( new CRMLeadRequest() );
        when( leadOpportunityBusinessConfig.isContactDatabaseConfig() ).thenReturn( true );
        when( contactServiceV1.getContact( anyString(), anyString() ) ).thenReturn( con );
        when( contactBuilderV1.convertTo( any( LeadRequest.class ), any( Contact.class ) ) ).thenReturn( con );

        final GenericLeadRequest lead = new GenericLeadRequest();
        lead.setLeadType( RecordType.SELLER.toString() );
        businessServiceImpl.createLead( lead, false, null );

        verify( leadService ).createLead( any( CRMLeadRequest.class ), anyBoolean() );
        Mockito.verifyZeroInteractions( leadTask );
    }

    /**
     * Creates the buyer lead disable notification dedup.
     */
    @Test( enabled = false )
    public void createBuyerLeadDisableNotificationDedup() {

        final CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setId( "123123" );
        leadResponse.setLeadStatus( "test" );
        final CRMResponse crmResponse = new CRMResponse();
        final List< Map< String, Object > > list = new ArrayList();
        final Map< String, Object > dedupMap = new HashMap<>();
        dedupMap.put( CRMConstants.ID, "Test" );
        dedupMap.put( LEAD_DEDUP_COUNT, "1" );
        list.add( dedupMap );
        crmResponse.setRecords( list );
        final Contact con = new Contact();

        when( deDuplicationService.deDuplicateLead( anyString() ) ).thenReturn( crmResponse );
        when( deDuplicationService.getDeDuplicatedLead( anyString(), anyObject() ) ).thenReturn( crmResponse );
        when( leadService.createLead( any( CRMLeadRequest.class ), anyBoolean() ) ).thenReturn( leadResponse );
        when( leadService.getLead( anyString() ) ).thenReturn( leadResponse );
        when( emailNotificationBuilder.convertTo( any( GenericLeadRequest.class ) ) )
                .thenReturn( new EmailNotification() );
        when( mailService.send( any( EmailNotification.class ) ) ).thenReturn( new NotificationResponse() );
        final Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Id", "123456" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        when( crmLeadRequestBuilder.convertTo( any( LeadRequest.class ) ) ).thenReturn( new CRMLeadRequest() );
        when( leadOpportunityBusinessConfig.isContactDatabaseConfig() ).thenReturn( true );
        when( contactServiceV1.getContact( anyString(), anyString() ) ).thenReturn( con );
        when( contactBuilderV1.convertTo( any( LeadRequest.class ), any( Contact.class ) ) ).thenReturn( con );

        final GenericLeadRequest lead = new GenericLeadRequest();
        lead.setLeadType( RecordType.BUYER.toString() );
        businessServiceImpl.createLead( lead, false, null );
        verify( leadService ).createLead( any( CRMLeadRequest.class ), anyBoolean() );
    }

    /**
     * Creates the buyer lead enable notification.
     */
    @Test
    public void createBuyerLeadEnableNotification() {
        final CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setId( "123123" );

        final CRMResponse crmResponse = new CRMResponse();
        crmResponse.setRecords( new ArrayList< Map< String, Object > >() );

        when( deDuplicationService.deDuplicateLead( anyString() ) ).thenReturn( crmResponse );
        when( deDuplicationService.getDeDuplicatedLead( anyString(), anyObject() ) ).thenReturn( crmResponse );
        when( leadService.createLead( any( CRMLeadRequest.class ), anyBoolean() ) ).thenReturn( leadResponse );
        when( emailNotificationBuilder.convertTo( any( GenericLeadRequest.class ) ) )
                .thenReturn( new EmailNotification() );
        when( mailService.send( any( EmailNotification.class ) ) ).thenReturn( new NotificationResponse() );
        final Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Id", "123456" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );

        when( crmLeadRequestBuilder.convertTo( any( LeadRequest.class ) ) ).thenReturn( new CRMLeadRequest() );
        when( config.isOfficeAfterHourEnabled() ).thenReturn( true );
        when( config.getShiftHoursStates1() ).thenReturn( "AB" );
        final GenericLeadRequest lead = new GenericLeadRequest();

        when( config.getTimeZoneId() ).thenReturn( "Asia/Kolkata" );
        when( config.getShiftHourStartState1() ).thenReturn( 1 );
        when( config.getShiftHourEndState1() ).thenReturn( 1 );
        Mockito.when( contactServiceV1.getContact( anyString(), anyString() ) ).thenReturn( null );
        Mockito.when( contactBuilderV1.convertTo( lead, null ) ).thenReturn( new Contact() );
        Mockito.doNothing().when( leadTask ).updateLeadScore( any( LeadRequest.class ), any(Contact.class),  Mockito.anyInt(),
                Mockito.anyBoolean() );

        when( config.getAfterHoursStates() ).thenReturn( "AB" );
        lead.setLeadType( RecordType.BUYER.toString() );
        lead.setState( "AB" );
        businessServiceImpl.createLead( lead, true, null );

        verify( leadService ).createLead( any( CRMLeadRequest.class ), anyBoolean() );
        verify( config, Mockito.times( 2 ) ).getTimeZoneId();
        verify( config, Mockito.times( 1 ) ).getOfficeAfterHourStart();
        verify( config, Mockito.times( 1 ) ).getOfficeAfterMinsStart();
        verify( config, Mockito.times( 1 ) ).getShiftHourStartState1();
        verify( config, Mockito.times( 1 ) ).getShiftHourEndState1();
    }

    /**
     * Test request type creat lead.
     */
    @Test
    public void testRequestTypeCreatLead() {
        final CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setId( "123123" );

        final CRMResponse crmResponse = new CRMResponse();
        final List< Map< String, Object > > records = new ArrayList< Map< String, Object > >();
        final Map< String, Object > record = new HashMap< String, Object >();
        record.put( "Id", "value" );
        record.put( LEAD_DEDUP_COUNT, "1" );
        records.add( record );
        crmResponse.setRecords( records );

        when( deDuplicationService.getDeDuplicatedLead( anyString(), anyObject() ) ).thenReturn( crmResponse );
        final CRMLeadResponse response = new CRMLeadResponse();
        response.setLeadStatus( "open" );
        when( leadService.getLead( anyString() ) ).thenReturn( response );
        when( leadService.createLead( any( CRMLeadRequest.class ) ) ).thenReturn( leadResponse );
        when( emailNotificationBuilder.convertTo( any( GenericLeadRequest.class ) ) )
                .thenReturn( new EmailNotification() );
        when( mailService.send( any( EmailNotification.class ) ) ).thenReturn( new NotificationResponse() );
        final Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Id", "123456" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        when( crmLeadRequestBuilder.convertTo( any( LeadRequest.class ), any( CRMLeadRequest.class ) ) )
                .thenReturn( new CRMLeadRequest() );
        when( crmLeadRequestBuilder.convertTo( any( LeadRequest.class ) ) ).thenReturn( new CRMLeadRequest() );

        final RequestTypeLeadRequest lead = new RequestTypeLeadRequest();
        lead.setState( "test" );
        Mockito.when( contactServiceV1.getContact( anyString(), anyString() ) ).thenReturn( null );
        Mockito.when( contactBuilderV1.convertTo( any( GenericLeadRequest.class ), any( Contact.class ) ) )
                .thenReturn( new Contact() );
        when( opportunityBusinessService.updateOpportunity( any( LeadRequest.class ), anyString() ) )
                .thenReturn( new OpportunityResponse() );
        when( leadService.updateLead( any( CRMLeadRequest.class ), anyString(), anyBoolean() ) )
                .thenReturn( new CRMLeadResponse() );
        when( config.getAfterHoursStates() ).thenReturn( "AB" );

        businessServiceImpl.createLead( lead, null );
        verify( leadService ).updateLead( any( CRMLeadRequest.class ), anyString(), anyBoolean() );
    }

    /**
     * Test request type creat lead with lead status lost.
     */
    @Test
    public void testRequestTypeCreatLeadWithLeadStatusLost() {
        final CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setId( "123123" );

        final CRMResponse crmResponse = new CRMResponse();
        final List< Map< String, Object > > records = new ArrayList< Map< String, Object > >();
        final Map< String, Object > record = new HashMap< String, Object >();
        record.put( "Id", "value" );
        record.put( LEAD_DEDUP_COUNT, "1" );
        records.add( record );
        crmResponse.setRecords( records );

        when( deDuplicationService.getDeDuplicatedLead( anyString(), anyObject() ) ).thenReturn( crmResponse );
        final CRMLeadResponse response = new CRMLeadResponse();
        response.setLeadStatus( "lost" );
        when( leadService.getLead( anyString() ) ).thenReturn( response );
        when( leadService.createLead( any( CRMLeadRequest.class ) ) ).thenReturn( leadResponse );
        when( emailNotificationBuilder.convertTo( any( GenericLeadRequest.class ) ) )
                .thenReturn( new EmailNotification() );
        when( mailService.send( any( EmailNotification.class ) ) ).thenReturn( new NotificationResponse() );
        final Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Id", "123456" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        when( crmLeadRequestBuilder.convertTo( any( LeadRequest.class ), any( CRMLeadRequest.class ) ) )
                .thenReturn( new CRMLeadRequest() );
        when( crmLeadRequestBuilder.convertTo( any( LeadRequest.class ) ) ).thenReturn( new CRMLeadRequest() );

        final RequestTypeLeadRequest lead = new RequestTypeLeadRequest();
        lead.setState( "test" );
        when( opportunityBusinessService.updateOpportunity( any( LeadRequest.class ), anyString() ) )
                .thenReturn( new OpportunityResponse() );
        when( leadService.updateLead( any( CRMLeadRequest.class ), anyString(), anyBoolean() ) )
                .thenReturn( new CRMLeadResponse() );
        Mockito.when( contactServiceV1.getContact( anyString(), anyString() ) ).thenReturn( null );
        Mockito.when( contactBuilderV1.convertTo( any( GenericLeadRequest.class ), any( Contact.class ) ) )
                .thenReturn( new Contact() );
        when( leadService.createLead( any( CRMLeadRequest.class ), anyBoolean() ) ).thenReturn( new CRMLeadResponse() );
        when( config.getAfterHoursStates() ).thenReturn( "AB" );

        businessServiceImpl.createLead( lead, null );
        verify( leadService ).createLead( any( CRMLeadRequest.class ), anyBoolean() );
    }

    /**
     * Test request type creat lead with lead status qualified.
     */
    @Test
    public void testRequestTypeCreatLeadWithLeadStatusQualified() {
        final CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setId( "123123" );

        final CRMResponse crmResponse = new CRMResponse();
        final List< Map< String, Object > > records = new ArrayList< Map< String, Object > >();
        final Map< String, Object > record = new HashMap< String, Object >();
        record.put( "Id", "value" );
        record.put( LEAD_DEDUP_COUNT, "1" );
        records.add( record );
        crmResponse.setRecords( records );

        when( deDuplicationService.getDeDuplicatedLead( anyString(), anyObject() ) ).thenReturn( crmResponse );
        final CRMLeadResponse response = new CRMLeadResponse();
        response.setLeadStatus( "qualified" );
        when( leadService.getLead( anyString() ) ).thenReturn( response );
        when( leadService.createLead( any( CRMLeadRequest.class ) ) ).thenReturn( leadResponse );
        when( emailNotificationBuilder.convertTo( any( GenericLeadRequest.class ) ) )
                .thenReturn( new EmailNotification() );
        when( mailService.send( any( EmailNotification.class ) ) ).thenReturn( new NotificationResponse() );
        final Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Id", "123456" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        when( crmLeadRequestBuilder.convertTo( any( LeadRequest.class ), any( CRMLeadRequest.class ) ) )
                .thenReturn( new CRMLeadRequest() );
        when( crmLeadRequestBuilder.convertTo( any( LeadRequest.class ) ) ).thenReturn( new CRMLeadRequest() );

        final RequestTypeLeadRequest lead = new RequestTypeLeadRequest();
        lead.setState( "test" );
        when( opportunityBusinessService.updateOpportunity( any( LeadRequest.class ), anyString() ) )
                .thenReturn( new OpportunityResponse() );
        when( leadService.updateLead( any( CRMLeadRequest.class ), anyString(), anyBoolean() ) )
                .thenReturn( new CRMLeadResponse() );
        when( config.getAfterHoursStates() ).thenReturn( "AB" );

        Mockito.when( contactServiceV1.getContact( anyString(), anyString() ) ).thenReturn( null );
        Mockito.when( contactBuilderV1.convertTo( any( GenericLeadRequest.class ), any( Contact.class ) ) )
                .thenReturn( new Contact() );

        businessServiceImpl.createLead( lead, null );
        verify( opportunityBusinessService ).updateOpportunity( any( LeadRequest.class ), anyString() );
    }

    /**
     * Test create unbounce lead.
     *
     * @param status
     *            the status
     */
    @Test( dataProvider = "leadStatus" )
    public void testCreateUnbounceLead( final String status ) {
        final String jsonData = "{\"lastName\":[\"vishwa1642\"],\"email\":[\"vishu-19a@sharklasers.com\"],\"source\":[\"source\"],"
                + "\"leadType\":[\"BUYER\"],\"leadSourceUrl\":[\"http://www.google.com\"],\"requestType\":[\"SCHEDULE_TOUR\"]}";
        final GenericLeadRequest lead = testCreateLead( status );
        lead.setLastName( "lastName" );
        lead.setLeadSourceUrl( "http://www.google.com" );
        lead.setEmail( "vishu-19a@sharklasers.com" );
        lead.setRequestType( LeadRequestType.SCHEDULE_TOUR.name() );
        lead.setLeadType( RecordType.BUYER.name() );
        lead.setSource( "source" );
        when( genericLeadRequestBuilder.convertTo( anyMap() ) ).thenReturn( lead );
        final LeadResponse resp = businessServiceImpl.createUnbounceLead( jsonData, null );
        Assert.assertNotNull( resp );
    }

    /**
     * Test create unbounce lead validation error.
     *
     * @param status
     *            the status
     */
    @Test( dataProvider = "leadStatus", expectedExceptions = InvalidArgumentException.class )
    public void testCreateUnbounceLeadValidationError( final String status ) {
        final String jsonData = "{\"lastName\":[\"vishwa1642\"],\"email\":[\"vishu-19a@sharklasers.com\"],\"source\":[\"source\"],"
                + "\"leadType\":[\"BUYER\"],\"leadSourceUrl\":[\"http://www.google.com\"],\"requestType\":[\"SCHEDULE_TOUR\"]}";
        final GenericLeadRequest lead = testCreateLead( status );
        lead.setLeadSourceUrl( "http://www.google.com" );
        lead.setEmail( "vishu-19a@sharklasers.com" );
        lead.setRequestType( LeadRequestType.SCHEDULE_TOUR.name() );
        lead.setLeadType( RecordType.BUYER.name() );
        lead.setSource( "source" );
        when( genericLeadRequestBuilder.convertTo( anyMap() ) ).thenReturn( lead );
        doThrow( InvalidArgumentException.class ).when( leadValidator ).validateUnbounceLeadRequest( lead );
        businessServiceImpl.createUnbounceLead( jsonData, null );
    }

    /**
     * Test create unbounce lead null lead object.
     */
    @Test( expectedExceptions = InvalidArgumentException.class )
    public void testCreateUnbounceLeadNullLeadObject() {
        final String jsonData = "{\"lastName\":[\"vishwa1642\"],\"email\":[\"vishu-19a@sharklasers.com\"],\"source\":[\"source\"],"
                + "\"leadType\":[\"BUYER\"],\"leadSourceUrl\":[\"http://www.google.com\"],\"requestType\":[\"SCHEDULE_TOUR\"]}";
        when( genericLeadRequestBuilder.convertTo( anyMap() ) ).thenReturn( null );
        doThrow( InvalidArgumentException.class ).when( leadValidator ).validateUnbounceLeadRequest( null );
        businessServiceImpl.createUnbounceLead( jsonData, null );
    }

    /**
     * Should get CRM lead for valid input.
     */
    @Test
    public void shouldGetCRMLeadForValidInput() {
        final String leadId = "test lead id";
        final CRMLeadResponse expected = new CRMLeadResponse();
        when( leadService.getLead( anyString() ) ).thenReturn( expected );
        final CRMLeadResponse actual = businessServiceImpl.getCRMLead( leadId );
        verify( leadService ).getLead( anyString() );
        assertEquals( expected, actual );
    }

    /**
     * Load lead source.
     *
     * @return the lead source
     */
    private LeadSource loadLeadSource() {
        final LeadSource leadSource = new LeadSource();
        leadSource.setDoNotCall( false );
        leadSource.setDoNotEmail( false );
        leadSource.setState( "AK" );
        leadSource.setRecordType( RecordType.BUYER.getType() );
        leadSource.setSource( "testSource" );
        return leadSource;
    }

    /**
     * Test handle lead change.
     */
    @Test
    public void testHandleLeadChange() {
        final LeadSource leadSource = loadLeadSource();
        leadSource.setFirstName( "test" );
        leadSource.setLastName( "test" );
        leadSource.setEmail( "test@test.com" );
        final String state = "test";
        final String excludedState = "GA";
        leadSource.setState( state );

        final List< String > leadList = new ArrayList< String >();
        leadList.add( "test@test.com" );

        final EmailNotification emailNotification = new EmailNotification();

        final NotificationResponse notificationResponse = new NotificationResponse();
        notificationResponse.setStatus( "NEW" );

        leadSource.setLeadStatus( FORWARDED_TO_REF_EX.toString() );
        when( config.isLeadReferralEnabled() ).thenReturn( true );

        ReflectionTestUtils.setField( config, "leadReferralEnabled", true );
        final Map< String, Object > leadMap = new HashMap<>();
        leadMap.put( "Name", "Buyer" );
        when( config.getOwnerStates() ).thenReturn( excludedState );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( leadMap );
        final ReferralExchangeRequest referralExchangeRequest = new ReferralExchangeRequest();

        when( referralExchangeRequestBuilder.convertTo( leadSource ) ).thenReturn( referralExchangeRequest );
        when( config.isReferralEmailNotificationEnabled() ).thenReturn( TRUE );

        when( referralExchangeRequestEmailBuilder.convertTo( "test test", leadList ) ).thenReturn( emailNotification );
        when( mailService.send( emailNotification ) ).thenReturn( notificationResponse );
        when( contactBuilderV1.convertTo( any( LeadRequest.class ), any( Contact.class ) ) )
                .thenReturn( new Contact() );
        when( ocleadRequestBuilder.convertTo( any( LeadSource.class ) ) ).thenReturn( new GenericLeadRequest() );
        businessServiceImpl.handleLeadChange( leadSource );

        assertNotNull( emailNotification );
        assertNotNull( notificationResponse );
    }

    /**
     * Test handle lead change_should create ocl lead.
     */
    // Commented as part of story OWNCORE-6869 - Disabling ALL OCL lead creation
    // by Gravitas.
    /*
     * @Test
     * public void testHandleLeadChange_shouldCreateOclLead() {
     * final LeadSource leadSource = loadLeadSource();
     * leadSource.setState( "FL" );
     * leadSource.setLeadStatus( QUALIFIED.getStatus() );
     * leadSource.setInterestedInFinancing( true );
     * final CRMResponse crmResponse = new CRMResponse();
     * crmResponse.setRecords( new ArrayList< Map< String, Object > >() );
     * final GenericLeadRequest leadRequest = new GenericLeadRequest();
     * final CRMLeadRequest crmLeadRequest = new CRMLeadRequest();
     * final Map< String, Object > leadMap = new HashMap<>();
     * leadMap.put( "Name", "Buyer" );
     * leadSource.setLastModifiedById( "test" );
     * when( config.getSfApiUserId() ).thenReturn( "1test" );
     * when( contactBuilderV1.convertTo( any( LeadRequest.class ), any(
     * Contact.class ) ) )
     * .thenReturn( new Contact() );
     * when( ocleadRequestBuilder.convertTo( any( LeadSource.class ) )
     * ).thenReturn( new GenericLeadRequest() );
     * when( crmQueryService.findOne( anyString(), any( QueryParams.class ) )
     * ).thenReturn( leadMap );
     * when( opportunityServiceImpl.isOpportunityExistsForRecordTypeAndEmail(
     * leadSource.getEmail(),
     * RecordType.OWNERS_COM_LOANS ) ).thenReturn( false );
     * when( deDuplicationService.getDeDuplicatedLead( leadSource.getEmail(),
     * RecordType.OWNERS_COM_LOANS ) )
     * .thenReturn( crmResponse );
     * when( ownerGenericLeadRequestBuilder.convertTo( leadSource )
     * ).thenReturn( leadRequest );
     * doNothing().when( leadValidator ).validateOCLLeadRequest( leadRequest );
     * when( crmLeadRequestBuilder.convertTo( leadRequest ) ).thenReturn(
     * crmLeadRequest );
     * when( leadService.createLead( crmLeadRequest, true ) ).thenReturn( new
     * CRMLeadResponse() );
     * businessServiceImpl.handleLeadChange( leadSource );
     * verify( crmQueryService ).findOne( anyString(), any( QueryParams.class )
     * );
     * verify( ownerGenericLeadRequestBuilder ).convertTo( leadSource );
     * verify( leadValidator ).validateOCLLeadRequest( leadRequest );
     * verify( crmLeadRequestBuilder ).convertTo( leadRequest );
     * verify( leadService ).createLead( crmLeadRequest, true );
     * verifyZeroInteractions( marketingEmailBusinessService );
     * verifyZeroInteractions( referralExchangeRequestBuilder );
     * verifyZeroInteractions( referralExchangeService );
     * }
     */
    /**
     * Should forward to referral exchange for valid lead source.
     */
    @Test
    public void shouldForwardToReferralExchangeForValidLeadSource() {
        final LeadSource leadSource = loadLeadSource();
        final String state = "GA";
        final String excludedOwnerState = "GA";
        final String excludedReferralState = "MA";
        leadSource.setState( state );
        leadSource.setLeadStatus( FORWARDED_TO_REF_EX.getStatus() );
        leadSource.setLastModifiedById( "test" );
        when( config.isLeadReferralEnabled() ).thenReturn( true );
        when( config.getSfApiUserId() ).thenReturn( "1test" );
        when( config.getReferralExcludedStates() ).thenReturn( excludedReferralState );
        when( config.getOwnerStates() ).thenReturn( excludedOwnerState );
        when( contactBuilderV1.convertTo( any( LeadRequest.class ), any( Contact.class ) ) )
                .thenReturn( new Contact() );
        when( ocleadRequestBuilder.convertTo( leadSource ) ).thenReturn( new GenericLeadRequest() );
        final Map< String, Object > leadMap = new HashMap<>();
        leadMap.put( "Name", "Buyer" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( leadMap );
        final ReferralExchangeRequest referralExchangeRequest = new ReferralExchangeRequest();
        when( referralExchangeRequestBuilder.convertTo( leadSource ) ).thenReturn( referralExchangeRequest );

        businessServiceImpl.handleLeadChange( leadSource );
        verify( crmQueryService ).findOne( anyString(), any( QueryParams.class ) );
        verify( ocleadRequestBuilder ).convertTo( leadSource );
        verify( contactBuilderV1 ).convertTo( any( LeadRequest.class ), any( Contact.class ) );
    }

    /**
     * Should forward to referral exchange for valid lead source.
     */
    @Test
    public void shouldUpdateLead_WithReferralFailureDetails_IfExceptionOccurs() {
        final LeadSource leadSource = loadLeadSource();
        final String state = "GA";
        final String excludedOwnerState = "GA";
        final String excludedReferralState = "MA";
        final Map< String, Object > leadMap = new HashMap<>();
        leadSource.setLeadStatus( FORWARDED_TO_REF_EX.getStatus() );
        when( config.isLeadReferralEnabled() ).thenReturn( true );
        when( config.getOwnerStates() ).thenReturn( excludedOwnerState );
        leadMap.put( "Name", "Buyer" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( leadMap );
        final ReferralExchangeRequest referralExchangeRequest = new ReferralExchangeRequest();
        when( referralExchangeRequestBuilder.convertTo( leadSource ) ).thenReturn( referralExchangeRequest );
        when( referralExchangeService.forwardRequest( referralExchangeRequest ) )
                .thenThrow( new HttpClientErrorException( HttpStatus.ACCEPTED ) );
        when( contactBuilderV1.convertTo( any( LeadRequest.class ), any( Contact.class ) ) )
                .thenReturn( new Contact() );
        when( ocleadRequestBuilder.convertTo( any( LeadSource.class ) ) ).thenReturn( new GenericLeadRequest() );
        businessServiceImpl.handleLeadChange( leadSource );
        verify( referralExchangeService, VerificationModeFactory.times( 0 ) )
                .forwardRequest( any( ReferralExchangeRequest.class ) );
    }

    /**
     * Test handle lead change outbound attempt.
     */
    @Test
    public void testHandleLeadChangeOutboundAttempt() {
        final LeadSource leadSource = new LeadSource();
        leadSource.setCreatedDateTime( new DateTime() );
        final Map< String, Object > leadMap = new HashMap<>();
        leadMap.put( "Name", "Buyer" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( leadMap );
        leadSource.setLeadStatus( "Outbound Attempt" );
        doNothing().when( marketingEmailBusinessService ).startLeadFollowupEmailProcess( any( LeadSource.class ) );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( leadMap );

        when( contactBuilderV1.convertTo( any( LeadRequest.class ), any( Contact.class ) ) )
                .thenReturn( new Contact() );
        when( ocleadRequestBuilder.convertTo( any( LeadSource.class ) ) ).thenReturn( new GenericLeadRequest() );
        businessServiceImpl.handleLeadChange( leadSource );
        verify( referralExchangeService, VerificationModeFactory.times( 0 ) )
                .forwardRequest( any( ReferralExchangeRequest.class ) );
    }

    /**
     * Test handle lead change outbound attempt not buyer lead.
     */
    @Test
    public void testHandleLeadChangeOutboundAttemptNotBuyerLead() {
        final Map< String, Object > leadMap = new HashMap<>();
        leadMap.put( "Name", "Buyer1" );
        final LeadSource source = new LeadSource();
        source.setLeadStatus( "Outbound Attempt" );
        doNothing().when( marketingEmailBusinessService ).startLeadFollowupEmailProcess( any( LeadSource.class ) );
        when( contactBuilderV1.convertTo( any( LeadRequest.class ), any( Contact.class ) ) )
                .thenReturn( new Contact() );
        when( ocleadRequestBuilder.convertTo( any( LeadSource.class ) ) ).thenReturn( new GenericLeadRequest() );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( leadMap );
        businessServiceImpl.handleLeadChange( source );
        verify( marketingEmailBusinessService, VerificationModeFactory.times( 0 ) )
                .startLeadFollowupEmailProcess( any( LeadSource.class ) );
        verify( referralExchangeService, VerificationModeFactory.times( 0 ) )
                .forwardRequest( any( ReferralExchangeRequest.class ) );
    }

    /**
     * Test handle lead change lost not eligible lead.
     */
    @Test
    public void testHandleLeadChangeLostNotEligibleLead() {
        final Map< String, Object > leadMap = new HashMap<>();
        leadMap.put( "Name", "Buyer" );
        final LeadSource source = new LeadSource();
        source.setLeadStatus( "Lost" );
        doNothing().when( marketingEmailBusinessService ).startLeadFollowupEmailProcess( any( LeadSource.class ) );
        when( contactBuilderV1.convertTo( any( LeadRequest.class ), any( Contact.class ) ) )
                .thenReturn( new Contact() );
        when( ocleadRequestBuilder.convertTo( any( LeadSource.class ) ) ).thenReturn( new GenericLeadRequest() );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( leadMap );
        businessServiceImpl.handleLeadChange( source );
        verify( marketingEmailBusinessService, VerificationModeFactory.times( 0 ) )
                .startLeadFollowupEmailProcess( any( LeadSource.class ) );
        verify( referralExchangeService, VerificationModeFactory.times( 0 ) )
                .forwardRequest( any( ReferralExchangeRequest.class ) );
    }

    /**
     * Test handle lead change lost eligible lead.
     */
    @Test
    public void testHandleLeadChangeLostEligibleLead() {
        final Map< String, Object > leadMap = new HashMap<>();
        leadMap.put( "Name", "Buyer" );
        final LeadSource source = loadLeadSource();
        source.setLeadStatus( LeadStatus.QUALIFIED.getStatus() );
        source.setClosedReason( "Out Of Coverage Area" );
        when( contactBuilderV1.convertTo( any( LeadRequest.class ), any( Contact.class ) ) )
                .thenReturn( new Contact() );
        when( ocleadRequestBuilder.convertTo( any( LeadSource.class ) ) ).thenReturn( new GenericLeadRequest() );
        doNothing().when( marketingEmailBusinessService ).startLeadFollowupEmailProcess( any( LeadSource.class ) );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( leadMap );
        businessServiceImpl.handleLeadChange( source );
        verify( marketingEmailBusinessService, VerificationModeFactory.times( 0 ) )
                .startLeadFollowupEmailProcess( any( LeadSource.class ) );
    }

    /**
     * Should do nothing for invalid record type.
     */
    @Test
    public void shouldNotDoAnythingIfRecordTypeNotFoundInCrm() {
        final LeadSource source = new LeadSource();
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) )
                .thenThrow( ResultNotFoundException.class );
        when( contactBuilderV1.convertTo( any( LeadRequest.class ), any( Contact.class ) ) )
                .thenReturn( new Contact() );
        when( ocleadRequestBuilder.convertTo( any( LeadSource.class ) ) ).thenReturn( new GenericLeadRequest() );
        businessServiceImpl.handleLeadChange( source );
        verify( marketingEmailBusinessService, VerificationModeFactory.times( 0 ) )
                .startLeadFollowupEmailProcess( any( LeadSource.class ) );
        verify( referralExchangeService, VerificationModeFactory.times( 0 ) )
                .forwardRequest( any( ReferralExchangeRequest.class ) );
    }

    /**
     * Should not forward to referral exchange if disabled.
     */
    @Test
    public void shouldNotForwardToReferralExchangeIfDisabled() {
        final Map< String, Object > leadMap = new HashMap<>();
        leadMap.put( "Name", "Buyer" );
        final LeadSource source = new LeadSource();
        source.setLeadStatus( "Lost" );
        when( contactBuilderV1.convertTo( any( LeadRequest.class ), any( Contact.class ) ) )
                .thenReturn( new Contact() );
        when( ocleadRequestBuilder.convertTo( any( LeadSource.class ) ) ).thenReturn( new GenericLeadRequest() );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( leadMap );
        businessServiceImpl.handleLeadChange( source );
        verify( marketingEmailBusinessService, VerificationModeFactory.times( 0 ) )
                .startLeadFollowupEmailProcess( any( LeadSource.class ) );
        verify( referralExchangeService, VerificationModeFactory.times( 0 ) )
                .forwardRequest( any( ReferralExchangeRequest.class ) );
    }

    /**
     * Should not forward to referral exchange if lead state is present in pre
     * defined after hours states.
     */
    @Test
    public void shouldNot_ForwardTo_ReferralExchange_ForBlankLeadState() {
        final Map< String, Object > leadMap = new HashMap<>();
        leadMap.put( "Name", "Buyer" );
        final LeadSource source = loadLeadSource();
        source.setClosedReason( "Out Of Coverage Area" );
        source.setState( StringUtils.EMPTY );
        source.setLeadStatus( "Lost" );
        when( contactBuilderV1.convertTo( any( LeadRequest.class ), any( Contact.class ) ) )
                .thenReturn( new Contact() );
        when( ocleadRequestBuilder.convertTo( any( LeadSource.class ) ) ).thenReturn( new GenericLeadRequest() );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( leadMap );
        businessServiceImpl.handleLeadChange( source );
        verifyZeroInteractions( marketingEmailBusinessService );
    }

    /**
     * Should not forward to referral exchange if lead is already referred.
     */
    @Test
    public void shouldNot_ForwardTo_ReferralExchange_IfAlreadyReferred() {
        final Map< String, Object > leadMap = new HashMap<>();
        leadMap.put( "Name", "Buyer" );
        final LeadSource source = loadLeadSource();
        source.setClosedReason( "Out Of Coverage Area" );
        source.setState( StringUtils.EMPTY );
        source.setLeadStatus( "Lost" );
        source.setReferred( true );
        when( contactBuilderV1.convertTo( any( LeadRequest.class ), any( Contact.class ) ) )
                .thenReturn( new Contact() );
        when( ocleadRequestBuilder.convertTo( any( LeadSource.class ) ) ).thenReturn( new GenericLeadRequest() );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( leadMap );
        businessServiceImpl.handleLeadChange( source );
        verifyZeroInteractions( marketingEmailBusinessService );
        verifyZeroInteractions( referralExchangeService );
    }

    /**
     * Should not forward to referral exchange if lead has opted for 'do not
     * call'.
     */
    @Test
    public void shouldNot_ForwardTo_ReferralExchange_IfOptedFor_DoNotCall() {
        final Map< String, Object > leadMap = new HashMap<>();
        leadMap.put( "Name", "Buyer" );
        final LeadSource source = loadLeadSource();
        source.setClosedReason( "Out Of Coverage Area" );
        source.setState( StringUtils.EMPTY );
        source.setLeadStatus( "Lost" );
        source.setDoNotCall( true );
        when( contactBuilderV1.convertTo( any( LeadRequest.class ), any( Contact.class ) ) )
                .thenReturn( new Contact() );
        when( ocleadRequestBuilder.convertTo( any( LeadSource.class ) ) ).thenReturn( new GenericLeadRequest() );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( leadMap );
        businessServiceImpl.handleLeadChange( source );
        verifyZeroInteractions( marketingEmailBusinessService );
        verifyZeroInteractions( referralExchangeService );
    }

    /**
     * Should not forward to referral exchange if lead has opted for 'do not
     * email'.
     */
    @Test
    public void shouldNot_ForwardTo_ReferralExchange_IfOptedFor_DoNotEmail() {
        final Map< String, Object > leadMap = new HashMap<>();
        leadMap.put( "Name", "Buyer" );
        final LeadSource source = loadLeadSource();
        source.setClosedReason( "Out Of Coverage Area" );
        source.setState( StringUtils.EMPTY );
        source.setLeadStatus( "Lost" );
        source.setDoNotEmail( true );
        when( contactBuilderV1.convertTo( any( LeadRequest.class ), any( Contact.class ) ) )
                .thenReturn( new Contact() );
        when( ocleadRequestBuilder.convertTo( any( LeadSource.class ) ) ).thenReturn( new GenericLeadRequest() );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( leadMap );
        businessServiceImpl.handleLeadChange( source );
        verifyZeroInteractions( marketingEmailBusinessService );
        verifyZeroInteractions( referralExchangeService );
    }

    /**
     * Test get lead id by request type and email.
     */
    @Test
    public void testGetLeadIdByRequestTypeAndEmail() {
        final String email = "email@email.com";
        final String recordTypeId = "recordTypeId";
        final String leadId = "leadId";
        when( leadService.getLeadIdByEmailAndRecordTypeId( email, recordTypeId ) ).thenReturn( leadId );
        final String actualLeadId = businessServiceImpl.getLeadIdByRequestTypeAndEmail( recordTypeId, email );
        assertEquals( leadId, actualLeadId );
        verify( leadService ).getLeadIdByEmailAndRecordTypeId( email, recordTypeId );
    }

    /**
     * Test get lead id by request type and email should return null if lead id
     * not found.
     */
    @Test
    public void testGetLeadIdByRequestTypeAndEmailShouldReturnNullIfLeadIdNotFound() {
        final String recordType = "recordType";
        final String email = "email@email.com";
        final String recordTypeId = "recordTypeId";
        when( recordTypeService.getRecordTypeIdByName( recordType, LEAD.getName() ) ).thenReturn( recordTypeId );
        when( leadService.getLeadIdByEmailAndRecordTypeId( email, recordTypeId ) )
                .thenThrow( ResultNotFoundException.class );
        final String actual = businessServiceImpl.getLeadIdByRequestTypeAndEmail( recordType, email );
        assertNull( actual );
    }

    /**
     * Test convert lead to opportunity.
     */
    @Test
    public void testConvertLeadToOpportunity() {
        final String leadId = "leadId";
        final String expected = "expectedId";
        when( leadService.convertLeadToOpportunity( leadId ) ).thenReturn( expected );
        final String actual = businessServiceImpl.convertLeadToOpportunity( leadId );
        assertEquals( expected, actual );
    }

    /**
     * Should not forward to referral exchange for android lead source.
     */
    @Test
    public void shouldNotForwardToReferralExchangeForAndroidLeadSource() {
        final LeadSource leadSource = loadLeadSource();
        final String state = "test";
        final String excludedState = "GA";
        leadSource.setState( state );
        leadSource.setSource( "App-Android-Owners.com Registration" );
        leadSource.setLeadStatus( FORWARDED_TO_REF_EX.getStatus() );
        when( config.isLeadReferralEnabled() ).thenReturn( true );
        when( config.getOwnerStates() ).thenReturn( excludedState );
        final Map< String, Object > leadMap = new HashMap<>();
        leadMap.put( "Name", "Buyer" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( leadMap );
        final ReferralExchangeRequest referralExchangeRequest = new ReferralExchangeRequest();
        when( contactBuilderV1.convertTo( any( LeadRequest.class ), any( Contact.class ) ) )
                .thenReturn( new Contact() );
        when( ocleadRequestBuilder.convertTo( any( LeadSource.class ) ) ).thenReturn( new GenericLeadRequest() );
        when( referralExchangeRequestBuilder.convertTo( leadSource ) ).thenReturn( referralExchangeRequest );
        businessServiceImpl.handleLeadChange( leadSource );
        verifyZeroInteractions( referralExchangeRequestBuilder );
        verifyZeroInteractions( referralExchangeService );
        verifyZeroInteractions( leadService );
    }

    /**
     * Should not forward to referral exchange for ios lead source.
     */
    @Test
    public void shouldNotForwardToReferralExchangeForIosLeadSource() {
        final LeadSource leadSource = loadLeadSource();
        final String state = "test";
        final String excludedState = "GA";
        leadSource.setState( state );
        leadSource.setSource( "App-iOS-Owners.com Registration" );
        leadSource.setLeadStatus( FORWARDED_TO_REF_EX.getStatus() );
        when( config.isLeadReferralEnabled() ).thenReturn( true );
        when( config.getOwnerStates() ).thenReturn( excludedState );
        final Map< String, Object > leadMap = new HashMap<>();
        leadMap.put( "Name", "Buyer" );
        when( contactBuilderV1.convertTo( any( LeadRequest.class ), any( Contact.class ) ) )
                .thenReturn( new Contact() );
        when( ocleadRequestBuilder.convertTo( any( LeadSource.class ) ) ).thenReturn( new GenericLeadRequest() );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( leadMap );
        final ReferralExchangeRequest referralExchangeRequest = new ReferralExchangeRequest();
        when( referralExchangeRequestBuilder.convertTo( leadSource ) ).thenReturn( referralExchangeRequest );
        businessServiceImpl.handleLeadChange( leadSource );
        verifyZeroInteractions( referralExchangeRequestBuilder );
        verifyZeroInteractions( referralExchangeService );
        verifyZeroInteractions( leadService );
    }

    /**
     * Test handle lead change.
     */
    @Test
    public void testHandleLeadChange_BuyerRegistration() {
        final LeadSource leadSource = loadLeadSource();
        leadSource.setLastModifiedById( "ZZZZ" );
        leadSource.setRecordTypeName( "Buyer" );
        leadSource.setLeadStatus( "leadStatus" );

        final Map< String, Object > leadMap = new HashMap<>();
        leadMap.put( "Name", "Buyer" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( leadMap );
        when( config.isLeadReferralEnabled() ).thenReturn( false );
        when( config.getSfApiUserId() ).thenReturn( "00539000004XirlAAA" );

        when( contactBuilderV1.convertTo( any( LeadRequest.class ), any( Contact.class ) ) )
                .thenReturn( new Contact() );
        when( ocleadRequestBuilder.convertTo( any( LeadSource.class ) ) ).thenReturn( new GenericLeadRequest() );
        businessServiceImpl.handleLeadChange( leadSource );

        Mockito.verify( config ).getSfApiUserId();
        Mockito.verifyZeroInteractions( marketingEmailBusinessService );
        Mockito.verifyZeroInteractions( referralExchangeService );
        Mockito.verifyZeroInteractions( leadService );
    }

    /**
     * Test handle lead change.
     */
    @Test( enabled = false )
    public void testHandleLeadCreate_BuyerRegistration() {
        final LeadSource leadSource = loadLeadSource();
        leadSource.setRecordTypeName( "Buyer" );

        businessServiceImpl.handleLeadCreate( leadSource );

        Mockito.verify( buyerFarmingBusinessService ).registerBuyer( leadSource );
        Mockito.verifyZeroInteractions( leadTask );
    }

    /**
     * Test handle lead create_ without buyer registration.
     */
    @Test
    public void testHandleLeadCreate_WithoutBuyerRegistration() {
        final LeadSource leadSource = loadLeadSource();
        leadSource.setRecordTypeName( "Seller" );
        when( leadService.isValidPhoneNumber( anyObject() ) ).thenReturn( true );

        businessServiceImpl.handleLeadCreate( leadSource );

        Mockito.verifyZeroInteractions( buyerFarmingBusinessService );
    }

    /**
     * Creates the buyer lead disable notification.
     */
    @Test( enabled = false )
    public void createBuyerLead_BuyerRegistration() {

        final CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setId( "123123" );
        leadResponse.setId( "test" );

        final CRMResponse crmResponse = new CRMResponse();
        final List< Map< String, Object > > records = new ArrayList< Map< String, Object > >();
        final Map< String, Object > recMap = new HashMap<>();
        recMap.put( "test", "test" );
        recMap.put( LEAD_DEDUP_COUNT, "1" );
        records.add( recMap );
        crmResponse.setRecords( records );
        final Contact con = new Contact();

        when( deDuplicationService.deDuplicateLead( anyString() ) ).thenReturn( crmResponse );
        when( deDuplicationService.getDeDuplicatedLead( anyString(), anyObject() ) ).thenReturn( crmResponse );
        when( leadService.createLead( any( CRMLeadRequest.class ), anyBoolean() ) ).thenReturn( leadResponse );
        when( emailNotificationBuilder.convertTo( any( GenericLeadRequest.class ) ) )
                .thenReturn( new EmailNotification() );
        when( mailService.send( any( EmailNotification.class ) ) ).thenReturn( new NotificationResponse() );
        final Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Id", "123456" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        when( crmLeadRequestBuilder.convertTo( any( LeadRequest.class ) ) ).thenReturn( new CRMLeadRequest() );
        when( leadOpportunityBusinessConfig.isContactDatabaseConfig() ).thenReturn( true );
        when( contactServiceV1.getContact( anyString(), anyString() ) ).thenReturn( con );
        when( contactBuilderV1.convertTo( any( LeadRequest.class ), any( Contact.class ) ) ).thenReturn( con );
        Mockito.doNothing().when( leadTask ).updateLeadScore( any( LeadRequest.class ), any(Contact.class), Mockito.anyInt(),
                Mockito.anyBoolean() );
        Mockito.when( leadService.getLead( anyString() ) ).thenReturn( leadResponse );

        final GenericLeadRequest lead = new GenericLeadRequest();
        lead.setLeadType( RecordType.BUYER.toString() );
        lead.setState( "AB" );

        when( config.getTimeZoneId() ).thenReturn( "Asia/Kolkata" );
        when( config.getOfficeAfterHourStart() ).thenReturn( 1 );
        when( config.getOfficeAfterMinsStart() ).thenReturn( 1 );
        when( config.isOfficeAfterHourEnabled() ).thenReturn( true );
        when( config.getAfterHoursStates() ).thenReturn( "AB" );

        businessServiceImpl.createLead( lead, true, null );

        verify( leadService ).updateLead( any( CRMLeadRequest.class ), anyString(), anyBoolean() );
        Mockito.verify( buyerFarmingBusinessService ).registerBuyer( any( LeadSource.class ) );
        verify( leadTask ).updateLeadScore( any( LeadRequest.class ), any(Contact.class), Mockito.anyInt(),
                Mockito.anyBoolean() );
        verify( config, Mockito.times( 1 ) ).getTimeZoneId();
        verify( config, Mockito.times( 1 ) ).getOfficeAfterHourStart();
        verify( config, Mockito.times( 1 ) ).getOfficeAfterMinsStart();
    }

    /**
     * Creates the buyer lead disable notification.
     */
    @Test( enabled = false )
    public void createBuyerLead_BuyerRegistration_ForAlreadyRegisteredBuyer() {

        final CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setId( "123123" );
        leadResponse.setId( "test" );

        final CRMResponse crmResponse = new CRMResponse();
        final List< Map< String, Object > > records = new ArrayList< Map< String, Object > >();
        final Map< String, Object > recMap = new HashMap<>();
        recMap.put( "test", "test" );
        recMap.put( LEAD_DEDUP_COUNT, "1" );
        records.add( recMap );
        crmResponse.setRecords( records );
        final Contact con = new Contact();
        con.setOwnersComId( "ownersComId" );

        when( deDuplicationService.deDuplicateLead( anyString() ) ).thenReturn( crmResponse );
        when( deDuplicationService.getDeDuplicatedLead( anyString(), anyObject() ) ).thenReturn( crmResponse );
        when( leadService.createLead( any( CRMLeadRequest.class ), anyBoolean() ) ).thenReturn( leadResponse );
        when( emailNotificationBuilder.convertTo( any( GenericLeadRequest.class ) ) )
                .thenReturn( new EmailNotification() );
        when( mailService.send( any( EmailNotification.class ) ) ).thenReturn( new NotificationResponse() );
        final Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Id", "123456" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        when( crmLeadRequestBuilder.convertTo( any( LeadRequest.class ) ) ).thenReturn( new CRMLeadRequest() );
        when( leadOpportunityBusinessConfig.isContactDatabaseConfig() ).thenReturn( true );
        when( contactServiceV1.getContact( anyString(), anyString() ) ).thenReturn( con );
        when( contactBuilderV1.convertTo( any( LeadRequest.class ), any( Contact.class ) ) ).thenReturn( con );
        Mockito.doNothing().when( leadTask ).updateLeadScore( any( LeadRequest.class ), any(Contact.class), Mockito.anyInt(),
                Mockito.anyBoolean() );
        Mockito.when( leadService.getLead( anyString() ) ).thenReturn( leadResponse );

        final GenericLeadRequest lead = new GenericLeadRequest();
        lead.setLeadType( RecordType.BUYER.toString() );
        lead.setState( "AB" );

        when( config.getTimeZoneId() ).thenReturn( "Asia/Kolkata" );
        when( config.getOfficeAfterHourStart() ).thenReturn( 1 );
        when( config.getOfficeAfterMinsStart() ).thenReturn( 1 );
        when( config.isOfficeAfterHourEnabled() ).thenReturn( true );
        when( config.getAfterHoursStates() ).thenReturn( "AB" );

        businessServiceImpl.createLead( lead, true, null );

        verify( leadService ).updateLead( any( CRMLeadRequest.class ), anyString(), anyBoolean() );
        Mockito.verifyZeroInteractions( buyerFarmingBusinessService );
        verify( leadTask ).updateLeadScore( any( LeadRequest.class ), any(Contact.class), Mockito.anyInt(),
                Mockito.anyBoolean() );
        verify( config, Mockito.times( 1 ) ).getTimeZoneId();
        verify( config, Mockito.times( 1 ) ).getOfficeAfterHourStart();
        verify( config, Mockito.times( 1 ) ).getOfficeAfterMinsStart();
    }

    /**
     * Test handle lead change should handle qualified status.
     */
    @Test
    public void testHandleLeadChange_ShouldHandleQualifiedStatus() {
        final LeadSource leadSource = loadLeadSource();
        leadSource.setInterestedInFinancing( false );
        leadSource.setLastModifiedById( "b" );
        leadSource.setLeadStatus( LeadStatus.QUALIFIED.getStatus() );

        final Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Name", BUYER.getType() );

        final Contact contact = new Contact();
        final GenericLeadRequest genericLeadRequest = new GenericLeadRequest();
        final Contact updatedContact = new Contact();

        when( config.getSfApiUserId() ).thenReturn( "a" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        when( buyerFarmingConfig.isBuyerFarmingEnabled() ).thenReturn( false );
        when( contactServiceV1.getContact( anyString(), anyString() ) ).thenReturn( contact );
        when( ocleadRequestBuilder.convertTo( leadSource ) ).thenReturn( genericLeadRequest );
        when( contactBuilderV1.convertTo( genericLeadRequest, contact ) ).thenReturn( updatedContact );
        when( config.isOfficeAfterHourEnabled() ).thenReturn( false );
        when( contactServiceV1.getContactByCrmId( leadSource.getId() ) ).thenReturn( contact );

        businessServiceImpl.handleLeadChange( leadSource );
        verify( contactServiceV1 ).save( updatedContact );
        verifyZeroInteractions( processBusinessService, runtimeService, opportunityServiceImpl, leadValidator,
                leadFollowupBusinessService, buyerFarmingBusinessService, ownerGenericLeadRequestBuilder );
    }

    /**
     * Test handle lead change should handle qualified status and signal inside
     * sales farming process.
     */
    @Test
    public void testHandleLeadChange_ShouldHandleQualifiedStatus_AndSignalInsideSalesFarmingProcess() {
        final LeadSource leadSource = loadLeadSource();
        leadSource.setInterestedInFinancing( false );
        leadSource.setLastModifiedById( "b" );
        leadSource.setLeadStatus( LeadStatus.QUALIFIED.getStatus() );

        final Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Name", BUYER.getType() );

        final Contact contact = new Contact();
        final GenericLeadRequest genericLeadRequest = new GenericLeadRequest();
        final Contact updatedContact = new Contact();
        final com.owners.gravitas.domain.entity.Process process = new Process();

        when( config.getSfApiUserId() ).thenReturn( "a" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        when( buyerFarmingConfig.isBuyerFarmingEnabled() ).thenReturn( true );
        when( contactServiceV1.getContact( anyString(), anyString() ) ).thenReturn( contact );
        when( ocleadRequestBuilder.convertTo( leadSource ) ).thenReturn( genericLeadRequest );
        when( contactBuilderV1.convertTo( genericLeadRequest, contact ) ).thenReturn( updatedContact );
        when( config.isOfficeAfterHourEnabled() ).thenReturn( false );
        when( buyerFarmingConfig.isBuyerAutoRegistrationEnabled() ).thenReturn( TRUE );
        when( buyerFarmingBusinessService.isBuyerAutoRegistrationEmail( leadSource.getEmail() ) ).thenReturn( TRUE );
        when( buyerFarmingConfig.isBuyerInsideSalesFarmingEnabled() ).thenReturn( TRUE );
        when( processBusinessService.deActivateAndSignal( anyString(), any( GravitasProcess.class ), anyMap() ) )
                .thenReturn( process );
        when( contactServiceV1.getContactByCrmId( leadSource.getId() ) ).thenReturn( contact );

        businessServiceImpl.handleLeadChange( leadSource );
        verify( contactServiceV1 ).save( updatedContact );
        verify( processBusinessService, times( 1 ) ).deActivateAndSignal( anyString(), any( GravitasProcess.class ),
                anyMap() );
        verify( buyerFarmingBusinessService, times( 0 ) ).updateFarmingStatus( anyString(), any( BuyerFarmType.class ),
                anyBoolean() );
        verifyZeroInteractions( opportunityServiceImpl, leadValidator, leadFollowupBusinessService,
                ownerGenericLeadRequestBuilder );
    }

    /**
     * Test handle lead change should handle qualified status and not start lead
     * management process and signal inside sales farming process.
     */
    @Test
    public void testHandleLeadChange_ShouldHandleQualifiedStatus_AndNotStartLeadManagementProcess_AndSignalInsideSalesFarmingProcess() {
        final LeadSource leadSource = loadLeadSource();
        leadSource.setInterestedInFinancing( false );
        leadSource.setLastModifiedById( "b" );
        leadSource.setLeadStatus( LeadStatus.QUALIFIED.getStatus() );

        final Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Name", BUYER.getType() );

        final Contact contact = new Contact();
        final GenericLeadRequest genericLeadRequest = new GenericLeadRequest();
        final Contact updatedContact = new Contact();
        final com.owners.gravitas.domain.entity.Process process = new Process();

        when( config.getSfApiUserId() ).thenReturn( "a" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        when( buyerFarmingConfig.isBuyerFarmingEnabled() ).thenReturn( true );
        when( contactServiceV1.getContact( anyString(), anyString() ) ).thenReturn( contact );
        when( ocleadRequestBuilder.convertTo( leadSource ) ).thenReturn( genericLeadRequest );
        when( contactBuilderV1.convertTo( genericLeadRequest, contact ) ).thenReturn( updatedContact );
        when( config.isOfficeAfterHourEnabled() ).thenReturn( false );
        when( buyerFarmingConfig.isBuyerAutoRegistrationEnabled() ).thenReturn( TRUE );
        when( buyerFarmingBusinessService.isBuyerAutoRegistrationEmail( leadSource.getEmail() ) ).thenReturn( TRUE );
        when( buyerFarmingConfig.isBuyerInsideSalesFarmingEnabled() ).thenReturn( TRUE );
        when( processBusinessService.deActivateAndSignal( anyString(), any( GravitasProcess.class ), anyMap() ) )
                .thenReturn( process );
        when( processBusinessService.getProcess( anyString(), any( GravitasProcess.class ), anyString() ) )
                .thenReturn( process );
        when( contactServiceV1.getContactByCrmId( leadSource.getId() ) ).thenReturn( contact );

        businessServiceImpl.handleLeadChange( leadSource );
        verify( contactServiceV1 ).save( updatedContact );
        verify( processBusinessService, times( 1 ) ).deActivateAndSignal( anyString(), any( GravitasProcess.class ),
                anyMap() );
        verify( buyerFarmingBusinessService, times( 0 ) ).updateFarmingStatus( anyString(), any( BuyerFarmType.class ),
                anyBoolean() );
        verifyZeroInteractions( opportunityServiceImpl, leadValidator, leadFollowupBusinessService,
                ownerGenericLeadRequestBuilder, runtimeService );
    }

    /**
     * Test handle lead change should handle outbound attempt status.
     */
    @Test
    public void testHandleLeadChange_ShouldHandleOutboundAttemptStatus() {
        final LeadSource leadSource = loadLeadSource();
        leadSource.setInterestedInFinancing( false );
        leadSource.setLastModifiedById( "b" );
        leadSource.setLeadStatus( LeadStatus.OUTBOUND_ATTEMPT.getStatus() );

        final Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Name", BUYER.getType() );

        final Contact contact = new Contact();
        final GenericLeadRequest genericLeadRequest = new GenericLeadRequest();
        final Contact updatedContact = new Contact();

        when( config.getSfApiUserId() ).thenReturn( "a" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        when( buyerFarmingConfig.isBuyerFarmingEnabled() ).thenReturn( false );
        when( contactServiceV1.getContact( anyString(), anyString() ) ).thenReturn( contact );
        when( ocleadRequestBuilder.convertTo( leadSource ) ).thenReturn( genericLeadRequest );
        when( contactBuilderV1.convertTo( genericLeadRequest, contact ) ).thenReturn( updatedContact );
        when( config.isOfficeAfterHourEnabled() ).thenReturn( false );
        when( contactServiceV1.getContactByCrmId( leadSource.getId() ) ).thenReturn( contact );
        businessServiceImpl.handleLeadChange( leadSource );
        verify( contactServiceV1 ).save( updatedContact );
        verify( leadFollowupBusinessService ).startLeadFollowupEmailProcess( leadSource );
        verifyZeroInteractions( processBusinessService, runtimeService, opportunityServiceImpl, leadValidator,
                leadFollowupBusinessService, buyerFarmingBusinessService, ownerGenericLeadRequestBuilder );
    }

    /**
     * Test handle lead change should handle lost status and forward to referral
     * ex.
     */
    @Test
    public void testHandleLeadChange_ShouldHandleLostStatus_AndForwardToReferralEx() {
        final LeadSource leadSource = loadLeadSource();
        leadSource.setInterestedInFinancing( false );
        leadSource.setLastModifiedById( "b" );
        leadSource.setLeadStatus( LeadStatus.LOST.getStatus() );
        leadSource.setReferred( false );
        leadSource.setDoNotCall( false );
        leadSource.setDoNotEmail( false );
        leadSource.setSource( "a" );
        leadSource.setClosedReason( "Out Of Coverage Area" );

        final Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Name", BUYER.getType() );

        final Contact contact = new Contact();
        final GenericLeadRequest genericLeadRequest = new GenericLeadRequest();
        final Contact updatedContact = new Contact();
        final ReferralExchangeRequest refExRequest = new ReferralExchangeRequest();
        final EmailNotification notification = new EmailNotification();

        when( config.getSfApiUserId() ).thenReturn( "a" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        when( buyerFarmingConfig.isBuyerFarmingEnabled() ).thenReturn( false );
        when( contactServiceV1.getContact( anyString(), anyString() ) ).thenReturn( contact );
        when( ocleadRequestBuilder.convertTo( leadSource ) ).thenReturn( genericLeadRequest );
        when( contactBuilderV1.convertTo( genericLeadRequest, contact ) ).thenReturn( updatedContact );
        when( config.isOfficeAfterHourEnabled() ).thenReturn( false );
        when( config.isLeadReferralEnabled() ).thenReturn( TRUE );
        when( config.getReferralExcludedStates() ).thenReturn( "" );
        when( config.getOwnerStates() ).thenReturn( "" );
        when( referralExchangeRequestBuilder.convertTo( leadSource ) ).thenReturn( refExRequest );
        when( config.isReferralEmailNotificationEnabled() ).thenReturn( TRUE );
        when( referralExchangeRequestEmailBuilder.convertTo( anyString(), anyList() ) ).thenReturn( notification );
        when( mailService.send( notification ) ).thenReturn( new NotificationResponse() );
        when( contactServiceV1.getContactByCrmId( leadSource.getId() ) ).thenReturn( contact );

        businessServiceImpl.handleLeadChange( leadSource );

        verify( contactServiceV1 ).save( updatedContact );
        verify( referralExchangeRequestBuilder ).convertTo( leadSource );
        verify( referralExchangeService ).forwardRequest( refExRequest );
        verify( mailService ).send( notification );
        verify( leadService ).updateLead( anyMap(), anyString(), anyBoolean() );
        verifyZeroInteractions( processBusinessService, runtimeService, opportunityServiceImpl, leadValidator,
                leadFollowupBusinessService, buyerFarmingBusinessService, ownerGenericLeadRequestBuilder );
    }

    /**
     * Test handle lead change should handle forward to ref ex status and signal
     * inside sales farming process.
     */
    @Test
    public void testHandleLeadChange_ShouldHandleForwardToRefExStatus_AndSignalInsideSalesFarmingProcess() {
        final LeadSource leadSource = loadLeadSource();
        leadSource.setInterestedInFinancing( false );
        leadSource.setLastModifiedById( "b" );
        leadSource.setLeadStatus( LeadStatus.FORWARDED_TO_REF_EX.getStatus() );
        leadSource.setReferred( false );
        leadSource.setDoNotCall( false );
        leadSource.setDoNotEmail( false );
        leadSource.setSource( "a" );
        leadSource.setClosedReason( "Duplicate Lead" );

        final Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Name", BUYER.getType() );

        final Contact contact = new Contact();
        final GenericLeadRequest genericLeadRequest = new GenericLeadRequest();
        final Contact updatedContact = new Contact();

        when( config.getSfApiUserId() ).thenReturn( "a" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        when( buyerFarmingConfig.isBuyerFarmingEnabled() ).thenReturn( false );
        when( contactServiceV1.getContact( anyString(), anyString() ) ).thenReturn( contact );
        when( ocleadRequestBuilder.convertTo( leadSource ) ).thenReturn( genericLeadRequest );
        when( contactBuilderV1.convertTo( genericLeadRequest, contact ) ).thenReturn( updatedContact );
        when( config.isOfficeAfterHourEnabled() ).thenReturn( false );
        when( config.isLeadReferralEnabled() ).thenReturn( false );
        Mockito.when( contactBuilderV1.convertTo( any( GenericLeadRequest.class ), any( Contact.class ) ) )
                .thenReturn( updatedContact );
        when( contactServiceV1.getContactByCrmId( leadSource.getId() ) ).thenReturn( contact );
        businessServiceImpl.handleLeadChange( leadSource );
        verify( contactServiceV1 ).save( updatedContact );
        verifyZeroInteractions( processBusinessService, referralExchangeService, referralExchangeRequestBuilder,
                runtimeService, opportunityServiceImpl, leadValidator, leadFollowupBusinessService,
                buyerFarmingBusinessService, ownerGenericLeadRequestBuilder, mailService );
    }

    /**
     * Test handle lead change should handle forward to ref ex status and signal
     * inside sales farming process 2.
     */
    @Test
    public void testHandleLeadChange_ShouldHandleForwardToRefExStatus_AndSignalInsideSalesFarmingProcess2() {
        final LeadSource leadSource = loadLeadSource();
        leadSource.setInterestedInFinancing( false );
        leadSource.setLastModifiedById( "b" );
        leadSource.setLeadStatus( LeadStatus.FORWARDED_TO_REF_EX.getStatus() );
        leadSource.setReferred( false );
        leadSource.setDoNotCall( false );
        leadSource.setDoNotEmail( false );
        leadSource.setSource( "a" );
        leadSource.setClosedReason( "" );

        final Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Name", BUYER.getType() );

        final Contact contact = new Contact();
        final GenericLeadRequest genericLeadRequest = new GenericLeadRequest();
        final Contact updatedContact = new Contact();

        when( config.getSfApiUserId() ).thenReturn( "a" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        when( buyerFarmingConfig.isBuyerFarmingEnabled() ).thenReturn( false );
        when( ocleadRequestBuilder.convertTo( leadSource ) ).thenReturn( genericLeadRequest );
        when( contactServiceV1.getContact( anyString(), anyString() ) ).thenReturn( contact );
        when( contactBuilderV1.convertTo( genericLeadRequest, contact ) ).thenReturn( updatedContact );
        when( config.isOfficeAfterHourEnabled() ).thenReturn( false );
        when( config.isLeadReferralEnabled() ).thenReturn( false );
        when( contactServiceV1.getContactByCrmId( leadSource.getId() ) ).thenReturn( contact );
        businessServiceImpl.handleLeadChange( leadSource );
        verify( contactServiceV1 ).save( updatedContact );
        verifyZeroInteractions( processBusinessService, referralExchangeService, referralExchangeRequestBuilder,
                runtimeService, opportunityServiceImpl, leadValidator, leadFollowupBusinessService,
                buyerFarmingBusinessService, ownerGenericLeadRequestBuilder, mailService );
    }

    /**
     * Test handle lead create should not update score.
     */
    @Test
    public void testHandleLeadCreateShouldNotUpdateScore() {
        final LeadSource leadSource = loadLeadSource();
        leadSource.setRecordTypeName( leadSource.getRecordType() );
        final String states = "AK";
        leadSource.setCreatedById( "aa" );

        when( buyerFarmingConfig.isBuyerFarmingEnabled() ).thenReturn( false );
        when( config.getAfterHoursStates() ).thenReturn( states );
        when( config.getTimeZoneId() ).thenReturn( "US/Eastern" );
        when( config.getOfficeAfterHourStart() ).thenReturn( 0 );
        when( config.getOfficeAfterMinsStart() ).thenReturn( 1 );
        when( config.getOfficeAfterHourEnd() ).thenReturn( 21 );
        when( config.getOfficeAfterMinsEnd() ).thenReturn( 59 );
        when( config.getSfApiUserId() ).thenReturn( "" );

        businessServiceImpl.handleLeadCreate( leadSource );
        verifyZeroInteractions( leadTask );
    }

    /**
     * Test handle lead create should not update score 2.
     */
    @Test
    public void testHandleLeadCreateShouldNotUpdateScore2() {
        final LeadSource leadSource = loadLeadSource();
        leadSource.setRecordTypeName( leadSource.getRecordType() );
        final String states = "AK";
        leadSource.setCreatedById( "aa" );
        leadSource.setState( null );

        when( buyerFarmingConfig.isBuyerFarmingEnabled() ).thenReturn( false );
        when( config.getAfterHoursStates() ).thenReturn( states );
        when( config.getTimeZoneId() ).thenReturn( "US/Eastern" );
        when( config.getOfficeAfterHourStart() ).thenReturn( 21 );
        when( config.getOfficeAfterMinsStart() ).thenReturn( 0 );
        when( config.getOfficeAfterHourEnd() ).thenReturn( 7 );
        when( config.getOfficeAfterMinsEnd() ).thenReturn( 55 );
        when( config.getSfApiUserId() ).thenReturn( "" );

        businessServiceImpl.handleLeadCreate( leadSource );
        verifyZeroInteractions( leadTask );
    }

    /**
     * Test create lead.
     */
    @Test
    public void testCreateLead() {
        final CRMResponse response = new CRMResponse();
        final List< Map< String, Object > > records = new ArrayList<>();
        final Map< String, Object > map = new HashMap<>();
        map.put( CRMConstants.LEAD_CONVERTED_OPPORTUNITY_ID, "" );
        map.put( CRMConstants.STATUS, QUALIFIED.getStatus() );
        map.put( CRMConstants.LEAD_DEDUP_COUNT, 1 );
        records.add( map );
        response.setRecords( records );
        final LeadRequest leadRequest = new LeadRequest();
        leadRequest.setLeadType( RecordType.BUYER.name() );
        final CRMLeadResponse crmLeadesponse = new CRMLeadResponse();
        crmLeadesponse.setLeadStatus( QUALIFIED.getStatus() );
        final OpportunityResponse oppResponse = new OpportunityResponse();
        oppResponse.setId( "id1" );

        final Contact contact = new Contact();

        when( opportunityBusinessService.updateOpportunity( any( LeadRequest.class ), anyString() ) )
                .thenReturn( oppResponse );
        when( leadOpportunityBusinessConfig.isContactDatabaseConfig() ).thenReturn( TRUE );
        when( deDuplicationService.getDeDuplicatedLead( anyString(), any( RecordType.class ) ) ).thenReturn( response );
        when( leadService.getLead( anyString() ) ).thenReturn( crmLeadesponse );
        when( contactServiceV1.getContact( anyString(), anyString() ) ).thenReturn( contact );
        when( contactBuilderV1.convertTo( leadRequest, contact ) ).thenReturn( contact );

        businessServiceImpl.createLead( leadRequest, false, null );
        verify( contactServiceV1 ).save( any( Contact.class ) );
    }

    /**
     * Test is Mortgage Lead with CA state
     */
    @Test
    public void test_isMortgageLead() {
        final LeadSource source = new LeadSource();
        source.setRecordTypeName( "Buyer" );
        source.setState( "CA" );
        source.setId( "test" );
        source.setFirstName( "tests" );
        source.setPhone( "1234567890" );
        source.setCreatedById( "re" );
        source.setInterestedInFinancing( true );
        source.setRecordType( "Owners.com Loans" );
        final boolean isMortageFlag = businessServiceImpl.isMortgageLead( source );

        Assert.assertEquals( isMortageFlag, true );

    }

    /**
     * Test is Mortgage Lead with non CA State
     */
    @Test
    public void test_isMortgageLeadWithNonCAstate() {
        final LeadSource source = new LeadSource();
        source.setRecordTypeName( "Buyer" );
        source.setState( "FL" );
        source.setId( "test" );
        source.setFirstName( "tests" );
        source.setPhone( "1234567890" );
        source.setCreatedById( "re" );
        source.setInterestedInFinancing( true );
        source.setRecordType( "Owners.com Loans" );
        final boolean isMortageFlag = businessServiceImpl.isMortgageLead( source );

        Assert.assertEquals( isMortageFlag, false );

    }

    /**
     * Test is Mortgage Lead with Finance not interested lead
     */
    @Test
    public void test_isMortgageLeadWithFinanceNotInterested() {
        final LeadSource source = new LeadSource();
        source.setRecordTypeName( "Buyer" );
        source.setState( "CA" );
        source.setId( "test" );
        source.setFirstName( "tests" );
        source.setPhone( "1234567890" );
        source.setCreatedById( "re" );
        source.setInterestedInFinancing( false );
        source.setRecordType( "BUYER" );
        final boolean isMortageFlag = businessServiceImpl.isMortgageLead( source );

        Assert.assertEquals( isMortageFlag, false );

    }

    /**
     * Test is Mortgage Lead with OCL lead
     */
    @Test
    public void test_isMortgageLeadWithOCLtype() {
        final LeadSource source = new LeadSource();
        source.setRecordTypeName( "Buyer" );
        source.setState( "CA" );
        source.setId( "test" );
        source.setFirstName( "tests" );
        source.setPhone( "1234567890" );
        source.setCreatedById( "re" );
        source.setInterestedInFinancing( false );
        source.setRecordType( "Owners.com Loans" );
        final boolean isMortageFlag = businessServiceImpl.isMortgageLead( source );

        Assert.assertEquals( isMortageFlag, true );

    }

    /**
     * Test create buyer lead handle create with mortageLeadNotification
     */
    // disabling test case as per OWNCORE-7120 stall comment by Satya
    @Test( enabled = false )
    public void testHandleCreateLead_withMortgageLead() {
        final LeadSource source = new LeadSource();
        source.setRecordTypeName( "Buyer" );
        source.setState( "CA" );
        source.setId( "test" );
        source.setFirstName( "tests" );
        source.setCreatedById( "re" );
        source.setFirstName( "tests" );
        source.setPhone( "1234567890" );
        source.setInterestedInFinancing( true );
        when( config.isMortgageLeadNotificationEnabled() ).thenReturn( true );
        when( config.getTimeZoneId() ).thenReturn( "Asia/Kolkata" );
        when( config.getOfficeAfterHourStart() ).thenReturn( 1 );
        when( config.getOfficeAfterMinsStart() ).thenReturn( 1 );
        when( config.isOfficeAfterHourEnabled() ).thenReturn( true );
        when( config.getAfterHoursStates() ).thenReturn( "AB" );
        when( config.getSfApiUserId() ).thenReturn( "test" );
        businessServiceImpl.handleLeadCreate( source );
        verify( mailService ).send( null );

    }

    /**
     * Test create buyer lead handle create with mortageLeadNotification
     */
    // disabling test case as per OWNCORE-7120 stall comment by Satya
    @Test( enabled = false )
    public void testHandleCreateLead_withMortgageLeadOCLLead() {
        final LeadSource source = new LeadSource();
        source.setRecordTypeName( "Buyer" );
        source.setState( "CA" );
        source.setId( "test" );
        source.setCreatedById( "re" );
        source.setRecordType( "Owners.com Loans" );
        source.setFirstName( "tests" );
        source.setPhone( "1234567890" );
        source.setInterestedInFinancing( true );
        when( config.isMortgageLeadNotificationEnabled() ).thenReturn( true );
        when( config.getTimeZoneId() ).thenReturn( "Asia/Kolkata" );
        when( config.getOfficeAfterHourStart() ).thenReturn( 1 );
        when( config.getOfficeAfterMinsStart() ).thenReturn( 1 );
        when( config.isOfficeAfterHourEnabled() ).thenReturn( true );
        when( config.getAfterHoursStates() ).thenReturn( "AB" );
        when( config.getSfApiUserId() ).thenReturn( "test" );
        businessServiceImpl.handleLeadCreate( source );
        verify( mailService ).send( null );

    }
    
    /**
     * Test for fetching all the public leads
     */
    @Test
    public void testGetAvailableLeads() {
        final LeadDetailsRequest request = new LeadDetailsRequest();
        request.setDirection( "desc" );
        request.setPage( 1 );
        request.setProperty( "score" );
        request.setSize( 100 );
        final List<Contact> responselist = new ArrayList<>();
        final PageImpl< Contact > response = new PageImpl<>( responselist );
       when( contactServiceV1.getAllPublicLeads( request.getPage(), request.getSize(), request.getDirection(), request.getProperty() )).thenReturn( response );
    }
    
    /**
     * Test handle lead create_ without buyer registration.
     */
    @Test
    public void testHandleLeadCreate_WithoutBuyerRegistrationWithInvalidPhoneNo() {
        final LeadSource leadSource = loadLeadSource();
        leadSource.setRecordTypeName( "Seller" );
        leadSource.setPhone( "8002312345" );
        final Contact contact = new Contact();
        when( leadService.isValidPhoneNumber( anyObject() ) ).thenReturn( false );
        when( contactServiceV1.getContact( anyString(), anyString() ) ).thenReturn( contact );
        
        businessServiceImpl.handleLeadCreate( leadSource );

        Mockito.verifyZeroInteractions( buyerFarmingBusinessService );
    }
}
