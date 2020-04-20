package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * The Class OpportunityResponseLog.
 *
 * @author raviz
 */
@Entity( name = "gr_opportunity_response_log" )
public class OpportunityResponseLog extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -9217901246155961851L;

    /** The thread id. */
    @Column( name = "thread_id" )
    private String threadId;

    /** The opp fb id. */
    @Column( name = "opp_fb_id", nullable = false )
    private String oppFbId;

    /** The agent email. */
    @Column( name = "agent_email", nullable = false )
    private String agentEmail;

    /** The status. */
    @Column( name = "status", nullable = false )
    private String status;

    /**
     * Instantiates a new opportunity response log.
     *
     * @param threadId
     *            the thread id
     * @param oppFbId
     *            the opp fb id
     * @param agentEmail
     *            the agent email
     * @param status
     *            the status
     */
    public OpportunityResponseLog( final String threadId, final String oppFbId, final String agentEmail,
            final String status ) {
        super();
        this.threadId = threadId;
        this.oppFbId = oppFbId;
        this.agentEmail = agentEmail;
        this.status = status;
    }

    public OpportunityResponseLog() {
        super();
    }

    /**
     * Gets the thread id.
     *
     * @return the thread id
     */
    public String getThreadId() {
        return threadId;
    }

    /**
     * Sets the thread id.
     *
     * @param threadId
     *            the new thread id
     */
    public void setThreadId( final String threadId ) {
        this.threadId = threadId;
    }

    /**
     * Gets the opp fb id.
     *
     * @return the opp fb id
     */
    public String getOppFbId() {
        return oppFbId;
    }

    /**
     * Sets the opp fb id.
     *
     * @param oppFbId
     *            the new opp fb id
     */
    public void setOppFbId( final String oppFbId ) {
        this.oppFbId = oppFbId;
    }

    /**
     * Gets the agent email.
     *
     * @return the agent email
     */
    public String getAgentEmail() {
        return agentEmail;
    }

    /**
     * Sets the agent email.
     *
     * @param agentEmail
     *            the new agent email
     */
    public void setAgentEmail( final String agentEmail ) {
        this.agentEmail = agentEmail;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status
     *            the new status
     */
    public void setStatus( final String status ) {
        if (status.length() > 3999) {
            this.status = status.substring( 0, 3999 );
        } else {
            this.status = status;
        }
    }
}
