package com.owners.gravitas.delegate.buyerFarming;

import static com.owners.gravitas.enums.GravitasProcess.INSIDE_SALES_FARMING_PROCESS;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.amqp.BuyerWebActivitySource;
import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.BuyerFarmingBusinessService;
import com.owners.gravitas.delegate.BaseServiceTask;

/**
 * The Class BuyerActivityFollowupServiceTask.
 */
@Service( "buyerActivityFollowupServiceTask" )
public class BuyerActivityFollowupServiceTask extends BaseServiceTask {
    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( BuyerActivityFollowupServiceTask.class );

    /** The Constant BUYER_WEB_ACTIVITY_SOURCE. */
    public static final String BUYER_WEB_ACTIVITY_SOURCE = "buyerWebActivitySource";

    /** The runtime service. */
    @Autowired
    protected RuntimeService runtimeService;

    /** The buyer farming business service. */
    @Autowired
    private BuyerFarmingBusinessService buyerFarmingBusinessService;

    /*
     * (non-Javadoc)
     * @see
     * org.flowable.engine.delegate.JavaDelegate#execute(org.flowable.engine.
     * delegate.DelegateExecution)
     */
    @Override
    public void execute( final DelegateExecution execution ) {
        final String executionId = execution.getId();
        final LeadSource leadSource = getLeadSource( executionId );
        try {
            LOGGER.info( "Inside buyer activity follow up task for leadSource : {}",  leadSource.getId());
            final BuyerWebActivitySource buyerWebActivityMessage = ( BuyerWebActivitySource ) runtimeService
                    .getVariable( executionId, BUYER_WEB_ACTIVITY_SOURCE );
            buyerFarmingBusinessService.sendWebActivityFollowupEmail( buyerWebActivityMessage );
        } catch ( final Exception e ) {
            setVariablesInParentProcess( execution, leadSource, e, INSIDE_SALES_FARMING_PROCESS );
        }
    }
}
