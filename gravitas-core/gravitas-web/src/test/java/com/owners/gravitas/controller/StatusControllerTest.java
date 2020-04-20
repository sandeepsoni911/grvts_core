/**
 * 
 */
package com.owners.gravitas.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.mockito.InjectMocks;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.config.BaseControllerTest;

/**
 * The Class StatusControllerTest.
 *
 * @author harshads
 */
public class StatusControllerTest extends BaseControllerTest {

    /** mock of {@link StatusController}. **/
    @InjectMocks
    private StatusController controller;

    /**
     * before method.
     */
    @BeforeClass
    public void setUpBase() throws Exception {
        super.setUpMockMVC( controller );
    }

    /**
     * Test the get status.
     * 
     * @throws Exception
     *
     */
    @Test
    public void testGetStatus() throws Exception {
        this.mockMvc.perform( get( "/lbstatus" ) ).andExpect( status().isOk() );
    }

}
