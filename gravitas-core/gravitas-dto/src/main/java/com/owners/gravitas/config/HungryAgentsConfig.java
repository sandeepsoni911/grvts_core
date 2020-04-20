package com.owners.gravitas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class HungryAgentsConfig.
 * 
 * @author ankusht
 */
@ManagedResource( objectName = "com.owners.gravitas:name=HungryAgentsConfig" )
@Component
public class HungryAgentsConfig {

    /** The property writer. */
    @Autowired
    private PropertyWriter propertyWriter;

    /** The hungry agents enabled. */
    @Value( "${hungry.agents.enabled:false}" )
    private boolean hungryAgentsEnabled;

    /** The hungry agents threshold. */
    @Value( "${hungry.agents.opportunity.count.threshold:3}" )
    private int hungryAgentsOppCountThreshold;

    /** The hungry agents opp assignment period. */
    @Value( "${hungry.agents.opportunity.assignment.period:7}" )
    private int hungryAgentsOppAssignmentPeriod;

    /** The hungry agents bucket name. */
    @Value( "${hungry.agents.bucket.name:HUNGRYAGENTS}" )
    private String hungryAgentsBucketName;

    /** The Constant ASSIGNED_TO_THE_MOST_ELIGIBLE_HUNGRY_AGENT. */
    public static final String ASSIGNED_TO_THE_MOST_ELIGIBLE_HUNGRY_AGENT = "Assigned to the most eligible hungry agent";

    /** The Constant AGENT_HAS_MORE_THAN_THRESHOLD_NO_OF_OPPORTUNITIES. */
    public static final String AGENT_HAS_MORE_THAN_THRESHOLD_NO_OF_OPPORTUNITIES = "Agent has more than threshold number of opportunities assigned to him";

    /** The Constant IS_HUNGRY_AGENT_ENABLED. */
    public static final String IS_HUNGRY_AGENT_ENABLED = "isHungryAgentEnabled";

    /**
     * Checks if is hungry agents enabled.
     *
     * @return true, if is hungry agents enabled
     */
    @ManagedAttribute
    public boolean isHungryAgentsEnabled() {
        return hungryAgentsEnabled;
    }

    /**
     * Sets the hungry agents enabled.
     *
     * @param hungryAgentsEnabled
     *            the new hungry agents enabled
     */
    @ManagedAttribute
    public void setHungryAgentsEnabled( final boolean hungryAgentsEnabled ) {
        this.hungryAgentsEnabled = hungryAgentsEnabled;
        propertyWriter.saveJmxProperty( "hungry.agents.enabled", hungryAgentsEnabled );
    }

    /**
     * Gets the hungry agents threshold.
     *
     * @return the hungry agents threshold
     */
    @ManagedAttribute
    public int getHungryAgentsOppCountThreshold() {
        return hungryAgentsOppCountThreshold;
    }

    /**
     * Sets the hungry agents threshold.
     *
     * @param hungryAgentsThreshold
     *            the new hungry agents threshold
     */
    @ManagedAttribute
    public void setHungryAgentsOppCountThreshold( final int hungryAgentsThreshold ) {
        this.hungryAgentsOppCountThreshold = hungryAgentsThreshold;
        propertyWriter.saveJmxProperty( "hungry.agents.opportunity.count.threshold", hungryAgentsThreshold );
    }

    /**
     * Gets the hungry agents opp assignment period.
     *
     * @return the hungry agents opp assignment period
     */
    @ManagedAttribute
    public int getHungryAgentsOppAssignmentPeriod() {
        return hungryAgentsOppAssignmentPeriod;
    }

    /**
     * Sets the hungry agents opp assignment period.
     *
     * @param hungryAgentsOppAssignmentPeriod
     *            the new hungry agents opp assignment period
     */
    @ManagedAttribute
    public void setHungryAgentsOppAssignmentPeriod( final int hungryAgentsOppAssignmentPeriod ) {
        this.hungryAgentsOppAssignmentPeriod = hungryAgentsOppAssignmentPeriod;
        propertyWriter.saveJmxProperty( "hungry.agents.opportunity.assignment.period",
                hungryAgentsOppAssignmentPeriod );
    }

    /**
     * Gets the hungry agents bucket name.
     *
     * @return the hungry agents bucket name
     */
    @ManagedAttribute
    public String getHungryAgentsBucketName() {
        return hungryAgentsBucketName;
    }

    /**
     * Sets the hungry agents bucket name.
     *
     * @param hungryAgentsBucketName
     *            the new hungry agents bucket name
     */
    @ManagedAttribute
    public void setHungryAgentsBucketName( final String hungryAgentsBucketName ) {
        this.hungryAgentsBucketName = hungryAgentsBucketName;
        propertyWriter.saveJmxProperty( "hungry.agents.bucket.name", hungryAgentsBucketName );
    }
}
