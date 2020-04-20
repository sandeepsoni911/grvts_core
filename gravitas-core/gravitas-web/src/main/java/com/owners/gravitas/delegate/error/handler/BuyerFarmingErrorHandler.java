package com.owners.gravitas.delegate.error.handler;

import org.flowable.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.ProcessBusinessService;
import com.owners.gravitas.delegate.BaseServiceTask;
import com.owners.gravitas.exception.ActivitiException;
import com.owners.gravitas.util.ErrorTokenGenerator;

/**
 * The Class InsideSalesFarmingProcessErrorHandler.
 *
 * @author ankusht
 */
@Component( "buyerFarmingErrorHandler" )
public class BuyerFarmingErrorHandler extends BaseServiceTask {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( BuyerFarmingErrorHandler.class );

    /** The process business service. */
    @Autowired
    private ProcessBusinessService processBusinessService;

    /**
     * Execute will be called to log exception .
     *
     * @param execution
     *            - <code>DelegateExecution</code> instance
     */
    @Override
    public void execute( final DelegateExecution execution ) {
        final String executionId = execution.getId();
        try {
            final ActivitiException exception = getActivitiException( executionId );
            if (exception != null) {
                logException( exception, executionId );
                final LeadSource leadSource = getLeadSource( executionId );
                if (leadSource != null) {
                    final String email = leadSource.getEmail();
                    processBusinessService.deActivateProcess( email, exception.getGravitasProcess() );
                }
            }
        } catch ( final Exception exception ) {
            LOGGER.error( "IGNORE: Problem in buyer farming error handling", exception );
            // do nothing, as there's no exception
        }
    }

    /**
     * Log exception.
     *
     * @param exception
     *            the exception
     * @param executionId
     *            the execution id
     */
    private void logException( final Exception exception, final String executionId ) {
        final String errorId = ErrorTokenGenerator.getErrorId();
        final StringBuilder error = new StringBuilder( "Error id->" + errorId );
        error.append( "\n The activiti execution id " + executionId );
        error.append( "\n" + exception.getLocalizedMessage() );
        LOGGER.error( error.toString(), exception );
    }
}
