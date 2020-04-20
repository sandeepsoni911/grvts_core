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
 * The Class UserGroup.
 *
 * @author raviz
 */
@Entity( name = "GR_USER_GROUP" )
@EntityListeners( AuditingEntityListener.class )
public class UserGroup extends AbstractPersistable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2384994436321312673L;

    /** The stage. */
    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "USER_ID", nullable = false, insertable = true, updatable = true )
    private User user;

    /** The stage. */
    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "GROUP_ID", nullable = false, insertable = true, updatable = true )
    private Group group;

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
     * Instantiates a new user group.
     */
    public UserGroup() {

    }

    /**
     * Gets the user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user.
     *
     * @param user
     *            the new user
     */
    public void setUser( final User user ) {
        this.user = user;
    }

    /**
     * Gets the group.
     *
     * @return the group
     */
    public Group getGroup() {
        return group;
    }

    /**
     * Sets the group.
     *
     * @param group
     *            the new group
     */
    public void setGroup( final Group group ) {
        this.group = group;
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
