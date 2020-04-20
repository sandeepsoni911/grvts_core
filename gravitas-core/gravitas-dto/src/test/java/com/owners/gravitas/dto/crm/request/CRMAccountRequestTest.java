/**
 *
 */
package com.owners.gravitas.dto.crm.request;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class CRMAccountRequestTest.
 *
 * @author harshads
 */
public class CRMAccountRequestTest {

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
        defaultValues.put( "name", null );
        defaultValues.put( "street", null );
        defaultValues.put( "city", null );
        defaultValues.put( "state", null );
        defaultValues.put( "zip", null );
        defaultValues.put( "recordTypeId", null );
        defaultValues.put( "phone", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createAccountRequestTestValues() {
        accountRequestTestValues = new PropertiesAndValues();
        accountRequestTestValues.put( "name", "123" );
        accountRequestTestValues.put( "phone", "test.com" );
        accountRequestTestValues.put( "street", "1245" );
        accountRequestTestValues.put( "city", "878787" );
        accountRequestTestValues.put( "state", "454545" );
        accountRequestTestValues.put( "zip", "454548" );
        accountRequestTestValues.put( "recordTypeId", "454548" );
    }

    /**
     * Tests the {@link CRMAccountRequest} with default values. Tests the
     * getters
     * and setters of CRMAccountRequest.
     */
    @Test
    public final void testCRMAccountRequest() {
        final BeanLikeTester blt = new BeanLikeTester( CRMAccountRequest.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, accountRequestTestValues );
        blt.testToString( defaultValues, accountRequestTestValues );
    }

}
