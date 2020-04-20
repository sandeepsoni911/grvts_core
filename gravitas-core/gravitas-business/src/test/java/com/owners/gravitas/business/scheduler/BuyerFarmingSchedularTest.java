package com.owners.gravitas.business.scheduler;

import static com.owners.gravitas.constants.Constants.LEAD;
import static com.owners.gravitas.enums.BuyerFarmType.LONG_TERM_BUYER;
import static com.owners.gravitas.enums.GravitasProcess.LEAD_MANAGEMENT_PROCESS;
import static com.owners.gravitas.enums.LeadStatus.OUTBOUND_ATTEMPT;
import static java.lang.Boolean.FALSE;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.business.BuyerFarmingBusinessService;
import com.owners.gravitas.business.ProcessBusinessService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.BuyerFarmingConfig;
import com.owners.gravitas.config.SchedulerJobsJmxConfig;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.ObjectType;
import com.owners.gravitas.domain.entity.Process;
import com.owners.gravitas.lock.AgentNewOppSchedulerLockHandler;
import com.owners.gravitas.service.ContactEntityService;
import com.owners.gravitas.service.ObjectTypeService;

/**
 * The Class BuyerFarmingSchedularTest.
 *
 * @author raviz
 */
public class BuyerFarmingSchedularTest extends AbstractBaseMockitoTest {

    /** The buyer farming schedular. */
    @InjectMocks
    private BuyerFarmingSchedular buyerFarmingSchedular;

    /** The contact service V 1. */
    @Mock
    private ContactEntityService contactServiceV1;

    /** The object type service. */
    @Mock
    private ObjectTypeService objectTypeService;

    /** The process management business service. */
    @Mock
    private ProcessBusinessService processBusinessService;

    /** The buyer registration business service. */
    @Mock
    private BuyerFarmingBusinessService buyerFarmingBusinessService;

    /** The buyer farming config. */
    @Mock
    private BuyerFarmingConfig buyerFarmingConfig;

    /** The scheduler jobs jmx config. */
    @Mock
    private SchedulerJobsJmxConfig schedulerJobsJmxConfig;

    /** The lock handler. */
    @Mock
    private AgentNewOppSchedulerLockHandler lockHandler;

    /**
     * Test check for outbound leads should start farming process.
     */
    @Test
    public void testCheckForOutboundLeadsShouldStartFarmingProcess() {
        final ObjectType objectType = new ObjectType();
        final String email = "test@test.com";
        final Process process = new Process();
        final Contact contact = new Contact();
        final Integer gracePeriod = 3;
        contact.setCreatedDate( new DateTime().minusDays( 5 ) );
        contact.setEmail( email );
        final List< Contact > contacts = new ArrayList<>();
        contacts.add( contact );

        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( schedulerJobsJmxConfig.isEnableOutboundAttemptCheckSchedular() ).thenReturn( true );
        when( lockHandler.acquireLock( anyString() ) ).thenReturn( true );
        when( buyerFarmingConfig.isBuyerFarmingEnabled() ).thenReturn( true );
        when( objectTypeService.findByName( LEAD ) ).thenReturn( objectType );
        when( contactServiceV1.getOutboundAttemptContacts( OUTBOUND_ATTEMPT.getStatus(), LEAD ) )
                .thenReturn( contacts );
        when( buyerFarmingConfig.getLongTermLeadGracePeriod() ).thenReturn( gracePeriod );
        when( processBusinessService.getProcess( email, LEAD_MANAGEMENT_PROCESS, "active" ) ).thenReturn( process );

        buyerFarmingSchedular.checkForOutboundLeads();

        verify( contactServiceV1 ).getOutboundAttemptContacts( OUTBOUND_ATTEMPT.getStatus(), LEAD );
        verify( buyerFarmingConfig ).getLongTermLeadGracePeriod();
        verify( processBusinessService ).getProcess( email, LEAD_MANAGEMENT_PROCESS, "active" );
        verify( processBusinessService ).deActivateAndSignal( anyString(), any(), anyMap() );
        verify( buyerFarmingBusinessService ).updateFarmingStatus( contact.getCrmId(), LONG_TERM_BUYER, FALSE );
    }

    /**
     * Test check for outbound leads should not start farming process when
     * process is not halted.
     */
    @Test
    public void testCheckForOutboundLeadsShouldNotStartFarmingProcessWhenProcessIsNotHalted() {
        final ObjectType objectType = new ObjectType();
        final String email = "test@test.com";
        final Contact contact = new Contact();
        final Integer gracePeriod = 3;
        contact.setCreatedDate( new DateTime().minusDays( 5 ) );
        contact.setEmail( email );
        final List< Contact > contacts = new ArrayList<>();
        contacts.add( contact );

        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( schedulerJobsJmxConfig.isEnableOutboundAttemptCheckSchedular() ).thenReturn( true );
        when( lockHandler.acquireLock( anyString() ) ).thenReturn( true );
        when( buyerFarmingConfig.isBuyerFarmingEnabled() ).thenReturn( true );
        when( objectTypeService.findByName( LEAD ) ).thenReturn( objectType );
        when( contactServiceV1.getOutboundAttemptContacts( OUTBOUND_ATTEMPT.getStatus(), LEAD ) )
                .thenReturn( contacts );
        when( buyerFarmingConfig.getLongTermLeadGracePeriod() ).thenReturn( gracePeriod );
        when( processBusinessService.getProcess( email, LEAD_MANAGEMENT_PROCESS, "active" ) ).thenReturn( null );

        buyerFarmingSchedular.checkForOutboundLeads();

        verify( contactServiceV1 ).getOutboundAttemptContacts( OUTBOUND_ATTEMPT.getStatus(), LEAD );
        verify( buyerFarmingConfig ).getLongTermLeadGracePeriod();
        verify( processBusinessService ).getProcess( email, LEAD_MANAGEMENT_PROCESS, "active" );
        verify( processBusinessService, times( 0 ) ).deActivateAndSignal( anyString(), any(), anyMap() );
        verifyZeroInteractions( buyerFarmingBusinessService );
    }

    /**
     * Test check for outbound leads should not start farming process when grace
     * period is short.
     */
    @Test
    public void testCheckForOutboundLeadsShouldNotStartFarmingProcessWhenGracePeriodIsShort() {
        final ObjectType objectType = new ObjectType();
        final String email = "test@test.com";
        final Contact contact = new Contact();
        final Integer gracePeriod = 3;
        contact.setCreatedDate( new DateTime().minusDays( 1 ) );
        contact.setEmail( email );
        final List< Contact > contacts = new ArrayList<>();
        contacts.add( contact );

        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( schedulerJobsJmxConfig.isEnableOutboundAttemptCheckSchedular() ).thenReturn( true );
        when( lockHandler.acquireLock( anyString() ) ).thenReturn( true );
        when( buyerFarmingConfig.isBuyerFarmingEnabled() ).thenReturn( true );
        when( objectTypeService.findByName( LEAD ) ).thenReturn( objectType );
        when( contactServiceV1.getOutboundAttemptContacts( OUTBOUND_ATTEMPT.getStatus(), LEAD ) )
                .thenReturn( contacts );
        when( buyerFarmingConfig.getLongTermLeadGracePeriod() ).thenReturn( gracePeriod );

        buyerFarmingSchedular.checkForOutboundLeads();

        verify( contactServiceV1 ).getOutboundAttemptContacts( OUTBOUND_ATTEMPT.getStatus(), LEAD );
        verify( buyerFarmingConfig ).getLongTermLeadGracePeriod();
        verifyZeroInteractions( processBusinessService );
        verifyZeroInteractions( buyerFarmingBusinessService );
    }

    /**
     * Test check for outbound leads should not start farming process when
     * created date is not available.
     */
    @Test
    public void testCheckForOutboundLeadsShouldNotStartFarmingProcessWhenCreatedDateIsNotAvailable() {
        final ObjectType objectType = new ObjectType();
        final String email = "test@test.com";
        final Contact contact = new Contact();
        contact.setEmail( email );
        final List< Contact > contacts = new ArrayList<>();
        contacts.add( contact );

        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( schedulerJobsJmxConfig.isEnableOutboundAttemptCheckSchedular() ).thenReturn( true );
        when( lockHandler.acquireLock( anyString() ) ).thenReturn( true );
        when( buyerFarmingConfig.isBuyerFarmingEnabled() ).thenReturn( false );
        when( objectTypeService.findByName( LEAD ) ).thenReturn( objectType );
        when( contactServiceV1.getOutboundAttemptContacts( OUTBOUND_ATTEMPT.getStatus(), LEAD ) )
                .thenReturn( contacts );

        buyerFarmingSchedular.checkForOutboundLeads();

        verifyZeroInteractions( contactServiceV1 );
        verify( buyerFarmingConfig ).isBuyerFarmingEnabled();
        verifyZeroInteractions( processBusinessService );
        verifyZeroInteractions( buyerFarmingBusinessService );
    }

    /**
     * Test check for outbound leads should not start farming process when
     * excpetion is thrown.
     */
    @Test
    public void testCheckForOutboundLeadsShouldNotStartFarmingProcessWhenExcpetionIsThrown() {
        final ObjectType objectType = new ObjectType();
        final String email = "test@test.com";
        final Process process = new Process();
        final Contact contact = new Contact();
        final Integer gracePeriod = 3;
        contact.setCreatedDate( new DateTime().minusDays( 5 ) );
        contact.setEmail( email );
        final List< Contact > contacts = new ArrayList<>();
        contacts.add( contact );

        when( schedulerJobsJmxConfig.isEnableAllJobs() ).thenReturn( true );
        when( schedulerJobsJmxConfig.isEnableOutboundAttemptCheckSchedular() ).thenReturn( true );
        when( lockHandler.acquireLock( anyString() ) ).thenReturn( true );
        when( buyerFarmingConfig.isBuyerFarmingEnabled() ).thenReturn( true );
        when( objectTypeService.findByName( LEAD ) ).thenReturn( objectType );
        when( contactServiceV1.getOutboundAttemptContacts( OUTBOUND_ATTEMPT.getStatus(), LEAD ) )
                .thenReturn( contacts );
        when( buyerFarmingConfig.getLongTermLeadGracePeriod() ).thenReturn( gracePeriod );
        when( processBusinessService.getProcess( email, LEAD_MANAGEMENT_PROCESS, "active" ) ).thenReturn( process );
        doThrow( Exception.class ).when( buyerFarmingBusinessService ).updateFarmingStatus( contact.getCrmId(),
                LONG_TERM_BUYER, FALSE );

        buyerFarmingSchedular.checkForOutboundLeads();

        verify( contactServiceV1 ).getOutboundAttemptContacts( OUTBOUND_ATTEMPT.getStatus(), LEAD );
        verify( buyerFarmingConfig ).getLongTermLeadGracePeriod();
        verify( processBusinessService ).getProcess( email, LEAD_MANAGEMENT_PROCESS, "active" );
        verify( processBusinessService ).deActivateAndSignal( anyString(), any(), anyMap() );
        verify( buyerFarmingBusinessService ).updateFarmingStatus( contact.getCrmId(), LONG_TERM_BUYER, FALSE );
    }

}
