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
import com.owners.gravitas.dto.crm.request.CRMOpportunityContactRoleRequest;
import com.owners.gravitas.dto.crm.response.CRMAccess;
import com.owners.gravitas.dto.crm.response.OpportunityContactRoleResponse;
import com.owners.gravitas.service.CRMAuthenticatorService;
import com.owners.gravitas.service.CRMQueryService;

/**
 * The Class OpportunityServiceImplTest.
 *
 * @author harshads
 */
public class OpportunityContactRoleServiceImplTest extends AbstractBaseMockitoTest {

    /** mock of {@link CRMServiceImpl}. */
    @InjectMocks
    private OpportunityContactRoleServiceImpl opportunityContactRoleServiceImpl;

    /** mock of {@link RestTemplate}. */
    @Mock
    private RestTemplate restTemplate;

    /** mock of {@link CRMAuthenticatorService}. */
    @Mock
    private CRMAuthenticatorService crmAuthenticator;

    @Mock
    private CRMQueryService crmQueryService;

    /** Test create lead with happy flow. */
    @Test
    public void createOpportunityContactRoleTest() {

        OpportunityContactRoleResponse oppContactRoleResponse = new OpportunityContactRoleResponse();
        oppContactRoleResponse.setId( "112" );

        Mockito.when( crmAuthenticator.authenticate() ).thenReturn( new CRMAccess() );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( oppContactRoleResponse, HttpStatus.OK ) );
        OpportunityContactRoleResponse oppContactRoleResponse1 = opportunityContactRoleServiceImpl
                .createOpportunityContactRole( new CRMOpportunityContactRoleRequest() );

        Assert.assertEquals( oppContactRoleResponse1.getId(), "112" );
    }

}
