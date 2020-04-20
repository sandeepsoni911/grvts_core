package com.owners.gravitas.business;

import java.util.concurrent.Future;

import com.google.api.services.gmail.Gmail;
import com.owners.gravitas.dto.request.EmailRequest;
import com.owners.gravitas.dto.response.AgentEmailResult;

/**
 * The Interface AgentEmailBusinessService.
 * 
 * @author ankusht
 */
public interface AgentEmailBusinessService {

    /**
     * Send agent email.
     *
     * @param emailRequest
     *            the email request
     * @param gmail
     *            the gmail
     * @param agentEmail
     *            the agent email
     * @return the future
     */
    Future< AgentEmailResult > sendAgentEmail( final EmailRequest emailRequest, final Gmail gmail,
            final String agentEmail );
}
