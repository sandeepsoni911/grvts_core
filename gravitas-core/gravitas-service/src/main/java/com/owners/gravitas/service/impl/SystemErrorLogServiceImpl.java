package com.owners.gravitas.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.domain.entity.SystemErrorLog;
import com.owners.gravitas.repository.SystemErrorLogRepository;
import com.owners.gravitas.service.SystemErrorLogService;

/**
 * The Class SystemErrorLogServiceImpl.
 *
 * @author ankusht
 */
@Service
@Transactional( readOnly = true )
public class SystemErrorLogServiceImpl implements SystemErrorLogService {

    /** The system error log repository. */
    @Autowired
    private SystemErrorLogRepository systemErrorLogRepository;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.SystemErrorLogService#saveSystemErrorLogs(
     * java.util.Collection)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public void saveSystemErrorLogs( final Collection< SystemErrorLog > systemErrorLogs ) {
        systemErrorLogRepository.save( systemErrorLogs );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.SystemErrorLogService#findAllSystemsErrors()
     */
    @Override
    public List< SystemErrorLog > findLatestSystemErrors() {
        return systemErrorLogRepository.findLatestSystemErrors();
    }

}
