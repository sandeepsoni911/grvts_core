/**
 * 
 */
package com.owners.gravitas.dto.request;

import java.util.ArrayList;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author nishak
 *
 */
public class ClientVersionRequestTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues clientVersionRequestTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * VersionRequestTest.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createActionRequestTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "versionList", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createActionRequestTestValues() {
        clientVersionRequestTestValues = new PropertiesAndValues();
        clientVersionRequestTestValues.put( "versionList", new ArrayList<>() );
    }

    /**
     * Tests the {@link ClientVersionRequest} with default values. Tests the
     * getters
     * and setters of ClientVersionRequest.
     */
    @Test
    public final void testActionRequest() {
        final BeanLikeTester blt = new BeanLikeTester( ClientVersionRequest.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, clientVersionRequestTestValues );
    }
}
