package com.owners.gravitas.business;

import java.io.IOException;

import javax.mail.Message;
import javax.mail.MessagingException;

/**
 * Interface for ZillowHotlineLeadBusinessService
 * @author sandeepsoni
 *
 */
public interface ZillowHotlineLeadBusinessService {
	
	/**
     * Scrap zillow hotline lead mailbox message by reading the attachment
     * as csv file for lead details
     *
     * @param message
     *            the message
     * @return the lead response
	 * @throws IOException 
	 * @throws MessagingException 
     */
    void scrapeZillowHotlineLeadAccount( Message message ) throws MessagingException, IOException;

}
