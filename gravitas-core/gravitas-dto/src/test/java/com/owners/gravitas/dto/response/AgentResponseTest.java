package com.owners.gravitas.dto.response;

import java.util.Arrays;
import java.util.List;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.ConstructorSignatureAndPropertiesMapping;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.dto.response.BaseResponse.Status;

/**
 * The Class AgentResponseTest.
 *
 * @author amits
 */
public class AgentResponseTest {
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
     * NotificationResponse.
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
        defaultValues.put( "id", null );
        defaultValues.put( "status", Status.SUCCESS );
        defaultValues.put( "message", "Operation was successful" );
    }

    /**
     * This method is to create the actual values.
     */
    private void createAgentResponseTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "id", "test" );
        testValues.put( "status", Status.SUCCESS );
        testValues.put( "message", "Operation was successful" );
    }

    /**
     * Tests the {@link AgentResponse} with default values. Tests the
     * getters
     * and setters of AgentResponse.
     */
    @Test
    public final void testAgentResponse() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();

        List< Class< ? > > signature1 = Arrays.< Class< ? > > asList( String.class );
        List< Class< ? > > signature2 = Arrays.< Class< ? > > asList( String.class, BaseResponse.Status.class,
                String.class );
        mapping.put( signature1, Arrays.asList( "id" ) );
        mapping.put( signature2, Arrays.asList( "id", "status", "message" ) );
        final BeanLikeTester blt = new BeanLikeTester( AgentResponse.class, mapping );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }
}
