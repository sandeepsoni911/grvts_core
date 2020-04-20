package com.owners.gravitas.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.owners.gravitas.business.AgentTaskBusinessService;
import com.owners.gravitas.business.GroupManagementBusinessService;
import com.owners.gravitas.business.PropertyBusinessService;
import com.owners.gravitas.business.UserBusinessService;
import com.owners.gravitas.config.BaseControllerTest;
import com.owners.gravitas.dto.ApiUser;
import com.owners.gravitas.dto.request.SavedSearchChimeraRequest;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.CheckScheduleMeetingValidationResponse;
import com.owners.gravitas.dto.response.CheckinDetailResponse;
import com.owners.gravitas.dto.response.GroupDetailResponse;
import com.owners.gravitas.dto.response.SaveSearchResponse;
import com.owners.gravitas.dto.response.ScheduleMeetingResponse;
import com.owners.gravitas.dto.response.SuggestionsResponse;
import com.owners.gravitas.util.GravitasWebUtil;
import com.owners.gravitas.validator.UserRoleValidator;

// TODO: Auto-generated Javadoc
/**
 * The Class UserControllerTest.
 *
 * @author amits
 */
public class UserControllerTest extends BaseControllerTest {

    /** mock of {@link UserController}. **/
    @InjectMocks
    private UserController controller;

    /** The role validator. */
    @Mock
    private UserRoleValidator roleValidator;

    /** The gravitas web util. */
    @Mock
    private GravitasWebUtil gravitasWebUtil;

    /** The user business service. */
    @Mock
    private UserBusinessService userBusinessService;

    /** The group management business service. */
    @Mock
    private GroupManagementBusinessService groupManagementBusinessService;

    /** The agent task business servicse. */
    @Mock
    private AgentTaskBusinessService agentTaskBusinessService;
    
    /** The property business service. */
    @Mock
    private PropertyBusinessService propertyBusinessService;

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
     * Test get agent details.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetAgentDetails() throws Exception {
        final String email = "test.user@test.com";
        Mockito.when( userBusinessService.getUserDetails( email ) ).thenReturn( new BaseResponse() );
        this.mockMvc.perform( MockMvcRequestBuilders.get( "/webapi/users/{email}", email ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test users by role.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetUsersByFilters() throws Exception {
        final String role = "agent";
        final String filter = "default";
        Mockito.when( userBusinessService.getUsersByFilters( role, filter ) ).thenReturn( new BaseResponse() );
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.get( "/webapi/users" ).param( "role", role ).param( "filter", filter ) )
                .andExpect( status().isOk() );

        verify( userBusinessService ).getUsersByFilters( role, filter );
    }

    /**
     * Test get user roles by email.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetUserRolesByEmail() throws Exception {
        final String email = "test.user@test.com";
        Mockito.doNothing().when( roleValidator ).validateByUserEmail( email );
        Mockito.when( userBusinessService.getUserRolesByEmail( email ) ).thenReturn( new BaseResponse() );
        this.mockMvc.perform( MockMvcRequestBuilders.get( "/webapi/users/{email}/roles", email ) )
                .andExpect( status().isOk() );

        verify( userBusinessService ).getUserRolesByEmail( email );
    }

    /**
     * Test reset password.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testResetPassword() throws Exception {
        final String email = "test.user@test.com";
        Mockito.when( userBusinessService.resetPassword( email ) ).thenReturn( new BaseResponse() );
        this.mockMvc.perform( MockMvcRequestBuilders.patch( "/webapi/users/{email}/password", email ) )
                .andExpect( status().isOk() );

        verify( userBusinessService ).resetPassword( email );
    }

    /**
     * Test is google user exists.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testIsGoogleUserExists() throws Exception {
        final String email = "test.user@test.com";
        Mockito.when( userBusinessService.isGoogleUserExists( email ) ).thenReturn( true );
        this.mockMvc.perform( MockMvcRequestBuilders.get( "/webapi/users/{email}/exists", email ) )
                .andExpect( status().isOk() );
        verify( userBusinessService ).isGoogleUserExists( email );
    }

    /**
     * Test get agents by manager.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetAgentsByManager() throws Exception {
        final Set< String > roles = new HashSet< String >();
        final ApiUser user = new ApiUser();
        final String email = "test@a.com";
        final String role = "MANAGING_BROKER";
        user.setRoles( roles );
        user.setEmail( email );

        final String filter = "test";
        final BaseResponse baseResponse = new BaseResponse();

        Mockito.when( roleValidator.validateByUserEmail( email, role ) ).thenReturn( user );
        Mockito.when( userBusinessService.getAgentsByManager( email, filter ) ).thenReturn( baseResponse );
        this.mockMvc.perform(
                MockMvcRequestBuilders.get( "/webapi/managers/{email}/users", email ).param( "filter", filter ) )
                .andExpect( status().isOk() );
        verify( userBusinessService ).getAgentsByManager( email, filter );
    }

    /**
     * Test get groups by agent.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetGroupsByAgent() throws Exception {
        final String email = "test@test.com";
        final String url = "/webapi/users/" + email + "/groups";
        when( groupManagementBusinessService.getGroupsByAgent( email ) ).thenReturn( new GroupDetailResponse() );
        this.mockMvc.perform(
                get( url ).contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
        verify( groupManagementBusinessService ).getGroupsByAgent( email );
    }

    /**
     * Test update agents groups.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testUpdateAgentsGroups() throws Exception {
        final String email = "test@test.com";
        final String url = "/webapi/users/" + email + "/groups";
        when( groupManagementBusinessService.updateAgentsGroups( Mockito.anyString(),
                Mockito.anyListOf( String.class ) ) ).thenReturn( new BaseResponse() );
        this.mockMvc.perform( put( url ).content( "[\"groupName1\"]" ).contentType( MediaType.APPLICATION_JSON )
                .header( "principal", "uid:test@test.com" ) ).andExpect( status().isOk() );
        verify( groupManagementBusinessService ).updateAgentsGroups( Mockito.anyString(),
                Mockito.anyListOf( String.class ) );
    }

    /**
     * Test get user activity.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetUserActivity() throws Exception {
        final String input = "test@test.com";
        final String url = "/webapi/users/activity";
        final BaseResponse baseResponse = new BaseResponse();
        Mockito.doNothing().when( roleValidator ).validateUserActivityInput( input );
        Mockito.when( userBusinessService.getUserActivityDetails( input ) ).thenReturn( baseResponse );
        this.mockMvc.perform( MockMvcRequestBuilders.get( url ).param( "input", input ) ).andExpect( status().isOk() );
        verify( userBusinessService ).getUserActivityDetails( input );
    }

    /**
     * Test get checkin details.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetCheckinDetails() throws Exception {
        final String search = "test";
        final String fromDate = "fromDate";
        final String toDate = "toDate";
        final String url = "/webapi/users/checkin-details";
        final CheckinDetailResponse checkinDetailResponse = new CheckinDetailResponse();
        Mockito.when( agentTaskBusinessService.getCheckinDetails( search, fromDate, toDate ) )
                .thenReturn( checkinDetailResponse );
        this.mockMvc.perform( MockMvcRequestBuilders.get( url ).param( "search", search ).param( "fromDate", fromDate )
                .param( "toDate", toDate ) ).andExpect( status().isOk() );
        verify( agentTaskBusinessService ).getCheckinDetails( search, fromDate, toDate );
    }

    /**
     * Testget questionnaire.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testgetQuestionnaire() throws Exception {
        final String type = "test";
        final String url = "/webapi/users/questionnaire";
        Mockito.when( agentTaskBusinessService.getQuestionnaire( type ) ).thenReturn( new HashSet< String >() );
        this.mockMvc.perform( MockMvcRequestBuilders.get( url ).param( "type", type ) ).andExpect( status().isOk() );
        verify( agentTaskBusinessService ).getQuestionnaire( type );
    }

    /**
     * Testanswer questionnaire.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testanswerQuestionnaire() throws Exception {
        final Map< String, Object > request = new HashMap<>();
        final String url = "/webapi/users/answer-questionnaire";
        Mockito.when( agentTaskBusinessService.answerQuestionnaire( request ) ).thenReturn( new BaseResponse() );
        this.mockMvc.perform( MockMvcRequestBuilders.post( url ).content( "{  }" )
                .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
        verify( agentTaskBusinessService ).answerQuestionnaire( request );
    }
    
    @Test
    public void testViewExistSaveSearch() throws Exception {
        final String userId = "abc123";
        Mockito.doNothing().when( roleValidator ).validateUserId( userId );
        Mockito.when( userBusinessService.viewExistSaveSearch( Mockito.anyString() ) )
                .thenReturn( new SaveSearchResponse() );
        this.mockMvc.perform( MockMvcRequestBuilders.get( "/webapi/users/test/saveSearchDetails" ).header( "principal",
                "uid:test@test.com" ) ).andExpect( status().isOk() );
    }
    
    /**
     * Test create save search.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testCreateSaveSearch() throws Exception {
        final SavedSearchChimeraRequest request = new SavedSearchChimeraRequest();
        final String url = "/webapi/users/SaveSearch";
        Mockito.when( userBusinessService.createSaveSearch( request ) ).thenReturn( new Object() );
        this.mockMvc.perform( MockMvcRequestBuilders.post( url ).content( "{  }" )
                .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test create save search.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testUpdateSaveSearch() throws Exception {
        final SavedSearchChimeraRequest request = new SavedSearchChimeraRequest();
        final String url = "/webapi/users/SaveSearch";
        Mockito.when( userBusinessService.createSaveSearch( request ) ).thenReturn( new Object() );
        this.mockMvc.perform( MockMvcRequestBuilders.put( url ).content( "{  }" )
                .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test Get Schedule Meeting Details for Agent.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetScheduleMeetingDetailsForBuyerWithFromAndToDateAsNUll() throws Exception {
        final Map< String, Object > request = new HashMap< String, Object >();
        final String email = "test_buyer@test.com";
        final String url = "/webapi/users/{email}/meetings/status";
        final Gson gson = new Gson();
        final String jsonContent = gson.toJson( request );
        final ScheduleMeetingResponse ScheduleMeetingResponse = new ScheduleMeetingResponse();
        when( userBusinessService.getScheduleMeetingDetails( email, null, null, false ) )
                .thenReturn( ScheduleMeetingResponse );
        this.mockMvc.perform( MockMvcRequestBuilders.get( url, email ) ).andExpect( status().isOk() );
    }

    /**
     * Test Get Schedule Meeting Details for Agent.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetScheduleMeetingDetailsForAgentWithFromAndToDateAsNUll() throws Exception {
        final String email = "test_agent@test.com";
        final String url = "/webapi/users/" + email + "/meetings/status";
        final ScheduleMeetingResponse ScheduleMeetingResponse = new ScheduleMeetingResponse();
        when( userBusinessService.getScheduleMeetingDetails( email, null, null, true ) )
                .thenReturn( ScheduleMeetingResponse );
        this.mockMvc.perform( get( url ).content( "{ }" ).contentType( MediaType.APPLICATION_JSON ).header( "principal",
                "uid:test@test.com" ) ).andExpect( status().isOk() );
    }

    /**
     * Test Get Suggetions.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetSuggetionsWithDummySuggestionId() throws Exception {
        final String suggetionId = "dummy";
        final String url = "/webapi/suggetions/{suggetionId}";
        final SuggestionsResponse suggestionsResponse = new SuggestionsResponse();
        when( propertyBusinessService.getAllOptionsInLocation( suggetionId ) ).thenReturn( suggestionsResponse );
        this.mockMvc.perform( MockMvcRequestBuilders.get( url, suggetionId ) ).andExpect( status().isOk() );
    }

    /**
     * Test Get Schedule Meeting Details for Agent and Buyer.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetScheduleMeetingDetailsForAllWithFromAndToDateAsNUll() throws Exception {
        final String email = "test_agent@test.com";
        final String url = "/webapi/users/meetings/status";
        final ScheduleMeetingResponse ScheduleMeetingResponse = new ScheduleMeetingResponse();
        when( userBusinessService.getScheduleMeetingDetails( null, null, null, null ) )
                .thenReturn( ScheduleMeetingResponse );
        this.mockMvc.perform( MockMvcRequestBuilders.get( url ) ).andExpect( status().isOk() );
    }
    
    
    /**
     * Test Get Suggetions.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testIsAgentScheduledMeetingExists() throws Exception {
        final String crmId = "dummy";
        final String agentEmailId = "test@dummy.com";
        final String url = "/webapi/users/agents/{agentEmail}/opportunity/{crmId}/calendar/events/exists";
        final CheckScheduleMeetingValidationResponse response = new CheckScheduleMeetingValidationResponse();
        when( agentTaskBusinessService.checkIfAgentMeetingEventExists( agentEmailId, crmId ) ).thenReturn( response );
        this.mockMvc.perform( MockMvcRequestBuilders.get( url, agentEmailId, crmId ) ).andExpect( status().isOk() );
    }

}
