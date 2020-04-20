package com.owners.gravitas.builder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.amqp.AgentSource;
import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.builder.SoldStagePTSEmailNotificationBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class SoldStagePTSEmailNotificationBuilderTest.
 *
 * @author shivamm
 */
public class SoldStagePTSEmailNotificationBuilderTest extends AbstractBaseMockitoTest {

    /** The sold stage PTS email notification builder. */
    @InjectMocks
    private SoldStagePTSEmailNotificationBuilder soldStagePTSEmailNotificationBuilder;

    /** The property writer. */
    @Mock
    private PropertyWriter propertyWriter;

    /** The Constant minutes. */
    private static final int minutes1 = 4320;

    /**
     * Sets the up.
     */
    @BeforeMethod
    public void setUp() {
        ReflectionTestUtils.setField( soldStagePTSEmailNotificationBuilder, "agentSoldStageMailDelayMinutes",
                minutes1 );
        ReflectionTestUtils.setField( soldStagePTSEmailNotificationBuilder, "ptsFromEmail", "pts@owners.com" );

    }

    /**
     * Test convert to for FL state.
     */
    @Test
    public void testConvertToForStateWithPtsTrue() {
        AgentSource source = new AgentSource();
        source.setName( "test" );
        source.setEmail( "test@test.com" );
        OpportunitySource opportunitySource = new OpportunitySource();
        opportunitySource.setPropertyState( "FL" );
        opportunitySource.setOpportunityType( "Buyer" );
        EmailNotification emailNotification = soldStagePTSEmailNotificationBuilder.convertTo( true, source,
                opportunitySource );
        assertEquals(
                ReflectionTestUtils.getField( SoldStagePTSEmailNotificationBuilder.class, "PTS_CLOSED_SALE_PTS_EMAIL" ),
                emailNotification.getMessageTypeName() );
    }

    /**
     * Test convert to for different state with PTS false.
     */
    @Test
    public void testConvertToForDifferentStateWithPTSFalse() {
        AgentSource source = new AgentSource();
        source.setName( "test" );
        source.setEmail( "test@test.com" );
        OpportunitySource opportunitySource = new OpportunitySource();
        opportunitySource.setPropertyState( "test" );
        opportunitySource.setOpportunityType( "Buyer" );
        EmailNotification emailNotification = soldStagePTSEmailNotificationBuilder.convertTo( false, source,
                opportunitySource );
        assertEquals( ReflectionTestUtils.getField( SoldStagePTSEmailNotificationBuilder.class,
                "PTS_CLOSED_SALE_WITHOUT_PTS" ), emailNotification.getMessageTypeName() );
    }

    /**
     * Test convert from should throw exception.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertFromShouldThrowException() {
        EmailNotification source = null;
        Map< String, Object > destination = null;
        soldStagePTSEmailNotificationBuilder.convertFrom( source, destination );
    }

    /**
     * Test convert to for null source.
     */
    @Test
    public void testConvertToForNullSource() {
        EmailNotification destination = null;
        AgentSource source = null;
        OpportunitySource opportunitySource = new OpportunitySource();
        EmailNotification emailNotification = soldStagePTSEmailNotificationBuilder.convertTo( true, source,
                opportunitySource );
        assertEquals( destination, emailNotification );
    }

    /**
     * Test get agent pending sale mail delay minutes.
     */
    @Test
    public void testGetAgentPendingSaleMailDelayMinutes() {
        int agentPendingSaleMailDelayMinutes = soldStagePTSEmailNotificationBuilder.getAgentSoldStageMailDelayMinutes();
        assertEquals( minutes1, agentPendingSaleMailDelayMinutes );
    }

    /**
     * Test set pending sale mail delay minutes.
     */
    @Test
    public void testSetPendingSaleMailDelayMinutes() {
        int val = 1;
        soldStagePTSEmailNotificationBuilder.setAgentSoldStageMailDelayMinutes( val );
        assertEquals( val, ReflectionTestUtils.getField( soldStagePTSEmailNotificationBuilder,
                "agentSoldStageMailDelayMinutes" ) );
        verify( propertyWriter ).saveJmxProperty( Mockito.anyString(), Mockito.anyInt() );
    }
}
