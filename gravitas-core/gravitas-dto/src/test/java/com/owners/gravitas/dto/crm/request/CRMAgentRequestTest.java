/**
 *
 */
package com.owners.gravitas.dto.crm.request;

import java.util.Date;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class CRMAgentRequestTest.
 *
 * @author amits
 */
public class CRMAgentRequestTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues accountRequestTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * CRMAccountRequest.
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
        defaultValues.put( "fullName", null );
        defaultValues.put( "email", null );
        defaultValues.put( "status", null );
        defaultValues.put( "state", null );
        defaultValues.put( "phone", null );
        defaultValues.put( "address1", null );
        defaultValues.put( "address2", null );
        defaultValues.put( "city", null );
        defaultValues.put( "homeZip", null );
        defaultValues.put( "notes", null );
        defaultValues.put( "drivingRadius", null );
        defaultValues.put( "agentType", null );
        defaultValues.put( "mobileCarrier", null );
        defaultValues.put( "license", null );
        defaultValues.put( "recordTypeId", null );
        defaultValues.put( "startingDate", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createAccountRequestTestValues() {
        accountRequestTestValues = new PropertiesAndValues();
        accountRequestTestValues.put( "fullName", "123" );
        accountRequestTestValues.put( "phone", "test.com" );
        accountRequestTestValues.put( "email", "1245" );
        accountRequestTestValues.put( "status", "878787" );
        accountRequestTestValues.put( "state", "454545" );
        accountRequestTestValues.put( "address1", "test" );
        accountRequestTestValues.put( "address2", "test" );
        accountRequestTestValues.put( "city", "test" );
        accountRequestTestValues.put( "homeZip", "test" );
        accountRequestTestValues.put( "notes", "test" );
        accountRequestTestValues.put( "drivingRadius", "test" );
        accountRequestTestValues.put( "agentType", "Field Agent" );
        accountRequestTestValues.put( "mobileCarrier", "at&t" );
        accountRequestTestValues.put( "license", "testlic011" );
        accountRequestTestValues.put( "recordTypeId", "234234234asca35" );
        accountRequestTestValues.put( "startingDate", new Date() );
    }

    /**
     * Tests the {@link CRMAgentRequest} with default values. Tests the
     * getters
     * and setters of CRMAgentRequest.
     */
    @Test
    public final void testCRMAgentRequest() {
        final BeanLikeTester blt = new BeanLikeTester( CRMAgentRequest.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, accountRequestTestValues );
        blt.testToString( defaultValues, accountRequestTestValues );
    }

}
