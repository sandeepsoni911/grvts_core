package com.owners.gravitas.business.builder;

import static com.owners.gravitas.constants.Constants.NOTIFICATION_CLIENT_ID;
import static org.apache.commons.lang.StringUtils.isBlank;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hubzu.notification.dto.client.email.Email;
import com.hubzu.notification.dto.client.email.EmailNotification;
import com.hubzu.notification.dto.client.email.EmailRecipients;
import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.constants.NotificationParameters;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.service.ContactEntityService;

/**
 * The Class SavedSearchFollowupEmailNotificationBuilder.
 *
 * @author raviz
 */
@Component( "savedSearchFollowupEmailNotificationBuilder" )
public class SavedSearchFollowupEmailNotificationBuilder extends AbstractBuilder< LeadSource, EmailNotification > {

    /** The agent name. */
    @Value( "${buyer.notification.agent.fullName}" )
    private String agentFullName;

    /** The from email. */
    @Value( "${buyer.farming.insideSales.savedSearch.followup.from.email}" )
    private String fromEmail;

    /** The reply to email. */
    @Value( "${buyer.farming.insideSales.savedSearch.followup.replyTo.email}" )
    private String replyToEmail;
    
    /** The contact service V 1. */
    @Autowired
    private ContactEntityService contactServiceV1;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public EmailNotification convertTo( final LeadSource source, final EmailNotification destination ) {
        EmailNotification emailNotification = destination;
        if (source != null) {
            if (emailNotification == null) {
                emailNotification = new EmailNotification();
            }
            // set from,reply-to,messageType and notification-id
            final Email email = new Email();
            email.setFromEmail( fromEmail );
            email.setFromDisplayName( agentFullName );
            email.setReplyToEmail( replyToEmail );
            emailNotification.setCreatedOn( new Timestamp( System.currentTimeMillis() ) );
            emailNotification.setClientId( NOTIFICATION_CLIENT_ID );

            // set dynamic parameters
            final Map< String, String > parameterMap = new HashMap<>();
            parameterMap.put( NotificationParameters.BUYER_FIRST_NAME,
                    isBlank( source.getFirstName() ) ? source.getLastName() : source.getFirstName() );
            parameterMap.put( NotificationParameters.USER_ID, getOwnersUserId(source.getId()));
            email.setParameterMap( parameterMap );

            // set recipients
            final EmailRecipients recipients = new EmailRecipients();
            final List< String > toList = new ArrayList<>();
            toList.add( source.getEmail() );
            recipients.setToList( toList );
            email.setRecipients( recipients );

            emailNotification.setEmail( email );
        }
        return emailNotification;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public LeadSource convertFrom( final EmailNotification source, final LeadSource destination ) {
        throw new UnsupportedOperationException( "convertFrom operation is not supported" );
    }
    
    /**
     * Get owners id
     * 
     * @param crmId
     * @return
     */
    private String getOwnersUserId(String crmId) {
    	final Contact contact = contactServiceV1.findByCrmId( crmId );
    	return contact.getOwnersComId();
    }
}
