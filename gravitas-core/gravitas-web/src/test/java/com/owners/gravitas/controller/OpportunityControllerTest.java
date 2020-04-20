package com.owners.gravitas.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.owners.gravitas.business.OpportunityBusinessService;
import com.owners.gravitas.config.BaseControllerTest;
import com.owners.gravitas.dto.OpportunityDetails;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.BaseResponse.Status;

/**
 * Test class for {@link OpportunityControllerTest}.
 *
 * @author harshads
 */
public class OpportunityControllerTest extends BaseControllerTest {
    /** mock of {@link OpportunityController}. **/
    @InjectMocks
    private OpportunityController controller;

    /** mock of {@link OpportunityBusinessService}. **/
    @Mock
    private OpportunityBusinessService businessService;

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
     * Test assign agent to opportunity.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testAssignAgentToOpportunity() throws Exception {
        final Map< String, Object > request = new HashMap< String, Object >();
        final String opportunityId = "opportunityId";
        final Gson gson = new Gson();
        final String jsonContent = gson.toJson( request );
        Mockito.doNothing().when( businessService ).updateOpportunity( opportunityId, request );
        this.mockMvc
                .perform( MockMvcRequestBuilders.patch( "/webapi/opportunities/{crmId}/assignAgent", "crmid" )
                        .content( jsonContent ).contentType( MediaType.APPLICATION_JSON_VALUE ) )
                .andExpect( status().isOk() );
    }
    
    /**
     * Test reject agent for opportunity.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testRejectAgentForOpportunity() throws Exception {
        final Map< String, Object > request = new HashMap< String, Object >();
        final String opportunityId = "opportunityId";
        final Gson gson = new Gson();
        final String jsonContent = gson.toJson( request );
        Mockito.doNothing().when( businessService ).rejectOpportunity( opportunityId, request );
        this.mockMvc
                .perform( MockMvcRequestBuilders.patch( "/webapi/opportunities/{crmId}/rejectAgent", "crmid" )
                        .content( jsonContent ).contentType( MediaType.APPLICATION_JSON_VALUE ) )
                .andExpect( status().isOk() );
    }

    /**
     * Test get opportunity by CRM id.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetOpportunityByCRMId() throws Exception {
        final String crmId = "crmid";
        final OpportunityDetails opportunityDetails = new OpportunityDetails();
        Mockito.when( businessService.getOpportunityByCRMId( crmId ) ).thenReturn( opportunityDetails );
        this.mockMvc.perform( MockMvcRequestBuilders.get( "/webapi/opportunities/{crmId}", "crmid" ) )
                .andExpect( status().isOk() );
    }
    
    /**
     * Test get opportunity by CRM id.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testForwardToReferralExchange() throws Exception {
        final String crmId = "crmid";
        this.mockMvc.perform( MockMvcRequestBuilders.post( "/webapi/opportunities/{crmId}/refer", crmId ) 
                .content( "{  }").contentType( MediaType.APPLICATION_JSON ))
                .andExpect( status().isOk() );
    }
    
    /**
     * Test create skip meeting action flow controller
     * 
     * @throws Exception
     */
    @Test
    public void testCreateSkipMeetingActionFlow() throws Exception {
        final String crmId = "crmId";
        final String insideSalesEmail = "email@email.com";
        Mockito.when( businessService.createSkipMeetingActionFlow( insideSalesEmail, crmId ) ).thenReturn(
                new BaseResponse( Status.SUCCESS, "You have clicked SKIP and NO tour/meeting has been created." ) );
        this.mockMvc.perform( MockMvcRequestBuilders.post( "/webapi/opportunities/{crmId}/skipmeeting/{insideSalesEmail}",
                crmId, insideSalesEmail ) ).andExpect( status().isOk() );
    }
   
}
