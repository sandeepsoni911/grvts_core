package com.owners.gravitas.business.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.owners.gravitas.business.GravitasNotificationBusinessService;
import com.owners.gravitas.business.builder.request.SystemErrorSlackRequestBuilder;
import com.owners.gravitas.dto.GravitasHealthStatus;
import com.owners.gravitas.dto.request.SlackRequest;
import com.owners.gravitas.service.SlackService;

/**
 * The Class GravitasNotificationBusinessServiceImpl.
 * 
 * @author ankusht
 */
@Service
public class GravitasNotificationBusinessServiceImpl implements GravitasNotificationBusinessService {
    
    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( GravitasNotificationBusinessServiceImpl.class );

    /** The system error slack request builder. */
    @Autowired
    private SystemErrorSlackRequestBuilder systemErrorSlackRequestBuilder;

    /** The system failure error slack channel. */
    @Value( "${slack.channel.url.system.status.errors}" )
    private String systemFailureErrorSlackChannel;

    /** The slack service. */
    @Autowired
    private SlackService slackService;

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.GravitasNotificationBusinessService#
     * notifySystemError(com.owners.gravitas.dto.GravitasHealthStatus)
     */
    @Override
    public void notifySystemError( final GravitasHealthStatus gravitasHealthStatus ) {
        LOGGER.info( gravitasHealthStatus.getSystemName() + " is down. Notifying slack channel." );
        final SlackRequest slackRequest = systemErrorSlackRequestBuilder.convertTo( gravitasHealthStatus );
        slackService.publishToSlack( slackRequest, systemFailureErrorSlackChannel );
    }

}
