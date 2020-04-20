package com.owners.gravitas.service;

import com.owners.gravitas.domain.entity.MarketingEmailLog;

/**
 * The Interface LeadFollowupLogService.
 *
 * @author vishwanathm
 */
public interface LeadFollowupLogService {

    /**
     * Gets the marketing email log buy lead id.
     *
     * @param leadId
     *            the lead id
     * @return the marketing email log buy lead id
     */
    MarketingEmailLog getMarketingEmailLogBuyLeadId( String leadId );

    /**
     * Save marketing email log.
     *
     * @param marketingEmailLog
     *            the marketing email log
     * @return the marketing email log
     */
    MarketingEmailLog saveMarketingEmailLog( MarketingEmailLog marketingEmailLog );

    /**
     * Delete marketing email log.
     *
     * @param marketingEmailLog
     *            the marketing email log
     */
    void deleteMarketingEmailLog( MarketingEmailLog marketingEmailLog );

}
