package com.owners.gravitas.service.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.dao.AgentReportDao;
import com.owners.gravitas.service.AgentReportService;

/**
 * The Class AgentReportServiceImpl.
 *
 * @author raviz
 */
@Service( "agentReportServiceImpl" )
public class AgentReportServiceImpl implements AgentReportService {

    /** The report repository. */
    @Autowired
    private AgentReportDao agentReportDao;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentReportService#getAgentsStagewiseCta(java
     * .sql.Date, java.sql.Date, java.lang.String)
     */
    @Override
    public List< Object[] > getAgentsStagewiseCta( final Date fromDate, final Date toDate, final String agentEmail ) {
        return agentReportDao.getAgentsStagewiseCta( fromDate, toDate, agentEmail );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.ReportService#getLostOpportunityCount(java.
     * sql.Date, java.sql.Date, java.lang.String)
     */
    @Override
    public Object getClosedLostOpportunityCount( final Date startDate, final Date endDate, final String agentEmail,
            final List< String > type ) {
        return agentReportDao.getClosedLostOpportunityCount( startDate, endDate, agentEmail, type );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.ReportService#getAssignedOpportunityCount(
     * java.sql.Date, java.sql.Date, java.lang.String)
     */
    @Override
    public Object getAssignedOpportunityCount( final Date startDate, final Date endDate, final String agentEmail,
            final List< String > type ) {
        return agentReportDao.getAssignedOpportunityCount( startDate, endDate, agentEmail, type );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.ReportService#
     * getOpportunityContainsStageCount(java.sql.Date, java.sql.Date,
     * java.lang.String, java.util.List)
     */
    @Override
    public Object getOpportunityCountByStage( final Date startDate, final Date endDate, final String agentEmail,
            final List< String > stages, final List< String > type ) {
        return agentReportDao.getOpportunityCountByStage( startDate, endDate, agentEmail, stages, type );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentReportService#getFaceToFaceCount(java.
     * lang.String)
     */
    @Override
    public Object getFaceToFaceCount( final String email ) {
        return agentReportDao.getFaceToFaceCount( email );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.AgentReportService#
     * getOpportunitiesStageDetailsByDateRange(java.lang.String, java.sql.Date,
     * java.sql.Date)
     */
    @Override
    public List< Object[] > getOpportunitiesStageDetailsByDateRange( final String agentEmail, final Date fromDate,
            final Date toDate ) {
        return agentReportDao.getOpportunitiesStageDetailsByDateRange( agentEmail, fromDate, toDate );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.AgentReportService#
     * getOpportunitiesFirstResponseTime(java.lang.String, java.sql.Date,
     * java.sql.Date)
     */
    @Override
    public List< Object[] > getOpportunitiesFirstResponseTime( final String agentEmail, final Date fromDate,
            final Date toDate ) {
        return agentReportDao.getOpportunitiesFirstResponseTime( agentEmail, fromDate, toDate );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentReportService#getAgentStatistics(java.
     * lang.String)
     */
    @Override
    public List< Object[] > getAgentStatistics( final String email ) {
        return agentReportDao.getAgentStatistics( email );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.AgentReportService#
     * getManagingBrokerStatistics(java.lang.String)
     */
    @Override
    public List< Object[] > getManagingBrokerStatistics( final String email ) {
        return agentReportDao.getManagingBrokerStatistics( email );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentReportService#getFaceToFaceStatistics(
     * java.lang.String)
     */
    @Override
    public List< Object[] > getFaceToFaceStatistics( final String email, boolean isForAgent ) {
        return agentReportDao.getFaceToFaceStatistics( email, isForAgent );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentReportService#getFaceToFaceCountDownload
     * (java.lang.String, boolean)
     */
    @Override
    public List< Object[] > getFaceToFaceCountDownload( final String email, boolean isForAgent ) {
        return agentReportDao.downloadFaceToFaceCount( email );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentReportService#getAgentGridStatistics(
     * java.lang.String)
     */
    @Override
    public List< Object[] > getAgentGridStatistics( final String email ) {
        return agentReportDao.getAgentGridStatistics( email );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.AgentReportService#
     * getManagingBrokerGridStatistics(java.lang.String)
     */
    @Override
    public List< Object[] > getManagingBrokerGridStatistics( final String email ) {
        return agentReportDao.getManagingBrokerGridStatistics( email );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.AgentReportService#
     * getFaceToFaceGridStatistics(java.lang.String)
     */
    @Override
    public List< Object[] > getFaceToFaceGridStatistics( String email, boolean isForAgent ) {
        return agentReportDao.getFaceToFaceGridStatistics( email, isForAgent );
    }

    @Override
    public List< Object[] > getAgentsAndTheirManagingBrokerEmailID( List< String > agentsEmailList ) {
        return agentReportDao.getAgentsAndTheirManagingBrokerEmailID( agentsEmailList );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.AgentReportService#
     * getManagingBrokerStatistics(java.lang.String)
     */
    @Override
    public List< Object[] > getExecutiveStatistics( final String email ) {
        return agentReportDao.getExecutiveStatistics( email );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.AgentReportService#
     * getManagingBrokerGridStatistics(java.lang.String)
     */
    @Override
    public List< Object[] > getExecutiveGridStatistics( final String email ) {
        return agentReportDao.getExecutiveGridStatistics( email );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.AgentReportService#
     * getExecutiveFaceToFaceGrid(java.lang.String)
     */
    @Override
    public List< Object[] > getExecutiveFaceToFaceGrid( String email ) {
        return agentReportDao.getExecutiveFaceToFaceGrid( email );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentReportService#getFaceToFaceStatistics(
     * java.lang.String)
     */
    @Override
    public List< Object[] > getExecutiveFaceToFace( final String email ) {
        return agentReportDao.getExecutiveFaceToFace( email );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentReportService#
     * getExecutiveFaceToFaceDownload(
     * java.lang.String)
     */
    @Override
    public List< Object[] > getExecutiveFaceToFaceDownload( final String email ) {
        return agentReportDao.getExecutiveFaceToFaceDownload( email );
    }

}
