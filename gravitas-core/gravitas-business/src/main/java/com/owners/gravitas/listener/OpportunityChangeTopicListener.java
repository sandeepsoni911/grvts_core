package com.owners.gravitas.listener;

import static com.owners.gravitas.enums.EventType.UPDATE;

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
import com.owners.gravitas.gateway.OpportunityChangeMessageGateway;
import com.owners.gravitas.lock.TopicListenerLockHandler;
import com.owners.gravitas.service.HostService;

@Component
public class OpportunityChangeTopicListener extends TopicMessageListener {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( OpportunityChangeTopicListener.class );

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
            LOGGER.info( "Opportunity change Before lock : {}", dataNode );
            final String lockName = String.valueOf( dataNode.hashCode() );
            final EventType eventType = getEventType( dataNode );
            LOGGER.info( "Event type received is: {}, lock name is: {} and host name is: {}", eventType, lockName, hostService.getHost() );
            final String opportunityId = getObjectId( dataNode );
            LOGGER.info( "The opportunity id is: {}", opportunityId );
            if (UPDATE == eventType) {
                if (lockHandler.acquireLock( lockName )) {
                    final OpportunitySource opportunity = opportunityBusinessService.getOpportunity( opportunityId );
                    opportunity.setEventType( eventType );
                    final List< String > emailIds = opportunity.getPrimaryContact().getEmails();
                    if (emailIds.contains( systemHealthJmxConfig.getSystemHealthOpportunityEmail() )) {
                        systemHealthBusinessService.listenToOpportunityChangeTopic( opportunity );
                    } else if (canProcess( emailIds, opportunity.getLastModifiedBy() )) {
                        // Don't push changes to the queue if update is from
                        // gravitas api user
                        LOGGER.info( "Event: {}, Agent: {} is assigned to opportunity: {}", opportunity.getEventType(), opportunity.getAgentEmail()
                                , opportunity.getCrmId() );
                        opportunityChangeMessageGateway.publishOpportunityChange( opportunity );
                        LOGGER.info( "Message pushed into OpportunitySource queue : {}", opportunityId );
                    } else {
                        LOGGER.info("Not sending data to Opportunity change queue : {}", opportunity.getCrmId());
                    }
                    LOGGER.debug( " Opportunity change Message processed " );
                } else {
                    LOGGER.info( "Could not acquire lock on lock name: {} and opportunity id: {}", lockName, opportunityId );
                }
            }
        } catch ( HttpServerErrorException | HttpClientErrorException ex ) {
            LOGGER.error( "Error in Opportunity change listener : {}, response : {}", dataNode, ex.getResponseBodyAsString(), ex );
            throw ex;
        } catch ( Exception ex) {
            LOGGER.error( "Error in Opportunity change listener : {}", dataNode, ex );
            throw ex;
        }
    }
}
