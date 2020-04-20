package com.owners.gravitas.service;

import com.hubzu.notification.dto.client.sms.SmsNotification;
import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.dto.response.NotificationResponse;

public interface SmsService {

	/**
	 * send sms
	 * 
	 * @param smsNotification
	 * @return
	 */
    NotificationResponse send(SmsNotification smsNotification);

    /**
     * construct sms notification
     * 
     * @param opportunitySource
     * @param phone
     * @param smsText
     * @return
     */
    SmsNotification prepareAndGetSmsNotification( OpportunitySource opportunitySource, String phone, String smsText );
}
