package com.owners.gravitas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class AuditTrailConfig.
 *
 * @author vishwanathm
 */
@ManagedResource( objectName = "com.owners.gravitas:name=AuditTrailConfig" )
@Component
public class AuditTrailConfig {

    /** The enable audit trail. */
    @Value( "${agent.autoAudit.logging.enabled: true}" )
    private boolean enableAutoAuditTrail;

    /** The property writer. */
    @Autowired
    private PropertyWriter propertyWriter;

    /**
     * Checks if is enable audit trail.
     *
     * @return true, if is enable audit trail
     */
    @ManagedAttribute
    public boolean isEnableAutoAuditTrail() {
        return enableAutoAuditTrail;
    }

    /**
     * Sets the enable audit trail.
     *
     * @param enableAutoAuditTrail
     *            the new enable audit trail
     */
    @ManagedAttribute
    public void setEnableAutoAuditTrail( final boolean enableAutoAuditTrail ) {
        this.enableAutoAuditTrail = enableAutoAuditTrail;
        propertyWriter.saveJmxProperty( "agent.autoAudit.logging.enabled", enableAutoAuditTrail );
    }
}
