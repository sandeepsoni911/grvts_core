package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * The Class JmxUser.
 * 
 * @author ankusht
 */
@Entity( name = "GR_JMX_USER" )
public class JmxUser extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 6258111850536117636L;

    /** The email. */
    @Column( name = "USERNAME", nullable = false )
    private String username;

    /** The password. */
    @Column( name = "PASSWORD", nullable = false )
    private String password;

    @Column( name = "IV", nullable = false )
    private String iv;

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username
     *            the new username
     */
    public void setUsername( final String username ) {
        this.username = username;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password
     *            the new password
     */
    public void setPassword( final String password ) {
        this.password = password;
    }

    /**
     * Gets the iv.
     *
     * @return the iv
     */
    public String getIv() {
        return iv;
    }

    /**
     * Sets the iv.
     *
     * @param iv
     *            the new iv
     */
    public void setIv( final String iv ) {
        this.iv = iv;
    }
}
