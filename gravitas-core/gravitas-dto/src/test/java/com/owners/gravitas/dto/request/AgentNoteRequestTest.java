package com.owners.gravitas.dto.request;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class AgentNoteRequestTest.
 */
public class AgentNoteRequestTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues slackRequestTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * AgentNoteRequest.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createSlackRequestTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "details", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createSlackRequestTestValues() {
        slackRequestTestValues = new PropertiesAndValues();
        slackRequestTestValues.put( "details", "test" );
    }

    /**
     * Tests the {@link AgentNoteRequest} with default values. Tests the getters
     * and setters of AgentNoteRequest.
     */
    @Test
    public final void testAgentNoteRequest() {
        final BeanLikeTester blt = new BeanLikeTester( AgentNoteRequest.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, slackRequestTestValues );
    }
}
