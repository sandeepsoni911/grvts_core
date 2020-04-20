/**
 *
 */
package com.owners.gravitas.dto;

import java.util.ArrayList;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.enums.ErrorCode;

/**
 * The Class SlackErrorTest.
 *
 * @author shivamm
 */
public class SlackErrorTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues slackErrorTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * SlackErrorTest.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createAddressTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "exception", null );
        defaultValues.put( "errorCode", null );
        defaultValues.put( "errorId", null );
        defaultValues.put( "errorMessage", null );
        defaultValues.put( "requestUrl", null );
        defaultValues.put( "user", null );
        defaultValues.put( "requestParams", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createAddressTestValues() {
        slackErrorTestValues = new PropertiesAndValues();
        slackErrorTestValues.put( "exception", new Exception() );
        slackErrorTestValues.put( "errorCode", ErrorCode.AFFILIATE_EMAIL_PARSING_ERROR );
        slackErrorTestValues.put( "errorId", "test" );
        slackErrorTestValues.put( "errorMessage", "test" );
        slackErrorTestValues.put( "requestUrl", "test" );
        slackErrorTestValues.put( "user", "test" );
        slackErrorTestValues.put( "requestParams", new ArrayList<>() );
    }

    /**
     * Tests the {@link SlackErrorTest} with default values. Tests the getters
     * and setters of SlackErrorTest.
     */
    @Test
    public final void testAddress() {
        final BeanLikeTester blt = new BeanLikeTester( SlackError.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, slackErrorTestValues );
    }

}
