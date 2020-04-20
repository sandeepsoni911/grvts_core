/*
 *
 */
package com.owners.gravitas.business.builder.request;

import static com.owners.gravitas.constants.Constants.BLANK_SPACE;
import static com.owners.gravitas.constants.Constants.COLON;
import static com.owners.gravitas.constants.Constants.DIGIT_REGEX;
import static com.owners.gravitas.constants.Constants.EXCLAMATORY_MARK;
import static com.owners.gravitas.constants.Constants.HYPHEN;
import static com.owners.gravitas.constants.Constants.MILLION;
import static com.owners.gravitas.constants.Constants.MILLION_CONSTANT;
import static com.owners.gravitas.constants.Constants.THOUSAND;
import static com.owners.gravitas.constants.Constants.THOUSAND_CONSTANT;
import static com.owners.gravitas.constants.Constants.ZERO;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.math.BigDecimal;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.builder.AbstractBuilder;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.ReferralExchangeDetails;
import com.owners.gravitas.dto.request.ReferralExchangeRequest;

/**
 * The Class ReferralExchangeOpportunityRequestBuilder.
 *
 * @author shivamm
 */
@Component( "referralExchangeOpportunityRequestBuilder" )
public class ReferralExchangeOpportunityRequestBuilder
        extends AbstractBuilder< OpportunitySource, ReferralExchangeRequest > {

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

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public ReferralExchangeRequest convertTo( final OpportunitySource source,
            final ReferralExchangeRequest destination ) {
        ReferralExchangeRequest request = destination;
        if (source != null) {
            if (request == null) {
                request = new ReferralExchangeRequest();
            }
            setConfigParams( request );
            final ReferralExchangeDetails opportunityData = setOpportunityParams( source, request );
            opportunityData.setPrice( getPriceRange( source.getPriceRanges() ) );
            setComment( source, opportunityData );
        }
        return request;
    }

    /**
     * Gets the price range.
     *
     * @param leadPrice
     *            the price range str
     * @return the price range
     */
    private BigDecimal getPriceRange( final String opportunityPrice ) {
        return ( isNotBlank( opportunityPrice ) ) ? getNumericValue( getPrice( opportunityPrice ) )
                : getNumericValue( ZERO );
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
        final int index = priceRanges.indexOf( HYPHEN );
        final int length = priceRanges.length();
        if (index != -1) {
            price = getPriceRanges( priceRanges, index, length );
        } else {
            price = priceRanges.substring( 0, length - 1 );
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
        if (THOUSAND_CONSTANT.equalsIgnoreCase( lastChar )) {
            priceRangesStr = priceRanges.substring( nextIndex, length - 1 ) + THOUSAND;
        } else if (MILLION_CONSTANT.equalsIgnoreCase( lastChar )) {
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
    private ReferralExchangeDetails setOpportunityParams( final OpportunitySource source,
            final ReferralExchangeRequest request ) {
        final ReferralExchangeDetails opportunityData = request.getData();
        opportunityData.setFirstName( source.getPrimaryContact().getFirstName() );
        opportunityData.setLastName( source.getPrimaryContact().getLastName() );
        opportunityData.setEmail( source.getPrimaryContact().getEmails().iterator().next() );
        opportunityData.setPhone( getPhone( source.getPrimaryContact() ) );
        opportunityData.setState( StringUtils.isNotBlank( source.getPropertyState() ) ? source.getPropertyState()
                : Constants.NOT_AVAILABLE );
        opportunityData.setCity( StringUtils.isNotBlank( source.getPropertyCity() ) ? source.getPropertyCity()
                : Constants.NOT_AVAILABLE );
        opportunityData.setSubmissionType( SUBMISSION_TYPE );
        return opportunityData;
    }

    /**
     * Gets the phone.
     *
     * @param contact
     *            the contact
     * @return the phone
     */
    private String getPhone( final Contact contact ) {
        return ( CollectionUtils.isNotEmpty( contact.getPhoneNumbers() ) )
                ? contact.getPhoneNumbers().iterator().next().getNumber() : EMPTY;
    }

    /**
     * Sets the config params.
     *
     * @param request
     *            the new config params
     */
    private void setConfigParams( final ReferralExchangeRequest request ) {
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
     * @param opportunityData
     *            the opportunity data
     */
    private void setComment( final OpportunitySource source, final ReferralExchangeDetails opportunityData ) {
        final StringBuilder comments = new StringBuilder();
        comments.append( INQUIRY_TYPE + COLON );
        comments.append( source.getLeadRequestType() );
        comments.append( EXCLAMATORY_MARK + EXCLAMATORY_MARK );
        if (isNotBlank( source.getPropertyAddress() )) {
            comments.append( BLANK_SPACE + PROPERTY_ADDRESS + COLON );
            comments.append( source.getPropertyAddress() );
        }
        opportunityData.setComments( comments.toString().trim() );
    }

    /**
     * Convert from.
     *
     * @param source
     *            the source
     * @param destination
     *            the destination
     * @return the opportunity source
     */
    @Override
    public OpportunitySource convertFrom( final ReferralExchangeRequest source, final OpportunitySource destination ) {
        throw new UnsupportedOperationException( "convertFrom operation is not supported" );
    }
}
