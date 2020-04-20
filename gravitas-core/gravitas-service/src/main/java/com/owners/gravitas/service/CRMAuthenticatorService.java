package com.owners.gravitas.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.ResourceAccessException;

import com.owners.gravitas.dto.crm.response.CRMAccess;

/**
 * The Interface CRMAuthenticator.
 */
public interface CRMAuthenticatorService {

    /**
     * Gets the CRM access.
     *
     * @return the CRM access
     */
    @Retryable( maxAttempts = 3, backoff = @Backoff( delay = 1000 ), include = { ResourceAccessException.class } )
    CRMAccess authenticate();
}
