package com.owners.gravitas.service;

import java.util.Set;

import com.owners.gravitas.domain.entity.RefCode;
import com.owners.gravitas.domain.entity.RefType;

// TODO: Auto-generated Javadoc
/**
 * The Interface RefCodeService.
 *
 * @author raviz
 */
public interface RefCodeService {

    /**
     * Find by code.
     *
     * @param code
     *            the code
     * @return the ref code
     */
    public RefCode findByCode( String code );

    /**
     * Find ref code by ref type.
     *
     * @param refType
     *            the ref type
     * @return the sets the
     */
    Set< RefCode > findRefCodeByRefType( RefType refType );
}
