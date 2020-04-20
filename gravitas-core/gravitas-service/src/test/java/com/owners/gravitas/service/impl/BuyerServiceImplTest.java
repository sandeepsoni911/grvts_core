package com.owners.gravitas.service.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.BuyerFarmingConfig;
import com.owners.gravitas.dto.request.SavedSearchRequest;
import com.owners.gravitas.dto.response.BuyerDeviceResponse;
import com.owners.gravitas.dto.response.RegistrationResponse;
import com.owners.gravitas.dto.response.SaveSearchResponse;
import com.owners.gravitas.dto.response.SaveSearchResultResponse;
import com.owners.gravitas.util.RestUtil;

public class BuyerServiceImplTest extends AbstractBaseMockitoTest {

    @InjectMocks
    private BuyerServiceImpl buyerServiceImpl;

    @Mock
    private RestTemplate restTemplate;
    /** The config. */
    @Mock
    private BuyerFarmingConfig config;

    /**
     * Test register buyer.
     */
    @Test
    private void testRegisterBuyer() {
        final Map< String, Object > userDetails = new HashMap<>();
        ReflectionTestUtils.setField( buyerServiceImpl, "buyerAutoRegistrationUrl", "http://test.com/test" );
        Mockito.when( restTemplate.exchange( "http://test.com/test", HttpMethod.POST,
                RestUtil.buildRequest( null, userDetails ), RegistrationResponse.class ) )
                .thenReturn( new ResponseEntity< RegistrationResponse >( new RegistrationResponse(), HttpStatus.OK ) );

        buyerServiceImpl.registerBuyer( userDetails );

        Mockito.verify( restTemplate ).exchange( "http://test.com/test", HttpMethod.POST,
                RestUtil.buildRequest( null, userDetails ), RegistrationResponse.class );
    }

    @Test(enabled=false)
    private void testSaveSearch() {
        final SavedSearchRequest req = new SavedSearchRequest();
        ReflectionTestUtils.setField( buyerServiceImpl, "saveSearchUrl", "http://test.com/test" );
        Mockito.when( restTemplate.exchange( "http://test.com/test", HttpMethod.POST,
                RestUtil.buildRequest( null, req ), SaveSearchResponse.class ) )
                .thenReturn( new ResponseEntity< SaveSearchResponse >( new SaveSearchResponse(), HttpStatus.OK ) );

        buyerServiceImpl.saveSearch( req );

        Mockito.verify( restTemplate ).exchange( "http://test.com/test", HttpMethod.POST,
                RestUtil.buildRequest( null, req ), SaveSearchResponse.class );
    }

    @Test
    private void testCheckSaveSearchExists() {
        final String uuid = "uuid";
        final SaveSearchResponse res = new SaveSearchResponse();
        res.setResult( new SaveSearchResultResponse() );

        ReflectionTestUtils.setField( buyerServiceImpl, "checkSaveSearchExists", "http://test.com/test" );
        Mockito.when( restTemplate.getForObject( "http://test.com/test" + uuid, SaveSearchResponse.class, uuid ) )
                .thenReturn( res );

        buyerServiceImpl.checkSaveSearchExists( uuid );

        Mockito.verify( restTemplate ).getForObject( "http://test.com/test" + uuid, SaveSearchResponse.class, uuid );
    }

    @Test
    private void testCheckSaveSearchExists_forExistingSavedSearch() {
        final String uuid = "uuid";
        final SaveSearchResponse res = new SaveSearchResponse();
        final SaveSearchResultResponse ssre = new SaveSearchResultResponse();
        ssre.setSaveSearchCount( 1 );
        res.setResult( ssre );

        ReflectionTestUtils.setField( buyerServiceImpl, "checkSaveSearchExists", "http://test.com/test" );
        Mockito.when( restTemplate.getForObject( "http://test.com/test" + uuid, SaveSearchResponse.class, uuid ) )
                .thenReturn( res );

        final boolean exists = buyerServiceImpl.checkSaveSearchExists( uuid );
        Assert.assertTrue( exists );

        Mockito.verify( restTemplate ).getForObject( "http://test.com/test" + uuid, SaveSearchResponse.class, uuid );
    }

    @Test
    private void testCheckSaveSearchExists_forNonExistingSavedSearch() {
        final String uuid = "uuid";
        final SaveSearchResponse res = new SaveSearchResponse();
        final SaveSearchResultResponse ssre = new SaveSearchResultResponse();
        ssre.setSaveSearchCount( 0 );
        res.setResult( ssre );

        ReflectionTestUtils.setField( buyerServiceImpl, "checkSaveSearchExists", "http://test.com/test" );
        Mockito.when( restTemplate.getForObject( "http://test.com/test" + uuid, SaveSearchResponse.class, uuid ) )
                .thenReturn( res );

        final boolean exists = buyerServiceImpl.checkSaveSearchExists( uuid );
        Assert.assertFalse( exists );

        Mockito.verify( restTemplate ).getForObject( "http://test.com/test" + uuid, SaveSearchResponse.class, uuid );
    }

    /**
     * Test is buyer auto registration email should return true when auto
     * registered email.
     */
    @Test
    public void testIsBuyerAutoRegistrationEmailShouldReturnTrueWhenAutoRegisteredEmail() {
        final String emailStr = "test@test.com";
        final String autoRegEmailStr = "test@test.com,test1@test.com";
        when( config.getBuyerAutoRegistrationEmailStr() ).thenReturn( autoRegEmailStr );
        final boolean result = buyerServiceImpl.isBuyerAutoRegistrationEmail( emailStr );
        assertTrue( result );
        verify( config ).getBuyerAutoRegistrationEmailStr();
    }

    /**
     * Test is buyer auto registration email should return false when not auto
     * registered email.
     */
    @Test
    public void testIsBuyerAutoRegistrationEmailShouldReturnFalseWhenNotAutoRegisteredEmail() {
        final String emailStr = "test@test.com";
        final String autoRegEmailStr = "xyz@test.com";

        when( config.getBuyerAutoRegistrationEmailStr() ).thenReturn( autoRegEmailStr );
        final boolean result = buyerServiceImpl.isBuyerAutoRegistrationEmail( emailStr );
        assertFalse( result );
        verify( config ).getBuyerAutoRegistrationEmailStr();
    }

    /**
     * Test is buyer auto registration email should return true when auto reg
     * email string is blank.
     */
    @Test
    public void testIsBuyerAutoRegistrationEmailShouldReturnTrueWhenAutoRegEmailStringIsBlank() {
        final String emailStr = "test@test.com";
        final String autoRegEmailStr = "";

        when( config.getBuyerAutoRegistrationEmailStr() ).thenReturn( autoRegEmailStr );

        final boolean result = buyerServiceImpl.isBuyerAutoRegistrationEmail( emailStr );

        assertTrue( result );
        verify( config ).getBuyerAutoRegistrationEmailStr();
    }

    /**
     * Test is buyer auto registration email should return true when auto reg
     * email string is null.
     */
    @Test
    public void testIsBuyerAutoRegistrationEmailShouldReturnTrueWhenAutoRegEmailStringIsNull() {
        final String emailStr = "test@test.com";
        final String autoRegEmailStr = null;

        when( config.getBuyerAutoRegistrationEmailStr() ).thenReturn( autoRegEmailStr );

        final boolean result = buyerServiceImpl.isBuyerAutoRegistrationEmail( emailStr );

        assertTrue( result );
        verify( config ).getBuyerAutoRegistrationEmailStr();
    }
    
    @Test
	public void testGetBuyerDeviceDetails() {
        ReflectionTestUtils.setField( buyerServiceImpl, "userDeviceDetailsUrl", "http://test.com/test" );
		Mockito.when(restTemplate.getForObject(anyString(), any(Class.class))).thenReturn("{\"result\":null}");
		BuyerDeviceResponse buyerDeviceResponse = buyerServiceImpl.getBuyerDeviceDetails("gashgasvdjashv");
		assertNotNull(buyerDeviceResponse);
	}
}
