package com.owners.gravitas.domain.entity;

import java.util.Arrays;
import java.util.List;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.ConstructorSignatureAndPropertiesMapping;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class ZipDistanceTest.
 * 
 * @author pabhishek
 */
public class ZipDistanceTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The zip distance values. */
    private PropertiesAndValues zipDistanceValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createZipDistanceValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "id", null );
        defaultValues.put( "sourceZip", null );
        defaultValues.put( "destinationZip", null );
        defaultValues.put( "miles", 10.1 );
        defaultValues.put( "excluded", true );
    }

    /**
     * Creates the zip distance values.
     */
    private void createZipDistanceValues() {
        zipDistanceValues = new PropertiesAndValues();
        zipDistanceValues.put( "id", "test" );
        zipDistanceValues.put( "sourceZip", "12345" );
        zipDistanceValues.put( "destinationZip", "67890" );
        zipDistanceValues.put( "miles", 10.1 );
        zipDistanceValues.put( "excluded", false );
    }

    /**
     * Test zip distance.
     */
    @Test
    public final void testZipDistance() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        mapping.put( signature1, Arrays.asList() );
        final BeanLikeTester blt = new BeanLikeTester( ZipDistance.class, mapping );
        blt.testMutatorsAndAccessors( defaultValues, zipDistanceValues );
    }

}
