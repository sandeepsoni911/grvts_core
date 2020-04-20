package com.owners.gravitas.dao;

import java.util.Set;

import com.owners.gravitas.domain.Agent;
import com.owners.gravitas.domain.AgentHolder;
import com.owners.gravitas.domain.LastViewed;

/**
 * The Interface AgentDao.
 */
public interface AgentDao {

    /**
     * Save agent.
     *
     * @param agentHolder
     *            the agent holder
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
     */
    LastViewed updateLastViewed( String agentId, String id, String node );

    /**
     * Gets the all agents id.
     *
     * @return the all agents id
     */
    Set< String > getAllAgentIds();

    /**
     * Checks if is agent exist.
     *
     * @param agentId
     *            the agent id
     * @return true, if is agent exist
     */
    boolean isAgentExist( String agentId );

    /**
     * Gets the agent by id.
     *
     * @param agentId
     *            the agent id
     * @return the agent by id
     */
    Agent getAgentById( final String agentId );

    /**
     * Gets the agent email.
     *
     * @param agentId
     *            the agent id
     * @return the agent email
     */
    String getAgentEmailById( String agentId );
}
