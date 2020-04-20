package com.owners.gravitas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owners.gravitas.domain.entity.OpportunityAction;

/**
 * The Interface OpportunityActionRepository.
 *
 * @author raviz
 */
public interface OpportunityActionRepository extends JpaRepository< OpportunityAction, String > {
    /**
     * Find by action group id and action id.
     *
     * @param actiongroupid
     *            the actiongroupid
     * @param actionId
     *            the action id
     * @return the list
     */
    OpportunityAction findByActionFlowIdAndActionId( String actionFlowid, String actionId );

    /**
     * Find by action group id.
     *
     * @param actiongroupid
     *            the actiongroupid
     * @return the list
     */
    List< OpportunityAction > findByActionFlowId( String actionFlowid );
}
