package com.owners.gravitas.dto.request;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class VersionRequestTest.
 *
 * @author shivamm
 */
public class VersionRequestTest {
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues versionRequestTestValues = null;

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
        defaultValues.put( "clientType", null );
        defaultValues.put( "minVersion", null );
        defaultValues.put( "minMessage", null );
        defaultValues.put( "currentVersion", null );
        defaultValues.put( "currentMessage", null );
        defaultValues.put( "url", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createActionRequestTestValues() {
        versionRequestTestValues = new PropertiesAndValues();
        versionRequestTestValues.put( "clientType", "AgentApp-IOS" );
        versionRequestTestValues.put( "minVersion", "1.0.1" );
        versionRequestTestValues.put( "minMessage", "Updated" );
        versionRequestTestValues.put( "currentVersion", "1.0.1" );
        versionRequestTestValues.put( "currentMessage", "Updated" );
        versionRequestTestValues.put( "url", "http://android.playstore.com" );
    }

    /**
     * Tests the {@link ClientVersionRequest} with default values. Tests the
     * getters
     * and setters of ClientVersionRequest.
     */
    @Test
    public final void testActionRequest() {
        final BeanLikeTester blt = new BeanLikeTester( VersionRequest.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, versionRequestTestValues );
    }
}
