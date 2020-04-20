package com.owners.gravitas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class LeadOpportunityBusinessConfig.
 *
 * @author shivamm
 */
@ManagedResource( objectName = "com.owners.gravitas:name=LeadOpportunityBusinessConfig" )
@Component
public class LeadOpportunityBusinessConfig {

    /** The contact database config. */
    @Value( "${contact.database.config:true}" )
    private boolean contactDatabaseConfig;
    
    /** The reassigned opportunity task config. */
    @Value( "${reassigned.opportunity.task.config:true}" )
    private boolean reassignedOpportunityTaskConfig;
    
    @Value( "${buyer.downloadapp.alert:false}" )
    private boolean buyerDownloadAppAlertEnabled;
    
    @Value( "${buyer.downloadapp.email.filter:[a-kA-K](.*)}" )
    private String buyerDownloadAppEmailFilter;
    
    @Value( "${buyer.downloadapp.tiny.url:https://tinyurl.com/ya3lq7pn}" )
    private String buyerDownloadAppTinyUrl;
    
    @Value( "${buyer.downloadapp.reminder.delay:P3D}" )
    private String buyerReminderDelay;
    
    @Value( "${buyer.downloadapp.sender:8888000099}" )
    private String smsSender;
    
    @Value( "${buyer.downloadapp.country.code:+1}" )
    private String countryCode;
    
    @Value( "${buyer.downloadapp.text:Owners.com® puts the power of buying a new home in your hands. Download our app today.}" )
    private String buyerDownloadAppText;

    /** The property writer. */
    @Autowired
    private PropertyWriter propertyWriter;

    /**
     * Checks if is contact database config.
     *
     * @return true, if is contact database config
     */
    @ManagedAttribute
    public boolean isContactDatabaseConfig() {
        return contactDatabaseConfig;
    }

    /**
     * Sets the contact database config.
     *
     * @param contactDatabaseConfig
     *            the new contact database config
     */
    @ManagedAttribute( description = "Enable/Disable contact database functionality" )
    public void setContactDatabaseConfig( boolean contactDatabaseConfig ) {
        this.contactDatabaseConfig = contactDatabaseConfig;
        propertyWriter.saveJmxProperty( "contact.database.config", contactDatabaseConfig );
    }

    /**
     * @return the reassignedOpportunityTaskConfig
     */
    @ManagedAttribute
    public boolean isReassignedOpportunityTaskConfig() {
        return reassignedOpportunityTaskConfig;
    }

    /**
     * @param reassignedOpportunityTaskConfig
     *            the reassignedOpportunityTaskConfig to set
     */
    @ManagedAttribute( description = "Enable/Disable reassigned opportunity task functionality" )
    public void setReassignedOpportunityTaskConfig( boolean reassignedOpportunityTaskConfig ) {
        this.reassignedOpportunityTaskConfig = reassignedOpportunityTaskConfig;
        propertyWriter.saveJmxProperty( "reassigned.opportunity.task.config", reassignedOpportunityTaskConfig );
    }
    
    /**
     * @return the buyerDownloadAppAlertEnabled
     */
    @ManagedAttribute
    public boolean isBuyerDownloadAppAlertEnabled() {
        return buyerDownloadAppAlertEnabled;
    }

    /**
     * @param buyerDownloadAppAlertEnabled
     *            the buyerDownloadAppAlertEnabled to set
     */
    @ManagedAttribute
    public void setBuyerDownloadAppAlertEnabled( boolean buyerDownloadAppAlertEnabled ) {
        this.buyerDownloadAppAlertEnabled = buyerDownloadAppAlertEnabled;
        propertyWriter.saveJmxProperty( "buyer.downloadapp.alert", buyerDownloadAppAlertEnabled );
    }

    /**
     * @return the buyerDownloadAppEmailFilter
     */
    @ManagedAttribute
    public String getBuyerDownloadAppEmailFilter() {
        return buyerDownloadAppEmailFilter;
    }

    /**
     * @param buyerDownloadAppEmailFilter
     *            the buyerDownloadAppEmailFilter to set
     */
    @ManagedAttribute
    public void setBuyerDownloadAppEmailFilter( String buyerDownloadAppEmailFilter ) {
        this.buyerDownloadAppEmailFilter = buyerDownloadAppEmailFilter;
        propertyWriter.saveJmxProperty( "buyer.downloadapp.email.filter", buyerDownloadAppEmailFilter );
    }

    /**
     * @return the buyerDownloadAppTinyUrl
     */
    @ManagedAttribute
    public String getBuyerDownloadAppTinyUrl() {
        return buyerDownloadAppTinyUrl;
    }

    /**
     * @param buyerDownloadAppTinyUrl
     *            the buyerDownloadAppTinyUrl to set
     */
    @ManagedAttribute
    public void setBuyerDownloadAppTinyUrl( String buyerDownloadAppTinyUrl ) {
        this.buyerDownloadAppTinyUrl = buyerDownloadAppTinyUrl;
        propertyWriter.saveJmxProperty( "buyer.downloadapp.tiny.url", buyerDownloadAppTinyUrl );
    }

    /**
     * @return the buyerDownloadAppText
     */
    @ManagedAttribute
    public String getBuyerDownloadAppText() {
        return buyerDownloadAppText;
    }

    /**
     * @param buyerDownloadAppText
     *            the buyerDownloadAppText to set
     */
    @ManagedAttribute
    public void setBuyerDownloadAppText( String buyerDownloadAppText ) {
        this.buyerDownloadAppText = buyerDownloadAppText;
        propertyWriter.saveJmxProperty( "buyer.downloadapp.text", buyerDownloadAppText );
    }

    /**
     * @return the smsSender
     */
    @ManagedAttribute
    public String getSmsSender() {
        return smsSender;
    }

    /**
     * @param smsSender
     *            the smsSender to set
     */
    @ManagedAttribute
    public void setSmsSender( String smsSender ) {
        this.smsSender = smsSender;
        propertyWriter.saveJmxProperty( "buyer.downloadapp.sender", smsSender );
    }

    /**
     * @return the buyerReminderDelay
     */
    @ManagedAttribute
    public String getBuyerReminderDelay() {
        return buyerReminderDelay;
    }

    /**
     * @param buyerReminderDelay
     *            the buyerReminderDelay to set
     */
    @ManagedAttribute
    public void setBuyerReminderDelay( String buyerReminderDelay ) {
        this.buyerReminderDelay = buyerReminderDelay;
        propertyWriter.saveJmxProperty( "buyer.downloadapp.reminder.delay", buyerReminderDelay );
    }

    /**
     * @return the countryCode
     */
    @ManagedAttribute
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * @param countryCode
     *            the countryCode to set
     */
    @ManagedAttribute
    public void setCountryCode( String countryCode ) {
        this.countryCode = countryCode;
        propertyWriter.saveJmxProperty( "buyer.downloadapp.country.code", countryCode );
    }   
}
