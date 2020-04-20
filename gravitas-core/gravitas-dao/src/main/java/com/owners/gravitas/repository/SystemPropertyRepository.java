package com.owners.gravitas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owners.gravitas.domain.entity.SystemProperty;

/**
 * The Interface SystemPropertyRepository.
 */
public interface SystemPropertyRepository extends JpaRepository< SystemProperty, String > {

    /**
     * Find by name.
     *
     * @param name
     *            the name
     * @return the system property
     */
    public SystemProperty findByName( String name );

    /**
     * Find all by name.
     *
     * @param name
     *            the name
     * @return the list
     */
    public List< SystemProperty > findAllByNameIn( List< String > name );
}
