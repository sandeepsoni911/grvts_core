package com.owners.gravitas.dto;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class RegisteredUserTest.
 *
 * @author vishwanathm
 */
public class RegisteredUserTest {
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
     * ActionLogResponseTest.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createRegisteredUserTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "userId", null );
        defaultValues.put( "firstName", null );
        defaultValues.put( "lastName", null );
        defaultValues.put( "email", null );
        defaultValues.put( "phone", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createRegisteredUserTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "userId", "userId" );
        testValues.put( "firstName", "firstName" );
        testValues.put( "lastName", "lastName" );
        testValues.put( "email", "email@email.com" );
        testValues.put( "phone", "phone" );
    }

    /**
     * Tests the {@link RegisteredUser} with default values. Tests the
     * getters
     * and setters of RegisteredUser.
     */
    @Test
    public final void testRegisteredUserResponse() {

        final BeanLikeTester blt = new BeanLikeTester( RegisteredUser.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }

}
