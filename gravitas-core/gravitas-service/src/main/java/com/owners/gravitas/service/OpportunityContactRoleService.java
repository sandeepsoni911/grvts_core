package com.owners.gravitas.service;

import com.owners.gravitas.dto.crm.request.CRMOpportunityContactRoleRequest;
import com.owners.gravitas.dto.crm.response.OpportunityContactRoleResponse;

/**
 * The Interface OpportunityContactRoleService.
 *
 * @author amits
 */
public interface OpportunityContactRoleService {

    /**
     * Creates the opportunity contact role.
     *
     * @param crmOpportunityContactRole
     *            the crm opportunity contact role
     * @return the opportunity contact role response
     */
    public OpportunityContactRoleResponse createOpportunityContactRole(
            CRMOpportunityContactRoleRequest crmOpportunityContactRoleRequest );
}
