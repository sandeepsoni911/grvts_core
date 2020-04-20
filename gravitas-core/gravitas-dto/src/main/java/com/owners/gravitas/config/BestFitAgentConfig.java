/**
 *
 */
package com.owners.gravitas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.owners.gravitas.util.PropertyWriter;

/**
 * @author harshads
 *
 */
@ManagedResource(objectName = "com.owners.gravitas:name=BestFitAgentConfig")
@Component
public class BestFitAgentConfig {

	/** The property writer. */
	@Autowired
	private PropertyWriter propertyWriter;

	/** The default day threshold. */
	@Value("${bestfit.agent.default.day.threshold}")
	private int defaultDayThreshold;

	/** The default opportunity threshold. */
	@Value("${bestfit.agent.default.opportunity.threshold}")
	private int defaultOpportunityThreshold;

	/** The default opportunity rr threshold. */
	@Value("${bestfit.agent.default.opportunity.rr.threshold}")
	private int defaultOpportunityRRThrehold;

	/**
	 * Gets the default day threshold.
	 *
	 * @return the defaultDayThreshold
	 */
	@ManagedAttribute
	public int getDefaultDayThreshold() {
		return defaultDayThreshold;
	}

	/**
	 * Sets the default day threshold.
	 *
	 * @param defaultDayThreshold
	 *            the defaultDayThreshold to set
	 */
	@ManagedAttribute
	public void setDefaultDayThreshold(int defaultDayThreshold) {
		this.defaultDayThreshold = defaultDayThreshold;
		propertyWriter.saveJmxProperty("bestfit.agent.default.day.threshold", defaultDayThreshold);
	}

	/**
	 * Gets the default opportunity threshold.
	 *
	 * @return the defaultOpportunityThreshold
	 */
	@ManagedAttribute
	public int getDefaultOpportunityThreshold() {
		return defaultOpportunityThreshold;
	}

	/**
	 * Sets the default opportunity threshold.
	 *
	 * @param defaultOpportunityThreshold
	 *            the defaultOpportunityThreshold to set
	 */
	@ManagedAttribute
	public void setDefaultOpportunityThreshold(int defaultOpportunityThreshold) {
		this.defaultOpportunityThreshold = defaultOpportunityThreshold;
		propertyWriter.saveJmxProperty("bestfit.agent.default.opportunity.threshold", defaultOpportunityThreshold);
	}

	/**
	 * Gets the default opportunity rr threhold.
	 *
	 * @return the defaultOpportunityRRThrehold
	 */
	@ManagedAttribute
	public int getDefaultOpportunityRRThrehold() {
		return defaultOpportunityRRThrehold;
	}

	/**
	 * Sets the default opportunity rr threhold.
	 *
	 * @param defaultOpportunityRRThrehold
	 *            the defaultOpportunityRRThrehold to set
	 */
	@ManagedAttribute
	public void setDefaultOpportunityRRThrehold(int defaultOpportunityRRThrehold) {
		this.defaultOpportunityRRThrehold = defaultOpportunityRRThrehold;
		propertyWriter.saveJmxProperty("bestfit.agent.default.opportunity.rr.threshold", defaultOpportunityRRThrehold);
	}
}
