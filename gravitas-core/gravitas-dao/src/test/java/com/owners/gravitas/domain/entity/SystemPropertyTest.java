package com.owners.gravitas.domain.entity;

import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.ConstructorSignatureAndPropertiesMapping;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class SystemPropertyTest.
 * 
 * @author pabhishek
 */
public class SystemPropertyTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The system property values. */
    private PropertiesAndValues systemPropertyValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createSystemPropertyValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "id", null );
        defaultValues.put( "createdBy", null );
        defaultValues.put( "createdDate", null );
        defaultValues.put( "lastModifiedBy", null );
        defaultValues.put( "lastModifiedDate", null );
        defaultValues.put( "name", null );
        defaultValues.put( "value", null );
    }

    /**
     * Creates the system property values.
     */
    private void createSystemPropertyValues() {
        systemPropertyValues = new PropertiesAndValues();
        systemPropertyValues.put( "id", "test" );
        systemPropertyValues.put( "createdBy", "test" );
        systemPropertyValues.put( "createdDate", new DateTime() );
        systemPropertyValues.put( "lastModifiedBy", "test" );
        systemPropertyValues.put( "lastModifiedDate", new DateTime() );
        systemPropertyValues.put( "name", "testuser" );
        systemPropertyValues.put( "value", "test1" );
    }

    /**
     * Test system property.
     */
    @Test
    public final void testSystemProperty() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        mapping.put( signature1, Arrays.asList() );
        final BeanLikeTester blt = new BeanLikeTester( SystemProperty.class, mapping );
        blt.testMutatorsAndAccessors( defaultValues, systemPropertyValues );
    }

}
