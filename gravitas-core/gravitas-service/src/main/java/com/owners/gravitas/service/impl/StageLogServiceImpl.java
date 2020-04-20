package com.owners.gravitas.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.config.LeadOpportunityBusinessConfig;
import com.owners.gravitas.domain.entity.Opportunity;
import com.owners.gravitas.domain.entity.StageLog;
import com.owners.gravitas.repository.StageLogRepository;
import com.owners.gravitas.service.OpportunityService;
import com.owners.gravitas.service.StageLogService;

/**
 * The Class StageLogServiceImpl.
 *
 * @author vishwanathm
 */
@Service( "stageLogService" )
public class StageLogServiceImpl implements StageLogService {

    /** The stage log repository. */
    @Autowired
    private StageLogRepository stageLogRepository;

    @Autowired
    private OpportunityService opportunityService;

    /** The lead opportunity business config. */
    @Autowired
    private LeadOpportunityBusinessConfig leadOpportunityBusinessConfig;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( StageLogServiceImpl.class );

    /**
     * Log stage change.
     *
     * @param opportunityId
     *            the opportunity id
     * @param stageStr
     *            the stage str
     */
    @Override
    public void saveOpportunityStagelog( final String opportunityId, final String stageStr ) {
        LOGGER.debug( "Saving opportunity stage log for opportunityid " + opportunityId );
        if (leadOpportunityBusinessConfig.isContactDatabaseConfig()) {
            final Opportunity opportunity = opportunityService.getOpportunityByFbId( opportunityId, Boolean.FALSE );
            final StageLog stage = new StageLog();
            stage.setStage( stageStr );
            stage.setOpportunity( opportunity );
            stageLogRepository.save( stage );
            LOGGER.info( "added stage " + stageStr + " in StageLog table for " + opportunityId );
        }
    }
}
