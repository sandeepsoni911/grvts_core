package com.owners.gravitas.service;

import com.owners.gravitas.domain.entity.SchedulerLog;
import com.owners.gravitas.enums.JobType;

import java.util.List;

import org.springframework.data.domain.Pageable;

/**
 * The Interface SchedulerLogService.
 *
 * @author ankusht
 */
public interface SchedulerLogService {

    /**
     * Starts a job.
     *
     * @param jobType
     *            the job type
     * @return the scheduler log
     */
    SchedulerLog startJob( final JobType jobType );

    /**
     * Ends a job.
     *
     * @param schedulerLog
     *            the scheduler log
     * @return the scheduler log
     */
    void endJob( final SchedulerLog schedulerLog );

    /**
     * Find by scheduler name.
     *
     * @param schedulerName the scheduler name
     * @param page the page
     * @return the list
     */
    List< SchedulerLog > findBySchedulerName( final String schedulerName, final Pageable page );
}
