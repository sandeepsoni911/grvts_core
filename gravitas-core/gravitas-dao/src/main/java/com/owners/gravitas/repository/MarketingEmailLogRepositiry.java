package com.owners.gravitas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owners.gravitas.domain.entity.MarketingEmailLog;

/**
 * The Interface MarketingEmailLogRepositiry.
 *
 * @author vishwanathm
 */
public interface MarketingEmailLogRepositiry extends JpaRepository< MarketingEmailLog, String > {

    /**
     * Find by lead id.
     *
     * @param leadId
     *            the lead id
     * @return the marketing email log
     */
    public MarketingEmailLog findByLeadId( String leadId );
}
