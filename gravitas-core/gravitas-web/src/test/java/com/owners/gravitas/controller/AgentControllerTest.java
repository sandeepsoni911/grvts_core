/*
 *
 */
package com.owners.gravitas.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.business.AgentBusinessService;
import com.owners.gravitas.business.AgentContactBusinessService;
import com.owners.gravitas.business.AgentLookupBusinessService;
import com.owners.gravitas.business.AgentOpportunityBusinessService;
import com.owners.gravitas.business.AgentReminderBusinessService;
import com.owners.gravitas.business.AgentReportsBusinessService;
import com.owners.gravitas.business.AgentTaskBusinessService;
import com.owners.gravitas.business.BeanValidationService;
import com.owners.gravitas.config.BaseControllerTest;
import com.owners.gravitas.dto.ApiUser;
import com.owners.gravitas.dto.request.ActionRequest;
import com.owners.gravitas.dto.request.AgentNoteRequest;
import com.owners.gravitas.dto.request.AgentOnboardRequest;
import com.owners.gravitas.dto.request.AgentSuggestedPropertyRequest;
import com.owners.gravitas.dto.request.AgentTaskRequest;
import com.owners.gravitas.dto.request.LastViewedRequest;
import com.owners.gravitas.dto.request.ReminderRequest;
import com.owners.gravitas.dto.request.TaskUpdateRequest;
import com.owners.gravitas.dto.response.ActionLogResponse;
import com.owners.gravitas.dto.response.AgentEmailPermissionResponse;
import com.owners.gravitas.dto.response.AgentEmailResponse;
import com.owners.gravitas.dto.response.AgentResponse;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.util.GravitasWebUtil;
import com.owners.gravitas.util.PropertiesUtil;
import com.owners.gravitas.validator.ContactUpdateRequestValidator;
import com.owners.gravitas.validator.OpportunityUpdateRequestValidator;
import com.owners.gravitas.validator.UserRoleValidator;

/**
 * The Class AgentControllerTest.
 *
 * @author vishwanathm
 */
public class AgentControllerTest extends BaseControllerTest {

    /** mock of {@link AgentController}. **/
    @InjectMocks
    private AgentController controller;

    /** The role validator. */
    @Mock
    private UserRoleValidator roleValidator;

    /** The gravitas web util. */
    @Mock
    private GravitasWebUtil gravitasWebUtil;

    /** The agent opportunity business service. */
    @Mock
    private AgentBusinessService agentBusinessService;

    /** The agent opportunity business service. */
    @Mock
    private AgentContactBusinessService agentContactBusinessService;

    /** The Opportunity update request validator. */
    @Mock
    private AgentTaskBusinessService agentTaskBusinessService;

    /** The Opportunity update request validator. */
    @Mock
    private OpportunityUpdateRequestValidator OpportunityUpdateRequestValidator;

    /** The Contact update request validator. */
    @Mock
    private ContactUpdateRequestValidator contactUpdateRequestValidator;

    /** The Agent reminder business service. */
    @Mock
    private AgentReminderBusinessService agentReminderBusinessService;

    /** The agent opportunity business service. */
    @Mock
    private AgentOpportunityBusinessService agentOpportunityBusinessService;

    /** The agent lookup business service. */
    @Mock
    private AgentLookupBusinessService agentLookupBusinessService;

    /** The agent reports business service. */
    @Mock
    private AgentReportsBusinessService agentReportsBusinessService;

    /** The bean validation service. */
    @Mock
    private BeanValidationService beanValidationService;

    /**
     * before method.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeClass
    public void setUpBase() throws Exception {
        super.setUpMockMVC( controller );
    }

    /**
     * Test register agent.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testRegisterAgent() throws Exception {
        final ApiUser user = new ApiUser();
        Mockito.when( gravitasWebUtil.getAppUser() ).thenReturn( user );
        Mockito.when( agentBusinessService.register( Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( new AgentResponse( "uid" ) );
        this.mockMvc.perform( MockMvcRequestBuilders.post( "/api/agents" ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test add note.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testAddNote() throws Exception {
        final ApiUser user = new ApiUser();
        user.setUid( "test" );
        user.setEmail( "test@test.com" );
        Mockito.when( roleValidator.validateByAgentId( Mockito.anyString() ) ).thenReturn( user );
        this.mockMvc.perform( MockMvcRequestBuilders.post( "/api/agents/test/opportunities/test/notes" )
                .content( "{\"details\":\"test\"}" ).contentType( MediaType.APPLICATION_JSON )
                .header( "principal", "test:test@test.com" ) ).andExpect( status().isOk() );
    }

    /**
     * Test update note.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testUpdateNote() throws Exception {
        final ApiUser user = new ApiUser();
        user.setUid( "test" );
        user.setEmail( "test@test.com" );
        Mockito.when( roleValidator.validateByAgentId( Mockito.anyString() ) ).thenReturn( user );
        Mockito.when( agentBusinessService.updateAgentNote( Mockito.anyString(), Mockito.anyString(),
                Mockito.any( AgentNoteRequest.class ) ) ).thenReturn( new AgentResponse( "test" ) );
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.put( "/api/agents/test/notes/56123" ).content( "{\"details\":\"test\"}" )
                                .contentType( MediaType.APPLICATION_JSON ).header( "principal", "test:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test update note.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testDeleteNote() throws Exception {
        final ApiUser user = new ApiUser();
        user.setUid( "test" );
        user.setEmail( "test@test.com" );
        Mockito.when( roleValidator.validateByAgentId( Mockito.anyString() ) ).thenReturn( user );
        Mockito.when( agentBusinessService.deleteAgentNote( Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( new AgentResponse( "test" ) );
        this.mockMvc.perform(
                MockMvcRequestBuilders.delete( "/api/agents/test/notes/56123" ).content( "{\"details\":\"test\"}" )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "test:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test update opportunity stage.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testUpdateOpportunityStage() throws Exception {
        final ApiUser user = new ApiUser();
        user.setUid( "test" );
        user.setEmail( "test@test.com" );
        Mockito.when( roleValidator.validateByAgentId( Mockito.anyString() ) ).thenReturn( user );
        Mockito.doNothing().when( OpportunityUpdateRequestValidator ).validateOpportunityRequest( Mockito.anyMap() );
        this.mockMvc.perform( MockMvcRequestBuilders.patch( "/api/agents/test/opportunities/test-id" )
                .content( "{\"stage\":\"test\"}" ).contentType( MediaType.APPLICATION_JSON )
                .header( "principal", "test:test@test.com" ) ).andExpect( status().isOk() );
    }

    /**
     * Test add device.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testAddDevice() throws Exception {
        final ApiUser user = new ApiUser();
        user.setUid( "test" );
        user.setEmail( "test@test.com" );
        Mockito.when( roleValidator.validateByAgentId( Mockito.anyString() ) ).thenReturn( user );
        this.mockMvc
                .perform( MockMvcRequestBuilders.post( "/api/agents/test/devices" )
                        .content( "{\"deviceId\":\"8749287384\",\"deviceType\": \"IOS\"}" )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "test:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test remove device.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testRemoveDevice() throws Exception {
        final ApiUser user = new ApiUser();
        user.setUid( "test" );
        user.setEmail( "test@test.com" );
        Mockito.when( roleValidator.validateByAgentId( Mockito.anyString() ) ).thenReturn( user );
        this.mockMvc.perform( MockMvcRequestBuilders.delete( "/api/agents/test/devices/test" ).header( "principal",
                "test:test@test.com" ) ).andExpect( status().isOk() );
    }

    /**
     * Test the create opportunity for wrong url.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testInvalidPostUrl() throws Exception {
        this.mockMvc.perform( post( "/api/test" ).content(
                "{\"mlsId\":\"Vishwanath\",\"seller\" : {\"id\" :\"test1\", \"lastName\" : \"Mathapati\"},\"propertyOrder\": { \"property\": {\"address\" :{\"state\" : \"GA\"}}}}" )
                .contentType( MediaType.APPLICATION_JSON_UTF8 ) ).andExpect( status().is( 404 ) );
    }

    /**
     * Test synchronize agent.
     *
     * @throws Exception
     *             the exception
     */
    /*
     * @Test
     * public void testSynchronizeAgent() throws Exception {
     * ApiUser user = new ApiUser();
     * user.setUid( "test" );
     * user.setEmail( "test@test.com" );
     * Mockito.when( roleValidator.getValidUser( Mockito.anyString() )
     * ).thenReturn( user );
     * Mockito.when( agentBusinessService.register( Mockito.anyString(),
     * Mockito.anyString() ) )
     * .thenReturn( new AgentResponse( "uid" ) );
     * this.mockMvc
     * .perform( MockMvcRequestBuilders.put( "/api/agents/test" ).header(
     * "principal", "uid:test@test.com" ) )
     * .andExpect( status().isOk() );
     * }
     */

    /**
     * Test create agent task.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testCreateAgentTask() throws Exception {
        final ApiUser user = new ApiUser();
        user.setUid( "test" );
        user.setEmail( "test@test.com" );
        Mockito.when( roleValidator.validateByAgentId( Mockito.anyString() ) ).thenReturn( user );
        Mockito.when( agentTaskBusinessService.createAgentTask( Mockito.anyString(), Mockito.anyString(),
                Mockito.any( AgentTaskRequest.class ) ) ).thenReturn( new AgentResponse( "uid" ) );
        this.mockMvc
                .perform( MockMvcRequestBuilders.post( "/api/agents/test/opportunities/opportunityId/tasks" ).content(
                        "{\"title\":\"test\",\"description\":\"test\",\"dueDtm\":\"1484567486066\",\"location\":\"PARIS\"}" )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test create agent action.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testCreateAgentAction() throws Exception {
        final ApiUser user = new ApiUser();
        user.setUid( "test" );
        user.setEmail( "test@test.com" );
        final Map< String, String > map = new HashMap<>();
        map.put( "action.property.actionTypes", "SMS,TO_EMAIL" );
        map.put( "action.property.actionEntities", "OPPORTUNITY,LEAD" );
        map.put( "action.property.platforms", "AGENT_APP,GRAVITAS" );
        if (PropertiesUtil.getPropertiesMap() == null) {
            PropertiesUtil.setPropertiesMap( map );
        } else {
            PropertiesUtil.getPropertiesMap().putAll( map );
        }

        Mockito.when( roleValidator.validateByAgentId( Mockito.anyString() ) ).thenReturn( user );
        Mockito.when(
                agentBusinessService.createAgentAction( Mockito.anyString(), Mockito.any( ActionRequest.class ) ) )
                .thenReturn( new AgentResponse( "uid" ) );
        this.mockMvc.perform( MockMvcRequestBuilders.post( "/api/agents/test/actions" ).content(
                "{\"actionType\":\"SMS\",\"actionEntity\":\"OPPORTUNITY\",\"actionEntityId\":\"e2e2e2fe-925a-43c7-b96c-c26ac69a5988\","
                        + "\"previousValue\":\"wetwet\",\"currentValue\":\"twetwt\",\"platform\":\"AGENT_APP\",\"platformVersion\":\"2.3\",\"description\":\"description\"}" )
                .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test update contact.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testUpdateContact() throws Exception {
        final ApiUser user = new ApiUser();
        user.setUid( "test" );
        user.setEmail( "test@test.com" );
        Mockito.when( roleValidator.validateByAgentId( Mockito.anyString() ) ).thenReturn( user );
        Mockito.doNothing().when( contactUpdateRequestValidator ).validate( Mockito.anyMap() );
        Mockito.when( agentContactBusinessService.updateContact( Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMap() ) ).thenReturn( new AgentResponse( "uid" ) );
        this.mockMvc.perform( MockMvcRequestBuilders.patch( "/api/agents/test/contacts/contactId" ).content(
                "{\"firstName\":\"Loren\",\"lastName\":\"Dpenpa\",\"phone\":\"1258546514\",\"preferredContactTime\":\"10PM\",\"preferredContactMethod\":\"Email\"}" )
                .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test get CTA audit logs.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetCTAAuditLogs() throws Exception {
        final ApiUser user = new ApiUser();
        user.setUid( "test" );
        user.setEmail( "test@test.com" );
        Mockito.when( roleValidator.validateByAgentId( Mockito.anyString() ) ).thenReturn( user );
        Mockito.doNothing().when( contactUpdateRequestValidator ).validate( Mockito.anyMap() );
        Mockito.when(
                agentBusinessService.getCTAAuditLogs( Mockito.anyString(), Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( new ActionLogResponse() );
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( "/api/agents/test/opportunities/opportunityId/actions" )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test get agent metrics.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetAgentMetrics() throws Exception {
        final ApiUser user = new ApiUser();
        user.setUid( "test" );
        user.setEmail( "test@test.com" );
        when( roleValidator.validateByAgentId( anyString() ) ).thenReturn( user );
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( "/api/agents/test/metrics" )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
        verify( agentReportsBusinessService ).getPerformanceLog( anyString(), anyInt() );
    }

    /**
     * Test update reminder.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testUpdateReminder() throws Exception {
        final ApiUser user = new ApiUser();
        user.setUid( "test" );
        user.setEmail( "test@test.com" );
        when( roleValidator.validateByAgentId( anyString() ) ).thenReturn( user );
        this.mockMvc.perform( MockMvcRequestBuilders.patch( "/api/agents/agentId/tasks/taskId/reminders/reminderId" )
                .content( "{\"triggerDtm\":\"1484906780767\"}" ).contentType( MediaType.APPLICATION_JSON )
                .header( "principal", "uid:test@test.com" ) ).andExpect( status().isOk() );
        verify( agentReminderBusinessService ).updateReminder( anyString(), anyString(), anyString(),
                any( ReminderRequest.class ) );
    }

    /**
     * Test send multiple buyer request emails.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testSendMultipleBuyerRequestEmails() throws Exception {
        final ApiUser user = new ApiUser();
        user.setUid( "test" );
        user.setEmail( "test@test.com" );
        when( roleValidator.validateByAgentId( anyString() ) ).thenReturn( user );
        this.mockMvc
                .perform( MockMvcRequestBuilders.post( "/api//agents/agentId/sendMultipleEmail" ).content( "[{}]" )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
        verify( agentBusinessService ).sendAgentEmails( anyString(), anyList() );
    }

    /**
     * Test close agent tasks.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testCloseAgentTask() throws Exception {
        final ApiUser user = new ApiUser();
        user.setUid( "test" );
        user.setEmail( "test@test.com" );
        Mockito.when( roleValidator.validateByAgentId( Mockito.anyString() ) ).thenReturn( user );
        Mockito.doNothing().when( contactUpdateRequestValidator ).validate( Mockito.anyMap() );
        Mockito.when( agentTaskBusinessService.handleAgentTask( Mockito.anyString(), Mockito.anyString(),
                Mockito.any( TaskUpdateRequest.class ) ) ).thenReturn( new AgentResponse( "test" ) );
        this.mockMvc
                .perform( MockMvcRequestBuilders.patch( "/api/agents/test/tasks/taskId" )
                        .content( "{\"description\":\"TSET CLOSED TASK\",\"scheduleDtm\":\"1484906780767\"}" )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test close agent tasks.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testCompleteAgentTask() throws Exception {
        final ApiUser user = new ApiUser();
        user.setUid( "test" );
        user.setEmail( "test@test.com" );
        Mockito.when( roleValidator.validateByAgentId( Mockito.anyString() ) ).thenReturn( user );
        Mockito.doNothing().when( contactUpdateRequestValidator ).validate( Mockito.anyMap() );
        Mockito.when( agentTaskBusinessService.handleAgentTask( Mockito.anyString(), Mockito.anyString(),
                Mockito.any( TaskUpdateRequest.class ) ) ).thenReturn( new AgentResponse( "test" ) );
        this.mockMvc
                .perform( MockMvcRequestBuilders.patch( "/api/agents/test/tasks/taskId/complete" )
                        .content( "{\"description\":\"TSET CLOSED TASK\",\"scheduleDtm\":\"1484906780767\"}" )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test updates last viewed.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testUpdateLastViewed() throws Exception {
        final ApiUser user = new ApiUser();
        user.setUid( "test" );
        user.setEmail( "test@test.com" );
        Mockito.when( roleValidator.validateByAgentId( Mockito.anyString() ) ).thenReturn( user );
        Mockito.doNothing().when( contactUpdateRequestValidator ).validate( Mockito.anyMap() );
        Mockito.when( agentBusinessService.updateLastViewed( Mockito.anyString(), Mockito.anyString(),
                Mockito.any( LastViewedRequest.class ) ) ).thenReturn( new AgentResponse( "test" ) );
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.patch( "/api/agents/test/id" ).content( "{\"objectType\":\"request\"}" )
                                .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test setting of reminder.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testSetReminder() throws Exception {
        final ApiUser user = new ApiUser();
        user.setUid( "test" );
        user.setEmail( "test@test.com" );
        Mockito.when( roleValidator.validateByAgentId( Mockito.anyString() ) ).thenReturn( user );
        Mockito.doNothing().when( contactUpdateRequestValidator ).validate( Mockito.anyMap() );
        Mockito.when( agentReminderBusinessService.setReminder( Mockito.anyString(), Mockito.anyString(),
                Mockito.any( ReminderRequest.class ) ) ).thenReturn( new AgentResponse( "test" ) );
        this.mockMvc.perform( MockMvcRequestBuilders.post( "/api/agents/test/tasks/taskId/reminder" )
                .content( "{\"triggerDtm\":\"1484567486066\"}" ).contentType( MediaType.APPLICATION_JSON )
                .header( "principal", "uid:test@test.com" ) ).andExpect( status().isOk() );
    }

    /**
     * Test get best performing agents by zipcode v1.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetBestPerformingAgentsByZipcodeV1() throws Exception {
        final ApiUser user = new ApiUser();
        user.setUid( "test" );
        user.setEmail( "test@test.com" );
        Mockito.when( agentLookupBusinessService.getBestPerformingAgentsByZipcodeV1( Mockito.anyString(),

                Mockito.anyString(), Mockito.anyString() ) ).thenReturn( new HashMap< String, Object >() );

        this.mockMvc
                .perform( MockMvcRequestBuilders
                        .get( "/api/agents/v1/bestFit?zipcode=02187&crmOpportunityId=006Q000000EWrY7&state=GA" )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test create agent opportunity.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testCreateAgentOpportunity() throws Exception {
        final ApiUser user = new ApiUser();
        user.setUid( "test" );
        user.setEmail( "test@test.com" );
        Mockito.when( roleValidator.validateByAgentId( Mockito.anyString() ) ).thenReturn( user );
        Mockito.when( agentOpportunityBusinessService.createAgentOpportunity( Mockito.any(), Mockito.any() ) )
                .thenReturn( new BaseResponse() );
        this.mockMvc.perform( MockMvcRequestBuilders.post( "/api/agents/test/opportunities" ).content(
                "{\"firstName\":\"test\",\"lastName\":\"test\",\"email\":\"test@test.com\",\"phone\":\"7856369856\",\"mlsPackageType\":\"MIN_SERVICE_STATE_6_MONTHS\",\"propertyAddress\":\"test\",\"leadType\":\"BUYER\",\"preApprovedForMortgage\":\"Unknown\",\"buyerReadinessTimeline\":\"Now\",\"workingWithRealtor\":\"test\",\"priceRange\":\"100K - 150K\",\"interestedZipcodes\":\"NA\"}" )
                .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test send buyer request email.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testSendBuyerRequestEmail() throws Exception {
        final ApiUser user = new ApiUser();
        user.setUid( "test" );
        user.setEmail( "test@test.com" );
        Mockito.when( roleValidator.validateByAgentId( Mockito.anyString() ) ).thenReturn( user );
        final AgentEmailResponse list = new AgentEmailResponse();
        Mockito.when( agentBusinessService.sendAgentEmails( Mockito.anyString(), Mockito.any( List.class ) ) )
                .thenReturn( list );
        this.mockMvc.perform( MockMvcRequestBuilders.post( "/api/agents/test/sendEmail" )
                .content( "{\"to\":[\"test@test.com\"],\"subject\":\"subject\",\"bodyText\":\"bodyText\"}" )
                .contentType( MediaType.APPLICATION_JSON ) ).andExpect( status().isOk() );
    }

    /**
     * Test add to google calendar.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testAddToGoogleCalendar() throws Exception {
        final ApiUser user = new ApiUser();
        user.setUid( "test" );
        user.setEmail( "test@test.com" );
        Mockito.when( roleValidator.validateByAgentId( Mockito.anyString() ) ).thenReturn( user );

        Mockito.when( agentTaskBusinessService.addToGoogleCalendar( Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString() ) ).thenReturn( new AgentResponse( "test" ) );

        this.mockMvc.perform( MockMvcRequestBuilders.post( "/api/agents/123456/tasks/123456/addToGoogleCalendar" )
                .contentType( MediaType.APPLICATION_JSON ) ).andExpect( status().isOk() );
    }

    /**
     * Test onboard.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testOnboard() throws Exception {
        final ApiUser user = new ApiUser();
        user.setUid( "test" );
        user.setEmail( "test@test.com" );
        Mockito.when( agentBusinessService.onboard( Mockito.any() ) ).thenReturn( new BaseResponse() );

        final String data = "test-data";
        final MockMultipartFile imageFile = new MockMultipartFile( "image", "my-image.jpeg", "image/jpeg",
                data.getBytes() );

        this.mockMvc.perform( MockMvcRequestBuilders.fileUpload( "/api/agents/onboard" ).file( imageFile )
                .param( "firstName", "abhi" ).param( "lastName", "kumar" ).param( "email", "abhi.kumar@ownerstest.com" )
                .param( "biodata", "this is my test biodata" ).param( "phone", "1111111114" )
                .param( "address1", "block no # 332" ).param( "address2", "my street4" ).param( "city", "Atlanta" )
                .param( "state", "GA" ).param( "zip", "4115" ).param( "personalEmail", "abhi.kumar@gmail.com" )
                .param( "role", "1099_AGENT" ).param( "managingBrokerId", "manager.test@owners.com" )
                .param( "mobileCarrier", "ATnT" ).param( "agentStartingDate", "07/08/2017" )
                .param( "notes", "This are agents notes" ).param( "drivingRadius", "25" ).param( "status", "active" )
                .param( "deleteFile", "false" ) ).andExpect( status().isOk() );
        verify( beanValidationService ).validateData( any( AgentOnboardRequest.class ) );
        verify( agentBusinessService ).onboard( any( AgentOnboardRequest.class ) );
    }

    /**
     * Test update agent.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testUpdateAgent() throws Exception {
        final ApiUser user = new ApiUser();
        user.setUid( "test" );
        user.setEmail( "test@test.com" );
        Mockito.when( agentBusinessService.updateAgent( "", new AgentOnboardRequest() ) )
                .thenReturn( new BaseResponse() );

        final String data = "test-data";
        final MockMultipartFile imageFile = new MockMultipartFile( "image", "my-image.jpeg", "image/jpeg",
                data.getBytes() );

        this.mockMvc.perform( MockMvcRequestBuilders.fileUpload( "/api/agents/update/abhi.kumar@ownerstest.com" )
                .file( imageFile ).param( "firstName", "abhi" ).param( "lastName", "kumar" )
                .param( "email", "abhi.kumar@ownerstest.com" ).param( "biodata", "this is my test biodata" )
                .param( "phone", "1111111114" ).param( "address1", "block no # 332" ).param( "address2", "my street4" )
                .param( "city", "Atlanta" ).param( "state", "GA" ).param( "zip", "4115" )
                .param( "personalEmail", "abhi.kumar@gmail.com" ).param( "role", "1099_AGENT" )
                .param( "managingBrokerId", "manager.test@owners.com" ).param( "mobileCarrier", "ATnT" )
                .param( "agentStartingDate", "07/08/2017" ).param( "notes", "This are agents notes" )
                .param( "drivingRadius", "25" ).param( "status", "active" ).param( "deleteFile", "false" ) );

    }

    /**
     * Test update agent details.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testUpdateAgentDetails() throws Exception {
        final ApiUser user = new ApiUser();
        user.setUid( "test" );
        user.setEmail( "test@test.com" );
        Mockito.when( roleValidator.validateByAgentId( Mockito.anyString() ) ).thenReturn( user );
        Mockito.when( agentBusinessService.updateAgent( Mockito.anyString(), Mockito.anyMap() ) )
                .thenReturn( new BaseResponse() );
        this.mockMvc
                .perform( MockMvcRequestBuilders.patch( "/api/agents/test" ).content( "{\"onDuty\":true}" )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "test:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test update action flow.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testUpdateActionflow() throws Exception {
        final ApiUser user = new ApiUser();
        user.setUid( "test" );
        user.setEmail( "test@test.com" );
        Mockito.when( roleValidator.validateByAgentId( Mockito.anyString() ) ).thenReturn( user );
        Mockito.when( agentBusinessService.updateAction( Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMap() ) ).thenReturn( new BaseResponse() );
        this.mockMvc
                .perform( MockMvcRequestBuilders.patch( "/api/agents/123456/actionFlow/123456/action/1" )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "test:test@test.com" ) )
                .andExpect( status().isOk() );

    }

    /**
     * Test update note.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testDeleteTask() throws Exception {
        final ApiUser user = new ApiUser();
        user.setUid( "test" );
        user.setEmail( "test@test.com" );
        Mockito.when( roleValidator.validateByAgentId( Mockito.anyString() ) ).thenReturn( user );
        Mockito.when( agentTaskBusinessService.deleteAgentTask( Mockito.anyString(), Mockito.anyString(), any() ) )
                .thenReturn( new AgentResponse( "test" ) );
        this.mockMvc
                .perform( MockMvcRequestBuilders.delete( "/api/agents/test/tasks/56123" )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "test:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test get agent opportunity commission.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetAgentCommission() throws Exception {
        final ApiUser user = new ApiUser();
        user.setUid( "test" );
        user.setEmail( "test@test.com" );
        Mockito.when( roleValidator.validateByAgentId( Mockito.anyString() ) ).thenReturn( user );
        Mockito.when(
                agentOpportunityBusinessService.getOpportunityCommission( Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( new BaseResponse() );
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( "/api/agents/test/opportunities/opportunityId/commission" )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test create agent suggested property.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testCreateAgentSuggestedProperty() throws Exception {
        final ApiUser user = new ApiUser();
        user.setUid( "test" );
        user.setEmail( "test@test.com" );
        Mockito.when( roleValidator.validateByAgentId( Mockito.anyString() ) ).thenReturn( user );
        Mockito.when( agentBusinessService.createAgentSuggestedProperty( Mockito.anyString(),
                Mockito.any( AgentSuggestedPropertyRequest.class ) ) ).thenReturn( new AgentResponse( "uid" ) );
        this.mockMvc.perform( MockMvcRequestBuilders.post( "/api/agents/test/suggestedProperty" ).content(
                "{\"opportunityId\":\"opportunityTest1\",\"dateTime\":\"1484567486066\",\"mlsId\":\"mls123\",\"propertyAddress\":\"PARIS\",\"notes\":\"dummyNotes\"}" )
                .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test update agent signature
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void testSaveAgentSignature() throws Exception {
        final ApiUser user = new ApiUser();
        user.setUid( "test" );
        user.setEmail( "test@test.com" );

        Mockito.when( roleValidator.validateByAgentId( Mockito.anyString() ) ).thenReturn( user );
        Mockito.when( agentBusinessService.saveAgentSignature( Mockito.anyString(), Mockito.any( Object.class ) ) )
                .thenReturn( new BaseResponse() );
        this.mockMvc
                .perform( MockMvcRequestBuilders.put( "/api/agents/test/preferences/signature" )
                        .content( "{\"signatureText\":\"test signature\"}" )
                        .contentType( MediaType.APPLICATION_JSON_VALUE ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    @Test
    public void testSaveAgentPreferencesData() throws Exception {
        final ApiUser user = new ApiUser();
        user.setUid( "test" );
        user.setEmail( "test@test.com" );
        Mockito.when( roleValidator.validateByAgentId( Mockito.anyString() ) ).thenReturn( user );
        Mockito.when( agentBusinessService.saveAgentPreferencesData( Mockito.anyString(), Mockito.anyString(),
                Mockito.anyBoolean(), Mockito.anyMap() ) ).thenReturn( new BaseResponse() );
        this.mockMvc
                .perform( MockMvcRequestBuilders
                        .put( "/api/agents/test/add/preferences?path=data.pref.sms&agentSpecific=true" )
                        .content( "{\"signatureText\":\"test signature\"}" )
                        .contentType( MediaType.APPLICATION_JSON_VALUE ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test delete reminder.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testDeleteReminder() throws Exception {
        final ApiUser user = new ApiUser();
        user.setUid( "test" );
        user.setEmail( "test@test.com" );
        Mockito.when( roleValidator.validateByAgentId( Mockito.anyString() ) ).thenReturn( user );
        Mockito.when( agentReminderBusinessService.deleteReminder( Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString() ) ).thenReturn( new AgentResponse( "test" ) );
        this.mockMvc
                .perform( MockMvcRequestBuilders.delete( "/api/agents/test/tasks/56123/reminders/remind123" )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "test:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test is email permitted returns true.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testIsEmailPermitted_returns_true() throws Exception {
        final ApiUser user = new ApiUser();
        user.setUid( "test" );
        user.setEmail( "test@test.com" );
        when( roleValidator.validateByAgentId( anyString() ) ).thenReturn( user );
        when( agentOpportunityBusinessService.isCrmIdPermitted( anyString(), anyString() ) )
                .thenReturn( new AgentEmailPermissionResponse() );
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( "/api/agents/testAgent/permissions/crmid/dummyCrmId" )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
        verify( agentOpportunityBusinessService ).isCrmIdPermitted( anyString(), anyString() );
    }

}
