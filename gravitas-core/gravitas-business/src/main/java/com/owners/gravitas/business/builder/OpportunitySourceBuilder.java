package com.owners.gravitas.business.builder;

import static com.owners.gravitas.util.DateUtil.toDate;
import static com.owners.gravitas.util.StringUtils.convertObjectToString;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.owners.gravitas.amqp.OpportunitySource;

/**
 * The Class OpportunitySourceBuilder.
 *
 * @author amits
 */
@Component( "opportunitySourceBuilder" )
public class OpportunitySourceBuilder extends AbstractBuilder< Map< String, Object >, OpportunitySource > {

    /** The Constant NEW_LINE. */
    private static final String NEW_LINE = "\n";

    /** The request summary builder. */
    @Autowired
    private RequestSummaryBuilder requestSummaryBuilder;

    /**
     * Converts Map< String, String > to OpportunitySource.
     *
     * @param source
     *            is source request.
     * @param destination
     *            the destination
     * @return the opportunity source
     */
    @Override
    public OpportunitySource convertTo( final Map< String, Object > source, final OpportunitySource destination ) {
        OpportunitySource opportunitySource = destination;
        if (source != null) {
            if (opportunitySource == null) {
                opportunitySource = new OpportunitySource();
            }
            opportunitySource.setLeadSource( convertObjectToString( source.get( "LeadSource" ) ) );
            opportunitySource.setWorkingWithExternalAgent(
                    convertObjectToString( source.get( "Working_with_External_Agent__c" ) ) );
            opportunitySource
                    .setBuyerReadinessTimeline( convertObjectToString( source.get( "Buyer_Readiness_Timeline__c" ) ) );
            opportunitySource.setBudget( convertObjectToString( source.get( "Price_Range__c" ) ) );
            opportunitySource
                    .setMedianPrice( toBigDecimal( convertObjectToString( source.get( "Median_Price__c" ) ) ) );
            opportunitySource.setStage( convertObjectToString( source.get( "StageName" ) ) );
            opportunitySource.setReasonLost( convertObjectToString( source.get( "Reason_Lost__c" ) ) );
            opportunitySource
                    .setBuySideCommission( convertObjectToString( source.get( "Commission_Post_Closing__c" ) ) );
            opportunitySource.setSalesPrice(
                    toBigDecimal( convertObjectToString( source.get( "Sales_Price_Post_Closing__c" ) ) ) );
            opportunitySource.setExpectedContractDate( toDate( convertObjectToString( source.get( "CloseDate" ) ) ) );
            opportunitySource.setPreApprovedAmount(
                    toBigDecimal( convertObjectToString( source.get( "Pre_approved_Amount__c" ) ) ) );
            opportunitySource.setOpportunityType( getRecordTypeName( source.get( "RecordType" ) ) );
            opportunitySource.setLastModifiedBy( getLastModifiedBy( source.get( "LastModifiedBy" ) ) );
            opportunitySource.setCommissionBasePrice(
                    toBigDecimal( convertObjectToString( source.get( "Commission_Base_Price__c" ) ) ) );
            opportunitySource.setTitleCompany( convertObjectToString( source.get( "Title_Company_Non_PTS__c" ) ) );
            opportunitySource.setPendingDate( toDate( convertObjectToString( source.get( "Pending_Date__c" ) ) ) );
            opportunitySource
                    .setActualClosingDate( toDate( convertObjectToString( source.get( "Acutal_Closing_Date__c" ) ) ) );
            opportunitySource.setExpectedAgentRevenue(
                    toBigDecimal( convertObjectToString( source.get( "Expected_Agent_Revenue__c" ) ) ) );
            opportunitySource.setExpectedOwnersComRevenue(
                    toBigDecimal( convertObjectToString( source.get( "Expected_Owners_com_Revenue__c" ) ) ) );
            opportunitySource.setAgentEmail( convertObjectToString( source.get( "Owners_Agent__c" ) ) );
            opportunitySource.setInterestedZipCodes( convertObjectToString( source.get( "Interested_Zip_Codes__c" ) ) );
            opportunitySource.setLeadRequestType( convertObjectToString( source.get( "Lead_Request_Type__c" ) ) );
            opportunitySource.setNotes( convertObjectToString( source.get( "Opportunity_Notes__c" ) ) );
            opportunitySource
                    .setPreApprovedForMortgage( convertObjectToString( source.get( "Pre_Approved_for_Mortgage__c" ) ) );
            opportunitySource.setListingId( convertObjectToString( source.get( "Listing_ID__c" ) ) );
            opportunitySource
                    .setOfferAmount( toBigDecimal( convertObjectToString( source.get( "Offer_Amount__c" ) ) ) );
            opportunitySource
                    .setEarnestMoneyDeposit( convertObjectToString( source.get( "Earnest_Money_Deposit__c" ) ) );
            opportunitySource.setPurchaseMethod( convertObjectToString( source.get( "Purchase_Method__c" ) ) );
            opportunitySource.setDownPayment( convertObjectToString( source.get( "Down_Payment__c" ) ) );
            opportunitySource.setPropertyTourInformation(
                    convertObjectToString( source.get( "Property_Tour_Information__c" ) ) );
            opportunitySource.setLeadMessage( convertObjectToString( source.get( "Lead_Message__c" ) ) );
            opportunitySource.setPreferedLanguage( convertObjectToString( source.get( "Preferred_Language__c" ) ) );
            opportunitySource.setFinancing( convertObjectToString( source.get( "Financing__c" ) ) );
            opportunitySource
                    .setTitleSelectionReason( convertObjectToString( source.get( "Title_Selection_Reason__c" ) ) );
            opportunitySource.setPriceRange( convertObjectToString( source.get( "Price_Ranges__c" ) ) );
            opportunitySource.setPropertyAddress( convertObjectToString( source.get( "Property_Address__c" ) ) );
            opportunitySource
                    .setListingSideCommission( convertObjectToString( source.get( "Listing_Side_Commission__c" ) ) );
            opportunitySource.setListPrice( toBigDecimal( convertObjectToString( source.get( "List_Price__c" ) ) ) );
            opportunitySource.setListDate( toDate( convertObjectToString( source.get( "Listing_Date__c" ) ) ) );
            opportunitySource
                    .setExpirationDate( toDate( convertObjectToString( source.get( "Listing_Expiration_Date__c" ) ) ) );
            opportunitySource.setPropertyState( convertObjectToString( source.get( "Property_State__c" ) ) );
            opportunitySource.setRecordType( convertObjectToString( source.get( "Record_Type_Name__c" ) ) );
            opportunitySource.setBuyerLeadQuality( convertObjectToString( source.get( "Buyer_Lead_Quality__c" ) ) );
            opportunitySource.setDedupCount( convertObjectToString( source.get( "Gravitas_Dedup_Count__c" ) ) );
            opportunitySource.setPropertyCity( convertObjectToString( source.get( "Property_City_del__c" ) ) );
            opportunitySource.setSellerPropertyState( convertObjectToString( source.get( "Property_States_del__c" ) ) );
            opportunitySource.setReferred(
                    Boolean.valueOf( convertObjectToString( source.get( "Referred_Successfully__c" ) ) ) );
            opportunitySource.setPriceRanges( convertObjectToString( source.get( "Price_Ranges__c" ) ) );
            opportunitySource.setPropertyZip( convertObjectToString( source.get( "Property_Zip_del__c" ) ) );
            opportunitySource.setFarmingGroup( convertObjectToString( source.get( "Farming_Group__c" ) ) );
            final String recordHistory = convertObjectToString( source.get( "Gravitas_Record_History__c" ) );
            if (recordHistory != null) {
                opportunitySource
                        .setRequestSummary( requestSummaryBuilder.convertTo( recordHistory.split( NEW_LINE ) ) );
            }
        }
        return opportunitySource;
    }

    /** Method not supported. */
    @Override
    public Map< String, Object > convertFrom( final OpportunitySource source, final Map< String, Object > destination ) {
        throw new UnsupportedOperationException( "convertFrom is not supported" );
    }

    /**
     * Gets the record type name.
     *
     * @param map
     *            the map
     * @return the record type name
     */
    private String getRecordTypeName( final Object map ) {
        return ( ( Map< String, String > ) map ).get( "Name" );
    }

    /**
     * Gets the last modified by.
     *
     * @param map
     *            the map
     * @return the last modified by
     */
    private String getLastModifiedBy( final Object map ) {
        return ( map != null ) ? ( ( Map< String, String > ) map ).get( "FirstName" ) + " "
                + ( ( Map< String, String > ) map ).get( "LastName" ) : StringUtils.EMPTY;
    }

    /**
     * To big decimal.
     *
     * @param value
     *            the value
     * @return the big decimal
     */
    private BigDecimal toBigDecimal( final String value ) {
        return value == null ? null : BigDecimal.valueOf( Double.valueOf( value ) );
    }

}
