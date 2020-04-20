package com.owners.gravitas.domain;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class ClientVersionTest.
 *
 * @author shivamm
 */
public class ClientVersionTest {
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
     * ReminderTest.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "minVersion", null );
        defaultValues.put( "minMessage", null );
        defaultValues.put( "currentVersion", null );
        defaultValues.put( "currentMessage", null );
        defaultValues.put( "url", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "minVersion", "test" );
        testValues.put( "minMessage", "test" );
        testValues.put( "currentVersion", "test" );
        testValues.put( "currentMessage", "test" );
        testValues.put( "url", "test" );
    }

    /**
     * Tests the {@link ClientVersionTest} with default values. Tests the
     * getters
     * and setters of ReminderTest.
     */
    @Test
    public final void testReminder() {
        final BeanLikeTester blt = new BeanLikeTester( ClientVersion.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }

    /**
     * Test to audit map.
     */
    @Test
    public final void testToAuditMap() {
        Reminder reminder = new Reminder();
        reminder.toAuditMap();
    }

}
