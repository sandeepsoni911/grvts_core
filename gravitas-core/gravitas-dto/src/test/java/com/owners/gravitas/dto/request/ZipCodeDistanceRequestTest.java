package com.owners.gravitas.dto.request;

import java.util.ArrayList;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class ZipCodeDistanceRequestTest.
 *
 * @author shivamm
 */
public class ZipCodeDistanceRequestTest {
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues zipCodeDistanceRequestTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * ReminderRequest.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createEmailRequestTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "exclude", false );
        defaultValues.put( "zipCodes", null );

    }

    /**
     * This method is to create the actual values.
     */
    private void createEmailRequestTestValues() {
        zipCodeDistanceRequestTestValues = new PropertiesAndValues();
        zipCodeDistanceRequestTestValues.put( "exclude", true );
        zipCodeDistanceRequestTestValues.put( "zipCodes", new ArrayList<>() );

    }

    /**
     * Tests the {@link ZipCodeDistanceRequest} with default values. Tests the
     * getters
     * and setters of ZipCodeDistanceRequest.
     */
    @Test
    public final void testZipCodeDistanceRequest() {
        final BeanLikeTester blt = new BeanLikeTester( ZipCodeDistanceRequest.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, zipCodeDistanceRequestTestValues );
    }
}
