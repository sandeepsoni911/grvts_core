/*
 *
 */
package com.owners.gravitas.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The Class OpportunitySource.
 *
 * @author amits
 */

@JsonInclude( JsonInclude.Include.NON_NULL )
public class AgentSatistics {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8852680567751915158L;

    /** The opportunity. */
    private List< OpportunityDetails > opportunity;

    /** The agnet name. */
    private String agentName;

    /** The managing broker name. */
    private String managingBrokerName;

    /** The f 2 f created on. */
    private String f2fCreatedOn;

    /** The opportunity created on. */
    private String opportunityCreatedOn;

    /**
     * Gets the opportunity.
     *
     * @return the opportunity
     */
    public List< OpportunityDetails > getOpportunity() {
        return opportunity;
    }

    /**
     * Sets the opportunity.
     *
     * @param opportunity
     *            the new opportunity
     */
    public void setOpportunity( final List< OpportunityDetails > opportunity ) {
        this.opportunity = opportunity;
    }

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
     * Gets the f 2 f created on.
     *
     * @return the f 2 f created on
     */
    public String getF2fCreatedOn() {
        return f2fCreatedOn;
    }

    /**
     * Sets the f 2 f created on.
     *
     * @param f2fCreatedOn
     *            the new f 2 f created on
     */
    public void setF2fCreatedOn( final String f2fCreatedOn ) {
        this.f2fCreatedOn = f2fCreatedOn;
    }

    /**
     * Gets the opportunity created on.
     *
     * @return the opportunity created on
     */
    public String getOpportunityCreatedOn() {
        return opportunityCreatedOn;
    }

    /**
     * Sets the opportunity created on.
     *
     * @param opportunityCreatedOn
     *            the new opportunity created on
     */
    public void setOpportunityCreatedOn( final String opportunityCreatedOn ) {
        this.opportunityCreatedOn = opportunityCreatedOn;
    }

}
