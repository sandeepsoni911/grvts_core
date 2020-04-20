package com.owners.gravitas.business.builder.request;

import static com.owners.gravitas.constants.Constants.LEAD_SOURCE_URL;
import static com.owners.gravitas.enums.RecordType.OWNERS_COM_LOANS;
import static com.owners.gravitas.util.DateUtil.CRM_DATE_TIME_PATTERN;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.builder.AbstractBuilder;
import com.owners.gravitas.dto.request.GenericLeadRequest;
import com.owners.gravitas.enums.LeadRequestType;
import com.owners.gravitas.util.DateUtil;

/**
 * The Class OCLGenericLeadRequestBuilder.
 *
 * @author shivamm
 */
@Component( "oclGenericLeadRequestBuilder" )
public class OCLGenericLeadRequestBuilder extends AbstractBuilder< LeadSource, GenericLeadRequest > {

    /**
     * Converts LeadSource to LeadRequest.
     *
     * @param source
     *            is source request.
     * @param destination
     *            the destination
     * @return the lead request
     */
    @Override
    public GenericLeadRequest convertTo( LeadSource source, GenericLeadRequest destination ) {
        GenericLeadRequest leadRequest = destination;
        if (source != null) {
            if (leadRequest == null) {
                leadRequest = new GenericLeadRequest();
            }
            leadRequest.setFirstName( source.getFirstName() );
            leadRequest.setLastName( source.getLastName() );
            leadRequest.setEmail( source.getEmail() );
            leadRequest.setPhone( source.getPhone() );
            leadRequest.setCompany( source.getCompany() );
            leadRequest.setSource( source.getSource() );
            leadRequest.setComments( source.getComments() );
            leadRequest.setLeadType( OWNERS_COM_LOANS.name() );
            leadRequest.setListingId( source.getListingId() );
            leadRequest.setAdditionalPropertyData( source.getAdditionalPropertyData() );
            leadRequest.setAlId( source.getAlId() );
            leadRequest.setBuyerLeadQuality( source.getBuyerLeadQuality() );
            leadRequest.setBuyerReadinessTimeline( source.getBuyerReadinessTimeline() );
            leadRequest.setDownPayment( source.getDownPayment() );
            leadRequest.setEarnestMoneyDeposit( source.getEarnestMoneyDeposit() );
            leadRequest.setFinancing( source.getFinancing() );
            leadRequest.setGclId( source.getGclId() );
            leadRequest.setGoogleAnalyticsCampaign( source.getGoogleAnalyticsCampaign() );
            leadRequest.setGoogleAnalyticsContent( source.getGoogleAnalyticsContent() );
            leadRequest.setGoogleAnalyticsMedium( source.getGoogleAnalyticsMedium() );
            leadRequest.setGoogleAnalyticsSource( source.getGoogleAnalyticsSource() );
            leadRequest.setGoogleAnalyticsTerm( source.getGoogleAnalyticsTerm() );
            leadRequest.setInterestedZipcodes( source.getInterestedZipcodes() );
            leadRequest.setLastVisitDateTime( getDateString( source.getLastVisitDateTime() ) );
            leadRequest.setLeadSourceUrl( LEAD_SOURCE_URL );
            leadRequest.setLeadStatus( source.getLeadStatus() );
            leadRequest.setListingCreationDate( getDateString( source.getListingCreationDate() ) );
            leadRequest.setMarketingOptIn( source.getMarketingOptIn() );
            leadRequest.setMedianPrice( source.getMedianPrice() );
            leadRequest.setMessage( source.getMessage() );
            leadRequest.setMlsId( source.getMlsId() );
            leadRequest.setMlsPackageType( source.getMlsPackageType() );
            leadRequest.setOfferAmount( source.getOfferAmount() );
            leadRequest.setOwnersComIdentifier( source.getOwnersComIdentifier() );
            leadRequest.setOwnersVisitorId( source.getOwnersVisitorId() );
            leadRequest.setOwnsHome( source.isOwnsHome() );
            leadRequest.setPreApprovedForMortgage( source.getPreApprovedForMortgage() );
            leadRequest.setPreferredContactMethod( source.getPreferredContactMethod() );
            leadRequest.setPreferredContactTime( source.getPreferredContactTime() );
            leadRequest.setPreferredLanguage( source.getPreferredLanguage() );
            leadRequest.setPriceRange( source.getPriceRange() );
            leadRequest.setPropertyAddress( source.getPropertyAddress() );
            leadRequest.setPropertyTourInformation( source.getPropertyTourInformation() );
            leadRequest.setPurchaseMethod( source.getPurchaseMethod() );
            leadRequest.setRequestType( LeadRequestType.OTHER.toString() );
            leadRequest.setSavedSearchValues( source.getSavedSearchValues() );
            leadRequest.setState( source.getState() );
            leadRequest.setUnbouncePageVariant( source.getUnbouncePageVariant() );
            leadRequest.setWebsite( source.getWebsite() );
            leadRequest.setWebsiteSessionData( source.getWebsiteSessionData() );
            leadRequest.setWorkingWithRealtor( source.getWorkingWithRealtor() );
            leadRequest.setLoanNumber( source.getLoanNumber() );
        }
        return leadRequest;
    }

    /**
     * Gets the date string.
     *
     * @param dateTime
     *            the date time
     * @return the date string
     */
    private String getDateString( final DateTime dateTime ) {
        return ( dateTime != null ) ? DateUtil.toString( dateTime, CRM_DATE_TIME_PATTERN ) : null;

    }

    @Override
    public LeadSource convertFrom( GenericLeadRequest source, LeadSource destination ) {
    	LeadSource leadSource = destination;
        if (source != null) {
            if (leadSource == null) {
            	leadSource = new LeadSource();
            }
            leadSource.setFirstName( source.getFirstName() );
            leadSource.setLastName( source.getLastName() );
            leadSource.setEmail( source.getEmail() );
            leadSource.setPhone( source.getPhone() );
            leadSource.setCompany( source.getCompany() );
            leadSource.setSource( source.getSource() );
            leadSource.setComments( source.getComments() );
//            leadSource.setLeadType( OWNERS_COM_LOANS.name() );
            leadSource.setListingId( source.getListingId() );
            leadSource.setAdditionalPropertyData( source.getAdditionalPropertyData() );
            leadSource.setAlId( source.getAlId() );
            leadSource.setBuyerLeadQuality( source.getBuyerLeadQuality() );
            leadSource.setBuyerReadinessTimeline( source.getBuyerReadinessTimeline() );
            leadSource.setDownPayment( source.getDownPayment() );
            leadSource.setEarnestMoneyDeposit( source.getEarnestMoneyDeposit() );
            leadSource.setFinancing( source.getFinancing() );
            leadSource.setGclId( source.getGclId() );
            leadSource.setGoogleAnalyticsCampaign( source.getGoogleAnalyticsCampaign() );
            leadSource.setGoogleAnalyticsContent( source.getGoogleAnalyticsContent() );
            leadSource.setGoogleAnalyticsMedium( source.getGoogleAnalyticsMedium() );
            leadSource.setGoogleAnalyticsSource( source.getGoogleAnalyticsSource() );
            leadSource.setGoogleAnalyticsTerm( source.getGoogleAnalyticsTerm() );
            leadSource.setInterestedZipcodes( source.getInterestedZipcodes() );
            leadSource.setLastVisitDateTime( DateUtil.getDateTimeFromString( source.getLastVisitDateTime() ) );
            leadSource.setLeadSourceUrl( LEAD_SOURCE_URL );
            leadSource.setLeadStatus( source.getLeadStatus() );
            leadSource.setListingCreationDate( DateUtil.getDateTimeFromString( source.getListingCreationDate() ) );
            leadSource.setMarketingOptIn( source.isMarketingOptIn() );
            leadSource.setMedianPrice( source.getMedianPrice() );
            leadSource.setMessage( source.getMessage() );
            leadSource.setMlsId( source.getMlsId() );
            leadSource.setMlsPackageType( source.getMlsPackageType() );
            leadSource.setOfferAmount( source.getOfferAmount() );
            leadSource.setOwnersComIdentifier( source.getOwnersComIdentifier() );
            leadSource.setOwnersVisitorId( source.getOwnersVisitorId() );
            leadSource.setOwnsHome( source.isOwnsHome() );
            leadSource.setPreApprovedForMortgage( source.getPreApprovedForMortgage() );
            leadSource.setPreferredContactMethod( source.getPreferredContactMethod() );
            leadSource.setPreferredContactTime( source.getPreferredContactTime() );
            leadSource.setPreferredLanguage( source.getPreferredLanguage() );
            leadSource.setPriceRange( source.getPriceRange() );
            leadSource.setPropertyAddress( source.getPropertyAddress() );
            leadSource.setPropertyTourInformation( source.getPropertyTourInformation() );
            leadSource.setPurchaseMethod( source.getPurchaseMethod() );
            leadSource.setRequestType( LeadRequestType.OTHER.toString() );
            leadSource.setSavedSearchValues( source.getSavedSearchValues() );
            leadSource.setState( source.getState() );
            leadSource.setUnbouncePageVariant( source.getUnbouncePageVariant() );
            leadSource.setWebsite( source.getWebsite() );
            leadSource.setWebsiteSessionData( source.getWebsiteSessionData() );
            leadSource.setWorkingWithRealtor( source.getWorkingWithRealtor() );
            leadSource.setLoanNumber( source.getLoanNumber() );
        }
        return leadSource;
    }
}
