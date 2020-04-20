package com.owners.gravitas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class AgentTopicJmxConfig.
 *
 * @author raviz
 */
@ManagedResource( objectName = "com.owners.gravitas:name=AgentTopicJmxConfig" )
@Component
public class AgentTopicJmxConfig {

    /** The property writer. */
    @Autowired
    private PropertyWriter propertyWriter;

    /**
     * The enable agent sync.If set to true then it allow gravitas to sync
     * newly created agents on salesforce to database.
     */
    @Value( "${agent.topic.dbSync.enabled: true}" )
    private boolean enableAgentDbSync;

    /**
     * Checks if is enable agent sync.
     *
     * @return true, if is enable agent sync
     */
    @ManagedAttribute
    public boolean isEnableAgentDbSync() {
        return enableAgentDbSync;
    }

    /**
     * Sets the enable agent sync. If set to true then it allow gravitas to sync
     * newly created agents on salesforce to database.
     *
     * @param enableAgentSync
     *            the new enable agent sync
     */
    @ManagedAttribute
    public void setEnableAgentDbSync( final boolean enableAgentDbSync ) {
        this.enableAgentDbSync = enableAgentDbSync;
        propertyWriter.saveJmxProperty( "agent.topic.dbSync.enabled", enableAgentDbSync );
    }

}
