package com.owners.gravitas.dao.impl;

import static com.owners.gravitas.util.RestUtil.buildRequest;
import static com.owners.gravitas.util.RestUtil.createHttpHeader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

import com.owners.gravitas.dao.AgentSuggestedPropertyDao;
import com.owners.gravitas.dto.request.AgentSuggestedPropertyRequest;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.util.JsonUtil;

/**
 * The Class AgentSuggestedPropertyDaoImpl.
 *
 * @author javeedsy
 */
@Repository
public class AgentSuggestedPropertyDaoImpl extends BaseDaoImpl implements AgentSuggestedPropertyDao {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentSuggestedPropertyDaoImpl.class );

    /**
     * Save agent's suggested property.
     *
     * @param suggestedProperty
     *            the suggestedProperty
     * @return the post response
     */
    @Override
    public PostResponse saveAgentSuggestedProperty( final AgentSuggestedPropertyRequest suggestedProperty,
            final String agentId ) {
        LOGGER.debug( "Adding suggested property in firebase for agent " + agentId + ". suggestedProperty = {}",
                JsonUtil.toJson( suggestedProperty ) );
        final String reqUrl = buildFirebaseURL( "agents/" + agentId + "/suggestedProperty" );
        return getRestTemplate().exchange( reqUrl, HttpMethod.POST,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ),
                        suggestedProperty ),
                PostResponse.class ).getBody();
    }
}
