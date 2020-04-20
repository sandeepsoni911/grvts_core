package com.owners.gravitas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owners.gravitas.domain.entity.ContactLog;

/**
 * 
 * @author gururasm
 *
 */
public interface ContactLogRepository extends JpaRepository< ContactLog, String > {
    
    List< ContactLog > findByContactIdOrderByCreatedDateDesc(String contactId);

}
