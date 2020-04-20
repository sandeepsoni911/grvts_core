package com.owners.gravitas.dto.response;

import java.util.ArrayList;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class SaveSearchResultResponseTest.
 * 
 * @author pabhishek
 */
public class SaveSearchResultResponseTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The save search result response test values. */
    private PropertiesAndValues saveSearchResultResponseTestValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createSavedSearchResultResponseTestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "status", null );
        defaultValues.put( "duplicateSavedSearchID", null );
        defaultValues.put( "message", null );
        defaultValues.put( "saveSearchCount", 0 );
        defaultValues.put( "saveSearchList", null );
    }

    /**
     * Creates the saved search result response test values.
     */
    private void createSavedSearchResultResponseTestValues() {
        saveSearchResultResponseTestValues = new PropertiesAndValues();
        saveSearchResultResponseTestValues.put( "status", "teststatus" );
        saveSearchResultResponseTestValues.put( "duplicateSavedSearchID", "test" );
        saveSearchResultResponseTestValues.put( "message", "testmessage" );
        saveSearchResultResponseTestValues.put( "saveSearchCount", 0 );
        saveSearchResultResponseTestValues.put( "saveSearchList", null );
    }

    /**
     * Test save search result response.
     */
    @Test
    public final void testSaveSearchResultResponse() {
        final BeanLikeTester blt = new BeanLikeTester( SaveSearchResultResponse.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, saveSearchResultResponseTestValues );
    }
}
