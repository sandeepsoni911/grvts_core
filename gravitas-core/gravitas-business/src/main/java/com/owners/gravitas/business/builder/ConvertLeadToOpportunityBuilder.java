package com.owners.gravitas.business.builder;

import static com.owners.gravitas.constants.Constants.BLANK_SPACE;
import static com.owners.gravitas.constants.Constants.LEAD_TO_OPPORTUNITY_CONVERT_NOTIFICATION_MESSAGE_TYPE_NAME;
import static com.owners.gravitas.constants.Constants.NOTIFICATION_CLIENT_ID;
import static com.owners.gravitas.constants.NotificationParameters.LEAD_FULL_NAME;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hubzu.notification.dto.client.email.Email;
import com.hubzu.notification.dto.client.email.EmailNotification;
import com.hubzu.notification.dto.client.email.EmailRecipients;
import com.owners.gravitas.amqp.OpportunityCreate;
import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.dto.Contact;

/**
 * The Class ConvertLeadToOpportunityBuilder.
 *
 * @author shivamm
 */
@Component( "convertLeadToOpportunityBuilder" )
public class ConvertLeadToOpportunityBuilder extends AbstractBuilder< OpportunitySource, EmailNotification > {

    /** The reply to email. */
    @Value( "${buyer.notification.replyToEmail}" )
    private String replyToEmail;

    /** The afba Owners Email. */
    @Value( "${buyer.notification.afbaOwners.email}" )
    private String afbaOwnersEmail;

    /**
     * This method converts {@link OpportunityCreate} to
     * {@link EmailNotification}.
     *
     * @param source
     *            {@link OpportunityCreate}
     * @param destination
     *            {@link EmailNotification}.
     *
     * @return returns instance of {@link EmailNotification}.
     */
    @Override
    public EmailNotification convertTo( OpportunitySource source, EmailNotification destination ) {
        EmailNotification emailNotification = destination;
        Contact contact = null;
        if (source != null) {
            contact = source.getPrimaryContact();
            if (emailNotification == null) {
                emailNotification = new EmailNotification();
            }
            final Email email = new Email();
            email.setFromEmail( source.getOpportunityOwnerEmail() );
            email.setFromDisplayName( source.getOpportunityOwnerName() );
            email.setReplyToEmail( replyToEmail );

            emailNotification.setMessageTypeName( LEAD_TO_OPPORTUNITY_CONVERT_NOTIFICATION_MESSAGE_TYPE_NAME );
            emailNotification.setCreatedOn( new Timestamp( System.currentTimeMillis() ) );
            emailNotification.setClientId( NOTIFICATION_CLIENT_ID );

            final Map< String, String > parameterMap = new HashMap<>();
            parameterMap.put( LEAD_FULL_NAME, getName( contact.getFirstName(), contact.getLastName() ) );

            email.setParameterMap( parameterMap );

            final EmailRecipients recipients = new EmailRecipients();
            final List< String > toList = new ArrayList<>();
            for ( String emailId : contact.getEmails() ) {
                toList.add( emailId );
            }
            recipients.setToList( toList );
            email.setRecipients( recipients );

            final List< String > bccList = new ArrayList<>();
            bccList.add( afbaOwnersEmail );
            recipients.setBccList( bccList );

            emailNotification.setEmail( email );
        }
        return emailNotification;
    }

    /**
     * Gets the name.
     *
     * @param firstName
     *            the first name
     * @param lastName
     *            the last name
     * @return the name
     */
    private String getName( final String firstName, final String lastName ) {
        String name = lastName;
        if (StringUtils.isNotBlank( firstName )) {
            name = firstName + BLANK_SPACE + lastName;
        }
        return name;
    }

    /**
     * This method converts {@link EmailNotification} to
     * {@link OpportunityCreate}.
     *
     * @param source
     *            {@link EmailNotification}.
     *
     * @param destination
     *            {@link OpportunityCreate}.
     *
     * @return returns instance of {@link OpportunityCreate}.
     */
    @Override
    public OpportunitySource convertFrom( EmailNotification source, OpportunitySource destination ) {
        throw new UnsupportedOperationException( "convertFrom operation is not supported" );
    }
}
