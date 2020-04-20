package com.owners.gravitas.listener;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.joda.time.DateTime;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.LeadBusinessService;
import com.owners.gravitas.business.SystemHealthBusinessService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.SystemHealthJmxConfig;
import com.owners.gravitas.dto.crm.response.CRMLeadResponse;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.gateway.LeadMessageGateway;
import com.owners.gravitas.lock.TopicListenerLockHandler;

/**
 * The Class TopicMessageListenerTest.
 *
 * @author amits
 */
public class LeadTopicMessageListenerTest extends AbstractBaseMockitoTest {

    /** The topic message listener. */
    @InjectMocks
    private LeadTopicMessageListener leadTopicMessageListener;

    /** The system health business service. */
    @Mock
    private SystemHealthBusinessService systemHealthBusinessService;

    /** The client session channel. */
    @Mock
    private ClientSessionChannel clientSessionChannel;

    /** The message. */
    @Mock
    private Message message;

    /** The lead message gateway. */
    @Mock
    private LeadMessageGateway leadMessageGateway;

    /** The object mapper. */
    @Mock
    private ObjectMapper objectMapper;

    /** The lock handler. */
    @Mock
    private TopicListenerLockHandler lockHandler;

    /** The lead business service. */
    @Mock
    private LeadBusinessService leadBusinessService;

    /** The system health jmx config. */
    @Mock
    private SystemHealthJmxConfig systemHealthJmxConfig;

    /**
     * Sets the up.
     */
    @BeforeMethod
    public void setUp() {
        ReflectionTestUtils.setField( leadTopicMessageListener, "emailExclusionPrefix", "a" );
        ReflectionTestUtils.setField( leadTopicMessageListener, "emailExclusionSuffix", "a" );
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
    public void testOnMessage() throws JsonProcessingException, IOException {
        final String str = "{\"clientId\":\"test\",\"data\":{\"event\":{\"createdDate\":\"2017-06-02T06:36:04.000+0000\",\"type\":\"updated\"},\"sobject\":{\"Status\":\"New\",\"Company\":\"ra_kgh ra_hkjgh6 (test@test.com)\",\"Email\":\"test@test.com\",\"Lead_Record_Type_Name__c\":\"Buyer\",\"ConvertedDate\":null,\"Property_State_BR__c\":\"GA\",\"Owners_Visitor_ID__c\":\"ownersVisitorId\",\"Salutation\":null,\"Reason_Closed__c\":null,\"Name\":\"ra_kgh ra_hkjgh6\",\"LeadSource\":\"test.com\",\"OwnerId\":\"00G39000003tyq9EAA\",\"Phone\":\"(541) 754-3010\",\"RecordTypeId\":\"141241414\",\"Id\":\"1414141241\",\"Hubzu_Property_ID__c\":\"ownersComIdentifier\"}},\"channel\":\"/topic/test\"}";
        when( message.getJSON() ).thenReturn( str );
        when( systemHealthJmxConfig.getSystemHealthLeadEmail() ).thenReturn( "" );
        final CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setLastModifiedDate( new DateTime().plusSeconds( 11 ) );
        leadResponse.setCreatedDateTime( new DateTime() );
        leadResponse.setEmail( "test@test.com" );
        final ObjectMapper objectMapper1 = new ObjectMapper();
        final JsonNode root = objectMapper1.readTree( str.getBytes() );
        when( lockHandler.acquireLock( anyString() ) ).thenReturn( true );
        when( leadBusinessService.getCRMLead( anyString() ) ).thenReturn( leadResponse );
        when( objectMapper.readTree( anyString().getBytes() ) ).thenReturn( root );
        leadTopicMessageListener.onMessage( clientSessionChannel, message );
        verify( leadMessageGateway ).publishLeadUpdate( any() );
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
        final String str = "{\"clientId\":\"test\",\"data\":{\"event\":{\"createdDate\":\"2017-06-02T06:36:04.000+0000\",\"type\":\"updated\"},\"sobject\":{\"Status\":\"New\",\"Company\":\"ra_kgh ra_hkjgh6 (test@test.com)\",\"Email\":\"test@test.com\",\"Lead_Record_Type_Name__c\":\"Buyer\",\"ConvertedDate\":null,\"Property_State_BR__c\":\"GA\",\"Owners_Visitor_ID__c\":\"ownersVisitorId\",\"Salutation\":null,\"Reason_Closed__c\":null,\"Name\":\"ra_kgh ra_hkjgh6\",\"LeadSource\":\"test.com\",\"OwnerId\":\"00G39000003tyq9EAA\",\"Phone\":\"(541) 754-3010\",\"RecordTypeId\":\"141241414\",\"Id\":\"1414141241\",\"Hubzu_Property_ID__c\":\"ownersComIdentifier\"}},\"channel\":\"/topic/test\"}";
        when( message.getJSON() ).thenReturn( str );
        final ObjectMapper objectMapper1 = new ObjectMapper();
        final JsonNode root = objectMapper1.readTree( str.getBytes() );
        when( lockHandler.acquireLock( anyString() ) ).thenReturn( false );
        when( leadBusinessService.getCRMLead( anyString() ) ).thenReturn( new CRMLeadResponse() );
        when( objectMapper.readTree( anyString().getBytes() ) ).thenReturn( root );
        when( objectMapper.writeValueAsString( any( JsonNode.class ) ) ).thenReturn( str );
        leadTopicMessageListener.onMessage( clientSessionChannel, message );
        verifyZeroInteractions( leadMessageGateway );
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
        leadTopicMessageListener.onMessage( clientSessionChannel, message );
    }

    /**
     * Test on message for health service lead.
     *
     * @throws JsonProcessingException
     *             the json processing exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testOnMessageForHealthServiceLead() throws JsonProcessingException, IOException {
        final String str = "{\"clientId\":\"test\",\"data\":{\"event\":{\"createdDate\":\"2017-06-02T06:36:04.000+0000\",\"type\":\"updated\"},\"sobject\":{\"Status\":\"New\",\"Company\":\"ra_kgh ra_hkjgh6 (test@test.com)\",\"Email\":\"test@test.com\",\"Lead_Record_Type_Name__c\":\"Buyer\",\"ConvertedDate\":null,\"Property_State_BR__c\":\"GA\",\"Owners_Visitor_ID__c\":\"ownersVisitorId\",\"Salutation\":null,\"Reason_Closed__c\":null,\"Name\":\"ra_kgh ra_hkjgh6\",\"LeadSource\":\"test.com\",\"OwnerId\":\"00G39000003tyq9EAA\",\"Phone\":\"(541) 754-3010\",\"RecordTypeId\":\"141241414\",\"Id\":\"1414141241\",\"Hubzu_Property_ID__c\":\"ownersComIdentifier\"}},\"channel\":\"/topic/test\"}";
        when( message.getJSON() ).thenReturn( str );
        when( systemHealthJmxConfig.getSystemHealthLeadEmail() ).thenReturn( "test@test.com" );
        final CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setEmail( "test@test.com" );
        leadResponse.setLastModifiedDate( new DateTime().plusSeconds( 11 ) );
        leadResponse.setCreatedDateTime( new DateTime() );
        final ObjectMapper objectMapper1 = new ObjectMapper();
        final JsonNode root = objectMapper1.readTree( str.getBytes() );
        when( lockHandler.acquireLock( anyString() ) ).thenReturn( true );
        when( leadBusinessService.getCRMLead( anyString() ) ).thenReturn( leadResponse );
        when( objectMapper.readTree( anyString().getBytes() ) ).thenReturn( root );
        leadTopicMessageListener.onMessage( clientSessionChannel, message );
        verify( systemHealthBusinessService ).listenToLeadChangeTopic( any( LeadSource.class ) );
    }

    /**
     * Test on message for health service lead should throw http server error
     * exception.
     *
     * @throws JsonProcessingException
     *             the json processing exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test( expectedExceptions = HttpServerErrorException.class )
    public void testOnMessage_ForHealthServiceLead_ShouldThrowHttpServerErrorException()
            throws JsonProcessingException, IOException {
        final String str = "{\"clientId\":\"test\",\"data\":{\"event\":{\"createdDate\":\"2017-06-02T06:36:04.000+0000\",\"type\":\"updated\"},\"sobject\":{\"Status\":\"New\",\"Company\":\"ra_kgh ra_hkjgh6 (test@test.com)\",\"Email\":\"test@test.com\",\"Lead_Record_Type_Name__c\":\"Buyer\",\"ConvertedDate\":null,\"Property_State_BR__c\":\"GA\",\"Owners_Visitor_ID__c\":\"ownersVisitorId\",\"Salutation\":null,\"Reason_Closed__c\":null,\"Name\":\"ra_kgh ra_hkjgh6\",\"LeadSource\":\"test.com\",\"OwnerId\":\"00G39000003tyq9EAA\",\"Phone\":\"(541) 754-3010\",\"RecordTypeId\":\"141241414\",\"Id\":\"1414141241\",\"Hubzu_Property_ID__c\":\"ownersComIdentifier\"}},\"channel\":\"/topic/test\"}";
        when( message.getJSON() ).thenReturn( str );
        when( systemHealthJmxConfig.getSystemHealthLeadEmail() ).thenReturn( "test@test.com" );
        final CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setEmail( "test@test.com" );
        leadResponse.setLastModifiedDate( new DateTime().plusSeconds( 11 ) );
        leadResponse.setCreatedDateTime( new DateTime() );
        final ObjectMapper objectMapper1 = new ObjectMapper();
        final JsonNode root = objectMapper1.readTree( str.getBytes() );
        when( lockHandler.acquireLock( anyString() ) ).thenReturn( true );
        when( leadBusinessService.getCRMLead( anyString() ) ).thenReturn( leadResponse );
        when( objectMapper.readTree( anyString().getBytes() ) ).thenReturn( root );
        doThrow( new HttpServerErrorException( HttpStatus.BAD_GATEWAY ) ).when( systemHealthBusinessService )
                .listenToLeadChangeTopic( any( LeadSource.class ) );
        leadTopicMessageListener.onMessage( clientSessionChannel, message );
    }

    /**
     * Test on message for health service lead should throw http client error
     * exception.
     *
     * @throws JsonProcessingException
     *             the json processing exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test( expectedExceptions = HttpClientErrorException.class )
    public void testOnMessage_ForHealthServiceLead_ShouldThrowHttpClientErrorException()
            throws JsonProcessingException, IOException {
        final String str = "{\"clientId\":\"test\",\"data\":{\"event\":{\"createdDate\":\"2017-06-02T06:36:04.000+0000\",\"type\":\"updated\"},\"sobject\":{\"Status\":\"New\",\"Company\":\"ra_kgh ra_hkjgh6 (test@test.com)\",\"Email\":\"test@test.com\",\"Lead_Record_Type_Name__c\":\"Buyer\",\"ConvertedDate\":null,\"Property_State_BR__c\":\"GA\",\"Owners_Visitor_ID__c\":\"ownersVisitorId\",\"Salutation\":null,\"Reason_Closed__c\":null,\"Name\":\"ra_kgh ra_hkjgh6\",\"LeadSource\":\"test.com\",\"OwnerId\":\"00G39000003tyq9EAA\",\"Phone\":\"(541) 754-3010\",\"RecordTypeId\":\"141241414\",\"Id\":\"1414141241\",\"Hubzu_Property_ID__c\":\"ownersComIdentifier\"}},\"channel\":\"/topic/test\"}";
        when( message.getJSON() ).thenReturn( str );
        when( systemHealthJmxConfig.getSystemHealthLeadEmail() ).thenReturn( "test@test.com" );
        final CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setEmail( "test@test.com" );
        leadResponse.setLastModifiedDate( new DateTime().plusSeconds( 11 ) );
        leadResponse.setCreatedDateTime( new DateTime() );
        final ObjectMapper objectMapper1 = new ObjectMapper();
        final JsonNode root = objectMapper1.readTree( str.getBytes() );
        when( lockHandler.acquireLock( anyString() ) ).thenReturn( true );
        when( leadBusinessService.getCRMLead( anyString() ) ).thenReturn( leadResponse );
        when( objectMapper.readTree( anyString().getBytes() ) ).thenReturn( root );
        doThrow( new HttpClientErrorException( HttpStatus.BAD_GATEWAY ) ).when( systemHealthBusinessService )
                .listenToLeadChangeTopic( any( LeadSource.class ) );
        leadTopicMessageListener.onMessage( clientSessionChannel, message );
    }

    /**
     * Test on message for created event.
     *
     * @throws JsonProcessingException
     *             the json processing exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testOnMessageForCreatedEvent() throws JsonProcessingException, IOException {
        final String str = "{\"clientId\":\"test\",\"data\":{\"event\":{\"createdDate\":\"2017-06-02T06:36:04.000+0000\",\"type\":\"created\"},\"sobject\":{\"Status\":\"New\",\"Company\":\"ra_kgh ra_hkjgh6 (test@test.com)\",\"Email\":\"test@test.com\",\"Lead_Record_Type_Name__c\":\"Buyer\",\"ConvertedDate\":null,\"Property_State_BR__c\":\"GA\",\"Owners_Visitor_ID__c\":\"ownersVisitorId\",\"Salutation\":null,\"Reason_Closed__c\":null,\"Name\":\"ra_kgh ra_hkjgh6\",\"LeadSource\":\"test.com\",\"OwnerId\":\"00G39000003tyq9EAA\",\"Phone\":\"(541) 754-3010\",\"RecordTypeId\":\"141241414\",\"Id\":\"1414141241\",\"Hubzu_Property_ID__c\":\"ownersComIdentifier\"}},\"channel\":\"/topic/test\"}";
        when( message.getJSON() ).thenReturn( str );
        final CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setEmail( "test@test.com" );
        leadResponse.setLastModifiedDate( new DateTime().plusSeconds( 11 ) );
        leadResponse.setCreatedDateTime( new DateTime() );
        final ObjectMapper objectMapper1 = new ObjectMapper();
        final JsonNode root = objectMapper1.readTree( str.getBytes() );
        when( objectMapper.readTree( anyString().getBytes() ) ).thenReturn( root );
        leadTopicMessageListener.onMessage( clientSessionChannel, message );
        verifyZeroInteractions( lockHandler, systemHealthBusinessService, systemHealthJmxConfig );
    }
}
