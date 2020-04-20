package com.owners.gravitas.dto.response;

import org.joda.time.DateTime;
import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class NotificationResponseTest.
 *
 * @author vishwanathm
 */
public class NotificationResponseTest {
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues notificationResponseTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * NotificationResponse.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createNotificationResponseTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "status", null );
        defaultValues.put( "statusCode", null );
        defaultValues.put( "statusMessage", null );
        defaultValues.put( "requestStartTime", null );
        defaultValues.put( "executionTime", 0 );
        defaultValues.put( "result", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createNotificationResponseTestValues() {
        notificationResponseTestValues = new PropertiesAndValues();
        notificationResponseTestValues.put( "status", "status" );
        notificationResponseTestValues.put( "statusCode", "statusCode" );
        notificationResponseTestValues.put( "statusMessage", "statusMessae" );
        notificationResponseTestValues.put( "requestStartTime", new DateTime() );
        notificationResponseTestValues.put( "executionTime", 10 );
        notificationResponseTestValues.put( "result", "result" );
    }

    /**
     * Tests the {@link NotificationResponse} with default values. Tests the
     * getters
     * and setters of NotificationResponse.
     */
    @Test
    public final void testNotificationResponse() {
        final BeanLikeTester blt = new BeanLikeTester( NotificationResponse.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, notificationResponseTestValues );
    }
}
