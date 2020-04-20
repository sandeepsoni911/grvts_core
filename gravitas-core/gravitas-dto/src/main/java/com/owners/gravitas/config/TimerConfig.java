package com.owners.gravitas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The Class TimerConfig.
 *
 * @author vishwanathm
 */
@Component
public class TimerConfig {

    /** The next date hour. */
    @Value( "${lead.marketing.email.startYourSearch.hour}" )
    private int startYourSearchHour;

    /** The next date minute. */
    @Value( "${lead.marketing.email.startYourSearch.minute}" )
    private int startYourSearchMinute;

    /** The start your search duration. */
    @Value( "${lead.marketing.email.startYourSearch.duration}" )
    private int startYourSearchDuration;

    /** The next date_days. */
    @Value( "${lead.marketing.email.rebate.duration}" )
    private String rebateDuration;

    /** The schedule tour duration. */
    @Value( "${lead.marketing.email.scheduleTour.duration}" )
    private String scheduleTourDuration;

    /** The marketing your agent duration. */
    @Value( "${lead.marketing.email.marketingYourAgent.duration}" )
    private String marketingYourAgentDuration;

    /** The final outreach duration. */
    @Value( "${lead.marketing.email.finalOutreach.duration}" )
    private String finalOutreachDuration;

    /**
     * Gets the start your search hour.
     *
     * @return the start your search hour
     */
    public int getStartYourSearchHour() {
        return startYourSearchHour;
    }

    /**
     * Gets the start your search minute.
     *
     * @return the start your search minute
     */
    public int getStartYourSearchMinute() {
        return startYourSearchMinute;
    }

    /**
     * Gets the start your search duration.
     *
     * @return the start your search duration
     */
    public int getStartYourSearchDuration() {
        return startYourSearchDuration;
    }

    /**
     * Gets the rebate duration.
     *
     * @return the rebate duration
     */
    public String getRebateDuration() {
        return rebateDuration;
    }

    /**
     * Gets the schedule tour duration.
     *
     * @return the schedule tour duration
     */
    public String getScheduleTourDuration() {
        return scheduleTourDuration;
    }

    /**
     * Gets the marketing your agent duration.
     *
     * @return the marketing your agent duration
     */
    public String getMarketingYourAgentDuration() {
        return marketingYourAgentDuration;
    }

    /**
     * Gets the final outreach duration.
     *
     * @return the final outreach duration
     */
    public String getFinalOutreachDuration() {
        return finalOutreachDuration;
    }

}
