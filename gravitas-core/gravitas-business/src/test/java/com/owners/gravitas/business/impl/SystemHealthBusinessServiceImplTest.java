/*
 *
 */
package com.owners.gravitas.business.impl;

import static com.owners.gravitas.enums.CRMObject.OPPORTUNITY;
import static com.owners.gravitas.enums.GravitasSystemType.FIREBASE;
import static com.owners.gravitas.enums.RecordType.BUYER;
import static com.owners.gravitas.enums.TopicName.AGENT;
import static com.owners.gravitas.enums.TopicName.CONTACT;
import static com.owners.gravitas.enums.TopicName.LEAD;
import static com.owners.gravitas.enums.TopicName.LEAD_CREATE;
import static com.owners.gravitas.enums.TopicName.OPPORTUNITY_CHANGE;
import static com.owners.gravitas.enums.TopicName.OPPORTUNITY_CREATE;
import static com.owners.gravitas.enums.TopicName.values;
import static com.owners.gravitas.enums.TopicStatusType.active;
import static com.owners.gravitas.enums.TopicStatusType.inprogress;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.amqp.OpportunityContact;
import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.GravitasNotificationBusinessService;
import com.owners.gravitas.business.LeadBusinessService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.SystemHealthJmxConfig;
import com.owners.gravitas.domain.entity.SchedulerLog;
import com.owners.gravitas.domain.entity.SystemErrorLog;
import com.owners.gravitas.domain.entity.TopicStatus;
import com.owners.gravitas.dto.GravitasHealthStatus;
import com.owners.gravitas.enums.TopicName;
import com.owners.gravitas.event.FailedTopicEvent;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.exception.ResultNotFoundException;
import com.owners.gravitas.listener.AmqpCompositeConnectionListener;
import com.owners.gravitas.service.AccountService;
import com.owners.gravitas.service.CRMAuthenticatorService;
import com.owners.gravitas.service.ContactService;
import com.owners.gravitas.service.OpportunityService;
import com.owners.gravitas.service.RecordTypeService;
import com.owners.gravitas.service.SystemErrorLogService;
import com.owners.gravitas.service.SystemHealthService;
import com.owners.gravitas.service.TopicStatusService;

/**
 * The Class SystemHealthBusinessServiceImplTest.
 *
 * @author ankusht
 */
public class SystemHealthBusinessServiceImplTest extends AbstractBaseMockitoTest {

    /** The crm authenticator. */
    @Mock
    @Qualifier( "crmRestAuthenticator" )
    private CRMAuthenticatorService crmAuthenticatorService;

    /** The system error log service. */
    @Mock
    private SystemErrorLogService systemErrorLogService;

    /** The system health service. */
    @Mock
    private SystemHealthService systemHealthService;

    /** The opportunity service. */
    @Mock
    private OpportunityService opportunityService;

    /** The amqp composite connection listener. */
    @Mock
    private AmqpCompositeConnectionListener amqpCompositeConnectionListener;

    /** The business service. */
    @Mock
    private LeadBusinessService leadBusinessService;

    /** The record type service. */
    @Mock
    private RecordTypeService recordTypeService;

    /** The topic status service. */
    @Mock
    private TopicStatusService topicStatusService;

    /** The account service. */
    @Mock
    private AccountService accountService;

    /** The contact service. */
    @Mock
    private ContactService contactService;

    /** The gravitas notification business service. */
    @Mock
    private GravitasNotificationBusinessService gravitasNotificationBusinessService;

    /** The system health jmx config. */
    @Mock
    private SystemHealthJmxConfig systemHealthJmxConfig;

    /** The health business service impl. */
    @InjectMocks
    private SystemHealthBusinessServiceImpl systemHealthBusinessServiceImpl;

    /** The application event publisher. */
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * Test check system health when all systems are up and topic statuses not
     * present in db and default opportunity is present.
     */
    @Test
    public void testCheckSystemHealth_WhenAllSystemsAreUp_AndTopicStatusesNotPresentInDb_AndDefaultOpportunityIsPresent() {
        final SchedulerLog schedulerLog = new SchedulerLog();
        final String oppRecordTypeId = "oppRecordTypeId";
        final String opportunityId = "oppId";
        final String email = "a@a.com";

        when( amqpCompositeConnectionListener.isBrokerReachable() ).thenReturn( true );
        when( topicStatusService.findTopicNames() ).thenReturn( null );
        when( recordTypeService.getRecordTypeIdByName( BUYER.name(), OPPORTUNITY.name() ) )
                .thenReturn( oppRecordTypeId );
        when( opportunityService.getOpportunityIdByRecordTypeIdAndEmail( oppRecordTypeId, email ) )
                .thenReturn( opportunityId );
        when( systemHealthJmxConfig.getSystemHealthOpportunityEmail() ).thenReturn( email );

        systemHealthBusinessServiceImpl.checkSystemHealth( schedulerLog );

        verifyZeroInteractions( systemErrorLogService );
        verify( topicStatusService ).findTopicNames();
        verify( topicStatusService, times( values().length ) ).save( any( TopicStatus.class ) );
        verify( systemHealthService ).updateCrmOpportunity( opportunityId );
        verify( systemHealthService ).connectToRefDataNode();
        verify( crmAuthenticatorService ).authenticate();
        verify( amqpCompositeConnectionListener ).isBrokerReachable();
        verify( systemHealthService ).executeQueryOnGravitasDB();
        verify( systemHealthService ).createCrmAgent();
        verify( systemHealthService ).createCrmLead( anyString() );
        verify( recordTypeService ).getRecordTypeIdByName( BUYER.name(), OPPORTUNITY.name() );
        verify( opportunityService ).getOpportunityIdByRecordTypeIdAndEmail( oppRecordTypeId, email );
    }

    /**
     * Test check system health when all systems are up and topic statuses not
     * present in db 2 and default opportunity is present.
     */
    @Test
    public void testCheckSystemHealth_WhenAllSystemsAreUp_AndTopicStatusesNotPresentInDb2_AndDefaultOpportunityIsPresent() {
        final SchedulerLog schedulerLog = new SchedulerLog();
        final String oppRecordTypeId = "oppRecordTypeId";
        final String opportunityId = "oppId";
        final Set< String > topicNames = new HashSet<>();
        topicNames.add( "aa" );
        final String email = "a@a.com";

        when( amqpCompositeConnectionListener.isBrokerReachable() ).thenReturn( true );
        when( topicStatusService.findTopicNames() ).thenReturn( topicNames );
        when( recordTypeService.getRecordTypeIdByName( BUYER.name(), OPPORTUNITY.name() ) )
                .thenReturn( oppRecordTypeId );
        when( opportunityService.getOpportunityIdByRecordTypeIdAndEmail( oppRecordTypeId, email ) )
                .thenReturn( opportunityId );
        when( systemHealthJmxConfig.getSystemHealthOpportunityEmail() ).thenReturn( email );

        systemHealthBusinessServiceImpl.checkSystemHealth( schedulerLog );

        verifyZeroInteractions( systemErrorLogService );
        verify( topicStatusService ).findTopicNames();
        verify( topicStatusService, times( values().length ) ).save( any( TopicStatus.class ) );
        verify( systemHealthService ).updateCrmOpportunity( opportunityId );
        verify( systemHealthService ).connectToRefDataNode();
        verify( crmAuthenticatorService ).authenticate();
        verify( amqpCompositeConnectionListener ).isBrokerReachable();
        verify( systemHealthService ).executeQueryOnGravitasDB();
        verify( systemHealthService ).createCrmAgent();
        verify( systemHealthService ).createCrmLead( anyString() );
        verify( recordTypeService ).getRecordTypeIdByName( BUYER.name(), OPPORTUNITY.name() );
        verify( opportunityService ).getOpportunityIdByRecordTypeIdAndEmail( oppRecordTypeId, email );
    }

    /**
     * Gets the all topic names.
     *
     * @return the all topic names
     */
    @DataProvider( name = "getAllTopicNames" )
    public Object[][] getAllTopicNames() {
        final Set< String > topicNames = new HashSet<>();
        for ( final TopicName topicName : TopicName.values() ) {
            topicNames.add( topicName.getName() );
        }
        return new Object[][] { { topicNames } };
    }

    /**
     * Test check system health when all systems are up and topic statuses are
     * present in db and default opportunity is absent.
     *
     * @param topicNames
     *            the topic names
     */
    @Test( dataProvider = "getAllTopicNames" )
    public void testCheckSystemHealth_WhenAllSystemsAreUp_AndTopicStatusesArePresentInDb_AndDefaultOpportunityIsAbsent(
            final Set< String > topicNames ) {
        final SchedulerLog schedulerLog = new SchedulerLog();
        final String oppRecordTypeId = "oppRecordTypeId";
        final String opportunityId = "oppId";
        final String email = "a@a.com";
        final String leadId = "leadId";

        when( amqpCompositeConnectionListener.isBrokerReachable() ).thenReturn( true );
        when( topicStatusService.findTopicNames() ).thenReturn( topicNames );
        when( recordTypeService.getRecordTypeIdByName( BUYER.name(), OPPORTUNITY.name() ) )
                .thenReturn( oppRecordTypeId );
        when( opportunityService.getOpportunityIdByRecordTypeIdAndEmail( oppRecordTypeId, email ) )
                .thenThrow( ResultNotFoundException.class );
        when( systemHealthService.createDefaultLeadIfNotExist( email ) ).thenReturn( leadId );
        when( leadBusinessService.convertLeadToOpportunity( leadId ) ).thenReturn( opportunityId );
        when( systemHealthJmxConfig.getSystemHealthOpportunityEmail() ).thenReturn( email );

        systemHealthBusinessServiceImpl.checkSystemHealth( schedulerLog );

        verifyZeroInteractions( systemErrorLogService );
        verify( topicStatusService ).findTopicNames();
        verify( topicStatusService, times( values().length ) ).updateTopicStatus( anyString(), anyString() );
        verify( systemHealthService ).updateCrmOpportunity( opportunityId );
        verify( systemHealthService ).connectToRefDataNode();
        verify( crmAuthenticatorService ).authenticate();
        verify( amqpCompositeConnectionListener ).isBrokerReachable();
        verify( systemHealthService ).executeQueryOnGravitasDB();
        verify( systemHealthService ).createCrmAgent();
        verify( systemHealthService ).createCrmLead( anyString() );
        verify( recordTypeService ).getRecordTypeIdByName( BUYER.name(), OPPORTUNITY.name() );
        verify( opportunityService ).getOpportunityIdByRecordTypeIdAndEmail( oppRecordTypeId, email );
        verify( systemHealthService ).createDefaultLeadIfNotExist( email );
        verify( leadBusinessService ).convertLeadToOpportunity( leadId );
    }

    /**
     * Test check system health should throw exception if default opportunity is
     * not created.
     *
     * @param topicNames
     *            the topic names
     */
    @Test( dataProvider = "getAllTopicNames", expectedExceptions = ApplicationException.class )
    public void testCheckSystemHealth_ShouldThrowException_IfDefaultOpportunityIsNotCreated(
            final Set< String > topicNames ) {
        final SchedulerLog schedulerLog = new SchedulerLog();
        final String oppRecordTypeId = "oppRecordTypeId";
        final String opportunityId = "oppId";
        final String email = "a@a.com";
        final String leadId = "";

        when( amqpCompositeConnectionListener.isBrokerReachable() ).thenReturn( true );
        when( topicStatusService.findTopicNames() ).thenReturn( topicNames );
        when( recordTypeService.getRecordTypeIdByName( BUYER.name(), OPPORTUNITY.name() ) )
                .thenReturn( oppRecordTypeId );
        when( opportunityService.getOpportunityIdByRecordTypeIdAndEmail( oppRecordTypeId, email ) )
                .thenThrow( ResultNotFoundException.class );
        when( systemHealthService.createDefaultLeadIfNotExist( email ) ).thenReturn( leadId );
        when( leadBusinessService.convertLeadToOpportunity( leadId ) ).thenReturn( opportunityId );
        when( systemHealthJmxConfig.getSystemHealthOpportunityEmail() ).thenReturn( email );

        systemHealthBusinessServiceImpl.checkSystemHealth( schedulerLog );
    }

    /**
     * Test check system health should throw exception if default lead is not
     * created.
     *
     * @param topicNames
     *            the topic names
     */
    @Test( dataProvider = "getAllTopicNames", expectedExceptions = ApplicationException.class )
    public void testCheckSystemHealth_ShouldThrowException_IfDefaultLeadIsNotCreated( final Set< String > topicNames ) {
        final SchedulerLog schedulerLog = new SchedulerLog();
        final String oppRecordTypeId = "oppRecordTypeId";
        final String opportunityId = "";
        final String email = "a@a.com";
        final String leadId = "leadId";

        when( amqpCompositeConnectionListener.isBrokerReachable() ).thenReturn( true );
        when( topicStatusService.findTopicNames() ).thenReturn( topicNames );
        when( recordTypeService.getRecordTypeIdByName( BUYER.name(), OPPORTUNITY.name() ) )
                .thenReturn( oppRecordTypeId );
        when( opportunityService.getOpportunityIdByRecordTypeIdAndEmail( oppRecordTypeId, email ) )
                .thenThrow( ResultNotFoundException.class );
        when( systemHealthService.createDefaultLeadIfNotExist( email ) ).thenReturn( leadId );
        when( leadBusinessService.convertLeadToOpportunity( leadId ) ).thenReturn( opportunityId );
        when( systemHealthJmxConfig.getSystemHealthOpportunityEmail() ).thenReturn( email );

        systemHealthBusinessServiceImpl.checkSystemHealth( schedulerLog );
    }

    /**
     * Test check system health when all systems are down and topic statuses not
     * present in db and default opportunity is present.
     */
    @Test
    public void testCheckSystemHealth_WhenAllSystemsAreDown_AndTopicStatusesNotPresentInDb_AndDefaultOpportunityIsPresent() {
        final SchedulerLog schedulerLog = new SchedulerLog();
        final String oppRecordTypeId = "oppRecordTypeId";
        final String opportunityId = "oppId";
        final String email = "a@a.com";

        when( systemHealthService.connectToRefDataNode() ).thenThrow( Exception.class );
        when( crmAuthenticatorService.authenticate() ).thenThrow( Exception.class );
        when( amqpCompositeConnectionListener.isBrokerReachable() ).thenReturn( false );
        doThrow( Exception.class ).when( systemHealthService ).executeQueryOnGravitasDB();
        when( topicStatusService.findTopicNames() ).thenReturn( null );
        when( recordTypeService.getRecordTypeIdByName( BUYER.name(), OPPORTUNITY.name() ) )
                .thenReturn( oppRecordTypeId );
        when( opportunityService.getOpportunityIdByRecordTypeIdAndEmail( oppRecordTypeId, email ) )
                .thenReturn( opportunityId );
        when( systemHealthJmxConfig.getSystemHealthOpportunityEmail() ).thenReturn( email );
        when( systemHealthJmxConfig.getRabbitmqDownMsg() ).thenReturn( "RabbitMQ is unreachable" );

        systemHealthBusinessServiceImpl.checkSystemHealth( schedulerLog );

        verify( systemErrorLogService ).saveSystemErrorLogs( anyCollection() );
        verify( topicStatusService ).findTopicNames();
        verify( topicStatusService, times( values().length ) ).save( any( TopicStatus.class ) );
        verify( systemHealthService ).updateCrmOpportunity( opportunityId );
        verify( systemHealthService ).connectToRefDataNode();
        verify( crmAuthenticatorService ).authenticate();
        verify( amqpCompositeConnectionListener ).isBrokerReachable();
        verify( systemHealthService ).executeQueryOnGravitasDB();
        verify( systemHealthService ).createCrmAgent();
        verify( systemHealthService ).createCrmLead( anyString() );
        verify( recordTypeService ).getRecordTypeIdByName( BUYER.name(), OPPORTUNITY.name() );
        verify( opportunityService ).getOpportunityIdByRecordTypeIdAndEmail( oppRecordTypeId, email );
    }

    /**
     * Gets the topic statuses and syst err logs.
     *
     * @return the topic statuses and syst err logs
     */
    @DataProvider( name = "getTopicStatusesAndSystErrLogs" )
    public Object[][] getTopicStatusesAndSystErrLogs() {
        final List< TopicStatus > topicStatuses = new ArrayList<>();
        topicStatuses.add( new TopicStatus( LEAD.getName(), "inprogress" ) );
        final List< SystemErrorLog > systErrLogs = new ArrayList<>();
        final SystemErrorLog log = new SystemErrorLog();
        log.setSystemName( FIREBASE.getType() );
        systErrLogs.add( log );
        return new Object[][] { { topicStatuses, systErrLogs } };
    }

    /**
     * Test get gravitas health status should return health status of all the
     * systems.
     *
     * @param topicStatuses
     *            the topic statuses
     * @param systErrLogs
     *            the syst err logs
     */
    @Test( dataProvider = "getTopicStatusesAndSystErrLogs" )
    public void testGetGravitasHealthStatusShouldReturnHealthStatusOfAllTheSystems(
            final List< TopicStatus > topicStatuses, final List< SystemErrorLog > systErrLogs ) {
        topicStatuses.add( new TopicStatus( OPPORTUNITY_CREATE.getName(), "active" ) );
        when( systemErrorLogService.findLatestSystemErrors() ).thenReturn( systErrLogs );
        when( topicStatusService.findAll() ).thenReturn( topicStatuses );
        when( systemHealthJmxConfig.getRabbitmqDownMsg() ).thenReturn( "RabbitMQ is unreachable" );
        when( systemHealthJmxConfig.getTopicDownMsg() ).thenReturn( "%s got disconnected" );
        final List< GravitasHealthStatus > actual = systemHealthBusinessServiceImpl.getGravitasHealthStatus();
        assertNotNull( actual );
        assertEquals( 5, actual.size() );
        verify( systemErrorLogService ).findLatestSystemErrors();
        verify( topicStatusService ).findAll();
    }

    /**
     * Test get gravitas health status should return health status of all the
     * systems when all topics are working fine.
     *
     * @param topicStatuses
     *            the topic statuses
     * @param systErrLogs
     *            the syst err logs
     */
    @Test( dataProvider = "getTopicStatusesAndSystErrLogs" )
    public void testGetGravitasHealthStatusShouldReturnHealthStatusOfAllTheSystemsWhenAllTopicsAreWorkingFine(
            final List< TopicStatus > topicStatuses, final List< SystemErrorLog > systErrLogs ) {
        topicStatuses.clear();
        when( systemErrorLogService.findLatestSystemErrors() ).thenReturn( systErrLogs );
        when( topicStatusService.findAll() ).thenReturn( topicStatuses );
        final List< GravitasHealthStatus > actual = systemHealthBusinessServiceImpl.getGravitasHealthStatus();
        assertNotNull( actual );
        assertEquals( 5, actual.size() );
        verify( systemErrorLogService ).findLatestSystemErrors();
        verify( topicStatusService ).findAll();
    }

    /**
     * Test listen to lead change topic.
     */
    @Test
    public void testListenToLeadChangeTopic() {
        final LeadSource leadSource = new LeadSource();
        final String opportunityId = "opportunityId";
        when( leadBusinessService.convertLeadToOpportunity( leadSource.getId() ) ).thenReturn( opportunityId );
        systemHealthBusinessServiceImpl.listenToLeadChangeTopic( leadSource );
        verify( topicStatusService ).updateTopicStatus( LEAD.getName(), active.name() );
        verify( leadBusinessService ).convertLeadToOpportunity( leadSource.getId() );
    }

    /**
     * Test listen to opportunity create topic.
     *
     * @param topicStatuses
     *            the topic statuses
     * @param systErrLogs
     *            the syst err logs
     */
    @Test( dataProvider = "getTopicStatusesAndSystErrLogs" )
    public void testListenToOpportunityCreateTopic( final List< TopicStatus > topicStatuses,
            final List< SystemErrorLog > systErrLogs ) {
        final OpportunitySource opportunitySource = new OpportunitySource();
        final String contactId = "contactId";
        final String accountId = "accountId";
        topicStatuses.add( new TopicStatus( OPPORTUNITY_CREATE.getName(), "active" ) );

        when( contactService.findContactIdByOpportunityId( opportunitySource.getCrmId() ) ).thenReturn( contactId );
        when( accountService.findAccountIdByOpportunityId( opportunitySource.getCrmId() ) ).thenReturn( accountId );
        when( systemErrorLogService.findLatestSystemErrors() ).thenReturn( systErrLogs );
        when( topicStatusService.findAll() ).thenReturn( topicStatuses );
        when( systemHealthJmxConfig.getRabbitmqDownMsg() ).thenReturn( "RabbitMQ is unreachable" );
        when( systemHealthJmxConfig.getTopicDownMsg() ).thenReturn( "%s got disconnected" );

        systemHealthBusinessServiceImpl.listenToOpportunityCreateTopic( opportunitySource );
        verify( topicStatusService ).updateTopicStatus( OPPORTUNITY_CREATE.getName(), active.name() );
    }

    /**
     * Test listen to opportunity create topic when account deletion throws
     * exception.
     */
    @Test
    public void testListenToOpportunityCreateTopicWhenAccountDeletionThrowsException() {
        final OpportunitySource opportunitySource = new OpportunitySource();
        final String contactId = "contactId";
        final String accountId = "accountId";
        when( contactService.findContactIdByOpportunityId( opportunitySource.getCrmId() ) ).thenReturn( contactId );
        when( accountService.findAccountIdByOpportunityId( opportunitySource.getCrmId() ) )
                .thenThrow( Exception.class );
        systemHealthBusinessServiceImpl.listenToOpportunityCreateTopic( opportunitySource );
        verify( topicStatusService ).updateTopicStatus( OPPORTUNITY_CREATE.getName(), active.name() );
    }

    /**
     * Test listen to opportunity change topic.
     */
    @Test
    public void testListenToOpportunityChangeTopic() {
        final OpportunitySource opportunitySource = new OpportunitySource();
        systemHealthBusinessServiceImpl.listenToOpportunityChangeTopic( opportunitySource );
        verify( topicStatusService ).updateTopicStatus( OPPORTUNITY_CHANGE.getName(), active.name() );
    }

    /**
     * Test listen to contact change topic.
     */
    @Test
    public void testListenToContactChangeTopic() {
        final OpportunityContact opportunityContact = new OpportunityContact();
        systemHealthBusinessServiceImpl.listenToContactChangeTopic( opportunityContact );
        verify( topicStatusService ).updateTopicStatus( CONTACT.getName(), active.name() );
    }

    /**
     * Test listen to agent create topic.
     */
    @Test
    public void testListenToAgentCreateTopic() {
        systemHealthBusinessServiceImpl.listenToAgentCreateTopic();
        verify( topicStatusService ).updateTopicStatus( AGENT.getName(), active.name() );
    }

    /**
     * Test check system errors should reset topic connection when topic fails.
     */
    @Test
    public void testCheckSystemErrorsShouldResetTopicConnectionWhenTopicFails() {
        final List< TopicStatus > topicStatusList = new ArrayList<>();
        final String serviceUp = "Service is operating normally";
        final String serviceDown = "Service is down";
        final String topicDown = "%s got disconnected";
        topicStatusList.add( new TopicStatus( LEAD.getName(), inprogress.name() ) );
        topicStatusList.add( new TopicStatus( OPPORTUNITY_CREATE.getName(), inprogress.name() ) );
        topicStatusList.add( new TopicStatus( OPPORTUNITY_CHANGE.getName(), inprogress.name() ) );
        topicStatusList.add( new TopicStatus( AGENT.getName(), inprogress.name() ) );
        topicStatusList.add( new TopicStatus( CONTACT.getName(), inprogress.name() ) );

        when( systemHealthJmxConfig.getContactTopicUpdateWaitDuration() ).thenReturn( 0 );
        when( systemErrorLogService.findLatestSystemErrors() ).thenReturn( new ArrayList<>() );
        when( topicStatusService.findAll() ).thenReturn( topicStatusList );
        when( systemHealthJmxConfig.getServiceUpMsg() ).thenReturn( serviceUp );
        when( systemHealthJmxConfig.getServiceDownMsg() ).thenReturn( serviceDown );
        when( systemHealthJmxConfig.getTopicDownMsg() ).thenReturn( topicDown );
        doNothing().when( applicationEventPublisher ).publishEvent( any( FailedTopicEvent.class ) );
        doNothing().when( gravitasNotificationBusinessService ).notifySystemError( any( GravitasHealthStatus.class ) );
        systemHealthBusinessServiceImpl.checkSystemErrors();

        verify( applicationEventPublisher, Mockito.times( 5 ) ).publishEvent( any( FailedTopicEvent.class ) );
        verify( gravitasNotificationBusinessService, Mockito.times( 5 ) )
                .notifySystemError( any( GravitasHealthStatus.class ) );
    }

    /**
     * Test listen to lead create topic.
     */
    @Test
    public void testListenToLeadCreateTopic() {
        final LeadSource leadSource = new LeadSource();
        doNothing().when( topicStatusService ).updateTopicStatus( LEAD_CREATE.getName(), active.name() );
        systemHealthBusinessServiceImpl.listenToLeadCreateTopic( leadSource );
        verify( topicStatusService ).updateTopicStatus( LEAD_CREATE.getName(), active.name() );
    }

    /**
     * Test notify gravitas db down.
     */
    @Test
    public void testNotifyGravitasDbDown() {
        final String errorMsg = "error";
        doNothing().when( gravitasNotificationBusinessService ).notifySystemError( any( GravitasHealthStatus.class ) );
        systemHealthBusinessServiceImpl.notifyGravitasDbDown( errorMsg );
        verify( gravitasNotificationBusinessService ).notifySystemError( any( GravitasHealthStatus.class ) );
    }
}
