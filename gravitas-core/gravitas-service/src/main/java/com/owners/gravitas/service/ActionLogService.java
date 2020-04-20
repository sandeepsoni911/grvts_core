package com.owners.gravitas.service;

import java.util.List;

import com.owners.gravitas.domain.entity.ActionLog;

/**
 * The Interface ActionLogService.
 *
 * @author vishwanathm
 */
public interface ActionLogService {

    /**
     * Save.
     *
     * @param actionLog
     *            the action log
     * @return the action log
     */
    ActionLog save( ActionLog actionLog );

    /**
     * Save all.
     *
     * @param logList
     *            the log list
     * @return the list
     */
    void saveAll( List< ActionLog > logList );

    /**
     * Gets the CTA audit logs.
     *
     * @param email
     *            the email
     * @param ctaValues
     *            the cta values
     * @param opportunityId
     *            the opportunity id
     * @return the CTA audit logs
     */
    List< ActionLog > getCTAAuditLogs( String email, String opportunityId, List< String > ctaValues );


    /**
     * Gets the action logs.
     *
     * @param actionEntity
     *            the action entity
     * @param ctaValues
     *            the cta values
     * @return the action logs
     */
    List< ActionLog > getActionLogs( String actionEntity, List< String > ctaValues );
}
