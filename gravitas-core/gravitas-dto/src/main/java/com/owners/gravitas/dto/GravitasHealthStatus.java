package com.owners.gravitas.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * The DTO GravitasHealthStatus which keeps health information of different
 * systems connecting with Gravitas.
 * 
 * @author ankusht
 */
public class GravitasHealthStatus {

    /** The system name. */
    private String systemName;

    /** The message. */
    private String message;

    /** The working. */
    private boolean working;

    /** The failure info. */
    private String failureInfo;

    /** The sub systems. */
    private List< GravitasHealthStatus > subSystems = new ArrayList<>();

    /**
     * Instantiates a new gravitas health status.
     *
     * @param systemName
     *            the system name
     * @param message
     *            the message
     * @param working
     *            the working
     * @param failureInfo
     *            the failure info
     */
    public GravitasHealthStatus( final String systemName, final String message, final boolean working,
            final String failureInfo ) {
        super();
        this.systemName = systemName;
        this.message = message;
        this.working = working;
        this.failureInfo = failureInfo;
    }

    /**
     * Gets the system name.
     *
     * @return the system name
     */
    public String getSystemName() {
        return systemName;
    }

    /**
     * Sets the system name.
     *
     * @param systemName
     *            the new system name
     */
    public void setSystemName( final String systemName ) {
        this.systemName = systemName;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     *
     * @param message
     *            the new message
     */
    public void setMessage( final String message ) {
        this.message = message;
    }

    /**
     * Checks if is working.
     *
     * @return true, if is working
     */
    public boolean isWorking() {
        return working;
    }

    /**
     * Sets the working.
     *
     * @param working
     *            the new working
     */
    public void setWorking( final boolean working ) {
        this.working = working;
    }

    /**
     * Gets the failure info.
     *
     * @return the failure info
     */
    public String getFailureInfo() {
        return failureInfo;
    }

    /**
     * Sets the failure info.
     *
     * @param failureInfo
     *            the new failure info
     */
    public void setFailureInfo( final String failureInfo ) {
        this.failureInfo = failureInfo;
    }

    /**
     * Gets the sub systems.
     *
     * @return the sub systems
     */
    public List< GravitasHealthStatus > getSubSystems() {
        return subSystems;
    }

    /**
     * Sets the sub systems.
     *
     * @param subSystems
     *            the new sub systems
     */
    public void setSubSystems( List< GravitasHealthStatus > subSystems ) {
        this.subSystems = subSystems;
    }
}
