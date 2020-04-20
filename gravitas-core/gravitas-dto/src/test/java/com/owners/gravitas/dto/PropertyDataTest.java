/**
 *
 */
package com.owners.gravitas.dto;

import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;

/**
 * The Class PropertyDataTest.
 *
 * @author Amits
 */
public class PropertyDataTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues personTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * PropertyData.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createPersonTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "bathRooms", null );
        defaultValues.put( "propertyAddress", null );
        defaultValues.put( "bedRooms", null );
        defaultValues.put( "price", null );
        defaultValues.put( "size", null );
        defaultValues.put( "images", null );
        defaultValues.put( "pdpURL", null );
        defaultValues.put( "canonicalPdpURL", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createPersonTestValues() {
        personTestValues = new PropertiesAndValues();
        personTestValues.put( "bathRooms", null );
        personTestValues.put( "propertyAddress", null );
        personTestValues.put( "bedRooms", null );
        personTestValues.put( "price", null );
        personTestValues.put( "size", null );
        personTestValues.put( "images", null );
        personTestValues.put( "pdpURL", null );
        personTestValues.put( "canonicalPdpURL", null );
    }

    /**
     * Tests the {@link PropertyData} with default values. Tests the
     * getters
     * and setters of PropertyData.
     */
//    @Test
//    public final void testPerson() {
//        final BeanLikeTester blt = new BeanLikeTester( PropertyData.class );
//        blt.testDefaultValues( defaultValues );
//        blt.testMutatorsAndAccessors( defaultValues, personTestValues );
//    }

}
