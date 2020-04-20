package com.owners.gravitas.listener;

import static com.owners.gravitas.enums.EventType.UPDATE;
import static org.apache.commons.lang3.StringUtils.isBlank;

import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.LeadBusinessService;
import com.owners.gravitas.business.SystemHealthBusinessService;
import com.owners.gravitas.config.SystemHealthJmxConfig;
import com.owners.gravitas.dto.crm.response.CRMLeadResponse;
import com.owners.gravitas.gateway.LeadMessageGateway;
import com.owners.gravitas.lock.TopicListenerLockHandler;

/**
 * Message listener for subscribed topic.
 *
 * @author amits
 */
@Component
public class LeadTopicMessageListener extends TopicMessageListener {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( LeadTopicMessageListener.class );

    /** The lead message gateway. */
    @Autowired
    private LeadMessageGateway topicMessageGateway;

    /** The job synchronization. */
    @Autowired
    private TopicListenerLockHandler lockHandler;

    /** The lead business service. */
    @Autowired
    private LeadBusinessService leadBusinessService;

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
        try {
            final String dataNode = getDataNode( message );
            LOGGER.info("Lead Change Request Data : {}", dataNode);
            final String lockName = String.valueOf( dataNode.hashCode() );
            if (UPDATE.equals( getEventType( dataNode ) )) {
                if (lockHandler.acquireLock( lockName )) {
                    final String leadId = getObjectId( dataNode );
                    final CRMLeadResponse crmResponse = leadBusinessService.getCRMLead( leadId );
                    final LeadSource leadSource = new LeadSource();
                    BeanUtils.copyProperties( crmResponse, leadSource, "lastVisitDateTime", "listingCreationDate" );
                    final String emailId = leadSource.getEmail();
                    if (systemHealthJmxConfig.getSystemHealthLeadEmail().equals( emailId )
                            && isBlank( leadSource.getConvertedDate() )) {
                        systemHealthBusinessService.listenToLeadChangeTopic( leadSource );
                    } else if (!isSystemHealthCheckupEmail( emailId )) {
                        topicMessageGateway.publishLeadUpdate( leadSource );
                        LOGGER.info( "Message pushed into Lead queue: {}", leadId );
                    } else {
                        LOGGER.info("Not sending data to lead queue data: {}", dataNode);
                    }
                }
                LOGGER.debug( " Lead change Message processed " );
            }
        } catch (Exception ex){
            LOGGER.error( "Error while Lead Change Topic : {}", ex.getMessage(), ex );
            throw ex;
        }
    }

}
