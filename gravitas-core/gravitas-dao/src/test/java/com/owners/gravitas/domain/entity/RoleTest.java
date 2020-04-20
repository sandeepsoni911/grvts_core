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
 * The Class RoleTest.
 * 
 * @author pabhishek
 */
public class RoleTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The role values. */
    private PropertiesAndValues roleValues = null;

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
        defaultValues.put( "id", null );
        defaultValues.put( "createdBy", null );
        defaultValues.put( "createdDate", null );
        defaultValues.put( "lastModifiedBy", null );
        defaultValues.put( "lastModifiedDate", null );
        defaultValues.put( "roleMember", null );
        defaultValues.put( "rolePermissions", null );
        defaultValues.put( "name", null );
        defaultValues.put( "description", null );
    }

    /**
     * Creates the role test values.
     */
    private void createRoleTestValues() {
        final RoleMember roleMember1 = new RoleMember();
        final RoleMember roleMember2 = new RoleMember();
        ArrayList< RoleMember > roleMembers = new ArrayList< RoleMember >();
        roleMembers.add( roleMember1 );
        roleMembers.add( roleMember2 );

        final RolePermission rolePermission1 = new RolePermission();
        final RolePermission rolePermission2 = new RolePermission();
        ArrayList< RolePermission > rolePermissions = new ArrayList< RolePermission >();
        rolePermissions.add( rolePermission1 );
        rolePermissions.add( rolePermission2 );

        roleValues = new PropertiesAndValues();
        roleValues.put( "id", "test" );
        roleValues.put( "createdBy", null );
        roleValues.put( "createdDate", new DateTime() );
        roleValues.put( "lastModifiedBy", null );
        roleValues.put( "lastModifiedDate", new DateTime() );
        roleValues.put( "roleMember", roleMembers );
        roleValues.put( "rolePermissions", rolePermissions );
        roleValues.put( "name", "test1" );
        roleValues.put( "description", "abcd" );
    }

    /**
     * Test role.
     */
    @Test
    public final void testRole() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        mapping.put( signature1, Arrays.asList() );
        List< Class< ? > > signature2 = Arrays.< Class< ? > > asList( List.class, List.class, String.class,
                String.class );
        mapping.put( signature2, Arrays.asList( "roleMember", "rolePermissions", "name", "description" ) );
        final BeanLikeTester blt = new BeanLikeTester( Role.class, mapping );
        blt.testMutatorsAndAccessors( defaultValues, roleValues );
    }

}
