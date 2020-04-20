package com.owners.gravitas.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hubzu.dsync.service.DistributedSynchronizationService;

/**
 * The SyncLockHandler manages the distributed services lock management.
 */
@Component
public class SyncLockHandler {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( SyncLockHandler.class );

    /** The Constant LOCK_KEY_PREFIX. */
    public static final String LOCK_KEY_PREFIX = "GRAVITAS-";

    /** The job synchronization. */
    @Autowired
    private DistributedSynchronizationService databaseDistributedSynchronizationService;

    /**
     * Acquire lock.
     *
     * @param lockName
     *            the lock name
     * @return true, if successful
     */
    public boolean acquireLock( final String lockName ) {
        final String lockKey = getLockPrefix() + lockName;
        LOGGER.debug( "Acquiring lock for:" + lockKey );
        boolean acquireLock = false;
        try {
            if (databaseDistributedSynchronizationService.acquireLock( lockKey )) {
                acquireLock = true;
            }
        } catch ( Exception ex ) {
            LOGGER.error( ex.getLocalizedMessage(), ex );
            throw ex;
        }
        return acquireLock;
    }

    /**
     * Release lock after standard mins.
     *
     * @param standardMins
     *            the standard mins
     * @param key
     *            the key
     */
    public void releaseLock( final String key ) {
        final String lockKey = getLockPrefix() + key;
        LOGGER.debug( "Releasing lock: " + lockKey );
        try {
            databaseDistributedSynchronizationService.releaseLock( lockKey );
        } catch ( Exception ex ) {
            // Do nothing
            LOGGER.info( "Problem releasing lock, ignoring and removing lock", ex );
        }
    }

    /**
     * Gets the lock prefix.
     *
     * @return the lock prefix
     */
    protected String getLockPrefix() {
        return LOCK_KEY_PREFIX;
    }
}
