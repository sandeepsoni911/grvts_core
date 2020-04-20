package com.owners.gravitas.business;

import javax.mail.Message;

/**
 * The Interface OclEmailBusinessService.
 *
 * @author ankusht
 */
public interface OclEmailBusinessService {

	/**
	 * Scrape OCL email message.
	 * <ul>
	 * <li>If an opportunity with given loan number is found then update that
	 * opportunity</li>
	 * <li>Else if a lead with given email id is found then convert that lead
	 * into an opportunity and then update that opportunity</li>
	 * <li>Else if create a lead with given email id, convert that lead into an
	 * opportunity and then update that opportunity</li>
	 * </ul>
	 *
	 * @param message
	 *            the message
	 */
	void scrapeOclEmailMessage( Message message );
}
