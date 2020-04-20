package com.owners.gravitas.service.impl;

import static com.owners.gravitas.constants.NotificationParameters.CLIENT_ID;
import static com.owners.gravitas.constants.NotificationParameters.MESSAGE_TYPE_NAME;
import static com.owners.gravitas.constants.NotificationParameters.PRIORITY;
import static com.owners.gravitas.constants.NotificationParameters.SMS_TEXT;
import static com.owners.gravitas.constants.NotificationParameters.USER;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hubzu.notification.dto.client.sms.SmsNotification;
import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.config.LeadOpportunityBusinessConfig;
import com.owners.gravitas.dto.PhoneNumber;
import com.owners.gravitas.dto.response.NotificationResponse;
import com.owners.gravitas.enums.PhoneNumberType;
import com.owners.gravitas.service.SmsService;
import com.owners.gravitas.util.JsonUtil;
import com.owners.gravitas.util.StringUtils;

/**
 * 
 * @author gururasm
 *
 */
@Service
public class SmsServiceImpl implements SmsService {

    private static final Logger LOGGER = LoggerFactory.getLogger( SmsServiceImpl.class );

    @Value( "${sms.notification.api.endpoint}" )
    private String smsUrl;

    /** The rest template. */
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private LeadOpportunityBusinessConfig leadOpportunityBusinessConfig;

    @Override
    public NotificationResponse send( final SmsNotification smsNotification ) {
        LOGGER.info( "Sending sms notification with request json as : {}", JsonUtil.toJson( smsNotification ) );
        NotificationResponse response = null;
        try {
            final String notificationResponse = restTemplate.postForEntity( smsUrl, smsNotification, String.class )
                    .getBody();
            LOGGER.info( "sms sending response : {} From Host : {}, notification engine url : {}", notificationResponse,
                    InetAddress.getLocalHost(), smsUrl );
            response = JsonUtil.convertFromJson( notificationResponse, NotificationResponse.class );
        } catch ( final Exception e ) {
            LOGGER.error( "Problem in hitting Notification Engine : {}", smsUrl, e );
        }
        return response;
    }

    @Override
    public SmsNotification prepareAndGetSmsNotification( final OpportunitySource opportunitySource, String phone,
            final String smsText ) {
        final SmsNotification smsNotification = new SmsNotification();
        smsNotification.setClientId( CLIENT_ID );
        smsNotification.setMessageTypeName( MESSAGE_TYPE_NAME );
        smsNotification.setPriority( PRIORITY );
        final List< String > phoneNumbers = new ArrayList<>();
        phone = StringUtils.formatPhoneNumber( phone );
        if (phone.length() <= 10) {
            phone = leadOpportunityBusinessConfig.getCountryCode() + phone;
        }
        phoneNumbers.add( phone );
        smsNotification.setPhoneNumbers( phoneNumbers );
        smsNotification.setFromPhoneNumber( leadOpportunityBusinessConfig.getSmsSender() );
        final Map< String, String > parameterMap = new HashMap<>();
        parameterMap.put( SMS_TEXT, smsText );
        smsNotification.setParameterMap( parameterMap );
        return smsNotification;
    }
}
