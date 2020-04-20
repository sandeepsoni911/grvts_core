package com.owners.gravitas.service;

import com.hubzu.notification.dto.client.email.data.EmailDataFilter;
import com.owners.gravitas.dto.NotificationResponseObject;

/**
 * The Interface MailDetailStatusService.
 * 
 * @author javeedsy
 */
public interface MailDetailStatusService {

    /**
     * gets email notification detail status using EmailDataFilter.
     *
     * @param emailDataFilter
     *            the email Data Filter.
     * @return the notification response
     */
    NotificationResponseObject getNotificationFeedback( EmailDataFilter emailDataFilter );

}
