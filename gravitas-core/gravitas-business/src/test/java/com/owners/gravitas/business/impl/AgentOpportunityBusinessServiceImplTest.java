/*
 *
 */
package com.owners.gravitas.business.impl;

import static com.owners.gravitas.enums.BuyerFarmType.ACTIVE_BUYER;
import static com.owners.gravitas.enums.CRMObject.LEAD;
import static com.owners.gravitas.enums.CRMObject.OPPORTUNITY;
import static com.owners.gravitas.enums.LeadStatus.FORWARDED_TO_REF_EX;
import static com.owners.gravitas.enums.LeadStatus.NEW;
import static com.owners.gravitas.enums.LeadStatus.OPEN;
import static com.owners.gravitas.enums.OpportunityChangeType.Stage;
import static com.owners.gravitas.enums.RecordType.BUYER;
import static java.lang.Boolean.FALSE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.flowable.engine.RuntimeService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.amazonaws.services.machinelearning.model.PredictResult;
import com.amazonaws.services.machinelearning.model.Prediction;
import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.ActionFlowBusinessService;
import com.owners.gravitas.business.AgentLookupBusinessService;
import com.owners.gravitas.business.AgentNotificationBusinessService;
import com.owners.gravitas.business.AgentReminderBusinessService;
import com.owners.gravitas.business.AgentTaskBusinessService;
import com.owners.gravitas.business.BuyerFarmingBusinessService;
import com.owners.gravitas.business.LeadBusinessService;
import com.owners.gravitas.business.OpportunityBusinessService;
import com.owners.gravitas.business.builder.ContactBuilder;
import com.owners.gravitas.business.builder.OppAssignmentBuyerNotificationBuilder;
import com.owners.gravitas.business.builder.domain.ActionGroupBuilder;
import com.owners.gravitas.business.builder.domain.OpportunityBuilder;
import com.owners.gravitas.business.builder.request.ReferralExchangeOpportunityRequestBuilder;
import com.owners.gravitas.business.builder.request.ReferralExchangeRequestEmailBuilder;
import com.owners.gravitas.business.task.OpportunityTask;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.ActionFlowConfig;
import com.owners.gravitas.config.AgentOpportunityBusinessConfig;
import com.owners.gravitas.config.BadgeCounterJmxConfig;
import com.owners.gravitas.config.CoShoppingConfig;
import com.owners.gravitas.config.HappyAgentsConfig;
import com.owners.gravitas.config.HungryAgentsConfig;
import com.owners.gravitas.config.LeadBusinessConfig;
import com.owners.gravitas.config.LeadOpportunityBusinessConfig;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.domain.Reminder;
import com.owners.gravitas.domain.Request;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.domain.Stage;
import com.owners.gravitas.domain.Task;
import com.owners.gravitas.domain.entity.AgentCommission;
import com.owners.gravitas.domain.entity.ContactAttribute;
import com.owners.gravitas.domain.entity.ObjectAttributeConfig;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.dto.request.NotificationRequest;
import com.owners.gravitas.dto.response.AgentEmailPermissionResponse;
import com.owners.gravitas.dto.response.AgentResponse;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.LeadResponse;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.enums.BuyerFarmType;
import com.owners.gravitas.enums.BuyerStage;
import com.owners.gravitas.enums.RecordType;
import com.owners.gravitas.enums.SellerStage;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.handler.InContactStageChangeHandler;
import com.owners.gravitas.handler.OpportunityChangeHandler;
import com.owners.gravitas.handler.OpportunityChangeHandlerFactory;
import com.owners.gravitas.service.ActionGroupService;
import com.owners.gravitas.service.AgentCommissionService;
import com.owners.gravitas.service.AgentContactService;
import com.owners.gravitas.service.AgentOpportunityService;
import com.owners.gravitas.service.AgentRequestService;
import com.owners.gravitas.service.AgentTaskService;
import com.owners.gravitas.service.CRMQueryService;
import com.owners.gravitas.service.ContactEntityService;
import com.owners.gravitas.service.MailService;
import com.owners.gravitas.service.OpportunityService;
import com.owners.gravitas.service.RecordTypeService;
import com.owners.gravitas.service.ReferralExchangeService;
import com.owners.gravitas.service.SearchService;
import com.owners.gravitas.service.StageLogService;
import com.owners.gravitas.service.builder.AuditTrailOpportunityBuilder;

import junit.framework.Assert;

/**
 * The Class AgentOpportunityBusinessServiceImpl.
 *
 * @author shivamm, ankusht
 */
public class AgentOpportunityBusinessServiceImplTest extends AbstractBaseMockitoTest {

    /** The agent opportunity business service impl. */
    @InjectMocks
    private AgentOpportunityBusinessServiceImpl agentOpportunityBusinessServiceImpl;

    /** The hungry agents config. */
    @Mock
    private HungryAgentsConfig hungryAgentsConfig;

    /** The agent lookup business service. */
    @Mock
    private AgentLookupBusinessService agentLookupBusinessService;

    /** The opportunity change handler. */
    @Mock
    private OpportunityChangeHandler opportunityChangeHandler;

    /** The happy agents config. */
    @Mock
    private HappyAgentsConfig happyAgentsConfig;

    /** The agent opportunity service. */
    @Mock
    private AgentOpportunityService agentOpportunityService;

    /** The agent contact service. */
    @Mock
    private AgentContactService agentContactService;

    /** The opportunity service. */
    @Mock
    private OpportunityService opportunityService;
    /** The agent task service. */
    @Mock
    private AgentTaskService agentTaskService;

    /** The agent request service. */
    @Mock
    private AgentRequestService agentRequestService;

    /** The search service. */
    @Mock
    private SearchService searchService;

    /** The opportunity business service. */
    @Mock
    private OpportunityBusinessService opportunityBusinessService;

    /** The agent push notification business service. */
    @Mock
    private AgentNotificationBusinessService agentNotificationBusinessService;

    /** The opportunity builder. */
    @Mock
    private OpportunityBuilder opportunityBuilder;

    /** The action group builder. */
    @Mock
    private ActionGroupBuilder actionGroupBuilder;

    /** The action group service. */
    @Mock
    private ActionGroupService actionGroupService;

    /** The agent request business service impl. */
    @Mock
    private AgentRequestBusinessServiceImpl agentRequestBusinessServiceImpl;

    /** The OpportunityChangeHandlerFactory *. */
    @Mock
    private OpportunityChangeHandlerFactory opportunityChangeHandlerFactory;

    /** The agent reminder business service. */
    @Mock
    private AgentReminderBusinessService agentReminderBusinessService;

    /** The referral exchange opportunity request builder. */
    @Mock
    private ReferralExchangeOpportunityRequestBuilder referralExchangeOpportunityRequestBuilder;

    /** The referral exchange service. */
    @Mock
    private ReferralExchangeService referralExchangeService;

    /** The lead business service. */
    @Mock
    private LeadBusinessService leadBusinessService;

    /** The record type service. */
    @Mock
    private RecordTypeService recordTypeService;

    /** The agent opportunity business config. */
    @Mock
    private AgentOpportunityBusinessConfig agentOpportunityBusinessConfig;

    /** The in contact stage change handler. */
    @Mock
    private InContactStageChangeHandler inContactStageChangeHandler;

    /** The crm query service. */
    @Mock
    private CRMQueryService crmQueryService;

    /** The referral exchange request email builder. */
    @Mock
    private ReferralExchangeRequestEmailBuilder referralExchangeRequestEmailBuilder;

    /** The mail service. */
    @Mock
    private MailService mailService;

    /** The contact builder. */
    @Mock
    private ContactBuilder contactBuilder;

    /** The audit trail opportunity builder. */
    @Mock
    private AuditTrailOpportunityBuilder auditTrailOpportunityBuilder;

    /** The lead business config. */
    @Mock
    private LeadBusinessConfig leadBusinessConfig;

    /** The opportunity task. */
    @Mock
    private OpportunityTask opportunityTask;

    /** The lead opportunity business config. */
    @Mock
    private LeadOpportunityBusinessConfig leadOpportunityBusinessConfig;

    /** The action flow config. */
    @Mock
    private ActionFlowConfig actionFlowConfig;

    /** The badge counter jmx config. */
    @Mock
    private BadgeCounterJmxConfig badgeCounterJmxConfig;

    /** The stage log service. */
    @Mock
    private StageLogService stageLogService;

    /** The action flow business service. */
    @Mock
    private ActionFlowBusinessService actionFlowBusinessService;

    /** The buyer farming business service. */
    @Mock
    private BuyerFarmingBusinessService buyerFarmingBusinessService;

    /** The agent task business service. */
    @Mock
    private AgentTaskBusinessService agentTaskBusinessService;

    /** The contact service V 1. */
    @Mock
    private AgentCommissionService agentCommissionService;

    /** The contact service v1. */
    @Mock
    private ContactEntityService contactServiceV1;

    /** The request. */
    @Mock
    private HttpServletRequest httpRequest;
    
    /** The runtime service. */
    @Mock
    protected RuntimeService runtimeService;
    
    @Mock
    protected CoShoppingConfig coShoppingconfig;
    
    @Mock
    protected OppAssignmentBuyerNotificationBuilder oppAssignmentBuyerNotificationBuilder;
    
    /**
     * Gets the predict result and records map.
     *
     * @return the predict result and records map
     */
    @DataProvider( name = "getPredictResultAndRecordsMap" )
    public Object[][] getPredictResultAndRecordsMap() {
        final Map< String, Float > predictedScores = new HashMap<>();
        predictedScores.put( "test", 3.0f );
        final Prediction prediction = new Prediction();
        prediction.setPredictedScores( predictedScores );
        prediction.setPredictedLabel( "test" );
        final PredictResult predictResult = new PredictResult();
        predictResult.setPrediction( prediction );
        final Map< String, String > recordsMap = new HashMap< String, String >();
        return new Object[][] { { predictResult, recordsMap } };
    }

    /**
     * Gets the required data.
     *
     * @return the required data
     */
    @DataProvider( name = "getRequiredData" )
    public Object[][] getRequiredData() {
        final Map< String, Float > predictedScores = new HashMap<>();
        predictedScores.put( "1", 3.0f );
        final Prediction prediction = new Prediction();
        prediction.setPredictedScores( predictedScores );
        prediction.setPredictedLabel( "1" );
        final PredictResult predictResult = new PredictResult();
        predictResult.setPrediction( prediction );

        final Map< String, String > recordsMap = new HashMap< String, String >();
        final Search search = new Search();
        final Opportunity opportunity = new Opportunity();
        final PostResponse postResponse = new PostResponse();

        final Contact primaryContact = new Contact();
        final List< String > emails = new ArrayList< String >();
        emails.add( "email1@email.com" );
        primaryContact.setEmails( emails );
        return new Object[][] { { predictResult, recordsMap, search, opportunity, postResponse, primaryContact } };
    }

    /**
     * Test patch opportunity with result size greater then zero.
     */
    @Test
    public void testPatchOpportunity_WithResultSizeGreaterThenZero() {
        final String agentId = "test";
        final String opportunityId = "test";
        final Map< String, Object > request = new HashMap<>();
        request.put( "stage", "test" );
        request.put( "reasonLost", "other" );
        request.put( "reasonLostDetails", "" );
        final Opportunity opportunity = new Opportunity();
        final Set< String > contacts = new HashSet<>();
        contacts.add( "1234" );
        opportunity.setContacts( contacts );
        opportunity.setOpportunityType( RecordType.BUYER.getType() );
        final List< Stage > stageHistory = new ArrayList<>();
        final Stage stage = new Stage();
        stage.setStage( BuyerStage.NEW.getStage() );
        stageHistory.add( stage );
        opportunity.setStageHistory( stageHistory );
        final com.owners.gravitas.domain.Contact contact = new com.owners.gravitas.domain.Contact();
        final List< String > emails = new ArrayList<>();
        emails.add( "email1@email.com" );
        contact.setEmails( emails );

        when( agentOpportunityService.getOpportunityById( agentId, opportunityId ) ).thenReturn( opportunity );
        when( agentOpportunityService.patchOpportunity( agentId, opportunityId, request, opportunity,
                "email1@email.com" ) ).thenReturn( opportunity );
        when( agentContactService.getContactById( agentId, String.valueOf( opportunity.getContacts().toArray()[0] ) ) )
                .thenReturn( contact );
        when( leadOpportunityBusinessConfig.isContactDatabaseConfig() ).thenReturn( true );

        final AgentResponse resp = agentOpportunityBusinessServiceImpl.patchOpportunity( agentId, opportunityId,
                request );

        verify( agentOpportunityService ).getOpportunityById( agentId, opportunityId );
        verify( agentOpportunityService ).patchOpportunity( agentId, opportunityId, request, opportunity,
                "email1@email.com" );
        assertEquals( resp.getStatus(), Status.SUCCESS );
        assertEquals( opportunityId, resp.getId() );
    }

    /**
     * Test patch opportunity with result size less then zero.
     */
    @Test
    public void testPatchOpportunity_WithResultSizeLessThenZero() {
        final String agentId = "test";
        final String opportunityId = "test";
        final Map< String, Object > request = new HashMap<>();
        final AgentResponse resp = agentOpportunityBusinessServiceImpl.patchOpportunity( agentId, opportunityId,
                request );
        assertEquals( resp.getStatus(), Status.SUCCESS );
    }

    /**
     * Test patch opportunity With CrmRequest And Stage As ShowingHomes.
     */
    @Test
    public void testPatchOpportunity_WithCrmRequestAndStageAsShowingHomes() {
        final String agentId = "test";
        final String opportunityId = "test";
        final Map< String, Object > request = new HashMap<>();
        request.put( "stage", BuyerStage.SHOWING_HOMES.getStage().toString() );
        request.put( "reasonLost", "other" );
        request.put( "reasonLostDetails", "" );
        request.put( "httpRequest", httpRequest );
        final Opportunity opportunity = new Opportunity();
        final Set< String > contacts = new HashSet<>();
        contacts.add( "1234" );
        opportunity.setContacts( contacts );
        final com.owners.gravitas.domain.Contact contact = new com.owners.gravitas.domain.Contact();
        final List< String > emails = new ArrayList<>();
        emails.add( "email1@email.com" );
        contact.setEmails( emails );
        when( httpRequest.getHeader( "User-Agent" ) ).thenReturn( "mobile" );
        when( agentOpportunityService.getOpportunityById( agentId, opportunityId ) ).thenReturn( opportunity );

        final AgentResponse resp = agentOpportunityBusinessServiceImpl.patchOpportunity( agentId, opportunityId,
                request );

        verify( agentOpportunityService ).getOpportunityById( agentId, opportunityId );
        assertEquals( resp.getStatus(), Status.FAILURE );
        assertEquals( opportunityId, resp.getId() );
    }

    /**
     * Test patch opportunity With CrmRequest And Stage As FaceToFace.
     */
    @Test
    public void testPatchOpportunity_WithCrmRequestAndStageAsFaceToFace() {
        final String agentId = "test";
        final String opportunityId = "test";
        final Map< String, Object > request = new HashMap<>();
        request.put( "stage", BuyerStage.FACETOFACE.getStage().toString() );
        request.put( "reasonLost", "other" );
        request.put( "reasonLostDetails", "" );
        request.put( "httpRequest", httpRequest );
        final Opportunity opportunity = new Opportunity();
        final Set< String > contacts = new HashSet<>();
        contacts.add( "1234" );
        opportunity.setContacts( contacts );
        final com.owners.gravitas.domain.Contact contact = new com.owners.gravitas.domain.Contact();
        final List< String > emails = new ArrayList<>();
        emails.add( "email1@email.com" );
        contact.setEmails( emails );
        when( httpRequest.getHeader( "User-Agent" ) ).thenReturn( "mobile" );
        when( agentOpportunityService.getOpportunityById( agentId, opportunityId ) ).thenReturn( opportunity );

        final AgentResponse resp = agentOpportunityBusinessServiceImpl.patchOpportunity( agentId, opportunityId,
                request );

        verify( agentOpportunityService ).getOpportunityById( agentId, opportunityId );
        assertEquals( resp.getStatus(), Status.FAILURE );
        assertEquals( opportunityId, resp.getId() );
    }

    /**
     * Test create agent opportunity.
     *
     * @param predictResult
     *            the predict result
     * @param recordsMap
     *            the records map
     */
    @Test( dataProvider = "getPredictResultAndRecordsMap" )
    public void testCreateAgentOpportunityShouldCreateANewOpportunity( final PredictResult predictResult,
            final Map< String, String > recordsMap ) {
        final String agentEmail = "test";
        final LeadRequest agentLeadRequest = new LeadRequest();
        agentLeadRequest.setEmail( "test" );
        final String recordTypeName = "Buyer";
        agentLeadRequest.setLeadType( recordTypeName );
        agentLeadRequest.setPriceRange( "test" );
        final String oppRecordTypeId = "test";
        final String crmOpportunityId = "test";
        final String leadRecordTypeId = "test";
        final LeadResponse leadResponse = new LeadResponse( Status.OK, "test", "test" );
        final OpportunitySource opportunitySource = new OpportunitySource();
        when( agentOpportunityService.getOpportunityScore( recordsMap ) ).thenReturn( predictResult );

        when( recordTypeService.getRecordTypeIdByName( recordTypeName, OPPORTUNITY.getName() ) )
                .thenReturn( oppRecordTypeId );
        when( opportunityBusinessService.getOpportunityIdByRecordTypeAndEmail( oppRecordTypeId, "test" ) )
                .thenReturn( "" );
        when( recordTypeService.getRecordTypeIdByName( recordTypeName, LEAD.getName() ) )
                .thenReturn( leadRecordTypeId );
        when( leadBusinessService.getLeadIdByRequestTypeAndEmail( leadRecordTypeId, "test" ) ).thenReturn( "" );
        when( leadBusinessService.createLead( agentLeadRequest, FALSE, null ) ).thenReturn( leadResponse );
        when( leadBusinessService.convertLeadToOpportunity( "test" ) ).thenReturn( crmOpportunityId );
        when( opportunityBusinessService.getOpportunity( crmOpportunityId ) ).thenReturn( opportunitySource );
        when( crmQueryService.findOne( anyString(), any( QueryParams.class ) ) ).thenReturn( new HashMap() );
        final BaseResponse resp = agentOpportunityBusinessServiceImpl.createAgentOpportunity( agentEmail,
                agentLeadRequest );
        verify( recordTypeService ).getRecordTypeIdByName( recordTypeName, OPPORTUNITY.getName() );
        verify( opportunityBusinessService ).getOpportunityIdByRecordTypeAndEmail( oppRecordTypeId, "test" );
        verify( recordTypeService ).getRecordTypeIdByName( recordTypeName, LEAD.getName() );
        verify( leadBusinessService ).getLeadIdByRequestTypeAndEmail( leadRecordTypeId, "test" );
        verify( leadBusinessService ).createLead( agentLeadRequest, FALSE, null );
        verify( leadBusinessService ).convertLeadToOpportunity( "test" );
        verify( opportunityBusinessService ).getOpportunity( crmOpportunityId );

        assertEquals( Status.SUCCESS, resp.getStatus() );
        assertEquals( crmOpportunityId, resp.getMessage() );
    }

    /**
     * Test create agent opportunity should throw exception if opportunity
     * already exists.
     *
     * @param predictResult
     *            the predict result
     * @param recordsMap
     *            the records map
     */
    @Test( dataProvider = "getPredictResultAndRecordsMap", expectedExceptions = ApplicationException.class )
    public void testCreateAgentOpportunityShouldThrowExceptionIfOpportunityAlreadyExists(
            final PredictResult predictResult, final Map< String, String > recordsMap ) {
        final LeadRequest agentLeadRequest = new LeadRequest();
        agentLeadRequest.setEmail( "test" );
        agentLeadRequest.setLeadType( "Buyer" );
        final String oppRecordTypeId = "test";
        when( recordTypeService.getRecordTypeIdByName( "Buyer", OPPORTUNITY.getName() ) ).thenReturn( oppRecordTypeId );
        when( opportunityBusinessService.getOpportunityIdByRecordTypeAndEmail( oppRecordTypeId,
                agentLeadRequest.getEmail() ) ).thenReturn( "abc" );
        agentOpportunityBusinessServiceImpl.createAgentOpportunity( "test@test.com", agentLeadRequest );
    }

    /**
     * Test create agent opportunity should throw exception if lead already
     * exists.
     *
     * @param predictResult
     *            the predict result
     * @param recordsMap
     *            the records map
     */
    @Test( dataProvider = "getPredictResultAndRecordsMap", expectedExceptions = ApplicationException.class )
    public void testCreateAgentOpportunityShouldThrowExceptionIfLeadAlreadyExists( final PredictResult predictResult,
            final Map< String, String > recordsMap ) {
        final LeadRequest agentLeadRequest = new LeadRequest();
        final String recordType = "Buyer";
        final String leadRecordTypeId = "leadRecordTypeId";
        agentLeadRequest.setEmail( "test" );
        agentLeadRequest.setLeadType( recordType );
        final String oppRecordTypeId = "test";
        when( recordTypeService.getRecordTypeIdByName( "Buyer", OPPORTUNITY.getName() ) ).thenReturn( oppRecordTypeId );
        when( opportunityBusinessService.getOpportunityIdByRecordTypeAndEmail( oppRecordTypeId,
                agentLeadRequest.getEmail() ) ).thenReturn( null );
        when( recordTypeService.getRecordTypeIdByName( recordType, LEAD.getName() ) ).thenReturn( leadRecordTypeId );
        when( leadBusinessService.getLeadIdByRequestTypeAndEmail( leadRecordTypeId, agentLeadRequest.getEmail() ) )
                .thenReturn( "abc" );
        agentOpportunityBusinessServiceImpl.createAgentOpportunity( "test@test.com", agentLeadRequest );
    }

    /**
     * Test handle opportunity change should do nothing if opportunity absent in
     * FB and not assigned to any agent and mark opportunity as good.
     *
     * @param predictResult
     *            the predict result
     * @param recordsMap
     *            the records map
     */
    @Test( dataProvider = "getPredictResultAndRecordsMap" )
    public void testHandleOpportunityChange_ShouldDoNothing_IfOpportunityAbsentInFBAndNotAssignedToAnyAgent_AndMarkOpportunityAsGood(
            final PredictResult predictResult, final Map< String, String > recordsMap ) {
        final OpportunitySource opportunitySource = new OpportunitySource();
        opportunitySource.setStage( FORWARDED_TO_REF_EX.getStatus() );
        opportunitySource.setReferred( true );
        when( agentOpportunityService.getOpportunityScore( recordsMap ) ).thenReturn( predictResult );
        when( agentOpportunityBusinessConfig.isOpportunityReferralEnabled() ).thenReturn( true );

        agentOpportunityBusinessServiceImpl.handleOpportunityChange( opportunitySource );

        verify( agentOpportunityBusinessConfig ).isOpportunityReferralEnabled();
        verifyZeroInteractions( referralExchangeOpportunityRequestBuilder, referralExchangeService );
    }

    /**
     * Test handle opportunity change should unassign opportunity 2 and mark
     * opportunity as good.
     *
     * @param predictResult
     *            the predict result
     * @param recordsMap
     *            the records map
     * @param opportunitySearch
     *            the opportunity search
     * @param existingOpportunity
     *            the existing opportunity
     * @param postResponse
     *            the post response
     * @param primaryContact
     *            the primary contact
     */
    @Test( dataProvider = "getRequiredData" )
    public void testHandleOpportunityChange_ShouldUnassignOpportunity2_AndMarkOpportunityAsGood(
            final PredictResult predictResult, final Map< String, String > recordsMap, final Search opportunitySearch,
            final Opportunity existingOpportunity, final PostResponse postResponse, final Contact primaryContact ) {
        final OpportunitySource opportunitySource = new OpportunitySource();
        opportunitySource.setCrmId( "crmId" );
        final String stage = "Claimed";
        opportunitySource.setStage( stage );
        opportunitySource.setPrimaryContact( primaryContact );
        final String oppSourceAgentEmail = "test@test.com";

        final String agentId = "agentId";
        opportunitySearch.setAgentId( agentId );
        final String opportunityId = "opportunityId";
        opportunitySearch.setOpportunityId( opportunityId );
        postResponse.setName( opportunityId );
        final Set< String > contacts = new HashSet<>();
        contacts.add( "contact1" );
        existingOpportunity.setContacts( contacts );

        final Map< String, Task > tasks = new HashMap<>();
        tasks.put( "task1", new Task() );
        final Map< String, Request > requests = new HashMap<>();
        requests.put( "req1", new Request() );

        when( agentOpportunityService.getOpportunityScore( recordsMap ) ).thenReturn( predictResult );
        when( agentOpportunityBusinessConfig.isOpportunityReferralEnabled() ).thenReturn( false );
        when( searchService.searchByCrmOpportunityId( opportunitySource.getCrmId() ) ).thenReturn( opportunitySearch );
        when( searchService.searchByAgentEmail( oppSourceAgentEmail ) ).thenReturn( null );
        when( agentOpportunityService.getOpportunityById( opportunitySearch.getAgentId(),
                opportunitySearch.getOpportunityId() ) ).thenReturn( existingOpportunity );
        when( agentTaskService.getTasksByOpportunityId( agentId, opportunityId ) ).thenReturn( tasks );
        when( agentRequestService.getRequestsByOpportunityId( agentId, opportunityId ) ).thenReturn( requests );

        agentOpportunityBusinessServiceImpl.handleOpportunityChange( opportunitySource );

        verify( agentOpportunityBusinessConfig ).isOpportunityReferralEnabled();
        verify( searchService ).searchByCrmOpportunityId( opportunitySource.getCrmId() );
        verify( agentOpportunityService ).patchOpportunity( anyString(), anyString(), anyMap(),
                any( Opportunity.class ), anyString() );
        verify( agentContactService ).patchContact( anyString(), anyString(), anyMap() );
        verify( agentRequestService ).saveAgentRequests( requests, agentId );
    }

    /**
     * Test handle opportunity change should update existing buyer opportunity
     * and mark opportunity as good.
     *
     * @param predictResult
     *            the predict result
     * @param recordsMap
     *            the records map
     * @param opportunitySearch
     *            the opportunity search
     * @param existingOpportunity
     *            the existing opportunity
     * @param postResponse
     *            the post response
     * @param primaryContact
     *            the primary contact
     */
    @Test( dataProvider = "getRequiredData" )
    public void testHandleOpportunityChange_ShouldUpdateExistingBuyerOpportunity_AndMarkOpportunityAsGood(
            final PredictResult predictResult, final Map< String, String > recordsMap, final Search opportunitySearch,
            final Opportunity existingOpportunity, final PostResponse postResponse, final Contact primaryContact ) {
        final OpportunitySource opportunitySource = new OpportunitySource();
        opportunitySource.setCrmId( "crmId" );
        final String stage = BuyerStage.IN_CONTACT.getStage();
        opportunitySource.setStage( stage );
        opportunitySource.setPrimaryContact( primaryContact );
        final String oppSourceAgentEmail = "agent@owners.com";
        opportunitySource.setAgentEmail( oppSourceAgentEmail );
        opportunitySource.setStageChanged( true );

        final String agentId = "agentId";
        opportunitySearch.setAgentId( agentId );
        opportunitySearch.setAgentEmail( oppSourceAgentEmail );
        final String opportunityId = "opportunityId";
        opportunitySearch.setOpportunityId( opportunityId );
        postResponse.setName( opportunityId );
        final Set< String > contacts = new HashSet<>();
        contacts.add( "contact1" );
        existingOpportunity.setContacts( contacts );
        existingOpportunity.setDeleted( true );
        existingOpportunity.setOpportunityType( RecordType.BUYER.getType() );

        final List< Stage > stageHistory = new ArrayList<>();
        final Stage inContactStage = new Stage();
        inContactStage.setStage( stage );
        stageHistory.add( inContactStage );
        existingOpportunity.setStageHistory( stageHistory );

        final Map< String, Task > tasks = new HashMap<>();
        tasks.put( "task1", new Task() );
        final Map< String, Request > requests = new HashMap<>();
        requests.put( "req1", new Request() );

        when( agentOpportunityService.getOpportunityScore( recordsMap ) ).thenReturn( predictResult );
        when( agentOpportunityBusinessConfig.isOpportunityReferralEnabled() ).thenReturn( false );
        when( searchService.searchByCrmOpportunityId( opportunitySource.getCrmId() ) ).thenReturn( opportunitySearch );
        when( searchService.searchByAgentEmail( oppSourceAgentEmail ) ).thenReturn( new Search() );
        when( agentOpportunityService.getOpportunityById( opportunitySearch.getAgentId(),
                opportunitySearch.getOpportunityId() ) ).thenReturn( existingOpportunity );
        when( agentTaskService.getTasksByOpportunityId( agentId, opportunityId ) ).thenReturn( tasks );
        when( agentRequestService.getRequestsByOpportunityId( agentId, opportunityId ) ).thenReturn( requests );
        when( opportunityChangeHandlerFactory.getChangeHandler( Stage, stage ) )
                .thenReturn( inContactStageChangeHandler );
        when( leadOpportunityBusinessConfig.isContactDatabaseConfig() ).thenReturn( true );

        agentOpportunityBusinessServiceImpl.handleOpportunityChange( opportunitySource );

        verify( agentOpportunityBusinessConfig ).isOpportunityReferralEnabled();
        verify( searchService ).searchByCrmOpportunityId( opportunitySource.getCrmId() );
        verify( agentOpportunityService, Mockito.times( 2 ) ).patchOpportunity( anyString(), anyString(), anyMap(),
                any( Opportunity.class ), anyString() );
        verify( agentContactService ).patchContact( anyString(), anyString(), anyMap() );
        verify( inContactStageChangeHandler ).handleChange( agentId, opportunityId, primaryContact, stage );

    }

    /**
     * Test handle opportunity change should update existing seller opportunity
     * and mark opportunity as good.
     *
     * @param predictResult
     *            the predict result
     * @param recordsMap
     *            the records map
     * @param opportunitySearch
     *            the opportunity search
     * @param existingOpportunity
     *            the existing opportunity
     * @param postResponse
     *            the post response
     * @param primaryContact
     *            the primary contact
     */
    @Test( dataProvider = "getRequiredData" )
    public void testHandleOpportunityChange_ShouldUpdateExistingSellerOpportunity_AndMarkOpportunityAsGood(
            final PredictResult predictResult, final Map< String, String > recordsMap, final Search opportunitySearch,
            final Opportunity existingOpportunity, final PostResponse postResponse, final Contact primaryContact ) {
        final OpportunitySource opportunitySource = new OpportunitySource();
        opportunitySource.setCrmId( "crmId" );
        final String stage = SellerStage.IN_CONTACT.getStage();
        opportunitySource.setStage( stage );
        opportunitySource.setPrimaryContact( primaryContact );
        final String oppSourceAgentEmail = "agent@owners.com";
        opportunitySource.setAgentEmail( oppSourceAgentEmail );

        final String agentId = "agentId";
        opportunitySearch.setAgentId( agentId );
        opportunitySearch.setAgentEmail( oppSourceAgentEmail );
        final String opportunityId = "opportunityId";
        opportunitySearch.setOpportunityId( opportunityId );
        postResponse.setName( opportunityId );
        final Set< String > contacts = new HashSet<>();
        contacts.add( "contact1" );
        existingOpportunity.setContacts( contacts );
        existingOpportunity.setOpportunityType( RecordType.SELLER.getType() );
        existingOpportunity.setDeleted( FALSE );

        final List< Stage > stageHistory = new ArrayList<>();
        final Stage inContactStage = new Stage();
        inContactStage.setStage( stage );
        stageHistory.add( inContactStage );
        existingOpportunity.setStageHistory( stageHistory );

        final Map< String, Task > tasks = new HashMap<>();
        tasks.put( "task1", new Task() );
        final Map< String, Request > requests = new HashMap<>();
        requests.put( "req1", new Request() );

        when( agentOpportunityService.getOpportunityScore( recordsMap ) ).thenReturn( predictResult );
        when( agentOpportunityBusinessConfig.isOpportunityReferralEnabled() ).thenReturn( false );
        when( searchService.searchByCrmOpportunityId( opportunitySource.getCrmId() ) ).thenReturn( opportunitySearch );
        when( searchService.searchByAgentEmail( oppSourceAgentEmail ) ).thenReturn( new Search() );
        when( agentOpportunityService.getOpportunityById( opportunitySearch.getAgentId(),
                opportunitySearch.getOpportunityId() ) ).thenReturn( existingOpportunity );
        when( agentRequestService.getRequestsByOpportunityId( agentId, opportunityId ) ).thenReturn( requests );

        agentOpportunityBusinessServiceImpl.handleOpportunityChange( opportunitySource );

        verify( agentOpportunityBusinessConfig ).isOpportunityReferralEnabled();
        verify( searchService ).searchByCrmOpportunityId( opportunitySource.getCrmId() );
    }

    /**
     * Test handle opportunity change should transfer seller opportunity to new
     * agent and mark opportunity as good.
     *
     * @param predictResult
     *            the predict result
     * @param recordsMap
     *            the records map
     * @param opportunitySearch
     *            the opportunity search
     * @param existingOpportunity
     *            the existing opportunity
     * @param postResponse
     *            the post response
     * @param primaryContact
     *            the primary contact
     */
    @Test( dataProvider = "getRequiredData" )
    public void testHandleOpportunityChange_ShouldTransferSellerOpportunityToNewAgent_AndMarkOpportunityAsGood(
            final PredictResult predictResult, final Map< String, String > recordsMap, final Search opportunitySearch,
            final Opportunity existingOpportunity, final PostResponse postResponse, final Contact primaryContact ) {
        final OpportunitySource opportunitySource = new OpportunitySource();
        opportunitySource.setCrmId( "crmId" );
        final String stage = SellerStage.IN_CONTACT.getStage();
        opportunitySource.setStage( stage );
        opportunitySource.setPrimaryContact( primaryContact );
        final String oppSourceAgentEmail = "agent@owners.com";
        opportunitySource.setAgentEmail( oppSourceAgentEmail );
        final String agentId = "agentId";
        opportunitySearch.setAgentId( agentId );
        final String oldOppSearchId = "oldId";
        opportunitySearch.setId( oldOppSearchId );
        opportunitySearch.setAgentEmail( "agent1@owners.com" );
        final String opportunityId = "opportunityId";
        opportunitySearch.setOpportunityId( opportunityId );
        opportunitySearch.setContactEmail( "email1@email.com" );
        postResponse.setName( opportunityId );
        final Set< String > contacts = new HashSet<>();
        contacts.add( "contact1" );
        existingOpportunity.setContacts( contacts );
        existingOpportunity.setOpportunityType( RecordType.SELLER.getType() );
        existingOpportunity.setFirstContactDtm( 1L );
        existingOpportunity.setStage( "stage" );
        final List< Stage > stageHistory = new ArrayList<>();
        final Stage inContactStage = new Stage();
        inContactStage.setStage( stage );
        stageHistory.add( inContactStage );
        existingOpportunity.setStageHistory( stageHistory );
        final List< String > notificationIds = new ArrayList<>();
        notificationIds.add( "test" );
        final Reminder reminder = new Reminder();
        reminder.setTriggerDtm( ( long ) 011454276767.1 );
        reminder.setNotificationIds( notificationIds );
        final Map< String, Reminder > taskMap = new HashMap<>();
        taskMap.put( "test", reminder );
        final Task agentTask = new Task();
        agentTask.setReminders( taskMap );
        agentTask.setRequestId( "test" );
        final Map< String, Task > tasks = new HashMap<>();
        tasks.put( "task1", agentTask );
        final Map< String, Task > oldTasks = new HashMap<>();
        oldTasks.put( "oldTasks", agentTask );
        final Request request = new Request();
        request.setOpportunityId( "test" );
        final Map< String, Request > requests = new HashMap<>();
        requests.put( "test", request );
        final Map< String, Request > oldRequests = new HashMap<>();
        oldRequests.put( "test", request );
        final Contact existingContact = new Contact();
        final List< String > emails = new ArrayList<>();
        emails.add( "a@a.com" );
        existingContact.setEmails( emails );
        final Search newAgentSearch = new Search();
        final String newAgentId = "newAgentId";
        newAgentSearch.setAgentId( newAgentId );
        final String newSearchEmail = "new@email.com";
        newAgentSearch.setAgentEmail( newSearchEmail );
        final PostResponse postResponse2 = new PostResponse();
        final String newOpportunityId = "newOpportunityId";
        postResponse2.setName( newOpportunityId );
        final PostResponse postResponse3 = new PostResponse();
        postResponse3.setName( "testId" );
        final com.owners.gravitas.domain.Contact contact = new com.owners.gravitas.domain.Contact();
        contact.setEmails( emails );

        when( agentOpportunityService.getOpportunityScore( recordsMap ) ).thenReturn( predictResult );
        when( agentOpportunityBusinessConfig.isOpportunityReferralEnabled() ).thenReturn( false );
        when( searchService.searchByCrmOpportunityId( opportunitySource.getCrmId() ) ).thenReturn( opportunitySearch );
        when( searchService.searchByAgentEmail( oppSourceAgentEmail ) ).thenReturn( newAgentSearch );
        when( agentOpportunityService.getOpportunityById( opportunitySearch.getAgentId(),
                opportunitySearch.getOpportunityId() ) ).thenReturn( existingOpportunity );
        when( agentRequestService.getRequestsByOpportunityId( agentId, opportunityId ) ).thenReturn( requests );
        when( agentTaskService.getTasksByOpportunityId( agentId, opportunityId ) ).thenReturn( tasks );
        when( agentRequestService.getRequestsByOpportunityId( agentId, opportunityId ) ).thenReturn( requests );
        when( agentContactService.getContactById( agentId, "contact1" ) ).thenReturn( contact );
        when( agentOpportunityService.saveOpportunity( newAgentId, newSearchEmail, existingOpportunity,
                opportunitySource.getPrimaryContact().getEmails().iterator().next() ) ).thenReturn( postResponse2 );
        when( agentTaskService.getTasksByOpportunityId( opportunitySearch.getAgentId(), opportunityId ) )
                .thenReturn( oldTasks );
        when( agentRequestService.getRequestsByOpportunityId( opportunitySearch.getAgentId(), opportunityId ) )
                .thenReturn( oldRequests );
        when( agentContactService.saveContact( newAgentId, contact ) ).thenReturn( postResponse3 );
        when( leadOpportunityBusinessConfig.isContactDatabaseConfig() ).thenReturn( true );
        agentOpportunityBusinessServiceImpl.handleOpportunityChange( opportunitySource );

        verify( agentOpportunityBusinessConfig ).isOpportunityReferralEnabled();
        verify( agentOpportunityService ).patchOpportunity( anyString(), anyString(), anyMap(),
                any( Opportunity.class ), anyString() );
        verify( agentContactService ).patchContact( anyString(), anyString(), anyMap() );
        verify( agentRequestService ).getRequestsByOpportunityId( agentId, opportunityId );
        verify( agentRequestService ).saveAgentRequests( anyMap(), anyString() );
        verify( agentContactService ).getContactById( anyString(), anyString() );
        verify( searchService ).save( any( Search.class ) );
        verify( searchService ).delete( oldOppSearchId );
      /*  verify( agentNotificationBusinessService, times( 1 ) ).sendPushNotification( anyString(),
                any( NotificationRequest.class ) );*/
        verify( stageLogService ).saveOpportunityStagelog( newOpportunityId, existingOpportunity.getStage() );
        verify( agentContactService ).saveContact( newAgentId, contact );
        verify( agentContactService ).getContactById( agentId, "contact1" );
    }

    /**
     * Test handle opportunity change should update existing buyer opportunity
     * and mark opportunity as good and assign to same agent.
     *
     * @param predictResult
     *            the predict result
     * @param recordsMap
     *            the records map
     * @param opportunitySearch
     *            the opportunity search
     * @param existingOpportunity
     *            the existing opportunity
     * @param postResponse
     *            the post response
     * @param primaryContact
     *            the primary contact
     */
    @Test( dataProvider = "getRequiredData" )
    public void testHandleOpportunityChange_ShouldUpdateExistingBuyerOpportunity_AndMarkOpportunityAsGood_AndAssignToSameAgent(
            final PredictResult predictResult, final Map< String, String > recordsMap, final Search opportunitySearch,
            final Opportunity existingOpportunity, final PostResponse postResponse, final Contact primaryContact ) {
        final OpportunitySource opportunitySource = new OpportunitySource();
        opportunitySource.setCrmId( "crmId" );
        final String agentEmail = "test";
        final String stage = BuyerStage.IN_CONTACT.getStage();
        opportunitySource.setStage( stage );
        opportunitySource.setPrimaryContact( primaryContact );
        final String oppSourceAgentEmail = "agent@owners.com";
        opportunitySource.setAgentEmail( oppSourceAgentEmail );
        opportunitySource.setStageChanged( true );
        final String agentId = "agentId";
        opportunitySearch.setAgentId( agentId );
        opportunitySearch.setAgentEmail( oppSourceAgentEmail );
        final String opportunityId = "opportunityId";
        opportunitySearch.setOpportunityId( opportunityId );
        postResponse.setName( opportunityId );
        final Set< String > contacts = new HashSet<>();
        contacts.add( "contact1" );
        existingOpportunity.setContacts( contacts );
        existingOpportunity.setDeleted( true );
        existingOpportunity.setOpportunityType( RecordType.BUYER.getType() );
        final List< Stage > stageHistory = new ArrayList<>();
        final Stage inContactStage = new Stage();
        inContactStage.setStage( stage );
        stageHistory.add( inContactStage );
        existingOpportunity.setStageHistory( stageHistory );
        final List< String > notificationIds = new ArrayList<>();
        notificationIds.add( "test" );
        final Reminder reminder = new Reminder();
        reminder.setNotificationIds( notificationIds );
        final Map< String, Reminder > taskmap = new HashMap<>();
        taskmap.put( "test", reminder );
        final Task agentTask = new Task();
        agentTask.setReminders( taskmap );
        final Map< String, Task > tasks = new HashMap<>();
        tasks.put( "task1", agentTask );
        final Map< String, Request > requests = new HashMap<>();
        requests.put( "req1", new Request() );

        when( agentOpportunityService.getOpportunityScore( recordsMap ) ).thenReturn( predictResult );
        when( agentOpportunityBusinessConfig.isOpportunityReferralEnabled() ).thenReturn( false );
        when( searchService.searchByCrmOpportunityId( opportunitySource.getCrmId() ) ).thenReturn( opportunitySearch );
        when( searchService.searchByAgentEmail( oppSourceAgentEmail ) ).thenReturn( new Search() );
        when( agentOpportunityService.getOpportunityById( opportunitySearch.getAgentId(),
                opportunitySearch.getOpportunityId() ) ).thenReturn( existingOpportunity );
        when( agentTaskService.getTasksByOpportunityId( agentId, opportunityId ) ).thenReturn( tasks );
        when( agentRequestService.getRequestsByOpportunityId( agentId, opportunityId ) ).thenReturn( requests );
        when( opportunityChangeHandlerFactory.getChangeHandler( Stage, stage ) )
                .thenReturn( inContactStageChangeHandler );
        agentOpportunityBusinessServiceImpl.handleOpportunityChange( opportunitySource );

        verify( agentOpportunityBusinessConfig ).isOpportunityReferralEnabled();
        verify( searchService ).searchByCrmOpportunityId( opportunitySource.getCrmId() );
        verify( agentOpportunityService, Mockito.times( 2 ) ).patchOpportunity( anyString(), anyString(), anyMap(),
                any( Opportunity.class ), anyString() );
        verify( agentContactService ).patchContact( anyString(), anyString(), anyMap() );
    }

    /**
     * Test handle opportunity change should fwd to referral exchange.
     */
    @Test
    public void testHandleOpportunityChange_ShouldFwdToReferralExchange() {
        final OpportunitySource opportunitySource = new OpportunitySource();
        opportunitySource.setReferred( false );
        opportunitySource.setPropertyState( "GA" );
        opportunitySource.setStage( FORWARDED_TO_REF_EX.getStatus() );
        final Opportunity opportunity = new Opportunity();

        when( agentOpportunityBusinessConfig.isOpportunityReferralEnabled() ).thenReturn( true );
        when( leadBusinessConfig.getReferralExcludedStates() ).thenReturn( "" );
        when( opportunityBuilder.convertTo( opportunitySource ) ).thenReturn( opportunity );
        when( contactServiceV1.getContactByCrmId( anyString() ) )
                .thenReturn( new com.owners.gravitas.domain.entity.Contact() );
        agentOpportunityBusinessServiceImpl.handleOpportunityChange( opportunitySource );

        verify( buyerFarmingBusinessService ).updateFarmingStatus( opportunitySource.getCrmId(),
                BuyerFarmType.LONG_TERM_BUYER, true );
        verify( opportunityBusinessService ).forwardToReferralExchange( opportunitySource );
        verify( contactServiceV1 ).getContactByCrmId( anyString() );
        verify( contactServiceV1 ).save( Mockito.any( com.owners.gravitas.domain.entity.Contact.class ) );
    }

    /**
     * Test handle opportunity change should process new opportunity.
     */
    @Test
    public void testHandleOpportunityChange_ShouldProcessNewOpportunity() {
        final OpportunitySource opportunitySource = new OpportunitySource();
        opportunitySource.setReferred( false );
        opportunitySource.setPropertyState( "GA" );
        opportunitySource.setStage( NEW.getStatus() );
        final String crmOppId = "crmOppId";
        opportunitySource.setCrmId( crmOppId );
        opportunitySource.setRecordType( RecordType.BUYER.getType() );
        final String agentEmail = "a@a.com";
        opportunitySource.setAgentEmail( agentEmail );
        final Search agentSearch = new Search();
        final Contact primaryContact = new Contact();
        final List< String > emails = new ArrayList<>();
        emails.add( "email@email.com" );
        primaryContact.setEmails( emails );
        opportunitySource.setPrimaryContact( primaryContact );
        final com.owners.gravitas.domain.Contact contact = new com.owners.gravitas.domain.Contact();
        final Opportunity opportunity = new Opportunity();
        final String agentId = agentSearch.getAgentId();

        when( agentOpportunityBusinessConfig.isOpportunityReferralEnabled() ).thenReturn( true );
        when( leadBusinessConfig.getReferralExcludedStates() ).thenReturn( "" );
        when( happyAgentsConfig.getAutoAssignAgentEmail() ).thenReturn( "" );
        when( searchService.searchByAgentEmail( opportunitySource.getAgentEmail() ) ).thenReturn( agentSearch );
        when( opportunityChangeHandlerFactory.getChangeHandler( Stage, opportunitySource.getStage() ) )
                .thenReturn( opportunityChangeHandler );
        when( actionFlowBusinessService.isEligibleForScriptedCall( opportunitySource.getRecordType(),
                agentSearch.getAgentEmail() ) ).thenReturn( true );
        when( contactBuilder.convertFrom( opportunitySource.getPrimaryContact() ) ).thenReturn( contact );
        when( agentContactService.saveContact( agentId, contact ) ).thenReturn( new PostResponse() );
        when( opportunityBuilder.convertTo( opportunitySource ) ).thenReturn( opportunity );
        when( agentOpportunityService.saveOpportunity( agentId, agentSearch.getAgentEmail(), opportunity,
                opportunitySource.getPrimaryContact().getEmails().iterator().next() ) )
                        .thenReturn( new PostResponse() );
        when(coShoppingconfig.isEnableBuyerOpportunityScheduleTour()).thenReturn( false );
        agentOpportunityBusinessServiceImpl.handleOpportunityChange( opportunitySource );

        verify( agentRequestBusinessServiceImpl ).createAgentRequest( any( OpportunitySource.class ), anyString(),
                anyString() );
        verify( opportunityChangeHandler ).handleChange( anyString(), anyString(), any( Contact.class ), anyString() );
        verify( opportunityChangeHandler ).handleBadgeCounterChange( agentId );
        verify( opportunityChangeHandler ).sendFeedbackEmail( anyString(), anyString(), any( Contact.class ) );
        verify( searchService ).save( any( Search.class ) );
        verify( actionFlowBusinessService ).createActionGroup( anyString(), anyString(), any( Opportunity.class ),
                any( Search.class ) );
        verify( buyerFarmingBusinessService ).updateFarmingStatus( crmOppId, ACTIVE_BUYER, true );
        verifyZeroInteractions( agentLookupBusinessService, agentNotificationBusinessService );
    }

    /**
     * Test handle opportunity change should process new opportunity and send
     * push notification.
     */
    @Test
    public void testHandleOpportunityChange_ShouldProcessNewOpportunity_AndSendPushNotification() {
        final OpportunitySource opportunitySource = new OpportunitySource();
        opportunitySource.setReferred( false );
        opportunitySource.setPropertyState( "GA" );
        opportunitySource.setStage( NEW.getStatus() );
        final String crmOppId = "crmOppId";
        opportunitySource.setRecordType( RecordType.BUYER.getType() );
        opportunitySource.setCrmId( crmOppId );
        final String agentEmail = "a@a.com";
        opportunitySource.setAgentEmail( agentEmail );
        final Search agentSearch = new Search();
        final Contact primaryContact = new Contact();
        final List< String > emails = new ArrayList<>();
        emails.add( "email@email.com" );
        primaryContact.setEmails( emails );
        opportunitySource.setPrimaryContact( primaryContact );
        final com.owners.gravitas.domain.Contact contact = new com.owners.gravitas.domain.Contact();
        final Opportunity opportunity = new Opportunity();
        final String agentId = agentSearch.getAgentId();

        when( agentOpportunityBusinessConfig.isOpportunityReferralEnabled() ).thenReturn( true );
        when( leadBusinessConfig.getReferralExcludedStates() ).thenReturn( "" );
        when( happyAgentsConfig.getAutoAssignAgentEmail() ).thenReturn( "" );
        when( searchService.searchByAgentEmail( opportunitySource.getAgentEmail() ) ).thenReturn( agentSearch );
        when( opportunityChangeHandlerFactory.getChangeHandler( Stage, opportunitySource.getStage() ) )
                .thenReturn( opportunityChangeHandler );
        when( actionFlowBusinessService.isEligibleForScriptedCall( opportunitySource.getRecordType(),
                agentSearch.getAgentEmail() ) ).thenReturn( false );
        when( contactBuilder.convertFrom( opportunitySource.getPrimaryContact() ) ).thenReturn( contact );
        when( agentContactService.saveContact( agentId, contact ) ).thenReturn( new PostResponse() );
        when( opportunityBuilder.convertTo( opportunitySource ) ).thenReturn( opportunity );
        when( agentOpportunityService.saveOpportunity( agentId, agentSearch.getAgentEmail(), opportunity,
                opportunitySource.getPrimaryContact().getEmails().iterator().next() ) )
                        .thenReturn( new PostResponse() );
        when(coShoppingconfig.isEnableBuyerOpportunityScheduleTour()).thenReturn( false );
        agentOpportunityBusinessServiceImpl.handleOpportunityChange( opportunitySource );

        verify( agentNotificationBusinessService ).sendPushNotification( anyString(),
                any( NotificationRequest.class ) );
        verify( agentRequestBusinessServiceImpl ).createAgentRequest( any( OpportunitySource.class ), anyString(),
                anyString() );
        verify( opportunityChangeHandler ).handleChange( anyString(), anyString(), any( Contact.class ), anyString() );
        verify( opportunityChangeHandler ).handleBadgeCounterChange( agentId );
        verify( opportunityChangeHandler ).sendFeedbackEmail( anyString(), anyString(), any( Contact.class ) );
        verify( searchService ).save( any( Search.class ) );
        verify( buyerFarmingBusinessService ).updateFarmingStatus( crmOppId, ACTIVE_BUYER, true );
        verifyZeroInteractions( agentLookupBusinessService );
    }

    /**
     * Test handle opportunity change should process new opportunity and not
     * send push notification.
     */
    @Test
    public void testHandleOpportunityChange_ShouldProcessNewOpportunity_AndNotSendPushNotification() {
        final OpportunitySource opportunitySource = new OpportunitySource();
        opportunitySource.setReferred( false );
        opportunitySource.setPropertyState( "GA" );
        opportunitySource.setStage( OPEN.getStatus() );
        final String crmOppId = "crmOppId";
        opportunitySource.setRecordType( RecordType.BUYER.getType() );
        opportunitySource.setCrmId( crmOppId );
        final String agentEmail = "a@a.com";
        opportunitySource.setAgentEmail( agentEmail );
        final Search agentSearch = new Search();
        final Contact primaryContact = new Contact();
        final List< String > emails = new ArrayList<>();
        emails.add( "email@email.com" );
        primaryContact.setEmails( emails );
        opportunitySource.setPrimaryContact( primaryContact );
        final com.owners.gravitas.domain.Contact contact = new com.owners.gravitas.domain.Contact();
        final Opportunity opportunity = new Opportunity();
        final String agentId = agentSearch.getAgentId();

        when( agentOpportunityBusinessConfig.isOpportunityReferralEnabled() ).thenReturn( true );
        when( leadBusinessConfig.getReferralExcludedStates() ).thenReturn( "" );
        when( happyAgentsConfig.getAutoAssignAgentEmail() ).thenReturn( "" );
        when( searchService.searchByAgentEmail( opportunitySource.getAgentEmail() ) ).thenReturn( agentSearch );
        when( opportunityChangeHandlerFactory.getChangeHandler( Stage, opportunitySource.getStage() ) )
                .thenReturn( opportunityChangeHandler );
        when( actionFlowBusinessService.isEligibleForScriptedCall( opportunitySource.getRecordType(),
                agentSearch.getAgentEmail() ) ).thenReturn( false );
        when( contactBuilder.convertFrom( opportunitySource.getPrimaryContact() ) ).thenReturn( contact );
        when( agentContactService.saveContact( agentId, contact ) ).thenReturn( new PostResponse() );
        when( opportunityBuilder.convertTo( opportunitySource ) ).thenReturn( opportunity );
        when( agentOpportunityService.saveOpportunity( agentId, agentSearch.getAgentEmail(), opportunity,
                opportunitySource.getPrimaryContact().getEmails().iterator().next() ) )
                        .thenReturn( new PostResponse() );

        agentOpportunityBusinessServiceImpl.handleOpportunityChange( opportunitySource );

        verify( agentRequestBusinessServiceImpl ).createAgentRequest( any( OpportunitySource.class ), anyString(),
                anyString() );
        verify( opportunityChangeHandler ).handleChange( anyString(), anyString(), any( Contact.class ), anyString() );
        verify( opportunityChangeHandler ).handleBadgeCounterChange( agentId );
        verify( opportunityChangeHandler ).sendFeedbackEmail( anyString(), anyString(), any( Contact.class ) );
        verify( searchService ).save( any( Search.class ) );
        verify( buyerFarmingBusinessService ).updateFarmingStatus( crmOppId, ACTIVE_BUYER, true );
        verifyZeroInteractions( agentLookupBusinessService, agentNotificationBusinessService );
    }

    /**
     * Test handle opportunity change should do nothing id agent not registered.
     */
    @Test
    public void testHandleOpportunityChange_ShouldDoNothingIdAgentNotRegistered() {
        final OpportunitySource opportunitySource = new OpportunitySource();
        opportunitySource.setReferred( false );
        opportunitySource.setPropertyState( "GA" );
        opportunitySource.setStage( NEW.getStatus() );
        final String crmOppId = "crmOppId";
        opportunitySource.setCrmId( crmOppId );
        final String agentEmail = "a@a.com";
        opportunitySource.setAgentEmail( agentEmail );
        final Contact primaryContact = new Contact();
        final List< String > emails = new ArrayList<>();
        emails.add( "email@email.com" );
        primaryContact.setEmails( emails );
        opportunitySource.setPrimaryContact( primaryContact );

        when( agentOpportunityBusinessConfig.isOpportunityReferralEnabled() ).thenReturn( true );
        when( leadBusinessConfig.getReferralExcludedStates() ).thenReturn( "" );
        when( happyAgentsConfig.getAutoAssignAgentEmail() ).thenReturn( "" );
        when( searchService.searchByAgentEmail( opportunitySource.getAgentEmail() ) ).thenReturn( null );

        agentOpportunityBusinessServiceImpl.handleOpportunityChange( opportunitySource );

        verifyZeroInteractions( opportunityChangeHandler, buyerFarmingBusinessService, agentLookupBusinessService,
                actionFlowBusinessService );
    }

    /**
     * Test handle opportunity change should auto assign if enabled.
     */
    @Test
    public void testHandleOpportunityChange_ShouldAutoAssignIfEnabled() {
        final OpportunitySource opportunitySource = new OpportunitySource();
        opportunitySource.setReferred( false );
        opportunitySource.setPropertyState( "GA" );
        opportunitySource.setStage( NEW.getStatus() );
        final String crmOppId = "crmOppId";
        opportunitySource.setCrmId( crmOppId );
        final String agentEmail = "a@a.com";
        opportunitySource.setAgentEmail( agentEmail );
        final Contact primaryContact = new Contact();
        final List< String > emails = new ArrayList<>();
        emails.add( "email@email.com" );
        primaryContact.setEmails( emails );
        opportunitySource.setPrimaryContact( primaryContact );
        opportunitySource.setRecordType( "Buyer" );

        when( agentOpportunityBusinessConfig.isOpportunityReferralEnabled() ).thenReturn( true );
        when( leadBusinessConfig.getReferralExcludedStates() ).thenReturn( "" );
        when( happyAgentsConfig.getAutoAssignAgentEmail() ).thenReturn( agentEmail );
        when( searchService.searchByAgentEmail( opportunitySource.getAgentEmail() ) ).thenReturn( null );

        agentOpportunityBusinessServiceImpl.handleOpportunityChange( opportunitySource );

        verifyZeroInteractions( opportunityChangeHandler, buyerFarmingBusinessService, actionFlowBusinessService );
        verify( agentLookupBusinessService ).getMostEligibleAgent( opportunitySource, true, null, null );
    }

    /**
     * Test handle opportunity change should unassign opporunity if agent not
     * registered.
     */
    @Test
    public void testHandleOpportunityChange_ShouldUnassignOpporunityIfAgentNotRegistered() {
        final OpportunitySource opportunitySource = new OpportunitySource();
        opportunitySource.setReferred( false );
        opportunitySource.setPropertyState( "GA" );
        opportunitySource.setStage( NEW.getStatus() );
        final String crmOppId = "crmOppId";
        opportunitySource.setCrmId( crmOppId );
        final String agentEmail = "a@a.com";
        opportunitySource.setAgentEmail( agentEmail );
        final Contact primaryContact = new Contact();
        final List< String > emails = new ArrayList<>();
        emails.add( "email@email.com" );
        primaryContact.setEmails( emails );
        opportunitySource.setPrimaryContact( primaryContact );

        final Opportunity existingOpportunity = new Opportunity();
        final Set< String > contacts = new HashSet<>();
        final String contact = "contact";
        contacts.add( contact );
        existingOpportunity.setContacts( contacts );

        final Search opportunitySearch = new Search();
        final String opportunityId = opportunitySearch.getOpportunityId();
        final String agentId = opportunitySearch.getAgentId();
        final Map< String, Task > taskMap = new HashMap<>();

        when( agentOpportunityBusinessConfig.isOpportunityReferralEnabled() ).thenReturn( FALSE );
        when( searchService.searchByCrmOpportunityId( crmOppId ) ).thenReturn( opportunitySearch );
        when( agentOpportunityService.getOpportunityById( opportunitySearch.getAgentId(),
                opportunitySearch.getOpportunityId() ) ).thenReturn( existingOpportunity );
        when( agentTaskService.getTasksByOpportunityId( agentId, opportunityId ) ).thenReturn( taskMap );
        when( agentRequestService.getRequestsByOpportunityId( anyString(), anyString() ) )
                .thenReturn( new HashMap<>() );
        when( badgeCounterJmxConfig.isBadgeCountEnabled() ).thenReturn( true );

        agentOpportunityBusinessServiceImpl.handleOpportunityChange( opportunitySource );

        verify( agentOpportunityService ).patchOpportunity( anyString(), anyString(), anyMap(),
                any( Opportunity.class ), anyString() );
        verify( agentContactService ).patchContact( anyString(), anyString(), anyMap() );
        verify( agentRequestService ).saveAgentRequests( anyMap(), anyString() );
    }

    /**
     * Test handle opportunity change should unassign opporunity if agent not
     * registered and auto assign.
     */
    @Test
    public void testHandleOpportunityChange_ShouldUnassignOpporunityIfAgentNotRegistered_AndAutoAssign() {
        final OpportunitySource opportunitySource = new OpportunitySource();
        opportunitySource.setReferred( false );
        opportunitySource.setPropertyState( "GA" );
        opportunitySource.setStage( NEW.getStatus() );
        final String crmOppId = "crmOppId";
        opportunitySource.setCrmId( crmOppId );
        final String agentEmail = "a@a.com";
        opportunitySource.setAgentEmail( agentEmail );
        final Contact primaryContact = new Contact();
        final List< String > emails = new ArrayList<>();
        emails.add( "email@email.com" );
        primaryContact.setEmails( emails );
        opportunitySource.setPrimaryContact( primaryContact );

        final Opportunity existingOpportunity = new Opportunity();
        final Set< String > contacts = new HashSet<>();
        final String contact = "contact";
        contacts.add( contact );
        existingOpportunity.setContacts( contacts );

        final Search opportunitySearch = new Search();
        final String opportunityId = opportunitySearch.getOpportunityId();
        final String agentId = opportunitySearch.getAgentId();
        final Map< String, Task > taskMap = new HashMap<>();

        when( agentOpportunityBusinessConfig.isOpportunityReferralEnabled() ).thenReturn( FALSE );
        when( searchService.searchByCrmOpportunityId( crmOppId ) ).thenReturn( opportunitySearch );
        when( agentOpportunityService.getOpportunityById( opportunitySearch.getAgentId(),
                opportunitySearch.getOpportunityId() ) ).thenReturn( existingOpportunity );
        when( agentTaskService.getTasksByOpportunityId( agentId, opportunityId ) ).thenReturn( taskMap );
        when( agentRequestService.getRequestsByOpportunityId( anyString(), anyString() ) )
                .thenReturn( new HashMap<>() );
        when( badgeCounterJmxConfig.isBadgeCountEnabled() ).thenReturn( true );
        when( happyAgentsConfig.getAutoAssignAgentEmail() ).thenReturn( agentEmail );

        agentOpportunityBusinessServiceImpl.handleOpportunityChange( opportunitySource );

        verify( agentOpportunityService ).patchOpportunity( anyString(), anyString(), anyMap(),
                any( Opportunity.class ), anyString() );
        verify( agentContactService ).patchContact( anyString(), anyString(), anyMap() );
    }

    /**
     * Test get opportunity commission.
     */
    @Test
    public void testGetOpportunityCommission() {
        final String agentId = "test";
        final String opportunityId = "crmOppId";
        final Opportunity opportunity = new Opportunity();
        final com.owners.gravitas.domain.entity.Contact contact = new com.owners.gravitas.domain.entity.Contact();
        contact.setOwnersComId( "test" );
        contact.setCrmId( opportunityId );
        opportunity.setCrmId( "crmOppId" );
        opportunity.setLeadSource( "Self Generated" );
        opportunity.setOpportunityType( BUYER.getType() );
        final ContactAttribute contactAttribute = new ContactAttribute();
        final ObjectAttributeConfig objectAttributeConfig = new ObjectAttributeConfig();
        objectAttributeConfig.setAttributeName( "state" );
        contactAttribute.setObjectAttributeConfig( objectAttributeConfig );
        contactAttribute.setValue( "FL" );
        final ContactAttribute priceRangecontactAttribute = new ContactAttribute();
        final ObjectAttributeConfig priceRangeobjectAttributeConfig = new ObjectAttributeConfig();
        priceRangeobjectAttributeConfig.setAttributeName( "priceRange" );
        priceRangecontactAttribute.setObjectAttributeConfig( priceRangeobjectAttributeConfig );
        priceRangecontactAttribute.setValue( "100K - 150K" );
        final Set< ContactAttribute > contactAttributes = new HashSet<>();
        contactAttributes.add( contactAttribute );
        contactAttributes.add( priceRangecontactAttribute );
        contact.setContactAttributes( contactAttributes );
        final AgentCommission agentCommission = new AgentCommission();
        final String state = "state";
        final int topPrice = 0;
        when( agentOpportunityService.getOpportunityById( agentId, opportunityId ) ).thenReturn( opportunity );

        when( contactServiceV1.findByCrmId( opportunityId ) ).thenReturn( contact );

        when( agentCommissionService.getCommissionByStateAndTopPrice( state, topPrice ) ).thenReturn( agentCommission );
        agentOpportunityBusinessServiceImpl.getOpportunityCommission( agentId, opportunityId );
    }

    /**
     * Testis crm id permitted returns false invalid crm id or email null.
     */
    @Test
    public void testisCrmIdPermitted_Returns_false_invalid_crmId_or_email_null() {
        final String agentId = "DummyAgent";
        final String crmId = "DummyCrmId";

        final AgentEmailPermissionResponse failureResponse = new AgentEmailPermissionResponse();
        failureResponse.setStatus( Status.FAILURE );
        failureResponse.setMessage( "Invalid CrmId or EmailId does not exist." );
        failureResponse.setAllowed( false );

        when( contactServiceV1.getContactByCrmId( crmId ) ).thenReturn( null );
        agentOpportunityBusinessServiceImpl.isCrmIdPermitted( agentId, crmId );
        verify( contactServiceV1 ).getContactByCrmId( crmId );
        Assert.assertFalse( failureResponse.isAllowed() );
    }
}
