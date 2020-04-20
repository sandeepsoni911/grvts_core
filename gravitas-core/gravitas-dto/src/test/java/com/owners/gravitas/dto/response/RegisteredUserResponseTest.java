package com.owners.gravitas.dto.response;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.dto.RegisteredUser;

/**
 * The Class RegisteredUserResponseTest.
 *
 * @author vishwanathm
 */
public class RegisteredUserResponseTest {
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
        createRegisteredUserResponseTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "user", null );
        defaultValues.put( "status", null );
        defaultValues.put( "message", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createRegisteredUserResponseTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "user", new RegisteredUser() );
        testValues.put( "status", "status" );
        testValues.put( "message", "message" );
    }

    /**
     * Tests the {@link RegisteredUserResponse} with default values. Tests the
     * getters
     * and setters of RegisteredUserResponse.
     */
    @Test
    public final void testRegisteredUserResponse() {

        final BeanLikeTester blt = new BeanLikeTester( RegisteredUserResponse.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }
}
