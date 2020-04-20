/**
 *
 */
package com.owners.gravitas.dto;

import java.util.HashMap;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class ActionLogDTOTest.
 *
 * @author shivamm
 */
public class ActionLogDTOTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues actionLogDTOTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * ActionLogDTOTest.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createAddressTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "actionType", null );
        defaultValues.put( "actionDtm", null );
        defaultValues.put( "actionEntity", null );
        defaultValues.put( "actionEntityId", null );
        defaultValues.put( "actionBy", null );
        defaultValues.put( "previousValuesMap", null );
        defaultValues.put( "currentValuesMap", null );
        defaultValues.put( "description", null );
        defaultValues.put( "platform", null );
        defaultValues.put( "platformVersion", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createAddressTestValues() {
        actionLogDTOTestValues = new PropertiesAndValues();
        actionLogDTOTestValues.put( "actionType", "test" );
        actionLogDTOTestValues.put( "actionDtm", 1L );
        actionLogDTOTestValues.put( "actionEntity", "test" );
        actionLogDTOTestValues.put( "actionEntityId", "test" );
        actionLogDTOTestValues.put( "actionBy", "test" );
        actionLogDTOTestValues.put( "previousValuesMap", new HashMap<>() );
        actionLogDTOTestValues.put( "currentValuesMap", new HashMap<>() );
        actionLogDTOTestValues.put( "description", "test" );
        actionLogDTOTestValues.put( "platform", "test" );
        actionLogDTOTestValues.put( "platformVersion", "test" );
    }

    /**
     * Tests the {@link ActionLogDTOTest} with default values. Tests the getters
     * and setters of ActionLogDTOTest.
     */
    @Test
    public final void testAddress() {
        final BeanLikeTester blt = new BeanLikeTester( ActionLogDto.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, actionLogDTOTestValues );
    }

}
