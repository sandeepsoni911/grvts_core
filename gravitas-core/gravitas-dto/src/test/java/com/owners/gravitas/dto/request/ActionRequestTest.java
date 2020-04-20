package com.owners.gravitas.dto.request;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class ActionRequestTest.
 *
 * @author vishwanathm
 */
public class ActionRequestTest {
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues actionRequestTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * AgentDeviceRequest.
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
        defaultValues.put( "actionType", null );
        defaultValues.put( "actionEntity", null );
        defaultValues.put( "actionEntityId", null );
        defaultValues.put( "previousValue", null );
        defaultValues.put( "currentValue", null );
        defaultValues.put( "platform", null );
        defaultValues.put( "platformVersion", null );
        defaultValues.put( "description", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createActionRequestTestValues() {
        actionRequestTestValues = new PropertiesAndValues();
        actionRequestTestValues.put( "actionType", "actionType" );
        actionRequestTestValues.put( "actionEntity", "actionEntity" );
        actionRequestTestValues.put( "actionEntityId", "actionEntityId" );
        actionRequestTestValues.put( "previousValue", "previousValue" );
        actionRequestTestValues.put( "currentValue", "currentValue" );
        actionRequestTestValues.put( "platform", "platform" );
        actionRequestTestValues.put( "platformVersion", "platformVersion" );
        actionRequestTestValues.put( "description", "description" );
    }

    /**
     * Tests the {@link ActionRequest} with default values. Tests the
     * getters
     * and setters of ActionRequest.
     */
    @Test
    public final void testActionRequest() {
        final BeanLikeTester blt = new BeanLikeTester( ActionRequest.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, actionRequestTestValues );
    }
}
