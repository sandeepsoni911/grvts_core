package com.owners.gravitas.service.impl;

import static java.lang.Boolean.TRUE;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.model.TableCell;
import com.google.api.services.bigquery.model.TableDataInsertAllRequest;
import com.google.api.services.bigquery.model.TableDataInsertAllRequest.Rows;
import com.google.api.services.bigquery.model.TableDataList;
import com.google.api.services.bigquery.model.TableRow;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dao.AgentDao;
import com.owners.gravitas.dao.dto.CbsaAgentDetail;
import com.owners.gravitas.domain.Agent;
import com.owners.gravitas.domain.AgentHolder;
import com.owners.gravitas.domain.LastViewed;
import com.owners.gravitas.domain.entity.AgentDetailsV1;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.dto.crm.request.CRMAgentRequest;
import com.owners.gravitas.dto.crm.response.CRMAccess;
import com.owners.gravitas.dto.crm.response.CRMAgentResponse;
import com.owners.gravitas.exception.BigQueryDataException;
import com.owners.gravitas.repository.AgentDetailsRepository;
import com.owners.gravitas.repository.AgentDetailsV1Repository;
import com.owners.gravitas.service.CRMAuthenticatorService;
import com.owners.gravitas.service.CRMQueryService;

/**
 * The Class AgentServiceImplTest.
 *
 * @author vishwanathm
 */
public class AgentServiceImplTest extends AbstractBaseMockitoTest {

    /** The agent service impl. */
    @InjectMocks
    private AgentServiceImpl agentServiceImpl;

    /** The agent dao. */
    @Mock
    private AgentDao agentDao;

    /** The crm authenticator. */
    @Mock
    private CRMAuthenticatorService crmAuthenticator;

    /** The rest template. */
    @Mock
    private RestTemplate restTemplate;

    /** The bigquery service. */
    @Mock
    private Bigquery bigqueryService;

    /** The agent details V 1 repository. */
    @Mock
    private AgentDetailsV1Repository agentDetailsV1Repository;

    /** The crm query service. */
    @Mock
    private CRMQueryService crmQueryService;

    /** The agent details repository. */
    @Mock
    private AgentDetailsRepository agentDetailsRepository;

    /**
     * Test asave agent.
     */
    @Test
    public void testAsaveAgent() {
        final Agent agent = new Agent();
        when( agentDao.saveAgent( Mockito.any( AgentHolder.class ) ) ).thenReturn( agent );
        final Agent actualAgent = agentServiceImpl.saveAgent( new AgentHolder() );
        assertEquals( actualAgent, agent );
        verify( agentDao ).saveAgent( Mockito.any( AgentHolder.class ) );
    }

    /**
     * Test update last viewed.
     */
    @Test
    public void testUpdateLastViewed() {
        final LastViewed lastViewed = new LastViewed();
        when( agentDao.updateLastViewed( Mockito.anyString(), Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( lastViewed );
        final LastViewed actualLastViewed = agentServiceImpl.updateLastViewed( "agentId", "id", "node" );
        assertEquals( actualLastViewed, lastViewed );
        Mockito.verify( agentDao ).updateLastViewed( Mockito.anyString(), Mockito.anyString(), Mockito.anyString() );
    }

    /**
     * Test delete agent.
     */
    @Test
    public void testDeleteAgent() {
        final Agent agent = new Agent();
        when( agentDao.deleteAgent( Mockito.anyString() ) ).thenReturn( agent );
        final Agent actualAgent = agentServiceImpl.deleteAgent( "agentId" );
        assertEquals( actualAgent, agent );
        verify( agentDao ).deleteAgent( Mockito.anyString() );
    }

    /**
     * Test get all agent ids.
     */
    @Test
    public void testGetAllAgentIds() {
        final Set< String > agentIds = new HashSet< String >();
        agentIds.add( "id1" );
        agentIds.add( "id2" );
        Mockito.when( agentDao.getAllAgentIds() ).thenReturn( agentIds );
        final Set< String > availableAgentIds = agentServiceImpl.getAllAgentIds();
        Assert.assertNotNull( availableAgentIds );
        Assert.assertEquals( agentIds, availableAgentIds );
        Mockito.verify( agentDao ).getAllAgentIds();

    }

    /**
     * Test get agent by id.
     */
    @Test
    public void testGetAgentById() {
        final String agentId = "dummyId";
        final Agent expected = new Agent();
        when( agentDao.getAgentById( agentId ) ).thenReturn( expected );
        final Agent actual = agentServiceImpl.getAgentById( agentId );
        assertEquals( expected, actual );
        verify( agentDao ).getAgentById( agentId );
    }

    /**
     * Test get all mapped agent ids for empty table data list.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testGetAllMappedAgentIdsForEmptyTableDataList() throws IOException {
        ReflectionTestUtils.setField( agentServiceImpl, "analyticsBigqueryProjectId", "test" );
        ReflectionTestUtils.setField( agentServiceImpl, "idsToEmailsDataset", "test" );
        ReflectionTestUtils.setField( agentServiceImpl, "idsToEmailsTable", "test" );

        final TableDataList tableDataList = new TableDataList();
        final Bigquery.Tabledata tabledata = mock( Bigquery.Tabledata.class );
        final com.google.api.services.bigquery.Bigquery.Tabledata.List list = mock(
                com.google.api.services.bigquery.Bigquery.Tabledata.List.class );

        when( bigqueryService.tabledata() ).thenReturn( tabledata );
        when( tabledata.list( "test", "test", "test" ) ).thenReturn( list );
        when( list.execute() ).thenReturn( tableDataList );

        final Map< String, String > availableAgentIds = agentServiceImpl.getAllMappedAgentIds();
        Assert.assertNotNull( availableAgentIds );
    }

    /**
     * Test get all mapped agent ids for empty table data non list.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testGetAllMappedAgentIdsForEmptyTableDataNonList() throws IOException {
        ReflectionTestUtils.setField( agentServiceImpl, "analyticsBigqueryProjectId", "test" );
        ReflectionTestUtils.setField( agentServiceImpl, "idsToEmailsDataset", "test" );
        ReflectionTestUtils.setField( agentServiceImpl, "idsToEmailsTable", "test" );

        final List< TableRow > rows = new ArrayList<>();
        final TableRow row = new TableRow();
        final List< TableCell > cells = new ArrayList<>();
        final TableCell cell1 = new TableCell();
        cell1.setV( "email" );
        cells.add( cell1 );
        final TableCell cell2 = new TableCell();
        cell2.setV( "uid" );
        cells.add( cell2 );
        row.setF( cells );
        rows.add( row );

        final TableDataList tableDataList = new TableDataList();
        tableDataList.setRows( rows );
        final Bigquery.Tabledata tabledata = mock( Bigquery.Tabledata.class );
        final com.google.api.services.bigquery.Bigquery.Tabledata.List list = mock(
                com.google.api.services.bigquery.Bigquery.Tabledata.List.class );

        when( bigqueryService.tabledata() ).thenReturn( tabledata );
        when( tabledata.list( "test", "test", "test" ) ).thenReturn( list );
        when( list.execute() ).thenReturn( tableDataList );

        final Map< String, String > availableAgentIds = agentServiceImpl.getAllMappedAgentIds();
        Assert.assertNotNull( availableAgentIds );
    }

    /**
     * Test get all mapped agent ids should return empty response when IO
     * exception is encountered.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test( expectedExceptions = BigQueryDataException.class )
    public void testGetAllMappedAgentIdsShouldReturnEmptyResponseWhenIOExceptionIsEncountered() throws IOException {
        ReflectionTestUtils.setField( agentServiceImpl, "analyticsBigqueryProjectId", "test" );
        ReflectionTestUtils.setField( agentServiceImpl, "idsToEmailsDataset", "test" );
        ReflectionTestUtils.setField( agentServiceImpl, "idsToEmailsTable", "test" );
        final Bigquery.Tabledata tabledata = mock( Bigquery.Tabledata.class );
        when( bigqueryService.tabledata() ).thenReturn( tabledata );
        when( tabledata.list( "test", "test", "test" ) ).thenThrow( IOException.class );
        agentServiceImpl.getAllMappedAgentIds();
    }

    /**
     * Test get all mapped agent ids should return empty response for null table
     * data list.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testGetAllMappedAgentIdsShouldReturnEmptyResponseForNullTableDataList() throws IOException {
        ReflectionTestUtils.setField( agentServiceImpl, "analyticsBigqueryProjectId", "test" );
        ReflectionTestUtils.setField( agentServiceImpl, "idsToEmailsDataset", "test" );
        ReflectionTestUtils.setField( agentServiceImpl, "idsToEmailsTable", "test" );
        final Bigquery.Tabledata tabledata = mock( Bigquery.Tabledata.class );
        final com.google.api.services.bigquery.Bigquery.Tabledata.List list = mock(
                com.google.api.services.bigquery.Bigquery.Tabledata.List.class );
        when( bigqueryService.tabledata() ).thenReturn( tabledata );
        when( tabledata.list( "test", "test", "test" ) ).thenReturn( list );
        when( list.execute() ).thenReturn( null );
        final Map< String, String > availableAgentIds = agentServiceImpl.getAllMappedAgentIds();
        Assert.assertEquals( 0, availableAgentIds.size() );
    }

    /**
     * Test get all mapped agent ids should return empty response when cells are
     * null in table rows.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testGetAllMappedAgentIdsShouldReturnEmptyResponseWhenCellsAreNullInTableRows() throws IOException {
        ReflectionTestUtils.setField( agentServiceImpl, "analyticsBigqueryProjectId", "test" );
        ReflectionTestUtils.setField( agentServiceImpl, "idsToEmailsDataset", "test" );
        ReflectionTestUtils.setField( agentServiceImpl, "idsToEmailsTable", "test" );
        final List< TableRow > rows = new ArrayList<>();
        final TableRow row = new TableRow();
        rows.add( row );

        final TableDataList tableDataList = new TableDataList();
        tableDataList.setRows( rows );
        final Bigquery.Tabledata tabledata = mock( Bigquery.Tabledata.class );
        final com.google.api.services.bigquery.Bigquery.Tabledata.List list = mock(
                com.google.api.services.bigquery.Bigquery.Tabledata.List.class );

        when( bigqueryService.tabledata() ).thenReturn( tabledata );
        when( tabledata.list( "test", "test", "test" ) ).thenReturn( list );
        when( list.execute() ).thenReturn( tableDataList );

        final Map< String, String > availableAgentIds = agentServiceImpl.getAllMappedAgentIds();
        Assert.assertEquals( 0, availableAgentIds.size() );
    }

    /**
     * Test get agent email by id.
     */
    @Test
    public void testGetAgentEmailById() {
        final String agentId = "test";
        Mockito.when( agentDao.getAgentEmailById( agentId ) ).thenReturn( "test" );
        final String actual = agentServiceImpl.getAgentEmailById( agentId );
        assertNotNull( actual );
    }

    /**
     * Test patch crm agent.
     */
    @Test
    public void testPatchCRMAgent() {
        final String agentId = "agentId";
        final Map< String, Object > request = new HashMap<>();
        request.put( "onDuty", TRUE );

        when( crmAuthenticator.authenticate() ).thenReturn( new CRMAccess() );
        when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) ) )
                        .thenReturn( new ResponseEntity( new Object(), HttpStatus.OK ) );
        agentServiceImpl.patchCRMAgent( request, agentId );
        verify( crmAuthenticator ).authenticate();
    }

    /**
     * Test create CRM agent.
     */
    @Test
    public void testCreateCRMAgent() {
        final CRMAgentRequest crmAgentRequest = new CRMAgentRequest();
        final CRMAgentResponse crmAgentResponse = new CRMAgentResponse();
        crmAgentResponse.setId( "id" );
        when( crmAuthenticator.authenticate() ).thenReturn( new CRMAccess() );
        when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( CRMAgentResponse.class.getClass() ) ) )
                        .thenReturn( new ResponseEntity( crmAgentResponse, HttpStatus.OK ) );
        final CRMAgentResponse actualCrmAgentResponse = agentServiceImpl.createCRMAgent( crmAgentRequest );
        verify( crmAuthenticator ).authenticate();
        assertNotNull( actualCrmAgentResponse );
        assertEquals( actualCrmAgentResponse.getId(), "id" );
    }

    /**
     * Test update CRM agent.
     */
    @Test
    public void testUpdateCRMAgent() {
        final CRMAgentRequest crmAgentRequest = new CRMAgentRequest();
        crmAgentRequest.setEmail( "test.user@ownerstest.com" );
        final String agentId = "agentId";
        final Map< String, Object > request = new HashMap<>();
        request.put( "id", "id" );
        when( crmQueryService.findOne( Mockito.anyString(), Mockito.any() ) ).thenReturn( request );
        when( agentServiceImpl.getAgentDetails( crmAgentRequest.getEmail() ) ).thenReturn( request );
        when( crmAuthenticator.authenticate() ).thenReturn( new CRMAccess() );
        when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( CRMAgentResponse.class.getClass() ) ) )
                        .thenReturn( new ResponseEntity( new CRMAgentResponse(), HttpStatus.OK ) );
        agentServiceImpl.updateCRMAgent( crmAgentRequest );
        verify( crmAuthenticator ).authenticate();
    }

    /**
     * Test get crm agent details.
     */
    @Test
    public void testGetCrmAgentDetails() {
        final String email = "test.user@ownerstest.com";
        final Map< String, Object > request = new HashMap<>();
        when( crmQueryService.findOne( Mockito.anyString(), Mockito.any() ) ).thenReturn( request );
        final Map< String, Object > response = agentServiceImpl.getCrmAgentDetails( email );
        assertNotNull( response );
        assertEquals( request, response );
    }

    /**
     * Test is crm agent available.
     */
    @Test
    public void testIsCrmAgentAvailable() {
        final String crmAgentId = "crmAgentId";
        final Map< String, Object > agentResponse = new HashMap< String, Object >();
        agentResponse.put( "Active__c", true );
        when( crmAuthenticator.authenticate() ).thenReturn( new CRMAccess() );
        when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Map.class.getClass() ) ) )
                        .thenReturn( new ResponseEntity( agentResponse, HttpStatus.OK ) );
        final boolean response = agentServiceImpl.isCrmAgentAvailable( crmAgentId );
        verify( crmAuthenticator ).authenticate();
        assertNotNull( response );
    }

    /**
     * Test save agent uid mapping should insert data in big query for valid
     * data.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testSaveAgentUidMappingShouldInsertDataInBigQueryForValidData() throws IOException {
        final List< Rows > rowsList = new ArrayList<>();
        final Bigquery.Tabledata tabledata = mock( Bigquery.Tabledata.class );
        final com.google.api.services.bigquery.Bigquery.Tabledata.InsertAll insertAll = mock(
                com.google.api.services.bigquery.Bigquery.Tabledata.InsertAll.class );
        when( bigqueryService.tabledata() ).thenReturn( tabledata );
        when( tabledata.insertAll( anyString(), anyString(), anyString(), any( TableDataInsertAllRequest.class ) ) )
                .thenReturn( insertAll );
        agentServiceImpl.saveAgentUidMapping( rowsList );
        verify( bigqueryService ).tabledata();
    }

    /**
     * Test save agent uid mapping should throw big query data exception when IO
     * exception is encountered.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test( expectedExceptions = BigQueryDataException.class )
    public void testSaveAgentUidMappingShouldThrowBigQueryDataExceptionWhenIOExceptionIsEncountered()
            throws IOException {
        final List< Rows > rowsList = new ArrayList<>();
        final Bigquery.Tabledata tabledata = mock( Bigquery.Tabledata.class );
        final com.google.api.services.bigquery.Bigquery.Tabledata.InsertAll insertAll = mock(
                com.google.api.services.bigquery.Bigquery.Tabledata.InsertAll.class );
        when( bigqueryService.tabledata() ).thenReturn( tabledata );
        when( tabledata.insertAll( anyString(), anyString(), anyString(), any( TableDataInsertAllRequest.class ) ) )
                .thenThrow( IOException.class );
        agentServiceImpl.saveAgentUidMapping( rowsList );
    }

    /**
     * Test save fb crm id mapping should insert data in big query for valid
     * data.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testSaveFbCrmIdMappingShouldInsertDataInBigQueryForValidData() throws IOException {
        final List< Rows > rowsList = new ArrayList<>();
        final Bigquery.Tabledata tabledata = mock( Bigquery.Tabledata.class );
        final com.google.api.services.bigquery.Bigquery.Tabledata.InsertAll insertAll = mock(
                com.google.api.services.bigquery.Bigquery.Tabledata.InsertAll.class );
        when( bigqueryService.tabledata() ).thenReturn( tabledata );
        when( tabledata.insertAll( anyString(), anyString(), anyString(), any( TableDataInsertAllRequest.class ) ) )
                .thenReturn( insertAll );
        agentServiceImpl.saveOpportunityMapping( rowsList );
        verify( bigqueryService ).tabledata();
    }

    /**
     * Test save fb crm id mapping should throw big query data exception when IO
     * exception is encountered.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test( expectedExceptions = BigQueryDataException.class )
    public void testSaveFbCrmIdMappingShouldThrowBigQueryDataExceptionWhenIOExceptionIsEncountered()
            throws IOException {
        final List< Rows > rowsList = new ArrayList<>();
        final Bigquery.Tabledata tabledata = mock( Bigquery.Tabledata.class );
        final com.google.api.services.bigquery.Bigquery.Tabledata.InsertAll insertAll = mock(
                com.google.api.services.bigquery.Bigquery.Tabledata.InsertAll.class );
        when( bigqueryService.tabledata() ).thenReturn( tabledata );
        when( tabledata.insertAll( anyString(), anyString(), anyString(), any( TableDataInsertAllRequest.class ) ) )
                .thenThrow( IOException.class );
        agentServiceImpl.saveOpportunityMapping( rowsList );
    }

    /**
     * Test delete CRM agent.
     */
    @Test
    public void testDeleteCRMAgent() {
        final String agentId = "agentId";
        when( crmAuthenticator.authenticate() ).thenReturn( new CRMAccess() );
        when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) ) )
                        .thenReturn( new ResponseEntity( new Object(), HttpStatus.OK ) );
        agentServiceImpl.deleteCRMAgent( agentId );
        verify( crmAuthenticator ).authenticate();
    }

    /**
     * Test sync agent score.
     */
    @Test
    public void testSyncAgentScore() {
        Mockito.doNothing().when( agentDetailsRepository ).syncAgentScore();
        agentServiceImpl.syncAgentScore();
        verify( agentDetailsRepository ).syncAgentScore();
    }

    /**
     * Test get agent details.
     */
    @Test
    public void testGetAgentDetails() {
        final Map< String, Object > expectedResponse = new HashMap<>();
        final String email = "testEmail";
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( expectedResponse );
        final Map< String, Object > actualAgentDetails = agentServiceImpl.getAgentDetails( email );
        assertEquals( actualAgentDetails, expectedResponse );
    }

    /**
     * Test get crm agent id by email.
     */
    @Test
    public void testGetCRMAgentIdByEmail() {
        final Map< String, Object > response = new HashMap<>();
        final String expectedCrmAgentId = "test";
        response.put( "Id", expectedCrmAgentId );
        final String email = "testEmail";
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( response );
        final String actualCrmAgentId = agentServiceImpl.getCRMAgentIdByEmail( email );
        assertEquals( actualCrmAgentId, expectedCrmAgentId );
    }

    /**
     * Test get crm agent.
     */
    @Test
    public void testGetCRMAgent() {
        final String agentId = "agentId";
        when( crmAuthenticator.authenticate() ).thenReturn( new CRMAccess() );
        when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) ) )
                        .thenReturn( new ResponseEntity( new CRMAgentResponse(), HttpStatus.OK ) );
        final CRMAgentResponse crmAgent = agentServiceImpl.getCRMAgentById( agentId );
        assertNotNull( crmAgent );
        verify( crmAuthenticator ).authenticate();
    }

    /**
     * Test find active agents by zipcode.
     */
    @Test
    public void testFindActiveAgentsByZipcode() {
        final String zipcode = "test";
        final List< AgentDetailsV1 > actualAgents = new ArrayList<>();
        when( agentDetailsV1Repository.findActiveAgentsByZipcode( zipcode ) ).thenReturn( actualAgents );
        final List< AgentDetailsV1 > expectedAgents = agentServiceImpl.findActiveAgentsByZipcode( zipcode );
        assertEquals( actualAgents, expectedAgents );
        verify( agentDetailsV1Repository ).findActiveAgentsByZipcode( zipcode );
    }

    /**
     * Test find zip and agents by zip.
     */
    @Test
    public void testFindZipAndAgentsByZip() {
        final String zip = "test";
        final List< Object[] > expectedValues = new ArrayList<>();
        when( agentDetailsRepository.findZipAndAgentsByZip( zip ) ).thenReturn( expectedValues );
        final List< Object[] > actualValues = agentServiceImpl.findZipAndAgentsByZip( zip );
        assertEquals( actualValues, expectedValues );
        verify( agentDetailsRepository ).findZipAndAgentsByZip( zip );
    }

    /**
     * Test find agent by crm opportunity id.
     */
    @Test
    public void testFindAgentByCrmOpportunityId() {
        final String crmOppId = "test";
        final String expectedEmail = "test@test.com";
        when( agentDetailsRepository.findAgentByCrmOpportunityId( crmOppId ) ).thenReturn( expectedEmail );
        final String actualEmail = agentServiceImpl.findAgentByCrmOpportunityId( crmOppId );
        assertEquals( actualEmail, expectedEmail );
        verify( agentDetailsRepository ).findAgentByCrmOpportunityId( crmOppId );
    }

    /**
     * Test get cbsa agent details.
     */
    @Test
    public void testGetCbsaAgentDetails() {
        final Collection< String > emails = new ArrayList<>();
        final List< CbsaAgentDetail > expectedAgentDetails = new ArrayList<>();
        when( agentDetailsRepository.getCbsaAgentDetails( emails ) ).thenReturn( expectedAgentDetails );
        final List< CbsaAgentDetail > actualAgentDetails = agentServiceImpl.getCbsaAgentDetails( emails );
        assertEquals( actualAgentDetails, expectedAgentDetails );
        verify( agentDetailsRepository ).getCbsaAgentDetails( emails );
    }
}
