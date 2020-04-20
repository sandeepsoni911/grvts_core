package com.owners.gravitas.business.builder;

import static com.owners.gravitas.enums.AgentType.AVERAGE;
import static com.owners.gravitas.enums.AgentType.GOOD;
import static com.owners.gravitas.enums.AgentType.NEW;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.owners.gravitas.config.HappyAgentsConfig;
import com.owners.gravitas.domain.entity.AgentAssignmentLog;
import com.owners.gravitas.dto.AgentAssignmentLogDto;

/**
 * The Class AgentAssignmentLogBuilder.
 * 
 * @author ankusht
 */
@Component
public class AgentAssignmentLogBuilder extends AbstractBuilder< AgentAssignmentLogDto, AgentAssignmentLog > {

    /** The happy agents config. */
    @Autowired
    private HappyAgentsConfig happyAgentsConfig;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public AgentAssignmentLog convertTo( final AgentAssignmentLogDto source, final AgentAssignmentLog destination ) {
        AgentAssignmentLog log = destination;
        if (source != null) {
            if (log == null) {
                log = new AgentAssignmentLog();
            }
            log.setAgentEmail( source.getAgentEmail() );
            log.setAgentScore( source.getScore() );
            log.setAgentType( getAgentType( source.getScore() ) );
            log.setCbsaCode( source.getCbsa() );
            log.setOwnersMarketLabel( source.getMarketLabel() );
            log.setZip( source.getZip() );
            log.setPriority( -1 );
        }
        return log;
    }

    /**
     * Gets the agent type.
     *
     * @param score
     *            the score
     * @return the agent type
     */
    private String getAgentType( final double score ) {
        String agentType = AVERAGE.name().toLowerCase();
        if (score >= happyAgentsConfig.getAgentTypeAverageLowThresholdScore()
                && score < happyAgentsConfig.getAgentTypeAverageHighThresholdScore()) {
            agentType = AVERAGE.name().toLowerCase();
        } else if (score == happyAgentsConfig.getAgentTypeNewThresholdScore()) {
            agentType = NEW.name().toLowerCase();
        } else if (score >= happyAgentsConfig.getAgentTypeGoodLowThresholdScore()
                && score < happyAgentsConfig.getAgentTypeGoodHighThresholdScore()) {
            agentType = GOOD.name().toLowerCase();
        }
        return agentType;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public AgentAssignmentLogDto convertFrom( final AgentAssignmentLog source,
            final AgentAssignmentLogDto destination ) {
        throw new UnsupportedOperationException( "convertFrom operation is not supported" );
    }

}
