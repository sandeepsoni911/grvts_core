/**
 * 
 */
package com.owners.gravitas.dto;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class PropertyOrderTest.
 *
 * @author harshads
 */
public class PropertyOrderTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues propertyOrderTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * PropertyOrder.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createPropertyOrderTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "orderType", null );
        defaultValues.put( "property", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createPropertyOrderTestValues() {
        propertyOrderTestValues = new PropertiesAndValues();
        propertyOrderTestValues.put( "orderType", "123" );
        propertyOrderTestValues.put( "property", new Property() );
    }

    /**
     * Tests the {@link PropertyOrder} with default values. Tests the getters
     * and setters of PropertyOrder.
     */
    @Test
    public final void testPropertyOrder() {
        final BeanLikeTester blt = new BeanLikeTester( PropertyOrder.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, propertyOrderTestValues );
    }
}
