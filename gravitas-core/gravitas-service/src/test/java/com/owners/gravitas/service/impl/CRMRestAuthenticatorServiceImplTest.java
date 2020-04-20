package com.owners.gravitas.service.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.hubzu.common.cache.service.CacheService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.ApiConfig;
import com.owners.gravitas.dto.crm.response.CRMAccess;

/**
 * The Class CRMRestAuthenticatorServiceImplTest.
 *
 * @author vishwanathm
 */
public class CRMRestAuthenticatorServiceImplTest extends AbstractBaseMockitoTest {

    /** The crm rest authenticator service impl. */
    @InjectMocks
    private CRMRestAuthenticatorServiceImpl crmRestAuthenticatorServiceImpl;

    /** restTemplate for making rest call to owners. */
    @Mock
    private RestTemplate restTemplate;
    
    @Mock
    private CacheService gravitasCacheService;

    /**
     * Test get crm access.
     */
    @Test
    public void testGetCRMAccess() {
        ReflectionTestUtils.setField( crmRestAuthenticatorServiceImpl, "authenticationUrl", "test" );
        ReflectionTestUtils.setField( crmRestAuthenticatorServiceImpl, "crmConfig",
                new ApiConfig( "test", "test", "test", "test" ) );

        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new CRMAccess(), HttpStatus.OK ) );
        
        Mockito.when( gravitasCacheService.get(CRMRestAuthenticatorServiceImpl.SALESFORCE_AUTH_TOKEN)).thenReturn(null);
        
        Mockito.doNothing().when(gravitasCacheService).put(Mockito.anyString(), Mockito.any(), Mockito.anyLong());

        final CRMAccess access = crmRestAuthenticatorServiceImpl.authenticate();
        Assert.assertNotNull( access );
    }
}
