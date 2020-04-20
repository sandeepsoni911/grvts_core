package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * The Class RoleMember.
 * 
 * @author pabhishek
 */
@Entity( name = "GR_ROLE_MEMBER" )
@EntityListeners( AuditingEntityListener.class )
public class RoleMember extends AbstractPersistable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2307483509533201154L;

    /** The user. */
    @Id
    @ManyToOne
    @JoinColumn( name = "member_id" )
    private User user;

    /** The role. */
    @Id
    @ManyToOne
    @JoinColumn( name = "role_id" )
    private Role role;

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
     * Instantiates a new role member.
     */
    public RoleMember() {

    }

    /**
     * Instantiates a new role member.
     *
     * @param user
     *            the user
     * @param role
     *            the role
     */
    public RoleMember( final User user, final Role role ) {
        super();
        this.user = user;
        this.role = role;
    }

    /**
     * Instantiates a new role member.
     *
     * @param user
     *            the user
     * @param role
     *            the role
     * @param createdBy
     *            the created by
     * @param createdDate
     *            the created date
     */
    public RoleMember( final User user, final Role role, final String createdBy, final DateTime createdDate ) {
        super();
        this.user = user;
        this.role = role;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
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
     * Gets the role.
     *
     * @return the role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets the role.
     *
     * @param role
     *            the new role
     */
    public void setRole( final Role role ) {
        this.role = role;
    }

}
