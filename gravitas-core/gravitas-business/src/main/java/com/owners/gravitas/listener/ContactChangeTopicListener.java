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

import com.owners.gravitas.amqp.OpportunityContact;
import com.owners.gravitas.business.OpportunityBusinessService;
import com.owners.gravitas.business.SystemHealthBusinessService;
import com.owners.gravitas.config.SystemHealthJmxConfig;
import com.owners.gravitas.enums.EventType;
import com.owners.gravitas.gateway.ContactChangeMessageGateway;
import com.owners.gravitas.lock.TopicListenerLockHandler;

@Component
public class ContactChangeTopicListener extends TopicMessageListener {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( ContactChangeTopicListener.class );

    /** The lead message gateway. */
    @Autowired
    private ContactChangeMessageGateway contactChangeMessageGateway;

    /** The job synchronization. */
    @Autowired
    private TopicListenerLockHandler lockHandler;

    /** The opportunity business service. */
    @Autowired
    private OpportunityBusinessService opportunityBusinessService;

    /** The system health business service. */
    @Autowired
    private SystemHealthBusinessService systemHealthBusinessService;

    /** The system health jmx config. */
    @Autowired
    private SystemHealthJmxConfig systemHealthJmxConfig;

    /*
     * (non-Javadoc)
     * @see
     * org.cometd.bayeux.client.ClientSessionChannel.MessageListener#onMessage(
     * org.cometd.bayeux.client.ClientSessionChannel, org.cometd.bayeux.Message)
     */
    @Override
    public void onMessage( final ClientSessionChannel subscriptionChannel, final Message message ) {
        String dataNode = null;
        try {
            dataNode = getDataNode( message );
            LOGGER.info( "Contact change Before lock : {}", dataNode );
            final String lockName = String.valueOf( dataNode.hashCode() );
            if (lockHandler.acquireLock( lockName )) {
                final String jsonData = createCustomJSON( dataNode );
                final String crmId = getObjectId( jsonData );
                final EventType eventType = getEventType( dataNode );
                LOGGER.info( "contact change message received for" + crmId );
                final OpportunityContact primaryContact = opportunityBusinessService.findContactById( crmId, "contactId" );
                final List< String > emailIds = primaryContact.getPrimaryContact().getEmails();
                if (emailIds.contains( systemHealthJmxConfig.getSystemHealthOpportunityEmail() )) {
                    systemHealthBusinessService.listenToContactChangeTopic( primaryContact );
                } else if (eventType.equals( UPDATE )
                        && canProcess( emailIds, primaryContact.getPrimaryContact().getLastModifiedBy() )) {
                    LOGGER.info( "Event: {} , Agent: {} is assigned to contact:", eventType, primaryContact.getAgentEmail()
                            , primaryContact.getPrimaryContact().getCrmId() );
                    contactChangeMessageGateway.publishContactChange( primaryContact );
                    LOGGER.info( "Message pushed into Contact change queue. " );
                } else {
                    LOGGER.info( "Not Processing Contact change message : {}", dataNode );
                }
                LOGGER.debug( " Contact change Message processed " );
            }
        } catch ( HttpServerErrorException | HttpClientErrorException ex ) {
            LOGGER.error( "Error while running Contact change listener : {}, response : {}", dataNode, ex.getResponseBodyAsString(), ex );
            throw ex;
        } catch ( Exception ex) {
            LOGGER.error( "Error while running Contact change listener : {}", dataNode, ex );
            throw ex;
        }
    }

}
