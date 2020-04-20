/*
 *
 */
package com.owners.gravitas.business.builder.request;

import static com.owners.gravitas.constants.Constants.BLANK_SPACE;
import static com.owners.gravitas.constants.Constants.COLON;
import static com.owners.gravitas.constants.Constants.CRM_APPEND_SEPERATOR;
import static com.owners.gravitas.constants.Constants.DIGIT_REGEX;
import static com.owners.gravitas.constants.Constants.EXCLAMATORY_MARK;
import static com.owners.gravitas.constants.Constants.HYPHEN;
import static com.owners.gravitas.constants.Constants.MILLION;
import static com.owners.gravitas.constants.Constants.MILLION_CONSTANT;
import static com.owners.gravitas.constants.Constants.NOT_AVAILABLE;
import static com.owners.gravitas.constants.Constants.THOUSAND;
import static com.owners.gravitas.constants.Constants.THOUSAND_CONSTANT;
import static com.owners.gravitas.constants.Constants.ZERO;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.builder.AbstractBuilder;
import com.owners.gravitas.dto.ReferralExchangeDetails;
import com.owners.gravitas.dto.request.ReferralExchangeRequest;
import com.owners.gravitas.dto.response.PropertyDetailsResponse;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.service.PropertyService;

/**
 * The Class ReferralExchangeRequestBuilder.
 *
 * @author vishwanathm
 */
@Component( "referralExchangeRequestBuilder" )
public class ReferralExchangeRequestBuilder extends AbstractBuilder< LeadSource, ReferralExchangeRequest > {

    private static final Logger LOGGER = LoggerFactory.getLogger( ReferralExchangeRequestBuilder.class );

    /** The Constant submissionType. */
    private static final String SUBMISSION_TYPE = "self_service";

    /** The Constant INQUIRY_TYPE. */
    private static final String INQUIRY_TYPE = "Inquiry Type";

    /** The Constant PROPERTY_ADDRESS. */
    private static final String PROPERTY_ADDRESS = "Property Address";

    /** The token. */
    @Value( "${referral.exchange.token}" )
    private String token;

    /** The version. */
    @Value( "${referral.exchange.version}" )
    private String version;

    /** The test flag. */
    @Value( "${referral.exchange.test.flag}" )
    private boolean testFlag;

    /** The api name. */
    @Value( "${referral.exchange.api.name}" )
    private String apiName;

    /** The property details service. */
    @Autowired
    private PropertyService propertyDetailsService;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public ReferralExchangeRequest convertTo( final LeadSource source, final ReferralExchangeRequest destination ) {
        ReferralExchangeRequest request = destination;
        if (source != null) {
            if (request == null) {
                request = new ReferralExchangeRequest();
            }
            setConfigParams( request );
            final ReferralExchangeDetails leadData = setLeadParams( source, request );
            setPropertyParams( source.getListingId(), leadData, source );
            setComment( source, leadData );
        }
        return request;
    }

    /**
     * Sets the property params.
     *
     * @param listingId
     *            the listing id
     * @param leadData
     *            the lead data
     */
    private void setPropertyParams( final String listingId, final ReferralExchangeDetails leadData,
            final LeadSource source ) {
        if (isNotBlank( listingId )) {
            PropertyDetailsResponse propertyDetails = null;
            final String[] listingArray = listingId.split( CRM_APPEND_SEPERATOR );
            final String recentListingId = listingArray[listingArray.length - 1];
            try {
                propertyDetails = propertyDetailsService.getPropertyDetails( recentListingId );
            } catch ( ApplicationException e ) {
                LOGGER.debug( "Problem getting property details from Owners API, hence we are computing from CRM ", e );
                getListingIdDetails( leadData, source );
            }
            if (null != propertyDetails) {
                leadData.setCity( propertyDetails.getData().getPropertyAddress().getCity() );
                leadData.setPrice( getNumericValue( propertyDetails.getData().getPrice() ) );
                leadData.setBeds( getNumericValue( propertyDetails.getData().getBedRooms() ) );
                leadData.setBaths( propertyDetails.getData().getBathRooms() );
            }
        } else {
            getListingIdDetails( leadData, source );
        }
    }

    /**
     * Gets the details for listing ID.
     *
     * @param leadData
     *            the lead data
     * @param source
     *            the source
     * @return the details for listing ID
     */
    private ReferralExchangeDetails getListingIdDetails( final ReferralExchangeDetails leadData, final LeadSource source ) {
        BigDecimal price = null;
        if (isNotBlank( source.getPriceRanges() )) {
            final String priceStr = getPrice( source.getPriceRanges() );
            price = getNumericValue( priceStr );
        } else {
            price = getPriceRange( source.getPriceRange() );
        }
        leadData.setPrice( price );
        leadData.setCity( ( isNotBlank( source.getPropertyCity() ) ) ? source.getPropertyCity() : NOT_AVAILABLE );
        return leadData;
    }

    /**
     * Gets the price range.
     *
     * @param leadPrice
     *            the price range str
     * @return the price range
     */
    private BigDecimal getPriceRange( final String leadPrice ) {
        return ( isNotBlank( leadPrice ) ) ? getNumericValue( getPrice( leadPrice ) ) : getNumericValue( ZERO );
    }

    /**
     * Gets the price.
     *
     * @param priceRange
     *            the price range
     * @return the price
     */
    private String getPrice( final String priceRanges ) {
        String price = EMPTY;
        final int indexHyphen = priceRanges.indexOf( HYPHEN );
        final int index = priceRanges.indexOf( CRM_APPEND_SEPERATOR );
        final int length = priceRanges.length();
        if (indexHyphen != -1) {
            price = getPriceRanges( priceRanges, indexHyphen, length );
        } else if (priceRanges.substring( priceRanges.length() - 2, priceRanges.length() - 1 )
                .equalsIgnoreCase( MILLION_CONSTANT )) {
            price = priceRanges.substring( 0, 1 ) + MILLION;
        } else if (index != -1) {
            price = priceRanges.substring( indexHyphen + 1, index );
        }
        return price.trim();
    }

    /**
     * Gets the price ranges.
     *
     * @param priceRanges
     *            the price ranges
     * @param index
     *            the index
     * @param length
     *            the length
     * @return the price ranges
     */
    private String getPriceRanges( final String priceRanges, final int index, final int length ) {
        String priceRangesStr;
        final int nextIndex = index + 1;
        final String lastChar = priceRanges.substring( length - 1 );
        if (THOUSAND_CONSTANT.equalsIgnoreCase( lastChar ))
            priceRangesStr = priceRanges.substring( nextIndex, length - 1 ) + THOUSAND;
        else if (MILLION_CONSTANT.equalsIgnoreCase( lastChar )) {
            priceRangesStr = priceRanges.substring( nextIndex, length - 1 ) + MILLION;
        } else {
            priceRangesStr = priceRanges.substring( nextIndex, length );
        }
        return priceRangesStr;
    }

    /**
     * Sets the lead params.
     *
     * @param source
     *            the source
     * @param request
     *            the request
     * @return the referral exchange lead
     */
    private ReferralExchangeDetails setLeadParams( final LeadSource source, ReferralExchangeRequest request ) {
        final ReferralExchangeDetails leadData = request.getData();
        leadData.setFirstName( source.getFirstName() );
        leadData.setLastName( source.getLastName() );
        leadData.setEmail( source.getEmail() );
        leadData.setPhone( source.getPhone() );
        leadData.setState( source.getState() );
        leadData.setSubmissionType( SUBMISSION_TYPE );
        return leadData;
    }

    /**
     * Sets the config params.
     *
     * @param request
     *            the new config params
     */
    private void setConfigParams( ReferralExchangeRequest request ) {
        request.setToken( token );
        request.setVersion( version );
        request.setTestFlag( testFlag );
        request.setApiName( apiName );
    }

    /**
     * Gets the numeric value.
     *
     * @param inputNumber
     *            the input number
     * @return the numeric value
     */
    private BigDecimal getNumericValue( final String inputNumber ) {
        return ( inputNumber.matches( DIGIT_REGEX ) ) ? new BigDecimal( inputNumber ) : new BigDecimal( ZERO );
    }

    /**
     * Sets the comment.
     *
     * @param source
     *            the source
     * @param leadData
     *            the lead data
     */
    private void setComment( final LeadSource source, final ReferralExchangeDetails leadData ) {
        final StringBuilder comments = new StringBuilder();
        comments.append( INQUIRY_TYPE + COLON );
        comments.append( source.getRequestType() );
        comments.append( EXCLAMATORY_MARK + EXCLAMATORY_MARK );
        if (isNotBlank( source.getPropertyAddress() )) {
            comments.append( BLANK_SPACE + PROPERTY_ADDRESS + COLON );
            comments.append( source.getPropertyAddress() );
        }
        leadData.setComments( comments.toString().trim() );
    }

    /**
     * Convert from.
     *
     * @param source
     *            the source
     * @param destination
     *            the destination
     * @return the lead source
     */
    @Override
    public LeadSource convertFrom( final ReferralExchangeRequest source, final LeadSource destination ) {
        throw new UnsupportedOperationException( "convertFrom operation is not supported" );
    }
}
