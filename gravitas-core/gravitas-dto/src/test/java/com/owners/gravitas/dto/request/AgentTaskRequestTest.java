package com.owners.gravitas.dto.request;

import java.util.Date;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class AgentTaskRequestTest.
 *
 * @author amits
 */
public class AgentTaskRequestTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues agentTaskRequestTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * AgentTaskRequest.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createAgentTaskRequestTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "title", null );
        defaultValues.put( "description", null );
        defaultValues.put( "dueDtm", null );
        defaultValues.put( "location", null );
        defaultValues.put( "type", null );
        defaultValues.put( "status", null );
        defaultValues.put( "createdBy", null );
        defaultValues.put( "primary", false );
        defaultValues.put( "coShoppingId", null );
        }

    /**
     * This method is to create the actual values.
     */
    private void createAgentTaskRequestTestValues() {
        agentTaskRequestTestValues = new PropertiesAndValues();
        agentTaskRequestTestValues.put( "title", "test" );
        agentTaskRequestTestValues.put( "description", "test" );
        agentTaskRequestTestValues.put( "dueDtm", new Date() );
        agentTaskRequestTestValues.put( "location", "test" );
        agentTaskRequestTestValues.put( "type", "OTHER" );
        agentTaskRequestTestValues.put( "status", "Pending Confirmation" );
        agentTaskRequestTestValues.put( "createdBy", "Inside Sales" );
        agentTaskRequestTestValues.put( "primary", false );
        agentTaskRequestTestValues.put( "coShoppingId", "tests" );

    }

    /**
     * Tests the {@link AgentTaskRequest} with default values. Tests the getters
     * and setters of AgentTaskRequest.
     */
    @Test(enabled = false)
    public final void testAgentTaskRequest() {
        final BeanLikeTester blt = new BeanLikeTester( AgentTaskRequest.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, agentTaskRequestTestValues );
    }
}
