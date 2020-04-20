package com.owners.gravitas.service;

import java.util.List;

import com.owners.gravitas.domain.entity.Group;

/**
 * The Interface GroupService.
 *
 * @author raviz
 */
public interface GroupService {

    /**
     * Find by name.
     *
     * @param name
     *            the name
     * @return the group
     */
    Group findByName( String name );

    /**
     * Find groups by name and deleted.
     *
     * @param name
     *            the name
     * @param isDeleted
     *            the is deleted
     * @return the group
     */
    Group findGroupByNameAndDeleted( String name, boolean isDeleted );

    /**
     * Find group by id.
     *
     * @param id the id
     * @return the group
     */
    Group findGroupById( String id );

    /**
     * Save.
     *
     * @param group
     *            the group
     * @return the group
     */
    Group save( Group group );

    /**
     * Find by deleted.
     *
     * @param isDeleted
     *            the is deleted
     * @return the list
     */
    List< Group > findByDeleted( boolean isDeleted );

    /**
     * Find all.
     *
     * @return the list
     */
    List< Group > findAll();
}
