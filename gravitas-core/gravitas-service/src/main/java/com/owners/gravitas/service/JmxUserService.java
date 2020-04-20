package com.owners.gravitas.service;

import com.owners.gravitas.domain.entity.JmxUser;

/**
 * The Interface JmxUserService.
 * 
 * @author ankusht
 */
public interface JmxUserService {

    /**
     * Save.
     *
     * @param jmxUser
     *            the jmx user
     */
    JmxUser save( JmxUser jmxUser );

    /**
     * Delete.
     *
     * @param username
     *            the username
     */
    void delete( String username );

    /**
     * Find by username.
     *
     * @param username
     *            the username
     * @return the jmx user
     */
    JmxUser findByUsername( String username );

    /**
     * Gets the encrypted values.
     *
     * @param newPassword
     *            the new password
     * @return the encrypted values
     */
    String[] getEncryptedValues( String newPassword );
}
