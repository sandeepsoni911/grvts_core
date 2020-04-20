package com.owners.gravitas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owners.gravitas.domain.entity.EmailScrappingDetails;
/**
 * Repository for EmailScrappingDetails entity
 * @author sandeepsoni
 *
 */
public interface EmailScrappingDetailsRepository extends JpaRepository< EmailScrappingDetails, String> {

	
	
}
