package com.owners.gravitas.business.impl;

import static com.owners.gravitas.dto.response.BaseResponse.Status.OK;
import static com.owners.gravitas.dto.response.BaseResponse.Status.SUCCESS;
import static com.owners.gravitas.enums.BuyerFarmType.LONG_TERM_BUYER;
import static com.owners.gravitas.enums.FollowupType.FOLLOW_UP_1;
import static com.owners.gravitas.enums.FollowupType.FOLLOW_UP_2;
import static com.owners.gravitas.enums.NotificationType.MARKETING;
import static com.owners.gravitas.enums.ProspectAttributeType.FARMING_BUYER_ACTIONS;
import static com.owners.gravitas.enums.ProspectAttributeType.FARMING_STATUS;
import static com.owners.gravitas.enums.ProspectAttributeType.FARMING_SYSTEM_ACTIONS;
import static com.owners.gravitas.enums.RecordType.BUYER;
import static com.owners.gravitas.constants.Constants.LEAD;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertFalse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.flowable.engine.RuntimeService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.hubzu.notification.dto.client.email.Email;
import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.amqp.AlertDetails;
import com.owners.gravitas.amqp.BuyerWebActivitySource;
import com.owners.gravitas.amqp.ClientEventDetails;
import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.ProcessBusinessService;
import com.owners.gravitas.business.PropertyBusinessService;
import com.owners.gravitas.business.builder.ContactActivityBuilder;
import com.owners.gravitas.business.builder.LeadDetailsBuilder;
import com.owners.gravitas.business.builder.SavedSearchFollowupEmailNotificationBuilder;
import com.owners.gravitas.business.builder.WebActivityEmailNotificationBuilder;
import com.owners.gravitas.business.builder.request.OCLGenericLeadRequestBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.BuyerFarmingConfig;
import com.owners.gravitas.constants.BuyerFarmingConstants;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.ContactActivity;
import com.owners.gravitas.domain.entity.ContactAttribute;
import com.owners.gravitas.domain.entity.ContactJsonAttribute;
import com.owners.gravitas.domain.entity.ObjectAttributeConfig;
import com.owners.gravitas.domain.entity.ObjectType;
import com.owners.gravitas.domain.entity.Process;
import com.owners.gravitas.domain.entity.RefCode;
import com.owners.gravitas.dto.CrmNote;
import com.owners.gravitas.dto.Preference;
import com.owners.gravitas.dto.PropertyAddress;
import com.owners.gravitas.dto.PropertyData;
import com.owners.gravitas.dto.RegisteredUser;
import com.owners.gravitas.dto.request.GenericLeadRequest;
import com.owners.gravitas.dto.request.SavedSearchRequest;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.NotificationPreferenceResponse;
import com.owners.gravitas.dto.response.NotificationResponse;
import com.owners.gravitas.dto.response.PropertyDetailsResponse;
import com.owners.gravitas.dto.response.RegisteredUserResponse;
import com.owners.gravitas.dto.response.RegistrationResponse;
import com.owners.gravitas.dto.response.SaveSearchResponse;
import com.owners.gravitas.dto.response.SaveSearchResultResponse;
import com.owners.gravitas.enums.BuyerFarmType;
import com.owners.gravitas.enums.GravitasProcess;
import com.owners.gravitas.enums.RecordType;
import com.owners.gravitas.service.BuyerService;
import com.owners.gravitas.service.ContactActivityService;
import com.owners.gravitas.service.ContactEntityService;
import com.owners.gravitas.service.LeadService;
import com.owners.gravitas.service.MailService;
import com.owners.gravitas.service.NoteService;
import com.owners.gravitas.service.ObjectAttributeConfigService;
import com.owners.gravitas.service.ObjectTypeService;
import com.owners.gravitas.service.OpportunityService;
import com.owners.gravitas.service.RefCodeService;
import com.owners.gravitas.util.PropertiesUtil;

/**
 * The Class BuyerFarmingBusinessServiceImplTest.
 *
 * @author vishwanathm
 */
public class BuyerFarmingBusinessServiceImplTest extends AbstractBaseMockitoTest {

    /** The buyer farming business service impl. */
    @InjectMocks
    private BuyerFarmingBusinessServiceImpl buyerFarmingBusinessServiceImpl;

    /** The web activity email notification builder. */
    @Mock
    private WebActivityEmailNotificationBuilder webActivityEmailNotificationBuilder;

    /** The contact activity builder. */
    @Mock
    private ContactActivityBuilder contactActivityBuilder;

    /** The contact activity service. */
    @Mock
    private ContactActivityService contactActivityService;

    /** The ref code service. */
    @Mock
    private RefCodeService refCodeService;

    /** The contact service V 1. */
    @Mock
    private ContactEntityService contactServiceV1;

    /** The generic lead request builder. */
    @Mock
    private OCLGenericLeadRequestBuilder genericLeadRequestBuilder;

    /** The buyer service. */
    @Mock
    private BuyerService buyerService;

    /** The object type service. */
    @Mock
    private ObjectTypeService objectTypeService;

    /** The object attribute config service. */
    @Mock
    private ObjectAttributeConfigService objectAttributeConfigService;

    /** The lead service. */
    @Mock
    private LeadService leadService;

    /** The note service. */
    @Mock
    private NoteService noteService;

    /** The config. */
    @Mock
    private BuyerFarmingConfig config;

    /** The opportunity service. */
    @Mock
    private OpportunityService opportunityService;

    /** The lead details builder. */
    @Mock
    private LeadDetailsBuilder leadDetailsBuilder;

    /** The mail service. */
    @Mock
    private MailService mailService;

    /** The followup email notification builder. */
    @Mock
    private SavedSearchFollowupEmailNotificationBuilder followupEmailNotificationBuilder;

    /** The buyer farming config. */
    @Mock
    private BuyerFarmingConfig buyerFarmingConfig;

    /** The runtime service. */
    @Mock
    protected RuntimeService runtimeService;

    /** The property business service. */
    @Mock
    private PropertyBusinessService propertyBusinessService;

    /** The properties util. */
    @Mock
    private PropertiesUtil propertiesUtil;

    /** The email notification. */
    @Mock
    private EmailNotification emailNotification;

    /** The email. */
    @Mock
    private Email email;

    /** The process business service. */
    @Mock
    protected ProcessBusinessService processBusinessService;

    /**
     * Sets the up.
     */
    @BeforeMethod
    public void setUp() {
        ReflectionTestUtils.setField( buyerFarmingBusinessServiceImpl, "fromEmail", "test.user1@test1.com" );
    }

    /**
     * Test send followup email should send first followup email when type is
     * followup1.
     */
    @Test
    public void testSendFollowupEmailShouldSendFirstFollowupEmailWhenTypeIsFollowup1() {
        final String executionId = "testExecutionId";
        final LeadSource leadSource = new LeadSource();
        leadSource.setDoNotEmail( false );
        leadSource.setId( "testId" );
        leadSource.setEmail( "test@test.com" );
        final NotificationPreferenceResponse preferencesResponse = new NotificationPreferenceResponse();
        final List< Preference > preferences = new ArrayList<>();
        final Preference preference = new Preference();
        preference.setType( MARKETING.name() );
        preference.setValue( true );
        preferences.add( preference );
        preferencesResponse.setPreferences( preferences );
        final Contact contact = new Contact();
        contact.setOwnersComId( "test" );

        final ObjectType objectType1 = new ObjectType();
        objectType1.setName( LEAD );
        contact.setObjectType( objectType1 );
        final Set< ContactJsonAttribute > contactJsonAttributes = new HashSet<>();
        final ObjectAttributeConfig farmingSystemActionAttributeConfig = new ObjectAttributeConfig();
        final ObjectAttributeConfig farmingStatusAttributeConfig = new ObjectAttributeConfig();
        contact.setContactJsonAttributes( contactJsonAttributes );
        final ObjectType objectType = new ObjectType();
        final EmailNotification emailNotification = new EmailNotification();
        final NotificationResponse notificationResponse = new NotificationResponse();
        notificationResponse.setStatus( BaseResponse.Status.SUCCESS.name() );
        final RefCode refCode = new RefCode();

        when( runtimeService.getVariable( executionId, BuyerFarmingConstants.FOLLOWUP_TYPE ) )
                .thenReturn( FOLLOW_UP_1 );
        when( mailService.getNotificationPreferenceForUser( leadSource.getEmail() ) ).thenReturn( preferencesResponse );
        when( contactServiceV1.findByCrmId( leadSource.getId() ) ).thenReturn( contact );
        when( objectTypeService.findByName( "lead" ) ).thenReturn( objectType );
        when( buyerFarmingConfig.isBuyerFirstFollowupEmailEnabled() ).thenReturn( true );
        when( followupEmailNotificationBuilder.convertTo( leadSource ) ).thenReturn( emailNotification );
        when( mailService.send( emailNotification ) ).thenReturn( notificationResponse );
        when( refCodeService.findByCode( FOLLOW_UP_1.getType() ) ).thenReturn( refCode );
        when( objectAttributeConfigService.getObjectAttributeConfig( FARMING_SYSTEM_ACTIONS.getKey(), objectType ) )
                .thenReturn( farmingSystemActionAttributeConfig );
        when( objectAttributeConfigService.getObjectAttributeConfig( FARMING_STATUS.getKey(), objectType ) )
                .thenReturn( farmingStatusAttributeConfig );

        buyerFarmingBusinessServiceImpl.sendFollowupEmail( executionId, leadSource );

        verify( runtimeService ).getVariable( executionId, "FOLLOWUP_TYPE" );
        verify( mailService ).getNotificationPreferenceForUser( leadSource.getEmail() );
        verify( contactServiceV1 ).findByCrmId( leadSource.getId() );
        verify( objectTypeService ).findByName( "lead" );
        verify( buyerFarmingConfig ).isBuyerFirstFollowupEmailEnabled();
        verify( buyerFarmingConfig, times( 0 ) ).isBuyerSecondFollowupEmailEnabled();
        verify( followupEmailNotificationBuilder ).convertTo( leadSource );
        verify( mailService ).send( emailNotification );
        verify( refCodeService ).findByCode( FOLLOW_UP_1.getType() );
        verify( contactActivityService ).save( any( ContactActivity.class ) );
        verify( contactServiceV1 ).save( contact );
        verify( leadService ).updateLead( anyMap(), anyString(), anyBoolean() );
    }

    /**
     * Test send followup email should send second followup email when type is
     * followup2.
     */
    @Test
    public void testSendFollowupEmailShouldSendSecondFollowupEmailWhenTypeIsFollowup2() {
        final String executionId = "testExecutionId";
        final LeadSource leadSource = new LeadSource();
        leadSource.setDoNotEmail( false );
        leadSource.setId( "testId" );
        leadSource.setEmail( "test@test.com" );
        final NotificationPreferenceResponse preferencesResponse = new NotificationPreferenceResponse();
        final List< Preference > preferences = new ArrayList<>();
        final Preference preference = new Preference();
        preference.setType( MARKETING.name() );
        preference.setValue( true );
        preferences.add( preference );
        preferencesResponse.setPreferences( preferences );
        final Contact contact = new Contact();
        contact.setOwnersComId( "test" );
        final ObjectType objectType1 = new ObjectType();
        objectType1.setName( LEAD );
        contact.setObjectType( objectType1 );

        final Set< ContactJsonAttribute > contactJsonAttributes = new HashSet<>();
        final ObjectAttributeConfig farmingSystemActionAttributeConfig = new ObjectAttributeConfig();
        final ObjectAttributeConfig farmingStatusAttributeConfig = new ObjectAttributeConfig();
        final ObjectAttributeConfig farmingGroupAttributeConfig = new ObjectAttributeConfig();
        final ObjectAttributeConfig farmingBuyerActionsAttributeConfig = new ObjectAttributeConfig();

        contact.setContactJsonAttributes( contactJsonAttributes );

        final Set< ContactAttribute > contactAttributes = new HashSet<>();
        final ContactAttribute contactAttribute = new ContactAttribute();
        contactAttribute.setValue( LONG_TERM_BUYER.getType() );
        contactAttribute.setObjectAttributeConfig( farmingGroupAttributeConfig );
        contactAttributes.add( contactAttribute );
        contact.setContactAttributes( contactAttributes );

        final ContactJsonAttribute contactJsonAttribute = new ContactJsonAttribute();
        contactJsonAttribute.setObjectAttributeConfig( farmingBuyerActionsAttributeConfig );
        final ObjectType objectType = new ObjectType();
        final EmailNotification emailNotification = new EmailNotification();
        final NotificationResponse notificationResponse = new NotificationResponse();
        notificationResponse.setStatus( BaseResponse.Status.SUCCESS.name() );
        final RefCode refCode = new RefCode();

        when( runtimeService.getVariable( executionId, BuyerFarmingConstants.FOLLOWUP_TYPE ) )
                .thenReturn( FOLLOW_UP_2 );
        when( mailService.getNotificationPreferenceForUser( leadSource.getEmail() ) ).thenReturn( preferencesResponse );
        when( contactServiceV1.findByCrmId( leadSource.getId() ) ).thenReturn( contact );
        when( objectTypeService.findByName( "lead" ) ).thenReturn( objectType );
        when( buyerFarmingConfig.isBuyerSecondFollowupEmailEnabled() ).thenReturn( true );
        when( followupEmailNotificationBuilder.convertTo( leadSource ) ).thenReturn( emailNotification );
        when( mailService.send( emailNotification ) ).thenReturn( notificationResponse );
        when( refCodeService.findByCode( FOLLOW_UP_2.getType() ) ).thenReturn( refCode );
        when( buyerFarmingConfig.isBuyerSecondFollowupEmailEnabled() ).thenReturn( true );
        when( objectAttributeConfigService.getObjectAttributeConfig( anyString(), any( ObjectType.class ) ) )
                .thenReturn( farmingGroupAttributeConfig ).thenReturn( farmingBuyerActionsAttributeConfig )
                .thenReturn( farmingSystemActionAttributeConfig ).thenReturn( farmingStatusAttributeConfig );

        buyerFarmingBusinessServiceImpl.sendFollowupEmail( executionId, leadSource );

        verify( runtimeService ).getVariable( executionId, "FOLLOWUP_TYPE" );
        verify( mailService ).getNotificationPreferenceForUser( leadSource.getEmail() );
        verify( contactServiceV1 ).findByCrmId( leadSource.getId() );
        verify( objectTypeService ).findByName( "lead" );
        verify( buyerFarmingConfig, times( 0 ) ).isBuyerFirstFollowupEmailEnabled();
        verify( followupEmailNotificationBuilder ).convertTo( leadSource );
        verify( mailService ).send( emailNotification );
        verify( refCodeService ).findByCode( FOLLOW_UP_2.getType() );
        verify( contactActivityService ).save( any( ContactActivity.class ) );
        verify( contactServiceV1 ).save( contact );
        verify( leadService ).updateLead( anyMap(), anyString(), anyBoolean() );
    }

    /**
     * Test send followup email_ should not send email if dnd enabled.
     */
    @Test
    public void testSendFollowupEmail_ShouldNotSendEmailIfDndEnabled() {
        final String executionId = "id1";
        final LeadSource leadSource = new LeadSource();
        leadSource.setDoNotEmail( true );
        final Contact contact = new Contact();
        contact.setOwnersComId( "test" );

        when( contactServiceV1.findByCrmId( leadSource.getId() ) ).thenReturn( contact );

        buyerFarmingBusinessServiceImpl.sendFollowupEmail( executionId, leadSource );

        verify( contactServiceV1 ).findByCrmId( leadSource.getId() );
        verifyZeroInteractions( objectTypeService, runtimeService, buyerFarmingConfig, mailService,
                followupEmailNotificationBuilder, refCodeService, contactActivityService, leadService );
    }

    /**
     * Test send followup email should not send email if opted for dnd.
     */
    @Test
    public void testSendFollowupEmailShouldNotSendEmailIfOptedForDnd() {
        final String executionId = "id1";
        final LeadSource leadSource = new LeadSource();
        final Contact contact = new Contact();
        contact.setOwnersComId( "test" );
        final NotificationPreferenceResponse preferencesResponse = new NotificationPreferenceResponse();
        final List< Preference > preferences = new ArrayList<>();
        final Preference preference = new Preference();
        preference.setType( MARKETING.name() );
        preference.setValue( false );
        preferences.add( preference );
        preferencesResponse.setPreferences( preferences );

        when( contactServiceV1.findByCrmId( leadSource.getId() ) ).thenReturn( contact );
        when( mailService.getNotificationPreferenceForUser( leadSource.getEmail() ) ).thenReturn( preferencesResponse );

        buyerFarmingBusinessServiceImpl.sendFollowupEmail( executionId, leadSource );

        verify( contactServiceV1 ).findByCrmId( leadSource.getId() );
        verify( mailService ).getNotificationPreferenceForUser( leadSource.getEmail() );
        verifyZeroInteractions( objectTypeService, runtimeService, buyerFarmingConfig, followupEmailNotificationBuilder,
                refCodeService, contactActivityService, leadService );
    }

    /**
     * Test send followup email should not send email if owners id is blank.
     */
    @Test
    public void testSendFollowupEmailShouldNotSendEmailIfOwnersIdIsBlank() {
        final String executionId = "id1";
        final LeadSource leadSource = new LeadSource();
        final Contact contact = new Contact();
        final NotificationPreferenceResponse preferencesResponse = new NotificationPreferenceResponse();
        final List< Preference > preferences = new ArrayList<>();
        final Preference preference = new Preference();
        preference.setType( MARKETING.name() );
        preference.setValue( true );
        preferences.add( preference );
        preferencesResponse.setPreferences( preferences );

        when( contactServiceV1.findByCrmId( leadSource.getId() ) ).thenReturn( contact );
        when( mailService.getNotificationPreferenceForUser( leadSource.getEmail() ) ).thenReturn( preferencesResponse );

        buyerFarmingBusinessServiceImpl.sendFollowupEmail( executionId, leadSource );

        verify( contactServiceV1 ).findByCrmId( leadSource.getId() );
        verify( mailService ).getNotificationPreferenceForUser( leadSource.getEmail() );
        verifyZeroInteractions( objectTypeService, runtimeService, buyerFarmingConfig, followupEmailNotificationBuilder,
                refCodeService, contactActivityService, leadService );
    }

    /**
     * Test send followup email should not send first followup email if flag is
     * disabled.
     */
    @Test
    public void testSendFollowupEmailShouldNotSendFirstFollowupEmailIfFlagIsDisabled() {
        final String executionId = "id1";
        final LeadSource leadSource = new LeadSource();
        final Contact contact = new Contact();
        contact.setOwnersComId( "test" );
        final ObjectType objectType1 = new ObjectType();
        objectType1.setName( LEAD );
        contact.setObjectType( objectType1 );

        final NotificationPreferenceResponse preferencesResponse = new NotificationPreferenceResponse();
        final List< Preference > preferences = new ArrayList<>();
        final Preference preference = new Preference();
        preference.setType( MARKETING.name() );
        preference.setValue( true );
        preferences.add( preference );
        preferencesResponse.setPreferences( preferences );
        final ObjectType objectType = new ObjectType();
        objectType.setName( "lead" );

        when( contactServiceV1.findByCrmId( leadSource.getId() ) ).thenReturn( contact );
        when( mailService.getNotificationPreferenceForUser( leadSource.getEmail() ) ).thenReturn( preferencesResponse );
        when( runtimeService.getVariable( executionId, BuyerFarmingConstants.FOLLOWUP_TYPE ) )
                .thenReturn( FOLLOW_UP_1 );
        when( objectTypeService.findByName( "lead" ) ).thenReturn( objectType );
        when( buyerFarmingConfig.isBuyerFirstFollowupEmailEnabled() ).thenReturn( false );

        buyerFarmingBusinessServiceImpl.sendFollowupEmail( executionId, leadSource );

        verify( contactServiceV1 ).findByCrmId( leadSource.getId() );
        verify( mailService ).getNotificationPreferenceForUser( leadSource.getEmail() );
        verify( runtimeService ).getVariable( executionId, BuyerFarmingConstants.FOLLOWUP_TYPE );
        verify( objectTypeService ).findByName( "lead" );
        verify( buyerFarmingConfig ).isBuyerFirstFollowupEmailEnabled();
        verifyZeroInteractions( followupEmailNotificationBuilder, refCodeService, contactActivityService, leadService );
    }

    /**
     * Test send followup email should not send second followup email if flag is
     * disabled.
     */
    @Test
    public void testSendFollowupEmailShouldNotSendSecondFollowupEmailIfFlagIsDisabled() {
        final String executionId = "id1";
        final LeadSource leadSource = new LeadSource();
        final Contact contact = new Contact();
        contact.setOwnersComId( "test" );
        final ObjectType objectType1 = new ObjectType();
        objectType1.setName( LEAD );
        contact.setObjectType( objectType1 );

        final NotificationPreferenceResponse preferencesResponse = new NotificationPreferenceResponse();
        final List< Preference > preferences = new ArrayList<>();
        final Preference preference = new Preference();
        preference.setType( MARKETING.name() );
        preference.setValue( true );
        preferences.add( preference );
        preferencesResponse.setPreferences( preferences );
        final ObjectType objectType = new ObjectType();
        objectType.setName( "lead" );

        when( contactServiceV1.findByCrmId( leadSource.getId() ) ).thenReturn( contact );
        when( mailService.getNotificationPreferenceForUser( leadSource.getEmail() ) ).thenReturn( preferencesResponse );
        when( runtimeService.getVariable( executionId, BuyerFarmingConstants.FOLLOWUP_TYPE ) )
                .thenReturn( FOLLOW_UP_2 );
        when( objectTypeService.findByName( "lead" ) ).thenReturn( objectType );
        when( buyerFarmingConfig.isBuyerSecondFollowupEmailEnabled() ).thenReturn( false );

        buyerFarmingBusinessServiceImpl.sendFollowupEmail( executionId, leadSource );

        verify( contactServiceV1 ).findByCrmId( leadSource.getId() );
        verify( mailService ).getNotificationPreferenceForUser( leadSource.getEmail() );
        verify( runtimeService ).getVariable( executionId, BuyerFarmingConstants.FOLLOWUP_TYPE );
        verify( objectTypeService ).findByName( "lead" );
        verify( buyerFarmingConfig ).isBuyerSecondFollowupEmailEnabled();
        verifyZeroInteractions( followupEmailNotificationBuilder, refCodeService, contactActivityService, leadService );
    }

    /**
     * Test send followup email should not send second followup email if not
     * eligible for second followup.
     */
    @Test
    public void testSendFollowupEmailShouldNotSendSecondFollowupEmailIfNotEligibleForSecondFollowup() {
        final String executionId = "id1";
        final LeadSource leadSource = new LeadSource();
        final Contact contact = new Contact();
        contact.setOwnersComId( "test" );
        final ObjectType objectType1 = new ObjectType();
        objectType1.setName( LEAD );
        contact.setObjectType( objectType1 );

        final NotificationPreferenceResponse preferencesResponse = new NotificationPreferenceResponse();
        final List< Preference > preferences = new ArrayList<>();
        final Preference preference = new Preference();
        preference.setType( MARKETING.name() );
        preference.setValue( true );
        preferences.add( preference );
        preferencesResponse.setPreferences( preferences );
        final ObjectType objectType = new ObjectType();
        objectType.setName( "lead" );
        final ObjectAttributeConfig farmingBuyerActionsAttributeConfig = new ObjectAttributeConfig();
        final Set< ContactJsonAttribute > contactJsonAttributes = new HashSet< ContactJsonAttribute >();
        contact.setContactJsonAttributes( contactJsonAttributes );
        final ContactJsonAttribute contactJsonAttribute = new ContactJsonAttribute();
        contactJsonAttribute.setObjectAttributeConfig( farmingBuyerActionsAttributeConfig );
        contactJsonAttribute.setValue( "{\"" + FARMING_BUYER_ACTIONS.getKey() + "\":[\"none\"]}" );
        contactJsonAttributes.add( contactJsonAttribute );
        contact.setContactJsonAttributes( contactJsonAttributes );
        final Set< ContactAttribute > contactAttributes = new HashSet<>();
        final ContactAttribute contactAttribute = new ContactAttribute();
        final ObjectAttributeConfig farmingGroupAttributeConfig = new ObjectAttributeConfig();
        contactAttribute.setValue( LONG_TERM_BUYER.getType() );
        contactAttribute.setObjectAttributeConfig( farmingGroupAttributeConfig );
        contactAttributes.add( contactAttribute );
        contact.setContactAttributes( contactAttributes );

        when( contactServiceV1.findByCrmId( leadSource.getId() ) ).thenReturn( contact );
        when( mailService.getNotificationPreferenceForUser( leadSource.getEmail() ) ).thenReturn( preferencesResponse );
        when( runtimeService.getVariable( executionId, BuyerFarmingConstants.FOLLOWUP_TYPE ) )
                .thenReturn( FOLLOW_UP_2 );
        when( objectTypeService.findByName( "lead" ) ).thenReturn( objectType );
        when( buyerFarmingConfig.isBuyerSecondFollowupEmailEnabled() ).thenReturn( true );
        when( objectAttributeConfigService.getObjectAttributeConfig( anyString(), any( ObjectType.class ) ) )
                .thenReturn( farmingBuyerActionsAttributeConfig );

        buyerFarmingBusinessServiceImpl.sendFollowupEmail( executionId, leadSource );

        verify( contactServiceV1 ).findByCrmId( leadSource.getId() );
        verify( mailService ).getNotificationPreferenceForUser( leadSource.getEmail() );
        verify( runtimeService ).getVariable( executionId, BuyerFarmingConstants.FOLLOWUP_TYPE );
        verify( objectTypeService ).findByName( "lead" );
        verify( buyerFarmingConfig ).isBuyerSecondFollowupEmailEnabled();
        verifyZeroInteractions( followupEmailNotificationBuilder, refCodeService, contactActivityService, leadService );
    }

    /**
     * Test process web activity.
     */
    @Test
    public void testProcessWebActivity() {
        final BuyerWebActivitySource message = new BuyerWebActivitySource();
        message.setUserId( "12345" );
        final Contact contact = new Contact();
        final com.owners.gravitas.domain.entity.Process process = new Process();
        process.setExecutionId( "11" );
        final Map< String, Object > paramData = new HashMap<>();
        paramData.put( "buyerWebActivitySource", message );

        when( contactServiceV1.getContactByOwnersComId( message.getUserId() ) ).thenReturn( contact );
        when( processBusinessService.getProcess( contact.getEmail(), GravitasProcess.INSIDE_SALES_FARMING_PROCESS,
                "active" ) ).thenReturn( process );
        doNothing().when( runtimeService ).setVariable( process.getExecutionId(), "processWebActivity", true );
        doNothing().when( runtimeService ).trigger( process.getExecutionId(), paramData );

        buyerFarmingBusinessServiceImpl.processWebActivity( message );

        verify( contactServiceV1 ).getContactByOwnersComId( message.getUserId() );
        verify( processBusinessService ).getProcess( contact.getEmail(), GravitasProcess.INSIDE_SALES_FARMING_PROCESS,
                "active" );
        verify( runtimeService ).setVariable( process.getExecutionId(), "processWebActivity", true );
        verify( runtimeService ).trigger( process.getExecutionId(), paramData );
    }

    /**
     * Test send web activity followup email.
     *
     * @param propertiesMap
     *            the properties map
     * @param activityConfMap
     *            the activity conf map
     * @param contactWebActivityMessage
     *            the contact web activity message
     * @param notificationPreferenceResponse
     *            the notification preference response
     * @param propertyDetailsResponse
     *            the property details response
     */
    // @Test( dataProvider = "getActivityData" )
    public void testSendWebActivityFollowupEmail( final Map< String, String > propertiesMap,
            final Map< String, Object > activityConfMap, final BuyerWebActivitySource contactWebActivityMessage,
            final NotificationPreferenceResponse notificationPreferenceResponse,
            final PropertyDetailsResponse propertyDetailsResponse ) {
        final String highestPriorityAction = "FAVORITED";

        final ObjectType objectType = new ObjectType();
        objectType.setName( "lead" );

        final ObjectAttributeConfig objectAttributeConfig = new ObjectAttributeConfig();
        objectAttributeConfig.setAttributeName( "farmingGroup" );
        objectAttributeConfig.setObjectType( objectType );
        final ContactAttribute contactAttribute = new ContactAttribute();
        contactAttribute.setObjectAttributeConfig( objectAttributeConfig );
        contactAttribute.setValue( "Long Term" );
        final Set< ContactAttribute > contactAttributes = new HashSet< ContactAttribute >();
        contactAttributes.add( contactAttribute );

        final ObjectAttributeConfig objectJsonAttributeConfig1 = new ObjectAttributeConfig();
        objectJsonAttributeConfig1.setAttributeName( "farmingSystemActions" );
        objectJsonAttributeConfig1.setObjectType( objectType );
        final ContactJsonAttribute contactJsonAttribute1 = new ContactJsonAttribute();
        contactJsonAttribute1.setObjectAttributeConfig( objectJsonAttributeConfig1 );

        final ObjectAttributeConfig objectJsonAttributeConfig2 = new ObjectAttributeConfig();
        objectJsonAttributeConfig2.setAttributeName( "farmingStatus" );
        objectJsonAttributeConfig2.setObjectType( objectType );
        final ContactJsonAttribute contactJsonAttribute2 = new ContactJsonAttribute();
        contactJsonAttribute2.setObjectAttributeConfig( objectJsonAttributeConfig2 );

        final ObjectAttributeConfig objectJsonAttributeConfig3 = new ObjectAttributeConfig();
        objectJsonAttributeConfig3.setAttributeName( "farmingBuyerActions" );
        objectJsonAttributeConfig3.setObjectType( objectType );
        final ContactJsonAttribute contactJsonAttribute3 = new ContactJsonAttribute();
        contactJsonAttribute3.setObjectAttributeConfig( objectJsonAttributeConfig3 );

        final Set< ContactJsonAttribute > contactJsonAttributes = new HashSet< ContactJsonAttribute >();
        contactJsonAttributes.add( contactJsonAttribute1 );
        contactJsonAttributes.add( contactJsonAttribute2 );
        contactJsonAttributes.add( contactJsonAttribute3 );

        final Contact contact = new Contact();
        contact.setFirstName( "testfirstname" );
        contact.setEmail( "test.user@test1.com" );
        contact.setContactAttributes( contactAttributes );
        contact.setContactJsonAttributes( contactJsonAttributes );

        final String activitySuffixKey = activityConfMap.get( highestPriorityAction ).toString();
        final String emailTemplate = PropertiesUtil.getProperty( "buyer.web." + activitySuffixKey + ".emailtemplate" );
        final String emailDynamicParametersStr = PropertiesUtil
                .getProperty( "buyer.web." + activitySuffixKey + ".emailDyanamicParameters" );

        final String fromEmail = "test.user1@test1.com";
        final Map< String, Object > sourceMap = new HashMap< String, Object >();
        sourceMap.put( "fromEmail", "test.user1@test1.com" );
        sourceMap.put( "toEmail", contact.getEmail() );
        sourceMap.put( "ADDRESS_LINE1_LINE2", propertyDetailsResponse.getData().getPropertyAddress().getAddressLine1()
                + " , " + propertyDetailsResponse.getData().getPropertyAddress().getAddressLine2() );
        sourceMap.put( "BUYER_FIRST_NAME", contact.getFirstName() );

        final NotificationResponse notificationResponse = new NotificationResponse();
        notificationResponse.setStatus( "SUCCESS" );

        final String farmingSystemActions = PropertiesUtil
                .getProperty( "buyer.web." + activitySuffixKey + ".farmingSystemActions" );
        final String farmingStatus = PropertiesUtil.getProperty( "buyer.web." + activitySuffixKey + ".farmingStatus" );
        final String farmingBuyerActions = PropertiesUtil
                .getProperty( "buyer.web." + activitySuffixKey + ".farmingBuyerActions" );

        final ContactActivity contactActivity = new ContactActivity();

        when( contactServiceV1.getContactByOwnersComId( contactWebActivityMessage.getUserId() ) ).thenReturn( contact );
        when( objectTypeService.findByName( objectType.getName() ) ).thenReturn( objectType );
        when( objectAttributeConfigService.getObjectAttributeConfig( objectAttributeConfig.getAttributeName(),
                objectType ) ).thenReturn( objectAttributeConfig );
        when( objectAttributeConfigService.getObjectAttributeConfig( objectJsonAttributeConfig1.getAttributeName(),
                objectType ) ).thenReturn( objectJsonAttributeConfig1 );
        when( objectAttributeConfigService.getObjectAttributeConfig( objectJsonAttributeConfig2.getAttributeName(),
                objectType ) ).thenReturn( objectJsonAttributeConfig2 );
        when( objectAttributeConfigService.getObjectAttributeConfig( objectJsonAttributeConfig3.getAttributeName(),
                objectType ) ).thenReturn( objectJsonAttributeConfig3 );
        when( mailService.getNotificationPreferenceForUser( contact.getEmail() ) )
                .thenReturn( notificationPreferenceResponse );
        when( propertyBusinessService.getPropertyDetails(
                contactWebActivityMessage.getAlertDetails().get( 0 ).getClientEventDetails().get( 0 ).getListingId() ) )
                        .thenReturn( propertyDetailsResponse );
        when( webActivityEmailNotificationBuilder.convertTo( sourceMap ) ).thenReturn( emailNotification );
        doNothing().when( emailNotification ).setMessageTypeName( emailTemplate );
        when( emailNotification.getEmail() ).thenReturn( email );
        when( mailService.send( emailNotification ) ).thenReturn( notificationResponse );
        when( contactActivityBuilder.convertTo( contactWebActivityMessage.getAlertDetails().get( 0 ) ) )
                .thenReturn( contactActivity );
        when( contactActivityService.save( contactActivity ) ).thenReturn( contactActivity );

        buyerFarmingBusinessServiceImpl.sendWebActivityFollowupEmail( contactWebActivityMessage );

        verify( contactServiceV1 ).getContactByOwnersComId( contactWebActivityMessage.getUserId() );
        verify( objectTypeService, times( 2 ) ).findByName( objectType.getName() );
        verify( objectAttributeConfigService ).getObjectAttributeConfig( objectAttributeConfig.getAttributeName(),
                objectType );
        verify( objectAttributeConfigService ).getObjectAttributeConfig( objectJsonAttributeConfig1.getAttributeName(),
                objectType );
        verify( objectAttributeConfigService ).getObjectAttributeConfig( objectJsonAttributeConfig2.getAttributeName(),
                objectType );
        verify( objectAttributeConfigService ).getObjectAttributeConfig( objectJsonAttributeConfig3.getAttributeName(),
                objectType );
        verify( objectAttributeConfigService ).getObjectAttributeConfig( objectAttributeConfig.getAttributeName(),
                objectType );
        verify( mailService ).getNotificationPreferenceForUser( contact.getEmail() );
        verify( propertyBusinessService ).getPropertyDetails(
                contactWebActivityMessage.getAlertDetails().get( 0 ).getClientEventDetails().get( 0 ).getListingId() );
        verify( webActivityEmailNotificationBuilder ).convertTo( sourceMap );
        verify( emailNotification ).setMessageTypeName( emailTemplate );
        verify( emailNotification ).getEmail();
        verify( mailService ).send( emailNotification );
        verify( contactActivityBuilder ).convertTo( contactWebActivityMessage.getAlertDetails().get( 0 ) );
        verify( contactActivityService ).save( contactActivity );
    }

    /**
     * Gets the activity data.
     *
     * @return the activity data
     */
    @DataProvider( name = "getActivityData" )
    public Object[][] getActivityData() {
        final Map< String, String > propertiesMap = propertiesUtil.getPropertiesMap();
        propertiesMap.put( "buyer.web.activity1", "FAVORITED" );
        propertiesMap.put( "buyer.web.activity2", "PDP_VIEWED" );
        propertiesMap.put( "buyer.web.activity3", "PDP_SHARED" );
        propertiesMap.put( "buyer.web.activity4", "TOUR_REQUEST_ABANDONED" );
        propertiesMap.put( "buyer.web.activity5", "TOUR_SAVED" );
        propertiesMap.put( "buyer.web.activity6", "PDP_VIEWED_INDECISIVE" );
        propertiesMap.put( "buyer.web.total.activities", "6" );
        propertiesMap.put( "buyer.web.activity1.emailtemplate", "Marathon_CES_Fav2PDPS" );
        propertiesMap.put( "buyer.web.activity1.farmingSystemActions", "CES 1 - Properties favorited Email sent" );
        propertiesMap.put( "buyer.web.activity1.farmingStatus", "Engaged - Inside Sales Farming" );
        propertiesMap.put( "buyer.web.activity1.farmingBuyerActions", "CES 1 - Properties favorited" );
        propertiesMap.put( "buyer.web.activity1.emailDyanamicParameters", "ADDRESS_LINE1_LINE2,BUYER_FIRST_NAME" );

        final Map< String, Object > activityConfMap = new HashMap< String, Object >();
        activityConfMap.put( "FAVORITED", "activity1" );
        activityConfMap.put( "PDP_VIEWED", "activity2" );
        activityConfMap.put( "PDP_SHARED", "activity3" );
        activityConfMap.put( "TOUR_REQUEST_ABANDONED", "activity4" );
        activityConfMap.put( "TOUR_SAVED", "activity5" );
        activityConfMap.put( "PDP_VIEWED_INDECISIVE", "activity6" );

        final ClientEventDetails clientEventDetail = new ClientEventDetails();
        clientEventDetail.setListingId( "123456" );
        clientEventDetail.setUserId( "1234-4444" );
        final List< ClientEventDetails > clientEventDetails = new ArrayList< ClientEventDetails >();
        clientEventDetails.add( clientEventDetail );
        final AlertDetails alertDetail = new AlertDetails();
        alertDetail.setEventDisplayName( "FAVORITED" );
        alertDetail.setClientEventDetails( clientEventDetails );
        final List< AlertDetails > alertDetails = new ArrayList< AlertDetails >();
        alertDetails.add( alertDetail );
        final BuyerWebActivitySource contactWebActivityMessage = new BuyerWebActivitySource();
        contactWebActivityMessage.setUserId( "1234-4444" );
        contactWebActivityMessage.setAlertDetails( alertDetails );

        final Preference preference = new Preference();
        preference.setType( "MARKETING" );
        preference.setValue( true );
        final List< Preference > preferences = new ArrayList< Preference >();
        preferences.add( preference );
        final NotificationPreferenceResponse notificationPreferenceResponse = new NotificationPreferenceResponse();
        notificationPreferenceResponse.setPreferences( preferences );

        final PropertyAddress propertyAddress = new PropertyAddress();
        propertyAddress.setAddressLine1( "testline1" );
        propertyAddress.setAddressLine2( "testline2" );
        final PropertyData propertyData = new PropertyData();
        propertyData.setPropertyAddress( propertyAddress );
        final PropertyDetailsResponse propertyDetailsResponse = new PropertyDetailsResponse();
        propertyDetailsResponse.setData( propertyData );

        return new Object[][] { { propertiesMap, activityConfMap, contactWebActivityMessage,
                notificationPreferenceResponse, propertyDetailsResponse } };

    }

    /**
     * Test register buyer_ success flow.
     */
    @Test
    private void testRegisterBuyer_SuccessFlow() {
        final LeadSource source = new LeadSource();
        source.setEmail( "test@agent.com" );

        final GenericLeadRequest request = new GenericLeadRequest();

        final Contact contact = new Contact();
        final ContactJsonAttribute contactAttribute = new ContactJsonAttribute();
        final ObjectAttributeConfig oConf = new ObjectAttributeConfig();
        oConf.setAttributeName( "test" );
        contactAttribute.setObjectAttributeConfig( oConf );
        contactAttribute.setValue( "{\"farmingSystemActions\":[\"data\"],\"notes\":[\"data\"]}" );
        final Set< ContactJsonAttribute > contactJsonAttributeSet = new HashSet<>();
        contactJsonAttributeSet.add( contactAttribute );
        contact.setContactJsonAttributes( contactJsonAttributeSet );

        final ObjectType objectType = new ObjectType();
        objectType.setName( "lead" );

        final RegistrationResponse registrationResponse = new RegistrationResponse();
        final RegisteredUserResponse response = new RegisteredUserResponse();
        final RegisteredUser user = new RegisteredUser();
        user.setUserId( "test" );
        response.setUser( user );
        response.setStatus( OK.name() );
        registrationResponse.setResult( response );

        final ObjectAttributeConfig conf = new ObjectAttributeConfig();
        contactAttribute.setObjectAttributeConfig( conf );

        when( config.isBuyerAutoRegistrationEnabled() ).thenReturn( false );
        when( genericLeadRequestBuilder.convertTo( source ) ).thenReturn( request );
        when( contactServiceV1.getContact( source.getEmail(), RecordType.BUYER.getType() ) ).thenReturn( contact );
        when( objectTypeService.findByName( "lead" ) ).thenReturn( objectType );
        when( buyerService.registerBuyer( anyMap() ) ).thenReturn( registrationResponse );
        when( objectAttributeConfigService.getObjectAttributeConfig( any(), any() ) ).thenReturn( conf );
        when( leadDetailsBuilder.convertTo( request ) ).thenReturn( new HashMap< String, String >() );

        final Contact contactResponse = buyerFarmingBusinessServiceImpl.registerBuyer( source );
        verify(noteService, Mockito.atLeastOnce()).saveNote(Mockito.any(CrmNote.class));
    }

    /**
     * Test register buyer_ failure flow.
     */
    //@Test
    private void testRegisterBuyer_FailureFlow() {
        final LeadSource source = new LeadSource();
        source.setEmail( "test@agent.com" );

        final GenericLeadRequest request = new GenericLeadRequest();

        final Contact contact = new Contact();
        final ContactJsonAttribute contactAttribute = new ContactJsonAttribute();
        final ObjectAttributeConfig oConf = new ObjectAttributeConfig();
        oConf.setAttributeName( "test" );
        contactAttribute.setObjectAttributeConfig( oConf );
        contactAttribute.setValue( "{\"farmingSystemActions\":[\"data\"],\"notes\":[\"data\"]}" );
        final Set< ContactJsonAttribute > contactJsonAttributeSet = new HashSet<>();
        contactJsonAttributeSet.add( contactAttribute );
        contact.setContactJsonAttributes( contactJsonAttributeSet );

        final ObjectType objectType = new ObjectType();
        objectType.setName( "lead" );

        final RegistrationResponse registrationResponse = new RegistrationResponse();
        final RegisteredUserResponse response = new RegisteredUserResponse();
        final RegisteredUser user = new RegisteredUser();
        user.setUserId( "test" );
        response.setUser( user );
        response.setStatus( Status.FAILURE.name() );
        registrationResponse.setResult( response );

        final ObjectAttributeConfig conf = new ObjectAttributeConfig();
        contactAttribute.setObjectAttributeConfig( conf );

        when( config.isBuyerAutoRegistrationEnabled() ).thenReturn( false );
        when( genericLeadRequestBuilder.convertTo( source ) ).thenReturn( request );
        when( contactServiceV1.getContact( source.getEmail(), RecordType.BUYER.getType() ) ).thenReturn( contact );
        when( objectTypeService.findByName( "lead" ) ).thenReturn( objectType );
        when( buyerService.registerBuyer( anyMap() ) ).thenReturn( registrationResponse );
        when( objectAttributeConfigService.getObjectAttributeConfig( any(), any() ) ).thenReturn( conf );
        when( leadDetailsBuilder.convertTo( request ) ).thenReturn( new HashMap< String, String >() );

        final Contact contactResponse = buyerFarmingBusinessServiceImpl.registerBuyer( source );

        contactResponse.getContactJsonAttributes().forEach( attrib -> {
            Assert.assertEquals( attrib.getValue().contains( "Registration Failed" ), true );
        } );

    }

    /**
     * Test register buyer_ exception flow.
     */
    @Test
    private void testRegisterBuyer_ExceptionFlow() {
        final LeadSource source = new LeadSource();
        source.setEmail( "test@agent.com" );

        final GenericLeadRequest request = new GenericLeadRequest();

        final Contact contact = new Contact();
        final ContactJsonAttribute contactAttribute = new ContactJsonAttribute();
        final ObjectAttributeConfig oConf = new ObjectAttributeConfig();
        oConf.setAttributeName( "test" );
        contactAttribute.setObjectAttributeConfig( oConf );
        contactAttribute.setValue(
                "{\"farmingSystemActions\":[\"data\"],\"notes\":[\"data\"],\"farmingfailureCode\":[\"data\"]}" );
        final Set< ContactJsonAttribute > contactJsonAttributeSet = new HashSet<>();
        contactJsonAttributeSet.add( contactAttribute );
        contact.setContactJsonAttributes( contactJsonAttributeSet );

        final ObjectType objectType = new ObjectType();
        objectType.setName( "lead" );

        final RegistrationResponse registrationResponse = new RegistrationResponse();
        final RegisteredUserResponse response = new RegisteredUserResponse();
        final RegisteredUser user = new RegisteredUser();
        user.setUserId( "test" );
        response.setUser( user );
        response.setStatus( Status.FAILURE.name() );
        registrationResponse.setResult( response );

        final ObjectAttributeConfig conf = new ObjectAttributeConfig();
        contactAttribute.setObjectAttributeConfig( conf );

        when( config.isBuyerAutoRegistrationEnabled() ).thenReturn( false );
        when( genericLeadRequestBuilder.convertTo( source ) ).thenReturn( request );
        when( contactServiceV1.getContact( source.getEmail(), RecordType.BUYER.getType() ) ).thenReturn( contact );
        when( objectTypeService.findByName( "lead" ) ).thenReturn( objectType );
        when( buyerService.registerBuyer( anyMap() ) ).thenThrow( new ArrayIndexOutOfBoundsException() );
        when( objectAttributeConfigService.getObjectAttributeConfig( any(), any() ) ).thenReturn( conf );
        when( leadDetailsBuilder.convertTo( request ) ).thenReturn( new HashMap< String, String >() );

        final Contact contactResponse = buyerFarmingBusinessServiceImpl.registerBuyer( source );
        verify(processBusinessService, Mockito.atLeastOnce()).deActivateAndSignal(null, GravitasProcess.LEAD_MANAGEMENT_PROCESS, null);
    }

    /**
     * Testupdate farming status_for loast lead.
     */
    @Test
    public void testupdateFarmingStatus_forLoastLead() {
        final String crmId = "crmId";
        final Contact contact = new Contact();
        final ContactJsonAttribute contactAttribute = new ContactJsonAttribute();
        final ObjectAttributeConfig oConf = new ObjectAttributeConfig();
        oConf.setAttributeName( "test" );
        contactAttribute.setObjectAttributeConfig( oConf );
        contactAttribute.setValue(
                "{\"farmingSystemActions\":[\"data\"],\"notes\":[\"data\"],\"farmingfailureCode\":[\"data\"]}" );
        final Set< ContactJsonAttribute > contactJsonAttributeSet = new HashSet<>();
        contactJsonAttributeSet.add( contactAttribute );
        contact.setContactJsonAttributes( contactJsonAttributeSet );
        contact.setContactAttributes( new HashSet< ContactAttribute >() );

        when( contactServiceV1.findByCrmId( crmId ) ).thenReturn( contact );
        buyerFarmingBusinessServiceImpl.updateFarmingStatus( crmId, BuyerFarmType.LOST_BUYER, false );
        verify( contactServiceV1 ).save( contact );

    }

    /**
     * Testupdate farming status_for long term farming.
     */
    @Test
    public void testupdateFarmingStatus_forLongTermFarming() {
        final String crmId = "crmId";
        final Contact contact = new Contact();
        final ContactJsonAttribute contactAttribute = new ContactJsonAttribute();
        final ObjectAttributeConfig oConf = new ObjectAttributeConfig();
        oConf.setAttributeName( "test" );
        contactAttribute.setObjectAttributeConfig( oConf );
        contactAttribute.setValue(
                "{\"farmingSystemActions\":[\"data\"],\"notes\":[\"data\"],\"farmingfailureCode\":[\"data\"]}" );
        final Set< ContactJsonAttribute > contactJsonAttributeSet = new HashSet<>();
        contactJsonAttributeSet.add( contactAttribute );
        contact.setContactJsonAttributes( contactJsonAttributeSet );
        contact.setContactAttributes( new HashSet< ContactAttribute >() );

        when( contactServiceV1.findByCrmId( crmId ) ).thenReturn( contact );
        buyerFarmingBusinessServiceImpl.updateFarmingStatus( crmId, LONG_TERM_BUYER, true );
        verify( contactServiceV1 ).save( contact );
    }

    /**
     * Test is buyer auto registration email should return true when auto
     * registered email.
     */
    @Test
    public void testIsBuyerAutoRegistrationEmailShouldReturnTrueWhenAutoRegisteredEmail() {
        final String emailStr = "test@test.com";
        final String autoRegEmailStr = "test@test.com,test1@test.com";
        when( buyerService.isBuyerAutoRegistrationEmail( emailStr ) ).thenReturn( true );
        final boolean result = buyerFarmingBusinessServiceImpl.isBuyerAutoRegistrationEmail( emailStr );
        assertTrue( result );
        verify( buyerService ).isBuyerAutoRegistrationEmail( emailStr );

    }

    /**
     * Test is buyer auto registration email should return false when not auto
     * registered email.
     */
    @Test
    public void testIsBuyerAutoRegistrationEmailShouldReturnFalseWhenNotAutoRegisteredEmail() {
        final String emailStr = "test@test.com";
        final String autoRegEmailStr = "test@test.com,test1@test.com";
        final boolean result = buyerFarmingBusinessServiceImpl.isBuyerAutoRegistrationEmail( emailStr );
        assertFalse( result );
        verify( buyerService ).isBuyerAutoRegistrationEmail( emailStr );
    }

    /**
     * Test save search should create save search when not exist.
     */
    @Test
    public void testSaveSearchShouldCreateSaveSearchWhenNotExist() {
        final LeadSource leadSource = new LeadSource();
        final ObjectType objectType = new ObjectType();
        final GenericLeadRequest leadRequest = new GenericLeadRequest();
        leadRequest.setPriceRange( "test" );
        leadRequest.setEmail("test@email.com");
        final Contact contact = new Contact();
        contact.setOwnersComId( "test" );
        final SaveSearchResponse savedSearchResponse = new SaveSearchResponse();
        final SaveSearchResultResponse saveSearchResultResponse = new SaveSearchResultResponse();
        saveSearchResultResponse.setStatus( SUCCESS.name() );
        savedSearchResponse.setResult( saveSearchResultResponse );

        final Set< ContactJsonAttribute > contactJsonAttributes = new HashSet<>();
        final ContactJsonAttribute contactJsonAttribute = new ContactJsonAttribute();
        final ObjectAttributeConfig objectAttributeConfig = new ObjectAttributeConfig();

        contactJsonAttribute.setObjectAttributeConfig( objectAttributeConfig );
        contactJsonAttributes.add( contactJsonAttribute );
        contact.setContactJsonAttributes( contactJsonAttributes );

        when( genericLeadRequestBuilder.convertTo( leadSource ) ).thenReturn( leadRequest );
        when( objectTypeService.findByName( "lead" ) ).thenReturn( objectType );
        when( contactServiceV1.getContact( leadRequest.getEmail(), BUYER.getType() ) ).thenReturn( contact );
        when( buyerService.checkSaveSearchExists( contact.getOwnersComId() ) ).thenReturn( false );
        when( buyerService.saveSearch( any( SavedSearchRequest.class ) ) ).thenReturn( savedSearchResponse );
        when( objectAttributeConfigService.getObjectAttributeConfig( anyString(), any( ObjectType.class ) ) )
                .thenReturn( new ObjectAttributeConfig() ).thenReturn( new ObjectAttributeConfig() );

        buyerFarmingBusinessServiceImpl.saveSearch( leadSource );

        verify( genericLeadRequestBuilder ).convertTo( leadSource );
        verify( objectTypeService ).findByName( "lead" );
        verify( contactServiceV1 ).getContact( leadRequest.getEmail(), BUYER.getType() );
        verify( buyerService ).checkSaveSearchExists( contact.getOwnersComId() );
        verify( leadService ).updateLead( anyMap(), anyString(), anyBoolean() );
        verify( noteService ).saveNote( any( CrmNote.class ) );
        verify( runtimeService ).startProcessInstanceByKey( anyString(), anyMap() );
    }

    /**
     * Test save search should not create save search when exist.
     */
    @Test
    public void testSaveSearchShouldNotCreateSaveSearchWhenExist() {
        final LeadSource leadSource = new LeadSource();
        final ObjectType objectType = new ObjectType();
        final GenericLeadRequest leadRequest = new GenericLeadRequest();
        leadRequest.setEmail("test@email.com");
        final Contact contact = new Contact();
        contact.setOwnersComId( "test" );
        final Set< ContactJsonAttribute > contactJsonAttributes = new HashSet<>();
        final ContactJsonAttribute contactJsonAttribute = new ContactJsonAttribute();
        final ObjectAttributeConfig objectAttributeConfig = new ObjectAttributeConfig();
        contactJsonAttribute.setObjectAttributeConfig( objectAttributeConfig );
        contact.setContactJsonAttributes( contactJsonAttributes );
        contactJsonAttributes.add( contactJsonAttribute );

        when( genericLeadRequestBuilder.convertTo( leadSource ) ).thenReturn( leadRequest );
        when( objectTypeService.findByName( "lead" ) ).thenReturn( objectType );
        when( contactServiceV1.getContact( leadRequest.getEmail(), BUYER.getType() ) ).thenReturn( contact );
        when( buyerService.checkSaveSearchExists( contact.getOwnersComId() ) ).thenReturn( true );
        when( objectAttributeConfigService.getObjectAttributeConfig( anyString(), any( ObjectType.class ) ) )
                .thenReturn( new ObjectAttributeConfig() ).thenReturn( new ObjectAttributeConfig() );

        buyerFarmingBusinessServiceImpl.saveSearch( leadSource );

        verify( genericLeadRequestBuilder ).convertTo( leadSource );
        verify( objectTypeService ).findByName( "lead" );
        verify( contactServiceV1 ).getContact( leadRequest.getEmail(), BUYER.getType() );
        verify( buyerService ).checkSaveSearchExists( contact.getOwnersComId() );
        verify( noteService ).saveNote( Mockito.any( CrmNote.class ) );
        verify( contactServiceV1, times( 2 ) ).save( contact );
        verify( buyerService, times( 0 ) ).saveSearch( any( SavedSearchRequest.class ) );
        verify( leadService, times( 2 ) ).updateLead( anyMap(), anyString(), anyBoolean() );
    }

    /**
     * Test save search should not create save search when buyer api response is
     * failed.
     */
    @Test
    public void testSaveSearchShouldNotCreateSaveSearchWhenBuyerApiResponseIsFailed() {
        final LeadSource leadSource = new LeadSource();
        final ObjectType objectType = new ObjectType();
        final GenericLeadRequest leadRequest = new GenericLeadRequest();
        leadRequest.setEmail("test@email.com");
        leadRequest.setPropertyAddress( "test" );
        final Contact contact = new Contact();
        contact.setOwnersComId( "test" );
        final SaveSearchResponse savedSearchResponse = new SaveSearchResponse();
        final SaveSearchResultResponse saveSearchResultResponse = new SaveSearchResultResponse();
        saveSearchResultResponse.setStatus( BaseResponse.Status.FAILURE.name() );
        savedSearchResponse.setResult( saveSearchResultResponse );

        final Set< ContactJsonAttribute > contactJsonAttributes = new HashSet<>();
        final ContactJsonAttribute contactJsonAttribute = new ContactJsonAttribute();
        final ObjectAttributeConfig objectAttributeConfig = new ObjectAttributeConfig();

        contactJsonAttribute.setObjectAttributeConfig( objectAttributeConfig );
        contactJsonAttributes.add( contactJsonAttribute );
        contact.setContactJsonAttributes( contactJsonAttributes );

        when( genericLeadRequestBuilder.convertTo( leadSource ) ).thenReturn( leadRequest );
        when( objectTypeService.findByName( "lead" ) ).thenReturn( objectType );
        when( contactServiceV1.getContact( leadRequest.getEmail(), BUYER.getType() ) ).thenReturn( contact );
        when( buyerService.checkSaveSearchExists( contact.getOwnersComId() ) ).thenReturn( false );
        when( buyerService.saveSearch( any( SavedSearchRequest.class ) ) ).thenReturn( savedSearchResponse );
        when( objectAttributeConfigService.getObjectAttributeConfig( anyString(), any( ObjectType.class ) ) )
                .thenReturn( new ObjectAttributeConfig() ).thenReturn( new ObjectAttributeConfig() );
        when( leadDetailsBuilder.convertTo( leadRequest ) ).thenReturn( new HashMap<>() );

        buyerFarmingBusinessServiceImpl.saveSearch( leadSource );

        verify( genericLeadRequestBuilder ).convertTo( leadSource );
        verify( objectTypeService ).findByName( "lead" );
        verify( contactServiceV1 ).getContact( leadRequest.getEmail(), BUYER.getType() );
        verify( buyerService ).checkSaveSearchExists( contact.getOwnersComId() );
        verify( leadService ).updateLead( anyMap(), anyString(), anyBoolean() );
        verify( noteService ).saveNote( any( CrmNote.class ) );
        verify( leadDetailsBuilder ).convertTo( leadRequest );
        verifyZeroInteractions( runtimeService );
    }
    
    /**
     * Test save search should create save search when not exist.
     */
    @Test
    public void testSaveSearchShouldCreateSaveSearchWhenNotExistAndHasInvalidPhoneNo() {
        final LeadSource leadSource = new LeadSource();
        final ObjectType objectType = new ObjectType();
        final GenericLeadRequest leadRequest = new GenericLeadRequest();
        leadRequest.setPriceRange( "test" );
        leadRequest.setEmail("test@email.com");
        final Contact contact = new Contact();
        contact.setPhone( "8002312345" );
        contact.setOwnersComId( "test" );
        final SaveSearchResponse savedSearchResponse = new SaveSearchResponse();
        final SaveSearchResultResponse saveSearchResultResponse = new SaveSearchResultResponse();
        saveSearchResultResponse.setStatus( SUCCESS.name() );
        savedSearchResponse.setResult( saveSearchResultResponse );

        final Set< ContactJsonAttribute > contactJsonAttributes = new HashSet<>();
        final ContactJsonAttribute contactJsonAttribute = new ContactJsonAttribute();
        final ObjectAttributeConfig objectAttributeConfig = new ObjectAttributeConfig();

        contactJsonAttribute.setObjectAttributeConfig( objectAttributeConfig );
        contactJsonAttributes.add( contactJsonAttribute );
        contact.setContactJsonAttributes( contactJsonAttributes );

        when( leadService.isValidPhoneNumber( anyObject() ) ).thenReturn( true );
        when( genericLeadRequestBuilder.convertTo( leadSource ) ).thenReturn( leadRequest );
        when( objectTypeService.findByName( "lead" ) ).thenReturn( objectType );
        when( contactServiceV1.getContact( leadRequest.getEmail(), BUYER.getType() ) ).thenReturn( contact );
        when( buyerService.checkSaveSearchExists( contact.getOwnersComId() ) ).thenReturn( false );
        when( buyerService.saveSearch( any( SavedSearchRequest.class ) ) ).thenReturn( savedSearchResponse );
        when( objectAttributeConfigService.getObjectAttributeConfig( anyString(), any( ObjectType.class ) ) )
                .thenReturn( new ObjectAttributeConfig() ).thenReturn( new ObjectAttributeConfig() );

        buyerFarmingBusinessServiceImpl.saveSearch( leadSource );

        verify( genericLeadRequestBuilder ).convertTo( leadSource );
        verify( objectTypeService ).findByName( "lead" );
        verify( contactServiceV1 ).getContact( leadRequest.getEmail(), BUYER.getType() );
        verify( buyerService ).checkSaveSearchExists( contact.getOwnersComId() );
        verify( leadService ).updateLead( anyMap(), anyString(), anyBoolean() );
        verify( noteService ).saveNote( any( CrmNote.class ) );
        verify( runtimeService ).startProcessInstanceByKey( anyString(), anyMap() );
    }
}
