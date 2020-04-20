/*
 *
 */
package com.owners.gravitas.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.owners.gravitas.dto.AgentCumulativeStatistics;
import com.owners.gravitas.dto.ManagingBrokerCumulativeStatistics;

/**
 * The Class OpportunitySource.
 *
 * @author Jayant
 */
@JsonInclude( Include.NON_NULL )
public class AgentCumulativeResponse extends BaseResponse {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5880307885731426165L;

    /** The details. */
    private List< AgentCumulativeStatistics > statistics;

    private List< ManagingBrokerCumulativeStatistics > managingBrokerStatisticsList;

    public List< AgentCumulativeStatistics > getCumulativeStatistics() {
        return statistics;
    }

    public void setCumulativeStatistics( List< AgentCumulativeStatistics > statistics ) {
        this.statistics = statistics;
    }

    public List< ManagingBrokerCumulativeStatistics > getManagingBrokerStatisticsList() {
        return managingBrokerStatisticsList;
    }

    public void setManagingBrokerStatisticsList(
            List< ManagingBrokerCumulativeStatistics > managingBrokerStatisticsList ) {
        this.managingBrokerStatisticsList = managingBrokerStatisticsList;
    }

}
