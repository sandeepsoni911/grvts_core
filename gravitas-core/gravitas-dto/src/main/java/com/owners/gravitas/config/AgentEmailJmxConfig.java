package com.owners.gravitas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class for holding JMX properties related to agent email.
 *
 * @author ankusht
 */
@ManagedResource( objectName = "com.owners.gravitas:name=AgentEmailJmxConfig" )
@Component
public class AgentEmailJmxConfig {
    
    /** The property writer. */
    @Autowired
    private PropertyWriter propertyWriter;

    /** The agent email timeout. */
    @Value( "${agent.email.time.out}" )
    private int agentEmailTimeout;
    
    /**
     * Gets the agent email timeout.
     *
     * @return the agent email timeout
     */
    @ManagedAttribute
    public int getAgentEmailTimeout() {
        return agentEmailTimeout;
    }

    /**
     * Sets the agent email timeout.
     *
     * @param agentEmailTimeout
     *            the new agent email timeout
     */
    @ManagedAttribute
    public void setAgentEmailTimeout( int agentEmailTimeout ) {
        this.agentEmailTimeout = agentEmailTimeout;
        propertyWriter.saveJmxProperty( "agent.email.time.out", agentEmailTimeout );
    }

}
