package com.owners.gravitas.dto.response;

import java.util.List;

/**
 * The class AgentStateResponse
 * 
 * @author imranmoh
 *
 */
public class AgentStateResponse extends BaseResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 4008684469258404198L;

    private List< AgentStatesDetails > agentStatesDetails;

    /**
     * @return the agentStatesDetails
     */
    public List< AgentStatesDetails > getAgentStatesDetails() {
        return agentStatesDetails;
    }

    /**
     * @param agentStatesDetails
     *            the agentStatesDetails to set
     */
    public void setAgentStatesDetails( final List< AgentStatesDetails > agentStatesDetails ) {
        this.agentStatesDetails = agentStatesDetails;
    }
}
