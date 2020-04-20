package com.owners.gravitas.business;

import java.util.List;

import com.owners.gravitas.dto.request.NotificationRequest;
import com.owners.gravitas.dto.response.NotificationResponse;

/**
 * The Interface AgentNotificationBusinessService.
 *
 * @author amits
 */
public interface AgentNotificationBusinessService {

    /**
     * Send push notification.
     *
     * @param agentId
     *            the agent id
     * @param notificationRequest
     *            the notification request
     * @return the list
     */
    List< String > sendPushNotification( String agentId, NotificationRequest notificationRequest );

    /**
     * Update push notification.
     *
     * @param notificationId
     *            the request id
     * @param triggerOnDtm
     *            the trigger on dtm
     * @return the notification response
     */
    NotificationResponse updatePushNotification( String notificationId, Long triggerOnDtm );

    /**
     * Cancel push notification.
     *
     * @param notificationId
     *            the request id
     * @param triggerOnDtm
     *            the trigger on dtm
     * @return the notification response
     */
    NotificationResponse cancelPushNotification( String notificationId );
}
