package com.owners.gravitas.service.impl;

import static com.owners.gravitas.constants.Constants.COMMA;
import static com.owners.gravitas.util.RestUtil.buildRequest;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.hubzu.common.cache.service.CacheService;
import com.hubzu.dsync.service.DistributedSynchronizationService;
import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.config.LiveVoxConfiguration;
import com.owners.gravitas.config.LiveVoxJmxConfig;
import com.owners.gravitas.constants.CRMConstants;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.ObjectType;
import com.owners.gravitas.dto.request.LiveVoxCampaignRecordRequest;
import com.owners.gravitas.dto.request.LiveVoxMatchingCampaignRequest;
import com.owners.gravitas.dto.request.LiveVoxSessionRequest;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.LiveVoxMatchingCampaignResponse;
import com.owners.gravitas.dto.response.LiveVoxSessionResponse;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.enums.LeadStatus;
import com.owners.gravitas.enums.ProspectAttributeType;
import com.owners.gravitas.service.ContactEntityService;
import com.owners.gravitas.service.LeadService;
import com.owners.gravitas.service.LiveVoxService;
import com.owners.gravitas.service.ObjectTypeService;
import com.owners.gravitas.service.builder.LiveVoxCampaignRecordRequestBuilder;
import com.owners.gravitas.service.builder.LiveVoxMatchingCampaignRequestBuilder;
import com.owners.gravitas.service.cache.LiveVoxLookupService;
import com.owners.gravitas.service.util.LiveVoxUtil;
import com.owners.gravitas.util.JsonUtil;

@Service( "liveVoxService" )
public class LiveVoxServiceImpl implements LiveVoxService {

    /** Logger instance. */
    private static final Logger LOGGER = LoggerFactory.getLogger( LiveVoxServiceImpl.class );
    
    private static final String LIVEVOX_LEAD_NOT_SUBMITTED_CACHE = "livevoxleadnotsubmitted_";

    /** The live vox url. */
    @Value( "${livevox.api.endpoint}" )
    private String liveVoxURL;

    /** The live vox manual call URL. */
    @Value( "${livevox.append.campaign.api}" )
    private String appendCampaign;

    /** The live vox login URL. */
    @Value( "${livevox.login.api}" )
    private String login;

    /** The rest template. */
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier( value = "liveVoxConfig" )
    private LiveVoxConfiguration liveVoxConfig;

    @Autowired
    private LiveVoxLookupService liveVoxLookupService;
    
    @Autowired
    private LeadService leadService;
    
    @Autowired
    private LiveVoxCampaignRecordRequestBuilder liveVoxCampaignRecordRequestBuilder;

    @Autowired
    private ContactEntityService contactServiceV1;
    
    @Autowired
    private ObjectTypeService objectTypeService;

    @Autowired
    private DistributedSynchronizationService jedisDistributedSynchronizationService;
    
    @Autowired
    private CacheService gravitasCacheService;

    private final String liveVoxSessionHttpHeader = "LV-Session";

    private final String liveVoxAccessHttpHeader = "LV-Access";

    private final String CREATE_SESSION_LOCK = "CREATE_SESSION_LOCK";

    /** The live vox create session lock time in milliseconds. */
    @Value( "${livevox.create.session.lock.time.in.milliseconds}" )
    private long sessionLockTimeOutInMiliseconds;

    /** The live vox manual call URL. */
    @Value( "${livevox.session.validation.api}" )
    private String sessionValidation;

    @Value( "${livevox.matching.campaign.api}" )
    private String matchingCampaign;

    @Value( "${livevox.response.codes}" )
    private String responseCodes;
    
    @Value( "${livevox.cache.default.expiry.in.seconds:1296000}" )
    private long livevoxCacheExpiry;

    @Autowired
    @Qualifier( value = "liveVoxMatchingCampaignRequestBuilder" )
    private LiveVoxMatchingCampaignRequestBuilder liveVoxMatchingCampaignRequestBuilder;

    @Autowired
    private LiveVoxUtil liveVoxUtil;

    /** The ocl buyer lead status. */
    private List< String > responseCodeList;
    
    @Autowired
    private LiveVoxJmxConfig liveVoxJmxConfig;
    
    /** Lead invalid Phone Reason closed. */
    @Value( "${livevox.lead.source.exclusion.list}" )
    private String liveVoxExclusionLeadSource;

    /** The ocl lead buyer states list. */
    private List< String > liveVoxExclusionLeadSourceList;

    @PostConstruct
    private void initDataBuilder() {
        responseCodeList = new ArrayList<>();
        Stream.of( responseCodes.split( COMMA ) ).forEach( name -> responseCodeList.add( name.trim().toLowerCase() ) );
        
        liveVoxExclusionLeadSourceList = new ArrayList<>();
        Stream.of( liveVoxExclusionLeadSource.split( COMMA ) )
                .forEach( name -> liveVoxExclusionLeadSourceList.add( name.trim().toLowerCase() ) );

    }

    @Override
    public LiveVoxSessionResponse createSession( LiveVoxSessionRequest liveVoxSessionRequest ) {
        LOGGER.info( "LiveVox create session request : " + JsonUtil.toJson( liveVoxSessionRequest ) );
        LiveVoxSessionResponse response = null;
        try {
            if (jedisDistributedSynchronizationService.acquireLockBlocking( CREATE_SESSION_LOCK,
                    sessionLockTimeOutInMiliseconds )) {
                if (StringUtils.isEmpty( liveVoxLookupService.getSessionInfo() )) {
                    final HttpHeaders headers = new HttpHeaders();
                    headers.add( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE );
                    headers.add( liveVoxAccessHttpHeader, liveVoxConfig.getAccessToken() );
                    response = restTemplate.exchange( getURL( login ), POST,
                            buildRequest( headers, liveVoxSessionRequest ), LiveVoxSessionResponse.class ).getBody();
                } else {
                    response = new LiveVoxSessionResponse();
                    response.setSessionId( liveVoxLookupService.getSessionInfo() );
                }
            }
        } catch ( Exception e ) {
            LOGGER.error( "Exception occured while creating session to livevox ", e );
        } finally {
            jedisDistributedSynchronizationService.releaseLock( CREATE_SESSION_LOCK );
        }
        return response;
    }

    /**
     * Gets the url.
     *
     * @param operation
     *            the operation
     * @return the url
     */
    private String getURL( final String operation ) {
        return liveVoxURL + operation;
    }

    /**
     * Gets the headers.
     *
     * @return the headers
     */
    private HttpHeaders getHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.add( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE );
        headers.add( liveVoxAccessHttpHeader, liveVoxConfig.getAccessToken() );
        headers.add( liveVoxSessionHttpHeader, getSessionId() );
        return headers;
    }

    @Override
    public boolean isSessionValid( String sessionId ) {
        LOGGER.debug( "LiveVox is session valid for session id : {} ", sessionId );
        boolean isSessionValid = false;
        try {
            final HttpHeaders headers = new HttpHeaders();
            headers.add( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE );
            ResponseEntity< String > response = restTemplate.exchange( getURL( sessionValidation + "/" + sessionId ),
                    GET, buildRequest( headers, null ), String.class );
            String statusCode = response.getStatusCode().toString();
            if (responseCodeList.contains( statusCode )) {
                isSessionValid = true;
            }
        } catch ( Exception e ) {
            LOGGER.error( "Exception occured while checking if session is valid", e );
        }
        return isSessionValid;
    }

    @Override
    public BaseResponse appendCampaignRecord( int campaignId,
            LiveVoxCampaignRecordRequest liveVoxCampaignRecordRequest ) {
        LOGGER.info( "LiveVox appendCampaignRecord for campaignId : {} and liveVoxCampaignRecordRequest : {} ",
                campaignId, JsonUtil.toJson( liveVoxCampaignRecordRequest ) );
        BaseResponse baseResponse = null;
        try {
            ResponseEntity< String > response = restTemplate.exchange(
                    getURL( String.format( appendCampaign, campaignId ) ), PUT,
                    buildRequest( getHeaders(), liveVoxCampaignRecordRequest ), String.class );
            String statusCode = response.getStatusCode().toString();
            // only success code we receive from livevox is 204 No Content
            if (responseCodeList.contains( statusCode )) {
                baseResponse = new BaseResponse();
            } else {
                LOGGER.error(
                        "LiveVox appendCampaignRecord for campaignId : {} and liveVoxCampaignRecordRequest : {} failed with errorCode : {}",
                        campaignId, JsonUtil.toJson( liveVoxCampaignRecordRequest ), statusCode );
            }
        } catch ( Exception e ) {
            LOGGER.error(
                    "Error LiveVox appendCampaignRecord for campaignId : {} and liveVoxCampaignRecordRequest : {} ",
                    campaignId, JsonUtil.toJson( liveVoxCampaignRecordRequest ), e );
        }
        return baseResponse;
    }

    @Override
    public LiveVoxMatchingCampaignResponse findMatchingCampaigns(
            LiveVoxMatchingCampaignRequest liveVoxMatchingCampaignRequest ) {
        LOGGER.debug( "LiveVox findMatchingCampaigns liveVoxMatchingCampaignRequest : {} ",
                JsonUtil.toJson( liveVoxMatchingCampaignRequest ) );
        LiveVoxMatchingCampaignResponse liveVoxMatchingCampaignResponse = null;
        try {
            liveVoxMatchingCampaignResponse = restTemplate.exchange( getURL( matchingCampaign ), POST,
                    buildRequest( getHeaders(), liveVoxMatchingCampaignRequest ),
                    LiveVoxMatchingCampaignResponse.class ).getBody();
        } catch ( Exception e ) {
            LOGGER.error( "Exception occured in findMatchingCampaigns ", e );
        }
        return liveVoxMatchingCampaignResponse;
    }

    @Override
    public String getSessionId() {
        String cachedSessionId = liveVoxLookupService.getSessionInfo();
        if (!StringUtils.isEmpty( cachedSessionId ) && isSessionValid( cachedSessionId )) {
            return cachedSessionId;
        } else {
            liveVoxLookupService.removeSessionInfo();
        }
        LiveVoxSessionRequest liveVoxSessionRequest = new LiveVoxSessionRequest();
        liveVoxSessionRequest.setClientName( liveVoxConfig.getClientName() );
        liveVoxSessionRequest.setUserName( liveVoxConfig.getUserName() );
        liveVoxSessionRequest.setPassword( liveVoxConfig.getPassword() );
        liveVoxSessionRequest.setAgent( false );
        LiveVoxSessionResponse liveVoxSessionResponse = createSession( liveVoxSessionRequest );
        if (liveVoxSessionResponse != null && !StringUtils.isEmpty( liveVoxSessionResponse.getSessionId() )) {
            liveVoxLookupService.putSessionInfo( liveVoxSessionResponse.getSessionId() );
            return liveVoxSessionResponse.getSessionId();
        }
        return null;
    }

    @Override
    public Integer getMatchingCampaignId() {
        Integer campaignId = null;
        String cachedCampaingId = liveVoxLookupService.getMatchingCampaignIdInfo();
        if (!StringUtils.isEmpty( cachedCampaingId )) {
            campaignId = Integer.parseInt( cachedCampaingId );
        } else {
            LiveVoxMatchingCampaignRequest liveVoxMatchingCampaignRequest = liveVoxMatchingCampaignRequestBuilder
                    .createLiveVoxMatchingCampaignRequest();
            LiveVoxMatchingCampaignResponse liveVoxMatchingCampaignResponse = findMatchingCampaigns(
                    liveVoxMatchingCampaignRequest );
            if (liveVoxMatchingCampaignResponse != null
                    && !org.springframework.util.CollectionUtils
                            .isEmpty( liveVoxMatchingCampaignResponse.getCampaign() )
                    && liveVoxMatchingCampaignResponse.getCampaign().size() == 1) {
                campaignId = liveVoxMatchingCampaignResponse.getCampaign().get( 0 ).getId();
                liveVoxLookupService.putMatchingCampaignIdInfo(
                        String.valueOf( liveVoxMatchingCampaignResponse.getCampaign().get( 0 ).getId() ),
                        liveVoxUtil.getMatchingCampaignIdExpiryTime() );
            }
        }
        return campaignId;
    }
    
    @Override
    public boolean isLiveVoxEnabledState(final String state) {
        boolean isLiveVoxEnabledState = false;
        if (liveVoxJmxConfig.getLiveVoxStateValues().isEmpty()) {
            isLiveVoxEnabledState = true;
        } else if (org.apache.commons.lang3.StringUtils.isBlank(state)
                && liveVoxJmxConfig.isLiveVoxEnabledForNullState()) {
            isLiveVoxEnabledState = true;
        } else if (liveVoxJmxConfig.getLiveVoxStateValues().contains(state)) {
            isLiveVoxEnabledState = true;
        }
        return isLiveVoxEnabledState;
    }
    
    /**
     * Method to Set Lead attributes once lead successfully submitted to live
     * Vox
     * 
     * @param LeadSource,
     *            Contact, ObjectType
     */
    @Override
    public void handleLiveVoxSubmittedLeadAttributes( final LeadSource leadSource, final Contact contact, final ObjectType objectType ) {
        LOGGER.info( "Updating Lead with id :{} Status to {}", leadSource.getId(),
                LeadStatus.OUTBOUND_ATTEMPT.getStatus() );
        liveVoxUtil.addLiveVoxContactAttribute( contact.getContactAttributes(),
                ProspectAttributeType.SUBMITTED_TO_LIVEVOX.getKey(), String.valueOf( true ), objectType );
        final Map< String, Object > crmMap = new HashMap<>();
        crmMap.put( CRMConstants.STATUS, LeadStatus.OUTBOUND_ATTEMPT.getStatus() );
        leadService.updateLead( crmMap, leadSource.getId() );
        contact.setStage( LeadStatus.OUTBOUND_ATTEMPT.getStatus() );
        contactServiceV1.save( contact );
    }
    
    @Override
    public boolean isLivevoxLeadSubmitted( final LeadSource leadSource, final Contact contact) {
        LOGGER.info( "Checking Lead with id :{} for livevox submission", leadSource.getId());
        String submittedToLivevox = contactServiceV1.getContactAttribute(contact.getContactAttributes(), ProspectAttributeType.SUBMITTED_TO_LIVEVOX.getKey());
        if(org.apache.commons.lang3.StringUtils.isNotBlank(submittedToLivevox)){
            boolean submittedToLiveVox = Boolean.valueOf(submittedToLivevox);
            return submittedToLiveVox;
        }
        return false;
    }
    
    @Override
    public void checkAndSubmitToLiveVox(LeadSource leadSource){
        LOGGER.info("Checking Lead : {} for livevox phone number : {}", leadSource.getEmail(), leadSource.getPhone());
        boolean submitted = false;
        String phoneNumber = leadSource.getPhone();
        if(org.apache.commons.lang3.StringUtils.isNotBlank(phoneNumber)){
            phoneNumber = com.owners.gravitas.util.StringUtils.formatPhoneNumber(phoneNumber);
            if(org.apache.commons.lang3.StringUtils.isNotBlank(phoneNumber)){
                submitted = true;
                submitLeadToLiveVox(leadSource);
            }
        }
        if(!submitted){
            LOGGER.info("Lead : {} not submitted to livevox because of improper number : {}", leadSource.getEmail(), leadSource.getPhone());
            gravitasCacheService.putString(LIVEVOX_LEAD_NOT_SUBMITTED_CACHE + leadSource.getEmail(), "true", livevoxCacheExpiry);
        }
    }
    
    @Override
    public void submitLeadToLiveVox(LeadSource leadSource){
        LOGGER.info("Livevox submit request for crmId : {}", leadSource.getId());
        if (liveVoxJmxConfig.isLiveVoxServiceEnabled()
                && isLiveVoxEnabledState(leadSource.getState())
                && !liveVoxExclusionLeadSourceList.contains(leadSource.getSource().toLowerCase())) {
            LOGGER.info( "Request to livevox for the crmId : {} ", leadSource.getId() );
            final Contact contact = contactServiceV1.getContactByCrmId( leadSource.getId() );
            if (contact != null && !org.springframework.util.StringUtils.isEmpty( leadSource.getPhone() )) {
                LOGGER.info( "sending lead information to livevox for the crmId : {} ", leadSource.getId() );
                gravitasCacheService.deleteString(LIVEVOX_LEAD_NOT_SUBMITTED_CACHE + leadSource.getEmail());
                final ObjectType objectType = objectTypeService.findByName( "lead" );
                final LiveVoxCampaignRecordRequest liveVoxCampaignRecordRequest = liveVoxCampaignRecordRequestBuilder
                        .convertTo( leadSource );
                if (!liveVoxUtil.isLiveVoxAfterOfficeHours()) {
                    final Integer campaignId = getMatchingCampaignId();
                    if (campaignId != null) {
                        LOGGER.info( "Appending campaign records to livevox for the crmId : {} ", leadSource.getId() );
                        final BaseResponse response = appendCampaignRecord( campaignId,
                                liveVoxCampaignRecordRequest );
                        LOGGER.info( "Lead with id :{} submitted to Live Vox with status :{}", leadSource.getId(), JsonUtil.toJson(response));
                        if (response != null && Status.SUCCESS.equals( response.getStatus() )) {
                            LOGGER.info( "Lead with id :{} successfully submitted to LIve Vox", leadSource.getId());
                            handleLiveVoxSubmittedLeadAttributes( leadSource, contact, objectType );
                        } else {
                            LOGGER.info( "Putting campaign record to failure queue for the crmId : {} ",
                                    leadSource.getId() );
                            final String key = liveVoxUtil.getLiveVoxFailureQueueKey();
                            liveVoxLookupService.putCampaignRecordsInFailureQueue( key, liveVoxCampaignRecordRequest );
                            liveVoxUtil.addLiveVoxContactAttribute( contact.getContactAttributes(),
                                    ProspectAttributeType.SUBMITTED_TO_LIVEVOX.getKey(), String.valueOf( false ),
                                    objectType );
                        }
                    } else {
                        LOGGER.info(
                                "No active campaign/more than one active campaign found putting the lead information for crmId : {} in cache",
                                leadSource.getId() );
                        if(liveVoxJmxConfig.isLiveVoxCacheEnabled()) {
                            liveVoxLookupService.putCampaignRecordsInQueue( liveVoxCampaignRecordRequest );
                        }
                    }
                } else {
                    LOGGER.info(
                            "The lead request has come after office hours putting the lead details for crmId : {} in cache",
                            leadSource.getId() );
                    if(liveVoxJmxConfig.isLiveVoxCacheEnabled()) {
                        liveVoxLookupService.putCampaignRecordsInQueue( liveVoxCampaignRecordRequest );
                    }
                }
            }
        }
    }

    @Override
    public boolean isLeadSubmittedToLiveVox(LeadSource leadSource) {
        LOGGER.info("Check if Lead : {} submitted to livevox", leadSource.getEmail());
        boolean submitted = true;
        try {
            String isNotSubmitted = gravitasCacheService
                    .getString(LIVEVOX_LEAD_NOT_SUBMITTED_CACHE + leadSource.getEmail());
            if (org.apache.commons.lang3.StringUtils.isNotBlank(isNotSubmitted)) {
                submitted = !(Boolean.parseBoolean(isNotSubmitted));
            }
        } catch (Exception e) {
            LOGGER.error("Error while fetching livevox submitted values", e);
            submitted = true;
        }
        return submitted;
    }
    
}
