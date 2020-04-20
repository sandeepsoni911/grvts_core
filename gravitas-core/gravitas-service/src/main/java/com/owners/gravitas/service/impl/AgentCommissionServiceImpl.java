package com.owners.gravitas.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.domain.entity.AgentCommission;
import com.owners.gravitas.repository.AgentCommissionRepository;
import com.owners.gravitas.service.AgentCommissionService;

@Service
public class AgentCommissionServiceImpl implements AgentCommissionService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentCommissionServiceImpl.class );

    /** The agent commission repository. */
    @Autowired
    private AgentCommissionRepository agentCommissionRepository;

    /**
     * Gets agent commission data for a state and a price range.
     *
     * @param state
     *            the state of opportunity
     * @param topPrice
     *            the upper value of the price range
     * @return the list of agent commission
     */
    @Override
    public AgentCommission getCommissionByStateAndTopPrice( String state,
            int topPrice ) {
        LOGGER.info( "finding agent commission for state : {}, topPrice : {}", state,
                topPrice );
        return agentCommissionRepository.findTopByStateAndMinValueLessThanEqualOrderByMinValueDesc( state, topPrice );
    }
}
