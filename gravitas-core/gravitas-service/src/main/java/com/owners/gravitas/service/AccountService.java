package com.owners.gravitas.service;

import com.owners.gravitas.dto.crm.request.CRMAccountRequest;
import com.owners.gravitas.dto.crm.response.AccountResponse;

/**
 * The Interface AccountService.
 *
 * @author vishwanathm
 */
public interface AccountService {

    /**
     * Creates the account.
     *
     * @param crmAccountRequest
     *            the account
     * @return the string
     */
    AccountResponse createAccount( final CRMAccountRequest crmAccountRequest );

    /**
     * Delete CRM account.
     *
     * @param accountId
     *            the account id
     */
    void deleteCRMAccount( final String accountId );

    /**
     * Find account id by opportunity id.
     *
     * @param crmId
     *            the crm id
     * @return the string
     */
    String findAccountIdByOpportunityId( final String crmId );

    /**
     * Gets the account id by email.
     *
     * @param emailId
     *            the email id
     * @return the account id by email
     */
    String getAccountIdByEmail( final String emailId );

    /**
     * Find account name by id.
     *
     * @param accountId
     *            the account id
     * @return the string
     */
    String findAccountNameById( final String accountId );
}
