package com.owners.gravitas.business.builder;

import org.springframework.stereotype.Component;

import com.owners.gravitas.domain.entity.AgentCoverage;
import com.owners.gravitas.dto.request.AgentOnboardRequest;

/**
 * The Class AgentCoverageBuilder.
 *
 * @author pabhishek
 */
@Component
public class AgentCoverageBuilder extends AbstractBuilder< AgentCoverage, AgentOnboardRequest > {

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public AgentCoverage convertFrom( AgentOnboardRequest source, AgentCoverage destination ) {
        AgentCoverage agentCoverage = destination;
        if (source != null) {
            if (agentCoverage == null) {
                agentCoverage = new AgentCoverage();
            }
            agentCoverage.setZip( "" );
            agentCoverage.setServable( true );
            agentCoverage.setType( "" );
        }
        return agentCoverage;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public AgentOnboardRequest convertTo( AgentCoverage source, AgentOnboardRequest destination ) {
        throw new UnsupportedOperationException();
    }

}
