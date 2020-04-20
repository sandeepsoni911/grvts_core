package com.owners.gravitas.config;

import static com.owners.gravitas.enums.TopicName.AGENT;
import static com.owners.gravitas.enums.TopicName.CONTACT;
import static com.owners.gravitas.enums.TopicName.LEAD;
import static com.owners.gravitas.enums.TopicName.LEAD_CREATE;
import static com.owners.gravitas.enums.TopicName.OPPORTUNITY_CHANGE;
import static com.owners.gravitas.enums.TopicName.OPPORTUNITY_CREATE;
import static com.owners.gravitas.enums.TopicStatusType.active;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.cometd.bayeux.Channel;
import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.cometd.bayeux.client.ClientSessionChannel.MessageListener;
import org.cometd.client.BayeuxClient;
import org.cometd.client.transport.ClientTransport;
import org.cometd.client.transport.LongPollingTransport;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.owners.gravitas.dto.crm.response.CRMAccess;
import com.owners.gravitas.enums.ErrorCode;
import com.owners.gravitas.enums.TopicName;
import com.owners.gravitas.event.FailedTopicEvent;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.listener.AgentCreateTopicListener;
import com.owners.gravitas.listener.ContactChangeTopicListener;
import com.owners.gravitas.listener.LeadCreateTopicMessageListener;
import com.owners.gravitas.listener.LeadTopicMessageListener;
import com.owners.gravitas.listener.OpportunityChangeTopicListener;
import com.owners.gravitas.listener.OpportunityCreateTopicListener;
import com.owners.gravitas.service.CRMAuthenticatorService;
import com.owners.gravitas.service.TopicStatusService;
import com.owners.gravitas.util.PropertiesUtil;
import com.owners.gravitas.util.PropertyWriter;

/**
 * Subscribe to Salesforce topic configuration.
 *
 * @author amits
 *
 */
@ManagedResource( objectName = "com.owners.gravitas:name=TopicSubscribscriptionConfig" )
@Component
public class TopicSubscribscriptionConfig implements ApplicationListener< FailedTopicEvent > {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( TopicSubscribscriptionConfig.class );

    /** The subscription endpoint uri. */
    @Value( value = "${salesforce.subscription.endpoint.uri}" )
    private String subscriptionEndpointUri;

    /** The handshake timeout seconds. */
    @Value( value = "${salesforce.subscription.handshake.check.timeout}" )
    private String handshakeTimeoutSeconds;

    /** The enable topic listener. */
    @Value( "${salesforce.topic.listener.enabled: true}" )
    private boolean enableTopicListener;

    /** CRMauthenticator for authentication. */
    @Autowired
    private CRMAuthenticatorService crmAuthenticatorService;

    /** The lead topic message listener. */
    @Autowired
    private LeadTopicMessageListener leadTopicMessageListener;

    /** The lead create topic message listener. */
    @Autowired
    private LeadCreateTopicMessageListener leadCreateTopicMessageListener;

    /** The agent opportunity topic message listener. */
    @Autowired
    private OpportunityChangeTopicListener opportunityChangeTopicListener;

    /** The contact change topic listener. */
    @Autowired
    private ContactChangeTopicListener contactChangeTopicListener;

    /** The Opportunity create topic listener. */
    @Autowired
    private OpportunityCreateTopicListener opportunityCreateTopicListener;

    /** The agent create topic listener. */
    @Autowired
    private AgentCreateTopicListener agentCreateTopicListener;

    /** The property writer. */
    @Autowired
    private PropertyWriter propertyWriter;

    /** The topic status service. */
    @Autowired
    private TopicStatusService topicStatusService;

    /** The bayeux client. */
    private BayeuxClient bayeuxClient;

    /**
     * Handshake.
     */
    @PostConstruct
    private void handshake() {
        if (enableTopicListener) {
            LOGGER.info( "Subscribing to all topics on salesforce" );
            try {
                bayeuxClient = getBayeuxClient();
                bayeuxClient.getChannel( Channel.META_HANDSHAKE ).addListener( new ClientSessionChannel.MessageListener() {
                    @Override
                    public void onMessage( final ClientSessionChannel channel, final Message message ) {
                        LOGGER.info( "In meta handshake" );
                        if (enableTopicListener && channel.getSession().isHandshook()) {
                            LOGGER.info( "Handshake done, now subscribing" );
                            subscribe( bayeuxClient, getProperty( LEAD ), leadTopicMessageListener );
                            subscribe( bayeuxClient, getProperty( OPPORTUNITY_CHANGE ), opportunityChangeTopicListener );
                            subscribe( bayeuxClient, getProperty( OPPORTUNITY_CREATE ), opportunityCreateTopicListener );
                            subscribe( bayeuxClient, getProperty( CONTACT ), contactChangeTopicListener );
                            subscribe( bayeuxClient, getProperty( AGENT ), agentCreateTopicListener );
                            subscribe( bayeuxClient, getProperty( LEAD_CREATE ), leadCreateTopicMessageListener );
                        }
                    }
                } );
    
                bayeuxClient.handshake();
                LOGGER.info("Started Handshake");
                final boolean handshaken = bayeuxClient.waitFor( Long.parseLong( handshakeTimeoutSeconds ) * 1000,
                        BayeuxClient.State.CONNECTED );
                LOGGER.info("Handshake done : {}", handshaken);
                if (!handshaken) {
                    LOGGER.error( "Failed to handshake: " + bayeuxClient );
                    throw new ApplicationException( "Topic subscription handshake failed.",
                            ErrorCode.TOPIC_HANDSHAKE_FAILED );
                }
            } catch (Exception e){
                LOGGER.error("Error while subscribing to Salesforce Topics", e);
            }
        }
    }

    /**
     * Gets the bayeux client.
     *
     * @return the bayeux client
     */
    private BayeuxClient getBayeuxClient() {
        final CRMAccess crmAccess = crmAuthenticatorService.authenticate();
        LOGGER.info( "Handshake to saleforce topic." );
        final SslContextFactory sslContextFactory = new SslContextFactory();
        final HttpClient httpClient = new HttpClient(sslContextFactory);
        httpClient.setFollowRedirects(false);

        try {
            httpClient.start();
        } catch ( final Exception e ) {
            LOGGER.error("Exception while getting BayeuxClient", e);
            throw new ApplicationException( e.getMessage(), e, ErrorCode.TOPIC_HANDSHAKE_FAILED );
        }
        final ClientTransport clientTransport = new LongPollingTransport( new HashMap< String, Object >(),
                httpClient ) {
            @Override
            protected void customize( final Request exchange ) {
                LOGGER.info("Authenticating for topics");
                super.customize( exchange );
                final HttpField field = new HttpField("Authorization",
                        "OAuth " + crmAuthenticatorService.authenticate().getAccessToken());
                exchange.getHeaders().add(field);
            }
        };
        LOGGER.info("Returning BayeuxClient");
        return new BayeuxClient( crmAccess.getInstanceUrl() + subscriptionEndpointUri, clientTransport );
    }

    /**
     * Subscribe the Salesforce topic.
     *
     * @param bayeuxClient
     *            the bayeux client
     * @param topicName
     *            the topic name
     * @param topicMessageListener
     *            the topic message listener
     */
    private void subscribe( final BayeuxClient bayeuxClient, final String topicName,
            final MessageListener topicMessageListener ) {
        LOGGER.info( "Subscribe to saleforce topic : " + topicName );
        try {
            final ClientSessionChannel subscriptionChannel = bayeuxClient.getChannel( topicName );
            subscriptionChannel.subscribe( topicMessageListener );
            LOGGER.info( "Subscribe done : {}", topicName );
        } catch (Exception e){
            LOGGER.error("Error while subscribing to Salesforce Topic : {}", topicName, e);
        }
    }

    /**
     * Unsubscribe the Salesforce topic.
     *
     * @param bayeuxClient
     *            the bayeux client
     * @param topicName
     *            the topic name
     * @param topicMessageListener
     *            the topic message listener
     */
    private void unsubscribe( final BayeuxClient bayeuxClient, final String topicName,
            final MessageListener topicMessageListener ) {
        LOGGER.info( "Unsubscribe from saleforce topic : " + topicName );
        final ClientSessionChannel subscriptionChannel = bayeuxClient.getChannel( topicName );
        subscriptionChannel.unsubscribe( topicMessageListener );
        LOGGER.info( "Unsubscribe done : {}", topicName );
    }

    /**
     * Subscribe all topic.
     */
    @ManagedOperation
    public void subscribeAllTopic() {
        if (!enableTopicListener) {
            enableTopicListener = true;
            handshake();
        }
    }

    /**
     * Unsubscribe all topic.
     */
    @ManagedOperation
    public void unsubscribeAllTopic() {
        if (enableTopicListener) {
            LOGGER.info("Subsubscribing the topics");
            unsubscribe( bayeuxClient, getProperty( LEAD ), leadTopicMessageListener );
            unsubscribe( bayeuxClient, getProperty( OPPORTUNITY_CHANGE ), opportunityChangeTopicListener );
            unsubscribe( bayeuxClient, getProperty( OPPORTUNITY_CREATE ), opportunityCreateTopicListener );
            unsubscribe( bayeuxClient, getProperty( CONTACT ), contactChangeTopicListener );
            unsubscribe( bayeuxClient, getProperty( AGENT ), agentCreateTopicListener );
            unsubscribe( bayeuxClient, getProperty( LEAD_CREATE ), leadCreateTopicMessageListener );
            bayeuxClient.disconnect();
            bayeuxClient.abort();
            bayeuxClient = null;
            enableTopicListener = false;
        }
    }

    /**
     * Checks if is enable topic listener.
     *
     * @return true, if is enable topic listener
     */
    @ManagedAttribute
    public boolean isEnableTopicListener() {
        return enableTopicListener;
    }

    /**
     * Sets the enable topic listener.
     *
     * @param enableTopicListener
     *            the new enable topic listener
     */
    @ManagedAttribute
    public void setEnableTopicListener( final boolean enableTopicListener ) {
        this.enableTopicListener = enableTopicListener;
        propertyWriter.saveJmxProperty( "salesforce.topic.listener.enabled", enableTopicListener );
    }

    /**
     * On application event.
     *
     * @param event
     *            the event
     */
    @Override
    public void onApplicationEvent( final FailedTopicEvent event ) {
        subscribe( event.getFailedTopicName() );
    }

    /**
     * subscribe the topic.
     *
     * @param topicName
     *            the topic name
     */
    private void subscribe( final TopicName topicName ) {
        MessageListener topicMessageListener = null;
        switch ( topicName ) {
            case LEAD:
                topicMessageListener = leadTopicMessageListener;
                break;
            case OPPORTUNITY_CHANGE:
                topicMessageListener = opportunityChangeTopicListener;
                break;
            case OPPORTUNITY_CREATE:
                topicMessageListener = opportunityCreateTopicListener;
                break;
            case CONTACT:
                topicMessageListener = contactChangeTopicListener;
                break;
            case AGENT:
                topicMessageListener = agentCreateTopicListener;
                break;
            case LEAD_CREATE:
                topicMessageListener = leadCreateTopicMessageListener;
                break;
            default:
                LOGGER.info( "Invalid topic " + topicName + " used for resubscription" );
                break;
        }
        if (topicMessageListener != null && bayeuxClient != null) {
            subscribe( bayeuxClient, getProperty( topicName ), topicMessageListener );
            topicStatusService.updateTopicStatus( topicName.getName(), active.name() );
            LOGGER.info( "Resubscribed " + topicName.getName() + " successfully" );
        }
    }

    /**
     * Unsubscribe the topic.
     *
     * @param topic
     *            the topic
     */
    @ManagedOperation
    public void unsubscribe( final String topic ) {
        MessageListener topicMessageListener = null;
        final TopicName topicName = TopicName.valueOf( topic );
        switch ( topicName ) {
            case LEAD:
                topicMessageListener = leadTopicMessageListener;
                break;
            case OPPORTUNITY_CHANGE:
                topicMessageListener = opportunityChangeTopicListener;
                break;
            case OPPORTUNITY_CREATE:
                topicMessageListener = opportunityCreateTopicListener;
                break;
            case CONTACT:
                topicMessageListener = contactChangeTopicListener;
                break;
            case AGENT:
                topicMessageListener = agentCreateTopicListener;
                break;
            case LEAD_CREATE:
                topicMessageListener = leadCreateTopicMessageListener;
                break;
            default:
                LOGGER.info( "Invalid topic name provided for subscribe : {}", topic );
                break;
        }
        if (topicMessageListener != null && bayeuxClient != null) {
            unsubscribe( bayeuxClient, getProperty( topicName ), topicMessageListener );
            LOGGER.info( "Unsubscribed " + topicName.getName() + " successfully" );
        }
    }

    /**
     * Gets the property.
     *
     * @param topicName
     *            the topic name
     * @return the property
     */
    private String getProperty( final TopicName topicName ) {
        return PropertiesUtil.getProperty( topicName.getPropertyName() );
    }
}
