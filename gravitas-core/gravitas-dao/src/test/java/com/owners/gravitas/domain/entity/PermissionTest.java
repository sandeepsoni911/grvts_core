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
 * The Class PermissionTest.
 * 
 * @author pabhishek
 */
public class PermissionTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The permission values. */
    private PropertiesAndValues permissionValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createPermissionTestValues();
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
        defaultValues.put( "rolePermission", null );
        defaultValues.put( "name", null );
        defaultValues.put( "description", null );
    }

    /**
     * Creates the permission test values.
     */
    private void createPermissionTestValues() {
        final RolePermission rolePermission1 = new RolePermission();
        final RolePermission rolePermission2 = new RolePermission();
        ArrayList< RolePermission > rolePermissions = new ArrayList< RolePermission >();
        rolePermissions.add( rolePermission1 );
        rolePermissions.add( rolePermission2 );

        permissionValues = new PropertiesAndValues();
        permissionValues.put( "id", "test" );
        permissionValues.put( "createdBy", null );
        permissionValues.put( "createdDate", new DateTime() );
        permissionValues.put( "lastModifiedBy", null );
        permissionValues.put( "lastModifiedDate", new DateTime() );
        permissionValues.put( "rolePermission", rolePermissions );
        permissionValues.put( "name", "test1" );
        permissionValues.put( "description", "abcd" );
    }

    /**
     * Test permission.
     */
    @Test
    public final void testPermission() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        mapping.put( signature1, Arrays.asList() );
        List< Class< ? > > signature2 = Arrays.< Class< ? > > asList( List.class, String.class, String.class );
        mapping.put( signature2, Arrays.asList( "rolePermission", "name", "description" ) );
        final BeanLikeTester blt = new BeanLikeTester( Permission.class, mapping );
        blt.testMutatorsAndAccessors( defaultValues, permissionValues );
    }
}
