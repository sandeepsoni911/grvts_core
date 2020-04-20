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
 * The Class AgentOnboardEmailNotificationBuilder.
 *
 * @author amits
 */
@ManagedResource( objectName = "com.owners.gravitas:name=AgentOnboardEmailConfig" )
@Component( "agentOnboardEmailNotificationBuilder" )
public class AgentOnboardEmailNotificationBuilder extends PTSEmailNotificationBuilder {

    /** The Constant PTS_WELCOME_EMAIL_IL. */
    private static final String PTS_WELCOME_EMAIL_IL = "PTS-WELCOME-EMAIL-IL";

    /** The Constant PTS_WELCOME_EMAIL_MA. */
    private static final String PTS_WELCOME_EMAIL_MA = "PTS-WELCOME-EMAIL-MA";

    /** The Constant PTS_WELCOME_EMAIL_GA. */
    private static final String PTS_WELCOME_EMAIL_GA = "PTS-WELCOME-EMAIL-GA";

    /** The Constant PTS_WELCOME_EMAIL_FL_TX_OH_PA_CA. */
    private static final String PTS_WELCOME_EMAIL_FL_TX_OH_PA_CA = "PTS-WELCOME-EMAIL-FL-TX-OH-PA-CA";

    /** The agent onboard mail delay. */
    @Value( "${agent.onboard.mail.delay.minutes: 2880}" ) // default value 2
                                                          // dyas.
    private int agentOnboardMailDelayMinutes;

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
                case "PA":
                case "CA": {
                    emailNotification.setMessageTypeName( PTS_WELCOME_EMAIL_FL_TX_OH_PA_CA );
                    break;
                }
                case "GA": {
                    emailNotification.setMessageTypeName( PTS_WELCOME_EMAIL_GA );
                    break;
                }
                case "MA": {
                    emailNotification.setMessageTypeName( PTS_WELCOME_EMAIL_MA );
                    break;
                }
                case "IL": {
                    emailNotification.setMessageTypeName( PTS_WELCOME_EMAIL_IL );
                    break;
                }
                default:
                    return null;
                // If state is not one of these then return null.
            }
            final long now = emailNotification.getCreatedOn().getTime();
            emailNotification
                    .setTriggerOn( new DateTime( now ).plusMinutes( agentOnboardMailDelayMinutes ).getMillis() );
        }
        return emailNotification;
    }

    /**
     * Gets the agent onboard mail delay.
     *
     * @return the agentOnboardMailDelay
     */
    @ManagedAttribute
    public int getAgentOnboardMailDelayMinutes() {
        return agentOnboardMailDelayMinutes;
    }

    /**
     * Sets the agent onboard mail delay.
     *
     * @param agentOnboardMailDelayMinutes
     *            the new agent onboard mail delay minutes
     */
    @ManagedAttribute
    public void setAgentOnboardMailDelayMinutes( final int agentOnboardMailDelayMinutes ) {
        this.agentOnboardMailDelayMinutes = agentOnboardMailDelayMinutes;
        propertyWriter.saveJmxProperty( "agent.onboard.mail.delay.minutes", agentOnboardMailDelayMinutes );
    }
}
