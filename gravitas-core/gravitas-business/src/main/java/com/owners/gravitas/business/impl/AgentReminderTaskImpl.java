package com.owners.gravitas.business.impl;

import static com.owners.gravitas.enums.PushNotificationType.CLAIMED_OPPORTUNITY_REMINDER;
import static com.owners.gravitas.enums.PushNotificationType.NEW_OPPORTUNITY_REMINDER;
import static com.owners.gravitas.enums.PushNotificationType.SCRIPTED_FLOW_INCOMPLETE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.business.ActionFlowBusinessService;
import com.owners.gravitas.business.AgentNotificationBusinessService;
import com.owners.gravitas.dto.request.NotificationRequest;
import com.owners.gravitas.enums.PushNotificationType;
import com.owners.gravitas.service.AgentOpportunityService;
import com.owners.gravitas.service.SearchService;

/**
 * The Class AgentReminderTaskImpl.
 *
 * @author amits
 */
@Service
public class AgentReminderTaskImpl implements AgentReminderTask {

    /** Logger instance. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentReminderTaskImpl.class );

    /** The agent push notification business service. */
    @Autowired
    private AgentNotificationBusinessService agentNotificationBusinessService;

    /** The agent opportunity service. */
    @Autowired
    private AgentOpportunityService agentOpportunityService;

    /** The action flow business service. */
    @Autowired
    private ActionFlowBusinessService actionFlowBusinessService;

    /** The search service. */
    @Autowired
    private SearchService searchService;

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.impl.AgentReminderTask#
     * sendNewOppNotification(java.lang.String)
     */
    @Override
    @Async
    @Transactional( readOnly = true )
    public void sendNewOppNotification( final String agentId ) {
        final String agentEmail = searchService.searchByAgentId( agentId ).getAgentEmail();
        if (!actionFlowBusinessService.isEligibleForScriptedCall( agentEmail )) {
            final int newOpportunityCount = agentOpportunityService.getAgentNewOpportunitiesCount( agentId );
            if (newOpportunityCount > 0) {
                LOGGER.info( "Sending reminder push notification to agent " + agentId );
                agentNotificationBusinessService.sendPushNotification( agentId,
                        getNotificationRequest( null, newOpportunityCount, NEW_OPPORTUNITY_REMINDER ) );
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.impl.AgentReminderTask#
     * sendClaimedOppNotification(java.lang.String)
     */
    @Override
    @Async
    @Transactional( readOnly = true )
    public void sendClaimedOppNotification( final String agentId ) {
        final String agentEmail = searchService.searchByAgentId( agentId ).getAgentEmail();
        if (!actionFlowBusinessService.isEligibleForScriptedCall( agentEmail )
                && agentOpportunityService.hasAgentClaimedOpportunityWithOpenTasks( agentId )) {
            LOGGER.info( "Sending reminder push notification to agent " + agentId );
            agentNotificationBusinessService.sendPushNotification( agentId,
                    getNotificationRequest( null, 0, CLAIMED_OPPORTUNITY_REMINDER ) );
        }
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.impl.AgentReminderTask#
     * sendActionFlowIncompleteReminder(java.lang.String)
     */
    @Override
    @Async
    @Transactional( readOnly = true, propagation = Propagation.REQUIRES_NEW )
    public void sendActionFlowIncompleteReminder( final String agentId ) {
        LOGGER.info( "checking whether the action flows are incomplete for agent " + agentId );
        final String agentEmail = searchService.searchByAgentId( agentId ).getAgentEmail();
        if (actionFlowBusinessService.isEligibleForScriptedCall( agentEmail )
                && agentOpportunityService.hasIncompleteActionFlow( agentEmail )) {
            LOGGER.info( "Sending action flow incomplete push notification to agent " + agentId );
            actionFlowBusinessService.sendOpportunityPushNotifications( agentId, SCRIPTED_FLOW_INCOMPLETE );
        }
    }

    /**
     * Gets the notification request.
     *
     * @param opportunityId
     *            the opportunity id
     * @param oppCount
     *            the opp count
     * @param eventType
     *            the event type
     * @return the notification request
     */
    private NotificationRequest getNotificationRequest( final String opportunityId, final int oppCount,
            final PushNotificationType eventType ) {
        final NotificationRequest notificationRequest = new NotificationRequest( oppCount );
        notificationRequest.setEventType( eventType );
        notificationRequest.setOpportunityId( opportunityId );
        return notificationRequest;
    }

}
