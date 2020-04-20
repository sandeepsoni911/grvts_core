package com.owners.gravitas.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.owners.gravitas.business.NotificationBusiness;

/**
 * Scheduler to send reports for record
 * view framework
 * @author sandeepsoni
 *
 */
@Component
public class RecordViewScheduler {
	
	@Autowired
	NotificationBusiness notificationBusiness;
	
	@Scheduled( cron = "${scheduler_recordview_scheduler_interval}" )
    public void sendSavedSearchDigestNotifications() {
		System.out.println("Current time is : "+getCurrentTimeStamp());
		notificationBusiness.processPendingNotifications();
	}

	/**
	 * To get current timestamp
	 * @return
	 */
	public static String getCurrentTimeStamp() {
	    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	}
}
