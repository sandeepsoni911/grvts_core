package com.owners.gravitas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owners.gravitas.domain.entity.StateTimeZone;

/**
 * The Interface StateTimeZoneRepositiry.
 *
 * @author vishwanathm
 */
public interface StateTimeZoneRepositiry extends JpaRepository< StateTimeZone, String > {

    /**
     * Find by state code.
     *
     * @param stateCode
     *            the state code
     * @return the state time zone
     */
    StateTimeZone findByStateCode( final String stateCode );
}
