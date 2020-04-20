package com.owners.gravitas.util;

import static com.owners.gravitas.constants.Constants.UTF_8;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.exception.ApplicationException;



/**
 * This is test class for testing {@link EncryptDecryptUtil}.
 *
 * @author vishwanathm
 *
 */
public class EncryptDecryptUtilTest {

    /**
     * Sets the up.
     *
     * @throws Exception
     *             the exception
     */

    @BeforeClass
    public void setUp() throws Exception {
        final Map< String, String > propertiesMap = PropertiesUtil.getPropertiesMap();
        propertiesMap.put( "security.key", "sd>f#$@(*&)*;Alt!%$EHUB" );
    }

    /**
     * Test Case No-1 - Test encrypt object.
     *
     * @Objective testing encryptObject method.
     * @Expected The encryption of string has to be happen.
     * @Actual The encryption of string has to be happened.
     */
    @Test
    public void testEncryptObject() {
        final Map< String, String > values = new HashMap< String, String >();
        values.put( "username", "test123" );
        String[] encryptedData = EncryptDecryptUtil.encrypt( values );
        Assert.assertNotNull( encryptedData );
        Assert.assertEquals( 2, encryptedData.length );
    }

    /**
     * Test Case No-2 - Test decrypt object.
     *
     * @throws Exception
     *             the exception
     * @Objective testing encryptObject method.
     * @Expected The decryption of string has to be happen.
     * @Actual The decryption of string has to be happened.
     */
    @Test
    public void testDecryptObject() throws Exception {
        final Map< String, String > values = new HashMap< String, String >();
        values.put( "username", "test123" );
        String[] encryptedData = EncryptDecryptUtil.encrypt( values );
        Assert.assertNotNull( encryptedData );
        final Map< String, String > encryptedData1 = EncryptDecryptUtil.decrypt( encryptedData[0],
                encryptedData[1] );
        Assert.assertNotNull( encryptedData1 );
        Assert.assertEquals( 1, encryptedData1.size() );
    }

    /**
     * Test Case No-3 - Test for {@link InvalidURLFormatException} while
     * decryption.
     *
     * @Objective testing encryptObject method.
     * @Expected The method has to throw {@link InvalidURLFormatException}.
     * @Actual The method has to thrown {@link InvalidURLFormatException}.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testEncryptObjectForException() {
        EncryptDecryptUtil.decrypt( "test", "test" );
    }

    /**
     * Test Case No-3 - Test for {@link ApplicationException} while decryption.
     *
     * @throws Exception
     *             the exception
     * @Objective testing encryptObject method.
     * @Expected The method has to throw {@link ApplicationException}.
     * @Actual The method has to thrown {@link ApplicationException}.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testdecryptObjectForApplicationException() throws Exception {
        String testValue1 = "a";
        testValue1 = URLDecoder.decode( testValue1, UTF_8 );
        String testValue2 = URLDecoder.decode( "b", UTF_8 );
        EncryptDecryptUtil.decrypt( testValue1, testValue2 );
    }

    /**
     * @Objective: To test the private constructor of EncryptDecryptUtil
     * @Expected: Method should invoke the private constructor
     * @Actual: Private constructor is invoked.
     */
    @Test
    public void testConstructorIsPrivate() throws Exception {
        Constructor< EncryptDecryptUtil > constructor = EncryptDecryptUtil.class
                .getDeclaredConstructor();
        Assert.assertTrue( Modifier.isPrivate( constructor.getModifiers() ) );
        constructor.setAccessible( true );
        constructor.newInstance();

    }

    @Test
    public void testGenerateEmailLink() throws Exception {
        final Map< String, String > values = new HashMap< String, String >();
        //values.put( "username", "test123" );
        String url = EncryptDecryptUtil.generateEmailLink( "contextUrl", values, "/test" );
        Assert.assertNotNull( url );
    }

}
