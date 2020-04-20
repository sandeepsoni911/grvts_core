package com.owners.gravitas.dto.response;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.dto.ErrorDetail;

/**
 * The Class AgentEmailResponseTest.
 *
 * @author shivamm
 */
public class AgentEmailResponseTest {

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
        createAgentEmailResponseTestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "results", null );
    }

    /**
     * Creates the agent details response test values.
     */
    private void createAgentEmailResponseTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "results", new ArrayList< AgentEmailResult >() );
    }

    /**
     * Test agent details response.
     */
    @Test
    public final void testAgentDetailsResponse() {
        final BeanLikeTester blt = new BeanLikeTester( AgentEmailResponse.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }

    /**
     * Test add result.
     */
    @Test
    public final void testAddResult() {
        AgentEmailResult agentEmailResult = new AgentEmailResult( "test", new ArrayList< ErrorDetail >() );
        AgentEmailResponse resp = new AgentEmailResponse();
        resp.addResult( agentEmailResult );
        List< AgentEmailResult > field = ( List< AgentEmailResult > ) ReflectionTestUtils.getField( resp, "results" );
        assertEquals( field.get( 0 ), agentEmailResult );
    }

    /**
     * Test agent details response.
     */
    @Test
    public final void testClearResults() {
        AgentEmailResult agentEmailResult = new AgentEmailResult( "test", new ArrayList< ErrorDetail >() );
        AgentEmailResponse resp = new AgentEmailResponse();
        resp.clearResults();
        List< AgentEmailResult > field = ( List< AgentEmailResult > ) ReflectionTestUtils.getField( resp, "results" );
        assertEquals( 0, field.size() );
    }
}
