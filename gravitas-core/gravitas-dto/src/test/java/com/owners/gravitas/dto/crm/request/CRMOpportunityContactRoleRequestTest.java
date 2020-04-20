package com.owners.gravitas.dto.crm.request;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.dto.request.LeadRequest;

public class CRMOpportunityContactRoleRequestTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues contactRequestTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * CRMAccess.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createAccountRequestTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "contactId", null );
        defaultValues.put( "primary", false );
        defaultValues.put( "opportunityId", null );
        defaultValues.put( "role", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createAccountRequestTestValues() {
        contactRequestTestValues = new PropertiesAndValues();
        contactRequestTestValues.put( "contactId", "123" );
        contactRequestTestValues.put( "primary", true );
        contactRequestTestValues.put( "opportunityId", "1245" );
        contactRequestTestValues.put( "role", "878787" );
    }

    /**
     * Tests the {@link LeadRequest} with default values. Tests the getters
     * and setters of LeadRequest.
     */
    @Test
    public final void testCRMContactRequest() {
        final BeanLikeTester blt = new BeanLikeTester( CRMOpportunityContactRoleRequest.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, contactRequestTestValues );
        blt.testToString( defaultValues, contactRequestTestValues );
    }

}
