package com.owners.gravitas.domain.entity;

import static com.owners.gravitas.constants.Constants.CAUSE_COLUMN_LENGTH;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * The Class SystemErrorLog.
 *
 * @author ankusht
 */
@Entity( name = "GR_SYSTEM_ERROR_LOG" )
@EntityListeners( AuditingEntityListener.class )
public class SystemErrorLog extends AbstractPersistable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5671325418194620059L;

    /** The Scheduler log. */
    @ManyToOne
    @JoinColumn( name = "SCHEDULER_LOG_ID", nullable = false )
    private SchedulerLog schedulerLog;

    /** The system name. */
    @Column( name = "SYSTEM_NAME", nullable = false )
    private String systemName;

    /** The cause. */
    @Column( name = "CAUSE", length = CAUSE_COLUMN_LENGTH )
    private String cause;

    /** The created by. */
    @CreatedBy
    @Column( name = "CREATED_BY", updatable = false )
    private String createdBy;

    /** The created date. */
    @CreatedDate
    @Column( name = "CREATED_ON", updatable = false )
    @Type( type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime" )
    private DateTime createdDate;

    /**
     * Gets the scheduler log.
     *
     * @return the scheduler log
     */
    public SchedulerLog getSchedulerLog() {
        return schedulerLog;
    }

    /**
     * Sets the scheduler log.
     *
     * @param schedulerLog
     *            the new scheduler log
     */
    public void setSchedulerLog( final SchedulerLog schedulerLog ) {
        this.schedulerLog = schedulerLog;
    }

    /**
     * Gets the system name.
     *
     * @return the system name
     */
    public String getSystemName() {
        return systemName;
    }

    /**
     * Sets the system name.
     *
     * @param systemName
     *            the new system name
     */
    public void setSystemName( final String systemName ) {
        this.systemName = systemName;
    }

    /**
     * Gets the cause.
     *
     * @return the cause
     */
    public String getCause() {
        return cause;
    }

    /**
     * Sets the cause.
     *
     * @param cause
     *            the new cause
     */
    public void setCause( final String cause ) {
        this.cause = cause;
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
}
