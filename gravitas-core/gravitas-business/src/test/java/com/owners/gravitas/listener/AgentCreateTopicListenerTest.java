package com.owners.gravitas.listener;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.owners.gravitas.amqp.AgentSource;
import com.owners.gravitas.business.AgentBusinessService;
import com.owners.gravitas.business.SystemHealthBusinessService;
import com.owners.gravitas.business.builder.AgentSourceBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.SystemHealthJmxConfig;
import com.owners.gravitas.dto.crm.response.CRMAgentResponse;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.gateway.AgentCreateMessageGateway;
import com.owners.gravitas.lock.TopicListenerLockHandler;
import com.owners.gravitas.util.JsonUtil;

/**
 * The Class TopicMessageListenerTest.
 *
 * @author amits
 */
@PrepareForTest( JsonUtil.class )
public class AgentCreateTopicListenerTest extends AbstractBaseMockitoTest {

    /** The topic message listener. */
    @InjectMocks
    private AgentCreateTopicListener agentCreateTopicListener;

    /** The client session channel. */
    @Mock
    private ClientSessionChannel clientSessionChannel;

    /** The message. */
    @Mock
    private Message message;

    /** The lead message gateway. */
    @Mock
    private AgentCreateMessageGateway agentMessageGateway;

    /** The object mapper. */
    @Mock
    private ObjectMapper objectMapper;

    /** The root. */
    @Mock
    private JsonNode root;

    /** The lock handler. */
    @Mock
    private TopicListenerLockHandler lockHandler;

    /** The agent source builder. */
    @Mock
    private AgentSourceBuilder agentSourceBuilder;

    /** The system health jmx config. */
    @Mock
    private SystemHealthJmxConfig systemHealthJmxConfig;

    /** The agent business service. */
    @Mock
    private AgentBusinessService agentBusinessService;

    /** The system health business service. */
    @Mock
    private SystemHealthBusinessService systemHealthBusinessService;

    /** The lead message gateway. */
    @Mock
    private AgentCreateMessageGateway agentCreateMessageGateway;

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
        when( message.getJSON() ).thenReturn( "{test}" );
        when( lockHandler.acquireLock( anyString() ) ).thenReturn( false );
        when( objectMapper.readTree( anyString().getBytes() ) ).thenReturn( root );
        when( root.path( anyString() ) ).thenReturn( root );
        when( objectMapper.writeValueAsString( any( JsonNode.class ) ) )
                .thenReturn( "{\"sobject\":{\"key\":\"value\"}}" );
        agentCreateTopicListener.onMessage( clientSessionChannel, message );
        verifyZeroInteractions( agentMessageGateway );
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
        agentCreateTopicListener.onMessage( clientSessionChannel, message );
    }

    /**
     * Test on message when event type is add.
     *
     * @throws JsonProcessingException
     *             the json processing exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testOnMessageWhenEventTypeIsAdd() throws JsonProcessingException, IOException {
        final String agentId = "agentId";
        final String eventType = "created";
        final CRMAgentResponse crmAgentResponse = new CRMAgentResponse();
        crmAgentResponse.setId( agentId );
        crmAgentResponse.setEmail( "test@test.com" );
        AgentSource agentSource = new AgentSource();
        agentSource.setEmail( "test@test.com" );
        final String value = "{\"sobject\":{\"Id\":" + "\"" + agentId + "\"" + "},\"event\":{\"type\":" + "\""
                + eventType + "\"" + "}}";

        when( message.getJSON() ).thenReturn( value );
        when( objectMapper.readTree( anyString().getBytes() ) ).thenReturn( root );
        PowerMockito.mockStatic( JsonUtil.class );
        PowerMockito.when( JsonUtil.toJson( any( JsonNode.class ) ) ).thenReturn( value );
        when( lockHandler.acquireLock( anyString() ) ).thenReturn( true );
        when( agentBusinessService.getCRMAgentById( agentId ) ).thenReturn( crmAgentResponse );
        when( systemHealthJmxConfig.getSystemHealthAgentEmail() ).thenReturn( "testSystemHealthEmail" );
        when( agentSourceBuilder.convertTo( crmAgentResponse ) ).thenReturn( agentSource );
        doNothing().when( agentCreateMessageGateway ).publishAgentCreate( any( AgentSource.class ) );
        doNothing().when( agentBusinessService ).syncAgentDetails( any( AgentSource.class ) );

        agentCreateTopicListener.onMessage( clientSessionChannel, message );

        verify( lockHandler ).acquireLock( anyString() );
        verify( agentBusinessService ).getCRMAgentById( agentId );
        verify( systemHealthJmxConfig ).getSystemHealthAgentEmail();
        verifyZeroInteractions( systemHealthBusinessService );
        verify( agentCreateMessageGateway ).publishAgentCreate( any( AgentSource.class ) );
        verifyNoMoreInteractions( agentCreateMessageGateway );
        verify( agentCreateMessageGateway, Mockito.times( 0 ) ).publishAgentUpdate( any( AgentSource.class ) );
    }

    /**
     * Test on message when event type is update.
     *
     * @throws JsonProcessingException
     *             the json processing exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testOnMessageWhenEventTypeIsUpdate() throws JsonProcessingException, IOException {
        final String agentId = "agentId";
        final String eventType = "updated";
        final CRMAgentResponse crmAgentResponse = new CRMAgentResponse();
        crmAgentResponse.setId( agentId );
        crmAgentResponse.setEmail( "test@test.com" );
        AgentSource agentSource = new AgentSource();
        agentSource.setEmail( "test@test.com" );
        final String value = "{\"sobject\":{\"Id\":" + "\"" + agentId + "\"" + "},\"event\":{\"type\":" + "\""
                + eventType + "\"" + "}}";

        when( message.getJSON() ).thenReturn( value );
        when( objectMapper.readTree( anyString().getBytes() ) ).thenReturn( root );
        PowerMockito.mockStatic( JsonUtil.class );
        PowerMockito.when( JsonUtil.toJson( any( JsonNode.class ) ) ).thenReturn( value );
        when( lockHandler.acquireLock( anyString() ) ).thenReturn( true );
        when( agentBusinessService.getCRMAgentById( agentId ) ).thenReturn( crmAgentResponse );
        when( agentSourceBuilder.convertTo( crmAgentResponse ) ).thenReturn( agentSource );
        when( systemHealthJmxConfig.getSystemHealthAgentEmail() ).thenReturn( "testSystemHealthEmail" );
        doNothing().when( agentCreateMessageGateway ).publishAgentUpdate( any( AgentSource.class ) );

        agentCreateTopicListener.onMessage( clientSessionChannel, message );

        verify( lockHandler ).acquireLock( anyString() );
        verify( agentBusinessService ).getCRMAgentById( agentId );
        verify( systemHealthJmxConfig ).getSystemHealthAgentEmail();
        verifyZeroInteractions( systemHealthBusinessService );
        verify( agentCreateMessageGateway, Mockito.times( 0 ) ).publishAgentCreate( any( AgentSource.class ) );
        verifyZeroInteractions( agentBusinessService );
        verify( agentCreateMessageGateway ).publishAgentUpdate( any( AgentSource.class ) );
        verifyNoMoreInteractions( agentCreateMessageGateway );
    }
}
