package com.owners.gravitas.dto.response;

import java.util.ArrayList;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.owners.gravitas.dto.User;

/**
 * The Class UserDetailsResponseTest.
 * 
 * @author pabhishek
 */
public class UserDetailsResponseTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The test values. */
    private PropertiesAndValues testValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createUserDetailsResponseTestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "users", new ArrayList< User >() );
        defaultValues.put( "message", "Operation was successful" );
        defaultValues.put( "status", BaseResponse.Status.SUCCESS );
    }

    /**
     * Creates the user details response test values.
     */
    private void createUserDetailsResponseTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "users", new ArrayList< User >() );
        testValues.put( "message", "test" );
        testValues.put( "status", BaseResponse.Status.FAILURE );
    }

    /**
     * Test user details response.
     */
    @Test
    public final void testUserDetailsResponse() {
        final BeanLikeTester blt = new BeanLikeTester( UserDetailsResponse.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }
}
