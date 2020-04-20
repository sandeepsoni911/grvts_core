package com.owners.gravitas.business.impl;

import static com.owners.gravitas.enums.JobType.OPPORTUNITY_MAPPING_JOB;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dao.AgentOpportunityDao;
import com.owners.gravitas.domain.entity.SchedulerLog;
import com.owners.gravitas.service.AgentService;
import com.owners.gravitas.service.SchedulerLogService;

/**
 * The Class AgentAnalyicsBusinessServiceImplTest.
 *
 * @author vishwanathm
 */
public class AgentAnalyicsBusinessServiceImplTest extends AbstractBaseMockitoTest {

    /** The agent analyics business service impl. */
    @InjectMocks
    private AgentAnalyicsBusinessServiceImpl agentAnalyicsBusinessServiceImpl;

    /** The AgentHolder dao. */
    @Mock
    private AgentService agentService;

    /** The scheduler log service. */
    @Mock
    private SchedulerLogService schedulerLogService;

    /** The agent opportunity dao. */
    @Mock
    private AgentOpportunityDao agentOpportunityDao;

    /** The test. */
    @Test
    public void testStartAgentAnalytics() {
        final Set< String > agentIds = new HashSet<>();
        agentIds.add( "abc" );
        agentIds.add( "pqr" );
        agentIds.add( "xyz" );
        final Map< String, String > mappedAgentIds = new HashMap<>();
        mappedAgentIds.put( "abc1", "abc1@abc1.com" );

        Mockito.when( agentService.getAllAgentIds() ).thenReturn( agentIds );
        Mockito.when( agentService.getAllMappedAgentIds() ).thenReturn( mappedAgentIds );
        Mockito.when( agentService.getAgentEmailById( Mockito.anyString() ) ).thenReturn( "XYZ" );
        agentAnalyicsBusinessServiceImpl.startAgentAnalytics();

        Mockito.verify( agentService ).saveAgentUidMapping( Mockito.anyList() );
    }

    /**
     * Test start agent analytics with no agent registered.
     */
    @Test
    public void testStartAgentAnalytics_WithNoAgentRegistered() {
        final Set< String > agentIds = new HashSet<>();
        agentIds.add( "abc1" );
        final Map< String, String > mappedAgentIds = new HashMap<>();
        mappedAgentIds.put( "abc1", "abc1@abc1.com" );

        Mockito.when( agentService.getAllAgentIds() ).thenReturn( agentIds );
        Mockito.when( agentService.getAllMappedAgentIds() ).thenReturn( mappedAgentIds );
        Mockito.when( agentService.getAgentEmailById( Mockito.anyString() ) ).thenReturn( "XYZ" );
        agentAnalyicsBusinessServiceImpl.startAgentAnalytics();

        Mockito.verify( agentService, Mockito.times( 1 ) ).getAllAgentIds();
        Mockito.verify( agentService, Mockito.times( 1 ) ).getAllMappedAgentIds();
    }

    /** The test. */
    @Test
    public void testMapFbCrmOppIds() {

        final Map< String, Map > map = new HashMap<>();
        final Map< String, Map > map1 = new HashMap<>();
        map1.putAll( map );
        final Set< String > agentIds = new HashSet<>();
        agentIds.add( "test" );
        final SchedulerLog schedulerLog = new SchedulerLog();
        schedulerLog.setEndTime( new DateTime() );
        final List< SchedulerLog > list = new ArrayList<>();
        list.add( schedulerLog );
        Mockito.when( agentService.getAllAgentIds() ).thenReturn( agentIds );
        Mockito.when(
                schedulerLogService.findBySchedulerName( OPPORTUNITY_MAPPING_JOB.toString(), new PageRequest( 0, 1 ) ) )
                .thenReturn( list );
        Mockito.when( agentOpportunityDao.getOpportunitiesFromTime( "test", schedulerLog.getEndTime().getMillis() ) )
                .thenReturn( map1 );
        agentAnalyicsBusinessServiceImpl.opportunityMappingAnalytics();
        Mockito.doNothing().when( agentService ).saveOpportunityMapping( new ArrayList<>() );

        Mockito.verify( agentOpportunityDao ).getOpportunitiesFromTime( Mockito.anyString(), Mockito.anyLong() );
        Mockito.verify( schedulerLogService ).findBySchedulerName( Mockito.anyString(),
                Mockito.any( PageRequest.class ) );
    }

    /**
     * Test opportunity mapping analytics.
     */
    @Test
    public void testOpportunityMappingAnalytics() {
        final Set< String > agentIds = new HashSet<>();
        final String id1 = "agent1@a.com";
        final String id2 = "agent2@a.com";
        agentIds.add( id1 );
        agentIds.add( id2 );

        when( agentService.getAllAgentIds() ).thenReturn( agentIds );
        final List< SchedulerLog > schedulerLogList = new ArrayList<>();
        final SchedulerLog log = new SchedulerLog();
        final DateTime endTime = DateTime.now();
        log.setEndTime( endTime );
        schedulerLogList.add( log );
        final Map< String, Map > map1 = new HashMap<>();
        final Map valueMap1 = new HashMap<>();
        valueMap1.put( "crmId", "crmId1" );
        map1.put( "key1", valueMap1 );
        final Map< String, Map > map2 = new HashMap<>();
        final Map valueMap2 = new HashMap<>();
        valueMap2.put( "crmId", "crmId2" );
        map2.put( "key2", valueMap2 );

        when( schedulerLogService.findBySchedulerName( anyString(), any( Pageable.class ) ) )
                .thenReturn( schedulerLogList );
        when( agentOpportunityDao.getOpportunitiesFromTime( id1, endTime.getMillis() ) ).thenReturn( map1 );
        when( agentOpportunityDao.getOpportunitiesFromTime( id2, endTime.getMillis() ) ).thenReturn( map2 );

        agentAnalyicsBusinessServiceImpl.opportunityMappingAnalytics();

        verify( agentService ).saveOpportunityMapping( anyList() );
        verify( agentService ).getAllAgentIds();
        verify( schedulerLogService ).findBySchedulerName( anyString(), any( Pageable.class ) );
        verify( agentOpportunityDao ).getOpportunitiesFromTime( id1, endTime.getMillis() );
        verify( agentOpportunityDao ).getOpportunitiesFromTime( id2, endTime.getMillis() );
    }
}
