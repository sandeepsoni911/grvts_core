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
 * The Class UserTest.
 * 
 * @author pabhishek
 */
public class UserTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues userValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * AgentInfo.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createUserTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "id", null );
        defaultValues.put( "createdBy", null );
        defaultValues.put( "createdDate", null );
        defaultValues.put( "lastModifiedBy", null );
        defaultValues.put( "lastModifiedDate", null );
        defaultValues.put( "roleMember", null );
        defaultValues.put( "email", null );
        defaultValues.put( "status", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createUserTestValues() {
        final RoleMember roleMember1 = new RoleMember();
        final RoleMember roleMember2 = new RoleMember();
        ArrayList< RoleMember > roleMembers = new ArrayList< RoleMember >();
        roleMembers.add( roleMember1 );
        roleMembers.add( roleMember2 );
        userValues = new PropertiesAndValues();
        userValues.put( "id", "test" );
        userValues.put( "roleMember", roleMembers );
        userValues.put( "createdBy", null );
        userValues.put( "createdDate", new DateTime() );
        userValues.put( "lastModifiedBy", null );
        userValues.put( "lastModifiedDate", new DateTime() );
        userValues.put( "email", "test@gmail.com" );
        userValues.put( "status", "active" );
    }

    /**
     * Test user.
     */
    @Test
    public final void testUser() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        mapping.put( signature1, Arrays.asList() );
        List< Class< ? > > signature2 = Arrays.< Class< ? > > asList( List.class, String.class, String.class );
        mapping.put( signature2, Arrays.asList( "roleMember", "email", "status" ) );
        final BeanLikeTester blt = new BeanLikeTester( User.class, mapping );
        blt.testMutatorsAndAccessors( defaultValues, userValues );
    }
}
