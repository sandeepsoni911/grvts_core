package com.owners.gravitas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class CoShoppingConfig.
 *
 * @author gururasm
 */
@ManagedResource( objectName = "com.owners.gravitas:name=CoShoppingConfig" )
@Component
public class CoShoppingConfig {

    /** The buyer farming enabled. */
    @Value( "${coshopping.lead.push.enabled:false}" )
    private boolean coshoppingLeadPushEnabled;

    /** The coshopping lead update enabled. */
    @Value( "${coshopping.lead.update.enabled:false}" )
    private boolean coshoppingLeadUpdateEnabled;

    /** The schedule tour meetings enabled. */
    @Value( "${schedule.tour.meetings.enabled:false}" )
    private boolean enableScheduleTourMeetings;

    /** The schedule tour meetings enabled. */
    @Value( "${coshopping.field.agent.scheduled.tour.enabled:false}" )
    private boolean scheduledTourByFieldAgentEnabled;

    /** The schedule tour meetings enabled. */
    @Value( "${schedule.buyer.tour.enabled:false}" )
    private boolean enableBuyerOpportunityScheduleTour;

    /** The property writer. */
    @Autowired
    private PropertyWriter propertyWriter;

    /**
     * Checks if is coshopping lead push enabled.
     *
     * @return the coshoppingLeadPushEnabled
     */
    @ManagedAttribute
    public boolean isCoshoppingLeadPushEnabled() {
        return coshoppingLeadPushEnabled;
    }

    /**
     * Sets the coshopping lead push enabled.
     *
     * @param coshoppingLeadPushEnabled
     *            the coshoppingLeadPushEnabled to set
     */
    @ManagedAttribute
    public void setCoshoppingLeadPushEnabled( final boolean coshoppingLeadPushEnabled ) {
        this.coshoppingLeadPushEnabled = coshoppingLeadPushEnabled;
        propertyWriter.saveJmxProperty( "coshopping.lead.push.enabled", coshoppingLeadPushEnabled );
    }

    /**
     * Checks if schedule tour meetings enabled.
     *
     * @return the enableScheduleTourMeetings
     */
    @ManagedAttribute
    public boolean isEnableScheduleTourMeetings() {
        return enableScheduleTourMeetings;
    }

    /**
     * sets the flag to enable schedule tour Meetings.
     *
     * @param enableScheduleTourMeetings
     *            the enableScheduleTourMeetings to set
     */
    @ManagedAttribute
    public void setEnableScheduleTourMeetings( final boolean enableScheduleTourMeetings ) {
        this.enableScheduleTourMeetings = enableScheduleTourMeetings;
        propertyWriter.saveJmxProperty( "schedule.tour.meetings.enabled", enableScheduleTourMeetings );
    }

    /**
     * Checks if is scheduled tour by field agent enabled.
     *
     * @return true, if is scheduled tour by field agent enabled
     */
    @ManagedAttribute
    public boolean isScheduledTourByFieldAgentEnabled() {
        return scheduledTourByFieldAgentEnabled;
    }

    /**
     * Sets the scheduled tour by field agent enabled.
     *
     * @param scheduledTourByFieldAgentEnabled
     *            the new scheduled tour by field agent enabled
     */
    @ManagedAttribute
    public void setScheduledTourByFieldAgentEnabled( final boolean scheduledTourByFieldAgentEnabled ) {
        this.scheduledTourByFieldAgentEnabled = scheduledTourByFieldAgentEnabled;
        propertyWriter.saveJmxProperty( "coshopping.field.agent.scheduled.tour.enabled",
                scheduledTourByFieldAgentEnabled );
    }

    /**
     * Checks if schedule tour meetings enabled
     * 
     * @return the enableScheduleTourMeetings
     */
    @ManagedAttribute
    public boolean isEnableBuyerOpportunityScheduleTour() {
        return enableBuyerOpportunityScheduleTour;
    }

    /**
     * sets the flag to enable schedule tour Meetings
     * 
     * @param enableScheduleTourMeetings
     *            the enableScheduleTourMeetings to set
     */
    @ManagedAttribute
    public void setEnableBuyerOpportunityScheduleTour( final boolean enableBuyerOpportunityScheduleTour ) {
        this.enableBuyerOpportunityScheduleTour = enableBuyerOpportunityScheduleTour;
        propertyWriter.saveJmxProperty( "schedule.buyer.tour.enabled", enableBuyerOpportunityScheduleTour );
    }

    /**
     * @return the coshoppingLeadUpdateEnabled
     */
    @ManagedAttribute
    public boolean isCoshoppingLeadUpdateEnabled() {
        return coshoppingLeadUpdateEnabled;
    }

    /**
     * @param coshoppingLeadUpdateEnabled
     *            the coshoppingLeadUpdateEnabled to set
     */
    @ManagedAttribute
    public void setCoshoppingLeadUpdateEnabled( final boolean coshoppingLeadUpdateEnabled ) {
        this.coshoppingLeadUpdateEnabled = coshoppingLeadUpdateEnabled;
        propertyWriter.saveJmxProperty( "coshopping.lead.update.enabled", coshoppingLeadUpdateEnabled );
    }

}
