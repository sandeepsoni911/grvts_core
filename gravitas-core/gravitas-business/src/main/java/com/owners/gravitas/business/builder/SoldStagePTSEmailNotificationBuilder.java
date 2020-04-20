package com.owners.gravitas.business.builder;

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
 * The Class SoldPTSEmailNotificationBuilder.
 *
 * @author shivamm
 */
@ManagedResource( objectName = "com.owners.gravitas:name=SoldStagePTSEmailNotificationBuilder" )
@Component( "soldStagePTSEmailNotificationBuilder" )
public class SoldStagePTSEmailNotificationBuilder extends PTSEmailNotificationBuilder {

    /** The Constant PTS_CLOSED_SALE_PTS_EMAIL. */
    private static final String PTS_CLOSED_SALE_PTS_EMAIL = "PTS-14-Closed_Sale_w-PTS";

    /** The Constant PTS_CLOSED_SALE_WITHOUT_PTS. */
    private static final String PTS_CLOSED_SALE_WITHOUT_PTS = "PTS-15-Closed_Sale_wo-PTS";

    /** The agent sold stage mail delay minutes. */
    @Value( "${agent.sold.stage.mail.delay.minutes: 1440}" ) // default value is
                                                             // 1 day
    private int agentSoldStageMailDelayMinutes;

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
                emailNotification.setMessageTypeName( PTS_CLOSED_SALE_PTS_EMAIL );
            } else {
                emailNotification.setMessageTypeName( PTS_CLOSED_SALE_WITHOUT_PTS );
            }
            final long now = emailNotification.getCreatedOn().getTime();
            emailNotification
                    .setTriggerOn( new DateTime( now ).plusMinutes( agentSoldStageMailDelayMinutes ).getMillis() );
        }
        return emailNotification;
    }

    /**
     * Gets the agent sold stage mail delay minutes.
     *
     * @return the agent sold stage mail delay minutes
     */
    @ManagedAttribute
    public int getAgentSoldStageMailDelayMinutes() {
        return agentSoldStageMailDelayMinutes;
    }

    /**
     * Sets the agent sold stage mail delay minutes.
     *
     * @param agentSoldStageMailDelayMinutes
     *            the new agent sold stage mail delay minutes
     */
    @ManagedAttribute
    public void setAgentSoldStageMailDelayMinutes( final int agentSoldStageMailDelayMinutes ) {
        this.agentSoldStageMailDelayMinutes = agentSoldStageMailDelayMinutes;
        propertyWriter.saveJmxProperty( "agent.sold.stage.mail.delay.minutes", agentSoldStageMailDelayMinutes );
    }

}
