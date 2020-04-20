package com.owners.gravitas.service.impl;

import static com.owners.gravitas.constants.CRMQuery.GET_ACCOUNT_ID_BY_EMAIL;
import static com.owners.gravitas.constants.CRMQuery.GET_ACCOUNT_ID_BY_OPPORTUNITY_ID;
import static com.owners.gravitas.constants.CRMQuery.GET_ACCOUNT_NAME_BY_ID;
import static com.owners.gravitas.constants.Constants.EMAIL;
import static com.owners.gravitas.constants.Constants.ID;
import static com.owners.gravitas.constants.Constants.QUERY_PARAM_ACCOUNT_ID;
import static com.owners.gravitas.util.RestUtil.buildRequest;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.dto.crm.request.CRMAccountRequest;
import com.owners.gravitas.dto.crm.response.AccountResponse;
import com.owners.gravitas.dto.crm.response.CRMAccess;
import com.owners.gravitas.service.AccountService;
import com.owners.gravitas.service.CRMAuthenticatorService;
import com.owners.gravitas.service.CRMQueryService;
import com.owners.gravitas.util.RestUtil;

/**
 * The Class AccountServiceImpl.
 *
 * @author vishwanathm
 */
@Service
public class AccountServiceImpl implements AccountService {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AccountServiceImpl.class );

    /** restTemplate for making rest call to owners. */
    @Autowired
    @Qualifier( "crmRestAuthenticator" )
    private CRMAuthenticatorService crmAuthenticator;

    /** restTemplate for making rest call to owners. */
    @Autowired
    private RestTemplate restTemplate;

    /** The crm query service. */
    @Autowired
    private CRMQueryService crmQueryService;

    /** query url. */
    @Value( value = "${salesforce.query.url}" )
    private String queryUrl;

    /** The account url. */
    @Value( value = "${salesforce.account.url}" )
    private String accountUrl;

    /**
     * Creates the account.
     *
     * @param crmAccount
     *            the account
     * @return the string as account id.
     */
    @Override
    public AccountResponse createAccount( final CRMAccountRequest crmAccount ) {
        final CRMAccess crmAccess = crmAuthenticator.authenticate();
        final String reqUrl = crmAccess.getInstanceUrl() + accountUrl;
        final AccountResponse accountResponse = restTemplate.exchange( reqUrl, HttpMethod.POST,
                buildRequest( RestUtil.createHttpHeader( crmAccess.getAccessToken() ), crmAccount ),
                AccountResponse.class ).getBody();
        LOGGER.debug( "account created " + accountResponse.getId() );
        return accountResponse;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AccountService#deleteCRMAccount(java.lang.
     * String)
     */
    @Override
    public void deleteCRMAccount( final String accountId ) {
        final CRMAccess crmAccess = crmAuthenticator.authenticate();
        final String reqUrl = crmAccess.getInstanceUrl() + accountUrl + accountId;
        restTemplate.exchange( reqUrl, HttpMethod.DELETE,
                buildRequest( RestUtil.createHttpHeader( crmAccess.getAccessToken() ), null ), AccountResponse.class )
                .getBody();
        LOGGER.debug( "account Deleted with id " + accountId );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AccountService#findAccountIdByOpportunityId(
     * java.lang.String)
     */
    @Override
    public String findAccountIdByOpportunityId( final String crmOpportunityId ) {
        final QueryParams params = new QueryParams();
        params.add( ID, crmOpportunityId );
        final Map< String, Object > response = crmQueryService.findOne( GET_ACCOUNT_ID_BY_OPPORTUNITY_ID, params );
        return response.get( QUERY_PARAM_ACCOUNT_ID ).toString();
    }

    /**
     * Find account name by id.
     *
     * @param accountId
     *            the account id
     * @return the string
     */
    @Override
    public String findAccountNameById( final String accountId ) {
        final QueryParams params = new QueryParams();
        params.add( ID, accountId );
        final Map< String, Object > response = crmQueryService.findOne( GET_ACCOUNT_NAME_BY_ID, params );
        String accountName = null;
        if (response != null && response.get( "Name" ) != null) {
            accountName = response.get( "Name" ).toString();
        }
        return accountName;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AccountService#getAccountIdByEmail(java.lang.
     * String)
     */
    @Override
    public String getAccountIdByEmail( final String emailId ) {
        final QueryParams params = new QueryParams();
        params.add( EMAIL, emailId );
        final Map< String, Object > response = crmQueryService.findOne( GET_ACCOUNT_ID_BY_EMAIL, params );
        return response.get( QUERY_PARAM_ACCOUNT_ID ).toString();
    }
}
