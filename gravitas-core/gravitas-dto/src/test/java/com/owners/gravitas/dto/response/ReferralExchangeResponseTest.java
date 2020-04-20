package com.owners.gravitas.dto.response;

import java.util.HashMap;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.dto.response.BaseResponse.Status;

public class ReferralExchangeResponseTest {
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
     * ReferralExchangeResponse.
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
        defaultValues.put( "uuid", null );
        defaultValues.put( "configuration", null );
        defaultValues.put( "status", Status.SUCCESS );
        defaultValues.put( "message", "Operation was successful" );
        defaultValues.put( "errors", new HashMap<>() );
    }

    /**
     * This method is to create the actual values.
     */
    private void createTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "uuid", "test" );
        testValues.put( "configuration", "test" );
        testValues.put( "status", Status.SUCCESS );
        testValues.put( "message", "Operation was successful" );
        testValues.put( "errors", new HashMap<>() );
    }

    /**
     * Tests the {@link ReferralExchangeResponse} with default values. Tests the
     * getters
     * and setters of ReferralExchangeResponse.
     */
    @Test
    public final void testReferralExchangeResponse() {
        final BeanLikeTester blt = new BeanLikeTester( ReferralExchangeResponse.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }
}
