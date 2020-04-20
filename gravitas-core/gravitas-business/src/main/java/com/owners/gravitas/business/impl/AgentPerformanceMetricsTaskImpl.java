package com.owners.gravitas.business.impl;

import static com.owners.gravitas.enums.BuyerStage.PENDING_SALE;
import static com.owners.gravitas.enums.BuyerStage.SOLD;
import static com.owners.gravitas.enums.RecordType.BUYER;
import static com.owners.gravitas.enums.RecordType.SELLER;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.business.AgentPerformanceMetricsTask;
import com.owners.gravitas.config.AgentMetricsJmxConfig;
import com.owners.gravitas.domain.Agent;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.domain.entity.AgentPerformanceLog;
import com.owners.gravitas.domain.entity.AgentPerformanceLogId;
import com.owners.gravitas.enums.SellerStage;
import com.owners.gravitas.service.AgentPerformanceLogService;
import com.owners.gravitas.service.AgentService;

/**
 * The Class AgentPerformanceMetricsTaskImpl.
 *
 * @author ankusht
 */
@Service
public class AgentPerformanceMetricsTaskImpl implements AgentPerformanceMetricsTask {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentPerformanceMetricsTaskImpl.class );

    /** The agent performance metrics service. */
    @Autowired
    private AgentPerformanceLogService agentPerformanceMetricsService;

    /** The agent service. */
    @Autowired
    private AgentService agentService;

    /** The agent performance metrics properties holder. */
    @Autowired
    private AgentMetricsJmxConfig agentMetricsJmxConfig;

    /**
     * Compute and save performance metrics.
     *
     * @param agentId
     *            the agent id
     */
    @Async
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public void computeAndSavePerformanceMetrics( final String agentId ) {
        LOGGER.info( "calculating performance for agent id: " + agentId );
        final Agent agent = agentService.getAgentById( agentId );
        final Set< Opportunity > opportunities = agent.getOpportunities().values().stream()
                .filter( op -> !op.getDeleted() ).collect( Collectors.toSet() );
        final AgentPerformanceLog agentPerformanceLog = buildMetrics( opportunities );
        final AgentPerformanceLogId agentPerformanceLogId = new AgentPerformanceLogId( agent.getInfo().getEmail(),
                DateTime.now() );
        agentPerformanceLog.setAgentPerformanceLogId( agentPerformanceLogId );
        agentPerformanceLog.setAgentFbId( agentId );
        agentPerformanceMetricsService.savePerformanceLog( agentPerformanceLog );
    }

    /**
     * Compute percentages.
     *
     * @param opportunities
     *            the opportunities
     * @return the map containing all the counts
     */
    private AgentPerformanceLog buildMetrics( final Collection< Opportunity > opportunities ) {
        final AgentPerformanceLog agentPerformanceLog = new AgentPerformanceLog();
        final List< Integer > counts = computeCounts( opportunities );
        final int total = opportunities.size();
        final Integer buyerOppCount = counts.get( 2 );
        final Integer sellerOppCount = counts.get( 3 );
        agentPerformanceLog.setPendingTransactions( counts.get( 0 ) );
        agentPerformanceLog.setClosedTransactions( counts.get( 1 ) );
        if (total > 0) {
            if (buyerOppCount != null) {
                agentPerformanceLog.setBuyerOpportunitiesPercentage( ( double ) buyerOppCount * 100 / total );
            }
            if (sellerOppCount != null) {
                agentPerformanceLog.setSellerOpportunitiesPercentage( ( double ) sellerOppCount * 100 / total );
            }
        } else {
            agentPerformanceLog.setBuyerOpportunitiesPercentage( 0.0 );
            agentPerformanceLog.setSellerOpportunitiesPercentage( 0.0 );
        }
        return agentPerformanceLog;
    }

    /**
     * Compute counts.
     *
     * @param opportunities
     *            the opportunities
     * @return the list
     */
    private List< Integer > computeCounts( final Collection< Opportunity > opportunities ) {
        Integer pendingCount = getPendingCount();
        Integer closedCount = getClosedCount();
        Integer buyerOppCount = getBuyerCount();
        Integer sellerOppCount = getSellerOppCount();
        for ( final Opportunity opportunity : opportunities ) {
            if (pendingCount != null && ( PENDING_SALE.getStage().equalsIgnoreCase( opportunity.getStage() )
                    || SellerStage.PENDING_SALE.getStage().equalsIgnoreCase( opportunity.getStage() ) )) {
                pendingCount++;
            } else if (closedCount != null && ( SOLD.getStage().equalsIgnoreCase( opportunity.getStage() )
                    || SellerStage.SOLD.getStage().equalsIgnoreCase( opportunity.getStage() ) )) {
                closedCount++;
            }

            if (buyerOppCount != null && BUYER.getType().equalsIgnoreCase( opportunity.getOpportunityType() )) {
                buyerOppCount++;
            } else if (sellerOppCount != null
                    && SELLER.getType().equalsIgnoreCase( opportunity.getOpportunityType() )) {
                sellerOppCount++;
            }
        }
        return Arrays.asList( pendingCount, closedCount, buyerOppCount, sellerOppCount );
    }

    /**
     * Gets the seller opp count.
     *
     * @return the seller opp count
     */
    private Integer getSellerOppCount() {
        Integer sellerOppCount = null;
        if (agentMetricsJmxConfig.isSellerOppoPctgCalcEnabled()) {
            sellerOppCount = 0;
        }
        return sellerOppCount;
    }

    /**
     * Gets the buyer count.
     *
     * @return the buyer count
     */
    private Integer getBuyerCount() {
        Integer buyerOppCount = null;
        if (agentMetricsJmxConfig.isBuyerOppoPctgCalcEnabled()) {
            buyerOppCount = 0;
        }
        return buyerOppCount;
    }

    /**
     * Gets the closed count.
     *
     * @return the closed count
     */
    private Integer getClosedCount() {
        Integer closedCount = null;
        if (agentMetricsJmxConfig.isClosedTransactionsCalcEnabled()) {
            closedCount = 0;
        }
        return closedCount;
    }

    /**
     * Gets the pending count.
     *
     * @return the pending count
     */
    private Integer getPendingCount() {
        Integer pendingCount = null;
        if (agentMetricsJmxConfig.isPendingTransactionsCalcEnabled()) {
            pendingCount = 0;
        }
        return pendingCount;
    }
}