package com.owners.gravitas.dto.request;

import java.util.Arrays;
import java.util.List;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.ConstructorSignatureAndPropertiesMapping;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.hubzu.notification.dto.client.push.AppType;
import com.owners.gravitas.enums.PushNotificationType;

/**
 * The Class NotificationRequestTest.
 */
public class NotificationRequestTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues testValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * NotificationRequest.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createNotificationRequestTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "eventType", null );
        defaultValues.put( "deviceToken", null );
        defaultValues.put( "deviceType", null );
        defaultValues.put( "firstName", null );
        defaultValues.put( "lastName", null );
        defaultValues.put( "taskId", null );
        defaultValues.put( "opportunityId", null );
        defaultValues.put( "opportunityCount", 0 );
        defaultValues.put( "title", null );
        defaultValues.put( "triggerDtm", null );
        defaultValues.put( "requestType", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createNotificationRequestTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "eventType", PushNotificationType.NEW_OPPORTUNITY );
        testValues.put( "deviceToken", "test" ); 
        testValues.put( "deviceType", AppType.IOS );
        testValues.put( "firstName", "test" );
        testValues.put( "lastName", "test" );
        testValues.put( "taskId", "test" );
        testValues.put( "opportunityId", "test" );
        testValues.put( "opportunityCount", 0 );
        testValues.put( "title", "test" );
        testValues.put( "triggerDtm", 1L );
        testValues.put( "requestType", "Make An Offer" );
    }

    /**
     * Tests the {@link NotificationRequest} with default values. Tests the
     * getters and setters of NotificationRequest.
     */
    @Test
    public final void testNotificationRequest() {
        final ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();

        final List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        final List< Class< ? > > signature2 = Arrays.< Class< ? > > asList( int.class );
        // List< Class< ? > > signature3 = Arrays.< Class< ? > > asList(
        // String.class, String.class, String.class,
        // String.class, String.class );
        final List< Class< ? > > signature3 = Arrays.< Class< ? > > asList( String.class, String.class,
                PushNotificationType.class, String.class, String.class );

        mapping.put( signature1, Arrays.asList() );
        mapping.put( signature2, Arrays.asList( "opportunityCount" ) );
        mapping.put( signature3, Arrays.asList( "firstName", "lastName", "eventType", "taskId", "opportunityId" ) );

        final BeanLikeTester blt = new BeanLikeTester( NotificationRequest.class, mapping );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }
}
