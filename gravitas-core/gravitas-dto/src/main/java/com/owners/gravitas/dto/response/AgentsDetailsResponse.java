package com.owners.gravitas.dto.response;

import java.util.List;

import com.owners.gravitas.dto.Agent;

public class AgentsDetailsResponse extends BaseResponse {

    /**
     * The class AgentsDetailsResponse
     */
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4498382977838065430L;
    
    /** The list of agent. */
    private List< Agent > agentsDetails;
    
    /**
     * Gets the agents details.
     *
     * @return the agent details
     */
    public List< Agent > getAgentsDetails() {
        return agentsDetails;
    }
    
    /**
     * Sets the list of agent.
     *
     * @param agentsDetails
     *            the new agentsDetails
     */
    public void setAgentsDetails( List< Agent > agentsDetails ) {
        this.agentsDetails = agentsDetails;
    }
}
