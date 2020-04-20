package com.owners.gravitas.service.impl;

import static com.owners.gravitas.constants.Constants.ACTION_OBJ;
import static com.owners.gravitas.constants.Constants.AGENT_ID;
import static com.owners.gravitas.enums.ActionEntity.AGENT_INFO;
import static com.owners.gravitas.enums.ActionType.UPDATE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.annotation.Audit;
import com.owners.gravitas.dao.AgentInfoDao;
import com.owners.gravitas.domain.AgentInfo;
import com.owners.gravitas.exception.AgentNotFoundException;
import com.owners.gravitas.service.AgentInfoService;

/**
 * The Class AgentInfoServiceImpl.
 *
 * @author amits
 */
@Service
public class AgentInfoServiceImpl implements AgentInfoService {

    /** The agent info dao. */
    @Autowired
    private AgentInfoDao agentInfoDao;

    /**
     * Save agent info.
     *
     * @param agentId
     *            the agent id
     * @param agentInfo
     *            the agent info
     * @return the agent response
     */
    @Override
    @Audit( type = UPDATE, entity = AGENT_INFO, args = { AGENT_ID, ACTION_OBJ } )
    public AgentInfo patchAgentInfo( final String agentId, final AgentInfo agentInfo ) {
        return agentInfoDao.patchAgentInfo( agentId, agentInfo );
    }

    /**
     * Gets the agent info.
     *
     * @param agentId
     *            the agent id
     * @return the agent info
     */

    @Override
    public AgentInfo getAgentInfo( final String agentId ) {
        AgentInfo agentInfo = agentInfoDao.getAgentInfo( agentId );
        if (agentInfo == null) {
            throw new AgentNotFoundException( "AgentInfo not found for agent id : " + agentId );
        }
        return agentInfo;
    }

}
