package com.owners.gravitas.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.business.EmailParsingLogBusinessService;
import com.owners.gravitas.domain.entity.LeadEmailParsingLog;
import com.owners.gravitas.service.LeadEmailParsingLogService;

/**
 * The Class EmailParsingLogBusinessServiceImpl.
 *
 * @author amits
 */
@Service( "emailParsingLogBusinessServiceImpl" )
@Transactional
public class EmailParsingLogBusinessServiceImpl implements EmailParsingLogBusinessService {
    /** The action log service. */
    @Autowired
    private LeadEmailParsingLogService leadEmailParsingLogService;

    /**
     * Save lead email parsing log.
     *
     * @param parsingLog
     *            the parsing log
     */
    @Override
    public void saveLeadEmailParsingLog( final LeadEmailParsingLog parsingLog ) {
        leadEmailParsingLogService.save( parsingLog );
    }
}
