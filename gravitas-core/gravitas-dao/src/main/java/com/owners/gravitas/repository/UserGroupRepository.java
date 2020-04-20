package com.owners.gravitas.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owners.gravitas.domain.entity.Group;
import com.owners.gravitas.domain.entity.User;
import com.owners.gravitas.domain.entity.UserGroup;

/**
 * The Interface UserGroupRepository.
 *
 * @author raviz
 */
public interface UserGroupRepository extends JpaRepository< UserGroup, String > {

    /**
     * Find by group.
     *
     * @param group
     *            the group
     * @return the sets the
     */
    Set< UserGroup > findByGroup( final Group group );

    /**
     * Find by user.
     *
     * @param user
     *            the user
     * @return the sets the
     */
    Set< UserGroup > findByUser( final User user );

    /**
     * Delete by group.
     *
     * @param group
     *            the group
     */
    void deleteByGroup( final Group group );

    /**
     * Delete by user.
     *
     * @param user
     *            the user
     */
    void deleteByUser( final User user );

    /**
     * Find by user and group.
     *
     * @param user
     *            the user
     * @param group
     *            the group
     * @return the user group
     */
    UserGroup findByUserAndGroup( final User user, final Group group );

    /**
     * Delete by user and group.
     *
     * @param user
     *            the user
     * @param group
     *            the group
     */
    void deleteByUserAndGroup( final User user, final Group group );

}
