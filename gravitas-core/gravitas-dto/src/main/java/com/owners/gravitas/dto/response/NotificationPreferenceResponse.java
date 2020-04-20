package com.owners.gravitas.dto.response;

import java.util.List;

import com.owners.gravitas.dto.Preference;

/**
 * The NotificationPreferenceResponse class
 * 
 * @author ankusht
 *
 */
public class NotificationPreferenceResponse {

    /** The preferences */
    private List< Preference > preferences;

    /** The messages */
    private List< String > messages;

    /** The success */
    private boolean success;

    /**
     * Gets the preferences
     * 
     * @return
     */
    public List< Preference > getPreferences() {
        return preferences;
    }

    /**
     * Sets the preferences
     * 
     * @param preferences
     */
    public void setPreferences( final List< Preference > preferences ) {
        this.preferences = preferences;
    }

    /**
     * Gets the messages
     * 
     * @return
     */
    public List< String > getMessages() {
        return messages;
    }

    /**
     * Sets the messages
     * 
     * @param messages
     */
    public void setMessages( final List< String > messages ) {
        this.messages = messages;
    }

    /**
     * Checks isSuccess
     * 
     * @return
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the success
     * 
     * @param success
     */
    public void setSuccess( final boolean success ) {
        this.success = success;
    }
}
