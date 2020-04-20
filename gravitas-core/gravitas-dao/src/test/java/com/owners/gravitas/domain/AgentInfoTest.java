package com.owners.gravitas.domain;

import java.util.HashSet;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AgentInfoTest {
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
        defaultValues.put( "phone", null );
        defaultValues.put( "email", null );
        defaultValues.put( "devices", new HashSet<>() );
        defaultValues.put( "onDuty", null );
        defaultValues.put( "lastLoginDtm", null );
        defaultValues.put( "lastModifiedDtm", null );
        defaultValues.put( "offDutyStartDtm", null );
        defaultValues.put( "offDutyEndDtm", null );
        defaultValues.put( "signature", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createAddressTestValues() {
        addressTestValues = new PropertiesAndValues();
        addressTestValues.put( "phone", null );
        addressTestValues.put( "email", "test" );
        addressTestValues.put( "devices", new HashSet<>() );
        addressTestValues.put( "onDuty", true );
        addressTestValues.put( "lastLoginDtm", 0L );
        addressTestValues.put( "lastModifiedDtm", 0L );
        addressTestValues.put( "offDutyStartDtm", 0L );
        addressTestValues.put( "offDutyEndDtm", 0L );
        addressTestValues.put( "signature", "test" );
    }

    /**
     * Tests the {@link AgentInfo} with default values. Tests the getters
     * and setters of AgentInfo.
     */
    @Test
    public final void testAgentInfo() {
        final BeanLikeTester blt = new BeanLikeTester( AgentInfo.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, addressTestValues );
        final AgentInfo info = new AgentInfo();
        Assert.assertTrue( info.addDevice( new Device() ) );
        Assert.assertTrue( info.removeDevice( new Device() ) );

    }
}
