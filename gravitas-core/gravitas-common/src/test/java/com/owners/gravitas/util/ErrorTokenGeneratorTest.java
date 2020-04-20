package com.owners.gravitas.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The Class ErrorTokenGeneratorTest.
 *
 * @author vishwanathm
 */
public class ErrorTokenGeneratorTest {

    /**
     * Test get error id.
     */
    @Test
    public void testGetErrorId() {
        final String id = ErrorTokenGenerator.getErrorId();
        Assert.assertTrue( id.startsWith( "GRAVITAS-" ) );
    }

    /**
     * Test private constructor.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testPrivateConstructor() throws Exception {
        Constructor< ErrorTokenGenerator > constructor = ErrorTokenGenerator.class.getDeclaredConstructor();
        Assert.assertTrue( Modifier.isPrivate( constructor.getModifiers() ) );
        constructor.setAccessible( true );
        constructor.newInstance();

    }
}
