/**
 *
 */
package com.owners.gravitas.dto;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class RabbitMQAccessTest.
 *
 * @author shivamm
 */
public class RabbitMQAccessTest {

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
     * CrmNoteTest.
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
        defaultValues.put( "accessToken", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createAddressTestValues() {
        addressTestValues = new PropertiesAndValues();
        addressTestValues.put( "accessToken", "test" );
    }

    /**
     * Tests the {@link rabbit mq status} with default values. Tests the getters
     * and setters of RabbitMQStatus.
     */
    @Test
    public final void testRabbitMQStatus() {
        final BeanLikeTester blt = new BeanLikeTester( RabbitMQAccess.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, addressTestValues );
    }

}
