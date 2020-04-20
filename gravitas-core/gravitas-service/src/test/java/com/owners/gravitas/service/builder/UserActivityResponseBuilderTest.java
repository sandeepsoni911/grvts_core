package com.owners.gravitas.service.builder;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.ActivityProperty;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.ContactActivity;
import com.owners.gravitas.domain.entity.ObjectAttributeConfig;
import com.owners.gravitas.domain.entity.RefCode;
import com.owners.gravitas.dto.NotificationResponseObject;
import com.owners.gravitas.dto.response.UserActivityResponse;

/**
 * The Class UserActivityResponseBuilderTest.
 *
 * @author bhardrah
 */
public class UserActivityResponseBuilderTest extends AbstractBaseMockitoTest {

    /** The user activity response builder. */
    @InjectMocks
    private UserActivityResponseBuilder userActivityResponseBuilder;

    /**
     * Test from.
     */
    @Test
    public void testFrom() {
        final Contact contact = new Contact();
        final List< ContactActivity > contactActivities = new ArrayList<>();
        final NotificationResponseObject notificationResponseObject = new NotificationResponseObject();
        final ContactActivity contactActivity = new ContactActivity();
        contact.setFirstName( "firstName" );
        contact.setLastName( "lastName" );
        contact.setPhone( "phone" );
        contact.setEmail( "email" );
        contact.setCreatedDate( new DateTime() );
        final RefCode refCode = new com.owners.gravitas.domain.entity.RefCode();
        refCode.setCode( "code" );
        contactActivity.setRefCode( refCode );
        contactActivity.setCreatedDate( new DateTime() );
        final List< ActivityProperty > activityProperties = new ArrayList<>();
        final ActivityProperty activityProperty = new ActivityProperty();
        activityProperty.setListingId( "listingId" );
        activityProperty.setPropertyId( "propertyId" );
        activityProperty.setPropertyAttributes( "propertyAttributes" );
        activityProperties.add( activityProperty );
        contactActivity.setActivityProperties( activityProperties );
        contactActivities.add( contactActivity );

        final UserActivityResponse userActivityResponse = userActivityResponseBuilder
                .from( contact, contactActivities, notificationResponseObject, null ).build();
        Assert.assertNotNull( userActivityResponse );

    }
    
    
    /**
     * To test builder when contact activities are null
     */
    @Test
    public void testFromWithNullActiityValues() {
        final Contact contact = new Contact();
        final NotificationResponseObject notificationResponseObject = new NotificationResponseObject();
        contact.setFirstName( "firstName" );
        contact.setLastName( "lastName" );
        contact.setPhone( "phone" );
        contact.setEmail( "email" );
        contact.setCreatedDate( new DateTime() );
        final ObjectAttributeConfig config = new ObjectAttributeConfig();
        final UserActivityResponse userActivityResponse = userActivityResponseBuilder
                .from( contact, null, notificationResponseObject, config ).build();
        Assert.assertNotNull( userActivityResponse );

    }
    
    /**
     * To test builder when contact activities are null
     * and autor registeration date found
     */
    @Test
    public void testFromWithNullActiityAndAutoRegisteredDate() {
        final Contact contact = new Contact();
        final NotificationResponseObject notificationResponseObject = new NotificationResponseObject();
        contact.setFirstName( "firstName" );
        contact.setLastName( "lastName" );
        contact.setPhone( "phone" );
        contact.setEmail( "email" );
        contact.setCreatedDate( new DateTime() );
        final ObjectAttributeConfig config = new ObjectAttributeConfig();
        final UserActivityResponse userActivityResponse = userActivityResponseBuilder
                .from( contact, null, notificationResponseObject, config ).build();
        Assert.assertNotNull( userActivityResponse );

    }
}
