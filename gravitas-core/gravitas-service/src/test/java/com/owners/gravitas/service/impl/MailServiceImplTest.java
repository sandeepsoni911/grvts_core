package com.owners.gravitas.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.NotificationPreferenceResponse;
import com.owners.gravitas.dto.response.NotificationResponse;

/**
 * The Class MailServiceImplTest.
 *
 * @author vishwanathm
 */
public class MailServiceImplTest extends AbstractBaseMockitoTest {

    /** The mail service impl. */
    @InjectMocks
    private MailServiceImpl mailServiceImpl;

    /** The rest template. */
    @Mock
    private RestTemplate restTemplate;

    /**
     * Test send.
     *
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    @Test
    public void testSend()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final Field field = mailServiceImpl.getClass().getDeclaredField( "notificationEngineURL" );
        field.setAccessible( true );
        field.set( mailServiceImpl, "test.com" );
        final NotificationResponse response = new NotificationResponse();
        response.setStatus( Status.SUCCESS.name() );
        response.setStatusCode( Status.SUCCESS.name() );
        final EmailNotification notification = new EmailNotification();
        Mockito.when( restTemplate.postForEntity( "test.com", notification, NotificationResponse.class ) )
                .thenReturn( new ResponseEntity< NotificationResponse >( response, HttpStatus.OK ) );
        final NotificationResponse restResp = mailServiceImpl.send( notification );
        Assert.assertNotNull( restResp );
        Assert.assertEquals( restResp.getStatus(), Status.SUCCESS.name() );
    }
    
    @Test
    public void shouldReturnNotificationPreferenceForUser() {
        ReflectionTestUtils.setField( mailServiceImpl, "notificationPrefApi", "testApi/" );
        NotificationPreferenceResponse notificationPreferenceResponse = new NotificationPreferenceResponse();
        when( restTemplate.getForObject( anyString(), any( Class.class ) ) )
                .thenReturn( notificationPreferenceResponse );
        NotificationPreferenceResponse notificationPreferenceResponse2 = mailServiceImpl
                .getNotificationPreferenceForUser( "" );
        assertEquals( notificationPreferenceResponse, notificationPreferenceResponse2 );
    }
}
