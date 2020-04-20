package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * The Class CbsaMarketLevel.
 * 
 * @author ankusht
 */
@Entity( name = "GR_CBSA_MARKET_LEVEL" )
public class CbsaMarketLevel extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 137325162771332210L;

    /** The owners market cbsa label. */
    @ManyToOne
    @JoinColumn( name = "CBSA_CODE_ID", unique = true, nullable = false )
    private OwnersMarketCbsaLabel ownersMarketCbsaLabel;

    /** The min opportunities. */
    @Column( name = "MIN_OPPORTUNITIES", nullable = false )
    private int minOpportunities;
    
    /** The min opportunities. */
    @Column( name = "RR_THRESHOLD", nullable = false )
    private int rrThreshold;

    /** The level 1 max good. */
    @Column( name = "LEVEL1_MAX_GOOD", nullable = false )
    private int level1MaxGood;

    /** The level 1 max new. */
    @Column( name = "LEVEL1_MAX_NEW", nullable = false )
    private int level1MaxNew;

    /** The level 1 max average. */
    @Column( name = "LEVEL1_MAX_AVERAGE", nullable = false )
    private int level1MaxAverage;

    /** The level 2 max good. */
    @Column( name = "LEVEL2_MAX_GOOD", nullable = false )
    private int level2MaxGood;

    /** The max opportunities in selected stage. */
    @Column( name = "MAX_OPPS_IN_SELECTED_STAGE", nullable = false )
    private int maxOpportunitiesInSelectedStage;

    /** The threshold period. */
    @Column( name = "THRESHOLD_PERIOD", nullable = false )
    private int thresholdPeriod;

    /**
     * Gets the owners market cbsa label.
     *
     * @return the owners market cbsa label
     */
    public OwnersMarketCbsaLabel getOwnersMarketCbsaLabel() {
        return ownersMarketCbsaLabel;
    }

    /**
     * Sets the owners market cbsa label.
     *
     * @param ownersMarketCbsaLabel
     *            the new owners market cbsa label
     */
    public void setOwnersMarketCbsaLabel( final OwnersMarketCbsaLabel ownersMarketCbsaLabel ) {
        this.ownersMarketCbsaLabel = ownersMarketCbsaLabel;
    }

    /**
     * Gets the min opportunities.
     *
     * @return the min opportunities
     */
    public int getMinOpportunities() {
        return minOpportunities;
    }

    /**
     * Sets the min opportunities.
     *
     * @param minOpportunities
     *            the new min opportunities
     */
    public void setMinOpportunities( final int minOpportunities ) {
        this.minOpportunities = minOpportunities;
    }

    /**
     * Gets the rr threshold.
     *
     * @return the rr threshold
     */
    public int getRrThreshold() {
        return rrThreshold;
    }

    /**
     * Sets the rr threshold.
     *
     * @param rrThreshold
     *            the new rr threshold
     */
    public void setRrThreshold( final int rrThreshold ) {
        this.rrThreshold = rrThreshold;
    }

    /**
     * Gets the level 1 max good.
     *
     * @return the level 1 max good
     */
    public int getLevel1MaxGood() {
        return level1MaxGood;
    }

    /**
     * Sets the level 1 max good.
     *
     * @param level1MaxGood
     *            the new level 1 max good
     */
    public void setLevel1MaxGood( final int level1MaxGood ) {
        this.level1MaxGood = level1MaxGood;
    }

    /**
     * Gets the level 1 max new.
     *
     * @return the level 1 max new
     */
    public int getLevel1MaxNew() {
        return level1MaxNew;
    }

    /**
     * Sets the level 1 max new.
     *
     * @param level1MaxNew
     *            the new level 1 max new
     */
    public void setLevel1MaxNew( final int level1MaxNew ) {
        this.level1MaxNew = level1MaxNew;
    }

    /**
     * Gets the level 1 max average.
     *
     * @return the level 1 max average
     */
    public int getLevel1MaxAverage() {
        return level1MaxAverage;
    }

    /**
     * Sets the level 1 max average.
     *
     * @param level1MaxAverage
     *            the new level 1 max average
     */
    public void setLevel1MaxAverage( final int level1MaxAverage ) {
        this.level1MaxAverage = level1MaxAverage;
    }

    /**
     * Gets the level 2 max good.
     *
     * @return the level 2 max good
     */
    public int getLevel2MaxGood() {
        return level2MaxGood;
    }

    /**
     * Sets the level 2 max good.
     *
     * @param level2MaxGood
     *            the new level 2 max good
     */
    public void setLevel2MaxGood( final int level2MaxGood ) {
        this.level2MaxGood = level2MaxGood;
    }

    /**
     * Gets the max opportunities in selected stage.
     *
     * @return the max opportunities in selected stage
     */
    public int getMaxOpportunitiesInSelectedStage() {
        return maxOpportunitiesInSelectedStage;
    }

    /**
     * Sets the max opportunities in selected stage.
     *
     * @param maxOpportunitiesInSelectedStage
     *            the new max opportunities in selected stage
     */
    public void setMaxOpportunitiesInSelectedStage( final int maxOpportunitiesInSelectedStage ) {
        this.maxOpportunitiesInSelectedStage = maxOpportunitiesInSelectedStage;
    }

    /**
     * Gets the threshold period.
     *
     * @return the threshold period
     */
    public int getThresholdPeriod() {
        return thresholdPeriod;
    }

    /**
     * Sets the threshold period.
     *
     * @param thresholdPeriod
     *            the new threshold period
     */
    public void setThresholdPeriod( final int thresholdPeriod ) {
        this.thresholdPeriod = thresholdPeriod;
    }

}
