/**
 *
 */
package com.owners.gravitas.util;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.UnsupportedEncodingException;

import org.mockito.InjectMocks;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;

/**
 * The class EncoderUtilTest
 *
 * @author raviz
 *
 */
public class EncoderUtilTest extends AbstractBaseMockitoTest {
    
    /** The encoder util. */
    @InjectMocks
    private EncoderUtil encoderUtil;

    /**
     * Test get encoded url.
     *
     * @throws UnsupportedEncodingException
     *             the unsupported encoding exception
     */
    @Test
    public void testGetEncodedUrl() throws UnsupportedEncodingException {
        final String url = EncoderUtil.getEncodedUrl( "test+6@test.com" );
        assertNotNull( url );
        assertEquals( url, "test%2B6%40test.com" );
    }
}
