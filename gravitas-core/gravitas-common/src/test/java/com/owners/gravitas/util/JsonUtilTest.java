package com.owners.gravitas.util;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.mockito.InjectMocks;
import org.testng.annotations.Test;

import com.owners.gravitas.exception.ApplicationException;

/**
 * The Class JsonUtilTest.
 *
 * @author vishwanathm
 */
public class JsonUtilTest {
    
    /** The json util. */
    @InjectMocks
    private JsonUtil jsonUtil;

    /**
     * Test tojson.
     */
    @Test
    public void testTojson() {
        final Map< String, String > map = new HashMap<>();
        map.put( "key", "value" );
        final String json = JsonUtil.toJson( map );
        Assert.assertNotNull( json );
        Assert.assertEquals( json, "{\"key\":\"value\"}" );

        final String json1 = JsonUtil.toJson( null );
        Assert.assertNull( json1 );
    }

    /**
     * Test to type.
     */
    @Test
    public void testToType() {
        final Object obj = JsonUtil.toType( "true", Object.class );
        Assert.assertNotNull( obj );

        final Object obj1 = JsonUtil.toType( null, Object.class );
        Assert.assertNull( obj1 );
    }

    /**
     * Test to type error case.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testToTypeErrorCase() {
        final Object obj = JsonUtil.toType( "jsonStr", Object.class );
        Assert.assertNotNull( obj );
    }

}
