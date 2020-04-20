package com.owners.gravitas.service.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.domain.entity.SchedulerLog;
import com.owners.gravitas.enums.JobType;
import com.owners.gravitas.repository.SchedulerLogRepository;
import com.owners.gravitas.service.HostService;
import com.owners.gravitas.service.SchedulerLogService;

/**
 * The Class SchedulerLogServiceImpl.
 *
 * @author ankusht
 */
@Service
@Transactional( readOnly = true )
public class SchedulerLogServiceImpl implements SchedulerLogService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( SchedulerLogServiceImpl.class );

    /** The host service. */
    @Autowired
    private HostService hostService;

    /** The scheduler log repository. */
    @Autowired
    private SchedulerLogRepository schedulerLogRepository;

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.SchedulerLogService#startJob(com.owners.
     * gravitas.enums.JobType)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public SchedulerLog startJob( final JobType jobType ) {
        LOGGER.info( "Starting the job " + jobType );
        final SchedulerLog schedulerLog = new SchedulerLog();
        schedulerLog.setSchedulerName( jobType.name() );
        schedulerLog.setStartTime( DateTime.now() );
        schedulerLog.setHostName( hostService.getHost() );
        return schedulerLogRepository.save( schedulerLog );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.SchedulerLogService#endJob(com.owners.
     * gravitas.domain.entity.SchedulerLog)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public void endJob( final SchedulerLog schedulerLog ) {
        LOGGER.debug( "Inside end job" );
        if (schedulerLog != null) {
            schedulerLog.setEndTime( DateTime.now() );
            schedulerLogRepository.save( schedulerLog );
            LOGGER.debug( "Job ended for " + schedulerLog.getSchedulerName() );
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.SchedulerLogService#fetchRecord(java.lang.
     * String, org.springframework.data.domain.Pageable)
     */
    @Override
    public List< SchedulerLog > findBySchedulerName( final String schedulerName, final Pageable page ) {
        return schedulerLogRepository.findBySchedulerNameOrderByEndTimeDesc( schedulerName, page ).getContent();
    }
}
