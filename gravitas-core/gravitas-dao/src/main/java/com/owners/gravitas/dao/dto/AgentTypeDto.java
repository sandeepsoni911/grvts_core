package com.owners.gravitas.dao.dto;

import java.util.LinkedList;
import java.util.List;

import com.owners.gravitas.domain.entity.AgentDetails;

/**
 * The Class AgentTypeDto.
 * 
 * @author ankusht
 */
public class AgentTypeDto {

    /** The cbsa. */
    private String cbsa;

    /** The agent details list. */
    private List< AgentDetails > agentDetailsList;

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
     * Gets the agent details list.
     *
     * @return the agent details list
     */
    public List< AgentDetails > getAgentDetailsList() {
        return agentDetailsList;
    }

    /**
     * Sets the agent details list.
     *
     * @param agentDetailsList
     *            the new agent details list
     */
    public void setAgentDetailsList( final List< AgentDetails > agentDetailsList ) {
        this.agentDetailsList = agentDetailsList;
    }

    /**
     * Instantiates a new agent type dto.
     */
    public AgentTypeDto() {
        super();
        agentDetailsList = new LinkedList<>();
    }

    /**
     * Instantiates a new agent type dto.
     *
     * @param cbsa
     *            the cbsa
     * @param agentDetailsList
     *            the agent details list
     */
    public AgentTypeDto( final String cbsa, final List< AgentDetails > agentDetailsList ) {
        super();
        this.cbsa = cbsa;
        this.agentDetailsList = agentDetailsList;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( cbsa == null ) ? 0 : cbsa.hashCode() );
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( final Object obj ) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AgentTypeDto other = ( AgentTypeDto ) obj;
        if (cbsa == null) {
            if (other.cbsa != null) {
                return false;
            }
        } else if (!cbsa.equals( other.cbsa )) {
            return false;
        }
        return true;
    }
}
