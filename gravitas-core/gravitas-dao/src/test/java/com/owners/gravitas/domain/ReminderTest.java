package com.owners.gravitas.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.ConstructorSignatureAndPropertiesMapping;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class ReminderTest.
 *
 * @author shivamm
 */
public class ReminderTest {
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
        defaultValues.put( "triggerDtm", null );
        defaultValues.put( "createdDtm", null );
        defaultValues.put( "lastModifiedDtm", null );
        defaultValues.put( "notificationIds", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "triggerDtm", 1L );
        testValues.put( "createdDtm", 1L );
        testValues.put( "lastModifiedDtm", 1L );
        testValues.put( "notificationIds", new ArrayList< String >() );
    }

    /**
     * Tests the {@link ReminderTest} with default values. Tests the getters
     * and setters of ReminderTest.
     */
    @Test
    public final void testReminder() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        List< Class< ? > > signature2 = Arrays.< Class< ? > > asList( Long.class, List.class );
        mapping.put( signature1, Arrays.asList() );
        mapping.put( signature2, Arrays.asList( "triggerDtm", "notificationIds" ) );

        final BeanLikeTester blt = new BeanLikeTester( Reminder.class, mapping );
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
