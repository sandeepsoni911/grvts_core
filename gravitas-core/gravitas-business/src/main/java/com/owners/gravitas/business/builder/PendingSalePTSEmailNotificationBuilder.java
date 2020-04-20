package com.owners.gravitas.business.builder;

import static com.owners.gravitas.enums.RecordType.BUYER;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.amqp.AgentSource;
import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class PendingSalePTSEmailNotificationBuilder.
 *
 * @author shivamm
 */
@ManagedResource( objectName = "com.owners.gravitas:name=PendingSalePTSEmailNotificationConfig" )
@Component( "pendingSalePTSEmailNotificationBuilder" )
public class PendingSalePTSEmailNotificationBuilder extends PTSEmailNotificationBuilder {

    /** The Constant PTS_PENDING_SALE_EMAIL_FL_TX_OH_PA. */
    private static final String PTS_PENDING_SALE_EMAIL_FL_TX_OH_PA = "PTS-8-PendingSale_w-PTS-FL-TX-OH-PA";

    /** The Constant PTS_WELCOME_EMAIL_IL. */
    private static final String PTS_PENDING_SALE_EMAIL_CA = "PTS-9-PendingSale_w-PTS-CA";

    /** The Constant PTS_WELCOME_EMAIL_GA. */
    private static final String PTS_PENDING_SALE_EMAIL_GA = "PTS-10-PendingSale_w-PTS-GA";

    /** The Constant PTS_WELCOME_EMAIL_MA. */
    private static final String PTS_PENDING_SALE_EMAIL_MA = "PTS-11-PendingSale_w-PTS-MA";

    /** The Constant PTS_PENDING_SALE_EMAIL_IL. */
    private static final String PTS_PENDING_SALE_EMAIL_IL = "PTS-12-PendingSale_w-PTS-IL";

    /** The Constant PTS_PENDING_SALE_WITHOUT_PTS. */
    private static final String PTS_PENDING_SALE_WITHOUT_PTS = "PTS-13-Pending-Sale_w-o_PTS";

    /** The agent pending sale mail delay minutes. */
    @Value( "${agent.pending.sale.mail.delay.minutes: 1440}" ) // default value
                                                               // is 1 day
    private int agentPendingSaleMailDelayMinutes;

    /** The property writer. */
    @Autowired
    private PropertyWriter propertyWriter;

    /**
     * Convert to.
     *
     * @param pts
     *            the pts
     * @param source
     *            the source
     * @param opportunitySource
     *            the opportunity source
     * @return the email notification
     */
    public EmailNotification convertTo( final boolean pts, final AgentSource source,
            final OpportunitySource opportunitySource ) {
        EmailNotification emailNotification = null;
        if (source != null && validateState( opportunitySource )) {
            emailNotification = super.convertTo( source, null );
            if (pts) {
                final String propertyState = getPropertyState( opportunitySource );
                final String messageTypeName = getMessageTypeName( propertyState );
                if (null == messageTypeName) {
                    return null;
                }
                emailNotification.setMessageTypeName( messageTypeName );
            } else {
                emailNotification.setMessageTypeName( PTS_PENDING_SALE_WITHOUT_PTS );
            }
            final long now = emailNotification.getCreatedOn().getTime();
            emailNotification
                    .setTriggerOn( new DateTime( now ).plusMinutes( agentPendingSaleMailDelayMinutes ).getMillis() );
        }
        return emailNotification;
    }

    /**
     * Gets the property state.
     *
     * @param opportunitySource
     *            the opportunity source
     * @return the property state
     */
    private String getPropertyState( final OpportunitySource opportunitySource ) {
        String propertyState = null;
        if (opportunitySource.getOpportunityType().equals( BUYER.getType() )) {
            propertyState = opportunitySource.getPropertyState();
        } else {
            propertyState = opportunitySource.getSellerPropertyState();
        }
        return propertyState;
    }

    /**
     * Gets the message type name.
     *
     * @param propertyState
     *            the property state
     * @return the message type name
     */
    private String getMessageTypeName( final String propertyState ) {
        String messageTypeName = null;
        switch ( propertyState ) {
            case "FL":
            case "TX":
            case "OH":
            case "PA": {
                messageTypeName = PTS_PENDING_SALE_EMAIL_FL_TX_OH_PA;
                break;
            }
            case "GA": {
                messageTypeName = PTS_PENDING_SALE_EMAIL_GA;
                break;
            }
            case "MA": {
                messageTypeName = PTS_PENDING_SALE_EMAIL_MA;
                break;
            }
            case "CA": {
                messageTypeName = PTS_PENDING_SALE_EMAIL_CA;
                break;
            }
            case "IL": {
                messageTypeName = PTS_PENDING_SALE_EMAIL_IL;
                break;
            }
            default: {
                messageTypeName = null;
            }
        }
        return messageTypeName;
    }

    /**
     * Gets the agent pending sale mail delay minutes.
     *
     * @return the agent pending sale mail delay minutes
     */
    @ManagedAttribute
    public int getAgentPendingSaleMailDelayMinutes() {
        return agentPendingSaleMailDelayMinutes;
    }

    /**
     * Sets the agent pending sale mail delay minutes.
     *
     * @param agentPendingSaleMailDelayMinutes
     *            the new agent pending sale mail delay minutes
     */
    @ManagedAttribute
    public void setAgentPendingSaleMailDelayMinutes( final int agentPendingSaleMailDelayMinutes ) {
        this.agentPendingSaleMailDelayMinutes = agentPendingSaleMailDelayMinutes;
        propertyWriter.saveJmxProperty( "agent.pending.sale.mail.delay.minutes", agentPendingSaleMailDelayMinutes );
    }
}
