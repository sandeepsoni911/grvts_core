package com.owners.gravitas.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * The Class AgentPerformanceLog.
 *
 * @author ankusht
 */
@Entity( name = "GR_AGENT_PERFORMANCE_LOG" )
@EntityListeners( AuditingEntityListener.class )
public class AgentPerformanceLog implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5009537036958999022L;

    /** The agent fb id. */
    @Column( name = "AGENT_FB_ID", nullable = false )
    private String agentFbId;

    /** The buyer opportunities percentage. */
    @Column( name = "BUYER_OPPORTUNITIES_PCTG" )
    private Double buyerOpportunitiesPercentage;

    /** The seller opportunities percentage. */
    @Column( name = "SELLER_OPPORTUNITIES_PCTG" )
    private Double sellerOpportunitiesPercentage;

    /** The number of pending transactions. */
    @Column( name = "PENDING_TRANSACTIONS" )
    private Integer pendingTransactions;

    /** The number of closed transactions. */
    @Column( name = "CLOSED_TRANSACTIONS" )
    private Integer closedTransactions;

    /** The created by. */
    @CreatedBy
    @Column( name = "CREATED_BY", updatable = false )
    private String createdBy;

    /** The agent performance log id. */
    @EmbeddedId
    private AgentPerformanceLogId agentPerformanceLogId;

    /**
     * Gets the agent fb id.
     *
     * @return the agent fb id
     */
    public String getAgentFbId() {
        return agentFbId;
    }

    /**
     * Sets the agent fb id.
     *
     * @param agentFbId
     *            the new agent fb id
     */
    public void setAgentFbId( final String agentFbId ) {
        this.agentFbId = agentFbId;
    }

    /**
     * Gets the buyer opportunities percentage.
     *
     * @return the buyer opportunities percentage
     */
    public Double getBuyerOpportunitiesPercentage() {
        return buyerOpportunitiesPercentage;
    }

    /**
     * Sets the buyer opportunities percentage.
     *
     * @param buyerOpportunitiesPercentage
     *            the new buyer opportunities percentage
     */
    public void setBuyerOpportunitiesPercentage( final Double buyerOpportunitiesPercentage ) {
        this.buyerOpportunitiesPercentage = buyerOpportunitiesPercentage;
    }

    /**
     * Gets the seller opportunities percentage.
     *
     * @return the seller opportunities percentage
     */
    public Double getSellerOpportunitiesPercentage() {
        return sellerOpportunitiesPercentage;
    }

    /**
     * Sets the seller opportunities percentage.
     *
     * @param sellerOpportunitiesPercentage
     *            the new seller opportunities percentage
     */
    public void setSellerOpportunitiesPercentage( final Double sellerOpportunitiesPercentage ) {
        this.sellerOpportunitiesPercentage = sellerOpportunitiesPercentage;
    }

    /**
     * Gets the pending transactions.
     *
     * @return the pending transactions
     */
    public Integer getPendingTransactions() {
        return pendingTransactions;
    }

    /**
     * Sets the pending transactions.
     *
     * @param pendingTransactions
     *            the new pending transactions
     */
    public void setPendingTransactions( final Integer pendingTransactions ) {
        this.pendingTransactions = pendingTransactions;
    }

    /**
     * Gets the closed transactions.
     *
     * @return the closed transactions
     */
    public Integer getClosedTransactions() {
        return closedTransactions;
    }

    /**
     * Sets the closed transactions.
     *
     * @param closedTransactions
     *            the new closed transactions
     */
    public void setClosedTransactions( final Integer closedTransactions ) {
        this.closedTransactions = closedTransactions;
    }

    /**
     * Gets the created by.
     *
     * @return the created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the created by.
     *
     * @param createdBy
     *            the new created by
     */
    public void setCreatedBy( final String createdBy ) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the agent performance log id.
     *
     * @return the agent performance log id
     */
    public AgentPerformanceLogId getAgentPerformanceLogId() {
        return agentPerformanceLogId;
    }

    /**
     * Sets the agent performance log id.
     *
     * @param agentPerformanceLogId
     *            the new agent performance log id
     */
    public void setAgentPerformanceLogId( final AgentPerformanceLogId agentPerformanceLogId ) {
        this.agentPerformanceLogId = agentPerformanceLogId;
    }

    /**
     * Instantiates a new agent performance log.
     *
     * @param agentFbId
     *            the agent fb id
     * @param agentPerformanceLogId
     *            the agent performance log id
     */
    public AgentPerformanceLog( final String agentFbId, final AgentPerformanceLogId agentPerformanceLogId ) {
        super();
        this.agentFbId = agentFbId;
        this.agentPerformanceLogId = agentPerformanceLogId;
    }

    /**
     * Instantiates a new agent performance log.
     */
    public AgentPerformanceLog() {
        super();
    }
}
