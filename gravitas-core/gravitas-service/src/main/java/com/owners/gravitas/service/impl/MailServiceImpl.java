package com.owners.gravitas.service.impl;

import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hubzu.notification.dto.client.digest.DigestEmailNotification;
import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.dto.response.NotificationPreferenceResponse;
import com.owners.gravitas.dto.response.NotificationResponse;
import com.owners.gravitas.service.MailService;
import com.owners.gravitas.util.JsonUtil;

/**
 * The Class MailServiceImpl.
 *
 * @author vishwanathm
 */
@Service
public class MailServiceImpl implements MailService {

    /** Logger instance. */
    private static final Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);

    /** The notification engine url. */
    @Value("${email.notification.api.endpoint}")
    private String notificationEngineURL;
    @Value("${email.digest_notification_api_endpoint}")
    private String notificationEngineDigestUrl;

    /** The owners notification preference url */
    @Value(value = "${owners.notification.preference.api.endpoint}")
    private String notificationPrefApi;

    /** The rest template. */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Send email notification using NotificationRequest.
     *
     * @param emailNotification
     *            the notification request.
     * @return NotificationPreferenceResponse instance.
     */
    @Override
    public NotificationResponse send(final EmailNotification emailNotification) {
        LOGGER.info("Sending email notification with request json as : {}", JsonUtil.toJson(emailNotification));
        NotificationResponse response = null;
        try {
            response = restTemplate.postForEntity(notificationEngineURL, emailNotification, NotificationResponse.class)
                    .getBody();
            LOGGER.info("mail sending response : " + JsonUtil.toJson(response) + " From Host "
                    + InetAddress.getLocalHost() + " notification engine url :" + notificationEngineURL);
        } catch (final Exception e) {
            LOGGER.error("Problem in hitting Notification Engine : {}", notificationEngineURL, e);
        }
        return response;
    }

    @Override
    public NotificationResponse sendDigest(final DigestEmailNotification digestEmailNotification) {
        LOGGER.info("Sending digestEmailNotification with request json as : {}, notificationEngineDigestUrl :{}",
                JsonUtil.toJson(digestEmailNotification), notificationEngineDigestUrl);
        NotificationResponse response = null;
        try {
            response = restTemplate
                    .postForEntity(notificationEngineDigestUrl, digestEmailNotification, NotificationResponse.class)
                    .getBody();
            LOGGER.info( "digest mail sending response : {} From Host {} and digest notification engine url: {}"
                    + JsonUtil.toJson( response ), InetAddress.getLocalHost(), notificationEngineDigestUrl );
        } catch (final Exception e) {
            LOGGER.error("Problem in hitting Notification Engine Digest URL : {}, digestEmailNotification : {}",
                    notificationEngineDigestUrl, JsonUtil.toJson(digestEmailNotification), e);
        }
        return response;
    }

    /**
     * Get a user's marketing mails' preferences on Owners.com
     *
     * @param email
     *            as string type.
     * @return NotificationPreferenceResponse instance.
     */
    @Override
    public NotificationPreferenceResponse getNotificationPreferenceForUser(final String email) {
        final NotificationPreferenceResponse notificationPreferenceResponse = restTemplate
                .getForObject(notificationPrefApi + email, NotificationPreferenceResponse.class);
        return notificationPreferenceResponse;
    }

}
