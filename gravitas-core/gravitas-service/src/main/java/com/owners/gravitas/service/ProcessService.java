package com.owners.gravitas.service;

import java.util.List;

import com.owners.gravitas.domain.entity.Process;
import com.owners.gravitas.enums.GravitasProcess;

/**
 * The Interface ProcessManagementService.
 */
public interface ProcessService {

    /**
     * Save.
     *
     * @param processMgmt
     *            the process mgmt
     * @return the process management
     */
    Process save( Process processMgmt );

    /**
     * Gets the process.
     *
     * @param email
     *            the email
     * @param processCode
     *            the process code
     * @param status
     *            the status
     * @return the process
     */
    Process getProcess( String email, GravitasProcess processCode, String status );
    
    
    /**
     * Find list of processed by email and process code.
     * 
     * @param email
     * @param processCode
     * @param status
     * @return
     */
    List<Process> getAllProcess( String email, GravitasProcess processCode, String status );

    /**
     * Gets the process.
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
    Process getProcess( String email, GravitasProcess processCode, String status, String executionId );

    /**
     * Gets the active process.
     *
     * @param email
     *            the email
     * @param status
     *            the status
     * @return the active process
     */
    List<Process> getActiveProcess( String email, String status );
}
