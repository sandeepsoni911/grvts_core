package com.owners.gravitas.service;

import com.owners.gravitas.domain.entity.ObjectAttributeConfig;
import com.owners.gravitas.domain.entity.ObjectType;

/**
 * The Interface ObjectAttributeConfigService.
 *
 * @author shivamm
 */
public interface ObjectAttributeConfigService {

    /**
     * Find by attribute name and object type.
     *
     * @param attributeName
     *            the attribute name
     * @param objectType
     *            the object type
     * @return the object attribute config
     */
    ObjectAttributeConfig getObjectAttributeConfig( String attributeName, ObjectType objectType );
}
