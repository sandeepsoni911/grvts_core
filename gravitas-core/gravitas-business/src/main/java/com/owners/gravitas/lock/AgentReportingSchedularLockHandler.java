package com.owners.gravitas.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class AgentReportingSchedularLockHandler manages the distributed agent
 * reporting scheduler services lock management.
 *
 * @author raviz
 */
@ManagedResource( objectName = "com.owners.gravitas:name=AgentReportingSchedularLockHandler" )
@Component
public class AgentReportingSchedularLockHandler extends SyncLockHandler {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentReportingSchedularLockHandler.class );

    /** The property writer. */
    @Autowired
    private PropertyWriter propertyWriter;

    /** The use lock. */
    @Value( "${agent.reporting.scheduler.useLock: true}" )
    private boolean useLock;

    /**
     * Acquire lock.
     *
     * @param lockName
     *            the lock name
     * @return true, if successful
     */
    public boolean acquireLock( String lockName ) {
        LOGGER.debug( "AgentReportingSchedularLockHandler acquiring lock for:" + lockName );
        boolean acquireLock = !useLock;
        if (useLock) {
            acquireLock = super.acquireLock( lockName );
        }
        return acquireLock;
    }

    /**
     * Gets the lock prefix.
     *
     * @return the lock prefix
     */
    protected String getLockPrefix() {
        return super.getLockPrefix() + "AGNT-REPORTING-";
    }

    /**
     * Checks if is use lock.
     *
     * @return true, if is use lock
     */
    @ManagedAttribute
    public boolean isUseLock() {
        return useLock;
    }

    /**
     * Sets the use lock.
     *
     * @param useLock
     *            the new use lock
     */
    @ManagedAttribute
    public void setUseLock( boolean useLock ) {
        this.useLock = useLock;
        propertyWriter.saveJmxProperty( "agent.reporting.scheduler.useLock", useLock );
    }
}
