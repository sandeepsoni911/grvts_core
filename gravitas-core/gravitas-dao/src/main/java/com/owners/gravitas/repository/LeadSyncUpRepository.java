package com.owners.gravitas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owners.gravitas.domain.entity.LeadSyncUp;

/**
 * The Interface LeadSyncUpRepository.
 *
 * @author kushaja
 */
public interface LeadSyncUpRepository extends JpaRepository< LeadSyncUp, String > {
   
}
