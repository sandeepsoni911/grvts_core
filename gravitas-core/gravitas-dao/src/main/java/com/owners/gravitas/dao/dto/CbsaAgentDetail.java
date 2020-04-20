package com.owners.gravitas.dao.dto;

import com.owners.gravitas.domain.entity.AgentDetails;

/**
 * The Class CbsaAgentDetail.
 * 
 * @author ankusht
 */
public class CbsaAgentDetail {

    /** The cbsa. */
    private String cbsa;

    /** The agent details. */
    private AgentDetails agentDetails;

    /**
     * Gets the cbsa.
     *
     * @return the cbsa
     */
    public String getCbsa() {
        return cbsa;
    }

    /**
     * Sets the cbsa.
     *
     * @param cbsa
     *            the new cbsa
     */
    public void setCbsa( final String cbsa ) {
        this.cbsa = cbsa;
    }

    /**
     * Gets the agent details.
     *
     * @return the agent details
     */
    public AgentDetails getAgentDetails() {
        return agentDetails;
    }

    /**
     * Sets the agent details.
     *
     * @param agentDetails
     *            the new agent details
     */
    public void setAgentDetails( final AgentDetails agentDetails ) {
        this.agentDetails = agentDetails;
    }
}
