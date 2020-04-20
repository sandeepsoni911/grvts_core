package com.owners.gravitas.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.CrmNote;
import com.owners.gravitas.dto.crm.response.CRMAccess;
import com.owners.gravitas.service.CRMAuthenticatorService;

/**
 * 
 * @author ankusht
 *
 */
public class NoteServiceImplTest extends AbstractBaseMockitoTest {

    @Mock
    @Qualifier( "crmRestAuthenticator" )
    private CRMAuthenticatorService crmAuthenticator;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private NoteServiceImpl noteServiceImpl;

    @DataProvider( name = "getCRMAccess" )
    public Object[][] getCRMAccess() {
        CRMAccess crmAccess = new CRMAccess();
        crmAccess.setInstanceUrl( "dummy url" );
        return new Object[][] { { crmAccess } };
    }

    @Test( dataProvider = "getCRMAccess" )
    public void shouldMakeEntryInNotes( final CRMAccess crmAccess ) {
        when( crmAuthenticator.authenticate() ).thenReturn( crmAccess );
        ResponseEntity< CrmNote > responseEntity = new ResponseEntity< CrmNote >( new CrmNote(), HttpStatus.OK );
        when( restTemplate.exchange( anyString(), any( HttpMethod.class ), any( HttpEntity.class ),
                any( Class.class ) ) ).thenReturn( responseEntity );
        noteServiceImpl.saveNote( new CrmNote( "dummy parent id", "dummy title", "dummy body" ) );
        verify( restTemplate, VerificationModeFactory.times( 1 ) ).exchange( anyString(), any( HttpMethod.class ),
                any( HttpEntity.class ), any( Class.class ) );
    }

}
