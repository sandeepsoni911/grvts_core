package com.owners.gravitas.dto;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class AffiliateEmailAttributeTest.
 *
 * @author amits
 */
public class AffiliateEmailAttributeTest {
    /**
     * for default value.
     */
    private PropertiesAndValues defaultValues = null;
    /**
     * for testing values.
     */
    private PropertiesAndValues attributesTestValues = null;

    /**
     * This method is calls the methods to create default and test values for
     * AffiliateEmailAttributes.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createAffiliateEmailAttributesTestValues();
    }

    /**
     * This method is to create the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "errorLog", null );
        defaultValues.put( "errorSource", null );
        defaultValues.put( "messageTypeName", null );
        defaultValues.put( "notificationFrom", null );
        defaultValues.put( "notificationTo", null );
    }

    /**
     * This method is to create the actual values.
     */
    private void createAffiliateEmailAttributesTestValues() {
        attributesTestValues = new PropertiesAndValues();
        attributesTestValues.put( "errorLog", "test" );
        attributesTestValues.put( "errorSource", "test" );
        attributesTestValues.put( "messageTypeName", "test" );
        attributesTestValues.put( "notificationFrom", "test" );
        attributesTestValues.put( "notificationTo", "test" );
    }

    /**
     * Tests the {@link AffiliateEmailAttribute} with default values. Tests the
     * getters
     * and setters of AffiliateEmailAttributes.
     */
    @Test
    public final void testAffiliateEmailAttributes() {
        final BeanLikeTester blt = new BeanLikeTester( AffiliateEmailAttribute.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, attributesTestValues );
    }
}
