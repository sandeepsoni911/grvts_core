package com.owners.gravitas.domain.entity;

import org.joda.time.DateTime;
import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class ContactAttributeTest.
 *
 * @author amits
 */
public class ContactAttributeTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The create config test values. */
    private PropertiesAndValues createConfigTestValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createConfigTestValues();
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
     * Creates the config test values.
     */
    private void createConfigTestValues() {
        createConfigTestValues = new PropertiesAndValues();
        createConfigTestValues.put( "objectAttributeConfig", new ObjectAttributeConfig() );
        createConfigTestValues.put( "value", "test" );
        createConfigTestValues.put( "id", "test" );
        createConfigTestValues.put( "createdBy", "test" );
        createConfigTestValues.put( "createdDate", new DateTime() );
        createConfigTestValues.put( "lastModifiedBy", "test" );
        createConfigTestValues.put( "lastModifiedDate", new DateTime() );
    }

    /**
     * Test lead email parsing log.
     */
    @Test
    public final void testContactAttribute() {
        final BeanLikeTester blt = new BeanLikeTester( ContactAttribute.class );
        blt.testMutatorsAndAccessors( defaultValues, createConfigTestValues );
    }
}
