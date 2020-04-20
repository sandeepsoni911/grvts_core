package com.owners.gravitas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owners.gravitas.domain.entity.LeadEmailParsingLog;

/**
 * The Interface LeadEmailParsingLogRepository.
 *
 * @author amits
 */
public interface LeadEmailParsingLogRepository extends JpaRepository< LeadEmailParsingLog, String > {

}
