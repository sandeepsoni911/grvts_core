/**
 * 
 */
package com.owners.gravitas.service.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

import com.hubzu.notification.dto.client.push.PushNotification;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.response.NotificationResponse;

/**
 * The Class PushNotificationServiceImplTest.
 *
 * @author harshads
 */
public class PushNotificationServiceImplTest extends AbstractBaseMockitoTest {

    /** The push notification service impl. */
    @InjectMocks
    PushNotificationServiceImpl pushNotificationServiceImpl;

    /** The rest template. */
    @Mock
    private RestTemplate restTemplate;

    /**
     * Send test.
     */
    @SuppressWarnings( "unchecked" )
    @Test
    public void sendTest() {
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( NotificationResponse.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new NotificationResponse(), HttpStatus.OK ) );
        pushNotificationServiceImpl.send( new PushNotification() );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( NotificationResponse.class.getClass() ) );

    }

    /**
     * Test cancel.
     */
    @SuppressWarnings( "unchecked" )
    @Test
    public void testCancel() {
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( NotificationResponse.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new NotificationResponse(), HttpStatus.OK ) );
        pushNotificationServiceImpl.cancel( "requestId" );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( NotificationResponse.class.getClass() ) );
    }

    /**
     * Test update.
     */
    @SuppressWarnings( "unchecked" )
    @Test
    public void testUpdate() {
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( NotificationResponse.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new NotificationResponse(), HttpStatus.OK ) );
        pushNotificationServiceImpl.update( "requestId", 0L );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( NotificationResponse.class.getClass() ) );
    }

}
