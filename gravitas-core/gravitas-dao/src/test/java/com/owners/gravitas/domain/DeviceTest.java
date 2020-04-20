package com.owners.gravitas.domain;

import java.util.HashSet;
import java.util.Set;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.domain.Device;

/**
 * The Class DeviceTest.
 *
 * @author amits
 */
public class DeviceTest {
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
     * Device.
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
        defaultValues.put( "type", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createAddressTestValues() {
        addressTestValues = new PropertiesAndValues();
        addressTestValues.put( "id", "test" );
        addressTestValues.put( "type", "test" );
    }

    /**
     * Tests the {@link Device} with default values. Tests the getters
     * and setters of Device.
     */
    @Test
    public final void testDevice() {
        final BeanLikeTester blt = new BeanLikeTester( Device.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, addressTestValues );
        Set set = new HashSet< Device >();
        Device device1 = new Device();
        device1.setId( "12374" );
        device1.setType( "Test" );
        Device device2 = new Device();
        device2.setId( "123742" );
        device2.setType( "Test2" );
        Device device3 = new Device();
        device3.setId( "12374" );
        device3.setType( "Test3" );
        Device device4 = new Device();
        device4.setId( null );
        device4.setType( "Test3" );

        Assert.assertTrue( set.add( device1 ) );
        Assert.assertTrue( set.add( device2 ) );
        Assert.assertFalse( set.add( device3 ) );
        Assert.assertTrue( set.add( device4 ) );
        Assert.assertFalse( set.add( device1 ) );
    }

    /**
     * Test equals method.
     */
    @Test
    public final void testEqualsFirst() {
        Device device = new Device();
        device.equals( device );
    }

    @Test
    public final void testEqualsSecond() {
        Device device = new Device();
        device.equals( null );
    }

    @Test
    public final void testEqualsThird() {
        Device device = new Device();
        Device device2 = new Device();
        device2.setId( "test" );
        device.setId( null );
        device.equals( device2 );
    }

}
