package com.owners.gravitas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.owners.gravitas.domain.entity.OpportunityResponseLog;

/**
 * The Interface OpportunityResponseLogRepository.
 *
 * @author raviz
 */
public interface OpportunityResponseLogRepository extends JpaRepository< OpportunityResponseLog, String > {

    final static String FIND_FAILED_OPPORTUNITIES_ID = "SELECT log_outer.opp_fb_id FROM gr_opportunity_response_log log_outer INNER JOIN "
            + "(SELECT opp_fb_id, MAX(created_on) AS maxdate FROM gr_opportunity_response_log GROUP BY opp_fb_id) log_inner "
            + "ON (log_outer.opp_fb_id = log_inner.opp_fb_id AND log_outer.created_on = log_inner.maxdate) "
            + "WHERE log_outer.status like %:status%";

    /**
     * Gets the failed opportunities.
     *
     * @param status
     *            the status
     * @return the failed opportunities
     */
    @Query( nativeQuery = true, value = FIND_FAILED_OPPORTUNITIES_ID )
    List< Object > getFailedOpportunities( @Param( "status" ) String status );
}
