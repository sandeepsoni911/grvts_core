package com.owners.gravitas.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.business.LeadBusinessService;
import com.owners.gravitas.config.BaseControllerTest;
import com.owners.gravitas.dto.request.GenericLeadRequest;
import com.owners.gravitas.dto.request.MakeOfferRequest;
import com.owners.gravitas.dto.request.RequestInformationRequest;
import com.owners.gravitas.dto.request.ScheduleTourRequest;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.LeadResponse;

/**
 * Test class for {@link LeadController}.
 *
 * @author harshads
 */
public class LeadControllerTest extends BaseControllerTest {

    /** mock of {@link LeadController}. **/
    @InjectMocks
    private LeadController controller;

    /** mock of {@link LeadBusinessService}. **/
    @Mock
    private LeadBusinessService businessService;

    /**
     * before method.
     */
    @BeforeClass
    public void setUpBase() throws Exception {
        super.setUpMockMVC( controller );
    }

    /**
     * Test create generic lead.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testCreateGenericLead() throws Exception {
        final LeadResponse leadResponse = new LeadResponse( Status.SUCCESS, "", "" );
        leadResponse.setId( "lead1" );
        Mockito.when(
                businessService.createLead( Mockito.any( GenericLeadRequest.class ), Mockito.any( Boolean.class ), Mockito.any( String.class ) ) )
                .thenReturn( leadResponse );
        this.mockMvc.perform( post( "/api/prospect" )
                .content( "{\"lastName\":\"Mathapati\",\"email\":\"vishu287@gmail.com\","
                        + "\"leadType\":\"BOTH\",\"requestType\":\"MAKE_OFFER\",\"source\":\"Owners\",\"leadSourceUrl\" : \"http://test.com\"}" )
                .contentType( MediaType.APPLICATION_JSON_UTF8 ) ).andExpect( status().isOk() );
    }

    /**
     * Test the create buyer lead for happy flow.
     *
     * @throws Exception
     */
    @Test
    public void testCreateBuyerLead() throws Exception {
        final LeadResponse leadResponse = new LeadResponse( Status.SUCCESS, "", "" );
        leadResponse.setId( "lead1" );
        Mockito.when(
                businessService.createLead( Mockito.any( GenericLeadRequest.class ), Mockito.any( Boolean.class ), Mockito.any( String.class ) ) )
                .thenReturn( leadResponse );
        this.mockMvc.perform( post( "/api/buyer/lead" )
                .content( "{\"firstName\":\"Vishwanath\",\"lastName\":\"Mathapati\",\"email\":\"vishu287@gmail.com\","
                        + "\"phone\":\"20042016\",\"company\":\"company-vis\",\"source\":\"Owners\",\"comments\" : \"test comments\"}" )
                .contentType( MediaType.APPLICATION_JSON_UTF8 ) ).andExpect( status().isOk() );
    }

    /**
     * Test the create buyer lead for wrong url.
     *
     * @throws Exception
     */
    @Test
    public void testInvalidPostUrl() throws Exception {
        this.mockMvc.perform( post( "/lead/test" )
                .content(
                        "{\"firstName\":\"First\",\"lastName\":\"Last\",\"email\":\"tes123@gmail.com\",\"phone\":\"1231311\"}" )
                .contentType( MediaType.APPLICATION_JSON_UTF8 ) ).andExpect( status().is( 404 ) );
    }

    /**
     * Test the create buyer lead for happy flow.
     *
     * @throws Exception
     */
    @Test
    public void testMakeanofferLead() throws Exception {
        final LeadResponse leadResponse = new LeadResponse( Status.SUCCESS, "", "" );
        leadResponse.setId( "lead1" );
        Mockito.when( businessService.createLead( Mockito.any( MakeOfferRequest.class ), Mockito.any( String.class ) ) ).thenReturn( leadResponse );
        this.mockMvc.perform( post( "/api//make-offer" )
                .content( "{\"firstName\":\"Vishwanath\",\"lastName\":\"Mathapati\",\"email\":\"vishu287@gmail.com\","
                        + "\"leadSourceUrl\":\"http://test.com\",\"company\":\"company-vis\",\"source\":\"Owners\",\"comments\" : \"test comments\"}" )
                .contentType( MediaType.APPLICATION_JSON_UTF8 ) ).andExpect( status().isOk() );
    }

    /**
     * Test the create buyer lead for happy flow.
     *
     * @throws Exception
     */
    @Test
    public void testScheduletourLead() throws Exception {
        final LeadResponse leadResponse = new LeadResponse( Status.SUCCESS, "", "" );
        leadResponse.setId( "lead1" );
        Mockito.when( businessService.createLead( Mockito.any( ScheduleTourRequest.class ), Mockito.any( String.class ) ) )
                .thenReturn( leadResponse );
        this.mockMvc.perform( post( "/api/schedule-tour" )
                .content( "{\"firstName\":\"Vishwanath\",\"lastName\":\"Mathapati\",\"email\":\"vishu287@gmail.com\","
                        + "\"leadSourceUrl\":\"http://test.com\",\"company\":\"company-vis\",\"source\":\"Owners\",\"comments\" : \"test comments\"}" )
                .contentType( MediaType.APPLICATION_JSON_UTF8 ) ).andExpect( status().isOk() );
    }

    /**
     * Test the create buyer lead for happy flow.
     *
     * @throws Exception
     */
    @Test
    public void testAsaquestionLead() throws Exception {
        final LeadResponse leadResponse = new LeadResponse( Status.SUCCESS, "", "" );
        leadResponse.setId( "lead1" );
        Mockito.when( businessService.createLead( Mockito.any( RequestInformationRequest.class ), Mockito.any( String.class ) ) )
                .thenReturn( leadResponse );
        this.mockMvc.perform( post( "/api/ask-question" )
                .content( "{\"firstName\":\"Vishwanath\",\"lastName\":\"Mathapati\",\"email\":\"vishu287@gmail.com\","
                        + "\"leadSourceUrl\":\"http://test.com\",\"company\":\"company-vis\",\"source\":\"Owners\",\"comments\" : \"test comments\"}" )
                .contentType( MediaType.APPLICATION_JSON_UTF8 ) ).andExpect( status().isOk() );
    }

    /**
     * Test creat unbounce lead.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testCreatUnbounceLead() throws Exception {
        final LeadResponse leadResponse = new LeadResponse( Status.SUCCESS, "", "" );
        leadResponse.setId( "lead1" );
        Mockito.when( businessService.createUnbounceLead( Mockito.anyString(), Mockito.any( String.class ) ) ).thenReturn( leadResponse );
        this.mockMvc
                .perform( post( "/api/unbounce/prospect" )
                        .param( "data.json",
                                "{\"lastName\":[\"vishwa1642\"],\"email\":[\"vishu-19a@sharklasers.com\"],\"source\":[\"source\"],\"leadType\":[\"BUYER\"],\"leadSourceUrl\":[\"http://www.google.com\"],\"requestType\":[\"SCHEDULE_TOUR\"]}" )
                        .contentType( MediaType.APPLICATION_FORM_URLENCODED_VALUE ) )
                .andExpect( status().isOk() );
    }

}
