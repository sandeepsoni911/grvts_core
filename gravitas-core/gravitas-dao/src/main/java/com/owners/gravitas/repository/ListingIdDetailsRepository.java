package com.owners.gravitas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.owners.gravitas.domain.entity.ListingIdDetails;

/**
 * The Interface ListingIdDetailsRepository.
 *
 * @author shivamm
 */
public interface ListingIdDetailsRepository extends JpaRepository< ListingIdDetails, String > {

    /**
     * Delete in bulk by opportunity id.
     *
     * @param opportunityId
     *            the opportunity id
     */
    @Modifying
    @Query( nativeQuery = true, value = "delete from GR_LISTING_OPPORTUNITY where OPPORTUNITY_ID =?1" )
    void deleteInBulkByOpportunityId( final String opportunityId );

}
