package com.owners.gravitas.service;

import java.sql.Date;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Interface AgentReportService.
 *
 * @author raviz
 */
public interface AgentReportService {

    /**
     * Gets the opportunities stage details by date range.
     *
     * @param agentEmail
     *            the agent email
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @return the opportunities stage details by date range
     */
    List< Object[] > getOpportunitiesStageDetailsByDateRange( String agentEmail, Date fromDate, Date toDate );

    /**
     * Gets the agents stagewise cta.
     *
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @param agentEmail
     *            the agent email
     * @return the agents stagewise cta
     */
    public List< Object[] > getAgentsStagewiseCta( Date fromDate, Date toDate, String agentEmail );

    /**
     * Gets the opportunity contains stage count.
     *
     * @param startDate
     *            the start date
     * @param endDate
     *            the end date
     * @param agentEmail
     *            the agent email
     * @param stages
     *            the stages
     * @param type
     *            the type
     * @return the opportunity contains stage count
     */
    Object getOpportunityCountByStage( Date startDate, Date endDate, String agentEmail, List< String > stages,
            List< String > type );

    /**
     * Gets the assigned opportunity count.
     *
     * @param startDate
     *            the start date
     * @param endDate
     *            the end date
     * @param agentEmail
     *            the agent email
     * @param type
     *            the type
     * @return the assigned opportunity count
     */
    Object getAssignedOpportunityCount( Date startDate, Date endDate, String agentEmail, List< String > type );

    /**
     * Gets the lost opportunity count.
     *
     * @param startDate
     *            the start date
     * @param endDate
     *            the end date
     * @param agentEmail
     *            the agent email
     * @param type
     *            the type
     * @return the lost opportunity count
     */
    Object getClosedLostOpportunityCount( Date startDate, Date endDate, String agentEmail, List< String > type );

    /**
     * Gets the opportunities first response time.
     *
     * @param agentEmail
     *            the agent email
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @return the opportunities first response time
     */
    List< Object[] > getOpportunitiesFirstResponseTime( String agentEmail, Date fromDate, Date toDate );

    /**
     * Gets the face to face count.
     *
     * @param email
     *            the email
     * @return the face to face count
     */
    Object getFaceToFaceCount( String email );

    /**
     * Gets the Face to Face statistics for the Agent
     * 
     * @param email
     *            the email
     * @return the Face to Face statistics for the Agent
     */
    List< Object[] > getAgentStatistics( String email );

    /**
     * Gets the Face to face statistics for the Managing Broker
     * 
     * @param email
     *            the email
     * @return the face to face statistics for the Managing Broker
     */
    List< Object[] > getManagingBrokerStatistics( String email );

    /**
     * Gets the Face to face count for the Managing Broker
     * 
     * @param email
     *            the email
     * @return the face to face count for the Managing Broker
     */
    List< Object[] > getFaceToFaceStatistics( String email, boolean isForAgent );

    /**
     * download the Face to face count for the Managing Broker
     * 
     * @param email
     *            the email
     * @return download face to face count for the Managing Broker
     */
    List< Object[] > getFaceToFaceCountDownload( final String email, boolean isForAgent );

    /**
     * Gets the Face to Face Grid statistics for the Agent
     * 
     * @param email
     *            the email
     * @return the Face to Face Grid statistics for the Agent
     */
    List< Object[] > getAgentGridStatistics( String email );

    /**
     * Gets the Face to Face Grid statistics for the Agent
     * 
     * @param email
     *            the email
     * @return the Face to Face Grid statistics for the Agent
     */
    List< Object[] > getManagingBrokerGridStatistics( String email );

    /**
     * Gets the Face to Face Grid count for the Managing Broker
     * 
     * @param email
     *            the email
     * @return the Face to Face Grid counta for the Managing Broker
     */
    List< Object[] > getFaceToFaceGridStatistics( String email, boolean isForAgent );

    /**
     * Gets the Agent & their Managing Broker email list
     * 
     * @param agentsEmailList
     *            the email of agents
     * @return the Agent & their Managing Broker email list
     */
    List< Object[] > getAgentsAndTheirManagingBrokerEmailID( List< String > agentsEmailList );

    /**
     * Gets the Face to Face Grid statistics for the Agent
     * 
     * @param email
     *            the email
     * @return the Face to Face Grid statistics for the Agent
     */
    List< Object[] > getExecutiveGridStatistics( String email );

    /**
     * Gets the Face to face statistics for the Managing Broker
     * 
     * @param email
     *            the email
     * @return the face to face statistics for the Managing Broker
     */
    List< Object[] > getExecutiveStatistics( String email );

    /**
     * Gets the Face to Face Grid count for the Managing Broker
     * 
     * @param email
     *            the email
     * @return the Face to Face Grid counta for the Managing Broker
     */
    List< Object[] > getExecutiveFaceToFaceGrid( String email );

    /**
     * Gets the Face to face count for the Executive
     * 
     * @param email
     *            the email
     * @return the face to face count for the Executive
     */
    List< Object[] > getExecutiveFaceToFace( String email );

    /**
     * Gets the Face to face count download for the Executive
     * 
     * @param email
     *            the email
     * @return the face to face count for the Executive
     */
    List< Object[] > getExecutiveFaceToFaceDownload( String email );

}
