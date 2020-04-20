package com.owners.gravitas.dao.impl;

import static com.owners.gravitas.util.RestUtil.buildRequest;
import static com.owners.gravitas.util.RestUtil.createHttpHeader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

import com.owners.gravitas.dao.OpportunityDataNodeDao;
import com.owners.gravitas.domain.OpportunityDataNode;
import com.owners.gravitas.dto.response.PostResponse;
/**
 * The OpportunityDataNodeDaoImpl
 * @author sandeepsoni
 *
 */
@Repository
public class OpportunityDataNodeDaoImpl  extends BaseDaoImpl implements OpportunityDataNodeDao {
	
	/** The Constant LOGGER. */
    private static final Logger logger = LoggerFactory.getLogger( OpportunityDataNodeDaoImpl.class );

	@Override
	public PostResponse addDataNode(OpportunityDataNode oppsDataNode, String agentId, String opportunityId,
			String dataNodeKey) {
			logger.debug( "Create OclReasonCode for agent id " + agentId );
	        final String reqUrl = buildFirebaseURL( "agents/" + agentId + "/opportunities/"+opportunityId+"/data/"+dataNodeKey);
	        return getRestTemplate().exchange( reqUrl, HttpMethod.PUT,
	                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), oppsDataNode ),
	                PostResponse.class ).getBody();
	}
	
	

	@Override
	public OpportunityDataNode getDataNode(String agentId, String opportunityId, String dataNodeKey) {
		String reqUrl = buildFirebaseURL( "agents/" + agentId + "/opportunities/"+opportunityId+"/data/"+dataNodeKey);
        return getRestTemplate().exchange( reqUrl, HttpMethod.GET,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), null ),
                OpportunityDataNode.class ).getBody();
	}

}
