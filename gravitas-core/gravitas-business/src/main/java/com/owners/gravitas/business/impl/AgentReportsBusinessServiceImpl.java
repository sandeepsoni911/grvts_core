package com.owners.gravitas.business.impl;

import static com.owners.gravitas.constants.Constants.CALL_KEY;
import static com.owners.gravitas.constants.Constants.EMAIL_KEY;
import static com.owners.gravitas.constants.Constants.PERCENTAGE_SIGN;
import static com.owners.gravitas.constants.Constants.SMS_KEY;
import static com.owners.gravitas.constants.Constants.TOTAL_KEY;
import static com.owners.gravitas.enums.RecordType.BUYER;
import static com.owners.gravitas.enums.RecordType.SELLER;
import static com.owners.gravitas.enums.StatusType.deleted;
import static com.owners.gravitas.enums.UserStatusType.INACTIVE;
import static com.owners.gravitas.enums.UserStatusType.ONBOARDING;
import static com.owners.gravitas.util.DateUtil.DEFAULT_CRM_DATE_PATTERN;
import static com.owners.gravitas.util.DateUtil.getReadableTime;
import static com.owners.gravitas.util.DateUtil.toSqlDate;
import static com.owners.gravitas.util.MathUtil.getMedian;
import static com.owners.gravitas.util.StringUtils.convertObjectToString;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.collections.ListUtils.union;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.api.services.admin.directory.model.User;
import com.google.api.services.admin.directory.model.UserName;
import com.hubzu.notification.common.util.JsonUtil;
import com.owners.gravitas.business.AgentPerformanceMetricsTask;
import com.owners.gravitas.business.AgentReportsBusinessService;
import com.owners.gravitas.business.builder.AgentAppCalendarEventsResponseBuilder;
import com.owners.gravitas.business.builder.AgentStatisticsBuilder;
import com.owners.gravitas.business.builder.response.AgentsResponseBuilder;
import com.owners.gravitas.business.task.AgentReportTask;
import com.owners.gravitas.config.AgentMetricsJmxConfig;
import com.owners.gravitas.config.AgentOpportunityBusinessConfig;
import com.owners.gravitas.config.BestFitAgentConfig;
import com.owners.gravitas.config.ClientStatisticsConfig;
import com.owners.gravitas.constants.UserRole;
import com.owners.gravitas.domain.Agent;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.domain.entity.AgentPerformanceLog;
import com.owners.gravitas.domain.entity.AgentStatistics;
import com.owners.gravitas.domain.entity.AgentTask;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.dto.AgentCumulativeStatistics;
import com.owners.gravitas.dto.AgentSatistics;
import com.owners.gravitas.dto.ApiUser;
import com.owners.gravitas.dto.CalendarEvent;
import com.owners.gravitas.dto.ClientStatisticsDTO;
import com.owners.gravitas.dto.ManagingBrokerCumulativeStatistics;
import com.owners.gravitas.dto.OpportunityDetails;
import com.owners.gravitas.dto.response.AgentAvailableTimeResponse;
import com.owners.gravitas.dto.response.AgentCumulativeResponse;
import com.owners.gravitas.dto.response.AgentStateResponse;
import com.owners.gravitas.dto.response.AgentStatesDetails;
import com.owners.gravitas.dto.response.AgentStatisticsResponse;
import com.owners.gravitas.dto.response.AgentsResponse;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.CalendarEventListResponse;
import com.owners.gravitas.dto.response.ClientFirstResponseTime;
import com.owners.gravitas.dto.response.ClientStatisticsResponse;
import com.owners.gravitas.enums.BuyerStage;
import com.owners.gravitas.enums.SellerStage;
import com.owners.gravitas.enums.UserStatusType;
import com.owners.gravitas.exception.AgentInvalidException;
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
 * The Class AgentReportsBusinessServiceImpl.
 *
 * @author ankusht
 */
@Service
@Transactional( readOnly = true )
public class AgentReportsBusinessServiceImpl implements AgentReportsBusinessService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentReportsBusinessServiceImpl.class );

    /** The Constant BUYER_OPPORTUNITIES. */
    public static final String BUYER_OPPORTUNITIES = "Buyer Opportunities";

    /** The Constant SELLER_OPPORTUNITIES. */
    public static final String SELLER_OPPORTUNITIES = "Seller Opportunities";

    /** The Constant PENDING_OPPORTUNITIES_COUNT. */
    public static final String PENDING_OPPORTUNITIES_COUNT = "Pending Transactions";

    /** The Constant CLOSED_OPPORTUNITIES_COUNT. */
    public static final String CLOSED_OPPORTUNITIES_COUNT = "Closed Transactions";

    /** The Constant RESPONSE_TIME. */
    public static final String RESPONSE_TIME = "Response Time";

    /** The Constant NA. */
    private static final String NA = "N/A";

    /** The Constant BUYER_OPPORTUNITIES_PERFORMANCE. */
    public static final String BUYER_OPPORTUNITIES_PERFORMANCE = "buyerOpportunities";

    /** The Constant SELLER_OPPORTUNITIES_PERFORMANCE. */
    public static final String SELLER_OPPORTUNITIES_PERFORMANCE = "sellerOpportunities";

    /** The Constant TOTAL. */
    public static final String TOTAL = "total";

    /** The Constant STAGES. */
    public static final String STAGES = "stages";

    /** The Constant PERCENT. */
    public static final String PERCENT = "percent";

    /** The Constant COUNT. */
    public static final String COUNT = "count";

    /** The Constant LABEL. */
    public static final String LABEL = "label";

    /** The Constant AVERAGE_RESPONSE_TIME. */
    public static final String AVERAGE_RESPONSE_TIME = "avgeResponseTime";

    /** The Constant AVERAGE_CLIENT_COUNT. */
    public static final String AVERAGE_CLIENT_COUNT = "avgClientCount";

    /** The Constant SCORE. */
    private static final String SCORE = "SCORE";

    /** The agent performance metrics task. */
    @Autowired
    private AgentPerformanceMetricsTask agentPerformanceMetricsTask;

    /** The agent service. */
    @Autowired
    private AgentService agentService;

    @Autowired
    private AgentTaskService agentTaskService;

    @Autowired
    private SearchService searchService;

    /** The agent metrics jmx config. */
    @Autowired
    private AgentMetricsJmxConfig agentMetricsJmxConfig;

    /** The agent performance log service. */
    @Autowired
    private AgentPerformanceLogService agentPerformanceLogService;

    /** The fb assigned date. */
    @Value( "${opportunity.response.time.after.date}" )
    private Long fbAssignedDate;

    @Value( "${agent.calendar.events.limit}" )
    private int calendarEventsLimit;

    /** The opportunity service. */
    @Autowired
    private OpportunityService opportunityService;

    /** The user service. */
    @Autowired
    private UserService userService;

    /** The best fit agent config. */
    @Autowired
    private BestFitAgentConfig bestFitAgentConfig;

    /** The agent details service. */
    @Autowired
    private AgentDetailsService agentDetailsService;

    /** The agents response builder. */
    @Autowired
    private AgentsResponseBuilder agentsResponseBuilder;

    /** The agent analytics service. */
    @Autowired
    private AgentStatisticsService agentStatisticsService;

    /** The agent analytics builder. */
    @Autowired
    private AgentStatisticsBuilder agentStatisticsBuilder;

    /** The agent report task. */
    @Autowired
    private AgentReportTask agentReportTask;

    /** The client statistics config. */
    @Autowired
    private ClientStatisticsConfig clientStatisticsConfig;

    /** The agent report service. */
    @Autowired
    private AgentReportService agentReportService;

    /** The contact service V 1. */
    @Autowired
    private ContactEntityService contactServiceV1;

    /** The gravitas web util. */
    @Autowired
    private GravitasWebUtil gravitasWebUtil;

    @Autowired
    private AgentAppCalendarEventsResponseBuilder calendarEventsResponseBuilder;

    /** The Constant SALES_PRICE. */
    public static final String SALES_PRICE = "salesPrice";

    /** The Constant AGENTS. */
    public static final String AGENTS = "agents";

    /** The Constant LOGGED_IN_AGENT. */
    public static final String LOGGED_IN_AGENT = "loggedInAgent";
    
    @Autowired
    private TimeZoneUtil timeZoneUtil;
    
    @Autowired
    private AgentOpportunityBusinessConfig agentOpportunityBusinessConfig;

    /**
     * Calculate agent performance metrics.
     */
    @Override
    public void evaluateAgentPerformanceMetrics() {
        LOGGER.info( "Starting agents performance metrics calculation" );
        agentService.getAllAgentIds()
                .forEach( agentId -> agentPerformanceMetricsTask.computeAndSavePerformanceMetrics( agentId ) );
    }

    /**
     * Gets the performance log.
     *
     * @param agentId
     *            the agent id
     * @param timeFrame
     *            the time frame
     * @return the performance log
     */
    @Override
    public Map< String, Object > getPerformanceLog( final String agentId, final Integer timeFrame ) {
        Map< String, Object > response = Collections.emptyMap();
        if (agentMetricsJmxConfig.isAgentPerformanceMetricsJobEnabled()) {
            response = new LinkedHashMap<>();
            final Pageable top = new PageRequest( 0, 1 );
            final List< AgentPerformanceLog > agentPerformanceLogList = agentPerformanceLogService
                    .findLatestMetricByAgentFbId( agentId, top );

            if (CollectionUtils.isNotEmpty( agentPerformanceLogList )) {
                final AgentPerformanceLog agentPerformanceLog = agentPerformanceLogList.get( 0 );
                if (agentMetricsJmxConfig.isBuyerOppoPctgCalcEnabled()
                        && null != agentPerformanceLog.getBuyerOpportunitiesPercentage()) {
                    response.put( BUYER_OPPORTUNITIES,
                            agentPerformanceLog.getBuyerOpportunitiesPercentage() + PERCENTAGE_SIGN );
                }
                if (agentMetricsJmxConfig.isSellerOppoPctgCalcEnabled()
                        && null != agentPerformanceLog.getSellerOpportunitiesPercentage()) {
                    response.put( SELLER_OPPORTUNITIES,
                            agentPerformanceLog.getSellerOpportunitiesPercentage() + PERCENTAGE_SIGN );
                }
                if (agentMetricsJmxConfig.isPendingTransactionsCalcEnabled()
                        && null != agentPerformanceLog.getPendingTransactions()) {
                    response.put( PENDING_OPPORTUNITIES_COUNT,
                            agentPerformanceLog.getPendingTransactions().toString() );
                }
                if (agentMetricsJmxConfig.isClosedTransactionsCalcEnabled()
                        && null != agentPerformanceLog.getClosedTransactions()) {
                    response.put( CLOSED_OPPORTUNITIES_COUNT, agentPerformanceLog.getClosedTransactions().toString() );
                }
                if (agentMetricsJmxConfig.isResponseTimeCalcEnabled()) {
                    response.put( RESPONSE_TIME, getResponseTime( agentId ) );
                }
            }
        }

        return response;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentReportsBusinessService#
     * getManagerOpportunityReport(java.lang.String)
     */
    @Override
    public Map< String, Object > getManagerOpportunityReport( final String mbEmail ) {
        final List< AgentDetails > agents = userService.getUsersByManagingBroker( mbEmail );
        filterAgentsOnStatuses( agents, null );
        LOGGER.info( "Total number of agents under " + mbEmail + " : " + agents.size() );
        return computePerformanceOverview( agents );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentReportsBusinessService#
     * getAgentOpportunityReport(java.lang.String)
     */
    @Override
    public Map< String, Object > getAgentOpportunityReport( final String email ) {
        final AgentDetails agent = agentDetailsService.findAgentByEmail( email );
        List< com.owners.gravitas.domain.entity.Opportunity > agentOpportunities = new ArrayList< com.owners.gravitas.domain.entity.Opportunity >();
        if (agent != null) {
            agentOpportunities = opportunityService.getAgentOpportunities( agent.getUser().getEmail() );
        }
        LOGGER.info( "Fetching Opportunity report for agent with email: " + email );
        return computeOpportunityCounts( agentOpportunities );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentReportsBusinessService#
     * getManagerSummary(java.lang.String)
     */
    @Override
    public Map< String, Object > getManagerSummary( final String mbEmail ) {
        final List< AgentDetails > agents = userService.getUsersByManagingBroker( mbEmail );
        filterAgentsOnStatuses( agents, null );
        LOGGER.info( "Total number of agents under managing broker  " + mbEmail + " is :" + agents.size() );
        return computeAgentSummary( agents );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.AgentReportsBusinessService#getAgentSummary(
     * java.lang.String)
     */
    @Override
    public Map< String, Object > getAgentSummary( final String email ) {
        final AgentDetails agent = agentDetailsService.findAgentByEmail( email );
        int countOfAgents = 0;
        List< com.owners.gravitas.domain.entity.Opportunity > agentOpportunities = new ArrayList< com.owners.gravitas.domain.entity.Opportunity >();
        if (agent != null) {
            agentOpportunities = opportunityService.getAgentOpportunities( agent.getUser().getEmail() );
            countOfAgents = 1;
        }
        LOGGER.info( "Fetching summary for agent with email: " + email );
        return computeAvgResponseTime( agentOpportunities, countOfAgents );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentReportsBusinessService#
     * getAgentsScoreSummary(java.lang.String)
     */
    @Override
    public List< com.owners.gravitas.dto.Agent > getAgentsScorePerformance( final String mbEmail ) {
        final List< com.owners.gravitas.domain.entity.User > users = new ArrayList<>();
        final List< AgentDetails > agents = filterAgentsOnStatuses( userService.getUsersByManagingBroker( mbEmail ),
                null );
        for ( final AgentDetails agentDetails : agents ) {
            users.add( agentDetails.getUser() );
        }
        LOGGER.info( "Total number of agents under managing broker  " + mbEmail + " is :" + agents.size() );
        final List< User > googleUsers = userService.getUsersByEmails( getUserEmails( users ) );
        return buildAgentsResponse( agentsResponseBuilder.convertTo( googleUsers ), agents ).getAgents();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentReportsBusinessService#
     * getAgentScoreStatistics(java.lang.String)
     */
    @Override
    public BaseResponse getAgentScoreStatistics( final String email ) {
        LOGGER.info( "Get Agent Statistics Data" );
        return agentStatisticsBuilder.convertTo( agentStatisticsService.getAgentScoreStatistics( email ) );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentReportsBusinessService#
     * createAgentDetailsData()
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public void performScoreAnalytics() {
        final List< AgentDetails > agentDetailsList = agentDetailsService.findAll();
        final List< AgentStatistics > agentHisoryAnalytics = getAgentStatistics( agentDetailsList );
        LOGGER.info( "Saving Agent Statistics data from schedular to database" );
        agentStatisticsService.save( agentHisoryAnalytics );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentReportsBusinessService#
     * getClientStatisticsReport(java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public ClientStatisticsResponse getClientStatisticsReport( final String fromDate, final String toDate,
            final String agentEmail ) {
        LOGGER.info( "Stagewise analysis started for agent " + agentEmail + " from :" + fromDate + " to : " + toDate );
        ClientStatisticsResponse response = null;
        final Date[] dates = getFromAndToDates( fromDate, toDate );
        final Future< Map< String, Map< String, String > > > stageMedianDetails = agentReportTask
                .getAgentsStagewiseMedian( agentEmail, dates[0], dates[1] );
        final Future< Map< String, Map< String, Map< String, String > > > > stageCtaDetails = agentReportTask
                .getAgentsStagewiseCta( dates[0], dates[1], agentEmail );
        try {
            response = buildClientStatisticsRespone( stageCtaDetails.get(), stageMedianDetails.get() );
        } catch ( InterruptedException | ExecutionException e ) {
            LOGGER.error( "Error in fetching client statistics report" + e.getMessage(), e );
        }
        return response;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentReportsBusinessService#
     * getClientStatisticsReportForBroker(java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public ClientStatisticsResponse getClientStatisticsReportForBroker( final String fromDate, final String toDate,
            final String email ) {
        validateManagingBroker( email );
        return getClientStatisticsReport( fromDate, toDate, email );
    }

    /**
     * Gets the lost opportunity percent.
     *
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @param agentEmail
     *            the agent email
     * @param type
     *            the type
     * @return the lost opportunity percent
     */
    @Override
    public Map< String, Integer > getClosedLostOpportunityCount( final String fromDate, final String toDate,
            final String agentEmail, final String type ) {
        LOGGER.info( "Get New,Cliamed,InContact to ClosedLost opportunity count " + agentEmail + " from :" + fromDate
                + " to : " + toDate + " type : " + type );
        final Date[] dates = getFromAndToDates( fromDate, toDate );
        final Map< String, Integer > response = new HashMap<>();
        final List< String > typeList = Arrays.asList( type.split( "," ) );
        final Integer lostOpportunityCount = Integer.valueOf( agentReportService
                .getClosedLostOpportunityCount( dates[0], dates[1], agentEmail, typeList ).toString() );
        final Integer assignedOpportunityCount = Integer.valueOf(
                agentReportService.getAssignedOpportunityCount( dates[0], dates[1], agentEmail, typeList ).toString() );
        LOGGER.info( " Lost count : " + lostOpportunityCount + " Totals assigne opportinity count : "
                + assignedOpportunityCount );
        response.put( "lost", lostOpportunityCount );
        response.put( "total", assignedOpportunityCount );
        return response;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentReportsBusinessService#
     * getLostOpportunityPercentForBroker(java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String)
     */
    @Override
    public Map< String, Integer > getClosedLostOpportunityCountForBroker( final String fromDate, final String toDate,
            final String email, final String type ) {
        validateManagingBroker( email );
        return getClosedLostOpportunityCount( fromDate, toDate, email, type );
    }

    /**
     * Gets the face to face opportunity percent.
     *
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @param agentEmail
     *            the agent email
     * @param stages
     *            the stages
     * @param type
     *            the type
     * @param isActive
     *            the is active
     * @return the face to face opportunity percent
     */
    @Override
    public Map< String, Integer > getOpportunityCountByStage( final String fromDate, final String toDate,
            final String agentEmail, final String stages, final String type, final boolean isActive ) {
        LOGGER.info( "Get staged opportunity count " + agentEmail + " from :" + fromDate + " to : " + toDate
                + " stages : " + stages + " type : " + type + " isActive : " + isActive );
        final Map< String, Integer > response = new HashMap<>();
        final Date[] dates = getFromAndToDates( fromDate, toDate );
        final List< String > stageList = Arrays.asList( stages.split( "," ) );
        final List< String > typeList = Arrays.asList( type.split( "," ) );
        final Integer stageOpportunityCount = Integer.valueOf( agentReportService
                .getOpportunityCountByStage( dates[0], dates[1], agentEmail, stageList, typeList ).toString() );
        final Integer assignedOpportunityCount = Integer.valueOf(
                agentReportService.getAssignedOpportunityCount( dates[0], dates[1], agentEmail, typeList ).toString() );
        LOGGER.info( " Staged count : " + stageOpportunityCount + " Totals assigne opportinity count : "
                + assignedOpportunityCount );
        response.put( "total", assignedOpportunityCount );
        response.put( "staged", stageOpportunityCount );
        return response;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentReportsBusinessService#
     * getFaceToFaceCount(java.lang.String)
     */
    @Override
    public double getFaceToFaceCount( final String email ) {
        final String faceToFaceCountObj = convertObjectToString( agentReportService.getFaceToFaceCount( email ) );
        return faceToFaceCountObj != null ? Double.valueOf( faceToFaceCountObj ) * 100 : 0;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentReportsBusinessService#
     * getFaceToFaceCountForBroker(java.lang.String)
     */
    @Override
    public double getFaceToFaceCountForBroker( final String email ) {
        validateManagingBroker( email );
        return getFaceToFaceCount( email );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentReportsBusinessService#
     * getStageOpportunityCountForBroker(java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String, boolean)
     */
    @Override
    public Map< String, Integer > getOpportunityCountByStageForBroker( final String fromDate, final String toDate,
            final String email, final String stages, final String type, final boolean isActive ) {
        validateManagingBroker( email );
        return getOpportunityCountByStage( fromDate, toDate, email, stages, type, isActive );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentReportsBusinessService#
     * getClientFirstResponseTime(java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public ClientFirstResponseTime getClientFirstResponseTime( final String fromDate, final String toDate,
            final String agentEmail ) {
        LOGGER.info(
                "Calculating first response time for agent " + agentEmail + " from: " + fromDate + " to: " + toDate );
        String fromDateStr = fromDate;
        String toDateStr = toDate;
        if (StringUtils.isBlank( fromDate ) || StringUtils.isBlank( toDate )) {
            fromDateStr = clientStatisticsConfig.getFromDate();
            toDateStr = clientStatisticsConfig.getToDate();
        }
        final DateTime from = DateUtil.toDateTime( fromDateStr, null );
        final DateTime to = DateUtil.toDateTime( toDateStr, null );
        final List< Long > buyerResponseTimes = opportunityService
                .findResponseTimeByAssignedAgentAndOpportunityType( agentEmail, BUYER.getType(), from, to );
        final List< Long > sellerResponseTimes = opportunityService
                .findResponseTimeByAssignedAgentAndOpportunityType( agentEmail, SELLER.getType(), from, to );
        return buildClientFirstResponseTime( buyerResponseTimes, sellerResponseTimes );
    }

    /**
     * Builds the client first response time.
     *
     * @param buyerResponseTimes
     *            the buyer response times
     * @param sellerResponseTimes
     *            the seller response times
     * @return the client first response time
     */
    private ClientFirstResponseTime buildClientFirstResponseTime( final List< Long > buyerResponseTimes,
            final List< Long > sellerResponseTimes ) {
        final ClientFirstResponseTime response = new ClientFirstResponseTime();
        response.setBuyerResponseTime( getReadableMedianTimeStr( buyerResponseTimes ) );
        response.setSellerResponseTime( getReadableMedianTimeStr( sellerResponseTimes ) );
        response.setAllResponseTime( getReadableMedianTimeStr( union( buyerResponseTimes, sellerResponseTimes ) ) );
        return response;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentReportsBusinessService#
     * getClientFirstResponseTimeForBroker(java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public ClientFirstResponseTime getClientFirstResponseTimeForBroker( final String fromDate, final String toDate,
            final String email ) {
        validateManagingBroker( email );
        return getClientFirstResponseTime( fromDate, toDate, email );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.AgentReportsBusinessService#getAgentRevenue(
     * java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public Map< String, Object > getAgentRevenue( final String agentEmail, final String fromDate,
            final String toDate ) {
        final Date[] dates = getFromAndToDates( fromDate, toDate );
        LOGGER.info( "Fetching revenue for agent " + agentEmail + " from " + dates[0] + " to " + dates[1] );
        final List< Object[] > result = contactServiceV1.getRevenueOfAgent( agentEmail,
                DateUtil.toString( dates[0], DEFAULT_CRM_DATE_PATTERN ),
                DateUtil.toString( dates[1], DEFAULT_CRM_DATE_PATTERN ) );
        double salesPrice = 0.0;
        if (!result.isEmpty()) {
            salesPrice = ( double ) result.get( 0 )[0];
        }
        final Map< String, Object > response = new HashMap< String, Object >();
        response.put( SALES_PRICE, salesPrice );
        return response;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.AgentReportsBusinessService#getAgentsRevenue
     * (java.lang.String, java.lang.String, com.owners.gravitas.dto.ApiUser,
     * int)
     */
    @Override
    public Map< String, Object > getAgentsRevenue( final String fromDate, final String toDate, final ApiUser apiUser,
            final Integer rankFilter ) {
        List< Object[] > result = null;
        boolean isLoggedInAgent = false;
        String loggedInAgentEmail = null;

        if (apiUser.getRoles().contains( UserRole.FIREBASE_AGENT ) || rankFilter == null) {
            result = contactServiceV1.getRevenueOfAgents( fromDate, toDate );
            isLoggedInAgent = true;
            loggedInAgentEmail = apiUser.getEmail();
        } else {
            result = contactServiceV1.getRevenueOfAgents( fromDate, toDate, rankFilter );
            isLoggedInAgent = false;
            loggedInAgentEmail = null;
        }
        return getAgentsRevenue( result, isLoggedInAgent, loggedInAgentEmail );
    }

    /**
     * Gets the agents revenue.
     *
     * @param result
     *            the result
     * @param isLoggedInAgent
     *            the is logged in agent
     * @param loggedInAgentEmail
     *            the logged in agent email
     * @return the agents revenue
     */
    private Map< String, Object > getAgentsRevenue( final List< Object[] > result, final boolean isLoggedInAgent,
            final String loggedInAgentEmail ) {
        final Map< String, Object > response = new HashMap< String, Object >();

        final List< String > finalAgentEmails = result.stream().map( r -> ( String ) r[1] )
                .collect( Collectors.toList() );

        final List< User > googleUsers = userService.getUsersByEmails( finalAgentEmails );

        // set agents details from google profile
        final AgentsResponse agentsResponse = agentsResponseBuilder.convertTo( googleUsers );

        // set agent details like status,score and revenue from database
        final AgentsResponse agentsResponseWithRevenue = buildAgentsRevenueResponse( agentsResponse, result );

        com.owners.gravitas.dto.Agent agent = null;
        if (isLoggedInAgent && loggedInAgentEmail != null) {
            final List< com.owners.gravitas.dto.Agent > agents = agentsResponseWithRevenue.getAgents().stream()
                    .filter( r -> r.getEmail().equalsIgnoreCase( loggedInAgentEmail ) ).collect( Collectors.toList() );
            if (!agents.isEmpty()) {
                agent = agents.get( 0 );
            }
        }

        response.put( AGENTS, agentsResponseWithRevenue.getAgents() );
        response.put( LOGGED_IN_AGENT, agent );
        return response;
    }

    /**
     * Builds the agents revenue response.
     *
     * @param agentsResponse
     *            the agents response
     * @param result
     *            the result
     * @return the agents response
     */
    private AgentsResponse buildAgentsRevenueResponse( final AgentsResponse agentsResponse,
            final List< Object[] > result ) {
        final AgentsResponse finalAgentsResponse = new AgentsResponse();
        for ( final Object[] objects : result ) {
            for ( final com.owners.gravitas.dto.Agent agent : agentsResponse.getAgents() ) {
                if (agent.getEmail().equalsIgnoreCase( ( String ) objects[1] )) {
                    final BigDecimal score = ( BigDecimal ) objects[2];
                    agent.setRevenue( ( double ) objects[0] );
                    agent.setScore( score.doubleValue() );
                    agent.setStatus( ( String ) objects[3] );
                    finalAgentsResponse.getAgents().add( agent );
                    break;
                }
            }
        }
        return finalAgentsResponse;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentReportsBusinessService#
     * getAgentStatistics(java.lang.String)
     */
    @Override
    public AgentStatisticsResponse getAgentStatistics( final String email ) {

        LOGGER.info( "Start: Fetching Agent Face to Face statistics" );
        final List< Object[] > statisticsList = agentReportService.getAgentStatistics( email );
        final AgentStatisticsResponse baseResponse = createFaceToFaceResponse( statisticsList, email, false );
        LOGGER.info( "End: Fetched Agent Face to Face statistics" );
        return baseResponse;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentReportsBusinessService#
     * getManagingBrokerStatistics(java.lang.String)
     */
    @Override
    public AgentStatisticsResponse getManagingBrokerStatistics( final String email ) {

        LOGGER.info( "Start: Fetching Managing Brocker Face to Face statistics" );
        final List< Object[] > statisticsList = agentReportService.getManagingBrokerStatistics( email );
        final AgentStatisticsResponse baseResponse = createMBFaceToFaceResponse( statisticsList, email, false );
        LOGGER.info( "End: Fetched Managing Brocker Face to Face statistics" );
        return baseResponse;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentReportsBusinessService#
     * getFaceToFaceStatistics(java.lang.String)
     */
    @Override
    public AgentCumulativeResponse getFaceToFaceStatistics( final String email, final boolean isWeek,
            final boolean isForAgent ) {

        LOGGER.info( "Start: Fetching Managing Brocker Cumulative Face to Face statistics" );
        final List< Object[] > statisticsList;
        if (isWeek) {
            statisticsList = agentReportService.getFaceToFaceGridStatistics( email, isForAgent );
        } else {
            statisticsList = agentReportService.getFaceToFaceStatistics( email, isForAgent );
        }
        final AgentCumulativeResponse baseResponse = createFaceToFaceCountResponse( statisticsList, isForAgent,
                isWeek );
        LOGGER.info( "End: Fetched Managing Brocker Cumulative Face to Face statistics" );
        return baseResponse;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentReportsBusinessService#
     * downloadAgentStatistics(java.lang.String)
     */
    @Override
    public Workbook downloadAgentStatistics( final String email, final boolean isForAgent ) {

        LOGGER.info( "Start: Fetching Agent Face to Face statistics" );
        final List< Object[] > statisticsList = agentReportService.getAgentStatistics( email );
        final Workbook workbook = createExcelStatistics( statisticsList, email );
        LOGGER.info( "End: Fetched Agent Face to Face statistics" );
        return workbook;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentReportsBusinessService#
     * downloadManagingBrokerStatistics(java.lang.String)
     */
    @Override
    public Workbook downloadManagingBrokerStatistics( final String email ) {

        LOGGER.info( "Start: Fetching Agent Face to Face statistics" );
        final List< Object[] > statisticsList = agentReportService.getManagingBrokerStatistics( email );
        final Workbook workbook = createExcelStatistics( statisticsList, null );
        LOGGER.info( "End: Fetched Agent Face to Face statistics" );
        return workbook;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentReportsBusinessService#
     * downloadFaceToFaceStatistics(java.lang.String)
     */
    @Override
    public Workbook downloadFaceToFaceStatistics( final String email, final boolean isForAgent ) {
        LOGGER.info( "Start: Fetching Agent Face to Face statistics" );
        final List< Object[] > statisticsList = agentReportService.getFaceToFaceCountDownload( email, isForAgent );
        final Set< String > emailList = new LinkedHashSet<>();
        if (isForAgent) {
            emailList.add( email );
        } else {
            statisticsList.forEach( entry -> {
                emailList.add( getStringValue( entry[2] ) );
            } );
        }

        final Map< String, String > userMap = getAgentNameMap( new ArrayList( emailList ) );

        final Workbook workbook = agentsResponseBuilder.createFaceToFaceCountExcel( statisticsList, userMap );
        LOGGER.info( "End: Fetched Agent Face to Face statistics" );
        return workbook;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentReportsBusinessService#
     * getAgentGridStatistics(java.lang.String)
     */
    @Override
    public AgentStatisticsResponse getAgentGridStatistics( final String email ) {

        LOGGER.info( "Start: Fetching Agent Face to Face statistics" );
        final List< Object[] > statisticsList = agentReportService.getAgentGridStatistics( email );
        final AgentStatisticsResponse baseResponse = createFaceToFaceResponse( statisticsList, email, true );
        LOGGER.info( "End: Fetched Agent Face to Face statistics" );
        return baseResponse;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentReportsBusinessService#
     * getManagingBrokerGridStatistics(java.lang.String)
     */
    @Override
    public AgentStatisticsResponse getManagingBrokerGridStatistics( final String email ) {

        LOGGER.info( "Start: Fetching Managing Brocker Face to Face statistics" );
        final List< Object[] > statisticsList = agentReportService.getManagingBrokerGridStatistics( email );
        final AgentStatisticsResponse baseResponse = createMBFaceToFaceResponse( statisticsList, email, true );
        LOGGER.info( "End: Fetched Managing Brocker Face to Face statistics" );
        return baseResponse;
    }

    /**
     * Build the Workbook object for Excel download.
     *
     * @param statisticsList
     *            the statistics list
     * @param agentEmailId
     *            the agent email id
     * @return the Workbook object for Excel download
     */
    private Workbook createExcelStatistics( final List< Object[] > statisticsList, final String agentEmailId ) {
        LOGGER.info( "Start: Create Excel of Agent statistics" );
        Map< String, String > userMap = new HashMap<>();
        final Set< String > emailSet = new LinkedHashSet< String >();

        if (!StringUtils.isEmpty( agentEmailId )) {
            emailSet.add( agentEmailId );
        } else {
            statisticsList.forEach( entry -> {
                emailSet.add( getStringValue( entry[6] ) );
            } );
        }

        userMap = getAgentNameMap( new ArrayList< String >( emailSet ) );
        final Workbook workbook = agentsResponseBuilder.createExcelReport( statisticsList, userMap );
        LOGGER.info( "End: Create Excel of Agent statistics" );
        return workbook;
    }

    /**
     * Build the map of Google users information.
     *
     * @param emailList
     *            the email list
     * @return the map of Google users information
     */
    private Map< String, String > getAgentNameMap( final List< String > emailList ) {
        LOGGER.info( "Start: Get Google Users information" );
        final Map< String, String > userMap = new HashMap<>();
        final List< User > googleUsers = userService.getUsersByEmails( emailList );
        for ( final User googleUser : googleUsers ) {
            final UserName googleUserName = googleUser.getName();
            userMap.put( googleUser.getPrimaryEmail(),
                    googleUserName.getGivenName() + " " + googleUserName.getFamilyName() );
        }
        LOGGER.info( "Ends: Get Google Users information" );
        return userMap;
    }

    /**
     * Build the Managing Broker FaceToFace Response.
     *
     * @param f2fStaticsData
     *            the f 2 f statics data
     * @param agentEmailId
     *            the agent email id
     * @param isGrid
     *            the is grid
     * @return the Managing Broker FaceToFace Response
     */
    private AgentStatisticsResponse createMBFaceToFaceResponse( final List< Object[] > f2fStaticsData,
            final String agentEmailId, final boolean isGrid ) {
        LOGGER.info( "Starts: Creating managing broker face to face response" );
        final Set< String > emailSet = new LinkedHashSet< String >();

        f2fStaticsData.forEach( entry -> {
            emailSet.add( getStringValue( entry[6] ) );
        } );
        final Map< String, String > userMap = getAgentNameMap( new ArrayList< String >( emailSet ) );
        final AgentStatisticsResponse baseResponse = createAgentFaceToFaceResponse( f2fStaticsData, userMap, isGrid );
        LOGGER.info( "Ends: Creating managing broker face to face response" );
        return baseResponse;
    }

    /**
     * Build the Executive FaceToFace Response.
     *
     * @param f2fStaticsData
     *            the f 2 f statics data
     * @param isGrid
     *            the is grid
     * @return the Executive FaceToFace Response
     */
    private AgentStatisticsResponse createExecutiveFaceToFaceResponse( final List< Object[] > f2fStaticsData,
            final boolean isGrid ) {
        LOGGER.info( "Starts: Creating managing broker face to face response" );
        final Set< String > emailSet = new LinkedHashSet< String >();

        f2fStaticsData.forEach( entry -> {
            emailSet.add( getStringValue( entry[6] ) );
        } );

        // Adding Managing Broker email ids
        final List< Object[] > agentsAndMBList = agentReportService
                .getAgentsAndTheirManagingBrokerEmailID( new ArrayList< String >( emailSet ) );
        // Adding MB email id list
        agentsAndMBList.forEach( entry -> {
            emailSet.add( getStringValue( entry[1] ) );
        } );

        final List< String > emailList = new ArrayList< String >( emailSet );
        final Map< String, String > userMap = getAgentNameMap( emailList );

        // Prepare Map (AgentEmail -> MB Name)
        final Map< String, String > agentEmailIdAndMBNameMap = new HashMap< String, String >();
        agentsAndMBList.forEach( entry -> {
            agentEmailIdAndMBNameMap.put( getStringValue( entry[0] ),
                    getUserNameBasedOnEmailId( getStringValue( entry[1] ), userMap ) );
        } );

        final AgentStatisticsResponse baseResponse = createManagingBrokerFaceToFaceResponse( f2fStaticsData,
                agentEmailIdAndMBNameMap, userMap, isGrid );
        LOGGER.info( "Ends: Creating managing broker face to face response" );
        return baseResponse;
    }

    /**
     * Build the faceToFace Response for the Agent.
     *
     * @param f2fStaticsData
     *            the f 2 f statics data
     * @param emailId
     *            the email id
     * @param isGrid
     *            the is grid
     * @return the faceToFace Response for the Agent
     */
    private AgentStatisticsResponse createFaceToFaceResponse( final List< Object[] > f2fStaticsData,
            final String emailId, final boolean isGrid ) {
        LOGGER.info( "Starts: Creating Agent face to face response" );
        final List< String > emailList = new ArrayList<>();
        emailList.add( emailId );
        final Map< String, String > userMap = getAgentNameMap( emailList );
        final AgentStatisticsResponse baseResponse = createAgentFaceToFaceResponse( f2fStaticsData, userMap, isGrid );
        LOGGER.info( "Ends: Creating Agent face to face response" );
        return baseResponse;
    }

    /**
     * Build the FaceToFace Count Response for Executive.
     *
     * @param f2fStaticsData
     *            the f 2 f statics data
     * @param isWeek
     *            the is week
     * @return the FaceToFace Count Response for Executive i.e. for all Managing
     *         Broker
     */
    private AgentCumulativeResponse createFaceToFaceCountExecutiveResponse( final List< Object[] > f2fStaticsData,
            final boolean isWeek ) {
        LOGGER.info( "Starts: Creating Cumulative face to face response" );
        final Map< String, String > userMap = getUserMap( f2fStaticsData );
        AgentCumulativeResponse baseResponse = new AgentCumulativeResponse();

        baseResponse = getExecutiveCumulativef2fStaticsGraphResponse( f2fStaticsData, userMap );
        LOGGER.info( "Ends: Creating Cumulative face to face response" );
        return baseResponse;
    }

    /**
     * Gets the executive cumulativef 2 f statics graph response.
     *
     * @param f2fStaticsData
     *            the f 2 f statics data
     * @param userMap
     *            the user map
     * @return the executive cumulativef 2 f statics graph response
     */
    private AgentCumulativeResponse getExecutiveCumulativef2fStaticsGraphResponse(
            final List< Object[] > f2fStaticsData, final Map< String, String > userMap ) {

        final Map< String, List< AgentCumulativeStatistics > > managingBrokerMap = new HashMap< String, List< AgentCumulativeStatistics > >();
        final AgentCumulativeResponse baseResponse = new AgentCumulativeResponse();

        for ( final Object[] entry : f2fStaticsData ) {
            final AgentCumulativeStatistics response = new AgentCumulativeStatistics();
            response.setCreatedOn( getStringValue( entry[1] ) );
            response.setF2fCount( getStringValue( entry[0] ) );
            final String mbEmail = getStringValue( entry[2] );

            final List< AgentCumulativeStatistics > mbList = managingBrokerMap.get( mbEmail );
            if (CollectionUtils.isEmpty( mbList )) {
                final List< AgentCumulativeStatistics > tmpList = new ArrayList< AgentCumulativeStatistics >();
                tmpList.add( response );
                managingBrokerMap.put( mbEmail, tmpList );
            } else {
                mbList.add( response );
            }
        }

        // Prepare Response object from Map
        final List< ManagingBrokerCumulativeStatistics > managingBrokerCumulativeStatisticsList = new ArrayList< ManagingBrokerCumulativeStatistics >();
        for ( final String emailId : managingBrokerMap.keySet() ) {

            final ManagingBrokerCumulativeStatistics managingBrokerCumulativeStatistics = new ManagingBrokerCumulativeStatistics();
            managingBrokerCumulativeStatistics.setManagingBrokerEmail( emailId );
            managingBrokerCumulativeStatistics.setManagingBrokerName( getUserNameBasedOnEmailId( emailId, userMap ) );
            managingBrokerCumulativeStatistics.setAgentCumulativeList( managingBrokerMap.get( emailId ) );
            managingBrokerCumulativeStatisticsList.add( managingBrokerCumulativeStatistics );
        }
        baseResponse.setManagingBrokerStatisticsList( managingBrokerCumulativeStatisticsList );
        return baseResponse;
    }

    /**
     * Build the FaceToFace Count Response for the Agents and Managing Broker.
     *
     * @param f2fStaticsData
     *            the f 2 f statics data
     * @param isForAgent
     *            the is for agent
     * @param isWeek
     *            the is week
     * @return the FaceToFace Count Response for the Agents and Managing Broker
     */
    private AgentCumulativeResponse createFaceToFaceCountResponse( final List< Object[] > f2fStaticsData,
            final boolean isForAgent, final boolean isWeek ) {
        LOGGER.info( "Starts: Creating Cumulative face to face response" );
        Map< String, String > userMap = new HashMap<>();
        if (isForAgent || isWeek) {
            LOGGER.info(
                    "Getting Google users information for the case of Agnet and if isWeek true for Managin Broker" );
            userMap = getUserMap( f2fStaticsData );
        }

        final AgentCumulativeResponse baseResponse = new AgentCumulativeResponse();
        final List< AgentCumulativeStatistics > f2fStaticsResponse = new ArrayList<>();
        for ( final Object[] entry : f2fStaticsData ) {
            LOGGER.info( "Creating AgentCumulativeStatistics Object" );
            final AgentCumulativeStatistics response = new AgentCumulativeStatistics();
            if (!isWeek) {
                response.setCreatedOn( getStringValue( entry[1] ) );
            }
            if (isForAgent || isWeek) { // this we need to set in case of isweek
                                        // if true and it is for MB or for Agent
                response.setAgentName( getUserNameBasedOnEmailId( getStringValue( entry[2] ), userMap ) );
            }
            response.setF2fCount( getStringValue( entry[0] ) );
            f2fStaticsResponse.add( response );

        }

        baseResponse.setCumulativeStatistics( f2fStaticsResponse );
        LOGGER.info( "Ends: Creating Cumulative face to face response" );
        return baseResponse;
    }

    /**
     * Build the Google UserMap.
     *
     * @param f2fStaticsData
     *            the f 2 f statics data
     * @return the Google UserMap
     */
    private Map< String, String > getUserMap( final List< Object[] > f2fStaticsData ) {
        LOGGER.info( "Start: getUserMap for the list of email id's" );
        final Set< String > emailSet = new LinkedHashSet< String >();

        f2fStaticsData.forEach( entry -> {
            emailSet.add( getStringValue( entry[2] ) );
        } );
        final List< String > emailList = new ArrayList< String >( emailSet );
        LOGGER.debug( "Fetching userMap from google for emails => {}", JsonUtil.toJsonString( emailList ) );
        final Map< String, String > userMap = getAgentNameMap( emailList );
        LOGGER.debug( "Fetched userMap from google. userMap => {}", JsonUtil.toJsonString( userMap ) );
        LOGGER.info( "End: getUserMap for the list of email id's" );
        return userMap;
    }

    /**
     * Build the Response for the Agent.
     *
     * @param f2fStaticsData
     *            the f 2 f statics data
     * @param agentEmailIdAndMBNameMap
     *            the agent email id and MB name map
     * @param userMap
     *            the user map
     * @param isGrid
     *            the is grid
     * @return the Response for the Agent
     */
    private AgentStatisticsResponse createManagingBrokerFaceToFaceResponse( final List< Object[] > f2fStaticsData,
            final Map< String, String > agentEmailIdAndMBNameMap, final Map< String, String > userMap,
            final boolean isGrid ) {
        LOGGER.info( "Start: creating Agent statistics face to face respsonse" );
        final Map< String, List< OpportunityDetails > > oppResponse = new LinkedHashMap< String, List< OpportunityDetails > >();
        final AgentStatisticsResponse baseResponse = new AgentStatisticsResponse();
        final List< AgentSatistics > f2fStaticsResponse = new ArrayList< AgentSatistics >();

        f2fStaticsData.forEach( entry -> {
            final String agentId = getStringValue( entry[0] );
            final String oppFirstName = getStringValue( entry[2] );
            final String oppLastName = getStringValue( entry[3] );
            final String f2fCreatedOn = getStringValue( entry[4] );
            final String opportunityCreatedOn = getStringValue( entry[9] );

            LOGGER.info( "Start: creating Agentstatistics Object for Agent id:" + agentId + "With Opportunity Name :"
                    + oppFirstName + oppLastName + "with created on date is:" + f2fCreatedOn );
            /* Creating OpportunityDetails object */
            final OpportunityDetails opportunityDetails = buildOpportunityDetails( entry, oppFirstName, oppLastName );
            List< OpportunityDetails > responseList = new ArrayList<>();
            if (isGrid) {
                responseList = oppResponse.get( agentId );
            } else {
                responseList = oppResponse.get( agentId + f2fCreatedOn );
            }
            if (responseList == null) {

                responseList = new ArrayList<>();
                AgentSatistics agentSatistics = new AgentSatistics();
                if (isGrid) {
                    oppResponse.put( agentId, responseList );
                    agentSatistics = buildAgentSatistics(
                            getUserNameBasedOnEmailId( getStringValue( entry[6] ), userMap ), f2fCreatedOn,
                            opportunityCreatedOn, responseList );
                } else {
                    agentSatistics = buildAgentSatistics(
                            getUserNameBasedOnEmailId( getStringValue( entry[6] ), userMap ), f2fCreatedOn, null,
                            responseList );
                    oppResponse.put( agentId + f2fCreatedOn, responseList );
                }
                agentSatistics.setManagingBrokerName( agentEmailIdAndMBNameMap.get( getStringValue( entry[6] ) ) );
                f2fStaticsResponse.add( agentSatistics );
            }
            responseList.add( opportunityDetails );

        } );

        baseResponse.setAgentSatistics( f2fStaticsResponse );
        LOGGER.info( "Ends: creating Agent statistics face to face respsonse" );
        return baseResponse;
    }

    /**
     * Build the Response for the Agent.
     *
     * @param f2fStaticsData
     *            the f 2 f statics data
     * @param userMap
     *            the user map
     * @param isGrid
     *            the is grid
     * @return the Response for the Agent
     */
    private AgentStatisticsResponse createAgentFaceToFaceResponse( final List< Object[] > f2fStaticsData,
            final Map< String, String > userMap, final boolean isGrid ) {
        LOGGER.info( "Start: creating Agent statistics face to face respsonse" );
        final Map< String, List< OpportunityDetails > > oppResponse = new LinkedHashMap< String, List< OpportunityDetails > >();
        final AgentStatisticsResponse baseResponse = new AgentStatisticsResponse();
        final List< AgentSatistics > f2fStaticsResponse = new ArrayList< AgentSatistics >();

        f2fStaticsData.forEach( entry -> {
            final String agentId = getStringValue( entry[0] );
            final String oppFirstName = getStringValue( entry[2] );
            final String oppLastName = getStringValue( entry[3] );
            final String f2fCreatedOn = getStringValue( entry[4] );
            final String opportunityCreatedOn = getStringValue( entry.length == 10 ? entry[9] : entry[8] );

            LOGGER.info( "Start: creating Agentstatistics Object for Agent id:" + agentId + "With Opportunity Name :"
                    + oppFirstName + oppLastName + "with created on date is:" + f2fCreatedOn );
            /* Creating OpportunityDetails object */
            final OpportunityDetails opportunityDetails = buildOpportunityDetails( entry, oppFirstName, oppLastName );
            List< OpportunityDetails > responseList = new ArrayList<>();
            if (isGrid) {
                responseList = oppResponse.get( agentId );
            } else {
                responseList = oppResponse.get( agentId + f2fCreatedOn );
            }
            if (responseList == null) {

                responseList = new ArrayList<>();
                AgentSatistics agentSatistics = new AgentSatistics();
                if (isGrid) {
                    oppResponse.put( agentId, responseList );
                    agentSatistics = buildAgentSatistics(
                            getUserNameBasedOnEmailId( getStringValue( entry[6] ), userMap ), f2fCreatedOn,
                            opportunityCreatedOn, responseList );
                } else {
                    agentSatistics = buildAgentSatistics(
                            getUserNameBasedOnEmailId( getStringValue( entry[6] ), userMap ), f2fCreatedOn, null,
                            responseList );
                    oppResponse.put( agentId + f2fCreatedOn, responseList );
                }
                f2fStaticsResponse.add( agentSatistics );
            }
            responseList.add( opportunityDetails );

        } );

        baseResponse.setAgentSatistics( f2fStaticsResponse );
        LOGGER.info( "Ends: creating Agent statistics face to face respsonse" );
        return baseResponse;
    }

    /**
     * Build the Agent Statistics Object.
     *
     * @param name
     *            the name
     * @param f2fCreatedOn
     *            the created on
     * @param opportunityDetailsList
     *            the opportunity details list
     * @return Agent Statistics Object
     */
    private AgentSatistics buildAgentSatistics( final String name, final String f2fCreatedOn,
            final String opportunityCreatedOn, final List< OpportunityDetails > opportunityDetailsList ) {
        LOGGER.info( "Start : buildAgentSatistics" );
        final AgentSatistics agentSatistics = new AgentSatistics();
        agentSatistics.setOpportunity( opportunityDetailsList );
        agentSatistics.setAgentName( name );
        if (!StringUtils.isEmpty( f2fCreatedOn )) {
            agentSatistics.setF2fCreatedOn( f2fCreatedOn );
        }
        if (!StringUtils.isEmpty( opportunityCreatedOn )) {
            agentSatistics.setOpportunityCreatedOn( opportunityCreatedOn );
        }
        LOGGER.info( "Ends : buildAgentSatistics" );
        return agentSatistics;
    }

    /**
     * Build the OpportunityDetails Object.
     *
     * @param entry
     *            the entry
     * @param oppFirstName
     *            the opp first name
     * @param oppLastName
     *            the opp last name
     * @return the OpportunityDetails Object
     */
    private OpportunityDetails buildOpportunityDetails( final Object[] entry, final String oppFirstName,
            final String oppLastName ) {
        final OpportunityDetails oppDetails = new OpportunityDetails();
        oppDetails.setName( oppFirstName + " " + oppLastName );
        oppDetails.setId( getStringValue( entry[5] ) );
        oppDetails.setF2fCreatedOn( getStringValue( entry[4] ) );
        oppDetails.setOpportunityCreatedOn( getStringValue( entry.length==10?entry[9]:entry[8] ) );
        return oppDetails;
    }

    /**
     * Gets the from and to dates.
     *
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @return the from and to dates
     */
    private Date[] getFromAndToDates( final String fromDate, final String toDate ) {
        final Date[] dates = new Date[2];
        if (StringUtils.isEmpty( fromDate ) || StringUtils.isEmpty( toDate )) {
            dates[0] = toSqlDate( clientStatisticsConfig.getFromDate(), DEFAULT_CRM_DATE_PATTERN );
            dates[1] = toSqlDate( clientStatisticsConfig.getToDate(), DEFAULT_CRM_DATE_PATTERN );
        } else {
            dates[0] = toSqlDate( fromDate, DEFAULT_CRM_DATE_PATTERN );
            dates[1] = toSqlDate( toDate, DEFAULT_CRM_DATE_PATTERN );
        }
        return dates;
    }

    /**
     * Gets the readable median time str.
     *
     * @param responseTimes
     *            the response times
     * @return the readable median time str
     */
    private String getReadableMedianTimeStr( final List< Long > responseTimes ) {
        String result = null;
        if (isNotEmpty( responseTimes )) {
            result = getReadableTime( getMedian( responseTimes ), true, false );
        }
        return result;
    }

    /**
     * Gets the clients response time.
     *
     * @param responseTimes
     *            the response times
     * @return the clients response time
     */
    private Map< String, List< Long > > getClientsResponseTime( final List< Object[] > responseTimes ) {
        final Map< String, List< Long > > clientResponseTimes = new HashMap<>();
        clientResponseTimes.put( BUYER.getType(), new ArrayList<>() );
        clientResponseTimes.put( SELLER.getType(), new ArrayList<>() );
        if (isNotEmpty( responseTimes )) {
            for ( final Object[] object : responseTimes ) {
                try {
                    final String type = convertObjectToString( object[0] ).trim();
                    final Long time = Long.valueOf( getStringValue( object[1] ) );
                    clientResponseTimes.get( type ).add( time );
                } catch ( final Exception e ) {
                    LOGGER.error( e.getMessage() );
                }
            }
        }
        return clientResponseTimes;
    }

    /**
     * Validate managing broker.
     *
     * @param email
     *            the email
     */
    private void validateManagingBroker( final String email ) {
        LOGGER.info( "Validating managing broker for agent:" + email );
        if (!gravitasWebUtil.getAppUserEmail().equalsIgnoreCase( userService.getManagingBroker( email ).getEmail() )) {
            throw new AgentInvalidException( email + " is not valid managing broker email" );
        }
    }

    /**
     * Builds the client statistics respone.
     *
     * @param stageCtaDetails
     *            the stage cta details
     * @param stageMedianDetails
     *            the stage median details
     * @return the client statistics response
     */
    private ClientStatisticsResponse buildClientStatisticsRespone(
            final Map< String, Map< String, Map< String, String > > > stageCtaDetails,
            final Map< String, Map< String, String > > stageMedianDetails ) {
        final ClientStatisticsResponse response = new ClientStatisticsResponse();
        final Map< String, Map< String, ClientStatisticsDTO > > ctaData = new HashMap<>();
        ctaData.put( BUYER.getType(), new HashMap<>() );
        ctaData.put( SELLER.getType(), new HashMap<>() );
        populateCtaData( stageCtaDetails, ctaData );
        populateMedianStageData( stageMedianDetails, ctaData );
        response.setCtaData( ctaData );
        return response;
    }

    /**
     * Populate cta data.
     *
     * @param stageCtaDetails
     *            the stage cta details
     * @param ctaData
     *            the cta data
     */
    private void populateCtaData( final Map< String, Map< String, Map< String, String > > > stageCtaDetails,
            final Map< String, Map< String, ClientStatisticsDTO > > ctaData ) {
        for ( final Map.Entry< String, Map< String, Map< String, String > > > ctaEntry : stageCtaDetails.entrySet() ) {
            final Map< String, ClientStatisticsDTO > ctaMap = ctaData.get( ctaEntry.getKey() );
            for ( final Map.Entry< String, Map< String, String > > stageEntry : ctaEntry.getValue().entrySet() ) {
                final Map< String, String > ctaValue = stageEntry.getValue();
                final ClientStatisticsDTO dto = new ClientStatisticsDTO();
                dto.setCall( ctaValue.get( CALL_KEY ) );
                dto.setEmail( ctaValue.get( EMAIL_KEY ) );
                dto.setSms( ctaValue.get( SMS_KEY ) );
                dto.setTotal( ctaValue.get( TOTAL_KEY ) );
                ctaMap.put( stageEntry.getKey(), dto );
            }
        }
    }

    /**
     * Populate median stage data.
     *
     * @param stageMedianDetails
     *            the stage median details
     * @param ctaData
     *            the cta data
     */
    private void populateMedianStageData( final Map< String, Map< String, String > > stageMedianDetails,
            final Map< String, Map< String, ClientStatisticsDTO > > ctaData ) {
        for ( final Map.Entry< String, Map< String, String > > stageMedianEntry : stageMedianDetails.entrySet() ) {
            final Map< String, ClientStatisticsDTO > ctaMap = ctaData.get( stageMedianEntry.getKey() );
            for ( final Map.Entry< String, String > entry : stageMedianEntry.getValue().entrySet() ) {
                final String stage = entry.getKey();
                final String duration = entry.getValue();
                ClientStatisticsDTO dto = ctaMap.get( stage );
                if (dto == null) {
                    dto = new ClientStatisticsDTO();
                    ctaMap.put( stage, dto );
                }
                dto.setDuration( duration );
            }
        }
    }

    /**
     * Filter agents on statuses.
     *
     * @param agents
     *            the agents
     * @param statuses
     *            the statuses
     * @return the list
     */
    private List< AgentDetails > filterAgentsOnStatuses( final List< AgentDetails > agents,
            final List< UserStatusType > statuses ) {
        List< UserStatusType > filters = new ArrayList<>();
        filters.add( INACTIVE );
        filters.add( ONBOARDING );
        if (CollectionUtils.isNotEmpty( statuses )) {
            filters = statuses;
        }
        final Iterator< AgentDetails > agentIterator = agents.iterator();
        while ( agentIterator.hasNext() ) {
            if (filters.contains( UserStatusType.getStatusType( agentIterator.next().getUser().getStatus() ) )) {
                agentIterator.remove();
            }
        }
        return agents;
    }

    /**
     * Gets the user emails.
     *
     * @param users
     *            the users
     * @return the user emails
     */
    private List< String > getUserEmails( final List< com.owners.gravitas.domain.entity.User > users ) {
        final List< String > emails = new ArrayList<>();
        for ( final com.owners.gravitas.domain.entity.User user : users ) {
            emails.add( user.getEmail() );
        }
        return emails;
    }

    /**
     * Builds the agent details response.
     *
     * @param agentsResponse
     *            the agents response
     * @param dbAgents
     *            the db agents
     * @return the user details response
     */
    private AgentsResponse buildAgentsResponse( final AgentsResponse agentsResponse,
            final List< AgentDetails > dbAgents ) {
        for ( final AgentDetails dbAgentDetails : dbAgents ) {
            for ( final com.owners.gravitas.dto.Agent agent : agentsResponse.getAgents() ) {
                if (agent.getEmail().equalsIgnoreCase( dbAgentDetails.getUser().getEmail() )) {
                    agent.setStatus( dbAgentDetails.getUser().getStatus() );
                    agent.setScore( dbAgentDetails.getScore() );
                    break;
                }
            }
        }
        Collections.sort( agentsResponse.getAgents() );
        return agentsResponse;
    }

    /**
     * Gets the response time.
     *
     * @param agentId
     *            the agent id
     * @return the response time
     */
    private String getResponseTime( final String agentId ) {
        final Agent agent = agentService.getAgentById( agentId );
        final Set< String > fbOpportunityIds = agent.getOpportunities().keySet();
        final DateTime fbAssignedDateTime = DateUtil.toDate( fbAssignedDate );
        List< Long > responseTimeList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty( fbOpportunityIds )) {
            responseTimeList = opportunityService.findResponseTimeByOpportunityIds( fbOpportunityIds,
                    fbAssignedDateTime );
        }
        responseTimeList = responseTimeList.stream().filter( rt -> rt != null && rt.longValue() > 0 )
                .collect( Collectors.toList() );
        String responseTime = NA;
        if (CollectionUtils.isNotEmpty( responseTimeList )) {
            final long medianTime = getMedian( responseTimeList );
            responseTime = getReadableTime( medianTime, false, false );
        }
        return responseTime;
    }

    /**
     * Compute performance overview.
     *
     * @param agents
     *            the agents
     * @return the map
     */
    private Map< String, Object > computePerformanceOverview( final List< AgentDetails > agents ) {
        final List< com.owners.gravitas.domain.entity.Opportunity > opportunities = getAllOpportunities( agents );
        LOGGER.info( "Total number of Opportunities" + opportunities.size() );
        return computeOpportunityCounts( opportunities );
    }

    /**
     * Compute opportunity counts.
     *
     * @param opportunities
     *            the opportunities
     * @return the map
     */
    private Map< String, Object > computeOpportunityCounts( final List< com.owners.gravitas.domain.entity.Opportunity > opportunities ) {
        final Map< String, Object > response = new HashMap< String, Object >();
        final Map< String, Object > buyerPerformance = new HashMap< String, Object >();
        response.put( BUYER_OPPORTUNITIES_PERFORMANCE, buyerPerformance );

        final Map< String, Object > sellerPerformance = new HashMap< String, Object >();
        response.put( SELLER_OPPORTUNITIES_PERFORMANCE, sellerPerformance );

        final Map< String, Object > buyerStageMap = new HashMap< String, Object >();
        final Map< String, Object > sellerStageMap = new HashMap< String, Object >();

        Integer buyerOppCount = 0;
        Integer sellerOppCount = 0;
        for ( final com.owners.gravitas.domain.entity.Opportunity opportunity : opportunities ) {
            final Contact contact = opportunity.getContact();
            if (BUYER.getType().equalsIgnoreCase( contact.getType() ) && contact.getStage() != null) {
                addOpportunityCount( buyerStageMap, BuyerStage.getStageType( contact.getStage() ).getResponseKey() );
                buyerOppCount++;
            } else if (SELLER.getType().equalsIgnoreCase( contact.getType() ) && contact.getStage() != null) {
                addOpportunityCount( sellerStageMap, SellerStage.getStageType( contact.getStage() ).getResponseKey() );
                sellerOppCount++;
            }
        }
        // adding total
        final Integer totalOppCount = buyerOppCount + sellerOppCount;
        buyerPerformance.put( STAGES, buyerStageMap );
        buyerPerformance.put( TOTAL, buyerOppCount );
        populateOpportunityResult( buyerPerformance, TOTAL, null, totalOppCount, buyerOppCount );
        populateStageSpecificPercentage( buyerStageMap, buyerOppCount, BUYER.getType() );

        sellerPerformance.put( STAGES, sellerStageMap );
        sellerPerformance.put( TOTAL, sellerOppCount );
        populateOpportunityResult( sellerPerformance, TOTAL, null, totalOppCount, sellerOppCount );
        populateStageSpecificPercentage( sellerStageMap, sellerOppCount, SELLER.getType() );

        return response;
    }

    /**
     * Adds the opportunity count.
     *
     * @param oppTypeMap
     *            the response
     * @param stage
     *            the stage
     */
    private void addOpportunityCount( final Map< String, Object > oppTypeMap, final String stage ) {
        // adding opportunity stage count
        final Integer oppStagecount = ( Integer ) oppTypeMap.get( stage );
        oppTypeMap.put( stage, getIncrementCountValue( oppStagecount ) );
    }

    /**
     * Gets the increment count value.
     *
     * @param count
     *            the count
     * @return the increment count value
     */
    private Integer getIncrementCountValue( Integer count ) {
        if (count == null) {
            count = 1;
        } else {
            count = count + 1;
        }
        return count;
    }

    /**
     * Adds the opportunity percent.
     *
     * @param response
     *            the response
     * @param totalOppCount
     *            the total opp count
     * @param oppType
     *            the opp type
     */
    private void populateStageSpecificPercentage( final Map< String, Object > response, final Integer totalOppCount,
            final String oppType ) {
        // adding opportunity stage percent
        for ( final String key : response.keySet() ) {
            populateOpportunityResult( response, key, getStageDisplayLabel( oppType, key ), totalOppCount,
                    ( Integer ) response.get( key ) );
        }
    }

    /**
     * Gets the stage display label.
     *
     * @param oppType
     *            the opp type
     * @param key
     *            the key
     * @return the stage display label
     */
    private String getStageDisplayLabel( final String oppType, final String key ) {
        String label = null;
        if (BUYER.getType().equalsIgnoreCase( oppType )) {
            label = BuyerStage.getDisplayLabel( key ).getStage();
        } else if (SELLER.getType().equalsIgnoreCase( oppType )) {
            label = SellerStage.getDisplayLabel( key ).getStage();
        }
        return label;

    }

    /**
     * Populate opportunity result.
     *
     * @param resultMap
     *            the result map
     * @param key
     *            the key
     * @param label
     *            the label
     * @param totalOppCount
     *            the total opp count
     * @param oppCount
     *            the opp count
     */
    private void populateOpportunityResult( final Map< String, Object > resultMap, final String key, final String label,
            final Integer totalOppCount, final Integer oppCount ) {
        final Map< String, Object > valueMap = new HashMap< String, Object >();
        addToValueMapIfNotNull( valueMap, LABEL, label );
        addToValueMapIfNotNull( valueMap, PERCENT, ( double ) oppCount * 100 / totalOppCount );
        addToValueMapIfNotNull( valueMap, COUNT, oppCount );
        resultMap.put( key, valueMap );
    }

    /**
     * Adds the to value map if not null.
     *
     * @param valueMap
     *            the value map
     * @param key
     *            the key
     * @param value
     *            the value
     */
    private void addToValueMapIfNotNull( final Map< String, Object > valueMap, final String key, final Object value ) {
        if (value != null) {
            valueMap.put( key, value );
        }
    }

    /**
     * Gets the all opportunities.
     *
     * @param agents
     *            the agents
     * @return the all opportunities
     */
    private List< com.owners.gravitas.domain.entity.Opportunity > getAllOpportunities( final List< AgentDetails > agents ) {
        final List< com.owners.gravitas.domain.entity.Opportunity > opportunities = new ArrayList< com.owners.gravitas.domain.entity.Opportunity >();
        for ( final AgentDetails agentDetails : agents ) {
            final List< com.owners.gravitas.domain.entity.Opportunity > agentOpportunities = opportunityService
                    .getAgentOpportunities( agentDetails.getUser().getEmail() );
            opportunities.addAll( agentOpportunities );
        }
        return opportunities;
    }

    /**
     * Compute agent summary.
     *
     * @param agents
     *            the agents
     * @return the map
     */
    private Map< String, Object > computeAgentSummary( final List< AgentDetails > agents ) {
        final List< com.owners.gravitas.domain.entity.Opportunity > opportunities = getAllOpportunities( agents );
        return computeAvgResponseTime( opportunities, agents.size() );
    }

    /**
     * Compute avg response time.
     *
     * @param opportunities
     *            the opportunities
     * @param countOfAgents
     *            the count of agents
     * @return the map
     */
    private Map< String, Object > computeAvgResponseTime( final List< com.owners.gravitas.domain.entity.Opportunity > opportunities,
            final Integer countOfAgents ) {
        final Map< String, Object > response = new HashMap< String, Object >();
        Long responseTime = 0L;
        int oppCountByDayThreshold = 0;
        final DateTime defaultDayThreshold = new DateTime( System.currentTimeMillis() )
                .minusDays( bestFitAgentConfig.getDefaultDayThreshold() );
        for ( final com.owners.gravitas.domain.entity.Opportunity opportunity : opportunities ) {
            if (opportunity.getResponseTime() != null) {
                responseTime = responseTime + opportunity.getResponseTime();
            }
            if (opportunity.getAssignedDate() != null && opportunity.getAssignedDate().isAfter( defaultDayThreshold )) {
                oppCountByDayThreshold++;
            }
        }
        String avgResponseTime = NA;
        double avgClientCount = 0.0;
        if (countOfAgents != 0) {
            avgClientCount = ( double ) oppCountByDayThreshold / countOfAgents;
        }
        if (CollectionUtils.isNotEmpty( opportunities )) {
            avgResponseTime = getReadableTime( responseTime / opportunities.size(), false, false );
        }
        response.put( AVERAGE_RESPONSE_TIME, avgResponseTime );
        response.put( AVERAGE_CLIENT_COUNT, avgClientCount );
        return response;
    }

    /**
     * Gets the agent analytics.
     *
     * @param agentDetailsList
     *            the agent details list
     * @return the agent analytics
     */
    private List< AgentStatistics > getAgentStatistics( final List< AgentDetails > agentDetailsList ) {
        final List< AgentStatistics > agentHisoryAnalytics = new ArrayList<>();
        for ( final AgentDetails agent : agentDetailsList ) {
            agentHisoryAnalytics.add( buildAgentStatistics( agent ) );
        }
        return agentHisoryAnalytics;
    }

    /**
     * Builds the agent analytics.
     *
     * @param agent
     *            the agent
     * @return the agent analytics
     */
    private AgentStatistics buildAgentStatistics( final AgentDetails agent ) {
        final AgentStatistics agentAnalytics = new AgentStatistics();
        agentAnalytics.setAgentDetails( agent );
        agentAnalytics.setKey( SCORE );
        agentAnalytics.setCreatedDate( new DateTime() );
        agentAnalytics.setValue( agent.getScore().toString() );
        return agentAnalytics;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentReportsBusinessService#
     * getExecutiveGridStatistics(java.lang.String)
     */
    @Override
    public AgentStatisticsResponse getExecutiveGridStatistics( final String email ) {

        LOGGER.info( "Start: Fetching Executive Face to Face statistics" );
        final List< Object[] > statisticsList = agentReportService.getExecutiveGridStatistics( email );
        if (CollectionUtils.isEmpty( statisticsList )) {
            AgentStatisticsResponse response = new AgentStatisticsResponse();
            response.setMessage( "No data available" );
            return response;
        }
        final AgentStatisticsResponse baseResponse = createExecutiveFaceToFaceResponse( statisticsList, true );
        LOGGER.info( "End: Fetched Managing Brocker Face to Face statistics" );
        return baseResponse;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentReportsBusinessService#
     * getManagingBrokerStatistics(java.lang.String)
     */
    @Override
    public AgentStatisticsResponse getExecutiveStatistics( final String email ) {

        LOGGER.info( "Start: Fetching Managing Brocker Face to Face statistics" );
        final List< Object[] > statisticsList = agentReportService.getExecutiveStatistics( email );
        final AgentStatisticsResponse baseResponse = createExecutiveFaceToFaceResponse( statisticsList, false );
        LOGGER.info( "End: Fetched Managing Brocker Face to Face statistics" );
        return baseResponse;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentReportsBusinessService#
     * getFaceToFaceStatistics(java.lang.String)
     */
    @Override
    public AgentCumulativeResponse getExecutiveFaceToFaceCount( final String email, final boolean isWeek ) {

        LOGGER.info( "Start: Fetching Managing Brocker Cumulative Face to Face statistics" );
        final List< Object[] > statisticsList;
        AgentCumulativeResponse baseResponse = null;
        if (isWeek) {
            statisticsList = agentReportService.getExecutiveFaceToFaceGrid( email );
            baseResponse = createFaceToFaceCountResponse( statisticsList, false, isWeek );
        } else {
            statisticsList = agentReportService.getExecutiveFaceToFace( email );
            baseResponse = createFaceToFaceCountExecutiveResponse( statisticsList, isWeek );
        }
        LOGGER.info( "End: Fetched Managing Brocker Cumulative Face to Face statistics" );
        return baseResponse;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentReportsBusinessService#
     * downloadManagingBrokerStatistics(java.lang.String)
     */
    @Override
    public Workbook downloadExecutiveF2FStatisticsReport( final String email ) {

        LOGGER.info( "Start: Fetching Agent Face to Face statistics for Executive" );
        final List< Object[] > statisticsList = agentReportService.getExecutiveStatistics( email );
        final List< Object[] > faceToFaceCountList = agentReportService.getExecutiveFaceToFaceDownload( email );
        final Set< String > emailSet = new LinkedHashSet< String >();
        statisticsList.forEach( entry -> {
            emailSet.add( getStringValue( entry[6] ) );
        } );
        faceToFaceCountList.forEach( entry -> {
            emailSet.add( getStringValue( entry[2] ) );
        } );

        // Adding Managing Broker email ids
        final List< Object[] > agentsAndMBList = agentReportService
                .getAgentsAndTheirManagingBrokerEmailID( new ArrayList< String >( emailSet ) );
        // Adding MB email id list
        agentsAndMBList.forEach( entry -> {
            emailSet.add( getStringValue( entry[1] ) );
        } );

        final List< String > emailList = new ArrayList< String >( emailSet );
        final Map< String, String > userMap = getAgentNameMap( emailList );
        final Workbook workbook = agentsResponseBuilder.createExecutiveReport( faceToFaceCountList, statisticsList,
                userMap, agentsAndMBList );
        LOGGER.info( "End: Fetched Agent Face to Face statistics" );
        return workbook;
    }

    /**
     * Gets the string value.
     *
     * @param object
     *            the object
     * @return the string value
     */
    private String getStringValue( final Object object ) {
        return ( null == object ) ? "" : object.toString();
    }

    /**
     * Gets the user name based on email id.
     *
     * @param emailId
     *            the email id
     * @param userMap
     *            the user map
     * @return the user name based on email id
     */
    private String getUserNameBasedOnEmailId( final String emailId, final Map< String, String > userMap ) {
        final String name = userMap.get( emailId );
        return name != null ? name : emailId;
    }

    @Override
    public CalendarEventListResponse getCalendarEventsFromAgentApp( final String agentEmail, Long startTime, Long endTime ) {
        LOGGER.info( "Get calendar events from agent app for the agent : " + agentEmail + ", startTime : " + startTime
                + ", endTime : " + endTime );
        CalendarEventListResponse agentAppCalendarEventsResponse = new CalendarEventListResponse();
        List< CalendarEvent > eventDetails = null;

        startTime = ( null == startTime ? DateUtil.getStartOfDayLongTime() : startTime );
        endTime = ( null == endTime ? DateUtil.getLongTimeAfterInputDays( calendarEventsLimit ) : endTime );
        startTime = new DateTime( startTime ).minusMinutes( 59 ).getMillis();
        LOGGER.info( "Calculated startTime : " + startTime + " and endTime : " + endTime );
        
        final List< AgentTask > agentTasks = agentTaskService.findByAgentEmailAndStatusNotAndDueDate( agentEmail,
                deleted.name(), new java.util.Date( startTime ), new java.util.Date( endTime ) );
        if (CollectionUtils.isNotEmpty( agentTasks )) {
            final Set< String > fbOpportunityIds = new HashSet<>();
            agentTasks.forEach( task -> fbOpportunityIds.add( task.getOpportunity().getOpportunityId() ) );
            final Map< String, Contact > oppTaskMap = prepareOpportunityMap( fbOpportunityIds );
            CalendarEvent eventDetail = null;
            eventDetails = new ArrayList<>();
            for ( final AgentTask task : agentTasks ) {
                final Contact contact = populateClientInfo( task.getOpportunity().getOpportunityId(), oppTaskMap );
                eventDetail = calendarEventsResponseBuilder.convertFrom( task, contact );
                eventDetails.add( eventDetail );
            }
            agentAppCalendarEventsResponse.setCalendarEvents( eventDetails );
            
            LOGGER.info( "Agent Calendar events response :" + JsonUtil.toJsonString( agentAppCalendarEventsResponse ) );
        } else {
            LOGGER.info( "No events found for the agent: " + agentEmail );
            agentAppCalendarEventsResponse = new CalendarEventListResponse( "No events found" );
        }
        final String timeZone = agentDetailsService.getAgentsTimeZone(agentEmail);
        agentAppCalendarEventsResponse.setTimezone(timeZone);
        
        return agentAppCalendarEventsResponse;
    }

    private Contact populateClientInfo( final String fbOpportunityId, final Map< String, Contact > oppTaskMap ) {
        final Contact contact = oppTaskMap.get( fbOpportunityId );
        return contact;
    }

    private Map< String, Contact > prepareOpportunityMap( final Set< String > fbOpportunityIds ) {
        final List< Contact > contacts = contactServiceV1.getAllContactByFbOpportunityId( fbOpportunityIds );
        final Map< String, Contact > oppTaskMap = new HashMap<>();

        for ( final Contact contact : contacts ) {
            for ( final com.owners.gravitas.domain.entity.Opportunity opportunityV1 : contact.getOpportunities() ) {
                oppTaskMap.put( opportunityV1.getOpportunityId(), contact );
            }
        }
        return oppTaskMap;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentReportsBusinessService#
     * getAllAgentStates()
     */
    @Override
    public AgentStateResponse getAllAgentStates() {
        final Map< String, String > stateToStateNameMap = timeZoneUtil.getStateToStateNameMapping();
        final AgentStateResponse response = new AgentStateResponse();
        response.setStatus( Status.FAILURE );
        response.setMessage( "Unable to fetch the states details, check if gravitas_timezone.json is updated one!!" );
        if (null != stateToStateNameMap && !stateToStateNameMap.isEmpty()) {
            final List< AgentStatesDetails > agentStatesDetailsList = stateToStateNameMap.entrySet().stream()
                    .map( e -> new AgentStatesDetails( e.getKey(), e.getValue() ) ).collect( Collectors.toList() );
            response.setAgentStatesDetails( agentStatesDetailsList );
            response.setStatus( Status.SUCCESS );
            response.setMessage( "All states details fetched successfully." );
        }
        return response;
    }
    
    @Override
    public AgentAvailableTimeResponse getAgentAvailableTime() {
    	final AgentAvailableTimeResponse response = new AgentAvailableTimeResponse();
    	response.setStatus( Status.FAILURE );
        response.setMessage( "Failure" );
        String startTime = agentOpportunityBusinessConfig.getAgentScheduleMetingStartTime();
        String endTime = agentOpportunityBusinessConfig.getAgentScheduleMetingEndTime();
        if(StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)){
        	response.setStartTime(startTime);
        	response.setEndTime(endTime);
        	response.setStatus( Status.SUCCESS );
            response.setMessage( "Success" );
        }
        return response;
    }
    
}
