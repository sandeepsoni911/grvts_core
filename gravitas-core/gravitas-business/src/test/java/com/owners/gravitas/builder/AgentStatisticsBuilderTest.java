package com.owners.gravitas.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.mockito.InjectMocks;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.AgentStatisticsBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.domain.entity.AgentStatistics;
import com.owners.gravitas.dto.crm.response.AgentStatisticsResponse;

/**
 * The Class AgentStatisticsBuilderTest.
 *
 * @author madhav
 */
public class AgentStatisticsBuilderTest extends AbstractBaseMockitoTest {

    /** The agent statistics builder. */
    @InjectMocks
    private AgentStatisticsBuilder agentStatisticsBuilder;

    /**
     * Test convert to destination not null.
     */
    @Test
    public void testConvertToDestinationNotNull() {
        AgentStatistics agentStatistics = new AgentStatistics();
        AgentDetails agentDetails = new AgentDetails();
        agentStatistics.setAgentDetails( agentDetails );
        agentStatistics.setKey( "score" );
        agentStatistics.setValue( "2.2" );
        agentStatistics.setCreatedDate( new DateTime() );

        List< AgentStatistics > agentStatisticsList = new ArrayList<>();
        agentStatisticsList.add( agentStatistics );

        AgentStatisticsResponse agentResponse = agentStatisticsBuilder.convertTo( agentStatisticsList,
                new AgentStatisticsResponse() );
        assertEquals( agentStatistics.getValue(), agentResponse.getAgentStatistics().get( 0 ).get( "score" ) );
        assertNotNull( agentResponse );
    }

    /**
     * Test convert to destination null.
     */
    @Test
    public void testConvertToDestinationNull() {
        AgentStatistics agentStatistics = new AgentStatistics();
        AgentDetails agentDetails = new AgentDetails();
        agentStatistics.setAgentDetails( agentDetails );
        agentStatistics.setKey( "score" );
        agentStatistics.setValue( "2.2" );
        agentStatistics.setCreatedDate( new DateTime() );

        List< AgentStatistics > agentStatisticsList = new ArrayList<>();
        agentStatisticsList.add( agentStatistics );

        AgentStatisticsResponse agentResponse = agentStatisticsBuilder.convertTo( agentStatisticsList, null );
        assertNotNull( agentResponse );
        assertEquals( agentStatistics.getValue(), agentResponse.getAgentStatistics().get( 0 ).get( "score" ) );
    }

    /**
     * Test convert from expect exception.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertFromExpectException() {
        agentStatisticsBuilder.convertFrom( new AgentStatisticsResponse(), new ArrayList<>() );
    }
}
