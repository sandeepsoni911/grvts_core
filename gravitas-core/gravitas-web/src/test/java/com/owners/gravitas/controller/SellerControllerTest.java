package com.owners.gravitas.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.business.OpportunityBusinessService;
import com.owners.gravitas.config.BaseControllerTest;
import com.owners.gravitas.dto.crm.response.OpportunityResponse;
import com.owners.gravitas.dto.request.OpportunityRequest;
import com.owners.gravitas.enums.RecordType;

/**
 * Test class for {@link SellerController}.
 *
 * @author harshads
 */
public class SellerControllerTest extends BaseControllerTest {

    /** mock of {@link SellerController}. **/
    @InjectMocks
    private SellerController controller;

    /** The seller business service. */
    @Mock
    private OpportunityBusinessService sellerBusinessService;

    /**
     * before method.
     */
    @BeforeClass
    public void setUpBase() throws Exception {
        super.setUpMockMVC( controller );
    }

    /**
     * Test the create opportunity for happy flow.
     *
     * @throws Exception
     */
    @Test
    public void testCreateOpportunity() throws Exception {
        final OpportunityResponse response = new OpportunityResponse();
        response.setId( "id1" );
        Mockito.when( sellerBusinessService.createSellerOpportunity( Mockito.any( OpportunityRequest.class ), Mockito.any(RecordType.class)) )
                .thenReturn( response );
        this.mockMvc.perform( post( "/api/opportunity" )
                .content(
                        "{ \"seller\": { \"id\": \"SELLER555\", \"firstName\": \"sellerid\", \"lastName\": \"555\", \"phoneNumber\": \"35555\", \"mainContactNumer\": \"66666\", \"email\": "
                        + "\"seller555@gmail.com\", \"address\": { \"address1\": \"335\", \"address2\": \"west stree\", \"city\": \"Gorgia\", \"state\": \"MI\", \"county\": \"Atlanta\", "
                        + "\"zip\": \"30066\" } }, \"propertyOrder\": { \"orderType\": \"NON-REO\", \"property\": { \"listingId\": \"LISTING66\", \"mlsBoard\": \"mlsBoard\", \"price\": 2222, "
                        + "\"numberBeds\": 2, \"numberFullBaths\": 2, \"numberHalfBaths\": 1, \"squareFeet\": 1022, \"address\": { \"address1\": \"555\", \"address2\": \"west stree\", "
                        + "\"city\": \"WC\", \"state\": \"PA\", \"county\": \"WC\", \"zip\": \"54455\" } } } }" )
                .contentType( MediaType.APPLICATION_JSON_UTF8 ) ).andExpect( status().isOk() );

        this.mockMvc.perform( post( "/api/seller/listing" )
                .content(
                        "{ \"seller\": { \"id\": \"SELLER555\", \"firstName\": \"sellerid\", \"lastName\": \"555\", \"phoneNumber\": \"35555\", \"mainContactNumer\": \"66666\", \"email\": "
                        + "\"seller555@gmail.com\", \"address\": { \"address1\": \"335\", \"address2\": \"west stree\", \"city\": \"Gorgia\", \"state\": \"MI\", \"county\": \"Atlanta\", "
                        + "\"zip\": \"30066\" } }, \"propertyOrder\": { \"orderType\": \"NON-REO\", \"property\": { \"listingId\": \"LISTING66\", \"mlsBoard\": \"mlsBoard\", \"price\": 2222, "
                        + "\"numberBeds\": 2, \"numberFullBaths\": 2, \"numberHalfBaths\": 1, \"squareFeet\": 1022, \"address\": { \"address1\": \"555\", \"address2\": \"west stree\", "
                        + "\"city\": \"WC\", \"state\": \"PA\", \"county\": \"WC\", \"zip\": \"54455\" } } } }" )
                .contentType( MediaType.APPLICATION_JSON_UTF8 ) ).andExpect( status().isOk() );
    }

    /**
     * Test the create opportunity for wrong url.
     *
     * @throws Exception
     */
    @Test
    public void testInvalidPostUrl() throws Exception {
        this.mockMvc.perform( post( "/api/test" )
                .content(
                        "{\"mlsId\":\"Vishwanath\",\"seller\" : {\"id\" :\"test1\", \"lastName\" : \"Mathapati\"},\"propertyOrder\": { \"property\": {\"address\" :{\"state\" : \"GA\"}}}}" )
                .contentType( MediaType.APPLICATION_JSON_UTF8 ) ).andExpect( status().is( 404 ) );
    }

}
