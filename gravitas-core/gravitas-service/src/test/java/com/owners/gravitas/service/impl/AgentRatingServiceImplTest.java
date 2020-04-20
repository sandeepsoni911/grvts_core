package com.owners.gravitas.service.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.domain.entity.AgentRating;
import com.owners.gravitas.repository.AgentRatingRepository;

/**
 * The Class AgentRatingServiceImplTest.
 *
 * @author raviz
 */
public class AgentRatingServiceImplTest extends AbstractBaseMockitoTest {

    /** The agent rating service impl. */
    @InjectMocks
    private AgentRatingServiceImpl agentRatingServiceImpl;

    /** The agent rating repository. */
    @Mock
    private AgentRatingRepository agentRatingRepository;

    /**
     * Test save should save the record.
     */
    @Test
    public void testSaveShouldSaveTheRecord() {
        final AgentRating agentRating = new AgentRating();
        agentRatingServiceImpl.save( agentRating );
        verify( agentRatingRepository ).save( agentRating );
    }

    /**
     * Test find one should return agent rating.
     */
    @Test
    public void testFindOneShouldReturnAgentRating() {
        final String id = "testId";
        final AgentRating expectedAgentRating = new AgentRating();
        when( agentRatingRepository.findOne( id ) ).thenReturn( expectedAgentRating );
        final AgentRating actualAgentRating = agentRatingServiceImpl.findOne( id );
        verify( agentRatingRepository ).findOne( id );
        assertEquals( actualAgentRating, expectedAgentRating );
    }

    /**
     * Test find by crm id and stage and client email and agent details should
     * return agent rating.
     */
    @Test
    public void testFindByCrmIdAndStageAndClientEmailAndAgentDetailsShouldReturnAgentRating() {
        final AgentDetails agentDetails = new AgentDetails();
        when( agentRatingRepository.findByCrmIdAndStageAndClientEmailAndAgentDetails( "testCrmId", "testStage",
                "testClientEmail", agentDetails ) ).thenReturn( new AgentRating() );
        final AgentRating actualAgentRating = agentRatingServiceImpl.findByCrmIdAndStageAndClientEmailAndAgentDetails(
                "testCrmId", "testStage", "testClientEmail", agentDetails );
        Assert.assertNotNull( actualAgentRating );
    }
}
