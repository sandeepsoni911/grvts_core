package com.owners.gravitas.business.builder;

import static com.owners.gravitas.util.DateUtil.DEFAULT_OWNERS_DATE_PATTERN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.owners.gravitas.domain.entity.AgentStatistics;
import com.owners.gravitas.dto.AgentAnalyticsDto;
import com.owners.gravitas.dto.crm.response.AgentStatisticsResponse;
import com.owners.gravitas.util.DateUtil;

/**
 * The Class AgentStatisticsBuilder.
 *
 *
 * @author madhavk
 */

@Component
public class AgentStatisticsBuilder extends AbstractBuilder< List< AgentStatistics >, AgentStatisticsResponse > {

    /** The Constant DATE. */
    private static final String DATE = "date";

    /** The Constant SCORE. */
    private static final String SCORE = "score";

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public AgentStatisticsResponse convertTo( final List< AgentStatistics > source,
            final AgentStatisticsResponse destination ) {
        AgentStatisticsResponse response = destination;
        if (source != null) {
            if (response == null) {
                response = new AgentStatisticsResponse();
            }
            response.setAgentStatistics( buildAgentStatisticsResponse( getAgentStatistics( source ) ) );
        }
        return response;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public List< AgentStatistics > convertFrom( final AgentStatisticsResponse source,
            final List< AgentStatistics > destination ) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the list of dto from agent analystics entity.
     *
     * @param source
     *            the source
     * @return the list of dto from agent analystics entity
     */
    private List< AgentAnalyticsDto > getAgentStatistics( final List< AgentStatistics > source ) {
        final List< AgentAnalyticsDto > agentAnalyticsDtos = new ArrayList<>();
        for ( final AgentStatistics agent : source ) {
            final AgentAnalyticsDto agentAnalyticsDto = new AgentAnalyticsDto();
            agentAnalyticsDto.setValue( agent.getValue() );
            agentAnalyticsDto.setCreatedDate( DateUtil.toString( agent.getCreatedDate(), DEFAULT_OWNERS_DATE_PATTERN ) );
            agentAnalyticsDtos.add( agentAnalyticsDto );
        }
        return agentAnalyticsDtos;
    }

    /**
     * Builds the agent analytics response.
     *
     * @param agentDtoResponse
     *            the agent dto response
     * @return the list
     */
    private List< Map< String, String > > buildAgentStatisticsResponse( List< AgentAnalyticsDto > agentDtoResponse ) {
        final List< Map< String, String > > agentResponseList = new ArrayList< Map< String, String > >();
        for ( AgentAnalyticsDto agent : agentDtoResponse ) {
            Map< String, String > agentAnalyticsResponse = new HashMap<>();
            agentAnalyticsResponse.put( DATE, agent.getCreatedDate() );
            agentAnalyticsResponse.put( SCORE, agent.getValue() );
            agentResponseList.add( agentAnalyticsResponse );
        }
        return agentResponseList;
    }

}
