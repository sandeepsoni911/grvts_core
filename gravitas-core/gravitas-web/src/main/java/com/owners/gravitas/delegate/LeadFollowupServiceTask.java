package com.owners.gravitas.delegate;

import static com.owners.gravitas.constants.Constants.ACTIVITI_EXCEPTION;
import static com.owners.gravitas.enums.ErrorCode.LEAD_FOLLOWUP_ERROR;

import org.flowable.engine.delegate.BpmnError;
import org.flowable.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.owners.gravitas.amqp.LeadSource;

/**
 * Activiti service task to send marketing email in case of each type of
 * marketing email.
 *
 * @author vishwanathm
 *
 */
@Component( "marketingEmailServiceTask" )
public class LeadFollowupServiceTask extends BaseServiceTask {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( LeadFollowupServiceTask.class );

    /**
     * Execute will be called to send marketing email in case of each type of
     * marketing email for configured duration.
     *
     * @param execution
     *            - <code>DelegateExecution</code> instance
     */
    @Override
    public void execute( final DelegateExecution execution ) {
        try {
            final LeadSource leadSource = getLeadSource( execution.getId() );
            marketingEmailBusinessService.sendLeadFollowupEmails( execution.getId(), leadSource );
            LOGGER.debug( "Marketing email started for execution id " + execution.getId() );
        } catch ( final Exception e ) {
            runtimeService.setVariable( execution.getId(), ACTIVITI_EXCEPTION, e );
            throw new BpmnError( LEAD_FOLLOWUP_ERROR.getCode(), LEAD_FOLLOWUP_ERROR.getErrorDetail() );
        }
    }
}
