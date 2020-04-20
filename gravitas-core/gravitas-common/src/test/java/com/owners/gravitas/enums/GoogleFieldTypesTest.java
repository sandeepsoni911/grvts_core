package com.owners.gravitas.enums;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The Class GoogleFieldTypesTest.
 * 
 * @author pabhishek
 */
public class GoogleFieldTypesTest {

    /**
     * Test.
     */
    @Test
    public final void test() {
        for ( GoogleFieldTypes googleFieldTypes : GoogleFieldTypes.values() ) {
            Assert.assertNotNull( googleFieldTypes );
            Assert.assertNotNull( googleFieldTypes.getType() );
        }
    }

    /**
     * Test get google field types.
     */
    @Test
    public final void testGetGoogleFieldTypes() {
        for ( GoogleFieldTypes googleFieldTypes : GoogleFieldTypes.values() ) {
            Assert.assertNotNull( GoogleFieldTypes.getGoogleFieldTypes( googleFieldTypes.getType() ) );
        }
    }
}
