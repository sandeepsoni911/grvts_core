package com.owners.gravitas.business.builder.domain;

import static com.owners.gravitas.constants.Constants.CRM_APPEND_SEPERATOR;
import static com.owners.gravitas.constants.Constants.GRAVITAS;

import java.util.Arrays;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.builder.AbstractBuilder;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.domain.Stage;
import com.owners.gravitas.enums.BuyerStage;
import com.owners.gravitas.enums.RecordType;
import com.owners.gravitas.enums.SellerStage;

/**
 * The Class OpportunityBuilder.
 *
 * @author harshads
 */
@Component( "opportunityBuilder" )
public class OpportunityBuilder extends AbstractBuilder< OpportunitySource, Opportunity > {

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public Opportunity convertTo( final OpportunitySource source, final Opportunity destination ) {
        Opportunity opportunity = destination;
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
            mergeOpportunity( source, opportunity );
            if (opportunity.getFirstContactDtm() == null) {
                setFirstContactDtm( opportunity );
            }
        }
        return opportunity;
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
        if (!opportunitySource.getStage().equals( opportunity.getStage() )) {
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
     * @param opportunity
     *            the new first contact dtm
     */
    private void setFirstContactDtm( final Opportunity opportunity ) {
        Long timeStamp = null;
        final Stage stage = opportunity.popStage();
        if (opportunity.getOpportunityType().equals( RecordType.BUYER.getType() )
                && !stage.getStage().equals( BuyerStage.NEW.getStage() )
                && !stage.getStage().equals( BuyerStage.CLAIMED.getStage() )) {
            timeStamp = stage.getTimestamp();
        } else if (opportunity.getOpportunityType().equals( RecordType.SELLER.getType() )
                && !stage.getStage().equals( SellerStage.NEW.getStage() )
                && !stage.getStage().equals( SellerStage.CLAIMED.getStage() )) {
            timeStamp = stage.getTimestamp();
        }
        opportunity.setFirstContactDtm( timeStamp );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public OpportunitySource convertFrom( final Opportunity source, final OpportunitySource destination ) {
        throw new UnsupportedOperationException( "convertFrom is not supported" );
    }

}
