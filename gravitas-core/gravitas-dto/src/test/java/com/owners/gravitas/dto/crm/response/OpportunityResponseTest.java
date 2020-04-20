/**
 * 
 */
package com.owners.gravitas.dto.crm.response;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.dto.response.BaseResponse.Status;

/**
 * The Class OpportunityResponseTest.
 *
 * @author harshads
 */
public class OpportunityResponseTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues opportunityResponseValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * OpportunityResponse.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createOpportunityResponseTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "id", null );
        defaultValues.put( "message", "Operation was successful" );
        defaultValues.put( "status", Status.SUCCESS );
    }

    /**
     * This method is to create the actual values.
     */
    private void createOpportunityResponseTestValues() {
        opportunityResponseValues = new PropertiesAndValues();
        opportunityResponseValues.put( "id", "123" );
        opportunityResponseValues.put( "message", "Operation was successful" );
        opportunityResponseValues.put( "status", Status.SUCCESS );
    }

    /**
     * Tests the OpportunityResponse with default values. Tests the getters
     * and
     * setters
     * of OpportunityResponse.
     */
    @Test
    public final void testOpportunityResponse() {
        final BeanLikeTester blt = new BeanLikeTester( OpportunityResponse.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, opportunityResponseValues );
    }
}
