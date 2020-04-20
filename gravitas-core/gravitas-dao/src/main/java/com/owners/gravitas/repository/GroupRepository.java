package com.owners.gravitas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owners.gravitas.domain.entity.Group;

/**
 * The Interface GroupRepository.
 *
 * @author raviz
 */
public interface GroupRepository extends JpaRepository< Group, String > {

    /**
     * Find by name.
     *
     * @param name
     *            the name
     * @return the group
     */
    Group findByName( final String name );

    /**
     * Find by name and deleted.
     *
     * @param name
     *            the name
     * @param isDeleted
     *            the is deleted
     * @return the group
     */
    Group findByNameAndDeleted( final String name, final boolean isDeleted );

    /**
     * Find by id.
     *
     * @param id
     *            the id
     * @return the group
     */
    Group findById( final String id );

    /**
     * Find by deleted.
     *
     * @param isDeleted
     *            the is deleted
     * @return the list
     */
    List< Group > findByDeleted( final boolean isDeleted );
}
