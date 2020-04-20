package com.owners.gravitas.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.owners.gravitas.domain.entity.AgentPerformanceLog;
import com.owners.gravitas.domain.entity.AgentPerformanceLogId;

/**
 * The Interface AgentPerformanceLogRepository.
 * 
 * @author ankusht
 */
public interface AgentPerformanceLogRepository extends JpaRepository< AgentPerformanceLog, AgentPerformanceLogId > {

	/**
	 * Find by agent fb id.
	 *
	 * @param agentFbId
	 *            the agent fb id
	 * @return the agent performance log
	 */
	@Query( "select apl from GR_AGENT_PERFORMANCE_LOG apl where apl.agentFbId=:agentFbId "
			+ "order by apl.agentPerformanceLogId.createdDate desc" )
	Page< AgentPerformanceLog > findLatestMetricByAgentFbId( @Param( "agentFbId" ) final String agentFbId,
			final Pageable pageRequest );

}
