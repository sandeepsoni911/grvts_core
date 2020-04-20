package com.owners.gravitas.service;

import java.util.Collection;
import java.util.List;

import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.domain.entity.User;

/**
 * The Interface AgentDetailsService.
 *
 * @author pabhishek
 */
public interface AgentDetailsService {

    /**
     * Save.
     *
     * @param agentDetails
     *            the agent details
     * @return the agent details
     */
    AgentDetails save( AgentDetails agentDetails );

    /**
     * Gets the agent coverage areas.
     *
     * @param email
     *            the email
     * @return the agent coverage areas
     */
    List< String > getAgentCoverageAreas( String email );

    /**
     * Find by user.
     *
     * @param user
     *            the user
     * @return the agent details
     */
    AgentDetails findByUser( final User user );

    /**
     * Find agent by email.
     *
     * @param email
     *            the email
     * @return the agent details
     */
    AgentDetails findAgentByEmail( String email );

    /**
     * Find agent email by listing id.
     *
     * @param listingId
     *            the listing id
     * @return the string
     */
    String findAgentEmailByListingId( String listingId );

    /**
     * Find agents from list of user ids.
     *
     * @param userIds the user ids
     * @return the list
     */
    List<AgentDetails> getAgents(List<User> userIds);

    /**
     * Find all.
     *
     * @return the list
     */
    List< AgentDetails > findAll();
    
    /**
     * Find by emails in.
     *
     * @param emails
     *            the emails
     * @return the list
     */
    List< AgentDetails > findByEmailsIn( Collection< String > emails );
    
    /**
     * Find Unavailable
     * agents by emails
     *
     * @param emails
     *            the emails
     * @return the list
     * */
    List< String > findUnAvailableAgentsByEmailsIn( Collection< String > emails );
    
    
    /**
     * To get Agents time zone
     * based on email id
     * @param emailId
     * @return
     */
	String getAgentsTimeZone(final String emailId);

    String getAgentsTimeZoneByAgentId(String agentId);
		
}
