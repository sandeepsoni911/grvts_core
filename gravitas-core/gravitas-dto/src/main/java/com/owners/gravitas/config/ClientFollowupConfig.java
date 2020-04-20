package com.owners.gravitas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class ClientFollowupConfig.
 *
 * @author raviz
 */
@ManagedResource( objectName = "com.owners.gravitas:name=ClientFollowupConfig" )
@Component
public class ClientFollowupConfig {

    /** The property writer. */
    @Autowired
    private PropertyWriter propertyWriter;

    /** The enable client follow up email. */
    @Value( "${owners.client.followup.notification.buyer.enabled: false}" )
    private boolean enableClientFollowUpEmailForBuyer;

    /** The enable client follow up email. */
    @Value( "${owners.client.followup.notification.seller.enabled: false}" )
    private boolean enableClientFollowUpEmailForSeller;

    /** The client follow up mail delay minutes. */
    // default value is 2 days.
    @Value( "${owners.notification.mail.delay.minutes: 2880}" )
    private int clientFollowUpMailDelayMinutes;

    /**
     * Checks if is enable client follow up email for buyer.
     *
     * @return true, if is enable client follow up email for buyer
     */
    @ManagedAttribute
    public boolean isEnableClientFollowUpEmailForBuyer() {
        return enableClientFollowUpEmailForBuyer;
    }

    /**
     * Sets the enable client follow up email for buyer.
     *
     * @param enableClientFollowUpEmailForBuyer
     *            the enable client follow up email for buyer
     */
    @ManagedAttribute
    public void setEnableClientFollowUpEmailForBuyer( final boolean enableClientFollowUpEmailForBuyer ) {
        this.enableClientFollowUpEmailForBuyer = enableClientFollowUpEmailForBuyer;
        propertyWriter.saveJmxProperty( "owners.client.followup.notification.buyer.enabled",
                enableClientFollowUpEmailForBuyer );
    }

    /**
     * Checks if is enable client follow up email for seller.
     *
     * @return true, if is enable client follow up email for seller
     */
    @ManagedAttribute
    public boolean isEnableClientFollowUpEmailForSeller() {
        return enableClientFollowUpEmailForSeller;
    }

    /**
     * Sets the enable client follow up email for seller.
     *
     * @param enableClientFollowUpEmailForSeller
     *            the enable client follow up email for seller
     */
    @ManagedAttribute
    public void setEnableClientFollowUpEmailForSeller( final boolean enableClientFollowUpEmailForSeller ) {
        this.enableClientFollowUpEmailForSeller = enableClientFollowUpEmailForSeller;
        propertyWriter.saveJmxProperty( "owners.client.followup.notification.seller.enabled",
                enableClientFollowUpEmailForSeller );
    }

    /**
     * Gets the client follow up mail delay minutes.
     *
     * @return the client follow up mail delay minutes
     */
    @ManagedAttribute
    public int getClientFollowUpMailDelayMinutes() {
        return clientFollowUpMailDelayMinutes;
    }

    /**
     * Sets the client follow up mail delay minutes.
     *
     * @param clientFollowUpMailDelayMinutes
     *            the new client follow up mail delay minutes
     */
    @ManagedAttribute
    public void setClientFollowUpMailDelayMinutes( final int clientFollowUpMailDelayMinutes ) {
        this.clientFollowUpMailDelayMinutes = clientFollowUpMailDelayMinutes;
        propertyWriter.saveJmxProperty( "owners.notification.mail.delay.minutes", clientFollowUpMailDelayMinutes );
    }

}
