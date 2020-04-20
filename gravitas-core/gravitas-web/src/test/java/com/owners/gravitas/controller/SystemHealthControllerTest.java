package com.owners.gravitas.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.business.SystemHealthBusinessService;
import com.owners.gravitas.config.BaseControllerTest;

/**
 * The Class SystemHealthControllerTest.
 * 
 * @author ankusht
 */
public class SystemHealthControllerTest extends BaseControllerTest {

    /** The system health business service. */
    @Mock
    private SystemHealthBusinessService systemHealthBusinessService;

    /** The system health controller. */
    @InjectMocks
    private SystemHealthController systemHealthController;

    /**
     * Sets the up base.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeClass
    public void setUpBase() throws Exception {
        super.setUpMockMVC( systemHealthController );
    }

    /**
     * Test set version.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testSetVersion() throws Exception {
        this.mockMvc.perform( get( "/api/system-health" ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
    }

}
