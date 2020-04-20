package com.owners.gravitas.business.builder;

import static com.owners.gravitas.constants.Constants.BLANK_SPACE;
import static com.owners.gravitas.constants.Constants.COMMA;
import static com.owners.gravitas.constants.Constants.HYPHEN;
import static com.owners.gravitas.constants.Constants.TILDA;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.owners.gravitas.config.BuyerFarmingConfig;
import com.owners.gravitas.dto.request.LeadRequest;

/**
 * The Class LeadDetailsBuilder.
 *
 * @author raviz
 */
@Component( "leadDetailsBuilder" )
public class LeadDetailsBuilder extends AbstractBuilder< LeadRequest, Map< String, String > > {

    /** The config. */
    @Autowired
    private BuyerFarmingConfig config;

    /** The owners elead network. */
    @Value( "${owners.lead.source.ownersEleadNetwork}" )
    private String ownersEleadNetwork;

    /** The affiliate zillow. */
    @Value( "${owners.lead.source.affiliateZillow}" )
    private String affiliateZillow;

    /** The unbounce landing page. */
    @Value( "${owners.lead.source.unbounceLandingPage}" )
    private String unbounceLandingPage;

    /** The owners lending tree. */
    @Value( "${owners.lead.source.ownersLendingTree}" )
    private String ownersLendingTree;

    /** The owners condo. */
    @Value( "${owners.lead.source.ownersCondo}" )
    private String ownersCondo;

    /** The owners com. */
    @Value( "${owners.lead.source.ownersCom}" )
    private String ownersCom;

    /** The lead sources filter1. */
    private List< String > leadSourcesFilter1;

    /** The lead sources filter2. */
    private List< String > leadSourcesFilter2;

    /**
     * Inits the data builder.
     */
    @PostConstruct
    private void initDataBuilder() {
        leadSourcesFilter1 = new ArrayList<>();
        Stream.of( config.getLeadSourcefilter1Str().split( COMMA ) )
                .forEach( email -> leadSourcesFilter1.add( email.trim().toLowerCase() ) );

        leadSourcesFilter2 = new ArrayList<>();
        Stream.of( config.getLeadSourcefilter2Str().split( COMMA ) )
                .forEach( email -> leadSourcesFilter2.add( email.trim().toLowerCase() ) );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public Map< String, String > convertTo( final LeadRequest source, final Map< String, String > destination ) {
        Map< String, String > propertyAddress = destination;
        if (source != null) {
            if (propertyAddress == null) {
                propertyAddress = new HashMap< String, String >();
            }
            final String leadSource = source.getSource();
            final String propertyAddressStr = source.getPropertyAddress();
            if (isNotBlank( propertyAddressStr )) {
                if (ownersCom.equalsIgnoreCase( leadSource ) || ownersCondo.equalsIgnoreCase( leadSource )) {
                    propertyAddress = getPropertyAddressForOwners( propertyAddressStr );
                } else if (affiliateZillow.equalsIgnoreCase( leadSource )) {
                    propertyAddress = getPropertyAddressForZillow( propertyAddressStr );
                } else if (ownersEleadNetwork.equalsIgnoreCase( leadSource )) {
                    propertyAddress.put( "state", source.getState() );
                    propertyAddress.put( "zip", source.getInterestedZipcodes() );
                } else if (unbounceLandingPage.equalsIgnoreCase( leadSource )) {
                    propertyAddress.put( "state", source.getState() );
                    propertyAddress.put( "zip", source.getInterestedZipcodes() );
                } else if (ownersLendingTree.equalsIgnoreCase( leadSource )) {
                    propertyAddress.put( "address", source.getPropertyAddress() );
                    propertyAddress.put( "state", source.getState() );
                    propertyAddress.put( "zip", source.getInterestedZipcodes() );
                } else {
                    propertyAddress.put( "state", source.getState() );
                    propertyAddress.put( "zip", source.getInterestedZipcodes() );
                }
            } else {
                propertyAddress.put( "state", source.getState() );
                propertyAddress.put( "zip", source.getInterestedZipcodes() );
            }
            propertyAddress.put( "mlsId", source.getMlsId() );
            propertyAddress.put( "leadSource", getLeadSource( leadSource ) );
        }
        return propertyAddress;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public LeadRequest convertFrom( final Map< String, String > source, final LeadRequest destination ) {
        throw new UnsupportedOperationException( "convertFrom operation is not supported" );
    }

    /**
     * Gets the property address for owners.
     *
     * @param propertyAddressStr
     *            the property address str
     * @return the property address for owners
     */
    private Map< String, String > getPropertyAddressForOwners( final String propertyAddressStr ) {
        final Map< String, String > propertyAdddressMap = new HashMap<>();
        final String[] propertyData = propertyAddressStr.split( COMMA );
        if (propertyData.length == 2) {
            final String addressLine = propertyData[0].trim();
            final int lastIndexOfSpace = addressLine.lastIndexOf( BLANK_SPACE );
            final String address1 = addressLine.substring( 0, lastIndexOfSpace );
            propertyAdddressMap.put( "address", address1.trim() );
            final String city = addressLine.substring( lastIndexOfSpace + 1, addressLine.length() );
            propertyAdddressMap.put( "city", city.trim() );
            final String[] stateAndZipData = propertyData[1].trim().split( BLANK_SPACE );
            propertyAdddressMap.put( "state", stateAndZipData[0].trim() );
            propertyAdddressMap.put( "zip", stateAndZipData[1].trim() );
        }
        return propertyAdddressMap;
    }

    /**
     * Gets the property address for zillow.
     *
     * @param propertyAddressStr
     *            the property address str
     * @return the property address for zillow
     */
    private Map< String, String > getPropertyAddressForZillow( final String propertyAddress ) {
        String propertyAddressStr = EMPTY;
        if (propertyAddress.contains( HYPHEN )) {
            final String[] propertyData = propertyAddress.split( HYPHEN );
            final String latestPropertyAddress = propertyData[0].trim();
            final StringBuilder stringBuilder = new StringBuilder( latestPropertyAddress );
            final int lastIndexOfSpace = latestPropertyAddress.lastIndexOf( BLANK_SPACE );
            stringBuilder.setCharAt( lastIndexOfSpace, ',' );
            propertyAddressStr = stringBuilder.toString();
        } else if (propertyAddress.contains( TILDA )) {
            propertyAddressStr = propertyAddress.substring( propertyAddress.lastIndexOf( TILDA ) + 2,
                    propertyAddress.length() );
        } else {
            propertyAddressStr = propertyAddress;
        }
        return buildPropertyAddress( propertyAddressStr );
    }

    /**
     * Builds the property address.
     *
     * @param propertyAddress
     *            the property address
     * @return the map
     */
    private Map< String, String > buildPropertyAddress( final String propertyAddress ) {
        final String[] propertyData = propertyAddress.split( COMMA );
        final Map< String, String > propertyAdddressMap = new HashMap<>();
        if (propertyData.length == 4) {
            // only address line 1 is available
            propertyAdddressMap.put( "address", propertyData[0].trim() );
            propertyAdddressMap.put( "city", propertyData[1].trim() );
            propertyAdddressMap.put( "state", propertyData[2].trim() );
            propertyAdddressMap.put( "zip", propertyData[3].trim() );
        } else if (propertyData.length == 5) {
            // address line 1 and address line 2 are available
            propertyAdddressMap.put( "address", propertyData[0].trim() + COMMA + propertyData[1].trim() );
            propertyAdddressMap.put( "city", propertyData[2].trim() );
            propertyAdddressMap.put( "state", propertyData[3].trim() );
            propertyAdddressMap.put( "zip", propertyData[4].trim() );
        }
        return propertyAdddressMap;
    }

    /**
     * Gets the lead source.
     *
     * @param leadSourceStr
     *            the lead source str
     * @return the lead source
     */
    private String getLeadSource( final String leadSourceStr ) {
        String leadSource = leadSourceStr;
        final boolean isOwnersLeadSource = leadSourcesFilter1.stream()
                .anyMatch( leadSourceVal -> leadSourceStr.toLowerCase().contains( leadSourceVal ) );
        if (isOwnersLeadSource) {
            leadSource = "Owners.com";
        } else {
            for ( final String leadSourceVal : leadSourcesFilter2 ) {
                if (leadSourceStr.toLowerCase().contains( leadSourceVal )) {
                    leadSource = leadSourceStr.toLowerCase().replace( leadSourceVal, EMPTY );
                    break;
                }
            }
        }
        return leadSource.trim();
    }

}
