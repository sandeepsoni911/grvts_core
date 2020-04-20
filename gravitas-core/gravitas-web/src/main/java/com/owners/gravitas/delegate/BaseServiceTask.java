package com.owners.gravitas.delegate;

import static com.owners.gravitas.constants.Constants.ACTIVITI_EXCEPTION;
import static com.owners.gravitas.constants.Constants.LEAD;
import static com.owners.gravitas.constants.Constants.LOST_STATUS;
import static com.owners.gravitas.enums.ErrorCode.BUYER_FARMING_ERROR;
import static com.owners.gravitas.constants.Constants.OPPORTUNITY;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.LeadFollowupBusinessService;
import com.owners.gravitas.enums.GravitasProcess;
import com.owners.gravitas.exception.ActivitiException;

/**
 * The Class BaseServiceTask.
 *
 * @author vishwanathm
 */
public abstract class BaseServiceTask implements JavaDelegate {

    /** The runtime service. */
    @Autowired
    protected RuntimeService runtimeService;

    /** The marketing email process business service. */
    @Autowired
    protected LeadFollowupBusinessService marketingEmailBusinessService;

    /*
     * (non-Javadoc)
     * @see
     * org.flowable.engine.delegate.JavaDelegate#execute(org.flowable.engine.
     * delegate.DelegateExecution)
     */
    @Override
    public abstract void execute( final DelegateExecution execution );

    /**
     * Gets the lead source.
     *
     * @param executionId
     *            the execution id
     * @return the lead source
     */
    protected LeadSource getLeadSource( final String executionId ) {
        LeadSource leadSource = null;
        final Object object = runtimeService.getVariable( executionId, LEAD );
        if (object != null) {
            leadSource = ( LeadSource ) object;
        }
        return leadSource;
    }
    
    /**
     * Gets the Opportunity source.
     *
     * @param executionId
     *            the execution id
     * @return the lead source
     */
    protected OpportunitySource getOpportunitySource( final String executionId ) {
        OpportunitySource opportunitySource = null;
        final Object object = runtimeService.getVariable( executionId, OPPORTUNITY );
        if (object != null) {
            opportunitySource = ( OpportunitySource ) object;
        }
        return opportunitySource;
    }

    /**
     * Gets the activiti exception.
     *
     * @param executionId
     *            the execution id
     * @return the exception
     */
    protected ActivitiException getActivitiException( final String executionId ) {
        ActivitiException exception = null;
        final Object object = runtimeService.getVariable( executionId, ACTIVITI_EXCEPTION );
        if (object != null) {
            exception = ( ActivitiException ) object;
        }
        return exception;
    }

    /**
     * Checks if is lost status.
     *
     * @param executionId
     *            the execution id
     * @return the boolean
     */
    protected Boolean isLostStatus( final String executionId ) {
        Boolean isLostStatus = null;
        final Object object = runtimeService.getVariable( executionId, LOST_STATUS );
        if (object != null) {
            isLostStatus = ( Boolean ) object;
        }
        return isLostStatus;
    }

    /**
     * Sets the variables in parent process.
     *
     * @param execution
     *            the execution
     * @param leadSource
     *            the lead source
     * @param exception
     *            the e
     * @param process
     */
    protected void setVariablesInParentProcess( final DelegateExecution execution, final LeadSource leadSource,
            final Exception exception, final GravitasProcess process ) {
        String parentId = execution.getSuperExecutionId();
        if (StringUtils.isEmpty(parentId)) {
        	parentId = execution.getId();
        }
        final ActivitiException error = new ActivitiException( BUYER_FARMING_ERROR.getCode(), exception.getMessage(),
                process );
        runtimeService.setVariable( parentId, ACTIVITI_EXCEPTION, error );
        runtimeService.setVariable( parentId, LEAD, leadSource );
        throw error;
    }
}
