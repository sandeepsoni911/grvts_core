package com.owners.gravitas.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.dao.SearchDao;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.service.SearchService;

/**
 * The Class SearchServiceImpl.
 *
 * @author vishwanathm
 */
@Service
public class SearchServiceImpl implements SearchService {

    /** The contact search dao. */
    @Autowired
    private SearchDao searchDao;

    /**
     * Save search.
     *
     * @param search
     *            the search
     */
    @Override
    public void save( final Search search ) {
        searchDao.saveSearch( search );
    }

    /**
     * Save agent contact search.
     *
     * @param agentId
     *            the agent id
     * @param allContactSearch
     *            the all contact search
     */
    public void saveAll( final Map< String, Search > searchMap ) {
        searchDao.saveSearches( searchMap );
    }

    /**
     * Search by crm opportunity id.
     *
     * @param opportunityId
     *            the opportunity id
     * @return the search
     */
    @Override
    public Search searchByCrmOpportunityId( final String opportunityId ) {
        return searchDao.searchByCrmOpportunityId( opportunityId );
    }

    /**
     * Search by opportunity id.
     *
     * @param opportunityId
     *            the opportunity id
     * @return the search
     */
    @Override
    public Search searchByOpportunityId( final String opportunityId ) {
        return searchDao.searchByOpportunityId( opportunityId );
    }

    /**
     * Search by agent email.
     *
     * @param agentEmail
     *            the agent email
     * @return the search
     */
    @Override
    public Search searchByAgentEmail( final String agentEmail ) {
        return searchDao.searchByAgentEmail( agentEmail );
    }

    /**
     * Search by contact email.
     *
     * @param contactEmail
     *            the contact email
     * @return the search
     */
    @Override
    public Search searchByContactEmail( final String contactEmail ) {
        return searchDao.searchByContactEmail( contactEmail );
    }

    /**
     * Search by agent id.
     *
     * @param agentId
     *            the agent id
     * @return the search
     */
    @Override
    public Search searchByAgentId( final String agentId ) {
        return searchDao.searchByAgentId( agentId );
    }

    /**
     * Search by crm contact id.
     *
     * @param contactId
     *            the contact id
     * @return the search
     */
    @Override
    public Search searchByContactId( final String contactId ) {
        return searchDao.searchByContactId( contactId );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.SearchService#updateSearch(com.owners.
     * gravitas.domain.Search)
     */
    @Override
    public void updateSearch( Search existingAgentSearch ) {
        searchDao.updateSearch( existingAgentSearch );
    }

    /**
     * Delete search.
     *
     * @param searchId
     *            the search id
     */
    @Override
    public void delete( String searchId ) {
        searchDao.deleteSearch( searchId );
    }
}
