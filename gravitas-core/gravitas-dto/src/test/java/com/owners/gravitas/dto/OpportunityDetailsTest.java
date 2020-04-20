package com.owners.gravitas.dto;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class OpportunityDetailsTest.
 *
 * @author ankusht
 */
public class OpportunityDetailsTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;

    /**
     * for testing values.
     */
    private PropertiesAndValues errorDetailsTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * ErrorDetail.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createErrorDetailTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "name", null );
        defaultValues.put( "type", null );
        defaultValues.put( "zipcode", null );
        defaultValues.put( "state", null );
        defaultValues.put( "id", null );
        defaultValues.put( "f2fCreatedOn", null );
        defaultValues.put( "opportunityCreatedOn", null );
        defaultValues.put( "email", null );
    }

    /**
     * This method is to create the default values.
     */
    private void createErrorDetailTestValues() {
        errorDetailsTestValues = new PropertiesAndValues();
        errorDetailsTestValues.put( "name", "test" );
        errorDetailsTestValues.put( "type", "test" );
        errorDetailsTestValues.put( "zipcode", "test" );
        errorDetailsTestValues.put( "state", "test" );
        errorDetailsTestValues.put( "id", "test" );
        errorDetailsTestValues.put( "f2fCreatedOn", "03/17/2018" );
        errorDetailsTestValues.put( "opportunityCreatedOn", "03/17/2018" );
        errorDetailsTestValues.put( "email", "test@email.com" );
    }

    /**
     * Tests the {@link OpportunityDetails} with default values. Tests the getters
     * and setters of OpportunityDetails.
     */
    @Test
    public final void testOpportunityDetails() {
        final BeanLikeTester blt = new BeanLikeTester( OpportunityDetails.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, errorDetailsTestValues );
    }
}
