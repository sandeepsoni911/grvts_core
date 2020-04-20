package com.owners.gravitas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class SCurveConfig.
 * 
 * @author ankusht
 */
@Component
@ManagedResource( objectName = "com.owners.gravitas:name=SCurveConfig" )
public class SCurveConfig {

    /** The property writer. */
    @Autowired
    private PropertyWriter propertyWriter;

    /** The is S curve allocation enabled. */
    @Value( "${agent.lookup.SCurveAllocation.enabled:false}" )
    private boolean scurveAllocationEnabled;

    /** The s curve allocation markets. Must be separated by comma(,) */
    @Value( "${agent.lookup.SCurveAllocation.states:FL,GA}" )
    private String scurveAllocationStates;

    /**
     * Checks if is scurve allocation enabled.
     *
     * @return true, if is scurve allocation enabled
     */
    @ManagedAttribute
    public boolean isScurveAllocationEnabled() {
        return scurveAllocationEnabled;
    }

    /**
     * Sets the scurve allocation enabled.
     *
     * @param scurveAllocationEnabled
     *            the new scurve allocation enabled
     */
    @ManagedAttribute
    public void setScurveAllocationEnabled( final boolean scurveAllocationEnabled ) {
        this.scurveAllocationEnabled = scurveAllocationEnabled;
        propertyWriter.saveJmxProperty( "agent.lookup.SCurveAllocation.enabled", scurveAllocationEnabled );
    }

    /**
     * Gets the scurve allocation states.
     *
     * @return the scurve allocation states
     */
    @ManagedAttribute
    public String getScurveAllocationStates() {
        return scurveAllocationStates;
    }

    /**
     * Sets the scurve allocation states.
     *
     * @param scurveAllocationStates
     *            the new scurve allocation states
     */
    @ManagedAttribute
    public void setScurveAllocationStates( final String scurveAllocationStates ) {
        this.scurveAllocationStates = scurveAllocationStates;
        propertyWriter.saveJmxProperty( "agent.lookup.SCurveAllocation.states", scurveAllocationStates );
    }
}
