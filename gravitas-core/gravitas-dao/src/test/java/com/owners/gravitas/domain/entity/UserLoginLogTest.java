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
 * The Class UserLoginLogTest.
 * 
 * @author pabhishek
 */
public class UserLoginLogTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The user login log values. */
    private PropertiesAndValues userLoginLogValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createUserLoginLogTestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "id", null );
        defaultValues.put( "ipAddress", null );
        defaultValues.put( "operatingSystem", null );
        defaultValues.put( "browser", null );
        defaultValues.put( "createdDate", null );
    }

    /**
     * Creates the user login log test values.
     */
    private void createUserLoginLogTestValues() {
        userLoginLogValues = new PropertiesAndValues();
        userLoginLogValues.put( "id", "1" );
        userLoginLogValues.put( "ipAddress", "1.0.0.1" );
        userLoginLogValues.put( "operatingSystem", "windows" );
        userLoginLogValues.put( "browser", "chrome" );
        userLoginLogValues.put( "createdDate", new DateTime() );
    }

    /**
     * Test user login log.
     */
    @Test
    public final void testUserLoginLog() {
        ConstructorSignatureAndPropertiesMapping mapping = new ConstructorSignatureAndPropertiesMapping();
        List< Class< ? > > signature1 = Arrays.< Class< ? > > asList();
        mapping.put( signature1, Arrays.asList() );
        List< Class< ? > > signature2 = Arrays.< Class< ? > > asList( String.class );
        mapping.put( signature2, Arrays.asList( "ipAddress" ) );
        final BeanLikeTester blt = new BeanLikeTester( UserLoginLog.class, mapping );
        blt.testMutatorsAndAccessors( defaultValues, userLoginLogValues );
    }

}
