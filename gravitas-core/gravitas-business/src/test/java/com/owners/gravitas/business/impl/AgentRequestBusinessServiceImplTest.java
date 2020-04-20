package com.owners.gravitas.business.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.builder.domain.RequestBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.Request;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.service.AgentRequestService;

/**
 * The Class AgentRequestBusinessServiceImplTest.
 *
 * @author shivamm
 */
public class AgentRequestBusinessServiceImplTest extends AbstractBaseMockitoTest {

    /** The request service. */
    @Mock
    private AgentRequestService requestService;

    /** The request builder. */
    @Mock
    private RequestBuilder requestBuilder;

    /** The agent request service. */
    @Mock
    private AgentRequestService agentRequestService;

    @InjectMocks
    private AgentRequestBusinessServiceImpl agentRequestBusinessServiceImpl;

    /**
     * Before test.
     */
    @BeforeMethod
    public void asetUp() {
        ReflectionTestUtils.setField( agentRequestBusinessServiceImpl, "enableOpportunityBuyerRequest", true );
    }

    /**
     * Should send new opportunity reminder.
     */
    @Test
    public void testCreateAgentRequest() {
        OpportunitySource opportunitySource = new OpportunitySource();
        String agentId = "test";
        String opportunityId = "test";
        Request request = new Request();
        when( requestBuilder.convertTo( opportunitySource ) ).thenReturn( request );
        PostResponse expected = new PostResponse();
        when( requestService.saveRequest( agentId, request ) ).thenReturn( expected );
        PostResponse actual = agentRequestBusinessServiceImpl.createAgentRequest( opportunitySource, agentId,
                opportunityId );
        assertEquals( expected, actual );
    }
}
