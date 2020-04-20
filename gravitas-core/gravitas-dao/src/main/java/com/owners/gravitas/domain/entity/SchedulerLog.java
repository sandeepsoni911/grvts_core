package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * The Class SchedulerLog.
 *
 * @author ankusht
 */
@Entity( name = "GR_SCHEDULER_LOG" )
@EntityListeners( AuditingEntityListener.class )
public class SchedulerLog extends AbstractPersistable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7289626222191042138L;

    /** The scheduler name. */
    @Column( name = "NAME", nullable = false )
    private String schedulerName;

    /** The scheduler start time. */
    @Column( name = "START_TIME", nullable = false )
    @Type( type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime" )
    private DateTime startTime;

    /** The scheduler end time. */
    @Column( name = "END_TIME", nullable = false )
    @Type( type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime" )
    private DateTime endTime;

    /** The host name. */
    @Column( name = "HOST_NAME", nullable = false )
    private String hostName;

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
     * Gets the scheduler name.
     *
     * @return the scheduler name
     */
    public String getSchedulerName() {
        return schedulerName;
    }

    /**
     * Sets the scheduler name.
     *
     * @param schedulerName
     *            the new scheduler name
     */
    public void setSchedulerName( final String schedulerName ) {
        this.schedulerName = schedulerName;
    }

    /**
     * Gets the start time.
     *
     * @return the start time
     */
    public DateTime getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time.
     *
     * @param startTime
     *            the new start time
     */
    public void setStartTime( final DateTime startTime ) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time.
     *
     * @return the end time
     */
    public DateTime getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time.
     *
     * @param endTime
     *            the new end time
     */
    public void setEndTime( final DateTime endTime ) {
        this.endTime = endTime;
    }

    /**
     * Gets the host name.
     *
     * @return the host name
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * Sets the host name.
     *
     * @param hostName
     *            the new host name
     */
    public void setHostName( final String hostName ) {
        this.hostName = hostName;
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
