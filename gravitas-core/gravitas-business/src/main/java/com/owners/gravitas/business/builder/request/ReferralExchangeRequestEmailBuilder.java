package com.owners.gravitas.business.builder.request;

import static com.owners.gravitas.constants.Constants.NOTIFICATION_CLIENT_ID;
import static com.owners.gravitas.constants.Constants.REFRRAL_EMAIL_NOTIFICATION_MESSAGE_TYPE_NAME;
import static com.owners.gravitas.constants.NotificationParameters.FULL_NAME;

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
import com.owners.gravitas.business.builder.AbstractBuilder;

/**
 * The Class ReferralExchangeRequestEmailBuilder.
 *
 * @author shivamm
 */
@Component( "referralExchangeRequestEmailBuilder" )
public class ReferralExchangeRequestEmailBuilder extends AbstractBuilder< String, EmailNotification > {

    /** The agent name. */
    @Value( "${referral.exchange.email.notification.display.name}" )
    private String displayName;

    /** The agent first name. */
    @Value( "${buyer.notification.agent.firstName}" )
    private String agentFirstName;

    /** The agent email. */
    @Value( "${buyer.notification.agent.email}" )
    private String agentEmail;

    /** The afba Owners Email. */
    @Value( "${buyer.notification.afbaOwners.email}" )
    private String afbaOwnersEmail;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public EmailNotification convertTo( final String name, final EmailNotification destination ) {
        throw new UnsupportedOperationException( "convertTo operation is not supported try the overloaded version" );
    }

    /**
     * Convert to.
     *
     * @param fullName
     *            the full name
     * @param emailList
     *            the email list
     * @return the email notification
     */
    public EmailNotification convertTo( final String fullName, final List< String > emailList ) {
        final EmailNotification emailNotification = new EmailNotification();
        final Email email = new Email();
        email.setFromEmail( agentEmail );
        email.setFromDisplayName( displayName );

        emailNotification.setMessageTypeName( REFRRAL_EMAIL_NOTIFICATION_MESSAGE_TYPE_NAME );
        emailNotification.setCreatedOn( new Timestamp( System.currentTimeMillis() ) );
        emailNotification.setClientId( NOTIFICATION_CLIENT_ID );

        final Map< String, String > parameterMap = new HashMap<>();
        parameterMap.put( FULL_NAME, fullName );

        email.setParameterMap( parameterMap );

        final EmailRecipients recipients = new EmailRecipients();
        final List< String > toList = new ArrayList<>();
        toList.addAll( emailList );
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
    public String convertFrom( final EmailNotification source, final String destination ) {
        throw new UnsupportedOperationException( "convertFrom operation is not supported" );
    }
}
