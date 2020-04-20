package com.owners.gravitas.dto.request;

import java.util.Date;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class ReminderRequestTest.
 *
 * @author shivamm
 */
public class ReminderRequestTest {
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues reminderRequestTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * ReminderRequest.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createReminderRequestTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "triggerDtm", null );
        defaultValues.put( "opportunityId", null );
        defaultValues.put( "taskId", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createReminderRequestTestValues() {
        reminderRequestTestValues = new PropertiesAndValues();
        reminderRequestTestValues.put( "triggerDtm", new Date() );
        reminderRequestTestValues.put( "opportunityId", "test123" );
        reminderRequestTestValues.put( "taskId", "test123" );

    }

    /**
     * Tests the {@link ReminderRequest} with default values. Tests the
     * getters
     * and setters of ReminderRequest.
     */
    @Test
    public final void testActionRequest() {
        final BeanLikeTester blt = new BeanLikeTester( ReminderRequest.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, reminderRequestTestValues );
    }
}
