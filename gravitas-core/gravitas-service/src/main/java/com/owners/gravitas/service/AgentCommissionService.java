package com.owners.gravitas.service;

import com.owners.gravitas.domain.entity.AgentCommission;

public interface AgentCommissionService {

    /**
     * Gets agent commission data for a state and a price range.
     *
     * @param state
     *            the state of opportunity
     * @param topPrice
     *            the upper value of the price range
     * @return the agent commission
     */
    AgentCommission getCommissionByStateAndTopPrice( String state, int topPrice );

}
