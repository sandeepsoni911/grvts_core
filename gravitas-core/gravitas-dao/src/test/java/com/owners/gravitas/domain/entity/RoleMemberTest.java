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
 * The Class RoleMemberTest.
 * 
 * @author pabhishek
 */
public class RoleMemberTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The role member values. */
    private PropertiesAndValues roleMemberValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createRoleMemberTestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "id", null );
        defaultValues.put( "createdBy", null );
        defaultValues.put( "createdDate", null );
        defaultValues.put( "user", null );
        defaultValues.put( "role", null );
    }

    /**
     * Creates the role member test values.
     */
    private void createRoleMemberTestValues() {
        final User user = new User();
        final Role role = new Role();

        roleMemberValues = new PropertiesAndValues();
        roleMemberValues.put( "id", "test" );
        roleMemberValues.put( "createdBy", null );
        roleMemberValues.put( "createdDate", new DateTime() );
        roleMemberValues.put( "user", user );
        roleMemberValues.put( "role", role );
    }

    /**
     * Test role member.
     */
    @Test
    public final void testRoleMember() {
        final ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        final List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        mapping.put( signature1, Arrays.asList() );
        final List< Class< ? > > signature2 = Arrays.< Class< ? > > asList( User.class, Role.class, String.class,
                DateTime.class );
        final List< Class< ? > > signature3 = Arrays.< Class< ? > > asList( User.class, Role.class );
        mapping.put( signature2, Arrays.asList( "user", "role", "createdBy", "createdDate" ) );
        mapping.put( signature3, Arrays.asList( "user", "role" ) );
        final BeanLikeTester blt = new BeanLikeTester( RoleMember.class, mapping );
        blt.testMutatorsAndAccessors( defaultValues, roleMemberValues );
    }

}
