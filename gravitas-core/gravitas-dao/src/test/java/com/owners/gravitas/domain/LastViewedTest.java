package com.owners.gravitas.domain;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class LastViewedTest.
 *
 * @author amits
 */
public class LastViewedTest {
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
     * LastViewed.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createLastViewedTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "lastViewedDtm", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createLastViewedTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "lastViewedDtm", 0L );
    }

    /**
     * Tests the {@link LastViewed} with default values. Tests the getters
     * and setters of LastViewed.
     */
    @Test
    public final void testRequest() {
        final BeanLikeTester blt = new BeanLikeTester( LastViewed.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }
}
