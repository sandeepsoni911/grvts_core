package com.owners.gravitas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.owners.gravitas.domain.entity.AgentCommission;

/**
 * The Interface AgentCommissionRepository.
 *
 * @author bhardrah
 */
public interface AgentCommissionRepository extends JpaRepository< AgentCommission, String > {

    /**
     * Gets agent commission data for a state and a price range.
     *
     * @param state
     *            the state of opportunity
     * @param topPrice
     *            the upper value of the price range
     * @return the list of agent commission
     */
    public AgentCommission findTopByStateAndMinValueLessThanEqualOrderByMinValueDesc( String state, int minValue );

}
