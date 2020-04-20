package com.owners.gravitas.service;

import com.owners.gravitas.domain.Search;
import com.owners.gravitas.dto.crm.response.CRMResponse;
import com.owners.gravitas.enums.RecordType;

/**
 * The Interface DeDuplicationService.
 */
public interface DeDuplicationService {

    /**
     * De duplicate lead.
     *
     * @param email
     *            the email
     * @return the CRM response
     */
    CRMResponse deDuplicateLead( String email );

    /**
     * De duplicate generic lead.
     *
     * @param email
     *            the email
     * @param type
     *            the type
     * @return the CRM response
     */
    CRMResponse getDeDuplicatedLead( String email, RecordType type );

    /**
     * Gets the search by email.
     *
     * @param email
     *            the email
     * @return the search by email
     */
    Search getSearchByContactEmail( String contactEmail );

}
