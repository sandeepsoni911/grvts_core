/**
 *
 */
package com.owners.gravitas.business.builder.request;

import static com.owners.gravitas.enums.CRMObject.OPPORTUNITY;

import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.owners.gravitas.business.builder.DeDupeAbstractBuilder;
import com.owners.gravitas.dto.crm.request.CRMOpportunityRequest;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.util.DateUtil;

/**
 * The Class BuyerOpportunityRequestBuilder.
 *
 * @author harshads
 */
@Component( "buyerOpportunityRequestBuilder" )
public class BuyerOpportunityRequestBuilder extends DeDupeAbstractBuilder< LeadRequest, CRMOpportunityRequest > {

    /**
     * Converts LeadRequest to CRMOpportunityRequest.
     *
     * @param source
     *            is source request.
     * @param destination
     *            is destination object to be created.
     */
    @Override
    public CRMOpportunityRequest convertTo( final LeadRequest source, final CRMOpportunityRequest destination ) {
        CRMOpportunityRequest buyerOpportunity = destination;
        StringBuilder auditBuilder = null;
        if (source != null) {
            if (buyerOpportunity == null) {
                buyerOpportunity = new CRMOpportunityRequest();
                BeanUtils.copyProperties( source, buyerOpportunity, "interestedInFinancing" );
            } else {
                auditBuilder = new StringBuilder();
                replaceOpportunityFields( source, buyerOpportunity, auditBuilder );
                appendOpportunityFields( source, buyerOpportunity, auditBuilder );
            }
            handleSpecialCases( source, buyerOpportunity, auditBuilder, false );
            createDedupTrace( buyerOpportunity, auditBuilder );
        }
        return buyerOpportunity;
    }

    /**
     * Creates the dedup trace.
     *
     * @param opportunityRequest
     *            the crm lead request
     * @param auditBuilder
     *            the audit builder
     */
    private void createDedupTrace( final CRMOpportunityRequest opportunityRequest, final StringBuilder auditBuilder ) {
        if (null != auditBuilder) {
            final String newAuditRecord = getAuditRecord( opportunityRequest.getAuditRecord(), OPPORTUNITY,
                    auditBuilder );
            opportunityRequest.setAuditRecord( newAuditRecord );
            opportunityRequest.setDeDuped( Boolean.TRUE );
        }
    }

    /**
     * Append opportunity fields.
     *
     * @param source
     *            the source
     * @param crmOpportunityRequest
     *            the crm opportunity request
     * @param auditBuilder
     *            the audit builder
     */
    private void appendOpportunityFields( final LeadRequest source, CRMOpportunityRequest crmOpportunityRequest,
            final StringBuilder auditBuilder ) {
        crmOpportunityRequest.setSavedSearchValues( appendValue( crmOpportunityRequest.getSavedSearchValues(),
                source.getSavedSearchValues(), auditBuilder, "Saved Search Values" ) );
        crmOpportunityRequest
                .setPropertyTourInformation( appendValue( crmOpportunityRequest.getPropertyTourInformation(),
                        source.getPropertyTourInformation(), auditBuilder, "Property Tour Information" ) );
        crmOpportunityRequest.setAdditionalPropertyData( appendValue( crmOpportunityRequest.getAdditionalPropertyData(),
                source.getAdditionalPropertyData(), auditBuilder, "Additional Property Data" ) );
        crmOpportunityRequest.setWebsiteSessionData( appendValue( crmOpportunityRequest.getWebsiteSessionData(),
                source.getWebsiteSessionData(), auditBuilder, "Website Session Data" ) );
        crmOpportunityRequest.setOrderId( appendValue( crmOpportunityRequest.getOrderId(), source.getOrderId(),
                auditBuilder, "Owners.com Order #" ) );
    }

    /**
     * Replace opportunity fields.
     *
     * @param source
     *            the source
     * @param CRMOpportunityRequest
     *            the crm opportunity request
     * @param auditBuilder
     *            the audit builder
     */
    private void replaceOpportunityFields( final LeadRequest source, CRMOpportunityRequest crmOpportunityRequest,
            final StringBuilder auditBuilder ) {
    	
    		if(!"Self Generated".equalsIgnoreCase(crmOpportunityRequest.getSource())) {
    			 crmOpportunityRequest.setSource(
    		                replaceNotMatching( crmOpportunityRequest.getSource(), source.getSource(), auditBuilder, "Source" ) );
    		}
        crmOpportunityRequest
                .setPreApprovedForMortgage( replaceNotMatching( crmOpportunityRequest.getPreApprovedForMortgage(),
                        source.getPreApprovedForMortgage(), auditBuilder, "PreApproved For Mortgage" ) );
        crmOpportunityRequest.setWorkingWithRealtor( replaceNotMatching( crmOpportunityRequest.getWorkingWithRealtor(),
                source.getWorkingWithRealtor(), auditBuilder, "Working With Realtor" ) );
        crmOpportunityRequest
                .setBuyerReadinessTimeline( replaceNotMatching( crmOpportunityRequest.getBuyerReadinessTimeline(),
                        source.getBuyerReadinessTimeline(), auditBuilder, "Buyer Readiness Timeline" ) );
        crmOpportunityRequest
                .setPreferredContactTime( replaceNotMatching( crmOpportunityRequest.getPreferredContactTime(),
                        source.getPreferredContactTime(), auditBuilder, "Preferred Contact Time" ) );
        crmOpportunityRequest
                .setPreferredContactMethod( replaceNotMatching( crmOpportunityRequest.getPreferredContactMethod(),
                        source.getPreferredContactMethod(), auditBuilder, "Preferred Contact Method" ) );
        crmOpportunityRequest.setBuyerLeadQuality( replaceNotMatching( crmOpportunityRequest.getBuyerLeadQuality(),
                source.getBuyerLeadQuality(), auditBuilder, "Buyer Lead Quality" ) );
        crmOpportunityRequest.setPreferredLanguage( replaceNotMatching( crmOpportunityRequest.getPreferredLanguage(),
                source.getPreferredLanguage(), auditBuilder, "Preferred Language" ) );
        crmOpportunityRequest.setState(
                replaceNotMatching( crmOpportunityRequest.getState(), source.getState(), auditBuilder, "State" ) );
        crmOpportunityRequest.setMlsId(
                replaceNotMatching( crmOpportunityRequest.getMlsId(), source.getMlsId(), auditBuilder, "MLS ID" ) );
        crmOpportunityRequest.setAlId(
                replaceNotMatching( crmOpportunityRequest.getAlId(), source.getAlId(), auditBuilder, "Al Id" ) );
        crmOpportunityRequest
                .setOwnersComIdentifier( replaceNotMatching( crmOpportunityRequest.getOwnersComIdentifier(),
                        source.getOwnersComIdentifier(), auditBuilder, "Owners Com Identifier" ) );
        crmOpportunityRequest.setFinancing( replaceNotMatching( crmOpportunityRequest.getFinancing(),
                source.getFinancing(), auditBuilder, "Financing" ) );
        crmOpportunityRequest.setWebsite( replaceNotMatching( crmOpportunityRequest.getWebsite(), source.getWebsite(),
                auditBuilder, "Website" ) );
        crmOpportunityRequest.setLeadSourceUrl( replaceNotMatching( crmOpportunityRequest.getLeadSourceUrl(),
                source.getLeadSourceUrl(), auditBuilder, "Lead Source URL" ) );
        crmOpportunityRequest.setPurchaseMethod( replaceNotMatching( crmOpportunityRequest.getPurchaseMethod(),
                source.getPurchaseMethod(), auditBuilder, "Purchase Method" ) );
        crmOpportunityRequest.setOwnersVisitorId( replaceNotMatching( crmOpportunityRequest.getOwnersVisitorId(),
                source.getOwnersVisitorId(), auditBuilder, "Owners Visitor ID" ) );
        crmOpportunityRequest.setComments( replaceNotMatching( crmOpportunityRequest.getComments(),
                source.getComments(), auditBuilder, "Comments" ) );
        crmOpportunityRequest.setListingId( replaceNotMatching( crmOpportunityRequest.getListingId(),
                source.getListingId(), auditBuilder, "Listing Id" ) );
        crmOpportunityRequest.setPriceRange( replaceNotMatching( crmOpportunityRequest.getPriceRange(),
                source.getPriceRange(), auditBuilder, "Price Range" ) );
        crmOpportunityRequest.setInterestedZipcodes( replaceNotMatching( crmOpportunityRequest.getInterestedZipcodes(),
                source.getInterestedZipcodes(), auditBuilder, "Interested Zip Codes" ) );
        crmOpportunityRequest.setPropertyAddress( replaceNotMatching( crmOpportunityRequest.getPropertyAddress(),
                source.getPropertyAddress(), auditBuilder, "Property Address" ) );
        crmOpportunityRequest.setMessage( replaceNotMatching( crmOpportunityRequest.getMessage(), source.getMessage(),
                auditBuilder, "Message" ) );

        // all numeric values
        crmOpportunityRequest.setOfferAmount( replaceNotMatchingNumbers( crmOpportunityRequest.getOfferAmount(),
                source.getOfferAmount(), auditBuilder, "Offer Amount" ) );
        crmOpportunityRequest
                .setEarnestMoneyDeposit( replaceNotMatchingNumbers( crmOpportunityRequest.getEarnestMoneyDeposit(),
                        source.getEarnestMoneyDeposit(), auditBuilder, "Earnest Money Deposit" ) );
        crmOpportunityRequest.setDownPayment( replaceNotMatchingNumbers( crmOpportunityRequest.getDownPayment(),
                source.getDownPayment(), auditBuilder, "Down Payment" ) );
        crmOpportunityRequest.setMedianPrice( replaceNotMatchingNumbers( crmOpportunityRequest.getMedianPrice(),
                source.getMedianPrice(), auditBuilder, "Median Price" ) );
    }

    /**
     * Gets the date time.
     *
     * @param sourceDateTime
     *            the source date time
     * @param crmDateTime
     *            the crm date time
     * @param auditBuilder
     *            the audit builder
     * @param propertyName
     *            the property name
     * @return the date time
     */
    public DateTime getDateTime( final String sourceDateTime, final DateTime crmDateTime, final String datePattern,
            final StringBuilder auditBuilder, final String propertyName ) {
        return DateUtil.toDateTime( replaceNotMatching( DateUtil.toString( crmDateTime, datePattern ), sourceDateTime,
                auditBuilder, propertyName ), datePattern );
    }

    /**
     * Method is not supported.
     */
    @Override
    public LeadRequest convertFrom( CRMOpportunityRequest source, LeadRequest destination ) {
        throw new UnsupportedOperationException( "convertFrom is not supported" );
    }
}
