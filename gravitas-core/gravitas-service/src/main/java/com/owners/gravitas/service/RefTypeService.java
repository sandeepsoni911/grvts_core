package com.owners.gravitas.service;

import com.owners.gravitas.domain.entity.RefType;

/**
 * The Interface RefTypeService.
 * 
 * @author pabhishek
 */
public interface RefTypeService {

    /**
     * Gets the ref type by type.
     *
     * @param refType
     *            the ref type
     * @return the ref type by type
     */
    RefType getRefTypeByType( String refType );
}
