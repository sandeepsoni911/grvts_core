package com.owners.gravitas.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.SchedulerLog;
import com.owners.gravitas.enums.JobType;
import com.owners.gravitas.repository.SchedulerLogRepository;
import com.owners.gravitas.service.HostService;

/**
 * The Class SchedulerLogServiceImplTest.
 *
 * @author ankusht
 */
public class SchedulerLogServiceImplTest extends AbstractBaseMockitoTest {

    /** The host service. */
    @Mock
    private HostService hostService;

    /** The scheduler log repository. */
    @Mock
    private SchedulerLogRepository schedulerLogRepository;

    /** The scheduler log service impl. */
    @InjectMocks
    private SchedulerLogServiceImpl schedulerLogServiceImpl;

    /**
     * Test start job.
     */
    @Test
    public void testStartJob() {
        SchedulerLog schedulerLog = new SchedulerLog();
        when( schedulerLogRepository.save( any( SchedulerLog.class ) ) ).thenReturn( schedulerLog );
        SchedulerLog schedulerLog2 = schedulerLogServiceImpl.startJob( JobType.GRAVITAS_HEALTH_CHECK_JOB );
        Assert.assertEquals( schedulerLog, schedulerLog2 );
    }

    /**
     * Test end job.
     */
    @Test
    public void testEndJob() {
        SchedulerLog schedulerLog = new SchedulerLog();
        schedulerLogServiceImpl.endJob( schedulerLog );
        verify( schedulerLogRepository ).save( schedulerLog );
    }

    /**
     * Test find by scheduler name.
     */
    @Test
    public void testFindBySchedulerName() {
        final String schedulerName = "testName";
        final Pageable page = new PageRequest( 1, 1 );
        when( schedulerLogRepository.findBySchedulerNameOrderByEndTimeDesc( schedulerName, page ) )
                .thenReturn( new PageImpl<>( new ArrayList< SchedulerLog >() ) );
        final List< SchedulerLog > names = schedulerLogServiceImpl.findBySchedulerName( schedulerName, page );
        assertNotNull( names );
        verify( schedulerLogRepository ).findBySchedulerNameOrderByEndTimeDesc( schedulerName, page );
    }
}
