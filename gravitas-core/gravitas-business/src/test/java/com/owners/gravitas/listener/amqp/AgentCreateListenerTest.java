package com.owners.gravitas.listener.amqp;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.amqp.AgentSource;
import com.owners.gravitas.business.AgentBusinessService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.exception.ApplicationException;

/**
 * The Class AgentCreateListenerTest.
 * 
 * @author ankusht
 */
public class AgentCreateListenerTest extends AbstractBaseMockitoTest {

    /** The lead change listener. */
    @InjectMocks
    private AgentCreateListener agentCreateListener;

    /** The agent business service. */
    @Mock
    private AgentBusinessService agentBusinessService;

    /**
     * Test handle agent create.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testHandleAgentCreate() throws Exception {
        final AgentSource agentSource = new AgentSource();
        doNothing().when( agentBusinessService ).sendPTSEmailNotifications( agentSource );
        doNothing().when( agentBusinessService ).syncAgentDetails( agentSource );
        agentCreateListener.handleAgentCreate( agentSource );
        verify( agentBusinessService ).sendPTSEmailNotifications( agentSource );
        verify( agentBusinessService ).syncAgentDetails( agentSource );
    }

    /**
     * Test handle agent create should throw exception.
     *
     * @throws Exception
     *             the exception
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testHandleAgentCreateShouldThrowException() throws Exception {
        final AgentSource agentSource = new AgentSource();
        doNothing().when( agentBusinessService ).sendPTSEmailNotifications( agentSource );
        doThrow( new IOException() ).when( agentBusinessService ).syncAgentDetails( agentSource );
        agentCreateListener.handleAgentCreate( agentSource );
    }

    /**
     * Test handle agent update.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testHandleAgentUpdate() throws IOException {
        final AgentSource agentSource = new AgentSource();
        doNothing().when( agentBusinessService ).updateAgent( agentSource );
        agentCreateListener.handleAgentUpdate( agentSource );
        verify( agentBusinessService ).updateAgent( agentSource );
    }

    /**
     * Test handle agent update should throw exception.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testHandleAgentUpdateShouldThrowException() throws IOException {
        final AgentSource agentSource = new AgentSource();
        doThrow( new IOException() ).when( agentBusinessService ).updateAgent( agentSource );
        agentCreateListener.handleAgentUpdate( agentSource );
    }
}
