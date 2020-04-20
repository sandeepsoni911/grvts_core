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
 * The Class ContactResponseTest.
 *
 * @author harshads
 */
public class ContactResponseTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues contactResponseValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * ContactResponse.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createContactResponseTestValues();
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
    private void createContactResponseTestValues() {
        contactResponseValues = new PropertiesAndValues();
        contactResponseValues.put( "id", "123" );
        contactResponseValues.put( "message", "Operation was successful" );
        contactResponseValues.put( "status", Status.SUCCESS );
    }

    /**
     * Tests the ContactResponse with default values. Tests the getters
     * and
     * setters
     * of ContactResponse.
     */
    @Test
    public final void testContactResponse() {
        final BeanLikeTester blt = new BeanLikeTester( ContactResponse.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, contactResponseValues );
    }



}
