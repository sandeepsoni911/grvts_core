package com.owners.gravitas.business.task;

import static com.owners.gravitas.enums.CRMObjectLabel.Bad;
import static com.owners.gravitas.enums.CRMObjectLabel.Good;
import static com.owners.gravitas.enums.CRMObjectLabel.NotAvailable;
import static java.lang.Boolean.FALSE;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.services.machinelearning.model.PredictResult;
import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.builder.ContactEntityBuilder;
import com.owners.gravitas.business.builder.request.LeadScoreRequestBuilder;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.enums.CRMObjectLabel;
import com.owners.gravitas.service.ContactEntityService;
import com.owners.gravitas.service.LeadService;
import com.owners.gravitas.util.JsonUtil;

/**
 * The Class LeadTask updates the CRM(SF) and database with lead details by
 * making rest call to aws lead machine learning API.
 *
 * @author vishwanathm
 */
@Service
public class LeadTask {
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( LeadTask.class );

    /** The Constant DEFAULT_NEW_LEAD_SCORE. */
    private static final String DEFAULT_NEW_LEAD_SCORE = "0.03";

    /** The Constant CRM_BUYER_LEAD_SCORE. */
    private static final String CRM_BUYER_LEAD_SCORE = "Buyer_Lead_Score__c";

    /** The Constant CRM_BUYER_LEAD_LABEL. */
    private static final String CRM_BUYER_LEAD_LABEL = "Buyer_Lead_Label__c";

    /** The Constant LEAD_SHIFT_TYPE_BUYER. */
    private static final String LEAD_SHIFT_TYPE_BUYER = "leadShiftTypeBuyer";

    /** The Constant GRAVITAS_DEDUP_COUNT. */
    private static final String GRAVITAS_DEDUP_COUNT = "gravitasDedupCount";

    /** The working hour str. */
    private static final String WORKING_HOUR_STR = "In Working Hours";

    /** The outside working hour str. */
    private static final String OUTSIDE_WORKING_HOUR_STR = "Outside Working Hours";

    /** The agent opportunity service. */
    @Autowired
    private LeadService leadService;

    /** The lead score request builder. */
    @Autowired
    private LeadScoreRequestBuilder leadScoreRequestBuilder;

    /** The contact service V 1. */
    @Autowired
    private ContactEntityService contactServiceV1;

    /** The contact builder V 1. */
    @Autowired
    private ContactEntityBuilder contactBuilderV1;

    /**
     * Update lead score, for the lead request made by prospect API.
     *
     * @param request
     *            the request
     * @param crmId
     *            the crm id
     * @param deDupCount
     *            the de dup count
     * @param leadShiftTypeBuyer
     *            the lead shift type buyer
     */
    @Transactional( propagation = Propagation.REQUIRED )
    public void updateLeadScore( final LeadRequest request, Contact contact, final Integer deDupCount,
            final Boolean leadShiftTypeBuyer ) {
        String crmId = contact.getCrmId();
        LOGGER.info( "update lead score for crm id " + crmId );
        final Map< String, String > records = leadScoreRequestBuilder.convertTo( request );
        records.put( GRAVITAS_DEDUP_COUNT, deDupCount.toString() );
        records.put( LEAD_SHIFT_TYPE_BUYER, ( leadShiftTypeBuyer ) ? WORKING_HOUR_STR : OUTSIDE_WORKING_HOUR_STR );
        final Map< String, Object > crmRequest = updateLeadScores( crmId, records );
        // handle dedupe failure- no change in lead score/label
        if (MapUtils.isNotEmpty( crmRequest )) {
            LOGGER.info( "Updating lead score in for crmId: {}", crmId );
            request.setBuyerLeadLabel( String.valueOf( crmRequest.get( CRM_BUYER_LEAD_LABEL ) ) );
            request.setBuyerLeadScore( String.valueOf( crmRequest.get( CRM_BUYER_LEAD_SCORE ) ) );
            contact = contactBuilderV1.convertTo( request, contact );
            contactServiceV1.save( contact );
            LOGGER.info( "Lead Score updated : {}", crmId );
        }
    }

    /**
     * Update lead score, for the leads directly created from CRM.
     *
     * @param source
     *            the source
     * @param crmId
     *            the crm id
     * @param leadShiftTypeBuyer
     *            the lead shift type buyer
     */
    @Async( value = "apiExecutor" )
    public void updateLeadScore( final LeadSource source, final String crmId, final Boolean leadShiftTypeBuyer ) {
        LOGGER.debug( "update lead score for crm id " + crmId );
        final Map< String, String > records = leadScoreRequestBuilder.convertTo( source );
        records.put( GRAVITAS_DEDUP_COUNT, source.getDeDupCounter().toString() );
        records.put( LEAD_SHIFT_TYPE_BUYER, ( leadShiftTypeBuyer ) ? WORKING_HOUR_STR : OUTSIDE_WORKING_HOUR_STR );
        updateLeadScores( crmId, records );
    }

    /**
     * Update lead scores.
     *
     * @param crmId
     *            the crm id
     * @param records
     *            the records
     * @return the map
     */
    private Map< String, Object > updateLeadScores( final String crmId, final Map< String, String > records ) {
        final DecimalFormat df = new DecimalFormat( "#.####" );
        final Map< String, Object > crmRequest = new HashMap<>();
        try {
            LOGGER.info( "lead scoring params are : " + JsonUtil.toJson( records ) );
            final PredictResult result = leadService.getLeadScore( records );
            final String predictionLabel = result.getPrediction().getPredictedLabel();
            final CRMObjectLabel label = "1".equals( predictionLabel ) ? Good : Bad;
            crmRequest.put( CRM_BUYER_LEAD_LABEL, label );
            final float leadScore = result.getPrediction().getPredictedScores().get( predictionLabel ).floatValue()
                    * 100;
            crmRequest.put( CRM_BUYER_LEAD_SCORE, df.format( leadScore ) );
            LOGGER.info( "lead score is : " + leadScore );
        } catch ( final Exception e ) {
            if (Integer.valueOf( records.get( GRAVITAS_DEDUP_COUNT ) ) == 0) {
                crmRequest.put( CRM_BUYER_LEAD_LABEL, NotAvailable );
                crmRequest.put( CRM_BUYER_LEAD_SCORE, DEFAULT_NEW_LEAD_SCORE );
            }
            LOGGER.error( "IGNORE: update lead score, has been handled this exception", e );
        }
        leadService.updateLead( crmRequest, crmId, FALSE );
        return crmRequest;
    }
}
