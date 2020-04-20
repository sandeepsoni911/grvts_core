package com.owners.gravitas.service;

import java.net.URISyntaxException;
import java.util.Set;

import org.springframework.web.client.RestClientException;

/**
 * The Interface SystemHealthService.
 *
 * @author raviz, ankusht
 */
public interface SystemHealthService {

    /**
     * Creates the crm agent.
     */
    void createCrmAgent();

    /**
     * Creates the crm lead.
     *
     * @param email
     *            the email
     */
    void createCrmLead( final String email );

    /**
     * Creates the default lead if not exist.
     *
     * @param email
     *            the email
     * @return the string
     */
    String createDefaultLeadIfNotExist( final String email );

    /**
     * Update crm opportunity.
     *
     * @param opportunityId
     *            the opportunity id
     */
    void updateCrmOpportunity( final String opportunityId );
    
    /**
     * Connect to ref data node.
     *
     * @return the sets the
     */
    public Set< String > connectToRefDataNode();

    /**
     * Execute query on gravitas DB.
     *
     * @return the int
     */
    public void executeQueryOnGravitasDB();
    
    /**
     * Gets the rabbit MQ status.
     *
     * @return the rabbit MQ status
     * @throws URISyntaxException
     * @throws RestClientException
     */
    String getRabbitMQStatus() throws RestClientException, URISyntaxException;
}
