package com.owners.gravitas.dao;

import com.owners.gravitas.domain.AgentInfo;

/**
 * The Interface AgentInfoDao.
 *
 * @author amits
 */
public interface AgentInfoDao {

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

    /**
     * Patch agent info phone.
     *
     * @param agentId
     *            the agent id
     * @param phone
     *            the phone
     * @return the string
     */
    String patchAgentInfoPhone( String agentId, String phone );

}
