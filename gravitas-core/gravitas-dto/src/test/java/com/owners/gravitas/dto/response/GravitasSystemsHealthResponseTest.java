package com.owners.gravitas.dto.response;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.dto.Agent;
import com.owners.gravitas.dto.response.BaseResponse.Status;

/**
 * The Class GravitasSystemsHealthResponseTest.
 *
 * @author shivamm
 */
public class GravitasSystemsHealthResponseTest {

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
        createAgentDetailsResponseTestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "topicsStatus", null );
        defaultValues.put( "firebaseStatus", null );
        defaultValues.put( "salesforceStatus", null );
        defaultValues.put( "gravitasDBStatus", null );
        defaultValues.put( "rabbitMQStatus", null );
        defaultValues.put( "message", "Operation was successful" );
        defaultValues.put( "status", Status.SUCCESS );
    }

    /**
     * Creates the agent details response test values.
     */
    private void createAgentDetailsResponseTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "topicsStatus", "test" );
        testValues.put( "firebaseStatus", "test" );
        testValues.put( "salesforceStatus", "test" );
        testValues.put( "gravitasDBStatus", "test" );
        testValues.put( "rabbitMQStatus", "test" );
        testValues.put( "message", "Operation was successful" );
        testValues.put( "status", Status.FAILURE );
    }

    /**
     * Test agent details response.
     */
    @Test
    public final void testAgentDetailsResponse() {
        final BeanLikeTester blt = new BeanLikeTester( GravitasSystemsHealthResponse.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }
}
