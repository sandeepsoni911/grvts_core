package com.owners.gravitas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.owners.gravitas.domain.entity.SystemErrorLog;

/**
 * The Interface SystemErrorLogRepository.
 *
 * @author ankusht
 */
public interface SystemErrorLogRepository extends JpaRepository< SystemErrorLog, String > {

    /**
     * Find all system error.
     *
     * @return the list
     */
    @Query( value = "SELECT * " + "\n FROM gr_system_error_log sel " + "\n   JOIN  " + "\n (SELECT sl.id "
            + "\n FROM gr_scheduler_log sl " + "\n WHERE sl.NAME = 'GRAVITAS_HEALTH_CHECK_JOB' "
            + "\n ORDER BY sl.start_time desc " + "\n LIMIT 1) isl "
            + "\n ON sel.SCHEDULER_LOG_ID = isl.ID ", nativeQuery = true )
    List< SystemErrorLog > findLatestSystemErrors();

}
