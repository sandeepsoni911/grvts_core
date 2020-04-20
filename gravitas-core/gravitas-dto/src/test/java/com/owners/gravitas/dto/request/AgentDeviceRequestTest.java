package com.owners.gravitas.dto.request;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.hubzu.notification.dto.client.push.AppType;

/**
 * The Class AgentDeviceRequestTest.
 */
public class AgentDeviceRequestTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues slackRequestTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * AgentDeviceRequest.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createSlackRequestTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "deviceId", null );
        defaultValues.put( "deviceType", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createSlackRequestTestValues() {
        slackRequestTestValues = new PropertiesAndValues();
        slackRequestTestValues.put( "deviceId", "test" );
        slackRequestTestValues.put( "deviceType", AppType.IOS );
    }

    /**
     * Tests the {@link AgentDeviceRequest} with default values. Tests the getters
     * and setters of AgentDeviceRequest.
     */
    @Test
    public final void testAgentDeviceRequest() {
        final BeanLikeTester blt = new BeanLikeTester( AgentDeviceRequest.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, slackRequestTestValues );
    }
}
