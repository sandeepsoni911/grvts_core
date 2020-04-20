package com.owners.gravitas.business.handler;

import java.util.ArrayList;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.ContactStatus;
import com.owners.gravitas.dto.crm.response.CRMAccess;
import com.owners.gravitas.dto.crm.response.CRMLeadResponse;
import com.owners.gravitas.repository.ContactStatusRepository;
import com.owners.gravitas.service.CRMAuthenticatorService;
import com.owners.gravitas.service.impl.LeadServiceImpl;

public class LeadProcessingRetryHandlerTest extends AbstractBaseMockitoTest
{
    @InjectMocks
    private LeadServiceImpl leadServiceImpl;
    
    @InjectMocks
    private LeadProcessingRetryHandler leadProcessingRetryHandler;
    
    @InjectMocks
    private ContactStatusRepository contactStatusRepository;
    
    /** mock of {@link RestTemplate}. */
    @Mock
    private RestTemplate restTemplate;

    /** mock of {@link CRMAuthenticatorService}. */
    @Mock
    private CRMAuthenticatorService crmAuthenticator;

    
    /*@Test
    public void retryFailedLeadsTest() 
    {
    	ContactStatus cs = new ContactStatus();
    	cs.setRetryCount(1);
    	CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setId( "112" );
        
        Mockito.when( contactStatusRepository.getContactStatusUnderRetryCount(Mockito.anyInt()) ).thenReturn( new ArrayList<ContactStatus>() );
    	Mockito.when( crmAuthenticator.authenticate() ).thenReturn( new CRMAccess() );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( leadResponse, HttpStatus.OK ) );
        
        String retriedLeads = leadProcessingRetryHandler.retryFailedLeads();
        Assert.notNull(retriedLeads);
        
    }*/

}