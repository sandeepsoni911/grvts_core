package com.owners.gravitas.dto.response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.ConstructorSignatureAndPropertiesMapping;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.dto.ErrorDetail;

/**
 * The Class AgentEmailResultTest.
 *
 * @author shivamm
 */
public class AgentEmailResultTest {
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues testValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createAgentResponseTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "status", null );
        defaultValues.put( "errors", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createAgentResponseTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "status", "test" );
        testValues.put( "errors", new ArrayList< ErrorDetail >() );
    }

    /**
     * Tests the {@link AgentEmailResult} with default values. Tests the
     * getters
     * and setters of AgentEmailResult.
     */
    @Test
    public final void testAgentEmailResult() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        List< Class< ? > > signature1 = Arrays.< Class< ? > > asList( String.class, List.class );
        mapping.put( signature1, Arrays.asList( "status", "errors" ) );
        final BeanLikeTester blt = new BeanLikeTester( AgentEmailResult.class, mapping );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }
}
