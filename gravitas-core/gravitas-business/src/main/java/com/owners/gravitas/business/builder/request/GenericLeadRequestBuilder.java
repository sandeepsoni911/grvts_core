package com.owners.gravitas.business.builder.request;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import com.owners.gravitas.business.builder.AbstractBuilder;
import com.owners.gravitas.dto.request.GenericLeadRequest;

/**
 * The Class GenericLeadRequestBuilder.
 *
 * @author vishwanathm
 */
@Component( "genericLeadRequestBuilder" )
public class GenericLeadRequestBuilder extends AbstractBuilder< Map< String, List< String > >, GenericLeadRequest > {

    /**
     * Converts Map< String, String[] > to GenericLeadRequest.
     *
     * @param source
     *            is source request.
     * @param destination
     *            is destination object to be created.
     * @return the generic lead request
     */
    public GenericLeadRequest convertTo( final Map< String, List< String > > source,
            final GenericLeadRequest destination ) {
        GenericLeadRequest request = destination;
        if (MapUtils.isNotEmpty( source )) {
            if(request==null){
                request = new GenericLeadRequest();
            }
            request.setFirstName( getValue( source.get( "firstName" ) ) );
            request.setAdditionalPropertyData( getValue( source.get( "additionalPropertyData" ) ) );
            request.setAlId( getValue( source.get( "alId" ) ) );
            request.setBuyerLeadQuality( getValue( source.get( "buyerLeadQuality" ) ) );
            request.setBuyerReadinessTimeline( getValue( source.get( "buyerReadinessTimeline" ) ) );
            request.setComments( getValue( source.get( "comments" ) ) );
            request.setCompany( getValue( source.get( "company" ) ) );
            request.setDownPayment( getValue( source.get( "downPayment" ) ) );
            request.setEarnestMoneyDeposit( getValue( source.get( "earnestMoneyDeposit" ) ) );
            request.setEmail( getValue( source.get( "email" ) ) );
            request.setFinancing( getValue( source.get( "financing" ) ) );
            request.setGclId( getValue( source.get( "gclId" ) ) );
            request.setGoogleAnalyticsCampaign( getValue( source.get( "googleAnalyticsCampaign" ) ) );
            request.setGoogleAnalyticsContent( getValue( source.get( "googleAnalyticsContent" ) ) );
            request.setGoogleAnalyticsMedium( getValue( source.get( "googleAnalyticsTerm" ) ) );
            request.setGoogleAnalyticsSource( getValue( source.get( "googleAnalyticsSource" ) ) );
            request.setGoogleAnalyticsTerm( getValue( source.get( "googleAnalyticsTerm" ) ) );
            request.setInterestedZipcodes( getValue( source.get( "interestedZipcodes" ) ) );
            request.setLastName( getValue( source.get( "lastName" ) ) );
            request.setLastVisitDateTime( getValue( source.get( "lastVisitDateTime" ) ) );
            request.setLeadSourceUrl( getValue( source.get( "leadSourceUrl" ) ) );
            request.setLeadStatus( getValue( source.get( "leadStatus" ) ) );
            request.setLeadType( getValue( source.get( "leadType" ) ) );
            request.setListingCreationDate( getValue( source.get( "listingCreationDate" ) ) );
            request.setListingId( getValue( source.get( "listingId" ) ) );
            request.setMarketingOptIn( isValue( source.get( "marketingOptIn" ) ) );
            request.setMedianPrice( getValue( source.get( "medianPrice" ) ) );
            request.setMessage( getValue( source.get( "message" ) ) );
            request.setMlsId( getValue( source.get( "mlsId" ) ) );
            request.setMlsPackageType( getValue( source.get( "mlsPackageType" ) ) );
            request.setOfferAmount( getValue( source.get( "offerAmount" ) ) );
            request.setOwnersComIdentifier( getValue( source.get( "ownersComIdentifier" ) ) );
            request.setOwnersVisitorId( getValue( source.get( "ownersVisitorId" ) ) );
            request.setOwnsHome( isValue( source.get( "ownsHome" ) ) );
            request.setPhone( getValue( source.get( "phone" ) ) );
            request.setPreApprovedForMortgage( getValue( source.get( "preApprovedForMortgage" ) ) );
            request.setPreferredContactMethod( getValue( source.get( "preferredContactMethod" ) ) );
            request.setPreferredContactTime( getValue( source.get( "preferredContactTime" ) ) );
            request.setPreferredLanguage( getValue( source.get( "preferredLanguage" ) ) );
            request.setPriceRange( getValue( source.get( "priceRange" ) ) );
            request.setPropertyAddress( getValue( source.get( "propertyAddress" ) ) );
            request.setPropertyTourInformation( getValue( source.get( "propertyTourInformation" ) ) );
            request.setPurchaseMethod( getValue( source.get( "purchaseMethod" ) ) );
            request.setRequestType( getValue( source.get( "requestType" ) ) );
            request.setSavedSearchValues( getValue( source.get( "savedSearchValues" ) ) );
            request.setSource( getValue( source.get( "source" ) ) );
            request.setState( getValue( source.get( "state" ) ) );
            request.setUnbouncePageVariant( getValue( source.get( "unbouncePageVariant" ) ) );
            request.setWebsite( getValue( source.get( "website" ) ) );
            request.setWebsiteSessionData( getValue( source.get( "websiteSessionData" ) ) );
            request.setWorkingWithRealtor( getValue( source.get( "workingWithRealtor" ) ) );
            request.setInterestedInFinancing( Boolean.valueOf(getValue(source.get( "interestedInFinancing" ))));
        }
        return request;
    }

    /**
     * Gets the value.
     *
     * @param array
     *            the array
     * @return the value
     */
    private String getValue( final List< String > list ) {
        return ( null != list && !list.isEmpty() ) ? list.get( 0 ) : null;
    }

    /**
     * Checks if is value.
     *
     * @param array
     *            the array
     * @return true, if is value
     */
    private boolean isValue( final List< String > list ) {
        return ( null != list && !list.isEmpty() ) ? Boolean.parseBoolean( list.get( 0 ) ) : Boolean.FALSE;

    }

    /**
     * Converts GenericLeadRequest to Map< String, String[] >.
     *
     * @param source
     *            is source request.
     * @param destination
     *            is destination object to be created.
     * @return the map
     */
    @Override
    public Map< String, List< String > > convertFrom( final GenericLeadRequest source,
            final Map< String, List< String > > destination ) {
        throw new UnsupportedOperationException( "convertFrom is not supported" );
    }
}
