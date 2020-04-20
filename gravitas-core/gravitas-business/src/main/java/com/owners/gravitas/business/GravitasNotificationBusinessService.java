package com.owners.gravitas.business;

import com.owners.gravitas.dto.GravitasHealthStatus;

/**
 * The Interface GravitasNotificationBusinessService.
 * 
 * @author ankusht
 */
public interface GravitasNotificationBusinessService {

    /**
     * Notify system error.
     *
     * @param gravitasHealthStatus
     *            the gravitas health status
     */
    void notifySystemError( final GravitasHealthStatus gravitasHealthStatus );
}
