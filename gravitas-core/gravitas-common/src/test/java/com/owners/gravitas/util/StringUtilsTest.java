/**
 *
 */
package com.owners.gravitas.util;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * The Class StringUtilsTest.
 *
 * @author harshads
 */
public class StringUtilsTest {

    /** The string utils. */
    @InjectMocks
    private StringUtils stringUtils;

    /**
     * Convert object to string null object.
     */
    @Test
    public void convertObjectToStringNullObject() {
        Assert.assertEquals( StringUtils.convertObjectToString( null ), null,
                "convertObjectToStringNullObject failed, should return null" );
    }

    /**
     * Convert object to string not null object.
     */
    @Test
    public void convertObjectToStringNotNullObject() {
        Assert.assertEquals( StringUtils.convertObjectToString( "test" ), "test",
                "convertObjectToStringNullObject failed, should return correct string value" );
    }

    /**
     * Test remove double quotes.
     *
     * @param value
     *            the value
     */
    @Test( dataProvider = "getValues" )
    public void testRemoveDoubleQuotes( final String value ) {
        Assert.assertNotNull( StringUtils.removeDoubleQuotes( value ) );
    }

    /**
     * Test is valid id.
     */
    @Test
    public void testIsValidId() {
        Assert.assertNotNull( StringUtils.isValidId( "test" ) );
    }

    /**
     * Test is valid id blank.
     */
    @Test
    public void testIsValidIdBlank() {
        Assert.assertNotNull( StringUtils.isValidId( "" ) );
    }

    /**
     * Test is valid id max length.
     */
    @Test
    public void testIsValidIdMaxLength() {
        Assert.assertNotNull(
                StringUtils.isValidId( "testtesttestsetetsetsetestsetestsetsetsetsetsetsetsetsetsetsetset" ) );
    }

    /**
     * Test is email id valid.
     */
    @Test
    public void testIsEmailIdValid() {
        Assert.assertNotNull( StringUtils.isEmailIdValid( "test@test.com" ) );
    }

    /**
     * Test is email id valid blank.
     */
    @Test
    public void testIsEmailIdValidBlank() {
        Assert.assertNotNull( StringUtils.isEmailIdValid( "" ) );
    }

    @Test
    public void testsubStringForLength() {
        Assert.assertEquals(
                StringUtils.subStringForLength( "(this is text) text under brackets will be seperated", 14 ),
                "(this is text)" );
    }

    /**
     * Gets the values.
     *
     * @return the values
     */
    @DataProvider( name = "getValues" )
    public Object[][] getValues() {
        return new Object[][] { { "" }, { " " }, { null }, { "email@email.com" }, { "email@email.com\"" },
                { "\"email@email.com" }, { "\"email@email.com\"" } };
    }
}
