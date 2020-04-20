package com.owners.gravitas.service.impl;

import static com.owners.gravitas.constants.CRMQuery.GET_CRM_AGENT_DETAILS_BY_EMAIL;
import static com.owners.gravitas.constants.Constants.ACTION_OBJ;
import static com.owners.gravitas.constants.Constants.EMAIL;
import static com.owners.gravitas.constants.Constants.ENTITY_ID;
import static com.owners.gravitas.constants.NotificationParameters.AGENT_FIRST_NAME;
import static com.owners.gravitas.constants.NotificationParameters.AGENT_FULL_NAME;
import static com.owners.gravitas.constants.NotificationParameters.AGENT_LAST_NAME;
import static com.owners.gravitas.enums.ActionEntity.AGENT;
import static com.owners.gravitas.enums.ActionType.CREATE;
import static com.owners.gravitas.enums.ActionType.DELETE;
import static com.owners.gravitas.util.RestUtil.buildRequest;
import static com.owners.gravitas.util.RestUtil.createHttpHeader;
import static com.owners.gravitas.util.StringUtils.convertObjectToString;
import static com.owners.gravitas.util.StringUtils.removeDoubleQuotes;
import static java.lang.String.valueOf;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.model.TableCell;
import com.google.api.services.bigquery.model.TableDataInsertAllRequest;
import com.google.api.services.bigquery.model.TableDataInsertAllRequest.Rows;
import com.google.api.services.bigquery.model.TableDataList;
import com.google.api.services.bigquery.model.TableRow;
import com.owners.gravitas.annotation.Audit;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.dao.AgentDao;
import com.owners.gravitas.dao.dto.CbsaAgentDetail;
import com.owners.gravitas.domain.Agent;
import com.owners.gravitas.domain.AgentHolder;
import com.owners.gravitas.domain.LastViewed;
import com.owners.gravitas.domain.entity.AgentDetailsV1;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.dto.crm.request.CRMAgentRequest;
import com.owners.gravitas.dto.crm.response.CRMAccess;
import com.owners.gravitas.dto.crm.response.CRMAgentResponse;
import com.owners.gravitas.exception.BigQueryDataException;
import com.owners.gravitas.repository.AgentDetailsRepository;
import com.owners.gravitas.repository.AgentDetailsV1Repository;
import com.owners.gravitas.service.AgentService;
import com.owners.gravitas.service.CRMAuthenticatorService;
import com.owners.gravitas.service.CRMQueryService;
import com.owners.gravitas.service.util.RestServiceUtil;
import com.owners.gravitas.util.RestUtil;
import com.owners.gravitas.util.StringUtils;

/**
 * The Class AgentServiceImpl.
 * 
 * @author ankusht, VISHWA, ABHISHEK
 */
@Service( "agentService" )
public class AgentServiceImpl implements AgentService {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentServiceImpl.class );

    @Value("${gravitas.coshopping_service_api_endpoint}")
    private String coshoppingServiceUrl;
    
    @Autowired
    private RestServiceUtil restServiceUtil;
    
    /** The agent dao. */
    @Autowired
    private AgentDao agentDao;

    /** The bigquery service. */
    @Autowired
    private Bigquery bigqueryService;

    /** The analytics bigquery project id. */
    @Value( "${agent.analytics.bigquery.project}" )
    private String analyticsBigqueryProjectId;

    /** The ids to emails dataset. */
    @Value( "${agent.analytics.bigquery.idsToEmails.dataset}" )
    private String idsToEmailsDataset;

    /** The ids to emails table. */
    @Value( "${agent.analytics.bigquery.idsToEmails.table}" )
    private String idsToEmailsTable;

    /** The fb crm id map dataset. */
    @Value( "${agent.analytics.fbCrmIdMapping.bigquery.dataset}" )
    private String fbCrmIdMapDataset;

    /** The fb crm id map table. */
    @Value( "${agent.analytics.fbCrmIdMapping.bigquery.table}" )
    private String fbCrmIdMapTable;

    /** The crm authenticator. */
    @Autowired
    private CRMAuthenticatorService crmAuthenticator;

    /** The agent url. */
    @Value( value = "${salesforce.agent.url}" )
    private String agentUrl;

    /** The rest template. */
    @Autowired
    private RestTemplate restTemplate;

    /** The agent details repository. */
    @Autowired
    private AgentDetailsRepository agentDetailsRepository;

    /** The agent details v1 repository. */
    @Autowired
    private AgentDetailsV1Repository agentDetailsV1Repository;

    /** The crm query service. */
    @Autowired
    private CRMQueryService crmQueryService;

    /**
     * Save agent.
     *
     * @param agentHolder
     *            the agent holder
     * @return the agent
     */
    @Override
    @Audit( type = CREATE, entity = AGENT, args = { ACTION_OBJ } )
    public Agent saveAgent( final AgentHolder agentHolder ) {
        return agentDao.saveAgent( agentHolder );
    }

    /**
     * Delete agent.
     *
     * @param agentId
     *            the agent id
     * @return the agent
     */
    @Override
    @Audit( type = DELETE, entity = AGENT, args = { ENTITY_ID } )
    public Agent deleteAgent( final String agentId ) {
        return agentDao.deleteAgent( agentId );
    }

    /**
     * Update last viewed.
     *
     * @param agentId
     *            the agent id
     * @param id
     *            the id
     * @param node
     *            the node
     * @return the last viewed
     */
    @Override
    public LastViewed updateLastViewed( final String agentId, final String id, final String node ) {
        return agentDao.updateLastViewed( agentId, id, node );
    }

    /**
     * Gets the all agents id.
     *
     * @return the all agents id
     */
    @Override
    public Set< String > getAllAgentIds() {
        return agentDao.getAllAgentIds();
    }

    /**
     * Gets the agent by id.
     *
     * @param agentId
     *            the agent id
     * @return the agent by id
     */
    @Override
    public Agent getAgentById( final String agentId ) {
        return agentDao.getAgentById( agentId );
    }

    /**
     * Gets the all mapped agent ids.
     *
     * @return the all mapped agent ids
     */
    @Override
    public Map< String, String > getAllMappedAgentIds() {
        final Map< String, String > linkedAgents = new HashMap<>();
        TableDataList list = null;
        try {
            list = bigqueryService.tabledata().list( analyticsBigqueryProjectId, idsToEmailsDataset, idsToEmailsTable )
                    .execute();
        } catch ( final IOException e ) {
            throw new BigQueryDataException( e.getLocalizedMessage(), e );
        }
        if (list != null && CollectionUtils.isNotEmpty( list.getRows() )) {
            final List< TableRow > tableRows = list.getRows();
            tableRows.forEach( tableRow -> {
                final List< TableCell > cells = tableRow.getF();
                if (CollectionUtils.isNotEmpty( cells )) {
                    final String uid = ( cells.get( 1 ).getV() != null ) ? valueOf( cells.get( 1 ).getV() ) : EMPTY;
                    final String email = ( cells.get( 0 ) != null ) ? valueOf( cells.get( 0 ).getV() ) : EMPTY;
                    linkedAgents.put( uid, removeDoubleQuotes( email ) );
                }
            } );
        }
        return linkedAgents;
    }

    /**
     * Gets the agent email by id.
     *
     * @param agentId
     *            the agent id
     * @return the agent email by id
     */
    @Override
    public String getAgentEmailById( final String agentId ) {
        return agentDao.getAgentEmailById( agentId );
    }

    /**
     * Save agent uid mapping.
     *
     * @param rowsList
     *            the rows list
     */
    @Override
    public void saveAgentUidMapping( final List< Rows > rowsList ) {
        try {
            bigqueryService.tabledata().insertAll( analyticsBigqueryProjectId, idsToEmailsDataset, idsToEmailsTable,
                    new TableDataInsertAllRequest().setRows( rowsList ) ).execute();
        } catch ( final IOException e ) {
            throw new BigQueryDataException( e.getLocalizedMessage(), e );
        }
    }

    /**
     * Save opportunity mapping.
     *
     * @param rowsList
     *            the rows list
     */
    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentService#savefBCrmIdMapping(java.util.
     * List)
     */
    @Override
    public void saveOpportunityMapping( final List< Rows > rowsList ) {
        try {
            bigqueryService.tabledata().insertAll( analyticsBigqueryProjectId, fbCrmIdMapDataset, fbCrmIdMapTable,
                    new TableDataInsertAllRequest().setRows( rowsList ) ).execute();
        } catch ( final IOException e ) {
            throw new BigQueryDataException( e.getLocalizedMessage(), e );
        }
    }

    /**
     * Checks if is crm agent available.
     *
     * @param crmAgentId
     *            the crm agent id
     * @return true, if is crm agent available
     */
    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentService#isCrmAgentAvailable(java.lang.
     * String)
     */
    @Override
    public boolean isCrmAgentAvailable( final String crmAgentId ) {
        final CRMAccess crmAccess = crmAuthenticator.authenticate();
        final String reqUrl = crmAccess.getInstanceUrl() + agentUrl + "/" + crmAgentId;
        final Map< String, Object > agentResponse = restTemplate
                .exchange( reqUrl, HttpMethod.GET,
                        buildRequest( RestUtil.createHttpHeader( crmAccess.getAccessToken() ), null ), Map.class )
                .getBody();
        return ( boolean ) agentResponse.get( "Active__c" );
    }

    /**
     * Find active agents for zipcode.
     *
     * @param zipcode
     *            the zipcode
     * @return the list
     */
    @Override
    public List< AgentDetailsV1 > findActiveAgentsByZipcode( final String zipcode ) {
        return agentDetailsV1Repository.findActiveAgentsByZipcode( zipcode );
    }

    /**
     * Gets the crm agent details.
     *
     * @param email
     *            the email
     * @return the crm agent details
     */
    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentService#getCrmAgentDetails(java.lang.
     * String)
     */
    @Override
    public Map< String, Object > getCrmAgentDetails( final String email ) {
        final QueryParams params = new QueryParams();
        params.add( EMAIL, email );
        return crmQueryService.findOne( GET_CRM_AGENT_DETAILS_BY_EMAIL, params );
    }

    /**
     * Creates the crm agent.
     *
     * @param crmAgent
     *            the crm agent
     * @return the CRM agent response
     */
    @Override
    public CRMAgentResponse createCRMAgent( final CRMAgentRequest crmAgent ) {
        final CRMAccess crmAccess = crmAuthenticator.authenticate();
        final String reqUrl = crmAccess.getInstanceUrl() + agentUrl;
        final CRMAgentResponse agentResponse = restTemplate.exchange( reqUrl, HttpMethod.POST,
                buildRequest( RestUtil.createHttpHeader( crmAccess.getAccessToken() ), crmAgent ),
                CRMAgentResponse.class ).getBody();
        LOGGER.debug( "agent created " + agentResponse.getId() + " Time is : " + LocalDateTime.now() );
        return agentResponse;
    }

    /**
     * Update CRM agent.
     *
     * @param crmAgent
     *            the crm agent
     * @return the CRM agent response
     */
    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.AgentService#updateCRMAgent(com.owners.
     * gravitas.dto.crm.request.CRMAgentRequest)
     */
    @Override
    public CRMAgentResponse updateCRMAgent( final CRMAgentRequest crmAgent ) {
        final Map< String, Object > crmAgentDetails = getAgentDetails( crmAgent.getEmail() );
        final String crmAgentId = convertObjectToString( crmAgentDetails.get( "Id" ) );
        final CRMAccess crmAccess = crmAuthenticator.authenticate();
        final String reqUrl = crmAccess.getInstanceUrl() + agentUrl + "/" + crmAgentId;
        final CRMAgentResponse agentResponse = restTemplate.exchange( reqUrl, HttpMethod.PATCH,
                buildRequest( RestUtil.createHttpHeader( crmAccess.getAccessToken() ), crmAgent ),
                CRMAgentResponse.class ).getBody();
        return agentResponse;
    }

    /**
     * Gets the agent details.
     *
     * @param email
     *            the email
     * @return the agent details
     */
    @Override
    public Map< String, Object > getAgentDetails( final String email ) {
        final QueryParams params = new QueryParams();
        params.add( EMAIL, email );
        return crmQueryService.findOne( GET_CRM_AGENT_DETAILS_BY_EMAIL, params );
    }

    /**
     * Delete CRM agent.
     *
     * @param agentId
     *            the agent id
     */
    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentService#deleteCRMAgent(java.lang.String)
     */
    @Override
    public void deleteCRMAgent( final String agentId ) {
        final CRMAccess accessTokenRes = crmAuthenticator.authenticate();
        final String reqUrl = accessTokenRes.getInstanceUrl() + agentUrl + "/" + agentId;
        LOGGER.debug( "sending delete agent request to CRM for agent id:" + agentId );
        restTemplate.exchange( reqUrl, HttpMethod.DELETE,
                buildRequest( RestUtil.createHttpHeader( accessTokenRes.getAccessToken() ), null ),
                CRMAgentResponse.class ).getBody();
    }

    /**
     * Gets the CRM agent id by email.
     *
     * @param emailId
     *            the email id
     * @return the CRM agent id by email
     */
    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentService#getCRMAgentIdByEmail(java.lang.
     * String)
     */
    @Override
    public String getCRMAgentIdByEmail( final String emailId ) {
        return convertObjectToString( getAgentDetails( emailId ).get( "Id" ) );
    }

    /**
     * Patch CRM agent.
     *
     * @param agentRequest
     *            the agent request
     * @param agentId
     *            the agent id
     */
    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentService#patchCRMAgent(java.util.Map,
     * java.lang.String)
     */
    @Override
    public void patchCRMAgent( final Map< String, Object > agentRequest, final String agentId ) {
        final CRMAccess crmAccess = crmAuthenticator.authenticate();
        final String reqUrl = crmAccess.getInstanceUrl() + agentUrl + agentId;
        LOGGER.debug( "sending patch agent request to CRM for id: " + agentId );
        restTemplate.exchange( reqUrl, HttpMethod.PATCH,
                buildRequest( RestUtil.createHttpHeader( crmAccess.getAccessToken() ), agentRequest ), Object.class )
                .getBody();
    }

    /**
     * Update agent score.
     */
    @Override
    public void syncAgentScore() {
        agentDetailsRepository.syncAgentScore();
    }

    /**
     * Gets the CRM agent by id.
     *
     * @param crmAgentId
     *            the crm agent id
     * @return the CRM agent by id
     */
    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.AgentService#getCRMAgentById(java.lang.
     * String)
     */
    @Override
    public CRMAgentResponse getCRMAgentById( final String crmAgentId ) {
        final CRMAccess accessTokenRes = crmAuthenticator.authenticate();
        final String reqUrl = accessTokenRes.getInstanceUrl() + agentUrl + "/" + crmAgentId;
        LOGGER.debug( "sending get agent request to CRM for agent id:" + crmAgentId );
        return restTemplate.exchange( reqUrl, HttpMethod.GET,
                buildRequest( createHttpHeader( accessTokenRes.getAccessToken() ), null ), CRMAgentResponse.class )
                .getBody();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentService#findAvailableAgentsByZips(java.
     * util.Collection)
     */
    @Override
    @Transactional( readOnly = true )
    public List< Object[] > findZipAndAgentsByZip( final String zip ) {
        return agentDetailsRepository.findZipAndAgentsByZip( zip );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentService#findByZipAndAgentEmails(java.
     * lang.String, java.util.Collection)
     */
    @Override
    @Transactional( readOnly = true )
    public List< Object[] > findByZipAndAgentEmails( final String zip, final Collection< String > emails ) {
        return agentDetailsRepository.findByZipAndAgentEmails( zip, emails );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentService#findAgentByCrmOpportunityId(java
     * .lang.String)
     */
    @Override
    @Transactional( readOnly = true )
    public String findAgentByCrmOpportunityId( final String crmOppId ) {
        return agentDetailsRepository.findAgentByCrmOpportunityId( crmOppId );
    }

    /**
     * Gets the cbsa agent details.
     *
     * @param emails
     *            the emails
     * @return the cbsa agent details
     */
    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentService#getCbsaAgentDetails(java.util.
     * Collection)
     */
    @Override
    @Transactional( readOnly = true )
    public List< CbsaAgentDetail > getCbsaAgentDetails( final Collection< String > emails ) {
        return agentDetailsRepository.getCbsaAgentDetails( emails );
    }
    
    @Override
    public Map< String, String > getAgentFirstAndLastName( String agentEmailId ) {
        final Map< String, String > agentMap = new HashMap<>();
        if (StringUtils.isEmailIdValid( agentEmailId )) {
            String[] name = agentEmailId.split( "@" );
            String[] fullName = name[0].split( "\\." );
            if (fullName.length > 1) {
                agentMap.put( AGENT_FIRST_NAME, org.apache.commons.lang3.StringUtils.capitalize( fullName[0] ) );
                agentMap.put( AGENT_LAST_NAME, org.apache.commons.lang3.StringUtils.capitalize( fullName[1] ) );
                agentMap.put( AGENT_FULL_NAME, org.apache.commons.lang3.StringUtils.capitalize( fullName[0] )
                        + Constants.SPACE + org.apache.commons.lang3.StringUtils.capitalize( fullName[1] ) );
            } else if (fullName.length == 1) {
                agentMap.put( AGENT_FIRST_NAME, org.apache.commons.lang3.StringUtils.capitalize( fullName[0] ) );
                agentMap.put( AGENT_FULL_NAME, org.apache.commons.lang3.StringUtils.capitalize( fullName[0] ) );
            }
        }
        return agentMap;
    }
}
