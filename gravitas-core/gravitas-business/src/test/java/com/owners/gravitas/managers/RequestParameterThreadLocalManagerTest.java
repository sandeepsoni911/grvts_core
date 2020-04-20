package com.owners.gravitas.managers;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;

/**
 * Test class RequestParameterThreadLocalManagerTest.
 *
 * @author raviz
 */
public class RequestParameterThreadLocalManagerTest extends AbstractBaseMockitoTest {

    /** The request parameter thread local manager. */
    @InjectMocks
    private RequestParameterThreadLocalManager threadLocalManager;

    /**
     * Test get request obj.
     */
    @Test
    public void testGetRequestObj() {
        final Map< String, Object > params = new HashMap<>();
        params.put( "key", "paramValue" );
        @SuppressWarnings( "unchecked" )
        final ThreadLocal< Map< String, Object > > requestParams = ( ThreadLocal< Map< String, Object > > ) ReflectionTestUtils
                .getField( threadLocalManager, "requestParamsThreadLoacal" );
        requestParams.set( params );

        final Map< String, Object > responseParams = threadLocalManager.getRequestObj();

        assertNotNull( responseParams );
        assertEquals( responseParams, params );
    }

    /**
     * Test set request obj.
     */
    @Test
    public void testSetRequestObj() {
        final String key = "key";
        final String value = "value";
        threadLocalManager.setRequestObj( key, value );
        @SuppressWarnings( "unchecked" )
        final ThreadLocal< Map< String, Object > > requestParams = ( ThreadLocal< Map< String, Object > > ) ReflectionTestUtils
                .getField( threadLocalManager, "requestParamsThreadLoacal" );

        assertNotNull( requestParams );
        assertEquals( requestParams.get().get( key ), value );
    }

    /**
     * Test get agent info.
     */
    @Test
    public void testGetAgentInfo() {
        final Object agentInfo = threadLocalManager.getAgentInfo();
        Assert.assertNotNull( agentInfo );
    }

    /**
     * Test set agent info.
     */
    @Test
    public void testSetAgentInfo() {
        final String agentInfo = "dummy agentinfo";
        threadLocalManager.setAgentInfo( agentInfo );
        @SuppressWarnings( "unchecked" )
        final
        ThreadLocal< Object > agentInfoThreadLocal = ( ThreadLocal< Object > ) ReflectionTestUtils
                .getField( threadLocalManager, "agentInfoThreadLocal" );

        assertNotNull( agentInfoThreadLocal );
    }

    /**
     * Test remove request obj.
     */
    @Test
    public void testRemoveRequestObj() {
        final Map< String, Object > params = new HashMap<>();
        params.put( "key", "paramValue" );
        @SuppressWarnings( "unchecked" )
        final ThreadLocal< Map< String, Object > > requestParams = ( ThreadLocal< Map< String, Object > > ) ReflectionTestUtils
                .getField( threadLocalManager, "requestParamsThreadLoacal" );
        requestParams.set( params );

        threadLocalManager.removeRequestObj();
        @SuppressWarnings( "unchecked" )
        final ThreadLocal< Map< String, Object > > actualRequestParams = ( ThreadLocal< Map< String, Object > > ) ReflectionTestUtils
                .getField( threadLocalManager, "requestParamsThreadLoacal" );

        assertEquals( actualRequestParams.get().isEmpty(), true );
    }

    @Test
    public void testRemoveAgentInfo() {
        @SuppressWarnings( "unchecked" )
        final ThreadLocal< Object > agentInfoThreadLocal = ( ThreadLocal< Object > ) ReflectionTestUtils
                .getField( threadLocalManager, "agentInfoThreadLocal" );
        agentInfoThreadLocal.set( "paramValue" );

        threadLocalManager.removeAgentInfo();
        @SuppressWarnings( "unchecked" )
        final ThreadLocal< Object > actualagentInfoThreadLocal = ( ThreadLocal< Object > ) ReflectionTestUtils
                .getField( threadLocalManager, "agentInfoThreadLocal" );

        assertEquals( actualagentInfoThreadLocal.get(), agentInfoThreadLocal.get() );

    }

    /**
     * Test get instance.
     */
    @Test
    public void testGetInstance() {
        final RequestParameterThreadLocalManager threadLocalManagerEx = ( RequestParameterThreadLocalManager ) ReflectionTestUtils
                .getField( threadLocalManager, "INSTANCE" );
        assertEquals( RequestParameterThreadLocalManager.getInstance(), threadLocalManagerEx );
    }

    /**
     * Test get instance.
     */
    @Test
    public void testIsDefaultRequestParam() {
        final Map< String, Object > defaultRequestObj = ( Map ) ReflectionTestUtils.getField( threadLocalManager,
                "defaultRequestObj" );
        assertTrue( threadLocalManager.isDefaultRequestParam( defaultRequestObj ) );

    }
}
