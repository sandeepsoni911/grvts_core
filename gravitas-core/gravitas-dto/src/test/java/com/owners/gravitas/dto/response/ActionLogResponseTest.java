package com.owners.gravitas.dto.response;

import java.util.ArrayList;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.dto.ActionLogDto;

/**
 * The Class ActionLogResponseTest.
 *
 * @author shivamm
 */
public class ActionLogResponseTest {
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues testValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * ActionLogResponseTest.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createActionLogResponseTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "actionLogs", null );
        defaultValues.put( "message", "Operation was successful" );
        defaultValues.put( "status", BaseResponse.Status.SUCCESS );
    }

    /**
     * This method is to create the actual values.
     */
    private void createActionLogResponseTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "actionLogs", new ArrayList< ActionLogDto >() );
        testValues.put( "message", "test" );
        testValues.put( "status", BaseResponse.Status.FAILURE );
    }

    /**
     * Tests the {@link ActionLogResponse} with default values. Tests the
     * getters
     * and setters of ActionLogResponse.
     */
    @Test
    public final void testAgentResponse() {

        final BeanLikeTester blt = new BeanLikeTester( ActionLogResponse.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }
}
