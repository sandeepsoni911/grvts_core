/**
 *
 */
package com.owners.gravitas.dto;

import java.math.BigDecimal;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class PersonTest.
 *
 * @author harshads
 */
public class ReferralExchangeDetailsTest {

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
     * ReferralExchangeDetails.
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
        defaultValues.put( "price", null );
        defaultValues.put( "firstName", null );
        defaultValues.put( "lastName", null );
        defaultValues.put( "phone", null );
        defaultValues.put( "city", null );
        defaultValues.put( "email", null );
        defaultValues.put( "state", null );
        defaultValues.put( "submissionType", null );
        defaultValues.put( "beds", null );
        defaultValues.put( "baths", null );
        defaultValues.put( "timeFrame", null );
        defaultValues.put( "mortgageStatus", null );
        defaultValues.put( "comments", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createPersonTestValues() {
        personTestValues = new PropertiesAndValues();
        personTestValues.put( "price", new BigDecimal( 0 ) );
        personTestValues.put( "firstName", "test" );
        personTestValues.put( "lastName", "test.com" );
        personTestValues.put( "phone", "test" );
        personTestValues.put( "city", "test" );
        personTestValues.put( "email", "test" );
        personTestValues.put( "state", "test" );
        personTestValues.put( "submissionType", "test" );
        personTestValues.put( "beds", new BigDecimal( 0 ) );
        personTestValues.put( "baths", new BigDecimal( 0 ) );
        personTestValues.put( "timeFrame", "test" );
        personTestValues.put( "mortgageStatus", "test" );
        personTestValues.put( "comments", "test" );
    }

    /**
     * Tests the {@link ReferralExchangeDetails} with default values. Tests the
     * getters
     * and setters of ReferralExchangeDetails.
     */
    @Test
    public final void testPerson() {
        final BeanLikeTester blt = new BeanLikeTester( ReferralExchangeDetails.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, personTestValues );
    }

}
