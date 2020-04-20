package com.owners.gravitas.service.impl;

import static com.owners.gravitas.util.RestUtil.buildRequest;
import static com.owners.gravitas.util.RestUtil.createHttpHeader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.owners.gravitas.dto.request.ReferralExchangeRequest;
import com.owners.gravitas.dto.response.ReferralExchangeResponse;
import com.owners.gravitas.service.ReferralExchangeService;

/**
 * The Class ReferralExchangeServiceImpl.
 *
 * @author vishwanathm
 */
@Service
public class ReferralExchangeServiceImpl implements ReferralExchangeService {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( ReferralExchangeServiceImpl.class );

    /** The referral exchange url. */
    @Value( "${referral.exchange.api.url}" )
    private String referralExchangeUrl;

    /** The rest template. */
    @Autowired
    private RestTemplate restTemplate;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.ReferralExchangeService#forwardRequest(com.
     * owners.gravitas.dto.request.ReferralExchangeRequest)
     */
    @Override
    public ReferralExchangeResponse forwardRequest( final ReferralExchangeRequest request ) {
        LOGGER.info( "forwarding record to referral exchange " + request.getData().getEmail() );
        return restTemplate.exchange( referralExchangeUrl, HttpMethod.POST,
                buildRequest( createHttpHeader( null ), request ), ReferralExchangeResponse.class ).getBody();
    }
}
