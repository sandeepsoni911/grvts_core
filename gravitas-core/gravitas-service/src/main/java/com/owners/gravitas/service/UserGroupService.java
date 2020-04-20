package com.owners.gravitas.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.owners.gravitas.domain.entity.Group;
import com.owners.gravitas.domain.entity.User;
import com.owners.gravitas.domain.entity.UserGroup;

/**
 * The Interface UserGroupService.
 *
 * @author raviz
 */
public interface UserGroupService {

    /**
     * Save.
     *
     * @param userGroup
     *            the user group
     * @return the user group
     */
    UserGroup save( UserGroup userGroup );

    /**
     * Save.
     *
     * @param userGroups
     *            the user groups
     * @return the list
     */
    List< UserGroup > save( Collection< UserGroup > userGroups );

    /**
     * Find by group.
     *
     * @param group
     *            the group
     * @return the sets
     */
    Set< UserGroup > findByGroup( Group group );

    /**
     * Find by user.
     *
     * @param user
     *            the user
     * @return the sets the
     */
    Set< UserGroup > findByUser( User user );

    /**
     * Delete by group and flush.
     *
     * @param group
     *            the group
     */
    void deleteByGroupAndFlush( Group group );

    /**
     * Delete by user and flush.
     *
     * @param user
     *            the user
     */
    void deleteByUserAndFlush( User user );

    /**
     * Find by user and group.
     *
     * @param user
     *            the user
     * @param group
     *            the group
     * @return the user group
     */
    UserGroup findByUserAndGroup( User user, Group group );

    /**
     * Delete by user and group.
     *
     * @param user
     *            the user
     * @param group
     *            the group
     */
    void deleteByUserAndGroup( User user, Group group );

}
