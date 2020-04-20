package com.owners.gravitas.business;

import java.util.List;
import java.util.Map;

import com.owners.gravitas.domain.entity.Process;
import com.owners.gravitas.enums.GravitasProcess;

/**
 * The Interface ProcessManagementBusinessService.
 */
public interface ProcessBusinessService {

    /**
     * De active existing process.
     *
     * @param email
     *            the email
     * @param processName
     *            the process name
     */
    Process deActivateProcess( String email, GravitasProcess processName );

    /**
     * De active existing process.
     *
     * @param email
     *            the email
     * @param processName
     *            the process name
     */
    void deActivateAllProcess( String email, GravitasProcess processCode, String status );
    
    
    /**
     * De activate process.
     *
     * @param email
     *            the email
     * @param processName
     *            the process name
     * @param executionId
     *            the execution id
     * @return the process
     */
    Process deActivateProcess( String email, GravitasProcess processName, String executionId );

    /**
     * Start new process.
     *
     * @param email
     *            the email
     * @param crmId
     *            the crm id
     * @param executionId
     *            the execution id
     * @param processName
     *            the process name
     */
    void createProcess( String email, String crmId, String executionId, GravitasProcess processName );

    /**
     * Creates the process.
     *
     * @param email
     *            the email
     * @param executionId
     *            the execution id
     * @param processName
     *            the process name
     */
    void createProcess( String email, String executionId, GravitasProcess processName );

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
     * De activate and signal.
     *
     * @param email
     *            the email
     * @param processName
     *            the process name
     * @param params
     *            the params
     * @return the process dto
     */
    Process deActivateAndSignal( String email, GravitasProcess processName, Map< String, Object > params );

    /**
     * Save.
     *
     * @param process
     *            the process
     */
    void save( Process process );

    /**
     * Gets the active process.
     *
     * @param email
     *            the email
     * @return the active process
     */
    List<Process> getActiveProcess( String email );
}
