package com.owners.gravitas.amqp;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.dto.Contact;

public class OpportunityContactTest {
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
     * OpportunityContact.
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
        defaultValues.put( "primaryContact", null );
        defaultValues.put( "agentEmail", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createAddressTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "primaryContact", new Contact() );
        testValues.put( "agentEmail", "test" );
    }

    /**
     * Tests the {@link OpportunityContact} with default values. Tests the getters
     * and setters of OpportunityContact.
     */
    @Test
    public final void testAddress() {
        final BeanLikeTester blt = new BeanLikeTester( OpportunityContact.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }

}
