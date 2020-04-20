/**
 *
 */
package com.owners.gravitas.dto;

import static org.junit.Assert.assertEquals;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class UserTest.
 *
 * @author harshads
 */
public class UserTest {

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
        defaultValues.put( "firstName", null );
        defaultValues.put( "lastName", null );
        defaultValues.put( "phone", null );
        defaultValues.put( "email", null );
        defaultValues.put( "status", null );
        defaultValues.put( "address", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createUserTestValues() {
        userTestValues = new PropertiesAndValues();
        userTestValues.put( "firstName", "123" );
        userTestValues.put( "lastName", "test.com" );
        userTestValues.put( "phone", "1245" );
        userTestValues.put( "email", "454545" );
        userTestValues.put( "status", "test" );
        userTestValues.put( "address", new UserAddress() );
    }

    /**
     * Tests the {@link User} with default values. Tests the getters
     * and setters of User.
     */
    @Test
    public final void testUser() {
        final BeanLikeTester blt = new BeanLikeTester( User.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, userTestValues );
    }

    /**
     * Test compare to.
     */
    @Test
    public final void testCompareTo() {
        User user = new User();
        user.setFirstName( "test" );
        int val = user.compareTo( user );
        assertEquals( 0, val );
    }
}
