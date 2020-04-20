package com.owners.gravitas.lock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.Test;

import com.hubzu.dsync.service.DistributedSynchronizationService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class AgentReportingSchedularLockHandlerTest.
 * 
 * @author ankusht
 */
public class AgentReportingSchedularLockHandlerTest extends AbstractBaseMockitoTest {

    /** The agent reporting schedular lock handler. */
    @InjectMocks
    private AgentReportingSchedularLockHandler agentReportingSchedularLockHandler;

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
        final String lockName = "lock";
        ReflectionTestUtils.setField( agentReportingSchedularLockHandler, "useLock", true );
        agentReportingSchedularLockHandler.acquireLock( lockName );
        verify( databaseDistributedSynchronizationService ).acquireLock( anyString() );
    }

    /**
     * Test acquire lock 2.
     */
    @Test
    public void testAcquireLock2() {
        final String lockName = "lock";
        ReflectionTestUtils.setField( agentReportingSchedularLockHandler, "useLock", false );
        agentReportingSchedularLockHandler.acquireLock( lockName );
        verifyZeroInteractions( databaseDistributedSynchronizationService );
    }

    /**
     * Test is use lock.
     */
    @Test
    public void testIsUseLock() {
        final boolean useLock = agentReportingSchedularLockHandler.isUseLock();
        assertEquals( ( boolean ) ReflectionTestUtils.getField( agentReportingSchedularLockHandler, "useLock" ),
                useLock );
    }

    /**
     * Test set use lock.
     */
    @Test
    public void testSetUseLock() {
        final boolean useLock = true;
        agentReportingSchedularLockHandler.setUseLock( useLock );
        assertEquals( ( boolean ) ReflectionTestUtils.getField( agentReportingSchedularLockHandler, "useLock" ),
                useLock );
        verify( propertyWriter ).saveJmxProperty( anyString(), any( Object.class ) );
    }
}
