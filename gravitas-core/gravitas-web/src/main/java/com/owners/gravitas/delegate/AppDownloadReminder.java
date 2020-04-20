package com.owners.gravitas.delegate;

import static com.owners.gravitas.constants.Constants.ACTIVITI_EXCEPTION;
import static com.owners.gravitas.enums.ErrorCode.APP_DOWNLOAD_REMINDER_ERROR;

import org.flowable.engine.delegate.BpmnError;
import org.flowable.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.OpportunityBusinessService;

@Component( "appDownloadReminderTask" )
public class AppDownloadReminder extends BaseServiceTask {

    private static final Logger LOGGER = LoggerFactory.getLogger( AppDownloadReminder.class );

    @Autowired
    private OpportunityBusinessService opportunityBusinessService;

    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public void execute( DelegateExecution execution ) {
        LOGGER.info( "executing appDownloadReminderTask for the execution id {}", execution.getId() );
        try {
            final OpportunitySource opportunitySource = getOpportunitySource( execution.getId() );
            opportunityBusinessService.sendAppDownloadReminder( opportunitySource );
        } catch ( Exception e ) {
            LOGGER.error(
                    "Error occurred while executing appDownloadReminderTask for the execution id {} with exception {}",
                    execution.getId(), e );
            runtimeService.setVariable( execution.getId(), ACTIVITI_EXCEPTION, e );
            throw new BpmnError( APP_DOWNLOAD_REMINDER_ERROR.getCode(), APP_DOWNLOAD_REMINDER_ERROR.getErrorDetail() );
        }
    }
}
