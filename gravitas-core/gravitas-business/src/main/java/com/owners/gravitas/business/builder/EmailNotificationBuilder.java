package com.owners.gravitas.business.builder;

import static com.owners.gravitas.constants.Constants.NOTIFICATION_CLIENT_ID;
import static com.owners.gravitas.constants.Constants.NOTIFICATION_MESSAGE_TYPE_NAME;
import static com.owners.gravitas.constants.NotificationParameters.AGENT_EMAIL;
import static com.owners.gravitas.constants.NotificationParameters.AGENT_FIRST_NAME;
import static com.owners.gravitas.constants.NotificationParameters.AGENT_FULL_NAME;
import static com.owners.gravitas.constants.NotificationParameters.LEAD_FIRST_NAME;

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
import com.owners.gravitas.dto.request.GenericLeadRequest;
import com.owners.gravitas.dto.request.LeadRequest;

/**
 * The Class NotificationRequestBuilder.
 *
 * @author vishwanathm
 */
@Component( "emailNotificationBuilder" )
public class EmailNotificationBuilder extends AbstractBuilder< LeadRequest, EmailNotification > {

    /** The agent name. */
    @Value( "${buyer.notification.agent.fullName}" )
    private String agentFullName;

    @Value( "${buyer.notification.agent.firstName}" )
    private String agentFirstName;

    /** The agent email. */
    @Value( "${buyer.notification.agent.email}" )
    private String agentEmail;

    /**
     * This method converts {@link GenericLeadRequest} to
     * {@link EmailNotification}.
     *
     * @param source
     *            {@link GenericLeadRequest}
     * @param destination
     *            {@link EmailNotification}.
     *
     * @return returns instance of {@link EmailNotification}.
     */
    @Override
    public EmailNotification convertTo( LeadRequest source, EmailNotification destination ) {
        EmailNotification emailNotification = destination;
        if (source != null) {
            if (emailNotification == null) {
                emailNotification = new EmailNotification();
            }
            final Email email = new Email();
            email.setFromEmail( agentEmail );
            email.setFromDisplayName( agentFullName );
            emailNotification.setMessageTypeName( NOTIFICATION_MESSAGE_TYPE_NAME );
            emailNotification.setCreatedOn( new Timestamp( System.currentTimeMillis() ) );
            emailNotification.setClientId( NOTIFICATION_CLIENT_ID );

            final Map< String, String > parameterMap = new HashMap<>();
            parameterMap.put( LEAD_FIRST_NAME, formatName( source ) );
            parameterMap.put( AGENT_FIRST_NAME, agentFirstName );
            parameterMap.put( AGENT_FULL_NAME, agentFullName );
            parameterMap.put( AGENT_EMAIL, agentEmail );
            email.setParameterMap( parameterMap );

            final EmailRecipients recipients = new EmailRecipients();
            final List< String > toList = new ArrayList<>();
            toList.add( source.getEmail() );
            recipients.setToList( toList );
            email.setRecipients( recipients );

            final List< String > bccList = new ArrayList<>();
            bccList.add( agentEmail );
            recipients.setBccList( bccList );

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
    public LeadRequest convertFrom( EmailNotification destination, LeadRequest source ) {
        throw new UnsupportedOperationException( "convertFrom operation is not supported" );
    }

    /**
     * Capitalizes the first letter of name.
     *
     * @param request
     *            the LeadRequest
     * @return the string
     */
    private String formatName( final LeadRequest request ) {
        String name = Character.toUpperCase( request.getLastName().charAt( 0 ) ) + request.getLastName().substring( 1 );
        if (StringUtils.isNotBlank( request.getFirstName() )) {
            name = Character.toUpperCase( request.getFirstName().charAt( 0 ) ) + request.getFirstName().substring( 1 );
        }
        return name;
    }

}
