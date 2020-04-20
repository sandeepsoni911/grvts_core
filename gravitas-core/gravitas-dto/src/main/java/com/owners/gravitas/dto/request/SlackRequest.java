package com.owners.gravitas.dto.request;

import java.util.ArrayList;
import java.util.List;

import com.owners.gravitas.dto.SlackAttachment;

/**
 * The Class SlackRequest.
 *
 * @author amits
 */
public class SlackRequest {

    /** The username. */
    private String username;

    /** The text. */
    private String text;

    /** The attachments. */
    private List< SlackAttachment > attachments = new ArrayList<>();

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username
     *            the username to set
     */
    public void setUsername( final String username ) {
        this.username = username;
    }

    /**
     * Gets the text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text.
     *
     * @param text
     *            the text to set
     */
    public void setText( final String text ) {
        this.text = text;
    }

    /**
     * Gets the attachments.
     *
     * @return the attachments
     */
    public List< SlackAttachment > getAttachments() {
        return attachments;
    }

    /**
     * Sets the attachments.
     *
     * @param attachments
     *            the new attachments
     */
    public void setAttachments( final List< SlackAttachment > attachments ) {
        this.attachments = attachments;
    }

    /**
     * Adds the attachments.
     *
     * @param slackAttachment
     *            the slack attachment
     */
    public void addAttachments( final SlackAttachment slackAttachment ) {
        this.attachments.add( slackAttachment );
    }
}
