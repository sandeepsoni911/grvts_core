package com.owners.gravitas.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.owners.gravitas.dto.Agent;

/**
 * The Class AgentsResponse.
 */
public class AgentsResponse extends BaseResponse {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -7009347820861864952L;

    /** The users. */
    @JsonProperty( value = "users" )
    private List< Agent > agents = new ArrayList<>();

    /**
     * Gets the agents.
     *
     * @return the agents
     */
    public List< Agent > getAgents() {
        return agents;
    }

    /**
     * Sets the agents.
     *
     * @param agents
     *            the agents to set
     */
    public void setAgents( final List< Agent > agents ) {
        this.agents = agents;
    }

}
