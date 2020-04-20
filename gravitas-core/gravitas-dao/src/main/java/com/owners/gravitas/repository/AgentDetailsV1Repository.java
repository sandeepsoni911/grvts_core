package com.owners.gravitas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.owners.gravitas.domain.entity.AgentDetailsV1;

/**
 * The Interface CubeAgentEligibleZipsRepository.
 *
 * @author amits
 */
public interface AgentDetailsV1Repository extends JpaRepository< AgentDetailsV1, String > {

    /** The GE t_ agent s_ b y_ zipcod e_ v1. */
    String GET_AGENTS_BY_ZIPCODE_V1 = "SELECT c.*,ad.language,ad.score FROM gr_agent_detail ad INNER JOIN cube_agent_eligible_zips_v1 c "
            + "INNER JOIN gr_user u ON u.id = ad.user_id AND u.email = c.email WHERE c.zip = :zipcode AND u.status = 'active' AND ad.availability=true ORDER BY ad.score DESC";

    /**
     * Find active agents for zipcode.
     *
     * @param zipcode
     *            the zipcode
     * @return the list
     */
    @Query( value = GET_AGENTS_BY_ZIPCODE_V1, nativeQuery = true )
    public List< AgentDetailsV1 > findActiveAgentsByZipcode( @Param( "zipcode" ) String zipcode );

}