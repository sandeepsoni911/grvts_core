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
 * The Class AffiliatedMailLockHandlerTest.
 *
 * @author amits
 */
public class AffiliatedMailLockHandlerTest extends AbstractBaseMockitoTest {

    /** The affiliated mail lock handler. */
    @InjectMocks
    private AffiliatedMailLockHandler affiliatedMailLockHandler;

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
        final String prefix = affiliatedMailLockHandler.getLockPrefix();
        affiliatedMailLockHandler.acquireLock( "test" );
        assertNotNull( prefix );
    }

    /**
     * Test acquire lock if use lock set to true.
     */
    @Test
    public void testAcquireLockIfUseLockSetToTrue() {
        final String prefix = affiliatedMailLockHandler.getLockPrefix();
        ReflectionTestUtils.setField( affiliatedMailLockHandler, "useLock", true );
        affiliatedMailLockHandler.acquireLock( "test" );
        assertNotNull( prefix );
    }

    /**
     * Test set use lock.
     */
    @Test
    public void testSetUseLock() {
        affiliatedMailLockHandler.setUseLock( Boolean.TRUE );
        final Boolean useLock = ( Boolean ) ReflectionTestUtils.getField( affiliatedMailLockHandler, "useLock" );
        assertEquals( useLock, Boolean.TRUE );
    }

    /**
     * Test get use lock.
     */
    @Test
    public void testGetUseLock() {
        ReflectionTestUtils.setField( affiliatedMailLockHandler, "useLock", Boolean.TRUE );
        final Boolean useLock = affiliatedMailLockHandler.isUseLock();
        assertEquals( useLock, Boolean.TRUE );
    }
}
