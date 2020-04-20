package com.owners.gravitas.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.ResourceAccessException;

import com.google.api.services.bigquery.model.TableDataInsertAllRequest.Rows;
import com.owners.gravitas.dao.dto.CbsaAgentDetail;
import com.owners.gravitas.domain.Agent;
import com.owners.gravitas.domain.AgentHolder;
import com.owners.gravitas.domain.LastViewed;
import com.owners.gravitas.domain.entity.AgentDetailsV1;
import com.owners.gravitas.dto.crm.request.CRMAgentRequest;
import com.owners.gravitas.dto.crm.response.CRMAgentResponse;

/**
 * The Interface AgentService.
 */
public interface AgentService {

    /**
     * Save agent.
     *
     * @param agentHolder
     *            the agent holder
     * @return the agent
     */
    Agent saveAgent( AgentHolder agentHolder );

    /**
     * Delete agent.
     *
     * @param agentId
     *            the agent id
     * @return the agent
     */
    Agent deleteAgent( String agentId );

    /**
     * Update last viewed.
     *
     * @param agentId
     *            the agent id
     * @param id
     *            the id
     * @param node
     *            the node
     * @return the last viewed
     */
    public LastViewed updateLastViewed( String agentId, String id, String node );

    /**
     * Gets the all agents id.
     *
     * @return the all agents id
     */
    Set< String > getAllAgentIds();

    /**
     * Gets the agent by id.
     *
     * @param agentId
     *            the agent id
     * @return the agent by id
     */
    Agent getAgentById( String agentId );

    /**
     * Gets the all mapped agent ids.
     *
     * @return the all mapped agent ids
     */
    Map< String, String > getAllMappedAgentIds();

    /**
     * Gets the agent email by id.
     *
     * @param agentId
     *            the agent id
     * @return the agent email by id
     */
    String getAgentEmailById( String agentId );

    /**
     * Save agent uid mapping.
     *
     * @param rowsList
     *            the rows list
     */
    void saveAgentUidMapping( List< Rows > rowsList );

    /**
     * Find crm agent availablity by id.
     *
     * @param crmAgentId
     *            the crm agent id
     * @return true, if agent is available
     */
    boolean isCrmAgentAvailable( String crmAgentId );

    /**
     * Find active agents for zipcode.
     *
     * @param zipcode
     *            the zipcode
     * @return the list
     */
    List< AgentDetailsV1 > findActiveAgentsByZipcode( String zipcode );

    /**
     * Gets the crm agent details.
     *
     * @param email
     *            the email
     * @return the crm agent details
     */
    Map< String, Object > getCrmAgentDetails( final String email );

    /**
     * Creates the crm agent.
     *
     * @param crmAgent
     *            the crm agent
     * @return the CRM agent response
     */
    @Retryable( maxAttempts = 3, backoff = @Backoff( delay = 1000 ), include = { ResourceAccessException.class } )
    CRMAgentResponse createCRMAgent( CRMAgentRequest crmAgent );

    /**
     * Update CRM agent.
     *
     * @param crmAgent
     *            the crm agent
     * @return the CRM agent response
     */
    @Retryable( maxAttempts = 3, backoff = @Backoff( delay = 1000 ), include = { ResourceAccessException.class } )
    CRMAgentResponse updateCRMAgent( CRMAgentRequest crmAgent );

    /**
     * Save opportunity mapping.
     *
     * @param rowsList
     *            the rows list
     */
    void saveOpportunityMapping( List< Rows > rowsList );

    /**
     * Delete crm agent.
     *
     * @param agentId
     *            the agent id
     */
    void deleteCRMAgent( String agentId );

    /**
     * Gets the CRM agent id by email.
     *
     * @param email
     *            the email
     * @return the CRM agent id by email
     */
    String getCRMAgentIdByEmail( String email );

    /**
     * Patch CRM agent.
     *
     * @param agentRequest
     *            the agent request
     * @param agentId
     *            the agent id
     */
    void patchCRMAgent( Map< String, Object > agentRequest, String agentId );

    /**
     * Gets the agent details.
     *
     * @param email
     *            the email
     * @return the agent details
     */
    Map< String, Object > getAgentDetails( String email );

    /**
     * Update agent score.
     */
    void syncAgentScore();

    /**
     * Gets the CRM agent.
     *
     * @param agentId
     *            the agent id
     * @return the CRM agent
     */
    CRMAgentResponse getCRMAgentById( String agentId );

    /**
     * Find agents by zips.
     *
     * @param zips
     *            the zips
     * @return the list
     */
    List< Object[] > findZipAndAgentsByZip( String zips );

    /**
     * Find agent by crm opportunity id.
     *
     * @param crmOppId
     *            the crm opp id
     * @return the string
     */
    String findAgentByCrmOpportunityId( String crmOppId );

    /**
     * Gets the cbsa agent details.
     *
     * @param emails
     *            the emails
     * @return the cbsa agent details
     */
    List< CbsaAgentDetail > getCbsaAgentDetails( Collection< String > emails );

    /**
     * Find by zip and agent emails.
     *
     * @param zip
     *            the zip
     * @param emails
     *            the emails
     * @return the list
     */
    List< Object[] > findByZipAndAgentEmails( String zip, Collection< String > emails );

    /**
     * Get agent first and last name from email id
     * 
     * @param agentEmailId
     * @return
     */
    Map< String, String > getAgentFirstAndLastName( String agentEmailId );
}
