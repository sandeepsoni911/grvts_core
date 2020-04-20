package com.owners.gravitas.gateway;

import org.springframework.integration.annotation.Gateway;

import com.owners.gravitas.amqp.LeadSource;

/**
 * The Interface LeadMessageGateway.
 *
 * @author amits
 */
public interface LeadMessageGateway {
    /**
     * This method publishes lead update details.
     *
     * @param message
     *            is the lead update to be published.
     */

    /**
     * @param message
     */
    @Gateway( requestChannel = "leadChange.update" )
    void publishLeadUpdate( LeadSource leadSource );


    /**
     * @param message
     */
    @Gateway( requestChannel = "lead.create" )
    void publishLeadCreate( LeadSource leadSource );
}
