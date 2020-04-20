package com.owners.gravitas.listener.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.hubzu.dsync.service.DistributedSynchronizationService;
import com.owners.gravitas.amqp.OpportunityContact;
import com.owners.gravitas.annotation.PerformanceLog;
import com.owners.gravitas.business.AgentContactBusinessService;
import com.owners.gravitas.lock.SyncCacheLockHandler;
import com.owners.gravitas.util.JsonUtil;

/**
 * The listener interface for receiving contactChange events.
 * The class that is interested in processing a contactChange
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addContactChangeListener<code> method. When
 * the contactChange event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ContactChangeEvent
 */
public class ContactChangeListener {
    
    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( ContactChangeListener.class );

    /** The agent contact business service. */
    @Autowired
    private AgentContactBusinessService agentContactBusinessService;
    
    @Autowired
    private SyncCacheLockHandler syncCacheLockHandler;
    
    /**
     * Handle contact change.
     *
     * @param contact
     *            the contact
     */
    @PerformanceLog
    public void handleContactChange( final OpportunityContact contact ) {
        String lockId = contact.getPrimaryContact().getCrmId();
        LOGGER.info("Acquiring Lock for : {}", lockId);
        try {
            syncCacheLockHandler.acquireLockBlocking(lockId);
            agentContactBusinessService.handleOpportunityContactChange(contact);
        } catch ( Exception e ) {
            LOGGER.error( "Exception while handling contact change{} with exception {}", JsonUtil.toJson( contact ), e );
        } finally {
            syncCacheLockHandler.releaseLock(lockId);
        }
    }
}
