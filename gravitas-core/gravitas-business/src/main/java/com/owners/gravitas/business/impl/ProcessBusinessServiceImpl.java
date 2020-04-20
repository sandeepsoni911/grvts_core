package com.owners.gravitas.business.impl;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.flowable.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.business.ProcessBusinessService;
import com.owners.gravitas.domain.entity.Process;
import com.owners.gravitas.enums.GravitasProcess;
import com.owners.gravitas.service.HostService;
import com.owners.gravitas.service.ProcessService;

/**
 * The Class ProcessManagementBusinessServiceImpl.
 *
 * @author amits
 */
@Service
@Transactional( readOnly = true )
public class ProcessBusinessServiceImpl implements ProcessBusinessService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( ProcessBusinessServiceImpl.class );

    /** The Constant ACTIVE. */
    protected static final String ACTIVE = "active";

    /** The Constant INACTIVE. */
    protected static final String INACTIVE = "inactive";

    /** The process management service. */
    @Autowired
    private ProcessService processManagementService;

    /** The host service. */
    @Autowired
    private HostService hostService;

    /** The runtime service. */
    @Autowired
    protected RuntimeService runtimeService;

    /**
     * Save process.
     *
     * @param processMgmt
     *            the process mgmt
     * @return the process management
     */
    @Override
    @Transactional( propagation = REQUIRED )
    public void save( final Process process ) {
        LOGGER.info(
                "Saving process for email " + process.getEmail() + " and process code" + process.getProcessCode() );
        processManagementService.save( process );

    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.ProcessBusinessService#getProcess(java.lang.
     * String, java.lang.String, java.lang.String)
     */
    @Override
    public Process getProcess( final String email, final GravitasProcess processCode, final String status ) {
        LOGGER.info( "Getting process for email " + email + " and process code" + processCode );
        return processManagementService.getProcess( email, processCode, status );
    }

    @Override
    public List< Process > getAllProcess( final String email, final GravitasProcess processCode, final String status ) {
        return processManagementService.getAllProcess( email, processCode, status );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.ProcessBusinessService#getProcess(java.lang.
     * String, com.owners.gravitas.enums.GravitasProcess, java.lang.String,
     * java.lang.String)
     */
    @Override
    public Process getProcess( final String email, final GravitasProcess processCode, final String status,
            final String executionId ) {
        LOGGER.info( "Getting process for email " + email + " and process code" + processCode + " and executionId "
                + executionId );
        return processManagementService.getProcess( email, processCode, status, executionId );
    }

    /**
     * Gets the active process.
     *
     * @param email
     *            the email
     * @return the active process
     */
    @Override
    public List<Process> getActiveProcess( final String email ) {
        LOGGER.info( "Getting process for email " + email );
        return processManagementService.getActiveProcess( email, ACTIVE );
    }

    /**
     * De-activate existing process.
     *
     * @param email
     *            the email
     * @param processName
     *            the process name
     */
    @Override
    @Transactional( propagation = REQUIRED )
    public Process deActivateProcess( final String email, final GravitasProcess processName ) {
        final Process process = getProcess( email, processName, ACTIVE );
        
        if (null != process) {
            LOGGER.info( "Process is marked inactive for email " + email + " and process code" + processName );
            process.setStatus( INACTIVE );
            save( process );
        }

        return process;
    }

    /**
     * De-activate existing process.
     *
     * @param email
     *            the email
     * @param processName
     *            the process name
     */
    @Override
    @Transactional( propagation = REQUIRED )
    public void deActivateAllProcess( final String email, final GravitasProcess processName, final String status ) {
        List< Process > processes = processManagementService.getAllProcess( email, processName, ACTIVE );
        
        if (CollectionUtils.isEmpty( processes )) {
            for(Process process : processes) {
            LOGGER.info( "Process is marked inactive for email " + email + " and process code" + processName );
            process.setStatus( INACTIVE );
            save( process );
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.ProcessBusinessService#deActivateProcess(
     * java.lang.String, com.owners.gravitas.enums.GravitasProcess,
     * java.lang.String)
     */
    @Override
    @Transactional( propagation = REQUIRED )
    public Process deActivateProcess( final String email, final GravitasProcess processName,
            final String executionId ) {
        final Process process = getProcess( email, processName, ACTIVE, executionId );
        if (null != process) {
            LOGGER.info( "Process is marked inactive for email " + email + " and process code" + processName
                    + " and execution id " + executionId );
            process.setStatus( INACTIVE );
            save( process );
        }
        return process;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.ProcessBusinessService#deActivateAndSignal(
     * java.lang.String, com.owners.gravitas.enums.GravitasProcess,
     * java.util.Map)
     */
    @Override
    @Transactional( propagation = REQUIRED )
    public Process deActivateAndSignal( final String email, final GravitasProcess processName,
            final Map< String, Object > params ) {
        final Process process = deActivateProcess( email, processName );
        if (null != process) {
            LOGGER.info( "Execution Id::" + process.getExecutionId() );
            if (MapUtils.isNotEmpty( params )) {
                LOGGER.info( "Params::" + params );
                runtimeService.setVariables( process.getExecutionId(), params );
            }
            // runtimeService.signal( process.getExecutionId() );
            runtimeService.trigger( process.getExecutionId() );
        }
        return process;
    }

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
    @Override
    @Transactional( propagation = REQUIRED )
    public void createProcess( final String email, final String crmId, final String executionId,
            final GravitasProcess processName ) {
        deActivateProcess( email, processName, ACTIVE );
        save( buildProcess( email, crmId, executionId, processName ) );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.ProcessBusinessService#createProcess(java.
     * lang.String, java.lang.String, com.owners.gravitas.enums.GravitasProcess)
     */
    @Override
    @Transactional( propagation = REQUIRED )
    public void createProcess( final String email, final String executionId, final GravitasProcess processName ) {
        save( buildProcess( email, null, executionId, processName ) );
    }

    /**
     * Builds the process.
     *
     * @param email
     *            the email
     * @param crmId
     *            the crm id
     * @param executionId
     *            the execution id
     * @param processName
     *            the process name
     * @return the process
     */
    private Process buildProcess( final String email, final String crmId, final String executionId,
            final GravitasProcess processName ) {
        final Process newProcess = new Process();
        newProcess.setCrmId( crmId );
        newProcess.setEmail( email );
        newProcess.setProcessCode( processName.name() );
        newProcess.setExecutionId( executionId );
        newProcess.setStatus( ACTIVE );
        newProcess.setGravitasEngineId( hostService.getHost() );
        return newProcess;
    }
}
