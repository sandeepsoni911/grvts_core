package com.owners.gravitas.business.builder;

import static com.owners.gravitas.constants.Constants.NOTIFICATION_CLIENT_ID;
import static com.owners.gravitas.constants.NotificationParameters.AFFILIATE_EMAIL_ERROR_LOG;
import static com.owners.gravitas.constants.NotificationParameters.AFFILIATE_EMAIL_ERROR_SOURCE;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hubzu.notification.dto.client.email.Email;
import com.hubzu.notification.dto.client.email.EmailNotification;
import com.hubzu.notification.dto.client.email.EmailRecipients;
import com.owners.gravitas.dto.AffiliateEmailAttribute;
import com.owners.gravitas.dto.request.GenericLeadRequest;

/**
 * The Class AffiliateErrorNotificationBuilder.
 *
 * @author amits
 */
@Component( "affiliateErrorNotificationBuilder" )
public class AffiliateErrorNotificationBuilder extends AbstractBuilder< AffiliateEmailAttribute, EmailNotification > {

    /**
     * This method converts {@link String} to {@link EmailNotification}.
     *
     * @param source
     *            {@link GenericLeadRequest}
     * @param destination
     *            {@link EmailNotification}.
     *
     * @return returns instance of {@link EmailNotification}.
     */
    @Override
    public EmailNotification convertTo( AffiliateEmailAttribute source, EmailNotification destination ) {
        EmailNotification emailNotification = destination;
        if (source != null) {
            if (emailNotification == null) {
                emailNotification = new EmailNotification();
            }
            final Email email = new Email();
            email.setFromEmail( source.getNotificationFrom() );
            email.setFromDisplayName( "" );
            emailNotification.setMessageTypeName( source.getMessageTypeName() );
            emailNotification.setCreatedOn( new Timestamp( System.currentTimeMillis() ) );
            emailNotification.setClientId( NOTIFICATION_CLIENT_ID );

            final Map< String, String > parameterMap = new HashMap< >();
            parameterMap.put( AFFILIATE_EMAIL_ERROR_LOG, source.getErrorLog() );
            parameterMap.put( AFFILIATE_EMAIL_ERROR_SOURCE, source.getErrorSource() );
            email.setParameterMap( parameterMap );

            final EmailRecipients recipients = new EmailRecipients();
            final List< String > toList = new ArrayList< >();
            toList.add( source.getNotificationTo() );
            recipients.setToList( toList );
            email.setRecipients( recipients );
            emailNotification.setEmail( email );
        }
        return emailNotification;
    }

    /**
     * This method converts {@link EmailNotification} to
     * {@link GenericLeadRequest}.
     *
     * @param source
     *            {@link EmailNotification}.
     *
     * @param destination
     *            {@link GenericLeadRequest}.
     *
     * @return returns instance of {@link GenericLeadRequest}.
     */
    @Override
    public AffiliateEmailAttribute convertFrom( EmailNotification destination, AffiliateEmailAttribute source ) {
        throw new UnsupportedOperationException( "convertFrom operation is not supported" );
    }

}
