package com.owners.gravitas.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.hubzu.dsync.service.DistributedSynchronizationService;

/**
 * The SyncCacheLockHandler manages the distributed services lock management.
 */
@Component
public class SyncCacheLockHandler {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( SyncCacheLockHandler.class );

    /** The Constant LOCK_KEY_PREFIX. */
    public static final String LOCK_KEY_PREFIX = "GRAVITAS-";

    /** The cache synchronization service */
    @Autowired
    private DistributedSynchronizationService jedisDistributedSynchronizationService;
    
    @Value( "${sync.lock.time.in.milliseconds:300000}" )
    private long leadLockTimeOutInMilliseconds;
    
    @Value( "${sync.lock.delay.in.release.milliseconds:200}" )
    private long asyncDelayInMilliseconds;

    /**
     * Acquire lock.
     *
     * @param lockName
     *            the lock name
     * @return true, if successful
     */
    public boolean acquireLockBlocking( final String lockName ) {
        boolean acquireLock = false;
        if (lockName != null) {
            final String lockKey = getLockPrefix() + lockName;
            LOGGER.info("Acquiring lock for: {}", lockKey);

            try {
                if (jedisDistributedSynchronizationService.acquireLockBlocking(lockKey,
                        leadLockTimeOutInMilliseconds)) {
                    acquireLock = true;
                }
            } catch (Exception ex) {
                LOGGER.error("Error while acquiring Lock : {}", lockKey, ex);
                throw ex;
            }
        }
        return acquireLock;
    }

    /**
     * Release lock after immediately.
     *
     * @param standardMins
     *            the standard mins
     * @param key
     *            the key
     */
    public void releaseLock( final String key ) {
        if (key != null) {
            final String lockKey = getLockPrefix() + key;
            LOGGER.info("Releasing lock: {}", lockKey);
            try {
                jedisDistributedSynchronizationService.releaseLock(lockKey);
            } catch (Exception ex) {
                LOGGER.error("Problem releasing lock, ignoring and removing lock : {}", lockKey, ex);
            }
        }
    }
    
    /**
     * Release lock after standard delay.
     *
     * @param key
     *            the key
     */
    @Async(value = "lockExecutor")
    public void releaseLockWithDelay( final String key) {
        if (key != null) {
            final String lockKey = getLockPrefix() + key;
            LOGGER.info( "Releasing lock after delay : {}", lockKey, asyncDelayInMilliseconds );
            try {
                Thread.sleep(asyncDelayInMilliseconds);
                jedisDistributedSynchronizationService.releaseLock( lockKey );
            } catch ( Exception ex ) {
                LOGGER.error( "Problem releasing lock, ignoring and removing lock : {}", lockKey, ex );
            }
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
