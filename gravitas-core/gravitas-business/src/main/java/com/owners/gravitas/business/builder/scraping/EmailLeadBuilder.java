package com.owners.gravitas.business.builder.scraping;

import javax.mail.Message;

import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.dto.request.GenericLeadRequest;

/**
 * The Interface EmailLeadBuilder.
 *
 * @author vishwanathm
 */
public interface EmailLeadBuilder {

    /**
     * Convert to instance of {LeadRequest}.
     *
     * @param messageTextPart
     *            the message text part
     * @return the map
     */
    LeadRequest convertTo( String messageTextPart );

    /**
     * Convert to instance of {LeadRequest}.
     *
     * @param message
     *            the message
     * @return the map
     */
    GenericLeadRequest convertTo( Message message );
}
