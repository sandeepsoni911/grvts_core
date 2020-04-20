package com.owners.gravitas.dto.request;

import java.util.ArrayList;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class AgentCoverageRequestTest.
 *
 * @author shivamm
 */
public class AgentCoverageRequestTest {
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues agentCoverageRequestTest = null;

    /**
     * This method is calls the methods to create default and test values for
     * AgentDeviceRequest.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createAgentCoverageRequestTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "zipCodes", null );
        defaultValues.put( "servable", false );
        defaultValues.put( "email", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createAgentCoverageRequestTestValues() {
        agentCoverageRequestTest = new PropertiesAndValues();
        agentCoverageRequestTest.put( "zipCodes", new ArrayList<String>() );
        agentCoverageRequestTest.put( "servable", true );
        agentCoverageRequestTest.put( "email", "description" );
    }

    /**
     * Tests the {@link AgentCoverageRequestTest} with default values. Tests the
     * getters
     * and setters of AgentCoverageRequestTest.
     */
    @Test
    public final void testActionRequest() {
        final BeanLikeTester blt = new BeanLikeTester( AgentCoverageRequest.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, agentCoverageRequestTest );
    }
}
