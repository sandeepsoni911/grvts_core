/*
 *
 */
package com.owners.gravitas.service;

import com.owners.gravitas.dto.request.ReferralExchangeRequest;
import com.owners.gravitas.dto.response.ReferralExchangeResponse;

/**
 * The Interface ReferralExchangeService.
 *
 * @author vishwanathm
 */
public interface ReferralExchangeService {

    /**
     * Forward lead.
     *
     * @param request
     *            the request
     * @return the lead exchange response
     */
    ReferralExchangeResponse forwardRequest( ReferralExchangeRequest request );

}
