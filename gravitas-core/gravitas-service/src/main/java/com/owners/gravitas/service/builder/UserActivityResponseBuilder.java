package com.owners.gravitas.service.builder;

import static com.owners.gravitas.constants.Constants.REGISTERED_ON_GRAVITAS;
import static com.owners.gravitas.constants.Constants.REGISTERED_ON_OWNERS_COM;
import static com.owners.gravitas.enums.ProspectAttributeType.LEAD_SOURCE_URL;
import static com.owners.gravitas.enums.ProspectAttributeType.NOTES;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hubzu.notification.dto.client.email.data.EmailData;
import com.owners.gravitas.domain.entity.ActivityProperty;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.ContactActivity;
import com.owners.gravitas.domain.entity.ContactAttribute;
import com.owners.gravitas.domain.entity.ContactJsonAttribute;
import com.owners.gravitas.domain.entity.ObjectAttributeConfig;
import com.owners.gravitas.domain.entity.ObjectType;
import com.owners.gravitas.dto.AlertDetail;
import com.owners.gravitas.dto.EventDetail;
import com.owners.gravitas.dto.FirstLeadDetail;
import com.owners.gravitas.dto.NotificationDetail;
import com.owners.gravitas.dto.NotificationResponseObject;
import com.owners.gravitas.dto.response.UserActivityResponse;
import com.owners.gravitas.enums.UserEventType;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.util.JsonUtil;

/**
 * The Class UserActivityResponseBuilder.
 *
 * @author bhardrah
 */
@Service
public class UserActivityResponseBuilder {

    /** The user activity response. */
    private UserActivityResponse userActivityResponse;

    /** The user activity response builder constructor. */
    public UserActivityResponseBuilder() {

    }

    /**
     * Sets the user Id.
     *
     * @param userId
     *            the user Id
     */
    public void setUserId( final String userId ) {
        this.userActivityResponse.setUserId( userId );
    }

    /**
     * Sets the first name.
     *
     * @param firstName
     *            the first name
     */
    public void setFirstName( final String firstName ) {
        this.userActivityResponse.setFirstName( firstName );
    }

    /**
     * Sets the last name.
     *
     * @param lastName
     *            the last name
     */
    public void setLastName( final String lastName ) {
        this.userActivityResponse.setLastName( lastName );
    }

    /**
     * Sets the phone.
     *
     * @param phone
     *            the phone
     */
    public void setPhone( final String phone ) {
        this.userActivityResponse.setPhone( phone );
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail( final String email ) {
        this.userActivityResponse.setEmail( email );
    }

    /**
     * Sets the alert details.
     *
     * @param alertDetails
     *            the alertDetails
     */
    public void setAlertDetails( final List< AlertDetail > alertDetails ) {
        this.userActivityResponse.setAlertDetails( alertDetails );
    }

    /**
     * Sets the first lead detail.
     *
     * @param firstLeadDetail
     *            the new first lead detail
     */
    public void setFirstLeadDetail( final FirstLeadDetail firstLeadDetail ) {
        this.userActivityResponse.setFirstLeadDetail( firstLeadDetail );
    }
    
    /**
     * sets the boolean indicating lead is claimed or not claimed
     * 
     * @param isLeadClaimed
     */
    public void setIsLeadClaimed( final boolean isLeadClaimed ) {
        this.userActivityResponse.setLeadClaimed( isLeadClaimed );
    }
    /**
     * sets the value for  opportunityId
     * 
     * @param opportunityId
     */
    public void setOpportunityId( final String opportunityId ) {
        this.userActivityResponse.setOpportunityId(opportunityId);
    }

    /**
     * Builds the user activity response builder object.
     *
     * @param contact
     *            the contact
     * @param contactActivities
     *            the contact contactActivitiy set
     * @param config
     */
    public UserActivityResponseBuilder from( final Contact contact, final List< ContactActivity > contactActivities,
            final NotificationResponseObject notificationResponseObject, final ObjectAttributeConfig config ) {
        if (contact != null) {
            final List< AlertDetail > alertDetails = new ArrayList< AlertDetail >();
            userActivityResponse = new UserActivityResponse();
            this.setFirstName( contact.getFirstName() );
            this.setLastName( contact.getLastName() );
            this.setPhone( contact.getPhone() );
            this.setEmail( contact.getEmail() );
            this.setOpportunityId(contact.getCrmId());
            if (contact.getAssignInsideSales() != null) {
                this.setIsLeadClaimed( true );
            }
            if (!CollectionUtils.isEmpty( contactActivities )) {
                this.setUserId( contactActivities.iterator().next().getOwnersComId() );
                final Iterator< ContactActivity > it = contactActivities.iterator();
                final Set< String > checkDuplicates = new HashSet<>();
                while ( it.hasNext() ) {
                    final List< EventDetail > eventDetails = new ArrayList<>();
                    final ContactActivity contactActivity = it.next();
                    final AlertDetail alertDetail = new AlertDetail();
                    alertDetail.setEventType( contactActivity.getRefCode().getCode() );
                    for ( final UserEventType userEventType : UserEventType.values() ) {
                        if (userEventType.toString().equalsIgnoreCase( contactActivity.getRefCode().getCode() )) {
                            alertDetail.setEventText( userEventType.getType() );
                        }
                    }
                    alertDetail.setCreatedOn( contactActivity.getCreatedDate().getMillis() );
                    EventDetail eventDetail;
                    final List< ActivityProperty > activityPropertyList = contactActivity.getActivityProperties();

                    for ( final ActivityProperty activityProperty : activityPropertyList ) {
                        if (!checkDuplicates.contains( activityProperty.getListingId() )) {
                            eventDetail = new EventDetail();
                            eventDetail.setListingId( activityProperty.getListingId() );
                            eventDetail.setPropertyId( activityProperty.getPropertyId() );
                            eventDetail.setPropertyAttributes( activityProperty.getPropertyAttributes() );
                            eventDetails.add( eventDetail );
                            checkDuplicates.add( activityProperty.getListingId() );
                        }
                    }
                    checkDuplicates.clear();

                    alertDetail.setEventDetails( eventDetails );
                    alertDetail.setNotificationDetail(
                            getNotificationDetail( contactActivity, notificationResponseObject ) );
                    alertDetails.add( alertDetail );
                }
            }
            this.setAlertDetails( alertDetails );
            this.setFirstLeadDetail( getFirstLeadDetail( contact ) );
        }

        return this;

    }

    /**
     * To get Auto registration date of buyer
     * 
     * @param contactJsonAttributes
     * @return
     */
    public void setAutoRegistrationDetails( final UserActivityResponse userActivityResponse, final Contact contact,
            final ObjectAttributeConfig config ) {
        AlertDetail alertDetail = null;
        final List< EventDetail > eventDetails = new ArrayList<>();
        if (contact != null && !CollectionUtils.isEmpty( contact.getContactJsonAttributes() )) {
            final List< String > notes = getContactAttributeJsonValue( contact, contact.getObjectType(), config );
            String registrationDate = null;
            for ( final String note : notes ) {
                registrationDate = getRegistrationDate( note );
                if (registrationDate != null) {
                    break;
                }
            }

            if (registrationDate != null) {
                alertDetail = new AlertDetail();
                alertDetail.setEventType( REGISTERED_ON_OWNERS_COM );
                final SimpleDateFormat formatter = new SimpleDateFormat( "EEE MMM d HH:mm:ss z yyyy" );
                Date date = null;
                try {
                    date = formatter.parse( registrationDate.trim() );
                } catch ( final ParseException e ) {
                    throw new ApplicationException( "Date Parsing Exception", e );
                }
                alertDetail.setCreatedOn( date.getTime() );
                alertDetail.setEventDetails( eventDetails );
                userActivityResponse.getAlertDetails().add( alertDetail );
            }
        }
    }

    /**
     * Gets the contact attribute json value.
     *
     * @param contact
     *            the contact
     * @param objectType
     *            the object type
     * @param key
     *            the key
     * @return the contact attribute json value
     */
    private List< String > getContactAttributeJsonValue( final Contact contact, final ObjectType objectType,
            final ObjectAttributeConfig config ) {
        List< String > jsonValueList = Collections.emptyList();
        for ( final ContactJsonAttribute contactJsonAttribute : contact.getContactJsonAttributes() ) {
            if (contactJsonAttribute.getValue() != null && contactJsonAttribute.getValue().contains( "\"notes\":" )) {
                final Map< String, List< String > > jsonMap = JsonUtil.toType( contactJsonAttribute.getValue(),
                        Map.class );
                jsonValueList = jsonMap.get( NOTES.getKey() );
            }
        }
        return jsonValueList;
    }

    /**
     * To get registration date from Json
     * 
     * @param value
     * @return
     */
    private String getRegistrationDate( final String value ) {
        String registrationDate = null;
        if (StringUtils.isNotEmpty( value ) && value.contains( REGISTERED_ON_GRAVITAS )) {
            registrationDate = value.substring(
                    value.indexOf( REGISTERED_ON_GRAVITAS ) + REGISTERED_ON_GRAVITAS.length(), value.length() );
        }
        return registrationDate;
    }

    /**
     * Builds the user activity response.
     */
    public UserActivityResponse build() {
        return this.userActivityResponse;
    }

    /**
     * Gets the notification detail.
     *
     * @param contactActivity
     *            the contact activity
     * @param notificationResponseObject
     *            the notification response object
     * @return the notification detail
     */
    private NotificationDetail getNotificationDetail( final ContactActivity contactActivity,
            final NotificationResponseObject notificationResponseObject ) {

        if (contactActivity.getNotificationId() == null || notificationResponseObject == null
                || CollectionUtils.isEmpty( notificationResponseObject.getContent() )) {
            return null;
        }
        final List< EmailData > emailContentList = notificationResponseObject.getContent();
        for ( final EmailData emailData : emailContentList ) {
            if (emailData.getRequestId().equalsIgnoreCase( contactActivity.getNotificationId() )) {

                final NotificationDetail notificationDetail = new NotificationDetail();
                notificationDetail.setFrom( emailData.getFromDisplayName() + " (" + emailData.getFrom() + ")" );
                notificationDetail.setSubject( emailData.getSubject() );
                notificationDetail.setSentOn( emailData.getCreatedOn().getTime() );

                for ( final UserEventType userEventType : UserEventType.values() ) {
                    if (userEventType.toString().equalsIgnoreCase( contactActivity.getRefCode().getCode() )) {
                        notificationDetail.setTrigger( userEventType.getType().toString() );
                    }
                }
                return notificationDetail;
            }
        }
        return null;
    }

    /**
     * Gets the first lead details.
     *
     * @param contact
     *            the contact
     * @return the first lead detail object.
     */
    private FirstLeadDetail getFirstLeadDetail( final Contact contact ) {

        final FirstLeadDetail firstLeadDetail = new FirstLeadDetail();
        firstLeadDetail.setCreatedOn( contact.getCreatedDate().getMillis() );
        firstLeadDetail.setLeadSource( contact.getSource() );
        firstLeadDetail
                .setLeadSourceUrl( getContactAttribute( contact.getContactAttributes(), LEAD_SOURCE_URL.getKey() ) );
        return firstLeadDetail;
    }

    /**
     * Gets the contact attribute from a set of attributes.
     *
     * @param contactAttributeSet
     *            the contact attribute set
     * @param attributeName
     *            the attribute name
     * @return the contact attribute, or null if contact doesn't exist.
     */
    private String getContactAttribute( final Set< ContactAttribute > contactAttributeSet,
            final String attributeName ) {
        String leadSourceUrl = null;
        if (!CollectionUtils.isEmpty( contactAttributeSet )) {
            for ( final ContactAttribute contactAttribute : contactAttributeSet ) {
                if (contactAttribute.getObjectAttributeConfig().getAttributeName().equalsIgnoreCase( attributeName )) {
                    leadSourceUrl = contactAttribute.getValue();
                    break;
                }
            }
        }
        return leadSourceUrl;
    }

}
