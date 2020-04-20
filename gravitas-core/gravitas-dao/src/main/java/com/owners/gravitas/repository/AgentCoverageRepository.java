package com.owners.gravitas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.domain.entity.AgentCoverage;
import com.owners.gravitas.domain.entity.AgentDetails;

/**
 * The Interface AgentCoverageRepository.
 *
 * @author pabhishek
 */
public interface AgentCoverageRepository extends JpaRepository< AgentCoverage, String > {

    /**
     * Delete by agent details.
     *
     * @param agentDetails
     *            the agent details
     */
    @Modifying
    @Transactional
    @Query( value = "delete from GR_AGENT_COVERAGE gac where gac.agentDetails = ?1" )
    public void deleteByAgentDetails( AgentDetails agentDetails );

    /**
     * Update servable zip.
     *
     * @param agentDetail
     *            the agent detail
     * @param zipCode
     *            the zip code
     * @param servable
     *            the servable
     */
    @Modifying
    @Query( "UPDATE GR_AGENT_COVERAGE ac SET ac.servable = ?3 where ac.zip = ?2 and ac.agentDetails = ?1" )
    public void updateServableZip( final AgentDetails agentDetail, final String zipCode, final boolean servable );

    /**
     * Update servable zip for all agents.
     *
     * @param zipCode
     *            the zip code
     * @param servable
     *            the servable
     */
    @Modifying
    @Query( "UPDATE GR_AGENT_COVERAGE ac SET ac.servable = ?2 where ac.zip = ?1" )
    public void updateServableZipForAllAgents( final String zipCode, final boolean servable );

}
