package com.owners.gravitas.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.owners.gravitas.dto.Group;
import com.owners.gravitas.dto.User;

/**
 * The Class UserGroupDetail.
 *
 * @author raviz
 */
public class UserGroupDetail extends Group {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6479241447422137037L;

    /** The created first name. */
    private String createdFirstName;

    /** The created last name. */
    private String createdLastName;

    /** The modified first name. */
    private String modifiedFirstName;

    /** The modified last name. */
    private String modifiedLastName;

    /** The users. */
    private List< User > users = new ArrayList<>();

    /**
     * Gets the created first name.
     *
     * @return the created first name
     */
    public String getCreatedFirstName() {
        return createdFirstName;
    }

    /**
     * Sets the created first name.
     *
     * @param createdFirstName
     *            the new created first name
     */
    public void setCreatedFirstName( final String createdFirstName ) {
        this.createdFirstName = createdFirstName;
    }

    /**
     * Gets the created last name.
     *
     * @return the created last name
     */
    public String getCreatedLastName() {
        return createdLastName;
    }

    /**
     * Sets the created last name.
     *
     * @param createdLastName
     *            the new created last name
     */
    public void setCreatedLastName( final String createdLastName ) {
        this.createdLastName = createdLastName;
    }

    /**
     * Gets the modified first name.
     *
     * @return the modified first name
     */
    public String getModifiedFirstName() {
        return modifiedFirstName;
    }

    /**
     * Sets the modified first name.
     *
     * @param modifiedFirstName
     *            the new modified first name
     */
    public void setModifiedFirstName( final String modifiedFirstName ) {
        this.modifiedFirstName = modifiedFirstName;
    }

    /**
     * Gets the modified last name.
     *
     * @return the modified last name
     */
    public String getModifiedLastName() {
        return modifiedLastName;
    }

    /**
     * Sets the modified last name.
     *
     * @param modifiedLastName
     *            the new modified last name
     */
    public void setModifiedLastName( final String modifiedLastName ) {
        this.modifiedLastName = modifiedLastName;
    }

    /**
     * Gets the users.
     *
     * @return the users
     */
    public List< User > getUsers() {
        return users;
    }

    /**
     * Sets the users.
     *
     * @param users
     *            the new users
     */
    public void setUsers( final List< User > users ) {
        this.users = users;
    }

}
