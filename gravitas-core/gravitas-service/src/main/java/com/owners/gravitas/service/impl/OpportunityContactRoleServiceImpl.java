package com.owners.gravitas.service.impl;

import static com.owners.gravitas.util.RestUtil.buildRequest;
import static com.owners.gravitas.util.RestUtil.createHttpHeader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.owners.gravitas.dto.crm.request.CRMOpportunityContactRoleRequest;
import com.owners.gravitas.dto.crm.response.CRMAccess;
import com.owners.gravitas.dto.crm.response.OpportunityContactRoleResponse;
import com.owners.gravitas.service.CRMAuthenticatorService;
import com.owners.gravitas.service.OpportunityContactRoleService;

/**
 * The Class OpportunityContactRoleServiceImpl.
 *
 * @author amits
 *
 */
@Service
public class OpportunityContactRoleServiceImpl implements OpportunityContactRoleService {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( OpportunityContactRoleServiceImpl.class );

    /** restTemplate for making rest call to owners. */
    @Autowired
    @Qualifier( "crmRestAuthenticator" )
    private CRMAuthenticatorService crmAuthenticator;

    /** restTemplate for making rest call to owners. */
    @Autowired
    private RestTemplate restTemplate;

    /** The opportunity contact role url. */
    @Value( value = "${salesforce.opportunityContactRole.url}" )
    private String opportunityContactRoleUrl;

    /**
     * Creates the opportunity contact role.
     *
     * @param crmOpportunityContactRoleRequest
     *            the crm opportunity contact role
     * @return the opportunity contact role response
     */
    @Override
    public OpportunityContactRoleResponse createOpportunityContactRole(
            final CRMOpportunityContactRoleRequest crmOpportunityContactRoleRequest ) {
        final CRMAccess crmAccess = crmAuthenticator.authenticate();
        final String reqUrl = crmAccess.getInstanceUrl() + opportunityContactRoleUrl;
        final OpportunityContactRoleResponse opportunityContactRoleResponse = restTemplate
                .exchange( reqUrl, HttpMethod.POST,
                        buildRequest( createHttpHeader( crmAccess.getAccessToken() ), crmOpportunityContactRoleRequest ),
                        OpportunityContactRoleResponse.class )
                .getBody();
        LOGGER.debug( "OpportunityContactRole created " + opportunityContactRoleResponse.getId() );
        return opportunityContactRoleResponse;
    }
}
