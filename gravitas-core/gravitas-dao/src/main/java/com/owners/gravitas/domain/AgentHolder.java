package com.owners.gravitas.domain;

/**
 * The Class AgentHolder.
 *
 * @author vishwanathm
 */
public class AgentHolder extends BaseDomain {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7882794904221599347L;

    /** The agent id. */
    private String agentId;

    /** The agent opportunity. */
    private Agent agent;

    /**
     * Instantiates a new agent.
     */
    public AgentHolder() {
        // Do Nothing
    }

    /**
     * Instantiates a new agent.
     *
     * @param agentId
     *            the agent id
     * @param agent
     *            the agent opportunity
     */
    public AgentHolder( String agentId, Agent agent ) {
        this.agentId = agentId;
        this.agent = agent;
    }

    /**
     * Instantiates a new agent.
     *
     * @param agentId
     *            the agent id
     */
    public AgentHolder( String agentId ) {
        this.agentId = agentId;
    }

    /**
     * Gets the agent id.
     *
     * @return the agent id
     */
    public String getAgentId() {
        return agentId;
    }

    /**
     * Sets the agent id.
     *
     * @param agentId
     *            the new agent id
     */
    public void setAgentId( String agentId ) {
        this.agentId = agentId;
    }

    /**
     * Gets the agent opportunity.
     *
     * @return the agent opportunity
     */
    public Agent getAgent() {
        return agent;
    }

    /**
     * Sets the agent opportunity.
     *
     * @param agent
     *            the new agent opportunity
     */
    public void setAgent( Agent agent ) {
        this.agent = agent;
    }
}
