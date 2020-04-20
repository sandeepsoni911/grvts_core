package com.owners.gravitas.service;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;

/**
 * The Interface GmailService.
 */
public interface GmailService {

    /**
     * Send email.
     *
     * @param gmailService
     *            the gmail service
     * @param message
     *            the message
     * @return the string
     */
    String sendEmail( final Gmail gmailService, final Message message );

    /**
     * Gets the gmail service.
     *
     * @param agentEmail
     *            the agent email
     * @return the gmail service
     */
    Gmail getGmailService( final String agentEmail );

}
