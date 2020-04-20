package com.owners.gravitas.builder;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.hubzu.notification.dto.client.push.PushNotification;
import com.owners.gravitas.business.builder.PushNotificationBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.BadgeCounterJmxConfig;
import com.owners.gravitas.dto.request.NotificationRequest;
import com.owners.gravitas.enums.LeadRequestType;
import com.owners.gravitas.enums.PushNotificationType;

/**
 * The Class PushNotificationBuilderTest.
 *
 * @author vishwanathm
 */
public class PushNotificationBuilderTest extends AbstractBaseMockitoTest {

    /** The push notification builder. */
    @InjectMocks
    private PushNotificationBuilder pushNotificationBuilder;
    
    @Mock
    private BadgeCounterJmxConfig badgeCounterJmxConfig;

    /**
     * Test convert to for lead.
     */
    @Test
    public void testConvertToForNewRequest() {
        ReflectionTestUtils.setField( pushNotificationBuilder, "notificationPriority", 6 );
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setEventType( PushNotificationType.NEW_REQUEST );
        PushNotification notification = pushNotificationBuilder.convertTo( notificationRequest );
        Assert.assertNotNull( notification );
    }
    
    @Test
    public void testConvertToForNewRequestAndRequestTypeAsScheduleTour() {
        ReflectionTestUtils.setField( pushNotificationBuilder, "notificationPriority", 6 );
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setEventType( PushNotificationType.NEW_REQUEST );
        notificationRequest.setRequestType(LeadRequestType.SCHEDULE_TOUR.toString());
        PushNotification notification = pushNotificationBuilder.convertTo( notificationRequest );
        Assert.assertNotNull( notification );
    }
    
    @Test
    public void testConvertToForNewRequestAndRequestTypeAsScheduleTourWithNotNullTaskId() {
        ReflectionTestUtils.setField( pushNotificationBuilder, "notificationPriority", 6 );
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setEventType( PushNotificationType.NEW_REQUEST );
        notificationRequest.setRequestType(LeadRequestType.SCHEDULE_TOUR.toString());
        notificationRequest.setTaskId("dummy");
        PushNotification notification = pushNotificationBuilder.convertTo( notificationRequest );
        Assert.assertNotNull( notification );
    }

    /**
     * Test convert to for task.
     */
    @Test
    public void testConvertToForTask() {
        ReflectionTestUtils.setField( pushNotificationBuilder, "notificationPriority", 6 );
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setEventType( PushNotificationType.NEW_TASK );
        notificationRequest.setFirstName( "test" );
        PushNotification notification = pushNotificationBuilder.convertTo( notificationRequest );
        Assert.assertNotNull( notification );
    }

    /**
     * Test convert to for opportunity.
     */
    @Test
    public void testConvertToForOpportunity() {
        ReflectionTestUtils.setField( pushNotificationBuilder, "notificationPriority", 6 );
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setEventType( PushNotificationType.NEW_OPPORTUNITY );
        notificationRequest.setOpportunityCount( 1 );
        PushNotification notification = pushNotificationBuilder.convertTo( notificationRequest );
        Assert.assertNotNull( notification );
    }

    /**
     * Test convert to source null.
     */
    @Test
    public void testConvertToSourceNull() {
        PushNotification notification = pushNotificationBuilder.convertTo( null );
        Assert.assertNull( notification );

        notification = pushNotificationBuilder.convertTo( null, null );
        Assert.assertNull( notification );
    }

    /**
     * Test convert to null destination.
     */
    @Test
    public void testConvertToNullDestination() {
        ReflectionTestUtils.setField( pushNotificationBuilder, "notificationPriority", 6 );
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setEventType( PushNotificationType.NEW_REQUEST );
        PushNotification notification = pushNotificationBuilder.convertTo( notificationRequest, null );
        Assert.assertNotNull( notification );

        notification = pushNotificationBuilder.convertTo( notificationRequest, new PushNotification() );
        Assert.assertNotNull( notification );
    }

    /**
     * Testconvert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertFrom() {
        pushNotificationBuilder.convertFrom( new PushNotification() );
    }

    /**
     * Test convert to for new opportunity reminder.
     */
    @Test
    public void testConvertToForNewOpportunityReminder() {
        ReflectionTestUtils.setField( pushNotificationBuilder, "notificationPriority", 6 );
        final NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setEventType( PushNotificationType.NEW_OPPORTUNITY_REMINDER );
        PushNotification notification = pushNotificationBuilder.convertTo( notificationRequest, null );
        Assert.assertNotNull( notification );

        notification = pushNotificationBuilder.convertTo( notificationRequest, new PushNotification() );
        Assert.assertNotNull( notification );
    }

    /**
     * Test convert to for claimed opportunity reminder.
     */
    @Test
    public void testConvertToForClaimedOpportunityReminder() {
        ReflectionTestUtils.setField( pushNotificationBuilder, "notificationPriority", 6 );
        final NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setEventType( PushNotificationType.CLAIMED_OPPORTUNITY_REMINDER );
        PushNotification notification = pushNotificationBuilder.convertTo( notificationRequest, null );
        Assert.assertNotNull( notification );

        notification = pushNotificationBuilder.convertTo( notificationRequest, new PushNotification() );
        Assert.assertNotNull( notification );
    }

    /**
     * Test convert to for scheduled task appointement alert.
     */
    @Test
    public void testConvertToForScheduledTaskAppointementAlert() {
        ReflectionTestUtils.setField( pushNotificationBuilder, "notificationPriority", 6 );
        final NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setEventType( PushNotificationType.SCHEDULED_TASK_APPOINTMENT_ALERT );
        PushNotification notification = pushNotificationBuilder.convertTo( notificationRequest, null );
        Assert.assertNotNull( notification );

        notification = pushNotificationBuilder.convertTo( notificationRequest, new PushNotification() );
        Assert.assertNotNull( notification );
    }

    /**
     * Test convert to for scheduled task appointement reminder.
     */
    @Test
    public void testConvertToForScheduledTaskAppointementReminder() {
        ReflectionTestUtils.setField( pushNotificationBuilder, "notificationPriority", 6 );
        final NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setEventType( PushNotificationType.SCHEDULED_TASK_APPOINTMENT_REMINDER );
        PushNotification notification = pushNotificationBuilder.convertTo( notificationRequest, null );
        Assert.assertNotNull( notification );

        notification = pushNotificationBuilder.convertTo( notificationRequest, new PushNotification() );
        Assert.assertNotNull( notification );
    }
}
