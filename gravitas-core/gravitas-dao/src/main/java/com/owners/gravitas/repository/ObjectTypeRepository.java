package com.owners.gravitas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.domain.entity.AgentCoverage;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.domain.entity.ObjectType;

/**
 * The Interface ObjectTypeRepository.
 *
 * @author shivamm
 */
public interface ObjectTypeRepository extends JpaRepository< ObjectType, String > {

    /**
     * Find by name.
     *
     * @return the object type
     */
    public ObjectType findByName( String name );

}
