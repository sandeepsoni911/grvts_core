package com.owners.gravitas.service.impl;

import static com.owners.gravitas.util.RestUtil.buildRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.dto.crm.response.CRMAccess;
import com.owners.gravitas.dto.crm.response.CRMResponse;
import com.owners.gravitas.enums.ErrorCode;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.exception.ResultNotFoundException;
import com.owners.gravitas.service.CRMAuthenticatorService;
import com.owners.gravitas.service.CRMQueryService;
import com.owners.gravitas.util.RestUtil;

/**
 * Service implementation for CRM query operation.
 *
 *
 * @author vishwanathm
 *
 */
@Service
public class CRMQueryServiceImpl implements CRMQueryService {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( CRMQueryServiceImpl.class );

    /** The Constant SF_FETCH_LIMIT. */
    private static final int SF_FETCH_LIMIT = 200;

    /** restTemplate for making rest call to owners. */
    @Autowired
    @Qualifier( "crmRestAuthenticator" )
    private CRMAuthenticatorService crmAuthenticator;

    /** restTemplate for making rest call to owners. */
    @Autowired
    private RestTemplate restTemplate;

    /** query url. */
    @Value( value = "${salesforce.query.url}" )
    private String queryUrl;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.CRMQueryService#findOne(java.lang.String,
     * com.owners.gravitas.dto.QueryParams)
     */
    @Override
    public Map< String, Object > findOne( final String query, final QueryParams params ) {
        final CRMResponse crmResponse = findAll( query, params );
        if (crmResponse.getRecords().isEmpty()) {
            LOGGER.warn( ErrorCode.RESULT_NOT_FOUND.getErrorDetail() + " : " + query );
            throw new ResultNotFoundException( ErrorCode.RESULT_NOT_FOUND.getErrorDetail() + " : " + query );
        }
        return crmResponse.getRecords().get( 0 );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.CRMQueryService#findAll(java.lang.String,
     * com.owners.gravitas.dto.QueryParams)
     */
    @Override
    public CRMResponse findAll( final String query, final QueryParams params ) {
        CRMResponse crmResponse = convertToCRMResponse( processQuery( query, params ) );
        if (crmResponse.getTotalSize() > SF_FETCH_LIMIT && crmResponse.getRecords().size() == SF_FETCH_LIMIT) {
            int offset = 0;
            while ( true ) {
                offset += SF_FETCH_LIMIT;
                CRMResponse nextCrmReponse = convertToCRMResponse(
                        processQuery( query + " limit " + SF_FETCH_LIMIT + " offset " + offset, params ) );
                crmResponse.getRecords().addAll( nextCrmReponse.getRecords() );
                if (nextCrmReponse.getTotalSize() < SF_FETCH_LIMIT) {
                    break;
                }
            }
        }
        return crmResponse;
    }

    /**
     * Process query.
     *
     * @param queryStr
     *            the query str
     * @param params
     *            the params
     * @return the string
     */
    private String processQuery( final String queryStr, final QueryParams params ) {
        final CRMAccess crmAccess = crmAuthenticator.authenticate();
        final String reqUrl = crmAccess.getInstanceUrl() + queryUrl + queryStr;

        try {
            return restTemplate.exchange( reqUrl, HttpMethod.GET,
                    buildRequest( RestUtil.createHttpHeader( crmAccess.getAccessToken() ), null ), String.class,
                    params.getParams() ).getBody();
        } catch ( RestClientException rce ) {
            LOGGER.error( "Got exception while posting entity to :{} {}", reqUrl, rce );
            if (rce instanceof HttpStatusCodeException) {
                String errorCause = ( ( HttpStatusCodeException ) rce ).getResponseBodyAsString();
                LOGGER.error( "Error while hitting url :" + reqUrl + " errorCause :" + errorCause, rce );
            }
            throw rce;
        }
    }

    /**
     * Convert to crm response.
     *
     * @param responseStr
     *            the response str
     * @return the CRM response
     */
    private CRMResponse convertToCRMResponse( final String responseStr ) {
        final ObjectMapper mapper = new ObjectMapper();
        final CRMResponse response = new CRMResponse();
        try {
            final Map< String, Object > map = mapper.readValue( responseStr,
                    new TypeReference< HashMap< String, Object > >() {
                    } );
            response.setTotalSize( ( Integer ) map.get( "totalSize" ) );
            response.setDone( ( Boolean ) map.get( "done" ) );
            response.setRecords( ( List< Map< String, Object > > ) map.get( "records" ) );
        } catch ( IOException e ) {
            throw new ApplicationException( "Object to Json transformation failed : " + responseStr.toString(), e,
                    ErrorCode.JSON_CONVERSION_ERROR );
        }
        return response;
    }
}
