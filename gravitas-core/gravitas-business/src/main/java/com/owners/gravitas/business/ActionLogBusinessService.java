package com.owners.gravitas.business;

import java.util.List;

import com.owners.gravitas.domain.entity.ActionLog;
import com.owners.gravitas.dto.ActionLogDto;
import com.owners.gravitas.dto.JmxChangeDto;

/**
 * The Interface ActionLogBusinessService.
 *
 * @author vishwanathm
 */
public interface ActionLogBusinessService {

    /**
     * Save action log.
     *
     * @param actionLog
     *            the action log
     */
    ActionLog saveActionLog( ActionLog actionLog );

    /**
     * Audit for action.
     *
     * @param actionLogDto
     *            the action log dto
     * @return the list
     */
    void auditForAction( ActionLogDto actionLogDto );

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
     * Agent audit for action.
     *
     * @param actionLogDtos
     *            the action log dtos
     */
    void agentAuditForAction( List< ActionLogDto > actionLogDtos );
    
    /**
     * Log jmx change.
     *
     * @param jmxChangeDto
     *            the jmxChangeDto
     */
    void logJmxChange( final JmxChangeDto jmxChangeDto );
}
