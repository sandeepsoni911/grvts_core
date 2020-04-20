package com.owners.gravitas.delegate.error.handler;

import static com.owners.gravitas.constants.NotificationParameters.CLIENT_ID;
import static com.owners.gravitas.constants.NotificationParameters.MESSAGE_TYPE_NAME;
import static com.owners.gravitas.constants.NotificationParameters.SMS;

import org.flowable.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.constants.NotificationParameters;
import com.owners.gravitas.delegate.BaseServiceTask;
import com.owners.gravitas.domain.entity.NotificationLog;
import com.owners.gravitas.exception.ActivitiException;
import com.owners.gravitas.service.NotificationService;

/**
 * 
 * @author gururasm
 *
 */
@Component( "appDownloadReminderErrorHandler" )
public class AppDownloadReminderErrorHandler extends BaseServiceTask {

    private static final Logger LOGGER = LoggerFactory.getLogger( AppDownloadReminderErrorHandler.class );
    
    @Autowired
    private NotificationService notificationService;

    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public void execute( DelegateExecution execution ) {
        LOGGER.info( "executing appDownloadReminderErrorHandler for the execution id {}", execution.getId() );
        try {
            final ActivitiException exception = getActivitiException( execution.getId() );
            final OpportunitySource opportunitySource = getOpportunitySource( execution.getId() );
            NotificationLog notificationLog = new NotificationLog();
            notificationLog.setClientId( CLIENT_ID );
            notificationLog.setMessageTypeName( MESSAGE_TYPE_NAME );
            notificationLog.setCrmId( opportunitySource.getCrmId() );
            notificationLog.setStatus( NotificationParameters.EXCEPTION );
            notificationLog.setStatusMessage( exception.getMessage() ); 
            notificationLog.setType( SMS );
            notificationService.save( notificationLog );
            LOGGER.error( "appDownloadReminder task completed with exceptions {}", exception );
        } catch ( Exception e ) {
            LOGGER.error(
                    "Error occurred while executing appDownloadReminderErrorHandler for the execution id {} with exception {}",
                    execution.getId(), e );
        }
    }
}
