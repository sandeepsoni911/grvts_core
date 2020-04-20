package com.owners.gravitas.dao.impl;

import static com.owners.gravitas.util.RestUtil.buildRequest;
import static com.owners.gravitas.util.RestUtil.createHttpHeader;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

import com.owners.gravitas.dao.ActionGroupDao;
import com.owners.gravitas.domain.Action;
import com.owners.gravitas.domain.ActionGroup;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.repository.ActionGroupRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class AgentActionGroupDaoImpl.
 *
 * @author shivamm
 */
@Repository
public class ActionGroupDaoImpl extends BaseDaoImpl implements ActionGroupDao {

    /** The action group repository. */
    @Autowired
    private ActionGroupRepository actionGroupRepository;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dao.AgentActionGroupDao#getActionGroup(java.lang.
     * String)
     */
    @Override
    public com.owners.gravitas.domain.entity.ActionGroup getActionGroup() {
        // currently there is only one action group so we are using the first
        // record
        return findAll().iterator().next();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dao.ActionGroupDao#findAll()
     */
    @Override
    public List< com.owners.gravitas.domain.entity.ActionGroup > findAll() {
        return actionGroupRepository.findAll();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dao.AgentOpportunityDao#createActionGroup(com.owners.
     * gravitas.domain.ActionGroup)
     */
    @Override
    public PostResponse createActionGroup( final String agentId, final ActionGroup actionGroup ) {
        final String reqUrl = buildFirebaseURL( "agents/" + agentId + "/actionFlow" );
        return getRestTemplate().exchange( reqUrl, HttpMethod.POST,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), actionGroup ),
                PostResponse.class ).getBody();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dao.ActionGroupDao#getActionInfo(java.lang.String,
     * java.lang.String, java.lang.String)
     */
    @Override
    public Action getAction( String agentId, String actionGroupId, String actionId ) {
        String reqUrl = buildFirebaseURL(
                "agents/" + agentId + "/actionFlow/" + actionGroupId + "/actions/" + actionId );
        return getRestTemplate().exchange( reqUrl, HttpMethod.GET,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), null ),
                Action.class ).getBody();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dao.ActionGroupDao#getAction(java.lang.String,
     * java.lang.String, java.lang.String)
     */
    @Override
    public ActionGroup getActionGroup( String agentId, String actionGroupId ) {
        String reqUrl = buildFirebaseURL( "agents/" + agentId + "/actionFlow/" + actionGroupId );
        return getRestTemplate().exchange( reqUrl, HttpMethod.GET,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), null ),
                ActionGroup.class ).getBody();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dao.ActionGroupDao#patchAgentActionInfo(java.lang.
     * String, java.lang.String, java.lang.String,
     * com.owners.gravitas.domain.Action)
     */
    @Override
    public void patchAction( final String agentId, final String actionGroupId, final String actionId,
            final Map< String, Object > actionMap ) {
        final String reqUrl = buildFirebaseURL(
                "agents/" + agentId + "/actionFlow/" + actionGroupId + "/actions/" + actionId );
        getRestTemplate().exchange( reqUrl, HttpMethod.PATCH,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), actionMap ),
                Action.class ).getBody();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dao.ActionGroupDao#getStartTime(java.lang.String,
     * java.lang.String)
     */
    @Override
    public String getStartTime( String agentId, String actionGroupId ) {
        String reqUrl = buildFirebaseURL( "agents/" + agentId + "/actionFlow/" + actionGroupId + "/startDtm" );
        return getRestTemplate().exchange( reqUrl, HttpMethod.GET,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), null ),
                String.class ).getBody();
    }

    /**
     * Patch action flow.
     *
     * @param agentId
     *            the agent id
     * @param actionGroupId
     *            the action group id
     * @param data
     *            the data
     */
    @Override
    public void patchActionGroup( final String agentId, final String actionGroupId, final Map< String, Object > data ) {
        final String reqUrl = buildFirebaseURL( "agents/" + agentId + "/actionFlow/" + actionGroupId );
        getRestTemplate().exchange( reqUrl, HttpMethod.PATCH,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ), data ),
                ActionGroup.class ).getBody();
    }
}
