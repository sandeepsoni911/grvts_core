package com.owners.gravitas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class HappyAgentsConfig.
 *
 * @author ankusht, abhishek
 */
@ManagedResource( objectName = "com.owners.gravitas:name=HappyAgentsConfig" )
@Component
public class HappyAgentsConfig {

    /** The property writer. */
    @Autowired
    private PropertyWriter propertyWriter;

    /** The Constant REASON_REFERRED_TO_REF_EX. */
    public static final String REASON_REFERRED_TO_REF_EX = "Referred to referral exchange";

    /** The Constant REASON_DISPLAYED_FOR_REFERRAL. */
    public static final String REASON_DISPLAYED_FOR_REFERRAL = "Displayed for referral";

    /** The Constant NO_AGENT_AVAILABLE_SUFFIX. */
    public static final String NO_AGENT_AVAILABLE_SUFFIX = " as no agent available in the property zip";

    /** The Constant REASON_CURRENTLY_ASSIGNED_AGENT. */
    public static final String REASON_CURRENTLY_ASSIGNED_AGENT = "Currently assigned agent";

    /** The Constant REASON_SELECTED_STAGES. */
    public static final String REASON_SELECTED_STAGES = "Agent has more than threshold number of opportunities in the selected stages";

    /**
     * The Constant REASON_ASSIGNED_TO_OTHER_AGENT_HAVING_LESS_THAN_MIN_OPPS.
     */
    public static final String REASON_ASSIGNED_TO_OTHER_AGENT_HAVING_LESS_THAN_MIN_OPPS = "Assigned to some other agent having less than minimum required opportunities";

    /**
     * The Constant
     * REASON_ASSIGNED_TO_OTHER_GOOD_AGENT_HAVING_LESS_LEVEL2_MAX_OPPS.
     */
    public static final String REASON_ASSIGNED_TO_OTHER_GOOD_AGENT_HAVING_LESS_LEVEL2_MAX_OPPS = "Assigned to some other GOOD agent having less than level 2 max opportunities";

    /**
     * The Constant
     * REASON_ASSIGNED_TO_OTHER_NEW_AGENT_HAVING_LESS_LEVEL1_MAX_OPPS.
     */
    public static final String REASON_ASSIGNED_TO_OTHER_NEW_AGENT_HAVING_LESS_LEVEL1_MAX_OPPS = "Assigned to some other NEW agent having less than level 1 max opportunities";

    /**
     * The Constant
     * REASON_ASSIGNED_TO_OTHER_GOOD_AGENT_HAVING_LESS_LEVEL1_MAX_OPPS.
     */
    public static final String REASON_ASSIGNED_TO_OTHER_GOOD_AGENT_HAVING_LESS_LEVEL1_MAX_OPPS = "Assigned to some other GOOD agent having less than level 1 max opportunities";

    /**
     * The Constant
     * REASON_ASSIGNED_TO_OTHER_AVERAGE_AGENT_HAVING_LESS_LEVEL2_MAX_OPPS.
     */
    public static final String REASON_ASSIGNED_TO_OTHER_AVERAGE_AGENT_HAVING_LESS_LEVEL1_MAX_OPPS = "Assigned to some other AVERAGE agent having less than level 1 max opportunities";

    /** The Constant REASON_AGENT_STATUS_IS_ONBOARDING. */
    public static final String REASON_AGENT_STATUS_IS_ONBOARDING = "Agent status is onboarding";

    /** The Constant REASON_AGENT_STATUS_IS_INACTIVE. */
    public static final String REASON_AGENT_STATUS_IS_INACTIVE = "Agent status is inactive";

    /** The Constant REASON_AGENT_STATUS_IS_HOLD. */
    public static final String REASON_AGENT_STATUS_IS_HOLD = "Agent status is onhold";

    /** The Constant REASON_AGENT_IS_UNAVAILABLE. */
    public static final String REASON_AGENT_IS_UNAVAILABLE = "Agent is unavailable";

    /** The Constant IS_HAPPY_AGENT_ENABLED. */
    public static final String IS_HAPPY_AGENT_ENABLED = "isHappyAgentEnabled";

    /** The Constant IS_HUNGRY_AGENT_IN_ZIP. */
    public static final String IS_AGENTS_IN_ZIP = "isHappyAgentInZip";

    /** The Constant BEST_FIT_AGENTS. */
    public static final String BEST_FIT_AGENTS = "bestFitAgents";

    /** The Constant REASON_AGENT_EXCEED_ALL_THRESHOLDS. */
    public static final String REASON_AGENT_EXCEED_ALL_THRESHOLDS = "Exceeded all thresholds";

    /** The selected buyer stages. */
    @Value( "${happy.agents.selected.buyer.stages:Showing Homes,Face To Face Meeting}" )
    private String selectedBuyerStages;

    /** The selected seller stages. */
    @Value( "${happy.agents.selected.seller.stages:Face To Face Meeting}" )
    private String selectedSellerStages;

    /** The agent type new threshold score. */
    @Value( "${happy.agents.new.agent.type.threshold.score:100}" )
    private double agentTypeNewThresholdScore;

    /** The agent type good low threshold score. */
    @Value( "${happy.agents.good.agent.type.threshold.low.score:40}" )
    private double agentTypeGoodLowThresholdScore;

    /** The agent type good high threshold score. */
    @Value( "${happy.agents.good.agent.type.threshold.high.score:100}" )
    private double agentTypeGoodHighThresholdScore;

    /** The agent type average low threshold score. */
    @Value( "${happy.agents.average.agent.type.threshold.low.score:20}" )
    private double agentTypeAverageLowThresholdScore;

    /** The agent type average high threshold score. */
    @Value( "${happy.agents.average.agent.type.threshold.high.score:40}" )
    private double agentTypeAverageHighThresholdScore;

    /** The selected seller stages. */
    @Value( "${happy.agents.autoassign.email:autoassign@owners.com}" )
    private String autoAssignAgentEmail;

    /**
     * Gets the selected buyer stages.
     *
     * @return the selected buyer stages
     */
    @ManagedAttribute
    public String getSelectedBuyerStages() {
        return selectedBuyerStages;
    }

    /**
     * Sets the selected buyer stages.
     *
     * @param selectedBuyerStages
     *            the new selected buyer stages
     */
    @ManagedAttribute
    public void setSelectedBuyerStages( final String selectedBuyerStages ) {
        this.selectedBuyerStages = selectedBuyerStages;
        propertyWriter.saveJmxProperty( "happy.agents.selected.buyer.stages", selectedBuyerStages );
    }

    /**
     * Gets the selected seller stages.
     *
     * @return the selected seller stages
     */
    @ManagedAttribute
    public String getSelectedSellerStages() {
        return selectedSellerStages;
    }

    /**
     * Sets the selected seller stages.
     *
     * @param selectedSellerStages
     *            the new selected seller stages
     */
    @ManagedAttribute
    public void setSelectedSellerStages( final String selectedSellerStages ) {
        this.selectedSellerStages = selectedSellerStages;
        propertyWriter.saveJmxProperty( "happy.agents.selected.seller.stages", selectedSellerStages );
    }

    /**
     * Gets the auto assign agent email.
     *
     * @return the auto assign agent email
     */
    @ManagedAttribute
    public String getAutoAssignAgentEmail() {
        return autoAssignAgentEmail;
    }

    /**
     * Sets the auto assign agent email.
     *
     * @param autoAssignAgentEmail
     *            the new auto assign agent email
     */
    @ManagedAttribute
    public void setAutoAssignAgentEmail( final String autoAssignAgentEmail ) {
        this.autoAssignAgentEmail = autoAssignAgentEmail;
        propertyWriter.saveJmxProperty( "happy.agents.autoassign.email", autoAssignAgentEmail );
    }

    /**
     * Gets the agent type new threshold score.
     *
     * @return the agent type new threshold score
     */
    @ManagedAttribute
    public double getAgentTypeNewThresholdScore() {
        return agentTypeNewThresholdScore;
    }

    /**
     * Sets the agent type new threshold score.
     *
     * @param agentTypeNewThresholdScore
     *            the new agent type new threshold score
     */
    @ManagedAttribute
    public void setAgentTypeNewThresholdScore( final double agentTypeNewThresholdScore ) {
        this.agentTypeNewThresholdScore = agentTypeNewThresholdScore;
        propertyWriter.saveJmxProperty( "happy.agents.new.agent.type.threshold.score", agentTypeNewThresholdScore );
    }

    /**
     * Gets the agent type good low threshold score.
     *
     * @return the agent type good low threshold score
     */
    @ManagedAttribute
    public double getAgentTypeGoodLowThresholdScore() {
        return agentTypeGoodLowThresholdScore;
    }

    /**
     * Sets the agent type good low threshold score.
     *
     * @param agentTypeGoodLowThresholdScore
     *            the new agent type good low threshold score
     */
    @ManagedAttribute
    public void setAgentTypeGoodLowThresholdScore( final double agentTypeGoodLowThresholdScore ) {
        this.agentTypeGoodLowThresholdScore = agentTypeGoodLowThresholdScore;
        propertyWriter.saveJmxProperty( "happy.agents.good.agent.type.threshold.low.score",
                agentTypeGoodLowThresholdScore );
    }

    /**
     * Gets the agent type good high threshold score.
     *
     * @return the agent type good high threshold score
     */
    @ManagedAttribute
    public double getAgentTypeGoodHighThresholdScore() {
        return agentTypeGoodHighThresholdScore;
    }

    /**
     * Sets the agent type good high threshold score.
     *
     * @param agentTypeGoodHighThresholdScore
     *            the new agent type good high threshold score
     */
    @ManagedAttribute
    public void setAgentTypeGoodHighThresholdScore( final double agentTypeGoodHighThresholdScore ) {
        this.agentTypeGoodHighThresholdScore = agentTypeGoodHighThresholdScore;
        propertyWriter.saveJmxProperty( "happy.agents.good.agent.type.threshold.high.score",
                agentTypeGoodHighThresholdScore );
    }

    /**
     * Gets the agent type average low threshold score.
     *
     * @return the agent type average low threshold score
     */
    @ManagedAttribute
    public double getAgentTypeAverageLowThresholdScore() {
        return agentTypeAverageLowThresholdScore;
    }

    /**
     * Sets the agent type average low threshold score.
     *
     * @param agentTypeAverageLowThresholdScore
     *            the new agent type average low threshold score
     */
    @ManagedAttribute
    public void setAgentTypeAverageLowThresholdScore( final double agentTypeAverageLowThresholdScore ) {
        this.agentTypeAverageLowThresholdScore = agentTypeAverageLowThresholdScore;
        propertyWriter.saveJmxProperty( "happy.agents.average.agent.type.threshold.low.score",
                agentTypeAverageLowThresholdScore );
    }

    /**
     * Gets the agent type average high threshold score.
     *
     * @return the agent type average high threshold score
     */
    @ManagedAttribute
    public double getAgentTypeAverageHighThresholdScore() {
        return agentTypeAverageHighThresholdScore;
    }

    /**
     * Sets the agent type average high threshold score.
     *
     * @param agentTypeAverageHighThresholdScore
     *            the new agent type average high threshold score
     */
    @ManagedAttribute
    public void setAgentTypeAverageHighThresholdScore( final double agentTypeAverageHighThresholdScore ) {
        this.agentTypeAverageHighThresholdScore = agentTypeAverageHighThresholdScore;
        propertyWriter.saveJmxProperty( "happy.agents.average.agent.type.threshold.low.score",
                agentTypeAverageHighThresholdScore );
    }

}
