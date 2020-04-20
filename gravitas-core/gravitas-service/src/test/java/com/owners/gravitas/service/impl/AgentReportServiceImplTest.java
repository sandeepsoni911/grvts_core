package com.owners.gravitas.service.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dao.AgentReportDao;

/**
 * The Class AgentReportServiceImplTest.
 *
 * @author raviz
 */
public class AgentReportServiceImplTest extends AbstractBaseMockitoTest {

    /** The agent report service impl. */
    @InjectMocks
    private AgentReportServiceImpl agentReportServiceImpl;

    /** The report repository. */
    @Mock
    private AgentReportDao agentReportDao;

    /**
     * Test get agents stagewise cta.
     */
    @Test
    public void testGetAgentsStagewiseCta() {
        final Date fromDate = new Date( 1L );
        final Date toDate = new Date( 2L );
        final String agentEmail = "testAgentEmail";
        final List< Object[] > list = new ArrayList<>();
        when( agentReportDao.getAgentsStagewiseCta( fromDate, toDate, agentEmail ) ).thenReturn( list );
        final List< Object[] > agentsStagewiseCta = agentReportServiceImpl.getAgentsStagewiseCta( fromDate, toDate,
                agentEmail );
        assertNotNull( agentsStagewiseCta );
        assertEquals( agentsStagewiseCta, list );
        verify( agentReportDao ).getAgentsStagewiseCta( fromDate, toDate, agentEmail );
    }

    /**
     * Test get opportunities stage details by date range.
     */
    @Test
    public void testGetOpportunitiesStageDetailsByDateRange() {
        final Date fromDate = new Date( 1L );
        final Date toDate = new Date( 2L );
        final String agentEmail = "testAgentEmail";
        final List< Object[] > list = new ArrayList<>();
        when( agentReportDao.getOpportunitiesStageDetailsByDateRange( agentEmail, fromDate, toDate ) )
                .thenReturn( list );
        final List< Object[] > stageDetailsByDateRange = agentReportServiceImpl
                .getOpportunitiesStageDetailsByDateRange( agentEmail, fromDate, toDate );
        assertNotNull( stageDetailsByDateRange );
        assertEquals( stageDetailsByDateRange, list );
        verify( agentReportDao ).getOpportunitiesStageDetailsByDateRange( agentEmail, fromDate, toDate );
    }

    /**
     * Test get opportunities first response time.
     */
    @Test
    public void testGetOpportunitiesFirstResponseTime() {
        final Date fromDate = new Date( 1L );
        final Date toDate = new Date( 2L );
        final String agentEmail = "testAgentEmail";
        final List< Object[] > list = new ArrayList<>();
        when( agentReportDao.getOpportunitiesFirstResponseTime( agentEmail, fromDate, toDate ) ).thenReturn( list );
        final List< Object[] > firstResponseTime = agentReportServiceImpl.getOpportunitiesFirstResponseTime( agentEmail,
                fromDate, toDate );
        assertNotNull( firstResponseTime );
        assertEquals( firstResponseTime, list );
        verify( agentReportDao ).getOpportunitiesFirstResponseTime( agentEmail, fromDate, toDate );
    }

    /**
     * Testget closed lost opportunity count.
     */
    @Test
    public void testgetClosedLostOpportunityCount() {
        final Date fromDate = new Date( 1L );
        final Date toDate = new Date( 2L );
        final String agentEmail = "testAgentEmail";
        final List< Object[] > list = new ArrayList<>();
        when( agentReportDao.getClosedLostOpportunityCount( fromDate, toDate, agentEmail, new ArrayList< String >() ) )
                .thenReturn( 1 );
        final Object count = agentReportServiceImpl.getClosedLostOpportunityCount( fromDate, toDate, agentEmail,
                new ArrayList< String >() );
        assertNotNull( count );
        assertEquals( count, 1 );
    }

    /**
     * Testget assigned opportunity count.
     */
    @Test
    public void testgetAssignedOpportunityCount() {
        final Date fromDate = new Date( 1L );
        final Date toDate = new Date( 2L );
        final String agentEmail = "testAgentEmail";
        final List< Object[] > list = new ArrayList<>();
        when( agentReportDao.getAssignedOpportunityCount( fromDate, toDate, agentEmail, new ArrayList< String >() ) )
                .thenReturn( 1 );
        final Object count = agentReportServiceImpl.getAssignedOpportunityCount( fromDate, toDate, agentEmail,
                new ArrayList< String >() );
        assertNotNull( count );
        assertEquals( count, 1 );
    }

    /**
     * Testget opportunity count by stage.
     */
    @Test
    public void testgetOpportunityCountByStage() {
        final Date fromDate = new Date( 1L );
        final Date toDate = new Date( 2L );
        final String agentEmail = "testAgentEmail";
        final List< Object[] > list = new ArrayList<>();
        when( agentReportDao.getOpportunityCountByStage( fromDate, toDate, agentEmail, new ArrayList< String >(),
                new ArrayList< String >() ) ).thenReturn( 1 );
        final Object count = agentReportServiceImpl.getOpportunityCountByStage( fromDate, toDate, agentEmail,
                new ArrayList< String >(), new ArrayList< String >() );
        assertNotNull( count );
        assertEquals( count, 1 );
    }

    /**
     * Test get face to face count.
     */
    @Test
    public void testGetFaceToFaceCount() {
        final String email = "test@test.com";
        final Object expectedCount = 12;
        when( agentReportDao.getFaceToFaceCount( email ) ).thenReturn( expectedCount );
        final Object actualCount = agentReportServiceImpl.getFaceToFaceCount( email );
        assertEquals( actualCount, expectedCount );
        verify( agentReportDao ).getFaceToFaceCount( email );
    }

    /**
     * Test get Agent Statistics.
     */
    @Test
    public void testGetAgentStatistics() {
        final String agentEmail = "agent@owners.com";
        final List< Object[] > list = new ArrayList<>();
        when( agentReportDao.getAgentStatistics( agentEmail ) ).thenReturn( list );
        final List< Object[] > response = agentReportServiceImpl.getAgentStatistics( agentEmail );
        assertNotNull( response );
        assertEquals( response, list );
        verify( agentReportDao ).getAgentStatistics( agentEmail );
    }

    /**
     * Test get Managing Broker Statistics.
     */
    @Test
    public void testGetManagingBrokerStatistics() {
        final String managingBroker = "managingBroker@owners.com";
        final List< Object[] > list = new ArrayList<>();
        when( agentReportDao.getManagingBrokerStatistics( managingBroker ) ).thenReturn( list );
        final List< Object[] > response = agentReportServiceImpl.getManagingBrokerStatistics( managingBroker );
        assertNotNull( response );
        assertEquals( response, list );
        verify( agentReportDao ).getManagingBrokerStatistics( managingBroker );
    }

    /**
     * Test get Face To Face Statistics For Agent.
     */
    @Test
    public void testGetFaceToFaceStatisticsForAgent() {
        final String agent = "agent@owners.com";
        final List< Object[] > list = new ArrayList<>();
        when( agentReportDao.getFaceToFaceStatistics( agent, true ) ).thenReturn( list );
        final List< Object[] > response = agentReportServiceImpl.getFaceToFaceStatistics( agent, true );
        assertNotNull( response );
        assertEquals( response, list );
        verify( agentReportDao ).getFaceToFaceStatistics( agent, true );
    }

    /**
     * Test Face To Face Statistics For Managing Broker.
     */
    @Test
    public void testGetFaceToFaceStatisticsForManagingBroker() {
        final String managingBroker = "managingBroker@owners.com";
        final List< Object[] > list = new ArrayList<>();
        when( agentReportDao.getFaceToFaceStatistics( managingBroker, false ) ).thenReturn( list );
        final List< Object[] > response = agentReportServiceImpl.getFaceToFaceStatistics( managingBroker, false );
        assertNotNull( response );
        assertEquals( response, list );
        verify( agentReportDao ).getFaceToFaceStatistics( managingBroker, false );
    }

    /**
     * Test get Face To Face Count Download For Agent.
     */
    @Test
    public void testGetFaceToFaceCountDownloadForAgent() {
        final String agent = "agent@owners.com";
        final List< Object[] > list = new ArrayList<>();
        when( agentReportDao.downloadFaceToFaceCount( agent ) ).thenReturn( list );
        final List< Object[] > response = agentReportServiceImpl.getFaceToFaceCountDownload( agent, true );
        assertNotNull( response );
        assertEquals( response, list );
        verify( agentReportDao ).downloadFaceToFaceCount( agent );
    }

    /**
     * Test Face To Face Count Download For Managing Broker.
     */
    @Test
    public void testGetFaceToFaceCountDownloadForManagingBroker() {
        final String managingBroker = "managingBroker@owners.com";
        final List< Object[] > list = new ArrayList<>();
        when( agentReportDao.downloadFaceToFaceCount( managingBroker ) ).thenReturn( list );
        final List< Object[] > response = agentReportServiceImpl.getFaceToFaceCountDownload( managingBroker, false );
        assertNotNull( response );
        assertEquals( response, list );
        verify( agentReportDao ).downloadFaceToFaceCount( managingBroker );
    }

    /**
     * Test Get Agent Grid Statistics .
     */
    @Test
    public void testGetAgentGridStatistics() {
        final String agent = "agent@owners.com";
        final List< Object[] > list = new ArrayList<>();
        when( agentReportDao.getAgentGridStatistics( agent ) ).thenReturn( list );
        final List< Object[] > response = agentReportServiceImpl.getAgentGridStatistics( agent );
        assertNotNull( response );
        assertEquals( response, list );
        verify( agentReportDao ).getAgentGridStatistics( agent );
    }

    /**
     * Get Managing Broker Grid Statistics.
     */
    @Test
    public void testGetManagingBrokerGridStatistics() {
        final String mangingBroker = "mangingBroker@owners.com";
        final List< Object[] > list = new ArrayList<>();
        when( agentReportDao.getManagingBrokerGridStatistics( mangingBroker ) ).thenReturn( list );
        final List< Object[] > response = agentReportServiceImpl.getManagingBrokerGridStatistics( mangingBroker );
        assertNotNull( response );
        assertEquals( response, list );
        verify( agentReportDao ).getManagingBrokerGridStatistics( mangingBroker );
    }

    /**
     * Test Get Face To Face Grid Statistics For Agent .
     */
    @Test
    public void testGetFaceToFaceGridStatisticsForAgent() {
        final String agent = "agent@owners.com";
        final List< Object[] > list = new ArrayList<>();
        when( agentReportDao.getFaceToFaceGridStatistics( agent, true ) ).thenReturn( list );
        final List< Object[] > response = agentReportServiceImpl.getFaceToFaceGridStatistics( agent, true );
        assertNotNull( response );
        assertEquals( response, list );
        verify( agentReportDao ).getFaceToFaceGridStatistics( agent, true );
    }

    /**
     * Test Get Face To Face Grid Statistics For Managing Broker.
     */
    @Test
    public void testGetFaceToFaceGridStatisticsForManagingBroker() {
        final String mangingBroker = "mangingBroker@owners.com";
        final List< Object[] > list = new ArrayList<>();
        when( agentReportDao.getFaceToFaceGridStatistics( mangingBroker, false ) ).thenReturn( list );
        final List< Object[] > response = agentReportServiceImpl.getFaceToFaceGridStatistics( mangingBroker, false );
        assertNotNull( response );
        assertEquals( response, list );
        verify( agentReportDao ).getFaceToFaceGridStatistics( mangingBroker, false );
    }
}
