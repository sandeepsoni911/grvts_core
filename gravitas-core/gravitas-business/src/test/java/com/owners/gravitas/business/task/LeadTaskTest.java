package com.owners.gravitas.business.task;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.amazonaws.services.machinelearning.model.PredictResult;
import com.amazonaws.services.machinelearning.model.Prediction;
import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.builder.ContactEntityBuilder;
import com.owners.gravitas.business.builder.request.LeadScoreRequestBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.dto.crm.response.CRMLeadResponse;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.service.ContactEntityService;
import com.owners.gravitas.service.LeadService;

/**
 * The Class LeadTaskTest.
 *
 * @author vishwanathm
 */
public class LeadTaskTest extends AbstractBaseMockitoTest {

    /** The lead task. */
    @InjectMocks
    private LeadTask leadTask;
    /** The agent opportunity service. */
    @Mock
    private LeadService leadService;

    /** The lead score request builder. */
    @Mock
    private LeadScoreRequestBuilder leadScoreRequestBuilder;

    /** The contact service V 1. */
    @Mock
    private ContactEntityService contactServiceV1;

    /** The contact builder V 1. */
    @Mock
    private ContactEntityBuilder contactBuilderV1;

    @DataProvider( name = "leadLabel" )
    public Object[][] leadLabel() {
        return new Object[][] { { "1" }, { "0" } };
    }

    @DataProvider( name = "leadLabel1" )
    public Object[][] leadLabel1() {
        return new Object[][] { { "1", true }, { "0", true }, { "1", false }, { "0", false } };
    }

    @DataProvider( name = "leadLabelAndDedupeCount" )
    public Object[][] leadLabelAndDedupeCount() {
        return new Object[][] { { "1", true, 0 }, { "0", true, 0 }, { "1", false, 0 }, { "0", false, 0 } };
    }

    /**
     * Test update lead score_for crm leads.
     */
    @Test( dataProvider = "leadLabel" )
    public void testUpdateLeadScore_forCRMLeads( final String label ) {
        final LeadSource source = new LeadSource();
        final String crmId = "test";
        source.setId( crmId );
        source.setState( "AB" );
        source.setDeDupCounter( 1 );
        final boolean leadShiftTypeBuyer = true;

        final Map< String, String > records = new HashMap<>();
        final PredictResult result = new PredictResult();
        final Prediction pred = new Prediction();
        result.setPrediction( pred );
        final java.util.Map< String, Float > predictedScores = new HashMap<>();
        predictedScores.put( label, new Float( 0.002 ) );
        result.getPrediction().setPredictedLabel( label );
        result.getPrediction().setPredictedScores( predictedScores );
        Mockito.when( leadScoreRequestBuilder.convertTo( source ) ).thenReturn( records );

        Mockito.when( leadService.getLeadScore( records ) ).thenReturn( result );
        Mockito.when( leadService.updateLead( Mockito.anyMap(), Mockito.anyString(), Mockito.anyBoolean() ) )
                .thenReturn( new CRMLeadResponse() );

        leadTask.updateLeadScore( source, crmId, leadShiftTypeBuyer );

        Mockito.verify( leadService ).getLeadScore( records );
        Mockito.verify( leadService ).updateLead( Mockito.anyMap(), Mockito.anyString(), Mockito.anyBoolean() );
    }

    @Test( dataProvider = "leadLabel1" )
    public void testGravitasLeadApiScoreUpdate( final String label, final boolean leadShiftTypeBuyer ) {
        final LeadRequest req = new LeadRequest();
        final String crmId = "test";
        req.setState( "AB" );
        final Map< String, String > records = new HashMap<>();
        final PredictResult result = new PredictResult();
        final Prediction pred = new Prediction();
        result.setPrediction( pred );
        final java.util.Map< String, Float > predictedScores = new HashMap<>();
        predictedScores.put( label, new Float( 0.002 ) );
        result.getPrediction().setPredictedLabel( label );
        result.getPrediction().setPredictedScores( predictedScores );
        Mockito.when( leadScoreRequestBuilder.convertTo( req ) ).thenReturn( records );
        Mockito.when( leadService.getLeadScore( records ) ).thenReturn( result );
        Mockito.when( leadService.updateLead( Mockito.anyMap(), Mockito.anyString(), Mockito.anyBoolean() ) )
                .thenReturn( new CRMLeadResponse() );

        final Contact contact = new Contact();
        contact.setStage( "stage" );
        contact.setCrmId(crmId);
        Mockito.when( contactServiceV1.findByCrmId( crmId ) ).thenReturn( contact );
        Mockito.when( contactBuilderV1.convertTo( req, contact ) ).thenReturn( contact );
        Mockito.when( contactServiceV1.save( contact ) ).thenReturn( contact );

        leadTask.updateLeadScore( req, contact, 1, leadShiftTypeBuyer );

        Mockito.verify( leadService ).getLeadScore( records );
        Mockito.verify( leadService ).updateLead( Mockito.anyMap(), Mockito.anyString(), Mockito.anyBoolean() );
    }

    @Test( dataProvider = "leadLabel1" )
    public void testGravitasLeadApiScoreUpdate_IfExceptionScoreShouldNotUpdate( final String label,
            final boolean leadShiftTypeBuyer ) {
        final LeadRequest req = new LeadRequest();
        final String crmId = "test";
        req.setState( "AB" );
        final Map< String, String > records = new HashMap<>();
        final PredictResult result = new PredictResult();
        final Prediction pred = new Prediction();
        result.setPrediction( pred );
        final java.util.Map< String, Float > predictedScores = new HashMap<>();
        predictedScores.put( label, new Float( 0.002 ) );
        result.getPrediction().setPredictedLabel( label );
        result.getPrediction().setPredictedScores( predictedScores );
        Mockito.when( leadScoreRequestBuilder.convertTo( req ) ).thenReturn( records );
        Mockito.when( leadService.getLeadScore( records ) ).thenThrow( new RuntimeException() );
        Mockito.when( leadService.updateLead( Mockito.anyMap(), Mockito.anyString(), Mockito.anyBoolean() ) )
                .thenReturn( new CRMLeadResponse() );

        final Contact contact = new Contact();
        contact.setStage( "stage" );
        contact.setCrmId(crmId);
        Mockito.when( contactServiceV1.findByCrmId( crmId ) ).thenReturn( contact );
        Mockito.when( contactBuilderV1.convertTo( req, contact ) ).thenReturn( contact );
        Mockito.when( contactServiceV1.save( contact ) ).thenReturn( contact );

        leadTask.updateLeadScore( req, contact, 0, leadShiftTypeBuyer );

        if (leadShiftTypeBuyer) {
            Assert.assertEquals( records.get( "leadShiftTypeBuyer" ), "In Working Hours" );
        } else {
            Assert.assertEquals( records.get( "leadShiftTypeBuyer" ), "Outside Working Hours" );
        }
        Mockito.verify( leadService ).getLeadScore( records );
        Mockito.verify( leadService ).updateLead( Mockito.anyMap(), Mockito.anyString(), Mockito.anyBoolean() );
    }

    @Test( dataProvider = "leadLabelAndDedupeCount" )
    public void testGravitasLeadApiScoreUpdate_IfExceptionScoreShouldNotUpdateWithDedupeCount( final String label,
            final boolean leadShiftTypeBuyer, final int DedupeCount ) {
        final LeadRequest req = new LeadRequest();
        final String crmId = "test";
        req.setState( "AB" );
        final Map< String, String > records = new HashMap<>();
        final PredictResult result = new PredictResult();
        final Prediction pred = new Prediction();
        result.setPrediction( pred );
        final java.util.Map< String, Float > predictedScores = new HashMap<>();
        predictedScores.put( label, new Float( 0.002 ) );
        result.getPrediction().setPredictedLabel( label );
        result.getPrediction().setPredictedScores( predictedScores );
        Mockito.when( leadScoreRequestBuilder.convertTo( req ) ).thenReturn( records );
        Mockito.when( leadService.getLeadScore( records ) ).thenThrow( new RuntimeException() );
        Mockito.when( leadService.updateLead( Mockito.anyMap(), Mockito.anyString(), Mockito.anyBoolean() ) )
                .thenReturn( new CRMLeadResponse() );

        final Contact contact = new Contact();
        contact.setStage( "stage" );
        contact.setCrmId(crmId);
        Mockito.when( contactServiceV1.findByCrmId( crmId ) ).thenReturn( contact );
        Mockito.when( contactBuilderV1.convertTo( req, contact ) ).thenReturn( contact );
        Mockito.when( contactServiceV1.save( contact ) ).thenReturn( contact );

        leadTask.updateLeadScore( req, contact, DedupeCount, leadShiftTypeBuyer );

        if (leadShiftTypeBuyer) {
            Assert.assertEquals( records.get( "leadShiftTypeBuyer" ), "In Working Hours" );
        } else {
            Assert.assertEquals( records.get( "leadShiftTypeBuyer" ), "Outside Working Hours" );
        }
        Mockito.verify( leadService ).getLeadScore( records );
        Mockito.verify( leadService ).updateLead( Mockito.anyMap(), Mockito.anyString(), Mockito.anyBoolean() );
    }
}
