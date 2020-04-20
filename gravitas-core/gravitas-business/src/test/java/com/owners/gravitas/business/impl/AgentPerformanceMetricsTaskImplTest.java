package com.owners.gravitas.business.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.AgentMetricsJmxConfig;
import com.owners.gravitas.domain.Agent;
import com.owners.gravitas.domain.AgentInfo;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.domain.entity.AgentPerformanceLog;
import com.owners.gravitas.enums.BuyerStage;
import com.owners.gravitas.enums.RecordType;
import com.owners.gravitas.enums.SellerStage;
import com.owners.gravitas.service.AgentPerformanceLogService;
import com.owners.gravitas.service.AgentService;

/**
 * The Class AgentPerformanceMetricsTaskImplTest.
 * 
 * @author ankusht
 */
public class AgentPerformanceMetricsTaskImplTest extends AbstractBaseMockitoTest {

    /** The agent performance metrics service. */
    @Mock
    private AgentPerformanceLogService agentPerformanceMetricsService;

    /** The agent service. */
    @Mock
    private AgentService agentService;

    /** The agent performance metrics properties holder. */
    @Mock
    private AgentMetricsJmxConfig agentMetricsJmxConfig;

    /** The agent performance metrics task impl. */
    @InjectMocks
    private AgentPerformanceMetricsTaskImpl agentPerformanceMetricsTaskImpl;

    /**
     * Test compute and save performance metrics when no opportunity is assigned
     * to agent.
     */
    @Test
    public void testComputeAndSavePerformanceMetricsWhenNoOpportunityIsAssignedToAgent() {
        String agentId = "dummyId";
        Agent agent = new Agent();
        AgentInfo info = new AgentInfo();
        agent.setInfo( info );
        when( agentService.getAgentById( agentId ) ).thenReturn( agent );
        agentPerformanceMetricsTaskImpl.computeAndSavePerformanceMetrics( agentId );
        verify( agentPerformanceMetricsService ).savePerformanceLog( any( AgentPerformanceLog.class ) );
    }

    /**
     * Test compute and save performance metrics when opportunities are assigned
     * to agent.
     *
     * @param opportunitiesMap
     *            the opportunities map
     */
    @Test( dataProvider = "opportunitiesMap" )
    public void testComputeAndSavePerformanceMetricsWhenOpportunitiesAreAssignedToAgent(
            Map< String, Opportunity > opportunitiesMap ) {
        String agentId = "dummyId";
        Agent agent = new Agent();
        AgentInfo info = new AgentInfo();
        agent.setInfo( info );
        agent.setOpportunities( opportunitiesMap );
        when( agentMetricsJmxConfig.isPendingTransactionsCalcEnabled() ).thenReturn( Boolean.TRUE );
        when( agentMetricsJmxConfig.isClosedTransactionsCalcEnabled() ).thenReturn( Boolean.TRUE );
        when( agentMetricsJmxConfig.isBuyerOppoPctgCalcEnabled() ).thenReturn( Boolean.TRUE );
        when( agentMetricsJmxConfig.isSellerOppoPctgCalcEnabled() ).thenReturn( Boolean.TRUE );
        when( agentService.getAgentById( agentId ) ).thenReturn( agent );
        agentPerformanceMetricsTaskImpl.computeAndSavePerformanceMetrics( agentId );
        verify( agentPerformanceMetricsService ).savePerformanceLog( any( AgentPerformanceLog.class ) );
    }

    /**
     * Test compute and save performance metrics when jmx props are disabled.
     *
     * @param opportunitiesMap
     *            the opportunities map
     */
    @Test( dataProvider = "opportunitiesMap" )
    public void testComputeAndSavePerformanceMetricsWhenJmxPropsAreDisabled(
            Map< String, Opportunity > opportunitiesMap ) {
        String agentId = "dummyId";
        Agent agent = new Agent();
        AgentInfo info = new AgentInfo();
        agent.setInfo( info );
        agent.setOpportunities( opportunitiesMap );
        when( agentMetricsJmxConfig.isPendingTransactionsCalcEnabled() ).thenReturn( Boolean.FALSE );
        when( agentMetricsJmxConfig.isClosedTransactionsCalcEnabled() ).thenReturn( Boolean.FALSE );
        when( agentMetricsJmxConfig.isBuyerOppoPctgCalcEnabled() ).thenReturn( Boolean.FALSE );
        when( agentMetricsJmxConfig.isSellerOppoPctgCalcEnabled() ).thenReturn( Boolean.FALSE );
        when( agentService.getAgentById( agentId ) ).thenReturn( agent );
        agentPerformanceMetricsTaskImpl.computeAndSavePerformanceMetrics( agentId );
        verify( agentPerformanceMetricsService ).savePerformanceLog( any( AgentPerformanceLog.class ) );
    }

    /**
     * Gets the opportunities map.
     *
     * @return the opportunities map
     */
    @DataProvider( name = "opportunitiesMap" )
    public Object[][] getOpportunitiesMap() {
        Map< String, Opportunity > opportunitiesMap = new HashMap<>();
        Opportunity opp = new Opportunity();
        opp.setOpportunityType( RecordType.BUYER.getType() );
        opp.setStage( BuyerStage.PENDING_SALE.getStage() );
        opp.setDeleted( false );
        opportunitiesMap.put( "1", opp );

        opp = new Opportunity();
        opp.setOpportunityType( RecordType.SELLER.getType() );
        opp.setStage( SellerStage.PENDING_SALE.getStage() );
        opp.setDeleted( false );
        opportunitiesMap.put( "2", opp );

        opp = new Opportunity();
        opp.setOpportunityType( RecordType.BUYER.getType() );
        opp.setStage( BuyerStage.SOLD.getStage() );
        opp.setDeleted( false );
        opportunitiesMap.put( "3", opp );

        opp = new Opportunity();
        opp.setOpportunityType( RecordType.SELLER.getType() );
        opp.setStage( SellerStage.SOLD.getStage() );
        opp.setDeleted( false );
        opportunitiesMap.put( "4", opp );

        opp = new Opportunity();
        opp.setOpportunityType( RecordType.MLS.getType() );
        opp.setStage( SellerStage.SOLD.getStage() );
        opp.setDeleted( false );
        opportunitiesMap.put( "5", opp );
        return new Object[][] { { opportunitiesMap } };
    }
}
