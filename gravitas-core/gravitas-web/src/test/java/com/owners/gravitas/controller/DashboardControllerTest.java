package com.owners.gravitas.controller;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.business.AgentReportsBusinessService;
import com.owners.gravitas.config.BaseControllerTest;
import com.owners.gravitas.dto.ApiUser;
import com.owners.gravitas.dto.crm.response.AgentStatisticsResponse;
import com.owners.gravitas.dto.response.AgentCumulativeResponse;
import com.owners.gravitas.dto.response.AgentStateResponse;
import com.owners.gravitas.dto.response.CalendarEventListResponse;
import com.owners.gravitas.dto.response.ClientFirstResponseTime;
import com.owners.gravitas.dto.response.ClientStatisticsResponse;
import com.owners.gravitas.validator.UserRoleValidator;

/**
 * Test class for {@link DashboardControllerTest}.
 *
 * @author madhav
 */
public class DashboardControllerTest extends BaseControllerTest {

    /** The controller. */
    @InjectMocks
    private DashboardController controller;

    /** The agent reports business service. */
    @Mock
    private AgentReportsBusinessService agentReportsBusinessService;

    /** The role validator. */
    @Mock
    private UserRoleValidator roleValidator;

    /**
     * Sets the up base.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeClass
    public void setUpBase() throws Exception {
        super.setUpMockMVC( controller );
    }

    /**
     * Test get manager opportunity report.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetManagerOpportunityReport() throws Exception {
        final Set< String > roles = new HashSet< String >();
        final ApiUser user = new ApiUser();
        final String email = "test@test.com";
        final String role = "MANAGING_BROKER";
        user.setRoles( roles );
        user.setEmail( email );

        when( roleValidator.validateByUserEmail( email, role ) ).thenReturn( user );
        when( agentReportsBusinessService.getManagerOpportunityReport( email ) )
                .thenReturn( new HashMap< String, Object >() );
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( "/webapi/managers/test@test.com/opportunities" )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );

        verify( roleValidator ).validateByUserEmail( email, role );
        verify( agentReportsBusinessService ).getManagerOpportunityReport( email );
    }

    /**
     * Test get agent opportunity report.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetAgentOpportunityReport() throws Exception {
        final Set< String > roles = new HashSet< String >();
        final ApiUser user = new ApiUser();
        final String email = "test@test.com";
        user.setRoles( roles );
        user.setEmail( email );

        when( roleValidator.validateByAgentEmail( email ) ).thenReturn( user );
        when( agentReportsBusinessService.getAgentOpportunityReport( email ) )
                .thenReturn( new HashMap< String, Object >() );
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( "/webapi/agents/test@test.com/opportunities" )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );

        verify( roleValidator ).validateByAgentEmail( email );
        verify( agentReportsBusinessService ).getAgentOpportunityReport( email );
    }

    /**
     * Test get manager summary.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetManagerSummary() throws Exception {
        final Set< String > roles = new HashSet< String >();
        final ApiUser user = new ApiUser();
        final String email = "test@test.com";
        final String role = "MANAGING_BROKER";
        user.setRoles( roles );
        user.setEmail( email );

        when( roleValidator.validateByUserEmail( email, role ) ).thenReturn( user );
        when( agentReportsBusinessService.getManagerSummary( email ) ).thenReturn( new HashMap< String, Object >() );
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( "/webapi/managers/test@test.com/agentsummary" )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );

        verify( roleValidator ).validateByUserEmail( email, role );
        verify( agentReportsBusinessService ).getManagerSummary( email );
    }

    /**
     * Test get agent summary.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetAgentSummary() throws Exception {
        final Set< String > roles = new HashSet< String >();
        final ApiUser user = new ApiUser();
        final String email = "test@test.com";
        user.setRoles( roles );
        user.setEmail( email );

        when( roleValidator.validateByAgentEmail( email ) ).thenReturn( user );
        when( agentReportsBusinessService.getAgentSummary( email ) ).thenReturn( new HashMap< String, Object >() );
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( "/webapi/agents/test@test.com/summary" )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );

        verify( roleValidator ).validateByAgentEmail( email );
        verify( agentReportsBusinessService ).getAgentSummary( email );
    }

    /**
     * Test get agent score summary.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetAgentScoreSummary() throws Exception {
        final String email = "test@test.com";
        when( agentReportsBusinessService.getAgentScoreStatistics( email ) )
                .thenReturn( new AgentStatisticsResponse() );
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( "/webapi/managers/test@test.com/agentsscoresummary" )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test get client statistics report when manager broker logs in.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetClientStatisticsReportWhenManagerBrokerLogsIn() throws Exception {
        final String email = "testAgentEmail";
        final String fromDate = "2000-01-01";
        final String toDate = "2020-01-01";
        final String url = "/webapi/users/" + email + "/reportees" + "/client-statistics";
        when( agentReportsBusinessService.getClientStatisticsReportForBroker( fromDate, toDate, email ) )
                .thenReturn( new ClientStatisticsResponse() );
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( url ).param( "fromDate", fromDate ).param( "toDate", toDate )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
        verify( agentReportsBusinessService ).getClientStatisticsReportForBroker( fromDate, toDate, email );
    }

    /**
     * Test get client statistics report when other user logs in.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetClientStatisticsReportWhenOtherUserLogsIn() throws Exception {
        final String agentEmail = "testAgentEmail";
        final String fromDate = "2000-01-01";
        final String toDate = "2020-01-01";
        final String url = "/webapi/users/" + agentEmail + "/client-statistics";
        when( agentReportsBusinessService.getClientStatisticsReport( fromDate, toDate, agentEmail ) )
                .thenReturn( new ClientStatisticsResponse() );
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( url ).param( "fromDate", fromDate ).param( "toDate", toDate )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
        verify( agentReportsBusinessService ).getClientStatisticsReport( fromDate, toDate, agentEmail );
        verify( roleValidator ).validateByAgentEmail( agentEmail );
    }

    /**
     * Test get client first response time when other user logs in.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetClientFirstResponseTimeWhenOtherUserLogsIn() throws Exception {
        final String agentEmail = "testAgentEmail";
        final String fromDate = "2000-01-01";
        final String toDate = "2020-01-01";
        final String url = "/webapi/users/" + agentEmail + "/first-response-time";
        when( agentReportsBusinessService.getClientFirstResponseTime( fromDate, toDate, agentEmail ) )
                .thenReturn( new ClientFirstResponseTime() );
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( url ).param( "fromDate", fromDate ).param( "toDate", toDate )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
        verify( agentReportsBusinessService ).getClientFirstResponseTime( fromDate, toDate, agentEmail );
        verify( roleValidator ).validateByAgentEmail( agentEmail );
    }

    /**
     * Test get client first response time when managing broker logs in.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetClientFirstResponseTimeWhenManagingBrokerLogsIn() throws Exception {
        final String agentEmail = "testAgentEmail";
        final String fromDate = "2000-01-01";
        final String toDate = "2020-01-01";
        final String url = "/webapi/users/" + agentEmail + "/reportees/first-response-time";
        when( agentReportsBusinessService.getClientFirstResponseTimeForBroker( fromDate, toDate, agentEmail ) )
                .thenReturn( new ClientFirstResponseTime() );
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( url ).param( "fromDate", fromDate ).param( "toDate", toDate )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
        verify( agentReportsBusinessService ).getClientFirstResponseTimeForBroker( fromDate, toDate, agentEmail );
    }

    /**
     * Testget stage opportunity count.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testgetStageOpportunityCount() throws Exception {
        final String agentEmail = "testAgentEmail";
        final String fromDate = "2000-01-01";
        final String toDate = "2020-01-01";
        final String stages = "Test1,Test2";
        final String type = "Test1,Test2";
        final String url = "/webapi/users/" + agentEmail + "/opportunity-count";
        when( agentReportsBusinessService.getOpportunityCountByStage( agentEmail, fromDate, toDate, stages, type,
                false ) ).thenReturn( new HashMap<>() );
        this.mockMvc.perform( MockMvcRequestBuilders.get( url ).param( "fromDate", fromDate ).param( "toDate", toDate )
                .param( "stages", stages ).param( "type", type ).contentType( MediaType.APPLICATION_JSON )
                .header( "principal", "uid:test@test.com" ) ).andExpect( status().isOk() );
        verify( agentReportsBusinessService ).getOpportunityCountByStage( fromDate, toDate, agentEmail, stages, type,
                false );
    }

    /**
     * Testget stage opportunity count without dates.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testgetStageOpportunityCountWithoutDates() throws Exception {
        final String agentEmail = "testAgentEmail";
        final String stages = "Test1,Test2";
        final String type = "Test1,Test2";
        final String url = "/webapi/users/" + agentEmail + "/opportunity-count";
        when( agentReportsBusinessService.getOpportunityCountByStage( agentEmail, null, null, stages, type, false ) )
                .thenReturn( new HashMap<>() );
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( url ).param( "stages", stages ).param( "type", type )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
        verify( agentReportsBusinessService ).getOpportunityCountByStage( null, null, agentEmail, stages, type, false );
    }

    /**
     * Testget stage opportunity count without stages and type throws exception.
     *
     * @throws Exception
     *             the exception
     */
    @Test( expectedExceptions = MissingServletRequestParameterException.class )
    public void testgetStageOpportunityCountWithoutStagesAndTypeThrowsException() throws Exception {
        final String agentEmail = "testAgentEmail";
        final String fromDate = "2000-01-01";
        final String toDate = "2020-01-01";
        final String stages = "Test1,Test2";
        final String type = "Test1,Test2";
        final String url = "/webapi/users/" + agentEmail + "/opportunity-count";
        when( agentReportsBusinessService.getOpportunityCountByStage( agentEmail, fromDate, toDate, stages, type,
                false ) ).thenReturn( new HashMap<>() );
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( url ).param( "fromDate", fromDate ).param( "toDate", toDate )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );

    }

    /**
     * Testget lost opportunity percent.
     *
     * @throws Exception
     *             the exception
     */
    @Test(enabled=false)
    public void testgetLostOpportunityPercent() throws Exception {
        final String agentEmail = "testAgentEmail";
        final String fromDate = "2000-01-01";
        final String toDate = "2020-01-01";
        final String type = "Test1,Test2";
        final String url = "/webapi/users/" + agentEmail + "/opportunity-count/closed-lost";
        when( agentReportsBusinessService.getClosedLostOpportunityCount( agentEmail, fromDate, toDate, type ) )
                .thenReturn( new HashMap<>() );
        this.mockMvc.perform( MockMvcRequestBuilders.get( url ).param( "fromDate", fromDate ).param( "toDate", toDate )
                .param( "type", type ).contentType( MediaType.APPLICATION_JSON )
                .header( "principal", "uid:test@test.com" ) ).andExpect( status().isOk() );
        verify( agentReportsBusinessService ).getClosedLostOpportunityCount( fromDate, toDate, agentEmail, type );
    }

    /**
     * Testget lost opportunity percent without dates.
     *
     * @throws Exception
     *             the exception
     */
    @Test(enabled=false)
    public void testgetLostOpportunityPercentWithoutDates() throws Exception {
        final String agentEmail = "testAgentEmail";
        final String type = "Test1,Test2";
        final String url = "/webapi/users/" + agentEmail + "/opportunity-count/closed-lost";
        when( agentReportsBusinessService.getClosedLostOpportunityCount( agentEmail, null, null, type ) )
                .thenReturn( new HashMap<>() );
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( url ).param( "type", type )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
        verify( agentReportsBusinessService ).getClosedLostOpportunityCount( null, null, agentEmail, type );
    }

    /**
     * Testget lost opportunity percent without type throws exception.
     *
     * @throws Exception
     *             the exception
     */
    @Test(enabled=false, expectedExceptions = MissingServletRequestParameterException.class )
    public void testgetLostOpportunityPercentWithoutTypeThrowsException() throws Exception {
        final String agentEmail = "testAgentEmail";
        final String fromDate = "2000-01-01";
        final String toDate = "2020-01-01";
        final String url = "/webapi/users/" + agentEmail + "/opportunity-count/closed-lost";
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( url ).param( "fromDate", fromDate ).param( "toDate", toDate )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Testget lost opportunity percent for broker.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testgetLostOpportunityPercentForBroker() throws Exception {
        final String agentEmail = "testAgentEmail";
        final String fromDate = "2000-01-01";
        final String toDate = "2020-01-01";
        final String type = "Test1,Test2";
        final String url = "/webapi/users/" + agentEmail + "/reportees/opportunity-count/closed-lost";
        when( agentReportsBusinessService.getClosedLostOpportunityCountForBroker( agentEmail, fromDate, toDate, type ) )
                .thenReturn( new HashMap<>() );
        this.mockMvc.perform( MockMvcRequestBuilders.get( url ).param( "fromDate", fromDate ).param( "toDate", toDate )
                .param( "type", type ).contentType( MediaType.APPLICATION_JSON )
                .header( "principal", "uid:test@test.com" ) ).andExpect( status().isOk() );
        verify( agentReportsBusinessService ).getClosedLostOpportunityCountForBroker( fromDate, toDate, agentEmail,
                type );
    }

    /**
     * Testget lost opportunity percent for broker without dates.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testgetLostOpportunityPercentForBrokerWithoutDates() throws Exception {
        final String agentEmail = "testAgentEmail";
        final String type = "Test1,Test2";
        final String url = "/webapi/users/" + agentEmail + "/reportees/opportunity-count/closed-lost";
        when( agentReportsBusinessService.getClosedLostOpportunityCountForBroker( agentEmail, null, null, type ) )
                .thenReturn( new HashMap<>() );
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( url ).param( "type", type )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
        verify( agentReportsBusinessService ).getClosedLostOpportunityCountForBroker( null, null, agentEmail, type );
    }

    /**
     * Testget lost opportunity percent for broker without type throws
     * exception.
     *
     * @throws Exception
     *             the exception
     */
    @Test( expectedExceptions = MissingServletRequestParameterException.class )
    public void testgetLostOpportunityPercentForBrokerWithoutTypeThrowsException() throws Exception {
        final String agentEmail = "testAgentEmail";
        final String fromDate = "2000-01-01";
        final String toDate = "2020-01-01";
        final String url = "/webapi/users/" + agentEmail + "/reportees/opportunity-count/closed-lost";
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( url ).param( "fromDate", fromDate ).param( "toDate", toDate )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    @Test
    public void testgetStageOpportunityCountForBroker() throws Exception {
        final String agentEmail = "testAgentEmail";
        final String fromDate = "2000-01-01";
        final String toDate = "2020-01-01";
        final String type = "Test1,Test2";
        final String stages = "Test1,Test2";
        final String url = "/webapi/users/" + agentEmail + "/reportees/opportunity-count";
        when( agentReportsBusinessService.getOpportunityCountByStageForBroker( agentEmail, fromDate, toDate, stages,
                type, false ) ).thenReturn( new HashMap<>() );
        this.mockMvc.perform( MockMvcRequestBuilders.get( url ).param( "fromDate", fromDate ).param( "toDate", toDate )
                .param( "type", type ).param( "stages", stages ).contentType( MediaType.APPLICATION_JSON )
                .header( "principal", "uid:test@test.com" ) ).andExpect( status().isOk() );
        verify( agentReportsBusinessService ).getOpportunityCountByStageForBroker( fromDate, toDate, agentEmail, stages,
                type, false );
    }

    @Test
    public void testgetStageOpportunityCountForBrokerWithoutDates() throws Exception {
        final String agentEmail = "testAgentEmail";
        final String type = "Test1,Test2";
        final String stages = "Test1,Test2";
        final String url = "/webapi/users/" + agentEmail + "/reportees/opportunity-count";
        when( agentReportsBusinessService.getOpportunityCountByStageForBroker( null, null, agentEmail, stages, type,
                false ) ).thenReturn( new HashMap<>() );
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( url ).param( "type", type ).param( "stages", stages )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
        verify( agentReportsBusinessService ).getOpportunityCountByStageForBroker( null, null, agentEmail, stages, type,
                false );
    }

    @Test( expectedExceptions = MissingServletRequestParameterException.class )
    public void testgetStageOpportunityCountForBrokerWithoutStagesAndTypeThrowsException() throws Exception {
        final String agentEmail = "testAgentEmail";
        final String fromDate = "2000-01-01";
        final String toDate = "2020-01-01";
        final String type = "Test1,Test2";
        final String stages = "Test1,Test2";
        final String url = "/webapi/users/" + agentEmail + "/reportees/opportunity-count";
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( url ).param( "fromDate", fromDate ).param( "toDate", toDate )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test get agent Statistics for Grid.
     *
     * @throws Exception
     *             the exception
     *             /**
     *             Test get agent Statistics for Grid.
     */
    @Test
    public void testGetAgentStatisticsForGridWithIsWeekTrue() throws Exception {
        final String agentEmail = "test@test.com";
        final String isWeek = "true";
        final ApiUser user = new ApiUser();
        final String url = "/webapi/users/" + agentEmail + "/f2f-stats";

        when( roleValidator.validateByAgentEmail( agentEmail ) ).thenReturn( user );
        when( agentReportsBusinessService.getAgentGridStatistics( agentEmail ) )
                .thenReturn( new com.owners.gravitas.dto.response.AgentStatisticsResponse() );
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( url ).param( "isWeek", isWeek )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test get agent Statistics for Graph.
     * <<<<<<< HEAD
     *
     * @throws Exception
     *             the exception
     *             =======
     *             >>>>>>> master
     */
    @Test
    public void testGetAgentStatisticsForGraphWithIsWeekFalse() throws Exception {
        final String agentEmail = "test@test.com";
        final String isWeek = "false";
        final ApiUser user = new ApiUser();
        final String url = "/webapi/users/" + agentEmail + "/f2f-stats";

        when( roleValidator.validateByAgentEmail( agentEmail ) ).thenReturn( user );
        when( agentReportsBusinessService.getAgentGridStatistics( agentEmail ) )
                .thenReturn( new com.owners.gravitas.dto.response.AgentStatisticsResponse() );
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( url ).param( "isWeek", isWeek )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test get agent Statistics count Grid.
     * <<<<<<< HEAD
     *
     * @throws Exception
     *             the exception
     *             =======
     *             >>>>>>> master
     */
    @Test
    public void testGetAgentStatisticsCountForGridWithIsWeekTrue() throws Exception {
        final String agentEmail = "test@test.com";
        final boolean isWeek = true;
        final ApiUser user = new ApiUser();
        final String url = "/webapi/users/" + agentEmail + "/f2f-count";

        when( roleValidator.validateByAgentEmail( agentEmail ) ).thenReturn( user );
        when( agentReportsBusinessService.getFaceToFaceStatistics( agentEmail, isWeek, true ) )
                .thenReturn( new AgentCumulativeResponse() );
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( url ).param( "isWeek", isWeek + "" )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test get agent Statistics count for Graph.
     * <<<<<<< HEAD
     *
     * @throws Exception
     *             the exception
     *             =======
     *             >>>>>>> master
     */
    @Test
    public void testGetAgentStatisticsCountForGraphWithIsWeekFalse() throws Exception {
        final String agentEmail = "test@test.com";
        final boolean isWeek = false;
        final ApiUser user = new ApiUser();
        final String url = "/webapi/users/" + agentEmail + "/f2f-count";

        when( roleValidator.validateByAgentEmail( agentEmail ) ).thenReturn( user );
        when( agentReportsBusinessService.getFaceToFaceStatistics( agentEmail, isWeek, true ) )
                .thenReturn( new AgentCumulativeResponse() );
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( url ).param( "isWeek", isWeek + "" )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test get Managing Broker Statistics for Grid.
     * <<<<<<< HEAD
     *
     * @throws Exception
     *             the exception
     *             =======
     *             >>>>>>> master
     */
    @Test
    public void testGetManagingBrokerStatisticsForGridWithIsWeekTrue() throws Exception {
        final String managingBrokerEmail = "test@test.com";
        final String isWeek = "true";
        final ApiUser user = new ApiUser();
        final String url = "/webapi/users/" + managingBrokerEmail + "/reportees/f2f-stats";

        when( agentReportsBusinessService.getManagingBrokerGridStatistics( managingBrokerEmail ) )
                .thenReturn( new com.owners.gravitas.dto.response.AgentStatisticsResponse() );
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( url ).param( "isWeek", isWeek )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test get Managing Broker Statistics for Graph.
     * <<<<<<< HEAD
     *
     * @throws Exception
     *             the exception
     *             =======
     *             >>>>>>> master
     */
    @Test
    public void testGetManagingBrokerStatisticsForGraphWithIsWeekFalse() throws Exception {
        final String agentEmail = "test@test.com";
        final String isWeek = "false";
        final ApiUser user = new ApiUser();
        final String url = "/webapi/users/" + agentEmail + "/reportees/f2f-stats";

        when( agentReportsBusinessService.getManagingBrokerStatistics( agentEmail ) )
                .thenReturn( new com.owners.gravitas.dto.response.AgentStatisticsResponse() );
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( url ).param( "isWeek", isWeek )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test get Managing Broker Statistics count Grid.
     * <<<<<<< HEAD
     *
     * @throws Exception
     *             the exception
     *             =======
     *             >>>>>>> master
     */
    @Test
    public void testGetManagingBrokerStatisticsCountForGridWithIsWeekTrue() throws Exception {
        final String agentEmail = "test@test.com";
        final boolean isWeek = true;
        final ApiUser user = new ApiUser();
        final String url = "/webapi/users/" + agentEmail + "/reportees/f2f-count";

        when( agentReportsBusinessService.getFaceToFaceStatistics( agentEmail, isWeek, false ) )
                .thenReturn( new AgentCumulativeResponse() );
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( url ).param( "isWeek", isWeek + "" )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test get Managing Broker Statistics count for Graph.
     * <<<<<<< HEAD
     *
     * @throws Exception
     *             the exception
     *             =======
     *             >>>>>>> master
     */
    @Test
    public void testGetManagingBrokerStatisticsCountForGraphWithIsWeekFalse() throws Exception {
        final String agentEmail = "test@test.com";
        final boolean isWeek = false;
        final ApiUser user = new ApiUser();
        final String url = "/webapi/users/" + agentEmail + "/reportees/f2f-count";

        when( agentReportsBusinessService.getFaceToFaceStatistics( agentEmail, isWeek, false ) )
                .thenReturn( new AgentCumulativeResponse() );
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( url ).param( "isWeek", isWeek + "" )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test Download agent Statistics.
     * <<<<<<< HEAD
     *
     * @throws Exception
     *             the exception
     *             =======
     *             >>>>>>> master
     */
    @Test
    public void testDownloadAgentStatistics() throws Exception {
        final String agentEmail = "test@test.com";
        final ApiUser user = new ApiUser();
        final String url = "/webapi/users/" + agentEmail + "/f2f-stats/download";
        final Workbook workbook = new XSSFWorkbook();
        when( roleValidator.validateByAgentEmail( agentEmail ) ).thenReturn( user );
        when( agentReportsBusinessService.downloadAgentStatistics( agentEmail, true ) ).thenReturn( workbook );
        this.mockMvc.perform( MockMvcRequestBuilders.get( url ).contentType( MediaType.APPLICATION_JSON )
                .header( "principal", "uid:test@test.com" ) ).andExpect( status().isOk() );
    }

    /**
     * Test Download managing Broker Statistics for Grid.
     * <<<<<<< HEAD
     *
     * @throws Exception
     *             the exception
     *             =======
     *             >>>>>>> master
     */
    @Test
    public void testdownloadManaginBrockerStatistics() throws Exception {
        final String agentEmail = "test@test.com";
        final ApiUser user = new ApiUser();
        final String url = "/webapi/users/" + agentEmail + "/reportees/f2f-stats/download";
        final Workbook workbook = new XSSFWorkbook();
        when( roleValidator.validateByAgentEmail( agentEmail ) ).thenReturn( user );
        when( agentReportsBusinessService.downloadManagingBrokerStatistics( agentEmail ) ).thenReturn( workbook );
        this.mockMvc.perform( MockMvcRequestBuilders.get( url ).contentType( MediaType.APPLICATION_JSON )
                .header( "principal", "uid:test@test.com" ) ).andExpect( status().isOk() );
    }

    /**
     * Test Download managing Broker statistics count.
     * <<<<<<< HEAD
     *
     * @throws Exception
     *             the exception
     *             =======
     *             >>>>>>> master
     */
    @Test
    public void testdownloadFaceToFaceCount() throws Exception {
        final String agentEmail = "test@test.com";
        final ApiUser user = new ApiUser();
        final String url = "/webapi/users/" + agentEmail + "/reportees/f2f-count/download";
        final Workbook workbook = new XSSFWorkbook();
        when( roleValidator.validateByAgentEmail( agentEmail ) ).thenReturn( user );
        when( agentReportsBusinessService.downloadFaceToFaceStatistics( agentEmail, false ) ).thenReturn( workbook );
        this.mockMvc.perform( MockMvcRequestBuilders.get( url ).contentType( MediaType.APPLICATION_JSON )
                .header( "principal", "uid:test@test.com" ) ).andExpect( status().isOk() );
    }

    /**
     * Test Download agent Statistics.
     * <<<<<<< HEAD
     *
     * @throws Exception
     *             the exception
     *             =======
     *             >>>>>>> master
     */
    @Test
    public void testDownloadAgentStatisticsthrowIOException() throws Exception {
        final String agentEmail = "test@test.com";
        final ApiUser user = new ApiUser();
        final String url = "/webapi/users/" + agentEmail + "/f2f-stats/download";
        boolean exceptionFlag = false;

        when( roleValidator.validateByAgentEmail( agentEmail ) ).thenReturn( user );
        when( agentReportsBusinessService.downloadAgentStatistics( agentEmail, true ) ).thenThrow( IOException.class );
        try {
            this.mockMvc.perform( MockMvcRequestBuilders.get( url ).param( "email", agentEmail )
                    .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) );
        } catch ( final Exception exception ) {
            exceptionFlag = true;
        }
        assertTrue( exceptionFlag );
    }

    /**
     * Test Download managing Broker Statistics for Grid.
     * <<<<<<< HEAD
     *
     * @throws Exception
     *             the exception
     *             =======
     *             >>>>>>> master
     */
    @Test
    public void testdownloadManaginBrockerStatisticsthrowIOException() throws Exception {
        final String managingBroker = "test@test.com";
        final ApiUser user = new ApiUser();
        final String url = "/webapi/users/" + managingBroker + "/reportees/f2f-stats/download";
        boolean exceptionFlag = false;

        when( roleValidator.validateByAgentEmail( managingBroker ) ).thenReturn( user );
        when( agentReportsBusinessService.downloadManagingBrokerStatistics( managingBroker ) )
                .thenThrow( IOException.class );

        try {
            this.mockMvc.perform( MockMvcRequestBuilders.get( url ).param( "email", managingBroker )
                    .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) );
        } catch ( final Exception exception ) {
            exceptionFlag = true;
        }
        assertTrue( exceptionFlag );
    }

    /**
     * Test Download managing Broker statistics count.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testdownloadFaceToFaceCountthrowIOException() throws Exception {
        final String managingBroker = "test@test.com";
        final ApiUser user = new ApiUser();
        final String url = "/webapi/users/" + managingBroker + "/reportees/f2f-count/download";
        boolean exceptionFlag = false;

        when( roleValidator.validateByAgentEmail( managingBroker ) ).thenReturn( user );
        when( agentReportsBusinessService.downloadFaceToFaceStatistics( managingBroker, false ) )
                .thenThrow( IOException.class );

        try {
            this.mockMvc.perform( MockMvcRequestBuilders.get( url ).param( "email", managingBroker )
                    .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) );
        } catch ( final Exception exception ) {
            exceptionFlag = true;
        }
        assertTrue( exceptionFlag );
    }

    /**
     * Test get Executive Statistics count Grid.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetExecutiveStatisticsCount_For_Grid() throws Exception {
        final String email = "test@test.com";
        final ApiUser user = new ApiUser();
        final String url = "/webapi/users/" + email + "/all-reportees/f2f-stats";

        when( agentReportsBusinessService.getExecutiveGridStatistics( email ) )
                .thenReturn( new com.owners.gravitas.dto.response.AgentStatisticsResponse() );
        this.mockMvc.perform( MockMvcRequestBuilders.get( url ).contentType( MediaType.APPLICATION_JSON )
                .header( "principal", "uid:test@test.com" ) ).andExpect( status().isOk() );
    }

    /**
     * Test get Executive Statistics count Grid.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetExecutiveF2FCount() throws Exception {
        final String email = "test@test.com";
        final boolean isWeek = true;
        final ApiUser user = new ApiUser();
        final String url = "/webapi/users/" + email + "/all-reportees/f2f-count";

        when( agentReportsBusinessService.getExecutiveFaceToFaceCount( email, isWeek ) )
                .thenReturn( new AgentCumulativeResponse() );
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( url ).param( "isWeek", isWeek + "" )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test Download Executive F2F count & Statistics.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testDownloadExecutiveF2FStatisticsReport() throws Exception {
        final String email = "test@test.com";
        final ApiUser user = new ApiUser();
        final String url = "/webapi/users/" + email + "/all-reportees/f2f-stats/download";
        final Workbook workbook = new XSSFWorkbook();
        // when( roleValidator.validateByUserEmail( email ) ).thenReturn( void
        // );
        when( agentReportsBusinessService.downloadExecutiveF2FStatisticsReport( email ) ).thenReturn( workbook );
        this.mockMvc.perform( MockMvcRequestBuilders.get( url ).contentType( MediaType.APPLICATION_JSON )
                .header( "principal", "uid:test@test.com" ) ).andExpect( status().isOk() );
    }

    /**
     * Test Download Executive F2F count & Statistics with IOException.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testDownloadExecutiveF2FStatisticsReportWithIOException() throws Exception {
        final String email = "test@test.com";
        final ApiUser user = new ApiUser();
        final String url = "/webapi/users/" + email + "/all-reportees/f2f-stats/download";
        boolean exceptionFlag = false;
        // when( userRoleValidator.validateByUserEmail( email ) ).thenReturn(
        // void );
        when( agentReportsBusinessService.downloadExecutiveF2FStatisticsReport( email ) )
                .thenThrow( IOException.class );
        try {
            this.mockMvc.perform( MockMvcRequestBuilders.get( url ).param( "email", email )
                    .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) );
        } catch ( final Exception exception ) {
            exceptionFlag = true;
        }
        assertTrue( exceptionFlag );
    }
    
    @Test
    public void testViewCalendarEventsFromAgentApp() throws Exception {
        final String email = "test@test.com";
        when( agentReportsBusinessService.getCalendarEventsFromAgentApp( anyString(), anyLong(), anyLong() ) )
                .thenReturn( new CalendarEventListResponse() );
        this.mockMvc.perform( MockMvcRequestBuilders.get( "/webapi/agents/" + email + "/agentApp/calendar/events" )
                .header( "principal", "uid:test@test.com" ) ).andExpect( status().isOk() );
    }
    
    @Test
    public void testGetAgentStates() throws Exception {
        when( agentReportsBusinessService.getAllAgentStates() ).thenReturn( new AgentStateResponse() );
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( "/webapi/agents/states" )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }
}
