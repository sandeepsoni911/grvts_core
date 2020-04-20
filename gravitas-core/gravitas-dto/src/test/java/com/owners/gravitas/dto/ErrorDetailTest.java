package com.owners.gravitas.dto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.ConstructorSignatureAndPropertiesMapping;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * This is test class for ErrorDetail.
 *
 * @author vishwanathm
 *
 */
public class ErrorDetailTest {
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues errorDetailsTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * ErrorDetail.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createErrorDetailTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "code", null );
        defaultValues.put( "detail", null );

    }

    /**
     * This method is to create the default values.
     */
    private void createErrorDetailTestValues() {
        errorDetailsTestValues = new PropertiesAndValues();
        errorDetailsTestValues.put( "code", "system.error" );
        errorDetailsTestValues.put( "detail", "System error occured" );
    }

    /**
     * Tests the RoleDetail with default values. Tests the getters and setters
     * of RoleDetail.
     */
    @Test
    public final void errorDetail() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        List< Class< ? > > signature1 = Arrays.< Class< ? > > asList( String.class, String.class );
        mapping.put( signature1, Arrays.asList( "code", "detail" ) );
        mapping.put( Collections.< Class< ? > > emptyList(), Collections.< String > emptyList() );
        final BeanLikeTester blt = new BeanLikeTester( ErrorDetail.class, mapping );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, errorDetailsTestValues );
    }
}
