package com.owners.gravitas.service;

import java.util.Collection;
import java.util.List;

import com.owners.gravitas.domain.entity.SystemErrorLog;

/**
 * The Interface SystemErrorLogService.
 * 
 * @author ankusht
 */
public interface SystemErrorLogService {

    /**
     * Save system error logs.
     *
     * @param systemErrorLogs
     *            the system error logs
     */
    void saveSystemErrorLogs( final Collection< SystemErrorLog > systemErrorLogs );

    /**
     * Find all systems errors.
     *
     * @return the list
     */
    List< SystemErrorLog > findLatestSystemErrors();
}
