package com.owners.gravitas.dao.impl;

import static com.owners.gravitas.util.RestUtil.buildRequest;
import static com.owners.gravitas.util.RestUtil.createHttpHeader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

import com.owners.gravitas.dao.AgentInfoDao;
import com.owners.gravitas.domain.AgentInfo;

/**
 * The Class AgentInfoDaoImpl.
 *
 * @author amits
 */
@Repository
public class AgentInfoDaoImpl extends BaseDaoImpl implements AgentInfoDao {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentInfoDaoImpl.class );

    /**
     * Adds the device.
     *
     * @param agentId
     *            the agent id
     * @param agentInfo
     *            the agent info
     * @return the agent response
     */
    @Override
    public AgentInfo patchAgentInfo( final String agentId, final AgentInfo agentInfo ) {
        final String reqUrl = buildFirebaseURL( "agents/" + agentId + "/info" );
        LOGGER.info( "Adding agent info for agent {}, url:{}", agentId, reqUrl);
        return getRestTemplate().exchange( reqUrl, HttpMethod.PATCH,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), agentInfo ),
                AgentInfo.class ).getBody();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dao.AgentInfoDao#getAgentInfo(java.lang.String)
     */
    @Override
    public AgentInfo getAgentInfo( String agentId ) {
        final String reqUrl = buildFirebaseURL( "agents/" + agentId + "/info" );
        LOGGER.info( "Getting agent info for agent {}, url:{}", agentId, reqUrl);
        return getRestTemplate().exchange( reqUrl, HttpMethod.GET,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), null ),
                AgentInfo.class ).getBody();

    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dao.AgentInfoDao#patchAgentInfoPhone(java.lang.
     * String, java.lang.String)
     */
    @Override
    public String patchAgentInfoPhone( String agentId, String phone ) {
        final String reqUrl = buildFirebaseURL( "agents/" + agentId + "/info/phone" );
        LOGGER.info( "Adding agent phone info for agent {}, url:{}, phone:{}", agentId, reqUrl, phone);
        return getRestTemplate().exchange( reqUrl, HttpMethod.PATCH,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), phone ),
                String.class ).getBody();
    }
}
