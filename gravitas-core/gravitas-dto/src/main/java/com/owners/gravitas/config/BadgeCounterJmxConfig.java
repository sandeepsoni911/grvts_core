package com.owners.gravitas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.owners.gravitas.util.PropertyWriter;

@ManagedResource( objectName = "com.owners.gravitas:name=BadgeCounterJmxConfig" )
@Component
public class BadgeCounterJmxConfig {

    /** The opportunity badge count enabled. */
    @Value( "${opportunity.badgeCount.enabled: false}" )
    private boolean badgeCountEnabled;

    /** The property writer. */
    @Autowired
    private PropertyWriter propertyWriter;

    /**
     * Gets the opportunity badge count enabled.
     *
     * @return the badge counter enabled
     */
    @ManagedAttribute
    public boolean isBadgeCountEnabled() {
        return badgeCountEnabled;
    }

    /**
     * Sets the opportunity badge counter enabled.
     *
     * @param badgeCountEnabled
     *            the new badge counter enabled
     */
    @ManagedAttribute
    public void setBadgeCountEnabled( final boolean badgeCountEnabled ) {
        this.badgeCountEnabled = badgeCountEnabled;
        propertyWriter.saveJmxProperty( "opportunity.badgeCount.enabled", badgeCountEnabled );
    }

}
