package com.owners.gravitas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class LeadBusinessConfig.
 */
@ManagedResource( objectName = "com.owners.gravitas:name=LeadBusinessConfig" )
@Component
public class LeadBusinessConfig {

    /** The lead referral enabled. */
    @Value( "${lead.referral.enabled:false}" )
    private boolean leadReferralEnabled;

    /** The office after hour enabled. */
    @Value( "${buyer.notification.officeAfterHour.enabled}" )
    private boolean officeAfterHourEnabled;

    /** The office hour start time. */
    @Value( "${buyer.notification.officeAfterHour.start}" )
    private int officeAfterHourStart;

    /** The office mins start. */
    @Value( "${buyer.notification.officeAfterMins.start}" )
    private int officeAfterMinsStart;

    /** The office hour end time. */
    @Value( "${buyer.notification.officeAfterHour.end}" )
    private int officeAfterHourEnd;

    /** The office mins end. */
    @Value( "${buyer.notification.officeAfterMins.end}" )
    private int officeAfterMinsEnd;
    
    @Value( "${buyer.tour.offset.hour:3}" )
    private int buyerTourOffsetHour;
    
    @Value( "${buyer.tour.asap.enabled:false}" )
    private boolean buyerTourAsapEnabled;

    /** The shift hour start state1. */
    @Value( "${shift.lead.buyer.start.hour.state1}" )
    private int shiftHourStartState1;

    /** The shift hour end state1. */
    @Value( "${shift.lead.buyer.end.hour.state1}" )
    private int shiftHourEndState1;

    /** The shift hour start state2. */
    @Value( "${shift.lead.buyer.start.hour.state2}" )
    private int shiftHourStartState2;

    /** The shift hour end state2. */
    @Value( "${shift.lead.buyer.end.hour.state2}" )
    private int shiftHourEndState2;

    /** The shift hour start state3. */
    @Value( "${shift.lead.buyer.start.hour.state3}" )
    private int shiftHourStartState3;

    /** The shift hour end state3. */
    @Value( "${shift.lead.buyer.end.hour.state3}" )
    private int shiftHourEndState3;

    /** The shift hours states1. */
    @Value( "${shift.lead.buyer.state1}" )
    private String shiftHoursStates1;

    /** The shift hours states2. */
    @Value( "${shift.lead.buyer.state2}" )
    private String shiftHoursStates2;

    /** The shift hours states3. */
    @Value( "${shift.lead.buyer.state3}" )
    private String shiftHoursStates3;

    /** The time zone id. */
    @Value( "${buyer.notification.officeAfterHour.timeZone}" )
    private String timeZoneId;

    /** The after hours states. */
    @Value( "${after.hours.allowed.states}" )
    private String afterHoursStates;

    /** The owner states. */
    @Value( "${owners.states.list}" )
    private String ownerStates;

    /** The referral email notification enabled. */
    @Value( "${referral.email.notification:true}" )
    private boolean referralEmailNotificationEnabled;

    /** The referral excluded states. */
    @Value( "${referral.excluded.states}" )
    private String referralExcludedStates;

    /** The salesforce api username. */
    @Value( value = "${salesforce.api.user.id}" )
    protected String sfApiUserId;
    
    /** The start hour for zillow phone lead */
    @Value( "${zillow.phone.lead.start.hour:48}" )
    private int zillowPhoneLeadStartHour;
    
    /** The end hour for zillow phone lead */
    @Value( "${zillow.phone.lead.end.hour:0}" )
    private int zillowPhoneLeadEndHour;
    
    /** The after hours states. */
    @Value( "${before.hour.syncUp.lead}" )
    private int beforeHourSyncUpLead;

    /** The property writer. */
    @Autowired
    private PropertyWriter propertyWriter;
    
    /** The flag for mortgage lead notification */
    //@Value( "${mortgage.lead.notification:true}" )
    private boolean isMortgageLeadNotificationEnabled;

    /**
     * Checks if is office after hour enabled.
     *
     * @return true, if is office after hour enabled
     */
    @ManagedAttribute
    public boolean isOfficeAfterHourEnabled() {
        return officeAfterHourEnabled;
    }

    /**
     * Sets the office after hour enabled.
     *
     * @param officeAfterHourEnabled
     *            the new office after hour enabled
     */
    @ManagedAttribute
    public void setOfficeAfterHourEnabled( final boolean officeAfterHourEnabled ) {
        this.officeAfterHourEnabled = officeAfterHourEnabled;
        propertyWriter.saveJmxProperty( "buyer.notification.officeAfterHour.enabled", officeAfterHourEnabled );
    }

    /**
     * Gets the after hours states.
     *
     * @return the after hours states
     */
    @ManagedAttribute
    public String getAfterHoursStates() {
        return afterHoursStates;
    }

    /**
     * Sets the after hours states.
     *
     * @param afterHoursStates
     *            the new after hours states
     */
    @ManagedAttribute
    public void setAfterHoursStates( final String afterHoursStates ) {
        this.afterHoursStates = afterHoursStates;
        propertyWriter.saveJmxProperty( "after.hours.allowed.states", afterHoursStates );
    }

    /**
     * Checks if is lead referral enabled.
     *
     * @return true, if is lead referral enabled
     */
    @ManagedAttribute
    public boolean isLeadReferralEnabled() {
        return leadReferralEnabled;
    }

    /**
     * Sets the lead referral enabled.
     *
     * @param leadReferralEnabled
     *            the new lead referral enabled
     */
    @ManagedAttribute( description = "Enable/Disable lead referral functionality" )
    public void setLeadReferralEnabled( final boolean leadReferralEnabled ) {
        this.leadReferralEnabled = leadReferralEnabled;
        propertyWriter.saveJmxProperty( "lead.referral.enabled", leadReferralEnabled );
    }

    /**
     * Checks if is referral email notification enabled.
     *
     * @return true, if is referral email notification enabled
     */
    @ManagedAttribute
    public boolean isReferralEmailNotificationEnabled() {
        return referralEmailNotificationEnabled;
    }

    /**
     * Sets the referral email notification enabled.
     *
     * @param referralEmailNotificationEnabled
     *            the new referral email notification enabled
     */
    @ManagedAttribute
    public void setReferralEmailNotificationEnabled( final boolean referralEmailNotificationEnabled ) {
        this.referralEmailNotificationEnabled = referralEmailNotificationEnabled;
        propertyWriter.saveJmxProperty( "referral.email.notification", referralEmailNotificationEnabled );
    }

    /**
     * Gets the office after hour start.
     *
     * @return the office after hour start
     */
    public int getOfficeAfterHourStart() {
        return officeAfterHourStart;
    }

    /**
     * Sets the office after hour start.
     *
     * @param officeAfterHourStart
     *            the new office after hour start
     */
    public void setOfficeAfterHourStart( final int officeAfterHourStart ) {
        this.officeAfterHourStart = officeAfterHourStart;
    }

    /**
     * Gets the office after mins start.
     *
     * @return the office after mins start
     */
    public int getOfficeAfterMinsStart() {
        return officeAfterMinsStart;
    }

    /**
     * Sets the office after mins start.
     *
     * @param officeAfterMinsStart
     *            the new office after mins start
     */
    public void setOfficeAfterMinsStart( final int officeAfterMinsStart ) {
        this.officeAfterMinsStart = officeAfterMinsStart;
    }

    /**
     * Gets the office after hour end.
     *
     * @return the office after hour end
     */
    public int getOfficeAfterHourEnd() {
        return officeAfterHourEnd;
    }

    /**
     * Sets the office after hour end.
     *
     * @param officeAfterHourEnd
     *            the new office after hour end
     */
    public void setOfficeAfterHourEnd( final int officeAfterHourEnd ) {
        this.officeAfterHourEnd = officeAfterHourEnd;
    }

    /**
     * Gets the office after mins end.
     *
     * @return the office after mins end
     */
    public int getOfficeAfterMinsEnd() {
        return officeAfterMinsEnd;
    }

    /**
     * Sets the office after mins end.
     *
     * @param officeAfterMinsEnd
     *            the new office after mins end
     */
    public void setOfficeAfterMinsEnd( final int officeAfterMinsEnd ) {
        this.officeAfterMinsEnd = officeAfterMinsEnd;
    }
    
    @ManagedAttribute
    public int getBuyerTourOffsetHour() {
        return buyerTourOffsetHour;
    }

    @ManagedAttribute( description = "Change Buyer Tour Offset Hours for Asap" )
    public void setBuyerTourOffsetHour(int buyerTourOffsetHour) {
        this.buyerTourOffsetHour = buyerTourOffsetHour;
        propertyWriter.saveJmxProperty( "buyer.tour.offset.hour", buyerTourOffsetHour );
    }
    
    
    @ManagedAttribute
    public boolean isBuyerTourAsapEnabled() {
        return buyerTourAsapEnabled;
    }

    @ManagedAttribute( description = "Enable Buyer Tour Asap" )
    public void setBuyerTourAsapEnabled(boolean buyerTourAsapEnabled) {
        this.buyerTourAsapEnabled = buyerTourAsapEnabled;
        propertyWriter.saveJmxProperty( "buyer.tour.asap.enabled", buyerTourAsapEnabled );
    }

    /**
     * Gets the shift hour start state1.
     *
     * @return the shift hour start state1
     */
    public int getShiftHourStartState1() {
        return shiftHourStartState1;
    }

    /**
     * Sets the shift hour start state1.
     *
     * @param shiftHourStartState1
     *            the new shift hour start state1
     */
    public void setShiftHourStartState1( int shiftHourStartState1 ) {
        this.shiftHourStartState1 = shiftHourStartState1;
    }

    /**
     * Gets the shift hour end state1.
     *
     * @return the shift hour end state1
     */
    public int getShiftHourEndState1() {
        return shiftHourEndState1;
    }

    /**
     * Sets the shift hour end state1.
     *
     * @param shiftHourEndState1
     *            the new shift hour end state1
     */
    public void setShiftHourEndState1( int shiftHourEndState1 ) {
        this.shiftHourEndState1 = shiftHourEndState1;
    }

    /**
     * Gets the shift hour start state2.
     *
     * @return the shift hour start state2
     */
    public int getShiftHourStartState2() {
        return shiftHourStartState2;
    }

    /**
     * Sets the shift hour start state2.
     *
     * @param shiftHourStartState2
     *            the new shift hour start state2
     */
    public void setShiftHourStartState2( int shiftHourStartState2 ) {
        this.shiftHourStartState2 = shiftHourStartState2;
    }

    /**
     * Gets the shift hour end state2.
     *
     * @return the shift hour end state2
     */
    public int getShiftHourEndState2() {
        return shiftHourEndState2;
    }

    /**
     * Sets the shift hour end state2.
     *
     * @param shiftHourEndState2
     *            the new shift hour end state2
     */
    public void setShiftHourEndState2( int shiftHourEndState2 ) {
        this.shiftHourEndState2 = shiftHourEndState2;
    }

    /**
     * Gets the shift hour start state3.
     *
     * @return the shift hour start state3
     */
    public int getShiftHourStartState3() {
        return shiftHourStartState3;
    }

    /**
     * Sets the shift hour start state3.
     *
     * @param shiftHourStartState3
     *            the new shift hour start state3
     */
    public void setShiftHourStartState3( int shiftHourStartState3 ) {
        this.shiftHourStartState3 = shiftHourStartState3;
    }

    /**
     * Gets the shift hour end state3.
     *
     * @return the shift hour end state3
     */
    public int getShiftHourEndState3() {
        return shiftHourEndState3;
    }

    /**
     * Sets the shift hour end state3.
     *
     * @param shiftHourEndState3
     *            the new shift hour end state3
     */
    public void setShiftHourEndState3( int shiftHourEndState3 ) {
        this.shiftHourEndState3 = shiftHourEndState3;
    }

    /**
     * Gets the shift hours states1.
     *
     * @return the shift hours states1
     */
    public String getShiftHoursStates1() {
        return shiftHoursStates1;
    }

    /**
     * Sets the shift hours states1.
     *
     * @param shiftHoursStates1
     *            the new shift hours states1
     */
    public void setShiftHoursStates1( String shiftHoursStates1 ) {
        this.shiftHoursStates1 = shiftHoursStates1;
    }

    /**
     * Gets the shift hours states2.
     *
     * @return the shift hours states2
     */
    public String getShiftHoursStates2() {
        return shiftHoursStates2;
    }

    /**
     * Sets the shift hours states2.
     *
     * @param shiftHoursStates2
     *            the new shift hours states2
     */
    public void setShiftHoursStates2( String shiftHoursStates2 ) {
        this.shiftHoursStates2 = shiftHoursStates2;
    }

    /**
     * Gets the shift hours states3.
     *
     * @return the shift hours states3
     */
    public String getShiftHoursStates3() {
        return shiftHoursStates3;
    }

    /**
     * Sets the shift hours states3.
     *
     * @param shiftHoursStates3
     *            the new shift hours states3
     */
    public void setShiftHoursStates3( String shiftHoursStates3 ) {
        this.shiftHoursStates3 = shiftHoursStates3;
    }

    /**
     * Gets the time zone id.
     *
     * @return the time zone id
     */
    public String getTimeZoneId() {
        return timeZoneId;
    }

    /**
     * Sets the time zone id.
     *
     * @param timeZoneId
     *            the new time zone id
     */
    public void setTimeZoneId( final String timeZoneId ) {
        this.timeZoneId = timeZoneId;
    }

    /**
     * Gets the referral excluded states.
     *
     * @return the referral excluded states
     */
    @ManagedAttribute
    public String getReferralExcludedStates() {
        return referralExcludedStates;
    }

    /**
     * Sets the referral excluded states.
     *
     * @param referralExcludedStates
     *            the new referral excluded states
     */
    @ManagedAttribute
    public void setReferralExcludedStates( final String referralExcludedStates ) {
        this.referralExcludedStates = referralExcludedStates;
        propertyWriter.saveJmxProperty( "referral.excluded.states", referralExcludedStates );
    }

    /**
     * Gets the owner states.
     *
     * @return the owner states
     */
    @ManagedAttribute
    public String getOwnerStates() {
        return ownerStates;
    }

    /**
     * Sets the owner states.
     *
     * @param ownerStates
     *            the new owner states
     */
    @ManagedAttribute
    public void setOwnerStates( final String ownerStates ) {
        this.ownerStates = ownerStates;
        propertyWriter.saveJmxProperty( "owners.states.list", ownerStates );
    }

    /**
     * Gets the sf api user id.
     *
     * @return the sf api user id
     */
    public String getSfApiUserId() {
        return sfApiUserId;
    }

    /**
     * Sets the sf api user id.
     *
     * @param sfApiUserId
     *            the new sf api user id
     */
    public void setSfApiUserId( final String sfApiUserId ) {
        this.sfApiUserId = sfApiUserId;
    }

	public boolean isMortgageLeadNotificationEnabled() {
		return isMortgageLeadNotificationEnabled;
	}

	public void setMortgageLeadNotificationEnabled(boolean isMortgageLeadNotificationEnabled) {
		this.isMortgageLeadNotificationEnabled = isMortgageLeadNotificationEnabled;
		propertyWriter.saveJmxProperty( "mortgage.lead.notification", referralEmailNotificationEnabled );
	}
	
	/**
     * @return the zillowPhoneLeadStartHour
     */
	@ManagedAttribute
    public int getZillowPhoneLeadStartHour() {
        return zillowPhoneLeadStartHour;
    }

    /**
     * @param zillowPhoneLeadStartHour
     *            the zillowPhoneLeadStartHour to set
     */
    @ManagedAttribute
    public void setZillowPhoneLeadStartHour( int zillowPhoneLeadStartHour ) {
        this.zillowPhoneLeadStartHour = zillowPhoneLeadStartHour;
        propertyWriter.saveJmxProperty( "zillow.phone.lead.start.hour", zillowPhoneLeadStartHour );
    }

    /**
     * @return the zillowPhoneLeadEndHour
     */
    @ManagedAttribute
    public int getZillowPhoneLeadEndHour() {
        return zillowPhoneLeadEndHour;
    }

    /**
     * @param zillowPhoneLeadEndHour
     *            the zillowPhoneLeadEndHour to set
     */
    @ManagedAttribute
    public void setZillowPhoneLeadEndHour( int zillowPhoneLeadEndHour ) {
        this.zillowPhoneLeadEndHour = zillowPhoneLeadEndHour;
        propertyWriter.saveJmxProperty( "zillow.phone.lead.end.hour", zillowPhoneLeadEndHour );
    }
    
    /**
	 * @return
	 */
	 @ManagedAttribute
	public int getBeforeHourSyncUpLead() {
		return beforeHourSyncUpLead;
	}

	/**
	 * @param beforeHourSyncUpLead
	 */
	 @ManagedAttribute
	public void setBeforeHourSyncUpLead(int beforeHourSyncUpLead) {
		this.beforeHourSyncUpLead = beforeHourSyncUpLead;
		propertyWriter.saveJmxProperty( "before.hour.syncUp.lead", beforeHourSyncUpLead );
	}
}
