package com.owners.gravitas.business.impl;

import static com.owners.gravitas.constants.CRMQuery.GET_CRM_AGENT_DETAILS_BY_EMAIL;
import static com.owners.gravitas.constants.CRMQuery.GET_OPPORTUNITY_BY_ID;
import static com.owners.gravitas.constants.Constants.BLANK;
import static com.owners.gravitas.constants.Constants.EMAIL;
import static com.owners.gravitas.constants.Constants.ID;
import static com.owners.gravitas.constants.Constants.OCL_REFFERAL_STATUS;
import static com.owners.gravitas.constants.Constants.STAGE;
import static com.owners.gravitas.dto.response.BaseResponse.Status.SUCCESS;
import static com.owners.gravitas.enums.BuyerStage.CLAIMED;
import static com.owners.gravitas.enums.BuyerStage.NEW;
import static com.owners.gravitas.enums.CRMObject.LEAD;
import static com.owners.gravitas.enums.CRMObject.OPPORTUNITY;
import static com.owners.gravitas.enums.LeadRequestType.OTHER;
import static com.owners.gravitas.enums.LeadStatus.FORWARDED_TO_REF_EX;
import static com.owners.gravitas.enums.OpportunityChangeType.Stage;
import static com.owners.gravitas.enums.ProspectAttributeType.FARMING_GROUP;
import static com.owners.gravitas.enums.PushNotificationType.NEW_OPPORTUNITY;
import static com.owners.gravitas.enums.PushNotificationType.UPDATE_BADGE_COUNTER;
import static com.owners.gravitas.enums.RecordType.BUYER;
import static com.owners.gravitas.util.ObjectUtil.isNull;
import static com.owners.gravitas.util.StringUtils.convertObjectToString;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.Math.round;
import static java.lang.System.currentTimeMillis;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.ActionFlowBusinessService;
import com.owners.gravitas.business.AgentLookupBusinessService;
import com.owners.gravitas.business.AgentNotificationBusinessService;
import com.owners.gravitas.business.AgentOpportunityBusinessService;
import com.owners.gravitas.business.AgentTaskBusinessService;
import com.owners.gravitas.business.BuyerFarmingBusinessService;
import com.owners.gravitas.business.LeadBusinessService;
import com.owners.gravitas.business.OpportunityBusinessService;
import com.owners.gravitas.business.builder.ContactBuilder;
import com.owners.gravitas.business.builder.OclLeadDetailsBuilder;
import com.owners.gravitas.business.builder.OppAssignmentBuyerNotificationBuilder;
import com.owners.gravitas.business.builder.domain.OpportunityBuilder;
import com.owners.gravitas.business.task.OpportunityTask;
import com.owners.gravitas.config.AgentOpportunityBusinessConfig;
import com.owners.gravitas.config.BadgeCounterJmxConfig;
import com.owners.gravitas.config.CoShoppingConfig;
import com.owners.gravitas.config.HappyAgentsConfig;
import com.owners.gravitas.config.LeadBusinessConfig;
import com.owners.gravitas.config.LeadOpportunityBusinessConfig;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.domain.Agent;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.domain.Request;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.domain.Stage;
import com.owners.gravitas.domain.entity.AgentCommission;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.domain.entity.ContactAttribute;
import com.owners.gravitas.domain.entity.ObjectType;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.Preference;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.dto.request.NotificationRequest;
import com.owners.gravitas.dto.request.OclLeadRequest;
import com.owners.gravitas.dto.response.AgentCommissionResponse;
import com.owners.gravitas.dto.response.AgentEmailPermissionResponse;
import com.owners.gravitas.dto.response.AgentResponse;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.LeadResponse;
import com.owners.gravitas.dto.response.NotificationPreferenceResponse;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.enums.BuyerFarmType;
import com.owners.gravitas.enums.BuyerStage;
import com.owners.gravitas.enums.ErrorCode;
import com.owners.gravitas.enums.LeadSourceName;
import com.owners.gravitas.enums.NotificationType;
import com.owners.gravitas.enums.PushNotificationType;
import com.owners.gravitas.enums.RecordType;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.handler.OpportunityChangeHandler;
import com.owners.gravitas.handler.OpportunityChangeHandlerFactory;
import com.owners.gravitas.service.AgentCommissionService;
import com.owners.gravitas.service.AgentContactService;
import com.owners.gravitas.service.AgentDetailsService;
import com.owners.gravitas.service.AgentOpportunityService;
import com.owners.gravitas.service.AgentRequestService;
import com.owners.gravitas.service.AgentService;
import com.owners.gravitas.service.CRMQueryService;
import com.owners.gravitas.service.ContactEntityService;
import com.owners.gravitas.service.MailService;
import com.owners.gravitas.service.ObjectTypeService;
import com.owners.gravitas.service.OpportunityService;
import com.owners.gravitas.service.RecordTypeService;
import com.owners.gravitas.service.SearchService;
import com.owners.gravitas.service.StageLogService;
import com.owners.gravitas.service.builder.AuditTrailOpportunityBuilder;
import com.owners.gravitas.util.JsonUtil;

/**
 * The Class AgentOpportunityBusinessServiceImpl.
 */
@Service( "agentOpportunityBusinessService" )
@Transactional
public class AgentOpportunityBusinessServiceImpl implements AgentOpportunityBusinessService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentOpportunityBusinessServiceImpl.class );
    
    /** The Constant AUTO_OCL_LEAD_SOURCE. */
    private static final String AUTO_OCL_LEAD_SOURCE = "AutOCL";

    /** The happy agents config. */
    @Autowired
    private HappyAgentsConfig happyAgentsConfig;

    /** The agent lookup business service. */
    @Autowired
    private AgentLookupBusinessService agentLookupBusinessService;

    /** The agent opportunity service. */
    @Autowired
    private AgentOpportunityService agentOpportunityService;

    /** The agent contact service. */
    @Autowired
    private AgentContactService agentContactService;

    /** The agent task business service. */
    @Autowired
    private AgentTaskBusinessService agentTaskBusinessService;

    /** The agent request service. */
    @Autowired
    private AgentRequestService agentRequestService;

    /** The opportunity service. */
    @Autowired
    protected OpportunityService opportunityService;

    /** The search service. */
    @Autowired
    private SearchService searchService;

    /** The opportunity business service. */
    @Autowired
    private OpportunityBusinessService opportunityBusinessService;

    /** The agent push notification business service. */
    @Autowired
    private AgentNotificationBusinessService agentNotificationBusinessService;

    /** The opportunity builder. */
    @Autowired
    private OpportunityBuilder opportunityBuilder;

    /** The audit trail opportunity builder. */
    @Autowired
    private AuditTrailOpportunityBuilder auditTrailOpportunityBuilder;

    /** The object type service. */
    @Autowired
    private ObjectTypeService objectTypeService;

    /** The agent request business service impl. */
    @Autowired
    private AgentRequestBusinessServiceImpl agentRequestBusinessServiceImpl;

    /** The OpportunityChangeHandlerFactory *. */
    @Autowired
    @Lazy
    private OpportunityChangeHandlerFactory opportunityChangeHandlerFactory;

    /** The opportunity task. */
    @Autowired
    private OpportunityTask opportunityTask;

    /** The agent service. */
    @Autowired
    private AgentService agentService;

    @Autowired
    private AgentDetailsService agentDetailsService;

    /** The default lead source url. */
    @Value( "${agent.app.opportunity.lead.source}" )
    private String defaultLeadSourceUrl;

    /** The default source. */
    @Value( "${agent.app.opportunity.source}" )
    private String defaultSource;

    /** The pre approved for mortgage. */
    @Value( "${agent.app.opportunity.pre.approved.for.mortgage}" )
    private String defaultPreApprovedForMortgage;

    /** The buyer readiness timeline. */
    @Value( "${agent.app.opportunity.buyer.readiness.timeline}" )
    private String defaultBuyerReadinessTimeline;

    /** The working with realtor. */
    @Value( "${agent.app.opportunity..working.with.realtor}" )
    private String defaultWorkingWithRealtor;

    /** The price range. */
    @Value( "${agent.app.opportunity.price.range}" )
    private String defaultPriceRange;

    /** The interested zipcodes. */
    @Value( "${agent.app.opportunity.interested.zipcodes}" )
    private String defaultInterestedZipcodes;

    @Value( "${agent.opportunity.stage.message}" )
    private String stageMessage;

    /** The Constant REASON_LOST_DETAILS. */
    @Value( "${agent.opportunity.stage.reasonLostDetails}" )
    private String reasonLostDetails;

    /** The lead business service. */
    @Autowired
    private LeadBusinessService leadBusinessService;

    /** The record type service. */
    @Autowired
    private RecordTypeService recordTypeService;

    /** The agent opportunity business config. */
    @Autowired
    private AgentOpportunityBusinessConfig agentOpportunityBusinessConfig;

    /** The contact builder. */
    @Autowired
    private ContactBuilder contactBuilder;

    /** The crm query service. */
    @Autowired
    private CRMQueryService crmQueryService;

    /** The lead business config. */
    @Autowired
    private LeadBusinessConfig leadBusinessConfig;

    /** The stage log service. */
    @Autowired
    private StageLogService stageLogService;

    /** The badge counter jmx config. */
    @Autowired
    private BadgeCounterJmxConfig badgeCounterJmxConfig;

    /** The action flow business service. */
    @Autowired
    private ActionFlowBusinessService actionFlowBusinessService;

    /** The buyer registration business service. */
    @Autowired
    private BuyerFarmingBusinessService buyerFarmingBusinessService;

    /** The buyer registration business service. */
    @Autowired
    private AgentCommissionService agentCommissionService;

    /** The contact service V 1. */
    @Autowired
    private ContactEntityService contactServiceV1;

    /** The mail service. */
    @Autowired
    private MailService mailService;

    /** The runtime service. */
    @Autowired
    protected RuntimeService runtimeService;

    @Autowired
    private LeadOpportunityBusinessConfig leadOpportunityBusinessConfig;

    @Autowired
    private CoShoppingConfig coShoppingconfig;
    
    @Autowired
    OppAssignmentBuyerNotificationBuilder oppAssignmentBuyerNotificationBuilder;
    
    /** The Ocl LeadDetails Builder. */
    @Autowired
    private OclLeadDetailsBuilder oclLeadDetailsBuilder;

    /** The buyer business service. */
    @Autowired
    private LeadBusinessService buyerBusinessService;
    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentOpportunityBusinessService#
     * handleOpportunityChange(com.owners.gravitas.amqp.OpportunitySource)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public void handleOpportunityChange( final OpportunitySource opportunitySource ) {
        LOGGER.info( "In handleOpportunityChange()" );
        opportunityTask.updateOpportunityScore( opportunitySource );
        if (isEligibleForReferral( opportunitySource )) {
            forwardToReferralExchange( opportunitySource );
        } else {
            processOpportunityChange( opportunitySource );
        }
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentOpportunityBusinessService#
     * patchOpportunity(java.lang.String, java.lang.String, java.util.Map)
     */
    @Override
    public AgentResponse patchOpportunity( final String agentId, final String opportunityId,
            final Map< String, Object > request ) {
        LOGGER.info( "Update opportunity " + opportunityId + " for agent " + agentId );
        final boolean isCrmRequest = isCrmRequest( request );
        if (request.size() > 0) {
            boolean isStageChanged = false;
            final Opportunity opportunity = agentOpportunityService.getOpportunityById( agentId, opportunityId );
            if (request.containsKey( STAGE )
                    && !String.valueOf( request.get( STAGE ) ).equalsIgnoreCase( opportunity.getStage() )) {

                if (isCrmRequest && ( String.valueOf( request.get( STAGE ) )
                        .equals( BuyerStage.SHOWING_HOMES.getStage().toString() )
                        || String.valueOf( request.get( STAGE ) )
                                .equals( BuyerStage.FACETOFACE.getStage().toString() ) )) {
                    return new AgentResponse( opportunityId, Status.FAILURE, stageMessage );
                }

                final List< Stage > stageHistory = opportunity.getStageHistory();
                final Stage stage = new Stage();
                stage.setStage( String.valueOf( request.get( STAGE ) ) );
                final long timestamp = Instant.now().toEpochMilli();
                stage.setTimestamp( timestamp );
                stageHistory.add( stage );
                request.put( "stageHistory", stageHistory );
                opportunity.setStage( String.valueOf( request.get( STAGE ) ) );
                isStageChanged = true;
                setFirstContactDtm( opportunity, request, timestamp );
            }
            if (request.containsKey( "reasonLost" )
                    && "other".equals( String.valueOf( request.get( "reasonLost" ) ).toLowerCase().trim() )
                    && StringUtils.isBlank( convertObjectToString( request.get( "reasonLostDetails" ) ) )) {
                request.put( "reasonLostDetails", reasonLostDetails );
            }
            request.put( "lastModifiedDtm", new Date().getTime() );
            opportunityBusinessService.updateCRMOpportunity( request, opportunity.getCrmId() );
            final com.owners.gravitas.domain.Contact contact = agentContactService.getContactById( agentId,
                    String.valueOf( opportunity.getContacts().toArray()[0] ) );
            agentOpportunityService.patchOpportunity( agentId, opportunityId, request, opportunity,
                    contact.getEmails().get( 0 ) );
            // above "request" object wont be carrying any value which need to
            // be persisted thats why no db call required here
            if (isStageChanged) {
                final Contact contactDto = contactBuilder.convertTo( contact );
                handleStageChange( agentId, opportunityId, opportunity.getStage(), contactDto, opportunity.getCrmId() );
            }
            
            //Creates AutOcl lead
            if (request.containsKey( OCL_REFFERAL_STATUS )
                    && String.valueOf( request.get( OCL_REFFERAL_STATUS ) ).equalsIgnoreCase( "Referred" )) {
                LOGGER.info( "Creating Auto ocl lead for crmId : {} ", opportunity.getCrmId() );

                final OclLeadRequest oclLeadRequest = new OclLeadRequest();
                oclLeadRequest.setCrmId( opportunity.getCrmId() );
                final LeadSource leadSource = oclLeadDetailsBuilder.convertTo( oclLeadRequest );
                leadSource.setSource( LeadSourceName.OUTSIDE_REFERRAL.getSource() );
                leadSource.setLeadSourceUrl( AUTO_OCL_LEAD_SOURCE );

                final LeadResponse leadResponse = buyerBusinessService.createOclLead( leadSource, false, null );
                LOGGER.info( "Auto ocl lead  Creation Response={},  ", JsonUtil.toJson( leadResponse ) );
            }
            
        }
        return new AgentResponse( opportunityId, Status.SUCCESS, "Opportunity update successful" );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentOpportunityBusinessService#
     * createAgentOpportunity(java.lang.String,
     * com.owners.gravitas.dto.request.LeadRequest)
     */
    @Override
    @Transactional( propagation = REQUIRES_NEW )
    public BaseResponse createAgentOpportunity( final String agentEmail, final LeadRequest agentLeadRequest ) {
        final String leadEmail = agentLeadRequest.getEmail();
        final String recordType = RecordType.getRecordTypeType( agentLeadRequest.getLeadType() );
        LOGGER.info(
                "creating self gen opp for agent: " + agentEmail + " buyer: " + leadEmail + " RecType: " + recordType );

        final String oppRecordTypeId = recordTypeService.getRecordTypeIdByName( recordType, OPPORTUNITY.getName() );
        String crmOpportunityId = opportunityBusinessService.getOpportunityIdByRecordTypeAndEmail( oppRecordTypeId,
                leadEmail );
        if (StringUtils.isNotBlank( crmOpportunityId )) {
            LOGGER.info( "Opportunity aleady exists with CRM ID: " + crmOpportunityId );
            throw new ApplicationException( "Email id already exists!", ErrorCode.EMAIL_ID_ALREADY_EXISTS );
        }

        final String leadRecordTypeId = recordTypeService.getRecordTypeIdByName( recordType, LEAD.getName() );
        final String leadId = leadBusinessService.getLeadIdByRequestTypeAndEmail( leadRecordTypeId, leadEmail );
        if (StringUtils.isNotBlank( leadId )) {
            LOGGER.info( "Leas aleady exists with CRM ID: " + leadId );
            throw new ApplicationException( "Email id already exists!", ErrorCode.EMAIL_ID_ALREADY_EXISTS );
        }

        setDefaultParameters( agentLeadRequest );
        agentLeadRequest.setState( getState(agentEmail) );
        final LeadResponse leadResponse = leadBusinessService.createLead( agentLeadRequest, FALSE, null );
        LOGGER.info( "Lead created with ID " + leadResponse.getId() );

        crmOpportunityId = leadBusinessService.convertLeadToOpportunity( leadResponse.getId() );
        updateAgentOpportunity( agentEmail, oppRecordTypeId, agentLeadRequest.getPriceRange(), crmOpportunityId );
        LOGGER.info( "Updated self gen opportunity" );

        // assign opportunity to Agent;
        final OpportunitySource opportunitySource = opportunityBusinessService.getOpportunity( crmOpportunityId );
        handleOpportunityChange( opportunitySource );
        return new BaseResponse( SUCCESS, crmOpportunityId );
    }

    /**
     * @param email
     * @return
     */
    private String getState(String email) {
        final Map< String, Object > crmAgentDetails = getAgentDetails( email );
        final String state = isNull( crmAgentDetails.get( "State__c" ) );
        return state;
    }
    /**
     * Forward to referral exchange.
     *
     * @param opportunitySource
     *            the opportunity source
     */
    private void forwardToReferralExchange( final OpportunitySource opportunitySource ) {
        final String crmId = opportunitySource.getCrmId();
        LOGGER.info( "Forwarding the opportunity to ref ex: " + crmId );
        buyerFarmingBusinessService.updateFarmingStatus( crmId, BuyerFarmType.LONG_TERM_BUYER, true );
        opportunityBusinessService.forwardToReferralExchange( opportunitySource );
        final com.owners.gravitas.domain.entity.Contact contact = contactServiceV1
                .getContactByCrmId( opportunitySource.getCrmId() );
        contact.setStage( BuyerStage.FORWARDED_TO_REF_EX.getStage() );
        contactServiceV1.save( contact );
    }

    /**
     * Process opportunity change.
     *
     * @param opportunitySource
     *            the opportunity source
     */
    private void processOpportunityChange( final OpportunitySource opportunitySource ) {
        final String crmOppId = opportunitySource.getCrmId();
        final Search opportunitySearch = searchService.searchByCrmOpportunityId( crmOppId );
        final String oppSourceAgentEmail = opportunitySource.getAgentEmail();
        LOGGER.info( "agent email for opportunity: " + crmOppId + " in processOpportunityChange is: "
                + oppSourceAgentEmail );
        if (null != opportunitySearch) { // opportunity was on-boarded earlier
            final Opportunity existingOpportunity = agentOpportunityService
                    .getOpportunityById( opportunitySearch.getAgentId(), opportunitySearch.getOpportunityId() );
            LOGGER.info( "Opportunity searched on FB by: " + crmOppId + ", Found:" + ( existingOpportunity != null ) );
            if (isEmpty( oppSourceAgentEmail )) {
                // opportunity is taken away from old & not assigned to anyone
                unassignOpportunity( existingOpportunity, opportunitySearch, opportunitySource.isStageChanged(),
                        false );
            } else {
                processAgentAssignment( opportunitySource, opportunitySearch, existingOpportunity );
                startBuyerReminderProcess( opportunitySource );
            }
        } else if (isNotEmpty( oppSourceAgentEmail )) {
            // new opportunity assigned to agent
            LOGGER.info( "processing new opportunity " + crmOppId );
            processNewOpportunity( opportunitySource );
            startBuyerReminderProcess( opportunitySource );
        }
        if (!opportunitySource.isStageChanged()) {
            handleFarmingGroupChangeChange( opportunitySource.getCrmId(), opportunitySource.getFarmingGroup() );
        }
    }

    private void startBuyerReminderProcess( final OpportunitySource opportunitySource ) {
        if (leadOpportunityBusinessConfig.isBuyerDownloadAppAlertEnabled()
                && null != opportunitySource.getPrimaryContact()) {
            final String email = CollectionUtils.isEmpty( opportunitySource.getPrimaryContact().getEmails() ) ? BLANK
                    : opportunitySource.getPrimaryContact().getEmails().get( 0 );
            if (email.matches( leadOpportunityBusinessConfig.getBuyerDownloadAppEmailFilter() )) {
                final Map< String, Object > initParams = new HashMap<>();
                initParams.put( Constants.OPPORTUNITY, opportunitySource );
                initParams.put( Constants.BUYER_REMINDER_DELAY, leadOpportunityBusinessConfig.getBuyerReminderDelay() );
                runtimeService.startProcessInstanceByKey( "appDownloadReminderProcess", initParams );
            }
        }
    }

    /**
     * Process agent assignment.
     *
     * @param opportunitySource
     *            the opportunity source
     * @param opportunitySearch
     *            the opportunity search
     * @param existingOpportunity
     *            the existing opportunity
     */
    private void processAgentAssignment( final OpportunitySource opportunitySource, final Search opportunitySearch,
            final Opportunity existingOpportunity ) {
        final String crmOppId = existingOpportunity.getCrmId();
        final String oppSourceAgentEmail = opportunitySource.getAgentEmail();
        final Search newAgentSearch = searchService.searchByAgentEmail( oppSourceAgentEmail );
        if (null != newAgentSearch) {
            if (opportunitySearch.getAgentEmail().equalsIgnoreCase( oppSourceAgentEmail )) {
                // some field change in opportunity OR assigned to same agent
                // again.
                LOGGER.info( "Going to update the opportunity: " + crmOppId );
                updateOppotunity( opportunitySource, existingOpportunity, opportunitySearch );
            } else {
                LOGGER.info( "Transferring the opportunity: " + crmOppId + " to a new agent: "
                        + opportunitySearch.getAgentEmail() );
                unassignOpportunity( existingOpportunity, opportunitySearch, opportunitySource.isStageChanged(), true );
                transferOpportunityFromOldToNewAgent( existingOpportunity, opportunitySearch, newAgentSearch );
            }
        } else {
            // this means new agent is not registered in firebase
            unassignOpportunity( existingOpportunity, opportunitySearch, opportunitySource.isStageChanged(), false );
            if (toAutoAssign( oppSourceAgentEmail, opportunitySource.getRecordType() )) {
                LOGGER.info( "opportunity: " + crmOppId + " is reassigned to AUTOASSIGN agent" );
                agentLookupBusinessService.getMostEligibleAgent( opportunitySource, true, null, null );
            }
        }
    }

    /**
     * Checks if is eligible for referral.
     *
     * @param opportunitySource
     *            the opportunity source
     * @return true, if is eligible for referral
     */
    private boolean isEligibleForReferral( final OpportunitySource opportunitySource ) {
        return ( agentOpportunityBusinessConfig.isOpportunityReferralEnabled()
                && FORWARDED_TO_REF_EX.getStatus().equalsIgnoreCase( opportunitySource.getStage() )
                && !opportunitySource.isReferred() && isExcludedReferralState( opportunitySource.getPropertyState() ) );
    }

    /**
     * Checks if is excluded referral state.
     *
     * @param leadState
     *            the lead state
     * @return true, if is excluded referral state
     */
    private boolean isExcludedReferralState( final String leadState ) {
        return StringUtils.isNotBlank( leadState )
                && !leadBusinessConfig.getReferralExcludedStates().contains( leadState );
    }

    /**
     * Assign deleted opportunity to same agent.
     *
     * @param existingOpportunity
     *            the existing opportunity
     * @param agentSearch
     *            the agent search
     * @param contactEmail
     *            the contact email
     */
    private void assignDeletedOpportunityToSameAgent( final Opportunity existingOpportunity, final Search agentSearch,
            final String contactEmail ) {
        final String agentId = agentSearch.getAgentId();
        final String opportunityId = agentSearch.getOpportunityId();
        final String oppCrmId = existingOpportunity.getCrmId();

        LOGGER.info( "Assigning opportunity " + opportunityId + " to agent " + agentId );
        final Map< String, Object > updateParams = new HashMap<>();
        updateParams.put( "deleted", FALSE );
        updateParams.put( "lastModifiedDtm", new Date().getTime() );

        agentOpportunityService.patchOpportunity( agentId, opportunityId, updateParams, existingOpportunity,
                contactEmail );
        agentContactService.patchContact( agentId, existingOpportunity.getContacts().toArray()[0].toString(),
                updateParams );
        agentTaskBusinessService.reassignTasksToSameAgent( agentId, opportunityId );
        updateAgentRequests( agentId, opportunityId, FALSE );

        LOGGER.info( "This is active buyer opportunity :" + oppCrmId );
    }

    /**
     * Unassign opportunity.
     *
     * @param existingOpportunity
     *            the existing opportunity
     * @param existingOpportunitySearch
     *            the existing opportunity search
     * @param isStageChanged
     *            the is stage changed
     */
    private void unassignOpportunity( final Opportunity existingOpportunity, final Search existingOpportunitySearch,
            final boolean isStageChanged, final boolean isAgentRegistered ) {
        final String opportunityId = existingOpportunitySearch.getOpportunityId();
        final String agentId = existingOpportunitySearch.getAgentId();
        LOGGER.info( "Unassign opportunity " + opportunityId + " from agent " + agentId );

        final Map< String, Object > updateParams = new HashMap<>();
        updateParams.put( "deleted", TRUE );
        updateParams.put( "lastModifiedDtm", new Date().getTime() );
        existingOpportunity.setDeleted( TRUE );

        // delete opportunity
        agentOpportunityService.patchOpportunity( agentId, opportunityId, updateParams, existingOpportunity,
                existingOpportunitySearch.getContactEmail() );

        // delete contact
        final String contactId = existingOpportunity.getContacts().toArray()[0].toString();
        agentContactService.patchContact( agentId, contactId, updateParams );
        LOGGER.info( "deleted contact on FB" );

        // unassign tasks on FB and in DB
        agentTaskBusinessService.deleteTasks( opportunityId, agentId );
        if (!isAgentRegistered) {
            LOGGER.info( "Unassigning tasks for " + opportunityId + " and " + agentId );
            agentTaskBusinessService.unassignTasks( opportunityId, agentId );
        }

        // delete requests
        updateAgentRequests( agentId, opportunityId, TRUE );

        // badge count update when an opportunity gets unassigned
        if (badgeCounterJmxConfig.isBadgeCountEnabled()) {
            LOGGER.info( "Badge coutner update for agent unassign opportunity for agentId : {}", agentId );
            final NotificationRequest notificationRequest = new NotificationRequest(
                    agentOpportunityService.getAgentNewOpportunitiesCount( agentId ) );
            notificationRequest.setEventType( UPDATE_BADGE_COUNTER );
            agentNotificationBusinessService.sendPushNotification( agentId, notificationRequest );
            LOGGER.info( "Badge notification successfully sent" );
        }
    }

    /**
     * Update agent requests.
     *
     * @param newAgentId
     *            the new agent id
     * @param opportunityId
     *            the opportunity id
     * @param deleted
     *            the deleted
     */
    private void updateAgentRequests( final String newAgentId, final String opportunityId, final boolean deleted ) {
        LOGGER.info( "Updating agent requests for " + opportunityId + " and " + newAgentId );
        final Map< String, Request > requests = agentRequestService.getRequestsByOpportunityId( newAgentId,
                opportunityId );
        for ( final Map.Entry< String, Request > requestEntry : requests.entrySet() ) {
            requestEntry.getValue().setDeleted( deleted );
            requestEntry.getValue().setLastModifiedDtm( new Date().getTime() );
        }
        agentRequestService.saveAgentRequests( requests, newAgentId );
    }

    /**
     * Transfer opportunity from old to new agent.
     *
     * @param existingOpportunity
     *            the existing opportunity
     * @param oldAgentSearch
     *            the old agent search
     * @param newAgentSearch
     *            the new agent search
     */
    private void transferOpportunityFromOldToNewAgent( final Opportunity existingOpportunity,
            final Search oldAgentSearch, final Search newAgentSearch ) {
        final String oldAgentId = oldAgentSearch.getAgentId();
        final String newAgentId = newAgentSearch.getAgentId();
        final String oldOpportunityId = oldAgentSearch.getOpportunityId();
        final String oppCrmId = existingOpportunity.getCrmId();

        LOGGER.info( "transferring opportunity " + oldOpportunityId + " from agent " + oldAgentSearch.getAgentEmail()
                + " to " + newAgentSearch.getAgentEmail() );
        // copy contacts to new agent
        final Map< String, String > newContacts = copyContact( oldAgentId, newAgentId,
                existingOpportunity.getContacts() );
        existingOpportunity.setContacts( newContacts.keySet() );
        resetValuesInReassignment( existingOpportunity );
        final String newOpportunityId = agentOpportunityService.saveOpportunity( newAgentId,
                newAgentSearch.getAgentEmail(), existingOpportunity, oldAgentSearch.getContactEmail() ).getName();
        LOGGER.info( "New opp id on FB is: " + newOpportunityId );

        stageLogService.saveOpportunityStagelog( newOpportunityId, existingOpportunity.getStage() );
        agentTaskBusinessService.copyTasksAndRequests( oldAgentId, newAgentId, oldOpportunityId, newOpportunityId,
                newContacts );

        // change status of old tasks to UNASSIGNED.
        agentTaskBusinessService.unassignTasks( oldOpportunityId, oldAgentId );

        String contactEmail = null;
        String contactId = null;
        for ( final Entry< String, String > contact : newContacts.entrySet() ) {
            contactId = contact.getKey();
            contactEmail = contact.getValue();
        }

        saveSearch( newAgentSearch.getAgentId(), newAgentSearch.getAgentEmail(), oppCrmId, newOpportunityId, contactId,
                contactEmail );
        searchService.delete( oldAgentSearch.getId() );

        if (!coShoppingconfig.isEnableScheduleTourMeetings()) {
            // sending scripted call and notification if
            // coShoppingconfig.isEnableScheduleTourMeetings() is false
            final boolean isEligibleForScriptedCall = actionFlowBusinessService.isEligibleForScriptedCall(
                    existingOpportunity.getOpportunityType(), newAgentSearch.getAgentEmail() );
            if (isEligibleForScriptedCall) {
                LOGGER.info( "agent is eligible for scripted call functionality :" + newAgentSearch.getAgentEmail() );
                actionFlowBusinessService.createActionGroup( newOpportunityId, newAgentId, existingOpportunity,
                        newAgentSearch );
            } else {
                LOGGER.info( "Sending PUSH notification when opp is transferred" );
                sendOpportunityPushNotifications( newOpportunityId, newAgentSearch );
            }
        }
        LOGGER.info( "This is active buyer opportunity :" + oppCrmId );
    }

    /**
     * Reset values in reassignment.
     *
     * @param existingOpportunity
     *            the existing opportunity
     */
    private void resetValuesInReassignment( final Opportunity existingOpportunity ) {
        existingOpportunity.setDeleted( FALSE );
        if (!existingOpportunity.getStage().equalsIgnoreCase( NEW.getStage() )
                && !existingOpportunity.getStage().equalsIgnoreCase( CLAIMED.getStage() )) {
            existingOpportunity.setFirstContactDtm( currentTimeMillis() );
        } else {
            existingOpportunity.setFirstContactDtm( null );
        }
        existingOpportunity.setAssignedDtm( currentTimeMillis() );
        existingOpportunity.setActionFlowIds( null );
    }

    /**
     * Update oppotunity.
     *
     * @param opportunitySource
     *            the opportunity source
     * @param existingOpportunity
     *            the existing opportunity
     * @param agentSearch
     *            the agent search
     */
    private void updateOppotunity( final OpportunitySource opportunitySource, final Opportunity existingOpportunity,
            final Search agentSearch ) {
        final String opportunityId = agentSearch.getOpportunityId();
        if (existingOpportunity.getDeleted()) {
            assignDeletedOpportunityToSameAgent( existingOpportunity, agentSearch,
                    opportunitySource.getPrimaryContact().getEmails().iterator().next() );
            if (!coShoppingconfig.isEnableScheduleTourMeetings()) {
                // sending scripted call and notification if
                // coShoppingconfig.isEnableScheduleTourMeetings() is false
                final boolean isEligibleForScriptedCall = actionFlowBusinessService.isEligibleForScriptedCall(
                        existingOpportunity.getOpportunityType(), agentSearch.getAgentEmail() );
                if (isEligibleForScriptedCall) {
                    LOGGER.info( "agent is eligible for scripted call functionality :" + agentSearch.getAgentEmail() );
                    actionFlowBusinessService.createActionGroup( opportunityId, agentSearch.getAgentId(),
                            existingOpportunity, agentSearch );
                } else {
                    LOGGER.info( "Sending PUSH notification when opp is assigned to same agent" );
                    sendOpportunityPushNotifications( opportunityId, agentSearch );
                }
            }
            // needed, as opportunity object is still referring to old value
            existingOpportunity.setDeleted( FALSE );
        }
        final Map< String, Object > updateParams = auditTrailOpportunityBuilder.convertTo( opportunitySource,
                existingOpportunity );
        agentOpportunityService.patchOpportunity( agentSearch.getAgentId(), opportunityId, updateParams,
                existingOpportunity, agentSearch.getContactEmail() );

        // create task on basis of opportunity stage
        if (opportunitySource.isStageChanged()) {
            handleStageChange( agentSearch.getAgentId(), opportunityId, opportunitySource.getStage(),
                    opportunitySource.getPrimaryContact(), opportunitySource.getCrmId() );
        }
        LOGGER.info( " updated opportunity with crm id " + opportunitySource.getCrmId() );
    }

    /**
     * To auto assign.
     *
     * @param oppSourceAgentEmail
     *            the opp source agent email
     * @param recordType
     *            the record type
     * @return true, if successful
     */
    private boolean toAutoAssign( final String oppSourceAgentEmail, final String recordType ) {
        return BUYER.getType().equalsIgnoreCase( recordType )
                && happyAgentsConfig.getAutoAssignAgentEmail().equalsIgnoreCase( oppSourceAgentEmail );
    }

    /**
     * Process new opportunity.
     *
     * @param opportunitySource
     *            the opportunity source
     */
    private void processNewOpportunity( final OpportunitySource opportunitySource ) {
        final String oppSrcAgentEmail = opportunitySource.getAgentEmail();
        final String crmOppId = opportunitySource.getCrmId();
        if (toAutoAssign( oppSrcAgentEmail, opportunitySource.getRecordType() )) {
            LOGGER.info( "opportunity: " + crmOppId + " is reassigned to AUTOASSIGN agent" );
            agentLookupBusinessService.getMostEligibleAgent( opportunitySource, true, null, null );
        } else {
            processNewOppAssignment( opportunitySource, crmOppId );
        }
    }

    /**
     * Process new opp assignment.
     *
     * @param opportunitySource
     *            the opportunity source
     * @param crmOppId
     *            the crm opp id
     */
    private void processNewOppAssignment( final OpportunitySource opportunitySource, final String crmOppId ) {
        final Search agentSearch = searchService.searchByAgentEmail( opportunitySource.getAgentEmail() );
        if (null != agentSearch) { // agent is already registered
            final String agentId = agentSearch.getAgentId();
            LOGGER.info( "Will assign " + crmOppId + " to agent: " + agentSearch.getAgentEmail() );
            final com.owners.gravitas.domain.Contact contact = contactBuilder
                    .convertFrom( opportunitySource.getPrimaryContact() );
            final String contactId = agentContactService.saveContact( agentId, contact ).getName();
            LOGGER.info( "New contact saved on FB with ID: " + contactId );

            final Opportunity opportunity = opportunityBuilder.convertTo( opportunitySource );
            opportunity.addContact( contactId );

            final AgentEmailPermissionResponse response = ( AgentEmailPermissionResponse ) isCrmIdPermitted( agentId,
                    crmOppId );
            opportunity.setOclReferralStatus( response.isAllowed() ? "Eligible" : "Not Eligible" );
            final PostResponse postResponse = agentOpportunityService.saveOpportunity( agentId,
                    agentSearch.getAgentEmail(), opportunity,
                    opportunitySource.getPrimaryContact().getEmails().iterator().next() );
            final String opportunityId = postResponse.getName();
            LOGGER.info( "CRM ID " + crmOppId + " saved in firebase having id: " + opportunityId );

            agentRequestBusinessServiceImpl.createAgentRequest( opportunitySource, opportunityId, agentId );

            // create task on basis of opportunity stage
            handleStageChange( agentId, opportunityId, opportunitySource.getStage(),
                    opportunitySource.getPrimaryContact(), opportunitySource.getCrmId() );

            final String contactEmail = opportunitySource.getPrimaryContact().getEmails().get( 0 );
            saveSearch( agentSearch.getAgentId(), agentSearch.getAgentEmail(), opportunity.getCrmId(), opportunityId,
                    contactId, contactEmail );

            if (!coShoppingconfig.isEnableScheduleTourMeetings()) {
                // sending scripted call and notification if
                // coShoppingconfig.isEnableScheduleTourMeetings() is false
                final boolean isEligibleForScriptedCall = actionFlowBusinessService
                        .isEligibleForScriptedCall( opportunitySource.getRecordType(), agentSearch.getAgentEmail() );
                if (isEligibleForScriptedCall) {
                    LOGGER.info( "agent is eligible for scripted call functionality :" + agentSearch.getAgentEmail() );
                    actionFlowBusinessService.createActionGroup( opportunityId, agentId, opportunity, agentSearch );
                } else if (NEW.getStage().equals( opportunitySource.getStage() )) {
                    LOGGER.info( "Sending PUSH notification when new opportunity is processed" );
                    sendOpportunityPushNotifications( opportunityId, agentSearch );
                }
            }

            if (opportunitySource.getRecordType().equals( RecordType.BUYER.getType() )) {
                LOGGER.info( "This is active buyer opportunity :" + crmOppId );
                buyerFarmingBusinessService.updateFarmingStatus( crmOppId, BuyerFarmType.ACTIVE_BUYER, true );
                sendOppAssignmentBuyerNotification(opportunitySource);
            }

        } else {
            LOGGER.error( "couldnt process opportunity " + opportunitySource.getCrmId() + ", as agent "
                    + opportunitySource.getAgentEmail() + " is not registered!!" );
        }
    }

    /**
     * Copy contact.
     *
     * @param oldAgentId
     *            the old agent id
     * @param newAgentId
     *            the new agent id
     * @param existingContacts
     *            the existing contacts
     * @return the map
     */
    private Map< String, String > copyContact( final String oldAgentId, final String newAgentId,
            final Set< String > existingContacts ) {
        final Map< String, String > newContacts = new HashMap< String, String >();
        for ( final String contactId : existingContacts ) {
            final com.owners.gravitas.domain.Contact existingContact = agentContactService.getContactById( oldAgentId,
                    contactId );
            existingContact.setDeleted( FALSE );
            final PostResponse contactResponse = agentContactService.saveContact( newAgentId, existingContact );
            LOGGER.debug( "copied contact " + existingContact.getEmails().get( 0 ) );
            newContacts.put( contactResponse.getName(), existingContact.getEmails().get( 0 ) );
        }
        return newContacts;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentOpportunityBusinessService#
     * sendOpportunityPushNotifications(java.lang.String,
     * com.owners.gravitas.domain.Search)
     */
    @Override
    public void sendOpportunityPushNotifications( final String opportunityId, final Search agentSearch ) {
        agentNotificationBusinessService.sendPushNotification( agentSearch.getAgentId(), getNotificationRequest(
                opportunityId, getNewOpportunityCount( agentSearch.getAgentId() ), NEW_OPPORTUNITY ) );
    }

    /**
     * Gets the notification request.
     *
     * @param opportunityId
     *            the opportunity id
     * @param oppCount
     *            the opp count
     * @param eventType
     *            the event type
     * @return the notification request
     */
    private NotificationRequest getNotificationRequest( final String opportunityId, final int oppCount,
            final PushNotificationType eventType ) {
        final NotificationRequest notificationRequest = new NotificationRequest( oppCount );
        notificationRequest.setEventType( eventType );
        notificationRequest.setOpportunityId( opportunityId );
        return notificationRequest;
    }

    /**
     * Gets the new opportunity count.
     *
     * @param agentId
     *            the agent id
     * @return the new opportunity count
     */
    private int getNewOpportunityCount( final String agentId ) {
        return agentOpportunityService.getAgentNewOpportunitiesCount( agentId );
    }

    /**
     * Save search.
     *
     * @param agentId
     *            the agent id
     * @param agentEmail
     *            the agent email
     * @param crmOpportunityId
     *            the crm opportunity id
     * @param opportunityId
     *            the opportunity id
     * @param contactId
     *            the contact id
     * @param contactEmail
     *            the contact email
     * @return the search
     */
    private Search saveSearch( final String agentId, final String agentEmail, final String crmOpportunityId,
            final String opportunityId, final String contactId, final String contactEmail ) {
        final Search search = new Search();
        search.setAgentId( agentId );
        search.setAgentEmail( agentEmail );
        search.setContactId( contactId );
        search.setContactEmail( contactEmail );
        search.setOpportunityId( opportunityId );
        search.setCrmOpportunityId( crmOpportunityId );
        searchService.save( search );
        LOGGER.info( "saved search on FB: " + crmOpportunityId + " " + opportunityId );
        return search;
    }

    /**
     * Handle stage change.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param stage
     *            the stage
     * @param contact
     *            the contact
     * @param crmOpportunityId
     *            the crm opportunity id
     */
    private void handleStageChange( final String agentId, final String opportunityId, final String stage,
            final Contact contact, final String crmOpportunityId ) {
        final OpportunityChangeHandler opportunityChangeHandler = opportunityChangeHandlerFactory
                .getChangeHandler( Stage, stage );
        if (opportunityChangeHandler != null) {
            LOGGER.info( "Changed stage is " + stage );
            opportunityChangeHandler.handleChange( agentId, opportunityId, contact, stage );
            opportunityChangeHandler.handleBadgeCounterChange( agentId );
            opportunityChangeHandler.sendFeedbackEmail( agentId, crmOpportunityId, contact );
        } else {
            LOGGER.info( "Handler not found hence default handler called " + stage );
            stageLogService.saveOpportunityStagelog( opportunityId, stage );
        }
    }

    /**
     * Handle stage change.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param stage
     *            the stage
     * @param contact
     *            the contact
     * @param crmOpportunityId
     *            the crm opportunity id
     */
    @Transactional( propagation = Propagation.REQUIRED )
    private void handleFarmingGroupChangeChange( final String crmOpportunityId, final String farmingGroup ) {
        LOGGER.info( "Changing farming group to :{}, for Opportunity crm id :{} ", farmingGroup, crmOpportunityId );
        if (farmingGroup != null) {
            final String objectTypeName = Constants.OPPORTUNITY.toLowerCase();
            final ObjectType objectType = objectTypeService.findByName( objectTypeName );
            final com.owners.gravitas.domain.entity.Contact contact1 = contactServiceV1.findByCrmId( crmOpportunityId );
            contactServiceV1.addContactAttribute( contact1.getContactAttributes(), FARMING_GROUP.getKey(), farmingGroup,
                    objectType );
            contactServiceV1.save( contact1 );
        }
    }

    /**
     * Sets the default parameters.
     *
     * @param agentLeadRequest
     *            the new default parameters
     */
    private void setDefaultParameters( final LeadRequest agentLeadRequest ) {
        agentLeadRequest.setRequestType( OTHER.name() );
        agentLeadRequest.setLeadSourceUrl( defaultLeadSourceUrl );
        agentLeadRequest.setSource( defaultSource );
        if (BUYER.getType().equalsIgnoreCase( agentLeadRequest.getLeadType() )) {
            if (StringUtils.isBlank( agentLeadRequest.getPreApprovedForMortgage() )) {
                agentLeadRequest.setPreApprovedForMortgage( defaultPreApprovedForMortgage );
            }
            if (StringUtils.isBlank( agentLeadRequest.getBuyerReadinessTimeline() )) {
                agentLeadRequest.setBuyerReadinessTimeline( defaultBuyerReadinessTimeline );
            }
            if (StringUtils.isBlank( agentLeadRequest.getWorkingWithRealtor() )) {
                agentLeadRequest.setWorkingWithRealtor( defaultWorkingWithRealtor );
            }
            if (StringUtils.isBlank( agentLeadRequest.getPriceRange() )) {
                agentLeadRequest.setPriceRange( defaultPriceRange );
            }
            if (StringUtils.isBlank( agentLeadRequest.getInterestedZipcodes() )) {
                agentLeadRequest.setInterestedZipcodes( defaultInterestedZipcodes );
            }
        }
    }

    /**
     * Update agent opportunity.
     *
     * @param email
     *            the email
     * @param oppRecordTypeId
     *            the opp record type id
     * @param priceRange
     *            the price range
     * @param opportunityCrmId
     *            the opportunity crm id
     */
    private void updateAgentOpportunity( final String email, final String oppRecordTypeId, final String priceRange,
            final String opportunityCrmId ) {
        final Map< String, Object > patchRequestMap = new HashMap<>();
        patchRequestMap.put( "ownersAgent", email );
        patchRequestMap.put( "stage", CLAIMED.getStage() );
        patchRequestMap.put( "recordTypeId", oppRecordTypeId );
        patchRequestMap.put( "leadSourceUrl", defaultLeadSourceUrl );
        patchRequestMap.put( "priceRange", priceRange );
        final Map< String, Object > crmAgentDetails = getAgentDetails( email );
        final String state = isNull( crmAgentDetails.get( "State__c" ) );
        patchRequestMap.put( "propertyState", state );
        patchRequestMap.put( "propertyStateDel", state );
        patchRequestMap.put( "assignedAgent", isNull( crmAgentDetails.get( "Id" ) ) );
        opportunityBusinessService.updateCRMOpportunity( patchRequestMap, opportunityCrmId );
    }

    /**
     * Gets the agent details.
     *
     * @param email
     *            the email
     * @return the agent details
     */
    private Map< String, Object > getAgentDetails( final String email ) {
        final QueryParams params = new QueryParams();
        params.add( EMAIL, email );
        return crmQueryService.findOne( GET_CRM_AGENT_DETAILS_BY_EMAIL, params );
    }

    /**
     * Gets the agent commission.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @return the agent commission
     */
    @Override
    public BaseResponse getOpportunityCommission( final String agentId, final String opportunityId ) {
        LOGGER.info( "Getting opportunity commission for agentId : {}, opportunityId : {}", agentId, opportunityId );
        final AgentCommissionResponse agentCommissionResponse = new AgentCommissionResponse();
        final Opportunity opportunity = agentOpportunityService.getOpportunityById( agentId, opportunityId );
        if (null != opportunity && BUYER.getType().equalsIgnoreCase( opportunity.getOpportunityType() )) {
            String priceRange = opportunity.getPriceRange();
            // if the price range is not available in the firebase then make a
            // rest call to salesforce
            if (isBlank( priceRange )) {
                LOGGER.info( "Price Range is not present in firebase so querying salesforce for opportunity id : {} ",
                        opportunity.getCrmId() );
                final QueryParams params = new QueryParams();
                params.add( ID, opportunity.getCrmId() );
                final Map< String, Object > details = crmQueryService.findOne( GET_OPPORTUNITY_BY_ID, params );
                priceRange = convertObjectToString( details.get( "Price_Ranges__c" ) );
            }
            final com.owners.gravitas.domain.entity.Contact contact = contactServiceV1
                    .findByCrmId( opportunity.getCrmId() );
            LOGGER.info( "found contact for crm id " + contact );
            if (isNotBlank( priceRange ) && null != contact) {
                float commisionPctg = 0;
                String commission = EMPTY;
                final List< ContactAttribute > stateContactList = contact
                        .getContactAttributes().stream().filter( contactAttribute -> contactAttribute
                                .getObjectAttributeConfig().getAttributeName().equalsIgnoreCase( "state" ) )
                        .collect( Collectors.toList() );
                String state = null;
                if (!CollectionUtils.isEmpty( stateContactList )) {
                    state = stateContactList.get( 0 ).getValue();
                }
                // If Opportunity is not associated with state then consider
                // agent
                // home state
                if (isBlank( state )) {
                    state = getAgentHomeState( agentId );
                    LOGGER.info(
                            "Opportunity is not associated with state, so considering agent home state for agentId: "
                                    + agentId + " and state: " + state );
                }

                if (isNotBlank( priceRange ) && isNotBlank( state )) {
                    final int topPrice = agentOpportunityService.getTopPrice( priceRange );
                    final AgentCommission agentCommission = agentCommissionService
                            .getCommissionByStateAndTopPrice( state, topPrice );
                    if (agentCommission != null) {
                        if (defaultSource.equalsIgnoreCase( opportunity.getLeadSource() )) {
                            commisionPctg = agentCommission.getSelfGenPercentage();
                        } else {
                            commisionPctg = agentCommission.getNonSelfGenPercentage();
                        }
                        commission = String.valueOf( round( commisionPctg * topPrice / 100 ) );
                    }
                    agentCommissionResponse.setCommission( commission );
                }
            }
        }
        LOGGER.info( "Getting opportunity commission for agentId : {}, opportunityId : {}, response : {}", agentId,
                opportunityId, JsonUtil.toJson( agentCommissionResponse ) );
        return agentCommissionResponse;
    }

    /**
     * Checks if Email is permitted for the given email id
     * 
     * @param agentId
     * @param emailId
     * @return the base response
     */
    @Override
    public BaseResponse isCrmIdPermitted( final String agentId, final String crmId ) {
        LOGGER.info( "Checking OCL FLag for agentId: {}, crmId: {}", agentId, crmId );
        final AgentEmailPermissionResponse response = new AgentEmailPermissionResponse();
        response.setAllowed( false );
        final com.owners.gravitas.domain.entity.Contact contact = contactServiceV1.getContactByCrmId( crmId );

        if (contact == null || contact.getEmail() == null) {
            final AgentEmailPermissionResponse failureResponse = new AgentEmailPermissionResponse();
            failureResponse.setStatus( Status.FAILURE );
            failureResponse.setMessage( "Invalid CrmId : " + crmId + " or EmailId does not exist." );
            failureResponse.setAllowed( false );
            return failureResponse;
        }
        final String agentHomeState = getAgentHomeState( agentId );
        LOGGER.info( "Agent state: {}, agentId: {}", agentHomeState, agentId );
        if (agentOpportunityBusinessConfig.getOclEmailAllowedStates().isEmpty() || agentOpportunityBusinessConfig
                .getOclEmailAllowedStates().toLowerCase().contains( agentHomeState.toLowerCase() )) {
            if (agentOpportunityBusinessConfig.getOclLeadSourceNotAllowed().isEmpty() || !agentOpportunityBusinessConfig
                    .getOclLeadSourceNotAllowed().toLowerCase().contains( contact.getSource().toLowerCase() )) {
                if (!isEmailOptedOut( contact.getEmail() )) {
                    response.setAllowed( true );
                }
            }
        }
        LOGGER.info( "OCL FLag Response for agentId: {}, crmId: {}, allowed: {}", agentId, crmId,
                response.isAllowed() );
        return response;
    }

    /**
     * Checks is user has opted out for Marketing emails
     * 
     * @param contactEmail
     * @return
     */
    private boolean isEmailOptedOut( final String emailId ) {
        boolean isEmailOptOut = FALSE;
        LOGGER.info( "Checking for user preference with email " + emailId );
        final NotificationPreferenceResponse notificationPreferenceResponse = mailService
                .getNotificationPreferenceForUser( emailId );
        final List< Preference > preferences = notificationPreferenceResponse.getPreferences();
        if (!CollectionUtils.isEmpty( preferences )) {
            isEmailOptOut = preferences.stream().anyMatch(
                    p -> NotificationType.MARKETING.name().equalsIgnoreCase( p.getType() ) && !p.getValue() );
        }
        LOGGER.info( "email opt out is " + isEmailOptOut );
        return isEmailOptOut;
    }

    /**
     * Gets the agent home state.
     *
     * @param agentId
     *            the agent id
     * @return the agent home state
     */
    private String getAgentHomeState( final String agentId ) {
        AgentDetails agentDetails = null;
        final Agent agent = agentService.getAgentById( agentId );
        if (null != agent && null != agent.getInfo()) {
            agentDetails = agentDetailsService.findAgentByEmail( agent.getInfo().getEmail() );
            return agentDetails != null ? agentDetails.getState() : null;
        }
        return null;
    }

    /**
     * Check is CRM request.
     *
     * @param request
     *            the price request
     * @return the isCrmRequest
     */
    private boolean isCrmRequest( final Map< String, Object > request ) {
        boolean isCrmRequest = false;
        if (request.get( "httpRequest" ) != null) {
            final HttpServletRequest httpRequest = ( HttpServletRequest ) request.get( "httpRequest" );
            LOGGER.info( "Request Source : {} " + httpRequest.getHeader( "User-Agent" ).toString() );
            if (httpRequest.getHeader( "User-Agent" ).indexOf( "Mobile" ) == -1) {
                isCrmRequest = true;
            }
            request.remove( "httpRequest" );
        }
        return isCrmRequest;
    }

    /**
     * Sets the first contact dtm.
     *
     * @param isNew
     *            the is new
     * @param opportunity
     *            the opportunity
     */
    private void setFirstContactDtm( final Opportunity opportunity, final Map< String, Object > request,
            final long timestamp ) {
        final Stage stage = opportunity.popStage();
        final boolean isEligible = null == opportunity.getFirstContactDtm()
                && ( opportunity.getOpportunityType().equalsIgnoreCase( RecordType.BUYER.getType() )
                        || opportunity.getOpportunityType().equalsIgnoreCase( RecordType.SELLER.getType() ) )
                && !stage.getStage().equalsIgnoreCase( BuyerStage.NEW.getStage() )
                && !stage.getStage().equalsIgnoreCase( BuyerStage.CLAIMED.getStage() );

        if (isEligible) {
            request.put( "firstContactDtm", timestamp );
        }
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.AgentOpportunityBusinessService#
     * sendConfirmedTaskPushNotifications(java.lang.String, java.lang.String,
     * com.owners.gravitas.enums.PushNotificationType)
     */
    @Override
    public void sendPendingOpportunityPushNotifications( final String taskId, final String agentId,
            final PushNotificationType eventType ) {
        agentNotificationBusinessService.sendPushNotification( agentId,
                getNotificationRequestForConfirmedTask( taskId, eventType ) );
    }

    /**
     * Build Notification request object for confirmed task
     * 
     * @param taskId
     * @param eventType
     * @return
     */
    private NotificationRequest getNotificationRequestForConfirmedTask( final String taskId,
            final PushNotificationType eventType ) {
        final NotificationRequest request = new NotificationRequest();
        request.setEventType( eventType );
        request.setTaskId( taskId );
        return request;
    }
    
    /**
     * Send email to buyer
     * on opp assignment.
     *
     * @param opportunitySource
     *            the opportunity source
     * @param opportunityType
     *            the opportunity type
     */
    private void sendOppAssignmentBuyerNotification( final OpportunitySource opportunitySource ) {
    	 LOGGER.info( "sending sendOppAssignmentBuyerNotification  email to buyer" );
         mailService.send( oppAssignmentBuyerNotificationBuilder.convertTo( opportunitySource ) );
    	
    }
}
