package com.owners.gravitas.business.task;

import static com.owners.gravitas.enums.ActionType.UPDATE;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.owners.gravitas.business.AuditTrailBusinessService;
import com.owners.gravitas.domain.ActionGroup;
import com.owners.gravitas.domain.AgentInfo;
import com.owners.gravitas.domain.LastViewed;
import com.owners.gravitas.dto.ActionLogDto;
import com.owners.gravitas.dto.request.LastViewedRequest;
import com.owners.gravitas.enums.ActionEntity;
import com.owners.gravitas.service.ActionGroupService;
import com.owners.gravitas.service.AgentInfoService;

@Service
public class AuditLogTask {

    /** The action group service. */
    @Autowired
    private ActionGroupService actionGroupService;

    /** The audit trail business service. */
    @Autowired
    private AuditTrailBusinessService auditTrailBusinessService;

    /** The agent info service. */
    @Autowired
    private AgentInfoService agentInfoService;

    /**
     * Audit last viewed.
     *
     * @param agentId
     *            the agent id
     * @param id
     *            the id
     * @param viewedRequest
     *            the viewed request
     * @param lastViewed
     *            the last viewed
     */
    @Async
    public void auditLastViewed( final String agentId, final String id, final LastViewedRequest viewedRequest,
            final LastViewed lastViewed ) {
        final AgentInfo info = agentInfoService.getAgentInfo( agentId );
        final ActionLogDto actionLogDto = auditTrailBusinessService.getActionLogDto( UPDATE, info.getEmail(),
                lastViewed.toAuditMap(), ActionEntity.valueOf( viewedRequest.getObjectType().toUpperCase() ), id );
        auditTrailBusinessService.saveAuditForAction( actionLogDto );
    }

    /**
     * Audit action flow.
     *
     * @param agentId
     *            the agent id
     * @param id
     *            the id
     * @param viewedRequest
     *            the viewed request
     * @param lastViewed
     *            the last viewed
     */
    @Async
    public void auditActionFlow( final String agentId, final String userEmail, final String actionGroupId,
            final String actionId, final Map< String, Object > request ) {
        final ActionGroup actionGroup = actionGroupService.getActionGroup( agentId, actionGroupId );
        final ActionLogDto actionLogDto = auditTrailBusinessService.getActionLogDto( UPDATE, userEmail, request,
                actionGroup.getActions().get( Integer.valueOf( actionId ) ).getName(), actionGroup.getOpportunityId() );
        auditTrailBusinessService.saveAuditForAction( actionLogDto );
    }
}
