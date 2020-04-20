package com.owners.gravitas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class for holding JMX properties related to agent performance metrics
 * job.
 *
 * @author ankusht
 */
@ManagedResource( objectName = "com.owners.gravitas:name=AgentMetricsJmxConfig" )
@Component
public class AgentMetricsJmxConfig {

    /** The property writer. */
    @Autowired
    private PropertyWriter propertyWriter;

    /** The calculate buyer opportunities percentage. */
    @Value( "${metric.buyer.opportunities.percentage.calculation.enabled: true}" )
    private boolean buyerOppoPctgCalcEnabled;

    /** The calculate seller opportunities percentage. */
    @Value( "${metric.seller.opportunities.percentage.calculation.enabled: true}" )
    private boolean sellerOppoPctgCalcEnabled;

    /** The calculate pending transactions. */
    @Value( "${metric.pending.transactions.calculation.enabled: true}" )
    private boolean pendingTransactionsCalcEnabled;

    /** The calculate closed transactions. */
    @Value( "${metric.closed.transactions.calculation.enabled: true}" )
    private boolean closedTransactionsCalcEnabled;

    /** The calculate response time. */
    @Value( "${metric.response.time.calculation.enabled: true}" )
    private boolean responseTimeCalcEnabled;

    /** The enable agent performance metrics job. */
    @Value( "${job.agent.performance.metrics.enabled: true}" )
    private boolean agentPerformanceMetricsJobEnabled;

    /**
     * Checks if is buyer oppo pctg calc enabled.
     *
     * @return true, if is buyer oppo pctg calc enabled
     */
    @ManagedAttribute
    public boolean isBuyerOppoPctgCalcEnabled() {
        return buyerOppoPctgCalcEnabled;
    }

    /**
     * Sets the buyer oppo pctg calc enabled.
     *
     * @param buyerOppoPctgCalcEnabled
     *            the new buyer oppo pctg calc enabled
     */
    @ManagedAttribute
    public void setBuyerOppoPctgCalcEnabled( boolean buyerOppoPctgCalcEnabled ) {
        this.buyerOppoPctgCalcEnabled = buyerOppoPctgCalcEnabled;
        propertyWriter.saveJmxProperty( "metric.buyer.opportunities.percentage.calculation.enabled",
                buyerOppoPctgCalcEnabled );
    }

    /**
     * Checks if is seller oppo pctg calc enabled.
     *
     * @return true, if is seller oppo pctg calc enabled
     */
    @ManagedAttribute
    public boolean isSellerOppoPctgCalcEnabled() {
        return sellerOppoPctgCalcEnabled;
    }

    /**
     * Sets the seller oppo pctg calc enabled.
     *
     * @param sellerOppoPctgCalcEnabled
     *            the new seller oppo pctg calc enabled
     */
    @ManagedAttribute
    public void setSellerOppoPctgCalcEnabled( boolean sellerOppoPctgCalcEnabled ) {
        this.sellerOppoPctgCalcEnabled = sellerOppoPctgCalcEnabled;
        propertyWriter.saveJmxProperty( "metric.seller.opportunities.percentage.calculation.enabled",
                sellerOppoPctgCalcEnabled );
    }

    /**
     * Checks if is pending transactions calc enabled.
     *
     * @return true, if is pending transactions calc enabled
     */
    @ManagedAttribute
    public boolean isPendingTransactionsCalcEnabled() {
        return pendingTransactionsCalcEnabled;
    }

    /**
     * Sets the pending transactions calc enabled.
     *
     * @param pendingTransactionsCalcEnabled
     *            the new pending transactions calc enabled
     */
    @ManagedAttribute
    public void setPendingTransactionsCalcEnabled( boolean pendingTransactionsCalcEnabled ) {
        this.pendingTransactionsCalcEnabled = pendingTransactionsCalcEnabled;
        propertyWriter.saveJmxProperty( "metric.pending.transactions.calculation.enabled",
                pendingTransactionsCalcEnabled );
    }

    /**
     * Checks if is closed transactions calc enabled.
     *
     * @return true, if is closed transactions calc enabled
     */
    @ManagedAttribute
    public boolean isClosedTransactionsCalcEnabled() {
        return closedTransactionsCalcEnabled;
    }

    /**
     * Sets the closed transactions calc enabled.
     *
     * @param closedTransactionsCalcEnabled
     *            the new closed transactions calc enabled
     */
    @ManagedAttribute
    public void setClosedTransactionsCalcEnabled( boolean closedTransactionsCalcEnabled ) {
        this.closedTransactionsCalcEnabled = closedTransactionsCalcEnabled;
        propertyWriter.saveJmxProperty( "metric.closed.transactions.calculation.enabled",
                closedTransactionsCalcEnabled );
    }

    /**
     * Checks if is response time calc enabled.
     *
     * @return true, if is response time calc enabled
     */
    @ManagedAttribute
    public boolean isResponseTimeCalcEnabled() {
        return responseTimeCalcEnabled;
    }

    /**
     * Sets the response time calc enabled.
     *
     * @param responseTimeCalcEnabled
     *            the new response time calc enabled
     */
    @ManagedAttribute
    public void setResponseTimeCalcEnabled( boolean responseTimeCalcEnabled ) {
        this.responseTimeCalcEnabled = responseTimeCalcEnabled;
        propertyWriter.saveJmxProperty( "metric.response.time.calculation.enabled", responseTimeCalcEnabled );
    }

    /**
     * Checks if is agent performance metrics job enabled.
     *
     * @return true, if is agent performance metrics job enabled
     */
    @ManagedAttribute
    public boolean isAgentPerformanceMetricsJobEnabled() {
        return agentPerformanceMetricsJobEnabled;
    }

    /**
     * Sets the agent performance metrics job enabled.
     *
     * @param agentPerformanceMetricsJobEnabled
     *            the new agent performance metrics job enabled
     */
    @ManagedAttribute
    public void setAgentPerformanceMetricsJobEnabled( boolean agentPerformanceMetricsJobEnabled ) {
        this.agentPerformanceMetricsJobEnabled = agentPerformanceMetricsJobEnabled;
        propertyWriter.saveJmxProperty( "job.agent.performance.metrics.enabled", agentPerformanceMetricsJobEnabled );
    }

}
