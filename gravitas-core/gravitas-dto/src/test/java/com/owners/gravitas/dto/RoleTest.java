package com.owners.gravitas.dto;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class RoleTest.
 * 
 * @author pabhishek
 */
public class RoleTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The test values. */
    private PropertiesAndValues testValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createRoleTestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "name", null );
        defaultValues.put( "description", null );
    }

    /**
     * Creates the role test values.
     */
    private void createRoleTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "name", "test" );
        testValues.put( "description", "description" );
    }

    /**
     * Test role.
     */
    @Test
    public final void testRole() {
        final BeanLikeTester blt = new BeanLikeTester( Role.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }
}
