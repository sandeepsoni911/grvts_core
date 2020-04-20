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

// TODO: Auto-generated Javadoc
/**
 * The Class RolePermission.
 * 
 * @author pabhishek
 */
@Entity( name = "GR_ROLE_PERMISSION" )
@EntityListeners( AuditingEntityListener.class )
public class RolePermission extends AbstractPersistable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5918161255815358856L;

    /** The role. */
    @Id
    @ManyToOne
    @JoinColumn( name = "role_id" )
    private Role role;

    /** The permission. */
    @Id
    @ManyToOne
    @JoinColumn( name = "permission_id" )
    private Permission permission;

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
     * Instantiates a new role permission.
     */
    public RolePermission() {

    }

    /**
     * Instantiates a new role permission.
     *
     * @param role
     *            the role
     * @param permission
     *            the permission
     * @param createdBy
     *            the created by
     * @param createdDate
     *            the created date
     */
    public RolePermission( Role role, Permission permission, String createdBy, DateTime createdDate ) {
        super();
        this.role = role;
        this.permission = permission;
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
    public void setCreatedBy( String createdBy ) {
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
    public void setCreatedDate( DateTime createdDate ) {
        this.createdDate = createdDate;
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
    public void setRole( Role role ) {
        this.role = role;
    }

    /**
     * Gets the permission.
     *
     * @return the permission
     */
    public Permission getPermission() {
        return permission;
    }

    /**
     * Sets the permission.
     *
     * @param permission
     *            the new permission
     */
    public void setPermission( Permission permission ) {
        this.permission = permission;
    }

}
