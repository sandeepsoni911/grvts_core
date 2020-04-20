package com.owners.gravitas.dto;

/**
 * The Class JmxUserDto.
 * 
 * @author ankusht
 */
public class JmxUserDto {

    /** The email. */
    private String username;

    /** The password. */
    private String password;

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
     * Instantiates a new jmx user dto.
     */
    public JmxUserDto() {
        super();
    }

    /**
     * Instantiates a new jmx user dto.
     *
     * @param email
     *            the email
     * @param password
     *            the password
     */
    public JmxUserDto( final String email, final String password ) {
        super();
        this.username = email;
        this.password = password;
    }

}
