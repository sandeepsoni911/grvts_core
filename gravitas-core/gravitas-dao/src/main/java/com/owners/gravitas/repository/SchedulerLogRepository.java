package com.owners.gravitas.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.owners.gravitas.domain.entity.SchedulerLog;

/**
 * The Interface SchedulerLogRepository.
 *
 * @author ankusht
 */
public interface SchedulerLogRepository extends JpaRepository< SchedulerLog, String > {

    /**
     * Find by scheduler name order by end time desc.
     *
     * @param schedulerName
     *            the scheduler name
     * @param pageRequest
     *            the page request
     * @return the page
     */
    Page< SchedulerLog > findBySchedulerNameOrderByEndTimeDesc( final String schedulerName,
            final Pageable pageRequest );
}
