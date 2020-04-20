package com.owners.gravitas.delegate;

import org.flowable.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.business.AgentBusinessService;

/**
 * The Class AgentAvailabilityServiceTask.
 * 
 * @author pabhishek
 */
@Service
public class AgentAvailabilityServiceTask {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentAvailabilityServiceTask.class );

    /** The agent business service. */
    @Autowired
    private AgentBusinessService agentBusinessService;

    /**
     * Handle start off duty.
     *
     * @param execution
     *            the execution
     */
    public void handleStartOffDuty( final DelegateExecution execution ) {
        LOGGER.info( "Inside handle start off duty" );
        agentBusinessService.processAgentOffDuty( execution.getId() );
    }

    /**
     * Handle end off duty.
     *
     * @param execution
     *            the execution
     */
    public void handleEndOffDuty( final DelegateExecution execution ) {
        LOGGER.info( "Inside handle end off duty" );
        agentBusinessService.processAgentOnDuty( execution.getId() );
    }
}
