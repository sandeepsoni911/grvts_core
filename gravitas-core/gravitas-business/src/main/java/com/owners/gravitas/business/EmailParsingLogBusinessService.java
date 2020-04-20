package com.owners.gravitas.business;

import com.owners.gravitas.domain.entity.LeadEmailParsingLog;

/**
 * The Interface EmailParsingLogBusinessService.
 *
 * @author amits
 */
public interface EmailParsingLogBusinessService {

    /**
     * Save lead email parsing log.
     *
     * @param parsingLog
     *            the parsing log
     */
    void saveLeadEmailParsingLog( LeadEmailParsingLog parsingLog );
}
