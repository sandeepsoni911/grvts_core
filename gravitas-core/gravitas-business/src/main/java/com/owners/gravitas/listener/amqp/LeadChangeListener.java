package com.owners.gravitas.listener.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.annotation.PerformanceLog;
import com.owners.gravitas.business.LeadBusinessService;
import com.owners.gravitas.lock.SyncCacheLockHandler;
import com.owners.gravitas.util.JsonUtil;

/**
 * The listener interface for receiving LeadChangeListener events.
 * The class that is interested in processing a lead change
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>handleLeadChange<code> method. When
 * the lead Change event occurs, that object's appropriate
 * method is invoked.
 *
 */
public class LeadChangeListener {
    
    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( LeadChangeListener.class );

    /** The lead business service. */
    @Autowired
    private LeadBusinessService leadBusinessService;
    
    @Autowired
    private SyncCacheLockHandler syncCacheLockHandler;
    
    /**
     * Handle lead change.
     *
     * @param leadSource
     *            the opportunity
     */
    @PerformanceLog
    public void handleLeadChange( final LeadSource leadSource ) {
        String lockId = leadSource.getId();
        try {
            syncCacheLockHandler.acquireLockBlocking(lockId);
            leadBusinessService.handleLeadChange(leadSource);
        } catch ( Exception e ) {
            LOGGER.error( "Exception while handling lead change {} with exception", JsonUtil.toJson( leadSource ), e );
        } finally {
            syncCacheLockHandler.releaseLock(lockId);
        }
    }

    /**
     * Handle lead create.
     *
     * @param leadSource
     *            the lead source
     */
    @PerformanceLog
    public void handleLeadCreate( final LeadSource leadSource ) {
        String lockId = leadSource.getId();
        try {
            syncCacheLockHandler.acquireLockBlocking(lockId);
            leadBusinessService.handleLeadCreate( leadSource );
        } catch ( Exception e ) {
            LOGGER.error( "Exception while handling lead create {} with exception", JsonUtil.toJson( leadSource ), e );
        } finally {
            syncCacheLockHandler.releaseLock(lockId);
        }
    }
}
