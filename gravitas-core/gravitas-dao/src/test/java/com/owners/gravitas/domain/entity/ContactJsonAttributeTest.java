package com.owners.gravitas.domain.entity;

import org.joda.time.DateTime;
import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class ContactJsonAttributeTest.
 *
 * @author shivamm
 */
public class ContactJsonAttributeTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The create test values. */
    private PropertiesAndValues createTestValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createTestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "objectAttributeConfig", null );
        defaultValues.put( "value", null );
        defaultValues.put( "id", null );
        defaultValues.put( "createdBy", null );
        defaultValues.put( "createdDate", null );
        defaultValues.put( "lastModifiedBy", null );
        defaultValues.put( "lastModifiedDate", null );
    }

    /**
     * Creates the test values.
     */
    private void createTestValues() {
        createTestValues = new PropertiesAndValues();
        createTestValues.put( "objectAttributeConfig", new ObjectAttributeConfig() );
        createTestValues.put( "value", "test" );
        createTestValues.put( "id", "test" );
        createTestValues.put( "createdBy", "test" );
        createTestValues.put( "createdDate", new DateTime() );
        createTestValues.put( "lastModifiedBy", "test" );
        createTestValues.put( "lastModifiedDate", new DateTime() );
    }

    /**
     * Test lead email parsing log.
     */
    @Test
    public final void testLeadEmailParsingLog() {
        final BeanLikeTester blt = new BeanLikeTester( ContactJsonAttribute.class );
        blt.testMutatorsAndAccessors( defaultValues, createTestValues );
    }
}
