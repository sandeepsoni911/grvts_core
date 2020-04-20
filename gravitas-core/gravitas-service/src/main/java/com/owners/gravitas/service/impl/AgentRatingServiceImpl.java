package com.owners.gravitas.service.impl;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.domain.entity.AgentRating;
import com.owners.gravitas.repository.AgentRatingRepository;
import com.owners.gravitas.service.AgentRatingService;

/**
 * The Class AgentRatingServiceImpl.
 * 
 * @author ankusht
 */
@Service
@Transactional( readOnly = true )
public class AgentRatingServiceImpl implements AgentRatingService {

    /** The agent rating repository. */
    @Autowired
    private AgentRatingRepository agentRatingRepository;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentRatingService#save(com.owners.gravitas.
     * domain.entity.AgentRating)
     */
    @Override
    @Transactional( propagation = REQUIRED )
    public AgentRating save( final AgentRating agentRating ) {
        return agentRatingRepository.save( agentRating );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentRatingService#findOne(java.lang.String)
     */
    @Override
    public AgentRating findOne( final String id ) {
        return agentRatingRepository.findOne( id );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.AgentRatingService#
     * findByCrmIdAndStageAndClientEmailAndAgentDetails(java.lang.String,
     * java.lang.String, java.lang.String,
     * com.owners.gravitas.domain.entity.AgentDetails)
     */
    @Override
    public AgentRating findByCrmIdAndStageAndClientEmailAndAgentDetails( final String crmId, final String stage,
            final String clientEmail, final AgentDetails agentDetails ) {
        return agentRatingRepository.findByCrmIdAndStageAndClientEmailAndAgentDetails( crmId, stage, clientEmail,
                agentDetails );
    }
}
