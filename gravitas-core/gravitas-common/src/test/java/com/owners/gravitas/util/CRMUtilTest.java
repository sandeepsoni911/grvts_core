package com.owners.gravitas.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The Class CRMUtilTest.
 *
 * @author vishwanathm
 */
public class CRMUtilTest {

    /**
     * Test build request.
     */
    @Test
    public void testBuildRequest() {
        Assert.assertNotNull( RestUtil.buildRequest( RestUtil.createHttpHeader( "test" ), null ) );
    }

    /**
     * Test create http header.
     */
    @Test
    public void testCreateHttpHeader() {
        Assert.assertNotNull( RestUtil.createHttpHeader( "test" ) );
    }

    /**
     * Test create http header with auto assign.
     */
    @Test
    public void testCreateHttpHeader_WithAutoAssignTrue() {
        Assert.assertNotNull( RestUtil.createHttpHeader( "test", true ) );
    }

    /**
     * Test basic http header.
     */
    @Test
    public void testBasicHttpHeader() {
        Assert.assertNotNull( RestUtil.createBasicHttpHeader( "test" ) );
    }

    /**
    * Test private constructor.
    *
    * @throws Exception
    *             the exception
    */
   @Test
   public void testPrivateConstructor() throws Exception {
       Constructor< RestUtil > constructor = RestUtil.class.getDeclaredConstructor();
       Assert.assertTrue( Modifier.isPrivate( constructor.getModifiers() ) );
       constructor.setAccessible( true );
       constructor.newInstance();

   }
}
