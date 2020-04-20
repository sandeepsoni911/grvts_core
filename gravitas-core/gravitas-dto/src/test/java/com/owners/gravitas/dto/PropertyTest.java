/**
 *
 */
package com.owners.gravitas.dto;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class PropertyTest.
 *
 * @author harshads
 */
public class PropertyTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues propertyTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * Property.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createPropertyTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "listingId", null );
        defaultValues.put( "propertyType", null );
        defaultValues.put( "price", null );
        defaultValues.put( "numberBeds", null );
        defaultValues.put( "numberFullBaths", null );
        defaultValues.put( "numberHalfBaths", null );
        defaultValues.put( "squareFeet", null );
        defaultValues.put( "address", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createPropertyTestValues() {
        propertyTestValues = new PropertiesAndValues();
        propertyTestValues.put( "listingId", "123" );
        propertyTestValues.put( "propertyType", "test.com" );
        propertyTestValues.put( "price", "1245" );
        propertyTestValues.put( "numberBeds", "878787" );
        propertyTestValues.put( "numberFullBaths", "454545" );
        propertyTestValues.put( "numberHalfBaths", "22" );
        propertyTestValues.put( "squareFeet", "22" );
        propertyTestValues.put( "address", new SellerAddress() );
    }

    /**
     * Tests the {@link Property} with default values. Tests the getters
     * and setters of Property.
     */
    @Test
    public final void testProperty() {
        final BeanLikeTester blt = new BeanLikeTester( Property.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, propertyTestValues );
    }



}
