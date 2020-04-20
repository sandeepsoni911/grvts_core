package com.owners.gravitas.delegate;

import static com.owners.gravitas.constants.Constants.ACTIVITI_EXCEPTION;
import static com.owners.gravitas.enums.ErrorCode.LEAD_FOLLOWUP_ERROR;

import org.flowable.engine.delegate.BpmnError;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;

import com.owners.gravitas.amqp.LeadSource;

/**
 * The Class LeadFollowupClosureServiceTask.
 *
 * @author vishwanathm
 */
@Service( "marketingEmailClosureServiceTask" )
public class LeadFollowupClosureServiceTask extends BaseServiceTask {

    /**
     * Execute will be called to clean up the given lead's marketing email log
     * before it going to end.
     *
     * @param execution
     *            - <code>DelegateExecution</code> instance
     */
    @Override
    public void execute( final DelegateExecution execution ) {
        try {
            final LeadSource leadSource = getLeadSource( execution.getId() );
            marketingEmailBusinessService.cleanLeadFollowupLog( leadSource.getId() );
        } catch ( final Exception e ) {
            runtimeService.setVariable( execution.getId(), ACTIVITI_EXCEPTION, e );
            throw new BpmnError( LEAD_FOLLOWUP_ERROR.getCode(), LEAD_FOLLOWUP_ERROR.getErrorDetail() );
        }
    }
}
