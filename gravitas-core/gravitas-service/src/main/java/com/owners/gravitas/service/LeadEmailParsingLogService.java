package com.owners.gravitas.service;

import com.owners.gravitas.domain.entity.LeadEmailParsingLog;

/**
 * The Interface LeadEmailParsingLogService.
 *
 * @author amits
 */
public interface LeadEmailParsingLogService {

    /**
     * Save.
     *
     * @param parsingLog
     *            the parsing log
     * @return the lead email parsing log
     */
    LeadEmailParsingLog save( LeadEmailParsingLog parsingLog );
}
