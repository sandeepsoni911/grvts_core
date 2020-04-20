/**
 *
 */
package com.owners.gravitas.dto;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class AddressTest.
 *
 * @author harshads
 */
public class AddressTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues addressTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * Address.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createAddressTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "address1", null );
        defaultValues.put( "address2", null );
        defaultValues.put( "city", null );
        defaultValues.put( "state", null );
        defaultValues.put( "zip", null );
        defaultValues.put( "county", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createAddressTestValues() {
        addressTestValues = new PropertiesAndValues();
        addressTestValues.put( "address1", "123" );
        addressTestValues.put( "address2", "test.com" );
        addressTestValues.put( "city", "1245" );
        addressTestValues.put( "state", "878787" );
        addressTestValues.put( "county", "454545" );
        addressTestValues.put( "zip", "454548" );
    }

    /**
     * Tests the {@link SellerAddress} with default values. Tests the getters
     * and setters of Address.
     */
    @Test
    public final void testAddress() {
        final BeanLikeTester blt = new BeanLikeTester( SellerAddress.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, addressTestValues );
    }

}
