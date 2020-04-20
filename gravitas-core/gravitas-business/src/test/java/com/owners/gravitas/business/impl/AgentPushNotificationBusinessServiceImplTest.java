package com.owners.gravitas.business.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.hubzu.notification.dto.client.push.PushNotification;
import com.owners.gravitas.business.builder.PushNotificationBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.AgentInfo;
import com.owners.gravitas.domain.Device;
import com.owners.gravitas.dto.request.NotificationRequest;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.NotificationResponse;
import com.owners.gravitas.service.AgentInfoService;
import com.owners.gravitas.service.PushNotificationService;

public class AgentPushNotificationBusinessServiceImplTest extends AbstractBaseMockitoTest {

    @InjectMocks
    private AgentNotificationBusinessServiceImpl agentPushNotificationBusinessServiceImpl;

    /** The agent info service. */
    @Mock
    private AgentInfoService agentInfoService;
    /** The push notification builder. */
    @Mock
    private PushNotificationBuilder pushNotificationBuilder;

    /** The push notification service. */
    @Mock
    private PushNotificationService pushNotificationService;

    @Test
    public void testSendPushNotification() {
        ReflectionTestUtils.setField( agentPushNotificationBusinessServiceImpl, "enablePushNotification",
                Boolean.TRUE );
        final AgentInfo agentInfo = new AgentInfo();
        final Device device = new Device();
        device.setType( "IOS" );
        agentInfo.addDevice( device );
        Mockito.when( agentInfoService.getAgentInfo( Mockito.any() ) ).thenReturn( agentInfo );
        final NotificationResponse notificationResponse = new NotificationResponse();
        notificationResponse.setStatus( Status.SUCCESS.name() );
        when( pushNotificationService.send( any( PushNotification.class ) ) ).thenReturn( notificationResponse );
        final List< String > notificationIds = agentPushNotificationBusinessServiceImpl.sendPushNotification( "test",
                new NotificationRequest() );
        Mockito.verify( pushNotificationService ).send( Mockito.any() );
        verify( pushNotificationBuilder ).convertTo( any( NotificationRequest.class ) );
        verify( agentInfoService ).getAgentInfo( anyString() );
        assertEquals( 1, notificationIds.size() );
    }

    @Test
    public void shouldNotSendPustNotificationsIfDisabled() {
        ReflectionTestUtils.setField( agentPushNotificationBusinessServiceImpl, "enablePushNotification",
                Boolean.FALSE );
        final List< String > list = agentPushNotificationBusinessServiceImpl.sendPushNotification( "test",
                new NotificationRequest() );
        verifyNoMoreInteractions( agentInfoService );
        verifyNoMoreInteractions( pushNotificationBuilder );
        verifyNoMoreInteractions( pushNotificationService );
        assertTrue( list.isEmpty() );
    }

    /**
     * Test update push notification.
     */
    @Test
    public void testUpdatePushNotification() {
        final NotificationResponse notificationResponse = new NotificationResponse();
        final String notificationId = "dummy notificationId";
        final Long triggerOnDtm = new Date().getTime();

        when( pushNotificationService.update( notificationId, triggerOnDtm ) ).thenReturn( notificationResponse );

        final NotificationResponse actual = agentPushNotificationBusinessServiceImpl
                .updatePushNotification( notificationId, triggerOnDtm );
        Assert.assertEquals( actual, notificationResponse );
    }

    /**
     * Test cancel push notification.
     */
    @Test
    public void testCancelPushNotification() {
        final NotificationResponse notificationResponse = new NotificationResponse();
        final String notificationId = "dummy notificationId";

        when( pushNotificationService.cancel( notificationId ) ).thenReturn( notificationResponse );

        final NotificationResponse actual = agentPushNotificationBusinessServiceImpl
                .cancelPushNotification( notificationId );
        Assert.assertEquals( actual, notificationResponse );
    }

}
