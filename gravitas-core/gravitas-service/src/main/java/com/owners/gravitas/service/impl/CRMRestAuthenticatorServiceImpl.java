package com.owners.gravitas.service.impl;

import static com.owners.gravitas.util.RestUtil.buildRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hubzu.common.cache.service.CacheService;
import com.owners.gravitas.config.ApiConfig;
import com.owners.gravitas.dto.crm.response.CRMAccess;
import com.owners.gravitas.service.CRMAuthenticatorService;

/**
 * The Class CRMRestAuthenticatorServiceImpl make connection to CRM and
 * authenticates the user with provided credential details.
 *
 *
 * @author vishwanathm
 */
@Service("crmRestAuthenticator")
public class CRMRestAuthenticatorServiceImpl implements CRMAuthenticatorService {

    public static final String SALESFORCE_AUTH_TOKEN = "SALESFORCE_AUTH_TOKEN";

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CRMRestAuthenticatorServiceImpl.class);

    /** authentication url. */
    @Value(value = "${salesforce.tokenaccess.url}")
    private String authenticationUrl;

    /** restTemplate for making rest call to owners. */
    @Autowired
    private RestTemplate restTemplate;

    /** salesforce api configuration. */
    @Autowired
    @Qualifier(value = "crmConfig")
    private ApiConfig crmConfig;

    @Autowired
    private CacheService gravitasCacheService;
    
    @Value( "${salesforce.token.cache.default.expiry.in.seconds:600}" )
    private long salesforceTokenCacheExpiry;

    /**
     * This method authenticates to salesforce and gets the {@link CRMAccess}
     * with configured authentication URL and provided credential details.
     *
     * @return the CRM access response
     */
    public CRMAccess authenticate() {
        LOGGER.debug("authentication request to saleforce.");
        Object object = gravitasCacheService.get(SALESFORCE_AUTH_TOKEN);
        if (object != null && object instanceof CRMAccess) {
            LOGGER.debug("Returning salesforce token from cache");
            CRMAccess crmAccess = (CRMAccess) object;
            return crmAccess;
        }
        final String requestUrl = String.format(authenticationUrl, crmConfig.getApiKey(), crmConfig.getApiSecret(),
                crmConfig.getUsername(), crmConfig.getPassword());
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        CRMAccess crmAccess = restTemplate.exchange(requestUrl, HttpMethod.POST, buildRequest(headers, null), CRMAccess.class)
                .getBody();
        if (crmAccess != null && StringUtils.isNotBlank(crmAccess.getAccessToken()) && salesforceTokenCacheExpiry > 0) {
            gravitasCacheService.put(SALESFORCE_AUTH_TOKEN, crmAccess, salesforceTokenCacheExpiry);
        }
        return crmAccess;
    }
}
