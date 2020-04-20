package com.owners.gravitas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owners.gravitas.domain.entity.RefType;

/**
 * The Interface RefTypeRepository.
 * 
 * @author pabhishek
 */
public interface RefTypeRepository extends JpaRepository< RefType, String > {

    /**
     * Find by type.
     *
     * @param refType
     *            the ref type
     * @return the ref type
     */
    RefType findByType( String refType );
}
