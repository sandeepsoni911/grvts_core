package com.owners.gravitas.delegate.buyerFarming;

import static com.owners.gravitas.constants.BuyerFarmingConstants.FOLLOWUP_TYPE;
import static com.owners.gravitas.enums.FollowupType.FOLLOW_UP_2;
import static com.owners.gravitas.enums.GravitasProcess.LEAD_MANAGEMENT_PROCESS;

import org.flowable.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.ActivitiBusinessService;
import com.owners.gravitas.business.BuyerFarmingBusinessService;
import com.owners.gravitas.delegate.BaseServiceTask;

/**
 * The Class SaveSearchFollowupEmailServiceTask.
 *
 * @author raviz
 */
@Service( "saveSearchFollowupEmailServiceTask" )
public class SaveSearchFollowupEmailServiceTask extends BaseServiceTask {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( SaveSearchFollowupEmailServiceTask.class );

    /** The buyer farming business service. */
    @Autowired
    private BuyerFarmingBusinessService buyerFarmingBusinessService;

    @Autowired
    private ActivitiBusinessService activitiBusinessService;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.delegate.BaseServiceTask#execute(org.flowable.engine.
     * delegate.DelegateExecution)
     */
    @Override
    public void execute( final DelegateExecution execution ) {
        final String executionId = activitiBusinessService.findLatestExecutionId( execution.getProcessInstanceId() );
        LOGGER.debug( "Start SaveSearchFollowupEmailServiceTask with Execution ID :{}", executionId );
        // final LeadSource leadSource = getLeadSource( execution.getId() );
        final LeadSource leadSource = getLeadSource( executionId );
        try {
            LOGGER.info( "Sending save search follow up email :{}", leadSource.getEmail() );
            buyerFarmingBusinessService.sendFollowupEmail( executionId, leadSource );
            runtimeService.setVariable( executionId, FOLLOWUP_TYPE, FOLLOW_UP_2 );
            LOGGER.debug( "End SaveSearchFollowupEmailServiceTask with Execution ID :{}", executionId );
        } catch ( final Exception e ) {
            LOGGER.error( "Exception while sending followup email::" + e.getMessage(), e );
            setVariablesInParentProcess( execution, leadSource, e, LEAD_MANAGEMENT_PROCESS );
        }
    }
}
