package com.owners.gravitas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class ActionFlowConfig.
 *
 * @author shivamm
 */
@ManagedResource( objectName = "com.owners.gravitas:name=ActionFlowConfig" )
@Component
public class ActionFlowConfig {

    /** The opportunity referral enabled. */
    @Value( "${scripted.calls.enabled:true}" )
    private boolean scriptedCallsEnabled;

    /** The scripted calls bucket. */
    @Value( "${scripted.calls.bucket:}" )
    private String scriptedCallsBucket;

    /** The property writer. */
    @Autowired
    private PropertyWriter propertyWriter;

    /**
     * Checks if is scripted calls enabled.
     *
     * @return true, if is scripted calls enabled
     */
    @ManagedAttribute
    public boolean isScriptedCallsEnabled() {
        return scriptedCallsEnabled;
    }

    /**
     * Sets the scripted calls enabled.
     *
     * @param scriptedCallsEnabled
     *            the new scripted calls enabled
     */
    @ManagedAttribute( description = "scripted calls enabled property" )
    public void setScriptedCallsEnabled( final boolean scriptedCallsEnabled ) {
        this.scriptedCallsEnabled = scriptedCallsEnabled;
        propertyWriter.saveJmxProperty( "scripted.calls.enabled", scriptedCallsEnabled );
    }

    /**
     * Gets the scripted calls bucket.
     *
     * @return the scripted calls bucket
     */
    @ManagedAttribute
    public String getScriptedCallsBucket() {
        return scriptedCallsBucket;
    }

    /**
     * Sets the scripted calls bucket.
     *
     * @param scriptedCallsBucket
     *            the new scripted calls bucket
     */
    @ManagedAttribute( description = "scripted calls bucket property" )
    public void setScriptedCallsBucket( final String scriptedCallsBucket ) {
        this.scriptedCallsBucket = scriptedCallsBucket;
        propertyWriter.saveJmxProperty( "scripted.calls.bucket", scriptedCallsBucket );
    }

}
