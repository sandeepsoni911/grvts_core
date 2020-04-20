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
 * The Class TopicListenerLockHandlerTest.
 *
 * @author amits
 */
public class TopicListenerLockHandlerTest extends AbstractBaseMockitoTest {

    /** The topic listener lock handler. */
    @InjectMocks
    private TopicListenerLockHandler topicListenerLockHandler;

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
        final String prefix = topicListenerLockHandler.getLockPrefix();
        topicListenerLockHandler.acquireLock( "test" );
        assertNotNull( prefix );
    }

    /**
     * Test acquire lock if use lock set to true.
     */
    @Test
    public void testAcquireLockIfUseLockSetToTrue() {
        final String prefix = topicListenerLockHandler.getLockPrefix();
        ReflectionTestUtils.setField( topicListenerLockHandler, "useLock", true );
        topicListenerLockHandler.acquireLock( "test" );
        assertNotNull( prefix );
    }

    /**
     * Test set use lock.
     */
    @Test
    public void testSetUseLock() {
        topicListenerLockHandler.setUseLock( Boolean.TRUE );
        final Boolean useLock = ( Boolean ) ReflectionTestUtils.getField( topicListenerLockHandler, "useLock" );
        assertEquals( useLock, Boolean.TRUE );
    }

    /**
     * Test get use lock.
     */
    @Test
    public void testGetUseLock() {
        ReflectionTestUtils.setField( topicListenerLockHandler, "useLock", Boolean.TRUE );
        final Boolean useLock = topicListenerLockHandler.isUseLock();
        assertEquals( useLock, Boolean.TRUE );
    }

}
