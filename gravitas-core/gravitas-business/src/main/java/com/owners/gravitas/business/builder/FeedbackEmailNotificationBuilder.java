package com.owners.gravitas.business.builder;

import static com.owners.gravitas.config.FeedbackEmailJmxConfig.RATING;
import static com.owners.gravitas.config.FeedbackEmailJmxConfig.RATING1;
import static com.owners.gravitas.config.FeedbackEmailJmxConfig.RATING2;
import static com.owners.gravitas.config.FeedbackEmailJmxConfig.RATING3;
import static com.owners.gravitas.config.FeedbackEmailJmxConfig.RATING4;
import static com.owners.gravitas.config.FeedbackEmailJmxConfig.RATING5;
import static com.owners.gravitas.config.FeedbackEmailJmxConfig.RATING_ID;
import static com.owners.gravitas.util.EncryptDecryptUtil.generateEmailLink;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.config.FeedbackEmailJmxConfig;
import com.owners.gravitas.service.AgentService;

/**
 * The Class FeedbackEmailNotificationBuilder.
 * 
 * @author ankusht
 */
@Component
public class FeedbackEmailNotificationBuilder extends AgentEmailNotificationBuilder {

    /** The feedback email jmx config. */
    @Autowired
    private FeedbackEmailJmxConfig feedbackEmailJmxConfig;

    /** The agent service. */
    @Autowired
    private AgentService agentService;

    /**
     * Creates the email notification.
     *
     * @param recipient
     *            the recipient
     * @param agentEmail
     *            the agent email
     * @param emailTemplate
     *            the email template
     * @param agentRatingId
     *            the agent rating id
     * @return the email notification
     */
    public EmailNotification createEmailNotification( final String recipient, final String agentEmail,
            final String emailTemplate, final String agentRatingId ) {
        final Map< String, Object > sourceMap = buildSourceMap( recipient, agentEmail );
        final EmailNotification notification = convertTo( sourceMap, null );
        notification.setMessageTypeName( emailTemplate );
        notification.getEmail().setReplyToEmail( feedbackEmailJmxConfig.getNoReplyOwnersEmailAddress() );
        notification.getEmail().getParameterMap().putAll( getFeedbackUrlMap( agentRatingId ) );
        return notification;
    }

    /**
     * Builds the source map.
     *
     * @param recipient
     *            the recipient
     * @param agentEmail
     *            the email
     * @return the map
     */
    private Map< String, Object > buildSourceMap( final String recipient, final String agentEmail ) {
        final Map< String, Object > sourceMap = new HashMap<>();
        final Map< String, Object > response = agentService.getAgentDetails( agentEmail );
        sourceMap.put( FROM_EMAIL, feedbackEmailJmxConfig.getFeedbackFromEmail() );
        sourceMap.put( NAME, response.get( "Name" ) );
        sourceMap.put( TO_EMAIL, recipient );
        return sourceMap;
    }

    /**
     * Gets the feedback url map.
     *
     * @param agentRatingId
     *            the agent rating
     * @return the feedback url map
     */
    private Map< String, String > getFeedbackUrlMap( final String agentRatingId ) {
        final Map< String, String > urls = new HashMap<>( 5 );
        urls.put( RATING1, encryptUrl( agentRatingId, "1" ) );
        urls.put( RATING2, encryptUrl( agentRatingId, "2" ) );
        urls.put( RATING3, encryptUrl( agentRatingId, "3" ) );
        urls.put( RATING4, encryptUrl( agentRatingId, "4" ) );
        urls.put( RATING5, encryptUrl( agentRatingId, "5" ) );
        return urls;
    }

    /**
     * Encrypt url.
     *
     * @param agentRatingId
     *            the agent rating id
     * @param rating
     *            the rating
     * @return the string
     */
    private String encryptUrl( final String agentRatingId, final String rating ) {
        final Map< String, String > values = new HashMap< String, String >();
        values.put( RATING_ID, agentRatingId );
        values.put( RATING, rating );
        return generateEmailLink( feedbackEmailJmxConfig.getFeedbackHostUrl(), values, EMPTY );
    }
}
