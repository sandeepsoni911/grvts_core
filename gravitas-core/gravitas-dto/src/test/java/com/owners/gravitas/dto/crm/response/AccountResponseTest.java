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
 * The Class AccountResponseTest.
 *
 * @author harshads
 */
public class AccountResponseTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues accountResponseValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * CRMAccess.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createAccountResponseTestValues();
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
    private void createAccountResponseTestValues() {
        accountResponseValues = new PropertiesAndValues();
        accountResponseValues.put( "id", "123" );
        accountResponseValues.put( "message", "Operation was successful" );
        accountResponseValues.put( "status", Status.SUCCESS );
    }

    /**
     * Tests the AccountResponse with default values. Tests the getters
     * and
     * setters
     * of AccountResponse.
     */
    @Test
    public final void testAccountResponse() {
        final BeanLikeTester blt = new BeanLikeTester( AccountResponse.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, accountResponseValues );
    }

}
