package com.owners.gravitas.service;

import com.owners.gravitas.domain.AgentInfo;

/**
 * The Interface AgentInfoService.
 *
 * @author amits
 */
public interface AgentInfoService {

    /**
     * Gets the agent info.
     *
     * @param agentId
     *            the agent id
     * @return the agent info
     */
    AgentInfo getAgentInfo( String agentId );

    /**
     * Save agent info.
     *
     * @param agentId
     *            the agent id
     * @param agentInfo
     *            the agent info
     * @return the agent response
     */
    AgentInfo patchAgentInfo( String agentId, AgentInfo agentInfo );
}
