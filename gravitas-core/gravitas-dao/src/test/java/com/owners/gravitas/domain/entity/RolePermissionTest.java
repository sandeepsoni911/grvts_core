package com.owners.gravitas.domain.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.ConstructorSignatureAndPropertiesMapping;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class RolePermissionTest.
 * 
 * @author pabhishek
 */
public class RolePermissionTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The role permission values. */
    private PropertiesAndValues rolePermissionValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createRolePermissionTestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "id", null );
        defaultValues.put( "createdBy", null );
        defaultValues.put( "createdDate", null );
        defaultValues.put( "role", null );
        defaultValues.put( "permission", null );
    }

    /**
     * Creates the role permission test values.
     */
    private void createRolePermissionTestValues() {
        final Role role = new Role();
        final Permission permission = new Permission();

        rolePermissionValues = new PropertiesAndValues();
        rolePermissionValues.put( "id", "test" );
        rolePermissionValues.put( "createdBy", null );
        rolePermissionValues.put( "createdDate", new DateTime() );
        rolePermissionValues.put( "role", role );
        rolePermissionValues.put( "permission", permission );
    }

    /**
     * Test role permission.
     */
    @Test
    public final void testRolePermission() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        mapping.put( signature1, Arrays.asList() );
        List< Class< ? > > signature2 = Arrays.< Class< ? > > asList( Role.class, Permission.class, String.class,
                DateTime.class );
        mapping.put( signature2, Arrays.asList( "role", "permission", "createdBy", "createdDate" ) );
        final BeanLikeTester blt = new BeanLikeTester( RolePermission.class, mapping );
        blt.testMutatorsAndAccessors( defaultValues, rolePermissionValues );
    }
}
