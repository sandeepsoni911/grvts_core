package com.owners.gravitas.domain;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


/**
 * The Class AgentTest.
 *
 * @author amits
 */
public class AgentTest {
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
     * Agent.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "info", null );
        defaultValues.put( "contacts", new HashMap<>() );
        defaultValues.put( "opportunities", new HashMap<>() );
        defaultValues.put( "requests", new HashMap<>() );
        defaultValues.put( "agentNotes", new HashMap<>() );
        defaultValues.put( "tasks", new HashMap<>() );
    }

    /**
     * This method is to create the actual values.
     */
    private void createTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "info", new AgentInfo() );
        testValues.put( "contacts", new HashMap<>() );
        testValues.put( "opportunities", new HashMap<>() );
        testValues.put( "requests", new HashMap<>() );
        testValues.put( "agentNotes", new HashMap<>() );
        testValues.put( "tasks", new HashMap<>() );
    }

    /**
     * Tests the {@link Agent} with default values. Tests the getters
     * and setters of Agent.
     */
    @Test
    public final void testAgent() {
        final BeanLikeTester blt = new BeanLikeTester( Agent.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
        Agent agent = new Agent();
        agent.addAgentNote( "test", new Note() );
        agent.addContact( "test", new Contact() );
        agent.addOpportunity( "test", new Opportunity() );
        agent.addRequest( "test", new Request() );
        Assert.assertNotNull( agent.getAgentNotes() );
        Assert.assertNotNull( agent.getContacts() );
        Assert.assertNotNull( agent.getOpportunities() );
        Assert.assertNotNull( agent.getRequests() );
    }

    /**
     * Test addTask method
     */
    @Test
    public final void testAddTask() {
        Agent agent = new Agent();
        String taskId = "test";
        Task task = new Task();
        agent.addTask( taskId, task );
        assertEquals( agent.getTasks().get( taskId ), task );
    }

}
