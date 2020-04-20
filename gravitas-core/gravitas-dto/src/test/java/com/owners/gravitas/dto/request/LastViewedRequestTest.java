package com.owners.gravitas.dto.request;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class LastViewedRequestTest.
 *
 * @author amits
 */
public class LastViewedRequestTest {

    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues lastViewedRequestTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * LastViewedRequest.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createLastViewedRequestTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "objectType", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createLastViewedRequestTestValues() {
        lastViewedRequestTestValues = new PropertiesAndValues();
        lastViewedRequestTestValues.put( "objectType", "test" );
    }

    /**
     * Tests the {@link LastViewedRequest} with default values. Tests the
     * getters
     * and setters of LastViewedRequest.
     */
    @Test
    public final void testLastViewedRequest() {
        final BeanLikeTester blt = new BeanLikeTester( LastViewedRequest.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, lastViewedRequestTestValues );
    }
}
