package com.owners.gravitas.listener;

import static com.owners.gravitas.enums.EventType.ADD;

import java.util.List;

import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.OpportunityBusinessService;
import com.owners.gravitas.business.SystemHealthBusinessService;
import com.owners.gravitas.config.SystemHealthJmxConfig;
import com.owners.gravitas.enums.EventType;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.gateway.OpportunityChangeMessageGateway;
import com.owners.gravitas.lock.TopicListenerLockHandler;
import com.owners.gravitas.service.HostService;

/**
 * The listener interface for receiving opportunityCreateTopic events.
 * The class that is interested in processing a opportunityCreateTopic
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addOpportunityCreateTopicListener<code> method. When
 * the opportunityCreateTopic event occurs, that object's appropriate
 * method is invoked.
 *
 * @see OpportunityCreateTopicEvent
 *
 * @author shivamm
 */
@Component
public class OpportunityCreateTopicListener extends TopicMessageListener {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( OpportunityCreateTopicListener.class );

    /** The job synchronization. */
    @Autowired
    private TopicListenerLockHandler lockHandler;

    /** The topic message gateway. */
    @Autowired
    private OpportunityChangeMessageGateway opportunityChangeMessageGateway;

    /** The opportunity business service. */
    @Autowired
    private OpportunityBusinessService opportunityBusinessService;

    /** The system health business service. */
    @Autowired
    private SystemHealthBusinessService systemHealthBusinessService;

    /** The system health jmx config. */
    @Autowired
    private SystemHealthJmxConfig systemHealthJmxConfig;

    /** The host service. */
    @Autowired
    private HostService hostService;

    /*
     * (non-Javadoc)
     * @see
     * org.cometd.bayeux.client.ClientSessionChannel.MessageListener#onMessage(
     * org.cometd.bayeux.client.ClientSessionChannel, org.cometd.bayeux.Message)
     */
    @Override
    public void onMessage( ClientSessionChannel subscriptionChannel, Message message ) {
        String dataNode = null;
        try {
            dataNode = getDataNode( message );
            LOGGER.info( "Opportunity create Before lock : {}", dataNode );
            final String lockName = String.valueOf( dataNode.hashCode() );
            final EventType eventType = getEventType( dataNode );
            LOGGER.info( "Event type received is: {}, lock name is: {} and host name is: {}", eventType, lockName, hostService.getHost() );
            final String opportunityId = getObjectId( dataNode );
            LOGGER.info( "The opportunity id is: {}", opportunityId );
            if (ADD == eventType) {
                if (lockHandler.acquireLock( lockName )) {
                    final OpportunitySource opportunitySource = opportunityBusinessService
                            .getOpportunityCreateDetails( opportunityId );
                    final List< String > emailIds = opportunitySource.getPrimaryContact().getEmails();
                    if (emailIds.contains( systemHealthJmxConfig.getSystemHealthLeadEmail() )) {
                        systemHealthBusinessService.listenToOpportunityCreateTopic( opportunitySource );
                    } else if (canProcess( emailIds )) {
                        opportunityChangeMessageGateway.publishOpportunityCreate( opportunitySource );
                        LOGGER.info( "Message pushed into OpportunityCreate queue : {}", opportunityId);
                    } else {
                        LOGGER.info("Not sending data to Opportunity create queue : {}", opportunityId);
                    }
                    LOGGER.debug( " Opportunity create Message processed " );
                } else {
                    LOGGER.info( "Could not acquire lock on lock name: {} and opportunity id: {}", lockName, opportunityId );
                }
            }
        } catch ( HttpServerErrorException | HttpClientErrorException ex ) {
            LOGGER.error( "Error in Opportunity create listener : {}, response : {}", dataNode, ex.getResponseBodyAsString(), ex );
            throw ex;
        } catch ( Exception ex) {
            LOGGER.error( "Error in Opportunity create listener : {}", dataNode, ex );
            throw ex;
        }
    }
}
