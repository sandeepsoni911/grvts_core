package com.owners.gravitas.listener.amqp;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.owners.gravitas.amqp.AgentSource;
import com.owners.gravitas.annotation.PerformanceLog;
import com.owners.gravitas.business.AgentBusinessService;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.util.JsonUtil;

/**
 * The listener interface for receiving agentCreate events.
 * The class that is interested in processing a agentCreate
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addAgentCreateListener<code> method. When
 * the agentCreate event occurs, that object's appropriate
 * method is invoked.
 *
 * @see AgentCreateEvent
 *
 * @author vishwanathm
 */
public class AgentCreateListener {
    
    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentCreateListener.class );

    /** The agent business service. */
    @Autowired
    private AgentBusinessService agentBusinessService;

    /**
     * Handle agent create.
     *
     * @param agentSource
     *            the agent source
     */
    @PerformanceLog
    public void handleAgentCreate( final AgentSource agentSource ) {
        agentBusinessService.sendPTSEmailNotifications( agentSource );
        try {
            agentBusinessService.syncAgentDetails( agentSource );
        } catch ( Exception e ) {
            LOGGER.error( "Problem in creating agent details {} with exception {}",
                    JsonUtil.toJson( agentSource ), e );
            throw new ApplicationException( "Problem in creating agent details ", e );
        }
    }

    /**
     * Handle agent update.
     *
     * @param agentSource
     *            the agent source
     */
    @PerformanceLog
    public void handleAgentUpdate( final AgentSource agentSource ) {
        try {
            agentBusinessService.updateAgent( agentSource );
        } catch ( IOException e ) {
            throw new ApplicationException( "Problem in sycnchronizing agent details ", e );
        }
    }
}
