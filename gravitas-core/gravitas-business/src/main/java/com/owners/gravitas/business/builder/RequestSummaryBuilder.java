package com.owners.gravitas.business.builder;

import static com.owners.gravitas.constants.Constants.CRM_APPEND_SEPERATOR;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * The Class AgentBuilder.
 *
 * @author vishwanathm
 */
@Component( "requestSummaryBuilder" )
public class RequestSummaryBuilder {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( RequestSummaryBuilder.class );

    /**
     * This method converts List object to Agent
     * object.
     *
     * @param records
     *            is the dto object to be converted to domain.
     * @param destination
     *            the destination
     * @return AgentHolder object
     */
    public String convertTo( String[] requests ) {
        final StringBuilder summaryBuilder = new StringBuilder();
        if (requests != null && requests.length > 0) {
            int i = 0;
            try {
                JSONObject requestJson = null;
                for ( String request : requests ) {
                    if (StringUtils.isNotBlank( request )) {
                        if (i > 0) {
                            summaryBuilder.append( CRM_APPEND_SEPERATOR + "\n" );
                        }
                        requestJson = ( JSONObject ) new JSONParser().parse( request );
                        createRequestSummary( summaryBuilder, requestJson );
                    }
                    i++;
                }
            } catch ( ParseException e ) {
                LOGGER.info( "Problem while generating Oppportunity Request summary ", e );
            }
        }
        return summaryBuilder.toString();
    }

    /**
     * Creates the request summary.
     *
     * @param requestSummary
     *            the request summary
     * @param requestJson
     *            the request json
     */
    private void createRequestSummary( final StringBuilder requestSummary, final JSONObject requestJson ) {
        requestSummary.append( requestJson.get( "requestType" ) + " : <" );
        requestSummary.append( "Opportunity Notes : " + requestJson.get( "comments" ) + ";" );
        requestSummary.append( "Listing Id : " + requestJson.get( "listingId" ) + ";" );
        requestSummary.append( "Lead Message : " + requestJson.get( "message" ) );

        switch ( String.valueOf( requestJson.get( "requestType" ) ) ) {
            case "MAKE_OFFER":
                requestSummary.append( ";" );
                requestSummary
                        .append( "Pre approved for mortgage : " + requestJson.get( "preApprovedForMortgage" ) + ";" );
                requestSummary.append( "Offer amount : " + requestJson.get( "offerAmount" ) + ";" );
                requestSummary.append( "Earnest Money Deposit : " + requestJson.get( "earnestMoneyDeposit" ) + ";" );
                requestSummary.append( "Purchase Method : " + requestJson.get( "purchaseMethod" ) + ";" );
                requestSummary.append( "Down Payment : " + requestJson.get( "downPayment" ) );
                break;
            case "SCHEDULE_TOUR":
                requestSummary.append( ";" );
                requestSummary.append( "Property Tour Information : " + requestJson.get( "propertyTourInformation" ) );
                break;
        }
        requestSummary.append( ">" );
    }

}
