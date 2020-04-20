package com.owners.gravitas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owners.gravitas.domain.entity.JmxUser;

/**
 * The Interface JmxUserRepository.
 * 
 * @author ankusht
 */
public interface JmxUserRepository extends JpaRepository< JmxUser, String > {

    /**
     * Delete by username.
     *
     * @param username
     *            the username
     */
    void deleteByUsername( String username );

    /**
     * Find by username.
     *
     * @param username
     *            the username
     */
    JmxUser findByUsername( String username );
}
