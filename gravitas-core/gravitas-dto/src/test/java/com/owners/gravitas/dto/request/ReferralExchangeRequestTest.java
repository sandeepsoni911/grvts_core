package com.owners.gravitas.dto.request;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.dto.ReferralExchangeDetails;

public class ReferralExchangeRequestTest {
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
     * ReferralExchangeRequest.
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
        defaultValues.put( "token", null );
        defaultValues.put( "version", null );
        defaultValues.put( "testFlag", false );
        defaultValues.put( "apiName", null );
        //defaultValues.put( "data", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "token", "test" );
        testValues.put( "version", "test" );
        testValues.put( "testFlag", true );
        testValues.put( "apiName", "test" );
       // testValues.put( "data", new ReferralExchangeDetails() );
    }

    /**
     * Tests the {@link ReferralExchangeRequest} with default values. Tests the
     * getters
     * and setters of ReferralExchangeRequest.
     */
    @Test(enabled=false)
    public final void testLeadRequest() {
        final BeanLikeTester blt = new BeanLikeTester( ReferralExchangeRequest.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }
}
