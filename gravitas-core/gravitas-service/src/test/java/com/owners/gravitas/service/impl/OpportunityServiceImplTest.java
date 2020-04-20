/**
 *
 */
package com.owners.gravitas.service.impl;

import static com.owners.gravitas.constants.Constants.TITLE_CLOSING_COMPANY;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
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
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.domain.entity.Opportunity;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.dto.crm.request.CRMOpportunityRequest;
import com.owners.gravitas.dto.crm.response.CRMAccess;
import com.owners.gravitas.dto.crm.response.CRMOpportunityResponse;
import com.owners.gravitas.dto.crm.response.CRMResponse;
import com.owners.gravitas.dto.crm.response.OpportunityResponse;
import com.owners.gravitas.enums.RecordType;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.repository.OpportunityRepository;
import com.owners.gravitas.service.CRMAuthenticatorService;
import com.owners.gravitas.service.CRMQueryService;

/**
 * The Class OpportunityServiceImplTest.
 *
 * @author harshads
 */
public class OpportunityServiceImplTest extends AbstractBaseMockitoTest {

    /** mock of {@link CRMServiceImpl}. */
    @InjectMocks
    private OpportunityServiceImpl opportunityServiceImpl;

    /** mock of {@link RestTemplate}. */
    @Mock
    private RestTemplate restTemplate;

    /** mock of {@link CRMAuthenticatorService}. */
    @Mock
    private CRMAuthenticatorService crmAuthenticator;

    /** The crm query service. */
    @Mock
    private CRMQueryService crmQueryService;

    /** The opportunity repository. */
    // @Mock
    // private OpportunityRepository opportunityRepository;

    /** The opportunityV1 repository. */
    @Mock
    private OpportunityRepository opportunityV1Repository;

    /** Test create lead with happy flow. */
    @Test
    public void createOpportunityTest() {

        final OpportunityResponse oppResponse = new OpportunityResponse();
        oppResponse.setId( "112" );

        Mockito.when( crmAuthenticator.authenticate() ).thenReturn( new CRMAccess() );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( oppResponse, HttpStatus.OK ) );
        final OpportunityResponse oppResponse1 = opportunityServiceImpl
                .createOpportunity( new CRMOpportunityRequest() );

        Assert.assertEquals( oppResponse1.getId(), "112" );
    }

    /**
     * Checks if is opportunity exists with exception test.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void isOpportunityExistsWithExceptionTest() {
        Mockito.when( crmAuthenticator.authenticate() ).thenReturn( new CRMAccess() );
        final CRMResponse response = new CRMResponse();
        response.setTotalSize( 123 );
        Mockito.when( crmQueryService.findAll( Mockito.anyString(), Mockito.any( QueryParams.class ) ) )
                .thenReturn( response );
        opportunityServiceImpl.isOpportunityExists( "12312" );
    }

    /**
     * Checks if is opportunity exists with test.
     */
    @Test
    public void isOpportunityExistsWithTest() {
        final CRMResponse response = new CRMResponse();
        Mockito.when( crmQueryService.findAll( Mockito.anyString(), Mockito.any( QueryParams.class ) ) )
                .thenReturn( response );
        opportunityServiceImpl.isOpportunityExists( "12312" );
        Mockito.verify( crmQueryService ).findAll( Mockito.anyString(), Mockito.any( QueryParams.class ) );
    }

    /**
     * Test update opportunity.
     */
    @Test
    public void testUpdateOpportunity() {
        Mockito.when( crmAuthenticator.authenticate() ).thenReturn( new CRMAccess() );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new CRMOpportunityResponse(), HttpStatus.OK ) );
        final CRMOpportunityResponse oppResponse1 = opportunityServiceImpl
                .updateOpportunity( new CRMOpportunityRequest(), "opprortunityId", true );

        Assert.assertNotNull( oppResponse1 );
    }

    /**
     * Test get opportunity.
     */
    @Test
    public void testGetOpportunity() {
        Mockito.when( crmAuthenticator.authenticate() ).thenReturn( new CRMAccess() );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new CRMOpportunityResponse(), HttpStatus.OK ) );
        final CRMOpportunityResponse oppResponse1 = opportunityServiceImpl.getOpportunity( "opprortunityId" );

        Assert.assertNotNull( oppResponse1 );
    }

    /**
     * Test patch opportunity.
     */
    @Test
    public void testPatchOpportunity() {
        Mockito.when( crmAuthenticator.authenticate() ).thenReturn( new CRMAccess() );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new CRMOpportunityResponse(), HttpStatus.OK ) );
        opportunityServiceImpl.patchOpportunity( new HashMap< String, Object >(), "opprortunityId" );
        Mockito.verify( crmAuthenticator ).authenticate();
    }

    /**
     * Test patch opportunity.
     */
    @Test
    public void testGetOpportunityIdByRecordTypeIdAndLoanNumber() {
        final Map< String, Object > map = new HashMap<>();
        map.put( "Id", "val" );
        Mockito.when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        final String response = opportunityServiceImpl.getOpportunityIdByRecordTypeIdAndLoanNumber( "opprortunityId",
                0 );
        Assert.assertNotNull( response );
        Assert.assertEquals( response, "val" );
    }

    /**
     * Test patch opportunity.
     */
    @Test
    public void testIsOpportunityExistsForRecordTypeAndEmail() {
        final CRMResponse cr = new CRMResponse();
        Mockito.when( crmQueryService.findAll( anyString(), any( QueryParams.class ) ) ).thenReturn( cr );
        final Boolean response = opportunityServiceImpl.isOpportunityExistsForRecordTypeAndEmail( "opprortunityId",
                RecordType.BOTH );
        Assert.assertFalse( response );
    }

    /**
     * Test get opportunity id by record type id and email.
     */
    @Test
    public void testGetOpportunityIdByRecordTypeIdAndEmail() {
        final String email = "test.user@ownerstest.com";
        final Map< String, Object > map = new HashMap<>();
        map.put( "Id", "val" );
        Mockito.when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( map );
        final String response = opportunityServiceImpl.getOpportunityIdByRecordTypeIdAndEmail( "recordTypeId", email );
        Assert.assertNotNull( response );
        Assert.assertEquals( response, "val" );
    }

    /*    *//**
             * Test get opportunity by opportunity id.
             *//*
              @Test
              public void testGetOpportunityByOpportunityId() {
              final OpportunityV1 opportunity = new OpportunityV1();
              Mockito.when( opportunityRepository.getOpportunityByOpportunityId( Mockito.any() ) ).thenReturn( opportunity );
              final Opportunity response = opportunityServiceImpl.getOpportunityByOpportunityId( "opportunityId" );
              Assert.assertNotNull( response );
              Assert.assertEquals( response, opportunity );
              }*/

    /**
     * Test get opportunity count.
     */
    /*    @Test
    public void testGetOpportunityCount() {
        Mockito.when( opportunityRepository
                .countByAssignedAgentIdAndAssignedDateGreaterThanEqualAndDeletedFalseAndTypeAndLeadSourceNotIn(
                        anyString(), any( DateTime.class ), anyString(), any( List.class ) ) )
                .thenReturn( 1 );
        final int response = opportunityServiceImpl.getOpportunityCount( "assignedAgentId", new DateTime(), "type",
                new ArrayList< String >() );
        Assert.assertNotNull( response );
        Assert.assertEquals( response, 1 );
    }*/

    /**
     * Test get opportunity count without considering lead source.
     */
    /*    @Test
    public void testGetOpportunityCountWithoutConsideringLeadSource() {
        Mockito.when( opportunityRepository.countByAssignedAgentIdAndAssignedDateGreaterThanEqualAndDeletedFalseAndType(
                anyString(), any( DateTime.class ), anyString() ) ).thenReturn( 1 );
        final int response = opportunityServiceImpl.getOpportunityCount( "assignedAgentId", new DateTime(), "type" );
        Assert.assertNotNull( response );
        Assert.assertEquals( response, 1 );
    }*/

    /**
     * Test count by assigned agent id and assigned date greater than equal and
     * deleted false and type.
     */
    /*    @Test
    public void testCountByAssignedAgentIdAndAssignedDateGreaterThanEqualAndDeletedFalseAndType() {
        Mockito.when( opportunityRepository.countByAssignedAgentIdAndAssignedDateGreaterThanEqualAndDeletedFalseAndType(
                anyString(), any( DateTime.class ), anyString() ) ).thenReturn( 1 );
        final long response = opportunityServiceImpl.getOpportunityCount( "assignedAgentId", new DateTime(), "type" );
        Assert.assertNotNull( response );
        Assert.assertEquals( response, 1 );
    }*/

    /**
     * Test save.
     */
    @Test
    public void testSave() {
        final Opportunity opportunity = new Opportunity();
        final Opportunity expectedOpportunity = new Opportunity();
        Mockito.when( opportunityV1Repository.save( opportunity ) ).thenReturn( expectedOpportunity );
        opportunityServiceImpl.save( opportunity );
        Mockito.verify( opportunityV1Repository ).save( opportunity );
    }

    /**
     * Test get opportunity by crm id.
     */
    /*    @Test
    public void testGetOpportunityByCrmId() {
        final String crmId = "crmid";
        final OpportunityV1 expectedOpportunity = new OpportunityV1();
        Mockito.when( opportunityRepository.findByCrmIdAndDeletedFalse( crmId ) ).thenReturn( expectedOpportunity );
        final Opportunity actualOpportunity = opportunityServiceImpl.getOpportunityByCrmId( crmId );
        Assert.assertNotNull( actualOpportunity );
        Assert.assertEquals( actualOpportunity, expectedOpportunity );
        Mockito.verify( opportunityRepository ).findByCrmIdAndDeletedFalse( crmId );
    }*/

    /**
     * Test opportunity count by assigned agent id and assigned date greater
     * than equal and deleted false and type.
     */
    /*    @Test
    public void testOpportunityCountByAssignedAgentIdAndAssignedDateGreaterThanEqualAndDeletedFalseAndType() {
        final String assignedAgentId = "assignedAgentId";
        final DateTime assignedDate = new DateTime();
        final String type = "type";
        final int expectedResponse = 1;
    
        Mockito.when( opportunityRepository.countByAssignedAgentIdAndAssignedDateGreaterThanEqualAndDeletedFalseAndType(
                assignedAgentId, assignedDate, type ) ).thenReturn( expectedResponse );
        final long actualResponse = opportunityServiceImpl
                .countByAssignedAgentIdAndAssignedDateGreaterThanEqualAndDeletedFalseAndType( assignedAgentId,
                        assignedDate, type );
        Assert.assertNotNull( actualResponse );
        Assert.assertEquals( actualResponse, ( long ) expectedResponse );
        Mockito.verify( opportunityRepository )
                .countByAssignedAgentIdAndAssignedDateGreaterThanEqualAndDeletedFalseAndType( assignedAgentId,
                        assignedDate, type );
    }*/

    /**
     * Test get agent opportunities.
     */
    @Test
    public void testGetAgentOpportunities() {
        final String assignedAgentId = "assignedAgentId";
        final List< Opportunity > expectedOpportunities = new ArrayList< Opportunity >();
        Mockito.when( opportunityV1Repository.findByAssignedAgentIdAndDeleted( assignedAgentId, Boolean.FALSE ) )
                .thenReturn( expectedOpportunities );
        final List< Opportunity > actualOpportunities = opportunityServiceImpl
                .getAgentOpportunities( assignedAgentId );
        Assert.assertNotNull( actualOpportunities );
        Assert.assertEquals( actualOpportunities, expectedOpportunities );
        Mockito.verify( opportunityV1Repository ).findByAssignedAgentIdAndDeleted( assignedAgentId, Boolean.FALSE );
    }

    /**
     * Test is opportunity exists for record type and email should return true.
     */
    @Test
    public void testIsOpportunityExistsForRecordTypeAndEmailShouldReturnTrue() {
        final CRMResponse cr = new CRMResponse();
        cr.setTotalSize( 2 );
        when( crmQueryService.findAll( anyString(), any( QueryParams.class ) ) ).thenReturn( cr );
        final Boolean response = opportunityServiceImpl.isOpportunityExistsForRecordTypeAndEmail( "opprortunityId",
                RecordType.BOTH );
        assertTrue( response );
    }

    /**
     * Test get title closing company by opportunity id should return title
     * closing company.
     */
    @Test
    public void testGetTitleClosingCompanyByOpportunityIdShouldReturnTitleClosingCompany() {
        final String opportunityId = "testOpportunityId";
        final String expectedTitleClosing = "test";
        final Map< String, Object > response = new HashMap<>();
        response.put( TITLE_CLOSING_COMPANY, expectedTitleClosing );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( response );
        final String actualTitleClosingCompanyByOpportunityId = opportunityServiceImpl
                .getTitleClosingCompanyByOpportunityId( opportunityId );
        verify( crmQueryService ).findOne( anyString(), any( QueryParams.class ) );
        assertEquals( actualTitleClosingCompanyByOpportunityId, expectedTitleClosing );
    }

    /**
     * Test get title closing company by opportunity id should return null.
     */
    @Test
    public void testGetTitleClosingCompanyByOpportunityIdShouldReturnNull() {
        final String opportunityId = "testOpportunityId";
        final String expectedTitleClosing = "test";
        final Map< String, Object > response = new HashMap<>();
        response.put( "different", expectedTitleClosing );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( response );
        final String actualTitleClosingCompanyByOpportunityId = opportunityServiceImpl
                .getTitleClosingCompanyByOpportunityId( opportunityId );
        verify( crmQueryService ).findOne( anyString(), any( QueryParams.class ) );
        assertNull( actualTitleClosingCompanyByOpportunityId );
    }

    /**
     * Testget opportunity by fb id.
     */
    @Test
    public void testgetOpportunityByFbId() {
        final Opportunity opp = new Opportunity();
        opp.setAssignedAgentId( "testAgent" );
        Mockito.when(
                opportunityV1Repository.findByOpportunityIdAndDeleted( Mockito.anyString(), Mockito.anyBoolean() ) )
                .thenReturn( opp );
        final Opportunity result = opportunityServiceImpl.getOpportunityByFbId( "test", Boolean.FALSE );
        Assert.assertNotNull( result );
        Assert.assertEquals( result.getAssignedAgentId(), opp.getAssignedAgentId() );
        Mockito.verify( opportunityV1Repository ).findByOpportunityIdAndDeleted( Mockito.anyString(),
                Mockito.anyBoolean() );
    }

    /**
     * Test find assigned agent emails by selected stages count.
     */
    @Test
    public void testFindAssignedAgentEmailsBySelectedStagesCount() {
        final Collection< String > selectedStages = new ArrayList<>();
        final Collection< String > emailsIn = new ArrayList<>();
        final List< String > expectedAgentEmails = new ArrayList<>();
        when( opportunityV1Repository.findAssignedAgentEmailsBySelectedStagesCount( selectedStages, emailsIn ) )
                .thenReturn( expectedAgentEmails );
        final List< String > actualAgentEmails = opportunityServiceImpl
                .findAssignedAgentEmailsBySelectedStagesCount( selectedStages, emailsIn );
        assertEquals( actualAgentEmails, expectedAgentEmails );
        verify( opportunityV1Repository ).findAssignedAgentEmailsBySelectedStagesCount( selectedStages, emailsIn );
    }

    /**
     * Test find agents by assigned date.
     */
    @Test
    public void testFindAgentsByAssignedDate() {
        final DateTime thresholdPeriod = new DateTime();
        final Collection< String > agentEmails = new ArrayList<>();
        final List< AgentDetails > expectedAgentDetails = new ArrayList<>();
        when( opportunityV1Repository.findByAssignedDateAfterAndAgentEmailsIn( thresholdPeriod, agentEmails ) )
                .thenReturn( expectedAgentDetails );
        final List< AgentDetails > actualAgentDetails = opportunityServiceImpl
                .findAgentsByAssignedDate( thresholdPeriod, agentEmails );
        assertEquals( actualAgentDetails, expectedAgentDetails );
        verify( opportunityV1Repository ).findByAssignedDateAfterAndAgentEmailsIn( thresholdPeriod, agentEmails );
    }

    /**
     * Test get agent opp count by assigned date.
     */
    @Test
    public void testGetAgentOppCountByAssignedDate() {
        final String agentEmail = "test";
        final DateTime assignedDate = new DateTime();
        final Integer expectedCount = 1;
        when( opportunityV1Repository.countByAssignedAgentIdAndAssignedDateAfterAndDeletedFalse( agentEmail,
                assignedDate ) ).thenReturn( expectedCount );
        final Integer actualCount = opportunityServiceImpl.getAgentOppCountByAssignedDate( agentEmail, assignedDate );
        assertEquals( actualCount, expectedCount );
        verify( opportunityV1Repository ).countByAssignedAgentIdAndAssignedDateAfterAndDeletedFalse( agentEmail,
                assignedDate );
    }
}
