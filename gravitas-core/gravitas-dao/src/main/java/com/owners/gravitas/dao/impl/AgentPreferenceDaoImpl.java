package com.owners.gravitas.dao.impl;

import static com.owners.gravitas.util.RestUtil.buildRequest;
import static com.owners.gravitas.util.RestUtil.createHttpHeader;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

import com.owners.gravitas.dao.AgentPreferenceDao;
import com.owners.gravitas.domain.AgentPreference;
import com.owners.gravitas.util.JsonUtil;

/**
 * 
 * @author gururasm
 *
 */
@Repository
public class AgentPreferenceDaoImpl extends BaseDaoImpl implements AgentPreferenceDao {

    private static final Logger LOGGER = LoggerFactory.getLogger( AgentPreferenceDaoImpl.class );

    @Override
    public AgentPreference saveAgentPreferences( final String agentId, final AgentPreference agentPreference ) {
        LOGGER.debug( "requested add agent preferences on firebase for agent : {} ", agentId );
        final String reqUrl = buildFirebaseURL( "agents/" + agentId + "/preferences" );
        return getRestTemplate().exchange( reqUrl, HttpMethod.PUT,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), agentPreference ),
                AgentPreference.class ).getBody();
    }

    @Override
    public Object saveAgentPreferencesData( final String fbPath, final Map< String, Object > agentPreference ) {
        LOGGER.info( "Adding agent preferences : {} in firebase for agent at {} ", JsonUtil.toJson( agentPreference ),
                fbPath );
        final String reqUrl = buildFirebaseURL( fbPath );
        return getRestTemplate().exchange( reqUrl, HttpMethod.PUT,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), agentPreference ),
                AgentPreference.class ).getBody();
    }
}
