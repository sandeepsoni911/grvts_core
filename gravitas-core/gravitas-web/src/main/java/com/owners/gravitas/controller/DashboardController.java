package com.owners.gravitas.controller;

import static com.owners.gravitas.constants.UserPermission.SAVE_SEARCH;
import static com.owners.gravitas.constants.UserPermission.VIEW_AGENT_REPORT;
import static com.owners.gravitas.constants.UserPermission.VIEW_AGENT_REVENUE;
import static com.owners.gravitas.constants.UserPermission.VIEW_AGENT_TASKS;
import static com.owners.gravitas.constants.UserPermission.VIEW_CLIENT_STATISTICS;
import static com.owners.gravitas.constants.UserPermission.VIEW_EXECUTIVE_REPORT;
import static com.owners.gravitas.constants.UserPermission.VIEW_MANAGING_BROKER_REPORT;
import static com.owners.gravitas.constants.UserRole.MANAGING_BROKER;
import static com.owners.gravitas.util.DateUtil.DEFAULT_CRM_DATE_PATTERN;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hubzu.cms.client.dto.CmsResponse;
import com.owners.gravitas.annotation.PerformanceLog;
import com.owners.gravitas.annotation.ReadArgs;
import com.owners.gravitas.business.AgentReportsBusinessService;
import com.owners.gravitas.dto.response.AgentAvailableTimeResponse;
import com.owners.gravitas.dto.response.AgentCumulativeResponse;
import com.owners.gravitas.dto.response.AgentStateResponse;
import com.owners.gravitas.dto.response.AgentStatisticsResponse;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.ClientFirstResponseTime;
import com.owners.gravitas.dto.response.ClientStatisticsResponse;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.service.GravitasCMSService;
import com.owners.gravitas.util.DateUtil;
import com.owners.gravitas.util.GravitasWebUtil;
import com.owners.gravitas.validator.UserRoleValidator;
import com.owners.gravitas.validators.Date;

/**
 * The Class DashboardController.
 *
 * @author pabhishek
 */
@RestController
public class DashboardController extends BaseWebController {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( DashboardController.class );

    /** The agent reports business service. */
    @Autowired
    private AgentReportsBusinessService agentReportsBusinessService;

    /** The user role validator. */
    @Autowired
    private UserRoleValidator userRoleValidator;

    /** The gravitas web util. */
    @Autowired
    private GravitasWebUtil gravitasWebUtil;
    
    @Autowired
    private GravitasCMSService gravitasCMSService;

    /** The error log file path. */
    @Value( "${face_to_face_meetings}" )
    private String face_to_face_meetings;

    /** The error log file path. */
    @Value( "${opportunities_considered}" )
    private String opportunities_considered;

    /**
     * Gets the manager opportunity report.
     *
     * @param email
     *            the email
     * @return the manager opportunity report
     */
    @CrossOrigin
    @RequestMapping( value = { "/managers/{email}/opportunities",
            "/users/{email}/reportees/opportunities-summary" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @Secured( { VIEW_MANAGING_BROKER_REPORT } )
    @PerformanceLog
    public Map< String, Object > getManagerOpportunityReport( @PathVariable final String email ) {
        userRoleValidator.validateByUserEmail( email, MANAGING_BROKER );
        LOGGER.info( "Fetching opportunity report of agents under managing broker :" + email );
        return agentReportsBusinessService.getManagerOpportunityReport( email );
    }

    /**
     * Gets the agent opportunity report.
     *
     * @param email
     *            the email
     * @return the agent opportunity report
     */
    @CrossOrigin
    @RequestMapping( value = { "/agents/{email}/opportunities",
            "/users/{email}/opportunities-summary" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @Secured( { VIEW_AGENT_REPORT, VIEW_MANAGING_BROKER_REPORT } )
    @PerformanceLog
    public Map< String, Object > getAgentOpportunityReport( @PathVariable final String email ) {
        userRoleValidator.validateByAgentEmail( email );
        LOGGER.info( "Fetching opportunity report of agents under managing broker :" + email );
        return agentReportsBusinessService.getAgentOpportunityReport( email );
    }

    /**
     * Gets the manager summary.
     *
     * @param email
     *            the email
     * @return the manager summary
     */
    @CrossOrigin
    @RequestMapping( value = { "/managers/{email}/agentsummary",
            "/users/{email}/reportees/summary" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @Secured( { VIEW_MANAGING_BROKER_REPORT } )
    @PerformanceLog
    public Map< String, Object > getManagerSummary( @PathVariable final String email ) {
        userRoleValidator.validateByUserEmail( email, MANAGING_BROKER );
        LOGGER.info( "Fetching opportunity report of agents under managing broker :" + email );
        return agentReportsBusinessService.getManagerSummary( email );
    }

    /**
     * Gets the agent summary.
     *
     * @param email
     *            the email
     * @return the agent summary
     */
    @CrossOrigin
    @RequestMapping( value = { "/agents/{email}/summary",
            "/users/{email}/summary" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @Secured( { VIEW_AGENT_REPORT } )
    @PerformanceLog
    public Map< String, Object > getAgentSummary( @PathVariable final String email ) {
        userRoleValidator.validateByAgentEmail( email );
        LOGGER.info( "Fetching opportunity report of agents under managing broker :" + email );
        return agentReportsBusinessService.getAgentSummary( email );
    }

    /**
     * Gets the agent score analytics.
     *
     * @param email
     *            the email
     * @return the agent score analytics
     */
    @CrossOrigin
    @RequestMapping( value = "/managers/{email}/agentsscoresummary", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @Secured( { VIEW_MANAGING_BROKER_REPORT } )
    @PerformanceLog
    public BaseResponse getAgentScoreStatistics( @PathVariable final String email ) {
        LOGGER.info( "Fetching score analysis report of agent with email id :" + email );
        return agentReportsBusinessService.getAgentScoreStatistics( email );
    }

    /**
     * Gets the client statistics report for broker.
     *
     * @param email
     *            the email
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @return the client statistics report for broker
     */
    @CrossOrigin
    @RequestMapping( value = "/users/{email}/reportees/client-statistics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @PerformanceLog
    @Secured( VIEW_MANAGING_BROKER_REPORT )
    public ClientStatisticsResponse getClientStatisticsReportForBroker( @PathVariable final String email,
            @RequestParam( required = false ) @Date( format = DEFAULT_CRM_DATE_PATTERN ) final String fromDate,
            @RequestParam( required = false ) @Date( format = DEFAULT_CRM_DATE_PATTERN ) final String toDate ) {
        LOGGER.info( "Fetching client statistics report for agent:" + email );
        return agentReportsBusinessService.getClientStatisticsReportForBroker( fromDate, toDate, email );
    }

    /**
     * Gets the client statistics report.
     *
     * @param email
     *            the email
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @return the client statistics report
     */
    @CrossOrigin
    @RequestMapping( value = "/users/{email}/client-statistics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @PerformanceLog
    @Secured( { VIEW_AGENT_REPORT, VIEW_CLIENT_STATISTICS } )
    public ClientStatisticsResponse getClientStatisticsReport( @PathVariable final String email,
            @RequestParam( required = false ) @Date( format = DEFAULT_CRM_DATE_PATTERN ) final String fromDate,
            @RequestParam( required = false ) @Date( format = DEFAULT_CRM_DATE_PATTERN ) final String toDate ) {
        LOGGER.info( "Fetching client statistics report for agent:" + email );
        userRoleValidator.validateByAgentEmail( email );
        return agentReportsBusinessService.getClientStatisticsReport( fromDate, toDate, email );
    }

    /**
     * Gets the face to face stage report.
     *
     * @param email
     *            the email
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @param stages
     *            the stages
     * @param type
     *            the type
     * @param isActive
     *            the is active
     * @return the face to face stage report
     */
    @CrossOrigin
    @RequestMapping( value = "/users/{email}/opportunity-count", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { VIEW_AGENT_REPORT, VIEW_CLIENT_STATISTICS } )
    public Map< String, Integer > getOpportunityCountByStage( @PathVariable final String email,
            @RequestParam( required = false ) @Date( format = DEFAULT_CRM_DATE_PATTERN ) final String fromDate,
            @RequestParam( required = false ) @Date( format = DEFAULT_CRM_DATE_PATTERN ) final String toDate,
            @RequestParam( required = true ) final String stages, @RequestParam( required = true ) final String type,
            @RequestParam( required = false ) final boolean isActive ) {
        LOGGER.info( "Fetching Opportunity Count By Stage report for agent:" + email );
        userRoleValidator.validateByAgentEmail( email );
        return agentReportsBusinessService.getOpportunityCountByStage( fromDate, toDate, email, stages, type,
                isActive );
    }

    /**
     * Gets the face to face count.
     *
     * @param email
     *            the email
     * @return the face to face count
     */
    @CrossOrigin
    @RequestMapping( value = "/users/{email}/facetoface-percent", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { VIEW_AGENT_REPORT, VIEW_CLIENT_STATISTICS } )
    public double getFaceToFaceCount( @PathVariable final String email ) {
        LOGGER.info( " Fetching face to face percentage for Agent " + email );
        userRoleValidator.validateByAgentEmail( email );
        return agentReportsBusinessService.getFaceToFaceCount( email );
    }

    /**
     * Gets the face to face count for broker.
     *
     * @param email
     *            the email
     * @return the face to face count for broker
     */
    @CrossOrigin
    @RequestMapping( value = "/users/{email}/reportees/facetoface-percent", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( VIEW_MANAGING_BROKER_REPORT )
    public double getFaceToFaceCountForBroker( @PathVariable final String email ) {
        LOGGER.info( "Fetching face to face report for agent:" + email );
        return agentReportsBusinessService.getFaceToFaceCountForBroker( email );
    }

    /**
     * Gets the stage opportunity count for agent.
     *
     * @param email
     *            the email
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @param stages
     *            the stages
     * @param type
     *            the type
     * @param isActive
     *            the is active
     * @return the stage opportunity count for agent
     */
    @CrossOrigin
    @RequestMapping( value = "/users/{email}/reportees/opportunity-count", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( VIEW_MANAGING_BROKER_REPORT )
    public Map< String, Integer > getOpportunityCountByStageForBroker( @PathVariable final String email,
            @RequestParam( required = false ) @Date( format = DEFAULT_CRM_DATE_PATTERN ) final String fromDate,
            @RequestParam( required = false ) @Date( format = DEFAULT_CRM_DATE_PATTERN ) final String toDate,
            @RequestParam( required = true ) final String stages, @RequestParam( required = true ) final String type,
            @RequestParam( required = false ) final boolean isActive ) {
        LOGGER.info( "Fetching face to face report for agent:" + email );
        return agentReportsBusinessService.getOpportunityCountByStageForBroker( fromDate, toDate, email, stages, type,
                isActive );
    }

    /**
     * Gets the closed lost stage report.
     *
     * @param email
     *            the email
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @param type
     *            the type
     * @return the closed lost stage report
     */
    @CrossOrigin
    @RequestMapping( value = "/users/{email}/reportees/opportunity-count/closed-lost", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( VIEW_MANAGING_BROKER_REPORT )
    public Map< String, Integer > getClosedLostOpportunityCountForBroker( @PathVariable final String email,
            @RequestParam( required = false ) @Date( format = DEFAULT_CRM_DATE_PATTERN ) final String fromDate,
            @RequestParam( required = false ) @Date( format = DEFAULT_CRM_DATE_PATTERN ) final String toDate,
            @RequestParam( required = true ) final String type ) {
        LOGGER.info( "Fetching closed lost report for agent:" + email );
        return agentReportsBusinessService.getClosedLostOpportunityCountForBroker( fromDate, toDate, email, type );
    }

    /**
     * Gets the closed lost stage report.
     *
     * @param email
     *            the email
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @param type
     *            the type
     * @return the closed lost stage report
     */
    @CrossOrigin
    @RequestMapping( value = "/users/{email}/opportunity-count/closed-lost", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    // @Secured( { VIEW_AGENT_REPORT, VIEW_CLIENT_STATISTICS } )
    public Map< String, Integer > getClosedLostOpportunityCount( @PathVariable final String email,
            @RequestParam( required = false ) @Date( format = DEFAULT_CRM_DATE_PATTERN ) final String fromDate,
            @RequestParam( required = false ) @Date( format = DEFAULT_CRM_DATE_PATTERN ) final String toDate,
            @RequestParam( required = true ) final String type ) {
        LOGGER.info( "Fetching closed lost report for agent:" + email );
        userRoleValidator.validateByAgentEmail( email );
        return agentReportsBusinessService.getClosedLostOpportunityCount( fromDate, toDate, email, type );
    }

    /**
     * Gets the client first response time for broker.
     *
     * @param email
     *            the email
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @return the client first response time for broker
     */
    @CrossOrigin
    @RequestMapping( value = "/users/{email}/reportees/first-response-time", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @PerformanceLog
    @Secured( VIEW_MANAGING_BROKER_REPORT )
    public ClientFirstResponseTime getClientFirstResponseTimeForBroker( @PathVariable final String email,
            @RequestParam( required = false ) @Date( format = DEFAULT_CRM_DATE_PATTERN ) final String fromDate,
            @RequestParam( required = false ) @Date( format = DEFAULT_CRM_DATE_PATTERN ) final String toDate ) {
        LOGGER.info( "Agent's first response time report for :" + email );
        return agentReportsBusinessService.getClientFirstResponseTimeForBroker( fromDate, toDate, email );
    }

    /**
     * Gets the client first response time.
     *
     * @param email
     *            the email
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @return the client first response time
     */
    @CrossOrigin
    @RequestMapping( value = "/users/{email}/first-response-time", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @PerformanceLog
    @Secured( { VIEW_AGENT_REPORT, VIEW_CLIENT_STATISTICS } )
    public ClientFirstResponseTime getClientFirstResponseTime( @PathVariable final String email,
            @RequestParam( required = false ) @Date( format = DEFAULT_CRM_DATE_PATTERN ) final String fromDate,
            @RequestParam( required = false ) @Date( format = DEFAULT_CRM_DATE_PATTERN ) final String toDate ) {
        LOGGER.info( "Agent's first response time report for :" + email );
        userRoleValidator.validateByAgentEmail( email );
        return agentReportsBusinessService.getClientFirstResponseTime( fromDate, toDate, email );
    }

    /**
     * Gets the agents by revenue.
     *
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @param rankFilter
     *            the rank filter
     * @return the agents by revenue
     */
    @CrossOrigin
    @RequestMapping( value = "/users/leaderboard", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( VIEW_AGENT_REVENUE )
    public Map< String, Object > getAgentsByRevenue(
            @RequestParam( required = false ) @Date( format = DEFAULT_CRM_DATE_PATTERN ) final String fromDate,
            @RequestParam( required = false ) @Date( format = DEFAULT_CRM_DATE_PATTERN ) final String toDate,
            @RequestParam( required = false ) final Integer rankFilter ) {
        return agentReportsBusinessService.getAgentsRevenue( fromDate, toDate, gravitasWebUtil.getAppUser(),
                rankFilter );
    }

    /**
     * Gets the agent revenue.
     *
     * @param email
     *            the email
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @return the agent revenue
     */
    @CrossOrigin
    @RequestMapping( value = "/users/{email}/sales-price", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( VIEW_AGENT_REVENUE )
    public Map< String, Object > getAgentRevenue( @PathVariable final String email,
            @RequestParam( required = false ) @Date( format = DEFAULT_CRM_DATE_PATTERN ) final String fromDate,
            @RequestParam( required = false ) @Date( format = DEFAULT_CRM_DATE_PATTERN ) final String toDate ) {
        userRoleValidator.validateByAgentEmail( email );
        return agentReportsBusinessService.getAgentRevenue( email, fromDate, toDate );
    }

    /**
     * Gets the agent f2f-stats.
     *
     * @param email
     *            the email
     * @return the agent f2f-stats
     */
    @CrossOrigin
    @RequestMapping( value = "users/{email}/f2f-stats", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { VIEW_MANAGING_BROKER_REPORT, VIEW_EXECUTIVE_REPORT } )
    public AgentStatisticsResponse getAgentStatistics( @PathVariable final String email,
            @RequestParam( required = true ) final boolean isWeek ) {
        LOGGER.info( "Fetching Face To Face per Agent statistics  for Agent with id:" + email );
        userRoleValidator.validateByAgentEmail( email );
        AgentStatisticsResponse baseResponse = new AgentStatisticsResponse();
        if (isWeek) {
            baseResponse = agentReportsBusinessService.getAgentGridStatistics( email );
        } else {
            baseResponse = agentReportsBusinessService.getAgentStatistics( email );
        }
        return baseResponse;
    }

    /**
     * Gets the agent f2f-count.
     *
     * @param email
     *            the email
     * @return the agent f2f-count
     */
    @CrossOrigin
    @RequestMapping( value = "/users/{email}/f2f-count", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { VIEW_MANAGING_BROKER_REPORT, VIEW_EXECUTIVE_REPORT } )
    public AgentCumulativeResponse getAgentF2FCount( @PathVariable final String email,
            @RequestParam( required = true ) final boolean isWeek ) {
        LOGGER.info( "Fetching Face To Face per Agent cumulative statistics  for Managing Brocker with id" + email );
        userRoleValidator.validateByAgentEmail( email );
        AgentCumulativeResponse baseResponse = new AgentCumulativeResponse();
        baseResponse = agentReportsBusinessService.getFaceToFaceStatistics( email, isWeek, true );
        return baseResponse;
    }

    /**
     * Gets the Managing Broker f2f-stats.
     *
     * @param email
     *            the email
     * @return the Managing Broker f2f-stats
     */
    @CrossOrigin
    @RequestMapping( value = "/users/{email}/reportees/f2f-stats", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { VIEW_MANAGING_BROKER_REPORT, VIEW_EXECUTIVE_REPORT } )
    public AgentStatisticsResponse getManagingBrokerStatistics( @PathVariable final String email,
            @RequestParam( required = true ) final boolean isWeek ) {
        LOGGER.info( "Fetching Face To Face per Agent statistics  for Managing Brocker with id:" + email );
        // userRoleValidator.validateByUserEmail( email );
        AgentStatisticsResponse baseResponse = new AgentStatisticsResponse();
        if (isWeek) {
            baseResponse = agentReportsBusinessService.getManagingBrokerGridStatistics( email );
        } else {
            baseResponse = agentReportsBusinessService.getManagingBrokerStatistics( email );
        }
        return baseResponse;
    }

    /**
     * Gets the Managing Broker f2f-count.
     *
     * @param email
     *            the email
     * @return the Managing Broker f2f-count
     */
    @CrossOrigin
    @RequestMapping( value = "/users/{email}/reportees/f2f-count", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { VIEW_MANAGING_BROKER_REPORT, VIEW_EXECUTIVE_REPORT } )
    public AgentCumulativeResponse getManaginBrokerF2FCount( @PathVariable final String email,
            @RequestParam( required = true ) final boolean isWeek ) {
        LOGGER.info( "Fetching Face To Face per Agent cumulative statistics  for Managing Brocker with id" + email );
        // userRoleValidator.validateByUserEmail( email );
        AgentCumulativeResponse baseResponse = new AgentCumulativeResponse();
        baseResponse = agentReportsBusinessService.getFaceToFaceStatistics( email, isWeek, false );
        return baseResponse;
    }

    /**
     * download the agent f2f-stats.
     *
     * @param email
     *            the email
     * @return download the agent f2f-stats
     */
    @CrossOrigin
    @RequestMapping( value = "/users/{email}/f2f-stats/download", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { VIEW_MANAGING_BROKER_REPORT, VIEW_EXECUTIVE_REPORT } )
    public void downloadAgentStatistics( @PathVariable final String email, final HttpServletResponse response )
            throws Exception {
        LOGGER.info( "Downloading Face To Face per Agent statistics  for Agent with id:" + email );
        userRoleValidator.validateByAgentEmail( email );
        try {
            final Workbook workbook = agentReportsBusinessService.downloadAgentStatistics( email, true );
            final String fileName = opportunities_considered
                    + DateUtil.toString( new DateTime(), DateUtil.DEFAULT_CRM_DATE_PATTERN ) + ".xlsx";
            response.setContentType( "application/vnd.ms-excel" );
            response.setHeader( "name", fileName );
            workbook.write( response.getOutputStream() );

        } catch ( final IOException e ) {
            throw new ApplicationException( e.getMessage(), e );
        }
    }

    /**
     * download the managing broker f2f-stats.
     *
     * @param email
     *            the email
     * @return download the managing broker f2f-stats
     */
    @CrossOrigin
    @RequestMapping( value = "/users/{email}/reportees/f2f-stats/download", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { VIEW_MANAGING_BROKER_REPORT, VIEW_EXECUTIVE_REPORT } )
    public void downloadManaginBrockerStatistics( @PathVariable final String email, final HttpServletResponse response )
            throws Exception {
        LOGGER.info( "Downloading Face To Face per Agent statistics  for Managing Brocker with id:" + email );
        // userRoleValidator.validateByUserEmail( email );
        try {
            final Workbook workbook = agentReportsBusinessService.downloadManagingBrokerStatistics( email );
            final String fileName = opportunities_considered
                    + DateUtil.toString( new DateTime(), DateUtil.DEFAULT_CRM_DATE_PATTERN ) + ".xlsx";
            response.setContentType( "application/vnd.ms-excel" );
            response.setHeader( "name", fileName );
            workbook.write( response.getOutputStream() );

        } catch ( final IOException e ) {
            throw new ApplicationException( e.getMessage(), e );
        }
    }

    /**
     * download the managing broker f2f-count.
     *
     * @param email
     *            the email
     * @return download the managing broker f2f-count
     */
    @CrossOrigin
    @RequestMapping( value = "/users/{email}/reportees/f2f-count/download", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { VIEW_MANAGING_BROKER_REPORT, VIEW_EXECUTIVE_REPORT } )
    public void downloadFaceToFaceCount( @PathVariable final String email, final HttpServletResponse response )
            throws Exception {
        LOGGER.info( "downloading Face To Face per Agent cumulative statistics  for Managing Brocker with id" + email );
        // userRoleValidator.validateByUserEmail( email );
        try {
            final Workbook workbook = agentReportsBusinessService.downloadFaceToFaceStatistics( email, false );
            final String fileName = face_to_face_meetings
                    + DateUtil.toString( new DateTime(), DateUtil.DEFAULT_CRM_DATE_PATTERN ) + ".xlsx";
            response.setContentType( "application/vnd.ms-excel" );
            response.setHeader( "name", fileName );
            workbook.write( response.getOutputStream() );
        } catch ( final IOException e ) {
            throw new ApplicationException( e.getMessage(), e );
        }
    }

    /**
     * Gets the Executive f2f-stats.
     *
     * @param email
     *            the email
     * @return All Managing Broker f2f-stats
     */
    @CrossOrigin
    @RequestMapping( value = "/users/{email}/all-reportees/f2f-stats", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( VIEW_EXECUTIVE_REPORT )
    public AgentStatisticsResponse getExecutiveStatistics( @PathVariable final String email ) {
        LOGGER.info( "Fetching Face To Face per Agent statistics for Executive with id:" + email );
        userRoleValidator.validateByUserEmail( email );
        AgentStatisticsResponse baseResponse = new AgentStatisticsResponse();
        baseResponse = agentReportsBusinessService.getExecutiveGridStatistics( email );
        return baseResponse;
    }

    /**
     * Gets the Executive f2f-count.
     *
     * @param email
     *            the email
     * @return All Opportunity considered per Agents for f2f conversion
     */
    @CrossOrigin
    @RequestMapping( value = "/users/{email}/all-reportees/f2f-count", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( VIEW_EXECUTIVE_REPORT )
    public AgentCumulativeResponse getExecutiveF2FCount( @PathVariable final String email,
            @RequestParam( required = true ) final boolean isWeek ) {
        LOGGER.info( "Fetching Face To Face per Agent cumulative statistics  for Executive with id" + email );
        userRoleValidator.validateByUserEmail( email );
        final AgentCumulativeResponse baseResponse = agentReportsBusinessService.getExecutiveFaceToFaceCount( email,
                isWeek );
        return baseResponse;
    }

    /**
     * download the Executive f2f count & statistics.
     *
     * @param email
     *            the email
     * @return download Executive f2f count & statistics.
     */
    @CrossOrigin
    @RequestMapping( value = "/users/{email}/all-reportees/f2f-stats/download", method = RequestMethod.GET )
    @ReadArgs
    @PerformanceLog
    @Secured( VIEW_EXECUTIVE_REPORT )
    public void downloadExecutiveF2FStatisticsReport( @PathVariable final String email,
            final HttpServletResponse response ) throws Exception {
        LOGGER.info( "Downloading Face To Face per Agent statistics  for Executive with id:" + email );
        userRoleValidator.validateByUserEmail( email );
        try {
            final Workbook workbook = agentReportsBusinessService.downloadExecutiveF2FStatisticsReport( email );

            final String fileName = opportunities_considered
                    + DateUtil.toString( new DateTime(), DateUtil.DEFAULT_CRM_DATE_PATTERN ) + ".xlsx";
            response.setContentType( "application/vnd.ms-excel" );
            response.setHeader( "Content-Disposition", "attachment; filename=" + fileName );
            workbook.write( response.getOutputStream() );

        } catch ( final IOException e ) {
            throw new ApplicationException( e.getMessage(), e );
        }
    }

    /**
     * view the calendar items of an agent from his/her agent app
     * 
     * @param agentId
     * @return
     */
    @CrossOrigin
    @RequestMapping( value = "/agents/{agentEmail}/agentApp/calendar/events", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { VIEW_AGENT_TASKS } )
    public BaseResponse getCalendarEventsFromAgentApp( @PathVariable final String agentEmail,
            @RequestParam( required = false ) final Long startTime,
            @RequestParam( required = false ) final Long endTime ) {
        userRoleValidator.validateByAgentEmail( agentEmail );
        return agentReportsBusinessService.getCalendarEventsFromAgentApp( agentEmail, startTime, endTime );
    }
    
    /**
     * @param crmId
     * @return
     */
    @CrossOrigin
    @RequestMapping( value = { "/cms" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @PerformanceLog
    public String cms( @RequestParam(value="path") final String resourcePath ) {
    	final CmsResponse response = gravitasCMSService.fetchFromCMS(resourcePath);
    	return response.getContent();
    }
    
    /**
     * @param crmId
     * @return
     */
    @CrossOrigin
    @RequestMapping( value = { "/agentAvailableTime" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @PerformanceLog
    public AgentAvailableTimeResponse agentAvailableTime() {
    	return agentReportsBusinessService.getAgentAvailableTime();
    }
    
    /**
     * 
     * Controller method to fetch the agent state Id and state name mapping
     * 
     * @return
     */
    @CrossOrigin
    @PerformanceLog
    @Secured( { SAVE_SEARCH } )
    @RequestMapping( value = "/agents/states", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    public AgentStateResponse getAgentStates() {
        return agentReportsBusinessService.getAllAgentStates();
    }
}
