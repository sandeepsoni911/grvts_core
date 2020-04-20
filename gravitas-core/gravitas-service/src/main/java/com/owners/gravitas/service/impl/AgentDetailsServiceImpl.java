package com.owners.gravitas.service.impl;

import static com.owners.gravitas.enums.ErrorCode.AGENT_RECORD_NOT_FOUND_ERROR;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.dao.UserDao;
import com.owners.gravitas.domain.entity.AgentCoverage;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.domain.entity.User;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.repository.AgentDetailsRepository;
import com.owners.gravitas.repository.OpportunityRepository;
import com.owners.gravitas.service.AgentDetailsService;
import com.owners.gravitas.util.TimeZoneUtil;

/**
 * The Class AgentDetailsServiceImpl.
 *
 * @author pabhishek
 */
@Service
public class AgentDetailsServiceImpl implements AgentDetailsService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentDetailsServiceImpl.class );

    /** The agent details repository. */
    @Autowired
    private AgentDetailsRepository agentDetailsRepository;

    /** The opportunity repository. */
    @Autowired
    private OpportunityRepository opportunityV1Repository;
    
    @Autowired
    TimeZoneUtil timeZoneUtil;
    
    @Autowired
    UserDao userDao;
    
    @Value( "${agent.calendar.default.timezone:EST}" )
    private String defaultTimeZoneForAgents;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentDetailsService#save(com.owners.gravitas.
     * domain.entity.AgentDetails)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED )
    public AgentDetails save( final AgentDetails agentDetails ) {
        return agentDetailsRepository.save( agentDetails );
    }

    /**
     * Gets the agent coverage areas.
     *
     * @param email
     *            the email
     * @return the agent coverage areas
     */
    @Override
    public List< String > getAgentCoverageAreas( final String email ) {
        final AgentDetails agentDetails = agentDetailsRepository.findAgentByEmail( email );
        final List< String > coverageAreas = new ArrayList<>();
        if (null != agentDetails) {
            for ( final AgentCoverage coverage : agentDetails.getCoverageArea() ) {
                coverageAreas.add( coverage.getZip() );
            }
        }
        return coverageAreas;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentDetailsService#findByUser(com.owners.
     * gravitas.domain.entity.User)
     */
    @Override
    public AgentDetails findByUser( final User user ) {
        return agentDetailsRepository.findByUser( user );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentDetailsService#findAgentByEmail(java.
     * lang.String)
     */
    @Override
    public AgentDetails findAgentByEmail( final String email ) {
        return agentDetailsRepository.findAgentByEmail( email );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentDetailsService#findAgentEmailByListingId
     * (java.lang.String)
     */
    @Override
    public String findAgentEmailByListingId( final String listingId ) {
        final String agentEmail = opportunityV1Repository.findAgentEmailByListingId( listingId );
        if (StringUtils.isBlank( agentEmail )) {
            throw new ApplicationException( AGENT_RECORD_NOT_FOUND_ERROR.getErrorDetail(),
                    AGENT_RECORD_NOT_FOUND_ERROR );
        }
        return agentEmail;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.AgentDetailsService#
     * findAgentsFromListOfUserIds(java.util.List)
     */
    @Override
    public List< AgentDetails > getAgents( final List< User > users ) {
        return agentDetailsRepository.getAgentByUsers( users );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.AgentDetailsService#findAll()
     */
    @Override
    public List< AgentDetails > findAll() {
        return agentDetailsRepository.findAll();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentDetailsService#findByEmailsIn(java.util.
     * Collection)
     */
    @Override
    @Transactional( readOnly = true )
    public List< AgentDetails > findByEmailsIn( final Collection< String > emails ) {
        return agentDetailsRepository.findByEmailsIn( emails );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentDetailsService#findUnAvailableAgentsByEmailsIn(java.util.
     * Collection)
     */
	@Override
	public List<String> findUnAvailableAgentsByEmailsIn(final Collection<String> emails) {
		return agentDetailsRepository.findUnAvailableAgentsByEmailsIn(emails);
	}

	@Override
	/**
     * To get Agents time zone
     * based on email id
     * @param emailId
     * @return
     */
	public String getAgentsTimeZone(final String emailId) {
		String timezone = defaultTimeZoneForAgents;
		try {
    		AgentDetails agentDetails = findAgentByEmail(emailId);
    		if(agentDetails != null && StringUtils.isNotBlank( agentDetails.getState())) {
    			timezone = timeZoneUtil.getTimeZoneByState(agentDetails.getState());
    		}
		} catch (Exception ex){
		    LOGGER.error("Exception while getting agent timezone : {}", emailId, ex);
		}
		return timezone;
	}
	
	@Override
	public String getAgentsTimeZoneByAgentId(final String agentId) {
        String timezone = defaultTimeZoneForAgents;
        try {
            User agent = userDao.findById(agentId);
            AgentDetails agentDetails = findByUser(agent);
            if (agentDetails != null && StringUtils.isNotBlank(agentDetails.getState())) {
                timezone = timeZoneUtil.getTimeZoneByState(agentDetails.getState());
            }
        } catch (Exception ex) {
            LOGGER.error("Exception while getting agent timezone for agentId: {}", agentId, ex);
        }
        return timezone;
    }
}
