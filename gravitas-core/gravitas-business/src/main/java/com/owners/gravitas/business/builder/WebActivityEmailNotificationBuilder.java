package com.owners.gravitas.business.builder;

import static com.owners.gravitas.constants.Constants.NOTIFICATION_CLIENT_ID;
import static java.lang.System.currentTimeMillis;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hubzu.notification.dto.client.email.Email;
import com.hubzu.notification.dto.client.email.EmailNotification;
import com.hubzu.notification.dto.client.email.EmailRecipients;
import com.owners.gravitas.constants.NotificationParameters;

/**
 * The Class WebActivityEmailNotificationBuilder.
 * 
 * @author pabhishek
 */
@Component
public class WebActivityEmailNotificationBuilder extends AbstractBuilder< Map< String, Object >, EmailNotification > {

    /** The agent name. */
    @Value( "${buyer.notification.agent.fullName}" )
    private String agentFullName;

    /** The Constant FROM_EMAIL. */
    private static final String FROM_EMAIL = "fromEmail";

    /** The Constant TO_EMAIL. */
    private static final String TO_EMAIL = "toEmail";

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public EmailNotification convertTo( final Map< String, Object > source, final EmailNotification destination ) {
        EmailNotification emailNotification = destination;
        if (emailNotification == null) {
            emailNotification = new EmailNotification();
        }
        final long now = currentTimeMillis();
        emailNotification.setCreatedOn( new Timestamp( now ) );
        emailNotification.setClientId( NOTIFICATION_CLIENT_ID );
        final Email email = new Email();
        email.setFromEmail( source.get( FROM_EMAIL ).toString() );
        email.setFromDisplayName( agentFullName );
        final Map< String, String > parameterMap = new HashMap<>();
        addToMapIfNotNull( parameterMap, NotificationParameters.SUBJECT, source );
        addToMapIfNotNull( parameterMap, NotificationParameters.BUYER_FIRST_NAME, source );
        addToMapIfNotNull( parameterMap, NotificationParameters.ADDRESS_LINE1_LINE2, source );
        addToMapIfNotNull( parameterMap, NotificationParameters.CITY_STATE_ZIP, source );
        addToMapIfNotNull( parameterMap, NotificationParameters.USER_ID, source );
        addToMapIfNotNull( parameterMap, NotificationParameters.VISITED_DATE, source );
        addToMapIfNotNull( parameterMap, NotificationParameters.LAST_VISITED_DATE, source );
        addToMapIfNotNull( parameterMap, NotificationParameters.TIME_SPENT_ON_SITE_IN_SEC, source );
        email.setParameterMap( parameterMap );
        final EmailRecipients recipients = new EmailRecipients();
        final List< String > toList = new ArrayList<>();
        toList.add( source.get( TO_EMAIL ).toString() );
        recipients.setToList( toList );
        email.setRecipients( recipients );
        emailNotification.setEmail( email );
        return emailNotification;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public Map< String, Object > convertFrom( final EmailNotification source,
            final Map< String, Object > destination ) {
        throw new UnsupportedOperationException( "convertFrom operation is not supported" );
    }

    /**
     * Adds the to map if not null.
     *
     * @param parameterMap
     *            the parameter map
     * @param key
     *            the key
     * @param sourceMap
     *            the source map
     */
    private void addToMapIfNotNull( final Map< String, String > parameterMap, final String key,
            final Map< String, Object > sourceMap ) {
        final Object value = sourceMap.get( key );
        if (value != null) {
            parameterMap.put( key, value.toString() );
        }
    }
}
