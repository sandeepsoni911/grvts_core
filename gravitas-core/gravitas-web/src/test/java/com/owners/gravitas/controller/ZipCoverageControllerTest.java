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
import com.owners.gravitas.business.ZipCoverageBusinessService;
import com.owners.gravitas.config.BaseControllerTest;
import com.owners.gravitas.dto.request.AgentCoverageRequest;
import com.owners.gravitas.dto.request.ZipCodeDistanceRequest;
import com.owners.gravitas.dto.response.BaseResponse;

/**
 * The Class ZipCoverageControllerTest.
 * 
 * @author pabhishek
 */
public class ZipCoverageControllerTest extends BaseControllerTest {

    /** The zip coverage controller. */
    @InjectMocks
    private ZipCoverageController zipCoverageController;

    /** The zip coverage business service. */
    @Mock
    private ZipCoverageBusinessService zipCoverageBusinessService;

    /**
     * Sets the up base.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeClass
    public void setUpBase() throws Exception {
        super.setUpMockMVC( zipCoverageController );
    }

    /**
     * Test exclude zip codes.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testExcludeZipCodes() throws Exception {
        final ZipCodeDistanceRequest zipCodeDistanceRequest = new ZipCodeDistanceRequest();
        final Gson gson = new Gson();
        final String jsonContent = gson.toJson( zipCodeDistanceRequest );
        final BaseResponse baseResponse = new BaseResponse();
        Mockito.when( zipCoverageBusinessService.excludeZipCodes( zipCodeDistanceRequest ) ).thenReturn( baseResponse );
        this.mockMvc.perform( MockMvcRequestBuilders.patch( "/api/zipDistance" ).content( jsonContent )
                .contentType( MediaType.APPLICATION_JSON_VALUE ) ).andExpect( status().isOk() );
    }

    /**
     * Test update agent servlable zip code.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testUpdateAgentServlableZipCode() throws Exception {
        final AgentCoverageRequest agentCoverageRequest = new AgentCoverageRequest();
        final Gson gson = new Gson();
        final String jsonContent = gson.toJson( agentCoverageRequest );
        final BaseResponse baseResponse = new BaseResponse();
        Mockito.when( zipCoverageBusinessService.updateAgentServlableZipCode( agentCoverageRequest ) )
                .thenReturn( baseResponse );
        this.mockMvc.perform( MockMvcRequestBuilders.patch( "/api/agentCoverage" ).content( jsonContent )
                .contentType( MediaType.APPLICATION_JSON_VALUE ) ).andExpect( status().isOk() );
    }

}
