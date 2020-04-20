package com.owners.gravitas.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties( ignoreUnknown = true )
public class ZillowAgentDetails {

    /** The agent name. */
    String agentName;

    /** The state. */
    String state;

    /**
     * Gets the agent name.
     *
     * @return the agent name
     */
    public String getAgentName() {
        return agentName;
    }

    /**
     * Sets the agent name.
     *
     * @param agentName
     *            the new agent name
     */
    public void setAgentName( final String agentName ) {
        this.agentName = agentName;
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state.
     *
     * @param state
     *            the new state
     */
    public void setState( final String state ) {
        this.state = state;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ZillowAgentDetails [agentName=" + agentName + ", state=" + state + "]";
    }
}
