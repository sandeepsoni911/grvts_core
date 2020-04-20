/**
 *
 */
package com.owners.gravitas.service.impl;

import static com.owners.gravitas.constants.Constants.QUERY_PARAM_ACCOUNT_ID;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.dto.crm.request.CRMAccountRequest;
import com.owners.gravitas.dto.crm.response.AccountResponse;
import com.owners.gravitas.dto.crm.response.CRMAccess;
import com.owners.gravitas.exception.ResultNotFoundException;
import com.owners.gravitas.service.CRMAuthenticatorService;
import com.owners.gravitas.service.CRMQueryService;

/**
 * The Class AccountServiceImplTest.
 *
 * @author harshads
 */
public class AccountServiceImplTest extends AbstractBaseMockitoTest {

    /** The account service. */
    @InjectMocks
    private AccountServiceImpl accountService;

    /** restTemplate for making rest call to owners. */
    @Mock
    private CRMAuthenticatorService crmAuthenticator;

    /** restTemplate for making rest call to owners. */
    @Mock
    private RestTemplate restTemplate;

    /** The crm query service. */
    @Mock
    private CRMQueryService crmQueryService;

    /** Test create account. */
    @Test
    public void createAccountTest() {
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setId( "112" );
        when( crmAuthenticator.authenticate() ).thenReturn( new CRMAccess() );
        when( restTemplate.exchange( anyString(), any( HttpMethod.class ), any( HttpEntity.class ),
                any( AccountResponse.class.getClass() ) ) )
                        .thenReturn( new ResponseEntity( accountResponse, HttpStatus.OK ) );
        AccountResponse accountResponse1 = accountService.createAccount( new CRMAccountRequest() );
        assertEquals( accountResponse1.getId(), "112" );
    }

    /**
     * Test delete CRM account.
     */
    @Test
    public void testDeleteCRMAccount() {
        CRMAccess access = new CRMAccess();
        when( crmAuthenticator.authenticate() ).thenReturn( access );
        when( restTemplate.exchange( anyString(), any( HttpMethod.class ), any( HttpEntity.class ),
                any( Class.class ) ) ).thenReturn( new ResponseEntity<>( null ) );
        accountService.deleteCRMAccount( "" );
        verify( restTemplate ).exchange( anyString(), any( HttpMethod.class ), any( HttpEntity.class ),
                any( Class.class ) );
    }

    /**
     * Test find account id by opportunity id.
     */
    @Test
    public void testFindAccountIdByOpportunityId() {
        String crmOpportunityId = "opId";
        Map< String, Object > responseMap = new HashMap<>();
        String expected = "expected";
        responseMap.put( QUERY_PARAM_ACCOUNT_ID, expected );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( responseMap );
        String actual = accountService.findAccountIdByOpportunityId( crmOpportunityId );
        assertEquals( expected, actual );
        verify( crmQueryService ).findOne( anyString(), any( QueryParams.class ) );
    }

    /**
     * Test get account id by email.
     */
    @Test
    public void testGetAccountIdByEmail() {
        String emailId = "a@a.com";
        Map< String, Object > responseMap = new HashMap<>();
        String expected = "expected";
        responseMap.put( QUERY_PARAM_ACCOUNT_ID, expected );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( responseMap );
        String actual = accountService.getAccountIdByEmail( emailId );
        assertEquals( expected, actual );
        verify( crmQueryService ).findOne( anyString(), any( QueryParams.class ) );
    }

    /**
     * Test find account name by id should return name.
     */
    @Test
    public void testFindAccountNameByIdShouldReturnName() {
        final String accountId = "testAccountId";
        final String expectedName = "testName";
        final Map< String, Object > response = new HashMap<>();
        response.put( "Name", expectedName );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( response );
        final String actualName = accountService.findAccountNameById( accountId );
        verify( crmQueryService ).findOne( anyString(), any( QueryParams.class ) );
        assertEquals( actualName, expectedName );
    }

    /**
     * Test find account name by id should return null.
     */
    @Test
    public void testFindAccountNameByIdShouldReturnNull() {
        final String accountId = "testAccountId";
        final String expectedName = "testName";
        final Map< String, Object > response = new HashMap<>();
        response.put( "testDifferent", expectedName );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( response );
        final String actualName = accountService.findAccountNameById( accountId );
        verify( crmQueryService ).findOne( anyString(), any( QueryParams.class ) );
        assertNull( actualName );
    }

    /**
     * Test find account name by id should throw exception.
     */
    @Test( expectedExceptions = ResultNotFoundException.class )
    public void testFindAccountNameByIdShouldThrowException() {
        final String accountId = "testAccountId";
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) )
                .thenThrow( ResultNotFoundException.class );
        accountService.findAccountNameById( accountId );
    }
}
