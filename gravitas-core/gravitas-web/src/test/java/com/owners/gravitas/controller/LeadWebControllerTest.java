package com.owners.gravitas.controller;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.owners.gravitas.business.LeadBusinessService;
import com.owners.gravitas.config.BaseControllerTest;
import com.owners.gravitas.dto.LeadDetailsList;
import com.owners.gravitas.dto.request.LeadDetailsRequest;
import com.owners.gravitas.validator.LeadDetailsRequestValidator;

public class LeadWebControllerTest extends BaseControllerTest {
    
    @InjectMocks
    LeadWebController leadWebController;
    
    /** mock of {@link LeadBusinessService}. **/
    @Mock
    private LeadBusinessService leadBusinessService;
    
    /** mock of {@link LeadDetailsRequestValidator}. **/
    @Mock
    private LeadDetailsRequestValidator leadDetailsRequestValidator;
    
    
    /**
     * before method.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeClass
    public void setUpBase() throws Exception {
        super.setUpMockMVC( leadWebController );
    }
    /**
     * Controller test to get the all public leads
     * 
     * @throws Exception
     */
    @Test
    public void testGetPublicAvailableLeads() throws Exception {
        final LeadDetailsRequest leadDetailsRequest = new LeadDetailsRequest();
        leadDetailsRequest.setDirection( "desc" );
        leadDetailsRequest.setPage( 1 );
        leadDetailsRequest.setProperty( "score" );
        leadDetailsRequest.setSize( 100 );
        final Gson gson = new Gson();
        final String jsonContent = gson.toJson( leadDetailsRequest );
        doNothing().when( leadDetailsRequestValidator ).validateLeadDetailsRequest( leadDetailsRequest );
        Mockito.when( leadBusinessService.getAvailableLeads( leadDetailsRequest ) ).thenReturn( new LeadDetailsList() );
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( "/webapi/leads/public")
                        .content( jsonContent ).contentType( MediaType.APPLICATION_JSON_VALUE ) )
                .andExpect( status().isOk() );
    }
    
    /**
     * Controller test to get the all public leads
     * 
     * @throws Exception
     */
    @Test
    public void testGetMyClaimLeads() throws Exception {
        final LeadDetailsRequest leadDetailsRequest = new LeadDetailsRequest();
        leadDetailsRequest.setDirection( "desc" );
        leadDetailsRequest.setPage( 1 );
        leadDetailsRequest.setProperty( "score" );
        leadDetailsRequest.setSize( 100 );
        final Gson gson = new Gson();
        final String jsonContent = gson.toJson( leadDetailsRequest );
        Mockito.when( leadBusinessService.getAllMyLeads( leadDetailsRequest ) ).thenReturn( new LeadDetailsList() );
        this.mockMvc
                .perform( MockMvcRequestBuilders.get( "/webapi/leads/myleads")
                        .content( jsonContent ).contentType( MediaType.APPLICATION_JSON_VALUE ) )
                .andExpect( status().isOk() );
    }
    
    /**
     * Test claim lead by CRM id.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testClaimLead() throws Exception {
        final String crmId = "crmid";
        this.mockMvc.perform( MockMvcRequestBuilders.patch( "/webapi/claim-lead/{crmId}", crmId ) 
                .content( "{  }").contentType( MediaType.APPLICATION_JSON ))
                .andExpect( status().isOk() );
    }
    
    /**
     * Test lead details by CRM id.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testLeadDetails() throws Exception {
        final String crmId = "crmid";
        this.mockMvc.perform( MockMvcRequestBuilders.get( "/webapi/get-lead/{crmId}", crmId ) 
                .content( "{  }").contentType( MediaType.APPLICATION_JSON ))
                .andExpect( status().isOk() );
    }
}
