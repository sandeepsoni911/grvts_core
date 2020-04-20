package com.owners.gravitas.dto.crm.response;

import java.util.List;
import java.util.Map;

import com.owners.gravitas.dto.response.BaseResponse;

/**
 * The Class AgentAnalyticsResponse.
 *
 * @author madhav
 */
public class AgentStatisticsResponse extends BaseResponse {

    private static final long serialVersionUID = 1L;

    /** The agent analytics. */
    private List< Map< String, String > > agentStatistics;

    /**
     * @return the agentStatistics
     */
    public List< Map< String, String > > getAgentStatistics() {
        return agentStatistics;
    }

    /**
     * @param agentStatistics
     *            the agentStatistics to set
     */
    public void setAgentStatistics( List< Map< String, String > > agentStatistics ) {
        this.agentStatistics = agentStatistics;
    }

}
