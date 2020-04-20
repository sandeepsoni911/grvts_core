package com.owners.gravitas.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.owners.gravitas.business.AgentRatingBusinessService;
import com.owners.gravitas.business.UserBusinessService;
import com.owners.gravitas.config.BaseControllerTest;
import com.owners.gravitas.dto.crm.request.UserLoginLogRequest;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.SaveSearchResponse;
import com.owners.gravitas.validator.UserRoleValidator;

/**
 * The Class PublicUserControllerTest.
 */
public class PublicUserControllerTest extends BaseControllerTest {

    /** The public user controller. */
    @InjectMocks
    private PublicUserController publicUserController;

    /** The user business service. */
    @Mock
    private UserBusinessService userBusinessService;

    /** The agent rating business service. */
    @Mock
    private AgentRatingBusinessService agentRatingBusinessService;

    /** The user role validator. */
    @Mock
    private UserRoleValidator roleValidator;

    /**
     * Sets the up base.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeClass
    public void setUpBase() throws Exception {
        super.setUpMockMVC( publicUserController );
    }

    /**
     * Test get user details.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetUserDetails() throws Exception {
        Mockito.when( userBusinessService.getUserDetails( Mockito.anyString() ) ).thenReturn( new BaseResponse() );
        this.mockMvc.perform(
                MockMvcRequestBuilders.get( "/api/users/test@test.com" ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test get agent details.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetAgentDetails() throws Exception {
        Mockito.when( userBusinessService.getAgentDetails( Mockito.anyString() ) ).thenReturn( new BaseResponse() );
        this.mockMvc.perform(
                MockMvcRequestBuilders.get( "/api/users/listings/test" ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test log user login.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testLogUserLogin() throws Exception {
        final UserLoginLogRequest userLoginLogRequest = new UserLoginLogRequest();
        final Gson gson = new Gson();
        final String jsonContent = gson.toJson( userLoginLogRequest );
        Mockito.when( userBusinessService.saveUserLoginLog( userLoginLogRequest ) ).thenReturn( new BaseResponse() );
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.post( "/api/users/login/log" ).contentType( MediaType.APPLICATION_JSON )
                                .content( jsonContent ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test save agent rating.
     *
     * @throws Exception
     *             the exception
     */
   // @Test
    public void testSaveAgentRating() throws Exception {
        final String a = "a";
        final String b = "b";
        Mockito.doNothing().when( agentRatingBusinessService ).saveAgentRating( a, b );
        this.mockMvc.perform( MockMvcRequestBuilders.get( "/api/ratings" ).param( "a", "a" ).param( "b", "b" )
                .header( "principal", "uid:test@test.com" ) ).andExpect( status().isOk() );
    }

    /**
     * Test get agents details.
     *
     * @throws Exception
     *             the exception
     */

    @Test
    public void testGetAgentsDetails() throws Exception {
        final String userId = "test.user@test.com";
        Mockito.doNothing().when( roleValidator ).validateUserId( userId );
        Mockito.when( userBusinessService.getAgentsDetails( Mockito.anyString(), Mockito.anyString() ) )
                .thenReturn( new BaseResponse() );
        this.mockMvc.perform(
                MockMvcRequestBuilders.get( "/api/users/test/agents" ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }
}
