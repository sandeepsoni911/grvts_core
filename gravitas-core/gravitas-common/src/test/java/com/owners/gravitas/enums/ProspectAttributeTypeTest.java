package com.owners.gravitas.enums;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test for ProspectAttributeTypeTest.
 *
 * @author shivamm
 */
public class ProspectAttributeTypeTest {

    /**
     * Tests all the enums.
     */
    @Test
    public void test() {
        for ( ProspectAttributeType rospectAttributeType : ProspectAttributeType.values() ) {
            Assert.assertNotNull( rospectAttributeType );
        }
    }

    /**
     * Tests get key.
     */
    @Test
    public void testGetKey() {
        Assert.assertNotNull( ProspectAttributeType.ADDITIONAL_PROPERTY_DATA.getKey() );
    }

    /**
     * Tests get data type.
     */
    @Test
    public void testGetDataType() {
        Assert.assertNotNull( ProspectAttributeType.ADDITIONAL_PROPERTY_DATA.getDataType() );
    }
}
