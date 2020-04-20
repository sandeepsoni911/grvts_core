package com.owners.gravitas.gateway;

import org.springframework.integration.annotation.Gateway;

import com.owners.gravitas.amqp.OpportunitySource;

/**
 * The Interface OpportunityMessageGateway.
 *
 * @author amits
 */
public interface OpportunityChangeMessageGateway {
    /**
     * This method publishes opportunity update details.
     *
     * @param message
     *            is the opportunity update to be published.
     */
    @Gateway( requestChannel = "opportunityChange.update" )
    void publishOpportunityChange( OpportunitySource opportunity );

    /**
     * Publish opportunity create.
     *
     * @param opportunitySource
     *            the opportunity create
     */
    @Gateway( requestChannel = "opportunityChange.create" )
    void publishOpportunityCreate( OpportunitySource opportunitySource );
}
