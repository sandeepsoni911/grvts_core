/*
 *
 */
package com.owners.gravitas.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.owners.gravitas.dto.AgentSatistics;

/**
 * The Class OpportunitySource.
 *
 * @author Jayant
 */
@JsonInclude(Include.NON_NULL)
public class AgentStatisticsResponse extends BaseResponse {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5880307885731426165L;

    /** The details. */
    private List<AgentSatistics> agentSatistics;

	public List<AgentSatistics> getAgentSatistics() {
		return agentSatistics;
	}

	public void setAgentSatistics(List<AgentSatistics> agentSatistics) {
		this.agentSatistics = agentSatistics;
	}

	
}
