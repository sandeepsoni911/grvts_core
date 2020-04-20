package com.owners.gravitas.enums;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test for GravitasSystemTypeTest.
 *
 * @author shivamm
 */
public class GravitasSystemTypeTest {

    /**
     * Tests all the enums.
     */
    @Test
    public void test() {
        for ( GravitasSystemType gravitasSystemType : GravitasSystemType.values() ) {
            Assert.assertNotNull( gravitasSystemType );
        }
    }

    /**
     * Tests get type.
     */
    @Test
    public void testGetType() {
        Assert.assertNotNull( GravitasSystemType.FIREBASE.getType() );
    }

    /**
     * Tests get system type.
     */
    @Test
    public void testGetSystemType() {
        Assert.assertNotNull( GravitasSystemType.FIREBASE.getSystemType( "Rabbit MQ Service" ) );
    }
}
