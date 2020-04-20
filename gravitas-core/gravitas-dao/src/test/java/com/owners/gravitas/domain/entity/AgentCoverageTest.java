package com.owners.gravitas.domain.entity;

import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.ConstructorSignatureAndPropertiesMapping;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class AgentCoverageTest.
 *
 * @author pabhishek
 */
public class AgentCoverageTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The agent coverage values. */
    private PropertiesAndValues agentCoverageValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createAgentCoverageTestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "id", null );
        defaultValues.put( "createdBy", null );
        defaultValues.put( "createdDate", null );
        defaultValues.put( "lastModifiedBy", null );
        defaultValues.put( "lastModifiedDate", null );
        defaultValues.put( "zip", null );
        defaultValues.put( "servable", true );
        defaultValues.put( "type", null );
        defaultValues.put( "agentDetails", null );
    }

    /**
     * Creates the agent coverage test values.
     */
    private void createAgentCoverageTestValues() {
        agentCoverageValues = new PropertiesAndValues();
        agentCoverageValues.put( "id", "test" );
        agentCoverageValues.put( "createdBy", null );
        agentCoverageValues.put( "createdDate", new DateTime() );
        agentCoverageValues.put( "lastModifiedBy", null );
        agentCoverageValues.put( "lastModifiedDate", new DateTime() );
        agentCoverageValues.put( "zip", "ziptest" );
        agentCoverageValues.put( "servable", true );
        agentCoverageValues.put( "type", "typetest" );
        agentCoverageValues.put( "agentDetails", new AgentDetails() );
    }

    /**
     * Test agent coverage.
     */
    @Test
    public final void testAgentCoverage() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        mapping.put( signature1, Arrays.asList() );
        List< Class< ? > > signature2 = Arrays.< Class< ? > > asList( String.class, boolean.class, String.class );
        mapping.put( signature2, Arrays.asList( "zip", "servable", "type" ) );
        final BeanLikeTester blt = new BeanLikeTester( AgentCoverage.class, mapping );
        blt.testMutatorsAndAccessors( defaultValues, agentCoverageValues );
    }

}
