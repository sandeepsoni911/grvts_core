/**
 *
 */
package com.owners.gravitas.business.scheduler;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.owners.gravitas.business.task.AgentTaskHelper;
import com.owners.gravitas.domain.Agent;
import com.owners.gravitas.domain.Task;
import com.owners.gravitas.service.AgentService;

/**
 * The Class AgentTaskDeleteStatusJobExecutor.
 *
 * @author harshads
 */

@Component
@ManagedResource( objectName = "com.owners.gravitas:name=AgentTaskDeleteStatusJobExecutor" )
public class AgentTaskDeleteStatusJobExecutor {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentTaskDeleteStatusJobExecutor.class );

    /** The agent service. */
    @Autowired
    private AgentService agentService;

    /** The async task. */
    @Autowired
    private AgentTaskHelper agentTaskHelper;

    /**
     * Sync delete status.
     *
     * @param status
     *            the status
     * @throws InterruptedException
     *             the interrupted exception
     * @throws ExecutionException
     *             the execution exception
     */
    @ManagedOperation
    public void syncDeleteStatus() throws InterruptedException, ExecutionException {
        LOGGER.info( "syncDeleteStatus started -->" + System.currentTimeMillis() );
        Set< String > agents = agentService.getAllAgentIds();
        LOGGER.info( "agent ids are : {} , & total agents are {}", agents, agents.size() );
        Map< String, Agent > agentResponse = new ConcurrentHashMap<>();
        for ( String fbAgent : agents ) {
            final Agent agent = agentService.getAgentById( fbAgent );
            LOGGER.info( "Agent {}  got {} Opportunites ", fbAgent, agent.getOpportunities().size() );
            agentResponse.put( fbAgent, agent );
        }
        for ( Entry< String, Agent > entry : agentResponse.entrySet() ) {
            final Map< String, Task > tasks = entry.getValue().getTasks();
            if (null != tasks) {
                LOGGER.info( "Agent {} got  {} tasks", entry.getKey(), tasks.size() );
                for ( Entry< String, Task > taskEntry : tasks.entrySet() ) {
                    final Task fbTask = taskEntry.getValue();
                    if (fbTask.getDeleted()) {
                        LOGGER.info( "lets process task {} ", taskEntry.getKey() );
                        agentTaskHelper.updateTaskDeleteFlag( taskEntry.getKey(), fbTask );
                    }
                }
                LOGGER.info( "Agent {} processed successfully!! ", entry.getKey() );
            }
        }
    }
}
