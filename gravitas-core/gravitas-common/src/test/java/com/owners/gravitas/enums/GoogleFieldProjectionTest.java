package com.owners.gravitas.enums;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test for GoogleFieldProjectionTest.
 *
 * @author shivamm
 */
public class GoogleFieldProjectionTest {

    /**
     * Tests all the enums.
     */
    @Test
    public void test() {
        for ( GoogleFieldProjection GoogleFieldProjection : GoogleFieldProjection.values() ) {
            Assert.assertNotNull( GoogleFieldProjection );
        }
    }

    /**
     * Tests get name.
     */
    @Test
    public void testGetName() {
        Assert.assertNotNull( GoogleFieldProjection.BASIC.getType() );
    }
}
