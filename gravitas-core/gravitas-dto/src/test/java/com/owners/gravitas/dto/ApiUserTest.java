package com.owners.gravitas.dto;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.ConstructorSignatureAndPropertiesMapping;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ApiUserTest {
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
        createApiUserTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "uid", null );
        defaultValues.put( "email", null );
        defaultValues.put( "roles", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createApiUserTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "uid", "test" );
        testValues.put( "email", "test" );
        testValues.put( "roles", new HashSet<String>() );
    }

    /**
     * Tests the {@link ApiUser} with default values. Tests the getters and
     * setters of ApiUser.
     */
    @Test
    public final void testApiUser() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        List< Class< ? > > signature2 = Arrays.< Class< ? > > asList( String.class, String.class );
        mapping.put( signature1, Arrays.asList() );
        mapping.put( signature2, Arrays.asList( "uid", "email" ) );

        final BeanLikeTester blt = new BeanLikeTester( ApiUser.class, mapping );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }
}
