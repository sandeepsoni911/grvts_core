package com.owners.gravitas.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.owners.gravitas.dto.agentassgn.EligibleAgent;
import com.owners.gravitas.dto.agentassgn.EligibleAgentResponse;
import com.owners.gravitas.service.AgentLookupService;

/**
 * The Class AgentLookupServiceImpl.
 * 
 * @author ankusht
 */
@Service
public class AgentLookupServiceImpl implements AgentLookupService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentLookupServiceImpl.class );

    /** The best fit api endpoint. */
    @Value( "${gravitas.agent.assignment.best.fit.endpoint}" )
    private String bestFitApiEndpoint;

    /** The rest template. */
    @Autowired
    private RestTemplate restTemplate;

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.AgentLookupService#
     * getBestAgentFromAgentAssgnServer(java.lang.String, java.lang.String)
     */
    @Override
    public List< EligibleAgent > getBestAgentsFromSCurveServer( final String zip, final long price,
            final String crmId ) {
        LOGGER.info( "Calling SCURVE server for getting best agents. zip=" + zip + ", price=" + price );
        final String url = String.format( bestFitApiEndpoint, zip, price, crmId );
        final EligibleAgentResponse response = restTemplate.getForObject( url, EligibleAgentResponse.class );
        LOGGER.info( "Response from SCURVE server is " + response );
        return response.getEligibleAgents();
    }

}
