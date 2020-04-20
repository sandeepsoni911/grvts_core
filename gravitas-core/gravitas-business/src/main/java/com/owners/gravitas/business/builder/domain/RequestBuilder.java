package com.owners.gravitas.business.builder.domain;

import static com.owners.gravitas.constants.Constants.GRAVITAS;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.builder.AbstractBuilder;
import com.owners.gravitas.domain.Request;
import com.owners.gravitas.enums.RequestType;

/**
 * The Class RequestBuilder.
 *
 * @author harshads
 */
@Component
public class RequestBuilder extends AbstractBuilder< OpportunitySource, Request > {

    /**
     * convert {@link OpportunitySource} to {@link Request}.
     *
     * @param source
     *            the source
     * @param destination
     *            the destination
     * @return the request
     */
    @Override
    public Request convertTo( OpportunitySource source, Request destination ) {
        Request request = destination;
        if (source != null) {
            if (request == null) {
                request = new Request();
            }
            if (source.getLeadRequestType() != null) {
                switch ( source.getLeadRequestType() ) {
                    case "Make An Offer":
                        request.setType( RequestType.BUYER_OFFER );
                        request.setPreApprovaedForMortgage( source.getPreApprovedForMortgage() );
                        request.setOfferAmount( source.getOfferAmount() );
                        request.setEarnestMoneyDeposit( source.getEarnestMoneyDeposit() );
                        request.setPurchaseMethod( source.getPurchaseMethod() );
                        request.setDownPayment( source.getDownPayment() );
                        break;
                    case "Schedule a Tour":
                        request.setType( RequestType.APPOINTMENT );
                        request.setPropertyTourInfo( source.getPropertyTourInformation() );
                        break;
                    case "Request Information":
                        request.setType( RequestType.INQUIRY );
                        break;
                    default:
                        request = null;
                }
                if (request != null) {
                    request.setConverted( false );
                    request.setListingId( source.getListingId() );
                    request.setOpportunityNotes( source.getNotes() );
                    request.setLastModifiedDtm( new Date().getTime() );
                    request.setCreatedBy( GRAVITAS );
                    request.setCreatedDtm( new Date().getTime() );
                }
            }
        }
        return request;
    }

    /** Method is not supported. */
    @Override
    public OpportunitySource convertFrom( Request source, OpportunitySource destination ) {
        throw new UnsupportedOperationException( "convertFrom is not supported" );
    }
}
