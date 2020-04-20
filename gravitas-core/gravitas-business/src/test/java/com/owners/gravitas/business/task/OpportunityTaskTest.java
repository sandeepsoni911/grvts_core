package com.owners.gravitas.business.task;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.amazonaws.services.machinelearning.model.PredictResult;
import com.amazonaws.services.machinelearning.model.Prediction;
import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.builder.request.OpportunityScoreRequestBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.service.AgentOpportunityService;
import com.owners.gravitas.service.OpportunityService;

/**
 * The Class OpportunityTaskTest.
 *
 * @author amits
 */
public class OpportunityTaskTest extends AbstractBaseMockitoTest {

    /** The opportunity task. */
    @InjectMocks
    private OpportunityTask opportunityTask;

    /** The opportunity score request builder. */
    @Mock
    private OpportunityScoreRequestBuilder opportunityScoreRequestBuilder;

    /** The agent opportunity service. */
    @Mock
    private AgentOpportunityService agentOpportunityService;

    /** The opportunity service. */
    @Mock
    private OpportunityService opportunityService;

    /**
     * Testupdate opportunity score_for good opportunity.
     */
    @Test
    public void testupdateOpportunityScore_forGoodOpportunity() {
        final OpportunitySource opportunity = new OpportunitySource();
        final Map< String, Float > predictedScores = new HashMap<>();
        final Map< String, Object > crmRequest = new HashMap<>();
        predictedScores.put( "1", 3.0f );
        Prediction prediction = new Prediction();
        prediction.setPredictedScores( predictedScores );
        prediction.setPredictedLabel( "1" );
        PredictResult predictResult = new PredictResult();
        predictResult.setPrediction( prediction );
        opportunity.setCrmId( "testCRMId" );
        crmRequest.put( "Opportunity_Label__c", "Good" );
        crmRequest.put( "Opportunity_Score__c", "300" );

        Mockito.when( agentOpportunityService.getOpportunityScore( Mockito.anyMap() ) ).thenReturn( predictResult );

        opportunityTask.updateOpportunityScore( opportunity );
        Mockito.verify( opportunityService ).patchOpportunity( crmRequest, opportunity.getCrmId() );
    }

    /**
     * Testupdate opportunity score_for bad opportunity.
     */
    @Test
    public void testupdateOpportunityScore_forBadOpportunity() {
        final OpportunitySource opportunity = new OpportunitySource();
        final Map< String, Float > predictedScores = new HashMap<>();
        final Map< String, Object > crmRequest = new HashMap<>();
        predictedScores.put( "0", 1.0f );
        Prediction prediction = new Prediction();
        prediction.setPredictedScores( predictedScores );
        prediction.setPredictedLabel( "0" );
        PredictResult predictResult = new PredictResult();
        predictResult.setPrediction( prediction );
        opportunity.setCrmId( "testCRMId" );
        crmRequest.put( "Opportunity_Label__c", "Bad" );
        crmRequest.put( "Opportunity_Score__c", "100" );

        Mockito.when( agentOpportunityService.getOpportunityScore( Mockito.anyMap() ) ).thenReturn( predictResult );

        opportunityTask.updateOpportunityScore( opportunity );
        Mockito.verify( opportunityService ).patchOpportunity( crmRequest, opportunity.getCrmId() );
    }

}
