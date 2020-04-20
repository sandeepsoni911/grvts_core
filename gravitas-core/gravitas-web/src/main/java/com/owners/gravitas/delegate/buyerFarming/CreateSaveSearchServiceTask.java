package com.owners.gravitas.delegate.buyerFarming;

import static com.owners.gravitas.enums.GravitasProcess.LEAD_MANAGEMENT_PROCESS;

import java.util.Date;

import org.flowable.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.BuyerFarmingBusinessService;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.delegate.BaseServiceTask;
import com.owners.gravitas.lock.SyncCacheLockHandler;
import com.owners.gravitas.util.JsonUtil;

/**
 * The Class CreateSaveSearchServiceTask.
 *
 * @author vishwanathm
 */
@Service( "createSaveSearchServiceTask" )
public class CreateSaveSearchServiceTask extends BaseServiceTask {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( CreateSaveSearchServiceTask.class );

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
        LOGGER.info( "Inside CreateSaveSearchServiceTask.execute()" );
        final String executionId = execution.getId();
        final LeadSource leadSource = getLeadSource( executionId );
        LOGGER.info("Processing lead source for save search : {}", JsonUtil.toJson(leadSource) );
        String lockId = leadSource.getId();
        try {
            syncCacheLock.acquireLockBlocking(lockId);
			if (leadSource != null && !leadSource.getEmail().startsWith(Constants.COMMON_LEAD_PREFIX+Constants.COMMON_EMAIL_PREFIX)) {
                LOGGER.debug( "Auto save search started for buyer: " + leadSource.getEmail() + " at: " + new Date() );
                buyerFarmingBusinessService.saveSearch( leadSource );
            }
        } catch ( final Exception e ) {
            LOGGER.error( "Exception occurred while creating save search", e );
            setVariablesInParentProcess( execution, leadSource, e, LEAD_MANAGEMENT_PROCESS );
        } finally {
            syncCacheLock.releaseLock(lockId);
        }
        LOGGER.info( "Exiting CreateSaveSearchServiceTask.execute()" );
    }
}
