package com.owners.gravitas.dto.response;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.dto.response.BaseResponse.Status;

/**
 * The Class PostResponseTest.
 *
 * @author amits
 */
public class PostResponseTest {
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
     * PostResponse.
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
        defaultValues.put( "name", null );
        defaultValues.put( "status", Status.SUCCESS );
        defaultValues.put( "message", "Operation was successful" );
    }

    /**
     * This method is to create the actual values.
     */
    private void createAgentResponseTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "name", "test" );
        testValues.put( "status", Status.SUCCESS );
        testValues.put( "message", "Operation was successful" );
    }

    /**
     * Tests the {@link PostResponse} with default values. Tests the
     * getters
     * and setters of PostResponse.
     */
    @Test
    public final void testAgentResponse() {
        final BeanLikeTester blt = new BeanLikeTester( PostResponse.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }
}
