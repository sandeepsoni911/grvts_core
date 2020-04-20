package com.owners.gravitas.business;

import com.owners.gravitas.dto.JmxUserDto;

/**
 * The Interface JmxUserBusinessService.
 * 
 * @author ankusht
 */
public interface JmxUserBusinessService {

    /**
     * Adds the jmx user.
     *
     * @param jmxUserDto
     *            the jmx user dto
     */
    void addJmxUser( JmxUserDto jmxUserDto );

    /**
     * Delete.
     *
     * @param email
     *            the email
     */
    void delete( String email );

    /**
     * Change password.
     *
     * @param email
     *            the email
     * @param password
     *            the password
     */
    void changePassword( String email, String password );
}
