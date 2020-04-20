package com.owners.gravitas.domain;

import java.util.Arrays;
import java.util.List;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.ConstructorSignatureAndPropertiesMapping;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.domain.Agent;
import com.owners.gravitas.domain.AgentHolder;

/**
 * The Class AgentHolderTest.
 *
 * @author amits
 */
public class AgentHolderTest {
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues addressTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * AgentHolder.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createAgentHolderTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "agentId", null );
        defaultValues.put( "agent", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createAgentHolderTestValues() {
        addressTestValues = new PropertiesAndValues();
        addressTestValues.put( "agentId", "test" );
        addressTestValues.put( "agent", new Agent() );
    }

    /**
     * Tests the {@link AgentHolder} with default values. Tests the getters
     * and setters of AgentHolder.
     */
    @Test
    public final void testAgentHolder() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();

        List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        List< Class< ? > > signature2 = Arrays.< Class< ? > > asList( String.class );
        List< Class< ? > > signature3 = Arrays.< Class< ? > > asList( String.class, Agent.class );
        mapping.put( signature1, Arrays.asList() );
        mapping.put( signature2, Arrays.asList( "agentId" ) );
        mapping.put( signature3, Arrays.asList( "agentId", "agent" ) );
        final BeanLikeTester blt = new BeanLikeTester( AgentHolder.class, mapping );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, addressTestValues );
    }
}
