package com.owners.gravitas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owners.gravitas.domain.entity.LeadLayout;


public interface LeadLayoutRepository extends JpaRepository< LeadLayout, String > {

    /**
     * Get LeadLayout
     * @param emailId
     * @param type
     * @param source
     * @return LeadLayout
     */
	List<LeadLayout> findByEmailIdAndTypeAndSource( String emailId, String type, String source );
	
}
