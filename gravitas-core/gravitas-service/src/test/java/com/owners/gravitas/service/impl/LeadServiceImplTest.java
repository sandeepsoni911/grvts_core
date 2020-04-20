/**
 *
 */
package com.owners.gravitas.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.amazonaws.services.machinelearning.AmazonMachineLearning;
import com.amazonaws.services.machinelearning.model.PredictResult;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.dto.crm.request.CRMLeadRequest;
import com.owners.gravitas.dto.crm.response.CRMAccess;
import com.owners.gravitas.dto.crm.response.CRMConvertedOpportunityResponse;
import com.owners.gravitas.dto.crm.response.CRMLeadResponse;
import com.owners.gravitas.service.CRMAuthenticatorService;
import com.owners.gravitas.service.CRMQueryService;
import com.owners.gravitas.service.RuleRunnerService;

/**
 *
 * Test class for {@link CRMServiceImpl}.
 *
 * @author harshads
 *
 */
public class LeadServiceImplTest extends AbstractBaseMockitoTest {

    /** mock of {@link CRMServiceImpl}. */
    @InjectMocks
    private LeadServiceImpl leadServiceImpl;

    /** mock of {@link RestTemplate}. */
    @Mock
    private RestTemplate restTemplate;

    /** mock of {@link CRMAuthenticatorService}. */
    @Mock
    private CRMAuthenticatorService crmAuthenticator;

    /** The crm query service. */
    @Mock
    private CRMQueryService crmQueryService;

    /** The amc client. */
    @Mock
    private AmazonMachineLearning amlcClient;

    /** The Rule Runner Service. */
    @Mock
    private RuleRunnerService ruleRunnerService;

    /** Test create lead with happy flow. */
    @Test
    public void createLeadTest() {
        CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setId( "112" );
        Mockito.when( crmAuthenticator.authenticate() ).thenReturn( new CRMAccess() );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( leadResponse, HttpStatus.OK ) );
        CRMLeadResponse leadResponse1 = leadServiceImpl.createLead( new CRMLeadRequest() );

        Assert.assertEquals( leadResponse1.getId(), "112" );
    }

    @Test
    public void testUpdateLead() {
        CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setId( "112" );
        Mockito.when( crmAuthenticator.authenticate() ).thenReturn( new CRMAccess() );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( leadResponse, HttpStatus.OK ) );
        leadServiceImpl.updateLead( new CRMLeadRequest(), "leadId", true );

        Assert.assertEquals( leadResponse.getId(), "112" );
    }

    @Test
    public void testGetLead() {
        CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setId( "112" );
        Mockito.when( crmAuthenticator.authenticate() ).thenReturn( new CRMAccess() );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( leadResponse, HttpStatus.OK ) );
        leadServiceImpl.getLead( "leadId" );
        Assert.assertEquals( leadResponse.getId(), "112" );
    }

    /**
     * Test update lead by map.
     */
    @Test
    public void testUpdateLeadByMap() {
        CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setId( "112" );
        Mockito.when( crmAuthenticator.authenticate() ).thenReturn( new CRMAccess() );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( leadResponse, HttpStatus.OK ) );
        final Map< String, Object > map = new HashMap();
        leadServiceImpl.updateLead( map, "leadId " );

        Assert.assertEquals( leadResponse.getId(), "112" );
    }

    /**
     * Test update lead by map.
     */
    @Test
    public void testUpdateLeadWithDisabledAssignmentRulesByMap() {
        CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setId( "112" );
        Mockito.when( crmAuthenticator.authenticate() ).thenReturn( new CRMAccess() );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( leadResponse, HttpStatus.OK ) );
        final Map< String, Object > map = new HashMap();
        leadServiceImpl.updateLead( map, "leadId ", false );

        Assert.assertEquals( leadResponse.getId(), "112" );
    }

    /**
     * Test get lead id by email and record type id.
     */
    @Test
    public void testgetLeadIdByEmailAndRecordTypeId() {
        final QueryParams params = new QueryParams();
        Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Id", "test" );
        Mockito.when( crmQueryService.findOne( "test", params ) ).thenReturn( map );

        String str = leadServiceImpl.getLeadIdByEmailAndRecordTypeId( "Id", "test" );
    }

    /**
     * Test convert lead to opportunity.
     */
    @Test
    public void testconvertLeadToOpportunity() {
        CRMConvertedOpportunityResponse response = new CRMConvertedOpportunityResponse();
        response.setResult( "test" );
        Mockito.when( crmAuthenticator.authenticate() ).thenReturn( new CRMAccess() );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( response, HttpStatus.OK ) );
        final Map< String, Object > map = new HashMap();
        leadServiceImpl.convertLeadToOpportunity( "leadId " );

        Assert.assertEquals( response.getResult(), "test" );
    }

    /**
     * Test create lead.
     */
    @Test
    public void testCreateLead() {
        final CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setId( "112" );
        Mockito.when( crmAuthenticator.authenticate() ).thenReturn( new CRMAccess() );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( leadResponse, HttpStatus.OK ) );
        CRMLeadResponse expectedLeadResponse = leadServiceImpl.createLead( new CRMLeadRequest(), true );

        Assert.assertEquals( expectedLeadResponse.getId(), "112" );
        Mockito.verify( crmAuthenticator ).authenticate();
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) );
    }

    /**
     * Test delete lead.
     */
    @Test
    public void testDeleteLead() {
        final CRMLeadResponse leadResponse = new CRMLeadResponse();
        final String leadId = "testLeadId";
        Mockito.when( crmAuthenticator.authenticate() ).thenReturn( new CRMAccess() );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( leadResponse, HttpStatus.OK ) );
        leadServiceImpl.deleteLead( leadId );
        Mockito.verify( crmAuthenticator ).authenticate();
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) );
    }

    @Test
    public void testGetOpportunityScore() {
        Mockito.when( amlcClient.predict( Mockito.any() ) ).thenReturn( new PredictResult() );
        PredictResult result = leadServiceImpl.getLeadScore( new HashMap< String, String >() );
        Assert.assertNotNull( result );
    }

    /**
     * Test Is Valid Phone No.
     */
    @Test
    public void testIsValidPhoneNoPatternWithValidPhoneNo() {
        Contact contact = new Contact();
        contact.setEmail( "dummy@gmail.com" );
        contact.setPhone( "080112345" );
        boolean leadHasvalidPhNo = leadServiceImpl.isValidPhoneNumber( contact );
        Assert.assertTrue( leadHasvalidPhNo );
    }
}
