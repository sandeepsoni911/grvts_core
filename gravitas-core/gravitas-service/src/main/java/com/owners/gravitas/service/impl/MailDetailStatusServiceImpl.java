package com.owners.gravitas.service.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.hubzu.notification.dto.client.email.data.EmailDataFilter;
import com.owners.gravitas.dto.NotificationResponseObject;
import com.owners.gravitas.service.MailDetailStatusService;
import com.owners.gravitas.util.JsonUtil;

/**
 * The Class MailServiceImpl.
 *
 * @author javeedsy
 */
@Service
public class MailDetailStatusServiceImpl implements MailDetailStatusService {

    /** Logger instance. */
    private static final Logger LOGGER = LoggerFactory.getLogger( MailDetailStatusServiceImpl.class );

    /** The notification engine url. */
    @Value( "${email.notification.details.api.endpoint}" )
    private String notificationEngineURL;

    /** The rest template. */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * gets email notification detail status using EmailDataFilter.
     *
     * @param emailDataFilter
     *            the email Data Filter.
     * @return the notification response
     */
    public NotificationResponseObject getNotificationFeedback( EmailDataFilter emailDataFilter ) {
        LOGGER.info( "Getting email notification details with request json as " + JsonUtil.toJson( emailDataFilter ) );
        NotificationResponseObject notificationResponseObject = null;
        if (CollectionUtils.isEmpty( emailDataFilter.getRequestIdList() )) {
            return notificationResponseObject;
        }
        try {
            HttpEntity< EmailDataFilter > request = new HttpEntity<>( emailDataFilter );
            String response = restTemplate.exchange( notificationEngineURL, HttpMethod.POST, request, String.class )
                    .getBody();
            notificationResponseObject = JsonUtil.getFromJson( response, NotificationResponseObject.class );
            LOGGER.info( "mail details response : " + JsonUtil.toJson( response ) + " From Host "
                    + InetAddress.getLocalHost() + " notification engine url :" + notificationEngineURL );
        } catch ( final UnknownHostException e ) {
            LOGGER.info( "Problem in getting host name : Which has no impact on business : IGNORE ", e );
        }
        return notificationResponseObject;
    }

}
