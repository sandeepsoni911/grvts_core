package com.owners.gravitas.service;

import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.NotificationLog;
import com.owners.gravitas.dto.response.NotificationResponse;
import com.owners.notification.dto.NotificationEngineResponse;

/**
 * The NotificationService interface
 * @author sandeepsoni
 *
 */
public interface NotificationService {
	
	/**
	 * To send notification with attachment
	 * @param notificationRequest
	 * @return
	 */
	public NotificationEngineResponse sendNotification(EmailNotification notificationRequest);
	
	/**
     * save to db
     * 
     * @param notificationLog
     * @return
     */
    NotificationLog save( NotificationLog notificationLog );
    
    /**
     * construct NotificationLog
     * 
     * @param reponse
     * @param contact
     * @return
     */
    NotificationLog prepareAndGetNotificationLog(NotificationResponse reponse, Contact contact);
}
