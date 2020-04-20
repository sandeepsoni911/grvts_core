package com.owners.gravitas.dto.crm.request;

import org.joda.time.DateTime;
import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class UserLoginLogRequestTest.
 * 
 * @author pabhishek
 */
public class UserLoginLogRequestTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The user login log request values. */
    private PropertiesAndValues userLoginLogRequestValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createUserLoginLogRequestTestValues();
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
     * Creates the user login log request test values.
     */
    private void createUserLoginLogRequestTestValues() {
        userLoginLogRequestValues = new PropertiesAndValues();
        userLoginLogRequestValues.put( "id", "1" );
        userLoginLogRequestValues.put( "ipAddress", "1.0.0.1" );
        userLoginLogRequestValues.put( "operatingSystem", "windows" );
        userLoginLogRequestValues.put( "browser", "chrome" );
        userLoginLogRequestValues.put( "createdDate", new DateTime() );
    }

    /**
     * Test user login log request.
     */
    @Test
    public final void testUserLoginLogRequest() {
        final BeanLikeTester blt = new BeanLikeTester( UserLoginLogRequest.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, userLoginLogRequestValues );
    }
}
