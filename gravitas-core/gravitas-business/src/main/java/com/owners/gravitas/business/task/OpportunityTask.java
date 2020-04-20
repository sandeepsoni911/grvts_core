package com.owners.gravitas.business.task;

import static com.owners.gravitas.enums.CRMObjectLabel.Bad;
import static com.owners.gravitas.enums.CRMObjectLabel.Good;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.amazonaws.services.machinelearning.model.PredictResult;
import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.builder.request.OpportunityScoreRequestBuilder;
import com.owners.gravitas.enums.CRMObjectLabel;
import com.owners.gravitas.service.AgentOpportunityService;
import com.owners.gravitas.service.OpportunityService;

/**
 * The Class OpportunityTask.
 */
@Service
public class OpportunityTask {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( OpportunityTask.class );

    /** The opportunity score request builder. */
    @Autowired
    private OpportunityScoreRequestBuilder opportunityScoreRequestBuilder;

    /** The agent opportunity service. */
    @Autowired
    private AgentOpportunityService agentOpportunityService;

    /** The opportunity service. */
    @Autowired
    private OpportunityService opportunityService;

    /**
     * Update opportunity score.
     *
     * @param opportunity
     *            the opportunity
     */
    @Async( value = "apiExecutor" )
    public void updateOpportunityScore( final OpportunitySource opportunity ) {
        LOGGER.info( "update opportunity score of opportunity " + opportunity.getCrmId() );
        final DecimalFormat df = new DecimalFormat( "#.####" );
        final Map< String, String > records = opportunityScoreRequestBuilder.convertTo( opportunity );
        final PredictResult result = agentOpportunityService.getOpportunityScore( records );
        final Map< String, Object > crmRequest = new HashMap<>();
        final String predictionLabel = result.getPrediction().getPredictedLabel();
        final CRMObjectLabel label = "1".equals( predictionLabel ) ? Good : Bad;
        crmRequest.put( "Opportunity_Label__c", label.name() );
        crmRequest.put( "Opportunity_Score__c",
                df.format( result.getPrediction().getPredictedScores().get( predictionLabel ).floatValue() * 100 ) );
        opportunityService.patchOpportunity( crmRequest, opportunity.getCrmId() );
    }
}
