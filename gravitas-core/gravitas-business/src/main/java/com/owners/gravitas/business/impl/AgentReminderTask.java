package com.owners.gravitas.business.impl;

/**
 * The Interface AgentReminderTask provided method for agent reminders.
 */
public interface AgentReminderTask {

    /**
     * Send new opp notification.
     *
     * @param agentId
     *            the agent id
     */
    void sendNewOppNotification( String agentId );

    /**
     * Send claimed opp notification.
     *
     * @param agentId
     *            the agent id
     */
    void sendClaimedOppNotification( String agentId );

    /**
     * Send action flow incomplete reminder.
     *
     * @param agentId
     *            the agent id
     */
    void sendActionFlowIncompleteReminder( String agentId );

}
