package com.owners.gravitas.business.impl;

import static com.owners.gravitas.constants.BuyerFarmingConstants.FIRST_FOLLOW_UP_EMAIL;
import static com.owners.gravitas.constants.BuyerFarmingConstants.FOLLOWUP_TYPE;
import static com.owners.gravitas.constants.BuyerFarmingConstants.INSIDE_SALES_FARMING_SUBSCRIPTION;
import static com.owners.gravitas.constants.BuyerFarmingConstants.SAVE_SEARCH_FAILED;
import static com.owners.gravitas.constants.BuyerFarmingConstants.SECOND_FOLLOW_UP_EMAIL;
import static com.owners.gravitas.constants.CRMConstants.FARMING_STATUS;
import static com.owners.gravitas.constants.CRMConstants.LEAD_FARMING_FAULRE_CODE;
import static com.owners.gravitas.constants.CRMConstants.LEAD_FARMING_SYSTEM_ACTIONS;
import static com.owners.gravitas.constants.CRMConstants.LEAD_STATUS;
import static com.owners.gravitas.constants.CRMConstants.REASON_CLOSED;
import static com.owners.gravitas.constants.Constants.COMMA;
import static com.owners.gravitas.constants.Constants.DOLLAR;
import static com.owners.gravitas.constants.Constants.LEAD;
import static com.owners.gravitas.constants.Constants.OPPORTUNITY;
import static com.owners.gravitas.constants.Constants.SAVED_SEARCH_FIRST_FOLLOWUP_NOTIFICATION_MESSAGE_TYPE_NAME;
import static com.owners.gravitas.constants.Constants.SAVED_SEARCH_SECOND_FOLLOWUP_NOTIFICATION_MESSAGE_TYPE_NAME;
import static com.owners.gravitas.constants.NotificationParameters.EMAIL_TEMPLATE;
import static com.owners.gravitas.constants.NotificationParameters.SUBJECT;
import static com.owners.gravitas.dto.response.BaseResponse.Status.OK;
import static com.owners.gravitas.dto.response.BaseResponse.Status.SUCCESS;
import static com.owners.gravitas.enums.BuyerFarmType.LONG_TERM_BUYER;
import static com.owners.gravitas.enums.BuyerFarmType.LOST_BUYER;
import static com.owners.gravitas.enums.FollowupType.FOLLOW_UP_1;
import static com.owners.gravitas.enums.FollowupType.FOLLOW_UP_2;
import static com.owners.gravitas.enums.ProspectAttributeType.FARMING_BUYER_ACTIONS;
import static com.owners.gravitas.enums.ProspectAttributeType.FARMING_FAILURE_CODE;
import static com.owners.gravitas.enums.ProspectAttributeType.FARMING_GROUP;
import static com.owners.gravitas.enums.ProspectAttributeType.FARMING_SYSTEM_ACTIONS;
import static com.owners.gravitas.enums.ProspectAttributeType.NOTES;
import static com.owners.gravitas.enums.RecordType.BUYER;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.amqp.AlertDetails;
import com.owners.gravitas.amqp.BuyerWebActivitySource;
import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.BuyerFarmingBusinessService;
import com.owners.gravitas.business.ProcessBusinessService;
import com.owners.gravitas.business.builder.ContactActivityBuilder;
import com.owners.gravitas.business.builder.LeadDetailsBuilder;
import com.owners.gravitas.business.builder.SavedSearchFollowupEmailNotificationBuilder;
import com.owners.gravitas.business.builder.WebActivityEmailContentBuilder;
import com.owners.gravitas.business.builder.WebActivityEmailNotificationBuilder;
import com.owners.gravitas.business.builder.request.OCLGenericLeadRequestBuilder;
import com.owners.gravitas.config.BuyerFarmingConfig;
import com.owners.gravitas.constants.BuyerFarmingConstants;
import com.owners.gravitas.constants.CRMConstants;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.ContactActivity;
import com.owners.gravitas.domain.entity.ContactAttribute;
import com.owners.gravitas.domain.entity.ContactJsonAttribute;
import com.owners.gravitas.domain.entity.ObjectAttributeConfig;
import com.owners.gravitas.domain.entity.ObjectType;
import com.owners.gravitas.domain.entity.RefCode;
import com.owners.gravitas.dto.CrmNote;
import com.owners.gravitas.dto.Preference;
import com.owners.gravitas.dto.request.GenericLeadRequest;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.dto.request.SavedSearchRequest;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.NotificationPreferenceResponse;
import com.owners.gravitas.dto.response.NotificationResponse;
import com.owners.gravitas.dto.response.RegisteredUserResponse;
import com.owners.gravitas.dto.response.RegistrationResponse;
import com.owners.gravitas.dto.response.SaveSearchResponse;
import com.owners.gravitas.enums.BuyerAction;
import com.owners.gravitas.enums.BuyerFarmType;
import com.owners.gravitas.enums.FollowupType;
import com.owners.gravitas.enums.GravitasProcess;
import com.owners.gravitas.enums.LeadStatus;
import com.owners.gravitas.enums.NotificationType;
import com.owners.gravitas.enums.ProspectAttributeType;
import com.owners.gravitas.service.BuyerService;
import com.owners.gravitas.service.ContactActivityService;
import com.owners.gravitas.service.ContactEntityService;
import com.owners.gravitas.service.EventPriorityService;
import com.owners.gravitas.service.LeadService;
import com.owners.gravitas.service.MailService;
import com.owners.gravitas.service.NoteService;
import com.owners.gravitas.service.ObjectAttributeConfigService;
import com.owners.gravitas.service.ObjectTypeService;
import com.owners.gravitas.service.OpportunityService;
import com.owners.gravitas.service.RefCodeService;
import com.owners.gravitas.util.JsonUtil;
import com.owners.gravitas.util.PropertiesUtil;

/**
 * The Class BuyerFarmingBusinessServiceImpl.
 *
 * @author vishwanathm
 */
@Service
@Transactional( readOnly = true )
public class BuyerFarmingBusinessServiceImpl implements BuyerFarmingBusinessService {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( BuyerFarmingBusinessServiceImpl.class );

    /** The Constant ACTIVITY_PREFIX. */
    private static final String ACTIVITY_PREFIX = "buyer.web.activity";

    /** The Constant ACTIVITY_PROP. */
    private static final String ACTIVITY_PROP = "buyer.web.total.activities";

    /** The Constant ACTVITY_KEY. */
    private static final String ACTVITY_KEY = "activity";

    /** The Constant ACTIVITY_KEY_PREFIX. */
    private static final String ACTIVITY_KEY_PREFIX = "buyer.web.";

    /** The Constant BUYER_WEB_ACTIVITY_SOURCE. */
    public static final String BUYER_WEB_ACTIVITY_SOURCE = "buyerWebActivitySource";
    
    public static final String DEFAULT_EMAIL_TEMPLATE = "Default_Email_Template";

    /** The from email. */
    @Value( "${buyer.farming.insideSales.web.activity.followup.from.email}" )
    private String fromEmail;

    /** The reply to email. */
    @Value( "${buyer.farming.insideSales.web.activity.followup.replyTo.email}" )
    private String replyToEmail;
    
    /** The web activity email notification builder. */
    @Autowired
    private WebActivityEmailNotificationBuilder webActivityEmailNotificationBuilder;
    
    @Autowired
    private WebActivityEmailContentBuilder webActivityEmailContentBuilder;

    /** The contact activity builder. */
    @Autowired
    private ContactActivityBuilder contactActivityBuilder;

    /** The contact activity service. */
    @Autowired
    private ContactActivityService contactActivityService;

    /** The ref code service. */
    @Autowired
    private RefCodeService refCodeService;

    /** The contact service V 1. */
    @Autowired
    private ContactEntityService contactServiceV1;

    /** The generic lead request builder. */
    @Autowired
    private OCLGenericLeadRequestBuilder genericLeadRequestBuilder;

    /** The buyer service. */
    @Autowired
    private BuyerService buyerService;

    /** The object type service. */
    @Autowired
    private ObjectTypeService objectTypeService;

    /** The object attribute config service. */
    @Autowired
    private ObjectAttributeConfigService objectAttributeConfigService;

    /** The lead service. */
    @Autowired
    private LeadService leadService;

    /** The note service. */
    @Autowired
    private NoteService noteService;

    /** The opportunity service. */
    @Autowired
    private OpportunityService opportunityService;

    /** The lead details builder. */
    @Autowired
    private LeadDetailsBuilder leadDetailsBuilder;

    /** The mail service. */
    @Autowired
    private MailService mailService;

    /** The followup email notification builder. */
    @Autowired
    private SavedSearchFollowupEmailNotificationBuilder followupEmailNotificationBuilder;

    /** The buyer farming config. */
    @Autowired
    private BuyerFarmingConfig buyerFarmingConfig;

    /** The runtime service. */
    @Autowired
    protected RuntimeService runtimeService;
    
    @Autowired
    private EventPriorityService eventPriorityService;

    /** The save search first followup duration. */
    @Value( "${buyer.farming.insideSales.savedSearch.first.followup.delay}" )
    private String savedSearchFirstFollowupDuration;

    /** The save search second followup duration. */
    @Value( "${buyer.farming.insideSales.savedSearch.second.followup.delay}" )
    private String savedSearchSecondFollowupDuration;

    /** The process business service. */
    @Autowired
    protected ProcessBusinessService processBusinessService;

    /** Lost Opportunity User Action Flag. */
    @Value( "${lost.opp.user.action.mail.flag}" )
    private boolean lostOppUsrActFlag;
    
    /** Lead invalid Phone Reason closed. */
    @Value( "${crm.lead.invalid.phone.reason.closed}" )
    private String invalidPhonereasonClosed;

    /**
     * Buyer register.
     *
     * @param leadSource
     *            the lead source
     * @return the contact
     */
    @Override
    @Transactional( propagation = REQUIRED )
    public Contact registerBuyer( final LeadSource leadSource ) {
        final Map< String, Object > crmLeadRequest = new HashMap<>();
        final LeadRequest leadRequest = getGenericLeadRequest( leadSource );
        String notesStr = BuyerFarmingConstants.FAILURE_COMMENTS_TXT + new Date();

        final Contact contact = contactServiceV1.getContact( leadSource.getEmail(), BUYER.getType() );
        final ObjectType objectType = objectTypeService.findByName( "lead" );

        try {
            final RegistrationResponse registrationResponse = buyerService
                    .registerBuyer( getBuyerDetails( leadRequest ) );
            LOGGER.info( "Buyer auto registration response: " + JsonUtil.toJson( registrationResponse ) );
            final RegisteredUserResponse response = registrationResponse.getResult();
            final String uuid = ( response.getUser() != null ) ? response.getUser().getUserId() : EMPTY;
            if (StringUtils.isNotBlank( uuid ) || OK.name().equals( response.getStatus() )) {
                notesStr = BuyerFarmingConstants.SUCCESS_COMMENTS_TXT + new Date();
                contact.setOwnersComId( uuid );
                contactServiceV1.addOrUpdateJsonAttribute( contact, objectType, ProspectAttributeType.FARMING_SYSTEM_ACTIONS.getKey(),
                        BuyerFarmingConstants.REGISTRATION_SUCCESS );
                contactServiceV1.addOrUpdateJsonAttribute( contact, objectType, ProspectAttributeType.NOTES.getKey(), notesStr );
                crmLeadRequest.put( CRMConstants.LEAD_FARMING_SYSTEM_ACTIONS,
                        BuyerFarmingConstants.REGISTRATION_SUCCESS );
            } else {
                LOGGER.info( "Buyer Registration failure response.", response.getMessage() );
                handleRegistrationFailure( contact, objectType, crmLeadRequest, response.getMessage(), notesStr );
            }
            if (StringUtils.isNotBlank( uuid )) {
                contact.setOwnersComId( uuid );
                crmLeadRequest.put( CRMConstants.LEAD_OWNERS_COM_UUID, uuid );
            }
            noteService.saveNote( new CrmNote( contact.getCrmId(), notesStr, null ) );
        } catch ( final ArrayIndexOutOfBoundsException e ) {
            LOGGER.error( "Invalid property address format", e );
            handleRegistrationFailure( contact, objectType, crmLeadRequest, "Invalid property address format",
                    notesStr );
            // Don't make single call: Notes sequence to be maintained.
            noteService.saveNote( new CrmNote( contact.getCrmId(), notesStr, null ) );
        } catch ( final Exception e ) {
            LOGGER.error( "Problem in buyer registration.", e );
            handleRegistrationFailure( contact, objectType, crmLeadRequest, e.getMessage(), notesStr );
            // Don't make single call: Notes sequence to be maintained.
            noteService.saveNote( new CrmNote( contact.getCrmId(), notesStr, null ) );
        }
        LOGGER.info("Saving contact post buyer farming : {}", contact.getId());
        contactServiceV1.save( contact );
        leadService.updateLead( crmLeadRequest, contact.getCrmId(), FALSE );
        return contact;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.BuyerFarmingBusinessService#startFarming(
     * java.lang.String, com.owners.gravitas.enums.BuyerFarmType)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED )
    public void updateFarmingStatus( final String crmId, final BuyerFarmType buyerFarmType,
            final boolean isOpportunity ) {
        LOGGER.info( "Starting farming for " + crmId + " & Farming type is: " + buyerFarmType );
        final Map< String, Object > updateParams = new HashMap<>();
        updateParams.put( CRMConstants.FARMING_GROUP, buyerFarmType.getType() );
        final Contact contact = contactServiceV1.findByCrmId( crmId );
        String objectTypeName = Constants.LEAD.toLowerCase();
        if (null != contact) {
            if (isOpportunity) {
                objectTypeName = Constants.OPPORTUNITY.toLowerCase();
                opportunityService.patchOpportunity( updateParams, crmId );
                LOGGER.debug( "Opportunity updated in CRM : " + buyerFarmType );
            } else if (LONG_TERM_BUYER.equals( buyerFarmType ) || LOST_BUYER.equals( buyerFarmType )) {
                leadService.updateLead( updateParams, crmId, FALSE );
                LOGGER.debug( "Lead updated in CRM : " + buyerFarmType );
            }

            final ObjectType objectType = objectTypeService.findByName( objectTypeName );
            contactServiceV1.addContactAttribute( contact.getContactAttributes(), FARMING_GROUP.getKey(), buyerFarmType.getType(),
                    objectType );
            contactServiceV1.save( contact );
            LOGGER.info( "Farming group updated for : " + crmId + " as " + buyerFarmType.getType() );
        }
    }

    /**
     * Save search for buyer.
     *
     * @param leadSource
     *
     * @param email
     *            the email
     * @return true, if is save search exists
     */
    private boolean isSaveSearchExists( final Contact contact, final ObjectType objectType,
            final LeadSource leadSource ) {
        final Map< String, Object > crmLeadRequest = new HashMap<>();
        final boolean isSaveSearchExists = buyerService.checkSaveSearchExists( contact.getOwnersComId() );

        if (isSaveSearchExists) {
            LOGGER.info( "Save Search exists for UUID " + contact.getOwnersComId() );
            final String notesStr = BuyerFarmingConstants.SAVE_SEARCH_EXISTS_COMMENTS_TXT + new Date();
            handleSaveSearchFailure( objectType, contact, crmLeadRequest, notesStr,
                    BuyerFarmingConstants.SAVE_SEARCH_EXISTS, null, FALSE );
            contactServiceV1.save( contact );
            leadService.updateLead( crmLeadRequest, contact.getCrmId(), FALSE );
            setupSaveSearchFollowupProcess( leadSource );
        }
        return isSaveSearchExists;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.BuyerFarmingBusinessService#
     * processWebActivity(com.owners.gravitas.amqp.BuyerWebActivitySource)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public void processWebActivity( final BuyerWebActivitySource buyerWebActivitySource ) {
        final Contact contact = contactServiceV1.getContactByOwnersComId( buyerWebActivitySource.getUserId() );
        if (contact != null) {
            LOGGER.info( "processing web activity for buyer : {}", contact.getEmail() );
            final Map< String, Object > paramData = new HashMap<>();
            paramData.put( BUYER_WEB_ACTIVITY_SOURCE, buyerWebActivitySource );
            final com.owners.gravitas.domain.entity.Process process = processBusinessService
                    .getProcess( contact.getEmail(), GravitasProcess.INSIDE_SALES_FARMING_PROCESS, "active" );
            if (process != null) {
                final String executionId = process.getExecutionId();
                runtimeService.setVariable( executionId, BuyerFarmingConstants.PROCESS_WEB_ACTIVITY, true );
                runtimeService.trigger( executionId, paramData );
            } else {
                LOGGER.error("No Process found for buyer : {}", contact.getEmail());
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.BuyerFarmingBusinessService#
     * sendWebActivityFollowupEmail(com.owners.gravitas.amqp.
     * BuyerWebActivitySource)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public void sendWebActivityFollowupEmail( final BuyerWebActivitySource buyerWebActivitySource ) {
        final Map< String, Object > activityConfMap = buildActivityConfMap();
        final Contact contact = contactServiceV1.getContactByOwnersComId( buyerWebActivitySource.getUserId() );
        final String objectType = contact.getObjectType().getName().toLowerCase();
        final BuyerAction highestPriorityAction = eventPriorityService.getHighestPriority( buyerWebActivitySource, contact );
        LOGGER.info("Sending web activity followup mail for highestPriorityAction :{}", highestPriorityAction);        
        if (null != highestPriorityAction && eventPriorityService.isEligibleForEmailNotification( highestPriorityAction )) {
            LOGGER.info( "Farming group for contact with email {} is long term and highest priority action {}",
                    contact.getEmail(), highestPriorityAction );
            final String activitySuffixKey = activityConfMap.get( highestPriorityAction.name() ).toString();
            String emailTemplate = PropertiesUtil
                    .getProperty( ACTIVITY_KEY_PREFIX + activitySuffixKey + ".emailtemplate" );
            String subject = null;
            if (DEFAULT_EMAIL_TEMPLATE.equals( emailTemplate )) {
                final Map<String, String> map = eventPriorityService.getSubjectAndEmailTemplate( highestPriorityAction );
                emailTemplate = map.get( EMAIL_TEMPLATE );
                subject = map.get( SUBJECT );
            }
            LOGGER.info( "Farming emailTemplate :{} ", emailTemplate );
            final Optional< AlertDetails > highestPriorityAlertDetail = buyerWebActivitySource.getAlertDetails()
                    .stream().filter( alert -> alert.getEventDisplayName().equals( highestPriorityAction.name() ) )
                    .findFirst();

            final String emailDynamicParametersStr = PropertiesUtil
                    .getProperty( ACTIVITY_KEY_PREFIX + activitySuffixKey + ".emailDyanamicParameters" );
            final String buyerName = StringUtils.isNotBlank( contact.getFirstName() ) ? contact.getFirstName()
                    : contact.getLastName();
            
            final Map< String, Object > sourceMap = webActivityEmailContentBuilder.buildEmailContent(
                    highestPriorityAlertDetail.get(), emailDynamicParametersStr, contact.getEmail(), buyerName, subject );

            LOGGER.info( "Sending email notification for contact with email : {}, emailDynamicParametersStr : {}, sourceMap : {}" ,contact.getEmail()
                    , emailDynamicParametersStr, sourceMap );
            final NotificationResponse notificationResponse = sendWebActivityFollowUpEmail( emailTemplate, sourceMap );
            if (Status.SUCCESS.name().equalsIgnoreCase( notificationResponse.getStatus() )) {
                LOGGER.info( "Updating fields for successful email send: {}", contact.getEmail());
                final String farmingSystemActions = PropertiesUtil
                        .getProperty( ACTIVITY_KEY_PREFIX + activitySuffixKey + ".farmingSystemActions" );
                final String farmingStatus = PropertiesUtil
                        .getProperty( ACTIVITY_KEY_PREFIX + activitySuffixKey + ".farmingStatus" );
                final String farmingBuyerActions = PropertiesUtil
                        .getProperty( ACTIVITY_KEY_PREFIX + activitySuffixKey + ".farmingBuyerActions" );
                updateDbFields( contact, objectType, farmingSystemActions, farmingStatus, farmingBuyerActions );
                if (objectType.toLowerCase().equals( OPPORTUNITY.toLowerCase() )) {
                    updateOppCrmFields( contact.getCrmId(), farmingSystemActions, farmingStatus, farmingBuyerActions );
                } else {
                    updateCrmFields( contact.getCrmId(), farmingSystemActions, farmingStatus, farmingBuyerActions );
                }
                updateContactActivity( buyerWebActivitySource.getUserId(), notificationResponse.getResult(),
                        highestPriorityAction.name(), highestPriorityAlertDetail.get() );
            }
        }
    }

    /**
     * Builds the activity conf map.
     *
     * @return the map
     */
    private Map< String, Object > buildActivityConfMap() {
        final Map< String, Object > activityConfMap = new LinkedHashMap< String, Object >();
        final int totalActivities = Integer.parseInt( PropertiesUtil.getProperty( ACTIVITY_PROP ) );
        for ( int suffix = 1; suffix <= totalActivities; suffix++ ) {
            final String propertyKey = ACTIVITY_PREFIX + suffix;
            activityConfMap.put( PropertiesUtil.getProperty( propertyKey ), ACTVITY_KEY + suffix );
        }
        LOGGER.info( "Activity configuration map : " + JsonUtil.toJson( activityConfMap ) );
        return activityConfMap;
    }

    /**
     * Send web activity follow up email.
     *
     * @param emailTemplate
     *            the email template
     * @param sourceMap
     *            the source map
     * @return the notification response
     */
    private NotificationResponse sendWebActivityFollowUpEmail( final String emailTemplate,
            final Map< String, Object > sourceMap ) {
        final EmailNotification notification = webActivityEmailNotificationBuilder.convertTo( sourceMap );
        notification.setMessageTypeName( emailTemplate );
        notification.getEmail().setReplyToEmail( replyToEmail );
        return mailService.send( notification );
    }

    /**
     * Update crm fields.
     *
     * @param crmId
     *            the crm id
     * @param leadFarmingSystemAction
     *            the lead farming system action
     * @param farmingStatus
     *            the farming status
     * @param farmingBuyerAction
     *            the farming buyer action
     */
    private void updateCrmFields( final String crmId, final String leadFarmingSystemAction, final String farmingStatus,
            final String farmingBuyerAction ) {
        LOGGER.info( "Updating crm fields for contact with crm id :" + crmId );
        final Map< String, Object > crmLeadRequest = new HashMap<>();
        crmLeadRequest.put( LEAD_FARMING_SYSTEM_ACTIONS, leadFarmingSystemAction );
        crmLeadRequest.put( CRMConstants.FARMING_STATUS, farmingStatus );
        crmLeadRequest.put( CRMConstants.FARMING_BUYER_ACTIONS, farmingBuyerAction );
        leadService.updateLead( crmLeadRequest, crmId, FALSE );
    }

    /**
     * Update crm fields for opportunity.
     *
     * @param crmId
     *            the crm id
     * @param leadFarmingSystemAction
     *            the lead farming system action
     * @param farmingStatus
     *            the farming status
     * @param farmingBuyerAction
     *            the farming buyer action
     */
    private void updateOppCrmFields( final String crmId, final String leadFarmingSystemAction,
            final String farmingStatus, final String farmingBuyerAction ) {
        LOGGER.info( "Updating Opportunity crm fields for contact with crm id :" + crmId );
        final Map< String, Object > crmLeadRequest = new HashMap<>();
        crmLeadRequest.put( LEAD_FARMING_SYSTEM_ACTIONS, leadFarmingSystemAction );
        crmLeadRequest.put( CRMConstants.FARMING_STATUS, farmingStatus );
        crmLeadRequest.put( CRMConstants.FARMING_BUYER_ACTIONS, farmingBuyerAction );
        opportunityService.patchOpportunity( crmLeadRequest, crmId );
    }

    /**
     * Update db fields.
     *
     * @param longTermContact
     *            the long term contact
     * @param objectTypeValue
     *            the object type value
     * @param leadFarmingSystemAction
     *            the lead farming system action
     * @param farmingStatus
     *            the farming status
     * @param farmingBuyerAction
     *            the farming buyer action
     * @return the contact
     */
    private Contact updateDbFields( final Contact longTermContact, final String objectTypeValue,
            final String leadFarmingSystemAction, final String farmingStatus, final String farmingBuyerAction ) {
        LOGGER.info(
                "Updating database fields for contact with crm id :{} and email id :{}" + longTermContact.getCrmId(),
                longTermContact.getEmail() );
        final ObjectType objectType = objectTypeService.findByName( objectTypeValue );
        contactServiceV1.addOrUpdateJsonAttribute( longTermContact, objectType, FARMING_SYSTEM_ACTIONS.getKey(),
                leadFarmingSystemAction );
        contactServiceV1.addOrUpdateJsonAttribute( longTermContact, objectType, ProspectAttributeType.FARMING_STATUS.getKey(),
                farmingStatus );
        contactServiceV1.addOrUpdateJsonAttribute( longTermContact, objectType, ProspectAttributeType.FARMING_BUYER_ACTIONS.getKey(),
                farmingBuyerAction );
        contactServiceV1.save( longTermContact );
        return longTermContact;
    }

    /**
     * Update contact activity.
     *
     * @param ownersComId
     *            the owners com id
     * @param reqId
     *            the req id
     * @param activityType
     *            the activity type
     * @param alertDetail
     *            the alert detail
     */
    private void updateContactActivity( final String ownersComId, final String reqId, final String activityType,
            final AlertDetails alertDetail ) {
        LOGGER.info( "Updating contact activity fields for owners com id :" + ownersComId + " and activity type "
                + activityType );
        final RefCode refCode = refCodeService.findByCode( activityType );
        final ContactActivity contactActivity = contactActivityBuilder.convertTo( alertDetail );
        contactActivity.setOwnersComId( ownersComId );
        contactActivity.setNotificationId( reqId );
        contactActivity.setSource( "WEB" );
        contactActivity.setAlertType( "Email" );
        contactActivity.setRefCode( refCode );
        contactActivityService.save( contactActivity );
    }

    /**
     * Update contact activity.
     *
     * @param ownersComId
     *            the owners com id
     * @param reqId
     *            the req id
     * @param activityType
     *            the activity type
     */
    private void updateContactActivity( final String ownersComId, final String reqId, final String activityType ) {
        LOGGER.info( "Updating contact activity fields for owners com id :" + ownersComId + " and activity type"
                + activityType );
        final RefCode refCode = refCodeService.findByCode( activityType );
        final ContactActivity contactActivity = new ContactActivity();
        contactActivity.setOwnersComId( ownersComId );
        contactActivity.setNotificationId( reqId );
        contactActivity.setSource( "GRAVITAS" );
        contactActivity.setAlertType( "Email" );
        contactActivity.setRefCode( refCode );
        contactActivityService.save( contactActivity );
    }

    /**
     * Handle registration failure.
     *
     * @param contact
     *            the contact
     * @param objectType
     *            the object type
     * @param crmLeadRequest
     *            the crm lead request
     * @param errorMsg
     *            the error msg
     * @param notesStr
     *            the notes str
     */
    protected void handleRegistrationFailure( final Contact contact, final ObjectType objectType,
            final Map< String, Object > crmLeadRequest, final String errorMsg, final String notesStr ) {
        contactServiceV1.addOrUpdateJsonAttribute( contact, objectType, ProspectAttributeType.NOTES.getKey(), notesStr );
        contactServiceV1.addOrUpdateJsonAttribute( contact, objectType, ProspectAttributeType.FARMING_SYSTEM_ACTIONS.getKey(),
                BuyerFarmingConstants.REGISTRATION_FAILED );
        contactServiceV1.addOrUpdateJsonAttribute( contact, objectType, ProspectAttributeType.FARMING_FAILURE_CODE.getKey(), errorMsg );
        crmLeadRequest.put( CRMConstants.LEAD_FARMING_SYSTEM_ACTIONS, BuyerFarmingConstants.REGISTRATION_FAILED );
        crmLeadRequest.put( CRMConstants.LEAD_FARMING_FAULRE_CODE, errorMsg );
        
        processBusinessService.deActivateAndSignal( contact.getEmail(), GravitasProcess.LEAD_MANAGEMENT_PROCESS, null );
    }

    /**
     * Gets the buyer details.
     *
     * @param leadRequest
     *            the lead source
     * @return the buyer details
     */
    private Map< String, Object > getBuyerDetails( final LeadRequest leadRequest ) {
        final Map< String, Object > buyerDetails = new HashMap<>();
        try {
            final Map< String, String > leadDetailsMap = leadDetailsBuilder.convertTo( leadRequest );
            buyerDetails.put( "leadDetails", leadDetailsMap );
        } catch ( final Exception e ) {
            LOGGER.warn( "Invalid property address format: " + leadRequest.getPropertyAddress(), e );
        }
        buyerDetails.put( "firstName", leadRequest.getFirstName() );
        buyerDetails.put( "lastName", leadRequest.getLastName() );
        buyerDetails.put( "emailAddress", leadRequest.getEmail() );
        return buyerDetails;
    }

    /**
     * Gets the generic lead request.
     *
     * @param leadSource
     *            the lead source
     * @return the generic lead request
     */
    @Override
    public GenericLeadRequest getGenericLeadRequest( final LeadSource leadSource ) {
        final GenericLeadRequest request = genericLeadRequestBuilder.convertTo( leadSource );
        request.setLeadSourceUrl( leadSource.getLeadSourceUrl() );
        request.setLeadType( leadSource.getRecordTypeName() );
        request.setRequestType( leadSource.getRequestType() );
        request.setFarmingBuyerAction( leadSource.getFarmingBuyerAction() );
        return request;
    }

    /**
     * Checks if is buyer auto registration email.
     *
     * @param emailStr
     *            the email str
     * @return true, if is buyer auto registration email
     */
    @Override
    public boolean isBuyerAutoRegistrationEmail( final String emailStr ) {
        return buyerService.isBuyerAutoRegistrationEmail( emailStr );
    }

    /**
     * Save search.
     *
     * @param leadSource
     *            the lead source
     * @return the save search response
     */
    @Override
    @Transactional( propagation = REQUIRED )
    public void saveSearch( final LeadSource leadSource ) {
        String notesStr = BuyerFarmingConstants.SAVE_SEARCH_FAILURE_COMMENTS_TXT + new Date();
        final LeadRequest leadRequest = getGenericLeadRequest( leadSource );
        final Map< String, Object > crmLeadRequest = new HashMap<>();
        final ObjectType objectType = objectTypeService.findByName( "lead" );
        final Contact contact = contactServiceV1.getContact( leadRequest.getEmail(), BUYER.getType() );
        final String uuid = contact.getOwnersComId();

        // if (StringUtils.isNotEmpty(uuid)) {
        try {
            // Checking phone no Validity of the lead

            final boolean leadHasvalidPhNo = leadService.isValidPhoneNumber( contact );
            if (!leadHasvalidPhNo) {
                contact.setStage( LeadStatus.LOST.getStatus() );
                crmLeadRequest.put( LEAD_STATUS, LeadStatus.LOST.getStatus() );
                crmLeadRequest.put( REASON_CLOSED, invalidPhonereasonClosed );

            }
            if (!isSaveSearchExists( contact, objectType, leadSource )) {
                final SavedSearchRequest savedSearchRequest = getSavedSearchRequest( leadRequest );
                savedSearchRequest.setUuid( uuid );
                LOGGER.info( "Saving buyer search : " + JsonUtil.toJson( savedSearchRequest ) + " for uuid "
                        + savedSearchRequest.getUuid() );
                final SaveSearchResponse response = buyerService.saveSearch( savedSearchRequest );
                LOGGER.info( "Buyer save search response: " + JsonUtil.toJson( response ) );
                if (response != null && response.getResult() != null
                        && SUCCESS.name().equals(response.getResult().getStatus())) {
                    LOGGER.info( "Save Search is successful  for UUID " + uuid );
                    notesStr = BuyerFarmingConstants.SAVE_SEARCH_SUCCESS_COMMENTS_TXT + new Date();
                    contactServiceV1.addOrUpdateJsonAttribute( contact, objectType, NOTES.getKey(), notesStr );
                    contactServiceV1.addOrUpdateJsonAttribute( contact, objectType, FARMING_SYSTEM_ACTIONS.getKey(),
                            BuyerFarmingConstants.SAVE_SEARCH_SUCCESS );
                    crmLeadRequest.put( LEAD_FARMING_SYSTEM_ACTIONS, BuyerFarmingConstants.SAVE_SEARCH_SUCCESS );
                    noteService.saveNote( new CrmNote( contact.getCrmId(), notesStr, null ) );
                    setupSaveSearchFollowupProcess( leadSource );
                } else {
                    if (response != null && response.getResult() != null) {
                        LOGGER.info(
                                "Save Search failed for UUID : {}  due to : {}", uuid, response.getResult().getMessage() );
                        handleSaveSearchFailure( objectType, contact, crmLeadRequest, notesStr, SAVE_SEARCH_FAILED,
                                response.getResult().getMessage(), TRUE );
                    } else {
                        LOGGER.info(
                                "Save Search failed for UUID " + uuid + " due to null response");
                        handleSaveSearchFailure( objectType, contact, crmLeadRequest, notesStr, SAVE_SEARCH_FAILED,
                                "Null Response", TRUE );
                    }
                }
            }
        } catch ( final ArrayIndexOutOfBoundsException e ) {
            LOGGER.error( "Invalid property address format", e );
            handleSaveSearchFailure( objectType, contact, crmLeadRequest, notesStr, SAVE_SEARCH_FAILED,
                    "Invalid property address format", TRUE );
        } catch ( final Exception e ) {
            LOGGER.error( "Problem in creating save search for UUID: " + uuid, e );
            handleSaveSearchFailure( objectType, contact, crmLeadRequest, notesStr, SAVE_SEARCH_FAILED, e.getMessage(),
                    TRUE );
        }
        contactServiceV1.save( contact );
        leadService.updateLead( crmLeadRequest, contact.getCrmId(), FALSE );
        /*
         * } else {
         * LOGGER.info(
         * "Save Search is not proceeded because of UUID(OwnersComId) unavailability for the email::"
         * + contact.getEmail() );
         * }
         */
    }

    /**
     * Save search.
     *
     * @param leadSource
     *            the lead source
     * @return the save search response
     */
    @Override
    @Transactional( propagation = REQUIRED )
    public void initiateSaveSearchFollowupProcess( final LeadSource leadSource ) {
        setupSaveSearchFollowupProcess( leadSource );
    }

    /**
     * Sets the up save search followup process.
     *
     * @param leadSource
     *            the new up save search followup process
     */
    private void setupSaveSearchFollowupProcess( final LeadSource leadSource ) {
        LOGGER.info( "setupSaveSearchFollowupProcess for leadId::{} and email id:{}", leadSource.getId(),
                leadSource.getEmail() );
        final Map< String, Object > followUpInitParams = new HashMap<>();
        followUpInitParams.put( LEAD, leadSource );
        followUpInitParams.put( FOLLOWUP_TYPE, FOLLOW_UP_1 );
        LOGGER.info( "FollowUp1 duration::{} and followup2 Duration::{}", savedSearchFirstFollowupDuration,
                savedSearchSecondFollowupDuration );

        followUpInitParams.put( "savedSearchFirstFollowupDuration", savedSearchFirstFollowupDuration );
        followUpInitParams.put( "savedSearchSecondFollowupDuration", savedSearchSecondFollowupDuration );
        LOGGER.info( "setupSaveSearchFollowupProcess started for leadId: " + leadSource.getId() );
        runtimeService.startProcessInstanceByKey( "savedSearchFollowupProcess", followUpInitParams );
        LOGGER.info( "setupSaveSearchFollowupProcess completed for leadId: " + leadSource.getId() );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.BuyerFarmingBusinessService#
     * sendFollowupEmail(java.lang.String, com.owners.gravitas.amqp.LeadSource)
     */
    @Override
    @Transactional( propagation = REQUIRED )
    public void sendFollowupEmail( final String executionId, final LeadSource leadSource ) {
        LOGGER.info( "ExecutionId:" + executionId + "::LEAD ID:" + leadSource.getId() );
        final Contact contact = contactServiceV1.findByCrmId( leadSource.getId() );
        if (!leadSource.isDoNotEmail() && !hasOptedForDnd( leadSource.getEmail() )
                && isNotBlank( contact.getOwnersComId() )) {
            final FollowupType type = ( FollowupType ) runtimeService.getVariable( executionId, FOLLOWUP_TYPE );
            ObjectType objectType = objectTypeService.findByName( "lead" );
            if (contact != null
                    && contact.getObjectType().getName().toLowerCase().equals( OPPORTUNITY.toLowerCase() )) {
                objectType = objectTypeService.findByName( Constants.OPPORTUNITY.toLowerCase() );
            }
            LOGGER.info( "objectType:" + objectType.getName() + ":: FollowupType:" + type );
            switch ( type ) {
                case FOLLOW_UP_1:
                    LOGGER.info( "Folllowup Type:" + FOLLOW_UP_1.name() + "::LEAD ID:" + leadSource.getId() );
                    if (buyerFarmingConfig.isBuyerFirstFollowupEmailEnabled()
                            && isEligibleForFirstFollowupEmail( contact, objectType )) {
                        sendFollowupEmail( leadSource, type, contact, objectType,
                                SAVED_SEARCH_FIRST_FOLLOWUP_NOTIFICATION_MESSAGE_TYPE_NAME );
                    }
                    break;
                case FOLLOW_UP_2:
                    LOGGER.info( "Folllowup Type:" + FOLLOW_UP_2.name() + "::LEAD ID:" + leadSource.getId() );
                    if (buyerFarmingConfig.isBuyerSecondFollowupEmailEnabled()
                            && isEligibleForSecondFollowupEmail( contact, objectType )) {
                        sendFollowupEmail( leadSource, type, contact, objectType,
                                SAVED_SEARCH_SECOND_FOLLOWUP_NOTIFICATION_MESSAGE_TYPE_NAME );
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Handle save search failure.
     *
     * @param objectType
     *            the object type
     * @param contact
     *            the contact
     * @param crmLeadRequest
     *            the crm lead request
     * @param notesStr
     *            the notes str
     * @param farmingSysActionsStr
     *            the farming sys actions str
     * @param message
     *            the message
     * @param failureResp
     *            the failure resp
     */
    private void handleSaveSearchFailure( final ObjectType objectType, final Contact contact,
            final Map< String, Object > crmLeadRequest, final String notesStr, final String farmingSysActionsStr,
            final String message, final boolean failureResp ) {
        contactServiceV1.addOrUpdateJsonAttribute( contact, objectType, FARMING_SYSTEM_ACTIONS.getKey(), farmingSysActionsStr );
        contactServiceV1.addOrUpdateJsonAttribute( contact, objectType, NOTES.getKey(), notesStr );
        // this should be in middle only
        if (failureResp) {
            contactServiceV1.addOrUpdateJsonAttribute( contact, objectType, FARMING_FAILURE_CODE.getKey(), message );
            crmLeadRequest.put( LEAD_FARMING_FAULRE_CODE, message );
        }
        crmLeadRequest.put( LEAD_FARMING_SYSTEM_ACTIONS, farmingSysActionsStr );
        noteService.saveNote( new CrmNote( contact.getCrmId(), notesStr, null ) );
    }

    /**
     * Gets the saved search request.
     *
     * @param leadRequest
     *            the lead source
     * @return the saved search request
     */
    private SavedSearchRequest getSavedSearchRequest( final LeadRequest leadRequest ) {
        final SavedSearchRequest savedSearchRequest = new SavedSearchRequest();
        String leadPrice = "0";
        if (leadRequest.getPriceRange() != null) {
            leadPrice = leadRequest.getPriceRange();
            leadPrice = leadPrice.replaceAll( DOLLAR, EMPTY );
            leadPrice = leadPrice.replaceAll( COMMA, EMPTY );
        }
        savedSearchRequest.setPrice( leadPrice );
        if (leadRequest.getPropertyAddress() == null) {
            savedSearchRequest.setZip( leadRequest.getInterestedZipcodes() );
            savedSearchRequest.setState( leadRequest.getState() );
        } else {
            final Map< String, String > leadDetailsMap = leadDetailsBuilder.convertTo( leadRequest );
            savedSearchRequest.setZip( leadDetailsMap.get( "zip" ) );
            savedSearchRequest.setCity( leadDetailsMap.get( "city" ) );
            savedSearchRequest.setState( leadDetailsMap.get( "state" ) );
            savedSearchRequest.setAddress( leadDetailsMap.get( "address" ) );
            savedSearchRequest.setMlsId( leadDetailsMap.get( "mlsId" ) );
        }
        return savedSearchRequest;
    }

    /**
     * Checks for opted for dnd.**
     *
     * @param email
     *            the email
     * @return true, if successful
     */
    private boolean hasOptedForDnd( final String email ) {
        LOGGER.info( "Checking whether the user opted for DND:" + email );
        boolean hasOptedForDnd = FALSE;
        final NotificationPreferenceResponse notificationPreferenceResponse = mailService
                .getNotificationPreferenceForUser( email );
        final List< Preference > preferences = notificationPreferenceResponse.getPreferences();
        if (CollectionUtils.isNotEmpty( preferences )) {
            hasOptedForDnd = preferences.stream().anyMatch(
                    p -> NotificationType.MARKETING.name().equalsIgnoreCase( p.getType() ) && !p.getValue() );
        }
        LOGGER.info( "has " + email + " opted for DND:" + hasOptedForDnd );
        return hasOptedForDnd;
    }

    /**
     * Send followup email.
     *
     * @param leadSource
     *            the lead source
     * @param followupType
     *            the followup type
     * @param contact
     *            the contact
     * @param objectType
     *            the object type
     * @param emailType
     *            the email type
     */
    private void sendFollowupEmail( final LeadSource leadSource, final FollowupType followupType, final Contact contact,
            final ObjectType objectType, final String emailType ) {
        LOGGER.info( "Sending save search followup email to " + leadSource.getEmail() + " of type " + followupType );
        final EmailNotification emailNotification = getFollowupEmailNotification( leadSource, emailType );
        final NotificationResponse notificationResponse = mailService.send( emailNotification );
        if (SUCCESS.name().equalsIgnoreCase( notificationResponse.getStatus() )) {
            LOGGER.info( "Save search followup email sent successfully to " + contact.getEmail() + " of type "
                    + followupType );
            final String farmingSystemActionValue = ( followupType == FollowupType.FOLLOW_UP_1 ) ? FIRST_FOLLOW_UP_EMAIL
                    : SECOND_FOLLOW_UP_EMAIL;

            LOGGER.debug( "Updating notification event details for uuid: " + contact.getOwnersComId() );
            updateContactActivity( contact.getOwnersComId(), notificationResponse.getResult(), followupType.getType() );
            updateLeadDetails( contact, objectType, farmingSystemActionValue );
            if (contact != null
                    && contact.getObjectType().getName().toLowerCase().equals( OPPORTUNITY.toLowerCase() )) {
                updateCrmOpportunity( contact.getCrmId(), farmingSystemActionValue );
            } else {
                updateCrmLead( contact.getCrmId(), farmingSystemActionValue );
            }
        } else {
            LOGGER.info(
                    "Failed to send save search followup email to " + contact.getEmail() + " of type " + followupType );
        }
    }

    /**
     * Gets the followup email notification.
     *
     * @param leadSource
     *            the lead source
     * @param emailType
     *            the email type
     * @return the followup email notification
     */
    private EmailNotification getFollowupEmailNotification( final LeadSource leadSource, final String emailType ) {
        final EmailNotification emailNotification = followupEmailNotificationBuilder.convertTo( leadSource );
        emailNotification.setMessageTypeName( emailType );
        return emailNotification;
    }

    /**
     * Update lead details.
     *
     * @param leadId
     *            the lead id
     * @param farmingSystemActionValue
     *            the farming system action value
     */
    private void updateLeadDetails( final Contact contact, final ObjectType objectType,
            final String farmingSystemActionValue ) {
        LOGGER.info( "Updating lead details in database for: " + contact.getEmail() );
        contactServiceV1.addOrUpdateJsonAttribute( contact, objectType, FARMING_SYSTEM_ACTIONS.getKey(), farmingSystemActionValue );
        contactServiceV1.addOrUpdateJsonAttribute( contact, objectType, ProspectAttributeType.FARMING_STATUS.getKey(),
                INSIDE_SALES_FARMING_SUBSCRIPTION );
        contactServiceV1.save( contact );
    }

    /**
     * Update crm lead.
     *
     * @param leadId
     *            the lead id
     * @param farmingSystemActionValue
     *            the farming system action value
     */
    private void updateCrmLead( final String leadId, final String farmingSystemActionValue ) {
        LOGGER.info( "Updating lead details on crm for: " + leadId );
        final Map< String, Object > crmLeadRequest = new HashMap<>();
        crmLeadRequest.put( LEAD_FARMING_SYSTEM_ACTIONS, farmingSystemActionValue );
        crmLeadRequest.put( FARMING_STATUS, INSIDE_SALES_FARMING_SUBSCRIPTION );
        leadService.updateLead( crmLeadRequest, leadId, FALSE );
    }

    /**
     * Update crm lead.
     *
     * @param leadId
     *            the lead id
     * @param farmingSystemActionValue
     *            the farming system action value
     */
    private void updateCrmOpportunity( final String oppId, final String farmingSystemActionValue ) {
        LOGGER.info( "Updating opportunity details on crm for: " + oppId );
        final Map< String, Object > crmOppdRequest = new HashMap<>();
        crmOppdRequest.put( LEAD_FARMING_SYSTEM_ACTIONS, farmingSystemActionValue );
        crmOppdRequest.put( FARMING_STATUS, INSIDE_SALES_FARMING_SUBSCRIPTION );
        opportunityService.patchOpportunity( crmOppdRequest, oppId );
    }

    /**
     * Checks if is eligible for second followup email.
     *
     * @param contact
     *            the contact
     * @param objectType
     *            the object type
     * @return true, if is eligible for second followup email
     */

    private boolean isEligibleForSecondFollowupEmail( final Contact contact, final ObjectType objectType ) {

        final String farmingGroup = getContactAttributeValue( contact, objectType, FARMING_GROUP.getKey() );
        final List< String > farmingBuyerActions = getContactAttributeJsonValue( contact, objectType,
                FARMING_BUYER_ACTIONS.getKey() );
        return LONG_TERM_BUYER.getType().equalsIgnoreCase( farmingGroup ) && isEmpty( farmingBuyerActions );
    }

    /**
     * Checks if is eligible for First followup email.
     *
     * @param contact
     *            the contact
     * @param objectType
     *            the object type
     * @return true, if is eligible for First followup email
     */
    private boolean isEligibleForFirstFollowupEmail( final Contact contact, final ObjectType objectType ) {
        boolean flag = TRUE;
        if (contact != null && contact.getObjectType().getName().toLowerCase().equals( OPPORTUNITY.toLowerCase() )) {
            final String farmingGroup = getContactAttributeValue( contact, objectType, FARMING_GROUP.getKey() );
            flag = LONG_TERM_BUYER.getType().equalsIgnoreCase( farmingGroup ) ? TRUE : FALSE;
        }
        LOGGER.info( "isEligibleForFirstFollowupEmail flag is: {}for email :{} ", flag, contact.getEmail() );
        return flag;
    }

    /**
     * Gets the contact attribute json value.
     *
     * @param contact
     *            the contact
     * @param objectType
     *            the object type
     * @param key
     *            the key
     * @return the contact attribute json value
     */
    private List< String > getContactAttributeJsonValue( final Contact contact, final ObjectType objectType,
            final String key ) {
        List< String > jsonValueList = Collections.emptyList();
        final ObjectAttributeConfig config = objectAttributeConfigService.getObjectAttributeConfig( key, objectType );
        for ( final ContactJsonAttribute contactJsonAttribute : contact.getContactJsonAttributes() ) {
            if (contactJsonAttribute.getObjectAttributeConfig().equals( config )) {
                if (contactJsonAttribute.getValue() != null) {
                    final Map< String, List< String > > jsonMap = JsonUtil.toType( contactJsonAttribute.getValue(),
                            Map.class );
                    jsonValueList = jsonMap.get( key );
                }
            }
        }
        return jsonValueList;
    }

    /**
     * Gets the contact attribute value.
     *
     * @param contact
     *            the contact
     * @param objectType
     *            the object type
     * @param key
     *            the key
     * @return the contact attribute value
     */
    private String getContactAttributeValue( final Contact contact, final ObjectType objectType, final String key ) {
        String attrValue = StringUtils.EMPTY;
        final ObjectAttributeConfig config = objectAttributeConfigService.getObjectAttributeConfig( key, objectType );
        for ( final ContactAttribute contactAttribute : contact.getContactAttributes() ) {
            if (contactAttribute.getObjectAttributeConfig().equals( config )) {
                attrValue = contactAttribute.getValue();
                break;
            }
        }
        return attrValue;
    }

    /**
     * Checks if is lead state is among long term farm state.
     *
     * @param state
     *            the state str
     *
     * @return true, if is lead state is in long term state string
     */
    @Override
    public boolean isFarmLongTermState( final String state ) {
        return buyerService.isFarmLongTermState( state );
    }
}
