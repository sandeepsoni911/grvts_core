package com.owners.gravitas.dto;

import org.outsideMyBox.testUtils.BeanLikeTester;
import org.outsideMyBox.testUtils.BeanLikeTester.PropertiesAndValues;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * The Class ImageTest.
 * 
 * @author pabhishek
 */
public class ImageTest {

    /** The default values. */
    private PropertiesAndValues defaultValues = null;

    /** The test values. */
    private PropertiesAndValues testValues = null;

    /**
     * Setup.
     */
    @BeforeClass
    public final void setup() {
        createDefaultValues();
        createImageTestValues();
    }

    /**
     * Creates the default values.
     */
    private void createDefaultValues() {
        defaultValues = new PropertiesAndValues();
        defaultValues.put( "thumbnailImageURL", null );
        defaultValues.put("imageURL", null );
    }

    /**
     * Creates the image test values.
     */
    private void createImageTestValues() {
        testValues = new PropertiesAndValues();
        testValues.put( "thumbnailImageURL", "test" );
        testValues.put( "imageURL", "test" );
    }

    /**
     * Test address.
     */
    @Test
    public final void testAddress() {
        final BeanLikeTester blt = new BeanLikeTester( Image.class );
        blt.testDefaultValues( defaultValues );
        blt.testMutatorsAndAccessors( defaultValues, testValues );
    }
}
