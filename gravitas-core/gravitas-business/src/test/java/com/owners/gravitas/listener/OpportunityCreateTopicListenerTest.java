package com.owners.gravitas.listener;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubzu.dsync.service.DistributedSynchronizationService;
import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.OpportunityBusinessService;
import com.owners.gravitas.business.SystemHealthBusinessService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.SystemHealthJmxConfig;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.gateway.OpportunityChangeMessageGateway;
import com.owners.gravitas.lock.TopicListenerLockHandler;
import com.owners.gravitas.service.CRMQueryService;
import com.owners.gravitas.service.HostService;

/**
 * The Class TopicMessageListenerTest.
 *
 * @author shivamm
 */
public class OpportunityCreateTopicListenerTest extends AbstractBaseMockitoTest {

    /** The topic message listener. */
    @InjectMocks
    private OpportunityCreateTopicListener oppCreateTopicMessageListener;

    /** The client session channel. */
    @Mock
    private ClientSessionChannel clientSessionChannel;

    /** The message. */
    @Mock
    private Message message;

    /** The lead message gateway. */
    @Mock
    private OpportunityChangeMessageGateway oppChangeMessageGateway;

    /** The object mapper. */
    @Mock
    private ObjectMapper objectMapper;

    /** The job synchronization. */
    @Mock
    private DistributedSynchronizationService databaseDistributedSynchronizationService;

    /** The root. */
    @Mock
    private JsonNode root;

    /** The crm query service. */
    @Mock
    private CRMQueryService crmQueryService;

    /** The opportunity business service. */
    @Mock
    private OpportunityBusinessService opportunityBusinessService;

    /** The lock handler. */
    @Mock
    private TopicListenerLockHandler lockHandler;

    /** The system health jmx config. */
    @Mock
    private SystemHealthJmxConfig systemHealthJmxConfig;

    /** The system health business service. */
    @Mock
    private SystemHealthBusinessService systemHealthBusinessService;

    /** The host service. */
    @Mock
    private HostService hostService;

    /** The opportunity change message gateway. */
    @Mock
    private OpportunityChangeMessageGateway opportunityChangeMessageGateway;

    /**
     * Test on message for create event.
     *
     * @throws JsonProcessingException
     *             the json processing exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testOnMessageForCreateEvent() throws JsonProcessingException, IOException {
        String str = "{\"clientId\":\"test\",\"data\":{\"event\":{\"createdDate\":\"2017-06-02T06:36:04.000+0000\",\"type\":\"updated\"},\"sobject\":{\"Status\":\"New\",\"Company\":\"ra_kgh ra_hkjgh6 (test@test.com)\",\"Email\":\"test@test.com\",\"Lead_Record_Type_Name__c\":\"Buyer\",\"ConvertedDate\":null,\"Property_State_BR__c\":\"GA\",\"Owners_Visitor_ID__c\":\"ownersVisitorId\",\"Salutation\":null,\"Reason_Closed__c\":null,\"Name\":\"ra_kgh ra_hkjgh6\",\"LeadSource\":\"test.com\",\"OwnerId\":\"00G39000003tyq9EAA\",\"Phone\":\"(541) 754-3010\",\"RecordTypeId\":\"141241414\",\"Id\":\"1414141241\",\"Hubzu_Property_ID__c\":\"ownersComIdentifier\"}},\"channel\":\"/topic/test\"}";
        final ObjectMapper objectMapper1 = new ObjectMapper();
        final JsonNode root = objectMapper1.readTree( str.getBytes() );
        String sfApiUserFullName = "Owners Bpm Dev";
        setField( oppCreateTopicMessageListener, "sfApiUserFullName", sfApiUserFullName );
        when( message.getJSON() ).thenReturn( str );
        when( lockHandler.acquireLock( anyString() ) ).thenReturn( true );
        when( objectMapper.readTree( anyString().getBytes() ) ).thenReturn( root );
        when( databaseDistributedSynchronizationService.acquireLock( anyString() ) ).thenReturn( true );
        Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Name", "test" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        OpportunitySource source = new OpportunitySource();
        when( opportunityBusinessService.getOpportunity( any() ) ).thenReturn( source );
        oppCreateTopicMessageListener.onMessage( clientSessionChannel, message );
    }

    /**
     * Test on message for exception.
     *
     * @throws JsonProcessingException
     *             the json processing exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testOnMessageForException() throws JsonProcessingException, IOException {
        String sfApiUserFullName = "Owners Bpm Dev";
        setField( oppCreateTopicMessageListener, "sfApiUserFullName", sfApiUserFullName );
        when( lockHandler.acquireLock( anyString() ) ).thenReturn( true );
        when( message.getJSON() ).thenReturn( "{test}" );
        when( objectMapper.readTree( anyString().getBytes() ) ).thenReturn( root );
        when( databaseDistributedSynchronizationService.acquireLock( anyString() ) ).thenReturn( true );
        when( root.path( anyString() ) ).thenReturn( root );
        when( objectMapper.writeValueAsString( any( JsonNode.class ) ) )
                .thenReturn( "{\"sobject\":{\"key\":\"value\"}" );
        Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Name", "test" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        OpportunitySource source = new OpportunitySource();
        when( opportunityBusinessService.getOpportunity( any() ) ).thenReturn( source );
        oppCreateTopicMessageListener.onMessage( clientSessionChannel, message );
        verify( oppChangeMessageGateway ).publishOpportunityChange( any() );
    }

    /**
     * Test on message.
     *
     * @throws JsonProcessingException
     *             the json processing exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testOnMessageFromGravitas() throws JsonProcessingException, IOException {
        String str = "{\"clientId\":\"test\",\"data\":{\"event\":{\"createdDate\":\"2017-06-02T06:36:04.000+0000\",\"type\":\"updated\"},\"sobject\":{\"Status\":\"New\",\"Company\":\"ra_kgh ra_hkjgh6 (test@test.com)\",\"Email\":\"test@test.com\",\"Lead_Record_Type_Name__c\":\"Buyer\",\"ConvertedDate\":null,\"Property_State_BR__c\":\"GA\",\"Owners_Visitor_ID__c\":\"ownersVisitorId\",\"Salutation\":null,\"Reason_Closed__c\":null,\"Name\":\"ra_kgh ra_hkjgh6\",\"LeadSource\":\"test.com\",\"OwnerId\":\"00G39000003tyq9EAA\",\"Phone\":\"(541) 754-3010\",\"RecordTypeId\":\"141241414\",\"Id\":\"1414141241\",\"Hubzu_Property_ID__c\":\"ownersComIdentifier\"}},\"channel\":\"/topic/test\"}";
        final ObjectMapper objectMapper1 = new ObjectMapper();
        final JsonNode root = objectMapper1.readTree( str.getBytes() );
        String sfApiUserFullName = "Owners Bpm Dev";
        setField( oppCreateTopicMessageListener, "sfApiUserFullName", sfApiUserFullName );
        when( message.getJSON() ).thenReturn( str );
        when( lockHandler.acquireLock( anyString() ) ).thenReturn( true );
        when( objectMapper.readTree( anyString().getBytes() ) ).thenReturn( root );
        when( databaseDistributedSynchronizationService.acquireLock( anyString() ) ).thenReturn( true );
        Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Name", "test" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        OpportunitySource source = new OpportunitySource();
        source.setLastModifiedBy( sfApiUserFullName );
        when( opportunityBusinessService.getOpportunity( any() ) ).thenReturn( source );
        oppCreateTopicMessageListener.onMessage( clientSessionChannel, message );
        verifyZeroInteractions( oppChangeMessageGateway );
    }

    /**
     * Test on message from gravitas for created event.
     *
     * @throws JsonProcessingException the json processing exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Test
    public void testOnMessageFromGravitasForCreatedEventAndEmailIdPresentInSystemHealthLeadEmails() throws JsonProcessingException, IOException {
        String str = "{\"clientId\":\"test\",\"data\":{\"event\":{\"createdDate\":\"2017-06-02T06:36:04.000+0000\",\"type\":\"created\"},\"sobject\":{\"Status\":\"New\",\"Company\":\"ra_kgh ra_hkjgh6 (test@test.com)\",\"Email\":\"test@test.com\",\"Lead_Record_Type_Name__c\":\"Buyer\",\"ConvertedDate\":null,\"Property_State_BR__c\":\"GA\",\"Owners_Visitor_ID__c\":\"ownersVisitorId\",\"Salutation\":null,\"Reason_Closed__c\":null,\"Name\":\"ra_kgh ra_hkjgh6\",\"LeadSource\":\"test.com\",\"OwnerId\":\"00G39000003tyq9EAA\",\"Phone\":\"(541) 754-3010\",\"RecordTypeId\":\"141241414\",\"Id\":\"1414141241\",\"Hubzu_Property_ID__c\":\"ownersComIdentifier\"}},\"channel\":\"/topic/test\"}";
        final ObjectMapper objectMapper1 = new ObjectMapper();
        final JsonNode root = objectMapper1.readTree( str.getBytes() );
        String sfApiUserFullName = "Owners Bpm Dev";
        setField( oppCreateTopicMessageListener, "sfApiUserFullName", sfApiUserFullName );

        List<String> emails = new ArrayList<>();
        emails.add( "test@test.com" );
        Contact primaryContact = new Contact();
        primaryContact.setEmails( emails );
        OpportunitySource opportunitySource = new OpportunitySource();
        opportunitySource.setPrimaryContact( primaryContact );
        when( opportunityBusinessService.getOpportunityCreateDetails( anyString() ) ).thenReturn( opportunitySource );
        when( systemHealthJmxConfig.getSystemHealthLeadEmail() ).thenReturn( "test@test.com" );
        doNothing().when( systemHealthBusinessService).listenToOpportunityCreateTopic( opportunitySource );
        when( message.getJSON() ).thenReturn( str );
        when( lockHandler.acquireLock( anyString() ) ).thenReturn( true );
        when( objectMapper.readTree( anyString().getBytes() ) ).thenReturn( root );
        when( databaseDistributedSynchronizationService.acquireLock( anyString() ) ).thenReturn( true );
        Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Name", "test" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        OpportunitySource source = new OpportunitySource();
        source.setLastModifiedBy( sfApiUserFullName );
        when( opportunityBusinessService.getOpportunity( any() ) ).thenReturn( source );
        oppCreateTopicMessageListener.onMessage( clientSessionChannel, message );
        verifyZeroInteractions( oppChangeMessageGateway );
    }

    @Test
    public void testOnMessageFromGravitasForCreatedEventAndEmailIdNotPresentInSystemHealthLeadEmails() throws JsonProcessingException, IOException {
        String str = "{\"clientId\":\"test\",\"data\":{\"event\":{\"createdDate\":\"2017-06-02T06:36:04.000+0000\",\"type\":\"created\"},\"sobject\":{\"Status\":\"New\",\"Company\":\"ra_kgh ra_hkjgh6 (test@test.com)\",\"Email\":\"test@test.com\",\"Lead_Record_Type_Name__c\":\"Buyer\",\"ConvertedDate\":null,\"Property_State_BR__c\":\"GA\",\"Owners_Visitor_ID__c\":\"ownersVisitorId\",\"Salutation\":null,\"Reason_Closed__c\":null,\"Name\":\"ra_kgh ra_hkjgh6\",\"LeadSource\":\"test.com\",\"OwnerId\":\"00G39000003tyq9EAA\",\"Phone\":\"(541) 754-3010\",\"RecordTypeId\":\"141241414\",\"Id\":\"1414141241\",\"Hubzu_Property_ID__c\":\"ownersComIdentifier\"}},\"channel\":\"/topic/test\"}";
        final ObjectMapper objectMapper1 = new ObjectMapper();
        final JsonNode root = objectMapper1.readTree( str.getBytes() );
        String sfApiUserFullName = "Owners Bpm Dev";
        setField( oppCreateTopicMessageListener, "sfApiUserFullName", sfApiUserFullName );

        List<String> emails = new ArrayList<>();
        emails.add( "test@test.com" );
        Contact primaryContact = new Contact();
        primaryContact.setEmails( emails );
        OpportunitySource opportunitySource = new OpportunitySource();
        opportunitySource.setPrimaryContact( primaryContact );
        when( opportunityBusinessService.getOpportunityCreateDetails( anyString() ) ).thenReturn( opportunitySource );
        when( systemHealthJmxConfig.getSystemHealthLeadEmail() ).thenReturn( "test1@test.com" );
        doNothing().when( opportunityChangeMessageGateway ).publishOpportunityCreate( opportunitySource );
        when( message.getJSON() ).thenReturn( str );
        when( lockHandler.acquireLock( anyString() ) ).thenReturn( true );
        when( objectMapper.readTree( anyString().getBytes() ) ).thenReturn( root );
        when( databaseDistributedSynchronizationService.acquireLock( anyString() ) ).thenReturn( true );
        Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Name", "test" );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        OpportunitySource source = new OpportunitySource();
        source.setLastModifiedBy( sfApiUserFullName );
        when( opportunityBusinessService.getOpportunity( any() ) ).thenReturn( source );
        oppCreateTopicMessageListener.onMessage( clientSessionChannel, message );
        verify( opportunityBusinessService).getOpportunityCreateDetails( anyString() );
        verify( systemHealthJmxConfig).getSystemHealthLeadEmail();
        verify( opportunityChangeMessageGateway).publishOpportunityCreate( opportunitySource );

    }

    /**
     * Test on message.
     *
     * @throws JsonProcessingException
     *             the json processing exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testOnMessageAquireLockFalse() throws JsonProcessingException, IOException {
        String str = "{\"clientId\":\"test\",\"data\":{\"event\":{\"createdDate\":\"2017-06-02T06:36:04.000+0000\",\"type\":\"updated\"},\"sobject\":{\"Status\":\"New\",\"Company\":\"ra_kgh ra_hkjgh6 (test@test.com)\",\"Email\":\"test@test.com\",\"Lead_Record_Type_Name__c\":\"Buyer\",\"ConvertedDate\":null,\"Property_State_BR__c\":\"GA\",\"Owners_Visitor_ID__c\":\"ownersVisitorId\",\"Salutation\":null,\"Reason_Closed__c\":null,\"Name\":\"ra_kgh ra_hkjgh6\",\"LeadSource\":\"test.com\",\"OwnerId\":\"00G39000003tyq9EAA\",\"Phone\":\"(541) 754-3010\",\"RecordTypeId\":\"141241414\",\"Id\":\"1414141241\",\"Hubzu_Property_ID__c\":\"ownersComIdentifier\"}},\"channel\":\"/topic/test\"}";
        final ObjectMapper objectMapper1 = new ObjectMapper();
        final JsonNode root = objectMapper1.readTree( str.getBytes() );
        when( message.getJSON() ).thenReturn( str );
        when( lockHandler.acquireLock( anyString() ) ).thenReturn( false );
        when( objectMapper.readTree( anyString().getBytes() ) ).thenReturn( root );
        when( databaseDistributedSynchronizationService.acquireLock( anyString() ) ).thenReturn( false );
        oppCreateTopicMessageListener.onMessage( clientSessionChannel, message );
        verifyZeroInteractions( oppChangeMessageGateway );
    }

    /**
     * Should not acquire lock if data node not found.
     *
     * @throws JsonProcessingException
     *             the json processing exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void shouldNotAcquireLockIfDataNodeNotFound() throws JsonProcessingException, IOException {
        when( message.getJSON() ).thenReturn( "{test}" );
        when( objectMapper.readTree( any( byte[].class ) ) ).thenThrow( IOException.class );
        oppCreateTopicMessageListener.onMessage( clientSessionChannel, message );
    }

}
