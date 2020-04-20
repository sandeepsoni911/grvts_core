package com.owners.gravitas.service.impl;

import static com.owners.gravitas.enums.CRMObject.AGENT;
import static com.owners.gravitas.enums.CRMObject.LEAD;
import static com.owners.gravitas.enums.LeadRequestType.REQUEST_INFORMATION;
import static com.owners.gravitas.enums.RecordType.BUYER;
import static com.owners.gravitas.enums.RecordType.OWNERS_COM;
import static java.lang.System.currentTimeMillis;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import com.owners.gravitas.config.SystemHealthJmxConfig;
import com.owners.gravitas.dao.SystemHealthDao;
import com.owners.gravitas.dto.crm.request.CRMAgentRequest;
import com.owners.gravitas.dto.crm.request.CRMLeadRequest;
import com.owners.gravitas.enums.ErrorCode;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.exception.ResultNotFoundException;
import com.owners.gravitas.service.AccountService;
import com.owners.gravitas.service.AgentService;
import com.owners.gravitas.service.LeadService;
import com.owners.gravitas.service.OpportunityService;
import com.owners.gravitas.service.RecordTypeService;
import com.owners.gravitas.service.SystemHealthService;

/**
 * The Class SystemHealthServiceImpl.
 *
 * @author raviz, ankusht
 */
@Service
public class SystemHealthServiceImpl implements SystemHealthService {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( SystemHealthServiceImpl.class );

    /** The agent service. */
    @Autowired
    private AgentService agentService;

    /** The record type service. */
    @Autowired
    private RecordTypeService recordTypeService;

    /** The lead service. */
    @Autowired
    private LeadService leadService;

    /** The opportunity service. */
    @Autowired
    private OpportunityService opportunityService;

    /** The account service. */
    @Autowired
    private AccountService accountService;

    /** The lead record type id. */
    private String buyerLeadRecordTypeId = null;

    /** The gravitas health dao. */
    @Autowired
    private SystemHealthDao systemHealthDao;

    /** The system health jmx config. */
    @Autowired
    private SystemHealthJmxConfig systemHealthJmxConfig;

    /**
     * Instantiates buyerLeadRecordTypeId.
     */
    @PostConstruct
    private void init() {
        try {
            buyerLeadRecordTypeId = recordTypeService.getRecordTypeIdByName( BUYER.toString(), LEAD.name() );
            if (isBlank( buyerLeadRecordTypeId )) {
                throw new ResultNotFoundException( "" );
            }
        } catch ( final ResultNotFoundException e ) {
            LOGGER.error( "IGNORE: Failed to find Buyer record type Id for Lead type" + ErrorCode.INTERNAL_SERVER_ERROR,
                    e );
            throw new ApplicationException( "Failed to find Buyer record type Id for Lead type",
                    ErrorCode.INTERNAL_SERVER_ERROR );
        }
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.SystemHealthService#createCrmAgent()
     */
    @Override
    @Async
    public void createCrmAgent() {
        LOGGER.debug( "Checking agent create topic status, time " + LocalDateTime.now() );
        try {
            final String agentId = agentService
                    .getCRMAgentIdByEmail( systemHealthJmxConfig.getSystemHealthAgentEmail() );
            if (isNotEmpty( agentId )) {
                // update, if already exists
                final CRMAgentRequest crmAgentRequest = new CRMAgentRequest();
                crmAgentRequest.setEmail( systemHealthJmxConfig.getSystemHealthAgentEmail() );
                crmAgentRequest.setFullName( String.valueOf( currentTimeMillis() ) );
                crmAgentRequest.setAddress1( "address 1" );
                agentService.updateCRMAgent( crmAgentRequest );
            }
        } catch ( final ResultNotFoundException e ) {
            LOGGER.debug( "IGNORE: Create, if doesn't exist", e );
            // create, if doesn't exist
            agentService.createCRMAgent( getCRMAgentRequest() );
        }
    }

    /**
     * Gets the CRM agent request.
     *
     * @return the CRM agent request
     */
    private CRMAgentRequest getCRMAgentRequest() {
        final CRMAgentRequest crmAgentRequest = new CRMAgentRequest();
        crmAgentRequest.setFullName( String.valueOf( currentTimeMillis() ) );
        crmAgentRequest.setEmail( systemHealthJmxConfig.getSystemHealthAgentEmail() );
        crmAgentRequest.setPhone( "1234567890" );
        crmAgentRequest.setStatus( "ACTIVE" );
        crmAgentRequest.setDrivingRadius( "15" );
        crmAgentRequest
                .setRecordTypeId( recordTypeService.getRecordTypeIdByName( OWNERS_COM.getType(), AGENT.getName() ) );
        return crmAgentRequest;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.SystemHealthService#createCrmLead()
     */
    @Override
    @Async
    public void createCrmLead( final String email ) {
        createDefaultLeadIfNotExist( email );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.SystemHealthService#
     * createDefaultLeadIfNotExist(java.lang.String)
     */
    @Override
    public String createDefaultLeadIfNotExist( final String email ) {
        deleteAccountIfExists( email );
        deleteLeadIfExists( email );
        return leadService.createLead( createCRMLeadRequest( email ), false ).getId();
    }

    /**
     * Delete account if exists.
     *
     * @param email
     */
    private void deleteAccountIfExists( final String email ) {
        try {
            final String accountId = accountService.getAccountIdByEmail( email );
            if (isNotBlank( accountId )) {
                accountService.deleteCRMAccount( accountId );
            }
        } catch ( final ResultNotFoundException e ) {
            LOGGER.error( "IGNORE: Account does not exists", e );
            // Do nothing.
        }
    }

    /**
     * Delete lead if exists.
     *
     * @param email
     */
    private void deleteLeadIfExists( final String email ) {
        try {
            final String leadId = leadService.getLeadIdByEmailAndRecordTypeId( email, buyerLeadRecordTypeId );
            leadService.deleteLead( leadId );
        } catch ( final ResultNotFoundException e ) {
            LOGGER.error( "IGNORE: Lead does not exists", e );
            // do nothing if lead doesn't exist
        }
    }

    /**
     * Creates the CRM lead request.
     *
     * @param email
     *            the email
     * @return the CRM lead request
     */
    private CRMLeadRequest createCRMLeadRequest( final String email ) {
        final CRMLeadRequest crmLeadRequest = new CRMLeadRequest();
        crmLeadRequest.setLastName( String.valueOf( currentTimeMillis() ) );
        crmLeadRequest.setSource( "gravitas_health_source" );
        crmLeadRequest.setEmail( email );
        crmLeadRequest.setRequestType( REQUEST_INFORMATION.toString() );
        crmLeadRequest.setRecordType( buyerLeadRecordTypeId );
        crmLeadRequest.setLeadSourceUrl( "http://test.com" );
        crmLeadRequest.setCompany( "company" );
        return crmLeadRequest;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.SystemHealthService#updateCrmOpportunity(java
     * .lang.String)
     */
    @Override
    public void updateCrmOpportunity( final String opportunityId ) {
        final Map< String, Object > patchRequest = new HashMap<>();
        patchRequest.put( "Name", currentTimeMillis() );
        opportunityService.patchOpportunity( patchRequest, opportunityId );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.SystemHealthService#connectToRefDataNode()
     */
    @Override
    public Set< String > connectToRefDataNode() {
        return systemHealthDao.connectToRefDataNode();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.SystemHealthService#executeQueryOnGravitasDB(
     * )
     */
    @Override
    public void executeQueryOnGravitasDB() {
        systemHealthDao.executeQueryOnGravitasDB();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.SystemHealthService#getRabbitMQStatus()
     */
    @Override
    public String getRabbitMQStatus() throws RestClientException, URISyntaxException {
        return systemHealthDao.getRabbitMQStatus();
    }
}
