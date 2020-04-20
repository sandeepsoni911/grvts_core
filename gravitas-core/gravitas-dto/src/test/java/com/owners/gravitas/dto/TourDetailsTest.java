package com.owners.gravitas.dto;

import java.util.ArrayList;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class TourDetailsTest.
 *
 * @author amits
 */
public class TourDetailsTest {
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
     * TourDetails.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "type", null );
        defaultValues.put( "value", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "type", "test" );
        testValues.put( "value", new ArrayList<>() );
    }

    /**
     * Tests the {@link TourDetails} with default values. Tests the
     * getters
     * and setters of TourDetails.
     */
    @Test
    public final void testTourDetails() {
        final BeanLikeTester blt = new BeanLikeTester( TourDetails.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }
}
