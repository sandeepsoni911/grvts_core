package com.owners.gravitas.domain.entity;

import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.ConstructorSignatureAndPropertiesMapping;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class AgentPerformanceLogIdTest.
 * 
 * @author pabhishek
 */
public class AgentPerformanceLogIdTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The agent performance log id values. */
    private PropertiesAndValues agentPerformanceLogIdValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createAgentPerformanceLogIdTestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "agentEmail", null );
        defaultValues.put( "createdDate", null );
    }

    /**
     * Creates the agent performance log id test values.
     */
    private void createAgentPerformanceLogIdTestValues() {
        agentPerformanceLogIdValues = new PropertiesAndValues();
        agentPerformanceLogIdValues.put( "agentEmail", "test" );
        agentPerformanceLogIdValues.put( "createdDate", new DateTime() );
    }

    /**
     * Test agent performance log id.
     */
    @Test
    public final void testAgentPerformanceLogId() {
        final ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        final List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        mapping.put( signature1, Arrays.asList() );
        final List< Class< ? > > signature2 = Arrays.< Class< ? > > asList( String.class, DateTime.class );
        mapping.put( signature2, Arrays.asList( "agentEmail", "createdDate" ) );
        final BeanLikeTester blt = new BeanLikeTester( AgentPerformanceLogId.class, mapping );
        blt.testMutatorsAndAccessors( defaultValues, agentPerformanceLogIdValues );
    }

    /**
     * Test equals and hash code.
     */
    @Test
    public final void testEqualsAndHashCode() {
        final DateTime dateTime = new DateTime();
        final AgentPerformanceLogId agentPerformanceLogId1 = new AgentPerformanceLogId( "abcd1", dateTime );
        final AgentPerformanceLogId agentPerformanceLogId2 = new AgentPerformanceLogId( "abcd1", dateTime );
        final AgentPerformanceLogId agentPerformanceLogId3 = new AgentPerformanceLogId( null, dateTime );
        final AgentPerformanceLogId agentPerformanceLogId4 = new AgentPerformanceLogId( null, dateTime );
        Assert.assertEquals( agentPerformanceLogId1, agentPerformanceLogId2 );
        Assert.assertTrue( agentPerformanceLogId1.hashCode() == agentPerformanceLogId2.hashCode() );
        Assert.assertEquals( agentPerformanceLogId3, agentPerformanceLogId4 );
        Assert.assertTrue( agentPerformanceLogId3.hashCode() == agentPerformanceLogId4.hashCode() );
    }
}
