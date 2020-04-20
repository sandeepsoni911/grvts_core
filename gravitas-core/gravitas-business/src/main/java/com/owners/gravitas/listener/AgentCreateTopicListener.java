package com.owners.gravitas.listener;

import static com.owners.gravitas.enums.EventType.ADD;
import static com.owners.gravitas.enums.EventType.UPDATE;

import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.owners.gravitas.amqp.AgentSource;
import com.owners.gravitas.business.AgentBusinessService;
import com.owners.gravitas.business.SystemHealthBusinessService;
import com.owners.gravitas.business.builder.AgentSourceBuilder;
import com.owners.gravitas.config.SystemHealthJmxConfig;
import com.owners.gravitas.dto.crm.response.CRMAgentResponse;
import com.owners.gravitas.enums.EventType;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.gateway.AgentCreateMessageGateway;
import com.owners.gravitas.lock.TopicListenerLockHandler;

/**
 * The listener interface for receiving agentCreateTopic events.
 * The class that is interested in processing a agentCreateTopic
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addAgentCreateTopicListener<code> method. When
 * the agentCreateTopic event occurs, that object's appropriate
 * method is invoked.
 *
 * @see AgentCreateTopicEvent
 *
 * @author vishwanathm
 */
@Component
public class AgentCreateTopicListener extends TopicMessageListener {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentCreateTopicListener.class );

    /** The lead message gateway. */
    @Autowired
    private AgentCreateMessageGateway agentCreateMessageGateway;

    /** The job synchronization. */
    @Autowired
    private TopicListenerLockHandler lockHandler;

    /** The system health business service. */
    @Autowired
    private SystemHealthBusinessService systemHealthBusinessService;

    /** The system health jmx config. */
    @Autowired
    private SystemHealthJmxConfig systemHealthJmxConfig;

    /** The agent business service. */
    @Autowired
    private AgentBusinessService agentBusinessService;

    /** The agent source builder. */
    @Autowired
    private AgentSourceBuilder agentSourceBuilder;

    /*
     * (non-Javadoc)
     * @see
     * org.cometd.bayeux.client.ClientSessionChannel.MessageListener#onMessage(
     * org.cometd.bayeux.client.ClientSessionChannel, org.cometd.bayeux.Message)
     */
    @Override
    public void onMessage( final ClientSessionChannel subscriptionChannel, final Message message ) {
        try {
            LOGGER.debug( "Agent create Before redis lock." );
            final String dataNode = getDataNode( message );
            final String lockName = String.valueOf( dataNode.hashCode() );
            if (lockHandler.acquireLock( lockName )) {
                final String crmAgentId = getObjectId( dataNode );
                final CRMAgentResponse crmAgentResponse = agentBusinessService.getCRMAgentById( crmAgentId );
                final AgentSource agentSource = agentSourceBuilder.convertTo( crmAgentResponse );
                final EventType eventType = getEventType( dataNode );
                final String emailId = agentSource.getEmail();
                LOGGER.debug( "Agent create message received for " + emailId );
                if (systemHealthJmxConfig.getSystemHealthAgentEmail().equals( emailId )) {
                    systemHealthBusinessService.listenToAgentCreateTopic();
                } else if (!isSystemHealthCheckupEmail( emailId )) {
                    if (ADD == eventType) {
                        LOGGER.info( "Agent update message received for " + agentSource.getEmail() );
                        agentCreateMessageGateway.publishAgentCreate( agentSource );
                        LOGGER.info( "Message pushed into Agent create queue." );
                    } else if (UPDATE == eventType) {
                        LOGGER.info( "Agent update message received for " + agentSource.getEmail() );
                        agentCreateMessageGateway.publishAgentUpdate( agentSource );
                        LOGGER.info( "Message pushed into Agent update queue." );
                    }
                }
                LOGGER.debug( "Agent Create Message processed" );
            }
        } catch ( HttpServerErrorException | HttpClientErrorException ex ) {
            LOGGER.error( ex.getResponseBodyAsString() );
            throw ex;
        } catch ( ApplicationException ex ) {
            LOGGER.error( ex.getMessage(), ex );
            throw ex;
        }
    }
}
