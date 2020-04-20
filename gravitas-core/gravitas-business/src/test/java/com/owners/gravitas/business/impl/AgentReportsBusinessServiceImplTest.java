package com.owners.gravitas.business.impl;

import static com.owners.gravitas.business.impl.AgentReportsBusinessServiceImpl.BUYER_OPPORTUNITIES;
import static com.owners.gravitas.business.impl.AgentReportsBusinessServiceImpl.CLOSED_OPPORTUNITIES_COUNT;
import static com.owners.gravitas.business.impl.AgentReportsBusinessServiceImpl.PENDING_OPPORTUNITIES_COUNT;
import static com.owners.gravitas.business.impl.AgentReportsBusinessServiceImpl.RESPONSE_TIME;
import static com.owners.gravitas.business.impl.AgentReportsBusinessServiceImpl.SELLER_OPPORTUNITIES;
import static com.owners.gravitas.constants.Constants.CALL_KEY;
import static com.owners.gravitas.constants.Constants.EMAIL_KEY;
import static com.owners.gravitas.constants.Constants.PERCENTAGE_SIGN;
import static com.owners.gravitas.constants.Constants.SMS_KEY;
import static com.owners.gravitas.constants.Constants.TOTAL_KEY;
import static com.owners.gravitas.enums.RecordType.BUYER;
import static com.owners.gravitas.enums.RecordType.SELLER;
import static com.owners.gravitas.enums.UserStatusType.INACTIVE;
import static com.owners.gravitas.enums.UserStatusType.ONBOARDING;
import static com.owners.gravitas.util.DateUtil.DEFAULT_CRM_DATE_PATTERN;
import static com.owners.gravitas.util.DateUtil.getReadableTime;
import static com.owners.gravitas.util.DateUtil.toSqlDate;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.apache.poi.ss.usermodel.Workbook;
import org.joda.time.DateTime;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.owners.gravitas.business.AgentPerformanceMetricsTask;
import com.owners.gravitas.business.builder.AgentAppCalendarEventsResponseBuilder;
import com.owners.gravitas.business.builder.AgentStatisticsBuilder;
import com.owners.gravitas.business.builder.response.AgentsResponseBuilder;
import com.owners.gravitas.business.task.AgentReportTask;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.AgentMetricsJmxConfig;
import com.owners.gravitas.config.BestFitAgentConfig;
import com.owners.gravitas.config.ClientStatisticsConfig;
import com.owners.gravitas.constants.UserRole;
import com.owners.gravitas.domain.Agent;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.domain.entity.AgentPerformanceLog;
import com.owners.gravitas.domain.entity.AgentStatistics;
import com.owners.gravitas.domain.entity.AgentTask;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.User;
import com.owners.gravitas.dto.ApiUser;
import com.owners.gravitas.dto.CalendarEvent;
import com.owners.gravitas.dto.ClientStatisticsDTO;
import com.owners.gravitas.dto.crm.response.AgentStatisticsResponse;
import com.owners.gravitas.dto.response.AgentCumulativeResponse;
import com.owners.gravitas.dto.response.AgentStateResponse;
import com.owners.gravitas.dto.response.AgentsResponse;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.CalendarEventListResponse;
import com.owners.gravitas.dto.response.ClientFirstResponseTime;
import com.owners.gravitas.dto.response.ClientStatisticsResponse;
import com.owners.gravitas.enums.UserStatusType;
import com.owners.gravitas.exception.AgentInvalidException;
import com.owners.gravitas.repository.OpportunityRepository;
import com.owners.gravitas.service.AgentDetailsService;
import com.owners.gravitas.service.AgentPerformanceLogService;
import com.owners.gravitas.service.AgentReportService;
import com.owners.gravitas.service.AgentService;
import com.owners.gravitas.service.AgentStatisticsService;
import com.owners.gravitas.service.AgentTaskService;
import com.owners.gravitas.service.ContactEntityService;
import com.owners.gravitas.service.OpportunityService;
import com.owners.gravitas.service.SearchService;
import com.owners.gravitas.service.UserService;
import com.owners.gravitas.util.DateUtil;
import com.owners.gravitas.util.GravitasWebUtil;
import com.owners.gravitas.util.TimeZoneUtil;

/**
 * The Class AgentReportsBusinessServiceImplTest.
 *
 * @author ankusht
 */
@PrepareForTest( DateUtil.class )
public class AgentReportsBusinessServiceImplTest extends AbstractBaseMockitoTest {

    /** The agent performance metrics service. */
    @Mock
    private AgentPerformanceLogService agentPerformanceMetricsService;

    /** The agent performance metrics task. */
    @Mock
    private AgentPerformanceMetricsTask agentPerformanceMetricsTask;

    /** The agent service. */
    @Mock
    private AgentService agentService;

    /** The agent metrics jmx config. */
    @Mock
    private AgentMetricsJmxConfig agentMetricsJmxConfig;

    /** The opportunity repository. */
    @Mock
    private OpportunityRepository opportunityV1Repository;

    /** The agent performance log service. */
    @Mock
    private AgentPerformanceLogService agentPerformanceLogService;

    /** The agent performance metrics business service impl. */
    @InjectMocks
    private AgentReportsBusinessServiceImpl agentReportsBusinessServiceImpl;

    /** The user service. */
    @Mock
    private UserService userService;

    /** The opportunity service. */
    @Mock
    private OpportunityService opportunityService;

    /** The agent details service. */
    @Mock
    private AgentDetailsService agentDetailsService;

    /** The best fit agent config. */
    @Mock
    private BestFitAgentConfig bestFitAgentConfig;

    /** The agents response builder. */
    @Mock
    private AgentsResponseBuilder agentsResponseBuilder;

    /** The agent analytics service. */
    @Mock
    private AgentStatisticsService agentAnalyticsService;

    /** The agent analytics builder. */
    @Mock
    private AgentStatisticsBuilder agentAnalyticsBuilder;

    /** The client statistics config. */
    @Mock
    private ClientStatisticsConfig clientStatisticsConfig;

    /** The agent report task. */
    @Mock
    private AgentReportTask agentReportTask;

    /** The contact service v1. */
    @Mock
    private ContactEntityService contactServiceV1;

    /** The gravitas web util. */
    @Mock
    private GravitasWebUtil gravitasWebUtil;

    /** The agent report service. */
    @Mock
    private AgentReportService agentReportService;

    @Mock
    private SearchService searchService;

    @Mock
    private AgentTaskService agentTaskService;

    @Mock
    private AgentAppCalendarEventsResponseBuilder calendarEventsResponseBuilder;
    
    @Mock
    private TimeZoneUtil timeZoneUtil;

    /**
     * Sets the up.
     */
    @BeforeMethod
    public void setUp() {
        ReflectionTestUtils.setField( agentReportsBusinessServiceImpl, "fbAssignedDate", 0l );
    }

    /**
     * Test evaluate agent performance metrics.
     */
    @Test
    public void testEvaluateAgentPerformanceMetrics() {
        final Set< String > list = new HashSet<>();
        final String agentId = "agent1";
        list.add( agentId );
        when( agentService.getAllAgentIds() ).thenReturn( list );
        doNothing().when( agentPerformanceMetricsTask ).computeAndSavePerformanceMetrics( agentId );
        agentReportsBusinessServiceImpl.evaluateAgentPerformanceMetrics();
        verify( agentPerformanceMetricsTask ).computeAndSavePerformanceMetrics( agentId );
    }

    /**
     * Gets the agent performance log.
     *
     * @return the agent performance log
     */
    @DataProvider( name = "getAgentPerformanceLog" )
    public Object[][] getAgentPerformanceLog() {
        final AgentPerformanceLog agentPerformanceLog = new AgentPerformanceLog();
        agentPerformanceLog.setBuyerOpportunitiesPercentage( 50.0 );
        agentPerformanceLog.setSellerOpportunitiesPercentage( 50.0 );
        agentPerformanceLog.setPendingTransactions( 1 );
        agentPerformanceLog.setClosedTransactions( 1 );
        final List< AgentPerformanceLog > list = new ArrayList<>();
        list.add( agentPerformanceLog );
        return new Object[][] { { list } };
    }

    /**
     * Test get performance log for valid inputs. Gets empty agent performance
     * log.
     *
     * @return the empty agent performance log
     */
    @DataProvider( name = "getEmptyAgentPerformanceLog" )
    public Object[][] getEmptyAgentPerformanceLog() {
        final AgentPerformanceLog agentPerformanceLog = new AgentPerformanceLog();
        final List< AgentPerformanceLog > list = new ArrayList<>();
        list.add( agentPerformanceLog );
        return new Object[][] { { list } };
    }

    /**
     * Test get performance log should return empty response if JMX is disabled.
     */
    @Test
    public void testGetPerformanceLogShouldReturnEmptyResponseIfJMXIsDisabled() {
        final String agentId = "dummyId";
        final Integer timeFrame = 0;
        when( agentMetricsJmxConfig.isAgentPerformanceMetricsJobEnabled() ).thenReturn( false );
        final Map< String, Object > actual = agentReportsBusinessServiceImpl.getPerformanceLog( agentId, timeFrame );
        assertEquals( 0, actual.size() );
        verify( agentMetricsJmxConfig ).isAgentPerformanceMetricsJobEnabled();
        verifyZeroInteractions( agentPerformanceMetricsService );
    }

    /**
     * Test get performance log should return null if JMX is disabled. Test get
     * performance log should return empty response if agent performance log not
     * found.
     */
    @Test
    public void testGetPerformanceLogShouldReturnEmptyResponseIfAgentPerformanceLogNotFound() {
        final String agentId = "dummyId";
        final Integer timeFrame = 0;
        final Pageable top = new PageRequest( 0, 1 );
        when( agentMetricsJmxConfig.isAgentPerformanceMetricsJobEnabled() ).thenReturn( true );
        when( agentPerformanceLogService.findLatestMetricByAgentFbId( agentId, top ) ).thenReturn( null );

        final Map< String, Object > actual = agentReportsBusinessServiceImpl.getPerformanceLog( agentId, timeFrame );
        assertEquals( 0, actual.size() );
        verify( agentMetricsJmxConfig ).isAgentPerformanceMetricsJobEnabled();
        verifyZeroInteractions( agentPerformanceMetricsService );
    }

    /**
     * Test get performance log should return empty response if jmx is disabled.
     *
     * @param list
     *            the list
     */
    @Test( dataProvider = "getAgentPerformanceLog" )
    public void testGetPerformanceLogShouldReturnEmptyResponseIfJmxIsDisabled(
            final List< AgentPerformanceLog > list ) {
        final String agentId = "dummyId";
        final Integer timeFrame = 0;
        final Pageable top = new PageRequest( 0, 1 );

        when( agentMetricsJmxConfig.isAgentPerformanceMetricsJobEnabled() ).thenReturn( true );
        when( agentPerformanceLogService.findLatestMetricByAgentFbId( agentId, top ) ).thenReturn( list );
        when( agentMetricsJmxConfig.isPendingTransactionsCalcEnabled() ).thenReturn( false );
        when( agentMetricsJmxConfig.isClosedTransactionsCalcEnabled() ).thenReturn( false );
        when( agentMetricsJmxConfig.isResponseTimeCalcEnabled() ).thenReturn( false );
        when( agentMetricsJmxConfig.isBuyerOppoPctgCalcEnabled() ).thenReturn( false );
        when( agentMetricsJmxConfig.isSellerOppoPctgCalcEnabled() ).thenReturn( false );

        final Map< String, Object > actual = agentReportsBusinessServiceImpl.getPerformanceLog( agentId, timeFrame );
        assertEquals( 0, actual.size() );
        verify( agentMetricsJmxConfig ).isAgentPerformanceMetricsJobEnabled();
        verifyZeroInteractions( agentPerformanceMetricsService );
    }

    /**
     * Test get performance log should return empty response if empty agent
     * performance log.
     *
     * @param list
     *            the list
     */
    @Test( dataProvider = "getEmptyAgentPerformanceLog" )
    public void testGetPerformanceLogShouldReturnEmptyResponseIfEmptyAgentPerformanceLog(
            final List< AgentPerformanceLog > list ) {
        final String agentId = "dummyId";
        final Integer timeFrame = 0;
        when( agentMetricsJmxConfig.isAgentPerformanceMetricsJobEnabled() ).thenReturn( true );
        final Pageable top = new PageRequest( 0, 1 );
        final Agent agent = new Agent();
        final Map< String, Opportunity > opportunities = new HashMap<>();
        opportunities.put( "key", new Opportunity() );
        agent.setOpportunities( opportunities );
        final List< Long > responseTimes = new ArrayList<>();
        responseTimes.add( null );
        responseTimes.add( 0L );

        when( agentPerformanceLogService.findLatestMetricByAgentFbId( agentId, top ) ).thenReturn( list );
        when( agentMetricsJmxConfig.isPendingTransactionsCalcEnabled() ).thenReturn( true );
        when( agentMetricsJmxConfig.isClosedTransactionsCalcEnabled() ).thenReturn( true );
        when( agentMetricsJmxConfig.isResponseTimeCalcEnabled() ).thenReturn( true );
        when( agentMetricsJmxConfig.isBuyerOppoPctgCalcEnabled() ).thenReturn( true );
        when( agentMetricsJmxConfig.isSellerOppoPctgCalcEnabled() ).thenReturn( true );
        when( agentService.getAgentById( agentId ) ).thenReturn( agent );
        when( opportunityV1Repository.findResponseTimeByOpportunityIds( Mockito.anySet(), Mockito.any() ) )
                .thenReturn( responseTimes );

        final Map< String, Object > expected = new LinkedHashMap<>();
        expected.put( RESPONSE_TIME, "N/A" );

        final Map< String, Object > actual = agentReportsBusinessServiceImpl.getPerformanceLog( agentId, timeFrame );
        assertEquals( expected, actual );
        verify( agentMetricsJmxConfig ).isAgentPerformanceMetricsJobEnabled();
        verify( agentPerformanceLogService ).findLatestMetricByAgentFbId( agentId, top );
    }

    /**
     * Test get performance log for valid inputs.
     *
     * @param list
     *            the list
     */
    // @Test( dataProvider = "getAgentPerformanceLog" )
    public void testGetPerformanceLogForValidInputs( final List< AgentPerformanceLog > list ) {
        final String agentId = "dummyId";
        final Integer timeFrame = 0;
        when( agentMetricsJmxConfig.isAgentPerformanceMetricsJobEnabled() ).thenReturn( true );
        final Pageable top = new PageRequest( 0, 1 );
        final AgentPerformanceLog agentPerformanceLog = list.get( 0 );
        final Agent agent = new Agent();
        final Map< String, Opportunity > opportunities = new HashMap<>();
        opportunities.put( "key", new Opportunity() );
        agent.setOpportunities( opportunities );
        final List< Long > responseTimes = new ArrayList<>();
        responseTimes.add( 120000L );

        when( agentPerformanceLogService.findLatestMetricByAgentFbId( agentId, top ) ).thenReturn( list );
        when( agentMetricsJmxConfig.isPendingTransactionsCalcEnabled() ).thenReturn( true );
        when( agentMetricsJmxConfig.isClosedTransactionsCalcEnabled() ).thenReturn( true );
        when( agentMetricsJmxConfig.isResponseTimeCalcEnabled() ).thenReturn( true );
        when( agentMetricsJmxConfig.isBuyerOppoPctgCalcEnabled() ).thenReturn( true );
        when( agentMetricsJmxConfig.isSellerOppoPctgCalcEnabled() ).thenReturn( true );
        when( agentService.getAgentById( agentId ) ).thenReturn( agent );
        when( opportunityV1Repository.findResponseTimeByOpportunityIds( Mockito.anySet(), Mockito.any() ) )
                .thenReturn( responseTimes );

        final Map< String, Object > expected = new LinkedHashMap<>();
        expected.put( BUYER_OPPORTUNITIES, agentPerformanceLog.getBuyerOpportunitiesPercentage() + PERCENTAGE_SIGN );
        expected.put( SELLER_OPPORTUNITIES, agentPerformanceLog.getSellerOpportunitiesPercentage() + PERCENTAGE_SIGN );
        expected.put( PENDING_OPPORTUNITIES_COUNT, agentPerformanceLog.getPendingTransactions().toString() );
        expected.put( CLOSED_OPPORTUNITIES_COUNT, agentPerformanceLog.getClosedTransactions().toString() );
        expected.put( RESPONSE_TIME, "2 mins " );

        final Map< String, Object > actual = agentReportsBusinessServiceImpl.getPerformanceLog( agentId, timeFrame );
        assertEquals( expected, actual );
        verify( agentMetricsJmxConfig ).isAgentPerformanceMetricsJobEnabled();
        verify( agentPerformanceLogService ).findLatestMetricByAgentFbId( agentId, top );
    }

    /**
     * Test get performance log for valid inputs 2.
     *
     * @param list
     *            the list
     */
    // @Test( dataProvider = "getAgentPerformanceLog" )
    public void testGetPerformanceLogForValidInputs2( final List< AgentPerformanceLog > list ) {
        final String agentId = "dummyId";
        final Integer timeFrame = 0;
        when( agentMetricsJmxConfig.isAgentPerformanceMetricsJobEnabled() ).thenReturn( true );
        final Pageable top = new PageRequest( 0, 1 );
        final AgentPerformanceLog agentPerformanceLog = list.get( 0 );
        final Agent agent = new Agent();
        final Map< String, Opportunity > opportunities = new HashMap<>();
        opportunities.put( "key", new Opportunity() );
        agent.setOpportunities( opportunities );
        final List< Long > responseTimes = new ArrayList<>();
        responseTimes.add( 120000L );
        responseTimes.add( 120000L );

        when( agentPerformanceLogService.findLatestMetricByAgentFbId( agentId, top ) ).thenReturn( list );
        when( agentMetricsJmxConfig.isPendingTransactionsCalcEnabled() ).thenReturn( true );
        when( agentMetricsJmxConfig.isClosedTransactionsCalcEnabled() ).thenReturn( true );
        when( agentMetricsJmxConfig.isResponseTimeCalcEnabled() ).thenReturn( true );
        when( agentMetricsJmxConfig.isBuyerOppoPctgCalcEnabled() ).thenReturn( true );
        when( agentMetricsJmxConfig.isSellerOppoPctgCalcEnabled() ).thenReturn( true );
        when( agentService.getAgentById( agentId ) ).thenReturn( agent );
        when( opportunityV1Repository.findResponseTimeByOpportunityIds( Mockito.anySet(), Mockito.any() ) )
                .thenReturn( responseTimes );

        final Map< String, Object > expected = new LinkedHashMap<>();
        expected.put( BUYER_OPPORTUNITIES, agentPerformanceLog.getBuyerOpportunitiesPercentage() + PERCENTAGE_SIGN );
        expected.put( SELLER_OPPORTUNITIES, agentPerformanceLog.getSellerOpportunitiesPercentage() + PERCENTAGE_SIGN );
        expected.put( PENDING_OPPORTUNITIES_COUNT, agentPerformanceLog.getPendingTransactions().toString() );
        expected.put( CLOSED_OPPORTUNITIES_COUNT, agentPerformanceLog.getClosedTransactions().toString() );
        expected.put( RESPONSE_TIME, "2 mins " );

        final Map< String, Object > actual = agentReportsBusinessServiceImpl.getPerformanceLog( agentId, timeFrame );
        assertEquals( expected, actual );
        verify( agentMetricsJmxConfig ).isAgentPerformanceMetricsJobEnabled();
        verify( agentPerformanceLogService ).findLatestMetricByAgentFbId( agentId, top );
    }

    /**
     * Test get performance log for valid inputs 3.
     *
     * @param list
     *            the list
     */
    // @Test( dataProvider = "getAgentPerformanceLog" )
    public void testGetPerformanceLogForValidInputs3( final List< AgentPerformanceLog > list ) {
        final String agentId = "dummyId";
        final Integer timeFrame = 0;
        when( agentMetricsJmxConfig.isAgentPerformanceMetricsJobEnabled() ).thenReturn( true );
        final Pageable top = new PageRequest( 0, 1 );
        final AgentPerformanceLog agentPerformanceLog = list.get( 0 );
        final Agent agent = new Agent();
        final Map< String, Opportunity > opportunities = new HashMap<>();
        opportunities.put( "key", new Opportunity() );
        agent.setOpportunities( opportunities );
        final List< Long > responseTimes = new ArrayList<>();
        responseTimes.add( 18120000L );
        responseTimes.add( 18120000L );

        when( agentPerformanceLogService.findLatestMetricByAgentFbId( agentId, top ) ).thenReturn( list );
        when( agentMetricsJmxConfig.isPendingTransactionsCalcEnabled() ).thenReturn( true );
        when( agentMetricsJmxConfig.isClosedTransactionsCalcEnabled() ).thenReturn( true );
        when( agentMetricsJmxConfig.isResponseTimeCalcEnabled() ).thenReturn( true );
        when( agentMetricsJmxConfig.isBuyerOppoPctgCalcEnabled() ).thenReturn( true );
        when( agentMetricsJmxConfig.isSellerOppoPctgCalcEnabled() ).thenReturn( true );
        when( agentService.getAgentById( agentId ) ).thenReturn( agent );
        when( opportunityV1Repository.findResponseTimeByOpportunityIds( Mockito.anySet(), Mockito.any() ) )
                .thenReturn( responseTimes );

        final Map< String, Object > expected = new LinkedHashMap<>();
        expected.put( BUYER_OPPORTUNITIES, agentPerformanceLog.getBuyerOpportunitiesPercentage() + PERCENTAGE_SIGN );
        expected.put( SELLER_OPPORTUNITIES, agentPerformanceLog.getSellerOpportunitiesPercentage() + PERCENTAGE_SIGN );
        expected.put( PENDING_OPPORTUNITIES_COUNT, agentPerformanceLog.getPendingTransactions().toString() );
        expected.put( CLOSED_OPPORTUNITIES_COUNT, agentPerformanceLog.getClosedTransactions().toString() );
        expected.put( RESPONSE_TIME, "5 hrs 2 mins " );

        final Map< String, Object > actual = agentReportsBusinessServiceImpl.getPerformanceLog( agentId, timeFrame );
        assertEquals( expected, actual );
        verify( agentMetricsJmxConfig ).isAgentPerformanceMetricsJobEnabled();
        verify( agentPerformanceLogService ).findLatestMetricByAgentFbId( agentId, top );
    }

    /**
     * Test get performance log for valid inputs 4.
     *
     * @param list
     *            the list
     */
    // @Test( dataProvider = "getAgentPerformanceLog" )
    public void testGetPerformanceLogForValidInputs4( final List< AgentPerformanceLog > list ) {
        final String agentId = "dummyId";
        final Integer timeFrame = 0;
        when( agentMetricsJmxConfig.isAgentPerformanceMetricsJobEnabled() ).thenReturn( true );
        final Pageable top = new PageRequest( 0, 1 );
        final AgentPerformanceLog agentPerformanceLog = list.get( 0 );
        final Agent agent = new Agent();
        final Map< String, Opportunity > opportunities = new HashMap<>();
        opportunities.put( "key", new Opportunity() );
        agent.setOpportunities( opportunities );
        final List< Long > responseTimes = new ArrayList<>();
        responseTimes.add( 111120000L );
        responseTimes.add( 111120000L );

        when( agentPerformanceLogService.findLatestMetricByAgentFbId( agentId, top ) ).thenReturn( list );
        when( agentMetricsJmxConfig.isPendingTransactionsCalcEnabled() ).thenReturn( true );
        when( agentMetricsJmxConfig.isClosedTransactionsCalcEnabled() ).thenReturn( true );
        when( agentMetricsJmxConfig.isResponseTimeCalcEnabled() ).thenReturn( true );
        when( agentMetricsJmxConfig.isBuyerOppoPctgCalcEnabled() ).thenReturn( true );
        when( agentMetricsJmxConfig.isSellerOppoPctgCalcEnabled() ).thenReturn( true );
        when( agentService.getAgentById( agentId ) ).thenReturn( agent );
        when( opportunityV1Repository.findResponseTimeByOpportunityIds( Mockito.anySet(), Mockito.any() ) )
                .thenReturn( responseTimes );

        final Map< String, Object > expected = new LinkedHashMap<>();
        expected.put( BUYER_OPPORTUNITIES, agentPerformanceLog.getBuyerOpportunitiesPercentage() + PERCENTAGE_SIGN );
        expected.put( SELLER_OPPORTUNITIES, agentPerformanceLog.getSellerOpportunitiesPercentage() + PERCENTAGE_SIGN );
        expected.put( PENDING_OPPORTUNITIES_COUNT, agentPerformanceLog.getPendingTransactions().toString() );
        expected.put( CLOSED_OPPORTUNITIES_COUNT, agentPerformanceLog.getClosedTransactions().toString() );
        expected.put( RESPONSE_TIME, "1 day 6 hrs 52 mins " );

        final Map< String, Object > actual = agentReportsBusinessServiceImpl.getPerformanceLog( agentId, timeFrame );
        assertEquals( expected, actual );
        verify( agentMetricsJmxConfig ).isAgentPerformanceMetricsJobEnabled();
        verify( agentPerformanceLogService ).findLatestMetricByAgentFbId( agentId, top );
    }

    /**
     * Test get manager opportunity report for buyer opportunity.
     */
    @Test
    public void testGetManagerOpportunityReportForBuyerOpportunity() {
        final String email = "test@test.com";
        final User user = new User();
        user.setEmail( email );
        user.setStatus( "ACTIVE" );
        final AgentDetails agent = new AgentDetails();
        agent.setId( "test" );
        agent.setScore( 0.0 );
        agent.setAvailability( true );
        agent.setUser( user );

        final List< AgentDetails > agents = new ArrayList<>();
        agents.add( agent );

        final List< UserStatusType > statuses = new ArrayList<>();
        statuses.add( INACTIVE );
        statuses.add( ONBOARDING );

        final com.owners.gravitas.domain.entity.Opportunity opportunity = new com.owners.gravitas.domain.entity.Opportunity();
        /*opportunity.setCrmId( "test" );
        opportunity.setType( "Buyer" );
        opportunity.setStage( "Writing Offer" );*/
        opportunity.setResponseTime( 1000L );
        opportunity.setAssignedDate( new DateTime() );
        opportunity.setCreatedBy( "testAgent" );
        final Contact con = new Contact();
        con.setStage( "test" );
        opportunity.setContact( con );
        final List< com.owners.gravitas.domain.entity.Opportunity > agentOpportunities = new ArrayList<>();
        agentOpportunities.add( opportunity );

        when( userService.getUsersByManagingBroker( email ) ).thenReturn( agents );
        when( opportunityService.getAgentOpportunities( email ) ).thenReturn( agentOpportunities );

        final Map< String, Object > expected = agentReportsBusinessServiceImpl.getManagerOpportunityReport( email );
        assertNotNull( expected );
        verify( userService ).getUsersByManagingBroker( email );
        verify( opportunityService ).getAgentOpportunities( email );
    }

    /**
     * Test get manager opportunity report for seller opportunity.
     */
    @Test
    public void testGetManagerOpportunityReportForSellerOpportunity() {
        final String email = "test@test.com";
        final User user = new User();
        user.setEmail( email );
        user.setStatus( "ACTIVE" );
        final AgentDetails agent = new AgentDetails();
        agent.setId( "test" );
        agent.setScore( 0.0 );
        agent.setAvailability( true );
        agent.setUser( user );

        final List< AgentDetails > agents = new ArrayList<>();
        agents.add( agent );

        final List< UserStatusType > statuses = new ArrayList<>();
        statuses.add( INACTIVE );
        statuses.add( ONBOARDING );

        final com.owners.gravitas.domain.entity.Opportunity opportunity = new com.owners.gravitas.domain.entity.Opportunity();
        // opportunity.setCrmId( "test" );
        // opportunity.setType( "Seller" );
        // opportunity.setStage( "Writing Offer" );
        opportunity.setCreatedBy( "testAgent" );
        opportunity.setResponseTime( 1000L );
        opportunity.setAssignedDate( new DateTime() );
        final Contact con = new Contact();
        con.setStage( "test" );
        opportunity.setContact( con );
        final List< com.owners.gravitas.domain.entity.Opportunity > agentOpportunities = new ArrayList<>();
        agentOpportunities.add( opportunity );

        when( userService.getUsersByManagingBroker( email ) ).thenReturn( agents );
        when( opportunityService.getAgentOpportunities( email ) ).thenReturn( agentOpportunities );

        final Map< String, Object > expected = agentReportsBusinessServiceImpl.getManagerOpportunityReport( email );
        assertNotNull( expected );
        verify( userService ).getUsersByManagingBroker( email );
        verify( opportunityService ).getAgentOpportunities( email );
    }

    /**
     * Test get agent opportunity report.
     */
    @Test
    public void testGetAgentOpportunityReport() {
        final String email = "test@test.com";
        final User user = new User();
        user.setEmail( email );
        user.setStatus( "ACTIVE" );
        final AgentDetails agent = new AgentDetails();
        agent.setId( "test" );
        agent.setScore( 0.0 );
        agent.setAvailability( true );
        agent.setUser( user );

        final List< AgentDetails > agents = new ArrayList<>();
        agents.add( agent );

        final List< UserStatusType > statuses = new ArrayList<>();
        statuses.add( INACTIVE );
        statuses.add( ONBOARDING );

        final com.owners.gravitas.domain.entity.Opportunity opportunity = new com.owners.gravitas.domain.entity.Opportunity();
        /*opportunity.setCrmId( "test" );
        opportunity.setType( "Buyer" );
        opportunity.setStage( "Writing Offer" );*/
        opportunity.setCreatedBy( "testAgent" );
        opportunity.setResponseTime( 1000L );
        opportunity.setAssignedDate( new DateTime() );
        final Contact con = new Contact();
        con.setStage( "test" );
        opportunity.setContact( con );
        final List< com.owners.gravitas.domain.entity.Opportunity > agentOpportunities = new ArrayList<>();
        agentOpportunities.add( opportunity );

        when( agentDetailsService.findAgentByEmail( email ) ).thenReturn( agent );
        when( opportunityService.getAgentOpportunities( email ) ).thenReturn( agentOpportunities );

        final Map< String, Object > expected = agentReportsBusinessServiceImpl.getAgentOpportunityReport( email );
        assertNotNull( expected );
        verify( agentDetailsService ).findAgentByEmail( email );
        verify( opportunityService ).getAgentOpportunities( email );

    }

    /**
     * Test get manager summary.
     */
    @Test
    public void testGetManagerSummary() {
        final String email = "test@test.com";
        final User user = new User();
        user.setEmail( email );
        user.setStatus( "ACTIVE" );
        final AgentDetails agent = new AgentDetails();
        agent.setId( "test" );
        agent.setScore( 0.0 );
        agent.setAvailability( true );
        agent.setUser( user );

        final List< AgentDetails > agents = new ArrayList<>();
        agents.add( agent );

        final com.owners.gravitas.domain.entity.Opportunity opportunity = new com.owners.gravitas.domain.entity.Opportunity();
        /*opportunity.setCrmId( "test" );
        opportunity.setType( "Buyer" );
        opportunity.setStage( "Writing Offer" );*/
        opportunity.setCreatedBy( "testAgent" );
        opportunity.setResponseTime( 1000L );
        opportunity.setAssignedDate( new DateTime() );
        final Contact con = new Contact();
        con.setStage( "test" );
        opportunity.setContact( con );
        final List< com.owners.gravitas.domain.entity.Opportunity > agentOpportunities = new ArrayList<>();
        agentOpportunities.add( opportunity );

        when( userService.getUsersByManagingBroker( email ) ).thenReturn( agents );
        when( opportunityService.getAgentOpportunities( email ) ).thenReturn( agentOpportunities );
        when( bestFitAgentConfig.getDefaultDayThreshold() ).thenReturn( 0 );

        final Map< String, Object > expected = agentReportsBusinessServiceImpl.getManagerSummary( email );
        assertNotNull( expected );
        verify( userService ).getUsersByManagingBroker( email );
        verify( opportunityService ).getAgentOpportunities( email );
        verify( bestFitAgentConfig ).getDefaultDayThreshold();

    }

    /**
     * Test get agent summary.
     */
    @Test
    public void testGetAgentSummary() {
        final String email = "test@test.com";
        final User user = new User();
        user.setEmail( email );
        user.setStatus( "ACTIVE" );
        final AgentDetails agent = new AgentDetails();
        agent.setId( "test" );
        agent.setScore( 0.0 );
        agent.setAvailability( true );
        agent.setUser( user );

        final List< AgentDetails > agents = new ArrayList<>();
        agents.add( agent );

        final List< UserStatusType > statuses = new ArrayList<>();
        statuses.add( INACTIVE );
        statuses.add( ONBOARDING );

        final com.owners.gravitas.domain.entity.Opportunity opportunity = new com.owners.gravitas.domain.entity.Opportunity();
        /* opportunity.setCrmId( "test" );
        opportunity.setType( "Buyer" );
        opportunity.setStage( "Writing Offer" );*/
        opportunity.setCreatedBy( "testAgent" );
        opportunity.setResponseTime( 1000L );
        opportunity.setAssignedDate( new DateTime() );
        final Contact con = new Contact();
        con.setStage( "test" );
        opportunity.setContact( con );
        final List< com.owners.gravitas.domain.entity.Opportunity > agentOpportunities = new ArrayList<>();
        agentOpportunities.add( opportunity );

        when( agentDetailsService.findAgentByEmail( email ) ).thenReturn( agent );
        when( opportunityService.getAgentOpportunities( email ) ).thenReturn( agentOpportunities );
        when( bestFitAgentConfig.getDefaultDayThreshold() ).thenReturn( 0 );

        final Map< String, Object > expected = agentReportsBusinessServiceImpl.getAgentSummary( email );
        assertNotNull( expected );
        verify( agentDetailsService ).findAgentByEmail( email );
        verify( opportunityService ).getAgentOpportunities( email );
        verify( bestFitAgentConfig ).getDefaultDayThreshold();
    }

    /**
     * Test get agents score performance.
     */
    @Test
    public void testGetAgentsScorePerformance() {
        final String email = "test@test.com";
        final User user = new User();
        user.setEmail( email );
        user.setStatus( "ACTIVE" );

        final List< User > users = new ArrayList<>();
        users.add( user );

        final AgentDetails agent = new AgentDetails();
        agent.setId( "test" );
        agent.setScore( 0.0 );
        agent.setAvailability( true );
        agent.setUser( user );

        final List< AgentDetails > agents = new ArrayList<>();
        agents.add( agent );

        final List< String > userEmailList = new ArrayList<>();
        userEmailList.add( "test@test.com" );

        final com.google.api.services.admin.directory.model.User googleUser = new com.google.api.services.admin.directory.model.User();
        googleUser.setEmails( userEmailList );
        final List< com.google.api.services.admin.directory.model.User > googleUsersList = new ArrayList<>();
        googleUsersList.add( googleUser );

        final com.owners.gravitas.dto.Agent agentDto = new com.owners.gravitas.dto.Agent();
        agentDto.setEmail( "test@test.com" );
        agentDto.setFirstName( "test" );
        agentDto.setLastName( "test" );

        final List< com.owners.gravitas.dto.Agent > agentList = new ArrayList<>();
        agentList.add( agentDto );
        final AgentsResponse response = new AgentsResponse();
        response.setAgents( agentList );

        when( userService.getUsersByManagingBroker( email ) ).thenReturn( agents );
        when( userService.getUsersByEmails( userEmailList ) ).thenReturn( googleUsersList );
        when( agentsResponseBuilder.convertTo( googleUsersList ) ).thenReturn( response );

        final List< com.owners.gravitas.dto.Agent > agentDtoList = agentReportsBusinessServiceImpl
                .getAgentsScorePerformance( email );

        assertNotNull( agentDtoList );
        verify( userService ).getUsersByManagingBroker( email );
        verify( userService ).getUsersByEmails( userEmailList );
        verify( agentsResponseBuilder ).convertTo( googleUsersList );

    }

    /**
     * Test get aet agent score statistics.
     */
    @Test
    public void testGetAetAgentScoreStatistics() {
        final String email = "test@test.com";
        final User user = new User();
        user.setId( "test" );
        final AgentDetails agentDetails = new AgentDetails();
        agentDetails.setUser( user );
        final AgentStatistics agentAnalytics = new AgentStatistics();
        agentAnalytics.setId( "id" );
        agentAnalytics.setAgentDetails( agentDetails );
        agentAnalytics.setKey( "score" );
        agentAnalytics.setValue( "5" );
        agentAnalytics.setCreatedBy( "test" );
        agentAnalytics.setCreatedDate( new DateTime() );

        final List< AgentStatistics > agentList = new ArrayList< AgentStatistics >();
        agentList.add( agentAnalytics );

        when( agentAnalyticsService.getAgentScoreStatistics( email ) ).thenReturn( agentList );
        when( agentAnalyticsBuilder.convertTo( agentList ) ).thenReturn( new AgentStatisticsResponse() );

        agentReportsBusinessServiceImpl.getAgentScoreStatistics( email );

        verify( agentAnalyticsService ).getAgentScoreStatistics( email );
        verify( agentAnalyticsBuilder ).convertTo( agentList );

    }

    /**
     * Test perform score statistics.
     */
    @Test
    public void testPerformScoreStatistics() {
        final User user = new User();
        user.setId( "test" );
        final AgentDetails agents = new AgentDetails();
        agents.setId( "test" );
        agents.setUser( user );
        agents.setScore( 2.5 );

        final List< AgentDetails > agentDetailsList = new ArrayList< AgentDetails >();
        agentDetailsList.add( agents );

        when( agentDetailsService.findAll() ).thenReturn( agentDetailsList );

        agentReportsBusinessServiceImpl.performScoreAnalytics();
        verify( agentDetailsService ).findAll();
    }

    /**
     * Test get client statistics report should not use default dates when start
     * and end dates are passed.
     */
    @Test
    public void testGetClientStatisticsReportShouldNotUseDefaultDatesWhenStartAndEndDatesArePassed() {
        final String agentEmail = "testAgentEmail";
        final String fromDate = "2000-01-01";
        final String toDate = "2005-01-01";
        final Date fromDtm = new Date( 1L );
        final Date toDtm = new Date( 2L );
        final Map< String, Map< String, String > > stageMedianDetails = new HashMap<>();
        final Map< String, String > buyerStageMedianData = new HashMap<>();
        buyerStageMedianData.put( "New", "1 Day" );
        buyerStageMedianData.put( "Claimed", "1 Day" );
        final Map< String, String > sellerStageMedianData = new HashMap<>();
        sellerStageMedianData.put( "New", "2 Day" );
        stageMedianDetails.put( BUYER.getType(), buyerStageMedianData );
        stageMedianDetails.put( SELLER.getType(), sellerStageMedianData );

        final Map< String, Map< String, Map< String, String > > > stageCtaDetails = new HashMap<>();
        final Map< String, String > buyerStageCta = new HashMap<>();
        buyerStageCta.put( CALL_KEY, "1" );
        buyerStageCta.put( SMS_KEY, "2" );
        buyerStageCta.put( EMAIL_KEY, "3" );
        buyerStageCta.put( TOTAL_KEY, "4" );
        final Map< String, Map< String, String > > buyerStageCtaData = new HashMap<>();
        buyerStageCtaData.put( "New", buyerStageCta );

        final Map< String, String > sellerStageCta = new HashMap<>();
        sellerStageCta.put( CALL_KEY, "5" );
        sellerStageCta.put( SMS_KEY, "6" );
        sellerStageCta.put( EMAIL_KEY, "7" );
        sellerStageCta.put( TOTAL_KEY, "8" );
        final Map< String, Map< String, String > > sellerStageCtaData = new HashMap<>();
        sellerStageCtaData.put( "New", sellerStageCta );

        stageCtaDetails.put( BUYER.getType(), buyerStageCtaData );
        stageCtaDetails.put( SELLER.getType(), sellerStageCtaData );

        mockStatic( DateUtil.class );
        PowerMockito.when( toSqlDate( fromDate, DEFAULT_CRM_DATE_PATTERN ) ).thenReturn( fromDtm );
        PowerMockito.when( toSqlDate( toDate, DEFAULT_CRM_DATE_PATTERN ) ).thenReturn( toDtm );
        when( agentReportTask.getAgentsStagewiseMedian( agentEmail, fromDtm, toDtm ) ).thenAnswer( invocation -> {
            final Future< Map< String, Map< String, String > > > future = Mockito.mock( FutureTask.class );
            when( future.isDone() ).thenReturn( false, false, true );
            when( future.get() ).thenReturn( stageMedianDetails );
            return future;
        } );

        when( agentReportTask.getAgentsStagewiseCta( fromDtm, toDtm, agentEmail ) ).thenAnswer( invocation -> {
            final Future< Map< String, Map< String, Map< String, String > > > > future = Mockito
                    .mock( FutureTask.class );
            when( future.isDone() ).thenReturn( false, false, true );
            when( future.get() ).thenReturn( stageCtaDetails );
            return future;
        } );

        final ClientStatisticsResponse clientStatisticsReport = agentReportsBusinessServiceImpl
                .getClientStatisticsReport( fromDate, toDate, agentEmail );
        final Map< String, Map< String, ClientStatisticsDTO > > ctaData = clientStatisticsReport.getCtaData();
        final Map< String, ClientStatisticsDTO > buyerMap = ctaData.get( BUYER.getType() );
        final ClientStatisticsDTO dto = buyerMap.get( "New" );
        assertNotNull( dto );
        assertEquals( dto.getDuration(), "1 Day" );
        assertEquals( dto.getCall(), "1" );
        assertEquals( dto.getSms(), "2" );
        assertEquals( dto.getEmail(), "3" );
        assertEquals( dto.getTotal(), "4" );

        final ClientStatisticsDTO dto1 = buyerMap.get( "Claimed" );
        assertNotNull( dto1 );
        assertEquals( dto1.getDuration(), "1 Day" );
        assertNull( dto1.getCall() );
        assertNull( dto1.getSms() );
        assertNull( dto1.getEmail() );
        assertNull( dto1.getTotal() );

        final Map< String, ClientStatisticsDTO > sellerMap = ctaData.get( SELLER.getType() );
        final ClientStatisticsDTO dto3 = sellerMap.get( "New" );
        assertNotNull( dto3 );
        assertEquals( dto3.getDuration(), "2 Day" );
        assertEquals( dto3.getCall(), "5" );
        assertEquals( dto3.getSms(), "6" );
        assertEquals( dto3.getEmail(), "7" );
        assertEquals( dto3.getTotal(), "8" );

        verifyZeroInteractions( clientStatisticsConfig );
        verifyStatic( times( 2 ) );
        toSqlDate( Mockito.anyString(), Mockito.anyString() );
        verify( agentReportTask ).getAgentsStagewiseMedian( agentEmail, fromDtm, toDtm );
        verify( agentReportTask ).getAgentsStagewiseCta( fromDtm, toDtm, agentEmail );
    }

    /**
     * Test get client statistics report should use default dates when empty
     * start and end dates are passed.
     */
    @Test
    public void testGetClientStatisticsReportShouldUseDefaultDatesWhenEmptyStartAndEndDatesArePassed() {
        final String agentEmail = "testAgentEmail";
        final String defaultFromDate = "2016-01-01";
        final String defaultToDate = "2020-01-01";
        final Date fromDtm = new Date( 1L );
        final Date toDtm = new Date( 2L );
        final Map< String, Map< String, String > > stageMedianDetails = new HashMap<>();
        final Map< String, String > buyerStageMedianData = new HashMap<>();
        buyerStageMedianData.put( "New", "1 Day" );
        final Map< String, String > sellerStageMedianData = new HashMap<>();
        sellerStageMedianData.put( "New", "2 Day" );
        stageMedianDetails.put( BUYER.getType(), buyerStageMedianData );
        stageMedianDetails.put( SELLER.getType(), sellerStageMedianData );

        final Map< String, Map< String, Map< String, String > > > stageCtaDetails = new HashMap<>();
        final Map< String, String > buyerStageCta = new HashMap<>();
        buyerStageCta.put( CALL_KEY, "1" );
        buyerStageCta.put( SMS_KEY, "2" );
        buyerStageCta.put( EMAIL_KEY, "3" );
        buyerStageCta.put( TOTAL_KEY, "4" );
        final Map< String, Map< String, String > > buyerStageCtaData = new HashMap<>();
        buyerStageCtaData.put( "New", buyerStageCta );

        final Map< String, String > sellerStageCta = new HashMap<>();
        sellerStageCta.put( CALL_KEY, "5" );
        sellerStageCta.put( SMS_KEY, "6" );
        sellerStageCta.put( EMAIL_KEY, "7" );
        sellerStageCta.put( TOTAL_KEY, "8" );
        final Map< String, Map< String, String > > sellerStageCtaData = new HashMap<>();
        sellerStageCtaData.put( "New", sellerStageCta );

        stageCtaDetails.put( BUYER.getType(), buyerStageCtaData );
        stageCtaDetails.put( SELLER.getType(), sellerStageCtaData );

        when( clientStatisticsConfig.getFromDate() ).thenReturn( defaultFromDate );
        when( clientStatisticsConfig.getToDate() ).thenReturn( defaultToDate );
        mockStatic( DateUtil.class );
        PowerMockito.when( toSqlDate( defaultFromDate, DEFAULT_CRM_DATE_PATTERN ) ).thenReturn( fromDtm );
        PowerMockito.when( toSqlDate( defaultToDate, DEFAULT_CRM_DATE_PATTERN ) ).thenReturn( toDtm );
        when( agentReportTask.getAgentsStagewiseMedian( agentEmail, fromDtm, toDtm ) ).thenAnswer( invocation -> {
            final Future< Map< String, Map< String, String > > > future = Mockito.mock( FutureTask.class );
            when( future.isDone() ).thenReturn( false, false, true );
            when( future.get() ).thenReturn( stageMedianDetails );
            return future;
        } );

        when( agentReportTask.getAgentsStagewiseCta( fromDtm, toDtm, agentEmail ) ).thenAnswer( invocation -> {
            final Future< Map< String, Map< String, Map< String, String > > > > future = Mockito
                    .mock( FutureTask.class );
            when( future.isDone() ).thenReturn( false, false, true );
            when( future.get() ).thenReturn( stageCtaDetails );
            return future;
        } );

        final ClientStatisticsResponse clientStatisticsReport = agentReportsBusinessServiceImpl
                .getClientStatisticsReport( "", "", agentEmail );
        final Map< String, Map< String, ClientStatisticsDTO > > ctaData = clientStatisticsReport.getCtaData();
        final Map< String, ClientStatisticsDTO > buyerMap = ctaData.get( BUYER.getType() );
        final ClientStatisticsDTO dto = buyerMap.get( "New" );
        assertNotNull( dto );
        assertEquals( dto.getDuration(), "1 Day" );
        assertEquals( dto.getCall(), "1" );
        assertEquals( dto.getSms(), "2" );
        assertEquals( dto.getEmail(), "3" );
        assertEquals( dto.getTotal(), "4" );

        final Map< String, ClientStatisticsDTO > sellerMap = ctaData.get( SELLER.getType() );
        final ClientStatisticsDTO dto3 = sellerMap.get( "New" );
        assertNotNull( dto3 );
        assertEquals( dto3.getDuration(), "2 Day" );
        assertEquals( dto3.getCall(), "5" );
        assertEquals( dto3.getSms(), "6" );
        assertEquals( dto3.getEmail(), "7" );
        assertEquals( dto3.getTotal(), "8" );

        verify( clientStatisticsConfig ).getFromDate();
        verify( clientStatisticsConfig ).getToDate();
        verifyStatic( times( 2 ) );
        toSqlDate( Mockito.anyString(), Mockito.anyString() );
        verify( agentReportTask ).getAgentsStagewiseMedian( agentEmail, fromDtm, toDtm );
        verify( agentReportTask ).getAgentsStagewiseCta( fromDtm, toDtm, agentEmail );
    }

    /**
     * Test get client statistics report should use default dates when start
     * date is not empty and end date is empty.
     */
    @Test
    public void testGetClientStatisticsReportShouldUseDefaultDatesWhenStartDateIsNotEmptyAndEndDateIsEmpty() {
        final String agentEmail = "testAgentEmail";
        final String defaultFromDate = "2016-01-01";
        final String defaultToDate = "2020-01-01";
        final Date fromDtm = new Date( 1L );
        final Date toDtm = new Date( 2L );
        final Map< String, Map< String, String > > stageMedianDetails = new HashMap<>();
        final Map< String, String > buyerStageMedianData = new HashMap<>();
        buyerStageMedianData.put( "New", "1 Day" );
        final Map< String, String > sellerStageMedianData = new HashMap<>();
        sellerStageMedianData.put( "New", "2 Day" );
        stageMedianDetails.put( BUYER.getType(), buyerStageMedianData );
        stageMedianDetails.put( SELLER.getType(), sellerStageMedianData );

        final Map< String, Map< String, Map< String, String > > > stageCtaDetails = new HashMap<>();
        final Map< String, String > buyerStageCta = new HashMap<>();
        buyerStageCta.put( CALL_KEY, "1" );
        buyerStageCta.put( SMS_KEY, "2" );
        buyerStageCta.put( EMAIL_KEY, "3" );
        buyerStageCta.put( TOTAL_KEY, "4" );
        final Map< String, Map< String, String > > buyerStageCtaData = new HashMap<>();
        buyerStageCtaData.put( "New", buyerStageCta );

        final Map< String, String > sellerStageCta = new HashMap<>();
        sellerStageCta.put( CALL_KEY, "5" );
        sellerStageCta.put( SMS_KEY, "6" );
        sellerStageCta.put( EMAIL_KEY, "7" );
        sellerStageCta.put( TOTAL_KEY, "8" );
        final Map< String, Map< String, String > > sellerStageCtaData = new HashMap<>();
        sellerStageCtaData.put( "New", sellerStageCta );

        stageCtaDetails.put( BUYER.getType(), buyerStageCtaData );
        stageCtaDetails.put( SELLER.getType(), sellerStageCtaData );

        when( clientStatisticsConfig.getFromDate() ).thenReturn( defaultFromDate );
        when( clientStatisticsConfig.getToDate() ).thenReturn( defaultToDate );
        mockStatic( DateUtil.class );
        PowerMockito.when( toSqlDate( defaultFromDate, DEFAULT_CRM_DATE_PATTERN ) ).thenReturn( fromDtm );
        PowerMockito.when( toSqlDate( defaultToDate, DEFAULT_CRM_DATE_PATTERN ) ).thenReturn( toDtm );
        when( agentReportTask.getAgentsStagewiseMedian( agentEmail, fromDtm, toDtm ) ).thenAnswer( invocation -> {
            final Future< Map< String, Map< String, String > > > future = Mockito.mock( FutureTask.class );
            when( future.isDone() ).thenReturn( false, false, true );
            when( future.get() ).thenReturn( stageMedianDetails );
            return future;
        } );

        when( agentReportTask.getAgentsStagewiseCta( fromDtm, toDtm, agentEmail ) ).thenAnswer( invocation -> {
            final Future< Map< String, Map< String, Map< String, String > > > > future = Mockito
                    .mock( FutureTask.class );
            when( future.isDone() ).thenReturn( false, false, true );
            when( future.get() ).thenReturn( stageCtaDetails );
            return future;
        } );

        final ClientStatisticsResponse clientStatisticsReport = agentReportsBusinessServiceImpl
                .getClientStatisticsReport( "2000-01-01", "", agentEmail );
        final Map< String, Map< String, ClientStatisticsDTO > > ctaData = clientStatisticsReport.getCtaData();
        final Map< String, ClientStatisticsDTO > buyerMap = ctaData.get( BUYER.getType() );
        final ClientStatisticsDTO dto = buyerMap.get( "New" );
        assertNotNull( dto );
        assertEquals( dto.getDuration(), "1 Day" );
        assertEquals( dto.getCall(), "1" );
        assertEquals( dto.getSms(), "2" );
        assertEquals( dto.getEmail(), "3" );
        assertEquals( dto.getTotal(), "4" );

        final Map< String, ClientStatisticsDTO > sellerMap = ctaData.get( SELLER.getType() );
        final ClientStatisticsDTO dto3 = sellerMap.get( "New" );
        assertNotNull( dto3 );
        assertEquals( dto3.getDuration(), "2 Day" );
        assertEquals( dto3.getCall(), "5" );
        assertEquals( dto3.getSms(), "6" );
        assertEquals( dto3.getEmail(), "7" );
        assertEquals( dto3.getTotal(), "8" );

        verify( clientStatisticsConfig ).getFromDate();
        verify( clientStatisticsConfig ).getToDate();
        verifyStatic( times( 2 ) );
        toSqlDate( Mockito.anyString(), Mockito.anyString() );
        verify( agentReportTask ).getAgentsStagewiseMedian( agentEmail, fromDtm, toDtm );
        verify( agentReportTask ).getAgentsStagewiseCta( fromDtm, toDtm, agentEmail );
    }

    /**
     * Test get client statistics report should use default dates when start
     * date is empty and end date is not empty.
     */
    @Test
    public void testGetClientStatisticsReportShouldUseDefaultDatesWhenStartDateIsEmptyAndEndDateIsNotEmpty() {
        final String agentEmail = "testAgentEmail";
        final String defaultFromDate = "2016-01-01";
        final String defaultToDate = "2020-01-01";
        final Date fromDtm = new Date( 1L );
        final Date toDtm = new Date( 2L );
        final Map< String, Map< String, String > > stageMedianDetails = new HashMap<>();
        final Map< String, String > buyerStageMedianData = new HashMap<>();
        buyerStageMedianData.put( "New", "1 Day" );
        final Map< String, String > sellerStageMedianData = new HashMap<>();
        sellerStageMedianData.put( "New", "2 Day" );
        stageMedianDetails.put( BUYER.getType(), buyerStageMedianData );
        stageMedianDetails.put( SELLER.getType(), sellerStageMedianData );

        final Map< String, Map< String, Map< String, String > > > stageCtaDetails = new HashMap<>();
        final Map< String, String > buyerStageCta = new HashMap<>();
        buyerStageCta.put( CALL_KEY, "1" );
        buyerStageCta.put( SMS_KEY, "2" );
        buyerStageCta.put( EMAIL_KEY, "3" );
        buyerStageCta.put( TOTAL_KEY, "4" );
        final Map< String, Map< String, String > > buyerStageCtaData = new HashMap<>();
        buyerStageCtaData.put( "New", buyerStageCta );

        final Map< String, String > sellerStageCta = new HashMap<>();
        sellerStageCta.put( CALL_KEY, "5" );
        sellerStageCta.put( SMS_KEY, "6" );
        sellerStageCta.put( EMAIL_KEY, "7" );
        sellerStageCta.put( TOTAL_KEY, "8" );
        final Map< String, Map< String, String > > sellerStageCtaData = new HashMap<>();
        sellerStageCtaData.put( "New", sellerStageCta );

        stageCtaDetails.put( BUYER.getType(), buyerStageCtaData );
        stageCtaDetails.put( SELLER.getType(), sellerStageCtaData );

        when( clientStatisticsConfig.getFromDate() ).thenReturn( defaultFromDate );
        when( clientStatisticsConfig.getToDate() ).thenReturn( defaultToDate );
        mockStatic( DateUtil.class );
        PowerMockito.when( toSqlDate( defaultFromDate, DEFAULT_CRM_DATE_PATTERN ) ).thenReturn( fromDtm );
        PowerMockito.when( toSqlDate( defaultToDate, DEFAULT_CRM_DATE_PATTERN ) ).thenReturn( toDtm );
        when( agentReportTask.getAgentsStagewiseMedian( agentEmail, fromDtm, toDtm ) ).thenAnswer( invocation -> {
            final Future< Map< String, Map< String, String > > > future = Mockito.mock( FutureTask.class );
            when( future.isDone() ).thenReturn( false, false, true );
            when( future.get() ).thenReturn( stageMedianDetails );
            return future;
        } );

        when( agentReportTask.getAgentsStagewiseCta( fromDtm, toDtm, agentEmail ) ).thenAnswer( invocation -> {
            final Future< Map< String, Map< String, Map< String, String > > > > future = Mockito
                    .mock( FutureTask.class );
            when( future.isDone() ).thenReturn( false, false, true );
            when( future.get() ).thenReturn( stageCtaDetails );
            return future;
        } );

        final ClientStatisticsResponse clientStatisticsReport = agentReportsBusinessServiceImpl
                .getClientStatisticsReport( "", "2005-01-01", agentEmail );
        final Map< String, Map< String, ClientStatisticsDTO > > ctaData = clientStatisticsReport.getCtaData();
        final Map< String, ClientStatisticsDTO > buyerMap = ctaData.get( BUYER.getType() );
        final ClientStatisticsDTO dto = buyerMap.get( "New" );
        assertNotNull( dto );
        assertEquals( dto.getDuration(), "1 Day" );
        assertEquals( dto.getCall(), "1" );
        assertEquals( dto.getSms(), "2" );
        assertEquals( dto.getEmail(), "3" );
        assertEquals( dto.getTotal(), "4" );

        final Map< String, ClientStatisticsDTO > sellerMap = ctaData.get( SELLER.getType() );
        final ClientStatisticsDTO dto3 = sellerMap.get( "New" );
        assertNotNull( dto3 );
        assertEquals( dto3.getDuration(), "2 Day" );
        assertEquals( dto3.getCall(), "5" );
        assertEquals( dto3.getSms(), "6" );
        assertEquals( dto3.getEmail(), "7" );
        assertEquals( dto3.getTotal(), "8" );

        verify( clientStatisticsConfig ).getFromDate();
        verify( clientStatisticsConfig ).getToDate();
        verifyStatic( times( 2 ) );
        toSqlDate( Mockito.anyString(), Mockito.anyString() );
        verify( agentReportTask ).getAgentsStagewiseMedian( agentEmail, fromDtm, toDtm );
        verify( agentReportTask ).getAgentsStagewiseCta( fromDtm, toDtm, agentEmail );
    }

    /**
     * Test get client statistics report should return null response when task
     * throws excpetion.
     */
    @Test
    public void testGetClientStatisticsReportShouldReturnNullResponseWhenTaskThrowsExcpetion() {
        final String agentEmail = "testAgentEmail";
        final String fromDate = "2000-01-01";
        final String toDate = "2005-01-01";
        final Date fromDtm = new Date( 1L );
        final Date toDtm = new Date( 2L );
        final Map< String, Map< String, String > > stageMedianDetails = new HashMap<>();
        final Map< String, String > buyerStageMedianData = new HashMap<>();
        buyerStageMedianData.put( "New", "1 Day" );
        buyerStageMedianData.put( "Claimed", "1 Day" );
        final Map< String, String > sellerStageMedianData = new HashMap<>();
        sellerStageMedianData.put( "New", "2 Day" );
        stageMedianDetails.put( BUYER.getType(), buyerStageMedianData );
        stageMedianDetails.put( SELLER.getType(), sellerStageMedianData );

        final Map< String, Map< String, Map< String, String > > > stageCtaDetails = new HashMap<>();
        final Map< String, String > buyerStageCta = new HashMap<>();
        buyerStageCta.put( CALL_KEY, "1" );
        buyerStageCta.put( SMS_KEY, "2" );
        buyerStageCta.put( EMAIL_KEY, "3" );
        buyerStageCta.put( TOTAL_KEY, "4" );
        final Map< String, Map< String, String > > buyerStageCtaData = new HashMap<>();
        buyerStageCtaData.put( "New", buyerStageCta );

        final Map< String, String > sellerStageCta = new HashMap<>();
        sellerStageCta.put( CALL_KEY, "5" );
        sellerStageCta.put( SMS_KEY, "6" );
        sellerStageCta.put( EMAIL_KEY, "7" );
        sellerStageCta.put( TOTAL_KEY, "8" );
        final Map< String, Map< String, String > > sellerStageCtaData = new HashMap<>();
        sellerStageCtaData.put( "New", sellerStageCta );

        stageCtaDetails.put( BUYER.getType(), buyerStageCtaData );
        stageCtaDetails.put( SELLER.getType(), sellerStageCtaData );

        mockStatic( DateUtil.class );
        PowerMockito.when( toSqlDate( fromDate, DEFAULT_CRM_DATE_PATTERN ) ).thenReturn( fromDtm );
        PowerMockito.when( toSqlDate( toDate, DEFAULT_CRM_DATE_PATTERN ) ).thenReturn( toDtm );
        when( agentReportTask.getAgentsStagewiseMedian( agentEmail, fromDtm, toDtm ) ).thenAnswer( invocation -> {
            final Future< Map< String, Map< String, String > > > future = Mockito.mock( FutureTask.class );
            when( future.isDone() ).thenReturn( false, false, true );
            when( future.get() ).thenThrow( new InterruptedException() );
            return future;
        } );

        when( agentReportTask.getAgentsStagewiseCta( fromDtm, toDtm, agentEmail ) ).thenAnswer( invocation -> {
            final Future< Map< String, Map< String, Map< String, String > > > > future = Mockito
                    .mock( FutureTask.class );
            when( future.isDone() ).thenReturn( false, false, true );
            when( future.get() ).thenThrow( new InterruptedException() );
            return future;
        } );

        final ClientStatisticsResponse clientStatisticsReport = agentReportsBusinessServiceImpl
                .getClientStatisticsReport( fromDate, toDate, agentEmail );

        assertNull( clientStatisticsReport );
        verifyZeroInteractions( clientStatisticsConfig );
        verifyStatic( times( 2 ) );
        toSqlDate( Mockito.anyString(), Mockito.anyString() );
        verify( agentReportTask ).getAgentsStagewiseMedian( agentEmail, fromDtm, toDtm );
        verify( agentReportTask ).getAgentsStagewiseCta( fromDtm, toDtm, agentEmail );
    }

    /**
     * Test agent revenue.
     */
    @Test
    public void testAgentRevenue() {
        final Map< String, Object > expectedResponse = new HashMap< String, Object >();
        expectedResponse.put( "salesPrice", 30.0 );
        final String agentEmail = "test.user@ownerstest.com";
        final String fromDate = "2016-08-22";
        final String toDate = "2016-08-26";
        final List< Object[] > result = new ArrayList< Object[] >();
        final Object[] obj = new Object[2];
        obj[0] = 30.0;
        obj[1] = "test.user@ownerstest.com";
        result.add( obj );

        when( contactServiceV1.getRevenueOfAgent( agentEmail, fromDate, toDate ) ).thenReturn( result );

        final Map< String, Object > actualResponse = agentReportsBusinessServiceImpl.getAgentRevenue( agentEmail,
                fromDate, toDate );

        assertEquals( expectedResponse, actualResponse );
        verify( contactServiceV1 ).getRevenueOfAgent( agentEmail, fromDate, toDate );
    }

    /**
     * Test agent revenue_ with empty result.
     */
    @Test
    public void testAgentRevenue_WithEmptyResult() {
        final Map< String, Object > expectedResponse = new HashMap< String, Object >();
        expectedResponse.put( "salesPrice", 0.0 );
        final String agentEmail = "test.user@ownerstest.com";
        final String fromDate = "2016-08-22";
        final String toDate = "2016-08-26";
        final List< Object[] > result = new ArrayList< Object[] >();

        when( contactServiceV1.getRevenueOfAgent( agentEmail, fromDate, toDate ) ).thenReturn( result );

        final Map< String, Object > actualResponse = agentReportsBusinessServiceImpl.getAgentRevenue( agentEmail,
                fromDate, toDate );

        assertEquals( expectedResponse, actualResponse );
        verify( contactServiceV1 ).getRevenueOfAgent( agentEmail, fromDate, toDate );
    }

    /**
     * Test get client statistics report for broker should return response when
     * managing broker is valid.
     */
    @Test
    public void testGetClientStatisticsReportForBrokerShouldReturnResponseWhenManagingBrokerIsValid() {
        final String mbEmail = "testMbEmail";
        final String agentEmail = "testAgentEmail";
        final String fromDate = "2000-01-01";
        final String toDate = "2005-01-01";
        final Date fromDtm = new Date( 1L );
        final Date toDtm = new Date( 2L );
        final User user = new User();
        user.setEmail( mbEmail );
        final Map< String, Map< String, String > > stageMedianDetails = new HashMap<>();
        final Map< String, String > buyerStageMedianData = new HashMap<>();
        buyerStageMedianData.put( "New", "1 Day" );
        buyerStageMedianData.put( "Claimed", "1 Day" );
        final Map< String, String > sellerStageMedianData = new HashMap<>();
        sellerStageMedianData.put( "New", "2 Day" );
        stageMedianDetails.put( BUYER.getType(), buyerStageMedianData );
        stageMedianDetails.put( SELLER.getType(), sellerStageMedianData );

        final Map< String, Map< String, Map< String, String > > > stageCtaDetails = new HashMap<>();
        final Map< String, String > buyerStageCta = new HashMap<>();
        buyerStageCta.put( CALL_KEY, "1" );
        buyerStageCta.put( SMS_KEY, "2" );
        buyerStageCta.put( EMAIL_KEY, "3" );
        buyerStageCta.put( TOTAL_KEY, "4" );
        final Map< String, Map< String, String > > buyerStageCtaData = new HashMap<>();
        buyerStageCtaData.put( "New", buyerStageCta );

        final Map< String, String > sellerStageCta = new HashMap<>();
        sellerStageCta.put( CALL_KEY, "5" );
        sellerStageCta.put( SMS_KEY, "6" );
        sellerStageCta.put( EMAIL_KEY, "7" );
        sellerStageCta.put( TOTAL_KEY, "8" );
        final Map< String, Map< String, String > > sellerStageCtaData = new HashMap<>();
        sellerStageCtaData.put( "New", sellerStageCta );

        stageCtaDetails.put( BUYER.getType(), buyerStageCtaData );
        stageCtaDetails.put( SELLER.getType(), sellerStageCtaData );

        when( gravitasWebUtil.getAppUserEmail() ).thenReturn( mbEmail );
        when( userService.getManagingBroker( agentEmail ) ).thenReturn( user );
        mockStatic( DateUtil.class );
        PowerMockito.when( toSqlDate( fromDate, DEFAULT_CRM_DATE_PATTERN ) ).thenReturn( fromDtm );
        PowerMockito.when( toSqlDate( toDate, DEFAULT_CRM_DATE_PATTERN ) ).thenReturn( toDtm );
        when( agentReportTask.getAgentsStagewiseMedian( agentEmail, fromDtm, toDtm ) ).thenAnswer( invocation -> {
            final Future< Map< String, Map< String, String > > > future = Mockito.mock( FutureTask.class );
            when( future.isDone() ).thenReturn( false, false, true );
            when( future.get() ).thenReturn( stageMedianDetails );
            return future;
        } );

        when( agentReportTask.getAgentsStagewiseCta( fromDtm, toDtm, agentEmail ) ).thenAnswer( invocation -> {
            final Future< Map< String, Map< String, Map< String, String > > > > future = Mockito
                    .mock( FutureTask.class );
            when( future.isDone() ).thenReturn( false, false, true );
            when( future.get() ).thenReturn( stageCtaDetails );
            return future;
        } );

        final ClientStatisticsResponse clientStatisticsReport = agentReportsBusinessServiceImpl
                .getClientStatisticsReportForBroker( fromDate, toDate, agentEmail );
        final Map< String, Map< String, ClientStatisticsDTO > > ctaData = clientStatisticsReport.getCtaData();
        final Map< String, ClientStatisticsDTO > buyerMap = ctaData.get( BUYER.getType() );
        final ClientStatisticsDTO dto = buyerMap.get( "New" );
        assertNotNull( dto );
        assertEquals( dto.getDuration(), "1 Day" );
        assertEquals( dto.getCall(), "1" );
        assertEquals( dto.getSms(), "2" );
        assertEquals( dto.getEmail(), "3" );
        assertEquals( dto.getTotal(), "4" );

        final ClientStatisticsDTO dto1 = buyerMap.get( "Claimed" );
        assertNotNull( dto1 );
        assertEquals( dto1.getDuration(), "1 Day" );
        assertNull( dto1.getCall() );
        assertNull( dto1.getSms() );
        assertNull( dto1.getEmail() );
        assertNull( dto1.getTotal() );

        final Map< String, ClientStatisticsDTO > sellerMap = ctaData.get( SELLER.getType() );
        final ClientStatisticsDTO dto3 = sellerMap.get( "New" );
        assertNotNull( dto3 );
        assertEquals( dto3.getDuration(), "2 Day" );
        assertEquals( dto3.getCall(), "5" );
        assertEquals( dto3.getSms(), "6" );
        assertEquals( dto3.getEmail(), "7" );
        assertEquals( dto3.getTotal(), "8" );

        verify( gravitasWebUtil ).getAppUserEmail();
        verify( userService ).getManagingBroker( agentEmail );
        verifyZeroInteractions( clientStatisticsConfig );
        verifyStatic( times( 2 ) );
        toSqlDate( Mockito.anyString(), Mockito.anyString() );
        verify( agentReportTask ).getAgentsStagewiseMedian( agentEmail, fromDtm, toDtm );
        verify( agentReportTask ).getAgentsStagewiseCta( fromDtm, toDtm, agentEmail );
    }

    /**
     * Test get client statistics report for broker should throw exception when
     * managing broker is invalid.
     */
    @Test( expectedExceptions = AgentInvalidException.class )
    public void testGetClientStatisticsReportForBrokerShouldThrowExceptionWhenManagingBrokerIsInvalid() {
        final String invalidMbEmail = "InvalidMbEmail";
        final String agentEmail = "testAgentEmail";
        final String fromDate = "2000-01-01";
        final String toDate = "2005-01-01";
        final User user = new User();
        user.setEmail( "testMbEmail" );

        when( gravitasWebUtil.getAppUserEmail() ).thenReturn( invalidMbEmail );
        when( userService.getManagingBroker( agentEmail ) ).thenReturn( user );

        agentReportsBusinessServiceImpl.getClientStatisticsReportForBroker( fromDate, toDate, agentEmail );
    }

    /**
     * Test get client first response time should not use default dates when
     * start and end dates are passed.
     *
     * @param clientData
     *            the client data
     */
    // @Test( dataProvider = "getClientFirstResponseData" )
    public void testGetClientFirstResponseTimeShouldNotUseDefaultDatesWhenStartAndEndDatesArePassed(
            final Object clientData ) {
        final String agentEmail = "testAgentEmail";
        final String fromDate = "2000-01-01";
        final String toDate = "2005-01-01";
        final Date fromDtm = new Date( 1L );
        final String allResponseTime = "23 mins ";
        final String buyerResponseTime = "25 mins ";
        final String sellerResponseTime = "21 mins ";
        final Date toDtm = new Date( 2L );
        @SuppressWarnings( "unchecked" )
        final List< Object[] > dbValues = ( List< Object[] > ) clientData;

        mockStatic( DateUtil.class );
        PowerMockito.when( toSqlDate( fromDate, DEFAULT_CRM_DATE_PATTERN ) ).thenReturn( fromDtm );
        PowerMockito.when( toSqlDate( toDate, DEFAULT_CRM_DATE_PATTERN ) ).thenReturn( toDtm );
        PowerMockito.when( getReadableTime( 1380000, true, false ) ).thenReturn( allResponseTime );
        PowerMockito.when( getReadableTime( 1500000, true, false ) ).thenReturn( buyerResponseTime );
        PowerMockito.when( getReadableTime( 1260000, true, false ) ).thenReturn( sellerResponseTime );
        when( agentReportService.getOpportunitiesFirstResponseTime( agentEmail, fromDtm, toDtm ) )
                .thenReturn( dbValues );

        final ClientFirstResponseTime response = agentReportsBusinessServiceImpl.getClientFirstResponseTime( fromDate,
                toDate, agentEmail );

        assertNotNull( response );
        assertEquals( response.getAllResponseTime(), allResponseTime );
        assertEquals( response.getBuyerResponseTime(), buyerResponseTime );
        assertEquals( response.getSellerResponseTime(), sellerResponseTime );
        verifyZeroInteractions( clientStatisticsConfig );
        verifyStatic( times( 2 ) );
        toSqlDate( Mockito.anyString(), Mockito.anyString() );
        verifyStatic( times( 3 ) );
        getReadableTime( Mockito.anyLong(), Mockito.anyBoolean(), Mockito.anyBoolean() );
        verify( agentReportService ).getOpportunitiesFirstResponseTime( agentEmail, fromDtm, toDtm );
    }

    /**
     * Test get client first response time should use default dates when empty
     * start and end dates are passed.
     *
     * @param clientData
     *            the client data
     */
    // @Test( dataProvider = "getClientFirstResponseData" )
    public void testGetClientFirstResponseTimeShouldUseDefaultDatesWhenEmptyStartAndEndDatesArePassed(
            final Object clientData ) {
        final String agentEmail = "testAgentEmail";
        final String defaultFromDate = "2016-01-01";
        final String defaultToDate = "2020-01-01";
        final Date fromDtm = new Date( 1L );
        final Date toDtm = new Date( 2L );
        final String allResponseTime = "23 mins ";
        final String buyerResponseTime = "25 mins ";
        final String sellerResponseTime = "21 mins ";
        @SuppressWarnings( "unchecked" )
        final List< Object[] > dbValues = ( List< Object[] > ) clientData;

        when( clientStatisticsConfig.getFromDate() ).thenReturn( defaultFromDate );
        when( clientStatisticsConfig.getToDate() ).thenReturn( defaultToDate );
        mockStatic( DateUtil.class );
        PowerMockito.when( toSqlDate( defaultFromDate, DEFAULT_CRM_DATE_PATTERN ) ).thenReturn( fromDtm );
        PowerMockito.when( toSqlDate( defaultToDate, DEFAULT_CRM_DATE_PATTERN ) ).thenReturn( toDtm );
        PowerMockito.when( getReadableTime( 1380000, true, false ) ).thenReturn( allResponseTime );
        PowerMockito.when( getReadableTime( 1500000, true, false ) ).thenReturn( buyerResponseTime );
        PowerMockito.when( getReadableTime( 1260000, true, false ) ).thenReturn( sellerResponseTime );
        when( agentReportService.getOpportunitiesFirstResponseTime( agentEmail, fromDtm, toDtm ) )
                .thenReturn( dbValues );

        final ClientFirstResponseTime response = agentReportsBusinessServiceImpl.getClientFirstResponseTime( "", "",
                agentEmail );

        assertNotNull( response );
        assertEquals( response.getAllResponseTime(), allResponseTime );
        assertEquals( response.getBuyerResponseTime(), buyerResponseTime );
        assertEquals( response.getSellerResponseTime(), sellerResponseTime );
        verify( clientStatisticsConfig ).getFromDate();
        verify( clientStatisticsConfig ).getToDate();
        verifyStatic( times( 2 ) );
        toSqlDate( Mockito.anyString(), Mockito.anyString() );
        verifyStatic( times( 3 ) );
        getReadableTime( Mockito.anyLong(), Mockito.anyBoolean(), Mockito.anyBoolean() );
        verify( agentReportService ).getOpportunitiesFirstResponseTime( agentEmail, fromDtm, toDtm );
    }

    /**
     * Test get client first response time should use default dates when empty
     * start date is passed.
     *
     * @param clientData
     *            the client data
     */
    // @Test( dataProvider = "getClientFirstResponseData" )
    public void testGetClientFirstResponseTimeShouldUseDefaultDatesWhenEmptyStartDateIsPassed(
            final Object clientData ) {
        final String agentEmail = "testAgentEmail";
        final String defaultFromDate = "2016-01-01";
        final String defaultToDate = "2020-01-01";
        final Date fromDtm = new Date( 1L );
        final Date toDtm = new Date( 2L );
        final String allResponseTime = "23 mins";
        final String buyerResponseTime = "25 mins";
        final String sellerResponseTime = "21 mins";
        @SuppressWarnings( "unchecked" )
        final List< Object[] > dbValues = ( List< Object[] > ) clientData;

        when( clientStatisticsConfig.getFromDate() ).thenReturn( defaultFromDate );
        when( clientStatisticsConfig.getToDate() ).thenReturn( defaultToDate );
        mockStatic( DateUtil.class );
        PowerMockito.when( toSqlDate( defaultFromDate, DEFAULT_CRM_DATE_PATTERN ) ).thenReturn( fromDtm );
        PowerMockito.when( toSqlDate( defaultToDate, DEFAULT_CRM_DATE_PATTERN ) ).thenReturn( toDtm );
        PowerMockito.when( getReadableTime( 1380000, true, false ) ).thenReturn( allResponseTime );
        PowerMockito.when( getReadableTime( 1500000, true, false ) ).thenReturn( buyerResponseTime );
        PowerMockito.when( getReadableTime( 1260000, true, false ) ).thenReturn( sellerResponseTime );
        when( agentReportService.getOpportunitiesFirstResponseTime( agentEmail, fromDtm, toDtm ) )
                .thenReturn( dbValues );

        final ClientFirstResponseTime response = agentReportsBusinessServiceImpl.getClientFirstResponseTime( "",
                "2016-12-31", agentEmail );

        assertNotNull( response );
        assertEquals( response.getAllResponseTime(), allResponseTime );
        assertEquals( response.getBuyerResponseTime(), buyerResponseTime );
        assertEquals( response.getSellerResponseTime(), sellerResponseTime );
        verify( clientStatisticsConfig ).getFromDate();
        verify( clientStatisticsConfig ).getToDate();
        verifyStatic( times( 2 ) );
        toSqlDate( Mockito.anyString(), Mockito.anyString() );
        verifyStatic( times( 3 ) );
        getReadableTime( Mockito.anyLong(), Mockito.anyBoolean(), Mockito.anyBoolean() );
        verify( agentReportService ).getOpportunitiesFirstResponseTime( agentEmail, fromDtm, toDtm );
    }

    /**
     * Test get client first response time should use default dates when empty
     * end date is passed.
     *
     * @param clientData
     *            the client data
     */
    // @Test( dataProvider = "getClientFirstResponseData" )
    public void testGetClientFirstResponseTimeShouldUseDefaultDatesWhenEmptyEndDateIsPassed( final Object clientData ) {
        final String agentEmail = "testAgentEmail";
        final String defaultFromDate = "2016-01-01";
        final String defaultToDate = "2020-01-01";
        final Date fromDtm = new Date( 1L );
        final Date toDtm = new Date( 2L );
        final String allResponseTime = "23 mins";
        final String buyerResponseTime = "25 mins";
        final String sellerResponseTime = "21 mins";
        @SuppressWarnings( "unchecked" )
        final List< Object[] > dbValues = ( List< Object[] > ) clientData;

        when( clientStatisticsConfig.getFromDate() ).thenReturn( defaultFromDate );
        when( clientStatisticsConfig.getToDate() ).thenReturn( defaultToDate );
        mockStatic( DateUtil.class );
        PowerMockito.when( toSqlDate( defaultFromDate, DEFAULT_CRM_DATE_PATTERN ) ).thenReturn( fromDtm );
        PowerMockito.when( toSqlDate( defaultToDate, DEFAULT_CRM_DATE_PATTERN ) ).thenReturn( toDtm );
        PowerMockito.when( getReadableTime( 1380000, true, false ) ).thenReturn( allResponseTime );
        PowerMockito.when( getReadableTime( 1500000, true, false ) ).thenReturn( buyerResponseTime );
        PowerMockito.when( getReadableTime( 1260000, true, false ) ).thenReturn( sellerResponseTime );
        when( agentReportService.getOpportunitiesFirstResponseTime( agentEmail, fromDtm, toDtm ) )
                .thenReturn( dbValues );

        final ClientFirstResponseTime response = agentReportsBusinessServiceImpl
                .getClientFirstResponseTime( "2016-01-01", "", agentEmail );

        assertNotNull( response );
        assertEquals( response.getAllResponseTime(), allResponseTime );
        assertEquals( response.getBuyerResponseTime(), buyerResponseTime );
        assertEquals( response.getSellerResponseTime(), sellerResponseTime );
        verify( clientStatisticsConfig ).getFromDate();
        verify( clientStatisticsConfig ).getToDate();
        verifyStatic( times( 2 ) );
        toSqlDate( Mockito.anyString(), Mockito.anyString() );
        verifyStatic( times( 3 ) );
        getReadableTime( Mockito.anyLong(), Mockito.anyBoolean(), Mockito.anyBoolean() );
        verify( agentReportService ).getOpportunitiesFirstResponseTime( agentEmail, fromDtm, toDtm );
    }

    /**
     * Test get client first response time should return empty response when
     * database record not found.
     */
    // @Test
    public void testGetClientFirstResponseTimeShouldReturnEmptyResponseWhenDatabaseRecordNotFound() {
        final String agentEmail = "testAgentEmail";
        final String defaultFromDate = "2016-01-01";
        final String defaultToDate = "2020-01-01";
        final Date fromDtm = new Date( 1L );
        final Date toDtm = new Date( 2L );

        mockStatic( DateUtil.class );
        PowerMockito.when( toSqlDate( defaultFromDate, DEFAULT_CRM_DATE_PATTERN ) ).thenReturn( fromDtm );
        PowerMockito.when( toSqlDate( defaultToDate, DEFAULT_CRM_DATE_PATTERN ) ).thenReturn( toDtm );
        when( clientStatisticsConfig.getFromDate() ).thenReturn( defaultFromDate );
        when( clientStatisticsConfig.getToDate() ).thenReturn( defaultToDate );

        when( agentReportService.getOpportunitiesFirstResponseTime( agentEmail, fromDtm, toDtm ) ).thenReturn( null );

        final ClientFirstResponseTime response = agentReportsBusinessServiceImpl
                .getClientFirstResponseTime( "2016-01-01", "", agentEmail );

        assertNotNull( response );
        assertNull( response.getAllResponseTime() );
        assertNull( response.getBuyerResponseTime() );
        assertNull( response.getSellerResponseTime() );
        verifyStatic( times( 2 ) );
        toSqlDate( Mockito.anyString(), Mockito.anyString() );
        verify( clientStatisticsConfig ).getFromDate();
        verify( clientStatisticsConfig ).getToDate();
        verify( agentReportService ).getOpportunitiesFirstResponseTime( agentEmail, fromDtm, toDtm );
    }

    /**
     * Test get client first response time for broker should return response
     * when managing broker is valid.
     *
     * @param clientData
     *            the client data
     */
    // @Test( dataProvider = "getClientFirstResponseData" )
    public void testGetClientFirstResponseTimeForBrokerShouldReturnResponseWhenManagingBrokerIsValid(
            final Object clientData ) {
        final String mbEmail = "testMbEmail";
        final String agentEmail = "testAgentEmail";
        final String fromDate = "2000-01-01";
        final String toDate = "2005-01-01";
        final Date fromDtm = new Date( 1L );
        final Date toDtm = new Date( 2L );
        final User user = new User();
        user.setEmail( mbEmail );
        final String allResponseTime = "23 mins ";
        final String buyerResponseTime = "25 mins ";
        final String sellerResponseTime = "21 mins";
        @SuppressWarnings( "unchecked" )
        final List< Object[] > dbValues = ( List< Object[] > ) clientData;

        when( gravitasWebUtil.getAppUserEmail() ).thenReturn( mbEmail );
        when( userService.getManagingBroker( agentEmail ) ).thenReturn( user );
        mockStatic( DateUtil.class );
        PowerMockito.when( toSqlDate( fromDate, DEFAULT_CRM_DATE_PATTERN ) ).thenReturn( fromDtm );
        PowerMockito.when( toSqlDate( toDate, DEFAULT_CRM_DATE_PATTERN ) ).thenReturn( toDtm );
        PowerMockito.when( getReadableTime( 1380000, true, false ) ).thenReturn( allResponseTime );
        PowerMockito.when( getReadableTime( 1500000, true, false ) ).thenReturn( buyerResponseTime );
        PowerMockito.when( getReadableTime( 1260000, true, false ) ).thenReturn( sellerResponseTime );
        when( agentReportService.getOpportunitiesFirstResponseTime( agentEmail, fromDtm, toDtm ) )
                .thenReturn( dbValues );

        final ClientFirstResponseTime response = agentReportsBusinessServiceImpl
                .getClientFirstResponseTimeForBroker( fromDate, toDate, agentEmail );

        assertNotNull( response );
        assertEquals( response.getAllResponseTime(), allResponseTime );
        assertEquals( response.getBuyerResponseTime(), buyerResponseTime );
        assertEquals( response.getSellerResponseTime(), sellerResponseTime );
        verify( gravitasWebUtil ).getAppUserEmail();
        verify( userService ).getManagingBroker( agentEmail );
        verifyZeroInteractions( clientStatisticsConfig );
        verifyStatic( times( 2 ) );
        toSqlDate( Mockito.anyString(), Mockito.anyString() );
        verify( agentReportService ).getOpportunitiesFirstResponseTime( agentEmail, fromDtm, toDtm );
    }

    /**
     * Test get client first response time for broker should throw exception
     * when managing broker is invalid.
     */
    @Test( expectedExceptions = AgentInvalidException.class )
    public void testGetClientFirstResponseTimeForBrokerShouldThrowExceptionWhenManagingBrokerIsInvalid() {
        final String invalidMbEmail = "InvalidMbEmail";
        final String agentEmail = "testAgentEmail";
        final String fromDate = "2000-01-01";
        final String toDate = "2005-01-01";
        final User user = new User();
        user.setEmail( "testMbEmail" );

        when( gravitasWebUtil.getAppUserEmail() ).thenReturn( invalidMbEmail );
        when( userService.getManagingBroker( agentEmail ) ).thenReturn( user );

        agentReportsBusinessServiceImpl.getClientFirstResponseTimeForBroker( fromDate, toDate, agentEmail );
    }

    /**
     * Testget lost opportunity percent.
     */
    @Test
    public void testgetLostOpportunityPercent() {
        final String agentEmail = "testAgentEmail";
        final String fromDate = "2000-01-01";
        final String toDate = "2005-01-01";
        final List< String > list = new ArrayList<>();
        list.add( "test" );
        when( agentReportService.getClosedLostOpportunityCount( Mockito.any( Date.class ), Mockito.any( Date.class ),
                Mockito.any( String.class ), Mockito.any( List.class ) ) ).thenReturn( new Integer( 1 ) );
        when( agentReportService.getAssignedOpportunityCount( Mockito.any( Date.class ), Mockito.any( Date.class ),
                Mockito.any( String.class ), Mockito.any( List.class ) ) ).thenReturn( new Integer( 1 ) );
        final Map< String, Integer > response = agentReportsBusinessServiceImpl.getClosedLostOpportunityCount( fromDate,
                toDate, agentEmail, "test" );
        assertEquals( response.get( "lost" ), new Integer( 1 ) );
        assertEquals( response.get( "total" ), new Integer( 1 ) );
    }

    /**
     * Testget lost opportunity percent when dates not passed.
     */
    @Test
    public void testgetLostOpportunityPercentWhenDatesNotPassed() {
        final String agentEmail = "testAgentEmail";
        final List< String > list = new ArrayList<>();
        list.add( "test" );
        when( agentReportService.getClosedLostOpportunityCount( Mockito.any( Date.class ), Mockito.any( Date.class ),
                Mockito.any( String.class ), Mockito.any( List.class ) ) ).thenReturn( new Integer( 1 ) );
        when( agentReportService.getAssignedOpportunityCount( Mockito.any( Date.class ), Mockito.any( Date.class ),
                Mockito.any( String.class ), Mockito.any( List.class ) ) ).thenReturn( new Integer( 1 ) );
        final Map< String, Integer > response = agentReportsBusinessServiceImpl.getClosedLostOpportunityCount( null,
                null, agentEmail, "test" );
        assertEquals( response.get( "lost" ), new Integer( 1 ) );
        assertEquals( response.get( "total" ), new Integer( 1 ) );
    }

    /**
     * Testget lost opportunity percent for broker should throw exception when
     * managing broker is invalid.
     */
    @Test( expectedExceptions = AgentInvalidException.class )
    public void testgetLostOpportunityPercentForBrokerShouldThrowExceptionWhenManagingBrokerIsInvalid() {
        final String invalidMbEmail = "InvalidMbEmail";
        final String agentEmail = "testAgentEmail";
        final String fromDate = "2000-01-01";
        final String toDate = "2005-01-01";
        final String type = "test";
        final User user = new User();
        user.setEmail( "testMbEmail" );

        when( gravitasWebUtil.getAppUserEmail() ).thenReturn( invalidMbEmail );
        when( userService.getManagingBroker( agentEmail ) ).thenReturn( user );
        agentReportsBusinessServiceImpl.getClosedLostOpportunityCountForBroker( fromDate, toDate, agentEmail, type );

    }

    /**
     * Testget lost opportunity percent for broker.
     */
    @Test
    public void testgetLostOpportunityPercentForBroker() {
        final String mbEmail = "testMbEmail";
        final String agentEmail = "testAgentEmail";
        final String fromDate = "2000-01-01";
        final String toDate = "2005-01-01";
        final String type = "test";
        final User user = new User();
        user.setEmail( mbEmail );

        when( gravitasWebUtil.getAppUserEmail() ).thenReturn( mbEmail );
        when( userService.getManagingBroker( agentEmail ) ).thenReturn( user );
        when( agentReportService.getClosedLostOpportunityCount( Mockito.any( Date.class ), Mockito.any( Date.class ),
                Mockito.any( String.class ), Mockito.any( List.class ) ) ).thenReturn( new Integer( 1 ) );
        when( agentReportService.getAssignedOpportunityCount( Mockito.any( Date.class ), Mockito.any( Date.class ),
                Mockito.any( String.class ), Mockito.any( List.class ) ) ).thenReturn( new Integer( 1 ) );

        final Map< String, Integer > response = agentReportsBusinessServiceImpl
                .getClosedLostOpportunityCountForBroker( fromDate, toDate, agentEmail, type );
        assertEquals( response.get( "lost" ), new Integer( 1 ) );
        assertEquals( response.get( "total" ), new Integer( 1 ) );
    }

    /**
     * Testget stage opportunity count.
     */
    @Test
    public void testgetStageOpportunityCount() {
        final String agentEmail = "testAgentEmail";
        final String fromDate = "2000-01-01";
        final String toDate = "2005-01-01";
        final List< String > list = new ArrayList<>();
        list.add( "test" );
        when( agentReportService.getOpportunityCountByStage( Mockito.any( Date.class ), Mockito.any( Date.class ),
                Mockito.any( String.class ), Mockito.any( List.class ), Mockito.any( List.class ) ) )
                        .thenReturn( new Integer( 1 ) );
        when( agentReportService.getAssignedOpportunityCount( Mockito.any( Date.class ), Mockito.any( Date.class ),
                Mockito.any( String.class ), Mockito.any( List.class ) ) ).thenReturn( new Integer( 1 ) );
        final Map< String, Integer > response = agentReportsBusinessServiceImpl.getOpportunityCountByStage( fromDate,
                toDate, agentEmail, "test", "test", false );
        assertEquals( response.get( "staged" ), new Integer( 1 ) );
        assertEquals( response.get( "total" ), new Integer( 1 ) );
    }

    /**
     * Testget stage opportunity count when dates not passed.
     */
    @Test
    public void testgetStageOpportunityCountWhenDatesNotPassed() {
        final String agentEmail = "testAgentEmail";
        final List< String > list = new ArrayList<>();
        list.add( "test" );
        when( agentReportService.getOpportunityCountByStage( Mockito.any( Date.class ), Mockito.any( Date.class ),
                Mockito.any( String.class ), Mockito.any( List.class ), Mockito.any( List.class ) ) )
                        .thenReturn( new Integer( 1 ) );
        when( agentReportService.getAssignedOpportunityCount( Mockito.any( Date.class ), Mockito.any( Date.class ),
                Mockito.any( String.class ), Mockito.any( List.class ) ) ).thenReturn( new Integer( 1 ) );
        final Map< String, Integer > response = agentReportsBusinessServiceImpl.getOpportunityCountByStage( null, null,
                agentEmail, "test", "test", false );
        assertEquals( response.get( "staged" ), new Integer( 1 ) );
        assertEquals( response.get( "total" ), new Integer( 1 ) );
    }

    /**
     * Testget opportunity count by stage for broker should throw exception when
     * managing broker is invalid.
     */
    @Test( expectedExceptions = AgentInvalidException.class )
    public void testgetOpportunityCountByStageForBrokerShouldThrowExceptionWhenManagingBrokerIsInvalid() {
        final String invalidMbEmail = "InvalidMbEmail";
        final String agentEmail = "testAgentEmail";
        final String fromDate = "2000-01-01";
        final String toDate = "2005-01-01";
        final String type = "test";
        final User user = new User();
        user.setEmail( "testMbEmail" );

        when( gravitasWebUtil.getAppUserEmail() ).thenReturn( invalidMbEmail );
        when( userService.getManagingBroker( agentEmail ) ).thenReturn( user );
        agentReportsBusinessServiceImpl.getOpportunityCountByStageForBroker( fromDate, toDate, agentEmail, "test",
                "test", false );
    }

    /**
     * Testget opportunity count by stage for broker.
     */
    @Test
    public void testgetOpportunityCountByStageForBroker() {
        final String mbEmail = "testMbEmail";
        final String agentEmail = "testAgentEmail";
        final String fromDate = "2000-01-01";
        final String toDate = "2005-01-01";
        final String type = "test";
        final User user = new User();
        user.setEmail( mbEmail );

        when( gravitasWebUtil.getAppUserEmail() ).thenReturn( mbEmail );
        when( userService.getManagingBroker( agentEmail ) ).thenReturn( user );
        when( agentReportService.getOpportunityCountByStage( Mockito.any( Date.class ), Mockito.any( Date.class ),
                Mockito.any( String.class ), Mockito.any( List.class ), Mockito.any( List.class ) ) )
                        .thenReturn( new Integer( 1 ) );
        when( agentReportService.getAssignedOpportunityCount( Mockito.any( Date.class ), Mockito.any( Date.class ),
                Mockito.any( String.class ), Mockito.any( List.class ) ) ).thenReturn( new Integer( 1 ) );

        final Map< String, Integer > response = agentReportsBusinessServiceImpl
                .getOpportunityCountByStageForBroker( fromDate, toDate, agentEmail, "test", "test", false );
        assertEquals( response.get( "staged" ), new Integer( 1 ) );
        assertEquals( response.get( "total" ), new Integer( 1 ) );
    }

    /**
     * Test get agents revenue should return revenue when rank filter is null
     * and role is agent.
     */
    @Test
    public void testGetAgentsRevenueShouldReturnRevenueWhenRankFilterIsNullAndRoleIsAgent() {
        final String fromDate = "01-01-2001";
        final String toDate = "01-01-2001";
        final ApiUser apiUser = new ApiUser();
        final Set< String > roles = new HashSet<>();
        roles.add( UserRole.FIREBASE_AGENT );
        final Integer rankFilter = null;
        apiUser.setRoles( roles );
        final List< Object[] > result = new ArrayList<>();

        when( contactServiceV1.getRevenueOfAgents( fromDate, toDate ) ).thenReturn( result );
        final Map< String, Object > agentsRevenue = agentReportsBusinessServiceImpl.getAgentsRevenue( fromDate, toDate,
                apiUser, rankFilter );
        verify( contactServiceV1 ).getRevenueOfAgents( fromDate, toDate );
        verify( userService ).getUsersByEmails( anyListOf( String.class ) );
        verify( agentsResponseBuilder )
                .convertTo( anyListOf( com.google.api.services.admin.directory.model.User.class ) );
    }

    /**
     * Test get agents revenue should return revenue when rank filter is not
     * null and role is not agent.
     */
    @Test
    public void testGetAgentsRevenueShouldReturnRevenueWhenRankFilterIsNotNullAndRoleIsNotAgent() {
        final String fromDate = "01-01-2001";
        final String toDate = "01-01-2001";
        final ApiUser apiUser = new ApiUser();
        final Set< String > roles = new HashSet<>();
        roles.add( "Diiferent role" );
        final Integer rankFilter = 1;
        apiUser.setRoles( roles );
        final List< Object[] > result = new ArrayList<>();

        when( contactServiceV1.getRevenueOfAgents( fromDate, toDate ) ).thenReturn( result );
        final Map< String, Object > agentsRevenue = agentReportsBusinessServiceImpl.getAgentsRevenue( fromDate, toDate,
                apiUser, rankFilter );
        verify( userService ).getUsersByEmails( anyListOf( String.class ) );
        verify( agentsResponseBuilder )
                .convertTo( anyListOf( com.google.api.services.admin.directory.model.User.class ) );
    }

    /**
     * Test get agents revenue should return revenue when rank filter is null
     * and role is agent1.
     */
    @Test
    public void testGetAgentsRevenueShouldReturnRevenueWhenRankFilterIsNullAndRoleIsAgent1() {
        final String fromDate = "01-01-2001";
        final String toDate = "01-01-2001";
        final ApiUser apiUser = new ApiUser();
        apiUser.setEmail( "test@test.com" );
        final Set< String > roles = new HashSet<>();
        roles.add( UserRole.FIREBASE_AGENT );
        final Integer rankFilter = null;
        apiUser.setRoles( roles );
        final List< Object[] > result = new ArrayList<>();

        when( contactServiceV1.getRevenueOfAgents( fromDate, toDate ) ).thenReturn( result );
        final Map< String, Object > agentsRevenue = agentReportsBusinessServiceImpl.getAgentsRevenue( fromDate, toDate,
                apiUser, rankFilter );
        verify( contactServiceV1 ).getRevenueOfAgents( fromDate, toDate );
        verify( userService ).getUsersByEmails( anyListOf( String.class ) );
        verify( agentsResponseBuilder )
                .convertTo( anyListOf( com.google.api.services.admin.directory.model.User.class ) );
    }

    /**
     * Gets the client first response data.
     *
     * @return the client first response data
     */
    @DataProvider( name = "getClientFirstResponseData" )
    private Object[][] getClientFirstResponseData() {
        final List< Object[] > dbValues = new ArrayList<>();
        final Object[] row1 = new Object[2];
        row1[0] = BUYER.getType();
        row1[1] = 300;
        final Object[] row2 = new Object[2];
        row2[0] = BUYER.getType();
        row2[1] = 1800;
        final Object[] row3 = new Object[2];
        row3[0] = BUYER.getType();
        row3[1] = 1500;
        final Object[] row4 = new Object[2];
        row4[0] = SELLER.getType();
        row4[1] = 1260;
        dbValues.add( row1 );
        dbValues.add( row2 );
        dbValues.add( row3 );
        dbValues.add( row4 );
        return new Object[][] { { dbValues } };
    }

    @Test( dataProvider = "getAgentStatisticsResponseData" )
    public void testGetAgentStatisticsWithGoodleInfo( final Object clientData ) {
        final String agentEmail = "anthony.thilak2@ownerstest.com";
        final List< String > emailList = new ArrayList<>();
        emailList.add( agentEmail );

        final List< com.google.api.services.admin.directory.model.User > usresList = new ArrayList<>();
        final com.google.api.services.admin.directory.model.User user = new com.google.api.services.admin.directory.model.User();
        final com.google.api.services.admin.directory.model.UserName userName = new com.google.api.services.admin.directory.model.UserName();
        user.setPrimaryEmail( agentEmail );
        userName.setFamilyName( "Anthony" );
        userName.setGivenName( "Thilak" );
        user.setName( userName );
        usresList.add( user );

        @SuppressWarnings( "unchecked" )
        final List< Object[] > dbValues = ( List< Object[] > ) clientData;
        when( agentReportService.getAgentStatistics( agentEmail ) ).thenReturn( dbValues );
        when( userService.getUsersByEmails( emailList ) ).thenReturn( usresList );

        final com.owners.gravitas.dto.response.AgentStatisticsResponse response = agentReportsBusinessServiceImpl
                .getAgentStatistics( agentEmail );
    }

    @Test( dataProvider = "getAgentStatisticsResponseData" )
    public void testGetAgentGridStatisticsWithGoodleInfo( final Object clientData ) {
        final String agentEmail = "anthony.thilak2@ownerstest.com";
        final List< String > emailList = new ArrayList<>();
        emailList.add( agentEmail );

        final List< com.google.api.services.admin.directory.model.User > usresList = new ArrayList<>();
        final com.google.api.services.admin.directory.model.User user = new com.google.api.services.admin.directory.model.User();
        final com.google.api.services.admin.directory.model.UserName userName = new com.google.api.services.admin.directory.model.UserName();
        user.setPrimaryEmail( agentEmail );
        userName.setFamilyName( "Anthony" );
        userName.setGivenName( "Thilak" );
        user.setName( userName );
        usresList.add( user );

        @SuppressWarnings( "unchecked" )
        final List< Object[] > dbValues = ( List< Object[] > ) clientData;
        when( agentReportService.getAgentGridStatistics( agentEmail ) ).thenReturn( dbValues );
        when( userService.getUsersByEmails( emailList ) ).thenReturn( usresList );

        final com.owners.gravitas.dto.response.AgentStatisticsResponse response = agentReportsBusinessServiceImpl
                .getAgentGridStatistics( agentEmail );
    }

    @Test( dataProvider = "agentF2FCountData" )
    public void testGetAgentF2FCountWithGoodleInfoWIthIsWeekFalse( final Object clientData ) {
        final String agentEmail = "anthony.thilak2@ownerstest.com";
        final boolean isWeek = false;
        final boolean isForAgent = true;

        final List< String > emailList = new ArrayList<>();
        emailList.add( agentEmail );

        final List< com.google.api.services.admin.directory.model.User > usresList = new ArrayList<>();
        final com.google.api.services.admin.directory.model.User user = new com.google.api.services.admin.directory.model.User();
        final com.google.api.services.admin.directory.model.UserName userName = new com.google.api.services.admin.directory.model.UserName();
        user.setPrimaryEmail( agentEmail );
        userName.setFamilyName( "Anthony" );
        userName.setGivenName( "Thilak" );
        user.setName( userName );
        usresList.add( user );

        @SuppressWarnings( "unchecked" )
        final List< Object[] > dbValues = ( List< Object[] > ) clientData;

        when( userService.getUsersByEmails( emailList ) ).thenReturn( usresList );
        when( agentReportService.getFaceToFaceStatistics( agentEmail, isForAgent ) ).thenReturn( dbValues );

        final AgentCumulativeResponse baseResponse = agentReportsBusinessServiceImpl
                .getFaceToFaceStatistics( agentEmail, isWeek, isForAgent );
    }

    @Test( dataProvider = "agentF2FCountData" )
    public void testGetAgentF2FCountWithGoodleInfoWIthIsWeekTrue( final Object clientData ) {
        final String agentEmail = "anthony.thilak2@ownerstest.com";
        final boolean isWeek = true;
        final boolean isForAgent = true;

        final List< String > emailList = new ArrayList<>();
        emailList.add( agentEmail );

        final List< com.google.api.services.admin.directory.model.User > usresList = new ArrayList<>();
        final com.google.api.services.admin.directory.model.User user = new com.google.api.services.admin.directory.model.User();
        final com.google.api.services.admin.directory.model.UserName userName = new com.google.api.services.admin.directory.model.UserName();
        user.setPrimaryEmail( agentEmail );
        userName.setFamilyName( "Anthony" );
        userName.setGivenName( "Thilak" );
        user.setName( userName );
        usresList.add( user );

        @SuppressWarnings( "unchecked" )
        final List< Object[] > dbValues = ( List< Object[] > ) clientData;

        when( userService.getUsersByEmails( emailList ) ).thenReturn( usresList );
        when( agentReportService.getFaceToFaceGridStatistics( agentEmail, isForAgent ) ).thenReturn( dbValues );

        final AgentCumulativeResponse baseResponse = agentReportsBusinessServiceImpl
                .getFaceToFaceStatistics( agentEmail, isWeek, isForAgent );
    }

    @Test( dataProvider = "getAgentStatisticsResponseData" )
    public void testGetManagingBrokerStatisticsWithGoodleInfo( final Object clientData ) {
        final String managingBrokerEmail = "blaine.byers@ownerstest.com";

        final String agentEmail = "anthony.thilak2@ownerstest.com";
        final List< String > emailList = new ArrayList<>();
        emailList.add( agentEmail );

        final List< com.google.api.services.admin.directory.model.User > usresList = new ArrayList<>();
        final com.google.api.services.admin.directory.model.User user = new com.google.api.services.admin.directory.model.User();
        final com.google.api.services.admin.directory.model.UserName userName = new com.google.api.services.admin.directory.model.UserName();
        user.setPrimaryEmail( agentEmail );
        userName.setFamilyName( "Anthony" );
        userName.setGivenName( "Thilak" );
        user.setName( userName );
        usresList.add( user );

        @SuppressWarnings( "unchecked" )
        final List< Object[] > dbValues = ( List< Object[] > ) clientData;
        when( agentReportService.getManagingBrokerGridStatistics( managingBrokerEmail ) ).thenReturn( dbValues );
        when( userService.getUsersByEmails( emailList ) ).thenReturn( usresList );

        final com.owners.gravitas.dto.response.AgentStatisticsResponse response = agentReportsBusinessServiceImpl
                .getManagingBrokerGridStatistics( managingBrokerEmail );
    }

    @Test( dataProvider = "getAgentStatisticsResponseData" )
    public void testGetManagingBrokerGridStatisticsWithGoodleInfo( final Object clientData ) {
        final String managingBrokerEmail = "blaine.byers@ownerstest.com";

        final String agentEmail = "anthony.thilak2@ownerstest.com";
        final List< String > emailList = new ArrayList<>();
        emailList.add( agentEmail );

        final List< com.google.api.services.admin.directory.model.User > usresList = new ArrayList<>();
        final com.google.api.services.admin.directory.model.User user = new com.google.api.services.admin.directory.model.User();
        final com.google.api.services.admin.directory.model.UserName userName = new com.google.api.services.admin.directory.model.UserName();
        user.setPrimaryEmail( "anthony.thilak2@ownerstest.com" );
        userName.setFamilyName( "Anthony" );
        userName.setGivenName( "Thilak" );
        user.setName( userName );
        usresList.add( user );

        @SuppressWarnings( "unchecked" )
        final List< Object[] > dbValues = ( List< Object[] > ) clientData;
        when( agentReportService.getManagingBrokerStatistics( managingBrokerEmail ) ).thenReturn( dbValues );
        when( userService.getUsersByEmails( emailList ) ).thenReturn( usresList );

        final com.owners.gravitas.dto.response.AgentStatisticsResponse response = agentReportsBusinessServiceImpl
                .getManagingBrokerStatistics( managingBrokerEmail );
    }

    @Test( dataProvider = "agentF2FCountData" )
    public void testGetGetManaginBrokerF2FCounttWithGoodleInfoWIthIsWeekFalse( final Object clientData ) {
        final String managingBrokerEmail = "blaine.byers@ownerstest.com";
        final boolean isWeek = false;
        final boolean isForAgent = false;

        final String agentEmail = "anthony.thilak2@ownerstest.com";
        final List< String > emailList = new ArrayList<>();
        emailList.add( agentEmail );

        final List< com.google.api.services.admin.directory.model.User > usresList = new ArrayList<>();
        final com.google.api.services.admin.directory.model.User user = new com.google.api.services.admin.directory.model.User();
        final com.google.api.services.admin.directory.model.UserName userName = new com.google.api.services.admin.directory.model.UserName();
        user.setPrimaryEmail( "anthony.thilak2@ownerstest.com" );
        userName.setFamilyName( "Anthony" );
        userName.setGivenName( "Thilak" );
        user.setName( userName );
        usresList.add( user );

        @SuppressWarnings( "unchecked" )
        final List< Object[] > dbValues = ( List< Object[] > ) clientData;

        when( userService.getUsersByEmails( emailList ) ).thenReturn( usresList );
        when( agentReportService.getFaceToFaceStatistics( agentEmail, isForAgent ) ).thenReturn( dbValues );

        final AgentCumulativeResponse baseResponse = agentReportsBusinessServiceImpl
                .getFaceToFaceStatistics( agentEmail, isWeek, isForAgent );
    }

    @Test( dataProvider = "agentF2FCountData" )
    public void testGetManaginBrokerF2FCountWithGoodleInfoWIthIsWeekTrue( final Object clientData ) {
        final String managingBrokerEmail = "blaine.byers@ownerstest.com";
        final boolean isWeek = true;
        final boolean isForAgent = false;

        final String agentEmail = "anthony.thilak2@ownerstest.com";
        final List< String > emailList = new ArrayList<>();
        emailList.add( agentEmail );

        final List< com.google.api.services.admin.directory.model.User > usresList = new ArrayList<>();
        final com.google.api.services.admin.directory.model.User user = new com.google.api.services.admin.directory.model.User();
        final com.google.api.services.admin.directory.model.UserName userName = new com.google.api.services.admin.directory.model.UserName();
        user.setPrimaryEmail( "anthony.thilak2@ownerstest.com" );
        userName.setFamilyName( "Anthony" );
        userName.setGivenName( "Thilak" );
        user.setName( userName );
        usresList.add( user );

        @SuppressWarnings( "unchecked" )
        final List< Object[] > dbValues = ( List< Object[] > ) clientData;

        when( userService.getUsersByEmails( emailList ) ).thenReturn( usresList );
        when( agentReportService.getFaceToFaceGridStatistics( agentEmail, isForAgent ) ).thenReturn( dbValues );

        final AgentCumulativeResponse baseResponse = agentReportsBusinessServiceImpl
                .getFaceToFaceStatistics( agentEmail, isWeek, isForAgent );
    }

    @Test( dataProvider = "getAgentStatisticsResponseData" )
    public void testGetExecutiveGridStatistics( final Object clientData ) {
        final String executiveEmail = "blaine.byers@ownerstest.com";
        @SuppressWarnings( "unchecked" )
        final List< Object[] > dbValues = ( List< Object[] > ) clientData;

        when( agentReportService.getExecutiveGridStatistics( executiveEmail ) ).thenReturn( dbValues );

        final com.owners.gravitas.dto.response.AgentStatisticsResponse response = agentReportsBusinessServiceImpl
                .getExecutiveGridStatistics( executiveEmail );
    }

    @Test( dataProvider = "getAgentStatisticsResponseData" )
    public void testGetExecutiveStatistics( final Object clientData ) {
        final String executiveEmail = "blaine.byers@ownerstest.com";
        @SuppressWarnings( "unchecked" )
        final List< Object[] > dbValues = ( List< Object[] > ) clientData;

        when( agentReportService.getExecutiveStatistics( executiveEmail ) ).thenReturn( dbValues );

        final com.owners.gravitas.dto.response.AgentStatisticsResponse response = agentReportsBusinessServiceImpl
                .getExecutiveStatistics( executiveEmail );
    }

    @Test( dataProvider = "getAgentStatisticsResponseData" )
    public void testGetExecutiveF2FCountWIthIsWeekFalse( final Object clientData ) {
        final String executiveEmail = "blaine.byers@ownerstest.com";
        final boolean isWeek = false;
        @SuppressWarnings( "unchecked" )
        final List< Object[] > dbValues = ( List< Object[] > ) clientData;

        when( agentReportService.getExecutiveFaceToFace( executiveEmail ) ).thenReturn( dbValues );

        final AgentCumulativeResponse baseResponse = agentReportsBusinessServiceImpl
                .getExecutiveFaceToFaceCount( executiveEmail, isWeek );
    }

    @Test( dataProvider = "agentF2FCountData" )
    public void testGetExecutiveF2FCountWithIsWeekTrue( final Object clientData ) {
        final String executiveEmail = "blaine.byers@ownerstest.com";
        final boolean isWeek = true;

        @SuppressWarnings( "unchecked" )
        final List< Object[] > dbValues = ( List< Object[] > ) clientData;
        when( agentReportService.getExecutiveFaceToFaceGrid( executiveEmail ) ).thenReturn( dbValues );

        final AgentCumulativeResponse baseResponse = agentReportsBusinessServiceImpl
                .getExecutiveFaceToFaceCount( executiveEmail, isWeek );
    }

    @Test( dataProvider = "getAgentStatisticsResponseData" )
    public void testDownloadExecutiveF2FStatisticsReport( final Object clientData ) {
        final String executiveEmail = "blaine.byers@ownerstest.com";
        final boolean isWeek = true;

        @SuppressWarnings( "unchecked" )
        final List< Object[] > statisticsList = ( List< Object[] > ) clientData;
        final List< Object[] > faceToFaceCountList = new ArrayList<>();
        final Object[] row1 = new Object[3];
        row1[0] = "1";
        row1[1] = "07/11/2017";
        row1[2] = "anthony.thilak2@ownerstest.com";
        faceToFaceCountList.add( row1 );
        final List< String > emailList = new ArrayList< String >();
        emailList.add( "abc@owners.com" );
        emailList.add( "xyz@owners.com" );
        final List< Object[] > agentsAndMBList = new ArrayList< Object[] >();
        final Object[] record1 = new Object[2];
        record1[0] = "agents@mail.com";
        record1[1] = "mb@mail.com";
        agentsAndMBList.add( record1 );
        final Map< String, String > userMap = new HashMap();
        final Workbook workbook = null;

        when( agentReportService.getExecutiveStatistics( executiveEmail ) ).thenReturn( statisticsList );
        when( agentReportService.getExecutiveFaceToFaceDownload( executiveEmail ) ).thenReturn( faceToFaceCountList );
        when( agentReportService.getAgentsAndTheirManagingBrokerEmailID( emailList ) ).thenReturn( agentsAndMBList );
        when( agentsResponseBuilder.createExecutiveReport( faceToFaceCountList, statisticsList, userMap,
                agentsAndMBList ) ).thenReturn( workbook );

        final Workbook workbook1 = agentReportsBusinessServiceImpl
                .downloadExecutiveF2FStatisticsReport( executiveEmail );
    }

    /**
     * Gets the Agent Statistics response data.
     *
     * @return the Agent Statistics response data
     */
    @DataProvider( name = "agentF2FCountData" )
    private Object[][] getAgentF2FCountData() {
        final List< Object[] > dbValues = new ArrayList<>();
        final Object[] row1 = new Object[3];
        row1[0] = "1";
        row1[1] = "07/11/2017";
        row1[2] = "anthony.thilak2@ownerstest.com";
        final Object[] row2 = new Object[3];
        row2[0] = "1";
        row2[1] = "10/11/2017";
        row2[2] = "anthony.thilak2@ownerstest.com";
        final Object[] row3 = new Object[3];
        row3[0] = "1";
        row3[1] = "13/11/2017";
        row3[2] = "anthony.thilak2@ownerstest.com";
        final Object[] row4 = new Object[3];
        row4[0] = "1";
        row4[1] = "16/11/2017";
        row4[2] = "anthony.thilak2@ownerstest.com";
        dbValues.add( row1 );
        dbValues.add( row2 );
        dbValues.add( row3 );
        dbValues.add( row4 );
        return new Object[][] { { dbValues } };
    }

    /**
     * Gets the Agent Statistics response data.
     *
     * @return the Agent Statistics response data
     */
    @DataProvider( name = "getAgentStatisticsResponseData" )
    private Object[][] getAgentStatisticsResponseData() {
        final List< Object[] > dbValues = new ArrayList<>();
        final Object[] row1 = new Object[10];
        row1[0] = "c6178c8e-c736-4f56-962e-70736e53131d";
        row1[1] = "8349485b-58f2-41f0-bba9-b060e552473e";
        row1[2] = "Alfie";
        row1[3] = "Benedict";
        row1[4] = "07/11/2017";
        row1[5] = "0063F000002IM36QAG";
        row1[6] = "anthony.thilak2@ownerstest.com";
        row1[7] = "Face To Face Meeting";
        row1[9] = "03/13/2017";
        final Object[] row2 = new Object[10];
        row2[0] = "c6178c8e-c736-4f56-962e-70736e53131d";
        row2[1] = "3a5de0d9-2c6e-485a-83a8-3496bd55abd1";
        row2[2] = "John";
        row2[3] = "Doe";
        row2[4] = "10/11/2017";
        row2[5] = "0063F000002IiRCQA0";
        row2[6] = "anthony.thilak2@ownerstest.com";
        row2[7] = "Face To Face Meeting";
        row2[9] = "03/13/2017";
        final Object[] row3 = new Object[10];
        row3[0] = "c6178c8e-c736-4f56-962e-70736e53131d";
        row3[1] = "6f7ed14e-6d88-461e-b36e-51c700c61181";
        row3[2] = "A";
        row3[3] = "B";
        row3[4] = "13/11/2017";
        row3[5] = "0063F000002IkEeQAK";
        row3[6] = "anthony.thilak2@ownerstest.com";
        row3[7] = "Face To Face Meeting";
        row3[9] = "03/13/2017";
        final Object[] row4 = new Object[10];
        row4[0] = "c6178c8e-c736-4f56-962e-70736e53131d";
        row4[1] = "5cb3f2b1-f85a-4591-bb73-579bb8b6258b";
        row4[2] = "Kelly";
        row4[3] = "Lanester";
        row4[4] = "16/11/2017";
        row4[5] = "0063F000002INYxQAO";
        row4[6] = "anthony.thilak2@ownerstest.com";
        row4[7] = "Face To Face Meeting";
        row4[9] = "03/13/2017";
        dbValues.add( row1 );
        dbValues.add( row2 );
        dbValues.add( row3 );
        dbValues.add( row4 );
        return new Object[][] { { dbValues } };
    }

    @Test
    public void getCalendarEventsFromAgentAppWithNoResponseFromDB() {
        when( agentTaskService.findByAgentEmailAndStatusNotAndDueDate( anyString(), anyString(), any( java.util.Date.class ),
                any( java.util.Date.class ) ) ).thenReturn( null );
        final CalendarEventListResponse eventListResponse = agentReportsBusinessServiceImpl.getCalendarEventsFromAgentApp(
                "test@test.com", new Long( "1523083588598" ), new Long( "1523087188598" ) );
        assertNotNull( eventListResponse );
        assertEquals( eventListResponse.getMessage(), "No events found" );
    }

    @Test
    public void getCalendarEventsFromAgentAppWithResponseFromDB() {
        final List< AgentTask > agentTasks = new ArrayList<>();
        final AgentTask agentTask = new AgentTask();
        final com.owners.gravitas.domain.entity.Opportunity opportunity = new com.owners.gravitas.domain.entity.Opportunity();
        opportunity.setOpportunityId( "asvfvhsgfss" );
        agentTask.setOpportunity( opportunity );
        agentTasks.add( agentTask );
        when( agentTaskService.findByAgentEmailAndStatusNotAndDueDate( anyString(), anyString(), any( java.util.Date.class ),
                any( java.util.Date.class ) ) ).thenReturn( agentTasks );
        final List< Contact > contacts = new ArrayList<>();
        final Contact contact = new Contact();
        final Set< com.owners.gravitas.domain.entity.Opportunity > opportunities = new HashSet<>();
        opportunities.add( opportunity );
        contact.setOpportunities( opportunities );
        when( contactServiceV1.getAllContactByFbOpportunityId( any( Set.class ) ) ).thenReturn( contacts );
        when( calendarEventsResponseBuilder.convertFrom( any( AgentTask.class ), any( Contact.class ) ) )
                .thenReturn( new CalendarEvent() );
        final CalendarEventListResponse eventListResponse = agentReportsBusinessServiceImpl.getCalendarEventsFromAgentApp(
                "test@test.com", new Long( "1523083588598" ), new Long( "1523087188598" ) );
        assertNotNull( eventListResponse );
        assertNotNull( eventListResponse.getCalendarEvents() );
    }
    
    @Test
    public void TestGetAllAgentStates() {
        final Map< String, String > map = new HashMap<>();
        map.put( "FL", "FLORIDA" );
        map.put( "TX", "TEXAS" );

        when( timeZoneUtil.getStateToStateNameMapping() ).thenReturn( map );
        final AgentStateResponse response = agentReportsBusinessServiceImpl.getAllAgentStates();
        assertNotNull( response );
        assertEquals( response.getStatus(), Status.SUCCESS );
    }
    
    @Test
    public void TestGetAllAgentStates_whenPropertyMissing() {
        final Map< String, String > map = new HashMap<>();
        when( timeZoneUtil.getStateToStateNameMapping() ).thenReturn( map );
        final AgentStateResponse response = agentReportsBusinessServiceImpl.getAllAgentStates();
        assertNotNull( response );
        assertEquals( response.getStatus(), Status.FAILURE );
    }
}
