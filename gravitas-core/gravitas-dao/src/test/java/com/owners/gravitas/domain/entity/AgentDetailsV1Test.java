package com.owners.gravitas.domain.entity;

import java.util.Arrays;
import java.util.List;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.ConstructorSignatureAndPropertiesMapping;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class AgentDetailsV1Test.
 *
 * @author pabhishek
 */
public class AgentDetailsV1Test {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The agent details V 1 values. */
    private PropertiesAndValues agentDetailsV1Values = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createAgentDetailsV1TestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "agentName", null );
        defaultValues.put( "email", null );
        defaultValues.put( "status", null );
        defaultValues.put( "phone", null );
        defaultValues.put( "state", null );
        defaultValues.put( "homeZip", null );
        defaultValues.put( "zip", null );
        defaultValues.put( "distance", null );
        defaultValues.put( "name", null );
        defaultValues.put( "mustKeep", null );
        defaultValues.put( "doNotDefaultMilage", null );
        defaultValues.put( "score", null );
        defaultValues.put( "language", null );
    }

    /**
     * Creates the agent details V 1 test values.
     */
    private void createAgentDetailsV1TestValues() {
        agentDetailsV1Values = new PropertiesAndValues();
        agentDetailsV1Values.put( "agentName", "agentNametest" );
        agentDetailsV1Values.put( "email", "emailtest" );
        agentDetailsV1Values.put( "status", "statustest" );
        agentDetailsV1Values.put( "phone", "phonetest" );
        agentDetailsV1Values.put( "state", "statetest" );
        agentDetailsV1Values.put( "homeZip", "homeziptest" );
        agentDetailsV1Values.put( "zip", "ziptest" );
        agentDetailsV1Values.put( "distance", "distancetest" );
        agentDetailsV1Values.put( "name", "nametest" );
        agentDetailsV1Values.put( "mustKeep", "mustkeeptest" );
        agentDetailsV1Values.put( "doNotDefaultMilage", "test1" );
        agentDetailsV1Values.put( "score", "test" );
        agentDetailsV1Values.put( "language", "test" );
    }

    /**
     * Test agent details V 1.
     */
    @Test
    public final void testAgentDetailsV1() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        mapping.put( signature1, Arrays.asList() );
        final BeanLikeTester blt = new BeanLikeTester( AgentDetailsV1.class, mapping );
        blt.testMutatorsAndAccessors( defaultValues, agentDetailsV1Values );
    }
}
