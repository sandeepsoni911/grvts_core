package com.owners.gravitas.service;

import java.util.List;

import com.owners.gravitas.dto.agentassgn.EligibleAgent;

/**
 * The Interface AgentLookupService.
 * 
 * @author ankusht
 */
public interface AgentLookupService {

    /**
     * Gets the best agents from S curve server.
     *
     * @param zip
     *            the zip
     * @param price
     *            the price
     * @param crmId
     *            the crm id
     * @return the best agents from S curve server
     */
    List< EligibleAgent > getBestAgentsFromSCurveServer( final String zip, final long price, String crmId );
}
