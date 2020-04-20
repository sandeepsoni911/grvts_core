package com.owners.gravitas.business.builder;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.amqp.AgentSource;
import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class EscrowEmailNotificationBuilder.
 * 
 * @author ankusht
 */
@Component
@ManagedResource( objectName = "com.owners.gravitas:name=EscrowEmailConfig" )
public class EscrowEmailNotificationBuilder extends PTSEmailNotificationBuilder {

    /** The Constant ESCROW_EMAIL_FL_TX_OH_PA. */
    private static final String ESCROW_EMAIL_FL_TX_OH_PA = "PTS-5-Escrow-Officer-FL-TX-OH-PA1";

    /** The Constant ESCROW_EMAIL_CA. */
    private static final String ESCROW_EMAIL_CA = "PTS-6-Escrow-Officer-CA";

    /** The escrow mail delay minutes. */
    @Value( "${escrow.mail.delay.minutes: 4320}" ) // default value 3 dyas.
    private int escrowMailDelayMinutes;

    /** The property writer. */
    @Autowired
    private PropertyWriter propertyWriter;

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.builder.PTSEmailNotificationBuilder#
     * convertTo(com.owners.gravitas.amqp.AgentSource,
     * com.hubzu.notification.dto.client.email.EmailNotification)
     */
    @Override
    public EmailNotification convertTo( final AgentSource source, final EmailNotification destination ) {
        EmailNotification emailNotification = destination;
        if (source != null && isNotBlank( source.getState() )) {
            emailNotification = super.convertTo( source, destination );
            switch ( source.getState() ) {
                case "FL":
                case "TX":
                case "OH":
                case "PA": {
                    emailNotification.setMessageTypeName( ESCROW_EMAIL_FL_TX_OH_PA );
                    break;
                }
                case "CA": {
                    emailNotification.setMessageTypeName( ESCROW_EMAIL_CA );
                    break;
                }
                default:
                    // If state is not one of these then return null.
                    return null;
            }
            final long now = emailNotification.getCreatedOn().getTime();
            emailNotification.setTriggerOn( new DateTime( now ).plusMinutes( escrowMailDelayMinutes ).getMillis() );
        }
        return emailNotification;
    }

    /**
     * Gets the escrow mail delay minutes.
     *
     * @return the escrow mail delay minutes
     */
    @ManagedAttribute
    public int getEscrowMailDelayMinutes() {
        return escrowMailDelayMinutes;
    }

    /**
     * Sets the escrow mail delay minutes.
     *
     * @param escrowMailDelayMinutes
     *            the new escrow mail delay minutes
     */
    @ManagedAttribute
    public void setEscrowMailDelayMinutes( final int escrowMailDelayMinutes ) {
        this.escrowMailDelayMinutes = escrowMailDelayMinutes;
        propertyWriter.saveJmxProperty( "escrow.mail.delay.minutes", escrowMailDelayMinutes );
    }
}
