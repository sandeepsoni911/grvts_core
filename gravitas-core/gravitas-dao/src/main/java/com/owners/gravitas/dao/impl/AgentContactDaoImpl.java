package com.owners.gravitas.dao.impl;

import static com.owners.gravitas.constants.Constants.AGENT_ID;
import static com.owners.gravitas.constants.Constants.CONTACT_ID;
import static com.owners.gravitas.constants.FirebaseQuery.GET_CONTACT_BY_ID;
import static com.owners.gravitas.util.RestUtil.buildRequest;
import static com.owners.gravitas.util.RestUtil.createHttpHeader;

import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

import com.owners.gravitas.dao.AgentContactDao;
import com.owners.gravitas.domain.Contact;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.dto.response.PostResponse;

/**
 * The Class AgentContactDaoImpl.
 *
 * @author harshads
 */
@Repository
public class AgentContactDaoImpl extends BaseDaoImpl implements AgentContactDao {

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dao.AgentContactDao#saveOpportunity(java.lang.String,
     * com.owners.gravitas.dto.Contact)
     */
    @Override
    public PostResponse saveContact( final String agentId, final Contact contact ) {
        final String reqUrl = buildFirebaseURL( "agents/" + agentId + "/contacts" );
        return getRestTemplate().exchange( reqUrl, HttpMethod.POST,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), contact ),
                PostResponse.class ).getBody();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dao.AgentContactDao#updateOpportunity(java.lang.
     * String,
     * java.lang.String, com.owners.gravitas.dto.Contact)
     */
    @Override
    public void updateContact( final String agentId, final String contactId, final Contact contact ) {
        final String reqUrl = buildFirebaseURL( "agents/" + agentId + "/contacts/" + contactId );
        getRestTemplate().exchange( reqUrl, HttpMethod.PUT,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), contact ),
                Contact.class ).getBody();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dao.AgentContactDao#updateContact(java.lang.String,
     * java.lang.String, java.util.Map)
     */
    @Override
    public void patchContact( final String agentId, final String contactId, final Map< String, Object > requestMap ) {
        final String reqUrl = buildFirebaseURL( "agents/" + agentId + "/contacts/" + contactId + "/" );
        getRestTemplate().exchange( reqUrl, HttpMethod.PATCH,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), requestMap ),
                String.class ).getBody();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dao.AgentContactDao#getContactById(java.lang.String,
     * java.lang.String)
     */
    @Override
    public Contact getContactById( final String agentId, final String contactId ) {
        final String reqUrl = buildFirebaseURL( "agents/" + agentId + "/contacts/" + contactId );
        return getRestTemplate().exchange( reqUrl, HttpMethod.GET,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), null ),
                Contact.class ).getBody();
    }
    /**
     * Gets the contact by opportunity id.
     *
     * @param opportunityId
     *            the opportunity id
     * @param agentId
     *            the agent id
     * @return the contact by opportunity id
     */
    @Override
    public Contact getContactByOpportunityId( final String contactId, final String agentId ) {
        final String reqUrl = getFirebaseHost() + GET_CONTACT_BY_ID;
        final QueryParams params = new QueryParams();
        params.add( CONTACT_ID, contactId );
        params.add( AGENT_ID, agentId );
        final Contact contact = getRestTemplate().exchange( reqUrl, HttpMethod.GET,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), null ),
                Contact.class, params.getParams() ).getBody();
        return contact;
    }
}
