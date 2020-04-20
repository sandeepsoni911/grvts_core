package com.owners.gravitas.listener.amqp;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.owners.gravitas.amqp.OpportunityContact;
import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.AgentOpportunityBusinessService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.lock.SyncCacheLockHandler;

/**
 * The Class OpportunityChangeListenerTest.
 *
 * @author amits
 */
public class OpportunityChangeListenerTest extends AbstractBaseMockitoTest {

    /** The opp change listener. */
    @InjectMocks
    private OpportunityChangeListener oppChangeListener;

    /** The agent opportunity business service. */
    @Mock
    private AgentOpportunityBusinessService agentOpportunityBusinessService;
    
    @Mock
    private SyncCacheLockHandler syncCacheLockHandler;

    /**
     * Test handle opportunity change.
     */
    @Test
    public void testHandleOpportunityChange() {
        String crmId = "007";
        OpportunityContact opportunityContact = new OpportunityContact();
        Contact primaryContact = new Contact();
        primaryContact.setCrmId(crmId);
        opportunityContact.setPrimaryContact(primaryContact);
        Mockito.when(syncCacheLockHandler.acquireLockBlocking(Mockito.anyString()))
        .thenReturn(true);
        Mockito.doNothing().when( agentOpportunityBusinessService )
                .handleOpportunityChange( Mockito.any( OpportunitySource.class ) );
        oppChangeListener.handleOpportunityChange( new OpportunitySource() );
        Mockito.verify( agentOpportunityBusinessService )
                .handleOpportunityChange( Mockito.any( OpportunitySource.class ) );
    }

    /**
     * Test handle opportunity change.
     */
    @Test
    public void testHandleOpportunityCreate() {
        oppChangeListener.handleOpportunityCreate( new OpportunitySource() );
    }

}
