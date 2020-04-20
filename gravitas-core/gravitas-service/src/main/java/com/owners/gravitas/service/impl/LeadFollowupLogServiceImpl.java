package com.owners.gravitas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.domain.entity.MarketingEmailLog;
import com.owners.gravitas.repository.MarketingEmailLogRepositiry;
import com.owners.gravitas.service.LeadFollowupLogService;

/**
 * The Class LeadFollowupLogServiceImpl.
 *
 * @author vishwanathm
 */
@Service
@Transactional( readOnly = true )
public class LeadFollowupLogServiceImpl implements LeadFollowupLogService {

    /** The marketing email log repositiry. */
    @Autowired
    private MarketingEmailLogRepositiry marketingEmailLogRepositiry;

    /**
     * Gets the marketing email log buy lead id.
     *
     * @param leadId
     *            the lead id
     * @return the marketing email log buy lead id
     */
    @Override
    public MarketingEmailLog getMarketingEmailLogBuyLeadId( final String leadId ) {
        return marketingEmailLogRepositiry.findByLeadId( leadId );
    }

    /**
     * Save marketing email log.
     *
     * @param marketingEmailLog
     *            the marketing email log
     * @return the marketing email log
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED )
    public MarketingEmailLog saveMarketingEmailLog( final MarketingEmailLog marketingEmailLog ) {
        return marketingEmailLogRepositiry.save( marketingEmailLog );
    }

    /**
     * Delete marketing email log.
     *
     * @param id
     *            the id
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED )
    public void deleteMarketingEmailLog( final MarketingEmailLog marketingEmailLog ) {
        marketingEmailLogRepositiry.delete( marketingEmailLog );
    }
}
