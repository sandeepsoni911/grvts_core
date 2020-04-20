package com.owners.gravitas.domain.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * The Class User.
 * 
 * @author pabhishek
 */
@Entity( name = "GR_USER" )
public class User extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -653017420701082687L;

    /** The role member. */
    @OneToMany( mappedBy = "user", cascade = CascadeType.ALL )
    private List< RoleMember > roleMember;

    /** The email. */
    @Column( name = "EMAIL", unique = true, nullable = false )
    private String email;

    /** The status. */
    @Column( name = "STATUS", nullable = true )
    private String status;

    /**
     * Instantiates a new user.
     */
    public User() {

    }

    /**
     * Instantiates a new user.
     *
     * @param roleMember
     *            the role member
     * @param email
     *            the email
     * @param status
     *            the status
     */
    public User( List< RoleMember > roleMember, String email, String status ) {
        super();
        this.roleMember = roleMember;
        this.email = email;
        this.status = status;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email
     *            the new email
     */
    public void setEmail( String email ) {
        this.email = email;
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
    public void setStatus( String status ) {
        this.status = status;
    }

    /**
     * Gets the role member.
     *
     * @return the role member
     */

    public List< RoleMember > getRoleMember() {
        return roleMember;
    }

    /**
     * Sets the role member.
     *
     * @param roleMember
     *            the new role member
     */
    public void setRoleMember( List< RoleMember > roleMember ) {
        this.roleMember = roleMember;
    }

}
