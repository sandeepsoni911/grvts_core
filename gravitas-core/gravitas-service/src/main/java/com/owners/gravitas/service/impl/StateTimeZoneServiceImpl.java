package com.owners.gravitas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.domain.entity.StateTimeZone;
import com.owners.gravitas.repository.StateTimeZoneRepositiry;
import com.owners.gravitas.service.StateTimeZoneService;

/**
 * The Class StateTimeZoneServiceImpl.
 *
 * @author vishwanathm
 */
@Service
public class StateTimeZoneServiceImpl implements StateTimeZoneService{

    /** The time zone repositiry. */
    @Autowired
    private StateTimeZoneRepositiry stateTimeZoneRepositiry;

    /**
     * Gets the state time zone.
     *
     * @param stateCode
     *            the state code
     * @return the state time zone
     */
    @Override
    public StateTimeZone getStateTimeZone( final String stateCode ) {
        return stateTimeZoneRepositiry.findByStateCode( stateCode );
    }

}
