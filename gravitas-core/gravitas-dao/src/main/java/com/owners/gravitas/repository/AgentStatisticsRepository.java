package com.owners.gravitas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.owners.gravitas.domain.entity.AgentStatistics;

/**
 * The Interface AgentStatisticsRepository.
 *
 * @author madhavk
 */
@Repository
public interface AgentStatisticsRepository extends JpaRepository< AgentStatistics, String > {

    /** The Constant FIND_AGENT_DETAILS_BY_EMAIL. */

    final static String FIND_AGENT_DETAILS_BY_EMAIL = "SELECT gaa FROM GR_AGENT_STATISTICS gaa inner join gaa.agentDetails ad inner join ad.user u WHERE u.email=:email and gaa.key = "
            + "'SCORE'" + " ORDER BY gaa.createdDate";

    /**
     * Find agent statistics.
     *
     * @param email
     *            the email
     * @return the list
     */
    @Query( value = FIND_AGENT_DETAILS_BY_EMAIL )
    public List< AgentStatistics > findAgentStatistics( @Param( "email" ) final String email );

}
