package com.owners.gravitas.business.builder.request;

import static com.owners.gravitas.enums.CRMObject.LEAD;
import static com.owners.gravitas.enums.LeadStatus.NEW;
import static com.owners.gravitas.enums.RecordType.OWNERS_COM_LOANS;
import static com.owners.gravitas.enums.RecordType.SELLER;
import static com.owners.gravitas.enums.RecordType.getRecordType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.owners.gravitas.business.builder.DeDupeAbstractBuilder;
import com.owners.gravitas.business.helper.DedupLeadSourcePrioirityHandler;
import com.owners.gravitas.dto.crm.request.CRMLeadRequest;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.service.HostService;
import com.owners.gravitas.service.RecordTypeService;

/**
 * The Class CRMLeadRequestBuilder.
 *
 * @author harshads
 */
@Component( "crmLeadRequestBuilder" )
public class CRMLeadRequestBuilder extends DeDupeAbstractBuilder< LeadRequest, CRMLeadRequest > {

    /** The record type service. */
    @Autowired
    private RecordTypeService recordTypeService;

    /** The dedup lead source prioirity handler. */
    @Autowired
    private DedupLeadSourcePrioirityHandler dedupLeadSourcePrioirityHandler;

    /** The host service. */
    @Autowired
    private HostService hostService;

    /**
     * Converts LeadRequest to CRMLeadRequest.
     *
     * @param source
     *            is source request.
     * @param destination
     *            is destination object to be created.
     * @return the CRM lead request
     */
    @Override
    public CRMLeadRequest convertTo( final LeadRequest source, final CRMLeadRequest destination ) {
        CRMLeadRequest crmLeadRequest = destination;
        StringBuilder auditBuilder = null;
        if (source != null) {
            final String recordTypeId = recordTypeService.getRecordTypeIdByName( source.getLeadType(), LEAD.getName() );
            if (crmLeadRequest == null) {
                crmLeadRequest = new CRMLeadRequest();
                BeanUtils.copyProperties( source, crmLeadRequest );
                crmLeadRequest.setLeadStatus( NEW.getStatus() );
                crmLeadRequest.setRecordType( recordTypeId );
            } else {
                auditBuilder = new StringBuilder();
                replaceLeadFields( source, crmLeadRequest, auditBuilder );
                replaceLeadFieldsWithoutAudit( crmLeadRequest, source );
                appendLeadFields( source, crmLeadRequest, auditBuilder );
                crmLeadRequest.setRecordType( replaceNotMatching( crmLeadRequest.getRecordType(), recordTypeId,
                        auditBuilder, "Lead Record Type" ) );
            }
            handleSpecialCases( source, crmLeadRequest, auditBuilder, true );
            setMLSPackageType( source, crmLeadRequest );
            setLeadState( source, crmLeadRequest, auditBuilder );
            if (OWNERS_COM_LOANS.equals( getRecordType( source.getLeadType() ) )) {
                crmLeadRequest.setState( source.getState() );
                crmLeadRequest.setPropertyState( source.getState() );
                crmLeadRequest.setMailingState( source.getState() );
            }
            crmLeadRequest.setGravitasEngineId( hostService.getHost() );
            createDedupTrace( crmLeadRequest, auditBuilder,
                    getRecordHistory( source, crmLeadRequest.getRecordHistory() ) );
        }
        return crmLeadRequest;
    }

    /**
     * Creates the dedup trace.
     *
     * @param crmLeadRequest
     *            the crm lead request
     * @param auditBuilder
     *            the audit builder
     * @param recordHistoryStr
     *            the record history str
     */
    private void createDedupTrace( final CRMLeadRequest crmLeadRequest, final StringBuilder auditBuilder,
            final String recordHistoryStr ) {
        if (null != auditBuilder) {
            final String newAuditRecord = getAuditRecord( crmLeadRequest.getAuditRecord(), LEAD, auditBuilder );
            crmLeadRequest.setAuditRecord( newAuditRecord );
            crmLeadRequest.setDeDuped( Boolean.TRUE );
            Integer deDupCount = 0;
            if (null != crmLeadRequest.getDeDupCounter()) {
                deDupCount = crmLeadRequest.getDeDupCounter();
            }
            crmLeadRequest.setDeDupCounter( deDupCount + 1 );
        }
        crmLeadRequest.setRecordHistory( recordHistoryStr );
    }

    /**
     * Append lead fields.
     *
     * @param source
     *            the source
     * @param crmLeadRequest
     *            the crm lead request
     * @param auditBuilder
     *            the audit builder
     */
    private void appendLeadFields( final LeadRequest source, final CRMLeadRequest crmLeadRequest,
            final StringBuilder auditBuilder ) {
        crmLeadRequest.setSavedSearchValues( appendValue( crmLeadRequest.getSavedSearchValues(),
                source.getSavedSearchValues(), auditBuilder, "Saved Search Values" ) );
        crmLeadRequest.setPropertyTourInformation( appendValue( crmLeadRequest.getPropertyTourInformation(),
                source.getPropertyTourInformation(), auditBuilder, "Property Tour Information" ) );
        crmLeadRequest.setAdditionalPropertyData( appendValue( crmLeadRequest.getAdditionalPropertyData(),
                source.getAdditionalPropertyData(), auditBuilder, "Additional Property Data" ) );
        crmLeadRequest.setWebsiteSessionData( appendValue( crmLeadRequest.getWebsiteSessionData(),
                source.getWebsiteSessionData(), auditBuilder, "Website Session Data" ) );
        crmLeadRequest.setOrderId(
                appendValue( crmLeadRequest.getOrderId(), source.getOrderId(), auditBuilder, "Owners.com Order #" ) );
    }

    /**
     * Replace lead fields.
     *
     * @param source
     *            the source
     * @param crmLeadRequest
     *            the crm lead request
     * @param auditBuilder
     *            the audit builder
     */
    private void replaceLeadFields( final LeadRequest source, final CRMLeadRequest crmLeadRequest,
            final StringBuilder auditBuilder ) {
        if (dedupLeadSourcePrioirityHandler.isSourceUpdateRequired( source.getSource().toLowerCase().trim(),
                crmLeadRequest.getSource().toLowerCase().trim() )) {
            crmLeadRequest.setSource(
                    replaceNotMatching( crmLeadRequest.getSource(), source.getSource(), auditBuilder, "Source" ) );
        }

        // replace all alpha-numeric values
        crmLeadRequest.setFirstName( replaceNotMatching( crmLeadRequest.getFirstName(), source.getFirstName(),
                auditBuilder, "First Name" ) );
        crmLeadRequest.setLastName(
                replaceNotMatching( crmLeadRequest.getLastName(), source.getLastName(), auditBuilder, "Last Name" ) );
        crmLeadRequest
                .setPhone( replaceNotMatching( crmLeadRequest.getPhone(), source.getPhone(), auditBuilder, "Phone" ) );
        crmLeadRequest.setPreApprovedForMortgage( replaceNotMatching( crmLeadRequest.getPreApprovedForMortgage(),
                source.getPreApprovedForMortgage(), auditBuilder, "PreApproved For Mortgage" ) );
        crmLeadRequest.setWorkingWithRealtor( replaceNotMatching( crmLeadRequest.getWorkingWithRealtor(),
                source.getWorkingWithRealtor(), auditBuilder, "Working With Realtor" ) );
        crmLeadRequest.setBuyerReadinessTimeline( replaceNotMatching( crmLeadRequest.getBuyerReadinessTimeline(),
                source.getBuyerReadinessTimeline(), auditBuilder, "Buyer Readiness Timeline" ) );
        crmLeadRequest.setMarketingOptIn(
                Boolean.valueOf( replaceNotMatching( String.valueOf( crmLeadRequest.getMarketingOptIn() ),
                        String.valueOf( source.isMarketingOptIn() ), auditBuilder, "Marketing Opt In" ) ) );
        crmLeadRequest.setPreferredContactTime( replaceNotMatching( crmLeadRequest.getPreferredContactTime(),
                source.getPreferredContactTime(), auditBuilder, "Preferred Contact Time" ) );
        crmLeadRequest.setPreferredContactMethod( replaceNotMatching( crmLeadRequest.getPreferredContactMethod(),
                source.getPreferredContactMethod(), auditBuilder, "Preferred Contact Method" ) );
        crmLeadRequest.setBuyerLeadQuality( replaceNotMatching( crmLeadRequest.getBuyerLeadQuality(),
                source.getBuyerLeadQuality(), auditBuilder, "Buyer Lead Quality" ) );
        crmLeadRequest.setPreferredLanguage( replaceNotMatching( crmLeadRequest.getPreferredLanguage(),
                source.getPreferredLanguage(), auditBuilder, "Preferred Language" ) );
        crmLeadRequest
                .setMlsId( replaceNotMatching( crmLeadRequest.getMlsId(), source.getMlsId(), auditBuilder, "MLS ID" ) );
        crmLeadRequest
                .setAlId( replaceNotMatching( crmLeadRequest.getAlId(), source.getAlId(), auditBuilder, "Al Id" ) );
        crmLeadRequest.setOwnersComIdentifier( replaceNotMatching( crmLeadRequest.getOwnersComIdentifier(),
                source.getOwnersComIdentifier(), auditBuilder, "Owners Com Identifier" ) );
        crmLeadRequest.setFinancing(
                replaceNotMatching( crmLeadRequest.getFinancing(), source.getFinancing(), auditBuilder, "Financing" ) );
        crmLeadRequest.setWebsite(
                replaceNotMatching( crmLeadRequest.getWebsite(), source.getWebsite(), auditBuilder, "Website" ) );
        crmLeadRequest.setLeadSourceUrl( replaceNotMatching( crmLeadRequest.getLeadSourceUrl(),
                source.getLeadSourceUrl(), auditBuilder, "Lead Source URL" ) );
        crmLeadRequest.setPurchaseMethod( replaceNotMatching( crmLeadRequest.getPurchaseMethod(),
                source.getPurchaseMethod(), auditBuilder, "Purchase Method" ) );
        crmLeadRequest.setOwnersVisitorId( replaceNotMatching( crmLeadRequest.getOwnersVisitorId(),
                source.getOwnersVisitorId(), auditBuilder, "Owners Visitor ID" ) );
        crmLeadRequest.setOwnsHome( Boolean.valueOf( replaceNotMatching( String.valueOf( crmLeadRequest.isOwnsHome() ),
                String.valueOf( source.isOwnsHome() ), auditBuilder, "Owns a home" ) ) );
        if (source.isInterestedInFinancing()) {
            crmLeadRequest.setInterestedInFinancing( Boolean.valueOf( replaceNotMatching(
                    String.valueOf( crmLeadRequest.isInterestedInFinancing() ),
                    String.valueOf( source.isInterestedInFinancing() ), auditBuilder, "Interested In Financing" ) ) );
        }
        crmLeadRequest.setComments(
                replaceNotMatching( crmLeadRequest.getComments(), source.getComments(), auditBuilder, "Comments" ) );
        crmLeadRequest.setListingId( replaceNotMatching( crmLeadRequest.getListingId(), source.getListingId(),
                auditBuilder, "Listing Id" ) );
        crmLeadRequest.setPriceRange( replaceNotMatching( crmLeadRequest.getPriceRange(), source.getPriceRange(),
                auditBuilder, "Price Range" ) );
        crmLeadRequest.setInterestedZipcodes( replaceNotMatching( crmLeadRequest.getInterestedZipcodes(),
                source.getInterestedZipcodes(), auditBuilder, "Interested Zip Codes" ) );
        crmLeadRequest.setPropertyAddress( replaceNotMatching( crmLeadRequest.getPropertyAddress(),
                source.getPropertyAddress(), auditBuilder, "Property Address" ) );
        crmLeadRequest.setMessage(
                replaceNotMatching( crmLeadRequest.getMessage(), source.getMessage(), auditBuilder, "Message" ) );

        // all numeric values
        crmLeadRequest.setOfferAmount( replaceNotMatchingNumbers( crmLeadRequest.getOfferAmount(),
                source.getOfferAmount(), auditBuilder, "Offer Amount" ) );
        crmLeadRequest.setEarnestMoneyDeposit( replaceNotMatchingNumbers( crmLeadRequest.getEarnestMoneyDeposit(),
                source.getEarnestMoneyDeposit(), auditBuilder, "Earnest Money Deposit" ) );
        crmLeadRequest.setDownPayment( replaceNotMatchingNumbers( crmLeadRequest.getDownPayment(),
                source.getDownPayment(), auditBuilder, "Down Payment" ) );
        crmLeadRequest.setMedianPrice( replaceNotMatchingNumbers( crmLeadRequest.getMedianPrice(),
                source.getMedianPrice(), auditBuilder, "Median Price" ) );
        crmLeadRequest.setPropertyCity( replaceNotMatching( crmLeadRequest.getPropertyCity(), source.getPropertyCity(),
                auditBuilder, "Property City" ) );
        crmLeadRequest.setPropertyType( replaceNotMatching( crmLeadRequest.getPropertyType(), source.getPropertyType(),
                auditBuilder, "Property Type" ) );
        crmLeadRequest.setPropertyBathroom( replaceNotMatchingNumbers( crmLeadRequest.getPropertyBathroom(),
                source.getPropertyBathroom(), auditBuilder, "Property Bathroom" ) );
        crmLeadRequest.setPropertyBedroom( replaceNotMatchingNumbers( crmLeadRequest.getPropertyBedroom(),
                source.getPropertyBedroom(), auditBuilder, "Property Bedroom" ) );
        crmLeadRequest.setPropertySquareFoot( replaceNotMatchingNumbers( crmLeadRequest.getPropertySquareFoot(),
                source.getPropertySquareFoot(), auditBuilder, "Property Square Foot" ) );
        crmLeadRequest.setSearchCity( replaceNotMatching( crmLeadRequest.getSearchCity(), source.getSearchCity(),
                auditBuilder, "Search City" ) );
        crmLeadRequest.setSearchAttributes( replaceNotMatching( crmLeadRequest.getSearchAttributes(),
                source.getSearchAttributes(), auditBuilder, "Search Attributes" ) );
        crmLeadRequest.setPartnerIdentifier( replaceNotMatching( crmLeadRequest.getPartnerIdentifier(),
                source.getPartnerIdentifier(), auditBuilder, "Partner Identifier" ) );
        crmLeadRequest.setLotSize(
                replaceNotMatching( crmLeadRequest.getLotSize(), source.getLotSize(), auditBuilder, "Lot Size" ) );
        crmLeadRequest.setCreditScore( replaceNotMatching( crmLeadRequest.getCreditScore(), source.getCreditScore(),
                auditBuilder, "Credit Score" ) );
        crmLeadRequest.setHomeType(
                replaceNotMatching( crmLeadRequest.getHomeType(), source.getHomeType(), auditBuilder, "Home type" ) );
        crmLeadRequest.setBrowser(
                replaceNotMatching( crmLeadRequest.getBrowser(), source.getBrowser(), auditBuilder, "Browser" ) );
        crmLeadRequest.setOs( replaceNotMatching( crmLeadRequest.getOs(), source.getOs(), auditBuilder, "OS" ) );
        crmLeadRequest.setDevice(
                replaceNotMatching( crmLeadRequest.getDevice(), source.getDevice(), auditBuilder, "Device" ) );

    }

    /**
     * Sets the lead state.
     *
     * @param source
     *            the source
     * @param crmLeadRequest
     *            the crm lead request
     * @param auditBuilder
     *            the audit builder
     */
    private void setLeadState( final LeadRequest source, final CRMLeadRequest crmLeadRequest,
            final StringBuilder auditBuilder ) {
        final String state = replaceNotMatching( crmLeadRequest.getState(), source.getState(), auditBuilder, "State" );
        if (SELLER.equals( getRecordType( source.getLeadType() ) )) {
            crmLeadRequest.setPropertyState( state );
            crmLeadRequest.setState( StringUtils.EMPTY );
        } else {
            crmLeadRequest.setState( state );
        }
    }

    /**
     * Replace fields without audit.
     *
     * @param crmLeadRequest
     *            the crm lead request
     * @param source
     *            the source
     */
    private void replaceLeadFieldsWithoutAudit( final CRMLeadRequest crmLeadRequest, final LeadRequest source ) {
        crmLeadRequest.setGoogleAnalyticsCampaign( replaceNotMatching( crmLeadRequest.getGoogleAnalyticsCampaign(),
                source.getGoogleAnalyticsCampaign() ) );
        crmLeadRequest.setGoogleAnalyticsContent(
                replaceNotMatching( crmLeadRequest.getGoogleAnalyticsContent(), source.getGoogleAnalyticsContent() ) );
        crmLeadRequest.setGoogleAnalyticsMedium(
                replaceNotMatching( crmLeadRequest.getGoogleAnalyticsMedium(), source.getGoogleAnalyticsMedium() ) );
        crmLeadRequest.setGoogleAnalyticsSource(
                replaceNotMatching( crmLeadRequest.getGoogleAnalyticsSource(), source.getGoogleAnalyticsSource() ) );
        crmLeadRequest.setGoogleAnalyticsTerm(
                replaceNotMatching( crmLeadRequest.getGoogleAnalyticsTerm(), source.getGoogleAnalyticsTerm() ) );
        crmLeadRequest.setUnbouncePageVariant(
                replaceNotMatching( crmLeadRequest.getUnbouncePageVariant(), source.getUnbouncePageVariant() ) );
        crmLeadRequest.setGclId( replaceNotMatching( crmLeadRequest.getGclId(), source.getGclId() ) );
        crmLeadRequest.setOwnersAgent( replaceNotMatching( crmLeadRequest.getOwnersAgent(), source.getOwnersAgent() ) );
    }

    /**
     * Sets the mls package type.
     *
     * @param source
     *            the source
     * @param crmLeadRequest
     *            the crm lead request
     */
    private void setMLSPackageType( final LeadRequest source, final CRMLeadRequest crmLeadRequest ) {
        if (StringUtils.isNotBlank( source.getMlsPackageType() )) {
            crmLeadRequest.setMlsPackageType( source.getMlsPackageType() );
        }
    }

    /**
     * Gets the record history.
     *
     * @param source
     *            the source
     * @param recordHistory
     *            the record history
     * @return the record history
     */
    private String getRecordHistory( final LeadRequest source, final String recordHistory ) {
        final StringBuilder builder = new StringBuilder(
                StringUtils.isBlank( recordHistory ) ? StringUtils.EMPTY : recordHistory );
        try {
            final ObjectMapper mapper = new ObjectMapper();
            builder.append( NEW_LINE + NEW_LINE + mapper.writeValueAsString( source ) );
        } catch ( final JsonProcessingException e ) {
            throw new ApplicationException( "Object to JSON conversion failed for record history", e );
        }
        return builder.toString();
    }

    /**
     * Method is not supported.
     *
     * @param source
     *            the source
     * @param destination
     *            the destination
     * @return the lead request
     */
    @Override
    public LeadRequest convertFrom( final CRMLeadRequest source, final LeadRequest destination ) {
        throw new UnsupportedOperationException( "convertFrom is not supported" );
    }
}
