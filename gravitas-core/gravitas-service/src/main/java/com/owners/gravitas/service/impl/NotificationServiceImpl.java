package com.owners.gravitas.service.impl;

import static com.owners.gravitas.constants.NotificationParameters.CLIENT_ID;
import static com.owners.gravitas.constants.NotificationParameters.MESSAGE_TYPE_NAME;
import static com.owners.gravitas.constants.NotificationParameters.SMS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.NotificationLog;
import com.owners.gravitas.dto.response.NotificationResponse;
import com.owners.gravitas.repository.NotificationRepository;
import com.owners.gravitas.service.NotificationService;
import com.owners.notification.dto.NotificationEngineResponse;
/**
 * Implementation class for NotificationService
 * @author sandeepsoni
 *
 */
@Service
public class NotificationServiceImpl implements NotificationService {
	
	
	/** The rest template */
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private NotificationRepository notificationRepository;
    
    @Value("${notification.engine.endpoint}")
    private String notificationEngineEndpoint;
    
    private static String NOTIFICATION_URL = "/notification/notify/message/send-sync";
    
    
	@Override
	public NotificationEngineResponse sendNotification(EmailNotification notificationRequest) {
		
		NotificationEngineResponse response = null;
		String url = notificationEngineEndpoint+NOTIFICATION_URL;
		HttpEntity<EmailNotification> request = new HttpEntity<>(notificationRequest);
		
		response = restTemplate.exchange(url, HttpMethod.POST, request, NotificationEngineResponse.class).getBody();
		return response;
	}
	
	@Override
    @Transactional( propagation = Propagation.REQUIRED )
    public NotificationLog save( NotificationLog notificationLog ) {
        return notificationRepository.save( notificationLog );
    }

    @Override
    public NotificationLog prepareAndGetNotificationLog(NotificationResponse reponse, Contact contact) {
        final NotificationLog notificationLog = new NotificationLog();
        notificationLog.setClientId( CLIENT_ID );
        notificationLog.setMessageTypeName( MESSAGE_TYPE_NAME );
        notificationLog.setCrmId( contact.getCrmId() );
        notificationLog.setPhone( contact.getPhone() );
        notificationLog.setEmail( contact.getEmail() );
        notificationLog.setRequestId( reponse.getResult() );
        notificationLog.setStatus( reponse.getStatus() );
        notificationLog.setStatusCode( reponse.getStatusCode() );
        notificationLog.setStatusMessage( reponse.getStatusMessage() );
        notificationLog.setType( SMS );
        return notificationLog;
    }
}
