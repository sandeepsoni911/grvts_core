package com.owners.gravitas.dto.crm.response;

import java.util.ArrayList;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.dto.response.BaseResponse.Status;

/**
 * The Class CRMResponseTest.
 *
 * @author vishwanathm
 */
public class CRMResponseTest {
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues crmResponseTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * CRMResponse.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createCRMResponseTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "totalSize", 0 );
        defaultValues.put( "done", false );
        defaultValues.put( "records", new ArrayList< >() );
        defaultValues.put( "message", "Operation was successful" );
        defaultValues.put( "status", Status.SUCCESS );
    }

    /**
     * This method is to create the actual values.
     */
    private void createCRMResponseTestValues() {
        crmResponseTestValues = new PropertiesAndValues();
        crmResponseTestValues.put( "totalSize", 7 );
        crmResponseTestValues.put( "done", true );
        crmResponseTestValues.put( "records", new ArrayList< >() );
        crmResponseTestValues.put( "message", "msg" );
        crmResponseTestValues.put( "status", Status.SUCCESS );
    }

    /**
     * Tests the CRMResponse with default values. Tests the getters
     * and setters of CRMResponse.
     */
    @Test
    public final void testCRMResponse() {
        final BeanLikeTester blt = new BeanLikeTester( CRMResponse.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, crmResponseTestValues );
    }
}
