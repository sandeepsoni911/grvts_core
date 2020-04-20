package com.owners.gravitas.builder;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;

import org.mockito.InjectMocks;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.request.ErrorSlackRequestBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.SlackError;
import com.owners.gravitas.dto.request.SlackRequest;
import com.owners.gravitas.enums.ErrorCode;

/**
 * Test class ErrorSlackRequestBuilderTest.
 *
 * @author raviz
 */
public class ErrorSlackRequestBuilderTest extends AbstractBaseMockitoTest {

    /** The error slack request builder. */
    @InjectMocks
    private ErrorSlackRequestBuilder errorSlackRequestBuilder;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        final SlackError source = new SlackError();
        source.setErrorCode( ErrorCode.INTERNAL_SERVER_ERROR );
        source.setException( new Exception() );
        final SlackRequest destination = new SlackRequest();

        final SlackRequest errorRequest = errorSlackRequestBuilder.convertTo( source, destination );

        assertNotNull( errorRequest );
        assertEquals( errorRequest, destination );
    }

    /**
     * Test convert to when slack request is null.
     */
    @Test
    public void testConvertTo_whenSlackRequestIsNull() {
        final SlackError source = new SlackError();
        source.setErrorCode( ErrorCode.INTERNAL_SERVER_ERROR );
        source.setException( new Exception() );
        final SlackRequest destination = null;

        final SlackRequest errorRequest = errorSlackRequestBuilder.convertTo( source, destination );

        assertNotNull( errorRequest );
        assertNotEquals( errorRequest, destination );

    }

    /**
     * Test convert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertFrom() {
        final SlackError source = new SlackError();
        final SlackRequest destination = new SlackRequest();
        errorSlackRequestBuilder.convertFrom( destination, source );
    }
}
