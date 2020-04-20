package com.owners.gravitas.dto.response;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class SaveSearchResponseTest.
 * 
 * @author pabhishek
 */
public class SaveSearchResponseTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The save search response test values. */
    private PropertiesAndValues saveSearchResponseTestValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createSaveSearchResponseTestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "result", null );
    }

    /**
     * Creates the save search response test values.
     */
    private void createSaveSearchResponseTestValues() {
        saveSearchResponseTestValues = new PropertiesAndValues();
        saveSearchResponseTestValues.put( "result", new SaveSearchResultResponse() );
    }

    /**
     * Test save search response.
     */
    @Test
    public final void testSaveSearchResponse() {
        final BeanLikeTester blt = new BeanLikeTester( SaveSearchResponse.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, saveSearchResponseTestValues );
    }
}
