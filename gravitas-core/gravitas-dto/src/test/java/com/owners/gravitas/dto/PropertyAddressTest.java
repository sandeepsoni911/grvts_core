package com.owners.gravitas.dto;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class PropertyAddressTest.
 *
 * @author vishwanathm
 */
public class PropertyAddressTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues propertyAddressTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * PropertyAddress.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createPropertyAddressTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "addressLine1", null );
        defaultValues.put( "addressLine2", null );
        defaultValues.put( "city", null );
        defaultValues.put( "state", null );
        defaultValues.put( "zip", null );
        defaultValues.put( "latitude", null );
        defaultValues.put( "longitude", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createPropertyAddressTestValues() {
        propertyAddressTestValues = new PropertiesAndValues();
        propertyAddressTestValues.put( "addressLine1", "1245" );
        propertyAddressTestValues.put( "addressLine2", "1245" );
        propertyAddressTestValues.put( "city", "1245" );
        propertyAddressTestValues.put( "state", "1245" );
        propertyAddressTestValues.put( "zip", "1245" );
        propertyAddressTestValues.put( "latitude", "1245" );
        propertyAddressTestValues.put( "longitude", "1245" );
    }

    /**
     * Tests the {@link PropertyAddress} with default values. Tests the getters
     * and setters of PropertyAddress.
     */
    @Test
    public final void testPropertyAddress() {
        final BeanLikeTester blt = new BeanLikeTester( PropertyAddress.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, propertyAddressTestValues );
    }

}
