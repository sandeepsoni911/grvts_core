package com.owners.gravitas.service.impl;

import static com.owners.gravitas.enums.CRMObject.LEAD;
import static com.owners.gravitas.enums.RecordType.BUYER;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.SystemHealthJmxConfig;
import com.owners.gravitas.dao.SystemHealthDao;
import com.owners.gravitas.dto.crm.request.CRMAgentRequest;
import com.owners.gravitas.dto.crm.request.CRMLeadRequest;
import com.owners.gravitas.dto.crm.response.CRMLeadResponse;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.exception.ResultNotFoundException;
import com.owners.gravitas.service.AccountService;
import com.owners.gravitas.service.AgentService;
import com.owners.gravitas.service.LeadService;
import com.owners.gravitas.service.OpportunityService;
import com.owners.gravitas.service.RecordTypeService;
import com.owners.gravitas.service.TopicStatusService;

/**
 * The Class SystemHealthServiceImplTest.
 *
 * @author ankusht
 */
public class SystemHealthServiceImplTest extends AbstractBaseMockitoTest {

    /** The agent service. */
    @Mock
    private AgentService agentService;

    /** The record type service. */
    @Mock
    private RecordTypeService recordTypeService;

    /** The lead service. */
    @Mock
    private LeadService leadService;

    /** The topic status service. */
    @Mock
    private TopicStatusService topicStatusService;

    /** The opportunity service. */
    @Mock
    private OpportunityService opportunityService;

    /** The account service. */
    @Mock
    private AccountService accountService;

    /** The gravitas health dao. */
    @Mock
    private SystemHealthDao systemHealthDao;

    /** The system health jmx config. */
    @Mock
    private SystemHealthJmxConfig systemHealthJmxConfig;

    /** The system health service impl. */
    @InjectMocks
    private SystemHealthServiceImpl systemHealthServiceImpl;

    /**
     * Test create crm agent should update if default agent already exists.
     */
    @Test
    public void testCreateCrmAgentShouldUpdateIfDefaultAgentAlreadyExists() {
        final String agentEmail = "gravitas_health_agent@test.com";
        final String agentId = "agentId";
        when( systemHealthJmxConfig.getSystemHealthAgentEmail() ).thenReturn( agentEmail );
        when( agentService.getCRMAgentIdByEmail( agentEmail ) ).thenReturn( agentId );
        systemHealthServiceImpl.createCrmAgent();
        verify( agentService ).updateCRMAgent( any( CRMAgentRequest.class ) );
        verify( agentService, times( 0 ) ).createCRMAgent( any( CRMAgentRequest.class ) );
    }

    /**
     * Test create crm agent should create if default agent doesnt exist.
     */
    @Test
    public void testCreateCrmAgentShouldCreateIfDefaultAgentDoesntExist() {
        final String agentEmail = "gravitas_health_agent@test.com";
        when( systemHealthJmxConfig.getSystemHealthAgentEmail() ).thenReturn( agentEmail );
        when( agentService.getCRMAgentIdByEmail( agentEmail ) ).thenThrow( ResultNotFoundException.class );
        systemHealthServiceImpl.createCrmAgent();
        verify( agentService ).createCRMAgent( any( CRMAgentRequest.class ) );
        verify( agentService, times( 0 ) ).updateCRMAgent( any( CRMAgentRequest.class ) );
    }

    /**
     * Test create crm agent should do nothing if default agent id is null.
     */
    @Test
    public void testCreateCrmAgentShouldDoNothingIfDefaultAgentIdIsNull() {
        final String agentEmail = "gravitas_health_agent@test.com";
        when( systemHealthJmxConfig.getSystemHealthAgentEmail() ).thenReturn( agentEmail );
        when( agentService.getCRMAgentIdByEmail( agentEmail ) ).thenReturn( null );
        systemHealthServiceImpl.createCrmAgent();
        verify( agentService, times( 0 ) ).createCRMAgent( any( CRMAgentRequest.class ) );
        verify( agentService, times( 0 ) ).updateCRMAgent( any( CRMAgentRequest.class ) );
    }

    /**
     * Test create crm lead should delete default account and create default
     * lead.
     */
    @Test
    public void testCreateCrmLeadShouldDeleteDefaultAccountAndCreateDefaultLead() {
        final String leadEmail = "gravitas_health_lead@test.com";
        final String buyerLeadRecordTypeId = "buyerLeadRecordTypeId";
        final String accountId = "accountId";
        when( systemHealthJmxConfig.getSystemHealthLeadEmail() ).thenReturn( leadEmail );
        when( recordTypeService.getRecordTypeIdByName( BUYER.toString(), LEAD.name() ) )
                .thenReturn( buyerLeadRecordTypeId );
        ReflectionTestUtils.invokeMethod( systemHealthServiceImpl, "init" );
        when( accountService.getAccountIdByEmail( leadEmail ) ).thenReturn( accountId );
        when( leadService.getLeadIdByEmailAndRecordTypeId( leadEmail, buyerLeadRecordTypeId ) )
                .thenThrow( ResultNotFoundException.class );
        when( leadService.createLead( any( CRMLeadRequest.class ), anyBoolean() ) ).thenReturn( new CRMLeadResponse() );
        systemHealthServiceImpl.createCrmLead( "a@a.com" );
        verify( leadService ).createLead( any( CRMLeadRequest.class ), anyBoolean() );
        verify( recordTypeService ).getRecordTypeIdByName( BUYER.toString(), LEAD.name() );
    }

    /**
     * Test create crm lead should delete default account and create default
     * lead 2.
     */
    @Test
    public void testCreateCrmLeadShouldDeleteDefaultAccountAndCreateDefaultLead2() {
        final String leadEmail = "gravitas_health_lead@test.com";
        final String buyerLeadRecordTypeId = "buyerLeadRecordTypeId";
        final String accountId = "";
        when( recordTypeService.getRecordTypeIdByName( BUYER.toString(), LEAD.name() ) )
                .thenReturn( buyerLeadRecordTypeId );
        ReflectionTestUtils.invokeMethod( systemHealthServiceImpl, "init" );
        when( accountService.getAccountIdByEmail( leadEmail ) ).thenReturn( accountId );
        when( leadService.getLeadIdByEmailAndRecordTypeId( leadEmail, buyerLeadRecordTypeId ) )
                .thenThrow( ResultNotFoundException.class );
        when( leadService.createLead( any( CRMLeadRequest.class ), anyBoolean() ) ).thenReturn( new CRMLeadResponse() );
        systemHealthServiceImpl.createCrmLead( "a@a.com" );
        verify( leadService ).createLead( any( CRMLeadRequest.class ), anyBoolean() );
        verify( recordTypeService ).getRecordTypeIdByName( BUYER.toString(), LEAD.name() );
    }

    /**
     * Test create crm lead should delete default lead and create default lead.
     */
    @Test
    public void testCreateCrmLeadShouldDeleteDefaultLeadAndCreateDefaultLead() {
        final String leadEmail = "gravitas_health_lead@test.com";
        final String buyerLeadRecordTypeId = "buyerLeadRecordTypeId";
        final String leadId = "leadId";
        when( systemHealthJmxConfig.getSystemHealthLeadEmail() ).thenReturn( leadEmail );
        when( recordTypeService.getRecordTypeIdByName( BUYER.toString(), LEAD.name() ) )
                .thenReturn( buyerLeadRecordTypeId );
        ReflectionTestUtils.invokeMethod( systemHealthServiceImpl, "init" );
        when( accountService.getAccountIdByEmail( leadEmail ) ).thenThrow( ResultNotFoundException.class );
        when( leadService.getLeadIdByEmailAndRecordTypeId( "a@a.com", buyerLeadRecordTypeId ) ).thenReturn( leadId );
        when( leadService.createLead( any( CRMLeadRequest.class ), anyBoolean() ) ).thenReturn( new CRMLeadResponse() );
        systemHealthServiceImpl.createCrmLead( "a@a.com" );
        verify( leadService ).deleteLead( leadId );
        verify( leadService ).createLead( any( CRMLeadRequest.class ), anyBoolean() );
        verify( recordTypeService ).getRecordTypeIdByName( BUYER.toString(), LEAD.name() );
    }

    /**
     * Test create crm lead should throw exception id buyer lead record type id
     * is not found.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testCreateCrmLeadShouldThrowExceptionIdBuyerLeadRecordTypeIdIsNotFound() {
        when( recordTypeService.getRecordTypeIdByName( BUYER.toString(), LEAD.name() ) )
                .thenThrow( ResultNotFoundException.class );
        ReflectionTestUtils.invokeMethod( systemHealthServiceImpl, "init" );
        systemHealthServiceImpl.createCrmLead( "a@a.com" );
    }

    /**
     * Test create crm lead should throw exception id buyer lead record type id
     * is null.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testCreateCrmLeadShouldThrowExceptionIdBuyerLeadRecordTypeIdIsNull() {
        when( recordTypeService.getRecordTypeIdByName( BUYER.toString(), LEAD.name() ) ).thenReturn( null );
        ReflectionTestUtils.invokeMethod( systemHealthServiceImpl, "init" );
        systemHealthServiceImpl.createCrmLead( "a@a.com" );
    }

    /**
     * Test create default lead if not exist should create default lead.
     */
    @Test
    public void testCreateDefaultLeadIfNotExistShouldCreateDefaultLead() {
        final String buyerLeadRecordTypeId = "buyerLeadRecordTypeId";
        final CRMLeadResponse response = new CRMLeadResponse();
        final String expected = "id";
        response.setId( expected );
        final String email = "a@a.com";
        when( recordTypeService.getRecordTypeIdByName( BUYER.toString(), LEAD.name() ) )
                .thenReturn( buyerLeadRecordTypeId );
        ReflectionTestUtils.invokeMethod( systemHealthServiceImpl, "init" );
        when( leadService.getLeadIdByEmailAndRecordTypeId( email, buyerLeadRecordTypeId ) )
                .thenThrow( ResultNotFoundException.class );
        when( leadService.createLead( any( CRMLeadRequest.class ), anyBoolean() ) ).thenReturn( response );
        final String actual = systemHealthServiceImpl.createDefaultLeadIfNotExist( email );
        assertEquals( expected, actual );
        verify( leadService ).createLead( any( CRMLeadRequest.class ), anyBoolean() );
        verify( recordTypeService ).getRecordTypeIdByName( BUYER.toString(), LEAD.name() );
        verify( leadService ).getLeadIdByEmailAndRecordTypeId( email, buyerLeadRecordTypeId );
    }

    /**
     * Test create default lead if not exist should return default lead id if
     * already exists.
     */
    @Test
    public void testCreateDefaultLeadIfNotExistShouldReturnDefaultLeadIdIfAlreadyExists() {
        final String buyerLeadRecordTypeId = "buyerLeadRecordTypeId";
        final CRMLeadResponse response = new CRMLeadResponse();
        final String expected = "id";
        response.setId( expected );
        final String email = "a@a.com";
       final CRMLeadResponse crmLeadResponse= new CRMLeadResponse();
       crmLeadResponse.setId( expected );
        when( recordTypeService.getRecordTypeIdByName( BUYER.toString(), LEAD.name() ) )
                .thenReturn( buyerLeadRecordTypeId );
        ReflectionTestUtils.invokeMethod( systemHealthServiceImpl, "init" );
        when( leadService.getLeadIdByEmailAndRecordTypeId( email, buyerLeadRecordTypeId ) ).thenReturn( expected );
        when( leadService.createLead( any( CRMLeadRequest.class ), anyBoolean() ) ).thenReturn(crmLeadResponse );
        final String actual = systemHealthServiceImpl.createDefaultLeadIfNotExist( email );
        assertEquals( expected, actual );
        verify( recordTypeService ).getRecordTypeIdByName( BUYER.toString(), LEAD.name() );
        verify( leadService ).getLeadIdByEmailAndRecordTypeId( email, buyerLeadRecordTypeId );
    }

    /**
     * Test update crm opportunity.
     */
    @Test
    public void testUpdateCrmOpportunity() {
        final String opportunityId = "opportunityId";
        systemHealthServiceImpl.updateCrmOpportunity( opportunityId );
        verify( opportunityService ).patchOpportunity( anyMap(), anyString() );
    }

    /**
     * Test connect to ref data node.
     */
    @Test
    public void testConnectToRefDataNode() {
        final Set< String > expected = new HashSet<>();
        when( systemHealthDao.connectToRefDataNode() ).thenReturn( expected );
        final Set< String > actual = systemHealthServiceImpl.connectToRefDataNode();
        assertEquals( expected, actual );
        verify( systemHealthDao ).connectToRefDataNode();
    }

    /**
     * Test execute query on gravitas DB.
     */
    @Test
    public void testExecuteQueryOnGravitasDB() {
        systemHealthServiceImpl.executeQueryOnGravitasDB();
        verify( systemHealthDao ).executeQueryOnGravitasDB();
    }

    /**
     * Test get rabbit MQ status.
     *
     * @throws RestClientException
     *             the rest client exception
     * @throws URISyntaxException
     *             the URI syntax exception
     */
    @Test
    public void testGetRabbitMQStatus() throws RestClientException, URISyntaxException {
        final String expected = "expected";
        when( systemHealthDao.getRabbitMQStatus() ).thenReturn( expected );
        final String actual = systemHealthServiceImpl.getRabbitMQStatus();
        assertEquals( expected, actual );
        verify( systemHealthDao ).getRabbitMQStatus();
    }

    /**
     * Test create default lead if not exist should create lead when account is
     * available.
     */
    @Test
    public void testCreateDefaultLeadIfNotExistShouldCreateLeadWhenAccountIsAvailable() {
        final String buyerLeadRecordTypeId = "buyerLeadRecordTypeId";
        final CRMLeadResponse response = new CRMLeadResponse();
        final String expected = "id";
        response.setId( expected );
        final String email = "a@a.com";
        final String accountId = "testAccountId";
        when( recordTypeService.getRecordTypeIdByName( BUYER.toString(), LEAD.name() ) )
                .thenReturn( buyerLeadRecordTypeId );
        ReflectionTestUtils.invokeMethod( systemHealthServiceImpl, "init" );
        when( leadService.getLeadIdByEmailAndRecordTypeId( email, buyerLeadRecordTypeId ) )
                .thenThrow( ResultNotFoundException.class );
        when( leadService.createLead( any( CRMLeadRequest.class ), anyBoolean() ) ).thenReturn( response );
        when( accountService.getAccountIdByEmail( email ) ).thenReturn( accountId );
        final String actual = systemHealthServiceImpl.createDefaultLeadIfNotExist( email );
        assertEquals( expected, actual );
        verify( leadService ).createLead( any( CRMLeadRequest.class ), anyBoolean() );
        verify( recordTypeService ).getRecordTypeIdByName( BUYER.toString(), LEAD.name() );
        verify( leadService ).getLeadIdByEmailAndRecordTypeId( email, buyerLeadRecordTypeId );
        verify( accountService ).getAccountIdByEmail( email );
        verify( accountService ).deleteCRMAccount( accountId );
    }
}
