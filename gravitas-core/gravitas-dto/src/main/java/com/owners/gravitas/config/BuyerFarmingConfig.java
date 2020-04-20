package com.owners.gravitas.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.owners.gravitas.util.PropertiesUtil;
import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class BuyerFarmingConfig.
 *
 * @author vishwanathm
 */
@ManagedResource( objectName = "com.owners.gravitas:name=BuyerFarmingConfig" )
@Component
public class BuyerFarmingConfig {

    /** The buyer farming enabled. */
    @Value( "${buyer.farming.enabled:false}" )
    private boolean buyerFarmingEnabled;

    /** The marathon test enabled. */
    @Value( "${buyer.autoRegistration.enabled: false}" )
    private boolean buyerAutoRegistrationEnabled;

    /** The buyer save search enabled. */
    @Value( "${buyer.farming.autoSaveSearch.enabled: false}" )
    private boolean buyerSaveSearchEnabled;

    /** The buyer inside sales farming enabled. */
    @Value( "${buyer.insideSales.farming.enalbled: false}" )
    private boolean buyerInsideSalesFarmingEnabled;

    /** The buyer field agent farming enabled. */
    @Value( "${buyer.fieldAgent.farming.enabled:false}" )
    private boolean buyerFieldAgentFarmingEnabled;
    
    /** The buyer field agent farming enabled. */
    @Value( "${buyer.web.activity.repeatpdpviewed.enabled:false}" )
    private boolean buyerWebActivityRepeatPdpViewedEnabled;
    
    /** The buyer field agent farming enabled. */
    @Value( "${buyer.web.activity.engagedtos.enabled:false}" )
    private boolean buyerWebActivityEngagedTosEnabled;
    
    /** The buyer field agent farming enabled. */
    @Value( "${buyer.web.activity.unengaged30.enabled:false}" )
    private boolean buyerWebActivityUnengaged30Enabled;

    /** The buyer auto registration email str. */
    @Value( "${buyer.autoRegistration.emails}" )
    private String buyerAutoRegistrationEmailStr;

    /** The lead sourcefilter1 str. */
    @Value( "${buyer.autoRegistration.leadSource.filter1}" )
    private String leadSourcefilter1Str;

    /** The lead sourcefilter2 str. */
    @Value( "${buyer.autoRegistration.leadSource.filter2}" )
    private String leadSourcefilter2Str;

    /** The buyer first followup email enabled. */
    @Value( "${buyer.first.followup.email.enabled:false}" )
    private boolean buyerFirstFollowupEmailEnabled;

    /** The buyer second followup email enabled. */
    @Value( "${buyer.second.followup.email.enabled:false}" )
    private boolean buyerSecondFollowupEmailEnabled;

    /** The Constant longTermLeadGracePeriod. */
    @Value( "${buyer.farming.long.term.lead.grace.period}" )
    private Integer longTermLeadGracePeriod;

    /** The favorited activity. */
    @Value( "${buyer.web.activity1:FAVORITED}" )
    private String favoritedActivity;

    @Value( "${buyer.web.activity2:REPEAT_PDP_VIEWED}" )
    private String repeatPdpViewedActivity;
    
    /** The pdp viewed activity. */
    @Value( "${buyer.web.activity3:PDP_VIEWED}" )
    private String pdpViewedActivity;

    /** The pdp shared activity. */
    @Value( "${buyer.web.activity4:PDP_SHARED}" )
    private String pdpSharedActivity;

    /** The tour request abandoned activity. */
    @Value( "${buyer.web.activity5:TOUR_REQUEST_ABANDONED}" )
    private String tourRequestAbandonedActivity;

    /** The tour saved activity. */
    @Value( "${buyer.web.activity6:TOUR_SAVED}" )
    private String tourSavedActivity;

    /** The undecided pdp viewed activity. */
    @Value( "${buyer.web.activity7:PDP_VIEWED_INDECISIVE}" )
    private String pdpViewedIndecisiveActivity;
    
    @Value( "${buyer.web.activity8:ENGAGED_TOS}" )
    private String engagedTosActivity;
    
    @Value( "${buyer.web.activity9:UNDECIDED_PDP_VIEWED}" )
    private String undecidedPdpViewedActivity;
    
    @Value( "${buyer.web.activity10:UNENGAGED_30}" )
    private String unengaged30Activity;

    @Value( "${buyer.web.activity.followup.enabled:false}" )
    private boolean webActivityFollowUpEnabled;
    
    /** The property writer. */
    @Autowired
    private PropertyWriter propertyWriter;
    
    /** The farm long term buyer states. */
    @Value( "${farm.long.term.buyer.states}" )
    private String farmLongTermBuyerStates;

    /**
     * Checks if is buyer auto registration enabled.
     *
     * @return true, if is buyer auto registration enabled
     */
    @ManagedAttribute
    public boolean isBuyerAutoRegistrationEnabled() {
        return buyerAutoRegistrationEnabled;
    }

    /**
     * Sets the buyer auto registration enabled.
     *
     * @param buyerAutoRegistrationEnabled
     *            the new buyer auto registration enabled
     */
    @ManagedAttribute
    public void setBuyerAutoRegistrationEnabled( final boolean buyerAutoRegistrationEnabled ) {
        this.buyerAutoRegistrationEnabled = buyerAutoRegistrationEnabled;
        propertyWriter.saveJmxProperty( "buyer.autoRegistration.enabled", buyerAutoRegistrationEnabled );
    }

    /**
     * Gets the buyer auto registration email str.
     *
     * @return the buyer auto registration email str
     */
    @ManagedAttribute
    public String getBuyerAutoRegistrationEmailStr() {
        return buyerAutoRegistrationEmailStr;
    }

    /**
     * Sets the buyer auto registration email str.
     *
     * @param buyerAutoRegistrationEmailStr
     *            the new buyer auto registration email str
     */
    @ManagedAttribute
    public void setBuyerAutoRegistrationEmailStr( final String buyerAutoRegistrationEmailStr ) {
        this.buyerAutoRegistrationEmailStr = buyerAutoRegistrationEmailStr;
        propertyWriter.saveJmxProperty( "buyer.autoRegistration.emails", buyerAutoRegistrationEmailStr );
    }

    /**
     * Gets the lead sourcefilter1 str.
     *
     * @return the lead sourcefilter1 str
     */
    public String getLeadSourcefilter1Str() {
        return leadSourcefilter1Str;
    }

    /**
     * Sets the lead sourcefilter1 str.
     *
     * @param leadSourcefilter1Str
     *            the new lead sourcefilter1 str
     */
    public void setLeadSourcefilter1Str( final String leadSourcefilter1Str ) {
        this.leadSourcefilter1Str = leadSourcefilter1Str;
        propertyWriter.saveJmxProperty( "buyer.autoRegistration.leadSource.filter1", leadSourcefilter1Str );
    }

    /**
     * Gets the lead sourcefilter2 str.
     *
     * @return the lead sourcefilter2 str
     */
    public String getLeadSourcefilter2Str() {
        return leadSourcefilter2Str;
    }

    /**
     * Sets the lead sourcefilter2 str.
     *
     * @param leadSourcefilter2Str
     *            the new lead sourcefilter2 str
     */
    public void setLeadSourcefilter2Str( final String leadSourcefilter2Str ) {
        this.leadSourcefilter2Str = leadSourcefilter2Str;
        propertyWriter.saveJmxProperty( "buyer.autoRegistration.leadSource.filter2", leadSourcefilter2Str );
    }

    /**
     * Checks if is buyer farming enabled.
     *
     * @return true, if is buyer farming enabled
     */
    @ManagedAttribute
    public boolean isBuyerFarmingEnabled() {
        return buyerFarmingEnabled;
    }

    /**
     * Sets the buyer farming enabled.
     *
     * @param buyerFarmingEnabled
     *            the new buyer farming enabled
     */
    @ManagedAttribute
    public void setBuyerFarmingEnabled( final boolean buyerFarmingEnabled ) {
        this.buyerFarmingEnabled = buyerFarmingEnabled;
        propertyWriter.saveJmxProperty( "buyer.farming.enabled", buyerFarmingEnabled );
    }

    /**
     * Checks if is buyer save search enabled.
     *
     * @return true, if is buyer save search enabled
     */
    @ManagedAttribute
    public boolean isBuyerSaveSearchEnabled() {
        return buyerSaveSearchEnabled;
    }

    /**
     * Sets the buyer save search enabled.
     *
     * @param buyerSaveSearchEnabled
     *            the new buyer save search enabled
     */
    @ManagedAttribute
    public void setBuyerSaveSearchEnabled( final boolean buyerSaveSearchEnabled ) {
        this.buyerSaveSearchEnabled = buyerSaveSearchEnabled;
        propertyWriter.saveJmxProperty( "buyer.farming.autoSaveSearch.enabled", buyerSaveSearchEnabled );
    }

    /**
     * Checks if is buyer inside sales farming enabled.
     *
     * @return true, if is buyer inside sales farming enabled
     */
    @ManagedAttribute
    public boolean isBuyerInsideSalesFarmingEnabled() {
        return buyerInsideSalesFarmingEnabled;
    }

    /**
     * Sets the buyer inside sales farming enabled.
     *
     * @param buyerInsideSalesFarmingEnabled
     *            the new buyer inside sales farming enabled
     */
    @ManagedAttribute
    public void setBuyerInsideSalesFarmingEnabled( final boolean buyerInsideSalesFarmingEnabled ) {
        this.buyerInsideSalesFarmingEnabled = buyerInsideSalesFarmingEnabled;
        propertyWriter.saveJmxProperty( "buyer.insideSales.farming.enalbled", buyerInsideSalesFarmingEnabled );
    }

    /**
     * Checks if is buyer field agent farming enabled.
     *
     * @return true, if is buyer field agent farming enabled
     */
    @ManagedAttribute
    public boolean isBuyerFieldAgentFarmingEnabled() {
        return buyerFieldAgentFarmingEnabled;
    }

    /**
     * Sets the buyer field agent farming enabled.
     *
     * @param buyerFieldAgentFarmingEnabled
     *            the new buyer field agent farming enabled
     */
    @ManagedAttribute
    public void setBuyerFieldAgentFarmingEnabled( final boolean buyerFieldAgentFarmingEnabled ) {
        this.buyerFieldAgentFarmingEnabled = buyerFieldAgentFarmingEnabled;
        propertyWriter.saveJmxProperty( "buyer.fieldAgent.farming.enabled", buyerFieldAgentFarmingEnabled );
    }
       
    /**
     * @return the buyerWebActivityRepeatPdpViewedEnabled
     */
    @ManagedAttribute
    public boolean isBuyerWebActivityRepeatPdpViewedEnabled() {
        return buyerWebActivityRepeatPdpViewedEnabled;
    }

    /**
     * @param buyerWebActivityRepeatPdpViewedEnabled
     *            the buyerWebActivityRepeatPdpViewedEnabled to set
     */
    @ManagedAttribute
    public void setBuyerWebActivityRepeatPdpViewedEnabled( boolean buyerWebActivityRepeatPdpViewedEnabled ) {
        this.buyerWebActivityRepeatPdpViewedEnabled = buyerWebActivityRepeatPdpViewedEnabled;
        propertyWriter.saveJmxProperty( "buyer.web.activity.repeatpdpviewed.enabled", buyerWebActivityRepeatPdpViewedEnabled );
    }

    /**
     * @return the buyerWebActivityEngagedTosEnabled
     */
    @ManagedAttribute
    public boolean isBuyerWebActivityEngagedTosEnabled() {
        return buyerWebActivityEngagedTosEnabled;
    }

    /**
     * @param buyerWebActivityEngagedTosEnabled
     *            the buyerWebActivityEngagedTosEnabled to set
     */
    @ManagedAttribute
    public void setBuyerWebActivityEngagedTosEnabled( boolean buyerWebActivityEngagedTosEnabled ) {
        this.buyerWebActivityEngagedTosEnabled = buyerWebActivityEngagedTosEnabled;
        propertyWriter.saveJmxProperty( "buyer.web.activity.engagedtos.enabled", buyerWebActivityEngagedTosEnabled );
    }

    /**
     * @return the buyerWebActivityUnengaged30Enabled
     */
    @ManagedAttribute
    public boolean isBuyerWebActivityUnengaged30Enabled() {
        return buyerWebActivityUnengaged30Enabled;
    }

    /**
     * @param buyerWebActivityUnengaged30Enabled
     *            the buyerWebActivityUnengaged30Enabled to set
     */
    @ManagedAttribute
    public void setBuyerWebActivityUnengaged30Enabled( boolean buyerWebActivityUnengaged30Enabled ) {
        this.buyerWebActivityUnengaged30Enabled = buyerWebActivityUnengaged30Enabled;
        propertyWriter.saveJmxProperty( "buyer.web.activity.unengaged30.enabled", buyerWebActivityUnengaged30Enabled );
    }

    /**
     * Checks if is buyer first followup email enabled.
     *
     * @return true, if is buyer first followup email enabled
     */
    @ManagedAttribute
    public boolean isBuyerFirstFollowupEmailEnabled() {
        return buyerFirstFollowupEmailEnabled;
    }

    /**
     * Sets the buyer first followup email enabled.
     *
     * @param buyerFirstFollowupEmailEnabled
     *            the new buyer first followup email enabled
     */
    @ManagedAttribute
    public void setBuyerFirstFollowupEmailEnabled( final boolean buyerFirstFollowupEmailEnabled ) {
        this.buyerFirstFollowupEmailEnabled = buyerFirstFollowupEmailEnabled;
        propertyWriter.saveJmxProperty( "buyer.first.followup.email.enabled", buyerFirstFollowupEmailEnabled );
    }

    /**
     * Checks if is buyer second followup email enabled.
     *
     * @return true, if is buyer second followup email enabled
     */
    @ManagedAttribute
    public boolean isBuyerSecondFollowupEmailEnabled() {
        return buyerSecondFollowupEmailEnabled;
    }

    /**
     * Sets the buyer second followup email enabled.
     *
     * @param buyerSecondFollowupEmailEnabled
     *            the new buyer second followup email enabled
     */
    @ManagedAttribute
    public void setBuyerSecondFollowupEmailEnabled( final boolean buyerSecondFollowupEmailEnabled ) {
        this.buyerSecondFollowupEmailEnabled = buyerSecondFollowupEmailEnabled;
        propertyWriter.saveJmxProperty( "buyer.second.followup.email.enabled", buyerSecondFollowupEmailEnabled );
    }

    /**
     * Gets the long term lead grace period.
     *
     * @return the long term lead grace period
     */
    @ManagedAttribute
    public Integer getLongTermLeadGracePeriod() {
        return longTermLeadGracePeriod;
    }

    /**
     * Sets the long term lead grace period.
     *
     * @param longTermLeadGracePeriod
     *            the new long term lead grace period
     */
    @ManagedAttribute
    public void setLongTermLeadGracePeriod( final Integer longTermLeadGracePeriod ) {
        this.longTermLeadGracePeriod = longTermLeadGracePeriod;
        propertyWriter.saveJmxProperty( "buyer.farming.long.term.lead.grace.period", longTermLeadGracePeriod );
    }

    /**
     * Gets the favorited activity.
     *
     * @return the favorited activity
     */
    @ManagedAttribute
    public String getFavoritedActivity() {
        return favoritedActivity;
    }

    /**
     * Sets the favorited activity.
     *
     * @param favoritedActivity
     *            the new favorited activity
     */
    @ManagedAttribute
    public void setFavoritedActivity( final String favoritedActivity ) {
        this.favoritedActivity = favoritedActivity;
        propertyWriter.saveJmxProperty( "buyer.web.activity1", favoritedActivity );
        updatePropertiesMap( "buyer.web.activity1", favoritedActivity );
    }

    /**
     * Gets the pdp viewed activity.
     *
     * @return the pdp viewed activity
     */
    @ManagedAttribute
    public String getPdpViewedActivity() {
        return pdpViewedActivity;
    }

    /**
     * Sets the pdp viewed activity.
     *
     * @param pdpViewedActivity
     *            the new pdp viewed activity
     */
    @ManagedAttribute
    public void setPdpViewedActivity( final String pdpViewedActivity ) {
        this.pdpViewedActivity = pdpViewedActivity;
        propertyWriter.saveJmxProperty( "buyer.web.activity3", pdpViewedActivity );
        updatePropertiesMap( "buyer.web.activity3", pdpViewedActivity );
    }

    /**
     * Gets the pdp shared activity.
     *
     * @return the pdp shared activity
     */
    @ManagedAttribute
    public String getPdpSharedActivity() {
        return pdpSharedActivity;
    }

    /**
     * Sets the pdp shared activity.
     *
     * @param pdpSharedActivity
     *            the new pdp shared activity
     */
    @ManagedAttribute
    public void setPdpSharedActivity( final String pdpSharedActivity ) {
        this.pdpSharedActivity = pdpSharedActivity;
        propertyWriter.saveJmxProperty( "buyer.web.activity4", pdpSharedActivity );
        updatePropertiesMap( "buyer.web.activity4", pdpSharedActivity );
    }

    /**
     * Gets the tour request abandoned activity.
     *
     * @return the tour request abandoned activity
     */
    @ManagedAttribute
    public String getTourRequestAbandonedActivity() {
        return tourRequestAbandonedActivity;
    }

    /**
     * Sets the tour request abandoned activity.
     *
     * @param tourRequestAbandonedActivity
     *            the new tour request abandoned activity
     */
    @ManagedAttribute
    public void setTourRequestAbandonedActivity( final String tourRequestAbandonedActivity ) {
        this.tourRequestAbandonedActivity = tourRequestAbandonedActivity;
        propertyWriter.saveJmxProperty( "buyer.web.activity5", tourRequestAbandonedActivity );
        updatePropertiesMap( "buyer.web.activity5", tourRequestAbandonedActivity );
    }

    /**
     * Gets the tour saved activity.
     *
     * @return the tour saved activity
     */
    @ManagedAttribute
    public String getTourSavedActivity() {
        return tourSavedActivity;
    }

    /**
     * Sets the tour saved activity.
     *
     * @param tourSavedActivity
     *            the new tour saved activity
     */
    @ManagedAttribute
    public void setTourSavedActivity( final String tourSavedActivity ) {
        this.tourSavedActivity = tourSavedActivity;
        propertyWriter.saveJmxProperty( "buyer.web.activity6", tourSavedActivity );
        updatePropertiesMap( "buyer.web.activity6", tourSavedActivity );
    }
    
    /**
     * @return the pdpViewedIndecisiveActivity
     */
    @ManagedAttribute
    public String getPdpViewedIndecisiveActivity() {
        return pdpViewedIndecisiveActivity;
    }

    /**
     * @param pdpViewedIndecisiveActivity
     *            the pdpViewedIndecisiveActivity to set
     */
    @ManagedAttribute
    public void setPdpViewedIndecisiveActivity( String pdpViewedIndecisiveActivity ) {
        this.pdpViewedIndecisiveActivity = pdpViewedIndecisiveActivity;
        propertyWriter.saveJmxProperty( "buyer.web.activity7", pdpViewedIndecisiveActivity );
        updatePropertiesMap( "buyer.web.activity7", pdpViewedIndecisiveActivity );
    }

    /**
     * @return the repeatPdpViewed
     */
    @ManagedAttribute
    public String getRepeatPdpViewedActivity() {
        return repeatPdpViewedActivity;
    }

    /**
     * @param repeatPdpViewed
     *            the repeatPdpViewed to set
     */
    @ManagedAttribute
    public void setRepeatPdpViewedActivity( String repeatPdpViewedActivity ) {
        this.repeatPdpViewedActivity = repeatPdpViewedActivity;
        propertyWriter.saveJmxProperty( "buyer.web.activity2", repeatPdpViewedActivity );
        updatePropertiesMap( "buyer.web.activity2", repeatPdpViewedActivity );
    }

    /**
     * @return the engagedTos
     */
    @ManagedAttribute
    public String getEngagedTosActivity() {
        return engagedTosActivity;
    }

    /**
     * @param engagedTos
     *            the engagedTos to set
     */
    @ManagedAttribute
    public void setEngagedTosActivity( String engagedTosActivity ) {
        this.engagedTosActivity = engagedTosActivity;
        propertyWriter.saveJmxProperty( "buyer.web.activity8", engagedTosActivity );
        updatePropertiesMap( "buyer.web.activity8", engagedTosActivity );
    }

    /**
     * @return the undecidedPdpViewed
     */
    @ManagedAttribute
    public String getUndecidedPdpViewedActivity() {
        return undecidedPdpViewedActivity;
    }

    /**
     * @param undecidedPdpViewed
     *            the undecidedPdpViewed to set
     */
    @ManagedAttribute
    public void setUndecidedPdpViewedActivity( String undecidedPdpViewedActivity ) {
        this.undecidedPdpViewedActivity = undecidedPdpViewedActivity;
        propertyWriter.saveJmxProperty( "buyer.web.activity9", undecidedPdpViewedActivity );
        updatePropertiesMap( "buyer.web.activity9", undecidedPdpViewedActivity );
    }

    /**
     * @return the unengaged30
     */
    @ManagedAttribute
    public String getUnengaged30Activity() {
        return unengaged30Activity;
    }

    /**
     * @param unengaged30
     *            the unengaged30 to set
     */
    @ManagedAttribute
    public void setUnengaged30Activity( String unengaged30Activity ) {
        this.unengaged30Activity = unengaged30Activity;
        propertyWriter.saveJmxProperty( "buyer.web.activity10", unengaged30Activity );
        updatePropertiesMap( "buyer.web.activity10", unengaged30Activity );
    }

    /**
     * @return the propertyWriter
     */
    public PropertyWriter getPropertyWriter() {
        return propertyWriter;
    }

    /**
     * @param propertyWriter the propertyWriter to set
     */
    public void setPropertyWriter( PropertyWriter propertyWriter ) {
        this.propertyWriter = propertyWriter;
    }

    /**
     * Checks if is web activity follow up enabled.
     *
     * @return true, if is web activity follow up enabled
     */
    @ManagedAttribute
    public boolean isWebActivityFollowUpEnabled() {
        return webActivityFollowUpEnabled;
    }

    /**
     * Sets the web activity follow up enabled.
     *
     * @param webActivityFollowUpEnabled
     *            the new web activity follow up enabled
     */
    @ManagedAttribute
    public void setWebActivityFollowUpEnabled( final boolean webActivityFollowUpEnabled ) {
        this.webActivityFollowUpEnabled = webActivityFollowUpEnabled;
        propertyWriter.saveJmxProperty( "buyer.web.activity.followup.enabled", webActivityFollowUpEnabled );
    }

    /**
     * Update properties map.
     *
     * @param key
     *            the key
     * @param value
     *            the value
     */
    private void updatePropertiesMap( final String key, final String value ) {
        final Map< String, String > propertiesMap = PropertiesUtil.getPropertiesMap();
        propertiesMap.put( key, value );
    }

	/**
	 * @return the farmLongTermBuyerStates
	 */
    @ManagedAttribute
	public String getFarmLongTermBuyerStates() {
		return farmLongTermBuyerStates;
	}

	/**
	 * @param farmLongTermBuyerStates the farmLongTermBuyerStates to set
	 */
    @ManagedAttribute
	public void setFarmLongTermBuyerStates(String farmLongTermBuyerStates) {
		this.farmLongTermBuyerStates = farmLongTermBuyerStates;
        propertyWriter.saveJmxProperty( "farm.long.term.buyer.states", farmLongTermBuyerStates );

	}
}