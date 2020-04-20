package com.owners.gravitas.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.business.PropertyBusinessService;
import com.owners.gravitas.config.BaseControllerTest;
import com.owners.gravitas.dto.response.PropertyDetailsResponse;

/**
 * The Class PropertyControllerTest.
 * 
 * @author pabhishek
 */
public class PropertyControllerTest extends BaseControllerTest {

    /** The property controller. */
    @InjectMocks
    private PropertyController propertyController;

    /** The property business service. */
    @Mock
    private PropertyBusinessService propertyBusinessService;

    /**
     * Sets the up base.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeClass
    public void setUpBase() throws Exception {
        super.setUpMockMVC( propertyController );
    }

    /**
     * Test get property details.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetPropertyDetails() throws Exception {
        Mockito.when( propertyBusinessService.getPropertyDetails( Mockito.anyString() ) )
                .thenReturn( new PropertyDetailsResponse() );
        this.mockMvc.perform(
                MockMvcRequestBuilders.get( "/webapi/property/listingid" ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

}
