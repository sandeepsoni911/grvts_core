package com.owners.gravitas.business.builder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.owners.gravitas.amqp.AlertDetails;
import com.owners.gravitas.amqp.ClientEventDetails;
import com.owners.gravitas.amqp.UserTrackingDetail;
import com.owners.gravitas.business.PropertyBusinessService;
import com.owners.gravitas.constants.Constants;
import static com.owners.gravitas.constants.NotificationParameters.FROM_EMAIL;
import static com.owners.gravitas.constants.NotificationParameters.TO_EMAIL;
import static com.owners.gravitas.constants.NotificationParameters.USER_ID;
import static com.owners.gravitas.constants.NotificationParameters.SUBJECT;
import static com.owners.gravitas.constants.NotificationParameters.CITY_STATE_ZIP;
import static com.owners.gravitas.constants.NotificationParameters.ADDRESS_LINE1_LINE2;
import static com.owners.gravitas.constants.NotificationParameters.BUYER_FIRST_NAME;
import static com.owners.gravitas.constants.NotificationParameters.VISITED_DATE;
import static com.owners.gravitas.constants.NotificationParameters.LAST_VISITED_DATE;
import static com.owners.gravitas.constants.NotificationParameters.TIME_SPENT_ON_SITE_IN_SEC;
import com.owners.gravitas.dto.PropertyAddress;
import com.owners.gravitas.dto.response.PropertyDetailsResponse;
import com.owners.gravitas.exception.ApplicationException;

/**
 * 
 * @author gururasm
 *
 */
@Component
public class WebActivityEmailContentBuilder {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( WebActivityEmailContentBuilder.class );

    /** The property business service. */
    @Autowired
    private PropertyBusinessService propertyBusinessService;

    /** The from email. */
    @Value( "${buyer.farming.insideSales.web.activity.followup.from.email}" )
    private String fromEmail;

    /**
     * build email content for respective alert type
     * 
     * @param alertDetails
     * @param webActivityEmailDetail
     */
    public Map< String, Object > buildEmailContent( AlertDetails alertDetails, final String emailDynamicParametersStr,
            final String contactEmail, final String buyerName, final String subject ) {
        final Map< String, Object > sourceMap = setUpBasicParams( contactEmail, buyerName );
        if (CollectionUtils.isNotEmpty( alertDetails.getClientEventDetails() )) {
            return buildSourceMapFromClientEventDetails( alertDetails.getClientEventDetails(),
                    emailDynamicParametersStr, subject, sourceMap );
        } else if (null != alertDetails.getUserTrackingDetail()) {
            return buildSourceMapFromUserTrackingDetail( alertDetails.getUserTrackingDetail(), sourceMap, subject );
        }
        return sourceMap;
    }

    /**
     * Builds the source map.
     *
     * @param clientEventDetails
     *            the client event details
     * @param emailDynamicParametersStr
     *            the email dynamic parameters str
     * @param contactEmail
     *            the contact email
     * 
     * @param buyerName
     *            the buyer name
     * @return the map
     */
    private Map< String, Object > buildSourceMapFromClientEventDetails(
            final List< ClientEventDetails > clientEventDetails, final String emailDynamicParametersStr,
            final String subject, final Map< String, Object > sourceMap ) {
        final String[] emailDynamicParametersArray = emailDynamicParametersStr.split( Constants.COMMA );

        String dynamicParameterValue = "no value";
        try {
            if (CollectionUtils.isNotEmpty( clientEventDetails )) {
                sourceMap.put( USER_ID, clientEventDetails.get( 0 ).getUserId() );
                final String recentActedOnListingId = getRecentActedOnPropertyListingId( clientEventDetails );
                LOGGER.info( "Retrieving recent acted on property details for listing id " + recentActedOnListingId );
                final PropertyDetailsResponse propertyDetails = propertyBusinessService
                        .getPropertyDetails( recentActedOnListingId );
                final PropertyAddress propertyAddress = propertyDetails.getData().getPropertyAddress();
                if (CITY_STATE_ZIP.equals( emailDynamicParametersArray[0] )) {
                    dynamicParameterValue = propertyAddress.getCity() + " , " + propertyAddress.getState() + " , "
                            + propertyAddress.getZip();
                } else if (ADDRESS_LINE1_LINE2.equals( emailDynamicParametersArray[0] )) {
                    dynamicParameterValue = propertyAddress.getAddressLine1();
                    if (StringUtils.isNotEmpty( propertyAddress.getAddressLine2() )) {
                        dynamicParameterValue = dynamicParameterValue + " , " + propertyAddress.getAddressLine2();
                    }
                }
                LOGGER.info( "Dynamic parameter value for listing id" + recentActedOnListingId + " is :"
                        + dynamicParameterValue );
            }
        } catch ( final ApplicationException e ) {
            LOGGER.error( "problem getting property details from owners.com", e );
        }
        sourceMap.put( emailDynamicParametersArray[0], dynamicParameterValue );
        sourceMap.put( SUBJECT, subject + " " + dynamicParameterValue );

        return sourceMap;
    }

    private Map< String, Object > buildSourceMapFromUserTrackingDetail( final UserTrackingDetail userTrackingDetail,
            final Map< String, Object > sourceMap, final String subject ) {

        if (null != userTrackingDetail) {
            sourceMap.put( USER_ID, userTrackingDetail.getUserId() );
            sourceMap.put( VISITED_DATE, userTrackingDetail.getVisitedDate() );
            sourceMap.put( LAST_VISITED_DATE, userTrackingDetail.getLastVisitedDate() );
            sourceMap.put( TIME_SPENT_ON_SITE_IN_SEC, userTrackingDetail.getTimeSpentOnSiteInSec() );
            sourceMap.put( SUBJECT, subject );
        }

        return sourceMap;
    }

    /**
     * Gets the recent acted on property listing id.
     *
     * @param clientEventDetails
     *            the client event details
     * @return the recent acted on property listing id
     */
    private String getRecentActedOnPropertyListingId( final List< ClientEventDetails > clientEventDetails ) {
        clientEventDetails.sort( ( c1, c2 ) -> {
            return Long.compare( c2.getGeneratedOn(), c1.getGeneratedOn() );
        } );
        final ClientEventDetails eventDetails = clientEventDetails.get( 0 );
        return eventDetails.getListingId();
    }

    private Map< String, Object > setUpBasicParams( String contactEmail, String buyerName ) {
        final Map< String, Object > sourceMap = new HashMap< String, Object >();
        sourceMap.put( FROM_EMAIL, fromEmail );
        sourceMap.put( TO_EMAIL, contactEmail );
        sourceMap.put( BUYER_FIRST_NAME, buyerName );
        return sourceMap;
    }
}
