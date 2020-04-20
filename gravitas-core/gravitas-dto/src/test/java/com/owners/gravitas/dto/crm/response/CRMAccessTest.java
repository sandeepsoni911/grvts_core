package com.owners.gravitas.dto.crm.response;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.dto.response.BaseResponse.Status;

/**
 * The Class CRMAccessTest.
 *
 * @author vishwanathm
 */
public class CRMAccessTest {
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues crmAccessTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * CRMAccess.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createCRMAccessTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "accessToken", null );
        defaultValues.put( "instanceUrl", null );
        defaultValues.put( "id", null );
        defaultValues.put( "tokenType", null );
        defaultValues.put( "issuedAt", null );
        defaultValues.put( "signature", null );
        defaultValues.put( "message", "Operation was successful" );
        defaultValues.put( "status", Status.SUCCESS );
    }

    /**
     * This method is to create the actual values.
     */
    private void createCRMAccessTestValues() {
        crmAccessTestValues = new PropertiesAndValues();
        crmAccessTestValues.put( "accessToken", "123" );
        crmAccessTestValues.put( "instanceUrl", "test.com" );
        crmAccessTestValues.put( "id", "1245" );
        crmAccessTestValues.put( "tokenType", "878787" );
        crmAccessTestValues.put( "issuedAt", "454545" );
        crmAccessTestValues.put( "signature", "454548" );
        crmAccessTestValues.put( "message", "msg" );
        crmAccessTestValues.put( "status", Status.SUCCESS );
    }

    /**
     * Tests the CRMAccess with default values. Tests the getters
     * and
     * setters
     * of CRMAccess.
     */
    @Test
    public final void testCRMAccess() {
        final BeanLikeTester blt = new BeanLikeTester( CRMAccess.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, crmAccessTestValues );
    }
}
