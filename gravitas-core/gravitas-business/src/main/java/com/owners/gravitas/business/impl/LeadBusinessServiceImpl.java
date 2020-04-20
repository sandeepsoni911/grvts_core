package com.owners.gravitas.business.impl;

import static com.owners.gravitas.constants.CRMConstants.LEAD_CONVERTED_OPPORTUNITY_ID;
import static com.owners.gravitas.constants.CRMConstants.LEAD_DEDUP_COUNT;
import static com.owners.gravitas.constants.CRMConstants.LEAD_REFERRED_DATE;
import static com.owners.gravitas.constants.CRMConstants.LEAD_STATUS;
import static com.owners.gravitas.constants.CRMConstants.REASON_CLOSED;
import static com.owners.gravitas.constants.CRMConstants.REFERRAL_FAILURE_DETAILS;
import static com.owners.gravitas.constants.CRMConstants.REFERRED_SUCCESSFULLY_FLAG;
import static com.owners.gravitas.constants.CRMQuery.GET_RECORD_TYPE_NAME;
import static com.owners.gravitas.constants.Constants.BLANK_SPACE;
import static com.owners.gravitas.constants.Constants.COMMA;
import static com.owners.gravitas.constants.Constants.ID;
import static com.owners.gravitas.constants.Constants.SEMI_COLON;
import static com.owners.gravitas.enums.BuyerFarmType.ACTIVE_BUYER;
import static com.owners.gravitas.enums.BuyerFarmType.LONG_TERM_BUYER;
import static com.owners.gravitas.enums.BuyerFarmType.LOST_BUYER;
import static com.owners.gravitas.enums.CRMObject.LEAD;
import static com.owners.gravitas.enums.CRMObject.OPPORTUNITY;
import static com.owners.gravitas.enums.GravitasProcess.INSIDE_SALES_FARMING_PROCESS;
import static com.owners.gravitas.enums.GravitasProcess.LEAD_MANAGEMENT_PROCESS;
import static com.owners.gravitas.enums.LeadStatus.FORWARDED_TO_REF_EX;
import static com.owners.gravitas.enums.LeadStatus.LOST;
import static com.owners.gravitas.enums.LeadStatus.NEW;
import static com.owners.gravitas.enums.LeadStatus.QUALIFIED;
import static com.owners.gravitas.enums.ReasonClosed.OUT_OF_COVERAGE_AREA;
import static com.owners.gravitas.enums.RecordType.BUYER;
import static com.owners.gravitas.enums.RecordType.OWNERS_COM_LOANS;
import static com.owners.gravitas.enums.RecordType.SELLER;
import static com.owners.gravitas.enums.RecordType.getRecordType;
import static com.owners.gravitas.util.StringUtils.convertObjectToString;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.persistence.OptimisticLockException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.RuntimeService;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.BuyerFarmingBusinessService;
import com.owners.gravitas.business.LeadBusinessService;
import com.owners.gravitas.business.LeadFollowupBusinessService;
import com.owners.gravitas.business.OpportunityBusinessService;
import com.owners.gravitas.business.ProcessBusinessService;
import com.owners.gravitas.business.builder.CoShoppingLeadBuilder;
import com.owners.gravitas.business.builder.ContactEntityBuilder;
import com.owners.gravitas.business.builder.ContactLogBuilder;
import com.owners.gravitas.business.builder.EmailNotificationBuilder;
import com.owners.gravitas.business.builder.LeadSyncUpBuilder;
import com.owners.gravitas.business.builder.OclLeadDetailsBuilder;
import com.owners.gravitas.business.builder.domain.OpportunityBuilder;
import com.owners.gravitas.business.builder.request.CRMLeadRequestBuilder;
import com.owners.gravitas.business.builder.request.ContactStatusBuilder;
import com.owners.gravitas.business.builder.request.GenericLeadRequestBuilder;
import com.owners.gravitas.business.builder.request.OCLGenericLeadRequestBuilder;
import com.owners.gravitas.business.builder.request.ReferralExchangeRequestBuilder;
import com.owners.gravitas.business.builder.request.ReferralExchangeRequestEmailBuilder;
import com.owners.gravitas.business.task.LeadTask;
import com.owners.gravitas.config.BuyerFarmingConfig;
import com.owners.gravitas.config.CoShoppingConfig;
import com.owners.gravitas.config.LeadBusinessConfig;
import com.owners.gravitas.config.LeadOpportunityBusinessConfig;
import com.owners.gravitas.config.ZillowHotlineJmxConfig;
import com.owners.gravitas.constants.CRMConstants;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.ContactLog;
import com.owners.gravitas.domain.entity.ContactStatus;
import com.owners.gravitas.domain.entity.LeadSyncUp;
import com.owners.gravitas.domain.entity.MenuConfig;
import com.owners.gravitas.domain.entity.SubMenuConfig;
import com.owners.gravitas.dto.LeadDetails;
import com.owners.gravitas.dto.LeadDetailsDTO;
import com.owners.gravitas.dto.LeadDetailsList;
import com.owners.gravitas.dto.MenuConfigDto;
import com.owners.gravitas.dto.MenuDto;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.dto.SubMenuConfigDto;
import com.owners.gravitas.dto.crm.request.CRMLeadRequest;
import com.owners.gravitas.dto.crm.response.CRMLeadResponse;
import com.owners.gravitas.dto.crm.response.CRMResponse;
import com.owners.gravitas.dto.request.CoShoppingLeadRequest;
import com.owners.gravitas.dto.request.GenericLeadRequest;
import com.owners.gravitas.dto.request.LeadDetailsRequest;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.dto.request.OclLeadRequest;
import com.owners.gravitas.dto.request.ReferralExchangeRequest;
import com.owners.gravitas.dto.request.RequestTypeLeadRequest;
import com.owners.gravitas.dto.request.SyncUpRequest;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.ClaimLeadResponse;
import com.owners.gravitas.dto.response.LeadDetailsResponse;
import com.owners.gravitas.dto.response.LeadLayoutResponse;
import com.owners.gravitas.dto.response.LeadResponse;
import com.owners.gravitas.dto.response.MenuConfigResponse;
import com.owners.gravitas.dto.response.NotificationResponse;
import com.owners.gravitas.enums.BuyerFarmType;
import com.owners.gravitas.enums.LeadLayoutEnum;
import com.owners.gravitas.enums.LeadStatus;
import com.owners.gravitas.enums.RecordType;
import com.owners.gravitas.exception.ResultNotFoundException;
import com.owners.gravitas.handler.lead.syncup.processor.LeadProcessSyncUpHandlerFactory;
import com.owners.gravitas.handler.lead.syncup.processor.impl.UuidProcessSyncUpServiceImpl;
import com.owners.gravitas.handler.lead.syncup.reader.LeadReadSyncUpHandlerFactory;
import com.owners.gravitas.handler.lead.syncup.reader.impl.UuidReadSyncUpServiceImpl;
import com.owners.gravitas.lock.SyncCacheLockHandler;
import com.owners.gravitas.service.CRMQueryService;
import com.owners.gravitas.service.CoShoppingService;
import com.owners.gravitas.service.ContactEntityService;
import com.owners.gravitas.service.ContactLogService;
import com.owners.gravitas.service.ContactStatusService;
import com.owners.gravitas.service.DeDuplicationService;
import com.owners.gravitas.service.LeadLayoutService;
import com.owners.gravitas.service.LeadService;
import com.owners.gravitas.service.LeadSyncUpEntityService;
import com.owners.gravitas.service.LiveVoxService;
import com.owners.gravitas.service.MailService;
import com.owners.gravitas.service.MenuConfigService;
import com.owners.gravitas.service.OpportunityService;
import com.owners.gravitas.service.ReferralExchangeService;
import com.owners.gravitas.service.RoleMemberService;
import com.owners.gravitas.service.cache.LiveVoxLookupService;
import com.owners.gravitas.service.wrapper.LeadContactWrapper;
import com.owners.gravitas.util.GravitasWebUtil;
import com.owners.gravitas.util.JsonUtil;
import com.owners.gravitas.validator.LeadValidator;
import com.zuner.coshopping.model.common.Resource;

/**
 * The Class LeadBusinessServiceImpl, is business service class to deal with
 * lead to configured CRM.
 *
 * @author vishwanathm
 */
@Service
@Transactional( readOnly = true )
public class LeadBusinessServiceImpl implements LeadBusinessService {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( LeadBusinessServiceImpl.class );

    /** The Constant CREATED. */
    private static final String CREATED = "created";

    /** The Constant UPDATED. */
    private static final String UPDATED = "updated";

    @Autowired
    private ZillowHotlineJmxConfig zillowHotlineJmxConfig;

    /** instance of {@link CRMService}. */
    @Autowired
    private LeadService leadService;

    /** The mail service. */
    @Autowired
    private MailService mailService;

    /** The lead request builder. */
    @Autowired
    private CRMLeadRequestBuilder crmLeadRequestBuilder;

    /** The notification request builder. */
    @Autowired
    private EmailNotificationBuilder emailNotificationBuilder;

    /** The de duplication service. */
    @Autowired
    private DeDuplicationService deDuplicationService;

    /** The opportunity business service. */
    @Autowired
    private OpportunityBusinessService opportunityBusinessService;

    /** The lead exchange service. */
    @Autowired
    private ReferralExchangeService referralExchangeService;

    /** The referral exchange request builder. */
    @Autowired
    private ReferralExchangeRequestBuilder referralExchangeRequestBuilder;

    /** The generic lead request builder. */
    @Autowired
    private GenericLeadRequestBuilder genericLeadRequestBuilder;

    /** The marketing email process business service impl. */
    @Autowired
    private LeadFollowupBusinessService leadFollowupBusinessService;

    /** The crm service. */
    @Autowired
    private CRMQueryService crmQueryService;

    /** The Opportunity Service service. */
    @Autowired
    private OpportunityService opportunityServiceImpl;

    /** The Lead validation service. */
    @Autowired
    private LeadValidator leadValidator;

    /** The generic lead request builder from lead source. */
    @Autowired
    private OCLGenericLeadRequestBuilder ownerGenericLeadRequestBuilder;

    /** The referral exchange request email builder. */
    @Autowired
    private ReferralExchangeRequestEmailBuilder referralExchangeRequestEmailBuilder;

    /** The config. */
    @Autowired
    private LeadBusinessConfig config;

    /** The buyer farming config. */
    @Autowired
    private BuyerFarmingConfig buyerFarmingConfig;

    /** The Ocl LeadDetails Builder. */
    @Autowired
    private OclLeadDetailsBuilder oclLeadDetailsBuilder;

    /** The referral exchange excluded lead sources. */
    @Value( "${referral.exchange.excluded.lead.sources}" )
    private String referralExchangeExcludedLeadSourcesStr;

    /** The ocl lead buyer states. */
    @Value( "${ocl.lead.buyer.state}" )
    private String oclLeadBuyerStates;

    /** The lead status. */
    @Value( "${ocl.lead.buyer.status}" )
    private String oclLeadBuyerStatus;

    /** The lead status. */
    @Value( "${buyer.farming.delay:PT10S}" )
    private String buyerFarmingDelay;

    /** The excluded lead sources. */
    private List< String > referralExchangeExcludedLeadSources;

    /** The ocl lead buyer states list. */
    private List< String > oclLeadBuyerStatesList;

    /** The ocl buyer lead status. */
    private List< String > oclLeadBuyerStatusList;

    /** The contact builder V 1. */
    @Autowired
    private ContactEntityBuilder contactBuilderV1;

    /** The contact service V 1. */
    @Autowired
    private ContactEntityService contactServiceV1;

    @Autowired
    private ContactLogService contactLogService;

    /** The lead opportunity business config. */
    @Autowired
    private LeadOpportunityBusinessConfig leadOpportunityBusinessConfig;

    /** The lead task. */
    @Autowired
    private LeadTask leadTask;
    
    @Autowired
    private SyncCacheLockHandler syncCacheLock;

    /** The buyer registration business service. */
    @Autowired
    private BuyerFarmingBusinessService buyerFarmingBusinessService;

    /** The runtime service. */
    @Autowired
    protected RuntimeService runtimeService;

    /** The process management business service. */
    @Autowired
    protected ProcessBusinessService processBusinessService;

    /** The generic lead request builder. */
    @Autowired
    private OCLGenericLeadRequestBuilder ocleadRequestBuilder;

    /** The invalid phone numbers. */
    @Value( value = "${buyer.farming.lead.not.considered.lost.reasons}" )
    private String excludedLostReasonsStr;

    /** The entity opportunity builder V 1. */
    @Autowired
    private com.owners.gravitas.service.builder.OpportunityEntityBuilder entityOpportunityBuilderV1;

    /** The opportunity builder. */
    @Autowired
    private OpportunityBuilder opportunityBuilder;

    @Autowired
    private ContactLogBuilder contactLogBuilder;

    /** The invalid phone values. */
    private final List< String > excludedLostReasons = new ArrayList<>();

    /** The state where notification to be triggered for financing leads */
    // @Value( "${notify.finance.interested.lead.state}" )
    private String notifyLeadsForFinanceInState;

    /** The gravitas web util. */
    @Autowired
    private GravitasWebUtil gravitasWebUtil;

    @Autowired
    @Qualifier( value = "liveVoxService" )
    private LiveVoxService liveVoxService;

    @Autowired
    private LiveVoxLookupService liveVoxLookupService;

    /** Lead invalid Phone Reason closed. */
    @Value( "${crm.lead.invalid.phone.reason.closed}" )
    private String invalidPhonereasonClosed;

    @Autowired
    private CoShoppingLeadBuilder coShoppingLeadBuilder;

    @Autowired
    private CoShoppingService coShoppingService;

    @Autowired
    private CoShoppingConfig coShoppingConfig;

    @Autowired
    private LeadLayoutService leadLayoutService;

    @Autowired
    private RoleMemberService roleMemberService;

    @Autowired
    private MenuConfigService menuConfigService;

    @Autowired
    LeadReadSyncUpHandlerFactory leadReadSyncUpHandlerFactory;

    @Autowired
    LeadProcessSyncUpHandlerFactory leadProcessSyncUpHandlerFactory;

    /** The LeadSyncUpBuilder service. */
    @Autowired
    private LeadSyncUpBuilder leadSyncUpBuilder;

    /** The LeadSyncUpEntityService */
    @Autowired
    LeadSyncUpEntityService leadSyncUpEntityService;

    @Value( "${lead.syncup.page.size}" )
    private int leadSyncUpPageSize;

    @Autowired
    private ContactStatusService contactStatusService;
    
    @Autowired
    private ContactStatusBuilder contactStatusBuilder;
    
    /**
     * Inits the excluded lead sources for referral exchange.
     */
    @PostConstruct
    private void initDataBuilder() {
        referralExchangeExcludedLeadSources = new ArrayList<>();
        Stream.of( referralExchangeExcludedLeadSourcesStr.split( COMMA ) )
                .forEach( name -> referralExchangeExcludedLeadSources.add( name.trim() ) );

        oclLeadBuyerStatesList = new ArrayList<>();
        Stream.of( oclLeadBuyerStates.split( COMMA ) )
                .forEach( name -> oclLeadBuyerStatesList.add( name.trim().toLowerCase() ) );

        oclLeadBuyerStatusList = new ArrayList<>();
        Stream.of( oclLeadBuyerStatus.split( COMMA ) )
                .forEach( name -> oclLeadBuyerStatusList.add( name.trim().toLowerCase() ) );

        Stream.of( excludedLostReasonsStr.split( COMMA ) ).forEach( value -> excludedLostReasons.add( value.trim() ) );

    }

    /**
     * Handle lead changes for 1) Forward lead to referral exchange. if,
     * referred is false and lead state is not one of configured state.
     *
     * 2) Forward lead to referral exchange. if, status is lost and reason is
     * "out of coverage area" and referred is false.
     *
     * 3) Start lead follow up business process of email campaign. if, lead
     * status is "Outbound attempt".
     *
     * @param leadSource
     *            the lead source
     */
    @Override
    @Transactional( propagation = REQUIRES_NEW )
    public void handleLeadChange( final LeadSource leadSource ) {
        final String buyerEmail = leadSource.getEmail();
        LOGGER.info( "Handle lead change : {}, Time is : {}", buyerEmail, LocalDateTime.now() );
        final String recordTypeName = getRecordTypeName( leadSource.getRecordType() );
        leadSource.setRecordTypeName( recordTypeName );
        final boolean isBuyerLead = ( null != recordTypeName && BUYER.getType().equalsIgnoreCase( recordTypeName ) );
        final Contact contact = contactServiceV1.getContactByCrmId( leadSource.getId() );
        if (isBuyerLead && contact == null) {
            // lead does not exists if it is created through SF
            LOGGER.info( "Lead does not exist : {} so sending to livevox", buyerEmail );
            liveVoxService.checkAndSubmitToLiveVox( leadSource );
        } else if (isBuyerLead && !liveVoxService.isLeadSubmittedToLiveVox( leadSource )) {
            // lead was not submitted due to given reasons:
            // 1. Phone Number was not correct
            LOGGER.info( "lead was not submitted lead : {}, phone number : {} so sending to livevox",
                    leadSource.getEmail(), contact.getPhone() );
            liveVoxService.submitLeadToLiveVox( leadSource );
        }
        if (isLeadChangedFromCRM( leadSource.getLastModifiedById() )) {
            if (isBuyerLead) {
                if (isEligibleToCreateOclLead( leadSource )) {
                    // Commented as part of story OWNCORE-6869 - Disabling ALL
                    // OCL lead creation by Gravitas.
                    // createOCLLead( leadSource );
                }
                handleLeadStatusChange( leadSource );
            }
            updateLead( leadSource, isBuyerLead );
        }
        LOGGER.info( "Lead changes done : {}", buyerEmail );
    }

    /**
     * Handle lead create.
     *
     * @param leadSource
     *            the lead source
     */
    @Override
    @Transactional( propagation = REQUIRES_NEW )
    public void handleLeadCreate( final LeadSource leadSource ) {
        LOGGER.info( "Handle lead create : {}, Time is : {}", leadSource.getEmail(), LocalDateTime.now() );
        if (BUYER.getType().equalsIgnoreCase( leadSource.getRecordTypeName() )) {
            LOGGER.info( "Starting lead process : {}", leadSource.getEmail() );
            startBuyerLeadProcess( leadSource );
            if (isLeadChangedFromCRM( leadSource.getCreatedById() )) {
                LOGGER.info( "Lead create from CRM : {}", leadSource.getEmail() );
                final Boolean leadShiftTypeBuyer = isAllowedState( leadSource.getState() )
                        && isOfficeShiftHoursForLeadScore( leadSource.getState() );
                leadTask.updateLeadScore( leadSource, leadSource.getId(), leadShiftTypeBuyer );
            }
            liveVoxService.checkAndSubmitToLiveVox( leadSource );
        } else if (SELLER.getType().equalsIgnoreCase( leadSource.getRecordTypeName() )) {
            final Contact contact = contactServiceV1.getContact( leadSource.getEmail(), SELLER.getType() );
            final boolean leadHasvalidPhNo = leadService.isValidPhoneNumber( contact );
            final Map< String, Object > crmLeadRequest = new HashMap<>();
            if (!leadHasvalidPhNo) {
                contact.setStage( LeadStatus.LOST.getStatus() );
                crmLeadRequest.put( LEAD_STATUS, LeadStatus.LOST.getStatus() );
                crmLeadRequest.put( REASON_CLOSED, invalidPhonereasonClosed );
                contactServiceV1.save( contact );
                leadService.updateLead( crmLeadRequest, contact.getCrmId(), FALSE );
            }
        }
        LOGGER.info( "Lead creation done : {}", leadSource.getEmail() );
    }

    /**
     * To check if leads are interested in finance
     * and belongs to specific state
     *
     * @param request
     * @return
     */
    public boolean isMortgageLead( final LeadSource leadSource ) {
        if (leadSource.getState() != null && ( leadSource.getState().equals( notifyLeadsForFinanceInState ) )
                && StringUtils.isNotEmpty( leadSource.getFirstName() )
                && StringUtils.isNotEmpty( leadSource.getPhone() )) {
            if (OWNERS_COM_LOANS.getType().equals( leadSource.getRecordType() )
                    || leadSource.isInterestedInFinancing()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates the lead.
     *
     * @param leadSource
     *            the lead source
     * @param recordTypeName
     *            the record type name
     */
    @Override
    @Transactional( propagation = REQUIRED )
    public void createorUpdateLead( final LeadSource leadSource, final String recordTypeName ) {
        Contact contact = contactServiceV1.getContact( leadSource.getEmail(),
                getRecordType( recordTypeName ).getType() );
        final boolean afterHoursNotificationRequired = ( contact == null );
        final GenericLeadRequest request = getGenericLeadRequest( leadSource );
        contact = createOrUpdateContact( request, leadSource.getId(), contact );
        if (afterHoursNotificationRequired) {
            LOGGER.debug( "New contact " + leadSource.getEmail() + " is getting created in gravitas." );
            sendAfterHoursNotification( request, TRUE );
        }
        contactServiceV1.save( contact );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.LeadBusinessService#createLead(com.owners.
     * gravitas.dto.request.LeadRequest, boolean, boolean)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public LeadResponse createLead( final LeadRequest request, final boolean isAfterHoursNotificationRequired, final String contactStatusId ) {
        return createLead( request, isAfterHoursNotificationRequired, TRUE, contactStatusId );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.LeadBusinessService#createLead(com.owners.
     * gravitas.dto.request.LeadRequest, boolean, java.lang.Boolean, boolean)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public LeadResponse createLead( final LeadRequest request, final boolean isAfterHoursNotificationRequired,
            final Boolean allowAutoAssignment, final String contactStatusId ) {
        return createLead( request, isAfterHoursNotificationRequired, request.getLeadType(), allowAutoAssignment, contactStatusId );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.LeadBusinessService#createLead(com.owners.
     * gravitas.dto.request.LeadRequest, boolean)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public LeadResponse createLead( final OclLeadRequest request, final boolean isAfterHoursNotificationRequired ) {
        return createOclLead( request );
    }
    
    /**
     * Method would be called by the retry mechanism from LeadProcessingRetryHandler
     * @see com.owners.gravitas.business.LeadBusinessService#createOclLead(com.owners.gravitas.dto.request.GenericLeadRequest)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public LeadResponse createOclLead( final GenericLeadRequest request, final String contactStatusId) {
    	LOGGER.info( "Retrying the failed ocl lead : " + request.getAlId() );
    	final LeadSource leadSource = ownerGenericLeadRequestBuilder.convertFrom(request);
        return createOclLead( leadSource, FALSE, contactStatusId );
    }

    /**
     * Creates the Ocl lead from request.
     *
     * @param request
     *            the request
     * @return the lead response
     */
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    private LeadResponse createOclLead( final OclLeadRequest oclLeadrequest ) {
        LOGGER.info( "Creating Ocl  lead with CRM id" + oclLeadrequest.getCrmId() );
        final LeadSource leadSource = oclLeadDetailsBuilder.convertTo( oclLeadrequest );
        return createOclLead( leadSource, Boolean.FALSE, null );
    }

    /**
     * Creates the lead for lead request types ASK_QUESTION, MAKE_OFFER and
     * SCHEDULE_TOUR.
     *
     * @param request
     *            the request
     * @return the lead response
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public LeadResponse createLead( final RequestTypeLeadRequest request, final String contactStatusId ) {
        final LeadRequest leadRequest = new LeadRequest();
        BeanUtils.copyProperties( request, leadRequest );
        return createLead( leadRequest, TRUE, contactStatusId );
    }

    /**
     * Creates the unbounce lead.
     *
     * @param jsonData
     *            the json data
     * @return the lead response
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public LeadResponse createUnbounceLead( final String jsonData, final String contactStatusId ) {
        final Map< String, List< String > > unbounceLeadMap = JsonUtil.toType( jsonData, HashMap.class );
        final GenericLeadRequest genericLeadRequest = genericLeadRequestBuilder.convertTo( unbounceLeadMap );
        leadValidator.validateUnbounceLeadRequest( genericLeadRequest );
        return createLead( genericLeadRequest, TRUE, contactStatusId );
    }

    @Override
    public void enterValueIntoRedisCache( final Map< String, Object > request ) {
        liveVoxLookupService.enterValueIntoRedisCache( request.get( "champignId" ) + "",
                request.get( "sessionId" ) + "" );
    }

    /**
     * Gets the CRM lead.
     *
     * @param leadId
     *            the lead id
     * @return the CRM lead
     */
    @Override
    public CRMLeadResponse getCRMLead( final String leadId ) {
        return leadService.getLead( leadId );
    }

    /**
     * Send notification.
     *
     * @param request
     *            the request
     * @param isAfterHoursNotificationRequired
     *            the is after hours notification required
     */
    @Override
    public void sendAfterHoursNotification( final LeadRequest request,
            final boolean isAfterHoursNotificationRequired ) {
        if (isNotificationRequired( isAfterHoursNotificationRequired ) && isAllowedState( request.getState() )
                && isAfterOfficeHours()) {
            mailService.send( emailNotificationBuilder.convertTo( request ) );
            LOGGER.debug( "After office hour Email notification sent to " + request.getEmail() );
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.LeadBusinessService#convertLeadToOpportunity
     * (java.lang.String)
     */
    @Override
    public String convertLeadToOpportunity( final String leadId ) {
        return leadService.convertLeadToOpportunity( leadId );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.LeadBusinessService#
     * getLeadIdByRequestTypeAndEmail(java.lang.String, java.lang.String)
     */
    @Override
    public String getLeadIdByRequestTypeAndEmail( final String recordTypeId, final String email ) {
        String leadId = StringUtils.EMPTY;
        try {
            leadId = leadService.getLeadIdByEmailAndRecordTypeId( email, recordTypeId );
        } catch ( final ResultNotFoundException e ) {
            LOGGER.info( "Lead not found for email: " + email + " and recordTypeId: " + recordTypeId );
        }
        return leadId;
    }

    /**
     * Creates the crm lead.
     *
     * @param request
     *            the request
     * @param allowAutoAssignment
     *            the allow auto assignment
     * @return the lead response
     */
    @Override
    public LeadResponse createOclLead( final LeadSource leadSource, final Boolean allowAutoAssignment, final String contactStatusId ) 
    {
    	GenericLeadRequest newCrmLeadRequest = null;
    	try
    	{
    		LOGGER.info( "calling CRM service for creating lead for {} and Time is :{} ", leadSource.getEmail(),
                    LocalDateTime.now() );
            Contact contact = contactServiceV1.getContact( leadSource.getEmail(), RecordType.OWNERS_COM_LOANS.getType() );
            if (contact != null) 
            {
                return new LeadResponse( Status.DUPLICATE, Constants.LEAD_DUPLICATE_MESSAGE, contact.getCrmId() );
            }
            newCrmLeadRequest = ownerGenericLeadRequestBuilder.convertTo( leadSource );
            newCrmLeadRequest.setLeadType( OWNERS_COM_LOANS.getType() );
            newCrmLeadRequest.setLeadSourceUrl( leadSource.getLeadSourceUrl() );
            final CRMLeadRequest crmLeadRequest = crmLeadRequestBuilder.convertTo( newCrmLeadRequest );
            final String leadId = leadService.createLead( crmLeadRequest, allowAutoAssignment ).getId();
            contact = createOclContact( newCrmLeadRequest, leadId );
            contactServiceV1.save( contact );
            
            return createResponse( leadId, LEAD.getName(), CREATED );
    	}
        catch ( final Exception e ) {
            LOGGER.info( "Exception Occurred while creating OCL lead : {} ", e );
            try {
                // insert failed lead record only if not triggered by the retry mech
                if (contactStatusId == null) {
                    final ContactStatus contactStatus = contactStatusBuilder.convertTo( newCrmLeadRequest );
                    contactStatus.setStatusMessage( e.getMessage() );
                    contactStatus.setRequestType( Constants.NEW );
                    contactStatusService.save( contactStatus );
                }
            } catch ( final Exception retryException ) {
                LOGGER.info( "createOclLead : Error while inserting failed lead : {}", newCrmLeadRequest.getEmail(),
                        retryException );
            }
        }
        return null;
    }

    /**
     * Save contact.
     *
     * @param request
     *            the request
     * @param leadCrmId
     *            the lead crm id
     * @return the contact
     */
    private Contact createOclContact( final LeadRequest request, final String leadCrmId ) {
        Contact contact = null;
        contact = contactBuilderV1.convertTo( request, contact );
        contact.setCrmId( leadCrmId );
        return contact;
    }

    /**
     * Initiate farming process.
     *
     * @param leadSource
     *            the lead source
     */
    private void handleLeadStatusChange( final LeadSource leadSource ) {
        final String buyerEmail = leadSource.getEmail();
        final String status = leadSource.getLeadStatus();
        final List< String > leadLostReasons = new ArrayList<>();
        switch ( LeadStatus.getType( status ) ) {
            case QUALIFIED:
                signalInsideSalesFarmingProcess( leadSource, ACTIVE_BUYER, TRUE );
                LOGGER.info( "Field agent farming process started: " + buyerEmail );
                break;
            case OUTBOUND_ATTEMPT:
                LOGGER.info( "Lead is unreachable: " + buyerEmail );
                leadFollowupBusinessService.startLeadFollowupEmailProcess( leadSource );
                break;
            case LOST:
            case FORWARDED_TO_REF_EX:
                BuyerFarmType farmType = LOST_BUYER;
                if (isEligibleForReferral( leadSource )) {
                    farmType = LONG_TERM_BUYER;
                    forwardToReferralExchange( leadSource );
                } else {
                    final String reasons = StringUtils.isNotBlank( leadSource.getClosedReason() )
                            ? leadSource.getClosedReason().trim()
                            : BLANK_SPACE;
                    Collections.addAll( leadLostReasons, reasons.split( SEMI_COLON ) );
                    if (CollectionUtils.isNotEmpty( leadLostReasons )) {
                        LOGGER.info( "Lead farming started on lost lead: " + buyerEmail );
                        for ( final String reason : leadLostReasons ) {
                            if (!excludedLostReasons.contains( reason )) {
                                farmType = LONG_TERM_BUYER;
                                break;
                            }
                        }
                    }
                }
                signalInsideSalesFarmingProcess( leadSource, farmType, FALSE );
                break;
            default:
                break;
        }
    }

    /**
     * Signal inside sales farming process.
     *
     * @param leadSource
     *            the lead source
     * @param farmType
     *            the farm type
     * @param isConverted
     *            the is converted
     */
    private void signalInsideSalesFarmingProcess( final LeadSource leadSource, final BuyerFarmType farmType,
            final Boolean isConverted ) {
        if (isFarmingLead( leadSource.getEmail() )) {
            final Map< String, Object > params = new HashMap<>();
            params.put( "buyerFarmType", farmType.name() );
            if (isConverted) {
                params.put( "leadStatus", LeadStatus.QUALIFIED.name() );
            }
            final com.owners.gravitas.domain.entity.Process process = processBusinessService
                    .deActivateAndSignal( leadSource.getEmail(), LEAD_MANAGEMENT_PROCESS, params );
            if (null != process && !isConverted) {
                buyerFarmingBusinessService.updateFarmingStatus( leadSource.getId(), farmType, FALSE );
                LOGGER.info( "Lead farming started for buyer: " + leadSource.getEmail() );
            }
        }
    }

    /**
     * Checks if is farming lead.
     *
     * @param email
     *            the email
     * @return true, if is farming lead
     */
    private boolean isFarmingLead( final String email ) {
        return buyerFarmingConfig.isBuyerFarmingEnabled() && buyerFarmingConfig.isBuyerAutoRegistrationEnabled()
                && buyerFarmingBusinessService.isBuyerAutoRegistrationEmail( email )
                && buyerFarmingConfig.isBuyerInsideSalesFarmingEnabled();
    }

    /**
     * Gets the generic lead request.
     *
     * @param leadSource
     *            the lead source
     * @return the generic lead request
     */
    private GenericLeadRequest getGenericLeadRequest( final LeadSource leadSource ) {
        final GenericLeadRequest request = ocleadRequestBuilder.convertTo( leadSource );
        request.setLeadSourceUrl( leadSource.getLeadSourceUrl() );
        request.setLeadType( leadSource.getRecordTypeName() );
        request.setRequestType( leadSource.getRequestType() );
        request.setFarmingBuyerAction( leadSource.getFarmingBuyerAction() );
        return request;
    }

    /**
     * Save contact.
     *
     * @param request
     *            the request
     * @param leadCrmId
     *            the lead crm id
     * @param contact
     *            the contact
     * @return the contact
     */
    private Contact createOrUpdateContact( final LeadRequest request, final String leadCrmId, final Contact contact ) {
        final Contact updatedContact = contactBuilderV1.convertTo( request, contact );
        updatedContact.setCrmId( leadCrmId );
        updatedContact.setStage( request.getLeadStatus() );
        return updatedContact;
    }

    /**
     * Start buyer lead process.
     *
     * @param leadSource
     *            the lead source
     */
    private void startBuyerLeadProcess( final LeadSource leadSource ) {
        if (buyerFarmingConfig.isBuyerFarmingEnabled()) {
            final Map< String, Object > params = new HashMap<>();
            params.put( "buyerFarmType", ACTIVE_BUYER.name() );
            params.put( "leadStatus", NEW.name() );
            params.put( "processWebActivity", false );
            processBusinessService.deActivateAndSignal( leadSource.getEmail(), INSIDE_SALES_FARMING_PROCESS, params );
            startBuyerLeadManagementProcess( leadSource );
        }
    }

    /**
     * Start buyer lead management process.
     *
     * @param leadSource
     *            the lead source
     */
    private void startBuyerLeadManagementProcess( final LeadSource leadSource ) {
        final Map< String, Object > initParams = new HashMap<>();
        initParams.put( Constants.LEAD, leadSource );
        initParams.put( Constants.BUYER_FARMING_DELAY, buyerFarmingDelay );
        final List<com.owners.gravitas.domain.entity.Process> processList = processBusinessService
                .getActiveProcess( leadSource.getEmail() );
        if (org.springframework.util.CollectionUtils.isEmpty(processList) && !leadSource.getLeadStatus().equals( QUALIFIED.getStatus() )) {
            runtimeService.startProcessInstanceByKey( "buyerFarmingProcess", initParams );
        }
    }

    /**
     * Checks if is lead from crm.
     *
     * @param createdOrModifiedById
     *            the created or modified by id
     * @return true, if is lead from crm
     */
    private boolean isLeadChangedFromCRM( final String createdOrModifiedById ) {
        return StringUtils.isNotBlank( createdOrModifiedById )
                && !createdOrModifiedById.startsWith( config.getSfApiUserId() );
    }

    /**
     * Checks if is eligible to create ocl lead.
     *
     * @param leadSource
     *            the lead source
     * @return the boolean
     */
    private Boolean isEligibleToCreateOclLead( final LeadSource leadSource ) {
        return ( isOCLBuyerLeadStatus( leadSource.getLeadStatus() ) && leadSource.isInterestedInFinancing()
                && isOCLBuyerLeadState( leadSource.getState() ) );
    }

    /**
     * Creates the lead from request.
     *
     * @param request
     *            the request
     * @param isAfterHoursNotificationRequired
     *            the is after hours notification required
     * @param leadType
     *            the lead type
     * @param allowAutoAssignment
     *            the allow auto assignment
     * @return the lead response
     */
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    private LeadResponse createLead( final LeadRequest request, final boolean isAfterHoursNotificationRequired,
            final String leadType, final Boolean allowAutoAssignment, final String contactStatusId ) {
        request.setLeadType( leadType );
        LOGGER.info( "Creating lead :{}", request.getEmail() );
        LeadResponse response = null;
        try {
            if (coShoppingConfig.isCoshoppingLeadPushEnabled()
                    && coShoppingService.isLeadEligibleForCoShoppingPush( request )) {
                final CoShoppingLeadRequest coShoppingLeadRequest = coShoppingLeadBuilder.convertTo( request );
                final Resource resource = coShoppingService.postLeadDetails( coShoppingLeadRequest );
                if (null != resource) {
                    request.setCoShoppingId( resource.getId() );
                    LOGGER.info( "Lead info saved to co-shopping with id {}", resource.getId() );
                }
            }
        } catch(final Exception e){
            LOGGER.error("Error while submitting lead data to Coshopping : {}", request.getEmail(), e);
        }

        if (!leadType.equals( RecordType.OWNERS_COM_LOANS.name() )) {
            // Db & Firebase Insertion for agent task
            response = deDuplicateAgentOpportunity( request );
        }
        final CRMResponse deDupResponse = getDeDuplicatedLeadByRecordType( request.getEmail(),
                RecordType.valueOf( leadType ) );
        String lockName = null;
        Contact contact = null;
        try {
            if (deDupResponse.getRecords().isEmpty()) {
                // inserting data into sf
                response = createCrmLead( request, allowAutoAssignment );
                lockName = response.getId();
                // getting existing lost contact
                syncCacheLock.acquireLockBlocking(lockName);
                contact = createContact( request, response.getId() );
                if (leadOpportunityBusinessConfig.isContactDatabaseConfig()) {
                    LOGGER.info( "Lead " + request.getEmail() + CREATED + " Time is : " + LocalDateTime.now() );
                    contactServiceV1.save( contact );
                }
                sendAfterHoursNotification( request, isAfterHoursNotificationRequired );
            } else {
                // de duping logic, saves to db, sf etc
                final Map< String, Object > leadsDetails = deDupResponse.getRecords().get( 0 );
                lockName = convertObjectToString( leadsDetails.get( CRMConstants.ID ) );
                syncCacheLock.acquireLockBlocking(lockName);
                final LeadContactWrapper leadContactWrapper = processLead( request, deDupResponse );
                contact = leadContactWrapper.getContact();
                response = leadContactWrapper.getLeadResponse();
            }
            final ContactLog contactLog = contactLogBuilder.convertTo( request );
            contactLogService.save( contactLog );
            try {
                updateLeadScore( deDupResponse, request, contact, request.getState(),
                        isAfterHoursNotificationRequired );
            } catch ( final Exception ex ) {
                LOGGER.error( "Error while updating lead scrore : {}", response.getId(), ex );
            }
            
        } catch ( final Exception ex ) 
        {
            LOGGER.error( "Error while processing lead information : {}", request.getEmail(), ex );
			try 
			{
				// insert failed lead record only if not triggered by the retry mech
				if(contactStatusId == null)
				{
					final ContactStatus contactStatus = contactStatusBuilder.convertTo(request);
					contactStatus.setStatusMessage(ex.getMessage());
					contactStatus.setRequestType(deDupResponse.getRecords().isEmpty() ? Constants.NEW : Constants.DEDUPED);
					contactStatusService.save(contactStatus);
				}
			}
			catch(final Exception e) 
			{
            	 LOGGER.error( "Error while inserting failed lead : {}", request.getEmail(), e );
            }
        } 
        finally 
        {
            if (StringUtils.isNotBlank(lockName)) 
            {
                // Doing this as transaction gets committed post method completion
                syncCacheLock.releaseLockWithDelay(lockName);
            }
        }
        LOGGER.info( "Create Lead Successfully done : {}", JsonUtil.toJson( request ) );
        return response;
    }

    /**
     * Update lead score.
     *
     * @param deDupResponse
     *            the de dup response
     * @param request
     *            the request
     * @param crmId
     *            the crm id
     * @param state
     *            the state
     * @param afterHoursFlag
     *            the after hours flag
     */
    private void updateLeadScore( final CRMResponse deDupResponse, final LeadRequest request, final Contact contact,
            final String state, final boolean afterHoursFlag ) {
        if (request.getLeadType().equals( BUYER.getType() )) {
            boolean isOpportunity = false;
            int dedupCnt = 0;
            if (!deDupResponse.getRecords().isEmpty()) {
                final Map< String, Object > leadsDetails = deDupResponse.getRecords().get( 0 );
                if (!leadsDetails.isEmpty()) {
                    dedupCnt = getDedupCounter( leadsDetails );
                    if (null != leadsDetails.get( LEAD_CONVERTED_OPPORTUNITY_ID )) {
                        isOpportunity = true;
                    }
                }
            }
            LOGGER.info( "dedup count is: {}", dedupCnt );
            if (!isOpportunity) {
                final Boolean leadShiftTypeBuyer = isAllowedState( state ) && isOfficeShiftHoursForLeadScore( state );
                leadTask.updateLeadScore( request, contact, dedupCnt, leadShiftTypeBuyer );
            }
        }
    }

    /**
     * De duplicate opportunity.
     *
     * @param request
     *            the request
     * @return the lead response
     */
    private LeadResponse deDuplicateAgentOpportunity( final LeadRequest request ) {
        LOGGER.info( "Checking for de dup of email" + request.getEmail() + " Time is : " + LocalDateTime.now() );
        LeadResponse response = null;
        if (BUYER.getType().equalsIgnoreCase( request.getLeadType() )
                || SELLER.getType().equalsIgnoreCase( request.getLeadType() )) {
            final Search search = deDuplicationService.getSearchByContactEmail( request.getEmail() );
            if (null != search) {
                opportunityBusinessService.processBuyerRequest( request, search.getAgentId(),
                        search.getOpportunityId() );
                response = createResponse( search.getOpportunityId(), OPPORTUNITY.getName(), UPDATED );
            }
        }
        return response;
    }

    /**
     * Creates the OCL lead.
     *
     * @param leadSource
     *            the lead source
     */
    // TODO move to service layer
    private void createOCLLead( final LeadSource leadSource ) {
        LOGGER.info( "Creating OCL lead " + leadSource.getEmail() + " Time is : " + LocalDateTime.now() );
        final boolean isOpportunityExists = opportunityServiceImpl
                .isOpportunityExistsForRecordTypeAndEmail( leadSource.getEmail(), OWNERS_COM_LOANS );
        if (!isOpportunityExists) {
            final CRMResponse leadDeDupRes = getDeDuplicatedLeadByRecordType( leadSource.getEmail(), OWNERS_COM_LOANS );
            if (leadDeDupRes.getRecords().isEmpty()) {
                final GenericLeadRequest leadRequest = ownerGenericLeadRequestBuilder.convertTo( leadSource );
                leadValidator.validateOCLLeadRequest( leadRequest );
                leadRequest.setLeadType( OWNERS_COM_LOANS.name() );
                createCrmLead( leadRequest, TRUE );
            }
        }
    }

    /**
     * Forward to referral exchange.
     *
     * @param leadSource
     *            the lead source
     */
    private void forwardToReferralExchange( final LeadSource leadSource ) {
        LOGGER.info( "Lead is eligible to forward to referral exchange:" + leadSource.getEmail() );
        final ReferralExchangeRequest newRequest = referralExchangeRequestBuilder.convertTo( leadSource );
        final Map< String, Object > updateLeadMap = new HashMap<>();
        boolean referred = TRUE;
        String referralFailureDetails = StringUtils.EMPTY;
        try {
            referralExchangeService.forwardRequest( newRequest );
            final List< String > emailList = new ArrayList< String >();
            emailList.add( leadSource.getEmail() );
            sendReferralExchangeNotification( getName( leadSource.getFirstName(), leadSource.getLastName() ),
                    emailList );
            LOGGER.info( "Lead referring to referral exchange successful: " + leadSource.getEmail() );
        } catch ( final HttpClientErrorException hce ) {
            LOGGER.info( "Problem referring lead to referral exchange: " + hce.getResponseBodyAsString(), hce );
            referred = FALSE;
            referralFailureDetails = hce.getResponseBodyAsString();
        }
        updateLeadMap.put( LEAD_REFERRED_DATE, new DateTime().toDate() );
        updateLeadMap.put( REFERRED_SUCCESSFULLY_FLAG, referred );
        updateLeadMap.put( REFERRAL_FAILURE_DETAILS, referralFailureDetails );
        leadService.updateLead( updateLeadMap, leadSource.getId(), FALSE );
    }

    /**
     * Creates the crm lead.
     *
     * @param request
     *            the request
     * @param allowAutoAssignment
     *            the allow auto assignment
     * @return the lead response
     */
    // TODO move to service layer
    private LeadResponse createCrmLead( final LeadRequest request, final Boolean allowAutoAssignment ) {
        LOGGER.info( "calling CRM service for creating lead for " + request.getEmail() + "Time is : "
                + LocalDateTime.now() );
        request.setLeadType( RecordType.getRecordTypeType( request.getLeadType() ) );
        final CRMLeadRequest newCrmLeadRequest = crmLeadRequestBuilder.convertTo( request );
        final String leadId = leadService.createLead( newCrmLeadRequest, allowAutoAssignment ).getId();
        return createResponse( leadId, LEAD.getName(), CREATED );
    }

    /**
     * Creates or update lead/opportunity, by retrieving the existing
     * lead/opportunity from lead. Based on status value as below status equals
     * 'qualified'=> updates opportunity for this Lead. status equals 'lost' =>
     * Create new Lead and with lead status to 'New'. For any others status =>
     * Updates existing Lead.
     *
     * @param newLeadrequest
     *            the new leadrequest
     * @param deDupResponse
     *            the de dup response
     * @return the lead response
     */
    private LeadContactWrapper processLead( final LeadRequest newLeadrequest, final CRMResponse deDupResponse ) {
        newLeadrequest.setLeadType( RecordType.getRecordTypeType( newLeadrequest.getLeadType() ) );
        final Map< String, Object > leadsDetails = deDupResponse.getRecords().get( 0 );
        String leadId = convertObjectToString( leadsDetails.get( CRMConstants.ID ) );
        CRMLeadResponse crmLeadResponse = leadService.getLead( leadId );
        final String leadStatus = crmLeadResponse.getLeadStatus();
        String objType = LEAD.getName();
        CRMLeadRequest newCrmLeadRequest = null;
        String operation = null;
        Contact contact = null;
        final String convertedOpportunityId = convertObjectToString(
                leadsDetails.get( LEAD_CONVERTED_OPPORTUNITY_ID ) );
        boolean startFarming = FALSE;

        final String existingDeDupLeadSource = ( String ) leadsDetails.get( CRMConstants.LEAD_SOURCE );
        final String newLeadSource = newLeadrequest.getSource();
        if (!StringUtils.isEmpty( existingDeDupLeadSource )
                && !StringUtils.isEmpty( zillowHotlineJmxConfig.getZillowLeadSourceStr() )
                && !StringUtils.isEmpty( zillowHotlineJmxConfig.getInboundCallsInsideSalesLeadSourceStr() )
                && existingDeDupLeadSource.toLowerCase()
                        .equals( zillowHotlineJmxConfig.getZillowLeadSourceStr().toLowerCase() )
                && !StringUtils.isEmpty( newLeadSource ) && newLeadSource.toLowerCase()
                        .startsWith( zillowHotlineJmxConfig.getInboundCallsInsideSalesLeadSourceStr().toLowerCase() )) {
            /*--
             * If during dedupe, we find an existing lead having source "Zillow Hotline", then still take the new updated lead, 
             * however change the new lead source to "Zillow Hotline" if the new lead source is "Owners.com Hotline". 
             * We need to do this so that the new changes are incorporated and at the same time the source is retained as "Zillow Hotline"
             *  for a lead which was already marked having source "Zillow Hotline".
             */
            LOGGER.error(
                    "INFO Zillow Lead Source Correction - Dedupe Criteria MATCHED- existingDeDupLeadSource : {}, zillowLeadSourceStr : {}, inboundCallsInsideSalesLeadSourceStr :{}",
                    existingDeDupLeadSource, zillowHotlineJmxConfig.getZillowLeadSourceStr(),
                    zillowHotlineJmxConfig.getInboundCallsInsideSalesLeadSourceStr() );
            newLeadrequest.setSource( zillowHotlineJmxConfig.getZillowLeadSourceStr() );
        } else {
            LOGGER.info(
                    "Zillow Lead Source Correction - Dedupe Criteria NOT MATCHED- existingDeDupLeadSource : {}, zillowLeadSourceStr : {}, inboundCallsInsideSalesLeadSourceStr :{}",
                    existingDeDupLeadSource, zillowHotlineJmxConfig.getZillowLeadSourceStr(),
                    zillowHotlineJmxConfig.getInboundCallsInsideSalesLeadSourceStr() );
        }

        switch ( LeadStatus.getType( leadStatus ) ) {
            case QUALIFIED:
                LOGGER.info( "lead is qualified,firebase is already updated. Lets update on CRM!!" );
                leadId = opportunityBusinessService.updateOpportunity( newLeadrequest, convertedOpportunityId ).getId();
                objType = OPPORTUNITY.getName();
                contact = createContact( newLeadrequest, leadId );
                operation = UPDATED;
                break;
            case LOST:
                LOGGER.info( "lead is lost, creating lead!!" );
                leadId = createCrmLead( newLeadrequest, FALSE ).getId();
                contact = createContact( newLeadrequest, leadId );
                operation = CREATED;
                startFarming = TRUE;
                break;
            default:
                LOGGER.info( "lead is existing, updating lead!!" );
                newCrmLeadRequest = crmLeadRequestBuilder.convertTo( newLeadrequest,
                        convertToCrmLeadRequest( crmLeadResponse ) );
                LOGGER.debug( "calling CRM service for update lead" );
                leadService.updateLead( newCrmLeadRequest, leadId, FALSE );
                crmLeadResponse = leadService.getLead( leadId );
                contact = createContact( newLeadrequest, leadId );
                contact.setStage( leadStatus );
                operation = UPDATED;
                break;
        }
        if (leadOpportunityBusinessConfig.isContactDatabaseConfig()) {
            LOGGER.info( objType + " " + newLeadrequest.getEmail() + operation + " Time is : " + LocalDateTime.now() );
            contactServiceV1.save( contact );
        }
        if (newLeadrequest.getLeadType().equals( BUYER.getType() ) && StringUtils.isBlank( convertedOpportunityId )
                && !startFarming) {
            final LeadSource leadSource = new LeadSource();
            BeanUtils.copyProperties( crmLeadResponse, leadSource );
            startBuyerLeadProcess( leadSource );
        }
        final LeadContactWrapper leadContactWrapper = new LeadContactWrapper();
        final LeadResponse leadResponse = createResponse( leadId, objType, operation );
        leadContactWrapper.setLeadResponse(leadResponse);
        leadContactWrapper.setContact(contact);
        return leadContactWrapper;
    }

    /**
     * Save contact.
     *
     * @param request
     *            the request
     * @param leadCrmId
     *            the lead crm id
     * @return the contact
     */
    private Contact createContact( final LeadRequest request, final String leadCrmId ) {
    	Contact contact = contactServiceV1.getContact( request.getEmail(), request.getLeadType() );
        contact = contactBuilderV1.convertTo( request, contact );
        contact.setCrmId( leadCrmId );
        return contact;
    }

    /**
     * Gets the record type name.
     *
     * @param recordTypeId
     *            the record type id
     * @return the record type name
     */
    private String getRecordTypeName( final String recordTypeId ) {
        String recordTypeName = null;
        try {
            final QueryParams params = new QueryParams();
            params.add( ID, recordTypeId );
            recordTypeName = convertObjectToString(
                    crmQueryService.findOne( GET_RECORD_TYPE_NAME, params ).get( "Name" ) );
        } catch ( final ResultNotFoundException re ) {
            LOGGER.info( "Record Type Id does not exists. " + recordTypeId, re );
        }
        return recordTypeName;
    }

    /**
     * Checks if is owner state.
     *
     * @param leadState
     *            the lead state
     * @return the boolean
     */
    private Boolean isOwnerState( final String leadState ) {
        return StringUtils.isNotBlank( leadState ) && !config.getOwnerStates().contains( leadState );
    }

    /**
     * Checks if is eligible for referral. The referral exchange leads were
     * supposed to go per below logic: If Lead was not already referred AND lead
     * "DO NOT CALL" was false AND lead "Email Opt Out" was false AND (If lead
     * was created in a "Not Covered" State - send to referral exchange) OR (If
     * lead status was marked as "Out of Coverage" - send to referral exchange)
     *
     * @param leadSource
     *            the lead source
     * @return true, if is eligible for referral
     */
    private boolean isEligibleForReferral( final LeadSource leadSource ) {
        return config.isLeadReferralEnabled() && !leadSource.isReferred() && !leadSource.isDoNotCall()
                && !leadSource.isDoNotEmail()
                && ( ( ( LOST.getStatus().equalsIgnoreCase( leadSource.getLeadStatus() )
                        && StringUtils.contains( leadSource.getClosedReason(), OUT_OF_COVERAGE_AREA.getReason() ) )
                        && isExcludedReferralState( leadSource.getState() ) )
                        || isOwnerState( leadSource.getState() )
                        || ( FORWARDED_TO_REF_EX.getStatus().equalsIgnoreCase( leadSource.getLeadStatus() )
                                && isExcludedReferralState( leadSource.getState() ) ) )
                && isExcludedLeadSource( leadSource.getSource() );
    }

    /**
     * Checks if is excluded referral state.
     *
     * @param leadState
     *            the lead state
     * @return true, if is excluded referral state
     */
    private boolean isExcludedReferralState( final String leadState ) {
        return StringUtils.isNotBlank( leadState ) && !config.getReferralExcludedStates().contains( leadState );
    }

    /**
     * Checks if is excluded lead source.
     *
     * @param leadSource
     *            the lead source
     * @return true, if is excluded lead source
     */
    private boolean isExcludedLeadSource( final String leadSource ) {
        return referralExchangeExcludedLeadSources.stream()
                .noneMatch( source -> leadSource.toLowerCase().contains( source ) );
    }

    /**
     * Checks if is OCL buyer lead state.
     *
     * @param leadState
     *            the lead state
     * @return true, if is OCL buyer lead state
     */
    private boolean isOCLBuyerLeadState( final String leadState ) {
        return oclLeadBuyerStatesList.contains( leadState.toLowerCase() );
    }

    /**
     * Checks if is OCL buyer lead status.
     *
     * @param leadStatus
     *            the lead status
     * @return true, if is OCL buyer lead status
     */
    private boolean isOCLBuyerLeadStatus( final String leadStatus ) {
        return oclLeadBuyerStatusList.contains( leadStatus.toLowerCase() );
    }

    /**
     * Convert to crm lead request.
     *
     * @param crmLeadResponse
     *            the crm lead response
     * @return the CRM lead request
     */
    private CRMLeadRequest convertToCrmLeadRequest( final CRMLeadResponse crmLeadResponse ) {
        final CRMLeadRequest crmLeadRequest = new CRMLeadRequest();
        BeanUtils.copyProperties( crmLeadResponse, crmLeadRequest );
        return crmLeadRequest;
    }

    /**
     * Gets the lead response.
     *
     * @param leadId
     *            the lead id
     * @param objType
     *            the obj type
     * @param operation
     *            the operation
     * @return the lead response
     */
    private LeadResponse createResponse( final String leadId, final String objType, final String operation ) {
        final String responseText = String.format( "%s %s successfully", objType, operation );
        LOGGER.info( responseText + "  with Id " + leadId + " Time is : " + LocalDateTime.now() );
        return new LeadResponse( Status.SUCCESS, responseText, leadId );
    }

    /**
     * Checks if is notification required.
     *
     * @param isAfterHoursNotificationRequired
     *            the is after hours notification required
     * @return true, if is notification required
     */
    private boolean isNotificationRequired( final boolean isAfterHoursNotificationRequired ) {
        return isAfterHoursNotificationRequired && config.isOfficeAfterHourEnabled();
    }

    /**
     * Checks if is allowed state.
     *
     * @param leadState
     *            the lead state
     * @return true, if is allowed state
     */
    private boolean isAllowedState( final String leadState ) {
        boolean isAllowed = false;
        if (StringUtils.isNotEmpty( leadState ) && config.getAfterHoursStates().contains( leadState )) {
            isAllowed = TRUE;
        }
        return isAllowed;
    }

    /**
     * Gets the de duplicated lead by record type.
     *
     * @param email
     *            the email
     * @param recordType
     *            the record type
     * @return the CRM response
     */
    private CRMResponse getDeDuplicatedLeadByRecordType( final String email, final RecordType recordType ) {
        final CRMResponse crmResponse = deDuplicationService.getDeDuplicatedLead( email, recordType );
        LOGGER.debug(
                crmResponse.getRecords().size() + " duplicate lead(s) found for record type " + recordType.getType() );
        return crmResponse;
    }

    /**
     * Checks if is office hours now.
     *
     * @return true, if is office hours now
     */
    private boolean isAfterOfficeHours() {
        final DateTimeZone timeZone = DateTimeZone.forID( config.getTimeZoneId() );
        final LocalDateTime ofcAfterHourStart = getLocalDAteTime( timeZone, config.getOfficeAfterHourStart(),
                config.getOfficeAfterMinsStart() );
        LocalDateTime ofcAfterHourEnd = getLocalDAteTime( timeZone, config.getOfficeAfterHourEnd(),
                config.getOfficeAfterMinsEnd() );

        LocalDateTime now = new LocalDateTime( timeZone );

        if (ofcAfterHourStart.isAfter( ofcAfterHourEnd )) {
            ofcAfterHourEnd = ofcAfterHourEnd.plusDays( Integer.valueOf( 1 ) );
            if (ofcAfterHourStart.isAfter( now )) {
                now = now.plusDays( Integer.valueOf( 1 ) );
            }
        }
        LOGGER.debug( "Afterhour start time: " + ofcAfterHourStart + " " + "Afterhour end time: " + ofcAfterHourEnd
                + "For time zone: " + timeZone.getID() );
        return now.isAfter( ofcAfterHourStart ) && now.isBefore( ofcAfterHourEnd );
    }

    /**
     * Checks if is after office hours for lead score.
     *
     * @param state
     *            the state
     * @return true, if is after office hours for lead score
     */
    private boolean isOfficeShiftHoursForLeadScore( final String state ) {
        final DateTimeZone timeZone = DateTimeZone.forID( config.getTimeZoneId() );
        // default hours are taken from after hours notification.

        int shiftHoursStart = 0;
        int shiftHoursEnd = 0;

        if (config.getShiftHoursStates1().contains( state )) {
            shiftHoursStart = config.getShiftHourStartState1();
            shiftHoursEnd = config.getShiftHourEndState1();
        } else if (config.getShiftHoursStates2().contains( state )) {
            shiftHoursStart = config.getShiftHourStartState2();
            shiftHoursEnd = config.getShiftHourEndState2();
        } else if (config.getShiftHoursStates3().contains( state )) {
            shiftHoursStart = config.getShiftHourStartState3();
            shiftHoursEnd = config.getShiftHourEndState3();
        }

        final LocalDateTime ofcAfterHourStart = getLocalDAteTime( timeZone, shiftHoursStart, 0 );
        final LocalDateTime ofcAfterHourEnd = getLocalDAteTime( timeZone, shiftHoursEnd, 0 );
        final LocalDateTime now = new LocalDateTime( timeZone );

        LOGGER.debug( "Shift hours start time: " + ofcAfterHourStart + " " + "Shift hours end time: " + ofcAfterHourEnd
                + "For time zone: " + timeZone.getID() );
        return now.isAfter( ofcAfterHourStart ) && now.isBefore( ofcAfterHourEnd );
    }

    /**
     * Gets the local d ate time.
     *
     * @param timeZone
     *            the time zone
     * @param hours
     *            the hours
     * @param mins
     *            the mins
     * @return the local d ate time
     */
    private LocalDateTime getLocalDAteTime( final DateTimeZone timeZone, final int hours, final int mins ) {
        return new LocalDateTime( timeZone ).withHourOfDay( hours ).withMinuteOfHour( mins ).withSecondOfMinute( 0 );
    }

    /**
     * Send successful referral exchange notification.
     *
     * @param fullName
     *            the full name
     * @param emailList
     *            the email list
     */
    private void sendReferralExchangeNotification( final String fullName, final List< String > emailList ) {
        if (config.isReferralEmailNotificationEnabled()) {
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
            name = firstName + BLANK_SPACE + lastName;
        }
        return name;
    }

    /**
     * Gets the dedup counter.
     *
     * @param leadMap
     *            the lead map
     * @return the dedup counter
     */
    private Integer getDedupCounter( final Map< String, Object > leadMap ) {
        // if LEAD_DEDUP_COUNT is null, setting the default value as 1
        if (null == leadMap.get( LEAD_DEDUP_COUNT )) {
            LOGGER.error( "LEAD_DEDUP_COUNT is null, Hence setting the default value as 1" );
            leadMap.put( LEAD_DEDUP_COUNT, 1 );
        }
        return Double.valueOf( String.valueOf( leadMap.get( LEAD_DEDUP_COUNT ) ) ).intValue() + 1;
    }

    /**
     * Update lead.
     *
     * @param leadSource
     *            the lead source
     * @param isBuyerLead
     *            the is buyer lead
     */
    private void updateLead( final LeadSource leadSource, final boolean isBuyerLead ) {
        Contact contact = contactServiceV1.getContactByCrmId( leadSource.getId() );
        if (contact == null && isBuyerLead) {
            startBuyerLeadManagementProcess( leadSource );
        }
        final GenericLeadRequest request = getGenericLeadRequest( leadSource );
        contact = createOrUpdateContact( request, leadSource.getId(), contact );

        if (QUALIFIED.equals( LeadStatus.getType( leadSource.getLeadStatus() ) )) {
            contact = updateLeadToOpportunity( contact, leadSource.getConvertedOpportunityId() );
        }
        contactServiceV1.save( contact );

    }

    /**
     * Update lead to opportunity.
     *
     * @param opportunityId
     *            the opportunity id
     */
    private Contact updateLeadToOpportunity( Contact contact, final String opportunityId ) {
        LOGGER.info( "Convert lead to opportunity in gravitas system " + opportunityId );
        if (StringUtils.isNotBlank( opportunityId )) {
            final OpportunitySource opportunitySource = opportunityBusinessService.getOpportunity( opportunityId );
            final Opportunity opportunity = opportunityBuilder.convertTo( opportunitySource );
            final Map< String, Object > updateParams = null;
            contact = entityOpportunityBuilderV1.convertFrom( contact, opportunity, null, updateParams );
            LOGGER.info( "Lead " + contact.getId() + " updated to opportunity. Lead " + contact.getCrmId() );
        }
        return contact;
    }

    /**
     * Method to convert the date to MM/dd/yyyy format by eliminating time part
     *
     * @param dt
     * @return
     */
    private static String getFormattedDate( final DateTime dt ) {
        String dateStr = null;
        if (null != dt) {
            final DateTimeFormatter fmt = DateTimeFormat.forPattern( "MM/dd/yyyy" );
            dateStr = fmt.print( dt );
        }
        return dateStr;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.LeadBusinessService#getAvailableLeads(com.
     * owners.gravitas.dto.request.LeadDetailsRequest)
     */
    @Override
    public LeadDetailsList getAvailableLeads( final LeadDetailsRequest leadDetailsRequest ) {
        int page = 0;
        if (leadDetailsRequest.getPage() != 0) {
            page = leadDetailsRequest.getPage() - 1;
        }

        final Page< Contact > objectsList = contactServiceV1.getAllPublicLeads( page, leadDetailsRequest.getSize(),
                leadDetailsRequest.getDirection(), leadDetailsRequest.getProperty() );

        return buildResponseForAllLeads( objectsList );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.LeadBusinessService#getAllMyLeads(com.owners
     * .gravitas.dto.request.LeadDetailsRequest)
     */
    @Override
    public LeadDetailsList getAllMyLeads( final LeadDetailsRequest leadDetailsRequest ) {
        final String emailId = gravitasWebUtil.getAppUserEmail();
        int page = 0;
        if (leadDetailsRequest.getPage() != 0) {
            page = leadDetailsRequest.getPage() - 1;
        }
        final Page< Contact > objectsList = contactServiceV1.getAllMyLeads( page, leadDetailsRequest.getSize(),
                leadDetailsRequest.getDirection(), leadDetailsRequest.getProperty(), emailId, leadDetailsRequest.getType() );
        return buildResponseForAllLeads( objectsList );
    }

    /**
     * Method to build the response of lead details for both public leads &
     * claimed leads
     * 
     * @param objectsList
     * @return
     */
    private LeadDetailsList buildResponseForAllLeads( final Page< Contact > objectsList ) {
        if (null == objectsList) {
            return null;
        }

        List< Object[] > objectArrayList = Collections.emptyList();
        final Set< String > crmIdList = extractAllCrmIds( objectsList );
        if (CollectionUtils.isNotEmpty( crmIdList )) {
            // get the additional lead attributes from the gr_contact_attr table
            objectArrayList = contactServiceV1.getLeadAttributesForCrmId( crmIdList );
        }

        final List< LeadDetails > leadsList = new ArrayList<>();
        for ( final Contact objArray : objectsList ) {

            final LeadDetails leadDetails = new LeadDetails();
            leadDetails.setState( objArray.getState() );
            leadDetails.setScore( objArray.getBuyerLeadScore() );
            leadDetails.setCrmId( objArray.getCrmId() );
            String name = "";
            if (objArray.getFirstName() != null && objArray.getFirstName().trim().length() > 0) {
                name = name + objArray.getFirstName();
            }
            if (objArray.getLastName() != null && objArray.getLastName().trim().length() > 0) {
                name = name + " " + objArray.getLastName();
            }
            leadDetails.setLeadName( name );
            leadDetails.setEmail( objArray.getEmail() );
            leadDetails.setPhone( objArray.getPhone() );
            leadDetails.setCreatedDate( getFormattedDate( objArray.getCreatedDate() ) );
            // populate additional lead attributes for each of the crm Id in the
            // leadDetails object
            setLeadAttributes( objectArrayList, leadDetails );
            leadsList.add( leadDetails );

        }
        final LeadDetailsList leadDetailsList = new LeadDetailsList();
        leadDetailsList.setLeadDetailsList( leadsList );
        leadDetailsList.setTotalElements( objectsList.getTotalElements() );
        leadDetailsList.setTotalPages( objectsList.getTotalPages() );
        leadDetailsList.setNumberOfElements( objectsList.getNumberOfElements() );
        leadDetailsList.setPageNumber( objectsList.getNumber() + 1L );

        return leadDetailsList;
    }

    /**
     * Method to extract all the CRM id into a set
     * 
     * @param objectsList
     * @return set containing list of CRM id
     */
    private Set< String > extractAllCrmIds( final Page< Contact > objectsList ) {
        final Set< String > crmIdSet = new HashSet<>();
        objectsList.forEach( contact -> crmIdSet.add( contact.getCrmId() ) );
        return crmIdSet;
    }

    /**
     * Sets the additional attributes from gr_contact_attr table for given crm
     * id
     * 
     * @param objectArrayList
     * @param leadDetails
     */
    private void setLeadAttributes( final List< Object[] > objectArrayList, final LeadDetails leadDetails ) {
        for ( final Object[] objectArray : objectArrayList ) {
            if (null != objectArray && leadDetails.getCrmId().equals( objectArray[2] )) {
                final String attributeName = getStringValue( objectArray[0] );
                final String attributeValue = getStringValue( objectArray[1] );
                switch ( attributeName ) {

                    case "additionalPropertyData":
                        leadDetails.setAdditionalPropertyData( attributeValue );
                        break;
                    case "alId":
                        leadDetails.setAlId( attributeValue );
                        break;
                    case "buyerLeadLabel":
                        leadDetails.setBuyerLeadLabel( attributeValue );
                        break;
                    case "buyerLeadQuality":
                        leadDetails.setBuyerLeadQuality( attributeValue );
                        break;
                    case "buyerReadinessTimeline":
                        leadDetails.setBuyerReadinessTimeline( attributeValue );
                        break;
                    case "comments":
                        leadDetails.setComments( attributeValue );
                        break;
                    case "downPayment":
                        leadDetails.setDownPayment( attributeValue );
                        break;
                    case "earnestMoneyDeposit":
                        leadDetails.setEarnestMoneyDeposit( attributeValue );
                        break;
                    case "farmingBuyerActions":
                        leadDetails.setFarmingBuyerActions( attributeValue );
                        break;
                    case "farmingfailureCode":
                        leadDetails.setFarmingfailureCode( attributeValue );
                        break;
                    case "farmingGroup":
                        leadDetails.setFarmingGroup( attributeValue );
                        break;
                    case "farmingStatus":
                        leadDetails.setFarmingStatus( attributeValue );
                        break;
                    case "farmingSystemActions":
                        leadDetails.setFarmingSystemActions( attributeValue );
                        break;
                    case "financing":
                        leadDetails.setFinancing( attributeValue );
                        break;
                    case "gclId":
                        leadDetails.setGclId( attributeValue );
                        break;
                    case "inquiryDate":
                        leadDetails.setInquiryDate( attributeValue );
                        break;
                    case "interestedInFinancing":
                        leadDetails.setInterestedInFinancing( attributeValue );
                        break;
                    case "interestedZipCodes":
                        leadDetails.setInterestedZipCodes( attributeValue );
                        break;
                    case "lastVisitDateTime":
                        leadDetails.setLastVisitDateTime( attributeValue );
                        break;
                    case "leadSourceUrl":
                        leadDetails.setLeadSourceUrl( attributeValue );
                        break;
                    case "listingCreationDate":
                        leadDetails.setListingCreationDate( attributeValue );
                        break;
                    case "loanNumber":
                        leadDetails.setLoanNumber( attributeValue );
                        break;
                    case "marketingOptIn":
                        leadDetails.setMarketingOptIn( attributeValue );
                        break;
                    case "medianPrice":
                        leadDetails.setMedianPrice( attributeValue );
                        break;
                    case "message":
                        leadDetails.setMessage( attributeValue );
                        break;
                    case "mlsId":
                        leadDetails.setMlsId( attributeValue );
                        break;
                    case "mlsPackageType":
                        leadDetails.setMlsPackageType( attributeValue );
                        break;
                    case "notes":
                        leadDetails.setNotes( attributeValue );
                        break;
                    case "offerAmount":
                        leadDetails.setOfferAmount( attributeValue );
                        break;
                    case "orderId":
                        leadDetails.setOrderId( attributeValue );
                        break;
                    case "ownersComIdentifier":
                        leadDetails.setOwnersComIdentifier( attributeValue );
                        break;
                    case "ownersVisitorId":
                        leadDetails.setOwnersVisitorId( attributeValue );
                        break;
                    case "ownHome":
                        leadDetails.setOwnHome( attributeValue );
                        break;
                    case "partnerIdentifier":
                        leadDetails.setPartnerIdentifier( attributeValue );
                        break;
                    case "preApprovedForMortgage":
                        leadDetails.setPreApprovedForMortgage( attributeValue );
                        break;
                    case "preferredContactmethod":
                        leadDetails.setPreferredContactmethod( attributeValue );
                        break;
                    case "preferredContactTime":
                        leadDetails.setPreferredContactTime( attributeValue );
                        break;
                    case "preferredLanguage":
                        leadDetails.setPreferredLanguage( attributeValue );
                        break;
                    case "priceRange":
                        leadDetails.setPriceRange( attributeValue );
                        break;
                    case "propertyAddress":
                        leadDetails.setPropertyAddress( attributeValue );
                        break;
                    case "propertyBathroom":
                        leadDetails.setPropertyBathroom( attributeValue );
                        break;
                    case "propertyBedroom":
                        leadDetails.setPropertyBedroom( attributeValue );
                        break;
                    case "propertyCity":
                        leadDetails.setPropertyCity( attributeValue );
                        break;
                    case "propertySquareFoot":
                        leadDetails.setPropertySquareFoot( attributeValue );
                        break;
                    case "propertyTourInformation":
                        leadDetails.setPropertyTourInformation( attributeValue );
                        break;
                    case "propertyType":
                        leadDetails.setPropertyType( attributeValue );
                        break;
                    case "purchaseMethod":
                        leadDetails.setPurchaseMethod( attributeValue );
                        break;
                    case "requestType":
                        leadDetails.setRequestType( attributeValue );
                        break;
                    case "savedSearchValues":
                        leadDetails.setSavedSearchValues( attributeValue );
                        break;
                    case "searchAttributes":
                        leadDetails.setSearchAttributes( attributeValue );
                        break;
                    case "searchCity":
                        leadDetails.setSearchCity( attributeValue );
                        break;
                    case "unbouncePageVariant":
                        leadDetails.setUnbouncePageVariant( attributeValue );
                        break;
                    case "website":
                        leadDetails.setWebsite( attributeValue );
                        break;
                    case "websiteSessionData":
                        leadDetails.setWebsiteSessionData( attributeValue );
                        break;
                    case "workingWithRealtor":
                        leadDetails.setWorkingWithRealtor( attributeValue );
                        break;
                    default:
                        LOGGER.info( "Attribute- {} is not been defined to set in the lead details list.",
                                attributeName );
                        break;
                }
            }
        }
    }

    /**
     * Method to build the response of lead details for crmId
     * 
     * @param contact
     * @param objectsList
     * @return
     */
    private LeadDetailsDTO buildResponseForLeads( final Contact contact, final List< Object[] > objectLists ) {
        LOGGER.info( "OpportunityBusinessServiceImpl buildResponseForLeads method Start" );
        if (null == objectLists && contact == null) {
            return null;
        }

        final LeadDetailsDTO leadDetails = new LeadDetailsDTO();
        leadDetails.setState( contact.getState() );
        leadDetails.setScore( contact.getBuyerLeadScore() );
        String name = "";
        if (contact.getFirstName() != null && contact.getFirstName().trim().length() > 0) {
            name = name + contact.getFirstName();
        }
        if (contact.getLastName() != null && contact.getLastName().trim().length() > 0) {
            name = name + " " + contact.getLastName();
        }
        leadDetails.setLeadName( name );
        leadDetails.setEmail( contact.getEmail() );
        leadDetails.setPhone( contact.getPhone() );
        leadDetails.setCompany( contact.getCompany() );
        leadDetails.setSource( contact.getSource() );
        leadDetails.setOwnersComIdentifier( contact.getOwnersComId() );
        leadDetails.setStage( contact.getStage() );
        leadDetails.setLastModifiedDate( getFormattedDate( contact.getLastModifiedDate() ) );
        leadDetails.setCreatedDate( getFormattedDate( contact.getCreatedDate() ) );

        final Map< String, Object > map = new HashMap<>();
        for ( final Object[] objectArray : objectLists ) {
            if (null != objectArray) {
                map.put( getStringValue( objectArray[0] ), getStringValue( objectArray[1] ) );
            }
        }

        try {
            org.apache.commons.beanutils.BeanUtils.populate( leadDetails, map );
        } catch ( final IllegalAccessException e ) {
            LOGGER.error(
                    "IllegalAccessException in OpportunityBusinessServiceImpl buildResponseForLeads method, exception : {}",
                    e );
        } catch ( final InvocationTargetException e ) {
            LOGGER.error(
                    "InvocationTargetException in OpportunityBusinessServiceImpl buildResponseForLeads method, exception : {}",
                    e );
        }
        LOGGER.info( "OpportunityBusinessServiceImpl buildResponseForLeads method End" );
        return leadDetails;
    }

    @Override
    public LeadDetailsResponse getLeadDetails( final String crmId ) {
        LOGGER.info( "Start of OpportunityBusinessServiceImpl getLeadDetails method crmId : {}", crmId );
        final LeadDetailsResponse response = new LeadDetailsResponse( Status.FAILURE, Constants.LEAD_DETAILS_FAILURE );
        if (StringUtils.isNotEmpty( crmId )) {
            final Set< String > crmIdSet = new HashSet<>();
            crmIdSet.add( crmId );
            try {
                final List< Object[] > objectLists = contactServiceV1.getLeadAttributesForCrmId( crmIdSet );
                final Contact contact = contactServiceV1.getCrmIdAndDeletedIsFalse( crmId, Constants.lead );
                final LeadDetailsDTO leadDetails = buildResponseForLeads( contact, objectLists );
                response.setLeadDetails( leadDetails );
                response.setMessage( Constants.LEAD_DETAILS_SUCCESS );
                response.setStatus( Status.SUCCESS );
            } catch ( final Exception e ) {
                LOGGER.info( "Exception in OpportunityBusinessServiceImpl getLeadDetails method crmId : {}, {}", crmId,
                        e );
            }
        }
        LOGGER.info( "End of OpportunityBusinessServiceImpl getLeadDetails method crmId : {}", crmId );
        return response;
    }
    
    @Override
    public LeadDetailsResponse getOpportunityDetails( final String crmId ) {
        LOGGER.info( "Start of OpportunityBusinessServiceImpl getOpportunityDetails method crmId : {}", crmId );
        final LeadDetailsResponse response = new LeadDetailsResponse( Status.FAILURE, Constants.OPPORTUNITY_DETAILS_FAILURE );
        if (StringUtils.isNotEmpty( crmId )) {
            final Set< String > crmIdSet = new HashSet<>();
            crmIdSet.add( crmId );
            try {
                final List< Object[] > objectLists = contactServiceV1.getOpportunityAttributesForCrmId( crmIdSet );
                final Contact contact = contactServiceV1.getCrmIdAndDeletedIsFalse( crmId, Constants.opportunity );
                final LeadDetailsDTO leadDetails = buildResponseForLeads( contact, objectLists );
                response.setLeadDetails( leadDetails );
                response.setMessage( Constants.OPPORTUNITY_DETAILS_SUCCESS );
                response.setStatus( Status.SUCCESS );
            } catch ( final Exception e ) {
                LOGGER.info( "Exception in OpportunityBusinessServiceImpl getOpportunityDetails method crmId : {}, {}", crmId,
                        e );
            }
        }
        LOGGER.info( "End of OpportunityBusinessServiceImpl getOpportunityDetails method crmId : {}", crmId );
        return response;
    }

    /**
     * Gets the string value.
     *
     * @param object
     *            the object
     * @return the string value
     */
    private String getStringValue( final Object object ) {
        return ( null == object ) ? "" : object.toString();
    }

    @Override
    @Transactional
    public ClaimLeadResponse claimLead( final String crmId ) {
        final ClaimLeadResponse response = new ClaimLeadResponse( crmId, Constants.CLAIM_LEAD_FAILURE, Status.SUCCESS,
                Constants.CLAIM_LEAD_FAILURE_HEADER );
        final String emailId = gravitasWebUtil.getAppUserEmail();
        final com.owners.gravitas.domain.entity.Contact contact = contactServiceV1.getCrmIdAndDeletedIsFalse( crmId, Constants.lead );
        if (contact != null && contact.getAssignInsideSales() == null) {
            contact.setAssignInsideSales( emailId );
            try {
                contactServiceV1.save( contact );
                response.setMessage( Constants.CLAIM_LEAD_SUCCESS_HEADER );
                response.setDetailMessage( Constants.CLAIM_LEAD_SUCCESS );
            } catch ( final OptimisticLockException e ) {
                LOGGER.info(
                        "OptimisticLockException in OpportunityBusinessServiceImpl claimLead method : {} , crmId : {}",
                        e.getMessage(), crmId );
            } catch ( final Exception e ) {
                LOGGER.info( "Exception in OpportunityBusinessServiceImpl claimLead method : {} , crmId : {}",
                        e.getMessage(), crmId );
            }
        }
        return response;
    }

    @Override
    public LeadLayoutResponse getLeadLayout( String source ) {
        LOGGER.info( "Start of LeadBusinessServiceImpl getLeadLayout method source : {}", source );
        final LeadLayoutResponse response = new LeadLayoutResponse( Status.SUCCESS, Constants.LEAD_LAYOUT_GET_SUCCESS );
        if (StringUtils.isNotEmpty( source ) && ( LeadLayoutEnum.ALLLEADS.name().equalsIgnoreCase( source )
                || LeadLayoutEnum.MYLEADS.name().equalsIgnoreCase( source ) )) {
            try {
                source = LeadLayoutEnum.ALLLEADS.name().equalsIgnoreCase( source ) ? LeadLayoutEnum.ALLLEADS.name()
                        : LeadLayoutEnum.MYLEADS.name();
                final String layout = leadLayoutService.getLeadLayout( gravitasWebUtil.getAppUserEmail(),
                        LeadLayoutEnum.LAYOUTTYPE.name(), source );
                if (layout == null || layout.length() == 0) {
                    if (source.equalsIgnoreCase( LeadLayoutEnum.ALLLEADS.name() )) {
                        response.setLayout( leadLayoutService.getDefaultAllLeadLayout() );
                    } else {
                        response.setLayout( leadLayoutService.getDefaultMyLeadLayout() );
                    }
                } else {
                    final ObjectMapper objectMapper = new ObjectMapper();
                    response.setLayout( objectMapper.readValue( layout, Object.class ) );
                }
            } catch ( final Exception e ) {
                LOGGER.info( "Exception in LeadBusinessServiceImpl getLeadLayout method source : {}, exception {}",
                        source, e );
                response.setMessage( Constants.LEAD_LAYOUT_GET_EXCEPTION );
                response.setStatus( Status.FAILURE );
            }
        } else {
            response.setMessage( Constants.LEAD_LAYOUT_SOURCE_NOT_CORRECT );
            response.setStatus( Status.FAILURE );
        }
        LOGGER.info( "End of LeadBusinessServiceImpl getLeadLayout method source : {}", source );
        return response;
    }

    @Override
    public LeadLayoutResponse saveLeadLayout( String source, final String layout ) {
        LOGGER.info( "Start of LeadBusinessServiceImpl saveLeadLayout method source : {}", source );
        final LeadLayoutResponse response = new LeadLayoutResponse( Status.SUCCESS,
                Constants.LEAD_LAYOUT_SAVE_SUCCESS );

        if (StringUtils.isNotEmpty( source ) && ( LeadLayoutEnum.ALLLEADS.name().equalsIgnoreCase( source )
                || LeadLayoutEnum.MYLEADS.name().equalsIgnoreCase( source ) ) && validateLeadLayout( layout )) {
            try {
                source = LeadLayoutEnum.ALLLEADS.name().equalsIgnoreCase( source ) ? LeadLayoutEnum.ALLLEADS.name()
                        : LeadLayoutEnum.MYLEADS.name();
                leadLayoutService.saveOrUpdateLeadLayout( gravitasWebUtil.getAppUserEmail(),
                        LeadLayoutEnum.LAYOUTTYPE.name(), source, layout );
            } catch ( final Exception e ) {
                LOGGER.info( "Exception in LeadBusinessServiceImpl saveLeadLayout method source : {}, exception {}",
                        source, e );
                response.setMessage( Constants.LEAD_LAYOUT_SAVE_EXCEPTION );
                response.setStatus( Status.FAILURE );
            }
        } else {
            response.setMessage( Constants.LEAD_LAYOUT_SOURCE_NOT_CORRECT );
            response.setStatus( Status.FAILURE );
        }
        LOGGER.info( "End of LeadBusinessServiceImpl saveLeadLayout method source : {}", source );
        return response;
    }

    private boolean validateLeadLayout( final String layout ) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.readValue( layout, Object.class );
        } catch ( final Exception ex ) {
            LOGGER.info( "Exception in LeadBusinessServiceImpl validateLeadLayout method layout : {}, exception {}",
                    layout, ex );
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public MenuConfigResponse getMenuConfigurationOfRole( String role ) {
        LOGGER.info( "Start of LeadBusinessServiceImpl getMenuConfigurationOfRole method role : {}", role );
        final MenuConfigResponse response = new MenuConfigResponse( Status.FAILURE, Constants.ROLE_CONFIG_GET_FAILURE );
        try {
            if (role != null && role.trim().length() > 0) {
                role = role.trim().toUpperCase();
                final Map< String, String > roleMap = roleMemberService.getRolesNameMap();
                if (roleMap != null && roleMap.get( role ) != null) {
                    final String roleId = roleMap.get( role );
                    final List< MenuConfig > config = menuConfigService.getMenuConfigOfRole( roleId );
                    response.setStatus( Status.SUCCESS );
                    response.setConfig( buildMenuConfigResponse( config ) );
                    response.setMessage( Constants.ROLE_CONFIG_GET_SUCCESS );
                } else {
                    response.setMessage( Constants.ROLE_CONFIG_GET_FAILURE_ROLE_NOT_CORRECT );
                }
            } else {
                response.setMessage( Constants.ROLE_CONFIG_GET_FAILURE_ROLE_NOT_CORRECT );
            }
        } catch ( final Exception e ) {
            LOGGER.info(
                    "Exception in LeadBusinessServiceImpl getMenuConfigurationOfRole method role : {}, exception {}",
                    role, e );
            response.setMessage( Constants.ROLE_CONFIG_GET_EXCEPTION );
        }
        LOGGER.info( "End of LeadBusinessServiceImpl getMenuConfigurationOfRole method source : {}", role );
        return response;
    }

    private List< MenuConfigDto > buildMenuConfigResponse( final List< MenuConfig > config ) {
        LOGGER.info( "Start of LeadBusinessServiceImpl buildMenuConfigResponse method config : {}", config );
        final List< MenuConfigDto > menuConfigDtoList = new ArrayList<>();
        MenuConfigDto menuConfigDto = null;
        List< SubMenuConfigDto > subMenuConfigDtoList = null;
        SubMenuConfigDto subMenuConfigDto = null;

        List< SubMenuConfig > subMenuConfig = null;
        if (config != null && config.size() > 0) {
            for ( final MenuConfig mconfig : config ) {
                if (mconfig != null) {
                    menuConfigDto = new MenuConfigDto();
                    menuConfigDto.setModule( mconfig.getModule() );
                    menuConfigDto.setModuleIcon( mconfig.getModuleIcon() );
                    menuConfigDto.setModuleRedirect( mconfig.getModuleRedirect() );
                    menuConfigDto.setModuleSelector( mconfig.getModuleSelector() );
                    subMenuConfig = mconfig.getSubModules();
                    if (subMenuConfig != null && subMenuConfig.size() > 0) {
                        subMenuConfigDtoList = new ArrayList<>();
                        for ( final SubMenuConfig smconfig : subMenuConfig ) {
                            subMenuConfigDto = new SubMenuConfigDto();
                            subMenuConfigDto.setModule( smconfig.getSubModule() );
                            if (smconfig.getDisable()) {
                                subMenuConfigDto.setDisable( true );
                            }
                            subMenuConfigDtoList.add( subMenuConfigDto );
                        }
                        menuConfigDto.setSubModules( subMenuConfigDtoList );
                    }
                    menuConfigDtoList.add( menuConfigDto );
                }
            }
        }
        LOGGER.info( "End of LeadBusinessServiceImpl buildMenuConfigResponse method config : {}", config );
        return menuConfigDtoList;
    }

    @Override
    @Transactional
    public MenuConfigResponse getListOfMenusForAllRole() {
        LOGGER.info( "Start of LeadBusinessServiceImpl getListOfMenusForAllRole" );
        final MenuConfigResponse response = new MenuConfigResponse( Status.FAILURE, Constants.MENU_LIST_GET_FAILURE );
        try {
            final Map< String, String > roleMap = roleMemberService.getRolesIdMap();
            final List< MenuConfig > config = menuConfigService.getListOfMenusForAllRole();
            if (roleMap != null && config != null) {
                response.setStatus( Status.SUCCESS );
                response.setConfig( buildMenuListResponse( config, roleMap ) );
                response.setMessage( Constants.MENU_LIST_GET_SUCCESS );
            } else {
                response.setMessage( Constants.MENU_LIST_GET_FAILURE_DATA_NOT_PRESENT );
            }
        } catch ( final Exception e ) {
            LOGGER.info( "Exception in LeadBusinessServiceImpl getListOfMenusForAllRole exception {}", e );
            response.setMessage( Constants.MENU_LIST_GET_EXCEPTION );
        }
        LOGGER.info( "End of LeadBusinessServiceImpl getListOfMenusForAllRole" );
        return response;
    }

    private Map< String, List< MenuDto > > buildMenuListResponse( final List< MenuConfig > config,
            final Map< String, String > roleMap ) {
        LOGGER.info( "Start of LeadBusinessServiceImpl buildMenuListResponse method config : {}", config );

        List< MenuDto > menus = null;
        String roleName = null;
        final Map< String, List< MenuDto > > map = new HashMap<>();

        for ( final MenuConfig mconfig : config ) {
            if (mconfig != null) {
                roleName = roleMap.get( mconfig.getRoleId() );
                roleName = roleName.replaceAll( "_", " " );
                if (map.containsKey( roleName )) {
                    menus = map.get( roleName );
                    menus.add( new MenuDto( mconfig.getMenuName(), mconfig.isDisable() ) );
                } else {
                    menus = new ArrayList<>();
                    menus.add( new MenuDto( mconfig.getMenuName(), mconfig.isDisable() ) );
                    map.put( roleName, menus );
                }
            }
        }
        /* Super Admin & Admin not required for now, so removing it from map */
        map.remove( "SUPER ADMIN" );
        map.remove( "AGENT SUCCESS" );
        LOGGER.info( "End of LeadBusinessServiceImpl buildMenuListResponse method config : {}", config );
        return map;
    }

    @Override
    @Transactional
    public MenuConfigResponse getListOfRolesForAllRoutePath() {
        LOGGER.info( "Start of LeadBusinessServiceImpl getListOfRolesForAllRoutePath" );
        final MenuConfigResponse response = new MenuConfigResponse( Status.FAILURE, Constants.ROLE_LIST_GET_FAILURE );
        try {
            final Map< String, String > roleMap = roleMemberService.getRolesIdMap();
            final List< MenuConfig > config = menuConfigService.findAllByDisableFalse();
            if (roleMap != null && config != null) {
                response.setStatus( Status.SUCCESS );
                response.setConfig( buildRoleListResponse( config, roleMap ) );
                response.setMessage( Constants.ROLE_LIST_GET_SUCCESS );
            } else {
                response.setMessage( Constants.ROLE_LIST_GET_FAILURE_DATA_NOT_PRESENT );
            }
        } catch ( final Exception e ) {
            LOGGER.info( "Exception in LeadBusinessServiceImpl getListOfRolesForAllRoutePath exception {}", e );
            response.setMessage( Constants.ROLE_LIST_GET_EXCEPTION );
        }
        LOGGER.info( "End of LeadBusinessServiceImpl getListOfRolesForAllRoutePath" );
        return response;
    }

    private Map< String, List< String > > buildRoleListResponse( final List< MenuConfig > config,
            final Map< String, String > roleMap ) {
        LOGGER.info( "Start of LeadBusinessServiceImpl buildRoleListResponse method config : {}", config );

        final Map< String, List< String > > map = new HashMap<>();
        List< String > list = null;
        for ( final MenuConfig menu : config ) {
            if (map.containsKey( menu.getModuleRedirect() )) {
                list = map.get( menu.getModuleRedirect() );
                list.add( roleMap.get( menu.getRoleId() ) );
            } else {
                list = new ArrayList<>();
                list.add( roleMap.get( menu.getRoleId() ) );
                map.put( menu.getModuleRedirect(), list );
            }
        }

        LOGGER.info( "End of LeadBusinessServiceImpl buildRoleListResponse method config : {}", config );
        return map;
    }

    @Override
    public void syncUpLead( final SyncUpRequest request, final boolean isCrone ) {
        final int leadSyncUpCount = startSyncUpActivity( request, isCrone );
        LOGGER.info( "Total Lead sync up count :{}", leadSyncUpCount );
    }

    /**
     * @param request
     * @return
     */
    private int startSyncUpActivity( final SyncUpRequest request, final boolean isCrone ) {
        LOGGER.info( "Request for lead sync up  :{}", request );
        final Date[] dates = leadService.getFromAndToDates( request, isCrone );
        request.setDates( dates );
        final UuidReadSyncUpServiceImpl uuidReadSyncUpServiceImpl = leadReadSyncUpHandlerFactory
                .getReader( request.getAttribute() );
        final List< Contact > response = uuidReadSyncUpServiceImpl.execute( request );

        final UuidProcessSyncUpServiceImpl uuidProcessSyncUpService = leadProcessSyncUpHandlerFactory
                .getProcessor( request.getAttribute() );

        LOGGER.info( "Total fetched Records : {}", response.size() );

        int leadCount = 0;
        for ( final Contact contact : response ) {
            LOGGER.info( "Trying to update conact with CRM id :{}", contact.getCrmId() );
            leadCount = leadCount + performSyncUp( contact, uuidReadSyncUpServiceImpl, uuidProcessSyncUpService );
        }

        return leadCount;
    }

    /**
     * @param request
     * @param uuidReadSyncUpServiceImpl
     * @param uuidProcessSyncUpService
     * @return
     */
    private int performSyncUp( final Contact contact, final UuidReadSyncUpServiceImpl uuidReadSyncUpServiceImpl,
            final UuidProcessSyncUpServiceImpl uuidProcessSyncUpService ) {
        LOGGER.info( "Contact  for lead sync up  :{}", contact.getCrmId() );
        final List< Contact > contactList = new ArrayList<>();
        final List< LeadSyncUp > leadSyncUpList = new ArrayList<>();
        int leadUpdateStatus = 0;
        try {
            final BaseResponse response = uuidProcessSyncUpService.processSyncUpAttribute( contact );
            LOGGER.info( "for CRM id :{} response is :{} and uuid is :{}", contact.getCrmId(),
                    response.getStatus().toString(), contact.getOwnersComId() );
            if (contact.getOwnersComId() != null) {
                LOGGER.info( "Going to update uuid :{} for crm id ;{}", contact.getOwnersComId(), contact.getCrmId() );
                final LeadSyncUp leadSyncUp = leadSyncUpBuilder.convertTo( contact );
                leadSyncUpList.add( leadSyncUp );
                contactList.add( contact );
                contactServiceV1.save( contactList );
                leadSyncUpEntityService.save( leadSyncUpList );
                leadUpdateStatus = leadUpdateStatus + 1;
            }
        } catch ( final Exception e ) {
            LOGGER.info( "for CRM id : got error while updating {}", contact.getCrmId() );
        }

        return leadUpdateStatus;
    }

}
