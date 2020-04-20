package com.owners.gravitas.dto.crm.request;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.dto.request.LeadRequest;

/**
 * The Class CRMContactRequestTest.
 */
public class CRMContactRequestTest {

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
        defaultValues.put( "firstName", null );
        defaultValues.put( "lastName", null );
        defaultValues.put( "email", null );
        defaultValues.put( "accountId", null );
        defaultValues.put( "phone", null );
        defaultValues.put( "street", null );
        defaultValues.put( "city", null );
        defaultValues.put( "state", null );
        defaultValues.put( "zip", null );
        defaultValues.put( "recordType", null );
        defaultValues.put( "preferredContactTime", null );
        defaultValues.put( "preferredContactMethod", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createAccountRequestTestValues() {
        contactRequestTestValues = new PropertiesAndValues();
        contactRequestTestValues.put( "firstName", "123" );
        contactRequestTestValues.put( "lastName", "test.com" );
        contactRequestTestValues.put( "email", "1245" );
        contactRequestTestValues.put( "accountId", "878787" );
        contactRequestTestValues.put( "phone", "454545" );
        contactRequestTestValues.put( "street", "454548" );
        contactRequestTestValues.put( "city", "454548" );
        contactRequestTestValues.put( "state", "454548" );
        contactRequestTestValues.put( "zip", "454548" );
        contactRequestTestValues.put( "recordType", "454548" );
        contactRequestTestValues.put( "preferredContactTime", "test" );
        contactRequestTestValues.put( "preferredContactMethod", "test" );
    }

    /**
     * Tests the {@link LeadRequest} with default values. Tests the getters
     * and setters of LeadRequest.
     */
    @Test
    public final void testCRMContactRequest() {
        final BeanLikeTester blt = new BeanLikeTester( CRMContactRequest.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, contactRequestTestValues );
        blt.testToString( defaultValues, contactRequestTestValues );
    }

}
