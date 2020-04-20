package com.owners.gravitas.dao;

import java.util.Map;

import com.owners.gravitas.domain.Search;

/**
 * The Interface SearchDao.
 */
public interface SearchDao {

    /**
     * Save search.
     *
     * @param search
     *            the search
     */
    void saveSearch( Search search );

    /**
     * Save all search.
     *
     * @param searchMap the search map
     */
    void saveSearches( Map< String, Search > searchMap );

    /**
     * Search by agent id.
     *
     * @param agentId
     *            the agent id
     * @return the search
     */
    Search searchByAgentId( String agentId );

    /**
     * Search by opportunity id.
     *
     * @param opportunityId
     *            the opportunity id
     * @return the search
     */
    Search searchByCrmOpportunityId( String opportunityId );

    /**
     * Search by agent email.
     *
     * @param agentEmail
     *            the agent email
     * @return the search
     */
    Search searchByAgentEmail( String agentEmail );

    /**
     * Search by contact email.
     *
     * @param contactEmail
     *            the contact email
     * @return the search
     */
    Search searchByContactEmail( String contactEmail );

    /**
     * Search by crm contact id.
     *
     * @param crmId
     *            the crm id
     * @return the search
     */
    Search searchByContactId( String crmId );

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
    void updateSearch( Search existingAgentSearch );

    /**
     * Delete search.
     *
     * @param searchId
     *            the search id
     */
    void deleteSearch( String searchId );

}
