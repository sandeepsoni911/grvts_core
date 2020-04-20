package com.owners.gravitas.listener.amqp;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.hubzu.dsync.service.DistributedSynchronizationService;
import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.amqp.OpportunityContact;
import com.owners.gravitas.business.LeadBusinessService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.lock.SyncCacheLockHandler;

/**
 * The Class LeadChangeListenerTest.
 */
public class LeadChangeListenerTest extends AbstractBaseMockitoTest {

    /** The lead change listener. */
    @InjectMocks
    private LeadChangeListener leadChangeListener;

    /** The lead business service. */
    @Mock
    private LeadBusinessService leadBusinessService;
    
    @Mock
    private SyncCacheLockHandler syncCacheLockHandler;

    /**
     * Test handle lead change.
     */
    @Test
    public void testHandleLeadChange() {
        String crmId = "007";
        LeadSource leadSource = new LeadSource();
        leadSource.setId(crmId);
        Mockito.when(syncCacheLockHandler.acquireLockBlocking(Mockito.anyString()))
        .thenReturn(true);
        Mockito.doNothing().when( leadBusinessService ).handleLeadChange( Mockito.any( LeadSource.class ) );
        leadChangeListener.handleLeadChange( new LeadSource() );
        Mockito.verify( leadBusinessService ).handleLeadChange( Mockito.any( LeadSource.class ) );
    }

    @Test
    public void testHandleLeadCreate() {
        String crmId = "007";
        LeadSource leadSource = new LeadSource();
        leadSource.setId(crmId);
        Mockito.when(syncCacheLockHandler.acquireLockBlocking(Mockito.anyString()))
        .thenReturn(true);
        Mockito.doNothing().when( leadBusinessService ).handleLeadCreate( Mockito.any( LeadSource.class ) );
        leadChangeListener.handleLeadCreate( new LeadSource() );
        Mockito.verify( leadBusinessService ).handleLeadCreate( Mockito.any( LeadSource.class ) );
    }

}
