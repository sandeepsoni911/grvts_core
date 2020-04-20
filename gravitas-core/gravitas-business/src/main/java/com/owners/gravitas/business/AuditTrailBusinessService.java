package com.owners.gravitas.business;

import java.util.Map;

import com.owners.gravitas.annotation.Audit;
import com.owners.gravitas.dto.ActionLogDto;
import com.owners.gravitas.enums.ActionEntity;
import com.owners.gravitas.enums.ActionType;

/**
 * The Interface AuditTrailBusinessService.
 *
 * @author vishwanathm
 */
public interface AuditTrailBusinessService {

    /**
     * Creates the audit log.
     *
     * @param argsMap
     *            the args map
     * @param audit
     *            the audit
     * @param returnObj
     *            the return obj
     */
    void createAuditLog( final Object[] joinPointArgsMap, final Audit audit, final Object returnObj );

    /**
     * Gets the action log dto.
     *
     * @param type
     *            the type
     * @param actionBy
     *            the action by
     * @param auditMap
     *            the audit map
     * @param entity
     *            the entity
     * @param actionEntityId
     *            the action entity id
     * @return the action log dto
     */
    ActionLogDto getActionLogDto( ActionType type, String actionBy, Map< String, Object > auditMap, ActionEntity entity,
            String actionEntityId );

    /**
     * Save audi for action.
     *
     * @param actionLogDto
     *            the action log dto
     */
    void saveAuditForAction( ActionLogDto actionLogDto );

    /**
     * Gets the action log dto.
     *
     * @param update
     *            the update
     * @param appUserEmail
     *            the app user email
     * @param request
     *            the request
     * @param name
     *            the name
     * @param opportunityId
     *            the opportunity id
     * @return the action log dto
     */
    ActionLogDto getActionLogDto( ActionType update, String appUserEmail, Map< String, Object > request, String name,
            String opportunityId );

}
