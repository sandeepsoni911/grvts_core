package com.owners.gravitas.dto.crm.response;

import java.util.Date;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class CRMAgentResponseTest.
 *
 * @author pabhishek
 */
public class CRMAgentResponseTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The test values. */
    private PropertiesAndValues testValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createCRMAgentResponseTestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "id", null );
        defaultValues.put( "name", null );
        defaultValues.put( "email", null );
        defaultValues.put( "phone", null );
        defaultValues.put( "state", null );
        defaultValues.put( "status", null );
        defaultValues.put( "address1", null );
        defaultValues.put( "address2", null );
        defaultValues.put( "city", null );
        defaultValues.put( "homeZip", null );
        defaultValues.put( "notes", null );
        defaultValues.put( "drivingRadius", null );
        defaultValues.put( "recordTypeId", null );
        defaultValues.put( "agentType", null );
        defaultValues.put( "startingDate", null );
        defaultValues.put( "mobileCarrier", null );
        defaultValues.put( "license", null );
        defaultValues.put( "fieldAgent", false );
        defaultValues.put( "available", false );
        defaultValues.put( "savedId", null );
    }

    /**
     * Creates the CRM agent response test values.
     */
    private void createCRMAgentResponseTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "id", "testId" );
        testValues.put( "name", "testName" );
        testValues.put( "email", "testEmail" );
        testValues.put( "phone", "testPhone" );
        testValues.put( "state", "testState" );
        testValues.put( "status", "testStatus" );
        testValues.put( "address1", "testAddress1" );
        testValues.put( "address2", "testAddress2" );
        testValues.put( "city", "testCity" );
        testValues.put( "homeZip", "testZip" );
        testValues.put( "notes", "testNotes" );
        testValues.put( "drivingRadius", "testRadius" );
        testValues.put( "recordTypeId", "testRecord" );
        testValues.put( "agentType", "testAgentType" );
        testValues.put( "startingDate", new Date() );
        testValues.put( "mobileCarrier", "testMobileCarrier" );
        testValues.put( "license", "testLicense" );
        testValues.put( "fieldAgent", true );
        testValues.put( "available", true );
        testValues.put( "savedId", "testSavedId" );
    }

    /**
     * Test CRM agent response.
     */
    @Test
    public final void testCRMAgentResponse() {
        final BeanLikeTester blt = new BeanLikeTester( CRMAgentResponse.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }
}
