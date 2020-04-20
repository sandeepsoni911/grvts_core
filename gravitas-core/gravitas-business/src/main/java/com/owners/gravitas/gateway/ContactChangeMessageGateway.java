package com.owners.gravitas.gateway;

import org.springframework.integration.annotation.Gateway;

import com.owners.gravitas.amqp.OpportunityContact;

public interface ContactChangeMessageGateway {

    /**
     * This method publishes opportunity update details.
     *
     * @param message
     *            is the opportunity update to be published.
     */
    @Gateway( requestChannel = "contactChange.update" )
    void publishContactChange( OpportunityContact contact );

}
