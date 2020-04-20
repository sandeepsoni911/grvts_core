package com.owners.gravitas.business.builder;

import static com.owners.gravitas.constants.Constants.NOTIFICATION_CLIENT_ID;
import static com.owners.gravitas.constants.NotificationParameters.AGENT_FULL_NAME;
import static java.lang.System.currentTimeMillis;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hubzu.notification.dto.client.email.Email;
import com.hubzu.notification.dto.client.email.EmailNotification;
import com.hubzu.notification.dto.client.email.EmailRecipients;

/**
 * The Class AgentEmailNotificationBuilder.
 * 
 * @author ankusht
 */
public class AgentEmailNotificationBuilder extends AbstractBuilder< Map< String, Object >, EmailNotification > {

    /** The Constant FROM_EMAIL. */
    protected static final String FROM_EMAIL = "fromEmail";

    /** The Constant NAME. */
    protected static final String NAME = "name";

    /** The Constant TO_EMAIL. */
    protected static final String TO_EMAIL = "toEmail";

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
        final Map< String, String > parameterMap = new HashMap<>();
        parameterMap.put( AGENT_FULL_NAME, source.get( NAME ).toString() );
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

}
