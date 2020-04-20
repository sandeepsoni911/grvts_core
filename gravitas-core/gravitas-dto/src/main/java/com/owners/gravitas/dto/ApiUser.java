package com.owners.gravitas.dto;

import java.util.Set;

/**
 * The Class ApiUser.
 */
public class ApiUser extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1399006183627243489L;

    /** The uid. */
    private String uid;

    /** The email. */
    private String email;

    /** The roles. */
    private Set< String > roles;

    /**
     * Instantiates a new app user.
     */
    public ApiUser() {
        // Do nothing
    }

    /**
     * Instantiates a new app user.
     *
     * @param uid
     *            the uid
     * @param email
     *            the email
     */
    public ApiUser( String uid, String email ) {
        this.uid = uid;
        this.email = email;
    }

    /**
     * Gets the uid.
     *
     * @return the uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * Sets the uid.
     *
     * @param uid
     *            the new uid
     */
    public void setUid( String uid ) {
        this.uid = uid;
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
     * Gets the roles.
     *
     * @return the roles
     */
    public Set< String > getRoles() {
        return roles;
    }

    /**
     * Sets the roles.
     *
     * @param roles
     *            the new roles
     */
    public void setRoles( Set< String > roles ) {
        this.roles = roles;
    }
}
