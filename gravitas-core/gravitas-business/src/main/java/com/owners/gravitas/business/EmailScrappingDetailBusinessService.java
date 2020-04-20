package com.owners.gravitas.business;

import javax.mail.Message;

/**
 * Interface for EmailScrappingDetail
 * @author sandeepsoni
 *
 */
public interface EmailScrappingDetailBusinessService {
	

    /**
     * Scrap gravitas marathon mailbox message by reading the plain text content from email
     * message
     * and create save response details to mysql db.
     *
     * @param message
     *            the message
     * @return the lead response
     */
    void scrapeEmailDetails( Message message );

}
