package com.owners.gravitas.service;

import com.owners.gravitas.domain.entity.ObjectType;

/**
 * The Interface ObjectTypeService.
 *
 * @author shivamm
 */
public interface ObjectTypeService {

    /**
     * Find by name.
     *
     * @param string
     *            the string
     * @return the list
     */
    ObjectType findByName( String string );

}
