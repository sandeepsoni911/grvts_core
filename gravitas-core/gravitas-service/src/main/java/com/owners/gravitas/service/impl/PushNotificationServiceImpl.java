package com.owners.gravitas.service.impl;

import static com.owners.gravitas.util.RestUtil.buildRequest;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hubzu.notification.dto.client.push.PushNotification;
import com.owners.gravitas.dto.response.NotificationResponse;
import com.owners.gravitas.service.PushNotificationService;
import com.owners.gravitas.util.JsonUtil;

/**
 * The Class PushNotificationServiceImpl.
 *
 * @author vishwanathm
 */
@Service
public class PushNotificationServiceImpl implements PushNotificationService {

    /** Logger instance. */
    private static final Logger LOGGER = LoggerFactory.getLogger( PushNotificationServiceImpl.class );

    /** The notification engine url. */
    @Value( "${push.notification.api.endpoint}" )
    private String pushNotificationURL;

    /** The rest template. */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Send.
     *
     * @param notification
     *            the notification
     * @return the notification response
     */
    @Override
    public NotificationResponse send( final PushNotification notification ) {
        LOGGER.info( "push notification reuqest : {}", JsonUtil.toJson( notification ) );
        final NotificationResponse response = restTemplate.exchange( getURL( "/send" ), POST,
                buildRequest( getHeaders(), notification ), NotificationResponse.class ).getBody();
        LOGGER.info("push notification sent response: {}", JsonUtil.toJson(response) );
        return response;
    }

    /**
     * Update.
     *
     * @param requestId
     *            the request id
     * @param triggerOnDtm
     *            the trigger on dtm
     * @return the notification response
     */
    @Override
    public NotificationResponse update( final String requestId, final Long triggerOnDtm ) {
        final String operation = "/update/" + requestId + "/UPDATE?triggerOn=" + triggerOnDtm;
        final NotificationResponse response = restTemplate
                .exchange( getURL( operation ), PUT, buildRequest( getHeaders(), null ), NotificationResponse.class )
                .getBody();
        LOGGER.info("update push notification sent response: {}", JsonUtil.toJson(response) );
        return response;
    }

    /**
     * Cancel.
     *
     * @param requestId
     *            the request id
     * @return the notification response
     */
    @Override
    public NotificationResponse cancel( final String requestId ) {
        final String operation = "/update/" + requestId + "/CANCEL";
        final NotificationResponse response = restTemplate
                .exchange( getURL( operation ), PUT, buildRequest( getHeaders(), null ), NotificationResponse.class )
                .getBody();
        LOGGER.info("Cancel push notification sent response: {}", JsonUtil.toJson(response) );
        return response;
    }

    /**
     * Gets the headers.
     *
     * @return the headers
     */
    private HttpHeaders getHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.add( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE );
        return headers;
    }

    /**
     * Gets the url.
     *
     * @param operation
     *            the operation
     * @return the url
     */
    private String getURL( final String operation ) {
        String url = pushNotificationURL + operation;
        LOGGER.info("Using Push Notification URL : {}", url);
        return url;
    }
}
