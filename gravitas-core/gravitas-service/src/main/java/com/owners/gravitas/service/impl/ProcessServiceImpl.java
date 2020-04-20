package com.owners.gravitas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.domain.entity.Process;
import com.owners.gravitas.enums.GravitasProcess;
import com.owners.gravitas.repository.ProcessRepository;
import com.owners.gravitas.service.ProcessService;

/**
 * The Class ProcessManagementServiceImpl.
 *
 * @author amits
 */
@Service
public class ProcessServiceImpl implements ProcessService {

    /** The process management repository. */
    @Autowired
    private ProcessRepository processManagementRepository;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.ProcessManagementService#save(com.owners.
     * gravitas.domain.entity.ProcessManagement)
     */
    @Override
    public Process save( final Process processMgmt ) {
        return processManagementRepository.save( processMgmt );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.ProcessManagementService#getProcess(java.lang
     * .String, java.lang.String, java.lang.String)
     */
    @Override
    public Process getProcess( final String email, final GravitasProcess processCode, final String status ) {
        return processManagementRepository.findByEmailAndProcessCodeAndStatus( email, processCode.name(), status );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.ProcessService#getProcess(java.lang.String,
     * com.owners.gravitas.enums.GravitasProcess, java.lang.String,
     * java.lang.String)
     */
    @Override
    public Process getProcess( final String email, final GravitasProcess processCode, final String status,
            final String executionId ) {
        return processManagementRepository.findByEmailAndProcessCodeAndStatusAndExecutionId( email, processCode.name(),
                status, executionId );
    }

    /**
     * Gets the active process.
     *
     * @param email
     *            the email
     * @param status
     *            the status
     * @return the active process
     */
    @Override
    public List<Process> getActiveProcess( final String email, final String status ) {
        return processManagementRepository.findByEmailAndStatus( email, status );
    }

    @Override
    public List< Process > getAllProcess( final String email, final GravitasProcess processCode, final String status ) {
        return processManagementRepository.findAllByEmailAndProcessCodeAndStatus( email, processCode.name(), status );
    }
}
