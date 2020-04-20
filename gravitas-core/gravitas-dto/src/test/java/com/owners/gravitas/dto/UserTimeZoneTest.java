/**
 *
 */
package com.owners.gravitas.dto;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class UserTimeZoneTest.
 *
 * @author harshads
 */
public class UserTimeZoneTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues userTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * User.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createUserTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "hourOffset", null );
        defaultValues.put( "minuteOffset", null );

    }

    /**
     * This method is to create the actual values.
     */
    private void createUserTestValues() {
        userTestValues = new PropertiesAndValues();
        userTestValues.put( "hourOffset", 123 );
        userTestValues.put( "minuteOffset", 124 );

    }

    /**
     * Tests the {@link User} with default values. Tests the getters
     * and setters of User.
     */
    @Test
    public final void testPropertyOrder() {
        final BeanLikeTester blt = new BeanLikeTester( UserTimeZone.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, userTestValues );
    }

    /**
     * Test set minute off set.
     */
    @Test
    public final void testSetMinuteOffSet() {
       UserTimeZone userTimeZone = new UserTimeZone();
       userTimeZone.setHourOffset( 1 );
       userTimeZone.setMinuteOffset( null );
    }
}
