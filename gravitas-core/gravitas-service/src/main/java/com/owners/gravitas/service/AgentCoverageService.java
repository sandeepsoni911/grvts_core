package com.owners.gravitas.service;

import com.owners.gravitas.domain.entity.AgentDetails;

/**
 * The Interface AgentCoverageService.
 */
public interface AgentCoverageService {

	/**
	 * Update servable zip code.
	 *
	 * @param agentDetails the agent details
	 * @param zipCode the zip code
	 * @param isServable the is servable
	 */
	public void updateServableZipCode( final AgentDetails agentDetails, final String zipCode, final boolean isServable );

	/**
	 * Update servable zip code for all agents.
	 *
	 * @param zipCode the zip code
	 * @param isServable the is servable
	 */
	public void updateServableZipCodeForAllAgents( final String zipCode, final boolean isServable );

}
