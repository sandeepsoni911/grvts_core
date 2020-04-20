package com.owners.gravitas.business.builder;

import static com.owners.gravitas.constants.CRMQuery.GET_LEAD_OWNER_INFO;
import static com.owners.gravitas.constants.Constants.ID;
import static com.owners.gravitas.constants.Constants.MARKETING_BUYER_FIRST_NAME;
import static com.owners.gravitas.constants.Constants.MARKETING_LEAD_OWNER_EMAIL;
import static com.owners.gravitas.constants.Constants.MARKETING_LEAD_OWNER_FIRST_NAME;
import static com.owners.gravitas.constants.Constants.MARKETING_LEAD_OWNER_FULL_NAME;
import static com.owners.gravitas.constants.Constants.NOTIFICATION_CLIENT_ID;
import static com.owners.gravitas.util.StringUtils.convertObjectToString;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hubzu.notification.dto.client.email.Email;
import com.hubzu.notification.dto.client.email.EmailNotification;
import com.hubzu.notification.dto.client.email.EmailRecipients;
import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.constants.NotificationParameters;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.service.CRMQueryService;

/**
 * The Class LeadFollowupNotificationBuilder.
 *
 * @author vishwanathm
 */
@Component( "leadFollowupNotificationBuilder" )
public class LeadFollowupNotificationBuilder extends AbstractBuilder< LeadSource, EmailNotification > {
	
	/** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( LeadFollowupNotificationBuilder.class );

    /** The crm query service impl. */
    @Autowired
    private CRMQueryService crmQueryService;

    /**
     * This method converts {@link LeadSource} to
     * {@link EmailNotification}.
     *
     * @param source
     *            {@link LeadSource}
     * @param destination
     *            {@link EmailNotification}.
     *
     * @return returns instance of {@link EmailNotification}.
     */
    @Override
    public EmailNotification convertTo( LeadSource source, EmailNotification destination ) {
        EmailNotification emailNotification = destination;
        if (source != null) {
            if (emailNotification == null) {
                emailNotification = new EmailNotification();
            }
            final Email email = new Email();
            final Map< String, Object > leadOwnerDetails = getLeadOwnerDetails( source.getOwnerId() );
            email.setFromEmail( convertObjectToString( leadOwnerDetails.get( "Email" ) ) );
            email.setFromDisplayName( convertObjectToString( leadOwnerDetails.get( "Name" ) ) );

            emailNotification.setCreatedOn( new Timestamp( System.currentTimeMillis() ) );
            emailNotification.setClientId( NOTIFICATION_CLIENT_ID );

            final Map< String, String > parameterMap = new HashMap<>();
            parameterMap.put( MARKETING_BUYER_FIRST_NAME, getBuyerName( source.getFirstName(), source.getLastName() ) );
            parameterMap.put( MARKETING_LEAD_OWNER_EMAIL, convertObjectToString( leadOwnerDetails.get( "Email" ) ) );
            parameterMap.put( MARKETING_LEAD_OWNER_FULL_NAME, convertObjectToString( leadOwnerDetails.get( "Name" ) ) );
            parameterMap.put( NotificationParameters.USER_ID, source.getOwnersVisitorId() );
            // this parameter is used only in INTRODUCTION_TEMPLATE.
            //parameterMap.put( MARKETING_LEAD_OWNER_FIRST_NAME, getBuyerName( source.getFirstName(), source.getLastName() ) );
            LOGGER.info(convertObjectToString( leadOwnerDetails.get( "FirstName" )+"::"+convertObjectToString( leadOwnerDetails.get( "Name" ))));
            if (null != leadOwnerDetails.get( "FirstName" )) {
            	parameterMap.put( MARKETING_LEAD_OWNER_FIRST_NAME, convertObjectToString( leadOwnerDetails.get( "FirstName" )));
            } else {
            	parameterMap.put( MARKETING_LEAD_OWNER_FIRST_NAME, convertObjectToString( leadOwnerDetails.get( "Name" )));
            }
            email.setParameterMap( parameterMap );

            final List< String > toList = new ArrayList<>();
            toList.add( source.getEmail() );
            final EmailRecipients recipients = new EmailRecipients();
            recipients.setToList( toList );
            email.setRecipients( recipients );
            emailNotification.setEmail( email );
        }
        return emailNotification;
    }

    /**
     * Gets the lead owner details for marketing email template.
     *
     * @param ownerId
     *            the owner id
     * @return the lead owner details
     */
    private Map< String, Object > getLeadOwnerDetails( final String ownerId ) {
        final QueryParams params = new QueryParams();
        params.add( ID, ownerId );
        return crmQueryService.findOne( GET_LEAD_OWNER_INFO, params );
    }

    /**
     * Gets the buyer name.
     *
     * @param firstName
     *            the first name
     * @param lastName
     *            the last name
     * @return the buyer name
     */
    private String getBuyerName( final String firstName, final String lastName ) {
        String buyerName = lastName;
        if (org.apache.commons.lang3.StringUtils.isNotBlank( firstName )) {
            buyerName = firstName;
        }
        return buyerName;
    }

    /**
     * This method converts {@link EmailNotification} to
     * {@link Map<String,String>}.
     *
     * @param source
     *            {@link EmailNotification}.
     *
     * @param destination
     *            {@link LeadSource}.
     *
     * @return returns instance of {@link LeadSource}.
     */
    @Override
    public LeadSource convertFrom( EmailNotification destination, LeadSource source ) {
        throw new UnsupportedOperationException( "convertFrom operation is not supported" );
    }
}
