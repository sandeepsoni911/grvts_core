package com.owners.gravitas.business.builder;

import static com.owners.gravitas.constants.Constants.NOTIFICATION_CLIENT_ID;
import static com.owners.gravitas.enums.RecordType.BUYER;
import static com.owners.gravitas.enums.RecordType.SELLER;
import static org.apache.commons.lang.StringUtils.isBlank;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hubzu.notification.dto.client.email.Email;
import com.hubzu.notification.dto.client.email.EmailNotification;
import com.hubzu.notification.dto.client.email.EmailRecipients;
import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.config.ClientFollowupConfig;
import com.owners.gravitas.dto.Contact;

/**
 * The Class ClientFollowUpEmailNotificationBuilder.
 *
 * @author raviz
 */
@Component( "clientFollowUpEmailNotificationBuilder" )
public class ClientFollowUpEmailNotificationBuilder extends AbstractBuilder< OpportunitySource, EmailNotification > {

    /** The Constant BUYER_MESSAGE_TYPE_NAME. */
    private static final String BUYER_MESSAGE_TYPE_NAME = "MKT_POST_TRANSACTION_BUYER";

    /** The Constant SELLER_MESSAGE_TYPE_NAME. */
    private static final String SELLER_MESSAGE_TYPE_NAME = "MKT_POST_TRANSACTION_SELLER";

    /** The Constant RECEIVER_NAME. */
    private static final String RECEIVER_NAME = "NAME";

    /** The client followup config. */
    @Autowired
    private ClientFollowupConfig clientFollowupConfig;

    /**
     * This method converts {@link OpportunitySource} to
     * {@link EmailNotification}.
     *
     * @param source
     *            {@link OpportunitySource}
     * @param destination
     *            {@link EmailNotification}.
     *
     * @return returns instance of {@link EmailNotification}.
     */
    @Override
    public EmailNotification convertTo( final OpportunitySource source, final EmailNotification destination ) {
        EmailNotification emailNotification = destination;
        if (source != null) {
            if (emailNotification == null) {
                emailNotification = new EmailNotification();
            }
            final Email email = new Email();
            email.setFromEmail( source.getOpportunityOwnerEmail() );
            final String recordType = source.getOpportunityType();
            if (BUYER.getType().equals( recordType )) {
                emailNotification.setMessageTypeName( BUYER_MESSAGE_TYPE_NAME );
            } else if (SELLER.getType().equals( recordType )) {
                emailNotification.setMessageTypeName( SELLER_MESSAGE_TYPE_NAME );
            }
            emailNotification.setCreatedOn( new Timestamp( System.currentTimeMillis() ) );
            emailNotification.setClientId( NOTIFICATION_CLIENT_ID );
            emailNotification.setTriggerOn( new DateTime( System.currentTimeMillis() )
                    .plusMinutes( clientFollowupConfig.getClientFollowUpMailDelayMinutes() ).getMillis() );

            final Map< String, String > parameterMap = new HashMap<>();
            parameterMap.put( RECEIVER_NAME, getReceiverName( source.getPrimaryContact() ) );
            email.setParameterMap( parameterMap );

            final EmailRecipients recipients = new EmailRecipients();
            recipients.setToList( source.getPrimaryContact().getEmails() );
            email.setRecipients( recipients );
            emailNotification.setEmail( email );
        }
        return emailNotification;
    }

    /**
     * This method converts {@link EmailNotification} to
     * {@link OpportunitySource}.
     *
     * @param source
     *            {@link EmailNotification}
     * @param destination
     *            {@link OpportunitySource}.
     *
     * @return returns instance of {@link OpportunitySource}.
     */
    @Override
    public OpportunitySource convertFrom( final EmailNotification source, final OpportunitySource destination ) {
        throw new UnsupportedOperationException( "convertFrom operation is not supported" );
    }

    /**
     * Gets the receiver name.
     *
     * @param contact
     *            the contact
     * @return the receiver name
     */
    private String getReceiverName( final Contact contact ) {
        return isBlank( contact.getFirstName() ) ? contact.getLastName() : contact.getFirstName();
    }
}
