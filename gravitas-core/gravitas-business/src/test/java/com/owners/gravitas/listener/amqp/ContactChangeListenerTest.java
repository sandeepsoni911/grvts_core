package com.owners.gravitas.listener.amqp;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.hubzu.dsync.service.DistributedSynchronizationService;
import com.owners.gravitas.amqp.OpportunityContact;
import com.owners.gravitas.business.AgentContactBusinessService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.lock.SyncCacheLockHandler;

/**
 * The Class ContactChangeListenerTest.
 *
 * @author amits
 */
public class ContactChangeListenerTest extends AbstractBaseMockitoTest {

    /** The contact change listener. */
    @InjectMocks
    private ContactChangeListener contactChangeListener;

    /** The agent contact business service. */
    @Mock
    private AgentContactBusinessService agentContactBusinessService;

    @Mock
    private SyncCacheLockHandler syncCacheLockHandler;

    /**
     * Test handle contact change.
     */
    @Test
    public void testHandleContactChange() {
        String crmId = "007";
        OpportunityContact opportunityContact = new OpportunityContact();
        Contact primaryContact = new Contact();
        primaryContact.setCrmId(crmId);
        opportunityContact.setPrimaryContact(primaryContact);
        Mockito.when(syncCacheLockHandler.acquireLockBlocking(Mockito.anyString()))
        .thenReturn(true);
        Mockito.doNothing().when(agentContactBusinessService)
                .handleOpportunityContactChange(Mockito.any(OpportunityContact.class));
        contactChangeListener.handleContactChange(opportunityContact);
        Mockito.verify(agentContactBusinessService)
                .handleOpportunityContactChange(Mockito.any(OpportunityContact.class));
    }

}
