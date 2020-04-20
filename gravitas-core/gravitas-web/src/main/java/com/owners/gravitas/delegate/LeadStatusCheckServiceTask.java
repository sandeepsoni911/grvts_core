package com.owners.gravitas.delegate;

import static com.owners.gravitas.constants.Constants.ACTIVITI_EXCEPTION;
import static com.owners.gravitas.constants.Constants.LEAD;
import static com.owners.gravitas.enums.ErrorCode.LEAD_FOLLOWUP_ERROR;

import org.flowable.engine.delegate.BpmnError;
import org.flowable.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.LeadBusinessService;
import com.owners.gravitas.dto.crm.response.CRMLeadResponse;
import com.owners.gravitas.enums.LeadStatus;

/**
 * Activiti service task verify the buyer lead status for the current execution.
 *
 * @author vishwanathm
 *
 */
@Component( "leadStatusCheckServiceTask" )
public class LeadStatusCheckServiceTask extends BaseServiceTask {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( LeadStatusCheckServiceTask.class );

    /** The Constant STATUS. */
    private static final String STATUS = "status";

    /** The lead service. */
    @Autowired
    private LeadBusinessService leadBusinessService;

    /**
     * Executes to verify the lead status by retrieving the lead from CRM for
     * current execution, and sets
     * TRUE if status equals 'Outbound Attempt'
     * else FASLE.
     *
     * @param execution
     *            - <code>DelegateExecution</code> instance
     */
    @Override
    public void execute( final DelegateExecution execution ) {
        try {
            final LeadSource leadSource = getLeadSource( execution.getId() );
            LOGGER.debug( "Lead deserialized is " + leadSource );
            final CRMLeadResponse response = leadBusinessService.getCRMLead( leadSource.getId() );
            if (response != null) {
                if (LeadStatus.OUTBOUND_ATTEMPT.getStatus().equalsIgnoreCase( response.getLeadStatus() )) {
                    runtimeService.setVariable( execution.getId(), STATUS, Boolean.TRUE );
                } else {
                    runtimeService.setVariable( execution.getId(), STATUS, Boolean.FALSE );
                }
                leadSource.setEmail( response.getEmail() );
                leadSource.setFirstName( response.getFirstName() );
                leadSource.setLastName( response.getLastName() );
                runtimeService.setVariable( execution.getId(), LEAD, leadSource );
                LOGGER.debug( "Lead status is " + response.getLeadStatus() );
            }
            LOGGER.debug( "CRM lead Id" + leadSource.getId() );
        } catch ( final Exception e ) {
            runtimeService.setVariable( execution.getId(), ACTIVITI_EXCEPTION, e );
            throw new BpmnError( LEAD_FOLLOWUP_ERROR.getCode(), LEAD_FOLLOWUP_ERROR.getErrorDetail() );
        }
    }
}
