package com.owners.gravitas.business.builder.request;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.builder.AbstractBuilder;

/**
 * The Class OpportunityScoreRequestBuilder.
 *
 * @author amits
 */
@Component
public class OpportunityScoreRequestBuilder extends AbstractBuilder< OpportunitySource, Map< String, String > > {

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public Map< String, String > convertTo( OpportunitySource source, Map< String, String > destination ) {
        Map< String, String > opportunityScoreRequest = destination;
        if (source != null) {
            if (opportunityScoreRequest == null) {
                opportunityScoreRequest = new HashMap<>();
            }
            opportunityScoreRequest.put( "leadRecordType", source.getRecordType() );
            opportunityScoreRequest.put( "leadSource", source.getLeadSource() );
            opportunityScoreRequest.put( "buyerReadinessTimeline", source.getBuyerReadinessTimeline() );
            opportunityScoreRequest.put( "state", source.getPropertyState() );
            opportunityScoreRequest.put( "preApprovedForMortgage", source.getPreApprovedForMortgage() );
            opportunityScoreRequest.put( "requestType", source.getLeadRequestType() );
            opportunityScoreRequest.put( "workingWithRealtor", source.getWorkingWithExternalAgent() );
            opportunityScoreRequest.put( "buyerLeadQuality", source.getBuyerLeadQuality() );
            opportunityScoreRequest.put( "gravitasDedupCount", source.getDedupCount() );
        }
        return opportunityScoreRequest;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public OpportunitySource convertFrom( Map< String, String > source, OpportunitySource destination ) {
        throw new UnsupportedOperationException( "convertFrom is not supported" );
    }

}
