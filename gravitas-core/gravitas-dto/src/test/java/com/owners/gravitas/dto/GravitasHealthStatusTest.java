package com.owners.gravitas.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.ConstructorSignatureAndPropertiesMapping;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class GravitasHealthStatusTest.
 */
public class GravitasHealthStatusTest {
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
     * FirebaseAccess.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createContactTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "systemName", null );
        defaultValues.put( "message", null );
        defaultValues.put( "working", false );
        defaultValues.put( "failureInfo", null );
        defaultValues.put( "subSystems", new ArrayList() );
    }

    /**
     * This method is to create the actual values.
     */
    private void createContactTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "systemName", "test" );
        testValues.put( "message", "test" );
        testValues.put( "working", true );
        testValues.put( "failureInfo", "test" );
        testValues.put( "subSystems", null );
    }

    /**
     * Tests the {@link SystemHealth} with default values. Tests the getters and
     * setters of GravitasHealthStatus.
     */
    @Test
    public final void testSystemHealth() {

        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        List< Class< ? > > signature2 = Arrays.< Class< ? > > asList( String.class, String.class, boolean.class,
                String.class );
        mapping.put( signature2, Arrays.asList( "systemName", "message", "working", "failureInfo" ) );

        final BeanLikeTester blt = new BeanLikeTester( GravitasHealthStatus.class, mapping );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );

    }
}
