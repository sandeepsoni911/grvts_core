package com.owners.gravitas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owners.gravitas.domain.entity.Process;

/**
 * The Interface ProcessManagementRepository.
 *
 * @author amits
 */
public interface ProcessRepository extends JpaRepository< Process, String > {

    /**
     * Find by email and process code.
     *
     * @return the process management
     */
    Process findByEmailAndProcessCode( String email, String processCode );

    /**
     * Find by email and process code.
     *
     * @return the process management
     */
    Process findByEmailAndProcessCodeAndStatus( String email, String processCode, String status );
    
    /**
     * Find list of processed by email and process code.
     *
     * @return the process management
     */
    List<Process> findAllByEmailAndProcessCodeAndStatus( String email, String processCode, String status );

    /**
     * Find by email and process code and status and execution id.
     *
     * @param email
     *            the email
     * @param processCode
     *            the process code
     * @param status
     *            the status
     * @param executionId
     *            the execution id
     * @return the process
     */
    Process findByEmailAndProcessCodeAndStatusAndExecutionId( String email, String processCode, String status,
            String executionId );

    /**
     * Find by email and status.
     *
     * @param email
     *            the email
     * @param status
     *            the status
     * @return the process
     */
    List<Process> findByEmailAndStatus( String email, String status );
}
