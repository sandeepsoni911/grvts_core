package com.owners.gravitas.enums;

/**
 * The Enum JobType.
 *
 * @author ankusht
 */
public enum JobType {

    /** The gravitas health check scheduler. */
    GRAVITAS_HEALTH_CHECK_JOB,

    /** The agent performance metrics calculation job. */
    AGENT_PERFORMANCE_METRICS_JOB,

    /** The claimed opportunities notification job. */
    AGENT_CLAIMED_OPP_REMINDER_JOB,

    /** The new opportunities notification job. */
    AGENT_NEW_OPP_REMINDER_JOB,

    /** Agent analytics job. */
    AGENT_ANALYTICS_JOB,

    /** The opportunity mapping job lock key. */
    OPPORTUNITY_MAPPING_JOB,

    /** The agent action flow incomplete reminder job. */
    AGENT_ACTION_FLOW_INCOMPLETE_REMINDER_JOB,

    /** The outbound lead check farming job. */
    OUTBOUND_LEAD_CHECK_FARMING_JOB,

    /** The agents response time job. */
    AGENTS_RESPONSE_TIME_JOB,
    
    /** The livevox submit lead job. */
    LIVEVOX_LEAD_SUBMIT_JOB;
}
