package com.owners.gravitas.business.builder.request;

import static com.owners.gravitas.constants.Constants.AT_THE_RATE;
import static com.owners.gravitas.constants.Constants.BLANK;
import static com.owners.gravitas.constants.Constants.COMMA;
import static com.owners.gravitas.constants.Constants.NULL_STRING;
import static com.owners.gravitas.constants.Constants.REG_EXP_NON_NUMERICS;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.builder.AbstractBuilder;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.enums.LeadRequestType;

/**
 * The Class LeadScoreRequestBuilder.
 *
 * @author vishwanathm
 */
@Component
public class LeadScoreRequestBuilder extends AbstractBuilder< LeadRequest, Map< String, String > > {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( LeadScoreRequestBuilder.class );

    /** The Constant UNKNOWN_VALUE. */
    private static final String UNKNOWN_VALUE = "Unknown";

    /** The Constant OTHER_VALUE. */
    private static final String OTHER_VALUE = "Other";

    /** The buyer readiness values. */
    private final List< String > buyerReadinessValues = new ArrayList<>();

    /** The request type values. */
    private final List< String > requestTypeValues = new ArrayList<>();

    /** The working with realtors values. */
    private final List< String > workingWithRealtorsValues = new ArrayList<>();

    /** The pre mortgage values. */
    private final List< String > preMortgageValues = new ArrayList<>();

    /** The invalid phone values. */
    private final List< String > invalidPhoneValues = new ArrayList<>();

    @Value( value = "${crm.buyer.readiness.timeline.values}" )
    private String buyerReadinessTimeLineValues;

    /** The request types. */
    @Value( value = "${crm.request.type.values}" )
    private String requestTypes;

    /** The working with realtor values. */
    @Value( value = "${crm.working.with.realtor.values}" )
    private String workingWithRealtorValues;

    /** The pre approved for mortgage values. */
    @Value( value = "${crm.pre.approved.mortgage.values}" )
    private String preApprovedForMortgageValues;

    /** The invalid phone numbers. */
    @Value( value = "${crm.invalid.phone.number.values}" )
    private String invalidPhoneNumbers;

    /**
     * Inits the data builder.
     */
    @PostConstruct
    private void initDataBuilder() {
        Stream.of( buyerReadinessTimeLineValues.split( COMMA ) )
                .forEach( value -> buyerReadinessValues.add( value.trim() ) );
        Stream.of( requestTypes.split( COMMA ) ).forEach( value -> this.requestTypeValues.add( value.trim() ) );
        Stream.of( workingWithRealtorValues.split( COMMA ) )
                .forEach( value -> workingWithRealtorsValues.add( value.trim() ) );
        Stream.of( preApprovedForMortgageValues.split( COMMA ) )
                .forEach( value -> preMortgageValues.add( value.trim() ) );
        Stream.of( invalidPhoneNumbers.split( COMMA ) ).forEach( value -> invalidPhoneValues.add( value.trim() ) );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public Map< String, String > convertTo( final LeadRequest source, final Map< String, String > destination ) {
        Map< String, String > leadScoreReq = destination;
        if (source != null) {
            if (leadScoreReq == null) {
                leadScoreReq = new HashMap<>();
            }
            leadScoreReq.put( "leadSource", source.getSource() );
            leadScoreReq.put( "buyerReadinessTimeline",
                    checkAgainstDefinedForBlankAsNull( buyerReadinessValues, source.getBuyerReadinessTimeline(), OTHER_VALUE ) );
            leadScoreReq.put( "requestType",
                    checkAgainstDefinedForBlankAsNull( requestTypeValues, getRequestType( source.getRequestType() ), OTHER_VALUE ) );
            leadScoreReq.put( "state", source.getState() );
            leadScoreReq.put( "workingWithRealtor",
                    checkAgainstDefined( workingWithRealtorsValues, source.getWorkingWithRealtor(), UNKNOWN_VALUE ) );
            leadScoreReq.put( "preApprovedForMortgage",
                    checkAgainstDefined( preMortgageValues, source.getPreApprovedForMortgage(), UNKNOWN_VALUE ) );
            leadScoreReq.put( "isInvalidPhone",
                    isValidPhoneNumber( source.getPhone(), source.getSource() ).toString() );
            leadScoreReq.put( "domain", getDomain( source.getEmail() ) );
        }
        return leadScoreReq;
    }

    /**
     * Convert to.
     *
     * @param source
     *            the source
     * @param destination
     *            the destination
     * @return the map
     */
    public Map< String, String > convertTo( final LeadSource source ) {
        final Map< String, String > leadScoreReq = new HashMap<>();
        if (source != null) {
            leadScoreReq.put( "leadSource", source.getSource() );
            leadScoreReq.put( "buyerReadinessTimeline",
                    checkAgainstDefinedForBlankAsNull( buyerReadinessValues, source.getBuyerReadinessTimeline(), OTHER_VALUE ) );
            leadScoreReq.put( "requestType",
                    checkAgainstDefinedForBlankAsNull( requestTypeValues, getRequestType( source.getRequestType() ), OTHER_VALUE ) );
            leadScoreReq.put( "state", source.getState() );
            leadScoreReq.put( "workingWithRealtor",
                    checkAgainstDefined( workingWithRealtorsValues, source.getWorkingWithRealtor(), UNKNOWN_VALUE ) );
            leadScoreReq.put( "preApprovedForMortgage",
                    checkAgainstDefined( preMortgageValues, source.getPreApprovedForMortgage(), UNKNOWN_VALUE ) );
            leadScoreReq.put( "isInvalidPhone",
                    isValidPhoneNumber( source.getPhone(), source.getSource() ).toString() );
            leadScoreReq.put( "domain", getDomain( source.getEmail() ) );
        }
        return leadScoreReq;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public LeadRequest convertFrom( final Map< String, String > source, final LeadRequest destination ) {
        throw new UnsupportedOperationException( "convertFrom is not supported" );
    }

    /**
     * Gets the domain.
     *
     * @param email
     *            the email
     * @return the domain
     */
    private String getDomain( final String email ) {
        String domain = email;
        if (StringUtils.isNotBlank( email )) {
            final int index = email.indexOf( AT_THE_RATE );
            if (index != -1) {
                domain = email.substring( index + 1, email.length() );
            }
        }
        return domain;
    }

    /**
     * Gets the request type.
     *
     * @param requestType
     *            the request type
     * @return the request type
     */
    private String getRequestType( final String requestType ) {
        String inputType = null;
        try {
            if (isNotBlank( requestType )) {
                inputType = LeadRequestType.valueOf( requestType ).getType();
            }
        } catch ( final IllegalArgumentException e ) {
            LOGGER.debug( "invalid request type received", e );
            inputType = requestType;
        }
        return inputType;
    }

    /**
     * Checks if is valid phone number.
     *
     * @param phone
     *            the phone
     * @param leadSource
     *            the lead source
     * @return true, if is valid phone number
     */
    private Integer isValidPhoneNumber( final String phone, final String leadSource ) {
        boolean validPhone = false;
        // convert (987) 654-3210 to 9876543210
        if (StringUtils.isNotBlank( phone )) {
            final String unformattedPhone = phone.replaceAll( REG_EXP_NON_NUMERICS, BLANK );
            if ("self generated".equalsIgnoreCase( leadSource )) {
                validPhone = true;
            } else if (StringUtils.isNotBlank( unformattedPhone ) && !"unknown".equalsIgnoreCase( unformattedPhone )
                    && unformattedPhone.length() == 10 && !invalidPhoneValues.contains( unformattedPhone )) {
                validPhone = true;
            }
        }
        return !validPhone ? 1 : 0;
    }

    /**
     * Check against defined.
     *
     * @param preDefinedValues
     *            the pre defined values
     * @param valueFromSf
     *            the value from sf
     * @param defaultValue
     *            the default value
     * @return the string
     */
    private String checkAgainstDefined( final List< String > preDefinedValues, final String valueFromSf,
            final String defaultValue ) {
        String finalValue = defaultValue;
        if (StringUtils.isNotBlank( valueFromSf )) {
            if (preDefinedValues.contains( valueFromSf )) {
                finalValue = valueFromSf;
            }
        }
        return finalValue;
    }

    /**
     * Check against defined for null.
     *
     * @param preDefinedValues
     *            the pre defined values
     * @param valueFromSf
     *            the value from sf
     * @param defaultValue
     *            the default value
     * @return the string
     */
    private String checkAgainstDefinedForBlankAsNull( final List< String > preDefinedValues, final String valueFromSf,
            final String defaultValue ) {
        String finalValue = defaultValue;
        if (StringUtils.isNotBlank( valueFromSf )) {
            if (preDefinedValues.contains( valueFromSf )) {
                finalValue = valueFromSf;
            }
        } else {
            finalValue = NULL_STRING;
        }
        return finalValue;
    }
}
