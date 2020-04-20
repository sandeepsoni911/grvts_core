package com.owners.gravitas.business.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.hubzu.notification.dto.client.email.Email;
import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.builder.LeadFollowupNotificationBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.TimerConfig;
import com.owners.gravitas.domain.entity.MarketingEmailLog;
import com.owners.gravitas.domain.entity.StateTimeZone;
import com.owners.gravitas.dto.CrmNote;
import com.owners.gravitas.dto.Preference;
import com.owners.gravitas.dto.response.NotificationPreferenceResponse;
import com.owners.gravitas.dto.response.NotificationResponse;
import com.owners.gravitas.enums.LeadStatus;
import com.owners.gravitas.enums.NotificationType;
import com.owners.gravitas.enums.RecordType;
import com.owners.gravitas.service.LeadFollowupLogService;
import com.owners.gravitas.service.LeadService;
import com.owners.gravitas.service.MailService;
import com.owners.gravitas.service.NoteService;
import com.owners.gravitas.service.StateTimeZoneService;

/**
 * The Class LeadFollowupBusinessServiceImplTest.
 *
 * @author vishwanathm
 */
public class LeadFollowupBusinessServiceImplTest extends AbstractBaseMockitoTest {

    /** The marketing email business service impl. */
    @InjectMocks
    private LeadFollowupBusinessServiceImpl leadFollowupBusinessServiceImpl;

    /** The marketing email log service. */
    @Mock
    private LeadFollowupLogService leadFollowupLogService;

    /** The timer config. */
    @Mock
    private TimerConfig timerConfig;

    /** The runtime service. */
    @Mock
    protected RuntimeService runtimeService;

    /** The lead service. */
    @Mock
    private LeadService leadService;

    /** The time zone service. */
    @Mock
    private StateTimeZoneService stateTimeZoneService;

    /** The marketing email notification builder. */
    @Mock
    private LeadFollowupNotificationBuilder leadFollowupNotificationBuilder;

    /** The mail service. */
    @Mock
    private MailService mailService;

    @Mock
    private NoteService noteService;

    /**
     * Gets the lead with invalid source.
     *
     * @return the lead with invalid source
     */
    @DataProvider( name = "getLeadWithInvalidSource" )
    public Object[][] getLeadWithInvalidSource() {
        final LeadSource leadSource = new LeadSource();
        leadSource.setSource( "Invalid source" );
        leadSource.setDoNotEmail( false );
        final StateTimeZone stz = new StateTimeZone();
        stz.setOffSetHour( 5 );
        return new Object[][] { { leadSource, stz } };
    }

    /**
     * Gets the lead with do not email.
     *
     * @return the lead with do not email
     */
    @DataProvider( name = "getLeadWithDoNotEmail" )
    public Object[][] getLeadWithDoNotEmail() {
        final LeadSource leadSource = new LeadSource();
        leadSource.setSource( "Owners.com" );
        leadSource.setDoNotEmail( true );
        final StateTimeZone stz = new StateTimeZone();
        stz.setOffSetHour( 5 );
        return new Object[][] { { leadSource, stz } };
    }

    /**
     * Test start marketing email process.
     *
     * @param leadSource
     *            the lead source
     * @param stz
     *            the stz
     */
    @Test( dataProvider = "getLeadSourceAndStateTimeZone" )
    public void testStartMarketingEmailProcess( final LeadSource leadSource, final StateTimeZone stz,
            final NotificationPreferenceResponse response ) {
        ReflectionTestUtils.setField( leadFollowupBusinessServiceImpl, "leadSources",
                Arrays.asList( new String[] { "owners.com" } ) );
        ReflectionTestUtils.setField( leadFollowupBusinessServiceImpl, "leadSourceString",
                "Owners.com,Unbounce Landing Page" );

        Mockito.when( leadFollowupLogService.getMarketingEmailLogBuyLeadId( Mockito.anyString() ) ).thenReturn( null );
        Mockito.when( leadFollowupLogService.saveMarketingEmailLog( Mockito.any( MarketingEmailLog.class ) ) )
                .thenReturn( new MarketingEmailLog() );
        Mockito.when( runtimeService.startProcessInstanceByKey( Mockito.anyString(), Mockito.anyMap() ) )
                .thenReturn( new ExecutionEntityImpl() );
        Mockito.when( stateTimeZoneService.getStateTimeZone( Mockito.anyString() ) ).thenReturn( stz );
        when( mailService.getNotificationPreferenceForUser( leadSource.getEmail() ) ).thenReturn( response );
        leadFollowupBusinessServiceImpl.startLeadFollowupEmailProcess( leadSource );
        Mockito.verify( runtimeService ).startProcessInstanceByKey( Mockito.anyString(), Mockito.anyMap() );
    }

    /**
     * Test start marketing email process.
     *
     * @param leadSource
     *            the lead source
     * @param stz
     *            the stz
     */
    @Test( dataProvider = "getLeadSourceAndStateTimeZone" )
    public void shouldStartMarketingEmailProcessWithoutTimezone( final LeadSource leadSource, final StateTimeZone stz,
            final NotificationPreferenceResponse response ) {
        ReflectionTestUtils.setField( leadFollowupBusinessServiceImpl, "leadSources",
                Arrays.asList( new String[] { "owners.com" } ) );
        ReflectionTestUtils.setField( leadFollowupBusinessServiceImpl, "leadSourceString",
                "Owners.com,Unbounce Landing Page" );

        Mockito.when( leadFollowupLogService.getMarketingEmailLogBuyLeadId( Mockito.anyString() ) ).thenReturn( null );
        Mockito.when( leadFollowupLogService.saveMarketingEmailLog( Mockito.any( MarketingEmailLog.class ) ) )
                .thenReturn( new MarketingEmailLog() );
        Mockito.when( runtimeService.startProcessInstanceByKey( Mockito.anyString(), Mockito.anyMap() ) )
                .thenReturn( new ExecutionEntityImpl() );
        Mockito.when( stateTimeZoneService.getStateTimeZone( Mockito.anyString() ) ).thenReturn( null );
        when( mailService.getNotificationPreferenceForUser( leadSource.getEmail() ) ).thenReturn( response );
        leadFollowupBusinessServiceImpl.startLeadFollowupEmailProcess( leadSource );
        Mockito.verify( runtimeService ).startProcessInstanceByKey( Mockito.anyString(), Mockito.anyMap() );
    }

    /**
     * Test start marketing email process.
     *
     * @param leadSource
     *            the lead source
     * @param stz
     *            the stz
     */
    @Test( dataProvider = "getLeadSourceAndStateTimeZone" )
    public void shouldStartMarketingEmailProcessWithoutTimezoneOffset( final LeadSource leadSource, final StateTimeZone stz,
            final NotificationPreferenceResponse response ) {
        ReflectionTestUtils.setField( leadFollowupBusinessServiceImpl, "leadSources",
                Arrays.asList( new String[] { "owners.com" } ) );
        ReflectionTestUtils.setField( leadFollowupBusinessServiceImpl, "leadSourceString",
                "Owners.com,Unbounce Landing Page" );

        Mockito.when( leadFollowupLogService.getMarketingEmailLogBuyLeadId( Mockito.anyString() ) ).thenReturn( null );
        Mockito.when( leadFollowupLogService.saveMarketingEmailLog( Mockito.any( MarketingEmailLog.class ) ) )
                .thenReturn( new MarketingEmailLog() );
        Mockito.when( runtimeService.startProcessInstanceByKey( Mockito.anyString(), Mockito.anyMap() ) )
                .thenReturn( new ExecutionEntityImpl() );
        Mockito.when( stateTimeZoneService.getStateTimeZone( Mockito.anyString() ) ).thenReturn( null );
        Mockito.when( stateTimeZoneService.getStateTimeZone( Mockito.anyString() ) ).thenReturn( new StateTimeZone() );
        when( mailService.getNotificationPreferenceForUser( leadSource.getEmail() ) ).thenReturn( response );
        leadFollowupBusinessServiceImpl.startLeadFollowupEmailProcess( leadSource );
        Mockito.verify( runtimeService ).startProcessInstanceByKey( Mockito.anyString(), Mockito.anyMap() );
    }

    /**
     *
     * @param leadSource
     *            the lead source
     * @param stz
     *            the stz
     */
    @Test( dataProvider = "getLeadWithInvalidSource" )
    public void shouldNotStartEmailProcessForInvalidSource( final LeadSource leadSource, final StateTimeZone stz ) {
        ReflectionTestUtils.setField( leadFollowupBusinessServiceImpl, "leadSources",
                Arrays.asList( new String[] { "owners.com" } ) );
        Mockito.when( leadFollowupLogService.getMarketingEmailLogBuyLeadId( anyString() ) ).thenReturn( null );
        Mockito.when( leadFollowupLogService.saveMarketingEmailLog( Mockito.any( MarketingEmailLog.class ) ) )
                .thenReturn( new MarketingEmailLog() );
        Mockito.when( runtimeService.startProcessInstanceByKey( Mockito.anyString(), Mockito.anyMap() ) )
                .thenReturn( new ExecutionEntityImpl() );
        Mockito.when( stateTimeZoneService.getStateTimeZone( Mockito.anyString() ) ).thenReturn( stz );
        leadFollowupBusinessServiceImpl.startLeadFollowupEmailProcess( leadSource );
        verifyZeroInteractions( runtimeService );
    }

    /**
     *
     * @param leadSource
     *            the lead source
     * @param stz
     *            the stz
     */
    @Test( dataProvider = "getLeadWithDoNotEmail" )
    public void shouldNotStartEmailProcessIfUserHasOptedForDndAtCrm( final LeadSource leadSource, final StateTimeZone stz ) {
        ReflectionTestUtils.setField( leadFollowupBusinessServiceImpl, "leadSources",
                Arrays.asList( new String[] { "owners.com" } ) );
        Mockito.when( leadFollowupLogService.getMarketingEmailLogBuyLeadId( anyString() ) ).thenReturn( null );
        Mockito.when( leadFollowupLogService.saveMarketingEmailLog( Mockito.any( MarketingEmailLog.class ) ) )
                .thenReturn( new MarketingEmailLog() );
        Mockito.when( runtimeService.startProcessInstanceByKey( Mockito.anyString(), Mockito.anyMap() ) )
                .thenReturn( new ExecutionEntityImpl() );
        Mockito.when( stateTimeZoneService.getStateTimeZone( Mockito.anyString() ) ).thenReturn( stz );
        leadFollowupBusinessServiceImpl.startLeadFollowupEmailProcess( leadSource );
        verifyZeroInteractions( runtimeService );
    }

    /**
     *
     * @param leadSource
     *            the lead source
     * @param stz
     *            the stz
     */
    @Test( dataProvider = "getLeadSourceAndStateTimeZone" )
    public void shouldNotStartEmailProcessIfUserHasOptedForDndAtOwners( final LeadSource leadSource, final StateTimeZone stz,
            final NotificationPreferenceResponse response ) {
        ReflectionTestUtils.setField( leadFollowupBusinessServiceImpl, "leadSources",
                Arrays.asList( new String[] { "owners.com" } ) );
        Mockito.when( leadFollowupLogService.getMarketingEmailLogBuyLeadId( anyString() ) ).thenReturn( null );
        Mockito.when( leadFollowupLogService.saveMarketingEmailLog( Mockito.any( MarketingEmailLog.class ) ) )
                .thenReturn( new MarketingEmailLog() );
        Mockito.when( runtimeService.startProcessInstanceByKey( Mockito.anyString(), Mockito.anyMap() ) )
                .thenReturn( new ExecutionEntityImpl() );
        Mockito.when( stateTimeZoneService.getStateTimeZone( Mockito.anyString() ) ).thenReturn( stz );
        response.getPreferences().iterator().next().setValue( false );
        when( mailService.getNotificationPreferenceForUser( leadSource.getEmail() ) ).thenReturn( response );
        leadFollowupBusinessServiceImpl.startLeadFollowupEmailProcess( leadSource );
        verifyZeroInteractions( runtimeService );
    }

    /**
     *
     * @param leadSource
     *            the lead source
     * @param stz
     *            the stz
     */
    @Test( dataProvider = "getLeadSourceAndStateTimeZone" )
    public void shouldNotStartEmailProcessIfAlreadyStarted( final LeadSource leadSource, final StateTimeZone stz,
            final NotificationPreferenceResponse response ) {
        ReflectionTestUtils.setField( leadFollowupBusinessServiceImpl, "leadSources",
                Arrays.asList( new String[] { "owners.com" } ) );
        Mockito.when( leadFollowupLogService.getMarketingEmailLogBuyLeadId( anyString() ) )
                .thenReturn( new MarketingEmailLog() );
        when( mailService.getNotificationPreferenceForUser( leadSource.getEmail() ) ).thenReturn( response );
        leadFollowupBusinessServiceImpl.startLeadFollowupEmailProcess( leadSource );
        verifyZeroInteractions( runtimeService );
    }

    /**
     * Gets the lead source and state time zone.
     *
     * @return the lead source and state time zone
     */
    @DataProvider( name = "getLeadSourceAndStateTimeZone" )
    public Object[][] getLeadSourceAndStateTimeZone() {
        final LeadSource leadSource = new LeadSource();
        leadSource.setId( "crmLeadId" );
        leadSource.setOwnerId( "ownerId" );
        leadSource.setEmail( "email@email.com" );
        leadSource.setRecordType( RecordType.BUYER.getType() );
        leadSource.setSource( "Owners.com" );
        leadSource.setLeadStatus( LeadStatus.OUTBOUND_ATTEMPT.getStatus() );
        leadSource.setState( "AK" );
        leadSource.setFirstName( "name name" );
        leadSource.setOwnersVisitorId( "ownersVisitorId" );

        final StateTimeZone stz = new StateTimeZone();
        stz.setOffSetHour( 5 );

        final List< Preference > preferences = new ArrayList< Preference >();

        final Preference preference = new Preference();
        preference.setType( NotificationType.MARKETING.name() );
        preferences.add( preference );
        preference.setValue( true );

        final NotificationPreferenceResponse response = new NotificationPreferenceResponse();
        response.setPreferences( preferences );

        return new Object[][] { { leadSource, stz, response } };
    }

    /**
     * Test start marketing email process false cases.
     *
     * @param leadSource
     *            the lead source
     * @param stz
     *            the stz
     */
    @Test( dataProvider = "getprocessDetaislfalseCase" )
    public void testStartMarketingEmailProcessFalseCases( final LeadSource leadSource, final StateTimeZone stz ) {
        ReflectionTestUtils.setField( leadFollowupBusinessServiceImpl, "leadSourceString",
                "Owners.com,Unbounce Landing Page" );
        ReflectionTestUtils.invokeMethod( leadFollowupBusinessServiceImpl, "initLeadSources" );
        Mockito.when( leadFollowupLogService.getMarketingEmailLogBuyLeadId( Mockito.anyString() ) )
                .thenReturn( new MarketingEmailLog() );
        Mockito.when( leadFollowupLogService.saveMarketingEmailLog( Mockito.any( MarketingEmailLog.class ) ) )
                .thenReturn( new MarketingEmailLog() );
        Mockito.when( runtimeService.startProcessInstanceByKey( Mockito.anyString(), Mockito.anyMap() ) )
                .thenReturn( new ExecutionEntityImpl() );
        Mockito.when( stateTimeZoneService.getStateTimeZone( Mockito.anyString() ) ).thenReturn( stz );

        leadFollowupBusinessServiceImpl.startLeadFollowupEmailProcess( leadSource );
        Mockito.verifyZeroInteractions( runtimeService );
    }

    /**
     * Test send marketing emails.
     *
     * @param templateName
     *            the template name
     */

    @Test( dataProvider = "getTemplateName" )
    public void testSendMarketingEmails( final String templateName ) {
        final LeadSource leadSource = new LeadSource();
        final EmailNotification en = new EmailNotification();
        final Email email = new Email();
        email.setParameterMap( new HashMap<>() );
        en.setEmail( email );

        final String executionId = "executionId";
        Mockito.when( runtimeService.getVariable( executionId, "EMAIL_TEMPLATE" ) ).thenReturn( templateName );
        Mockito.when( leadFollowupNotificationBuilder.convertTo( Mockito.any( LeadSource.class ) ) ).thenReturn( en );
        Mockito.doNothing().when( noteService ).saveNote( any( CrmNote.class ) );
        final NotificationResponse response = new NotificationResponse();
        response.setStatus( "success" );
        when( mailService.send( any( EmailNotification.class ) ) ).thenReturn( response );

        leadFollowupBusinessServiceImpl.sendLeadFollowupEmails( executionId, leadSource );
        Mockito.verify( mailService ).send( Mockito.any( EmailNotification.class ) );
    }

    /**
     * Should send email if notification response is failed.
     *
     * @param templateName
     *            the template name
     */
    @Test( dataProvider = "getTemplateName" )
    public void shouldSendEmailIfNotificationResponseIsFailed( final String templateName ) {
        final LeadSource leadSource = new LeadSource();
        final EmailNotification en = new EmailNotification();
        final Email email = new Email();
        email.setParameterMap( new HashMap<>() );
        en.setEmail( email );

        final String executionId = "executionId";
        Mockito.when( runtimeService.getVariable( executionId, "EMAIL_TEMPLATE" ) ).thenReturn( templateName );
        Mockito.when( leadFollowupNotificationBuilder.convertTo( Mockito.any( LeadSource.class ) ) ).thenReturn( en );
        Mockito.doNothing().when( noteService ).saveNote( any( CrmNote.class ) );
        final NotificationResponse response = new NotificationResponse();
        response.setStatus( "failed" );
        when( mailService.send( any( EmailNotification.class ) ) ).thenReturn( response );

        leadFollowupBusinessServiceImpl.sendLeadFollowupEmails( executionId, leadSource );
        Mockito.verify( mailService ).send( Mockito.any( EmailNotification.class ) );
    }

    @Test
    public void testCleanMarketingEmailLogForLostLead() {
        Mockito.when( leadFollowupLogService.getMarketingEmailLogBuyLeadId( Mockito.anyString() ) )
                .thenReturn( new MarketingEmailLog() );
        Mockito.doNothing().when( leadFollowupLogService )
                .deleteMarketingEmailLog( Mockito.any( MarketingEmailLog.class ) );
        leadFollowupBusinessServiceImpl.cleanLeadFollowupLog( "leadId" );
        Mockito.verify( leadFollowupLogService ).deleteMarketingEmailLog( Mockito.any( MarketingEmailLog.class ) );
        Mockito.verify( leadFollowupLogService ).getMarketingEmailLogBuyLeadId( Mockito.anyString() );
    }

    /**
     * Test clean marketing email log.
     */
    @Test
    public void testCleanMarketingEmailLog() {
        Mockito.when( leadFollowupLogService.getMarketingEmailLogBuyLeadId( Mockito.anyString() ) )
                .thenReturn( new MarketingEmailLog() );
        Mockito.doNothing().when( leadFollowupLogService )
                .deleteMarketingEmailLog( Mockito.any( MarketingEmailLog.class ) );
        leadFollowupBusinessServiceImpl.cleanLeadFollowupLog( "leadId" );
        Mockito.verify( leadFollowupLogService ).deleteMarketingEmailLog( Mockito.any( MarketingEmailLog.class ) );
        Mockito.verify( leadFollowupLogService ).getMarketingEmailLogBuyLeadId( Mockito.anyString() );
    }

    /**
     * Gets the template name.
     *
     * @return the template name
     */
    @DataProvider( name = "getTemplateName" )
    public Object[][] getTemplateName() {
        return new Object[][] { { "MARKETING_EMAIL_INTRODUCTION" }, { "MARKETING_EMAIL_START_YOUR_SEARCH" },
                { "MARKETING_EMAIL_REBATE" }, { "MARKETING_EMAIL_SCHEDULE_TOUR" },
                { "MARKETING_EMAIL_MAKE_YOUR_AGENT" }, { "MARKETING_EMAIL_FINAL_OUTREACH" }, { "" }, { null } };
    }

    /**
     * Gets the process detaislfalse case.
     *
     * @return the process detaislfalse case
     */
    @DataProvider( name = "getprocessDetaislfalseCase" )
    public Object[][] getprocessDetaislfalseCase() {
        final LeadSource leadSource = new LeadSource();
        leadSource.setId( "crmLeadId" );
        leadSource.setOwnerId( "ownerId" );
        leadSource.setEmail( "email@email.com" );
        leadSource.setRecordType( RecordType.BUYER.getType() );
        leadSource.setSource( "Owners.com" );
        leadSource.setLeadStatus( LeadStatus.OUTBOUND_ATTEMPT.getStatus() );
        leadSource.setState( "AK" );
        leadSource.setFirstName( "name name" );
        leadSource.setOwnersVisitorId( "ownersVisitorId" );

        final LeadSource leadSource2 = leadSource;
        leadSource2.setRecordType( RecordType.BOTH.getType() );

        final LeadSource leadSource3 = leadSource;
        leadSource3.setSource( "Unbounce Landing Page" );

        final StateTimeZone stz = new StateTimeZone();
        stz.setOffSetHour( 5 );
        return new Object[][] { { leadSource2, stz }, { leadSource3, stz } };
    }
}
