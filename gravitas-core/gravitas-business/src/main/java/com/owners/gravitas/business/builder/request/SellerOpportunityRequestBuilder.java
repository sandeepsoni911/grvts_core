package com.owners.gravitas.business.builder.request;

import static com.owners.gravitas.constants.Constants.BLANK_SPACE;
import static com.owners.gravitas.constants.Constants.OPPORTUNITY_PROBABILITY;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import com.owners.gravitas.business.builder.AbstractBuilder;
import com.owners.gravitas.dto.Property;
import com.owners.gravitas.dto.SellerAddress;
import com.owners.gravitas.dto.crm.request.CRMOpportunityRequest;
import com.owners.gravitas.dto.request.OpportunityRequest;
import com.owners.gravitas.enums.CustomerType;
import com.owners.gravitas.enums.OpportunityStage;

/**
 * The Class SellerOpportunityRequestBuilder.
 *
 * @author harshads
 */
@Component( "sellerOpportunityRequestBuilder" )
public class SellerOpportunityRequestBuilder extends AbstractBuilder< OpportunityRequest, CRMOpportunityRequest > {

    /**
     * Converts OpportunityRequest to CRMOpportunityRequest.
     *
     * @param source
     *            is source request.
     * @param destination
     *            is destination object to be created.
     */
    @Override
    public CRMOpportunityRequest convertTo( OpportunityRequest source, CRMOpportunityRequest destination ) {
        CRMOpportunityRequest crmOpportunity = destination;
        if (source != null) {
            if (crmOpportunity == null) {
                crmOpportunity = new CRMOpportunityRequest();
            }
            crmOpportunity.setName( source.getPropertyOrder().getProperty().getListingId() );
            crmOpportunity.setListingId( source.getPropertyOrder().getProperty().getListingId() );
            crmOpportunity.setPackageType( source.getPropertyOrder().getOrderType() );
            final SellerAddress propertyAddress = source.getPropertyOrder().getProperty().getAddress();
            if (null != propertyAddress) {
                crmOpportunity.setPropertyAddress(
                        getAddress( propertyAddress.getAddress1(), propertyAddress.getAddress2() ) );
                crmOpportunity.setPropertyCity( propertyAddress.getCity() );
                crmOpportunity.setState( propertyAddress.getState() );
                crmOpportunity.setPropertyZip( propertyAddress.getZip() );
            }

            // attributes from systems
            crmOpportunity.setComments( getNotes( source ) );
            // Adding 60 days
            crmOpportunity.setExpectedContractDate( new DateTime().plusDays( 60 ) );
            crmOpportunity.setStageName( OpportunityStage.PROSPECTING.getStage() );
            crmOpportunity.setType( CustomerType.NEW_CUSTOMER.getType() );
            crmOpportunity.setProbability( OPPORTUNITY_PROBABILITY );
        }
        return crmOpportunity;
    }

    /**
     * Gets the address.
     *
     * @param address1
     *            the address1
     * @param address2
     *            the address2
     * @return the address
     */
    private String getAddress( String address1, String address2 ) {
        String address = address1;
        if (StringUtils.isNotBlank( address2 )) {
            address += BLANK_SPACE + address2;
        }
        return address;
    }

    /**
     * Gets the notes.
     *
     * @param source
     *            the source
     * @return the notes
     */
    private String getNotes( final OpportunityRequest source ) {
        final StrBuilder notes = new StrBuilder();
        notes.appendln( appendParam( "Main Contact Number ", source.getSeller().getMainContactNumer() ) );
        final Property property = source.getPropertyOrder().getProperty();
        notes.appendln( appendParam( "Property Type", property.getPropertyType() ) );
        notes.appendln( appendParam( "Price", property.getPrice() ) );
        notes.appendln( appendParam( "Number of Beds", property.getNumberBeds() ) );
        notes.appendln( appendParam( "Number of Full Baths", property.getNumberFullBaths() ) );
        notes.appendln( appendParam( "Number of Half Baths", property.getNumberHalfBaths() ) );
        notes.appendln( appendParam( "Square Feet", property.getSquareFeet() ) );
        return notes.toString();
    }

    /**
     * Append if not null.
     *
     * @param key
     *            the key
     * @param value
     *            the value
     * @return the string
     */
    private String appendParam( final String key, final String value ) {
        String paramValue = "NA";
        if (StringUtils.isNotBlank( value )) {
            paramValue = value;
        }
        return key + " - " + paramValue;
    }

    /**
     * Method is not supported.
     */
    @Override
    public OpportunityRequest convertFrom( CRMOpportunityRequest source, OpportunityRequest destination ) {
        throw new UnsupportedOperationException( "convertFrom is not supported" );
    }

}
