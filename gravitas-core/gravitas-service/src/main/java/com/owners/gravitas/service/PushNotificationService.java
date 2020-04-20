package com.owners.gravitas.service;

import com.hubzu.notification.dto.client.push.PushNotification;
import com.owners.gravitas.dto.response.NotificationResponse;

/**
 * The Interface PushNotificationService.
 */
public interface PushNotificationService {

    /**
     * Send.
     *
     * @param notification
     *            the notification
     * @return the notification response
     */
    NotificationResponse send( PushNotification notification );

    /**
     * Update.
     *
     * @param requestId the request id
     * @param triggerOnDtm the trigger on dtm
     * @return the notification response
     */
    NotificationResponse update( String requestId, Long triggerOnDtm );

    /**
     * Cancel.
     *
     * @param requestId the request id
     * @return the notification response
     */
    NotificationResponse cancel( String requestId );

}
