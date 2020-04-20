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
 * The Class PreliminaryTitleReportsEmailNotificationBuilder.
 * 
 * @author ankusht
 */
@Component
@ManagedResource( objectName = "com.owners.gravitas:name=PreliminaryTitleReportsConfig" )
public class PreliminaryTitleReportsEmailNotificationBuilder extends PTSEmailNotificationBuilder {

    /** The Constant PRELIMINARY_TITLE_REPORTS_EMAIL_CA. */
    private static final String PRELIMINARY_TITLE_REPORTS_EMAIL_CA = "PTS-7-Preliminary-Title-Reports-CA";

    /** The preliminary title reports mail delay minutes. */
    @Value( "${preliminary.title.reports.mail.delay.minutes: 5760}" ) // default
                                                                      // value 4
                                                                      // dyas.
    private int preliminaryTitleReportsMailDelayMinutes;

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
                case "CA": {
                    emailNotification.setMessageTypeName( PRELIMINARY_TITLE_REPORTS_EMAIL_CA );
                    break;
                }
                default:
                    // If state is not one of these then return null.
                    return null;
            }
            final long now = emailNotification.getCreatedOn().getTime();
            emailNotification.setTriggerOn(
                    new DateTime( now ).plusMinutes( preliminaryTitleReportsMailDelayMinutes ).getMillis() );
        }
        return emailNotification;
    }

    /**
     * Gets the preliminary title reports mail delay minutes.
     *
     * @return the preliminary title reports mail delay minutes
     */
    @ManagedAttribute
    public int getPreliminaryTitleReportsMailDelayMinutes() {
        return preliminaryTitleReportsMailDelayMinutes;
    }

    /**
     * Sets the preliminary title reports mail delay minutes.
     *
     * @param preliminaryTitleReportsMailDelayMinutes
     *            the new preliminary title reports mail delay minutes
     */
    @ManagedAttribute
    public void setPreliminaryTitleReportsMailDelayMinutes( final int preliminaryTitleReportsMailDelayMinutes ) {
        this.preliminaryTitleReportsMailDelayMinutes = preliminaryTitleReportsMailDelayMinutes;
        propertyWriter.saveJmxProperty( "preliminary.title.reports.mail.delay.minutes",
                preliminaryTitleReportsMailDelayMinutes );
    }
}
