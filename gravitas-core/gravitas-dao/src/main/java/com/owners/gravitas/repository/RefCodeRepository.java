package com.owners.gravitas.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owners.gravitas.domain.entity.RefCode;
import com.owners.gravitas.domain.entity.RefType;

/**
 * The Interface RefCodeRepository.
 *
 * @author raviz
 */
public interface RefCodeRepository extends JpaRepository< RefCode, String > {

    /**
     * Find by code.
     *
     * @param refCode
     *            the ref code
     * @return the ref code
     */
    RefCode findByCode( final String refCode );

    /**
     * Find by ref type.
     *
     * @param refType
     *            the ref type
     * @return the sets the
     */
    Set< RefCode > findByRefType( final RefType refType );
}
