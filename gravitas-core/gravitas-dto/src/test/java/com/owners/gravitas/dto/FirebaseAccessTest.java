package com.owners.gravitas.dto;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class FirebaseAccessTest.
 */
public class FirebaseAccessTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues slackRequestTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * FirebaseAccess.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createSlackRequestTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "accessToken", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createSlackRequestTestValues() {
        slackRequestTestValues = new PropertiesAndValues();
        slackRequestTestValues.put( "accessToken", "34534534" );
    }

    /**
     * Tests the {@link FirebaseAccess} with default values. Tests the
     * getters
     * and setters of FirebaseAccess.
     */
    @Test
    public final void testAgentDeviceRequest() {
        final BeanLikeTester blt = new BeanLikeTester( FirebaseAccess.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, slackRequestTestValues );
    }
}
