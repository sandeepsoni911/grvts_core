package com.owners.gravitas.dto.crm.response;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.dto.response.BaseResponse.Status;

/**
 * The Class CRMConvertedOpportunityResponseTest.
 *
 * @author shivamm
 */
public class CRMConvertedOpportunityResponseTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues opportunityResponseTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * CRMAccess.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createopportunityRequestTestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "result", null );
        defaultValues.put( "opportunityid", null );
        defaultValues.put( "status", Status.SUCCESS );
        defaultValues.put( "message", "Operation was successful" );

    }

    /**
     * This method is to create the actual values.
     */
    private void createopportunityRequestTestValues() {
        opportunityResponseTestValues = new PropertiesAndValues();
        opportunityResponseTestValues.put( "result", "test" );
        opportunityResponseTestValues.put( "opportunityid", "test" );
        opportunityResponseTestValues.put( "status", Status.SUCCESS );
        opportunityResponseTestValues.put( "message", "Operation was successful" );

    }

    /**
     * Test CRM converted opportunity response.
     */
    @Test
    public final void testCRMConvertedOpportunityResponse() {
        final BeanLikeTester blt = new BeanLikeTester( CRMConvertedOpportunityResponse.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, opportunityResponseTestValues );
    }

}
