package com.owners.gravitas.business.impl;

import static com.owners.gravitas.enums.PushNotificationType.SCRIPTED_FLOW_NEW_OPPORTUNITY;
import static com.owners.gravitas.enums.RecordType.BUYER;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.flowable.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.business.ActionFlowBusinessService;
import com.owners.gravitas.business.AgentNotificationBusinessService;
import com.owners.gravitas.business.builder.domain.ActionGroupBuilder;
import com.owners.gravitas.config.ActionFlowConfig;
import com.owners.gravitas.domain.Action;
import com.owners.gravitas.domain.ActionGroup;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.domain.entity.Group;
import com.owners.gravitas.domain.entity.OpportunityAction;
import com.owners.gravitas.domain.entity.UserGroup;
import com.owners.gravitas.dto.request.NotificationRequest;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.enums.PushNotificationType;
import com.owners.gravitas.service.ActionGroupService;
import com.owners.gravitas.service.AgentOpportunityService;
import com.owners.gravitas.service.GroupService;
import com.owners.gravitas.service.UserGroupService;

/**
 * The Class ActionFlowBusinessServiceImpl.
 *
 * @author raviz
 */
@Service
public class ActionFlowBusinessServiceImpl implements ActionFlowBusinessService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( ActionFlowBusinessServiceImpl.class );

    /** The agent push notification business service. */
    @Autowired
    private AgentNotificationBusinessService agentNotificationBusinessService;

    /** The action group builder. */
    @Autowired
    private ActionGroupBuilder actionGroupBuilder;

    /** The action group service. */
    @Autowired
    private ActionGroupService actionGroupService;

    /** The agent opportunity service. */
    @Autowired
    private AgentOpportunityService agentOpportunityService;

    /** The action flow config. */
    @Autowired
    private ActionFlowConfig actionFlowConfig;

    /** The group service. */
    @Autowired
    private GroupService groupService;

    /** The user group service. */
    @Autowired
    private UserGroupService userGroupService;

    /** The runtime service. */
    @Autowired
    protected RuntimeService runtimeService;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.ActionFlowBusinessService#createActionGroup(
     * java.lang.String, java.lang.String,
     * com.owners.gravitas.domain.Opportunity,
     * com.owners.gravitas.domain.Search)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public void createActionGroup( final String opportunityId, final String agentId, final Opportunity opportunity,
            final Search agentSearch ) {
        LOGGER.info( "Creating action flow for opportunity :" + opportunityId + " and agent :" + agentId );
        final ActionGroup actionGroup = actionGroupBuilder.convertTo( opportunityId );
        if (actionGroup != null) {
            final PostResponse res = actionGroupService.createActionGroup( agentId, actionGroup, opportunityId,
                    opportunity );
            final List< String > actionFlowIds = new ArrayList<>();
            actionFlowIds.add( res.getName() );
            final Map< String, Object > actionFlowData = new HashMap<>();
            actionFlowData.put( "actionFlowIds", actionFlowIds );
            agentOpportunityService.patchOpportunity( agentId, agentSearch.getAgentEmail(), opportunityId,
                    actionFlowData );
            // send push notification
            sendOpportunityPushNotifications( agentId, SCRIPTED_FLOW_NEW_OPPORTUNITY );
            // start a scheduled process of Call reminder pushnotification
            final Map< String, Object > paramData = new HashMap<>();
            paramData.put( "agentId", agentId );
            paramData.put( "actionGroupId", res.getName() );
            runtimeService.startProcessInstanceByKey( "scriptedFlowProcess", paramData );
        }
    }

    /**
     * Gets the action info.
     *
     * @param agentId
     *            the agent id
     * @param actionFlowId
     *            the action flow id
     * @param action
     *            the action
     * @return the action info
     */
    @Override
    @Transactional( readOnly = true )
    public Action getActionInfo( final String agentId, final String actionFlowId, final String action ) {
        return actionGroupService.getActionInfo( agentId, actionFlowId, action );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.ActionFlowBusinessService#
     * isEligibleForScriptedCall(java.lang.String, java.lang.String)
     */
    @Override
    @Transactional( readOnly = true )
    public boolean isEligibleForScriptedCall( final String opportunityType, final String agentEmail ) {
        boolean isEligibleForScriptedCall = false;
        if (BUYER.getType().equals( opportunityType )) {
            isEligibleForScriptedCall = isAgentEligibleForScriptedCall( agentEmail );
        }
        return isEligibleForScriptedCall;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.ActionFlowBusinessService#
     * isEligibleForScriptedCall(java.lang.String)
     */
    @Override
    @Transactional( readOnly = true )
    public boolean isEligibleForScriptedCall( final String agentEmail ) {
        return isAgentEligibleForScriptedCall( agentEmail );
    }

    /**
     * Send opportunity push notifications.
     *
     * @param agentId
     *            the agent id
     * @param type
     *            the type
     */
    @Override
    public void sendOpportunityPushNotifications( final String agentId, final PushNotificationType type ) {
        final NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setEventType( type );
        agentNotificationBusinessService.sendPushNotification( agentId, notificationRequest );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.ActionFlowBusinessService#
     * getOpportunityAction(java.lang.String, java.lang.String)
     */
    @Override
    public OpportunityAction getOpportunityAction( final String actionGroupId, final String actionId ) {
        return actionGroupService.getOpportunityAction( actionGroupId, actionId );
    }

    /**
     * Checks if is agent eligible for scripted call.
     *
     * @param agentEmail
     *            the agent email
     * @return true, if is agent eligible for scripted call
     */
    private boolean isAgentEligibleForScriptedCall( final String agentEmail ) {
        LOGGER.info( "Checking whether the agent is eligible for scripted call " + agentEmail );
        boolean isEligible = false;
        if (actionFlowConfig.isScriptedCallsEnabled()) {
            isEligible = true;
            final String scriptedCallsBucket = actionFlowConfig.getScriptedCallsBucket();
            if (isNotBlank( scriptedCallsBucket )) {
                final Group group = groupService.findGroupByNameAndDeleted( scriptedCallsBucket, false );
                if (group == null) {
                    isEligible = false;
                    LOGGER.error( "Group not found with the name as:" + scriptedCallsBucket + " or it was deleted!!" );
                } else {
                    final Set< UserGroup > userGroups = userGroupService.findByGroup( group );
                    isEligible = userGroups.stream()
                            .anyMatch( usergroup -> usergroup.getUser().getEmail().equals( agentEmail ) );
                }
            }
        }
        LOGGER.info( "Is agent with email as " + agentEmail + " eligible for scripted call " + isEligible );
        return isEligible;
    }

}
