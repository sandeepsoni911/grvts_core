package com.owners.gravitas.dto.response;

import java.util.ArrayList;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.dto.Role;

/**
 * The Class RoleDetailsResponseTest.
 * 
 * @author pabhishek
 */
public class RoleDetailsResponseTest {

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
        createRoleDetailsResponseTestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "roles", new ArrayList< Role >() );
        defaultValues.put( "message", "Operation was successful" );
        defaultValues.put( "status", BaseResponse.Status.SUCCESS );
    }

    /**
     * Creates the role details response test values.
     */
    private void createRoleDetailsResponseTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "roles", new ArrayList< Role >() );
        testValues.put( "message", "test" );
        testValues.put( "status", BaseResponse.Status.FAILURE );
    }

    /**
     * Test user details response.
     */
    @Test
    public final void testUserDetailsResponse() {
        final BeanLikeTester blt = new BeanLikeTester( RoleDetailsResponse.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }
}
