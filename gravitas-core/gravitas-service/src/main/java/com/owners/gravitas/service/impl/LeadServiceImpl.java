package com.owners.gravitas.service.impl;

import static com.owners.gravitas.constants.CRMQuery.GET_LEAD_ID_BY_EMAIL_AND_RECORD_TYPE_ID;
import static com.owners.gravitas.constants.Constants.EMAIL;
import static com.owners.gravitas.constants.Constants.RECORD_TYPE_ID;
import static com.owners.gravitas.util.DateUtil.DEFAULT_CRM_DATE_PATTERN;
import static com.owners.gravitas.util.DateUtil.toSqlDate;
import static com.owners.gravitas.util.RestUtil.buildRequest;
import static com.owners.gravitas.util.RestUtil.createHttpHeader;
import static java.lang.Boolean.TRUE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.services.machinelearning.AmazonMachineLearning;
import com.amazonaws.services.machinelearning.model.PredictRequest;
import com.amazonaws.services.machinelearning.model.PredictResult;
import com.owners.gravitas.config.LeadBusinessConfig;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.drools.dto.ContactDetails;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.dto.crm.request.CRMLeadRequest;
import com.owners.gravitas.dto.crm.response.CRMAccess;
import com.owners.gravitas.dto.crm.response.CRMConvertedOpportunityResponse;
import com.owners.gravitas.dto.crm.response.CRMLeadResponse;
import com.owners.gravitas.dto.request.SyncUpRequest;
import com.owners.gravitas.service.CRMAuthenticatorService;
import com.owners.gravitas.service.CRMQueryService;
import com.owners.gravitas.service.ContactLogService;
import com.owners.gravitas.service.LeadService;
import com.owners.gravitas.service.RuleRunnerService;

/**
 * The Class LeadServiceImpl.
 *
 * @author vishwanathm
 */
@Service
public class LeadServiceImpl implements LeadService {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( LeadServiceImpl.class );

    /** restTemplate for making rest call to owners. */
    @Autowired
    @Qualifier( "crmRestAuthenticator" )
    private CRMAuthenticatorService crmAuthenticator;

    /** restTemplate for making rest call to owners. */
    @Autowired
    private RestTemplate restTemplate;

    /** lead creation url. */
    @Value( value = "${salesforce.create.lead.url}" )
    private String leadUrl;

    /** The crm query service. */
    @Autowired
    private CRMQueryService crmQueryService;
    
    @Autowired
    private ContactLogService contactLogService;

    /** The convert lead to opportunity api. */
    @Value( value = "${salesforce.convertlead.api.endpoint}" )
    private String convertLeadToOpportunityApi;

    /** The amc client. */
    @Autowired
    private AmazonMachineLearning amlClient;

    /** The Rule Runner Service. */
    @Autowired
    private RuleRunnerService ruleRunnerService;
    
    /** The LeadBusinessConfig. */
    @Autowired
    private LeadBusinessConfig leadBusinessConfig;

    /** The aws endpoint. */
    @Value( value = "${aws.predict.endpoint}" )
    private String awsEndpoint;

    /** The ml model id. */
    @Value( value = "${aws.leadscore.machine.learning.modelId}" )
    private String leadScoreMLmodelId;

    /** Lead invalid Phone No's starts with. */
    @Value( "${contact.drools.rule.file}" )
    private String contactDroolsRuleFile;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.LeadService#createLead(com.owners.gravitas.
     * dto.crm.request.CRMLeadRequest)
     */
    @Override
    public CRMLeadResponse createLead( final CRMLeadRequest crmLeadRequest ) {
        return createLead( crmLeadRequest, TRUE );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.LeadService#createLead(com.owners.gravitas.
     * dto.crm.request.CRMLeadRequest, boolean)
     */
    @Override
    public CRMLeadResponse createLead( final CRMLeadRequest crmLeadRequest, final boolean allowAutoAssignment ) {
        final CRMAccess accessTokenRes = crmAuthenticator.authenticate();
        final String reqUrl = accessTokenRes.getInstanceUrl() + leadUrl;
        LOGGER.debug( "sending create request to CRM with Json Request" );
        return restTemplate.exchange( reqUrl, HttpMethod.POST,
                buildRequest( createHttpHeader( accessTokenRes.getAccessToken(), allowAutoAssignment ),
                        crmLeadRequest ),
                CRMLeadResponse.class ).getBody();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.LeadService#updateLead(com.owners.gravitas.
     * dto.crm.request.CRMLeadRequest, java.lang.String, boolean)
     */
    @Override
    public CRMLeadResponse updateLead( final CRMLeadRequest crmLeadRequest, final String leadId,
            final boolean allowAutoAssignment ) {
        final CRMAccess accessTokenRes = crmAuthenticator.authenticate();
        final String reqUrl = accessTokenRes.getInstanceUrl() + leadUrl + "/" + leadId;
        LOGGER.debug( "sending update lead request to CRM." );
        return restTemplate.exchange( reqUrl, HttpMethod.PATCH,
                buildRequest( createHttpHeader( accessTokenRes.getAccessToken(), allowAutoAssignment ),
                        crmLeadRequest ),
                CRMLeadResponse.class ).getBody();
    }

    /**
     * Update lead.
     *
     * @param crmMap
     *            the crm map
     * @param leadId
     *            the lead id
     * @return the CRM lead response
     */
    @Override
    public CRMLeadResponse updateLead( final Map< String, Object > crmMap, final String leadId ) {
        final CRMAccess accessTokenRes = crmAuthenticator.authenticate();
        final String reqUrl = accessTokenRes.getInstanceUrl() + leadUrl + "/" + leadId;
        LOGGER.info("sending update lead request to CRM : {}", reqUrl);
        return restTemplate.exchange( reqUrl, HttpMethod.PATCH,
                buildRequest( createHttpHeader( accessTokenRes.getAccessToken() ), crmMap ), CRMLeadResponse.class )
                .getBody();
    }

    /**
     * Update lead.
     *
     * @param crmMap
     *            the crm map
     * @param leadId
     *            the lead id
     * @param allowAutoAssignment
     *            the assignement rule
     * @return the CRM lead response
     */
    @Override
    public CRMLeadResponse updateLead( final Map< String, Object > crmMap, final String leadId,
            final boolean allowAutoAssignment ) {
        final CRMAccess accessTokenRes = crmAuthenticator.authenticate();
        final String reqUrl = accessTokenRes.getInstanceUrl() + leadUrl + "/" + leadId;
        LOGGER.debug( "sending update lead request to CRM." + crmMap + "-leadId::" + leadId );
        return restTemplate.exchange( reqUrl, HttpMethod.PATCH,
                buildRequest( createHttpHeader( accessTokenRes.getAccessToken(), allowAutoAssignment ), crmMap ),
                CRMLeadResponse.class ).getBody();
    }

    /**
     * Gets the lead.
     *
     * @param leadId
     *            the lead id
     * @return the lead
     */
    @Override
    public CRMLeadResponse getLead( final String leadId ) {
        final CRMAccess accessTokenRes = crmAuthenticator.authenticate();
        final String reqUrl = accessTokenRes.getInstanceUrl() + leadUrl + "/" + leadId;
        LOGGER.info( "sending getLead request to CRM for leadId: {}, url:{}", leadId, reqUrl );
        return restTemplate.exchange( reqUrl, HttpMethod.GET,
                buildRequest( createHttpHeader( accessTokenRes.getAccessToken() ), null ), CRMLeadResponse.class )
                .getBody();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.LeadService#
     * getMortgageLeadIdByEmailAndRecordType(java.lang.String, java.lang.String)
     */
    @Override
    public String getLeadIdByEmailAndRecordTypeId( final String emailId, final String recordTypeId ) {
        final QueryParams params = new QueryParams();
        params.add( EMAIL, emailId );
        params.add( RECORD_TYPE_ID, recordTypeId );
        final Map< String, Object > response = crmQueryService.findOne( GET_LEAD_ID_BY_EMAIL_AND_RECORD_TYPE_ID,
                params );
        return ( String ) response.get( Constants.QUERY_PARAM_ID );
    }

    /**
     * Convert lead to opportunity.
     *
     * @param leadId
     *            the lead id
     * @return the opportunityId
     */
    @Override
    public String convertLeadToOpportunity( final String leadId ) {
        final CRMAccess accessTokenRes = crmAuthenticator.authenticate();
        final String reqUrl = accessTokenRes.getInstanceUrl() + convertLeadToOpportunityApi;
        final Map< String, String > body = new HashMap<>();
        body.put( "leadId", leadId );
        LOGGER.debug( "sending convert lead request to CRM." );
        final CRMConvertedOpportunityResponse crmConvertedOpportunityResponse = restTemplate.exchange( reqUrl,
                HttpMethod.POST, buildRequest( createHttpHeader( accessTokenRes.getAccessToken() ), body ),
                CRMConvertedOpportunityResponse.class ).getBody();
        LOGGER.info( "Succefully converted the lead: " + leadId + " into an opportunity: "
                + crmConvertedOpportunityResponse.getOpportunityid() );
        return crmConvertedOpportunityResponse.getOpportunityid();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.LeadService#deleteLead(java.lang.String)
     */
    @Override
    public void deleteLead( final String leadId ) {
        final CRMAccess accessTokenRes = crmAuthenticator.authenticate();
        final String reqUrl = accessTokenRes.getInstanceUrl() + leadUrl + "/" + leadId;
        LOGGER.debug( "sending getLead request to CRM for leadId:" + leadId );
        restTemplate.exchange( reqUrl, HttpMethod.DELETE,
                buildRequest( createHttpHeader( accessTokenRes.getAccessToken() ), null ), CRMLeadResponse.class )
                .getBody();
    }

    /**
     * Gets the opportunity score by making rest call to aws machine learning
     * lead score API and fetches the result in <code>PredictResult</code> based
     * on the different parameters sent in request.
     *
     * @param records
     *            the records
     * @return the opportunity score
     */
    @Override
    public PredictResult getLeadScore( final Map< String, String > records ) {
        final PredictRequest request = new PredictRequest().withMLModelId( leadScoreMLmodelId )
                .withPredictEndpoint( awsEndpoint ).withRecord( records );
        return amlClient.predict( request );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.LeadService#isValidPhoneNo(com.owners.
     * gravitas.domain.entity.Contact)
     */
    @Override
    public boolean isValidPhoneNumber( Contact contact ) {
        boolean isValidPhoneNumber = TRUE;
        String leadPhoneNumber = contact.getPhone();
        if (leadPhoneNumber == null) {
            return isValidPhoneNumber;
        }
        leadPhoneNumber = leadPhoneNumber.replaceAll( "[\\D]", "" );
        LOGGER.info( "Lead with email id :{} Formated Phone Number:{}", contact.getEmail(), leadPhoneNumber );

        ContactDetails contactDetails = new ContactDetails();
        contactDetails.setPhoneNumber( leadPhoneNumber );
        contactDetails.setStatus( isValidPhoneNumber );
        LOGGER.info( "For email id  :{} contactDetails before rule run:{}", contact.getEmail(), contactDetails );
        Object[] facts = { contactDetails };
        String[] rules = { contactDroolsRuleFile };
        ruleRunnerService.executeRuleRunner( rules, facts );
        LOGGER.info( "For email id  :{} contactDetails after rule run:{}", contact.getEmail(), contactDetails );

        return contactDetails.isStatus();
    }
    
    /* (non-Javadoc)
     * @see com.owners.gravitas.service.LeadService#getFromAndToDates(java.lang.String, java.lang.String)
     */
    @Override
    public Date[] getFromAndToDates( final SyncUpRequest request, boolean isCrone ) {
        LOGGER.info( "Start: selecting date range" );
        Date[] dates = null;
        String fromDate = request.getFromDate();
        String toDate = request.getToDate();
        final Calendar cal = Calendar.getInstance();
        final SimpleDateFormat format1 = new SimpleDateFormat( DEFAULT_CRM_DATE_PATTERN );
        if (!StringUtils.isEmpty( fromDate ) && !StringUtils.isEmpty( toDate )) {
        	dates = new Date[2];
            try {
                cal.setTime( format1.parse( toDate ) );
                cal.add( Calendar.DAY_OF_MONTH, 1 );
                toDate = format1.format( cal.getTime() );
                dates[0] = toSqlDate( fromDate.trim(), DEFAULT_CRM_DATE_PATTERN );
                dates[1] = toSqlDate( toDate.trim(), DEFAULT_CRM_DATE_PATTERN );
                LOGGER.info( "Selected Date ranges for API are :  fromDate : {} and toDate : {}", dates[0], dates[1] );
                request.setFromDate(dates[0].toString());
                request.setToDate(dates[1].toString());
            } catch ( final ParseException e ) {
                LOGGER.info( "Passed toDate is not formatable  in formaat {}", DEFAULT_CRM_DATE_PATTERN );
                e.printStackTrace();
            }
        } else if (isCrone) { 
        	dates =  getFromAndToDatesBasedOnHours();
        	LOGGER.info( "Selected Date ranges for crone job are :  fromDate : {} and toDate : {}", dates[0], dates[1] );
        	request.setFromDate(dates[0].toString());
        	request.setToDate(dates[1].toString());
        }
        return dates;
    }
    
    /* (non-Javadoc)
     * @see com.owners.gravitas.service.LeadService#getFromAndToDates(java.lang.String, java.lang.String)
     */
    @Override
    public Date[] getFromAndToDatesBasedOnHours() {
        LOGGER.info( "Start: selecting date range" );
        Date[] dates = new Date[2];
        final Calendar cal = Calendar.getInstance();
        final SimpleDateFormat format1 = new SimpleDateFormat( DEFAULT_CRM_DATE_PATTERN );
        Date date = cal.getTime();
        String toDate = format1.format(date);
            try {
            	cal.setTime( format1.parse( toDate ) );
        		cal.add( Calendar.HOUR, -leadBusinessConfig.getBeforeHourSyncUpLead() );
        		String fromDate = format1.format( cal.getTime() );
                dates[0] = toSqlDate( fromDate.trim(), DEFAULT_CRM_DATE_PATTERN );
                dates[1] = toSqlDate( toDate.trim(), DEFAULT_CRM_DATE_PATTERN );
            } catch ( final ParseException e ) {
                LOGGER.info( "Passed toDate is not formatable  in formaat {}", DEFAULT_CRM_DATE_PATTERN );
                e.printStackTrace();
            }
        LOGGER.info( "End: Selected Date ranges are :  fromDate : {} and toDate : {}", dates[0], dates[1] );
        return dates;
    }
        
}
