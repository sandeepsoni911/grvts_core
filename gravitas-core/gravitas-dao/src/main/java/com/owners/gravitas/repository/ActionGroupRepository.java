package com.owners.gravitas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owners.gravitas.domain.entity.ActionGroup;

/**
 * The Interface ActionGroupRepository.
 *
 * @author shivamm
 */
public interface ActionGroupRepository extends JpaRepository< ActionGroup, String > {

    public List< ActionGroup > findAll();

}
