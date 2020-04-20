package com.owners.gravitas.dto.response;

import java.util.ArrayList;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.dto.Preference;

/**
 * The Class NotificationPreferenceResponseTest.
 *
 * @author shivamm
 */
public class NotificationPreferenceResponseTest {
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues notificationPreferenceResponseTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * NotificationPreferenceResponseTest.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createNotificationPreferenceResponseTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "preferences", null );
        defaultValues.put( "messages", null );
        defaultValues.put( "success", false );
    }

    /**
     * This method is to create the actual values.
     */
    private void createNotificationPreferenceResponseTestValues() {
        notificationPreferenceResponseTestValues = new PropertiesAndValues();
        notificationPreferenceResponseTestValues.put( "preferences", new ArrayList< Preference >() );
        notificationPreferenceResponseTestValues.put( "messages", new ArrayList< String >() );
        notificationPreferenceResponseTestValues.put( "success", true );
    }

    /**
     * Tests the {@link NotificationPreferenceResponseTest} with default values.
     * Tests the
     * getters
     * and setters of NotificationPreferenceResponseTest.
     */
    @Test
    public final void testNotificationPreferenceResponse() {
        final BeanLikeTester blt = new BeanLikeTester( NotificationPreferenceResponse.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, notificationPreferenceResponseTestValues );
    }
}
