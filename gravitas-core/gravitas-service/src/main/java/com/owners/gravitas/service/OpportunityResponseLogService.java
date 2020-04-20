package com.owners.gravitas.service;

import java.util.List;

import com.owners.gravitas.domain.entity.OpportunityResponseLog;

/**
 * The Interface OpportunityResponseLogService.
 *
 * @author raviz
 */
public interface OpportunityResponseLogService {

    /**
     * Save.
     *
     * @param log
     *            the log
     * @return the opportunity response log
     */
    OpportunityResponseLog save( OpportunityResponseLog log );

    /**
     * Gets the failed opportunities.
     *
     * @param status
     *            the status
     * @return the failed opportunities
     */
    List< String > getFailedOpportunities( String status );

}
