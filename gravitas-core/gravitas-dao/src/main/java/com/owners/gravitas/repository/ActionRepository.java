package com.owners.gravitas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owners.gravitas.domain.entity.Action;

/**
 * The Interface ActionRepository.
 *
 * @author raviz
 */
public interface ActionRepository extends JpaRepository< Action, String > {

    /**
     * Find by action name.
     *
     * @param actionName
     *            the action name
     * @return the action
     */
    Action findByActionName( final String actionName );

}
