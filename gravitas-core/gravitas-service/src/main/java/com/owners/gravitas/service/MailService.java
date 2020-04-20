package com.owners.gravitas.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import com.hubzu.notification.dto.client.digest.DigestEmailNotification;
import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.dto.response.NotificationPreferenceResponse;
import com.owners.gravitas.dto.response.NotificationResponse;

/**
 * The Interface MailService.
 */
public interface MailService {

    /**
     * Send email notification using <code>EmailNotification</code>.
     *
     * @param emailNotification
     *            the email Notification.
     * @return the notification response
     */
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000))
    NotificationResponse send(EmailNotification emailNotification);

    /**
     * Get a user's marketing mails' preferences on Owners.com
     *
     * @param email
     * @return
     */
    NotificationPreferenceResponse getNotificationPreferenceForUser(String email);

    /**
     * 
     * @param digestEmailNotification
     * @return
     */
    NotificationResponse sendDigest(DigestEmailNotification digestEmailNotification);
}
