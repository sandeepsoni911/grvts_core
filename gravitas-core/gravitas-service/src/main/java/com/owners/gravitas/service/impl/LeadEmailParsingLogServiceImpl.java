package com.owners.gravitas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.domain.entity.LeadEmailParsingLog;
import com.owners.gravitas.repository.LeadEmailParsingLogRepository;
import com.owners.gravitas.service.LeadEmailParsingLogService;

/**
 * The Class LeadEmailParsingLogServiceImpl.
 *
 * @author amits
 */
@Service
public class LeadEmailParsingLogServiceImpl implements LeadEmailParsingLogService {

    /** The lead email parsing log repository. */
    @Autowired
    private LeadEmailParsingLogRepository leadEmailParsingLogRepository;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.ActionLogService#save(com.owners.gravitas.
     * domain.entity.LeadEmailParsingLog)
     */
    @Override
    public LeadEmailParsingLog save( LeadEmailParsingLog parsingLog ) {
        return leadEmailParsingLogRepository.save( parsingLog );
    }

}
