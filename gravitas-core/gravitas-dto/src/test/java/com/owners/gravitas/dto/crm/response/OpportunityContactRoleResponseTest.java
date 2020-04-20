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
 * The Class OpportunityContactRoleResponseTest.
 *
 * @author harshads
 */
public class OpportunityContactRoleResponseTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues opportunityContactRoleResponseValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * OpportunityContactRoleResponse.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createOpportunityContactRoleResponseTestValues();
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
    private void createOpportunityContactRoleResponseTestValues() {
        opportunityContactRoleResponseValues = new PropertiesAndValues();
        opportunityContactRoleResponseValues.put( "id", "123" );
        opportunityContactRoleResponseValues.put( "message", "Operation was successful" );
        opportunityContactRoleResponseValues.put( "status", Status.SUCCESS );
    }

    /**
     * Tests the OpportunityContactRoleResponse with default values. Tests the getters
     * and
     * setters
     * of OpportunityContactRoleResponse.
     */
    @Test
    public final void testOpportunityResponse() {
        final BeanLikeTester blt = new BeanLikeTester( OpportunityContactRoleResponse.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, opportunityContactRoleResponseValues );
    }


}
