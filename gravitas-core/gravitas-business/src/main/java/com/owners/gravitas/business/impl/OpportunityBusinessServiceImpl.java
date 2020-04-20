/*
 *
 */
package com.owners.gravitas.business.impl;

import static com.owners.gravitas.config.HappyAgentsConfig.REASON_REFERRED_TO_REF_EX;
import static com.owners.gravitas.constants.CRMQuery.GET_ACCOUNT_BY_SELLER_ID;
import static com.owners.gravitas.constants.CRMQuery.GET_AGENT_ID_BY_EMAIL;
import static com.owners.gravitas.constants.CRMQuery.GET_CONTACT_BY_EMAIL_ID;
import static com.owners.gravitas.constants.CRMQuery.GET_OPPORTUNITY_BY_ID;
import static com.owners.gravitas.constants.CRMQuery.GET_RECORD_TYPE_BY_ID;
import static com.owners.gravitas.constants.Constants.BLANK_SPACE;
import static com.owners.gravitas.constants.Constants.EMAIL;
import static com.owners.gravitas.constants.Constants.ID;
import static com.owners.gravitas.constants.Constants.NAME;
import static com.owners.gravitas.constants.Constants.PROPERTY_ORDER_TYPE;
import static com.owners.gravitas.constants.Constants.QUERY_PARAM_ID;
import static com.owners.gravitas.constants.Constants.UNDER_SCORE;
import static com.owners.gravitas.constants.NotificationParameters.USER;
import static com.owners.gravitas.enums.AgentTaskStatus.INCOMPLETE;
import static com.owners.gravitas.enums.AgentTaskStatus.PENDING;
import static com.owners.gravitas.enums.AssignmentStatus.displayed;
import static com.owners.gravitas.enums.AssignmentStatus.displayed_and_assigned;
import static com.owners.gravitas.enums.AssignmentStatus.displayed_for_referral;
import static com.owners.gravitas.enums.AssignmentStatus.referred;
import static com.owners.gravitas.enums.BuyerStage.FORWARDED_TO_REF_EX;
import static com.owners.gravitas.enums.CRMObject.ACCOUNT;
import static com.owners.gravitas.enums.CRMObject.CONTACT;
import static com.owners.gravitas.enums.CRMObject.OPPORTUNITY;
import static com.owners.gravitas.enums.OpportunityChangeType.Stage;
import static com.owners.gravitas.enums.RecordType.HUBZU;
import static com.owners.gravitas.enums.RecordType.OWNERS;
import static com.owners.gravitas.enums.RecordType.SELLER;
import static com.owners.gravitas.util.StringUtils.convertObjectToString;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import com.hubzu.notification.dto.client.sms.SmsNotification;
import com.owners.gravitas.amqp.OpportunityContact;
import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.ActionFlowBusinessService;
import com.owners.gravitas.business.AgentNotificationBusinessService;
import com.owners.gravitas.business.AgentOpportunityBusinessService;
import com.owners.gravitas.business.AgentTaskBusinessService;
import com.owners.gravitas.business.OpportunityBusinessService;
import com.owners.gravitas.business.builder.AgentTaskRequestBuilder;
import com.owners.gravitas.business.builder.CoShoppingLeadBuilder;
import com.owners.gravitas.business.builder.CoShoppingLeadUpdateModelBuilder;
import com.owners.gravitas.business.builder.ContactBuilder;
import com.owners.gravitas.business.builder.OpportunityContactBuilder;
import com.owners.gravitas.business.builder.OpportunitySourceBuilder;
import com.owners.gravitas.business.builder.domain.BuyerRequestBuilder;
import com.owners.gravitas.business.builder.request.BuyerOpportunityRequestBuilder;
import com.owners.gravitas.business.builder.request.CRMAccountRequestBuilder;
import com.owners.gravitas.business.builder.request.CRMContactRequestBuilder;
import com.owners.gravitas.business.builder.request.ReferralExchangeOpportunityRequestBuilder;
import com.owners.gravitas.business.builder.request.ReferralExchangeRequestEmailBuilder;
import com.owners.gravitas.business.builder.request.SellerOpportunityRequestBuilder;
import com.owners.gravitas.business.builder.request.SlackRequestBuilder;
import com.owners.gravitas.business.task.AgentLookupTask;
import com.owners.gravitas.business.tour.TourConfirmationBusiness;
import com.owners.gravitas.config.AgentOpportunityBusinessConfig;
import com.owners.gravitas.config.CoShoppingConfig;
import com.owners.gravitas.config.LeadOpportunityBusinessConfig;
import com.owners.gravitas.constants.CRMConstants;
import com.owners.gravitas.constants.CRMQuery;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.dao.AgentOpportunityDao;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.domain.Request;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.domain.Task;
import com.owners.gravitas.domain.entity.ActionLog;
import com.owners.gravitas.domain.entity.AgentAssignmentLog;
import com.owners.gravitas.domain.entity.Discount;
import com.owners.gravitas.domain.entity.NotificationLog;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.OpportunityDetails;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.dto.crm.request.CRMAccountRequest;
import com.owners.gravitas.dto.crm.request.CRMContactRequest;
import com.owners.gravitas.dto.crm.request.CRMOpportunityContactRoleRequest;
import com.owners.gravitas.dto.crm.request.CRMOpportunityRequest;
import com.owners.gravitas.dto.crm.response.CRMOpportunityResponse;
import com.owners.gravitas.dto.crm.response.CRMResponse;
import com.owners.gravitas.dto.crm.response.OpportunityResponse;
import com.owners.gravitas.dto.request.AgentMeetingRequest;
import com.owners.gravitas.dto.request.AgentTaskRequest;
import com.owners.gravitas.dto.request.CoShoppingLeadRequest;
import com.owners.gravitas.dto.request.CoShoppingLeadUpdateModel;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.dto.request.NotificationRequest;
import com.owners.gravitas.dto.request.OpportunityRequest;
import com.owners.gravitas.dto.request.ReferralExchangeRequest;
import com.owners.gravitas.dto.request.ScheduleTourLeadRequest;
import com.owners.gravitas.dto.request.ScheduleTourMeetingRequest;
import com.owners.gravitas.dto.response.AgentResponse;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.BuyerDeviceDetail;
import com.owners.gravitas.dto.response.BuyerDeviceResponse;
import com.owners.gravitas.dto.response.NotificationResponse;
import com.owners.gravitas.dto.response.OpportunityDiscountResponse;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.dto.response.PropertyDetailsResponse;
import com.owners.gravitas.enums.BuyerStage;
import com.owners.gravitas.enums.ErrorCode;
import com.owners.gravitas.enums.PushNotificationType;
import com.owners.gravitas.enums.RecordType;
import com.owners.gravitas.enums.TaskType;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.exception.InvalidArgumentException;
import com.owners.gravitas.exception.ResultNotFoundException;
import com.owners.gravitas.handler.OpportunityChangeHandler;
import com.owners.gravitas.handler.OpportunityChangeHandlerFactory;
import com.owners.gravitas.repository.DiscountRepository;
import com.owners.gravitas.service.AccountService;
import com.owners.gravitas.service.ActionLogService;
import com.owners.gravitas.service.AgentAssignmentLogService;
import com.owners.gravitas.service.AgentContactService;
import com.owners.gravitas.service.AgentOpportunityService;
import com.owners.gravitas.service.AgentRequestService;
import com.owners.gravitas.service.AgentTaskService;
import com.owners.gravitas.service.BuyerService;
import com.owners.gravitas.service.CRMQueryService;
import com.owners.gravitas.service.CoShoppingService;
import com.owners.gravitas.service.ContactEntityService;
import com.owners.gravitas.service.ContactService;
import com.owners.gravitas.service.MailService;
import com.owners.gravitas.service.NotificationService;
import com.owners.gravitas.service.OpportunityContactRoleService;
import com.owners.gravitas.service.OpportunityService;
import com.owners.gravitas.service.PropertyService;
import com.owners.gravitas.service.RecordTypeService;
import com.owners.gravitas.service.ReferralExchangeService;
import com.owners.gravitas.service.SearchService;
import com.owners.gravitas.service.SlackService;
import com.owners.gravitas.service.SmsService;
import com.owners.gravitas.util.DateUtil;
import com.owners.gravitas.util.GravitasWebUtil;
import com.owners.gravitas.util.JsonUtil;
import com.owners.gravitas.util.PropertiesUtil;
import com.zuner.coshopping.model.common.Resource;
import com.zuner.coshopping.model.enums.LeadRequestType;
import com.zuner.coshopping.model.lead.LeadModel;

/**
 * The Class OpportunityBusinessServiceImpl.
 *
 * @author vishwanathm
 */
@Service( "opportunityBusinessService" )
public class OpportunityBusinessServiceImpl implements OpportunityBusinessService {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( OpportunityBusinessServiceImpl.class );

    /** The Constant REFERRED_TO_REF_EXCHG. */
    private static final String REFERRED_TO_REF_EXCHG = "Referred to referral exchange";

    /** The Constant createdBy as Inside Sales */
    private static final String createdBy = "Inside Sales";

    /** The crm service. */
    @Autowired
    private CRMQueryService crmQueryService;

    /** The opportunity service. */
    @Autowired
    private OpportunityService opportunityService;

    /** The account service. */
    @Autowired
    private AccountService accountService;

    /** The contact service. */
    @Autowired
    private ContactService contactService;

    /** The agent opportunity service. */
    @Autowired
    private AgentOpportunityService agentOpportunityService;

    /** The opportunity contact role service. */
    @Autowired
    private OpportunityContactRoleService opportunityContactRoleService;

    /** The contact request builder. */
    @Autowired
    private CRMContactRequestBuilder crmContactRequestBuilder;

    /** The crm account request builder. */
    @Autowired
    private CRMAccountRequestBuilder crmAccountRequestBuilder;

    /** The crm opportunity request builder. */
    @Autowired
    private SellerOpportunityRequestBuilder sellerOpportunityRequestBuilder;

    /** The slack publish request builder. */
    @Autowired
    private SlackRequestBuilder slackRequestBuilder;

    /** The slack service. */
    @Autowired
    private SlackService slackService;

    /** The buyer opportunity request builder. */
    @Autowired
    private BuyerOpportunityRequestBuilder buyerOpportunityRequestBuilder;

    /** The opportunity source builder. */
    @Autowired
    private OpportunitySourceBuilder opportunitySourceBuilder;

    /** The opportunity contact builder. */
    @Autowired
    private OpportunityContactBuilder opportunityContactBuilder;

    /** The buyer request builder. */
    @Autowired
    private BuyerRequestBuilder buyerRequestBuilder;

    /** The request service. */
    @Autowired
    private AgentRequestService requestService;

    /** The agent task business service. */
    @Autowired
    @Lazy
    private AgentTaskBusinessService agentTaskBusinessService;

    /** The agent notification business service. */
    @Autowired
    private AgentNotificationBusinessService agentNotificationBusinessService;

    /** The gravitas web util. */
    @Autowired
    private GravitasWebUtil gravitasWebUtil;

    /** lead creation url. */
    @Value( value = "${slack.channel.url.paidlistings}" )
    private String slackPaidServiceChannelUrl;

    /** lead creation url. */
    @Value( value = "${slack.channel.url.freelistings}" )
    private String slackFreeServiceChannelUrl;

    /** The builder source url prop. */
    @Value( "${ocl.builder.source.url}" )
    private String oclBuilderSourceUrlProp;

    /** The record type service. */
    @Autowired
    private RecordTypeService recordTypeService;

    /** The agent opportunity business service. */
    @Autowired
    @Lazy
    private AgentOpportunityBusinessService agentOpportunityBusinessService;

    /** The agent assignment log service. */
    @Autowired
    private AgentAssignmentLogService agentAssignmentLogService;

    /** The agent lookup task. */
    @Autowired
    private AgentLookupTask agentLookupTask;

    /** The mail service. */
    @Autowired
    private MailService mailService;

    /** The referral exchange request email builder. */
    @Autowired
    private ReferralExchangeRequestEmailBuilder referralExchangeRequestEmailBuilder;

    /** The referral exchange opportunity request builder. */
    @Autowired
    private ReferralExchangeOpportunityRequestBuilder referralExchangeOpportunityRequestBuilder;

    /** The referral exchange service. */
    @Autowired
    private ReferralExchangeService referralExchangeService;

    /** The agent opportunity business config. */
    @Autowired
    private AgentOpportunityBusinessConfig agentOpportunityBusinessConfig;

    /** The contact entity service. */
    @Autowired
    private ContactEntityService contactEntityService;

    /** The property service. */
    @Autowired
    private PropertyService propertyService;

    /** The agent task service. */
    @Autowired
    private AgentTaskService agentTaskService;

    /** The ref ex agent email. */
    @Value( "${ref.ex.agent.email}" )
    private String refExAgentEmail;

    /** The ref ex agent email. */
    @Value( "${out.of.coverage}" )
    private String outOfCoverage;

    /** The ref ex agent email. */
    @Value( "${no.agent.available}" )
    private String noAgentAvailable;

    /** The Constant obj. */
    private final static Map< String, String > crmOppFields = new HashMap<>();

    /** The search service. */
    @Autowired
    private SearchService searchService;

    @Autowired
    private AgentTaskRequestBuilder agentTaskRequestBuilder;

    @Autowired
    private CoShoppingConfig coShoppingConfig;

    @Autowired
    private CoShoppingService coShoppingService;

    /** The co shopping lead request builder. */
    @Autowired
    private CoShoppingLeadBuilder coShoppingLeadBuilder;

    /** The co shopping lead update model builder. */
    @Autowired
    private CoShoppingLeadUpdateModelBuilder coShoppingLeadUpdateModelBuilder;

    @Autowired
    private ActionLogService actionLogService;

    /** The action flow business service. */
    @Autowired
    private ActionFlowBusinessService actionFlowBusinessService;

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private LeadOpportunityBusinessConfig leadOpportunityBusinessConfig;

    @Autowired
    private TourConfirmationBusiness tourConfirmationBusiness;
    
    /** The discount entity service. */
    @Autowired
    private DiscountRepository discountRepository;

    /** The opportunity dao. */
    @Autowired
    private AgentOpportunityDao opportunityDao;
    
    /** The contact builder. */
    @Autowired
    private ContactBuilder contactBuilder;
    
    /** The agent contact service. */
    @Autowired
    private AgentContactService agentContactService;
    
    /** The OpportunityChangeHandlerFactory *. */
    @Autowired
    @Lazy
    private OpportunityChangeHandlerFactory opportunityChangeHandlerFactory;

    /**
     * Initialize opportunity fields map.
     */
    @PostConstruct
    private void initializeOpportunityFieldsMap() {
        crmOppFields.put( "leadSource", "LeadSource" );
        crmOppFields.put( "workingWithExternalAgent", "Working_with_External_Agent__c" );
        crmOppFields.put( "buyerReadinessTimeline", "Buyer_Readiness_Timeline__c" );
        crmOppFields.put( "budget", "Price_Range__c" );
        crmOppFields.put( "areaOfInterest", "Interested_Zip_Codes__c" );
        crmOppFields.put( "financing", "Financing__c" );
        crmOppFields.put( "medianPrice", "Median_Price__c" );
        crmOppFields.put( "stage", "StageName" );
        crmOppFields.put( "reasonLost", "Reason_Lost__c" );
        crmOppFields.put( "buySideCommission", "Commission_Post_Closing__c" );
        crmOppFields.put( "salesPrice", "Sales_Price_Post_Closing__c" );
        crmOppFields.put( "expectedContractDate", "CloseDate" );
        crmOppFields.put( "preApprovedAmount", "Pre_approved_Amount__c" );
        crmOppFields.put( "opportunityType", "Opportunity_Type__c" );
        crmOppFields.put( "commissionBasePrice", "Commission_Base_Price__c" );
        crmOppFields.put( "titleCompany", "Title_Company_Non_PTS__c" );
        crmOppFields.put( "pendingDate", "Pending_Date__c" );
        crmOppFields.put( "actualClosingDate", "Acutal_Closing_Date__c" );
        crmOppFields.put( "titleSelectionReason", "Title_Selection_Reason__c" );
        crmOppFields.put( "priceRange", "Price_Ranges__c" );
        crmOppFields.put( "referralSuccessDetails", "Referred_Successfully__c" );
        crmOppFields.put( "referralFailureDetails", "Referral_Failure_Details__c" );
        crmOppFields.put( "dateReferred", "Date_Referred__c" );
        crmOppFields.put( "dateAgreementSentOutReferred", "Date_Agreement_Sent_Out__c" );
        crmOppFields.put( "propertyAddress", "Property_Address__c" );
        crmOppFields.put( "listingSideCommission", "Listing_Side_Commission__c" );
        crmOppFields.put( "listPrice", "List_Price__c" );
        crmOppFields.put( "listDate", "Listing_Date__c" );
        crmOppFields.put( "expirationDate", "Listing_Expiration_Date__c" );
        crmOppFields.put( "offerAmount", "Offer_Amount__c" );
        crmOppFields.put( "reasonLostDetails", "Lost_Reason__c" );
        crmOppFields.put( "preApprovedForMortgage", "Pre_Approved_for_Mortgage__c" );
        crmOppFields.put( "ownersAgent", "Owners_Agent__c" );
        crmOppFields.put( "recordTypeId", "RecordTypeId" );
        crmOppFields.put( "leadSourceUrl", "Lead_Source_URL__c" );
        crmOppFields.put( "propertyState", "Property_State__c" );
        crmOppFields.put( "propertyStateDel", "Property_States_del__c" );
        crmOppFields.put( "assignedAgent", "Agent__c" );
        crmOppFields.put( "propertyZip", "Property_Zip_del__c" );
    }

    /**
     * Creates the opportunity.
     *
     * @param opportunityRequest
     *            the opportunity request
     * @param oppRecordType
     *            the opp record type
     * @return the opportunity response
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public OpportunityResponse createSellerOpportunity( final OpportunityRequest opportunityRequest,
            final RecordType oppRecordType ) {
        opportunityService.isOpportunityExists( opportunityRequest.getPropertyOrder().getProperty().getListingId() );
        final String accountId = createAccountIfNotExist( opportunityRequest, HUBZU );
        final String contactId = createContactIfNotExists( opportunityRequest, accountId, OWNERS );
        final OpportunityResponse opportunityResponse = createOpportunity( opportunityRequest, accountId, contactId,
                oppRecordType );
        createOpportunityContactRole( contactId, opportunityResponse.getId(), HUBZU );
        slackService.publishToSlack( slackRequestBuilder.convertTo( opportunityRequest ),
                getSlackChannelUrl( opportunityRequest.getPropertyOrder().getOrderType() ) );
        return opportunityResponse;
    }

    /**
     * Update opportunity.
     *
     * @param leadRequest
     *            the lead request
     * @param opportunityId
     *            the opportunity id
     * @return the opportunity response
     */
    @Override
    public OpportunityResponse updateOpportunity( final LeadRequest leadRequest, final String opportunityId ) {
        final CRMOpportunityResponse opportunityResponse = opportunityService.getOpportunity( opportunityId );
        final CRMOpportunityRequest crmOpportunityRequest = buyerOpportunityRequestBuilder.convertTo( leadRequest,
                convertToCrmOpportunityRequest( opportunityResponse ) );
        opportunityService.updateOpportunity( crmOpportunityRequest, opportunityId, FALSE );
        final OpportunityResponse response = new OpportunityResponse();
        response.setId( opportunityId );
        LOGGER.info( "opportunity updated " + opportunityResponse.getId() + " Time is : " + LocalDateTime.now() );
        return response;
    }

    /**
     * Find contact by id.
     *
     * @param id
     *            the id
     * @param findBy
     *            the find by
     * @return the primary contact
     */
    @Override
    public OpportunityContact findContactById( final String id, final String findBy ) {
        return opportunityContactBuilder.convertTo( contactService.findContactById( id, findBy ) );
    }

    /**
     * Gets the opportunity details.
     *
     * @param opportunityId
     *            the opportunity id
     * @return the opportunity details
     */
    @Override
    public OpportunitySource getOpportunity( final String opportunityId ) {
        final QueryParams params = new QueryParams();
        params.add( ID, opportunityId );
        final Map< String, Object > details = crmQueryService.findOne( GET_OPPORTUNITY_BY_ID, params );

        final OpportunitySource opportunity = new OpportunitySource();
        opportunitySourceBuilder.convertTo( details, opportunity );
        opportunity.setCrmId( opportunityId );
        opportunity.setPrimaryContact( findContactById( opportunityId, "opportunityId" ).getPrimaryContact() );
        return opportunity;
    }

    /**
     * Gets the agent associated opportunities from CRM by agent email.
     *
     * @param agentEmail
     *            the agent email
     * @return the opportunities
     */
    @Override
    public CRMResponse getAgentOpportunities( final String agentEmail ) {
        final QueryParams params = new QueryParams();
        params.add( EMAIL, agentEmail );
        return crmQueryService.findAll( CRMQuery.GET_OPPORTUNITIES_BY_AGENT_EMAIL, params );
    }

    /**
     * Process buyer request.
     *
     * @param request
     *            the request
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     */
    @Override
    public void processBuyerRequest( final LeadRequest request, final String agentId, final String opportunityId ) {
        final Opportunity opportunity = agentOpportunityService.getOpportunityById( agentId, opportunityId );

        if (!opportunity.getDeleted()) {
            // For all buyer requests send push notification immediately
            NotificationRequest notificationRequest = new NotificationRequest( request.getFirstName(),
                    request.getLastName(), PushNotificationType.NEW_REQUEST, null, opportunityId );

            final Request buyerRequest = buyerRequestBuilder.convertTo( request );
            // buyerRequest will be null when buyer requestType is none of
            // MAKE_OFFER, SCHEDULE_TOUR or REQUEST_INFORMATION
            if (buyerRequest != null) {
                if (coShoppingConfig.isEnableBuyerOpportunityScheduleTour()
                        && request.getRequestType().equalsIgnoreCase( LeadRequestType.SCHEDULE_TOUR.toString() )) {
                    notificationRequest = new NotificationRequest( request.getFirstName(), request.getLastName(),
                            PushNotificationType.NEW_TASK, null, opportunityId );
                    // create task and attach it to opportunity
                    buyerRequest.setStatus( PENDING.name() );
                    final PostResponse taskResponse = agentTaskBusinessService.createBuyerTask( request, agentId,
                            opportunityId, null, buyerRequest );
                    LOGGER.info( "Task ID :{} for opportunityId :{}", taskResponse.getName(), opportunityId );
                    notificationRequest.setTaskId( taskResponse.getName() );
                } else {
                    buyerRequest.setOpportunityId( opportunityId );
                    final PostResponse buyerRequestResponse = requestService.saveRequest( agentId, buyerRequest );
                    // create task and attach it to opportunity
                    buyerRequest.setStatus( INCOMPLETE.name() );
                    final PostResponse taskResponse = agentTaskBusinessService.createBuyerTask( request, agentId,
                            opportunityId, buyerRequestResponse.getName(), buyerRequest );

                    // updating request after task creation.
                    final Map< String, Object > requestParams = new HashMap<>();
                    requestParams.put( "taskId", taskResponse.getName() );
                    requestService.patchAgentRequest( buyerRequestResponse.getName(), agentId, requestParams );
                }

                notificationRequest.setRequestType( request.getRequestType() );
                agentNotificationBusinessService.sendPushNotification( agentId, notificationRequest );
            }
        }
    }

    /**
     * Updates opportunity by loan phase.
     *
     * @param crmOpportunityId
     *            the crm opportunity id
     * @param loanPhase
     *            the loan phase
     * @param loanNumber
     *            the loan number
     * @param oppRecordTypeId
     *            the opp record type id
     */
    @Override
    public void updateOclOpportunity( final String crmOpportunityId, final String loanPhase, final int loanNumber,
            final String oppRecordTypeId ) {
        final Map< String, Object > patchRequest = new HashMap<>();
        final String loanPhaseKey = PropertiesUtil.getLoanPhasePropertyPrefix( loanPhase );
        patchRequest.put( "Loan_Number__c", loanNumber );
        patchRequest.put( "Loan_Phase__c", loanPhase );
        patchRequest.put( "StageName", PropertiesUtil.getProperty(
                Constants.OCL_LOAN_PHASE_PRE_FIX + loanPhaseKey + Constants.OCL_LOAN_PHASE_STAGE_SUFFIX ) );
        patchRequest.put( "Loan_Phase_Description__c", PropertiesUtil.getProperty(
                Constants.OCL_LOAN_PHASE_PRE_FIX + loanPhaseKey + Constants.OCL_LOAN_PHASE_DESCRIPTION_SUFFIX ) );
        patchRequest.put( "RecordTypeId", oppRecordTypeId );
        patchRequest.put( "Lead_Source_URL__c", oclBuilderSourceUrlProp );
        opportunityService.patchOpportunity( patchRequest, crmOpportunityId );
    }

    /**
     * Gets the opportunity create details.
     *
     * @param opportunityId
     *            the opportunity id
     * @return the opportunity create details
     */
    @Override
    public OpportunitySource getOpportunityCreateDetails( final String opportunityId ) {
        final QueryParams params = new QueryParams();
        params.add( ID, opportunityId );
        final Map< String, Object > details = crmQueryService.findOne( GET_RECORD_TYPE_BY_ID, params );

        final OpportunitySource opportunity = new OpportunitySource();
        opportunity.setOpportunityType( getObjectName( details.get( "RecordType" ) ) );
        opportunity.setOpportunityOwnerName( getObjectName( details.get( "Owner" ) ) );
        opportunity.setOpportunityOwnerEmail( getOpportunityOwnerEmail( details.get( "Owner" ) ) );
        opportunity.setCrmId( opportunityId );

        // Get the contact
        final Contact primaryContact = getContactDetails( opportunityId );
        opportunity.setPrimaryContact( primaryContact );
        return opportunity;
    }

    /**
     * Gets the opportunity by crm id.
     *
     * @param crmId
     *            the crm id
     * @return the opportunity by crm id
     */
    @Override
    public OpportunityDetails getOpportunityByCRMId( final String crmId ) {
        final CRMOpportunityResponse crmOpportunityResponse = opportunityService.getOpportunity( crmId );
        final OpportunityDetails response = new OpportunityDetails();
        response.setName( crmOpportunityResponse.getName() );
        response.setType( crmOpportunityResponse.getRecordTypeName() );
        response.setZipcode( crmOpportunityResponse.getPropertyZip() );
        response.setEmail( contactService.findEmailByCrmId( crmId ) );
        response.setState(
                response.getType().equalsIgnoreCase( SELLER.getType() ) ? crmOpportunityResponse.getSellerState()
                        : crmOpportunityResponse.getState() );
        return response;
    }

    /**
     * Gets the contact details.
     *
     * @param opportunityId
     *            the opportunity id
     * @return the Contact details
     */
    @Override
    public Contact getContactDetails( final String opportunityId ) {
        // Get the contact
        return findContactById( opportunityId, "opportunityId" ).getPrimaryContact();
    }

    /**
     * Assign opportunity agent.
     *
     * @param crmId
     *            the opportunity id
     * @param request
     *            the request
     * @param currentUser
     *            the current user
     */
    @Override
    @Transactional
    public void updateOpportunity( final String crmId, final Map< String, Object > request ) {
        LOGGER.info( "update CRM opportunity " + crmId );
        if (MapUtils.isNotEmpty( request )) {

            // 1. update on SF
            final String agentEmail = convertObjectToString( request.get( "agentEmail" ) );
            final Map< String, Object > patchRequest = new HashMap<>();
            patchRequest.put( "assignedAgent", getAgentIdByEmail( agentEmail ) );
            patchRequest.put( "propertyZip", request.get( "zipcode" ) );
            updateCRMOpportunity( patchRequest, crmId );

            // 2. audit
            LOGGER.info( "Create audit log for opportunity agent assignment." );
            agentLookupTask.updateAgentAssignmentAudit( crmId, agentEmail, gravitasWebUtil.getAppUserEmail() );

            // 3. update SF & DB
            final OpportunitySource opportunitySource = getOpportunity( crmId );
            agentOpportunityBusinessService.handleOpportunityChange( opportunitySource );

            updateAgentAssignmentLog( crmId, displayed.name() );
        }
    }

    /**
     * Update agent assignment log.
     *
     * @param crmId
     *            the crm id
     * @param status
     *            the status
     */
    private void updateAgentAssignmentLog( final String crmId, final String status ) {
        final Pageable page = new PageRequest( 0, 1 );
        final AgentAssignmentLog agentAssignmentLog = agentAssignmentLogService.findByOpportunityIdAndStatusLike( crmId,
                status, page );
        if (agentAssignmentLog != null) {
            String newStatus = displayed_and_assigned.name();
            if (displayed.name().equalsIgnoreCase( status )) {
                newStatus = getNewStatus( agentAssignmentLog.getAssignmentStatus(), displayed_and_assigned.name() );
            } else if (displayed_for_referral.name().equalsIgnoreCase( status )) {
                newStatus = referred.name();
                agentAssignmentLog.setReason( REASON_REFERRED_TO_REF_EX );
            }
            LOGGER.info( "Updating AgentAssignmentLog for crmId {} updated in DB with status {}", crmId, newStatus );
            agentAssignmentLog.setAssignmentStatus( newStatus );
            agentAssignmentLogService.save( agentAssignmentLog );
        }
    }

    /**
     * Gets the new status.
     *
     * @param assignmentStatus
     *            the assignment status
     * @return the new status
     */
    private String getNewStatus( final String oldStatus, final String suffix ) {
        String newStatus = suffix;
        final int lastIndexOfUnderscore = oldStatus.lastIndexOf( UNDER_SCORE );
        if (lastIndexOfUnderscore != -1) {
            final String prefix = oldStatus.substring( 0, lastIndexOfUnderscore );
            newStatus = prefix.toLowerCase() + UNDER_SCORE + suffix;
        }
        return newStatus;
    }

    /**
     * Convert to crm opportunity request.
     *
     * @param crmOpportunityResponse
     *            the crm opportunity response
     * @return the CRM opportunity request
     */
    private CRMOpportunityRequest convertToCrmOpportunityRequest(
            final CRMOpportunityResponse crmOpportunityResponse ) {
        final CRMOpportunityRequest crmOpportunityRequest = new CRMOpportunityRequest();
        BeanUtils.copyProperties( crmOpportunityResponse, crmOpportunityRequest );
        return crmOpportunityRequest;
    }

    /**
     * Creates the opportunity contact role.
     *
     * @param contactId
     *            the contact id
     * @param opportunityId
     *            the opportunity id
     * @param recordType
     *            the record type
     */
    private void createOpportunityContactRole( final String contactId, final String opportunityId,
            final RecordType recordType ) {
        final CRMOpportunityContactRoleRequest crmOpportunityContactRole = new CRMOpportunityContactRoleRequest();
        crmOpportunityContactRole.setContactId( contactId );
        crmOpportunityContactRole.setOpportunityId( opportunityId );
        crmOpportunityContactRole.setPrimary( true );
        crmOpportunityContactRole.setRole( recordType.getType() );
        opportunityContactRoleService.createOpportunityContactRole( crmOpportunityContactRole );
    }

    /**
     * Creates the opportunity.
     *
     * @param opportunityRequest
     *            the opportunity request
     * @param accountId
     *            the account id
     * @param contactId
     *            the contact id
     * @param oppRecordType
     *            the opp record type
     * @return the opportunity response
     */
    private OpportunityResponse createOpportunity( final OpportunityRequest opportunityRequest, final String accountId,
            final String contactId, final RecordType oppRecordType ) {
        final CRMOpportunityRequest crmOpportunityRequest = sellerOpportunityRequestBuilder
                .convertTo( opportunityRequest );
        final String recordTypeId = recordTypeService.getRecordTypeIdByName( oppRecordType.getType(),
                OPPORTUNITY.getName() );
        crmOpportunityRequest.setAccountId( accountId );
        crmOpportunityRequest.setRecordType( recordTypeId );
        crmOpportunityRequest.setContactId( contactId );
        return opportunityService.createOpportunity( crmOpportunityRequest );
    }

    /**
     * Creates the contact.
     *
     * @param opportunityRequest
     *            the opportunity request
     * @param accountId
     *            the account id
     * @param contactRecordType
     *            the contact record type
     * @return the string
     */
    private String createContactIfNotExists( final OpportunityRequest opportunityRequest, final String accountId,
            final RecordType contactRecordType ) {
        String contactId = getContact( opportunityRequest.getSeller().getEmail() );
        if (StringUtils.isBlank( contactId )) {
            final String recordTypeId = recordTypeService.getRecordTypeIdByName( contactRecordType.getType(),
                    CONTACT.getName() );
            final CRMContactRequest contact = crmContactRequestBuilder.convertTo( opportunityRequest );
            contact.setAccountId( accountId );
            contact.setRecordType( recordTypeId );
            contactId = contactService.createContact( contact ).getId();
        }
        return contactId;
    }

    /**
     * Creates the account if not exist.
     *
     * @param opportunityRequest
     *            the opportunity request
     * @param accountRecordType
     *            the account record type
     * @return the string
     */
    private String createAccountIfNotExist( final OpportunityRequest opportunityRequest,
            final RecordType accountRecordType ) {
        final String accountName = StringUtils.isNotBlank( opportunityRequest.getSeller().getFirstName() )
                ? opportunityRequest.getSeller().getFirstName() + BLANK_SPACE
                        + opportunityRequest.getSeller().getLastName() + " - "
                        + opportunityRequest.getSeller().getEmail()
                : opportunityRequest.getSeller().getLastName() + " - " + opportunityRequest.getSeller().getEmail();
        String accountId = getAccountId( accountName );
        if (StringUtils.isBlank( accountId )) {
            LOGGER.debug( "Creating account with id " + accountName );
            final CRMAccountRequest crmAccountRequest = crmAccountRequestBuilder.convertTo( opportunityRequest );
            final String recordTypeId = recordTypeService.getRecordTypeIdByName( accountRecordType.getType(),
                    ACCOUNT.getName() );
            crmAccountRequest.setRecordTypeId( recordTypeId );
            accountId = accountService.createAccount( crmAccountRequest ).getId();
        }
        return accountId;
    }

    /**
     * Gets the account id.
     *
     * @param accountName
     *            the account name
     * @return the account id
     */
    private String getAccountId( final String accountName ) {
        String accountId = null;
        final QueryParams params = new QueryParams();
        params.add( NAME, accountName );
        try {
            accountId = convertObjectToString(
                    crmQueryService.findOne( GET_ACCOUNT_BY_SELLER_ID, params ).get( QUERY_PARAM_ID ) );
        } catch ( final ResultNotFoundException re ) {
            LOGGER.info( "Account does not exist for seller id " + accountName, re );
        }
        return accountId;
    }

    /**
     * Gets the contact id.
     *
     * @param email
     *            the email
     * @return the contact id
     */
    private String getContact( final String email ) {
        String contactId = null;
        final QueryParams params = new QueryParams();
        params.add( EMAIL, email );
        try {
            contactId = convertObjectToString(
                    crmQueryService.findOne( GET_CONTACT_BY_EMAIL_ID, params ).get( QUERY_PARAM_ID ) );
        } catch ( final ResultNotFoundException re ) {
            LOGGER.info( "Contact does not exist for email " + email, re );
        }
        return contactId;
    }

    /**
     * Gets the record type name.
     *
     * @param map
     *            the map
     * @return the record type name
     */
    private String getObjectName( final Object map ) {
        return ( ( Map< String, String > ) map ).get( "Name" );
    }

    /**
     * Gets the opportunity owner email.
     *
     * @param map
     *            the map
     * @return the opportunity owner email
     */
    private String getOpportunityOwnerEmail( final Object map ) {
        return ( ( Map< String, String > ) map ).get( "Email" );
    }

    /**
     * Gets the slack channel url.
     *
     * @param orderType
     *            the order type
     * @return the slack url
     */
    private String getSlackChannelUrl( final String orderType ) {
        return PROPERTY_ORDER_TYPE.equals( orderType ) ? slackFreeServiceChannelUrl : slackPaidServiceChannelUrl;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.OpportunityBusinessService#
     * getOpportunityIdByRecordTypeAndLoanNumber(java.lang.String, int)
     */
    @Override
    public String getOpportunityIdByRecordTypeAndLoanNumber( final String recordTypeId, final int loanNumber ) {
        String opportunityId = StringUtils.EMPTY;
        try {
            opportunityId = opportunityService.getOpportunityIdByRecordTypeIdAndLoanNumber( recordTypeId, loanNumber );
        } catch ( final ResultNotFoundException e ) {
            LOGGER.info( "OCL opportunity not found for loan number: " + loanNumber, e );
        }
        return opportunityId;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.OpportunityBusinessService#
     * getOpportunityIdByRecordTypeAndEmail(java.lang.String, java.lang.String)
     */
    @Override
    public String getOpportunityIdByRecordTypeAndEmail( final String recordTypeId, final String email ) {
        String opportunityId = StringUtils.EMPTY;
        try {
            opportunityId = opportunityService.getOpportunityIdByRecordTypeIdAndEmail( recordTypeId, email );
        } catch ( final ResultNotFoundException e ) {
            LOGGER.info( "Opportunity not found for email: " + email + " and recordTypeId: " + recordTypeId );
        }
        return opportunityId;
    }

    /**
     * Forward to referral exchange.
     *
     * @param opportunitySource
     *            the opportunity source
     */
    @Override
    public void forwardToReferralExchange( final OpportunitySource opportunitySource,
            final Map< String, Object >... maps ) {
        final ReferralExchangeRequest newRequest = referralExchangeOpportunityRequestBuilder
                .convertTo( opportunitySource );
        boolean referred = TRUE;
        String referralFailureDetails = StringUtils.EMPTY;
        try {
            referralExchangeService.forwardRequest( newRequest );
            final String name = getName( opportunitySource.getPrimaryContact().getFirstName(),
                    opportunitySource.getPrimaryContact().getLastName() );
            LOGGER.info( "Sending referral ex notification to " + name );
            sendReferralExchangeNotification( name, opportunitySource.getPrimaryContact().getEmails() );
        } catch ( final HttpClientErrorException hce ) {
            LOGGER.debug( "Problem referring the opportunity to referral exchange: " + hce.getResponseBodyAsString(),
                    hce );
            referred = FALSE;
            referralFailureDetails = hce.getResponseBodyAsString();
        }

        final String crmId = opportunitySource.getCrmId();
        LOGGER.info( "Updating opportunity " + crmId + " in salesforce after referral exchange" );
        final Map< String, Object > patchRequest = new HashMap<>();
        patchRequest.put( "dateAgreementSentOutReferred", new DateTime().toDate() );
        patchRequest.put( "referralSuccessDetails", referred );
        patchRequest.put( "referralFailureDetails", referralFailureDetails );
        patchRequest.put( "broadcastStatus", REFERRED_TO_REF_EXCHG );
        patchRequest.put( "assignedAgent", getAgentIdByEmail( refExAgentEmail ) );
        patchRequest.put( "stage", FORWARDED_TO_REF_EX.getStage() );
        if (maps.length > 0) {
            for ( final Map< String, Object > map : maps ) {
                patchRequest.putAll( map );
            }
        }
        updateCRMOpportunity( patchRequest, crmId );
    }

    /**
     * Forward to referral exchange.
     *
     * @param opportunity
     *            CRM ID the opportunity source
     * @param message
     *            the message
     */
    @Override
    public void forwardToReferralExchange( final String crmId, final String message ) {
        updateAgentAssignmentLog( crmId, displayed_for_referral.name() );
        final OpportunitySource opportunitySource = getOpportunity( crmId );
        final Map< String, Object > patchRequest = new HashMap<>();
        if (message.contains( "COVERAGE" )) {
            patchRequest.put( "reasonLost", outOfCoverage );
        } else {
            patchRequest.put( "reasonLost", noAgentAvailable );
        }
        forwardToReferralExchange( opportunitySource, patchRequest );
    }

    /**
     * Gets the agent id by email.
     *
     * @param agentEmail
     *            the agent email
     * @return the agent id by email
     */
    private String getAgentIdByEmail( final String agentEmail ) {
        final QueryParams params = new QueryParams();
        params.add( EMAIL, agentEmail );
        return convertObjectToString( crmQueryService.findOne( GET_AGENT_ID_BY_EMAIL, params ).get( QUERY_PARAM_ID ) );
    }

    /**
     * Send successful referral exchange notification.
     *
     * @param firstName
     *            the first name
     * @param lastName
     *            the last name
     * @param emailList
     *            the email list
     */
    private void sendReferralExchangeNotification( final String fullName, final List< String > emailList ) {
        if (agentOpportunityBusinessConfig.isReferralEmailNotificationEnabled()) {
            final NotificationResponse response = mailService
                    .send( referralExchangeRequestEmailBuilder.convertTo( fullName, emailList ) );
            LOGGER.debug( "Email notification for referral exchange to" + emailList + " send status "
                    + response.getStatus() );
        }
    }

    /**
     * Gets the name.
     *
     * @param firstName
     *            the first name
     * @param lastName
     *            the last name
     * @return the name
     */
    private String getName( final String firstName, final String lastName ) {
        String name = lastName;
        if (StringUtils.isNotBlank( firstName )) {
            name = firstName + " " + lastName;
        }
        return name;
    }

    /**
     * Update crm opportunity.
     *
     * @param patchRequest
     *            the request
     * @param opportunityCrmId
     *            the opportunity crm id
     */
    @Override
    public void updateCRMOpportunity( final Map< String, Object > patchRequest, final String opportunityCrmId ) {
        final Map< String, Object > crmRequest = new HashMap<>();
        for ( final Entry< String, Object > entry : patchRequest.entrySet() ) {
            if (crmOppFields.get( entry.getKey() ) != null) {
                crmRequest.put( crmOppFields.get( entry.getKey() ), entry.getValue() );
            }
        }
        opportunityService.patchOpportunity( crmRequest, opportunityCrmId );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.OpportunityBusinessService#
     * assignOpportunityToAgent(com.owners.gravitas.amqp.OpportunitySource,
     * java.lang.String)
     */
    @Override
    @Async( value = "apiExecutor" )
    public void assignOpportunityToAgent( final OpportunitySource opportunitySource, final String agentEmail ) {
        LOGGER.info( "Assigning opportunity: " + opportunitySource.getCrmId() + " to agent: " + agentEmail );
        final Map< String, Object > patchRequest = new HashMap<>();
        patchRequest.put( "assignedAgent", getAgentIdByEmail( agentEmail ) );
        updateCRMOpportunity( patchRequest, opportunitySource.getCrmId() );
        opportunitySource.setAgentEmail( agentEmail );
        agentOpportunityBusinessService.handleOpportunityChange( opportunitySource );
    }

    /**
     * Reject agent for opportunity.
     *
     * @param crmId
     *            the opportunity id
     * @param request
     *            the request
     */
    @Override
    @Transactional
    public void rejectOpportunity( final String crmId, final Map< String, Object > request ) {
        LOGGER.info( "Update AgentAssignmentLog in DB for rejecting agent" + crmId );
        if (MapUtils.isNotEmpty( request )) {
            final String assignmentReason = agentAssignmentLogService
                    .getAgentRejectReason( convertObjectToString( request.get( "reason" ) ) );
            final String reasonMessage = convertObjectToString( request.get( "message" ) );
            final String agentEmailId = convertObjectToString( request.get( "agentEmailId" ) );
            final String nextAgentEmail = convertObjectToString( request.get( "nextAgentEmail" ) );

            final Pageable pageable = new PageRequest( 0, 1 );
            final AgentAssignmentLog agentAssignmentLog = agentAssignmentLogService.getAgentAssignmentLog( crmId,
                    agentEmailId, displayed.name(), pageable );
            if (agentAssignmentLog != null) {
                agentAssignmentLog.setAssignmentStatus( assignmentReason );
                agentAssignmentLog.setReason( reasonMessage );

                final AgentAssignmentLog nextAgentAssignmentLog = agentAssignmentLogService
                        .findByCrmOpportunityIdAndAgentEmailAndCreatedDate( crmId, nextAgentEmail,
                                agentAssignmentLog.getCreatedDate() );
                final String newStatus = getNewStatus( nextAgentAssignmentLog.getAssignmentStatus(), displayed.name() );
                nextAgentAssignmentLog.setAssignmentStatus( newStatus );

                agentAssignmentLogService.saveAll(
                        Arrays.asList( new AgentAssignmentLog[] { agentAssignmentLog, nextAgentAssignmentLog } ) );
            }
        }
    }

    @Override
    public AgentResponse createScheduledMeeting( final String crmId, final AgentMeetingRequest meetingRequest ) {
        LOGGER.info( "creating scheduled meeting for opportunity : {} request : {} ", crmId,
                JsonUtil.toJson( meetingRequest ) );
        final AgentTaskRequest agentTaskRequest = agentTaskRequestBuilder.convertTo( meetingRequest );
        final Search opportunitySearch = searchService.searchByCrmOpportunityId( crmId );
        final AgentResponse agentResponse = agentTaskBusinessService.createAgentTask( opportunitySearch.getAgentId(),
                opportunitySearch.getOpportunityId(), agentTaskRequest );
        LOGGER.info( "creating scheduled meeting for opportunity : {} with agent : {} response : {}", crmId,
                opportunitySearch.getAgentEmail(), JsonUtil.toJson( agentResponse ) );
        agentResponse.setMessage( "Meeting was successfully created" );
        return agentResponse;
    }

    @Override
    public AgentResponse createScheduledTourMeetings( final String crmId,
            final ScheduleTourMeetingRequest scheduleTourMeetingRequest ) {
        LOGGER.info( "creating scheduled tours for opportunity : {} request : {} ", crmId,
                JsonUtil.toJson( scheduleTourMeetingRequest ) );
        AgentResponse agentResponse = null;
        final List< ScheduleTourLeadRequest > meetingRequest = scheduleTourMeetingRequest.getMeetingRequest();
        isManualEntry( meetingRequest );
        if (coShoppingConfig.isEnableScheduleTourMeetings()) {
            if (checkIfPrimaryMeetingExists( meetingRequest )) {
                final Search opportunitySearch = searchService.searchByCrmOpportunityId( crmId );
                if (null == opportunitySearch) {
                    throw new ApplicationException(
                            "Unable to find the given crm Id " + crmId + " in search node of firebase. ",
                            ErrorCode.INVALID_OPPORTUNITY_SEARCHED );
                }
                final StringBuilder strIdBuilder = new StringBuilder();
                final List< String > idsList = createMultipleScheduledTourMeetings( meetingRequest, opportunitySearch );
                for ( final String id : idsList ) {
                    strIdBuilder.append( id + " " );
                }
                agentResponse = new AgentResponse( strIdBuilder.toString() );
                agentResponse.setMessage( "All schedule tour meetings successfully created" );
                return agentResponse;
            } else {
                return new AgentResponse( "", Status.FAILURE,
                        "failed to create the schedule tour meetings as there is no primary meeting available" );
            }
        }
        return new AgentResponse( "", Status.FAILURE,
                "failed to create the schedule tour meetings, check the EnableScheduleTourMeetings jmx flag" );

    }

    /***
     * Method to check if primary meeting is selected for first meeting
     *
     * @param meetingRequest
     * @return
     */
    private boolean checkIfPrimaryMeetingExists( final List< ScheduleTourLeadRequest > meetingRequest ) {
        LOGGER.info( "Check for isPrimary" );
        boolean isPrimaryPresent = false;
        for ( final ScheduleTourLeadRequest request : meetingRequest ) {
            if (request.isPrimary()) {
                isPrimaryPresent = true;
                break;
            }
        }
        return isPrimaryPresent;
    }

    /***
     * Method to check is manual entry, and setting isPrimary as true in order
     * to
     * mark this meeting as primary meeting
     * 
     * @param meetingRequest
     * @return
     */
    private void isManualEntry( final List< ScheduleTourLeadRequest > meetingRequest ) {
        LOGGER.info( "Trying to check is it ony for manual entry Request" );
        if (!CollectionUtils.isEmpty( meetingRequest ) && meetingRequest.size() == 1) {
            LOGGER.info( "It has only single request" );
            for ( final ScheduleTourLeadRequest request : meetingRequest ) {
                if (request.getListingId() == null) {
                    LOGGER.info( "It is manual entry request setting its primary as True" );
                    request.setPrimary( true );
                }
            }
        }
        LOGGER.info( "scheduled tours request after updating isPrimary request : {} ",
                JsonUtil.toJson( meetingRequest ) );
    }

    /***
     * Method to create multiple scheduled meetings/tours
     *
     * @param meetingRequest
     * @param opportunitySearch
     * @return
     */
    private List< String > createMultipleScheduledTourMeetings( final List< ScheduleTourLeadRequest > meetingRequest,
            final Search opportunitySearch ) {
        boolean isWarmTransfer = false;
        final List< String > agentResponseList = new ArrayList<>();

        // check if any meeting doesn't have due date and if not then update to
        // current date
        updateDefaultDueDate( meetingRequest );
               
        for ( final ScheduleTourLeadRequest request : meetingRequest ) {
            if (request.getListingId() != null) {
                final String coShoppingId = createCoShoppingScheduleTourMeetings( request,
                        opportunitySearch.getOpportunityId() );
                request.setCoShoppingId( coShoppingId );
            }
            final AgentTaskRequest agentTaskRequest = buildAgentTaskRequest( request );
            final AgentResponse agentResponse = agentTaskBusinessService.createAgentTask(
                    opportunitySearch.getAgentId(), opportunitySearch.getOpportunityId(), agentTaskRequest );
            agentResponseList.add( agentResponse.getId() );
            if (!isWarmTransfer) {
                if (request.getIsWarmTransferCall() != null) {
                    isWarmTransfer = request.getIsWarmTransferCall();
                }
            }
        }
        if (isWarmTransfer) {
            // Commenting out as part GRACOR-1430.
            // sendScheduleTourConfirmationEmail( opportunitySearch );
            updateStageToInContact( opportunitySearch.getAgentId(), opportunitySearch.getOpportunityId() );
        }
        return agentResponseList;
    }
    
    /**
     * Update stage to in contact.
     *
     * @param opportunityId
     *            the opportunity id
     */
    @Override
    public void updateStageToInContact( final String agentId, final String opportunityId ) {
        final com.owners.gravitas.domain.entity.Contact contactEntity = contactEntityService
                .getContactByFbOpportunityId( opportunityId );
        if (contactEntity != null) {
            try {
                LOGGER.info( "Changing Opportunity : {} stage to {}", opportunityId, BuyerStage.IN_CONTACT.getStage() );
                final Opportunity opportunity = agentOpportunityService.getOpportunityById( agentId, opportunityId );

                // updating stage in gr_contact table
                contactEntity.setStage( BuyerStage.IN_CONTACT.getStage() );
                contactEntity.setLastModifiedDate( new DateTime( System.currentTimeMillis() ) );
                contactEntityService.save( contactEntity );

                // updating stage & stageHistory in FireBase
                agentOpportunityService.patchOpportunityStage(agentId, opportunityId, BuyerStage.IN_CONTACT.getStage(), opportunity);
                
                // Updating OpportunityStage & creating system generated task on
                // stage change.
                final com.owners.gravitas.domain.Contact contact = agentContactService.getContactById( agentId,
                        String.valueOf( opportunity.getContacts().toArray()[0] ) );
                final Contact contactDto = contactBuilder.convertTo( contact );
                final OpportunityChangeHandler opportunityChangeHandler = opportunityChangeHandlerFactory
                        .getChangeHandler( Stage, BuyerStage.IN_CONTACT.getStage() );
                if (opportunityChangeHandler != null) {
                    opportunityChangeHandler.handleChange( agentId, opportunityId, contactDto,
                            BuyerStage.IN_CONTACT.getStage() );
                }

                // updating stage in SF
                LOGGER.info( "Updating Stage to InContact in SF for opportunityId : {}", opportunityId );
                final Map< String, Object > crmMap = new HashMap< String, Object >();
                crmMap.put( CRMConstants.STAGE, BuyerStage.IN_CONTACT.getStage() );
                opportunityService.patchOpportunity( crmMap, contactEntity.getCrmId() );
                
                LOGGER.info( "Updated Stage to InContact for opportunityId : {}", opportunityId );
            } catch ( final Exception ex ) {
                LOGGER.error(
                        "Exception occurred while updating Stage to InContact for opportunity id {} with exception ",
                        opportunityId, ex );
            }
        }
    }

    private void sendScheduleTourConfirmationEmail( final Search opportunitySearch ) {
        final Map< String, Task > openScheduleTourTasks = agentTaskService.getOpenTasksByType(
                opportunitySearch.getAgentId(), opportunitySearch.getOpportunityId(),
                TaskType.SCHEDULE_TOUR.getType() );
        LOGGER.info( "List of open schedule tour tasks of size {}", openScheduleTourTasks.size() );
        if (null != openScheduleTourTasks) {
            for ( final Entry< String, Task > taskEntry : openScheduleTourTasks.entrySet() ) {
                final Task task = taskEntry.getValue();
                task.setId( taskEntry.getKey() );
                if (!task.getDeleted() && task.getIsPrimary()
                        && TaskType.SCHEDULE_TOUR.getType().equalsIgnoreCase( task.getTaskType() )
                        && StringUtils.isNotBlank( task.getListingId() )
                        && StringUtils.isNotBlank( task.getCoShoppingId() )) {
                    LOGGER.info( "sending tour confirmation mail with task type: {}", task.getIsPrimary() );
                    tourConfirmationBusiness.sendConfirmationEmailForScheduleTour( opportunitySearch.getAgentId(),
                            task );
                }
            }
        }
    }

    /***
     * Method to either update co-shopping for existing SCHEDULE_TOUR or create
     * new request
     * in co-shopping for make offer and ask a question requests
     *
     * @param request
     * @return
     */
    private String createCoShoppingScheduleTourMeetings( final ScheduleTourLeadRequest request,
            final String opportunityId ) {
        validateScheduleTourLeadRequest( request ); // added this as part of
                                                    // Story GRACRM-802
        String coShoppingId = request.getCoShoppingId();
        final Date dueDate = DateUtil.getDueDateFromStringDt( request.getDueDtm(), request.getTimezone() );
        // If warm transfer true make status confirmed
        final String status = PENDING.name();
        // commenting below condition as per GRACOR-1430
//        if (request.getIsWarmTransferCall()) {
//            status = CONFIRMED.name();
//        }
        // if request type is of type Schedule a Tour & co-shopping id is
        // present then update co-shopping
        if (TaskType.SCHEDULE_TOUR.getType().equalsIgnoreCase( request.getRequestType() )
                && StringUtils.isNotBlank( request.getCoShoppingId() )) {
            final CoShoppingLeadUpdateModel coShoppingLeadUpdateModel = coShoppingLeadUpdateModelBuilder.build(
                    request.getCoShoppingId(), DateUtil.getPropertyTourDate( dueDate, request.getTimezone() ), status );
            try {
                coShoppingService.updateLeadDetails( coShoppingLeadUpdateModelBuilder
                        .buildCoShoppingLeadUpdateRequest( coShoppingLeadUpdateModel ) );
            } catch ( final Exception e ) {
                LOGGER.error( "Error while updating to Coshopping api for the opportunityId : {}", opportunityId, e );
            }
        } else {// else create a new schedule tour task in co-shopping
            com.owners.gravitas.domain.entity.Contact contact = null;
            if (StringUtils.isNotBlank( opportunityId )) {
                contact = contactEntityService.getContactByFbOpportunityId( opportunityId );
            }
            final PropertyDetailsResponse propertyDetails = propertyService
                    .getPropertyDetails( request.getListingId() );
            final CoShoppingLeadRequest coShoppingLeadRequest = coShoppingLeadBuilder.build( dueDate, contact,
                    propertyDetails.getData(), request.getTimezone() );
            coShoppingLeadRequest.getLeadModel().setListingId( request.getListingId() );
            coShoppingLeadRequest.getLeadModel().setType( LeadRequestType.SCHEDULE_TOUR );
            coShoppingLeadRequest.getLeadModel().setStatus( status );
            coShoppingLeadRequest.getLeadModel().setOfferAmount( request.getOfferAmount() );
            coShoppingLeadRequest.getLeadModel().setQuestion( request.getQuestion() );
            coShoppingLeadRequest.getLeadModel().setPropertyHalfBathroom( request.getPropertyHalfBathroom() );
            Resource coshoppingTourResponse = null;
            if (isLeadEligibleForCoShoppingPush( coShoppingLeadRequest )) {
                try {
                    coshoppingTourResponse = coShoppingService.postLeadDetails( coShoppingLeadRequest );
                } catch ( final Exception ex ) {
                    LOGGER.error(
                            "Exception occurred in create co-shopping schedule tour meetings while pushing to coshopping api for the opportunity id {} with exception ",
                            opportunityId, ex );
                }
            }
            if (null != coshoppingTourResponse) {// return co-shopping id.
                coShoppingId = coshoppingTourResponse.getId();
            }
        }
        return coShoppingId;
    }

    /**
     * Method to check if given lead request if eligible for co-shopping push.
     *
     * @param request
     * @return
     */
    private boolean isLeadEligibleForCoShoppingPush( final CoShoppingLeadRequest request ) {
        boolean eligibility = false;
        final LeadModel model = request.getLeadModel();
        if (null != model && StringUtils.isNotBlank( model.getUserId() ) && StringUtils.isNotBlank( model.getMlsId() )
                && StringUtils.isNotBlank( model.getListingId() ) && StringUtils.isNotBlank( model.getEmail() )
                && StringUtils.isNotBlank( model.getPropertyTourInformation() )) {
            LOGGER.info( "lead request with listing id : {} is eligible to push in coshopping DB ",
                    model.getListingId() );
            eligibility = true;
        }
        return eligibility;
    }

    /***
     * Method to build the AgentTaskRequest to store data in gravitas fb and
     * mysql
     *
     * @param scheduleTourLeadrequest
     * @return
     */
    private AgentTaskRequest buildAgentTaskRequest( final ScheduleTourLeadRequest scheduleTourLeadrequest ) {
        final AgentTaskRequest request = new AgentTaskRequest();
        request.setCoShoppingId( scheduleTourLeadrequest.getCoShoppingId() );
        request.setCreatedBy( createdBy );
        request.setDescription( scheduleTourLeadrequest.getDescription() );
        request.setDueDtm( DateUtil.getDueDateFromStringDt( scheduleTourLeadrequest.getDueDtm(),
                scheduleTourLeadrequest.getTimezone() ) );
        request.setLocation( scheduleTourLeadrequest.getLocation() );
        request.setPrimary( scheduleTourLeadrequest.isPrimary() );
        request.setStatus( PENDING.name() );
        // commenting below condition as per GRACOR-1430 & setting status to PENDING
//        request.setStatus( ( null != scheduleTourLeadrequest.getIsWarmTransferCall()
//                && scheduleTourLeadrequest.getIsWarmTransferCall() ) ? CONFIRMED.name() : PENDING.name() );
        request.setTitle( scheduleTourLeadrequest.getTitle() );
        request.setType( scheduleTourLeadrequest.getType() );
        request.setListingId( scheduleTourLeadrequest.getListingId() );
        request.setIsWarmTransferCall( scheduleTourLeadrequest.getIsWarmTransferCall() );
        return request;
    }

    /**
     * Method to update the due date as default current date when user has not
     * selected the due date for a meeting
     *
     * @param meetingRequest
     */
    private void updateDefaultDueDate( final List< ScheduleTourLeadRequest > meetingRequest ) {
        final DateFormat df = new SimpleDateFormat( "MM-dd-yyyy HH:mm" );
        final Date today = Calendar.getInstance().getTime();
        final String defaultDueDate = df.format( today );
        for ( final ScheduleTourLeadRequest request : meetingRequest ) {
            if (StringUtils.isBlank( request.getDueDtm() )) {
                request.setDueDtm( defaultDueDate );
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.OpportunityBusinessService#
     * createSkipCallActionFlow(java.lang.String, java.lang.String)
     */
    @Override
    public BaseResponse createSkipMeetingActionFlow( final String insideSalesEmail, final String crmId ) {
        final BaseResponse response = new BaseResponse();
        response.setMessage(
                "failed while creating action flow on skip meeting action, check if data exists in FB search node" );
        response.setStatus( Status.FAILURE );
        final Search search = searchService.searchByCrmOpportunityId( crmId );
        if (null != search) {
            // save audit data for skip action
            actionLogService.save( buildActionLogDetails( insideSalesEmail, search ) );
            // get opportunity details and check if eligible for scripted call &
            // push notification
            final Opportunity existingOpportunity = agentOpportunityService.getOpportunityById( search.getAgentId(),
                    search.getOpportunityId() );
            final boolean isEligibleForScriptedCall = actionFlowBusinessService
                    .isEligibleForScriptedCall( existingOpportunity.getOpportunityType(), search.getAgentEmail() );
            if (isEligibleForScriptedCall) {
                LOGGER.info( "agent is eligible for scripted call functionality : {} ", search.getAgentEmail() );
                // start action flow and send push notification
                actionFlowBusinessService.createActionGroup( search.getOpportunityId(), search.getAgentId(),
                        existingOpportunity, search );
                return new BaseResponse( Status.SUCCESS,
                        "You have clicked SKIP and NO tour/meeting has been created." );

            }
            return new BaseResponse( Status.SUCCESS, "You have clicked SKIP and NO tour/meeting has been created." );
        }
        return response;
    }

    /***
     * Builder method to build action log details while skipping the schedule
     * tour/meeting creation
     * 
     * @param insideSalesEmail
     *            the inside sales email
     * @param search
     *            the search node data
     * @return ActionLog
     */
    private ActionLog buildActionLogDetails( final String insideSalesEmail, final Search search ) {
        final ActionLog actionLog = new ActionLog();
        actionLog.setActionBy( insideSalesEmail );
        actionLog.setActionEntity( OPPORTUNITY.name() );
        actionLog.setActionEntityId( search.getOpportunityId() );
        actionLog.setActionType( Constants.SKIP_SCHEDULE_MEETING );
        actionLog.setFieldName( search.getAgentId() );
        actionLog.setDescription( Constants.SKIP_SCHEDULE_MEETING_DESC );
        return actionLog;
    }

    @Override
    public void sendAppDownloadReminder( final OpportunitySource opportunitySource ) {
        LOGGER.info( "Sending app download reminder for the opportunity {}", opportunitySource.getCrmId() );
        final com.owners.gravitas.domain.entity.Contact contact = contactEntityService
                .findByCrmId( opportunitySource.getCrmId() );
        final BuyerDeviceResponse buyerServiceResponse = buyerService.getBuyerDeviceDetails( contact.getOwnersComId() );
        if (null != buyerServiceResponse && null != buyerServiceResponse.getResult()) {
            final BuyerDeviceDetail buyerDeviceDetail = buyerServiceResponse.getResult();
            if (CollectionUtils.isEmpty( buyerDeviceDetail.getDevices() )) {
                final SmsNotification smsNotification = smsService.prepareAndGetSmsNotification( opportunitySource,
                        contact.getPhone(), leadOpportunityBusinessConfig.getBuyerDownloadAppText() );
                smsNotification.getParameterMap().put( USER, contact.getOwnersComId() );
                final NotificationResponse reponse = smsService.send( smsNotification );
                if (null != reponse) {
                    final NotificationLog notificationLog = notificationService.prepareAndGetNotificationLog( reponse,
                            contact );
                    notificationService.save( notificationLog );
                }
            } else {
                LOGGER.info( "Buyer has alredy installed the app for the opportunity {}",
                        opportunitySource.getCrmId() );
            }
        }
    }

    /**
     * @param scheduleTourLeadRequest
     */
    private void validateScheduleTourLeadRequest( final ScheduleTourLeadRequest scheduleTourLeadRequest ) {
        final List< String > errorList = new ArrayList<>();
        LOGGER.info( "Validating Schedule Tour Request :{}", JsonUtil.toJson( scheduleTourLeadRequest ) );

        validateInputField( scheduleTourLeadRequest.getListingId(),
                "error.scheduleTourMeetingRequest.meetingRequest.listingId", errorList );
        validateInputField( scheduleTourLeadRequest.getRequestType(),
                "error.scheduleTourMeetingRequest.meetingRequest.requestType", errorList );
        validateInputField( scheduleTourLeadRequest.getDueDtm(),
                "error.scheduleTourMeetingRequest.meetingRequest.dueDtm", errorList );
        validateInputField( scheduleTourLeadRequest.getCoShoppingId(),
                "error.scheduleTourMeetingRequest.meetingRequest.coShoppingId", errorList );
        validateInputField( scheduleTourLeadRequest.getTitle(), "error.scheduleTourMeetingRequest.meetingRequest.title",
                errorList );
        validateInputField( scheduleTourLeadRequest.getUserId(),
                "error.scheduleTourMeetingRequest.meetingRequest.userId", errorList );
        validateInputField( scheduleTourLeadRequest.getMlsId(), "error.scheduleTourMeetingRequest.meetingRequest.mlsId",
                errorList );
        validateInputField( scheduleTourLeadRequest.getTimezone(),
                "error.scheduleTourMeetingRequest.meetingRequest.timeZone", errorList );
        if (null == scheduleTourLeadRequest.getIsWarmTransferCall()) {
            errorList.add( "error.meeting.warmTransfer.required" );
        }

        if (!errorList.isEmpty()) {
            throw new InvalidArgumentException( "Invalid schedule tour request ", errorList );
        }
    }

    /**
     * Validate null and blank fields and populate with given error code
     * 
     * @param field
     * @param errorCode
     * @param errorList
     */
    private void validateInputField( final String field, final String errorCode, final List< String > errorList ) {
        if (StringUtils.isBlank( field )) {
            errorList.add( errorCode );
        }
    }
    
    /* (non-Javadoc)
     * @see com.owners.gravitas.business.OpportunityBusinessService#getDiscountForOpportunity(java.lang.String, java.lang.String)
     */
    @Override
    public OpportunityDiscountResponse getDiscountForOpportunity( final String agentFbId, final String fbId ) {
        final Opportunity opportunity = agentOpportunityService.getOpportunityById( agentFbId, fbId );
        final String oppCrmId = opportunity.getCrmId();
        final String oppPriceRange = opportunity.getPriceRange();
        final com.owners.gravitas.domain.entity.Contact contact = contactEntityService.findByCrmId( oppCrmId );
        final String oppState = contact.getState();
        LOGGER.info( "Opportunity CRM id :{} ,State :{} and Price Range :{}", oppCrmId, oppState, oppPriceRange );
        final OpportunityDiscountResponse opportunityDiscountResponse = new OpportunityDiscountResponse();
        opportunityDiscountResponse.setStatus( Status.FAILURE );
        if (( StringUtils.isEmpty( oppPriceRange ) && StringUtils.isEmpty( oppState ) )) {
            opportunityDiscountResponse.setMessage( Constants.PRICE_RANGE_AND_STATE_IS_NOT_PRESENT );
            return opportunityDiscountResponse;
        } else if (StringUtils.isEmpty( oppPriceRange )) {
            opportunityDiscountResponse.setMessage( Constants.PRICE_RANGE_IS_NOT_PRESENT );
            return opportunityDiscountResponse;
        } else if (StringUtils.isEmpty( oppState )) {
            opportunityDiscountResponse.setMessage( Constants.STATE_IS_NOT_PRESENT );
            return opportunityDiscountResponse;
        }
        List< Discount > discountList = getDiscount( oppPriceRange, oppState );
        if (CollectionUtils.isEmpty( discountList )) {
            LOGGER.info( "As no discount list found, checking failure reason" );
            discountList = discountRepository.findByState( oppState );
            LOGGER.info( "discount list by state :{}", discountList );
            if (CollectionUtils.isEmpty( discountList )) {
                opportunityDiscountResponse.setMessage( Constants.NO_DISCOUNT_FOR_STATE );
                return opportunityDiscountResponse;
            } else {
                opportunityDiscountResponse.setMessage( Constants.NO_DISCOUNT_FOR_PRICE_RANGE );
                return opportunityDiscountResponse;
            }
        }

        return createDiscountResponse( fbId, discountList.iterator().next() );
    }
    
    /**
     * @param oppPriceRange
     * @param oppState
     * @return
     */
    private List< Discount > getDiscount( final String oppPriceRange, final String oppState ) {
        final String[] priceRange = opportunityService.getOpportunityStartAndEndPriceRange( oppPriceRange );
        List< Discount > discountList = new ArrayList<>();
        int startPriceRange = 0;
        int endPriceRange = 0;
        if (priceRange.length > 1) {
            startPriceRange = Integer.parseInt( priceRange[0] );
            endPriceRange = Integer.parseInt( priceRange[1] );
        } else if (priceRange.length == 1) {
            endPriceRange = Integer.parseInt( priceRange[0] );
        }
        LOGGER.info( "start price range and end price range for opportunity :{}", startPriceRange, endPriceRange );
        if (startPriceRange != 0) {
            discountList = discountRepository.findByStateAndMinumumSalePriceBetweenOrderByMinumumSalePriceDesc(
                    oppState, startPriceRange, endPriceRange );
        } else {
            discountList = discountRepository
                    .findByStateAndMinumumSalePriceLessThanEqualOrderByMinumumSalePriceDesc( oppState, endPriceRange );
        }
        LOGGER.info( "Total matching pair for discount is :{}", discountList.size() );
        return discountList;
    }

    /**
     * @param discount
     * @return
     */
    private OpportunityDiscountResponse createDiscountResponse( final String oppFbId, final Discount discount ) {
        LOGGER.info( "For opportunity id :{} , creating discount as :{}", oppFbId, discount );
        final OpportunityDiscountResponse opportunityDiscountResponse = new OpportunityDiscountResponse();
        opportunityDiscountResponse.setOppFbId( oppFbId );
        opportunityDiscountResponse.setBuySellDiscount( discount.getBuySellDiscount() );
        opportunityDiscountResponse.setMaxDiscount( discount.getMaxDiscount() );
        opportunityDiscountResponse.setOclDiscount( discount.getOclDiscount() );
        opportunityDiscountResponse.setPtsDiscount( discount.getPtsDiscount() );
        return opportunityDiscountResponse;
    }
}
