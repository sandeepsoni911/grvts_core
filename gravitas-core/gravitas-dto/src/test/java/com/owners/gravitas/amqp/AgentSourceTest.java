package com.owners.gravitas.amqp;

import java.util.Date;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class AgentSourceTest.
 *
 * @author shivamm
 */
public class AgentSourceTest {
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues testValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * OpportunityContact.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createAddressTestValues();
    }

    /**
     * This method is to create the default values.
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
    }

    /**
     * This method is to create the actual values.
     */
    private void createAddressTestValues() {
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
    }

    /**
     * Tests the {@link AgentSource} with default values. Tests the
     * getters
     * and setters of AgentSource.
     */
    @Test
    public final void testAddress() {
        final BeanLikeTester blt = new BeanLikeTester( AgentSource.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }

}
