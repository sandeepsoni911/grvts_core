package com.owners.gravitas.business.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.getField;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.request.SystemErrorSlackRequestBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.GravitasHealthStatus;
import com.owners.gravitas.dto.request.SlackRequest;
import com.owners.gravitas.service.SlackService;

/**
 * The Class GravitasNotificationBusinessServiceImplTest.
 * 
 * @author ankusht
 */
public class GravitasNotificationBusinessServiceImplTest extends AbstractBaseMockitoTest {

    /** The gravitas notification business service impl. */
    @InjectMocks
    private GravitasNotificationBusinessServiceImpl gravitasNotificationBusinessServiceImpl;

    /** The system error slack request builder. */
    @Mock
    private SystemErrorSlackRequestBuilder systemErrorSlackRequestBuilder;

    /** The slack service. */
    @Mock
    private SlackService slackService;

    /**
     * Test notify system error.
     */
    @Test
    public void testNotifySystemError() {
        String systemName = "system name";
        String message = "message";
        boolean working = true;
        String failureInfo = "info";
        GravitasHealthStatus gravitasHealthStatus = new GravitasHealthStatus( systemName, message, working,
                failureInfo );
        SlackRequest slackRequest = new SlackRequest();
        when( systemErrorSlackRequestBuilder.convertTo( gravitasHealthStatus ) ).thenReturn( slackRequest );
        gravitasNotificationBusinessServiceImpl.notifySystemError( gravitasHealthStatus );
        verify( slackService ).publishToSlack( slackRequest,
                ( String ) getField( gravitasNotificationBusinessServiceImpl, "systemFailureErrorSlackChannel" ) );
        verify( systemErrorSlackRequestBuilder ).convertTo( gravitasHealthStatus );
    }
}
