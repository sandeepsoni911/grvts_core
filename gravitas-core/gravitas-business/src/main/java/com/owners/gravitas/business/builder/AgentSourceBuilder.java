package com.owners.gravitas.business.builder;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.owners.gravitas.amqp.AgentSource;
import com.owners.gravitas.dto.crm.response.CRMAgentResponse;

/**
 * The Class AgentSourceBuilder.
 *
 * @author amits
 */
@Component( "agentSourceBuilder" )
public class AgentSourceBuilder extends AbstractBuilder< CRMAgentResponse , AgentSource > {

    /**
     * Converts Map< String, String > to AgentSource.
     *
     * @param source
     *            is source request.
     * @param destination
     *            the destination
     * @return the Agent Source
     */
    @Override
    public AgentSource convertTo( final CRMAgentResponse source, final AgentSource destination ) {
        AgentSource agentSource = destination;
        if (source != null) {
            if (agentSource == null) {
                agentSource = new AgentSource();
            }
            BeanUtils.copyProperties( source, agentSource );
        }
        return agentSource;
    }

    /** Method not supported. */
    @Override
    public CRMAgentResponse convertFrom( final AgentSource source, final CRMAgentResponse destination ) {
        throw new UnsupportedOperationException( "convertFrom is not supported" );
    }
}
