/*
 *
 */
package com.owners.gravitas.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The Class OpportunitySource.
 *
 * @author Jayant
 */
@JsonInclude( Include.NON_NULL )
public class AgentCumulativeStatistics {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8852680567751915158L;

    private String f2fCount;

    private String createdOn;

    private String agentName;

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName( String agentName ) {
        this.agentName = agentName;
    }

    public String getF2fCount() {
        return f2fCount;
    }

    public void setF2fCount( String f2fCount ) {
        this.f2fCount = f2fCount;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn( String createdOn ) {
        this.createdOn = createdOn;
    }

}
