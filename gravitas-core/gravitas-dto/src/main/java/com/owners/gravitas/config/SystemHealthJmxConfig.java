package com.owners.gravitas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class SystemHealthJmxConfig.
 * 
 * @author ankusht
 */
@Component
@ManagedResource( objectName = "com.owners.gravitas:name=SystemHealthJmxConfig" )
public class SystemHealthJmxConfig {

    /** The system health lead email. */
    @Value( "${system.health.lead.email}" )
    private String systemHealthLeadEmail;

    /** The system health agent email. */
    @Value( "${system.health.agent.email}" )
    private String systemHealthAgentEmail;

    /** The system health opportunity email. */
    @Value( "${system.health.opportunity.email}" )
    private String systemHealthOpportunityEmail;

    /** The rabbit mq unreachable message. */
    @Value( "${rabbitmq.down.msg}" )
    private String rabbitmqDownMsg;

    /** The topic got disconnected message. */
    @Value( "${topic.down.msg}" )
    private String topicDownMsg;

    /** The service up msg. */
    @Value( "${gravitas.service.up}" )
    private String serviceUpMsg;

    /** The service down msg. */
    @Value( "${gravitas.service.down}" )
    private String serviceDownMsg;

    /** The contact topic update wait duration. */
    @Value( "${contact.topic.update.wait.duration.millis: 60000}" )
    private int contactTopicUpdateWaitDuration;

    /** The property writer. */
    @Autowired
    private PropertyWriter propertyWriter;

    /**
     * Gets the system health lead email.
     *
     * @return the system health lead email
     */
    public String getSystemHealthLeadEmail() {
        return systemHealthLeadEmail;
    }

    /**
     * Sets the system health lead email.
     *
     * @param systemHealthLeadEmail
     *            the new system health lead email
     */
    public void setSystemHealthLeadEmail( final String systemHealthLeadEmail ) {
        this.systemHealthLeadEmail = systemHealthLeadEmail;
    }

    /**
     * Gets the system health agent email.
     *
     * @return the system health agent email
     */
    public String getSystemHealthAgentEmail() {
        return systemHealthAgentEmail;
    }

    /**
     * Sets the system health agent email.
     *
     * @param systemHealthAgentEmail
     *            the new system health agent email
     */
    public void setSystemHealthAgentEmail( final String systemHealthAgentEmail ) {
        this.systemHealthAgentEmail = systemHealthAgentEmail;
    }

    /**
     * Gets the system health opportunity email.
     *
     * @return the system health opportunity email
     */
    public String getSystemHealthOpportunityEmail() {
        return systemHealthOpportunityEmail;
    }

    /**
     * Sets the system health opportunity email.
     *
     * @param systemHealthOpportunityEmail
     *            the new system health opportunity email
     */
    public void setSystemHealthOpportunityEmail( final String systemHealthOpportunityEmail ) {
        this.systemHealthOpportunityEmail = systemHealthOpportunityEmail;
    }

    /**
     * Gets the rabbitmq down msg.
     *
     * @return the rabbitmq down msg
     */
    public String getRabbitmqDownMsg() {
        return rabbitmqDownMsg;
    }

    /**
     * Sets the rabbitmq down msg.
     *
     * @param rabbitmqDownMsg
     *            the new rabbitmq down msg
     */
    public void setRabbitmqDownMsg( final String rabbitmqDownMsg ) {
        this.rabbitmqDownMsg = rabbitmqDownMsg;
    }

    /**
     * Gets the topic down msg.
     *
     * @return the topic down msg
     */
    public String getTopicDownMsg() {
        return topicDownMsg;
    }

    /**
     * Sets the topic down msg.
     *
     * @param topicDownMsg
     *            the new topic down msg
     */
    public void setTopicDownMsg( final String topicDownMsg ) {
        this.topicDownMsg = topicDownMsg;
    }

    /**
     * Gets the service up msg.
     *
     * @return the service up msg
     */
    public String getServiceUpMsg() {
        return serviceUpMsg;
    }

    /**
     * Sets the service up msg.
     *
     * @param serviceUpMsg
     *            the new service up msg
     */
    public void setServiceUpMsg( final String serviceUpMsg ) {
        this.serviceUpMsg = serviceUpMsg;
    }

    /**
     * Gets the service down msg.
     *
     * @return the service down msg
     */
    public String getServiceDownMsg() {
        return serviceDownMsg;
    }

    /**
     * Sets the service down msg.
     *
     * @param serviceDownMsg
     *            the new service down msg
     */
    public void setServiceDownMsg( final String serviceDownMsg ) {
        this.serviceDownMsg = serviceDownMsg;
    }

    /**
     * Gets the contact topic update wait duration.
     *
     * @return the contact topic update wait duration
     */
    @ManagedAttribute
    public int getContactTopicUpdateWaitDuration() {
        return contactTopicUpdateWaitDuration;
    }

    /**
     * Sets the contact topic update wait duration.
     *
     * @param contactTopicUpdateWaitDuration
     *            the new contact topic update wait duration
     */
    @ManagedAttribute
    public void setContactTopicUpdateWaitDuration( final int contactTopicUpdateWaitDuration ) {
        this.contactTopicUpdateWaitDuration = contactTopicUpdateWaitDuration;
        propertyWriter.saveJmxProperty( "contact.topic.update.wait.duration.millis", contactTopicUpdateWaitDuration );
    }
}
