package com.owners.gravitas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owners.gravitas.domain.entity.ObjectAttributeConfig;
import com.owners.gravitas.domain.entity.ObjectType;

/**
 * The Interface ObjectAttributeConfigRepositorys.
 *
 * @author shivamm
 */
public interface ObjectAttributeConfigRepository extends JpaRepository< ObjectAttributeConfig, String > {

    /**
     * Find by attribute name and object type.
     *
     * @param attributeName
     *            the attribute name
     * @param objectType
     *            the object type
     * @return the object attribute config
     */
    public ObjectAttributeConfig findByAttributeNameAndObjectType( String attributeName, ObjectType objectType );
}
