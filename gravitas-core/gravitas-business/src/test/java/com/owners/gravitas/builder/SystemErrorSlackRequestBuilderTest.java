package com.owners.gravitas.builder;

import static com.owners.gravitas.constants.Constants.GRAVITAS;
import static org.junit.Assert.assertEquals;

import org.mockito.InjectMocks;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.request.SystemErrorSlackRequestBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.GravitasHealthStatus;
import com.owners.gravitas.dto.request.SlackRequest;

/**
 * The Class SystemErrorSlackRequestBuilderTest.
 * 
 * @author ankusht
 */
public class SystemErrorSlackRequestBuilderTest extends AbstractBaseMockitoTest {

    /** The system error slack request builder. */
    @InjectMocks
    private SystemErrorSlackRequestBuilder systemErrorSlackRequestBuilder;

    /**
     * Test convert to when destination is non null.
     */
    @Test
    public void testConvertToWhenDestinationIsNonNull() {
        String systemName = "firebase";
        String message = "message";
        boolean working = true;
        String failureInfo = "failureInfo";
        GravitasHealthStatus source = new GravitasHealthStatus( systemName, message, working, failureInfo );
        SlackRequest destination = new SlackRequest();
        SlackRequest slackRequest = systemErrorSlackRequestBuilder.convertTo( source, destination );
        assertEquals( GRAVITAS, slackRequest.getUsername() );
        assertEquals( destination, slackRequest );
    }

    /**
     * Test convert to when destination is null.
     */
    @Test
    public void testConvertToWhenDestinationIsNull() {
        String systemName = "firebase";
        String message = "message";
        boolean working = true;
        String failureInfo = "failureInfo";
        GravitasHealthStatus source = new GravitasHealthStatus( systemName, message, working, failureInfo );
        SlackRequest destination = null;
        SlackRequest slackRequest = systemErrorSlackRequestBuilder.convertTo( source, destination );
        assertEquals( GRAVITAS, slackRequest.getUsername() );
    }

    /**
     * Test convert to when source is non null.
     */
    @Test
    public void testConvertToWhenSourceIsNonNull() {
        GravitasHealthStatus source = null;
        SlackRequest destination = new SlackRequest();
        SlackRequest slackRequest = systemErrorSlackRequestBuilder.convertTo( source, destination );
        assertEquals( destination, slackRequest );
    }

    /**
     * Test convert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertFrom() {
        SlackRequest source = null;
        GravitasHealthStatus destination = null;
        systemErrorSlackRequestBuilder.convertFrom( source, destination );
    }
}
