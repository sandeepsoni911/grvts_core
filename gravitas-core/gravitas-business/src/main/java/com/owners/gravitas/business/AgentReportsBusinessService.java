package com.owners.gravitas.business;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

import com.owners.gravitas.dto.Agent;
import com.owners.gravitas.dto.ApiUser;
import com.owners.gravitas.dto.response.AgentAvailableTimeResponse;
import com.owners.gravitas.dto.response.AgentCumulativeResponse;
import com.owners.gravitas.dto.response.AgentStateResponse;
import com.owners.gravitas.dto.response.AgentStatisticsResponse;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.CalendarEventListResponse;
import com.owners.gravitas.dto.response.ClientFirstResponseTime;
import com.owners.gravitas.dto.response.ClientStatisticsResponse;

// TODO: Auto-generated Javadoc
/**
 * The Interface AgentReportsBusinessService.
 *
 * @author ankusht
 */
public interface AgentReportsBusinessService {

    /**
     * Evaluate agent performance metrics.
     */
    void evaluateAgentPerformanceMetrics();

    /**
     * Gets the performance log.
     *
     * @param agentId
     *            the agent id
     * @param timeFrame
     *            the time frame
     * @return the performance log
     */
    Map< String, Object > getPerformanceLog( final String agentId, final Integer timeFrame );

    /**
     * Gets the agent opportunity report.
     *
     * @param email
     *            the email
     * @return the agent opportunity report
     */
    Map< String, Object > getAgentOpportunityReport( final String email );

    /**
     * Gets the manager opportunity report.
     *
     * @param mbEmail
     *            the mb email
     * @return the manager opportunity report
     */
    Map< String, Object > getManagerOpportunityReport( final String mbEmail );

    /**
     * Gets the agent summary.
     *
     * @param email
     *            the email
     * @return the agent summary
     */
    Map< String, Object > getAgentSummary( final String email );

    /**
     * Gets the manager summary.
     *
     * @param mbEmail
     *            the mb email
     * @return the manager summary
     */
    Map< String, Object > getManagerSummary( final String mbEmail );

    /**
     * Gets the agents score summary.
     *
     * @param mbEmail
     *            the mb email
     * @return the agents score summary
     */
    List< Agent > getAgentsScorePerformance( String mbEmail );

    /**
     * Gets the agent score statistics.
     *
     * @param email
     *            the email
     * @return the agent score statistics
     */
    BaseResponse getAgentScoreStatistics( String email );

    /**
     * Perform score analytics.
     */
    void performScoreAnalytics();

    /**
     * Gets the client statistics report.
     *
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @param agentEmail
     *            the agent email
     * @return the client statistics report
     */
    ClientStatisticsResponse getClientStatisticsReport( String fromDate, String toDate, String agentEmail );

    /**
     * Gets the lost opportunity percent.
     *
     * @param fromDate
     *            the start date
     * @param toDate
     *            the end date
     * @param agentEmail
     *            the agent email
     * @param type
     *            the type
     * @return the lost opportunity percent
     */
    Map< String, Integer > getClosedLostOpportunityCount( String fromDate, String toDate, String agentEmail,
            String type );

    /**
     * Gets the stage opportunity count for broker.
     *
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @param email
     *            the email
     * @param stages
     *            the stages
     * @param type
     *            the type
     * @param isActive
     *            the is active
     * @return the stage opportunity count for broker
     */
    Map< String, Integer > getOpportunityCountByStageForBroker( String fromDate, String toDate, String email,
            String stages, String type, boolean isActive );

    /**
     * Gets the stage opportunity count.
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
     * @return the stage opportunity count
     */
    Map< String, Integer > getOpportunityCountByStage( String fromDate, String toDate, String agentEmail, String stages,
            String type, boolean isActive );

    /**
     * Gets the lost opportunity percent for broker.
     *
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @param email
     *            the email
     * @param type
     *            the type
     * @return the lost opportunity percent for broker
     */
    Map< String, Integer > getClosedLostOpportunityCountForBroker( String fromDate, String toDate, String email,
            String type );

    /**
     * Gets the client statistics report for broker.
     *
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @param email
     *            the email
     * @return the client statistics report for broker
     */
    ClientStatisticsResponse getClientStatisticsReportForBroker( String fromDate, String toDate, String email );

    /**
     * Gets the client first response time.
     *
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @param agentEmail
     *            the agent email
     * @return the agents first response time
     */
    ClientFirstResponseTime getClientFirstResponseTime( String fromDate, String toDate, String agentEmail );

    /**
     * Gets the client first response time for broker.
     *
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @param agentEmail
     *            the agent email
     * @return the client first response time for broker
     */
    ClientFirstResponseTime getClientFirstResponseTimeForBroker( String fromDate, String toDate, String agentEmail );

    /**
     * Gets the agent revenue.
     *
     * @param agentEmail
     *            the agent email
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @return the agent revenue
     */
    Map< String, Object > getAgentRevenue( String agentEmail, String fromDate, String toDate );

    /**
     * Gets the agents revenue.
     *
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @param apiUser
     *            the api user
     * @param rankFilter
     *            the rank filter
     * @return the agents revenue
     */
    Map< String, Object > getAgentsRevenue( String fromDate, String toDate, ApiUser apiUser, Integer rankFilter );

    /**
     * Gets the face to face count.
     *
     * @param email
     *            the email
     * @return the face to face count
     */
    double getFaceToFaceCount( String email );

    /**
     * Gets the face to face count for broker.
     *
     * @param email
     *            the email
     * @return the face to face count for broker
     */
    double getFaceToFaceCountForBroker( String email );

    /**
     * Gets the Face to Face Grid statistics for the Agent
     * 
     * @param email
     *            the email
     * @return the Face to Face Grid statistics for the Agent
     */
    AgentStatisticsResponse getAgentGridStatistics( String email );

    /**
     * Gets the Face to Face statistics for the Agent
     * 
     * @param email
     *            the email
     * @return the Face to Face statistics for the Agent
     */
    AgentStatisticsResponse getAgentStatistics( String email );

    /**
     * Gets the Face to face count for the Managing Broker
     * 
     * @param email
     *            the email
     * @return the face to face count for the Managing Broker
     */
    AgentCumulativeResponse getFaceToFaceStatistics( String email, boolean isWeek, boolean isForAgent );

    /**
     * Gets the Face to Face Grid statistics for the Agent
     * 
     * @param email
     *            the email
     * @return the Face to Face Grid statistics for the Agent
     */
    AgentStatisticsResponse getManagingBrokerGridStatistics( String email );

    /**
     * Gets the Face to face statistics for the Managing Broker
     * 
     * @param email
     *            the email
     * @return the face to face statistics for the Managing Broker
     */
    AgentStatisticsResponse getManagingBrokerStatistics( String email );

    /**
     * Downloads the Face to Face statistics for the Agent
     * 
     * @param email
     *            the email
     * @return Excel of Face to Face Grid statistics for the Agent
     */
    Workbook downloadAgentStatistics( String email, boolean isForAgent );

    /**
     * Downloads the Face to Face statistics for the Managing Broker
     * 
     * @param email
     *            the email
     * @return Excel of Face to Face statistics for the Managing Broker
     */
    Workbook downloadManagingBrokerStatistics( String email );

    /**
     * Downloads the Face to Face cumulative for the Managing Broker
     * 
     * @param email
     *            the email
     * @return Excel of Face to Face cumulative for the Managing Broker
     */
    Workbook downloadFaceToFaceStatistics( String email, boolean isForAgent );

    /**
     * Gets the Face to Face Grid statistics for the Agent
     * 
     * @param email
     *            the email
     * @return the Face to Face Grid statistics for the Agent
     */
    AgentStatisticsResponse getExecutiveGridStatistics( String email );

    /**
     * Gets the Face to face statistics for the Executive
     * 
     * @param email
     *            the email
     * @return the face to face statistics for All Managing Broker
     */
    AgentStatisticsResponse getExecutiveStatistics( String email );

    /**
     * Gets the Face to face count for the Executive
     * 
     * @param email
     *            the email
     * @return the face to face count for the Executive (i.e. for all agents)
     */
    AgentCumulativeResponse getExecutiveFaceToFaceCount( String email, boolean isWeek );

    /**
     * Downloads the Face to Face statistics for the Managing Broker
     * 
     * @param email
     *            the email
     * @return Excel of Face to Face statistics for the Managing Broker
     */
    Workbook downloadExecutiveF2FStatisticsReport( String email );
    
    /**
     * Get calendar items for agent app
     * 
     * @param agentEmail
     * @param startDate
     * @param endDate
     * @return
     */
    CalendarEventListResponse getCalendarEventsFromAgentApp(String agentEmail, Long startTime, Long endTime);
    
    
    /**
     * 
     * Gets all the configured state names with their state ids.
     * 
     * @return AgentStateResponse
     */
    AgentStateResponse getAllAgentStates();
    
    /**
     * 
     * Gets agent available time info
     * 
     * @return AgentStateResponse
     */
    AgentAvailableTimeResponse getAgentAvailableTime();
}
