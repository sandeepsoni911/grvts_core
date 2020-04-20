package com.owners.gravitas.lock;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.Test;

import com.hubzu.dsync.service.DistributedSynchronizationService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class AgentNewOppSchedulerLockHandlerTest.
 *
 * @author amits
 */
public class AgentNewOppSchedulerLockHandlerTest extends AbstractBaseMockitoTest {

    /** The agent new opp scheduler lock handler. */
    @InjectMocks
    private AgentNewOppSchedulerLockHandler agentNewOppSchedulerLockHandler;

    /** The property writer. */
    @Mock
    private PropertyWriter propertyWriter;

    /** The job synchronization. */
    @Mock
    private DistributedSynchronizationService databaseDistributedSynchronizationService;

    /**
     * Test acquire lock.
     */
    @Test
    public void testAcquireLock() {
        ReflectionTestUtils.setField( agentNewOppSchedulerLockHandler, "useLock", false );
        final String prefix = agentNewOppSchedulerLockHandler.getLockPrefix();
        agentNewOppSchedulerLockHandler.acquireLock( "test" );
        assertNotNull( prefix );
    }

    /**
     * Test acquire lock if use lock set to true.
     */
    @Test
    public void testAcquireLockIfUseLockSetToTrue() {
        final String prefix = agentNewOppSchedulerLockHandler.getLockPrefix();
        ReflectionTestUtils.setField( agentNewOppSchedulerLockHandler, "useLock", true );
        agentNewOppSchedulerLockHandler.acquireLock( "test" );
        assertNotNull( prefix );
    }

    /**
     * Test set use lock.
     */
    @Test
    public void testSetUseLock() {
        agentNewOppSchedulerLockHandler.setUseLock( Boolean.TRUE );
        final Boolean useLock = ( Boolean ) ReflectionTestUtils.getField( agentNewOppSchedulerLockHandler, "useLock" );
        assertEquals( useLock, Boolean.TRUE );
    }

    /**
     * Test get use lock.
     */
    @Test
    public void testGetUseLock() {
        ReflectionTestUtils.setField( agentNewOppSchedulerLockHandler, "useLock", Boolean.TRUE );
        final Boolean useLock = agentNewOppSchedulerLockHandler.isUseLock();
        assertEquals( useLock, Boolean.TRUE );
    }
}
