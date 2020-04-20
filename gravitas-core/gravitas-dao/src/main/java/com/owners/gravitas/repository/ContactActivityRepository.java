package com.owners.gravitas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owners.gravitas.domain.entity.ContactActivity;

/**
 * The Interface ContactActivityRepository.
 * 
 * @author pabhishek
 */
public interface ContactActivityRepository extends JpaRepository< ContactActivity, String > {

    /**
     * Find by ownersCom id.
     *
     * @param ownersComId
     *            the ownersCom id
     * @return the set of Contact Activity
     */
	List< ContactActivity > findByOwnersComIdOrderByCreatedDateDesc( final String ownersComId );

}
