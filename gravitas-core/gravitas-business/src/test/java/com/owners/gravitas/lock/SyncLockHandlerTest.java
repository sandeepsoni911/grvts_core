package com.owners.gravitas.lock;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.hubzu.dsync.service.DistributedSynchronizationService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;

/**
 * The Class SyncLockHandlerTest.
 *
 * @author amits
 */
public class SyncLockHandlerTest extends AbstractBaseMockitoTest {

    /** The sync lock handler. */
    @InjectMocks
    private SyncLockHandler syncLockHandler;

    /** The job synchronization. */
    @Mock
    private DistributedSynchronizationService databaseDistributedSynchronizationService;

    /**
     * Test acquire lock.
     */
    @Test
    public void testAcquireLock() {
        final String prefix = syncLockHandler.getLockPrefix();
        syncLockHandler.acquireLock( "test" );
        syncLockHandler.releaseLock( "test" );
        assertNotNull( prefix );
    }

    /**
     * Test acquire lock successful.
     */
    @Test
    public void testAcquireLockSuccessful() {
        final String lockKey = "testLock";
        when( databaseDistributedSynchronizationService.acquireLock( anyString() ) ).thenReturn( true );
        final Boolean isLockAcquired = syncLockHandler.acquireLock( lockKey );
        assertEquals( isLockAcquired, Boolean.TRUE );
    }

    /**
     * Test acquire lock unsuccessful.
     */
    @Test( expectedExceptions = Exception.class )
    public void testAcquireLockUnsuccessful() {
        final String lockKey = "testLock";
        doThrow( Exception.class ).when( databaseDistributedSynchronizationService ).acquireLock( anyString() );
        final Boolean isLockAcquired = syncLockHandler.acquireLock( lockKey );
        assertEquals( isLockAcquired, Boolean.FALSE );
    }

    /**
     * Test release lock.
     */
    @Test
    public void testReleaseLock() {
        doThrow( Exception.class ).when( databaseDistributedSynchronizationService ).releaseLock( anyString() );
        syncLockHandler.releaseLock( "key1" );
    }
}
