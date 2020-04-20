package com.owners.gravitas.dao.impl;

import static com.owners.gravitas.constants.Constants.AGENT_EMAIL;
import static com.owners.gravitas.constants.Constants.AGENT_ID;
import static com.owners.gravitas.constants.Constants.CONTACT_EMAIL;
import static com.owners.gravitas.constants.Constants.CONTACT_ID;
import static com.owners.gravitas.constants.Constants.CRM_OPPORTUNITY_ID;
import static com.owners.gravitas.constants.Constants.OPPORTUNITY_ID;
import static com.owners.gravitas.constants.FirebaseQuery.GET_SEARCH_BY_AGENT_EMAIL;
import static com.owners.gravitas.constants.FirebaseQuery.GET_SEARCH_BY_AGENT_ID;
import static com.owners.gravitas.constants.FirebaseQuery.GET_SEARCH_BY_CONTACT_EMAIL;
import static com.owners.gravitas.constants.FirebaseQuery.GET_SEARCH_BY_CONTACT_ID;
import static com.owners.gravitas.constants.FirebaseQuery.GET_SEARCH_BY_CRM_OPPORTUNITY_ID;
import static com.owners.gravitas.constants.FirebaseQuery.GET_SEARCH_BY_OPPORTUNITY_ID;
import static com.owners.gravitas.util.RestUtil.buildRequest;
import static com.owners.gravitas.util.RestUtil.createHttpHeader;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

import com.owners.gravitas.dao.SearchDao;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.util.EncoderUtil;

/**
 * The Class SearchDaoImpl.
 *
 * @author vishwanathm
 */
@Repository
public class SearchDaoImpl extends BaseDaoImpl implements SearchDao {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( SearchDaoImpl.class );

    /**
     * Search by agent id.
     *
     * @param agentId
     *            the agent id
     * @return the search
     */
    @Override
    public Search searchByAgentId( final String agentId ) {
        return getSearchResult( GET_SEARCH_BY_AGENT_ID, AGENT_ID, agentId );
    }

    /**
     * Save contact search.
     *
     * @param searchHolder
     *            the contact search holder
     */
    @Override
    public void saveSearch( final Search search ) {
        LOGGER.info( "Creating Search node for opportunity " + search.getCrmOpportunityId() + " agent "
                + search.getAgentId() );
        // Lower the case for agent & contact email for case insensitive search
        if (search.getAgentEmail() != null) {
            search.setAgentEmail( search.getAgentEmail().toLowerCase() );
        }
        if (search.getContactEmail() != null) {
            search.setContactEmail( search.getContactEmail().toLowerCase() );
        }
        final String reqUrl = buildFirebaseURL( "/search" );
        getRestTemplate().exchange( reqUrl, HttpMethod.POST,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), search ),
                Search.class ).getBody();
    }

    /**
     * Save all search.
     *
     * @param searchMap
     *            the search map
     */
    @Override
    public void saveSearches( final Map< String, Search > searchMap ) {
        final String reqUrl = buildFirebaseURL( "/search" );
        getRestTemplate().exchange( reqUrl, HttpMethod.PATCH,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), searchMap ),
                Search.class ).getBody();
    }

    /**
     * Search by opportunity id.
     *
     * @param opportunityId
     *            the opportunity id
     * @return the search
     */
    @Override
    public Search searchByCrmOpportunityId( final String opportunityId ) {
        return getSearchResult( GET_SEARCH_BY_CRM_OPPORTUNITY_ID, CRM_OPPORTUNITY_ID, opportunityId );
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
        return getSearchResult( GET_SEARCH_BY_OPPORTUNITY_ID, OPPORTUNITY_ID, opportunityId );
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
        Search search = null;
        try {
            final String encodedAgentEmail = EncoderUtil.getEncodedUrl( agentEmail );
            search = getSearchResult( GET_SEARCH_BY_AGENT_EMAIL, AGENT_EMAIL, encodedAgentEmail.toLowerCase() );
        } catch ( UnsupportedEncodingException e ) {
            LOGGER.debug( e.getMessage() );
        }
        return search;
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
        Search search = null;
        try {
            final String encodedContactEmail = EncoderUtil.getEncodedUrl( contactEmail );
            search = getSearchResult( GET_SEARCH_BY_CONTACT_EMAIL, CONTACT_EMAIL, encodedContactEmail.toLowerCase() );
        } catch ( UnsupportedEncodingException e ) {
            LOGGER.debug( e.getMessage() );
        }
        return search;
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
        return getSearchResult( GET_SEARCH_BY_CONTACT_ID, CONTACT_ID, contactId );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dao.SearchDao#updateSearch(com.owners.gravitas.domain
     * .Search)
     */
    @Override
    public void updateSearch( Search existingAgentSearch ) {
        LOGGER.info( "Update search node " + existingAgentSearch.getId() );
        final String reqUrl = buildFirebaseURL( "/search" );
        getRestTemplate().exchange( reqUrl, HttpMethod.PUT,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ),
                        existingAgentSearch ),
                Search.class ).getBody();
    }

    /**
     * Delete search.
     *
     * @param searchId
     *            the search id
     */
    @Override
    public void deleteSearch( final String searchId ) {
        LOGGER.info( "Delete search node " + searchId );
        final String reqUrl = buildFirebaseURL( "/search/" + searchId );
        getRestTemplate().exchange( reqUrl, HttpMethod.DELETE,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), null ),
                Object.class ).getBody();
    }

    /**
     * Gets the search result.
     *
     * @param queryStr
     *            the query str
     * @param paramName
     *            the param name
     * @param paramValue
     *            the param value
     * @return the search result
     */
    private Search getSearchResult( final String queryStr, final String paramName, final String paramValue ) {
        final String reqUrl = buildFirebaseQueryURL( queryStr );
        final QueryParams params = new QueryParams();
        params.add( paramName, paramValue );
        Search search = null;
        LOGGER.info("retriviging search for " + paramName + " value is " + paramValue + ", url : " + reqUrl);
        final Map< String, Map< String, String > > searchMap = getRestTemplate().exchange( reqUrl, HttpMethod.GET,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), null ), Map.class,
                params.getParams() ).getBody();
        if (searchMap != null && !searchMap.isEmpty()) {
            final Map< String, String > searchObject = searchMap.get( searchMap.keySet().toArray()[0] );
            search = new Search( String.valueOf( searchMap.keySet().toArray()[0] ), searchObject.get( "agentId" ),
                    searchObject.get( "agentEmail" ), searchObject.get( "contactId" ),
                    searchObject.get( "crmOpportunityId" ), searchObject.get( "contactEmail" ),
                    searchObject.get( "opportunityId" ) );
        }
        return search;
    }
}
