package com.owners.gravitas.business.builder;

import static com.owners.gravitas.constants.Constants.UNDER_SCORE;
import static com.owners.gravitas.enums.AgentType.AVERAGE;
import static com.owners.gravitas.enums.AgentType.GOOD;
import static com.owners.gravitas.enums.AgentType.NEW;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.owners.gravitas.config.HappyAgentsConfig;
import com.owners.gravitas.dao.dto.AgentTypeDto;
import com.owners.gravitas.dao.dto.CbsaAgentDetail;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.enums.AgentType;

/**
 * The Class AgentTypeDtoMapBuilder.
 * 
 * @author ankusht
 */
@Component
public class AgentTypeDtoMapBuilder
        extends AbstractBuilder< List< CbsaAgentDetail >, Map< AgentType, List< AgentTypeDto > > > {

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
    public Map< AgentType, List< AgentTypeDto > > convertTo( final List< CbsaAgentDetail > source,
            final Map< AgentType, List< AgentTypeDto > > destination ) {
        Map< AgentType, List< AgentTypeDto > > map = null;
        if (CollectionUtils.isNotEmpty( source )) {
            final Map< String, List< AgentDetails > > temp = putInStagingMap( source );
            map = populateResultMap( temp );
        }
        return map;
    }

    /**
     * Populate result map.
     *
     * @param temp
     *            the temp
     * @return the map
     */
    private Map< AgentType, List< AgentTypeDto > > populateResultMap( final Map< String, List< AgentDetails > > temp ) {
        final Map< AgentType, List< AgentTypeDto > > map = initialize();
        temp.entrySet().forEach( entry -> {
            final String[] splitVals = entry.getKey().split( UNDER_SCORE );
            switch ( AgentType.valueOf( splitVals[1] ) ) {
                case GOOD:
                    map.get( GOOD ).add( new AgentTypeDto( splitVals[0], entry.getValue() ) );
                    break;

                case NEW:
                    map.get( NEW ).add( new AgentTypeDto( splitVals[0], entry.getValue() ) );
                    break;

                case AVERAGE:
                    map.get( AVERAGE ).add( new AgentTypeDto( splitVals[0], entry.getValue() ) );
                    break;

                default:
                    break;
            }
        } );
        return map;
    }

    /**
     * Put in staging map.
     *
     * @param source
     *            the source
     * @return the map
     */
    private Map< String, List< AgentDetails > > putInStagingMap( final List< CbsaAgentDetail > source ) {
        final Map< String, List< AgentDetails > > map = new HashMap<>();
        source.forEach( cbsaAgDtl -> {
            final String key = cbsaAgDtl.getCbsa() + UNDER_SCORE + getType( cbsaAgDtl.getAgentDetails().getScore() );
            if (map.get( key ) == null) {
                map.put( key, new LinkedList<>() );
            }
            map.get( key ).add( cbsaAgDtl.getAgentDetails() );
        } );
        return map;
    }

    /**
     * Gets the type.
     *
     * @param score
     *            the score
     * @return the type
     */
    private AgentType getType( final Double score ) {
        AgentType type = NEW;
        if (score != null) {
            if (score >= happyAgentsConfig.getAgentTypeAverageLowThresholdScore()
                    && score < happyAgentsConfig.getAgentTypeAverageHighThresholdScore()) {
                type = AVERAGE;
            } else if (score == happyAgentsConfig.getAgentTypeNewThresholdScore()) {
                type = NEW;
            } else if (score >= happyAgentsConfig.getAgentTypeGoodLowThresholdScore()
                    && score < happyAgentsConfig.getAgentTypeGoodHighThresholdScore()) {
                type = GOOD;
            }
        }
        return type;
    }

    /**
     * Initialize.
     *
     * @return the map
     */
    private Map< AgentType, List< AgentTypeDto > > initialize() {
        final Map< AgentType, List< AgentTypeDto > > map = new HashMap< >( 3 );
        map.put( GOOD, new LinkedList< >() );
        map.put( NEW, new LinkedList< >() );
        map.put( AVERAGE, new LinkedList< >() );
        return map;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public List< CbsaAgentDetail > convertFrom( final Map< AgentType, List< AgentTypeDto > > source,
            final List< CbsaAgentDetail > destination ) {
        throw new UnsupportedOperationException( "convertFrom is not supported" );
    }

}
