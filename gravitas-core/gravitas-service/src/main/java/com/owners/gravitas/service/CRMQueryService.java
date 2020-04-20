package com.owners.gravitas.service;

import java.util.Map;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.ResourceAccessException;

import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.dto.crm.response.CRMResponse;

/**
 * Service for CRM query related operation.
 *
 *
 * @author vishwanathm
 *
 */
public interface CRMQueryService {
    /**
     * Find one.
     *
     * @param query
     *            the query
     * @param params
     *            the query params
     * @return the map
     */
    @Retryable( maxAttempts = 3, backoff = @Backoff( delay = 1000 ), include = { ResourceAccessException.class } )
    Map< String, Object > findOne( final String query, QueryParams params );

    /**
     * Find all.
     *
     * @param query
     *            the query
     * @param params
     *            the query params
     * @return the CRM response
     */
    @Retryable( maxAttempts = 3, backoff = @Backoff( delay = 1000 ), include = { ResourceAccessException.class } )
    CRMResponse findAll( final String query, QueryParams params );
}
