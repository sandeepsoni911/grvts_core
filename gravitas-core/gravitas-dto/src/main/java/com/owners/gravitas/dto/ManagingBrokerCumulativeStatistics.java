/*
 *
 */
package com.owners.gravitas.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The Class OpportunitySource.
 *
 * @author javeedsy
 */
@JsonInclude( Include.NON_NULL )
public class ManagingBrokerCumulativeStatistics {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8852680567751915158L;

    /** The managing broker name. */
    private String managingBrokerName;

    /** The managing broker email. */
    private String managingBrokerEmail;

    /** The agent cumulative list. */
    private List< AgentCumulativeStatistics > agentCumulativeList;

    /**
     * Gets the managing broker name.
     *
     * @return the managing broker name
     */
    public String getManagingBrokerName() {
        return managingBrokerName;
    }

    /**
     * Sets the managing broker name.
     *
     * @param managingBrokerName
     *            the new managing broker name
     */
    public void setManagingBrokerName( final String managingBrokerName ) {
        this.managingBrokerName = managingBrokerName;
    }

    /**
     * Gets the managing broker email.
     *
     * @return the managing broker email
     */
    public String getManagingBrokerEmail() {
        return managingBrokerEmail;
    }

    /**
     * Sets the managing broker email.
     *
     * @param managingBrokerEmail
     *            the new managing broker email
     */
    public void setManagingBrokerEmail( final String managingBrokerEmail ) {
        this.managingBrokerEmail = managingBrokerEmail;
    }

    /**
     * Gets the agent cumulative list.
     *
     * @return the agent cumulative list
     */
    public List< AgentCumulativeStatistics > getAgentCumulativeList() {
        return agentCumulativeList;
    }

    /**
     * Sets the agent cumulative list.
     *
     * @param agentCumulativeList
     *            the new agent cumulative list
     */
    public void setAgentCumulativeList( final List< AgentCumulativeStatistics > agentCumulativeList ) {
        this.agentCumulativeList = agentCumulativeList;
    }

}
