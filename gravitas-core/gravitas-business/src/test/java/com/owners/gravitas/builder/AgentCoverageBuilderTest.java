package com.owners.gravitas.builder;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import org.mockito.InjectMocks;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.AgentCoverageBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.AgentCoverage;
import com.owners.gravitas.dto.request.AgentOnboardRequest;

/**
 * The Class AgentCoverageBuilderTest.
 *
 * @author shivamm
 */
public class AgentCoverageBuilderTest extends AbstractBaseMockitoTest {

    /** The agent source builder. */
    @InjectMocks
    private AgentCoverageBuilder agentCoverageBuilder;

    /**
     * Test convert from should return agent coverage.
     */
    @Test
    public void testConvertFromShouldReturnAgentCoverage() {
        final AgentCoverage actualAgentCoverage = agentCoverageBuilder.convertFrom( new AgentOnboardRequest(), null );

        assertNotNull( actualAgentCoverage );
        assertEquals( actualAgentCoverage.getZip(), "" );
        assertEquals( actualAgentCoverage.getType(), "" );
        assertEquals( ( actualAgentCoverage.isServable() ), true );
    }

    /**
     * Test convert from should return null when source and destination are
     * null.
     */
    @Test
    public void testConvertFromShouldReturnNullWhenSourceAndDestinationAreNull() {
        final AgentCoverage actualAgentCoverage = agentCoverageBuilder.convertFrom( null, null );
        assertNull( actualAgentCoverage );
    }

    /**
     * Test convert from should return agent coverage when source and
     * destination are not null.
     */
    @Test
    public void testConvertFromShouldReturnAgentCoverageWhenSourceAndDestinationAreNotNull() {
        final AgentOnboardRequest request = new AgentOnboardRequest();
        final AgentCoverage agentCoverage = new AgentCoverage();
        agentCoverage.setZip( "testZip" );
        agentCoverage.setServable( false );
        agentCoverage.setType( "testType" );

        final AgentCoverage actualAgentCoverage = agentCoverageBuilder.convertFrom( request, agentCoverage );

        assertNotNull( actualAgentCoverage );
        assertEquals( actualAgentCoverage.getZip(), agentCoverage.getZip() );
        assertEquals( actualAgentCoverage.getType(), agentCoverage.getType() );
        assertEquals( actualAgentCoverage.isServable(), agentCoverage.isServable() );
    }

    /**
     * Test convert to should throw unsupported operation exception.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertToShouldThrowUnsupportedOperationException() {
        AgentOnboardRequest req = agentCoverageBuilder.convertTo( new AgentCoverage() );
        assertNotNull( req );
    }
}
