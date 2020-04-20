package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * The Class AgentTaskStatusLog.
 *
 * @author raviz
 */
@Entity( name = "GR_AGENT_TASK_STATUS_LOG" )
@EntityListeners( AuditingEntityListener.class )
public class AgentTaskStatusLog extends AbstractPersistable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8851404285060335399L;

    /** The status. */
    @Column( name = "STATUS", nullable = false )
    private String status;

    /** The reason. */
    @Column( name = "REASON" )
    private String reason;

    /** The created by. */
    @CreatedBy
    @Column( name = "CREATED_BY", nullable = false )
    private String createdBy;

    /** The created date. */
    @CreatedDate
    @Column( name = "CREATED_ON", nullable = false )
    @Type( type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime" )
    private DateTime createdDate;

    /** The agent task. */
    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "AGENT_TASK_ID", nullable = false )
    private AgentTask agentTask;

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
        this.status = status;
    }

    /**
     * Gets the reason.
     *
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the reason.
     *
     * @param reason
     *            the new reason
     */
    public void setReason( final String reason ) {
        this.reason = reason;
    }

    /**
     * Gets the created by.
     *
     * @return the created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the created by.
     *
     * @param createdBy
     *            the new created by
     */
    public void setCreatedBy( final String createdBy ) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the created date.
     *
     * @return the created date
     */
    public DateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the created date.
     *
     * @param createdDate
     *            the new created date
     */
    public void setCreatedDate( final DateTime createdDate ) {
        this.createdDate = createdDate;
    }

    /**
     * Gets the agent task.
     *
     * @return the agent task
     */
    public AgentTask getAgentTask() {
        return agentTask;
    }

    /**
     * Sets the agent task.
     *
     * @param agentTask
     *            the new agent task
     */
    public void setAgentTask( final AgentTask agentTask ) {
        this.agentTask = agentTask;
    }

}
