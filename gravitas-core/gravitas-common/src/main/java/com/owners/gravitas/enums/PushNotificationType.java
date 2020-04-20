package com.owners.gravitas.enums;

/**
 * The Enum PushNotificationType.
 *
 * @author vishwanathm
 */
public enum PushNotificationType {

    /** The new request. */
    NEW_REQUEST,

    /** The new task. */
    NEW_TASK,

    /** The new opportunity. */
    NEW_OPPORTUNITY,

    /** The new opportunity reminder. */
    NEW_OPPORTUNITY_REMINDER,

    /** The claimed opportunity reminder. */
    CLAIMED_OPPORTUNITY_REMINDER,

    /** The scheduled task appointment alert. */
    SCHEDULED_TASK_APPOINTMENT_ALERT,

    /** The scheduled task appointment reminder. */
    SCHEDULED_TASK_APPOINTMENT_REMINDER,
    
    /** The Notification OCL reminder. */
    NOTIFICATION_OCL_REMINDER,

    /** The update badge counter */
    UPDATE_BADGE_COUNTER,

    /** The scripted flow incomplete. */
    SCRIPTED_FLOW_INCOMPLETE,

    /** The scripted calls new opportunity. */
    SCRIPTED_FLOW_NEW_OPPORTUNITY,

    /** The scripted flow call reminder. */
    SCRIPTED_FLOW_CALL_REMINDER,
    
    /** The on duty agent. */
    ON_DUTY_AGENT,
    
    /** New pending opportunity notification type. **/
    NEW_PENDING_OPPORTUNITY;
}
