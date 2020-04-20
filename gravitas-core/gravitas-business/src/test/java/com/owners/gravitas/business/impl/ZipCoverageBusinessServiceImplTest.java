package com.owners.gravitas.business.impl;

import static com.owners.gravitas.dto.response.BaseResponse.Status.SUCCESS;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.request.AgentCoverageRequest;
import com.owners.gravitas.dto.request.ZipCodeDistanceRequest;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.service.AgentCoverageService;
import com.owners.gravitas.service.AgentDetailsService;
import com.owners.gravitas.service.ZipDistanceService;

/**
 * The Class ZipCoverageBusinessServiceImplTest.
 *
 * @author amits
 */
public class ZipCoverageBusinessServiceImplTest extends AbstractBaseMockitoTest {

    /** The zip coverage business service. */
    @InjectMocks
    private ZipCoverageBusinessServiceImpl zipCoverageBusinessService;

    /** The zip distance service. */
    @Mock
    private ZipDistanceService zipDistanceService;

    /** The agent coverage service. */
    @Mock
    private AgentCoverageService agentCoverageService;

    /** The agent details service. */
    @Mock
    private AgentDetailsService agentDetailsService;

    /**
     * Test exclude zip codes.
     */
    @Test
    public void testExcludeZipCodes() {
        final ZipCodeDistanceRequest request = new ZipCodeDistanceRequest();
        List< String > zips = new ArrayList<>();
        zips.add( "42343" );
        request.setZipCodes( zips );
        final BaseResponse response = zipCoverageBusinessService.excludeZipCodes( request );
        Assert.assertEquals( response.getStatus(), SUCCESS );
    }

    /**
     * Test update agent servlable zip code.
     */
    @Test
    public void testUpdateAgentServlableZipCode() {
        final AgentCoverageRequest agentCoverageRequest = new AgentCoverageRequest();
        List< String > zips = new ArrayList<>();
        zips.add( "42343" );
        agentCoverageRequest.setEmail( "test" );
        agentCoverageRequest.setZipCodes( zips );
        final BaseResponse response = zipCoverageBusinessService.updateAgentServlableZipCode( agentCoverageRequest );
        Assert.assertEquals( response.getStatus(), SUCCESS );
    }
}
