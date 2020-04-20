package com.owners.gravitas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.repository.ListingIdDetailsRepository;
import com.owners.gravitas.service.ListingIdDetailsService;

/**
 * The Class ListingIdDetailsServiceImpl.
 *
 * @author shivamm
 */
@Service
@Transactional( readOnly = true )
public class ListingIdDetailsServiceImpl implements ListingIdDetailsService {

    @Autowired
    private ListingIdDetailsRepository listingIdDetailsRepository;

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.ListingIdDetailsService#
     * deleteInBulkByOpportunityCrmIdAndOpportunityDeleted(java.lang.String,
     * boolean)
     */
    @Override
    @Transactional
    public void delete( final String opportunityId ) {
        listingIdDetailsRepository.deleteInBulkByOpportunityId( opportunityId );
    }

}
