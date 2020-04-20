package com.owners.gravitas.delegate.error.handler;

import static com.owners.gravitas.constants.Constants.ACTIVITI_EXCEPTION;

import org.flowable.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.delegate.BaseServiceTask;
import com.owners.gravitas.util.ErrorTokenGenerator;

/**
 * The Class LeadFollowupErrorHandlerServiceTask is error handler for
 * processes.
 *
 * @author vishwanathm
 */
@Component( "marketingEmailErrorHandlerServiceTask" )
public class LeadFollowupErrorHandlerServiceTask extends BaseServiceTask {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( LeadFollowupErrorHandlerServiceTask.class );

    /**
     * Execute will be called to log exception and make required clean for given
     * lead for marketing email log.
     *
     * @param execution
     *            - <code>DelegateExecution</code> instance
     */
    @Override
    public void execute( final DelegateExecution execution ) {
        final String executionId = execution.getId();
        LeadSource leadSource = null;
        try {
            final Exception exception = ( Exception ) runtimeService.getVariable( executionId, ACTIVITI_EXCEPTION );
            leadSource = getLeadSource( executionId );
            logException( exception, leadSource, executionId );
            marketingEmailBusinessService.cleanLeadFollowupLog( leadSource.getId() );
        } catch ( final Exception exception ) {
            logException( exception, leadSource, executionId );
        }
    }

    /**
     * Log exception.
     *
     * @param exception
     *            the exception
     */
    private void logException( final Exception exception, final LeadSource leadSource, final String executionId ) {
        final String errorId = ErrorTokenGenerator.getErrorId();
        final StringBuilder error = new StringBuilder( "Error id->" + errorId );
        error.append( "\n The activiti execution id " + executionId );
        if (null != leadSource) {
            error.append( "\n" + "Marketing email failed for email : " + leadSource.getEmail() + " and for Lead Id: "
                    + leadSource.getId() );
        }
        error.append( "\n" + exception.getLocalizedMessage() );
        LOGGER.error( error.toString(), exception );
    }

}
