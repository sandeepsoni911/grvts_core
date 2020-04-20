
package com.owners.gravitas.managers;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;

/**
 * Test class ThreadLocalManagerTest
 *
 * @author raviz
 *
 */
public class ThreadLocalManagerTest extends AbstractBaseMockitoTest {

    /** The thread local manager. */
    @InjectMocks
    private ThreadLocalManager threadLocalManager;

    /**
     * Test get request params.
     */
    @Test
    public void testGetRequestParams() {
        final List< Object > params = new ArrayList<>();
        params.add( "param1" );
        @SuppressWarnings( "unchecked" )
        final ThreadLocal< List< Object > > requestParams = ( ThreadLocal< List< Object > > ) ReflectionTestUtils
                .getField( threadLocalManager, "requestParams" );
        requestParams.set( params );

        final List< Object > responseParams = threadLocalManager.getRequestParams();

        assertNotNull( responseParams );
        assertEquals( responseParams, params );
    }

    /**
     * Test set requestparams.
     */
    @Test
    public void testSetRequestparams() {
        final List< Object > params = new ArrayList<>();
        params.add( "param1" );

        threadLocalManager.setRequestParams( params );
        @SuppressWarnings( "unchecked" )
        final List< Object > responseParams = ( ( ThreadLocal< List< Object > > ) ReflectionTestUtils
                .getField( threadLocalManager, "requestParams" ) ).get();

        assertNotNull( responseParams );
        assertEquals( responseParams, params );
    }

    /**
     * Test remove request params.
     */
    @Test
    public void testRemoveRequestParams() {
        final List< Object > params = new ArrayList<>();
        params.add( "param1" );
        @SuppressWarnings( "unchecked" )
        final ThreadLocal< List< Object > > requestParams = ( ThreadLocal< List< Object > > ) ReflectionTestUtils
                .getField( threadLocalManager, "requestParams" );
        requestParams.set( params );

        threadLocalManager.removeRequestParams();

        @SuppressWarnings( "unchecked" )
        final List< Object > responseParams = ( ( ThreadLocal< List< Object > > ) ReflectionTestUtils
                .getField( threadLocalManager, "requestParams" ) ).get();

        assertNull( responseParams );
    }

    /**
     * Test get instance.
     */
    @Test
    public void testGetInstance() {
        ThreadLocalManager threadLocalManagerEx = ( ThreadLocalManager ) ReflectionTestUtils
                .getField( threadLocalManager, "INSTANCE" );
        assertEquals( ThreadLocalManager.getInstance(), threadLocalManagerEx );
    }

}
