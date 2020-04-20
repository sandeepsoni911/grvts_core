package com.owners.gravitas.listener.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.hubzu.dsync.service.DistributedSynchronizationService;
import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.annotation.PerformanceLog;
import com.owners.gravitas.business.AgentOpportunityBusinessService;
import com.owners.gravitas.lock.SyncCacheLockHandler;
import com.owners.gravitas.util.JsonUtil;

/**
 * The listener interface for receiving opportunityChange events.
 * The class that is interested in processing a opportunityChange
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addOpportunityChangeListener<code> method. When
 * the opportunityChange event occurs, that object's appropriate
 * method is invoked.
 *
 * @see OpportunityChangeEvent
 */
public class OpportunityChangeListener {
    
    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( OpportunityChangeListener.class );

    /** The agent opportunity business service. */
    @Autowired
    private AgentOpportunityBusinessService agentOpportunityBusinessService;
    
    @Autowired
    private SyncCacheLockHandler syncCacheLockHandler;
    
    /**
     * Handle opportunity change.
     *
     * @param opportunity
     *            the opportunity
     */
    @PerformanceLog
    public void handleOpportunityChange( OpportunitySource opportunity ) {
        String lockId = opportunity.getCrmId();
        try {
            syncCacheLockHandler.acquireLockBlocking(lockId);
            agentOpportunityBusinessService.handleOpportunityChange( opportunity );
        } catch ( Exception e ) {
            LOGGER.error( "Exception while handling opportunity change{} with exception {}",
                    JsonUtil.toJson( opportunity ), e );
        } finally {
            syncCacheLockHandler.releaseLock(lockId);
        }
    }

    /**
     * Handle opportunity create.
     *
     * @param opportunity
     *            the opportunity
     */
    @PerformanceLog
    public void handleOpportunityCreate( OpportunitySource opportunitySource ) {
        // agentOpportunityBusinessService.handleOpportunityCreate( opportunitySource );
    }
}
