package com.owners.gravitas.dto.response;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.dto.Agent;

/**
 * The Class AgentDetailsResponseTest.
 * 
 * @author pabhishek
 */
public class AgentDetailsResponseTest {

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
        defaultValues.put( "details", null );
        defaultValues.put( "message", "Operation was successful" );
        defaultValues.put( "status", BaseResponse.Status.SUCCESS );
    }

    /**
     * Creates the agent details response test values.
     */
    private void createAgentDetailsResponseTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "details", new Agent() );
        testValues.put( "message", "test" );
        testValues.put( "status", BaseResponse.Status.FAILURE );
    }

    /**
     * Test agent details response.
     */
    @Test
    public final void testAgentDetailsResponse() {
        final BeanLikeTester blt = new BeanLikeTester( AgentDetailsResponse.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }
}
