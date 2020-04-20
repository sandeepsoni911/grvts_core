package com.owners.gravitas.service;

import com.owners.gravitas.domain.entity.StateTimeZone;

/**
 * The Interface StateTimeZoneService.
 *
 * @author vishwanathm
 */
public interface StateTimeZoneService {

    /**
     * Gets the state time zone.
     *
     * @param stateCode
     *            the state code
     * @return the state time zone
     */
    StateTimeZone getStateTimeZone( String stateCode );

}
