package com.owners.gravitas.dataprep.dto;

/**
 * The Class MarketDto.
 * 
 * @author ankusht
 */
public class MarketDto {

    /** The cbsa. */
    private String cbsa;

    /** The min opportunities. */
    private int minOpportunities;

    /** The rr threshold. */
    private int rrThreshold;

    /** The level 1 max good. */
    private int level1MaxGood;

    /** The level 1 max new. */
    private int level1MaxNew;

    /** The level 1 max average. */
    private int level1MaxAverage;

    /** The level 2 max good. */
    private int level2MaxGood;

    /** The max opps in selected stage. */
    private int maxOppsInSelectedStage;

    /** The threshold period. */
    private int thresholdPeriod;

    /**
     * Gets the cbsa.
     *
     * @return the cbsa
     */
    public String getCbsa() {
        return cbsa;
    }

    /**
     * Sets the cbsa.
     *
     * @param cbsa
     *            the new cbsa
     */
    public void setCbsa( final String cbsa ) {
        this.cbsa = cbsa;
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
     * Gets the max opps in selected stage.
     *
     * @return the max opps in selected stage
     */
    public int getMaxOppsInSelectedStage() {
        return maxOppsInSelectedStage;
    }

    /**
     * Sets the max opps in selected stage.
     *
     * @param maxOppsInSelectedStage
     *            the new max opps in selected stage
     */
    public void setMaxOppsInSelectedStage( final int maxOppsInSelectedStage ) {
        this.maxOppsInSelectedStage = maxOppsInSelectedStage;
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

    /**
     * Instantiates a new market dto.
     */
    public MarketDto() {
        super();
    }

    /**
     * Instantiates a new market dto.
     *
     * @param cbsa
     *            the cbsa
     * @param minOpportunities
     *            the min opportunities
     * @param rrThreshold
     *            the rr threshold
     * @param level1MaxGood
     *            the level 1 max good
     * @param level1MaxNew
     *            the level 1 max new
     * @param level1MaxAverage
     *            the level 1 max average
     * @param level2MaxGood
     *            the level 2 max good
     * @param maxOppsInSelectedStage
     *            the max opps in selected stage
     * @param thresholdPeriod
     *            the threshold period
     */
    public MarketDto( final String cbsa, final int minOpportunities, final int rrThreshold, final int level1MaxGood,
            final int level1MaxNew, final int level1MaxAverage, final int level2MaxGood,
            final int maxOppsInSelectedStage, final int thresholdPeriod ) {
        super();
        this.cbsa = cbsa;
        this.minOpportunities = minOpportunities;
        this.rrThreshold = rrThreshold;
        this.level1MaxGood = level1MaxGood;
        this.level1MaxNew = level1MaxNew;
        this.level1MaxAverage = level1MaxAverage;
        this.level2MaxGood = level2MaxGood;
        this.maxOppsInSelectedStage = maxOppsInSelectedStage;
        this.thresholdPeriod = thresholdPeriod;
    }
}
