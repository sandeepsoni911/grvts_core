package com.owners.gravitas.service.builder;

import static com.owners.gravitas.constants.Constants.CRM_APPEND_SEPERATOR;
import static com.owners.gravitas.constants.Constants.GRAVITAS;
import static com.owners.gravitas.util.UpdateValueUtil.updateField;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.domain.Stage;
import com.owners.gravitas.enums.BuyerStage;
import com.owners.gravitas.enums.RecordType;

/**
 * The Class OpportunityBuilder.
 *
 * @author harshads
 */
@Component( "auditTrailOpportunityBuilder" )
public class AuditTrailOpportunityBuilder {

    /** The default response time. */
    @Value( "${opportunity.default.response.time}" )
    private Long defaultResponseTime;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    public Map< String, Object > convertTo( final OpportunitySource source, final Opportunity destination ) {
        Opportunity opportunity = destination;
        final Map< String, Object > updatedOpportunityMap = new HashMap<>();
        if (source != null) {
            if (opportunity == null) {
                opportunity = new Opportunity();
                final long time = new Date().getTime();
                opportunity.setAccepted( true );
                opportunity.setAcceptedDtm( time );
                opportunity.setCreatedDtm( time );
                opportunity.setAssignedDtm( time );
                opportunity.setCreatedBy( GRAVITAS );
                opportunity.setDeleted( Boolean.FALSE );
            }
            final Map< String, Object > beforeUpdateMap = opportunity.toAuditMap();
            mergeOpportunity( source, opportunity );
            setFirstContactDtm( destination == null, opportunity );
            final Map< String, Object > afterUpdateMap = opportunity.toAuditMap();
            for ( final Map.Entry< String, Object > entry : afterUpdateMap.entrySet() ) {
                final Object obj = updateField( beforeUpdateMap.get( entry.getKey() ), entry.getValue() );
                if (obj != null) {
                    updatedOpportunityMap.put( entry.getKey(), obj );
                }
            }
        }
        return updatedOpportunityMap;
    }

    /**
     * Merge opportunity.
     *
     * @param opportunitySource
     *            the opportunity source
     * @param opportunity
     *            the opportunity
     * @return the opportunity
     */
    private Opportunity mergeOpportunity( final OpportunitySource opportunitySource, final Opportunity opportunity ) {
        final long now = new Date().getTime();
        opportunity.setCrmId( opportunitySource.getCrmId() );
        opportunity.setLeadSource( opportunitySource.getLeadSource() );
        opportunity.setWorkingWithExternalAgent( opportunitySource.getWorkingWithExternalAgent() );
        opportunity.setBuyerReadinessTimeline( opportunitySource.getBuyerReadinessTimeline() );
        opportunity.setBudget( opportunitySource.getBudget() );
        opportunity.setReasonLost( opportunitySource.getReasonLost() );
        opportunity.setBuySideCommission( opportunitySource.getBuySideCommission() );
        opportunity.setSalesPrice( opportunitySource.getSalesPrice() );
        opportunity.setExpectedContractDate( opportunitySource.getExpectedContractDate() );
        opportunity.setPreApprovedAmount( opportunitySource.getPreApprovedAmount() );
        opportunity.setOpportunityType( opportunitySource.getOpportunityType() );
        opportunity.setCommissionBasePrice( opportunitySource.getCommissionBasePrice() );
        opportunity.setTitleCompany( opportunitySource.getTitleCompany() );
        opportunity.setPendingDate( opportunitySource.getPendingDate() );
        opportunity.setActualClosingDate( opportunitySource.getActualClosingDate() );
        opportunity.setExpectedAgentRevenue( opportunitySource.getExpectedAgentRevenue() );
        opportunity.setExpectedOwnersComRevenue( opportunitySource.getExpectedOwnersComRevenue() );
        opportunity.setNotes( opportunitySource.getNotes() );
        opportunity.setPreApprovedForMortgage( opportunitySource.getPreApprovedForMortgage() );
        opportunity.setAreaOfInterest( opportunitySource.getInterestedZipCodes() );
        opportunity.setFinancing( opportunitySource.getFinancing() );
        opportunity.setMedianPrice( opportunitySource.getMedianPrice() );
        opportunity.setPreferredLanguage( opportunitySource.getPreferedLanguage() );
        opportunity.setRequestSummary( opportunitySource.getRequestSummary() );
        if (opportunitySource.getStage() != null && !opportunitySource.getStage().equals( opportunity.getStage() )) {
            final Stage stage = new Stage();
            stage.setStage( opportunitySource.getStage() );
            stage.setTimestamp( now );
            opportunity.addStage( stage );
            opportunitySource.setStageChanged( true );
        }
        opportunity.setStage( opportunitySource.getStage() );
        opportunity.setListingIds( opportunitySource.getListingId() != null
                ? Arrays.asList( opportunitySource.getListingId().split( CRM_APPEND_SEPERATOR ) ) : null );
        opportunity.setLastModifiedDtm( now );
        opportunity.setTitleSelectionReason( opportunitySource.getTitleSelectionReason() );
        opportunity.setPriceRange( opportunitySource.getPriceRange() );
        opportunity.setPropertyAddress( opportunitySource.getPropertyAddress() );
        opportunity.setListingSideCommission( opportunitySource.getListingSideCommission() );
        opportunity.setListPrice( opportunitySource.getListPrice() );
        opportunity.setListDate( opportunitySource.getListDate() );
        opportunity.setExpirationDate( opportunitySource.getExpirationDate() );
        opportunity.setOfferAmount( opportunitySource.getOfferAmount() );
        return opportunity;
    }

    /**
     * Sets the first contact dtm.
     *
     * @param isNew
     *            the is new
     * @param opportunity
     *            the opportunity
     */
    private void setFirstContactDtm( final boolean isNew, final Opportunity opportunity ) {
        final Stage stage = opportunity.popStage();
        final boolean isEligible = null == opportunity.getFirstContactDtm()
                && ( opportunity.getOpportunityType().equals( RecordType.BUYER.getType() )
                        || opportunity.getOpportunityType().equals( RecordType.SELLER.getType() ) )
                && !stage.getStage().equals( BuyerStage.NEW.getStage() )
                && !stage.getStage().equals( BuyerStage.CLAIMED.getStage() );

        if (isEligible) {
            if (isNew) {
                opportunity.setFirstContactDtm(
                        new DateTime( opportunity.getAssignedDtm() ).plus( defaultResponseTime ).getMillis() );
            } else {
                opportunity.setFirstContactDtm( new DateTime( Instant.now().toEpochMilli() ).getMillis() );
            }
        }

    }
}
