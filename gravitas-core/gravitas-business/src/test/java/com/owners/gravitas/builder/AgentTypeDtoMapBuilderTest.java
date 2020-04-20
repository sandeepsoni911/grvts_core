package com.owners.gravitas.builder;

import static com.owners.gravitas.enums.AgentType.AVERAGE;
import static com.owners.gravitas.enums.AgentType.GOOD;
import static com.owners.gravitas.enums.AgentType.NEW;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.AgentTypeDtoMapBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.HappyAgentsConfig;
import com.owners.gravitas.dao.dto.AgentTypeDto;
import com.owners.gravitas.dao.dto.CbsaAgentDetail;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.enums.AgentType;

/**
 * The Class AgentTypeDtoMapBuilderTest.
 * 
 * @author ankusht
 */
public class AgentTypeDtoMapBuilderTest extends AbstractBaseMockitoTest {

    /** The agent type dto map builder. */
    @InjectMocks
    private AgentTypeDtoMapBuilder agentTypeDtoMapBuilder;

    /** The happy agents config. */
    @Mock
    private HappyAgentsConfig happyAgentsConfig;

    /**
     * Test convert to should return null for null source.
     */
    @Test
    public void testConvertToShouldReturnNullForNullSource() {
        final List< CbsaAgentDetail > source = null;
        final Map< AgentType, List< AgentTypeDto > > destination = MapUtils.EMPTY_SORTED_MAP;
        final Map< AgentType, List< AgentTypeDto > > actual = agentTypeDtoMapBuilder.convertTo( source, destination );
        assertNull( actual );
        verifyZeroInteractions( happyAgentsConfig );
    }

    /**
     * Test convert to should convert source into destination.
     */
    @Test
    public void testConvertToShouldConvertSourceIntoDestination() {
        final List< CbsaAgentDetail > source = new LinkedList<>();
        final Map< AgentType, List< AgentTypeDto > > destination = null;

        final CbsaAgentDetail dtl1 = new CbsaAgentDetail();
        final String cbsa = "cbsa1";
        dtl1.setCbsa( cbsa );
        final AgentDetails agentDetails1 = new AgentDetails();
        agentDetails1.setScore( 72.0 );
        dtl1.setAgentDetails( agentDetails1 );
        source.add( dtl1 );

        final CbsaAgentDetail dtl2 = new CbsaAgentDetail();
        dtl2.setCbsa( cbsa );
        final AgentDetails agentDetails2 = new AgentDetails();
        agentDetails2.setScore( 22.0 );
        dtl2.setAgentDetails( agentDetails2 );
        source.add( dtl2 );

        final CbsaAgentDetail dtl3 = new CbsaAgentDetail();
        dtl3.setCbsa( cbsa );
        final AgentDetails agentDetails3 = new AgentDetails();
        agentDetails3.setScore( 100.0 );
        dtl3.setAgentDetails( agentDetails3 );
        source.add( dtl3 );

        final CbsaAgentDetail dtl4 = new CbsaAgentDetail();
        dtl4.setCbsa( cbsa );
        final AgentDetails agentDetails4 = new AgentDetails();
        agentDetails4.setScore( null );
        dtl4.setAgentDetails( agentDetails4 );
        source.add( dtl4 );

        when( happyAgentsConfig.getAgentTypeAverageLowThresholdScore() ).thenReturn( 20.0 );
        when( happyAgentsConfig.getAgentTypeAverageHighThresholdScore() ).thenReturn( 40.0 );
        when( happyAgentsConfig.getAgentTypeNewThresholdScore() ).thenReturn( 100.0 );
        when( happyAgentsConfig.getAgentTypeGoodLowThresholdScore() ).thenReturn( 40.0 );
        when( happyAgentsConfig.getAgentTypeGoodHighThresholdScore() ).thenReturn( 100.0 );

        final Map< AgentType, List< AgentTypeDto > > actual = agentTypeDtoMapBuilder.convertTo( source, destination );
        assertNotNull( actual );
        assertEquals( cbsa, actual.get( GOOD ).get( 0 ).getCbsa() );
        assertEquals( cbsa, actual.get( AVERAGE ).get( 0 ).getCbsa() );
        assertEquals( cbsa, actual.get( NEW ).get( 0 ).getCbsa() );
        assertEquals( agentDetails1, actual.get( GOOD ).get( 0 ).getAgentDetailsList().iterator().next() );
        assertEquals( agentDetails2, actual.get( AVERAGE ).get( 0 ).getAgentDetailsList().iterator().next() );
        assertEquals( agentDetails3, actual.get( NEW ).get( 0 ).getAgentDetailsList().iterator().next() );
        assertEquals( agentDetails4, actual.get( NEW ).get( 0 ).getAgentDetailsList().get( 1 ) );
    }

    /**
     * Test convert from should throw exception.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertFromShouldThrowException() {
        final Map< AgentType, List< AgentTypeDto > > source = null;
        final List< CbsaAgentDetail > destination = null;
        agentTypeDtoMapBuilder.convertFrom( source, destination );
    }
}
