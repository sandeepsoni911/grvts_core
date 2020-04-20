package com.owners.gravitas.business.builder;

import static com.owners.gravitas.constants.Constants.S_CURVE_AGENT;
import static com.owners.gravitas.enums.SCurveStatus.S_CURVE_CONSIDERED;

import org.springframework.stereotype.Component;

import com.owners.gravitas.domain.entity.AgentAssignmentLog;
import com.owners.gravitas.dto.agentassgn.EligibleAgent;

/**
 * The Class EligibleAgentBuilder.
 * 
 * @author ankusht
 */
@Component
public class EligibleAgentBuilder extends AbstractBuilder< AgentAssignmentLog, EligibleAgent > {

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public EligibleAgent convertTo( final AgentAssignmentLog source, final EligibleAgent destination ) {
        throw new UnsupportedOperationException( "convertTo operation is not supported" );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public AgentAssignmentLog convertFrom( final EligibleAgent source, final AgentAssignmentLog destination ) {
        AgentAssignmentLog agentAssignmentLog = destination;
        if (source != null) {
            if (agentAssignmentLog == null) {
                agentAssignmentLog = new AgentAssignmentLog();
            }
            agentAssignmentLog.setAgentEmail( source.getEmail() );
            agentAssignmentLog.setAgentScore( source.getScore() );
            agentAssignmentLog.setAgentType( S_CURVE_AGENT );
            agentAssignmentLog.setAssignmentStatus( S_CURVE_CONSIDERED.name().toLowerCase() );
            agentAssignmentLog.setNumberOfOppsInThresholdPeriod( 0 );
        }
        return agentAssignmentLog;
    }

}
