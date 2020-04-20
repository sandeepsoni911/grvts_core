package com.owners.gravitas.gateway;

import org.springframework.integration.annotation.Gateway;

import com.owners.gravitas.amqp.AgentSource;

/**
 * The Interface AgentCreateMessageGateway.
 *
 * @author vishwanathm
 */
public interface AgentCreateMessageGateway {

    /**
     * Publish agent create.
     *
     * @param agentSource
     *            the agent source
     */
    @Gateway( requestChannel = "agent.create" )
    void publishAgentCreate( final AgentSource agentSource );

    /**
     * Publish agent update.
     *
     * @param agentSource
     *            the agent source
     */
    @Gateway( requestChannel = "agent.update" )
    void publishAgentUpdate( final AgentSource agentSource );
}
