package com.owners.gravitas.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.business.VersionBusinessService;
import com.owners.gravitas.config.BaseControllerTest;
import com.owners.gravitas.dto.ApiUser;
import com.owners.gravitas.dto.request.VersionRequest;
import com.owners.gravitas.exception.InvalidArgumentException;
import com.owners.gravitas.util.GravitasWebUtil;

/**
 * Test class for {@link VersionControllerTest}.
 *
 * @author nishak
 */
public class VersionControllerTest extends BaseControllerTest {

    /** The controller. */
    @InjectMocks
    private VersionController controller;

    /** The gravitas web util. */
    @Mock
    private GravitasWebUtil gravitasWebUtil;

    /** The version business service. */
    @Mock
    private VersionBusinessService versionBusinessService;

    /**
     * Sets the up base.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeClass
    public void setUpBase() throws Exception {
        super.setUpMockMVC( controller );
    }

    /**
     * Test set version.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testSetVersion() throws Exception {
        ApiUser user = new ApiUser();
        Mockito.when( gravitasWebUtil.getAppUser() ).thenReturn( user );
        Mockito.doNothing().when( versionBusinessService )
                .updateClientVersion( Mockito.anyListOf( VersionRequest.class ) );
        this.mockMvc.perform( post( "/webapi/app/version" ).header( "principal", "uid:test@test.com" )
                .content( "{ \"versionList\": [{"
                        + "\"clientType\": \"AgentApp-IOS\",\"minVersion\": \"1.0.3\",\"minMessage\": \"Please update to the latest version of the app.\",\"currentVersion\": \"1.0.4\","
                        + "\"currentMessage\": \"Please update to the latest version of the app.\","
                        + "\"url\": \"https: //apps.asaserv.us/owners\"}]}" )
                .contentType( MediaType.APPLICATION_JSON_UTF8 ) ).andExpect( status().isOk() );
    }

    /**
     * Test set version with same version values.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testSetVersionWithSameVersionValues() throws Exception {
        ApiUser user = new ApiUser();
        Mockito.when( gravitasWebUtil.getAppUser() ).thenReturn( user );
        Mockito.doNothing().when( versionBusinessService )
                .updateClientVersion( Mockito.anyListOf( VersionRequest.class ) );
        this.mockMvc.perform( post( "/webapi/app/version" ).header( "principal", "uid:test@test.com" )
                .content( "{ \"versionList\": [{"
                        + "\"clientType\": \"AgentApp-IOS\",\"minVersion\": \"1.0.4\",\"minMessage\": \"Please update to the latest version of the app.\",\"currentVersion\": \"1.0.4\","
                        + "\"currentMessage\": \"Please update to the latest version of the app.\","
                        + "\"url\": \"https: //apps.asaserv.us/owners\"}]}" )
                .contentType( MediaType.APPLICATION_JSON_UTF8 ) ).andExpect( status().isOk() );
    }
}
