package com.owners.gravitas.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hubzu.notification.dto.client.push.AppType;
import com.hubzu.notification.dto.client.push.PushNotification;
import com.owners.gravitas.business.AgentNotificationBusinessService;
import com.owners.gravitas.business.builder.PushNotificationBuilder;
import com.owners.gravitas.domain.Device;
import com.owners.gravitas.dto.request.NotificationRequest;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.NotificationResponse;
import com.owners.gravitas.service.AgentInfoService;
import com.owners.gravitas.service.PushNotificationService;
import com.owners.gravitas.util.JsonUtil;

/**
 * The Class AgentNotificationBusinessServiceImpl.
 *
 * @author amits
 */
@Service( "agentNotificationBusinessService" )
public class AgentNotificationBusinessServiceImpl implements AgentNotificationBusinessService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentNotificationBusinessServiceImpl.class );

    /** The agent info service. */
    @Autowired
    private AgentInfoService agentInfoService;

    /** The push notification builder. */
    @Autowired
    private PushNotificationBuilder pushNotificationBuilder;

    /** The push notification service. */
    @Autowired
    private PushNotificationService pushNotificationService;

    /** The use lock. */
    @Value( "${push.notification.enable: true}" )
    private boolean enablePushNotification;

    /**
     * Send push notification.
     *
     * @param agentId
     *            the agent id
     * @param notificationRequest
     *            the notification request
     * @return the list
     */
    @Override
    public List< String > sendPushNotification( final String agentId, final NotificationRequest notificationRequest ) {
        final List< String > notificationIds = new ArrayList<>();
        if (enablePushNotification) {
            LOGGER.info("push notification is active, lets send notification to agent :{}, notificationRequest : {}",
                    agentId, JsonUtil.toJson(notificationRequest));
            final Set< Device > devices = agentInfoService.getAgentInfo( agentId ).getDevices();
            for ( final Device device : devices ) {
                notificationRequest.setDeviceToken( device.getId() );
                notificationRequest.setDeviceType( AppType.valueOf( device.getType() ) );
                final PushNotification pushNotification = pushNotificationBuilder.convertTo( notificationRequest );
                final NotificationResponse response = pushNotificationService.send( pushNotification );
                if (Status.SUCCESS.name().equalsIgnoreCase( response.getStatus() )) {
                    notificationIds.add( response.getResult() );
                }
            }
        }
        return notificationIds;
    }

    /**
     * Update push notification.
     *
     * @param agentId
     *            the agent id
     * @param notificationRequest
     *            the notification request
     * @param notificationId
     *            the request id
     * @return the list
     */
    @Override
    public NotificationResponse updatePushNotification( final String notificationId, final Long triggerOnDtm ) {
        LOGGER.debug( "Push notification is updated, for resuest id " + notificationId );
        return pushNotificationService.update( notificationId, triggerOnDtm );
    }

    /**
     * Cancel push notification.
     *
     * @param agentId
     *            the agent id
     * @param notificationRequest
     *            the notification request
     * @return the list
     */
    @Override
    public NotificationResponse cancelPushNotification( final String notificationId ) {
        LOGGER.debug( "Push notification is cancelled, for notification id " + notificationId );
        return pushNotificationService.cancel( notificationId );
    }
}
