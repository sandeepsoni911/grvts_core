package com.owners.gravitas.service.impl;

import static com.owners.gravitas.constants.CRMQuery.GET_OPPORTUNITY;
import static com.owners.gravitas.constants.CRMQuery.GET_OPPORTUNITY_EMAIL_AND_RECORD_TYPE;
import static com.owners.gravitas.constants.CRMQuery.GET_OPPORTUNITY_ID_BY_RECORDTYPE_ID_AND_EMAIL;
import static com.owners.gravitas.constants.CRMQuery.GET_OPPORTUNITY_ID_BY_RECORDTYPE_ID_AND_LOAN_NUMBER;
import static com.owners.gravitas.constants.CRMQuery.GET_TITLE_CLOSING_COMPANY_BY_OPPORTUNITY_ID;
import static com.owners.gravitas.constants.Constants.EMAIL;
import static com.owners.gravitas.constants.Constants.ID;
import static com.owners.gravitas.constants.Constants.LOAN_NUMBER;
import static com.owners.gravitas.constants.Constants.NAME;
import static com.owners.gravitas.constants.Constants.RECORD_TYPE_ID;
import static com.owners.gravitas.constants.Constants.RECORD_TYPE_NAME;
import static com.owners.gravitas.constants.Constants.TITLE_CLOSING_COMPANY;
import static com.owners.gravitas.util.RestUtil.buildRequest;
import static com.owners.gravitas.util.RestUtil.createHttpHeader;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.domain.entity.Opportunity;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.dto.crm.request.CRMOpportunityRequest;
import com.owners.gravitas.dto.crm.response.CRMAccess;
import com.owners.gravitas.dto.crm.response.CRMOpportunityResponse;
import com.owners.gravitas.dto.crm.response.CRMResponse;
import com.owners.gravitas.dto.crm.response.OpportunityResponse;
import com.owners.gravitas.enums.ErrorCode;
import com.owners.gravitas.enums.RecordType;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.repository.OpportunityRepository;
import com.owners.gravitas.service.CRMAuthenticatorService;
import com.owners.gravitas.service.CRMQueryService;
import com.owners.gravitas.service.OpportunityService;
import com.owners.gravitas.util.JsonUtil;
import com.owners.gravitas.util.RestUtil;

/**
 * The Class OpportunityServiceImpl.
 *
 * @author vishwanathm
 */
@Service( "opportunityService" )
public class OpportunityServiceImpl implements OpportunityService {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( OpportunityServiceImpl.class );

    /** restTemplate for making rest call to owners. */
    @Autowired
    @Qualifier( "crmRestAuthenticator" )
    private CRMAuthenticatorService crmAuthenticator;

    /** restTemplate for making rest call to owners. */
    @Autowired
    private RestTemplate restTemplate;

    /** The opportunity url. */
    @Value( value = "${salesforce.opportunity.url}" )
    private String opportunityUrl;

    /** The crm service. */
    @Autowired
    private CRMQueryService crmQueryService;

    /** The opportunity repository. */
    @Autowired
    private OpportunityRepository opportunityV1Repository;

    /**
     * Creates the opportunity.
     *
     * @param crmOpportunity
     *            the crm opportunity
     * @return the opportunity response
     */
    @Override
    public OpportunityResponse createOpportunity( final CRMOpportunityRequest crmOpportunity ) {
        final CRMAccess crmAccess = crmAuthenticator.authenticate();
        final String reqUrl = crmAccess.getInstanceUrl() + opportunityUrl;
        final OpportunityResponse opportunityResponse = restTemplate.exchange( reqUrl, HttpMethod.POST,
                buildRequest( RestUtil.createHttpHeader( crmAccess.getAccessToken() ), crmOpportunity ),
                OpportunityResponse.class ).getBody();
        LOGGER.info( "opportunity created " + opportunityResponse.getId() + " Time is : " + LocalDateTime.now() );
        return opportunityResponse;
    }

    /**
     * Verify existing opportunity.
     *
     * @param mlsID
     *            the mls id
     */
    @Override
    public void isOpportunityExists( final String mlsID ) {
        // verify opportunity exists throw exception if already exists
        final QueryParams params = new QueryParams();
        params.add( NAME, mlsID );
        final CRMResponse response = crmQueryService.findAll( GET_OPPORTUNITY, params );
        if (response.getTotalSize() > 0) {
            throw new ApplicationException( "Opportunity for " + mlsID + " already exists",
                    ErrorCode.OPPORTUNITY_ALREADY_EXISTS );
        }
    }

    /**
     * Update opportunity.
     *
     * @param crmOpportunityRequest
     *            the crm opportunity request
     * @param opprortunityId
     *            the opprortunity id
     * @param allowAutoAssignment
     *            the allow auto assignment
     * @return the CRM opportunity response
     */
    @Override
    public CRMOpportunityResponse updateOpportunity( final CRMOpportunityRequest crmOpportunityRequest,
            final String opprortunityId, final boolean allowAutoAssignment ) {
        final CRMAccess crmAccess = crmAuthenticator.authenticate();
        final String reqUrl = crmAccess.getInstanceUrl() + opportunityUrl + opprortunityId;
        LOGGER.info( "sending update opportunity request to CRM : {}", JsonUtil.toJson(crmOpportunityRequest) );
        return restTemplate.exchange( reqUrl, HttpMethod.PATCH,
                buildRequest( createHttpHeader( crmAccess.getAccessToken(), allowAutoAssignment ),
                        crmOpportunityRequest ),
                CRMOpportunityResponse.class ).getBody();
    }

    /**
     * Patch opportunity.
     *
     * @param opportunityRequest
     *            the opportunity request
     * @param opprortunityId
     *            the opprortunity id
     * @return the CRM opportunity response
     */
    @Override
    public void patchOpportunity( final Map< String, Object > opportunityRequest, final String opprortunityId ) {
        final CRMAccess crmAccess = crmAuthenticator.authenticate();
        final String reqUrl = crmAccess.getInstanceUrl() + opportunityUrl + opprortunityId;
        LOGGER.info( "sending patch opportunity request to CRM for id: " + opprortunityId );
        restTemplate.exchange( reqUrl, HttpMethod.PATCH,
                buildRequest( RestUtil.createHttpHeader( crmAccess.getAccessToken() ), opportunityRequest ),
                Object.class ).getBody();
    }

    /**
     * Gets the opportunity.
     *
     * @param opportunityId
     *            the opportunity id
     * @return the opportunity
     */
    @Override
    public CRMOpportunityResponse getOpportunity( final String opportunityId ) {
        final CRMAccess crmAccess = crmAuthenticator.authenticate();
        final String reqUrl = crmAccess.getInstanceUrl() + opportunityUrl + "/" + opportunityId;
        LOGGER.info("Getting data for opportunityId : {} for url: {}", opportunityId, reqUrl);
        return restTemplate.exchange( reqUrl, HttpMethod.GET,
                buildRequest( RestUtil.createHttpHeader( crmAccess.getAccessToken() ), null ),
                CRMOpportunityResponse.class ).getBody();
    }

    /**
     * Gets the opportunity id by record type id and loan number.
     *
     * @param recordTypeId
     *            the record type
     * @param loanNumber
     *            the loan number
     * @return the opportunity id by record type and loan number
     */
    @Override
    public String getOpportunityIdByRecordTypeIdAndLoanNumber( final String recordTypeId, final int loanNumber ) {
        final QueryParams params = new QueryParams();
        params.add( RECORD_TYPE_ID, recordTypeId );
        params.add( LOAN_NUMBER, String.valueOf( loanNumber ) );
        final Map< String, Object > response = crmQueryService
                .findOne( GET_OPPORTUNITY_ID_BY_RECORDTYPE_ID_AND_LOAN_NUMBER, params );
        return response.get( Constants.QUERY_PARAM_ID ).toString();
    }

    /**
     * Gets the opportunity id by record type id and email.
     *
     * @param recordTypeId
     *            the record type id
     * @param email
     *            the email
     * @return the opportunity id by record type id and email
     */
    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.OpportunityService#
     * getOpportunityIdByRecordTypeIdAndEmail(java.lang.String,
     * java.lang.String)
     */
    @Override
    public String getOpportunityIdByRecordTypeIdAndEmail( final String recordTypeId, final String email ) {
        final QueryParams params = new QueryParams();
        params.add( RECORD_TYPE_ID, recordTypeId );
        params.add( EMAIL, String.valueOf( email ) );
        final Map< String, Object > response = crmQueryService.findOne( GET_OPPORTUNITY_ID_BY_RECORDTYPE_ID_AND_EMAIL,
                params );
        return response.get( Constants.QUERY_PARAM_ID ).toString();
    }

    /**
     * Checks if is opportunity exists for record type and email.
     *
     * @param email
     *            the email
     * @param type
     *            the type
     * @return true, if is opportunity exists for record type and email
     */
    @Override
    public boolean isOpportunityExistsForRecordTypeAndEmail( final String email, final RecordType type ) {
        final String recordTypeName = type.getType();
        final QueryParams params = new QueryParams();
        params.add( EMAIL, email );
        params.add( RECORD_TYPE_NAME, recordTypeName );
        boolean exists = FALSE;
        final CRMResponse response = crmQueryService.findAll( GET_OPPORTUNITY_EMAIL_AND_RECORD_TYPE, params );
        if (response.getTotalSize() > 0) {
            exists = TRUE;
        }
        return exists;
    }

    /**
     * Saves opportunity to database.
     *
     * @param opportunity
     *            the opportunity to be saved
     */
    /*    @Override
    @Transactional( propagation = Propagation.REQUIRED )
    public void save( final Opportunity opportunity ) {
        LOGGER.info( "saving opportunity in DB: " + opportunity.getCrmId() );
        opportunityRepository.save( opportunity );
    }*/
    /*
    *//**
       * Gets the opportunity by opportunity id.
       *
       * @param opportunityId
       *            the opportunity id
       * @return the opportunity by opportunity id
       *//*
        @Override
        public Opportunity getOpportunityByOpportunityId( final String opportunityId ) {
        return opportunityRepository.getOpportunityByOpportunityId( opportunityId );
        }*/

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.OpportunityService#getOpportunityByCrmId(java
     * .lang.String)
     */
    /*    @Override
    public Opportunity getOpportunityByCrmId( final String crmOpportunityId ) {
        return opportunityRepository.findByCrmIdAndDeletedFalse( crmOpportunityId );
    }*/

    /**
     * Gets the opportunity count.
     *
     * @param assignedAgentId
     *            the assigned agent id
     * @param assignedDate
     *            the assigned date
     * @param type
     *            the type
     * @param leadSource
     *            the lead source
     * @return the opportunity count
     */
/*    @Override
    public Integer getOpportunityCount( final String assignedAgentId, final DateTime assignedDate, final String type,
            final List< String > leadSource ) {
        return opportunityRepository
                .countByAssignedAgentIdAndAssignedDateGreaterThanEqualAndDeletedFalseAndTypeAndLeadSourceNotIn(
                        assignedAgentId, assignedDate, type, leadSource );
    }*/

    /**
     * Gets the opportunity count.
     *
     * @param assignedAgentId
     *            the assigned agent id
     * @param assignedDate
     *            the assigned date
     * @param type
     *            the type
     * @return the opportunity count
     */
    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.OpportunityService#
     * countByAssignedAgentIdAndAssignedDateGreaterThanEqualAndDeletedFalseAndType
     * (java.lang.String, org.joda.time.DateTime, java.lang.String)
     */
/*    @Override
    public Integer getOpportunityCount( final String assignedAgentId, final DateTime assignedDate, final String type ) {
        return opportunityRepository.countByAssignedAgentIdAndAssignedDateGreaterThanEqualAndDeletedFalseAndType(
                assignedAgentId, assignedDate, type );
    }*/

    /**
     * Count by assigned agent id and assigned date greater than equal and
     * deleted false and type.
     *
     * @param assignedAgentId
     *            the assigned agent id
     * @param assignedDate
     *            the assigned date
     * @param type
     *            the type
     * @return the long
     */
    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.OpportunityService#
     * countByAssignedAgentIdAndAssignedDateGreaterThanEqualAndDeletedFalseAndType
     * (java.lang.String, org.joda.time.DateTime, java.lang.String)
     */
/*    @Override
    public long countByAssignedAgentIdAndAssignedDateGreaterThanEqualAndDeletedFalseAndType(
            final String assignedAgentId, final DateTime assignedDate, final String type ) {
        return opportunityRepository.countByAssignedAgentIdAndAssignedDateGreaterThanEqualAndDeletedFalseAndType(
                assignedAgentId, assignedDate, type );
    }*/

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.OpportunityService#getAgentOpportunities(java
     * .lang.String)
     */
    @Override
    public List< Opportunity > getAgentOpportunities( final String assignedAgentId ) {
        return opportunityV1Repository.findByAssignedAgentIdAndDeleted( assignedAgentId, Boolean.FALSE );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.OpportunityService#
     * getTitleClosingCompanyByOpportunityId(java.lang.String)
     */
    @Override
    public String getTitleClosingCompanyByOpportunityId( final String opportunityId ) {
        final QueryParams params = new QueryParams();
        params.add( ID, opportunityId );
        final Map< String, Object > response = crmQueryService.findOne( GET_TITLE_CLOSING_COMPANY_BY_OPPORTUNITY_ID,
                params );
        return response.get( TITLE_CLOSING_COMPANY ) != null ? response.get( TITLE_CLOSING_COMPANY ).toString() : null;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.OpportunityService#getOpportunityByFbId(java.
     * lang.String, boolean)
     */
    @Override
    public Opportunity getOpportunityByFbId( final String opportunityId, final boolean isDeleted ) {
        return opportunityV1Repository.findByOpportunityIdAndDeleted( opportunityId, isDeleted );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.OpportunityService#
     * findAssignedAgentEmailBySelectedStagesCount(java.util.Collection, int)
     */
    @Override
    @Transactional( readOnly = true )
    public List< String > findAssignedAgentEmailsBySelectedStagesCount( final Collection< String > selectedStages,
            final Collection< String > emailsIn ) {
        return opportunityV1Repository.findAssignedAgentEmailsBySelectedStagesCount( selectedStages, emailsIn );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.OpportunityService#findAgentsByAssignedDate(
     * org.joda.time.DateTime, java.util.Collection)
     */
    @Override
    @Transactional( readOnly = true )
    public List< AgentDetails > findAgentsByAssignedDate( final DateTime thresholdPeriod,
            final Collection< String > agentEmails ) {
        return opportunityV1Repository.findByAssignedDateAfterAndAgentEmailsIn( thresholdPeriod, agentEmails );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.OpportunityService#
     * getAgentOppCountByAssignedDate(java.lang.String, org.joda.time.DateTime)
     */
    @Override
    @Transactional( readOnly = true )
    public int getAgentOppCountByAssignedDate( final String agentEmail, final DateTime assignedDate ) {
        return opportunityV1Repository.countByAssignedAgentIdAndAssignedDateAfterAndDeletedFalse( agentEmail,
                assignedDate );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.OpportunityService#save(com.owners.gravitas.
     * domain.entity.OpportunityV1)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED )
    public void save( final Opportunity opportunity ) {
        opportunityV1Repository.save( opportunity );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.OpportunityService#getOpportunityByFbId(java.
     * lang.String)
     */
    @Override
    public Opportunity getOpportunityByFbId( final String fbOppId ) {
        return opportunityV1Repository.findByOpportunityId( fbOppId );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.OpportunityService#
     * findResponseTimeByOpportunityIds(java.util.Collection,
     * org.joda.time.DateTime)
     */
    @Override
    @Transactional( readOnly = true )
    public List< Long > findResponseTimeByOpportunityIds( final Collection< String > fbOpportunityIds,
            final DateTime fbAssignedDate ) {
        return opportunityV1Repository.findResponseTimeByOpportunityIds( fbOpportunityIds, fbAssignedDate );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.OpportunityService#
     * findResponseTimeByAssignedAgentAndOpportunityType(java.lang.String,
     * java.lang.String)
     */
    @Override
    @Transactional( readOnly = true )
    public List< Long > findResponseTimeByAssignedAgentAndOpportunityType( final String agentEmail,
            final String opportunityType, final DateTime from, final DateTime to ) {
        return opportunityV1Repository.findResponseTimeByAssignedAgentAndOpportunityType( agentEmail, opportunityType,
                from, to );
    }

    /**
     * Find opportunity by crm id.
     *
     * @param crmId
     *            the crm id
     * @return the opportunity v1
     */
    @Override
    public Opportunity findOpportunityByCrmId( final String crmId ) {
        return opportunityV1Repository.findByCrmId( crmId );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.OpportunityService#
     * findAssignedAgentEmailsBySelectedStagesCountForDefault(java.util.
     * Collection, java.util.Collection)
     */
    @Override
    @Transactional( readOnly = true )
    public List< String > findAssignedAgentEmailsBySelectedStagesCountForDefault(
            final Collection< String > selectedStages, final Collection< String > emailsIn ) {
        return opportunityV1Repository.findAssignedAgentEmailsBySelectedStagesCountForDefault( selectedStages,
                emailsIn );
    }
    
    /* (non-Javadoc)
     * @see com.owners.gravitas.service.OpportunityService#getOpportunityStartAndEndPriceRange(java.lang.String)
     */
    @Override
    public String[] getOpportunityStartAndEndPriceRange( String oppPriceRange ) {
        String[] rangeArray = oppPriceRange.split( Constants.HYPHEN );
        for(int i = 0; i<rangeArray.length;i++) {
            String tempRange = rangeArray[i];
            if(tempRange.contains( Constants.MILLION_CONSTANT)) {
                tempRange  = tempRange.trim() + Constants.MILLION;
            } else if(tempRange.contains( Constants.THOUSAND_CONSTANT))  {
                tempRange = tempRange.trim() + Constants.THOUSAND;
            }
            rangeArray[i] = tempRange.replaceAll( "[\\D]", "" );
        }
        return rangeArray;
    }
}
