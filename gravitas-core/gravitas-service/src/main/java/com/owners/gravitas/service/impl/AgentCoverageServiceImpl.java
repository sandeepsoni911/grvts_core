package com.owners.gravitas.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.repository.AgentCoverageRepository;
import com.owners.gravitas.service.AgentCoverageService;

/**
 * The Class AgentCoverageServiceImpl.
 */
@Service
@Transactional
public class AgentCoverageServiceImpl implements AgentCoverageService {

    /** The agent coverage repository. */
    @Autowired
    AgentCoverageRepository agentCoverageRepository;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentCoverageService#updateServableZipCode(
     * com.owners.gravitas.domain.entity.AgentDetails, java.lang.String,
     * boolean)
     */
    public void updateServableZipCode( final AgentDetails agentDetails, final String zipCode,
            final boolean isServable ) {
        agentCoverageRepository.updateServableZip( agentDetails, zipCode, isServable );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.AgentCoverageService#
     * updateServableZipCodeForAllAgents(java.lang.String, boolean)
     */
    public void updateServableZipCodeForAllAgents( final String zipCode, final boolean isServable ) {
        agentCoverageRepository.updateServableZipForAllAgents( zipCode, isServable );
    }

}
