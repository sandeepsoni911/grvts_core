package com.owners.gravitas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owners.gravitas.domain.entity.ActionLog;

/**
 * The Interface ActionLogRepository.
 *
 * @author vishwanathm
 */
public interface ActionLogRepository extends JpaRepository< ActionLog, String > {

    /**
     * Gets the action log by action by and action entity id and action type in.
     *
     * @param email
     *            the email
     * @param opportunityId
     *            the opportunity id
     * @param ctaValues
     *            the cta values
     * @return the action log by action by and action type in
     */
    public List< ActionLog > getActionLogByActionByAndActionEntityIdAndActionTypeIn( final String email,
            final String opportunityId, final List< String > ctaValues );

    /**
     * Gets the action log by action entity and action type in.
     *
     * @param actionEntity
     *            the action entity
     * @param ctaValues
     *            the cta values
     * @return the action log by action entity and action type in
     */
    public List< ActionLog > getActionLogByActionEntityAndActionTypeInOrderByCreatedDate( final String actionEntity,
            final List< String > ctaValues );

}
