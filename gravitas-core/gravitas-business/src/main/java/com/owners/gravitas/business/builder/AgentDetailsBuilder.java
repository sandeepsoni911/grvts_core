package com.owners.gravitas.business.builder;

import static com.owners.gravitas.util.DateUtil.DEFAULT_OWNERS_DATE_PATTERN;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.owners.gravitas.domain.entity.AgentCoverage;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.domain.entity.User;
import com.owners.gravitas.domain.entity.ZipDistance;
import com.owners.gravitas.dto.request.AgentOnboardRequest;
import com.owners.gravitas.repository.AgentCoverageRepository;
import com.owners.gravitas.service.UserService;
import com.owners.gravitas.service.ZipDistanceService;
import com.owners.gravitas.util.DateUtil;

/**
 * The Class AgentDetailsBuilder.
 *
 * @author pabhishek
 */
@Component
public class AgentDetailsBuilder extends AbstractBuilder< AgentDetails, AgentOnboardRequest > {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentDetailsBuilder.class );

    /** The user service. */
    @Autowired
    private UserService userService;

    /** The zip distance service. */
    @Autowired
    private ZipDistanceService zipDistanceService;

    /** The agent coverage repository. */
    @Autowired
    private AgentCoverageRepository agentCoverageRepository;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public AgentDetails convertFrom( final AgentOnboardRequest source, final AgentDetails destination ) {
        AgentDetails agentDetails = destination;
        if (source != null) {
            if (agentDetails == null) {
                agentDetails = new AgentDetails();
                agentDetails.setScore( 0.0 );
            }
            if (!source.getAddress().getZip().equals( agentDetails.getHomeZip() )
                    || ( Integer.parseInt( source.getDrivingRadius() ) != agentDetails.getDrivingRadius() )) {
                final List< ZipDistance > zips = zipDistanceService.getZipsWithinCoverageArea(
                        new Double( source.getDrivingRadius() ), source.getAddress().getZip() );
                // TODO: review from lavjeet
                if (isNotEmpty( agentDetails.getCoverageArea() )) {
                    LOGGER.info( "Deleting Agents coverageArea records {}", agentDetails.getCoverageArea().size() );
                    agentCoverageRepository.deleteByAgentDetails( agentDetails );
                    LOGGER.info( "Deleted Agents coverageArea records" );
                }
                final List< AgentCoverage > agentCoverages = new ArrayList< AgentCoverage >();
                for ( final ZipDistance zip : zips ) {
                    final AgentCoverage agentCoverage = new AgentCoverage();
                    agentCoverage.setAgentDetails( agentDetails );
                    agentCoverage.setZip( zip.getDestinationZip() );
                    agentCoverage.setServable( true );
                    agentCoverages.add( agentCoverage );
                }
                agentDetails.setCoverageArea( agentCoverages );
            }
            agentDetails.setMobileCarrier( source.getMobileCarrier() );
            agentDetails.setDrivingRadius( Integer.parseInt( source.getDrivingRadius() ) );
            agentDetails.setAvailability( source.isAvailable() );
            agentDetails.setHomeZip( source.getAddress().getZip() );
            agentDetails.setStartingOn( DateUtil.toSqlDate( source.getAgentStartingDate(), DEFAULT_OWNERS_DATE_PATTERN ) );
            agentDetails.setState( source.getAddress().getState() );
            agentDetails.setLanguage( source.getLanguage() );
            agentDetails.setLicense( source.getLicense() );

            final User managingBroker = userService.findByEmail( source.getManagingBrokerId() );
            agentDetails.setManagingBroker( managingBroker );
        }
        return agentDetails;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public AgentOnboardRequest convertTo( AgentDetails source, AgentOnboardRequest destination ) {
        throw new UnsupportedOperationException();
    }
}
