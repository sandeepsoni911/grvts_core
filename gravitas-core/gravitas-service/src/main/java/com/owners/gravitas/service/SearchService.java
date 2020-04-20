package com.owners.gravitas.service;

import java.util.Map;

import com.owners.gravitas.domain.Search;

/**
 * The Interface SearchService.
 *
 * @author vishwanathm
 */
public interface SearchService {

    /**
     * Save search.
     *
     * the contact search holder
     *
     * @param search
     *            the contact search
     */
    public void save( Search search );

    /**
     * Save search list.
     *
     * @param searchList
     *            the all contact search
     */
    void saveAll( final Map< String, Search > searchMap );

    /**
     * Search.
     *
     * @param crmId
     *            the crm id
     * @return the search
     */
    public Search searchByCrmOpportunityId( String crmId );

    /**
     * Search by agent email.
     *
     * @param agentEmail
     *            the agent email
     * @return the search
     */
    public Search searchByAgentEmail( String agentEmail );

    /**
     * Search by contact email.
     *
     * @param contactEmail
     *            the contact email
     * @return the search
     */
    public Search searchByContactEmail( String contactEmail );

    /**
     * Search by agent id.
     *
     * @param agentId
     *            the agent id
     * @return the search
     */
    Search searchByAgentId( String agentId );

    /**
     * Search by crm contact id.
     *
     * @param contactId
     *            the contact id
     * @return the search
     */
    public Search searchByContactId( String contactId );

    /**
     * Search by opportunity id.
     *
     * @param opportunityId
     *            the opportunity id
     * @return the search
     */
    Search searchByOpportunityId( String opportunityId );

    /**
     * Update search.
     *
     * @param existingAgentSearch
     *            the existing agent search
     */
    public void updateSearch( Search existingAgentSearch );

    /**
     * Delete search.
     *
     * @param searchId
     *            the search id
     */
    void delete( String searchId );

}
