package com.owners.gravitas.business.builder.domain;

import static com.owners.gravitas.constants.Constants.GRAVITAS;
import static com.owners.gravitas.enums.PhoneNumberType.PRIMARY;
import static com.owners.gravitas.util.DateUtil.toDate;
import static com.owners.gravitas.util.StringUtils.convertObjectToString;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.owners.gravitas.business.builder.AbstractBuilder;
import com.owners.gravitas.business.builder.ContactBuilder;
import com.owners.gravitas.business.builder.RequestSummaryBuilder;
import com.owners.gravitas.domain.Agent;
import com.owners.gravitas.domain.AgentHolder;
import com.owners.gravitas.domain.AgentInfo;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.domain.Request;
import com.owners.gravitas.domain.Stage;
import com.owners.gravitas.domain.Task;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.PhoneNumber;
import com.owners.gravitas.enums.OpportunityChangeType;
import com.owners.gravitas.enums.RequestType;
import com.owners.gravitas.handler.OpportunityChangeHandler;
import com.owners.gravitas.handler.OpportunityChangeHandlerFactory;

/**
 * The Class AgentBuilder.
 *
 * @author vishwanathm
 */
@Component( "agentBuilder" )
public class AgentBuilder extends AbstractBuilder< List< Map< String, Object > >, AgentHolder > {

    /** The Constant NEW_LINE. */
    private static final String NEW_LINE = "\n";

    /** The request summary builder. */
    @Autowired
    private RequestSummaryBuilder requestSummaryBuilder;

    /** The enable opportunity buyer request. */
    @Value( "${opportunity.buyerRequest.enable: true}" )
    private boolean enableOpportunityBuyerRequest;

    /** The OpportunityChangeHandlerFactory *. */
    @Autowired
    private OpportunityChangeHandlerFactory opportunityChangeHandlerFactory;

    /** The contact builder. */
    @Autowired
    private ContactBuilder contactBuilder;

    /**
     * This method converts List object to Agent
     * object.
     *
     * @param records
     *            is the dto object to be converted to domain.
     * @param destination
     *            the destination
     * @return AgentHolder object
     */
    @Override
    public AgentHolder convertTo( List< Map< String, Object > > records, AgentHolder destination ) {
        AgentHolder agentHolder = destination;
        Agent agent = null;
        if (agentHolder == null) {
            agentHolder = new AgentHolder();
        }
        if (agent == null) {
            agent = new Agent();
            // create agent info
            final AgentInfo info = createAgentInfo();
            agent.setInfo( info );
        }
        if (records != null) {
            for ( final Map< String, Object > crmRecord : records ) {
                final Map< String, Object > opportunityRecord = ( Map ) ( Object ) crmRecord.get( "Opportunity" );
                final Map< String, Object > contactRecord = ( Map ) ( Object ) crmRecord.get( "Contact" );

                final String opportunityId = UUID.randomUUID().toString();
                final String contactId = UUID.randomUUID().toString();
                // create opportunity
                final Opportunity opportunity = createOpportunity( opportunityRecord );
                opportunity.addContact( contactId );
                // create contact
                final Contact contact = createContact( convertObjectToString( crmRecord.get( "ContactId" ) ),
                        contactRecord );
                // create agent
                final com.owners.gravitas.domain.Contact contactDomain = contactBuilder.convertFrom( contact );
                agent.addContact( contactId, contactDomain );
                agent.addOpportunity( opportunityId, opportunity );
                final Task task = buildTask( agentHolder.getAgentId(), opportunityId, opportunity, contact );
                if (task != null) {
                    agent.addTask( UUID.randomUUID().toString(), task );
                }
                if (enableOpportunityBuyerRequest) {
                    agent.addRequest( UUID.randomUUID().toString(), buildRequest( opportunityRecord, opportunityId ) );
                }

            }
        }
        agentHolder.setAgent( agent );
        return agentHolder;
    }

    /**
     * This method AgentHolder object to List.
     * object.
     *
     * @param source
     *            is the dto object to be converted to domain.
     * @param destination
     *            the destination
     * @return AgentHolder object
     */
    @Override
    public List< Map< String, Object > > convertFrom( AgentHolder source, List< Map< String, Object > > destination ) {
        throw new UnsupportedOperationException( "convertFrom is not supported" );
    }

    /**
     * Creates the task.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param opportunity
     *            the opportunity
     * @param contact
     *            the contact
     * @return the task
     */
    private Task buildTask( final String agentId, final String opportunityId, final Opportunity opportunity,
            final Contact contact ) {
        final OpportunityChangeHandler opportunityChangeHandler = opportunityChangeHandlerFactory
                .getChangeHandler( OpportunityChangeType.Stage, opportunity.getStage() );
        if (opportunityChangeHandler != null) {
            return opportunityChangeHandler.buildTask( agentId, opportunityId, contact );
        }
        return null;
    }

    /**
     * Creates the agent.
     *
     * @param agentEmail
     *            the agetn email
     * @return the agent info
     */
    private AgentInfo createAgentInfo() {
        final AgentInfo info = new AgentInfo();
        info.setLastLoginDtm( new Date().getTime() );
        info.setLastModifiedDtm( new Date().getTime() );
        info.setOnDuty( Boolean.TRUE );
        return info;
    }

    /**
     * Converts Map< String, String > to OpportunitySource.
     *
     * @param opportunityRecord
     *            the opportunity record
     * @return the opportunity source
     */
    private Opportunity createOpportunity( final Map< String, Object > opportunityRecord ) {
        final long currentTime = new Date().getTime();
        final Opportunity opportunity = new Opportunity();
        final Map< String, String > recordType = ( Map ) ( Object ) opportunityRecord.get( "RecordType" );
        opportunity.setAccepted( TRUE);
        opportunity.setDeleted( FALSE );
        opportunity.setAcceptedDtm( currentTime );
        opportunity.setCreatedDtm( currentTime );
        opportunity.setAssignedDtm( currentTime );
        opportunity.setCreatedBy( GRAVITAS );
        opportunity.setCrmId( convertObjectToString( opportunityRecord.get( "Id" ) ) );
        opportunity.setLeadSource( convertObjectToString( opportunityRecord.get( "LeadSource" ) ) );
        opportunity.setWorkingWithExternalAgent(
                convertObjectToString( opportunityRecord.get( "Working_with_External_Agent__c" ) ) );
        opportunity.setBuyerReadinessTimeline(
                convertObjectToString( opportunityRecord.get( "Buyer_Readiness_Timeline__c" ) ) );
        opportunity.setBudget( convertObjectToString( opportunityRecord.get( "Price_Range__c" ) ) );
        opportunity
                .setMedianPrice( toBigDecimal( convertObjectToString( opportunityRecord.get( "Median_Price__c" ) ) ) );
        opportunity.setStage( convertObjectToString( opportunityRecord.get( "StageName" ) ) );
        opportunity.setReasonLost( convertObjectToString( opportunityRecord.get( "Reason_Lost__c" ) ) );
        opportunity
                .setBuySideCommission( convertObjectToString( opportunityRecord.get( "Commission_Post_Closing__c" ) ) );
        opportunity.setSalesPrice(
                toBigDecimal( convertObjectToString( opportunityRecord.get( "Sales_Price_Post_Closing__c" ) ) ) );
        opportunity.setExpectedContractDate( toDate( convertObjectToString( opportunityRecord.get( "CloseDate" ) ) ) );
        opportunity.setPreApprovedAmount(
                toBigDecimal( convertObjectToString( opportunityRecord.get( "Pre_approved_Amount__c" ) ) ) );
        opportunity.setOpportunityType( recordType.get( "Name" ) );
        opportunity.setCommissionBasePrice(
                toBigDecimal( convertObjectToString( opportunityRecord.get( "Commission_Base_Price__c" ) ) ) );
        opportunity.setTitleCompany( convertObjectToString( opportunityRecord.get( "Title_Company_Non_PTS__c" ) ) );
        opportunity.setPendingDate( toDate( convertObjectToString( opportunityRecord.get( "Pending_Date__c" ) ) ) );
        opportunity.setActualClosingDate(
                toDate( convertObjectToString( opportunityRecord.get( "Acutal_Closing_Date__c" ) ) ) );
        opportunity.setExpectedAgentRevenue(
                toBigDecimal( convertObjectToString( opportunityRecord.get( "Expected_Agent_Revenue__c" ) ) ) );
        opportunity.setExpectedOwnersComRevenue(
                toBigDecimal( convertObjectToString( opportunityRecord.get( "Expected_Owners_com_Revenue__c" ) ) ) );
        opportunity.setAreaOfInterest( convertObjectToString( opportunityRecord.get( "Interested_Zip_Codes__c" ) ) );
        opportunity.setNotes( convertObjectToString( opportunityRecord.get( "Opportunity_Notes__c" ) ) );
        opportunity.setPreApprovedForMortgage(
                convertObjectToString( opportunityRecord.get( "Pre_Approved_for_Mortgage__c" ) ) );
        opportunity.setPreferredLanguage( convertObjectToString( opportunityRecord.get( "Preferred_Language__c" ) ) );
        opportunity.setTitleSelectionReason(
                convertObjectToString( opportunityRecord.get( "Title_Selection_Reason__c" ) ) );
        final String[] requests = getAllRequests(
                convertObjectToString( opportunityRecord.get( "Gravitas_Record_History__c" ) ) );
        opportunity.setRequestSummary( requestSummaryBuilder.convertTo( requests ) );
        final Stage stage = new Stage();
        stage.setStage( convertObjectToString( opportunityRecord.get( "StageName" ) ) );
        stage.setTimestamp( currentTime );
        opportunity.addStage( stage );
        opportunity.setPriceRange( convertObjectToString( opportunityRecord.get( "Price_Ranges__c" ) ) );
        opportunity.setPropertyAddress( convertObjectToString( opportunityRecord.get( "Property_Address__c" ) ) );
        opportunity.setListingSideCommission(
                convertObjectToString( opportunityRecord.get( "Listing_Side_Commission__c" ) ) );
        opportunity.setListPrice( toBigDecimal( convertObjectToString( opportunityRecord.get( "List_Price__c" ) ) ) );
        opportunity.setListDate( toDate( convertObjectToString( opportunityRecord.get( "Listing_Date__c" ) ) ) );
        opportunity.setExpirationDate(
                toDate( convertObjectToString( opportunityRecord.get( "Listing_Expiration_Date__c" ) ) ) );
        opportunity
                .setOfferAmount( toBigDecimal( convertObjectToString( opportunityRecord.get( "Offer_Amount__c" ) ) ) );
        return opportunity;
    }

    /**
     * Creates the contact.
     *
     * @param crmContactId
     *            the crm contact id
     * @param contactRecord
     *            the contact record
     * @return the contact
     */
    private Contact createContact( final String crmContactId, final Map< String, Object > contactRecord ) {
        final Contact contact = new Contact();
        contact.setCrmId( crmContactId );
        contact.setFirstName( convertObjectToString( contactRecord.get( "FirstName" ) ) );
        contact.setLastName( convertObjectToString( contactRecord.get( "LastName" ) ) );
        contact.setLastModifiedDtm( new Date().getTime() );
        contact.setLastModifiedBy( getLastModifiedBy( contactRecord ) );
        if (contactRecord.get( "Email" ) != null) {
            contact.addEmail( convertObjectToString( contactRecord.get( "Email" ) ) );
        }
        if (contactRecord.get( "Phone" ) != null) {
            contact.addPhone( new PhoneNumber( PRIMARY, convertObjectToString( contactRecord.get( "Phone" ) ) ) );
        }
        contact.setPreferredContactMethod(
                convertObjectToString( contactRecord.get( "Preferred_Contact_Method__c" ) ) );
        contact.setPreferredContactTime( convertObjectToString( contactRecord.get( "Preferred_Contact_Time__c" ) ) );
        return contact;
    }

    /**
     * Gets the last modified by.
     *
     * @param map
     *            the map
     * @return the last modified by
     */
    private String getLastModifiedBy( Object map ) {
        return ( map != null ) ? ( ( Map< String, String > ) map ).get( "FirstName" ) + " "
                + ( ( Map< String, String > ) map ).get( "LastName" ) : StringUtils.EMPTY;
    }

    /**
     * Builds the request.
     *
     * @param opportunityRecord
     *            the opportunity record
     * @param opportunityId
     *            the opportunity id
     * @return the request
     */
    private Request buildRequest( final Map< String, Object > opportunityRecord, final String opportunityId ) {
        Request request = null;
        String leadRequestType = convertObjectToString( opportunityRecord.get( "Lead_Request_Type__c" ) );
        if (leadRequestType != null) {
            request = new Request();
            switch ( leadRequestType ) {
                case "Make An Offer":
                    request.setType( RequestType.BUYER_OFFER );
                    request.setPreApprovaedForMortgage(
                            convertObjectToString( opportunityRecord.get( "Pre_Approved_for_Mortgage__c" ) ) );
                    request.setOfferAmount(
                            toBigDecimal( convertObjectToString( opportunityRecord.get( "Offer_Amount__c" ) ) ) );
                    request.setEarnestMoneyDeposit(
                            convertObjectToString( opportunityRecord.get( "Earnest_Money_Deposit__c" ) ) );
                    request.setPurchaseMethod( convertObjectToString( opportunityRecord.get( "Purchase_Method__c" ) ) );
                    request.setDownPayment( convertObjectToString( opportunityRecord.get( "Down_Payment__c" ) ) );
                    break;
                case "Schedule a Tour":
                    request.setType( RequestType.APPOINTMENT );
                    request.setPropertyTourInfo(
                            convertObjectToString( opportunityRecord.get( "Property_Tour_Information__c" ) ) );
                    break;
                case "Request Information":
                    request.setType( RequestType.INQUIRY );
                    break;
                default:
                    request = null;
            }
            if (request != null) {
                request.setConverted( false );
                request.setLeadMessage( convertObjectToString( opportunityRecord.get( "Lead_Message__c" ) ) );
                request.setListingId( convertObjectToString( opportunityRecord.get( "Listing_ID__c" ) ) );
                request.setOpportunityId( opportunityId );
                request.setOpportunityNotes( convertObjectToString( opportunityRecord.get( "Opportunity_Notes__c" ) ) );
                request.setLastModifiedDtm( new Date().getTime() );
                request.setCreatedBy( GRAVITAS );
                request.setCreatedDtm( new Date().getTime() );
            }
        }
        return request;
    }

    /**
     * Builds the request summary.
     *
     * @param recordHistory
     *            the record history
     * @return the string
     */
    private String[] getAllRequests( final String recordHistory ) {
        return StringUtils.isNotBlank( recordHistory ) ? recordHistory.split( NEW_LINE ) : new String[0];
    }

    /**
     * To big decimal.
     *
     * @param value
     *            the value
     * @return the big decimal
     */
    private BigDecimal toBigDecimal( String value ) {
        return value == null ? null : BigDecimal.valueOf( Double.valueOf( value ) );
    }
}
