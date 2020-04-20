package com.owners.gravitas.dto;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class UserAddressTest.
 * 
 * @author pabhishek
 */
public class UserAddressTest {

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
        createUserAddressTestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "address1", null );
        defaultValues.put( "address2", null );
        defaultValues.put( "city", null );
        defaultValues.put( "state", null );
        defaultValues.put( "zip", null );
    }

    /**
     * Creates the user address test values.
     */
    private void createUserAddressTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "address1", "address1" );
        testValues.put( "address2", "address2" );
        testValues.put( "city", "city" );
        testValues.put( "state", "state" );
        testValues.put( "zip", "zip" );
    }

    /**
     * Test user address.
     */
    @Test
    public final void testUserAddress() {
        final BeanLikeTester blt = new BeanLikeTester( UserAddress.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }
}
