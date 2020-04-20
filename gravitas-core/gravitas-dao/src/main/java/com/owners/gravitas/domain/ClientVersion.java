package com.owners.gravitas.domain;

/**
 * The Class ClientVersion.
 *
 * @author nishak
 */
public class ClientVersion {

    /** The minVersion. */
    private String minVersion;

    /** The minMessage. */
    private String minMessage;

    /** The currentVersion. */
    private String currentVersion;

    /** The currentMessage. */
    private String currentMessage;

    /** The download url. */
    private String url;

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
     * Gets the url.
     * 
     * @return the url
     */
    public final String getUrl() {
        return url;
    }

    /**
     * Sets the url.
     * 
     * @param url
     *            the url to set
     */
    public final void setUrl( final String url ) {
        this.url = url;
    }

}
