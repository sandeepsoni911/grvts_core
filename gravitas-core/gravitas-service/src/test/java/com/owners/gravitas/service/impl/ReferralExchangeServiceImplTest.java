/**
 *
 */
package com.owners.gravitas.service.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.request.ReferralExchangeRequest;
import com.owners.gravitas.dto.response.ReferralExchangeResponse;

/**
 *
 * Test class for {@link CRMServiceImpl}.
 *
 * @author amits
 *
 */
public class ReferralExchangeServiceImplTest extends AbstractBaseMockitoTest {

    /** mock of {@link CRMServiceImpl}. */
    @InjectMocks
    private ReferralExchangeServiceImpl referralExchangeServiceImpl;

    /** mock of {@link RestTemplate}. */
    @Mock
    private RestTemplate restTemplate;

    @Test
    public void testForwardLead() {
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new ReferralExchangeResponse(), HttpStatus.OK ) );
        ReferralExchangeResponse response = referralExchangeServiceImpl.forwardRequest( new ReferralExchangeRequest() );

        Assert.assertNotNull( response );
    }

}
