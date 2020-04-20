package com.owners.gravitas.dto.agentassgn;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class EligibleAgentResponse.
 * 
 * @author ankusht
 */
public class EligibleAgentResponse implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -4082682069771429205L;

    /** The eligible agents. */
    private List< EligibleAgent > eligibleAgents = new ArrayList< EligibleAgent >();

    /**
     * Instantiates a new eligible agent response.
     */
    public EligibleAgentResponse() {
        super();
    }

    /**
     * Instantiates a new eligible agent response.
     *
     * @param eligibleAgents
     *            the eligible agents
     */
    public EligibleAgentResponse( final List< EligibleAgent > eligibleAgents ) {
        super();
        this.eligibleAgents = eligibleAgents;
    }

    /**
     * Gets the eligible agents.
     *
     * @return the eligible agents
     */
    public List< EligibleAgent > getEligibleAgents() {
        return eligibleAgents;
    }

    /**
     * Sets the eligible agents.
     *
     * @param eligibleAgents
     *            the new eligible agents
     */
    public void setEligibleAgents( final List< EligibleAgent > eligibleAgents ) {
        this.eligibleAgents = eligibleAgents;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append( "EligibleAgentResponse [eligibleAgents=" ).append( eligibleAgents ).append( "]" );
        return builder.toString();
    }
}
