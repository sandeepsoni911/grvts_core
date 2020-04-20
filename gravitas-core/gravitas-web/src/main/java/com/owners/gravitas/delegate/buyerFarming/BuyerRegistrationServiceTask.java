package com.owners.gravitas.delegate.buyerFarming;

import static com.owners.gravitas.enums.GravitasProcess.LEAD_MANAGEMENT_PROCESS;

import org.flowable.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.BuyerFarmingBusinessService;
import com.owners.gravitas.delegate.BaseServiceTask;
import com.owners.gravitas.lock.SyncCacheLockHandler;
import com.owners.gravitas.util.JsonUtil;

/**
 * The Class BuyerRegistrationServiceTask.
 *
 * @author vishwanathm
 */
@Service( "buyerRegistrationServiceTask" )
public class BuyerRegistrationServiceTask extends BaseServiceTask {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( BuyerRegistrationServiceTask.class );

    /** The buyer registration business service. */
    @Autowired
    private BuyerFarmingBusinessService buyerFarmingBusinessService;
    
    @Autowired
    private SyncCacheLockHandler syncCacheLock;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.delegate.BaseServiceTask#execute(org.flowable.engine.
     * delegate.DelegateExecution)
     */
    @Override
    public void execute( final DelegateExecution execution ) {
        final LeadSource leadSource = getLeadSource( execution.getId() );
        LOGGER.info("Registering buyer in gravitas : {}", JsonUtil.toJson(leadSource) );
        String lockId = leadSource.getId();
        try {
            syncCacheLock.acquireLockBlocking(lockId);
            buyerFarmingBusinessService.registerBuyer( leadSource );
            LOGGER.info( "Auto buyer registration for " + leadSource.getEmail() + " completed." );
        } catch ( final Exception e ) {
            LOGGER.error( "Exception occurred while doing buyer registration", e );
            setVariablesInParentProcess( execution, leadSource, e, LEAD_MANAGEMENT_PROCESS );
        } finally {
            syncCacheLock.releaseLock(lockId);
        }
    }
}
