package com.owners.gravitas.dto;

import java.io.Serializable;

import com.google.api.services.admin.directory.model.User;

/**
 * The UserDetails Class.
 * 
 * @author Khanujal
 *
 */
public class UserDetails {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1094130037898617857L;

    /** The user. */
    private User user;

    /** The userPhoto. */
    private String userPhoto;

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
     *            the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the userPhoto.
     *
     * @return the userPhoto
     */
    public String getUserPhoto() {
        return userPhoto;
    }

    /**
     * Sets the userPhoto.
     *
     * @param userPhoto
     *            the userPhoto to set
     */
    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

}
