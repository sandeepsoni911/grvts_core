/**
 * 
 */
package com.owners.gravitas.dto;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class SellerTest.
 *
 * @author harshads
 */
public class SellerTest {

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
        defaultValues.put( "id", null );
        defaultValues.put( "firstName", null );
        defaultValues.put( "lastName", null );
        defaultValues.put( "phoneNumber", null );
        defaultValues.put( "mainContactNumer", null );
        defaultValues.put( "email", null );
        defaultValues.put( "address", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createUserTestValues() {
        userTestValues = new PropertiesAndValues();
        userTestValues.put( "id", "123" );
        userTestValues.put( "firstName", "123" );
        userTestValues.put( "lastName", "test.com" );
        userTestValues.put( "phoneNumber", "1245" );
        userTestValues.put( "mainContactNumer", "878787" );
        userTestValues.put( "email", "454545" );
        userTestValues.put( "address", new SellerAddress() );
    }

    /**
     * Tests the {@link User} with default values. Tests the getters
     * and setters of User.
     */
    @Test
    public final void testPropertyOrder() {
        final BeanLikeTester blt = new BeanLikeTester( Seller.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, userTestValues );
    }

}
