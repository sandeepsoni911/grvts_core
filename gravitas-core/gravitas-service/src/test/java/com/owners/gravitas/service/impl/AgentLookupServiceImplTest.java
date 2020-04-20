package com.owners.gravitas.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.agentassgn.EligibleAgent;
import com.owners.gravitas.dto.agentassgn.EligibleAgentResponse;

/**
 * The Class AgentLookupServiceImplTest.
 */
public class AgentLookupServiceImplTest extends AbstractBaseMockitoTest {

    /** The Constant S_CURVE_ENDPOINT. */
    private static final String S_CURVE_ENDPOINT = "http://owners.com/api/agent-assn/bestFit?zip=%s&price=%s&crmOppId=%s";

    /** The agent lookup service impl. */
    @InjectMocks
    private AgentLookupServiceImpl agentLookupServiceImpl;

    /** The rest template. */
    @Mock
    private RestTemplate restTemplate;

    /**
     * Test get best agents from S curve server.
     */
    @Test
    public void testGetBestAgentsFromSCurveServer() {
        ReflectionTestUtils.setField( agentLookupServiceImpl, "bestFitApiEndpoint", S_CURVE_ENDPOINT );
        final String zip = "zip";
        final long price = 500000L;
        final String crmId = "crmId";
        final EligibleAgentResponse response = new EligibleAgentResponse();
        final List< EligibleAgent > expected = new ArrayList<>();
        response.setEligibleAgents( expected );

        when( restTemplate.getForObject( anyString(), any( Class.class ) ) ).thenReturn( response );

        final List< EligibleAgent > actual = agentLookupServiceImpl.getBestAgentsFromSCurveServer( zip, price, crmId );
        assertEquals( expected, actual );
        verify( restTemplate ).getForObject( anyString(), any( Class.class ) );
    }
}
