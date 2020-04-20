package com.owners.gravitas.service.impl;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.domain.entity.OpportunityResponseLog;
import com.owners.gravitas.repository.OpportunityResponseLogRepository;
import com.owners.gravitas.service.OpportunityResponseLogService;

/**
 * The Class OpportunityResponseLogServiceImpl.
 *
 * @author raviz
 */
@Service
public class OpportunityResponseLogServiceImpl implements OpportunityResponseLogService {

    /** The opportunity response log repository. */
    @Autowired
    private OpportunityResponseLogRepository opportunityResponseLogRepository;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.OpportunityResponseLogService#save(com.owners
     * .gravitas.domain.entity.OpportunityResponseLog)
     */
    @Override
    @Transactional( propagation = REQUIRES_NEW )
    public OpportunityResponseLog save( final OpportunityResponseLog log ) {
        return opportunityResponseLogRepository.save( log );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.OpportunityResponseLogService#
     * getFailedOpportunities(java.lang.String)
     */
    @Override
    @Transactional( readOnly = true )
    public List< String > getFailedOpportunities( final String status ) {
        final List< String > oppIds = new ArrayList<>();
        final List< Object > failedOpportunities = opportunityResponseLogRepository.getFailedOpportunities(status);
        failedOpportunities.forEach( item -> oppIds.add( item.toString() ) );
        return oppIds;
    }

}
