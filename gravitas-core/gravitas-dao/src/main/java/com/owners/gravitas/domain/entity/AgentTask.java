package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 * The Class AgentTask.
 *
 * @author raviz
 */
@Entity( name = "GR_AGENT_TASK" )
public class AgentTask extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5451795329503483147L;

    /** The task id. */
    @Column( name = "TASK_ID", nullable = false )
    private String taskId;

    /** The type. */
    @Column( name = "TYPE", nullable = false )
    private String type;

    /** The location. */
    @Column( name = "LOCATION" )
    private String location;

    /** The scheduled dtm. */
    @Column( name = "SCHEDULED_DTM" )
    @Type( type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime" )
    private DateTime scheduledDtm;

    /** The description. */
    @Column( name = "DESCRIPTION" )
    private String description;

    /** The description. */
    @Column( name = "STATUS" )
    private String status;

    /** The title. */
    @Column( name = "TITLE", nullable = false )
    private String title;

    @Column( name = "TAG", nullable = false )
    private String tag;

    /** The deleted. */
    @Column( name = "IS_DELETED" )
    private boolean deleted;

    /** The deleted by. */
    @Column( name = "DELETED_BY" )
    private String deletedBy;

    /** The opportunity. */
    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "OPPORTUNITY_ID", nullable = false )
    private Opportunity opportunity;

    /** The warm call transfer. */
    @Column( name = "IS_WARM_CALL_TRANSFER" )
    private boolean warmCallTransferFlag;

    /**
     * Gets the task id.
     *
     * @return the task id
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * Sets the task id.
     *
     * @param taskId
     *            the new task id
     */
    public void setTaskId( final String taskId ) {
        this.taskId = taskId;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type
     *            the new type
     */
    public void setType( final String type ) {
        this.type = type;
    }

    /**
     * Gets the location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location.
     *
     * @param location
     *            the new location
     */
    public void setLocation( final String location ) {
        this.location = location;
    }

    /**
     * Gets the scheduled dtm.
     *
     * @return the scheduled dtm
     */
    public DateTime getScheduledDtm() {
        return scheduledDtm;
    }

    /**
     * Sets the scheduled dtm.
     *
     * @param scheduledDtm
     *            the new scheduled dtm
     */
    public void setScheduledDtm( final DateTime scheduledDtm ) {
        this.scheduledDtm = scheduledDtm;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description
     *            the new description
     */
    public void setDescription( final String description ) {
        this.description = description;
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
        this.status = status;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title
     *            the new title
     */
    public void setTitle( final String title ) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag( final String tag ) {
        this.tag = tag;
    }

    /**
     * @return the deleted
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * @param deleted
     *            the deleted to set
     */
    public void setDeleted( final boolean deleted ) {
        this.deleted = deleted;
    }

    /**
     * Gets the deleted by.
     *
     * @return the deleted by
     */
    public String getDeletedBy() {
        return deletedBy;
    }

    /**
     * Sets the deleted by.
     *
     * @param deletedBy
     *            the new deleted by
     */
    public void setDeletedBy( final String deletedBy ) {
        this.deletedBy = deletedBy;
    }

    /**
     * Gets the opportunity.
     *
     * @return the opportunity
     */
    public Opportunity getOpportunity() {
        return opportunity;
    }

    /**
     * Sets the opportunity.
     *
     * @param opportunity
     *            the new opportunity
     */
    public void setOpportunity( final Opportunity opportunity ) {
        this.opportunity = opportunity;
    }

    /**
     * @return the warmCallTransferFlag
     */
    public boolean isWarmCallTransferFlag() {
        return warmCallTransferFlag;
    }

    /**
     * @param warmCallTransferFlag
     *            the warmCallTransferFlag to set
     */
    public void setWarmCallTransferFlag( final boolean warmCallTransferFlag ) {
        this.warmCallTransferFlag = warmCallTransferFlag;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AgentTask [taskId=" + taskId + ", type=" + type + ", location=" + location + ", scheduledDtm="
                + scheduledDtm + ", description=" + description + ", status=" + status + ", title=" + title + ", tag="
                + tag + ", deleted=" + deleted + ", deletedBy=" + deletedBy + ", warmCallTransferFlag="
                + warmCallTransferFlag + ", getCreatedBy()=" + getCreatedBy() + ", getCreatedDate()=" + getCreatedDate()
                + ", getLastModifiedBy()=" + getLastModifiedBy() + ", getLastModifiedDate()=" + getLastModifiedDate()
                + ", getId()=" + getId() + ", isNew()=" + isNew() + ", getClass()=" + getClass() + ", hashCode()="
                + hashCode() + ", toString()=" + super.toString() + "]";
    }
}
