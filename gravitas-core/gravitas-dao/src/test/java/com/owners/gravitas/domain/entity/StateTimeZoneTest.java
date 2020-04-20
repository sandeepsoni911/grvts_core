package com.owners.gravitas.domain.entity;

import org.joda.time.DateTime;
import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.domain.AgentInfo;
import com.owners.gravitas.domain.entity.StateTimeZone;

public class StateTimeZoneTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues stateTimeZoneValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * AgentInfo.
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
        defaultValues.put( "id", null );
        defaultValues.put( "new", true );
        defaultValues.put( "stateCode", null );
        defaultValues.put( "timeZone", null );
        defaultValues.put( "offSetHour", null );
        defaultValues.put( "createdDate", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createAddressTestValues() {
        stateTimeZoneValues = new PropertiesAndValues();
        stateTimeZoneValues.put( "id", "test" );
        stateTimeZoneValues.put( "stateCode", "test" );
        stateTimeZoneValues.put( "new", true );
        stateTimeZoneValues.put( "timeZone", "test" );
        stateTimeZoneValues.put( "offSetHour", 0 );
        stateTimeZoneValues.put( "createdDate", new DateTime() );
    }

    /**
     * Tests the {@link AgentInfo} with default values. Tests the getters
     * and setters of AgentInfo.
     */
    @Test
    public final void testAgentInfo() {
        final BeanLikeTester blt = new BeanLikeTester( StateTimeZone.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, stateTimeZoneValues );
    }
}
