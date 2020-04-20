package com.owners.gravitas.dto;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class AgentTest.
 *
 * @author pabhishek
 */
public class AgentTest {

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
        createAgentTestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "biodata", null );
        defaultValues.put( "photoUrl", null );
        defaultValues.put( "address", null );
        defaultValues.put( "coverageArea", new ArrayList< String >() );
        defaultValues.put( "firstName", null );
        defaultValues.put( "lastName", null );
        defaultValues.put( "phone", null );
        defaultValues.put( "email", null );
        defaultValues.put( "drivingRadius", null );
        defaultValues.put( "mobileCarrier", null );
        defaultValues.put( "notes", null );
        defaultValues.put( "personalEmail", null );
        defaultValues.put( "managingBrokerId", null );
        defaultValues.put( "agentStartingDate", null );
        defaultValues.put( "status", null );
        defaultValues.put( "language", null );
        defaultValues.put( "type", null );
        defaultValues.put( "license", null );
        defaultValues.put( "score", null );
        defaultValues.put( "available", false );
        defaultValues.put( "revenue", null);
        defaultValues.put( "encodedPhotoData", null);
    }

    /**
     * Creates the agent test values.
     */
    private void createAgentTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "biodata", "test1" );
        testValues.put( "photoUrl", "test2" );
        testValues.put( "address", new UserAddress() );
        testValues.put( "coverageArea", new ArrayList< String >() );
        testValues.put( "firstName", "test4" );
        testValues.put( "lastName", "user4" );
        testValues.put( "phone", "1234567890" );
        testValues.put( "email", "test4.user4@gmail.com" );
        testValues.put( "drivingRadius", "test2" );
        testValues.put( "mobileCarrier", "test2" );
        testValues.put( "notes", "test2" );
        testValues.put( "personalEmail", "test2" );
        testValues.put( "managingBrokerId", "test2" );
        testValues.put( "agentStartingDate", "test2" );
        testValues.put( "status", "ACTIVE" );
        testValues.put( "language", "english" );
        testValues.put( "type", "Field Agent" );
        testValues.put( "license", "testlic323" );
        testValues.put( "score", 0.0 );
        testValues.put( "available", true );
        testValues.put( "revenue", 0.0 );
        testValues.put( "encodedPhotoData", "Test" );
    }

    /**
     * Test agent.
     */
    @Test
    public final void testAgent() {
        final BeanLikeTester blt = new BeanLikeTester( Agent.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }

    /**
     * Test add coverage area.
     */
    @Test
    public final void testAddCoverageArea() {
        Agent agent = new Agent();
        agent.addCoverageArea( "12345" );
        assertEquals( agent.getCoverageArea().iterator().next(), "12345" );
    }

    /**
     * Test add coverage area.
     */
    @Test
    public final void testRemoveCoverageArea() {
        Agent agent = new Agent();
        agent.removeCoverageArea( "12345" );
    }
}
