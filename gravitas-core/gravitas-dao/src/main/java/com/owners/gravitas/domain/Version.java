package com.owners.gravitas.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class Version.
 *
 * @author nishak
 */
public class Version {

    /** The minVersion. */
    private String minVersion;

    /** The minMessage. */
    private String minMessage;

    /** The currentVersion. */
    private String currentVersion;

    /** The currentMessage. */
    private String currentMessage;

    /** The ios. */
    private Map< String, Object > ios = new HashMap<>();

    /** The android. */
    private Map< String, Object > android = new HashMap<>();

    /**
     * Gets the minVersion.
     * 
     * @return the minVersion
     */
    public String getMinVersion() {
        return minVersion;
    }

    /**
     * Sets the minVersion.
     * 
     * @param minVersion
     *            the minVersion to set
     */
    public void setMinVersion( final String minVersion ) {
        this.minVersion = minVersion;
    }

    /**
     * Gets the minMessage.
     * 
     * @return the minMessage
     */
    public String getMinMessage() {
        return minMessage;
    }

    /**
     * Sets the minMessage.
     * 
     * @param minMessage
     *            the minMessage to set
     */
    public void setMinMessage( final String minMessage ) {
        this.minMessage = minMessage;
    }

    /**
     * Gets the currentVersion.
     * 
     * @return the currentVersion
     */
    public String getCurrentVersion() {
        return currentVersion;
    }

    /**
     * Sets the currentVersion.
     * 
     * @param currentVersion
     *            the currentVersion to set
     */
    public void setCurrentVersion( final String currentVersion ) {
        this.currentVersion = currentVersion;
    }

    /**
     * Gets the currentMessage.
     * 
     * @return the currentMessage
     */
    public String getCurrentMessage() {
        return currentMessage;
    }

    /**
     * Sets the currentMessage.
     * 
     * @param currentMessage
     *            the currentMessage to set
     */
    public void setCurrentMessage( final String currentMessage ) {
        this.currentMessage = currentMessage;
    }

    /**
     * Gets the ios.
     * 
     * @return the ios
     */
    public Map< String, Object > getIos() {
        return ios;
    }

    /**
     * Sets the ios.
     * 
     * @param ios
     *            the ios to set
     */
    public void setIos( final Map< String, Object > ios ) {
        this.ios = ios;
    }

    /**
     * Gets the android.
     * 
     * @return the android
     */
    public Map< String, Object > getAndroid() {
        return android;
    }

    /**
     * Sets the android.
     * 
     * @param android
     *            the android to set
     */
    public void setAndroid( final Map< String, Object > android ) {
        this.android = android;
    }

}
